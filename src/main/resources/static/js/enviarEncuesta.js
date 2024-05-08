let cantidadPreguntas = 0;

let votos = async ()=>{
    
    let respuestasEncuesta = [];

    const request = await fetch(`asamblea?codigo=${sessionStorage.getItem("codigoAsamblea")}`);

    let response = await request.json();

    response.encuesta.preguntas.forEach(pregunta => {
        const respuestas = document.querySelectorAll(`.respuestas-pregunta-${pregunta.idPregunta}`);

        respuestas.forEach(respuesta=>{
            if(respuesta.checked){
                respuestasEncuesta.push({
                    idRespuesta:respuesta.id.split("-")[3]
                });
            }
        });
    });

    let votos = {
        idUsuario:sessionStorage.getItem("idUsuario"),
        respuestasEncuesta
    }

    cantidadPreguntas = response.encuesta.preguntas.length


    return votos;
}


document.getElementById("enviar-encuesta").addEventListener("click",async ()=>{
    
    const votosEncuesta = await votos();

    if (votosEncuesta.respuestasEncuesta.length!==cantidadPreguntas) {
        alert("Por favor responda todas las preguntas");
        return;
    }

    const request = await fetch("votos/guardar",{
        method:"POST",
        headers:{
            "Accept":"application/json",
            "Content-type":"application/json"
        },
        body:JSON.stringify(votosEncuesta)
    });

    if(request.ok) alert("Respuestas guardadas correctamente");
    
    else alert("error interno del servidor");
    

});