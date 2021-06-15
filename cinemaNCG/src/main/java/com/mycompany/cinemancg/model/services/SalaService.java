/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cinemancg.model.services;

import com.google.gson.Gson;
import com.mycompany.cinemancg.model.SalaDAO;
import com.mycompany.cinemancg.model.Sala;
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

/**
 *
 * @author User
 */

@WebServlet(name = "SalaService", urlPatterns = {"/rooms"})
public class SalaService extends HttpServlet {
    
    private Gson gson = new Gson();
    private SalaDAO salaDAO = new SalaDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String idSala = request.getParameter("idSala");
            if (idSala != null) {
                Sala sala = salaDAO.get(Integer.valueOf(idSala));
                String salaJsonString = this.gson.toJson(sala);
                PrintWriter out = response.getWriter();
                out.print(salaJsonString);
                out.flush();
            } else {
                List<Sala> salas = salaDAO.getAll();
                String salaJsonString = this.gson.toJson(salas);
                PrintWriter out = response.getWriter();
                out.print(salaJsonString);
                out.flush();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, null, ex);
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
            Sala sala = gson.fromJson(body, Sala.class);
            salaDAO.add(sala);
            PrintWriter out = response.getWriter();
            out.print("{\"Status\":200}");
            out.flush();
        } catch (Exception ex) {
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }
        
    }
    
}
