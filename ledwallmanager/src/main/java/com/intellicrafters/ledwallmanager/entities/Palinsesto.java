package com.intellicrafters.ledwallmanager.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "palinsesti")
public class Palinsesto {

    @Id
    @Column(name="id_palinsesto")
    private String idPalinsesto;

    @Column(name="path_palinsesto")
    private String pathPalinsesto;

    public void setIdPalinsesto(String idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }

    public String getIdPalinsesto() {
        return this.idPalinsesto;
    }

    public void setPathPalinsesto(String pathPalinsesto) {
        this.pathPalinsesto = pathPalinsesto;
    }

    public String getPathPalinsesto() {
        return this.pathPalinsesto;
    }

}