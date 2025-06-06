function openModal() {
  document.getElementById("editModal").style.display = "block";
}

function closeModal() {
  document.getElementById("editModal").style.display = "none";
}

function openProfileModal() {
  document.getElementById('profileModal').style.display = 'block';
}

function closeProfileModal() {
  document.getElementById('profileModal').style.display = 'none';
}

function openDeleteModal() {
  document.getElementById('deleteModal').style.display = 'block';
}
function closeDeleteModal() {
  document.getElementById('deleteModal').style.display = 'none';
}

window.onload = function () {
  const closeBtn = document.getElementById("closeModalBtn");
  const closeX = document.querySelector(".modal .close");

  closeBtn.onclick = closeModal;
  closeX.onclick = closeModal;

  window.onclick = function (event) {
    if (event.target === document.getElementById("editModal")) {
      closeModal();
    }
  };
};