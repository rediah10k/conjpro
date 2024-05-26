const charts = {};  // Store chart instances
let codigoAsamblea = 0;
let needRecharge = false;
let actualizarInterval;

const traerResultados = async (codigo) => {
    let request = await fetch(`/resultado?codigoAsamblea=${codigo}`);
    if (request.ok) {
        let response = await request.json();
        return await response;
    } else {
        alert(await request.text());
        return [];
    }
};

const porcentajesPregunta = (resultadoPregunta) => {
    let resultados = resultadoPregunta.resultados;
    let sumaDeVotos = 0;

    for (const key in resultados) {
        sumaDeVotos += resultados[key];
    }

    let porcentajes = [];

    for (const key in resultados) {
        let porcentaje = (resultados[key] / sumaDeVotos) * 100;
        porcentajes.push(porcentaje);
    }

    return porcentajes;
};

const respuestas = (resultadoPregunta) => {
    let resultados = resultadoPregunta.resultados;
    let respuestas = [];

    for (const key in resultados) {
        respuestas.push(key);
    }

    return respuestas;
};

const actualizarResultados = async () => {
    console.log("Entrando a método actualizar resultados");
    const codigoGuardado = codigoAsamblea;
    const diagramasContainer = document.getElementById("diagramas-container");
    console.log(codigoGuardado)
    let resultados = await traerResultados(codigoGuardado);
    console.log(resultados);

    if (needRecharge) {
        diagramasContainer.innerHTML = '';  // Clear all previous content if needRecharge is true
        for (const key in charts) {
            if (charts.hasOwnProperty(key)) {
                charts[key].destroy();
            }
        }
        needRecharge = false;  // Reset the needRecharge flag
    }

    resultados.forEach((resultado, index) => {
        let canvasId = `pregunta-${index}`;

        if (!document.getElementById(canvasId)) {
            console.log("Entrando a función de generación de preguntas y respuestas");
            const div = document.createElement("div");
            div.className = 'diagrama p-4 border border-gray-300 rounded-lg flex flex-col items-center w-full sm:w-1/2 md:w-1/3 lg:w-1/4';

            const textoPregunta = document.createElement("h2");
            textoPregunta.className = 'text-lg font-semibold mb-2';
            textoPregunta.textContent = resultado.pregunta;

            const canvas = document.createElement("canvas");
            canvas.id = canvasId;

            div.appendChild(textoPregunta);
            div.appendChild(canvas);
            diagramasContainer.appendChild(div);
        }

        let datos = {
            labels: respuestas(resultado),
            datasets: [{
                data: porcentajesPregunta(resultado),
                backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 159, 64, 0.2)', 'rgba(255, 205, 86, 0.2)', 'rgba(201, 203, 207, 0.2)', 'rgba(54, 162, 235, 0.2)'],
                borderColor: ['rgb(75, 192, 192)', 'rgb(255, 159, 64)', 'rgb(255, 205, 86)', 'rgb(201, 203, 207)', 'rgb(54, 162, 235)'],
                borderWidth: 1
            }]
        };

        let opciones = {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false,  // Disable the legend
                },
                title: {
                    display: true,
                    text: `Porcentaje de votos pregunta ${index + 1}`,
                    fontSize: 20
                }
            }
        };

        let ctx = document.getElementById(canvasId).getContext('2d');

        if (charts[canvasId]) {
            charts[canvasId].data = datos;
            charts[canvasId].options = opciones;  // Update options to hide legend
            charts[canvasId].update();
        } else {
            charts[canvasId] = new Chart(ctx, {
                type: 'bar',
                data: datos,
                options: opciones
            });
        }
    });
};

document.getElementById("buscarAsamblea").addEventListener("click", () => {
    const codigoLoaded = document.getElementById("codigoAsamblea").value;

    if (codigoLoaded !== codigoAsamblea) {
        needRecharge = true;
    } else {
        needRecharge = false;
    }

    codigoAsamblea = codigoLoaded;

    if (needRecharge) {
        const diagramasContainer = document.getElementById("diagramas-container");
        diagramasContainer.innerHTML = '';
        // Destruir los gráficos existentes
        for (const key in charts) {
            if (charts.hasOwnProperty(key)) {
                charts[key].destroy();
                delete charts[key];
            }
        }
    }

    actualizarResultados();
    clearInterval(actualizarInterval);  // Limpiar el intervalo anterior si existe
    actualizarInterval = setInterval(actualizarResultados, 10000);  // Establecer un nuevo intervalo
});
