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
login_btn.onclick = function() {
  login_modal.style.display = "block"
}

login_span.onclick = function() {
  login_modal.style.display = "none"
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == login_modal) {
    login_modal.style.display = "none"
  }
}

register_btn.onclick = function() {
  register_modal.style.display = "block"
}

// When the user clicks on <span> (x), close the modal
register_span.onclick = function() {
  register_modal.style.display = "none"
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == register_modal) {
    register_modal.style.display = "none"
  }
}

var login_submit = document.getElementById("login-submit")
var idUsuario = document.getElementById("login-cedula")
var contrasena = document.getElementById("login-contrasena")
login_submit.onclick = function() {
    console.log(idUsuario.value)
    console.log(contrasena.value)
    fetch('http://localhost:8080/cinemaNCG/users?idUsuario='+ idUsuario.value +'&contrasena=' + contrasena.value)
  .then(response => response.json())
  .then(data => console.log(data));
}
