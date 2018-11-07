import br.com.cafeperfeito.sidtmcafe.model.dao.CargoDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.ColaboradorDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.UsuarioDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.Cargo;
import br.com.cafeperfeito.sidtmcafe.model.vo.Colaborador;
import br.com.cafeperfeito.sidtmcafe.model.vo.Usuario;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestJPA {

    public static void main(String[] args) throws ParseException {
        //EntityManager em = new ConnectionFactory().getEntityManager();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Cargo cargo = new Cargo("mais uma vez");
        cargo.setId(18);

        CargoDAO cargoDAO = new CargoDAO();

        cargoDAO.save(cargo);

        cargoDAO.getAll(Cargo.class).stream().forEach(System.out::println);


        Usuario usuario = new Usuario("Thiago Macedo", "Thiago", "123456", sdf.parse("02/04/1981 02:00:00"), BigDecimal.valueOf(5000.0), true, cargo, "j5tRRf6yQ/WY4Mq6JCPhpw==");
        usuario.setId(2);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.save(usuario);

        usuarioDAO.getAll(Usuario.class).stream().forEach(System.out::println);

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

        colaboradorDAO.getAll(Colaborador.class).stream().forEach(System.out::println);


    }

}
