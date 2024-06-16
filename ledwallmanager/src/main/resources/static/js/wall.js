/*
    LED Wall Management Script
*/

// Funzione per caricare i file XML
function caricaXML(percorso) 
{
    console.log(percorso)
    
    // Carica il file del palinsesto come oggetto XMLHttpRequest()
    xhttp = new XMLHttpRequest();
    xhttp.open("GET", percorso, false);
    xhttp.send();
    xmlDoc = xhttp.responseXML;
    
    return xmlDoc;

}

// Funzione per caricare il palinsesto
function caricaPalinsesto(file_palinsesto)
{
    
    // Carica il file del palinsesto come oggetto XMLHttpRequest()
    console.log("Caricamento del palinsesto...")

    // Per caricare un altro palinsesto è sufficiente modificare il nome del file .xml
    let xmlDoc = caricaXML(file_palinsesto);
    
    console.log("Palinsesto caricato con successo.")
    
    // Ottengo l'elemento radice
    let palinsesto = xmlDoc.documentElement;

    // Ottengo l'id del palinsesto
    let id_palinsesto = palinsesto.getAttribute("id")

    // Ottengo i cartelloni
    let cartelloni = palinsesto.getElementsByTagName("cartellone");
    console.log("Sono stati caricati " + cartelloni.length + " cartelloni.")

    // Cicla i cartelloni
    aggiornaWall(cartelloni, 0, id_palinsesto)

}

// Funzione per caricare i cartelloni
function aggiornaWall(cartelloni, i = 0, id_palinsesto) 
{

    console.log("Visualizzazione cartellone " + (i + 1) + " di " + cartelloni.length + "...")

    // Ottieni il cartellone
    let cartellone = cartelloni[i]

    // Ottengo l'identificativo del cartellone
    let id_cartellone = cartellone.getAttribute("id");

    // Ottengo il percorso del file HTML del cartellone
    let percorso = cartellone.getElementsByTagName("percorso")[0].textContent;

    // Ottengo la durata del cartellone
    let durata = cartellone.getElementsByTagName("durata")[0].textContent;

    // Mostro il cartellone
    document.getElementById("wall").src = percorso;

    // Aggiorna il contatore dei cartelloni
    i++;

    // Verifica se è l'ultimo cartellone
    if (i >= cartelloni.length) {
        console.log("Tutti i cartelloni sono stati visualizzati. Reset del sistema in corso...")
        i = 0;
        setTimeout(() => aggiornaWall(cartelloni, i, id_palinsesto), durata * 1000);
    }
    else {
        console.log("In attesa del cartellone successivo...")
        setTimeout(() => aggiornaWall(cartelloni, i, id_palinsesto), durata * 1000);
    }
    // Invia i dati alla servlet
    inviaDati(id_palinsesto, id_cartellone, durata);

}
//funzione per inviare i dati alla servlet
function inviaDati(id_palinsesto, id_cartellone, durata)
{
    // Recupero dell'ID dell'impianto
    let id_impianto = document.getElementsByTagName("html")[0].id

    // Creazione dell'oggetto XMLHttpRequest per effettuare la richiesta HTTP
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8000/assignment3_war_exploded/inviasegnalazione", true);

    //invio dei dati in formato json
    xhttp.setRequestHeader("Content-Type", "application/json");
    //funzione callback
    xhttp.onreadystatechange = function() 
    {
        // 4 per richiesta completata, 200 risposta "ok" dal server
        if (this.readyState == 4 && this.status == 200) {
            console.log("Dati inviati con successo alla servlet.");
        }
    }
    // Crea un oggetto JSON con i dati da inviare
    let dati = 
    {
        id_impianto: id_impianto,
        id_palinsesto: id_palinsesto,
        id_cartellone: id_cartellone,
        durata: durata
    };

    // Converte l'oggetto JSON in una stringa
    let datiJSON = JSON.stringify(dati);
    xhttp.send(datiJSON);
}
// Esegui lo script
//caricaPalinsesto();