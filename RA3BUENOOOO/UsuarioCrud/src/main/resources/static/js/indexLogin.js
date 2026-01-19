let formularioInicioSesion = document.getElementById("formularioInicioSesion");
let usernameInicioSesion = document.getElementById("usernameInicioSesion");
let passwordInicioSesion = document.getElementById("passwordInicioSesion");
let btnEnviarInicioSesion = document.getElementById("btnEnviarInicioSesion");

formularioInicioSesion.addEventListener('submit' , (e) => {
    e.preventDefault();

    var usernameIntroducido = usernameInicioSesion.value.trim();
    var passwordIntroducido = passwordInicioSesion.value.trim();

    fetch('/login', {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({username, password})
    })
        .then(())





}


