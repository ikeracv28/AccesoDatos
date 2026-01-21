
var saludoUsuario = document.getElementById("saludoUsuario")
var datosMostrar = document.getElementById("datosMostrar")
var btnSalir = document.getElementById("btnSalir")

btnSalir.addEventListener("click", ev => {
    window.location.href= 'killSession'
})



fetch('/user/datos', {

})
    .then((response) => {
        if (response.ok) {
            return response.json();
        }else {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
    })
    .then((data) => {


        // 1. Formatear la fecha
        const fecha = data.fechaCreacion ? new Date(data.fechaCreacion).toLocaleDateString() : '-';

        // Crear la tabla usando innerHTML con clases de Bootstrap
        var tablaHTML = `
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>idUsuario</th>
                        <th>Username</th>
                        <th>NombreRol</th>
                        <th>FechaCreacion</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${data.idUsuario}</td>
                        <td>${data.username}</td>
                        <td>${data.nombreRol}</td>
                        <td>${fecha}</td>
                    </tr>
                </tbody>
            </table>
        `;

        saludoUsuario.innerHTML = `Bienvenido de nuevo ${data.username}`


        // Limpiar el div y añadir la nueva tabla
        datosMostrar.innerHTML = tablaHTML; // Insertar la tabla en el div
    })
    .catch((error) => {
        console.error("Fallo en login:", error);
        // Aquí podrías agregar el código para mostrar un mensaje de error si lo deseas


        /*toast.textContent = error;
        // Mostramos el Toast
        toast.style.display = "flex";
        // Opcional: Podrías poner el mensaje del error en el toast
        // toast.innerText = "Usuario o contraseña incorrectos";

        setTimeout(() => {
            toast.style.display = "none";
        }, 3000);
        */

    });