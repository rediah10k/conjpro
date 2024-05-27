document.addEventListener("DOMContentLoaded", function() {
    const darkModeToggle = document.getElementById('modo-oscuro');
    const textSizeRange = document.getElementById('tamaño-texto');

    // Cargar configuraciones del local storage
    const darkMode = localStorage.getItem('darkMode') === 'true';
    const textSize = localStorage.getItem('textSize') || '100';

    // Aplicar configuraciones iniciales
    if (darkMode) {
        document.body.classList.add('dark-mode');
    }
    document.body.style.setProperty('--text-size', textSize + '%');
    textSizeRange.value = textSize;

    // Manejador para el toggle de modo oscuro
    darkModeToggle.addEventListener('click', () => {
        console.log("modo oscurooo")
        document.body.classList.toggle('dark-mode');
        localStorage.setItem('darkMode', document.body.classList.contains('dark-mode'));
    });

    // Manejador para el ajuste de tamaño de texto
    textSizeRange.addEventListener('input', () => {
        const newSize = textSizeRange.value;
        console.log("letraaa")
        document.body.style.setProperty('--text-size', newSize + '%');
        localStorage.setItem('textSize', newSize);
    });
});