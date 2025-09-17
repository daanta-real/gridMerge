# png_merger.py
import argparse
import os
from PIL import Image

def merge_png_files(input_folder: str, output_filename: str, tile_width: int, tile_height: int):
    """
    지정된 폴더의 PNG 이미지를 순서대로 읽어와 크기를 조절한 후,
    가로 10개씩 타일 형태로 합쳐 하나의 PNG 파일로 저장합니다.

    Args:
        input_folder (str): 원본 이미지가 있는 폴더 경로.
        output_filename (str): 결과로 저장될 파일명.
        tile_width (int): 타일의 가로 크기 (픽셀).
        tile_height (int): 타일의 세로 크기 (픽셀).
    """
    if not os.path.isdir(input_folder):
        print(f"오류: '{input_folder}' 폴더를 찾을 수 없습니다.")
        return

    print(f"'{input_folder}' 폴더의 이미지를 처리하고 있습니다...")

    # 01L, 01R... 순서로 정렬
    image_names = sorted(os.listdir(input_folder), key=lambda x: (int(x[:-5]), x[-5:]))
    png_files = [f for f in image_names if f.lower().endswith('.png')]

    if not png_files:
        print("처리할 PNG 이미지가 없습니다.")
        return

    resized_images = []
    tile_size = (tile_width, tile_height)

    # 이미지 크기 조절
    for filename in png_files:
        filepath = os.path.join(input_folder, filename)
        try:
            with Image.open(filepath) as img:
                original_width, original_height = img.size

                # 가로세로 비율 유지하며 긴 축을 지정된 크기에 맞춤
                if original_width > original_height:
                    new_width = tile_width
                    new_height = int(tile_width * original_height / original_width)
                else:
                    new_height = tile_height
                    new_width = int(tile_height * original_width / original_height)

                resized_img = img.resize((new_width, new_height), Image.Resampling.LANCZOS)

                # 새로운 타일 크기의 투명 이미지 생성
                new_img = Image.new('RGBA', tile_size, (0, 0, 0, 0))

                # 변환된 이미지를 중앙에 붙여넣기
                paste_x = (tile_size[0] - new_width) // 2
                paste_y = (tile_size[1] - new_height) // 2
                new_img.paste(resized_img, (paste_x, paste_y))

                resized_images.append(new_img)
        except Exception as e:
            print(f"오류 발생: {filename} - {e}")
            continue

    # 이미지 합치기 (가로 10개씩)
    images_per_row = 10
    num_images = len(resized_images)
    num_rows = (num_images + images_per_row - 1) // images_per_row

    total_width = images_per_row * tile_size[0]
    total_height = num_rows * tile_size[1]

    stitched_image = Image.new('RGBA', (total_width, total_height))

    for i, img in enumerate(resized_images):
        row = i // images_per_row
        col = i % images_per_row

        x_offset = col * tile_size[0]
        y_offset = row * tile_size[1]

        stitched_image.paste(img, (x_offset, y_offset))

    # 최종 이미지 저장
    stitched_image.save(output_filename)
    print(f"작업 완료: '{output_filename}' 파일이 생성되었습니다.")

# 스크립트를 직접 실행할 때만 아래 코드가 동작합니다.
if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="PNG 이미지들을 타일 형태로 합치는 스크립트입니다.")
    parser.add_argument("input_folder", help="원본 이미지가 있는 폴더 경로")
    parser.add_argument("output_filename", help="결과 파일명 (예: result.png)")
    parser.add_argument("tile_width", type=int, help="타일의 가로 크기 (픽셀)")
    parser.add_argument("tile_height", type=int, help="타일의 세로 크기 (픽셀)")

    args = parser.parse_args()

    merge_png_files(
        input_folder=args.input_folder,
        output_filename=args.output_filename,
        tile_width=args.tile_width,
        tile_height=args.tile_height
    )
