let asambleaIniciada = false;

function porcentajeUnidos() {
  let conecciones = JSON.parse(sessionStorage.getItem("unidos"));

  let conectados = conecciones.filter(coneccion => coneccion === true).length;
  let desconectados = conecciones.length - conectados;

  let quorum = parseInt(conecciones.length / 2) + 1;

  let iniciarAsamblea = document.getElementById("iniciarAsamblea");

  // Eliminar todos los listeners del botón anterior
  let nuevoIniciarAsamablea = iniciarAsamblea.cloneNode(true);
  iniciarAsamblea.parentNode.replaceChild(nuevoIniciarAsamablea, iniciarAsamblea);

  // Eliminar todos los listeners del botón anterior
  nuevoIniciarAsamablea.removeEventListener("click", iniciarAsambleaClickHandler);

  if (conectados >= quorum) {
      nuevoIniciarAsamablea.addEventListener("click", async (e) => {
          e.preventDefault();
          await fetch(`/iniciarAsamblea?codigo=${localStorage.getItem("codigoAsamblea")}`);
          location.href = "asambleaIniciadaAdmin";
      });
  } else {
      nuevoIniciarAsamablea.addEventListener("click", (e) => {
          e.preventDefault();
          insuficientesConecciones(quorum, conectados);
      });
  }

  let porcentajeConectados = conectados / conecciones.length * 100;
  let porcentajeDesconectados = desconectados / conecciones.length * 100;

  return [porcentajeDesconectados, porcentajeConectados];
}

let iniciarAsambleaClickHandler = () => {
  let conecciones = JSON.parse(sessionStorage.getItem("unidos"));
  let conectados = conecciones.filter(coneccion => coneccion === true).length;
  let quorum = parseInt(conecciones.length / 2) + 1;
  if (conectados >= quorum) {
    if(!asambleaIniciada){
      fetch(`/iniciarAsamblea?codigo=${localStorage.getItem("codigoAsamblea")}`);
    }
    else alert("Ya se ha iniciado la asamblea");
      
  } else {
      insuficientesConecciones(quorum, conectados);
  }
};

let insuficientesConecciones = (quorum, conectados) => {
  alert(`No se puede iniciar la asamblea, no hay suficientes usuarios conectados. Se necesitan ${quorum - conectados} usuarios más conectados para iniciar la asamblea.`);
}

let datos = {
  labels: ["Desconectados", "Conectados"],
  datasets: [{
      data: porcentajeUnidos(),
      backgroundColor: [
          'rgba(255, 0, 0, 0.5)', // Rojo para desconectados
          'rgba(0, 255, 0, 0.5)'  // Verde para conectados
      ],
      borderColor: [
          'rgba(255, 0, 0, 1)', // Rojo para desconectados
          'rgba(0, 255, 0, 1)'  // Verde para conectados
      ],
      borderWidth: 2
  }]
};

// Opciones del gráfico
let opciones = {
  responsive: false,
  maintainAspectRatio: false,
  title: {
      display: true,
      text: 'Porcentaje de personas conectadas y desconectadas',
      fontSize: 20
  }
};

// Crear el gráfico de pastel
let ctx = document.getElementById('miGraficoDePastel').getContext('2d');
let miGrafico = new Chart(ctx, {
  type: 'pie',
  data: datos,
  options: opciones
});

// Actualizar los datos del gráfico cada segundo
setInterval(function () {
  miGrafico.data.datasets[0].data = porcentajeUnidos();
  miGrafico.update();
}, 1000);
