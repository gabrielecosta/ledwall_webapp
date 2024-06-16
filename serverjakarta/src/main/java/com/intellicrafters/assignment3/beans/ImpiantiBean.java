package com.intellicrafters.assignment3.beans;
import java.util.ArrayList;
import com.intellicrafters.assignment3.Segnalazione;
import com.intellicrafters.assignment3.Impianto;

public class ImpiantiBean {
    ArrayList<Impianto> impiantiInattivi = new ArrayList<>();
    ArrayList<Impianto> impiantiAttivi = new ArrayList<>();
    ArrayList<Impianto> impianti = new ArrayList<>();

    public ArrayList<Impianto> getImpianti() { return this.impianti; }
    public void setImpianti(ArrayList<Impianto> impianti) {
        for(int i=0; i<impianti.size(); i++) {
            this.impianti.add(impianti.get(i));
        }
    }

    public ArrayList<Impianto> getImpiantiInattivi() {
        return this.impiantiInattivi;
    }
    public void setImpiantiInattivi(ArrayList<Impianto> impianti) {
        for(int i=0; i<impianti.size(); i++) {
            this.impiantiInattivi.add(impianti.get(i));
        }
    }

    public ArrayList<Impianto> getImpiantiAttivi() {
        return this.impiantiAttivi;
    }
    public void setImpiantiAttivi(ArrayList<Impianto> impianti) {
        for(int i=0; i<impianti.size(); i++) {
            this.impiantiAttivi.add(impianti.get(i));
        }
    }
}
