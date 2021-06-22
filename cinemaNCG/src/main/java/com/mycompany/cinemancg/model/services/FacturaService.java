package com.mycompany.cinemancg.model.services;

import com.google.gson.Gson;
import com.mycompany.cinemancg.model.Factura;
import com.mycompany.cinemancg.model.FacturaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FacturaService", urlPatterns = {"/invoices"})
public class FacturaService extends HttpServlet{
    
    private Gson gson = new Gson();
    private FacturaDAO FacturaDAO = new FacturaDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String idFactura = request.getParameter("idFactura");
            if (idFactura != null) {
                Factura factura = FacturaDAO.get(Integer.valueOf(idFactura));
                String facturaJsonString = this.gson.toJson(factura);
                PrintWriter out = response.getWriter();
                out.print(facturaJsonString);
                out.flush();
            } else {
                List<Factura> facturas = FacturaDAO.getAll();
                String facturaJsonString = this.gson.toJson(facturas);
                PrintWriter out = response.getWriter();
                out.print(facturaJsonString);
                out.flush();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(FacturaService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }  
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Factura factura = gson.fromJson(body, Factura.class);
            FacturaDAO.add(factura);
            PrintWriter out = response.getWriter();
            out.print("{\"Status\":200}");
            out.flush();
        } catch (Exception ex) {
            Logger.getLogger(FacturaService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }
    }   
}
