package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.service.ServiceCampoPersonalizado;
import br.com.cafeperfeito.sidtmcafe.service.ServiceComandoTecladoMouse;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroProduto;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCadastroProduto extends ServiceVariavelSistema implements Initializable, ModelController {

    public AnchorPane painelViewCadastroProduto;
    public TitledPane tpnCadastroProduto;
    public JFXTextField txtPesquisaProduto;
    public JFXTreeTableView ttvProduto;
    public Label lblRegistrosLocalizados;
    public TitledPane tpnDadoCadastral;
    public JFXTextField txtCodigo;
    public JFXTextField txtDescricao;
    public JFXTextField txtPeso;
    public JFXComboBox cboUnidadeComercial;
    public JFXComboBox cboSituacaoSistema;
    public JFXTextField txtPrecoFabrica;
    public JFXTextField txtMargem;
    public JFXTextField txtPrecoConsumidor;
    public JFXTextField txtVarejo;
    public JFXTextField txtComissaoPorc;
    public JFXTextField txtLucroBruto;
    public JFXTextField txtPrecoUltimoFrete;
    public JFXTextField txtComissaoReal;
    public JFXTextField txtLucroLiquido;
    public JFXTextField txtLucratividade;
    public Label lblDataCadastro;
    public Label lblDataCadastroDiff;
    public Label lblDataAtualizacao;
    public Label lblDataAtualizacaoDiff;
    public JFXListView listCodigoBarras;
    public JFXComboBox cboFiscalCestNcm;
    public JFXTextField txtFiscalGenero;
    public JFXTextField txtFiscalNcm;
    public JFXTextField txtFiscalCest;
    public JFXComboBox cboFiscalOrigem;
    public JFXComboBox cboFiscalIcms;
    public JFXComboBox cboFiscalPis;
    public JFXComboBox cboFiscalCofins;

    @Override
    public void fechar() {
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getTabs().remove(ViewCadastroProduto.getTabCadastroProduto());
        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroProduto);
    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjeros() {

        ServiceCampoPersonalizado.fieldClear(painelViewCadastroProduto);
    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTeclar() {
        eventHandlerCadastroProduto = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() < 0)
                    return;
                if (!ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().getText().equals(ViewCadastroProduto.getTituloJanela()))
                    return;
                switch (event.getCode()) {
                    case F1:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        setStatusFormulario("incluir");
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        setStatusFormulario("pesquisa");
                        break;
                    case F3:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        switch (getStatusBarTecla()) {
                            case "incluir":
                                break;
                            case "editar":
                                break;
                        }
                        setStatusFormulario("pesquisa");
                        break;
                    case F4:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        setStatusFormulario("editar");
                        break;
                    case F6:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        break;
                    case F7:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        txtPesquisaProduto.requestFocus();
                        break;
                    case F8:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        break;
                    case F12:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        fechar();
                        break;
                    case HELP:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        break;
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        break;
                }
            }
        };

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroProduto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjeros();
        fatorarObjetos();
        escutarTeclar();

        setStatusBarTecla("pesquisa");
        Platform.runLater(() -> {
            ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7));
        });
    }

    EventHandler<KeyEvent> eventHandlerCadastroProduto;
    int qtdRegistrosLocalizados = 0;
    int indexObservableProduto = 0;
    String tituloTab = ViewCadastroProduto.getTituloJanela();
    String statusFormulario, statusBarTecla;

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F3-Excluir]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

    public int getQtdRegistrosLocalizados() {
        return qtdRegistrosLocalizados;
    }

    public void setQtdRegistrosLocalizados(int qtdRegistrosLocalizados) {
        this.qtdRegistrosLocalizados = qtdRegistrosLocalizados;
    }

    public String getTituloTab() {
        return tituloTab;
    }

    public void setTituloTab(String tituloTab) {
        this.tituloTab = tituloTab;
    }

    public String getStatusFormulario() {
        return statusFormulario;
    }

    public void setStatusFormulario(String statusFormulario) {
        this.statusFormulario = statusFormulario;
        atualizaQtdRegistroLocalizado();
        setStatusBarTecla(statusFormulario);
    }

    public String getStatusBarTecla() {
        return statusBarTecla;
    }

    public void setStatusBarTecla(String statusFormulario) {
        switch (statusFormulario.toLowerCase()) {
            case "incluir":
                this.statusBarTecla = STATUS_BAR_TECLA_INCLUIR;
                break;
            case "editar":
                this.statusBarTecla = STATUS_BAR_TECLA_EDITAR;
                break;
            case "pesquisa":
                this.statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }

    void atualizaQtdRegistroLocalizado() {
        lblRegistrosLocalizados.setText("[" + getStatusFormulario() + "] " + getQtdRegistrosLocalizados() + " registros(s) localizado(s).");
    }
}
