package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.Constants;
import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.CargoDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.UsuarioDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Cargo;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import br.com.tlmacedo.cafeperfeito.service.ServiceCryptografia;
import br.com.tlmacedo.cafeperfeito.service.ServiceTremeView;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariavelSistema;
import br.com.tlmacedo.cafeperfeito.view.ViewLogin;
import br.com.tlmacedo.cafeperfeito.view.ViewPrincipal;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerLogin extends ServiceVariavelSistema implements Initializable, ModeloCafePerfeito, Constants {
    public AnchorPane painelViewLogin;
    public JFXComboBox<Usuario> cboUsuarioLogin;
    public JFXPasswordField pswUsuarioSenha;
    public JFXButton btnOK;
    public JFXButton btnCancela;


    private Usuario usuarioVO;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private CargoDAO cargoDAO = new CargoDAO();

    @Override
    public void fechar() {
        ViewLogin.getStage().close();
    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjetos() {
        cboUsuarioLogin.setPromptText(String.format("%s:", "Selecione usuário"));
//        System.out.println("Usuarios: " + usuarioDAO.getAll(Usuario.class));
//        System.out.println("Cargos: " + cargoDAO.getAll(Cargo.class));
        cboUsuarioLogin.getItems().setAll(usuarioDAO.getAll(Usuario.class));
    }

    @Override
    public void fatorarObjetos() {
        cboUsuarioLogin.setCellFactory(new Callback<ListView<Usuario>, ListCell<Usuario>>() {
            @Override
            public ListCell<Usuario> call(ListView<Usuario> param) {
                final ListCell<Usuario> cell = new ListCell<Usuario>() {
                    @Override
                    protected void updateItem(Usuario item, boolean empty) {
                        super.updateItem(item, empty);
                        System.out.println("item.getDetalhesUsuario()" + item);;
                        if (item == null) setText(null);
                        else {
                            if (getIndex() == -1) setText(item.toString());
                            else {
                                String novoTexto = "";
                                for (String det : item.getDetalheUsuario().split(";"))
                                    if (novoTexto == "") novoTexto += det;
                                    else novoTexto += "\r\n" + det;
                                setText(novoTexto);
                            }
                        }
                    }
                };
                return cell;
            }
        });
    }

    @Override
    public void escutarTecla() {
        //noinspection Duplicates
        painelViewLogin.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER && btnOK.isDisable())
                if (cboUsuarioLogin.isFocused())
                    pswUsuarioSenha.requestFocus();
                else
                    cboUsuarioLogin.requestFocus();
            if (event.getCode() == KeyCode.F12)
                btnCancela.fire();
        });

        cboUsuarioLogin.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            usuarioVO = newValue;
        });

        btnCancela.setOnAction(event -> fechar());

        btnOK.setDisable(true);

        btnOK.setOnAction(event -> {
            if (cboUsuarioLogin.getSelectionModel().getSelectedIndex() < 0) return;
            System.out.println(String.format("usuario: %s" , usuarioVO.getNome()));
            System.out.println(String.format("senha:: %s", usuarioVO.getSenha()));
            //System.out.println(String.format("senhaCrypto:   [%s]", ServiceCryptografia.encrypt(pswUsuarioSenha.getText())));


            if (!ServiceCryptografia.senhaValida(pswUsuarioSenha.getText(), usuarioVO.getSenha())) {
                new Thread(() -> new ServiceTremeView().setStage(ViewLogin.getStage())).start();
                return;
            }
            preencheVariaveisUsuario(usuarioVO);
            fechar();
            new ViewPrincipal().openViewPrincipal();
        });

        cboUsuarioLogin.getSelectionModel().selectedIndexProperty().addListener((ov, o, n) -> {
            habilitarBotaoOK();
        });

        pswUsuarioSenha.lengthProperty().addListener((ov, o, n) -> {
            habilitarBotaoOK();
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
        Platform.runLater(() -> Locale.setDefault(LOCALE_MY));
    }

    void habilitarBotaoOK() {
        btnOK.setDisable(usuarioVO == null || pswUsuarioSenha.getText().length() == 0);
    }

    void preencheVariaveisUsuario(Usuario colaboradorVO) {
//        USUARIO_LOGADO_ID = String.valueOf(colaboradorVO.getId());
//        USUARIO_LOGADO_NOME = colaboradorVO.getNome();
//        USUARIO_LOGADO_APELIDO = colaboradorVO.getApelido();
//        DATA_HORA = LocalDateTime.now();
//        DATA_HORA_STR = DATA_HORA.format(DTF_DATAHORA);
//        USUARIO_LOGADO_DATA = LocalDate.now();
//        USUARIO_LOGADO_DATA_STR = USUARIO_LOGADO_DATA.format(DTF_DATA);
//        USUARIO_LOGADO_HORA = LocalTime.now();
//        USUARIO_LOGADO_HORA_STR = USUARIO_LOGADO_HORA.format(DTF_HORA);
    }

}
