const toggleButton = document.getElementById('printoggle');
    const navbarMenu = document.getElementById('navbar-hamburger');

    toggleButton.addEventListener('click', () => {
        navbarMenu.classList.toggle('hidden');
    });