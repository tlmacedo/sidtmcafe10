import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.dao.CargoDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.ColaboradorDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.UsuarioDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.Cargo;
import br.com.cafeperfeito.sidtmcafe.model.vo.Colaborador;
import br.com.cafeperfeito.sidtmcafe.model.vo.Usuario;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestJPA {

    public static void main(String[] args) throws ParseException {
        EntityManager em = new ConnectionFactory().getEntityManager();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//        CargoDAO cargoDAO = new CargoDAO();
//
//        cargoDAO.getAll(Cargo.class).stream().forEach(System.out::println);
//
//
//        UsuarioDAO usuarioDAO = new UsuarioDAO();
//
//        Cargo cargo = cargoDAO.getById(Cargo.class, 1L);
//        Usuario usuario = new Usuario("Thiago Macedo", "Thiago", "123456", sdf.parse("02/04/1981 02:00:00"), BigDecimal.valueOf(15000.0), true, cargo , "qg93SUDsMWXgcYjlYFtX4Q==");
//        usuario.setId(2);
//        usuarioDAO.save(usuario);
//        cargo = cargoDAO.getById(Cargo.class, 2L);
//        usuario = new Usuario("Carla Macedo", "Carla", "567890", sdf.parse("21/09/1978 02:00:00"), BigDecimal.valueOf(5000.0), true, cargo , "JZ4WgTIDJiviuZ7agiW2/A==");
//        usuarioDAO.save(usuario);
//
//
//        usuarioDAO.getAll(Usuario.class).stream().forEach(System.out::println);
//
//        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
//
//        colaboradorDAO.getAll(Colaborador.class).stream().forEach(System.out::println);
//
//
    }

}
