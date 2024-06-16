package com.intellicrafters.assignment3;

import com.intellicrafters.assignment3.beans.ImpiantiBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "listaimpiantimvc", value = "/listaimpiantimvc")
public class ListaImpiantiMvc extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Connessione al database in corso...");
        // istanzio la classe che mi permette di fare le query
        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider("frostnetwork.iliadboxos.it", 3306, "intellicraftersdb", "intellicrafters", "mg7XSzS9mmvx5sJ4r2ex");
        // DBConnectionProvider dbConnectionProvider = new DBConnectionProvider("localhost", 3306, "intellicraftersdb", "root", "gabriele");
        // DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
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

        // Create an instance of the ImpiantiBean
        ImpiantiBean impiantiBean = new ImpiantiBean();
        impiantiBean.setImpianti(impianti);
        impiantiBean.setImpiantiAttivi(impianti_attivi);
        impiantiBean.setImpiantiInattivi(impianti_inattivi);

        // Set the bean in the request scope
        request.setAttribute("impianti", impiantiBean);

        // Forward the request to the JSP page
        request.getRequestDispatcher("/monitoraggio.jsp").forward(request, response);
    }

}
