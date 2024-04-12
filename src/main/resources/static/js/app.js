document.addEventListener("DOMContentLoaded", (event) => {
    const toggle = document.getElementById("printoggle")
    console.log(toggle)
    const menuDashboard = document.querySelector(".menu-dashboard")
    console.log(menuDashboard)
    const iconoMenu = document.getElementById("i_id")
    const enlacesMenu = document.querySelectorAll(".enlace")
    toggle.addEventListener("click", () => {
        menuDashboard.classList.toggle("open")

        if(iconoMenu.classList.contains("bx-menu")){
            iconoMenu.classList.replace("bx-menu", "bx-x")
        }else {
            iconoMenu.classList.replace("bx-x", "bx-menu")
        }
    })

    enlacesMenu.forEach(enlace => {
        enlace.addEventListener("click", () => {
            menuDashboard.classList.add("open")
            iconoMenu.classList.replace("bx-menu", "bx-x")
        })
    })
});






