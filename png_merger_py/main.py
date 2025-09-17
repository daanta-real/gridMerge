# main.py
import os
import sys
from png_merger import merge_png_files

def main():
    """
    PNG grid merger CLI 프로그램의 메인 함수입니다.
    """
    print("====================================")
    print("        PNG Grid Merger CLI")
    print("====================================")
    print("이 프로그램은 특정 폴더의 PNG 이미지들을 읽어와")
    print("크기를 조절하고 타일 형태로 합쳐 하나의 파일로 저장합니다.")
    print("------------------------------------\n")

    input_folder = input("✅ 원본 이미지가 있는 폴더 경로를 입력하세요: ")
    output_filename = input("✅ 결과 파일명을 입력하세요 (예: result.png): ")

    while True:
        try:
            tile_width = int(input("✅ 타일의 가로 크기 (px)를 입력하세요: "))
            tile_height = int(input("✅ 타일의 세로 크기 (px)를 입력하세요: "))
            break
        except ValueError:
            print("❌ 오류: 크기는 정수(숫자)로 입력해야 합니다. 다시 시도해 주세요.")

    try:
        # png_merger 함수에서 예외가 발생하면 이곳에서 처리됩니다.
        merge_png_files(input_folder, output_filename, tile_width, tile_height)
        print("\n✅ 작업이 성공적으로 완료되었습니다!")
    except FileNotFoundError as e:
        print(f"\n❌ 오류: {e}")
    except ValueError as e:
        print(f"\n❌ 오류: {e}")
    except Exception as e:
        print(f"\n❌ 예상치 못한 오류가 발생했습니다: {e}")
    finally:
        print("\n아무 키나 누르면 프로그램이 종료됩니다...")
        try:
            input()
        except EOFError:
            pass

if __name__ == "__main__":
    main()
