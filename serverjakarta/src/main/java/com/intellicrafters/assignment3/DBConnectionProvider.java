package com.intellicrafters.assignment3;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class DBConnectionProvider {
    // Attributi
    private String hostname;
    private int port;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    public DBConnectionProvider(String hostname, int port, String database, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    private void getConnection() {
        // Istanziazione di un oggetto di tipo Connection
        String connectionString = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;
        System.out.println(connectionString);
        Connection dbConnection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectionString, this.username, this.password);
        } catch (Exception e) {
            // Stampa l'eccezione
            e.printStackTrace();

            // Ritorna null
            dbConnection = null;
        }
        this.connection = dbConnection;
    }

    // Metodo per inserire le query nel DB
    public void inserisciSegnalazione(int idImpianto, String idPalinsesto, String idCartellone, int durata, Timestamp timestamp) {
        try {
            // Connessione al DB
            this.getConnection();

            // Inserimento nel database della segnalazione
            String query = "insert into segnalazioni(id_impianto, id_palinsesto, id_cartellone, durata, timestamp) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, idImpianto);
            preparedStatement.setString(2, idPalinsesto);
            preparedStatement.setString(3, idCartellone);
            preparedStatement.setInt(4, durata);
            preparedStatement.setTimestamp(5, timestamp);
            System.out.println("Inserimento della segnalazione in corso...");
            preparedStatement.executeUpdate();
            System.out.println("Operazione completata!");

            // Chiusura della connessione col database
            preparedStatement.close();
            this.connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore nella gestione della richiesta!");
        }
    }

    // Metodo per selezionare gli impianti attivi
    public ArrayList<Impianto> ottieniImpiantiAttivi() {
        // Connessione al database
        this.getConnection();

        // Lista di impianti
        ArrayList<Impianto> impiantiAttivi = new ArrayList<>();
        try {
            // Query per selezionare gli impianti attivi negli ultimi 2 minuti
            String query = "select * from impianti where id_impianto in (select id_impianto from segnalazioni where timestampdiff(minute, timestamp, NOW()) < 2)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_impianto = resultSet.getInt("id_impianto");
                String descrizione = resultSet.getString("descrizione");
                float latitudine = resultSet.getFloat("latitudine");
                float longitudine = resultSet.getFloat("longitudine");

                // Aggiungo l'impianto attivo alla lista degli impianti
                System.out.println("Aggiunta dell'impianto " + id_impianto + " in corso...");

                // Creazione di una lista di segnalazioni
                ArrayList<Segnalazione> segnalazioni = this.getLastSegnalazione(id_impianto);

                // Ottenimento dell'ultima segnalazione
                Segnalazione last_segnalazione = null;
                if(!segnalazioni.isEmpty()) {
                    last_segnalazione = segnalazioni.get(0);
                }

                boolean status = true;

                // Aggiunta dell'impianto attivo
                impiantiAttivi.add(new Impianto(id_impianto, descrizione, latitudine, longitudine, status, last_segnalazione));
            }
            // chiudo tutte cose
            resultSet.close();
            preparedStatement.close();
            this.connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Si è verificato un problema nel recuperare gli impianti attivi!");
        }
        // Ritorna gli impianti
        return impiantiAttivi;
    }

    // Metodo per selezionare gli impianti inattivi
    public ArrayList<Impianto> ottieniImpiantiInattivi() {

        // Connessione al database
        this.getConnection();

        // Lista di impianti
        ArrayList<Impianto> impiantiInattivi = new ArrayList<>();
        try {
            // Query per selezionare gli impianti inattivi negli ultimi 2 minuti
            String query = "select * from impianti where id_impianto not in (select id_impianto from segnalazioni where timestampdiff(minute, timestamp, NOW()) < 2)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_impianto = resultSet.getInt("id_impianto");
                String descrizione = resultSet.getString("descrizione");
                float latitudine = resultSet.getFloat("latitudine");
                float longitudine = resultSet.getFloat("longitudine");

                // Aggiungo l'impianto attivo alla lista degli impianti
                System.out.println("Aggiunta dell'impianto " + id_impianto + " in corso...");
                ArrayList<Segnalazione> segnalazioni = this.getLastSegnalazione(id_impianto);
                Segnalazione last_segnalazione;
                if (segnalazioni.isEmpty()) {
                    last_segnalazione = null;
                } else {
                    last_segnalazione = segnalazioni.get(0);
                }
                boolean status = false;
                impiantiInattivi.add(new Impianto(id_impianto, descrizione, latitudine, longitudine, status, last_segnalazione));
            }
            // Chiusura della connessione al database
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Si è verificato un problema nel recuperare gli impianti attivi!");
        }
        // Ritorna gli impianti
        return impiantiInattivi;
    }

    // Metodo per selezionare gli impianti inattivi
    public ArrayList<Segnalazione> getLastSegnalazione(int idImpianto) {
        // Connessione al database
        this.getConnection();

        // Segnalazioni
        ArrayList<Segnalazione> segnalazioni = new ArrayList<>();
        try {
            // Query per selezionare gli impianti inattivi negli ultimi 2 minuti
            String query = "select * from segnalazioni where id_impianto=? order by timestamp desc limit 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idImpianto);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_segnalazione = resultSet.getInt("id_segnalazione");
                int id_impianto = resultSet.getInt("id_impianto");
                String id_palinsesto = resultSet.getString("id_palinsesto");
                String id_cartellone = resultSet.getString("id_cartellone");
                int durata = resultSet.getInt("durata");
                String timestamp = resultSet.getString("timestamp");
                // Aggiungo l'impianto attivo alla lista degli impianti
                System.out.println("Richiesta ultima segnalazione impianto " + id_impianto + " in corso...");
                segnalazioni.add(new Segnalazione(id_segnalazione, id_impianto, id_palinsesto, id_cartellone, durata, timestamp));
            }
            // Chiusura della connessione al database
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Si è verificato un problema nel recuperare gli impianti attivi!");
        }
        // Ritorna gli impianti
        return segnalazioni;
    }

}
