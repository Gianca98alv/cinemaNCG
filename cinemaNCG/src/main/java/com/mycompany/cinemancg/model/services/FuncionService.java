package com.mycompany.cinemancg.model.services;

import com.google.gson.Gson;
import com.mycompany.cinemancg.model.FuncionDAO;
import com.mycompany.cinemancg.model.SalaDAO;
import com.mycompany.cinemancg.model.PeliculaDAO;
import com.mycompany.cinemancg.model.Funcion;
import com.mycompany.cinemancg.model.Sala;
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


@WebServlet(name = "FuncionService", urlPatterns = {"/shows"})
public class FuncionService extends HttpServlet{
    private Gson gson = new Gson();
    private FuncionDAO funcDAO = new FuncionDAO();
    private SalaDAO salaDAO = new SalaDAO();
    private PeliculaDAO peliDAO = new PeliculaDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String idFuncion = request.getParameter("idFuncion");
            if (idFuncion != null) {
                Funcion func = funcDAO.get(Integer.valueOf(idFuncion));
                String funcJsonString = this.gson.toJson(func);
                PrintWriter out = response.getWriter();
                out.print(funcJsonString);
                out.flush();
            } else {
                List<Funcion> funcs = funcDAO.getAll();
                String funcJsonString = this.gson.toJson(funcs);
                PrintWriter out = response.getWriter();
                out.print(funcJsonString);
                out.flush();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(FuncionService.class.getName()).log(Level.SEVERE, null, ex);
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
            Funcion func = gson.fromJson(body, Funcion.class);
            Sala sala  = salaDAO.get(func.getSala().getIdSala());
            Pelicula peli = peliDAO.get(func.getPelicula().getIdPelicula());
            if(sala != null && peli != null && funcDAO.validateSchedule(func)){
                funcDAO.add(func);
                PrintWriter out = response.getWriter();
                out.print("{\"Status\":200}");
                out.flush();
            }else{
                throw new Exception("No se encontro la sala, la pelicula o la sala esta ocupada en este horario");
            }
        } catch (Exception ex) {
            Logger.getLogger(FuncionService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }   
    }
}