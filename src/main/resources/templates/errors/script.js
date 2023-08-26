document.addEventListener("DOMContentLoaded", function () {
    const backButton = document.getElementById("backButton");

    if (backButton) {
        backButton.addEventListener("click", function () {
            window.location.href = "/";
        });     // Треба прописати логіку перенаправлення в залежності від того чи авторизований користувач
    }
});