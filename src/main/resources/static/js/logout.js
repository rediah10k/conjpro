document.getElementById("cerrarSesion").addEventListener("click", async (e) => {
    e.preventDefault();

    await fetch(`/cerrarSesion?idUsuario=${sessionStorage.getItem("idUsuario")}&codigoAsamblea=${sessionStorage.getItem("codigoAsamblea")}`);

    
    sessionStorage.clear();

    const response = await fetch("/logout", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
    });

    if (response.status === 200) {
        window.location.href = "/";
    }
});