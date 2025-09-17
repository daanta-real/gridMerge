# main.py
from png_merger import merge_png_files

def main():
    """
    PNG grid merger CLI 프로그램의 메인 함수입니다.
    사용자에게 필요한 정보를 입력받아 이미지를 합치는 작업을 수행합니다.
    """
    # 1. 프로그램 소개
    print("====================================")
    print("        PNG Grid Merger CLI")
    print("====================================")
    print("이 프로그램은 특정 폴더의 PNG 이미지들을 읽어와")
    print("크기를 조절하고 타일 형태로 합쳐 하나의 파일로 저장합니다.")
    print("------------------------------------\n")

    # 2. 사용자 입력 받기
    input_folder = input("✅ 원본 이미지가 있는 폴더 경로를 입력하세요: ")
    output_filename = input("✅ 결과 파일명을 입력하세요 (예: result.png): ")

    while True:
        try:
            tile_width = int(input("✅ 타일의 가로 크기 (px)를 입력하세요: "))
            tile_height = int(input("✅ 타일의 세로 크기 (px)를 입력하세요: "))
            break
        except ValueError:
            print("❌ 오류: 크기는 정수(숫자)로 입력해야 합니다. 다시 시도해 주세요.")

    # 3. png_merger 모듈 실행
    try:
        merge_png_files(input_folder, output_filename, tile_width, tile_height)
        print("\n✅ 작업이 성공적으로 완료되었습니다!")
    except Exception as e:
        print(f"\n❌ 오류가 발생하여 작업을 완료할 수 없습니다: {e}")
    finally:
        # 4. 결과 메시지 출력 및 프로그램 종료 안내
        print("\n아무 키나 누르면 프로그램이 종료됩니다...")
        # Windows와 Linux/macOS 환경에서 키 입력을 받기 위한 처리
        try:
            input()  # 사용자 입력을 기다립니다.
        except EOFError:
            pass

if __name__ == "__main__":
    main()
