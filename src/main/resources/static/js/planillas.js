//bg-green-500
//bg-red-500

const usersStatus = [];
const unidos = []

const ponerPlanilla = async ()=>{

    let codigoAsamblea = document.getElementById("codigoAsamblea").value;

    if(codigoAsamblea.trim() === "") return "";

    let request = await fetch(`planilla?codigo=${codigoAsamblea}`)

    if(!request.ok) return;

    let planilla = await request.json();
    
    return await planilla.usuarios;

}

const ponerCeldas = async ()=>{
    let usuarios = await ponerPlanilla();

    if(usuarios === "") return;

    usuarios.forEach(usuario => tableCells(usuario));
}

let tableCells = (usuario)=>{
    unidos.push(usuario.asistencia);

    const tableRow = document.createElement("tr");
    const tableRowStyles = "bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600".split(" ");

    tableRowStyles.forEach(style=> tableRow.classList.add(style));

    const tableHead = document.createElement("th");
    const tableHeadStyes = "flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white".split(" ");

    tableHeadStyes.forEach(style=>tableHead.classList.add(style));

    tableHead.innerHTML = `<div class="ps-3">
    <div class="text-base font-semibold">${usuario.nombre} ${usuario.apellido}</div>
    <div class="font-normal text-gray-500">${usuario.correo}</div>
    </div>  `;

    const statusData = document.createElement("td");
    const tdStyles = "px-6 py-4".split(" ");
    tdStyles.forEach(style=>statusData.classList.add(style));
    let status = usuario.asistencia;
    let online = "Online";
    let color = "bg-green-500";

    if (!status) {
        online = "Offline";
        color = "bg-red-500";
    }

    statusData.innerHTML = `<div class="flex items-center">
    <div class="h-2.5 w-2.5 rounded-full ${color} me-2"></div> ${online}
    </div>`;
    statusData.id = `status-${usuario.documento}`;

    const tdEdit = document.createElement("td");
    tdStyles.forEach(style=>tdEdit.classList.add(style));
    tdEdit.innerHTML = `<a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit user</a>`;
    
    usersStatus.push(statusData);
    tableRow.appendChild(tableHead);
    tableRow.appendChild(statusData);
    tableRow.appendChild(tdEdit);
    document.getElementById("table-body").appendChild(tableRow);
    sessionStorage.setItem("unidos", JSON.stringify(unidos));
}

const actualizarEstadoUsuarios = async ()=>{
    let usuarios =await ponerPlanilla();
    
    if(usuarios === "") return;

    let i = 0;
    usuarios.forEach(usuario=>{

        unidos.push(usuario.asistencia);

        if (usuario.asistencia) {
            usersStatus[i].innerHTML =  `<div class="flex items-center">
            <div class="h-2.5 w-2.5 rounded-full bg-green-500 me-2"></div> Online
            </div>`;
        }
        else {
            usersStatus[i].innerHTML =  `<div class="flex items-center">
            <div class="h-2.5 w-2.5 rounded-full bg-red-500 me-2"></div> Offline
            </div>`;
        }

        i++;
    })

    sessionStorage.setItem("unidos", JSON.stringify(unidos));
}

document.getElementById("buscarAsamblea").addEventListener("click",async (e)=>{
    e.preventDefault();
    usersStatus.length = 0;
    document.getElementById("table-body").innerHTML = "";
    unidos.length = 0;
    await ponerCeldas();
})

const actualizarPlanilla = async () => {
    const scrollPosition = window.scrollY; 
    unidos.length = 0;// Guarda la posición actual del scroll
    await actualizarEstadoUsuarios(); // Actualiza el estado de los usuarios
    window.scrollTo(0, scrollPosition); // Restaura la posición del scroll después de la actualización
    setTimeout(actualizarPlanilla, 1000); // Llama a la función nuevamente después de 5 segundos
}

setTimeout(actualizarPlanilla, 10000);