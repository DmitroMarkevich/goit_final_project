const simplemde = new SimpleMDE({element: document.getElementById("content")});
const form = document.querySelector('.edit-note-form');
const contentTextarea = document.getElementById("content");
const saveButton = document.querySelector('button[type="submit"]');

contentTextarea.addEventListener('input', function () {
    if (contentTextarea.value.trim().length >= 5) {
        saveButton.removeAttribute('disabled');
    } else {
        saveButton.setAttribute('disabled', 'disabled');
    }
});

form.addEventListener('submit', function (event) {
    if (contentTextarea.value.trim().length < 5) {
        event.preventDefault();
        alert("Please enter content with at least 5 characters.");
    }
});