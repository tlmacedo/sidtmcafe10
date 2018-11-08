import br.com.cafeperfeito.sidtmcafe.model.dao.CargoDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.EmpresaDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.UsuarioDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.Cargo;
import br.com.cafeperfeito.sidtmcafe.model.vo.Empresa;
import br.com.cafeperfeito.sidtmcafe.model.vo.Usuario;
import br.com.cafeperfeito.sidtmcafe.model.vo.enums.SituacaoNoSistefma;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestJPA {

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) throws ParseException {
//        EntityManager em = new ConnectionFactory().getEntityManager();


        CargoDAO cargoDAO = new CargoDAO();
        inserirCargo(cargoDAO);
        cargoDAO.getAll(Cargo.class).stream().forEach(System.out::println);

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = new Empresa(true, 2, "08009246000136", false, "042171865",
                "T. L. MACEDO", "CAFE PERFEITO", true, true, true, 1,
                null, sdf.parse("28/04/206 01:00:00"), null, null,
                sdf.parse("28/04/2006 00:00:00"), "213-5 - EMPRESÁRIO (INDIVIDUAL)");
        empresa = empresaDAO.save(empresa);
        empresaDAO.getAll(Empresa.class).stream().forEach(System.out::println);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        inserirUsuario(usuarioDAO);
        usuarioDAO.getAll(Usuario.class).stream().forEach(System.out::println);

        empresa.setUsuarioCadastro(usuarioDAO.getById(Usuario.class, 1L));
        empresaDAO.save(empresa);
        empresaDAO.getAll(Empresa.class).stream().forEach(System.out::println);




//        UsuarioDAO usuarioDAO = new UsuarioDAO();
//
//        Cargo cargo = cargoDAO.getById(Cargo.class, 1L);
//        Usuario usuario = new Usuario("Thiago Macedo", "Thiago", "123456", sdf.parse("02/04/1981 02:00:00"), BigDecimal.valueOf(15000.0), true, cargo, "qg93SUDsMWXgcYjlYFtX4Q==");
//        usuario.setId(2);
//        usuarioDAO.save(usuario);
//        cargo = cargoDAO.getById(Cargo.class, 2L);
//        usuario = new Usuario("Carla Macedo", "Carla", "567890", sdf.parse("21/09/1978 02:00:00"), BigDecimal.valueOf(5000.0), true, cargo, "JZ4WgTIDJiviuZ7agiW2/A==");
//        usuarioDAO.save(usuario);
//
//
//        usuarioDAO.getAll(Usuario.class).stream().forEach(System.out::println);
//
//        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
//
//        colaboradorDAO.getAll(Colaborador.class).stream().forEach(System.out::println);
//

    }

    private static void inserirCargo(CargoDAO cargoDAO) {
        cargoDAO.save(new Cargo("PROGRAMADOR"));
        cargoDAO.save(new Cargo("PROPRIETARIO"));
        cargoDAO.save(new Cargo("GERENTE"));
        cargoDAO.save(new Cargo("VENDEDOR INTERNO"));
        cargoDAO.save(new Cargo("VENDEDOR EXTERNO"));
        cargoDAO.save(new Cargo("CONSULTOR"));
        cargoDAO.save(new Cargo("TECNICO"));
        cargoDAO.save(new Cargo("CONTADOR"));
        cargoDAO.save(new Cargo("MONTADOR"));
        cargoDAO.save(new Cargo("ENTREGADOR"));
        cargoDAO.save(new Cargo("ATENDENTE"));
        cargoDAO.save(new Cargo("TELEFONISTA"));
        cargoDAO.save(new Cargo("MOTORISTA"));
        cargoDAO.save(new Cargo("CONFERENTE"));
        cargoDAO.save(new Cargo("COMPRADOR"));
        cargoDAO.save(new Cargo("FISCAL"));
        cargoDAO.save(new Cargo("ASSISTENTE"));
    }

    private static void inserirUsuario(UsuarioDAO usuarioDAO) throws ParseException {
        CargoDAO cargoDAO = new CargoDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        usuarioDAO.save(new Usuario("Thiago Macedo", "thiago", "123456", sdf.parse("28/04/2006 00:00:00"), BigDecimal.valueOf(8000.0),
                true, cargoDAO.getById(Cargo.class, 1L),
                empresaDAO.getById(Empresa.class, 1L),
                "qg93SUDsMWXgcYjlYFtX4Q==",1));
    }

}
