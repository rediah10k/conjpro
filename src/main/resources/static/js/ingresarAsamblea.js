let error = true;


document.getElementById("enviar").addEventListener("click", (event) => {
    sessionStorage.setItem("codigoAsamblea", document.getElementById("codigoAsm").value);
  });

  document.getElementById("idUsuario").value = sessionStorage.getItem("idUsuario");

  addEventListener("DOMContentLoaded", (event) => {
      var form = document.getElementById("contentForm");
      var successSpan = document.getElementById('successSpan');
      if (successSpan.textContent.trim().length > 0) {
          form.classList.add("hidden");
          error = false;
      }
  });

document.getElementById("enviar").addEventListener("click",()=>{
    sessionStorage.setItem("enviado", true);
});

setInterval(async () => {
    let enviado = sessionStorage.getItem("enviado") === null? false : true;

    console.log(enviado);
    if(!enviado) return;

    let codigoAsamblea = sessionStorage.getItem("codigoAsamblea");

    if (codigoAsamblea !== undefined) {

        let request = await fetch(`/asamblea?codigo=${codigoAsamblea}`);
        
        if (request.status === 200) {
            let response = await request.json();
            
            console.log(response);

            if (response.iniciada && response.horaFinalizacion === null && !error) {
                location.href = `asambleaIniciada`;
            }
        }
        else {
            error = true;
            alert(await request.text());
            sessionStorage.removeItem("enviado");
        }
    }
}, 1000);