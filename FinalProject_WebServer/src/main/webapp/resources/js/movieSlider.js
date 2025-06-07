document.addEventListener("DOMContentLoaded", function () {
  const slider = document.getElementById("movieSlider");
  const leftBtn = document.querySelector(".scroll-btn.left");
  const rightBtn = document.querySelector(".scroll-btn.right");

  if (slider && leftBtn && rightBtn) {
    leftBtn.addEventListener("click", () => {
      slider.scrollBy({ left: -300, behavior: "smooth" });
    });

    rightBtn.addEventListener("click", () => {
      slider.scrollBy({ left: 300, behavior: "smooth" });
    });
  }
});
