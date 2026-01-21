var datosMostrar = document.getElementById("datosMostrar")
var btnSalir = document.getElementById("btnSalir")
var volver = document.getElementById("volver")


btnSalir.addEventListener("click", ev => {
    window.location.href= 'killSession'
})

volver.addEventListener("click", ev => {
    window.location.href= '/control'
})


// Solicitud para obtener datos de administrador
fetch('/admin/verUsuarios', {})
    .then((response) => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
    })
    .then((data) => {

        // Crear la tabla con los datos
        var tablaHTML = `
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>idUsuario</th>
                        <th>Username</th>
                        <th>Rol</th>
                        <th>FechaCreacion</th>
                        <th>Estado</th>
                        
                        
                        
                    </tr>
                </thead>
                <tbody>
        `;

        // Iterar sobre los datos de usuarios y añadir filas a la tabla
        data.forEach(usuario => {
            // 1. Formatear la fecha
            const fecha = usuario.fechaCreacion ? new Date(usuario.fechaCreacion).toLocaleDateString() : '-';

            const estadoBadge = usuario.estado
                ? '<span class="badge bg-success">Activo</span>'
                : '<span class="badge bg-danger">Inactivo</span>';

            tablaHTML += `
                <tr>
                    <td>${usuario.idUsuario}</td>
                    <td>${usuario.username}</td>
                    <td>${usuario.nombreRol}</td>                       
                    <td>${fecha}</td>
                    <td class="text-center">${estadoBadge}</td>
                    
                    
                </tr>
            `;
        });

        // Cerrar las etiquetas de la tabla
        tablaHTML += `
                </tbody>
            </table>
        `;

        // Limpiar el div y añadir la nueva tabla
        datosMostrar.innerHTML = tablaHTML; // Insertar la tabla en el div
        console.log(data)
    })
    .catch((error) => {
        console.error("Fallo en login:", error);
        // Aquí podrías agregar el código para mostrar un mensaje de error si lo deseas
    });

