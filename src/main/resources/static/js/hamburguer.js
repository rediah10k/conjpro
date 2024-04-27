const toggleButton = document.getElementById('printoggle');
    const navbarMenu = document.getElementById('navbar-hamburger');

  // Agregar evento de clic al botón de alternar
    toggleButton.addEventListener('click', () => {
        navbarMenu.classList.toggle('hidden');
    });