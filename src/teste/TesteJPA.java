import br.com.tlmacedo.cafeperfeito.model.dao.UsuarioDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import br.com.tlmacedo.cafeperfeito.service.ServiceImprimirListaJSon;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TesteJPA {
    public static void main(String[] args) throws IOException {
        System.out.println("qual codigo de usuarioVO vc deseja verificar?:");
        Usuario usuarioVO = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioVO = usuarioDAO.getById(Usuario.class, Long.valueOf(new Scanner(System.in).nextLine()));
//        ModelMapper modelMapper = new ModelMapper();
//        UsuarioDTO usuarioDTO = modelMapper.map(usuarioVO, UsuarioDTO.class);
        List list = new ArrayList();
        list.add(usuarioVO);
        ServiceImprimirListaJSon.imprimirLista(list);

//        TelefoneDAO telefoneDAO = new TelefoneDAO();
//        ServiceImprimirListaJSon.imprimirLista(telefoneDAO.getAll(TelefoneDAO.class));
    }

    private static Long getDigitado(String scanner) {
        switch (Integer.parseInt(scanner)) {
            case 1:
                return 1L;
            case 2:
                return 2L;
            case 3:
                return 3L;
            case 4:
                return 4L;
            case 5:
                return 5L;
        }

        return null;
    }
}
