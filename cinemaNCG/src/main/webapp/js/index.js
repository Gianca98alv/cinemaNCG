/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Get login
var login_modal = document.getElementById("login-modal")
var login_btn = document.getElementById("login-btn")

// Get register
var register_modal = document.getElementById("register-modal")
var register_btn = document.getElementById("register-btn")

// Get the <span> element that closes the modal
var login_span = document.getElementsByClassName("login-close")[0]
var register_span = document.getElementsByClassName("register-close")[0]

// When the user clicks on the button, open the modal
login_btn.onclick = function () {
    login_modal.style.display = "block"
}

login_span.onclick = function () {
    login_modal.style.display = "none"
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == login_modal) {
        login_modal.style.display = "none"
    }
}

register_btn.onclick = function () {
    register_modal.style.display = "block"
}

// When the user clicks on <span> (x), close the modal
register_span.onclick = function () {
    register_modal.style.display = "none"
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == register_modal) {
        register_modal.style.display = "none"
    }
}

var login_submit = document.getElementById("login-submit")
var idUsuario = document.getElementById("login-cedula")
var contrasena = document.getElementById("login-contrasena")
login_submit.onclick = function () {
    fetch('http://localhost:8080/cinemaNCG/users?idUsuario=' + idUsuario.value + '&contrasena=' + contrasena.value)
            .then(response => response.json())
            .then(data => {
                console.log(data)
                sessionStorage.setItem('user', JSON.stringify(data));
                login_modal.style.display = "none"
                loadUserOptions();
            });
}

function loadUserOptions() {
    if (sessionStorage.getItem('user')) {
        document.getElementById("login-btn").style.display = "none"
        document.getElementById("register-btn").style.display = "none"
        document.getElementById("logout-btn").style.display = "inline"
        let user = sessionStorage.getItem('user')
        console.log(JSON.parse(sessionStorage.user))
        if (JSON.parse(sessionStorage.user).tipo != 1) {
            document.getElementById("admin-btn").style.display = "none"
            document.getElementById("info-btn").style.display = "inline"
        } else {
            document.getElementById("info-btn").style.display = "none"
            document.getElementById("admin-btn").style.display = "inline"
        }
    } else {
        document.getElementById("login-btn").style.display = "inline"
        document.getElementById("register-btn").style.display = "inline"
        document.getElementById("logout-btn").style.display = "none"
        document.getElementById("admin-btn").style.display = "none"
        document.getElementById("info-btn").style.display = "none"

    }
}

loadUserOptions();

var register_submit = document.getElementById("register-submit")
var register_idUsuario = document.getElementById("register-cedula")
var register_nombre = document.getElementById("register-nombre")
var register_contrasena = document.getElementById("register-contrasena")
register_submit.onclick = function () {
    var url = 'http://localhost:8080/cinemaNCG/users';
    var data = {
        "idUsuario": register_idUsuario.value,
        "contrasena": register_contrasena.value,
        "nombre": register_nombre.value,
        "tipo": 1
    };

    fetch(url, {
        method: 'POST', // or 'PUT'
        body: JSON.stringify(data), // data can be `string` or {object}!
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(res => res.json())
            .catch(error => console.error('Error:', error))
            .then(response => {
                console.log('Success:', response)
                register_modal.style.display = "none"
            });
}

var logout_btn = document.getElementById("logout-btn")
logout_btn.onclick = function () {
    sessionStorage.removeItem('user')
    loadUserOptions();
}

var films_list = document.getElementById("films")
function loadFilms() {
    let filmName = document.getElementById("idPelicula").value
    fetch('http://localhost:8080/cinemaNCG/films?nombrePelicula=' + filmName)
            .then(response => response.json())
            .then(data => {
                let list = "<ul>"
                let x = 1
                console.log(data[0].poster)
                data.forEach(film => {
                    list += "<li>"
                    list += "<h3>"+ film.idPelicula +"</h3>"
                    list += "<img src=\" data:image/jpeg;base64,"+ film.poster +"\" alt=\"Poster\" width=\"150\" height=\"150\">"
                    list += "</li>"
                    if (x %4 == 0) {
                        if (data.length == x) {
                            list += "</ul>"
                        } else {
                            list += "</ul><ul>"
                        }
                    }
                    x++
                })
                films_list.innerHTML = list
            });
}

loadFilms();

var buscar_btn = document.getElementById("buscar-btn")
buscar_btn.onclick = function() {
    loadFilms();
}
