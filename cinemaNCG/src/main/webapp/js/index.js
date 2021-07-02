
var login_modal = document.getElementById("login-modal")
var login_btn = document.getElementById("login-btn")

var register_modal = document.getElementById("register-modal")
var register_btn = document.getElementById("register-btn")

var login_span = document.getElementsByClassName("login-close")[0]
var register_span = document.getElementsByClassName("register-close")[0]
var record_span = document.getElementsByClassName("record-close")[0]

var record_modal = document.getElementById("record-modal")
var record_btn = document.getElementById("record-btn")

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
            document.getElementById("record-btn").style.display = "inline"
        } else {
            document.getElementById("record-btn").style.display = "none"
            document.getElementById("admin-btn").style.display = "inline"
        }
    } else {
        document.getElementById("login-btn").style.display = "inline"
        document.getElementById("register-btn").style.display = "inline"
        document.getElementById("logout-btn").style.display = "none"
        document.getElementById("admin-btn").style.display = "none"
        document.getElementById("record-btn").style.display = "none"

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
                data.forEach(film => {
                    list += "<li>"
                    list += "<h3 class=\"nameMovie\">"+ film.idPelicula +"</h3>"
                    list += "<div class=\"card\">";
                    list +=     "<div class=\"image\">";
                    list +=         "<img src=\"" + film.poster + "\"/>";
                    list +=     "</div>";
                    list +=     "<div class=\"details\">";
                    list +=         "<div class=\"center\">";
                    list +=             "<h3>Funciones</h3>";
                    list +=             "<ul>";
                    film.funcionList.forEach(func => {
                    list +=                 "<li>";
                    list +=                     "<div>"+ func.fechaInicio + " / S" + func.sala.idSala +"</div>";
                    list +=                     "<button onclick=\"escogerAsientos(" + func.idFuncion + ")\">Elegir</button>";
                    list +=                 "</li>";
                    });
                    list +=             "</ul>";
                    list +=         "</div>";
                    list +=     "</div>";
                    list += "</div>";
                    
                    list += "</li>";
                    
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

var buscar_btn = document.getElementById("buscar-btn");
buscar_btn.onclick = function() {
    loadFilms();
};

var escAsientosMod = document.getElementById("escAsientosMod");
var purchase_modal = document.getElementById("purchase-modal");
var purchase_span = document.getElementsByClassName("purchase-span")[0];

purchase_span.onclick = function () {
    purchase_modal.style.display = "none";
};

var seatMatrix = [];
var selectedSeats = [];
var letters = "ABCDEFGHIJKLMNOPQRSTVXYZ";

function escogerAsientos(idFuncion){
    fetch('http://localhost:8080/cinemaNCG/shows?idFuncion=' + idFuncion)
    .then(response => response.json())
    .then(show => {
        
        fetch('http://localhost:8080/cinemaNCG/tickets?idFuncion=' + idFuncion)
        .then(response => response.json())
        .then(tickets => {
        
            let dimen = {rows: show.sala.fila, columns: show.sala.columna};
            seatMatrix = [];
            for(let i = 0; i < dimen.rows; i++){
                seatMatrix.push([]);
                for(let j = 0; j < dimen.columns; j++){
                    seatMatrix[i].push(1);
                }
            }
            tickets.forEach(ticket => {
                seatMatrix[ticket.fila][ticket.columna] = 3;
            });

            let html = "";
            html += "<tr>";
            html += "<td></td>";
            for(let i = 0; i < dimen.columns; i++){
                html += "<td><h4>" + (i+1) +"</h4></td>";
            }
            html += "</tr>";
            let seatNumber = 1;
            for(let i = 0; i < dimen.rows; i++){
                html += "<tr><td> <h4>"+ letters[i] +"</h4> </td>";
                for(let j = 0; j < dimen.columns; j++){
                    let stateClass = seatMatrix[i][j] != 3? "seat-not-select" : "seat-reserved";
                    html += "<td>";
                    html += "<div id=\"seat-"+seatNumber+"\" class=\"seat " + stateClass + "\" onclick=\"selectSeat(" + seatNumber +", "+ show.idFuncion + ", " +  +i+", "+j+")\"></div>";
                    html += "</td>";
                    seatNumber++;
                }
                html += "</tr>";
            }
            
            let valueCed = "";
            let disableCed = "";
            let valueNom = "";
            let disableNom = "";
            if (sessionStorage.getItem('user')) {
                valueCed = JSON.parse(sessionStorage.user).idUsuario;
                valueNom = JSON.parse(sessionStorage.user).nombre;
                disableCed = "disabled";
                disableNom = "disabled";
            }
            html += "<tr>";
            html += "<td><label>Nombre: </label></td>";
            html += "<td colspan=10><input id=\"purcNom\" value=\"" + valueNom + "\" type=\"text\" placeholder=\"Ingrese su nombre\"" + disableNom + "></td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td><label>Cédula: </label></td>";
            html += "<td colspan=10><input id=\"purcCed\" value=\"" + valueCed + "\" type=\"text\" placeholder=\"Ingrese su cédula\"" + disableCed + "></td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td><label>Tarjeta: </label></td>";
            html += "<td colspan=10><input id=\"purcTar\" type=\"text\" placeholder=\"Ingrese su número de tarjeta\"></td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td colspan=10><button onclick=\"efectuarPago(" + show.precio + ")\" class=\"modal-btn\" type=\"button\">Comprar</button></td>"
            html += "</tr>";
            
            escAsientosMod.innerHTML = html;
            purchase_modal.style.display = "block";
        });
    });
}

function selectSeat(seatNumber, idFuncion, row, column){
    let seat = document.getElementById("seat-" +seatNumber);
    let state = seatMatrix[row][column];
    if(state == 1){
        seat.classList = "seat seat-select";
        seatMatrix[row][column] = 2
        selectedSeats.push({"funcion": {"idFuncion": idFuncion}, "fila": row, "columna": column});
    }else if(state == 2){
        seat.classList = "seat seat-not-select";
        seatMatrix[row][column] = 1;
        selectedSeats = selectedSeats.filter(seat =>
            seat.fila != row || seat.columna != column
        );
    }else{
        alert("Asiento reservado por otra persona");
    }
}

function efectuarPago(price){
    let purcNom = document.getElementById("purcNom").value;
    let purcCed = document.getElementById("purcCed").value;
    let purcTar = document.getElementById("purcTar").value;
    let user = null;
    if(sessionStorage.getItem('user')){user = {"idUsuario": purcCed};}
    if(purcNom != "" && purcCed != "" && purcTar != "" && selectedSeats.length > 0){
        var url = 'http://localhost:8080/cinemaNCG/invoices';
        var data = {
            "usuario": user,
            "cedula": purcCed,
            "nombre": purcNom,
            "numeroTarjeta": purcTar,
            "total": selectedSeats.length * price,
            "tiqueteList": selectedSeats
        };

        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => res.json())
            .catch(error => console.error('Error:', error))
            .then(response => {
                console.log('Success:', response)
                purchase_modal.style.display = "none"
                    selectedSeats = [];
                alert("Compra efectuada correctamente. Monto pagado: " + data.total);
        });
    }else{
        alert("Por favor seleccione al menos un asiento y complete los espacio requeridos.");
    }
}

var record_list = document.getElementById("record-list");

record_btn.onclick = function() {
    let user = JSON.parse(sessionStorage.user)
    fetch('http://localhost:8080/cinemaNCG/invoices?idUsuario=' + user.idUsuario)
            .then(response => response.json())
            .then(invoicesList => {
                let tableList = '<tr><th> N° Factura </th><th> Pelicula </th> <th> Fecha </th><th> Total </th></tr>'
                console.log(invoicesList)
                invoicesList.forEach(invoice => {
                    tableList += '<tr>'
                    tableList += '<td style = "margin: 10px;">'+ invoice.idFactura +'</td>'
                    tableList += '<td style = "margin: 10px;">'+ invoice.tiqueteList[0].funcion.pelicula.idPelicula +'</td>'
                    tableList += '<td style = "margin: 10px;">'+ invoice.tiqueteList[0].funcion.fechaInicio +'</td>'
                    tableList += '<td style = "margin: 10px;">'+ invoice.total +'</td>'
                    tableList += '</tr>'
                })
                record_list.innerHTML = tableList
                record_modal.style.display = "block"
            });
    
    
}

record_span.onclick = function(){
    record_modal.style.display = "none"
}

window.onclick = function (event) {
    if (event.target == record_modal) {
        record_modal.style.display = "none"
    }
}
