package com.intellicrafters.assignment3;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;
import java.sql.Timestamp;

@WebServlet(name = "inviasegnalazione", value = "/inviasegnalazione")
public class InviaSegnalazione extends HttpServlet {

    // Attributi
    private int idImpianto;
    private String idPalinsesto;
    private String idCartellone;
    private int durata;
    private Timestamp timestamp;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Estrazione dei dati della richiesta
        StringBuilder jsonBuffer = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(jsonBuffer.toString());
        this.idImpianto = jsonObject.getInt("id_impianto");
        this.idPalinsesto = jsonObject.getString("id_palinsesto");
        this.idCartellone = jsonObject.getString("id_cartellone");
        this.durata = jsonObject.getInt("durata");
        this.timestamp = new Timestamp(System.currentTimeMillis());

        // Dati estratti
        System.out.println("ID impianto: " + this.idImpianto);
        System.out.println("ID palinsesto: " + this.idPalinsesto);
        System.out.println("ID cartellone: " + this.idCartellone);
        System.out.println("Durata cartellone: " + this.durata);
        System.out.println("Timestamp: " + this.timestamp);

        // Connessione al database
        System.out.println("Connessione al database in corso...");
        // DBConnectionProvider dbConnectionProvider = new DBConnectionProvider("localhost", 3306, "intellicraftersdb", "root", "gabriele");
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider("frostnetwork.iliadboxos.it", 3306, "intellicraftersdb", "intellicrafters", "mg7XSzS9mmvx5sJ4r2ex");
        dbConnectionProvider.inserisciSegnalazione(this.idImpianto, this.idPalinsesto, this.idCartellone, this.durata, this.timestamp);

    }
}
