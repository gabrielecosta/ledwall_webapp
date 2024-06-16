<%@ page import="com.intellicrafters.assignment3.Impianto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.intellicrafters.assignment3.Segnalazione" %><%--
  Created by IntelliJ IDEA.
  User: gabriele
  Date: 5/20/24
  Time: 12:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sistema di monitoraggio impianti</title>
    <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css"/>
    <script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
    <style>
        body {
            font-family: 'Segoe UI Variable Display', 'SF Pro', Ubuntu, Cantarell, Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f8f9fa;
        }
        h1 {
            color: #343a40;
            margin-bottom: 20px;
            border-bottom: 2px solid #dee2e6;
            padding-bottom: 10px;
        }
        table {
            width: 100%;
            margin-bottom: 1rem;
            color: #212529;
            border-collapse: collapse;
            text-align: left;
        }
        table th,
        table td {
            padding: .75rem;
            vertical-align: top;
            border-top: 1px solid #dee2e6;
        }
        table thead th {
            vertical-align: bottom;
            border-bottom: 2px solid #dee2e6;
        }
        table tbody {
            border-top: 2px solid #dee2e6;
        }
        table {
            background-color: #fff;
        }
        th {
            background-color: #343a40;
            color: #fff;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        .map-container {
            margin-top: 20px;
        }
        .legend {
            list-style-type: none;
            padding: 0;
            margin: 20px 0;
        }
        .legend li {
            display: inline-block;
            margin-right: 20px;
        }
        .legend img {
            vertical-align: middle;
            margin-right: 5px;
        }
        .container {
            max-width: 1500px;
            margin-left: auto;
            margin-right: auto;
        }
        ul {
            padding: 0;
        }
    </style>
</head>
<body>
<div class="container">
    <jsp:useBean id="impianti" class="com.intellicrafters.assignment3.beans.ImpiantiBean" scope="request"/>
    <!-- ${impianti.impiantiInattivi} -->

    <h1>Elenco degli impianti attivi</h1>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th>ID impianto</th>
                <th>Descrizione</th>
                <th>Latitudine</th>
                <th>Longitudine</th>
                <th>Stato</th>
            </tr>
            </thead>
            <tbody>
            <%
                ArrayList<Impianto> impiantiAttivi = impianti.getImpiantiAttivi();
                for (int i = 0; i < impiantiAttivi.size(); i++) {
                    Impianto impianto = impiantiAttivi.get(i);
            %>
            <tr>
                <td><%= impianto.getIdImpianto() %></td>
                <td><%= impianto.getDescrizione() %></td>
                <td><%= impianto.getLatitudine() %></td>
                <td><%= impianto.getLongitudine() %></td>
                <td><%= impianto.isStatus() ? "Attivo" : "Inattivo" %></td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <h1>Elenco degli impianti inattivi</h1>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th>ID impianto</th>
                <th>Descrizione</th>
                <th>Latitudine</th>
                <th>Longitudine</th>
                <th>Stato</th>
            </tr>
            </thead>
            <tbody>
            <%
                ArrayList<Impianto> impiantiInattivi = impianti.getImpiantiInattivi();
                for (int i = 0; i < impiantiInattivi.size(); i++) {
                    Impianto impianto = impiantiInattivi.get(i);
            %>
            <tr>
                <td><%= impianto.getIdImpianto() %></td>
                <td><%= impianto.getDescrizione() %></td>
                <td><%= impianto.getLatitudine() %></td>
                <td><%= impianto.getLongitudine() %></td>
                <td><%= impianto.isStatus() ? "Attivo" : "Inattivo" %></td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <h1>Ultime segnalazioni ricevute per impianto</h1>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th>ID impianto</th>
                <th>ID segnalazione</th>
                <th>ID palinsesto</th>
                <th>ID cartellone</th>
                <th>Durata</th>
                <th>Timestamp</th>
            </tr>
            </thead>
            <tbody>
            <%
                ArrayList<Impianto> impianti_list = new ArrayList<>();
                impianti_list = impianti.getImpianti();
                for (int i = 0; i < impianti_list.size(); i++) {
                    Impianto impianto = impianti_list.get(i);
                    Segnalazione last_segnalazione = impianto.getLast_segnalazione();
                    if (last_segnalazione != null) {
            %>
            <tr>
                <td><%= last_segnalazione.getId_impianto() %></td>
                <td><%= last_segnalazione.getId_segnalazione() %></td>
                <td><%= last_segnalazione.getId_palinsesto() %></td>
                <td><%= last_segnalazione.getId_cartellone() %></td>
                <td><%= last_segnalazione.getDurata() %></td>
                <td><%= last_segnalazione.getTimestamp() %></td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <h1>Mappa degli impianti attivi ed inattivi</h1>
    <div class="legend">
        <ul>
            <li><img src="icone/attivo.png" width="22px" height="22px"> Attivo</li>
            <li><img src="icone/inattivo.png" width="20px" height="20px"> Inattivo</li>
        </ul>
    </div>
    <div id="map" class="map-container" style="width: 100%; height: 580px;"></div>
</div>

<script>
    let customIcon_attivo = {
        iconUrl: "icone/attivo.png",
        iconSize: [40, 40]
    }

    let customIcon_inattivo = {
        iconUrl: "icone/inattivo.png",
        iconSize: [40, 40]
    }

    // Creating map options
    var mapOptions = {
        center: [38.1390, 13.3518],
        zoom: 12
    }

    // Creating a map object
    var map = new L.map('map', mapOptions);

    // Creating a Layer object
    var layer = new L.TileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png');

    // Adding layer to the map
    map.addLayer(layer);
    let icona_attiva = L.icon(customIcon_attivo);
    let icona_inattiva = L.icon(customIcon_inattivo);

    let iconOptionAttivo = {
        title: "Attivo",
        icon: icona_attiva
    }

    let iconOptionInattivo = {
        title: "Inattivo",
        icon: icona_inattiva
    }

    <%
    for (int i = 0; i < impiantiAttivi.size(); i++) {
        Impianto impianto = impiantiAttivi.get(i);
        %>
    L.marker([<%= impianto.getLatitudine() %>, <%= impianto.getLongitudine() %>], iconOptionAttivo).addTo(map);
    <%
}
%>

    <%
    for (int i = 0; i < impiantiInattivi.size(); i++) {
        Impianto impianto = impiantiInattivi.get(i);
        %>
    L.marker([<%= impianto.getLatitudine() %>, <%= impianto.getLongitudine() %>], iconOptionInattivo).addTo(map);
    <%
}
%>
</script>
</body>
</html>
