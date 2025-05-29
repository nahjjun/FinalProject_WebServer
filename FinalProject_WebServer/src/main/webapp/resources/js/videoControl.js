function togglePlay() {
  const video = document.querySelector(".sgv-video"); // ← 너가 쓰는 클래스 기준
  const btn = document.getElementById("playBtn");

  if (video.paused) {
    video.play();
    btn.innerText = "정지하기 ⏸";
  } else {
    video.pause();
    btn.innerText = "재생하기 ▶️";
  }
}

function toggleMute() {
  const video = document.querySelector(".sgv-video");
  const btn = document.getElementById("muteBtn");

  video.muted = !video.muted;

  if (video.muted) {
    btn.innerText = "소리 켜기 🔇";
  } else {
    btn.innerText = "음소거 🔊";
  }
}
