let pregunta = 2;
let asamblea;
localStorage.codigoAsamblea = undefined

let consultarAsamblea = async (codigo) => {
    if (codigo === undefined || codigo === null) {
        return;
    }

    let request = await fetch(`/asamblea?codigo=${codigo}`, {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    if (request.ok) {
        asamblea = await request.json();
        document.getElementById("default-modal").classList.remove("hidden");

        let encuestas = asamblea.encuestas;
        if (encuestas[0].preguntas.length > 0) {
            ponerPreguntas();
        }

        localStorage.codigoAsamblea = codigo;
    } else alert(await request.text())
}

function toggleCheckbox(current) {
    const propiedad = document.getElementById("propiedad1");
    const coeficiente = document.getElementById("coeficiente1");

    if (current.id === "propiedad1") {
        coeficiente.checked = !current.checked;
    } else {
        propiedad.checked = !current.checked;
    }
}

document.getElementById("default-modal").classList.add("hidden");

document.querySelectorAll('#tabs a').forEach(tab => {
    tab.addEventListener('click', async function (event) {
        event.preventDefault();

        // Hide all tab panels
        document.querySelectorAll('#tabs a').forEach(t => {
            t.classList.replace('text-blue-500', 'text-gray-500');
            t.classList.replace('border-blue-500', 'border-transparent');
            document.querySelector(t.hash).classList.add('hidden');
        });
        // Show selected tab panel
        tab.classList.replace('text-gray-500', 'text-blue-500');
        tab.classList.replace('border-transparent', 'border-blue-500');
        document.querySelector(tab.hash).classList.remove('hidden');
    });
});

// Set default tab
document.querySelector('#default-tab').click();

document.getElementById("nuevaPregunta").addEventListener("click", () => {

    const preguntas = document.getElementById("preguntasGeneradas");

    const div = document.createElement("div");
    div.classList.add("flex");
    div.classList.add("item-center");
    div.style = "padding: 10px";
    div.id = `pregunta${pregunta}`;

    const input = document.createElement("input");
    const inputClasses = "pregunta bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5".split(" ");

    inputClasses.forEach(classI => input.classList.add(classI));

    input.placeholder = "Escribe la pregunta";
    input.type = "text";
    input.style.padding = "10px"
    input.id = `inputPregunta${pregunta}`;

    const button = document.createElement("button");
    const buttonClasses = "focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-3 py-10 me-3 mb-0.5 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900".split(" ");

    buttonClasses.forEach(classB => button.classList.add(classB));

    button.id = `eliminar${pregunta}`;
    button.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: ;msFilter:;"><path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path></svg>`;

    const checkboxClasses = "w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600".split(" ");

    const coeficiente = document.createElement("input");
    coeficiente.type = "checkbox";
    coeficiente.id = `coeficiente${pregunta}`;
    coeficiente.checked = true;
    coeficiente.classList.add("coeficiente");

    const propiedad = document.createElement("input");
    propiedad.type = "checkbox";
    propiedad.id = `propiedad${pregunta}`;
    propiedad.value = "false";

    checkboxClasses.forEach(checkBoxClass => {
        coeficiente.classList.add(checkBoxClass);
        propiedad.classList.add(checkBoxClass);
    })

    coeficiente.onclick = () => {
        propiedad.checked = !coeficiente.checked;
    }

    propiedad.onclick = () => {
        coeficiente.checked = !propiedad.checked;
    }

    const pCoeficiente = document.createElement("p");
    pCoeficiente.innerHTML = "Coeficiente";

    const pPropiedad = document.createElement("p");
    pPropiedad.innerHTML = "Propiedad";

    const checkboxes = document.createElement("div")
    checkboxes.classList.add("flex");
    checkboxes.classList.add("item-center");
    checkboxes.id = `checkboxes${pregunta}`;

    checkboxes.appendChild(coeficiente);
    checkboxes.appendChild(pCoeficiente);

    checkboxes.appendChild(propiedad);
    checkboxes.appendChild(pPropiedad);

    button.addEventListener("click", () => {
        preguntas.removeChild(div);
        preguntas.removeChild(checkboxes);
    })

    div.appendChild(input);
    div.appendChild(button);
    preguntas.appendChild(div);
    preguntas.appendChild(checkboxes);

    pregunta++;
});

document.getElementById("crearPreguntas").addEventListener("click", async (e) => {

    e.preventDefault();

    let preguntas = [];

    const registro = document.getElementById("preguntas").getElementsByClassName("pregunta");
    const coeficientes = document.getElementById("preguntas").getElementsByClassName("coeficiente");

    for (let i = 0; i < registro.length; i++) {
        if (registro.item(i).value === "") {
            alert("Por favor escriba todas las preguntas");
            return;
        }
        preguntas.push({
            pregunta: registro.item(i).value,
            votoCoeficiente: coeficientes.item(i).checked
        });
    }

    let encuesta = {
        preguntas,
        idAsamblea: asamblea.idAsamblea
    }

    let request = await fetch("/encuesta", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(encuesta)
    });

    if (request.ok) {
        alert("Encuesta generada correctamente")
        document.getElementById("preguntasGeneradas").innerHTML = "";
        document.getElementById("inputPregunta1").value = "";
        document.getElementById("coeficiente1").checked = true;
        document.getElementById("propiedad1").checked = false;
        consultarAsamblea(localStorage.codigoAsamblea);


    } else alert(await request.text())

});

let ponerPreguntas = () => {
    document.querySelectorAll('#tabs a').forEach(t => {
        t.classList.replace('text-blue-500', 'text-gray-500');
        t.classList.replace('border-blue-500', 'border-transparent');
        document.querySelector(t.hash).classList.add('hidden');
    });

    document.getElementById("seconda").classList.replace('text-gray-500', 'text-blue-500');
    document.getElementById("seconda").classList.replace('border-transparent', 'border-blue-500');

    document.getElementById("guardarRespuestas").classList.remove("hidden");
    const firts = document.getElementById("first");
    const second = document.getElementById("second");

    firts.classList.add("hidden");
    second.classList.remove("hidden");

    document.getElementById("noPreguntas").innerText = "";

    const campoPreguntas = document.getElementById("campoPreguntas");
    campoPreguntas.innerHTML = "";
    // Iterate over each encuesta
    asamblea.encuestas.forEach(encuesta => {
        // Create a container for the encuesta
        const encuestaContainer = document.createElement("div");
        encuestaContainer.classList.add("encuesta-container");
        encuestaContainer.style.marginRight = "10px";
        encuestaContainer.style.alignContent = "center";
        encuestaContainer.style.padding = "50px";

        let numeroPregunta = 1;

        encuesta.preguntas.forEach(pregunta => {

            const labelNumeroPregunta = document.createElement("label");
            labelNumeroPregunta.textContent = `Pregunta #${numeroPregunta}`;
            numeroPregunta++;
            const preguntaRespuestaContainer = document.createElement("div");
            const classesPreguntasRespuestaContainer = "w-full max-w-sm p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-6 md:p-8 dark:bg-gray-800 dark:border-gray-700".split(" "); 

            classesPreguntasRespuestaContainer.forEach(classP => preguntaRespuestaContainer.classList.add(classP));

            preguntaRespuestaContainer.classList.add("items-center");
            preguntaRespuestaContainer.classList.add("pregunta/respuesta-container");
            preguntaRespuestaContainer.appendChild(labelNumeroPregunta);

            const classesInput = "mb-5 bg-gray-100 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 cursor-not-allowed dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-gray-400 dark:focus:ring-blue-500 dark:focus:border-blue-500".split(" ");

            const preguntaLabel = document.createElement("input");
            preguntaLabel.value = pregunta.pregunta;

            classesInput.forEach(classInput => preguntaLabel.classList.add(classInput));
            preguntaLabel.disabled = true;
            preguntaRespuestaContainer.appendChild(preguntaLabel);
            preguntaRespuestaContainer.appendChild(preguntaLabel);

            const labelRespuesta = document.createElement("Respuestas")
            preguntaRespuestaContainer.appendChild(labelRespuesta);
            let i = 1;

            if (pregunta.respuestas.length!=0) {

                pregunta.respuestas.forEach(respuesta=>{

                    const divContainerRespuestas = document.createElement("div");
                    divContainerRespuestas.classList.add("flex");
                    divContainerRespuestas.classList.add("items-center")

                    const respuestaInput = document.createElement("input");
                    respuestaInput.value = respuesta.respuesta;
                    respuestaInput.id = `respuesta${i}_pregunta${pregunta.idPregunta}`;
                    respuestaInput.classList.add(`respuesta${pregunta.idPregunta}`);
                    const inputClasses = "bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5".split(" ");
                    inputClasses.forEach(classI => respuestaInput.classList.add(classI));
                    respuestaInput.disabled = true;
                    divContainerRespuestas.appendChild(respuestaInput);
                    preguntaRespuestaContainer.appendChild(divContainerRespuestas);

                    const button = document.createElement("button");
                    const buttonClasses = "focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-3 py-10 me-3 mb-0.5 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900".split(" ");

                    buttonClasses.forEach(classB => button.classList.add(classB));

                    button.id = `eliminar${pregunta}`;
                    button.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: ;msFilter:;"><path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path></svg>`;

                    button.addEventListener("click",async ()=>{

                            if(respuestaInput.disabled){
                                let confirmacion = confirm(`¿Esta seguro que desea eliminar la respuesta a la pregunta?`);

                                if (confirmacion) {
                                    console.log(pregunta);
                                    console.log(pregunta.idPregunta);
                                    let request = await fetch(`respuestas?respuesta=${respuestaInput.value}&idPregunta=${pregunta.idPregunta}`,{
                                        method:"DELETE",
                                        headers:{
                                            "Accept":"application/json",
                                            "Content-Type":"application/json"
                                        }
                                    });
                                    preguntaRespuestaContainer.removeChild(divContainerRespuestas)
                                }
                            }
                            else{
                                preguntaRespuestaContainer.removeChild(divContainerRespuestas)
                            }
                        }
                    );
                    divContainerRespuestas.appendChild(button);
                    i++;
                })
                labelRespuesta.innerText = "Respuestas";
            }
            else{
                labelRespuesta.innerText = "Aún no se han creado respuestas para esta pregunta";

            }


            const classesBtn = "text-gray-600 bg-white hover:bg-gray-100 border border-gray-300 font-medium rounded-lg text-sm px-5 py-3 text-center mr-2".split(" ");

            const agregarRespuestaButton = document.createElement("button");
            agregarRespuestaButton.textContent = "Agregar Respuesta";
            agregarRespuestaButton.style = "padding-bottom: 10px";


            agregarRespuestaButton.addEventListener("click", () => {
                const divContainerRespuestas = document.createElement("div");
                divContainerRespuestas.classList.add("flex");
                divContainerRespuestas.classList.add("items-center")

                const respuestaInput = document.createElement("input");
                respuestaInput.placeholder = "Escribe tu respuesta";
                respuestaInput.id = `respuesta${i}_pregunta${pregunta.idPregunta}`;
                respuestaInput.classList.add(`respuesta${pregunta.idPregunta}`);
                const inputClasses = "bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5".split(" ");

                inputClasses.forEach(classI => respuestaInput.classList.add(classI));


                const button = document.createElement("button");
                const buttonClasses = "focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-3 py-10 me-3 mb-0.5 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900".split(" ");

                buttonClasses.forEach(classB => button.classList.add(classB));

                button.id = `eliminar${pregunta}`;
                button.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: ;msFilter:;"><path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path></svg>`;

                button.addEventListener("click",async ()=>{

                        if(respuestaInput.disabled){
                            let confirmacion = confirm(`¿Esta seguro que desea eliminar la respuesta a la pregunta?`);

                            if (confirmacion) {
                                console.log(pregunta);
                                console.log(pregunta.idPregunta);
                                let request = await fetch(`respuestas?respuesta=${respuestaInput.value}&idPregunta=${pregunta.idPregunta}`,{
                                    method:"DELETE",
                                    headers:{
                                        "Accept":"application/json",
                                        "Content-Type":"application/json"
                                    }
                                });
                                preguntaRespuestaContainer.removeChild(divContainerRespuestas)
                            }
                        }
                        else{
                            preguntaRespuestaContainer.removeChild(divContainerRespuestas)
                        }
                    }
                );

                divContainerRespuestas.appendChild(respuestaInput);
                divContainerRespuestas.appendChild(button);
                preguntaRespuestaContainer.insertBefore(divContainerRespuestas, agregarRespuestaButton);
            });
            classesBtn.forEach(classBtn => agregarRespuestaButton.classList.add(classBtn));

            preguntaRespuestaContainer.style.paddingBottom = "10px";
            preguntaRespuestaContainer.appendChild(agregarRespuestaButton);

            // Append the pregunta container to the encuesta container
            encuestaContainer.appendChild(preguntaRespuestaContainer);
        });

        // Append the encuesta container to the second section
        campoPreguntas.appendChild(encuestaContainer);
    });
}

document.getElementById("guardarRespuestas").addEventListener("click",async ()=>{
    //alert("Hola");
    const preguntas = asamblea.encuestas[0].preguntas;
    const respuestasAppend = [];
    let todasLasPreguntasEstanRespondidas = true;
    let todosLosCamposDesabilitados = true;

    for (let index = 0; index < preguntas.length; index++) {
        const respuestas = document.getElementById("campoPreguntas").getElementsByClassName(`respuesta${preguntas[index].idPregunta}`);

        if(respuestas.length!==0){
            for (let j = 0; j < respuestas.length; j++) {

                const item = respuestas.item(j)

                if(item.value === ""){
                    alert("Por favor escriba todas las respuestas a la pregunta "+((preguntas[index].idPregunta-preguntas[0].idPregunta)+1));
                    return;
                }
                if(!item.disabled){
                    todosLosCamposDesabilitados = false;
                    respuestasAppend.push({
                        idPregunta:preguntas[index].idPregunta,
                        respuesta:respuestas.item(j).value
                    })
                }
            }
        }
        else{
            todasLasPreguntasEstanRespondidas = false;
        }
    }

    if(!todasLasPreguntasEstanRespondidas){
        alert("Por favor escriba al menos una respuesta para las preguntas");
    }

    else{
        if (todosLosCamposDesabilitados) return;

        for (let index = 0; index < preguntas.length; index++) {
            const respuestas = document.getElementById("campoPreguntas").getElementsByClassName(`respuesta${preguntas[index].idPregunta}`);

            for (let index = 0; index < respuestas.length; index++) {
                const item = respuestas.item(index);
                item.disabled = true;
            }
        }
        let request = await fetch("/respuestas",{
            method:"POST",
            headers:{
                "Accept":"application/json",
                "Content-Type":"application/json"
            },
            body:JSON.stringify(respuestasAppend)
        })

        if (request.ok) {
            alert("Respuestas guardadas correctamente");
        }else{
            alert(await request.text())
        }
    }
})


document.getElementById("buscarAsamblea").addEventListener("click", async () => {
    const codigoAsamblea = document.getElementById("codigoAsamblea").value;

    if (codigoAsamblea.trim() === "") {
        alert("Por favor escriba un código de asamblea");
        return;
    } else {
        await consultarAsamblea(codigoAsamblea);
    }

});
