var fila = document.getElementById("fila");
var columna = document.getElementById("columna");
var enviarRegSalas = document.getElementById("enviarRegSalas");
enviarRegSalas.onclick = function() {
    var data = {
        'fila': fila.value,
        'columna': columna.value
    };
    
    fetch('http://localhost:8080/cinemaNCG/rooms', {
        method: 'POST', // or 'PUT'
        body: JSON.stringify(data), // data can be string or {object}!
        headers:{
            'Content-Type': 'application/json'
        }
    }).then(res => res.json())
    .catch(error => console.error('Error:', error))
    .then(response => {console.log('Success:', response);
        fila.value = '';
        columna.value = '';
        obtenerSalas();
    });
};

var listaSalas = document.getElementById("listaSalas");
var selSala = document.getElementById("selSala");
function obtenerSalas() {
    fetch('http://localhost:8080/cinemaNCG/rooms')
    .then(response => response.json())
    .then(data => {console.log(data);
        let lista = '';
        data.forEach(sala => {
            lista += '<tr>' +
                '<td>' + sala.idSala + '</td>' +
                '<td>' + sala.fila + '</td>' +
                '<td>' + sala.columna + '</td>' +
                '<td>' + sala.fila*sala.columna + '</td>' +
            '</tr>';
        });
        listaSalas.innerHTML = lista;
        
        let sel = '<option value=0>Seleccione una sala</option>';
        
        data.forEach(sala => {
            sel += '<option value="' + sala.idSala + '">Sala #' + sala.idSala + '</option>';
        });
        selSala.innerHTML = sel;
    });
}
obtenerSalas();

var idPelicula = document.getElementById("idPelicula");
var duracion = document.getElementById("duracion");
var clasificacion = document.getElementById("clasificacion");
var estreno = document.getElementById("estreno");
var enviarRegPelis = document.getElementById("enviarRegPelis");
enviarRegPelis.onclick = function() {
    var poster = document.querySelector('#poster').files[0];
    getBase64(poster).then(
        posterB64 => {
            var data = {
                'idPelicula': idPelicula.value,
                'poster': posterB64,
                'duracion': duracion.value + ":00",
                'clasificacion': clasificacion.value,
                'estreno': estreno.checked?1:0
            };

            fetch('http://localhost:8080/cinemaNCG/films', {
                method: 'POST',
                body: JSON.stringify(data),
                headers:{
                    'Content-Type': 'application/json'
                }
            }).then(res => res.json())
            .catch(error => console.error('Error:', error))
            .then(response => {console.log('Success:', response);
                idPelicula.value = '';
                document.getElementById("poster").value = '';
                duracion.value = '';
                clasificacion.value = '';
                estreno.checked = false;
                obtenerPelis();
            });
        }
    );
};

var listaPelis = document.getElementById("listaPelis");
var selPeli = document.getElementById("selPeli");
function obtenerPelis() {
    fetch('http://localhost:8080/cinemaNCG/films')
    .then(response => response.json())
    .then(data => {console.log(data);
        let lista = '';
        data.forEach(pelicula => {
            lista += '<tr>' +
                '<td>' + pelicula.idPelicula + '</td>' +
                '<td>' + pelicula.duracion.split(":00 AM")[0] + " H" + '</td>' +
                '<td>' + pelicula.clasificacion + '</td>' +
                '<td>' + (pelicula.estreno?"Si":"No") + '</td>' +
                '<td>' + '<img src=\"' + pelicula.poster + '\"alt=\"Poster\" width=\"150\" height=\"150\"/></td>' +
            '</tr>';
        });
        listaPelis.innerHTML = lista;
        
        let sel = '<option value=0>Seleccione una pelicula</option>';
        data.forEach(pelicula => {
            sel += '<option value="' + pelicula.idPelicula + '">' + pelicula.idPelicula + '</option>';
        });
        selPeli.innerHTML = sel;
    });
}
obtenerPelis();

function getBase64(poster) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(poster);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

var precio = document.getElementById("precio");
var fechaInicio = document.getElementById("fechaInicio");
var enviarRegFuncion  = document.getElementById("enviarRegFuncion");
enviarRegFuncion.onclick = function() {
    var data = {
        'pelicula': {'idPelicula': selPeli.value},
        'sala': {'idSala': selSala.value},
        'precio': precio.value,
        'fechaInicio': fechaInicio.value.replace('T',' ') + ":00" //'2021-06-05 04:45:00'
    };
    
    console.log(fechaInicio.value);
    console.log(data.fechaInicio);
    
    fetch('http://localhost:8080/cinemaNCG/shows', {
        method: 'POST',
        body: JSON.stringify(data),
        headers:{
            'Content-Type': 'application/json'
        }
    }).then(res => res.json())
    .catch(error => console.error('Error:', error))
    .then(response => {console.log('Success:', response);
        selSala.selectedIndex = 0;
        selPeli.selectedIndex = 0;
        precio.value = '';
        fechaInicio.value = '';
        obtenerFunciones();
    });
};

var listaFunciones = document.getElementById("listaFunciones");
function obtenerFunciones(){
    fetch('http://localhost:8080/cinemaNCG/shows')
    .then(response => response.json())
    .then(data => {console.log(data);
        let lista = '';
        data.forEach(func => {
            lista += '<tr>' +
                '<td>' + func.sala.idSala + '</td>' +
                '<td>' + func.pelicula.idPelicula + '</td>' +
                '<td>' + func.precio + '</td>' +
                '<td>' + func.fechaInicio + '</td>' +
                '<td>' + func.pelicula.duracion.split(":00 AM")[0] + " H" + '</td>' +
            '</tr>';
        });
        listaFunciones.innerHTML = lista;
    });
}
obtenerFunciones();

var logout_btn = document.getElementById("logout-btn");
logout_btn.onclick = function () {
    sessionStorage.removeItem('user');
    window.location = "http://localhost:8080/cinemaNCG/";
};