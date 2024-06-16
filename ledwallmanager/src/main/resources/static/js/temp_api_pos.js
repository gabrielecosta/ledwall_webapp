function posizione(lat, long) 
{
    this.lat = lat
    this.long = long
    this.toString = function() {
        return this.lat + ',' + this.long
    }
    this.getLat = function() {
        return this.lat
    }
    this.getLong = function() {
        return this.long
    }
}

function timeStampData(year, month, day, hour, minute, second) {
    this.year =  year
    this.month = month
    this.day = day
    this.hour = hour
    this.minute = minute
    this.second = second
    this.getData = function() {
        return this.year + '-' + this.month + '-' + this.day
    }
    this.getTime = function() {
        return this.hour + ':' + this.minute + ':' + this.second
    }
    this.getDataTime = function() {
        return this.getData() + ' ' + this.getTime()
    }
}


// variabili globali

var current_data = 0;
var current_position = 0;
var api_data = [];
var json_data = [];

// Funzione per ottenere la data corrente e aggiornare l'elemento HTML
function getCurrentDate() 
{
    var currentDate = new Date();
    var day = currentDate.getDate();
    if (day < 10) {
        day = '0' + day
    }
    var month = currentDate.getMonth() + 1; // I mesi partono da 0, quindi aggiungiamo 1
    if (month < 10) {
        month = '0' + month
    }
    var year = currentDate.getFullYear();
    var hours = currentDate.getHours();
    var minutes = currentDate.getMinutes();
    if (minutes < 10) {
        minutes = '0' + minutes
    }
    var seconds = currentDate.getSeconds();
    if (seconds < 10) {
        seconds = '0' + seconds
    }
    // var formattedDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
    // console.log(formattedDate)
    // return formattedDate
    current_data = new timeStampData(year, month, day, hours, minutes, seconds)
    document.getElementById('current_data').innerHTML = current_data.getDataTime();
}

// Funzione per gestire la posizione ottenuta
function showPosition(position) {
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;
    // console.log("Latitude: " + latitude + ", Longitude: " + longitude);
    current_position = new posizione(latitude, longitude)
    console.log('Posizione corrente: ' + current_position.toString())
}
// Funzione per gestire eventuali errori nella geolocalizzazione
function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            alert("User denied the request for Geolocation.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("Location information is unavailable.");
            break;
        case error.TIMEOUT:
            alert("The request to get user location timed out.");
            break;
        case error.UNKNOWN_ERROR:
            alert("An unknown error occurred.");
            break;
    }
}


function caricaData() {
    setTimeout('getData()', 3000)
    setInterval('getData()', 1800000)
    setInterval('getCurrentDate()',1000)
}
function getData() {
    // chiamiamo la funzione all'inzio per ottenere la posizione geografica
    //getLocation()
    // adesso invoco la chiamata api dopo 2 secondo
    t = setTimeout('getWeather()', 2500)
    // getWeather()
}

function updateList() {
    console.log(json_data)
    console.log(api_data)
    let content = '<ul class="weather-list">'
    for (let i=0; i<api_data.length; i++) {
        imgTag = '<img src=\'https://openweathermap.org/img/wn/'+ json_data[i]['icon'] + '@2x.png' + '\'/>'
        desc = json_data[i]['main']
        content = content + '<li>' + api_data[i].toFixed(2) + ' °C, ' + desc + imgTag +' </li>'
    }
    content += '</ul>'
    //document.getElementById('lista_api').innerHTML = content
}


function getWeather() {
    api_key = 'e9468bc31c09d13c327e4129ae52c881'
    //lat = current_position.getLat().toFixed(4)
    //long = current_position.getLong().toFixed(4)
    lat = 38.0699
    long = 13.3202
    /*if (lat === undefined) {
        lat = '38.0699'
    }
    if (long === undefined) {
        long = '13.3202'
    }*/
    // https://api.openweathermap.org/data/3.0/onecall?lat=38.0699&lon=13.3202&appid=e9468bc31c09d13c327e4129ae52c881
    link = 'https://api.openweathermap.org/data/3.0/onecall?lat='+ lat + '&lon=' + long + '&appid='+ api_key
    // Fection data from open weather API
    fetch(link)
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore nella richiesta HTTP');
            }
            return response.json();
        })
        .then(data => {
            // Gestisci la risposta JSON qui
            weather = data['current']['weather'][0]
            desc = weather['main']
            imgTag = '<img src=\'https://openweathermap.org/img/wn/'+ weather['icon'] + '@2x.png' + '\'/>'
            temp_k = data['current']['temp']
            temp_c = temp_k - 273.15
            string_to_write = '<span class="weather_api">' + temp_c.toFixed(2) + '°C, ' + desc + ' ' + imgTag + '</span>'
            console.log('Effettuata query!')
            console.log(data)
            document.getElementById('tempo').innerHTML = string_to_write;
        })
        .catch(error => {
            console.error('Si è verificato un errore:', error);
        });
}