
import com.mycompany.cinemancg.model.Funcion;
import com.mycompany.cinemancg.model.FuncionDAO;
import com.mycompany.cinemancg.model.Pelicula;
import com.mycompany.cinemancg.model.PeliculaDAO;
import com.mycompany.cinemancg.model.Sala;
import com.mycompany.cinemancg.model.SalaDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */

public class FuncionTest {
    public static void main(String[] args) {
        try {
            String stringDate = "8/9/2021 01:00";
            Date inicio = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(stringDate);
            PeliculaDAO peliculaDAO = new PeliculaDAO();
            SalaDAO salaDAO = new SalaDAO();
            Pelicula pelicula = peliculaDAO.get("The Turning");
            Sala sala = salaDAO.get(1);
            //Funcion funcion = new Funcion(2, 3000.0f, inicio);
            FuncionDAO funcionDAO = new FuncionDAO();
            //funcion.setPelicula(pelicula);
            //funcion.setSala(sala);
            //int respuesta = funcionDAO.add(funcion);
            List<Funcion> funciones1 = funcionDAO.getAll();
            List<Funcion> funciones2 = funcionDAO.getAllByPelicula(pelicula.getIdPelicula());
            //funciones = funcionDAO.getAll();
            System.out.println(funciones1);
            System.out.println(funciones2);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
