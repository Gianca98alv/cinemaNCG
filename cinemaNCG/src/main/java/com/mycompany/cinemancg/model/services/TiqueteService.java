package com.mycompany.cinemancg.model.services;

import com.google.gson.Gson;
import com.mycompany.cinemancg.model.Tiquete;
import com.mycompany.cinemancg.model.TiqueteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TiqueteService", urlPatterns = {"/tickets"})
public class TiqueteService extends HttpServlet {
    private Gson gson = new Gson();
    private TiqueteDAO tiqueteDAO = new TiqueteDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String idFuncion = request.getParameter("idFuncion");
            if (idFuncion != null) {
                List<Tiquete> tiquetes = tiqueteDAO.getTiquetesByFuncion(Integer.valueOf(idFuncion));
                String tiquetesJsonString = this.gson.toJson(tiquetes);
                PrintWriter out = response.getWriter();
                out.print(tiquetesJsonString);
                out.flush();
            } else {
                String idFactura = request.getParameter("idFactura");
                if (idFactura != null) {
                    List<Tiquete> tiquetes = tiqueteDAO.getTiquetesByFactura(Integer.valueOf(idFactura));
                    String tiquetesJsonString = this.gson.toJson(tiquetes);
                    PrintWriter out = response.getWriter();
                    out.print(tiquetesJsonString);
                    out.flush();
                } else {
                    throw new Exception("Su solicitud no es validad");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TiqueteService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }  
    }
}
