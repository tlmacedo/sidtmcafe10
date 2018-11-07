/*
module sidtmcafe {
    exports br.com.cafeperfeito.sidtmCafe.view;
    exports br.com.cafeperfeito.sidtmCafe.controller;


    requires  javafx.controls;
    requires java.desktop;
    requires java.sql;
    requires javafx.fxml;
    requires com.jfoenix;
    requires json;

    requires javafx.base;
    requires javafx.graphics;
    requires javafx.web;



}
 */

package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.TabColaboradorDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabColaboradorVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceCryptografia;
import br.com.cafeperfeito.sidtmcafe.service.ServiceTremeView;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewLogin;
import br.com.cafeperfeito.sidtmcafe.view.ViewPrincipal;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerLogin extends ServiceVariavelSistema implements Initializable, ModelController, Constants {
    public AnchorPane painelViewLogin;
    public JFXComboBox<TabColaboradorVO> cboUsuarioLogin;
    public JFXPasswordField pswUsuarioSenha;
    public JFXButton btnOK;
    public JFXButton btnCancela;

    @Override
    public void fechar() {
        ViewLogin.getStage().close();
    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjetos() {
        cboUsuarioLogin.setPromptText("Selecione usuário: ");
        cboUsuarioLogin.getItems().setAll(new TabColaboradorDAO().getTabColaboradorVOList());
    }

    @Override
    public void fatorarObjetos() {
        cboUsuarioLogin.setCellFactory(new Callback<ListView<TabColaboradorVO>, ListCell<TabColaboradorVO>>() {
            @Override
            public ListCell<TabColaboradorVO> call(ListView<TabColaboradorVO> param) {
                final ListCell<TabColaboradorVO> cell = new ListCell<TabColaboradorVO>() {
                    @Override
                    protected void updateItem(TabColaboradorVO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) setText(null);
                        else {
                            if (getIndex() == -1) setText(item.toString());
                            else {
                                String novoTexto = "";
                                for (String det : item.getDetalheColaborador().split(";"))
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

        btnCancela.setOnAction(event -> fechar());

        btnOK.setDisable(true);

        btnOK.setOnAction(event -> {
            if (cboUsuarioLogin.getSelectionModel().getSelectedIndex() < 0) return;
            TabColaboradorVO colaboradorVO = cboUsuarioLogin.getSelectionModel().getSelectedItem();
            System.out.println(String.format("senhaDigitada: [%s]", pswUsuarioSenha.getText()));
            System.out.println(String.format("senhaUsuario:  [%s]", colaboradorVO.getSenha()));
            System.out.println(String.format("senhaCrypto:   [%s]", ServiceCryptografia.encrypt(pswUsuarioSenha.getText())));


            if (!ServiceCryptografia.encrypt(pswUsuarioSenha.getText()).equals(colaboradorVO.getSenha())) {
                new Thread(() -> new ServiceTremeView().setStage(ViewLogin.getStage())).start();
                return;
            }
            preencheVariaveisUsuario(colaboradorVO);
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
        btnOK.setDisable(cboUsuarioLogin.getSelectionModel().getSelectedIndex() < 0 || pswUsuarioSenha.getText().length() == 0);
    }

    void preencheVariaveisUsuario(TabColaboradorVO colaboradorVO) {
        USUARIO_LOGADO_ID = String.valueOf(colaboradorVO.getId());
        USUARIO_LOGADO_NOME = colaboradorVO.getNome();
        USUARIO_LOGADO_APELIDO = colaboradorVO.getApelido();
        DATA_HORA = LocalDateTime.now();
        DATA_HORA_STR = DATA_HORA.format(DTF_DATAHORA);
        USUARIO_LOGADO_DATA = LocalDate.now();
        USUARIO_LOGADO_DATA_STR = USUARIO_LOGADO_DATA.format(DTF_DATA);
        USUARIO_LOGADO_HORA = LocalTime.now();
        USUARIO_LOGADO_HORA_STR = USUARIO_LOGADO_HORA.format(DTF_HORA);
    }

}
