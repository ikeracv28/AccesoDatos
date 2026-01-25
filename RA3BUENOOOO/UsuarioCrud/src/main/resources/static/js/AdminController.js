var saludoUsuario = document.getElementById("saludoUsuario")
var datosMostrar = document.getElementById("datosMostrar")
var btnSalir = document.getElementById("btnSalir")
var btnVerUsuarios = document.getElementById("btnVerUsuarios")

// --- NUEVA VARIABLE PARA EL MODAL ---
let editModal;



btnSalir.addEventListener("click", ev => {
    window.location.href= 'killSession'
})

btnVerUsuarios.addEventListener("click", ev => {
    window.location.href= '/admin'
})

fetch('/admin/datosAdmin', {

})
    .then((response) => {
        if (response.ok) {
            return response.json();
        }else {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }
    })
    .then((data) => {
        // --- EFECTO DE BIENVENIDA (MANTENIDO) ---
        saludoUsuario.style.opacity = "0";
        saludoUsuario.innerHTML = `Bienvenido de nuevo, <span class="text-primary">${data.username}</span>`;

        setTimeout(() => {
            saludoUsuario.style.transition = "opacity 0.8s ease-in-out";
            saludoUsuario.style.opacity = "1";
        }, 100);

        // --- GENERACIÓN DE TABLA ESTILIZADA (MANTENIDO) ---
        const fecha = data.fechaCreacion ? new Date(data.fechaCreacion).toLocaleDateString() : '-';

        // Dentro de tu fetch en AdminController.js
        // ... dentro del fetch
        datosMostrar.innerHTML = `
    <div class="table-responsive"> <table class="table table-hover align-middle mb-0">
            <thead>
                <tr>
                    <th class="ps-3">ID</th>
                    <th>Username</th>
                    <th>Rol</th>
                    <th>Departamento</th>                   
                    <th>Fecha Creación</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="ps-3 fw-bold text-muted">${data.idUsuario}</td>
                    <td class="fw-bold">${data.username}</td>
                    <td><span class="badge bg-dark px-3 py-2 text-uppercase" style="border: 1px solid var(--main-col); color: var(--main-col);">${data.nombreRol}</span></td>
                    <td class="fw-bold">${data.nombreDepartamento}</td>
                    <td class="text-muted">${fecha}</td>
                </tr>
            </tbody>
        </table>
    </div>`;
    })
    .catch((error) => {
        console.error("Fallo al cargar datos:", error);
        datosMostrar.innerHTML = `<div class="alert alert-danger">Error al cargar la información del administrador.</div>`;
    });

// --- NUEVAS FUNCIONES PARA EL POP-UP Y ROLES (AÑADIDAS AL FINAL) ---

// ... (Tus variables y fetch de usuarios iniciales se mantienen igual)

document.addEventListener('DOMContentLoaded', () => {
    const modalElement = document.getElementById('editModal');
    if (modalElement) {
        editModal = new bootstrap.Modal(modalElement);
    }
    // Cargamos los roles reales de la base de datos al abrir la página
    cargarRolesEnSelect();

    // CORRECCIÓN: Aquí es donde cambias los textos que se ven en el Header
    actualizarEncabezado("PANEL ADMINISTRATIVO", "Panel de Administración > Lista de Usuarios Registrados");
});

// FUNCIÓN PARA RELLENAR EL SELECT DE ROLES
function cargarRolesEnSelect() {
    fetch('/admin/verRoles')
        .then(res => res.json())
        .then(roles => {
            const selectRol = document.getElementById('editRol');
            if (!selectRol) return;
            selectRol.innerHTML = "";
            roles.forEach(rol => {
                let option = document.createElement('option');
                option.value = rol.nombre;
                option.text = rol.nombre.toUpperCase();
                selectRol.appendChild(option);
            });
        }).catch(err => console.error("Error cargando roles:", err));
}

// FUNCIÓN PARA ABRIR EL POP-UP (Asegúrate de pasar los datos correctamente)
/*
function prepararEdicion(id, username, rol, estado) {
    document.getElementById('editId').value = id;
    document.getElementById('editUsername').value = username;

    // Al ser un select ahora, esto buscará la opción que coincida con el nombre del rol
    document.getElementById('editRol').value = rol;
    document.getElementById('editEstado').value = estado.toString();

    if (editModal) editModal.show();
}
*/


function actualizarEncabezado(titulo, subtitulo) {
    if(document.getElementById("headerTitulo")) {
        document.getElementById("headerTitulo").innerText = titulo;
    }
    if(document.getElementById("headerSubtitulo")) {
        document.getElementById("headerSubtitulo").innerText = subtitulo;
    }
}

// Función para abrir el modal de creación (Ahora ultra simple)
function abrirModalCrear() {
    const dialog = document.getElementById('createDialog');
    if (dialog) {
        dialog.showModal(); // Esto DEBE abrir el modal sí o sí
    } else {
        console.error("No se encuentra el elemento createDialog en el HTML");
    }
}

// Función para enviar los datos al servidor
function guardarNuevoUsuario(e) {
    e.preventDefault();

    const payload = {
        username: document.getElementById('createUsername').value,
        password: document.getElementById('createPassword').value,
        nombreRol: document.getElementById('createRol').value, // Lo que escribas en el input
        nombreDepartamento: document.getElementById('createDept').value, // Lo que escribas en el input
        estado: true
    };

    fetch('/admin/crearUsuario', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
        .then(res => {
            if (res.ok) {
                location.reload(); // Recargar para ver el nuevo usuario en la tabla
            } else {
                alert("Error al crear usuario. Revisa la consola (F12)");
            }
        })
        .catch(error => console.error("Error en la petición:", error));
}

// Asegúrate de registrar el evento al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('createForm');
    if (form) {
        form.addEventListener('submit', guardarNuevoUsuario);
    }
});



