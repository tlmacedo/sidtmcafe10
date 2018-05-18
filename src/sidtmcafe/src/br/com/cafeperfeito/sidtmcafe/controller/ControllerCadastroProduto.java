package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.ServiceCampoPersonalizado;
import br.com.cafeperfeito.sidtmcafe.service.ServiceComandoTecladoMouse;
import br.com.cafeperfeito.sidtmcafe.service.ServiceSegundoPlano;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroProduto;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getTabs().remove(ViewCadastroProduto.getTab());
        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroProduto);
    }

    @Override
    public void criarObjetos() {
        listaTarefa.add(new Pair<>("criarTabelaProduto", "criando tabela de produto"));
    }

    @Override
    public void preencherObjetos() {
        listaTarefa.add(new Pair("carregarListaProduto", "carregando lista de produtos"));
        listaTarefa.add(new Pair("preencherCboUnidadeComercial", "preenchendo dados unidade comercial"));
        listaTarefa.add(new Pair("preencherCboSituacaoSistema", "preenchendo situaão no istema"));
        listaTarefa.add(new Pair("preencherCboFiscalOrigem", "preenchendo dados fiscais de Origem"));
        listaTarefa.add(new Pair("preencherCboFiscalIcms", "preenchendo dados fiscal ICMS"));
        listaTarefa.add(new Pair("preencherCboFiscalPis", "preenchendo dados fiscal PIS"));
        listaTarefa.add(new Pair("preencherCboFiscalCofins", "preenchendo dados fiscal COFINS"));

        listaTarefa.add(new Pair("preencherTabelaProduto", "preenchendo tabela produto"));

    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTecla() {
        eventHandlerCadastroProduto = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() < 0)
                    return;
                if (!ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().getText().equals(getTituloTab()))
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
        setTituloTab(ViewCadastroProduto.getTituloJanela());
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();

        new ServiceSegundoPlano().tarefaAbreCadastroProduto(this, listaTarefa);
        ServiceCampoPersonalizado.fieldMask(painelViewCadastroProduto);
        ServiceCampoPersonalizado.fieldClear(painelViewCadastroProduto);

        Platform.runLater(() -> {
            setStatusFormulario("pesquisa");
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

    List<Pair<String, String>> listaTarefa = new ArrayList<>();
    ObservableList<TabProdutoVO> tabProdutoVOObservableList;
    FilteredList<TabProdutoVO> tabProdutoVOFilteredList;
    TabProdutoVO tabProdutoVO;
    List<TabProdutoEanVO> tabProdutoEanVOList;
    List<TabProdutoEanVO> deletadoTabProdutoEanVOList;

    List<SisUnidadeComercialVO> sisUnidadeComercialVOList;
    List<SisSituacaoSistemaVO> sisSituacaoSistemaVOList;
    List<FiscalCSTOrigemVO> fiscalCSTOrigemVOList;
    List<FiscalICMSVO> fiscalICMSVOList;
    List<FiscalPISCOFINSVO> fiscalPISVOList;
    List<FiscalPISCOFINSVO> fiscalCOFINSVOList;

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
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
                clearFieldDadoCadastral((AnchorPane) tpnDadoCadastral.getContent());
                txtCodigo.requestFocus();
                this.statusBarTecla = STATUS_BAR_TECLA_INCLUIR;
                break;
            case "editar":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
                txtCodigo.requestFocus();
                this.statusBarTecla = STATUS_BAR_TECLA_EDITAR;
                break;
            case "pesquisa":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), false);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), true);
                clearFieldDadoCadastral((AnchorPane) tpnDadoCadastral.getContent());
                txtPesquisaProduto.requestFocus();
                this.statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }

    void atualizaQtdRegistroLocalizado() {
        lblRegistrosLocalizados.setText("[" + getStatusFormulario() + "] " + getQtdRegistrosLocalizados() + " registros(s) localizado(s).");
    }

    void clearFieldDadoCadastral(AnchorPane anchorPane) {
        ServiceCampoPersonalizado.fieldClear(anchorPane);
    }

    public void carregarListaProduto() {
        tabProdutoVOObservableList = FXCollections.observableArrayList(new TabProdutoDAO().getTabProdutoVOList());
        tabProdutoVOFilteredList = new FilteredList<>(tabProdutoVOObservableList, produto -> true);
    }

    public void preencherCboUnidadeComercial() {
        cboUnidadeComercial.getItems().clear();
        if ((sisUnidadeComercialVOList = new ArrayList<>(new SisUnidadeComercialDAO().getSisUnidadeComercialVOList())) == null)
            return;
        cboUnidadeComercial.getItems().setAll(sisUnidadeComercialVOList);
    }

    public void preencherCboSituacaoSistema() {
        cboSituacaoSistema.getItems().clear();
        if ((sisSituacaoSistemaVOList = new ArrayList<>(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVOList())) == null)
            return;
        cboSituacaoSistema.getItems().setAll(sisSituacaoSistemaVOList);
    }

    public void preencherCboFiscalOrigem() {
        cboFiscalOrigem.getItems().clear();
        if ((fiscalCSTOrigemVOList = new ArrayList<FiscalCSTOrigemVO>(new FiscalCSTOrigemDAO().getFiscalCSTOrigemVOList())) == null)
            return;
        cboFiscalOrigem.getItems().setAll(fiscalCSTOrigemVOList);
    }

    public void preencherCboFiscalIcms() {
        cboFiscalIcms.getItems().clear();
        if ((fiscalICMSVOList = new ArrayList<FiscalICMSVO>(new FiscalICMSDAO().getFiscalICMSVOList())) == null)
            return;
        cboFiscalIcms.getItems().setAll(fiscalICMSVOList);
    }

    public void preencherCboFiscalPis() {
        cboFiscalPis.getItems().clear();
        if ((fiscalPISVOList = new ArrayList<FiscalPISCOFINSVO>(new FiscalPISCOFINSDAO().getFiscalPISCOFINSVOList())) == null)
            return;
        cboFiscalPis.getItems().setAll(fiscalPISVOList);
    }

    public void preencherCboFiscalCofins() {
        cboFiscalCofins.getItems().clear();
        if ((fiscalCOFINSVOList = new ArrayList<FiscalPISCOFINSVO>(new FiscalPISCOFINSDAO().getFiscalPISCOFINSVOList())) == null)
            return;
        cboFiscalCofins.getItems().setAll(fiscalCOFINSVOList);
    }

    void pesquisaProduto() {
        String busca;
        if ((busca = txtPesquisaProduto.getText().toLowerCase().trim()).length() > 0)
            tabProdutoVOFilteredList.setPredicate(produto -> {
                if (produto.getCodigo().toLowerCase().contains(busca)) return true;
                return false;
            });
        preencheTabelaProduto();
    }

    public void preencheTabelaProduto() {

    }

}
