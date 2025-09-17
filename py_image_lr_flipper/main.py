import tkinter as tk
from tkinter import messagebox
import tkinter.ttk as ttk
import tkinterdnd2 as tkdnd
from PIL import Image
import os
import threading

def flip_images_worker(file_paths, progress_bar, status_label, root):
    """
    이미지 파일을 좌우로 뒤집어 'flipped' 폴더에 저장하는 워커 함수.
    """
    total_files = len(file_paths)
    if not total_files:
        return

    for i, file_path in enumerate(file_paths):
        try:
            status_label.config(text=f"처리 중: {os.path.basename(file_path)}")
            progress_bar["value"] = (i + 1) / total_files * 100
            root.update_idletasks()

            img = Image.open(file_path)
            flipped_img = img.transpose(Image.FLIP_LEFT_RIGHT)
            original_dir = os.path.dirname(file_path)
            output_dir = os.path.join(original_dir, "flipped")

            if not os.path.exists(output_dir):
                os.makedirs(output_dir)

            output_path = os.path.join(output_dir, os.path.basename(file_path))
            flipped_img.save(output_path)

        except Exception as e:
            messagebox.showerror("오류", f"파일 처리 중 오류 발생: {file_path}\n오류 내용: {e}")
            return

    # 모든 작업이 완료된 후 완료 팝업창 띄우기
    # 이 팝업창이 확인되면 창이 자동으로 닫힘
    messagebox.showinfo("완료", "모든 이미지 처리가 완료되었습니다. Profit!")
    root.destroy()  # 완료 메시지 박스에서 "확인"을 누르면 GUI 창 종료

def drop_handler(event):
    """
    드래그 앤 드롭 이벤트를 처리하는 핸들러.
    """
    file_paths = root.tk.splitlist(event.data)

    if file_paths:
        progress_bar = ttk.Progressbar(root, orient="horizontal", length=300, mode="determinate")
        progress_bar.pack(pady=10)

        status_label = tk.Label(root, text="이미지 처리 대기 중...", fg="blue")
        status_label.pack(pady=5)

        # 별도 스레드에서 이미지 처리 함수 실행
        threading.Thread(
            target=flip_images_worker,
            args=(file_paths, progress_bar, status_label, root)
        ).start()
    else:
        # 파일이 드래그되지 않았을 때의 안내 메시지
        messagebox.showinfo("알림", "유효한 이미지 파일을 드래그하지 않았습니다.")

# GUI 창 설정
root = tkdnd.Tk()
root.title("이미지 뒤집기 프로그램")
root.geometry("400x200")

# 안내 라벨
info_label = tk.Label(root, text="이미지 파일을 여기에 드래그하여 놓으세요.", bd=2, relief="solid")
info_label.pack(expand=True, fill="both", padx=20, pady=20)

# 드래그 앤 드롭 이벤트 바인딩
info_label.drop_target_register(tkdnd.DND_FILES)
info_label.dnd_bind('<<Drop>>', drop_handler)

root.mainloop()
