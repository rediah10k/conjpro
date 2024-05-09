const preguntasActivadas = [];
let cantidadPreguntas = 0;

let traerPreguntas = async ()=>{

    let request = await fetch(`asamblea?codigo=${localStorage.getItem("codigoAsamblea")}`);

    if(!request.ok){
        throw new Error("No se pudieron traer las preguntas");
    }

    let asamblea = await request.json();

    return await asamblea.encuesta.preguntas;
}

let celdasTablaPreguntas = async ()=>{

    let preguntas = await traerPreguntas();
    cantidadPreguntas = preguntas.length;

    const tableBody = document.getElementById("table-body");

    preguntas.forEach(pregunta => {
        const tableRow = document.createElement("tr");
    const tableRowStyles = "bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600".split(" ");

    tableRowStyles.forEach(style=> tableRow.classList.add(style));

    const tableHead = document.createElement("th");
    const tableHeadStyes = "flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white".split(" ");

    tableHeadStyes.forEach(style=>tableHead.classList.add(style));

    tableHead.innerHTML = `<div class="ps-3">
    <div class="text-base font-semibold">${pregunta.pregunta}</div>
    </div>  `;

    const coeficiente = document.createElement("td");

    coeficiente.innerHTML = `<div class="flex items-center">
    ${pregunta.votoCoeficiente?"Sí":"No"}</div>
    </div>`;

    const tdActivar = document.createElement("td");
    const tdStyles = "px-6 py-4".split(" ");
    tdStyles.forEach(style=>tdActivar.classList.add(style));
    
    const buttonActivar = document.createElement("button");
    const buttonActivarStyles = "focus:outline-none text-white bg-green-500 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-2 py-2 me-1 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800".split(" ");

    buttonActivar.innerText = "Activar";
    buttonActivarStyles.forEach(estilo=>buttonActivar.classList.add(estilo));

    buttonActivar.addEventListener("click",async ()=>{

        let confirma = confirm("¿Está seguro de activar esta pregunta?");

        if(!confirma) return;
        
        await fetch(`activar_pregunta?idPregunta=${pregunta.idPregunta}`,{
            method:"PUT",
            headers:{
                "Content-Type":"application/json"
            }
        });

        preguntasActivadas.push(pregunta.idPregunta);
       
    });

    tdActivar.appendChild(buttonActivar);

    tableRow.appendChild(tableHead);
    tableRow.appendChild(coeficiente);
    tableRow.appendChild(tdActivar);
    
    tableBody.appendChild(tableRow);
    });

}

document.addEventListener("DOMContentLoaded",celdasTablaPreguntas);

document.getElementById("terminarAsamblea").addEventListener("click",async ()=>{
    if(cantidadPreguntas === 0){
        alert("No se puede terminar la asamblea sin preguntas");
        return;
    }

    if(preguntasActivadas.length !== cantidadPreguntas){
        alert("No se puede terminar la asamblea sin activar todas las preguntas");
        return;
    }
    
    let confirma = confirm("¿Está seguro de terminar la asamblea?");

    if(!confirma) return;

    await fetch(`terminar_asamblea?codigo=${localStorage.getItem("codigoAsamblea")}`,{
        method:"PUT",
        headers:{
            "Content-Type":"application/json"
        }
    });

    location.href = "asambleas";
});
