package com.intellicrafters.assignment3;

public class Segnalazione {
    private int id_segnalazione;
    private int id_impianto;
    private String id_palinsesto;
    private String id_cartellone;
    private int durata;
    private String timestamp;

    public Segnalazione(
            int id_segnalazione,
            int id_impianto,
            String id_palinsesto,
            String id_cartellone,
            int durata,
            String timestamp
    ) {
        this.setId_segnalazione(id_segnalazione);
        this.setId_impianto(id_impianto);
        this.setId_palinsesto(id_palinsesto);
        this.setId_cartellone(id_cartellone);
        this.setDurata(durata);
        this.setTimestamp(timestamp);
    }

    public int getId_segnalazione() {
        return this.id_segnalazione;
    }

    public void setId_segnalazione(int id_segnalazione) {
        this.id_segnalazione = id_segnalazione;
    }

    public int getId_impianto() {
        return this.id_impianto;
    }

    public void setId_impianto(int id_impianto) {
        this.id_impianto = id_impianto;
    }

    public String getId_palinsesto() {
        return this.id_palinsesto;
    }

    public void setId_palinsesto(String id_palinsesto) {
        this.id_palinsesto = id_palinsesto;
    }

    public String getId_cartellone() {
        return this.id_cartellone;
    }

    public void setId_cartellone(String id_cartellone) {
        this.id_cartellone = id_cartellone;
    }

    public int getDurata() {
        return this.durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getTimestamp() {
        return String.valueOf(this.timestamp);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

