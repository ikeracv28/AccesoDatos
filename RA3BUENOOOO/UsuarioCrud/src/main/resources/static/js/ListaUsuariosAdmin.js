// --- Selectores de navegación ---
const btnSalir = document.getElementById("btnSalir");
const volver = document.getElementById("volver");

if (btnSalir) {
    btnSalir.addEventListener("click", () => window.location.href = 'killSession');
}
if (volver) {
    volver.addEventListener("click", () => window.location.href = '/control');
}

// --- Lógica del Modal y Usuarios ---
let editModal;

document.addEventListener('DOMContentLoaded', () => {
    // Inicializar el modal de Bootstrap
    const modalElement = document.getElementById('editModal');
    if (modalElement) {
        editModal = new bootstrap.Modal(modalElement);
    }

    // Escuchar el envío del formulario
    const editForm = document.getElementById('editForm');
    if (editForm) {
        editForm.addEventListener('submit', enviarActualizacion);
    }

    // Cargar los datos iniciales de la tabla
    cargarUsuarios();
});

// Función para obtener y renderizar usuarios
function cargarUsuarios() {
    fetch('/admin/verUsuarios')
        .then(response => response.json())
        .then(data => {
            // Agregamos un div contenedor para el borde redondeado
            // ... dentro de cargarUsuarios
            let tablaHTML = `
            <div class="table-container">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-dark">
                            <tr>
                                <th class="ps-3">ID</th>
                                <th>Username</th>
                                <th>Rol</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white">`;

            data.forEach(usuario => {
                const fecha = usuario.fechaCreacion ? new Date(usuario.fechaCreacion).toLocaleDateString() : '-';
                const estadoBadge = usuario.estado ?
                    '<span class="badge bg-success">Activo</span>' :
                    '<span class="badge bg-danger">Inactivo</span>';

                // Usamos JSON.stringify para pasar el objeto de forma segura en el onclick si fuera necesario, 
                // pero aquí pasamos parámetros simples.
                tablaHTML += `
                    <tr>
                        <td class="ps-3 fw-bold text-muted">${usuario.idUsuario}</td>
                        <td>${usuario.username}</td>
                        <td><span class="badge bg-info text-dark">${usuario.nombreRol}</span></td>                       
                        <td>${fecha}</td>
                        <td>${estadoBadge}</td>
                        <td class="text-center">
                            <div class="btn-group">
                                <button class="btn btn-sm btn-warning fw-bold" 
                                    onclick="prepararEdicion('${usuario.idUsuario}', '${usuario.username}', '${usuario.nombreRol}', ${usuario.estado})">
                                    Editar
                                </button>
                                <button class="btn btn-sm btn-danger fw-bold" 
                                    onclick="eliminarUsuario('${usuario.idUsuario}')">
                                    Eliminar
                                </button>
                            </div>
                        </td>
                    </tr>`;
            });

            tablaHTML += `</tbody></table></div>`;
            document.getElementById("datosMostrar").innerHTML = tablaHTML;
        })
        .catch(err => console.error("Error cargando usuarios:", err));
}

// Función global para llenar el modal
window.prepararEdicion = function(id, username, rol, estado) {
    // Llenar campos
    document.getElementById('editId').value = id;
    document.getElementById('editUsername').value = username;
    document.getElementById('editRol').value = rol;

    // El select de estado necesita un string "true" o "false"
    document.getElementById('editEstado').value = String(estado);

    // Mostrar modal
    if (editModal) {
        editModal.show();
    }
};

// Función para enviar la actualización al servidor
function enviarActualizacion(e) {
    e.preventDefault();

    const updatedUser = {
        idUsuario: document.getElementById('editId').value,
        username: document.getElementById('editUsername').value,
        nombreRol: document.getElementById('editRol').value,
        estado: document.getElementById('editEstado').value === 'true' // Convertir string a booleano
    };

    fetch('/admin/actualizarUsuario', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedUser)
    })
        .then(res => {
            if(res.ok) {
                alert("Usuario actualizado con éxito");
                editModal.hide();
                location.reload();
            } else {
                alert("Error al actualizar");
            }
        })
        .catch(error => {
            console.error('Error en fetch:', error);
            alert("Error crítico al conectar con el servidor");
        });
}

// Función para eliminar
window.eliminarUsuario = function(id) {
    if(confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
        fetch(`/admin/eliminarUsuario/${id}`, { method: 'DELETE' })
            .then(res => {
                if(res.ok) {
                    alert("Usuario eliminado");
                    location.reload();
                } else {
                    alert("No se pudo eliminar el usuario");
                }
            })
            .catch(err => console.error("Error al eliminar:", err));
    }
};

// Función de encabezado
function actualizarEncabezado(titulo, subtitulo) {
    if(document.getElementById("headerTitulo")) document.getElementById("headerTitulo").innerText = titulo;
    if(document.getElementById("headerSubtitulo")) document.getElementById("headerSubtitulo").innerText = subtitulo;
}

actualizarEncabezado("Panel Administrativo", "Gestión total de la base de datos de usuarios");