var currentTheme = localStorage.getItem("theme");

    function setTheme(theme) {
        document.documentElement.setAttribute('data-bs-theme', theme)
        localStorage.setItem("theme", theme);
    }
    if (currentTheme) {
        setTheme(currentTheme);
        if (currentTheme === "dark") {
            document.getElementById("themeSwitch").checked = true;
        }
    }
    document.getElementById("themeSwitch").addEventListener("change", function () {
        if (this.checked) {
            setTheme("dark");
        } else {
            setTheme("light");
        }
     });