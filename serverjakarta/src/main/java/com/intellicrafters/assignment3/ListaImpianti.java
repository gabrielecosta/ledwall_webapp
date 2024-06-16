package com.intellicrafters.assignment3;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "listaimpianti", value = "/listaimpianti")
public class ListaImpianti extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Connessione al database in corso...");
        // istanzio la classe che mi permette di fare le query
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider("frostnetwork.iliadboxos.it", 3306, "intellicraftersdb", "intellicrafters", "mg7XSzS9mmvx5sJ4r2ex");
        // DBConnectionProvider dbConnectionProvider = new DBConnectionProvider("localhost", 3306, "intellicraftersdb", "root", "gabriele");
        ArrayList<Impianto> impianti = new ArrayList<>();
        ArrayList<Impianto> impianti_attivi = dbConnectionProvider.ottieniImpiantiAttivi();
        System.out.println("LISTA IMPIANTI ATTIVI");
        for (Impianto imp1 : impianti_attivi) {
            System.out.println(imp1);
            impianti.add(imp1);
        }
        ArrayList<Impianto> impianti_inattivi = dbConnectionProvider.ottieniImpiantiInattivi();
        System.out.println("LISTA IMPIANTI INATTIVI");
        for (Impianto imp2 : impianti_inattivi) {
            System.out.println(imp2);
            impianti.add(imp2);
        }

        StringBuilder jsonBuilder = this.buildJsonImpianti(impianti);
        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Write JSON string to response
        response.getWriter().write(jsonBuilder.toString());
    }

    private StringBuilder buildJsonImpianti(ArrayList<Impianto> impianti) {
        // Convert ArrayList to JSON manually
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < impianti.size(); i++) {
            Impianto imp = impianti.get(i);
            jsonBuilder.append("{");
            jsonBuilder.append("\"idImpianto\":").append(imp.getIdImpianto()).append(",");
            jsonBuilder.append("\"descrizione\":").append("\"").append(imp.getDescrizione()).append("\",");
            jsonBuilder.append("\"latitudine\":").append(imp.getLatitudine()).append(",");
            jsonBuilder.append("\"longitudine\":").append(imp.getLongitudine()).append(",");
            jsonBuilder.append("\"status\":").append(imp.isStatus()).append(",");
            if (imp.getLast_segnalazione() != null) {
                Segnalazione segnalazione = imp.getLast_segnalazione();
                jsonBuilder.append("\"last_segnalazione\":{");
                jsonBuilder.append("\"id_segnalazione\":").append(segnalazione.getId_segnalazione()).append(",");
                jsonBuilder.append("\"id_impianto\":").append(segnalazione.getId_impianto()).append(",");
                jsonBuilder.append("\"id_palinsesto\":").append("\"").append(segnalazione.getId_palinsesto()).append("\",");
                jsonBuilder.append("\"id_cartellone\":").append("\"").append(segnalazione.getId_cartellone()).append("\",");
                jsonBuilder.append("\"durata\":").append(segnalazione.getDurata()).append(",");
                jsonBuilder.append("\"timestamp\":").append("\"").append(segnalazione.getTimestamp()).append("\"");
                jsonBuilder.append("}");
            } else {
                jsonBuilder.append("\"last_segnalazione\":null");
            }
            jsonBuilder.append("}");
            if (i < impianti.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder;
    }

}
