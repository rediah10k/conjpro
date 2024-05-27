const toggleButton = document.getElementById('hamburgerButton');
    const navbarMenu = document.getElementById('navbar-hamburger');

    toggleButton.addEventListener('click', () => {
        navbarMenu.classList.toggle('hidden');
    });

document.getElementById('infoButton').addEventListener('click', function () {
    document.getElementById('infoDropdown').classList.toggle('show');
});


window.addEventListener('click', function (e) {
    if (!document.getElementById('infoButton').contains(e.target)) {
        document.getElementById('infoDropdown').classList.remove('show');
    }
});