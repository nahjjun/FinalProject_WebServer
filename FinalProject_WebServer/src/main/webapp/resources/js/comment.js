document.addEventListener('DOMContentLoaded', function () {
  const buttons = document.querySelectorAll('.toggle-edit');

  buttons.forEach(button => {
    button.addEventListener('click', function () {
      const commentId = this.getAttribute('data-comment-id');
      const form = document.getElementById('edit-form-' + commentId);
      if (form.style.display === 'none') {
        form.style.display = 'block';
      } else {
        form.style.display = 'none';
      }
    });
  });
});