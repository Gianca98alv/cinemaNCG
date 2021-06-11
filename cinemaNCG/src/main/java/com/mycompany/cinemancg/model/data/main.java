package com.mycompany.cinemancg.model.data;


import com.mycompany.cinemancg.model.Usuario;
import com.mycompany.cinemancg.model.UsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {  // Testers For Daos
    public static void main(String[] args) {
        try {
            Usuario usuario;
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuario = usuarioDAO.get("1");
            System.out.print(usuario.toString());
        } catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
