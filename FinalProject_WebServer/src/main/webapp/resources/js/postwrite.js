// 게시판 구분에 따라 이미지 업로드 필드 제어
function onBoardTypeChange() {
    const boardType = document.getElementById("boardType").value;
    const imageInput = document.getElementById("imageInput");

    if (boardType === "free") {
        imageInput.disabled = true;
        imageInput.style.display = "none";
    } else {
        imageInput.disabled = false;
        imageInput.style.display = "block";
    }
}

// 초기 실행 (페이지 로드 시에도 상태 반영되도록)
window.onload = function () {
    onBoardTypeChange();
};
