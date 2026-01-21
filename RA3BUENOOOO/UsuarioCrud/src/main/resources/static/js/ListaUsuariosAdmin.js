var datosMostrar = document.getElementById("datosMostrar")
var btnSalir = document.getElementById("btnSalir")
var volver = document.getElementById("volver")


btnSalir.addEventListener("click", ev => {
    window.location.href= 'killSession'
})

volver.addEventListener("click", ev => {
    window.location.href= '/control'
})


// Variable global para manejar el modal de Bootstrap
let editModal;
document.addEventListener('DOMContentLoaded', () => {
    editModal = new bootstrap.Modal(document.getElementById('editModal'));
});

// Solicitud original modificada
fetch('/admin/verUsuarios')
    .then(response => response.json())
    .then(data => {
        let tablaHTML = `
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Rol</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>`;

        data.forEach(usuario => {
            const fecha = usuario.fechaCreacion ? new Date(usuario.fechaCreacion).toLocaleDateString() : '-';
            const estadoBadge = usuario.estado ? '<span class="badge bg-success">Activo</span>' : '<span class="badge bg-danger">Inactivo</span>';

            // Añadimos botones con atributos 'data' para pasar los datos del usuario
            tablaHTML += `
                <tr>
                    <td>${usuario.idUsuario}</td>
                    <td>${usuario.username}</td>
                    <td>${usuario.nombreRol}</td>                       
                    <td>${fecha}</td>
                    <td class="text-center">${estadoBadge}</td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="prepararEdicion('${usuario.idUsuario}', '${usuario.username}', '${usuario.nombreRol}', ${usuario.estado})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="eliminarUsuario('${usuario.idUsuario}')">Eliminar</button>
                    </td>
                </tr>`;
        });

        tablaHTML += `</tbody></table>`;
        document.getElementById("datosMostrar").innerHTML = tablaHTML;
    });


// Configuración de los botones del Header
document.getElementById("headerSalir").addEventListener("click", () => {
    if(confirm("¿Deseas cerrar la sesión?")) {
        window.location.href = '/killSession';
    }
});

document.getElementById("headerVolver").addEventListener("click", () => {
    window.location.href = '/control';
});

/**
 * Función opcional para cambiar el título dinámicamente según la ventana
 * @param {string} titulo - El nombre de la sección actual
 */
function actualizarEncabezado(titulo, subtitulo) {
    document.getElementById("headerTitulo").innerText = titulo;
    document.getElementById("headerSubtitulo").innerText = subtitulo;
}

// Ejemplo de uso al cargar la página
actualizarEncabezado("Panel Administrativo", "Gestión total de la base de datos de usuarios");

// Función para cargar TODOS los datos en el modal
function prepararEdicion(id, username, rol, estado, fecha) {

    const estadoBadge = estado ? '<span class="badge bg-success">Activo</span>' : '<span class="badge bg-danger">Inactivo</span>';

    document.getElementById('editId').value = id;
    document.getElementById('editUsername').value = username;
    document.getElementById('editRol').value = rol;
    document.getElementById('editEstado').value = estadoBadge;


    editModal.show();
}

// Función para enviar la actualización al servidor
document.getElementById('editForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const updatedUser = {
        idUsuario: document.getElementById('editId').value,
        username: document.getElementById('editUsername').value,
        nombreRol: document.getElementById('editRol').value,
        estado: document.getElementById('editEstado').value === 'true'
        // No enviamos la fecha porque el Service la mantiene o es automática
    };

    fetch('/admin/actualizarUsuario', {
        method: 'POST', // O 'PUT' según tu controlador
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedUser)
    }).then(res => {
        if(res.ok) {
            alert("Usuario actualizado con éxito");
            location.reload();
        } else {
            alert("Error al actualizar");
        }
    });
});

// Función para eliminar
function eliminarUsuario(id) {
    if(confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
        fetch(`/admin/eliminarUsuario/${id}`, { method: 'DELETE' })
            .then(res => {
                if(res.ok) {
                    console.log("Este usuario se ha elimado")
                    location.reload()
                    btnVerUsuarios.click();
                }  });
    }
}

