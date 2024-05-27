let preguntasGeneradas = false;

let crearListeners = () => {
    const buttons = document.querySelectorAll('[data-accordion-target]');

    buttons.forEach(function (button) {

        button.addEventListener('click', function () {

            let targetSelector = this.getAttribute('data-accordion-target');

            const target = document.querySelector(targetSelector);

            if (target.classList.contains('hidden')) {
                target.classList.remove('hidden');

                this.setAttribute('aria-expanded', 'true');
            } else {

                target.classList.add('hidden');

                this.setAttribute('aria-expanded', 'false');
            }
        });
    });
}


let traerPreguntas = async () => {
    let request = await fetch(`/asamblea?codigo=${sessionStorage.getItem("codigoAsamblea")}`);

    if (request.status === 200) {
        let response = await request.json();

        return response.encuesta.preguntas;
    }

}


let crearAcordion = async () => {

     
    let preguntas = await traerPreguntas();

    preguntas.forEach(pregunta=>{ 

        let botonHtml = `<h2 id="accordion-flush-heading-${pregunta.idPregunta}">
  <button disabled type="button" id=${pregunta.idPregunta} class="flex items-center justify-between w-full py-5 font-medium rtl:text-right text-gray-500 border-b border-gray-200 dark:border-gray-700 dark:text-gray-400 gap-3" data-accordion-target="#accordion-flush-body-${pregunta.idPregunta}" aria-expanded="true" aria-controls="accordion-flush-body-${pregunta.idPregunta}">
    <span>${pregunta.pregunta}</span>
    <svg data-accordion-icon class="w-3 h-3 rotate-180 shrink-0" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
      <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5 5 1 1 5"/>
    </svg>
  </button>
</h2>`;
        let contenidoHtml = `<div id="accordion-flush-body-${pregunta.idPregunta}" class="hidden" aria-labelledby="accordion-flush-heading-${pregunta.idPregunta}">
      </div>
      `;
        
        document.getElementById('accordion-flush').insertAdjacentHTML('beforeend', botonHtml + contenidoHtml);

        pregunta.respuestas.forEach(respuesta=>{
            let respuestaHtml = `<div class="py-5 border-b border-gray-200 dark:border-gray-700">
            <input id="pregunta-${pregunta.idPregunta}-respuesta-${respuesta.idRespuesta}" type="radio" value="" class=" respuestas-pregunta-${pregunta.idPregunta} w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600" value=1>
            <label for="link-radio" class="ms-2 font-medium text-gray-900 dark:text-gray-300">${respuesta.respuesta}.</label>
          </div>`;

            document.getElementById(`accordion-flush-body-${pregunta.idPregunta}`).insertAdjacentHTML('beforeend', respuestaHtml);
        });

        const respuestas = document.querySelectorAll(`.respuestas-pregunta-${pregunta.idPregunta}`);
        
        respuestas.forEach(respuesta=>{
            respuesta.addEventListener("click",()=>{
                respuestas.forEach(rb=>rb.checked = false);

                respuesta.checked = true;
                console.log(respuesta.id);
            });
        });
    });

    crearListeners();
    preguntasGeneradas = true;
}

crearAcordion();

setInterval(async () => {
    
    let preguntas = await traerPreguntas()

    if (!preguntasGeneradas) return;

    preguntas.forEach(pregunta=>{
        const buttonPregunta = document.getElementById(`${pregunta.idPregunta}`);
        if (pregunta.activada) buttonPregunta.disabled = false;
    });

}, 1000);