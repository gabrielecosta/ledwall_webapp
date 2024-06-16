package com.intellicrafters.ledwallmanager.entities;
import jakarta.persistence.*;

@Entity
@Table(name="impianti")
public class Impianto {

    @Id
    @Column(name="id_impianto")
    private int idImpianto;

    @Column(name="descrizione")
    private String descrizione;

    @Column(name="latitudine")
    private Double latitudine;

    @Column(name="longitudine")
    private Double longitudine;

    @OneToOne
    @JoinColumn(name = "id_palinsesto", referencedColumnName = "id_palinsesto")
    private Palinsesto palinsesto;

    @Column(name="status_impianto")
    private int statusImpianto;

    public int getIdImpianto() {
        return this.idImpianto;
    }

    public void setIdImpianto(int idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getLatitudine() {
        return this.latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return this.longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    public Palinsesto getPalinsesto() {
        return this.palinsesto;
    }

    public void setPalinsesto(Palinsesto palinsesto) {
        this.palinsesto = palinsesto;
    }

    public int getStatusImpianto() {
        return this.statusImpianto;
    }

    public void setStatusImpianto(int statusImpianto) {
        this.statusImpianto = statusImpianto;
    }
}
