package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.service.ServiceTremeView;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewLogin;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerLogin extends ServiceVariavelSistema implements Initializable, ModelController, Constants {
    public AnchorPane painelViewLogin;
    public JFXComboBox cboUsuarioLogin;
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
    public void preencherObjeros() {

    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTeclar() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjeros();
        fatorarObjetos();
        escutarTeclar();
        Platform.runLater(() -> Locale.setDefault(LOCALE_MY));
    }

    void tremeLogin() {
        new Thread(() -> new ServiceTremeView().setStage(ViewLogin.getStage())).start();
    }
}
