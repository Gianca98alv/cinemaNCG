var fila = document.getElementById("fila")
var colmna = document.getElementById("columna")
var enviarRegSalas = document.getElementById("enviarRegSalas")
enviarRegSalas.onclick = function() {
    
    var data = {
        'fila': fila.value,
        'columna': columna.value
    }
    
    fetch('http://localhost:8085/cinemaNCG/rooms', {
    method: 'POST', // or 'PUT'
    body: JSON.stringify(data), // data can be string or {object}!
        headers:{
            'Content-Type': 'application/json'
        }
    }).then(res => res.json())
    .catch(error => console.error('Error:', error))
    .then(response => {console.log('Success:', response)
        fila.value = ''
        columna.value = ''
        obtenerSalas()
    });
}

var listaSalas = document.getElementById("listaSalas")
function obtenerSalas() {
    fetch('http://localhost:8085/cinemaNCG/rooms')
    .then(response => response.json())
    .then(data => {console.log(data)
        let lista = ''
        data.forEach(sala => {
            lista += '<tr>' +
                '<td>' + sala.idSala + '</td>' +
                '<td>' + sala.fila + '</td>' +
                '<td>' + sala.columna + '</td>' +
                '<td>' + sala.fila*sala.columna + '</td>' +
            '</tr>'
        })
        listaSalas.innerHTML = lista
    });
}
obtenerSalas()