// Error pages back buttons
document.addEventListener("DOMContentLoaded", function () {
    const backButton = document.getElementById("backButton");

    if (backButton) {
        backButton.addEventListener("click", function () {
            window.location.href = "/note/list";
        });
    }
});

// Copy share link to clipboard
function copyTextToClipboard(text) {
    const textArea = document.createElement("textarea");
    textArea.value = text;
    document.body.appendChild(textArea);
    textArea.select();
    document.execCommand("copy");
    document.body.removeChild(textArea);
}
const links = document.querySelectorAll(".share");
links.forEach(link => {
    link.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent the default link behavior
        const linkText = link.href; // Get the link's href
        copyTextToClipboard(linkText); // Copy the link to clipboard
        alert(`Note link copied to clipboard:\n${linkText}`);
    });
});

// Note delete alert
function showDeleteAlert() {
        if (confirm("Are you sure you want to delete this note?")) {
            document.querySelector("form").submit();
        } else {
            event.preventDefault();
        }
}