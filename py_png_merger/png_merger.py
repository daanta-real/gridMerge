# png_merger.py
import os
from PIL import Image

def merge_png_files(input_folder: str, output_path: str, tile_width: int, tile_height: int):
    """
    지정된 폴더의 PNG 이미지를 순서대로 읽어와 크기를 조절한 후,
    가로 10개씩 타일 형태로 합쳐 하나의 PNG 파일로 저장합니다.

    Args:
        input_folder (str): 원본 이미지가 있는 폴더 경로.
        output_path (str): 결과로 저장될 파일 경로.
        tile_width (int): 타일의 가로 크기 (픽셀).
        tile_height (int): 타일의 세로 크기 (픽셀).
    """
    if not os.path.isdir(input_folder):
        raise FileNotFoundError(f"'{input_folder}' 폴더를 찾을 수 없습니다.")

    image_names = sorted(os.listdir(input_folder), key=lambda x: (int(x[:-5]), x[-5:]))
    png_files = [f for f in image_names if f.lower().endswith('.png')]

    if not png_files:
        raise ValueError("처리할 PNG 이미지가 없습니다.")

    resized_images = []
    tile_size = (tile_width, tile_height)

    for filename in png_files:
        print(f"파일명: {filename}")
        filepath = os.path.join(input_folder, filename)
        try:
            with Image.open(filepath) as img:
                original_width, original_height = img.size

                if original_width > original_height:
                    new_width = tile_width
                    new_height = int(tile_width * original_height / original_width)
                else:
                    new_height = tile_height
                    new_width = int(tile_height * original_width / original_height)

                resized_img = img.resize((new_width, new_height), Image.Resampling.LANCZOS)

                new_img = Image.new('RGBA', tile_size, (0, 0, 0, 0))

                paste_x = (tile_size[0] - new_width) // 2
                paste_y = (tile_size[1] - new_height) // 2
                new_img.paste(resized_img, (paste_x, paste_y))

                resized_images.append(new_img)
        except Exception as e:
            print(f"오류 발생: {filename} - {e}")
            continue

    if not resized_images:
        raise RuntimeError("이미지 처리 중 오류가 발생하여 최종 이미지를 생성할 수 없습니다.")
    else:
        print("최종 파일 생성 준비 완료")

    images_per_row = 10
    num_images = len(resized_images)
    num_rows = (num_images + images_per_row - 1) // images_per_row

    total_width = images_per_row * tile_size[0]
    total_height = num_rows * tile_size[1]

    stitched_image = Image.new('RGBA', (total_width, total_height))
    print("stitched image 생성 완료")

    for i, img in enumerate(resized_images):
        row = i // images_per_row
        col = i % images_per_row

        x_offset = col * tile_size[0]
        y_offset = row * tile_size[1]

        stitched_image.paste(img, (x_offset, y_offset))

    # ⚠️ 이곳에 폴더 생성 로직을 추가했습니다.
    output_dir = os.path.dirname(output_path)
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    stitched_image.save(output_path)
    print(f"이미지 저장 to {output_path}")
