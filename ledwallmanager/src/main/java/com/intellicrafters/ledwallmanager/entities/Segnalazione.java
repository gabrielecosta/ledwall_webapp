package com.intellicrafters.ledwallmanager.entities;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="segnalazioni")
public class Segnalazione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id_segnalazione")
    private int idSegnalazione;

    @Column(name = "id_impianto")
    private int idImpianto;

    @Column(name = "id_palinsesto")
    private String idPalinsesto;

    @Column(name = "id_cartellone")
    private String idCartellone;

    @Column(name = "durata")
    private int durata;

    @Column(name = "timestamp")
    private Date timestamp;

    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(int idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public int getIdImpianto() {
        return this.idImpianto;
    }

    public void setIdImpianto(int idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getIdPalinsesto() {
        return this.idPalinsesto;
    }

    public void setIdPalinsesto(String idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }

    public String getIdCartellone() {
        return idCartellone;
    }

    public void setIdCartellone(String id_cartellone) {
        this.idCartellone = idCartellone;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}