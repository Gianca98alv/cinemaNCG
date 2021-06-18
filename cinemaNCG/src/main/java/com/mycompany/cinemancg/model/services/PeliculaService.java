package com.mycompany.cinemancg.model.services;

import com.google.gson.Gson;
import com.mycompany.cinemancg.model.PeliculaDAO;
import com.mycompany.cinemancg.model.Pelicula;
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

@WebServlet(name = "PeliculaService", urlPatterns = {"/films"})
public class PeliculaService extends HttpServlet {
    
    private Gson gson = new Gson();
    private PeliculaDAO peliculaDAO = new PeliculaDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String idPelicula = request.getParameter("idPelicula");
            if (idPelicula != null) {
                Pelicula pelicula = peliculaDAO.get(idPelicula);
                String peliculaJsonString = this.gson.toJson(pelicula);
                PrintWriter out = response.getWriter();
                out.print(peliculaJsonString);
                out.flush();
            } else {
                List<Pelicula> peliculas = peliculaDAO.getAll();
                String peliculaJsonString = this.gson.toJson(peliculas);
                PrintWriter out = response.getWriter();
                out.print(peliculaJsonString);
                out.flush();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(PeliculaService.class.getName()).log(Level.SEVERE, null, ex);
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
            Pelicula pelicula = gson.fromJson(body, Pelicula.class);
            peliculaDAO.add(pelicula);
            PrintWriter out = response.getWriter();
            out.print("{\"Status\":200}");
            out.flush();
        } catch (Exception ex) {
            Logger.getLogger(PeliculaService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }
        
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Pelicula pelicula = gson.fromJson(body, Pelicula.class);
            peliculaDAO.update(pelicula);
            PrintWriter out = response.getWriter();
            out.print("{\"Status\":200}");
            out.flush();
        } catch (Exception ex) {
            Logger.getLogger(PeliculaService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }
    }
}
