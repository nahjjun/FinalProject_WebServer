document.addEventListener('DOMContentLoaded', function () {
  const buttons = document.querySelectorAll('.toggle-edit');

  buttons.forEach(button => {
    button.addEventListener('click', function () {
      const commentId = this.getAttribute('data-comment-id');
      const form = document.getElementById('edit-form-' + commentId);
      
      if (!form) return; // form이 존재하지 않을 경우 방지

      const currentDisplay = window.getComputedStyle(form).display;

      if (currentDisplay === 'none') {
        form.style.display = 'block';
      } else {
        form.style.display = 'none';
      }
    });
  });
});
