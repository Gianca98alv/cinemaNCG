/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cinemancg.model.services;

import com.google.gson.Gson;
import com.mycompany.cinemancg.model.Usuario;
import com.mycompany.cinemancg.model.UsuarioDAO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */

@WebServlet(name = "UsuarioService", urlPatterns = {"/users"})
public class UsuarioService extends HttpServlet {
    
    private Gson gson = new Gson();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            Usuario usuario = usuarioDAO.get(request.getParameter("idUsuario"));
            String usuarioJsonString = this.gson.toJson(usuario);
            PrintWriter out = response.getWriter();
            out.print(usuarioJsonString);
            out.flush();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
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
            Usuario usuario = gson.fromJson(body, Usuario.class);
            usuarioDAO.add(usuario);
            PrintWriter out = response.getWriter();
            out.print("{\"Status\":200}");
            out.flush();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
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
            Usuario usuario = usuarioDAO.get(request.getParameter("idUsuario"));
            if (usuario.getContrasena().equals(request.getParameter("contrasena"))) {
                String usuarioJsonString = this.gson.toJson(usuario);
                PrintWriter out = response.getWriter();
                out.print(usuarioJsonString);
                out.flush();
            } else {
                throw new Exception ("Usuario o contrasena erronea");
            }
            
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
            String json_string = "{\"Status\":404}";
            PrintWriter out = response.getWriter();
            out.print(json_string);
            out.flush();
        }
    }

}
    
