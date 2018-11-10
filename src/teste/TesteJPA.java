import br.com.cafeperfeito.sidtmcafe.model.dao.UsuarioDAO;
import br.com.cafeperfeito.sidtmcafe.model.dto.UsuarioDTO;
import br.com.cafeperfeito.sidtmcafe.model.vo.Usuario;
import br.com.cafeperfeito.sidtmcafe.service.ServiceImprimirListaJSon;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TesteJPA {
    public static void main(String[] args) throws IOException {
        System.out.println("qual codigo de usuario vc deseja verificar?:");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario = usuarioDAO.getById(Usuario.class, Long.valueOf(new Scanner(System.in).nextLine()));
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        List list = new ArrayList();
        list.add(usuarioDTO);
        ServiceImprimirListaJSon.imprimirLista(list);
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
