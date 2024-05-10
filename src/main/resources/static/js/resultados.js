const traerResultados = async(codigo)=>{

    let request = await fetch(`/resultado?codigoAsamblea=${codigo}`);

    if (request.ok) {

        let response = await request.json();

        return await response;
    }
    else alert(await request.text());

}

const porcentajesPregunta = (resultadoPregunta)=>{

    let resultados = resultadoPregunta.resultados;

    let sumaDeVotos = 0;

    for (const key in resultados) {
        sumaDeVotos += resultados[key];
    }

    let porcentajes = [];

    for (const key in resultados) {
        let porcentaje = (resultados[key]/sumaDeVotos)*100;
        porcentajes.push(porcentaje);
    }

    return porcentajes;
}

const respuestas = (resultadoPregunta)=>{

    let resultados = resultadoPregunta.resultados;

    let respuestas = [];

    for (const key in resultados) {
        respuestas.push(key);
    }

    return respuestas;
}


document.getElementById("buscarAsamblea").addEventListener("click",async ()=>{

    const codigoAsamblea = document.getElementById("codigoAsamblea").value;
    const diagramas  = document.getElementById("diagramas");

    let resultados = await traerResultados(codigoAsamblea);

    let i = 1;

    resultados.forEach(resultado => {

        const div = document.createElement("div");
        div.style.paddingTop = "5%"
        const textoPregunta = document.createElement("label");

        textoPregunta.textContent = resultado.pregunta;
        textoPregunta.style.paddingLeft = "15%";
        textoPregunta.style.paddingBottom = "5%"
        textoPregunta.style.fontSize = "25px"

        const canvas = document.createElement("canvas");
        canvas.id = `pregunta ${i}`;
        canvas.width = 400;
        canvas.height = 400;

        let datos = {
            labels: respuestas(resultado),
            datasets: [{
                data: porcentajesPregunta(resultado)
            }]
          };
          
          let opciones = {
            responsive: false,
            maintainAspectRatio: false,
            title: {
                display: true,
                text: `Porcentaje de votos pregunta ${i}`,
                fontSize: 40
            }
          };
          
          let ctx = canvas.getContext('2d');
          new Chart(ctx, {
            type: 'bar',
            data: datos,
            options: opciones
          });

          div.appendChild(textoPregunta);
          div.appendChild(canvas);

          diagramas.appendChild(div);

          i++;
    });

});




