package com.intellicrafters.assignment3;

public class Impianto {
    private int idImpianto;
    private String descrizione = null;
    private float latitudine;
    private float longitudine;
    private boolean status;
    private Segnalazione last_segnalazione;

    public Impianto(int idImpianto, String descrizione, float latitudine, float longitudine, boolean status, Segnalazione last_segnalazione) {
        this.idImpianto = idImpianto;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.status = status;
        this.last_segnalazione = last_segnalazione;
    }

    public Impianto(int idImpianto, float latitudine, float longitudine) {
        this.idImpianto = idImpianto;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public int getIdImpianto() {
        return this.idImpianto;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public float getLatitudine() {
        return this.latitudine;
    }

    public float getLongitudine() {
        return this.longitudine;
    }

    public String toString() {
        return String.format("ID impianto: %d, Descrizione: %s, Latitudine: %f, Longitudine: %f", idImpianto, descrizione, latitudine, longitudine);
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Segnalazione getLast_segnalazione() {
        return this.last_segnalazione;
    }

    public void setLast_segnalazione(Segnalazione last_segnalazione) {
        this.last_segnalazione = last_segnalazione;
    }
}
