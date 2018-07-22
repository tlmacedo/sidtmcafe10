package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.model.TabModel;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.*;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroProduto;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerCadastroProduto extends ServiceVariavelSistema implements Initializable, ModelController {

    ObservableList<TabProdutoEanVO> listProdutoEanVOObservableList = FXCollections.observableArrayList();
    public AnchorPane painelViewCadastroProduto;
    public TitledPane tpnCadastroProduto;
    public JFXTextField txtPesquisaProduto;
    public TreeTableView<TabProdutoVO> ttvProduto;
    public Label lblRegistrosLocalizados;
    public TitledPane tpnDadoCadastral;
    public JFXTextField txtCodigo;
    public JFXTextField txtDescricao;
    public JFXTextField txtPeso;
    public JFXComboBox<SisUnidadeComercialVO> cboUnidadeComercial;
    public JFXComboBox<SisSituacaoSistemaVO> cboSituacaoSistema;
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
    public JFXListView<TabProdutoEanVO> listCodigoBarras;
    public JFXComboBox<FiscalCestNcmVO> cboFiscalCestNcm;
    public JFXTextField txtFiscalGenero;
    public JFXTextField txtFiscalNcm;
    public JFXTextField txtFiscalCest;
    public JFXComboBox<FiscalCstOrigemVO> cboFiscalOrigem;
    public JFXComboBox<FiscalIcmsVO> cboFiscalIcms;
    public JFXComboBox<FiscalPisCofinsVO> cboFiscalPis;
    public JFXComboBox<FiscalPisCofinsVO> cboFiscalCofins;

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
        listaTarefa.add(new Pair("preencherCboUnidadeComercial", "preenchendo dados unidade comercial"));
        listaTarefa.add(new Pair("preencherCboSituacaoSistema", "preenchendo situaão no istema"));
        listaTarefa.add(new Pair("preencherCboFiscalOrigem", "preenchendo dados fiscais de Origem"));
        listaTarefa.add(new Pair("preencherCboFiscalIcms", "preenchendo dados fiscal ICMS"));
        listaTarefa.add(new Pair("preencherCboFiscalPis", "preenchendo dados fiscal PIS"));
        listaTarefa.add(new Pair("preencherCboFiscalCofins", "preenchendo dados fiscal COFINS"));
        listaTarefa.add(new Pair("carregarListaProduto", "carregando lista de produtos"));

        listaTarefa.add(new Pair("preencherTabelaProduto", "preenchendo tabela produto"));

        new ServiceSegundoPlano().tarefaAbreCadastroEmpresa(getTaskCadastroProduto(), listaTarefa.size());

//        formatCnpj = new ServiceFormatarDado();
//        formatCnpj.maskField(txtCNPJ, ServiceFormatarDado.gerarMascara("cnpj", 0, "#"));
//        formatIe = new ServiceFormatarDado();
//        formatIe.maskField(txtIE, ServiceFormatarDado.gerarMascara("ie", 0, "#"));
    }

    @Override
    public void fatorarObjetos() {

    }

    @SuppressWarnings("Duplicates")
    @Override
    public void escutarTecla() {
        ttvProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //setEmpresaVO(newValue.getValue());
            }
        });

        ttvProduto.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && statusFormulario.toLowerCase().equals("pesquisa") && ttvProduto.getSelectionModel().getSelectedItem() != null) {
                //setEmpresaVO(ttvProduto.getSelectionModel().getSelectedItem().getValue());
            }
        });

        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 0 || newValue.intValue() == oldValue.intValue()) return;
            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(tituloTab))
                ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
        });

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
//                        setEmpresaVO(new TabEmpresaVO(1));
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
//                        listEndereco.getSelectionModel().selectFirst();
//                        if (!validarDados()) break;
//                        if (buscaDuplicidade()) break;
//                        if (salvarEmpresa()) {
//                            switch (getStatusFormulario().toLowerCase()) {
//                                case "incluir":
//                                    empresaVOObservableList.add(new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
//                                    break;
//                                case "editar":
//                                    empresaVOObservableList.set(empresaVOObservableList.indexOf(ttvProduto.getSelectionModel().getSelectedItem().getValue()),
//                                            new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
//                                    break;
//                            }
                        setStatusFormulario("pesquisa");
//                        }
                        break;
                    case F3:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
//                        switch (getStatusFormulario().toLowerCase()) {
//                            case "incluir":
//                                if (new ServiceAlertMensagem("Cancelar inclusão", USUARIO_LOGADO_APELIDO
//                                        + ", deseja cancelar inclusão no cadastro de empresa?",
//                                        "ic_cadastro_empresas_white_24dp.png").getRetornoAlert_YES_NO().get() == ButtonType.NO)
//                                    return;
//                                break;
//                            case "editar":
//                                if (new ServiceAlertMensagem("Cancelar edição", USUARIO_LOGADO_APELIDO
//                                        + ", deseja cancelar edição do cadastro de empresa?",
//                                        "ic_cadastro_empresas_white_24dp.png").getRetornoAlert_YES_NO().get() == ButtonType.NO)
//                                    return;
//                                setEmpresaVO(ttvProduto.getSelectionModel().getSelectedItem().getValue());
//                                break;
//                            default:
//                                return;
//                        }
                        setStatusFormulario("pesquisa");
                        break;
                    case F4:
                        if (!getStatusBarTecla().contains(event.getCode().toString()) || !(ttvProduto.getSelectionModel().getSelectedIndex() >= 0))
                            break;
                        setStatusFormulario("editar");
                        break;
                    case F6:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa") || !(event.isShiftDown())) break;
//                        keyShiftF6();
                        break;
                    case F7:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        txtPesquisaProduto.requestFocus();
                        break;
                    case F8:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
//                        cboFiltroPesquisa.requestFocus();
                        break;
                    case F12:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        fechar();
                        break;
                    case HELP:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        keyInsert();
                        break;
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        keyDelete();
                        break;
                }
                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() > 0)
                    ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().setOnCloseRequest(event1 -> {
                        if (!getStatusFormulario().toLowerCase().equals("pesquisa")) {
                            event1.consume();
                        }
                    });
            }
        };

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroProduto);

        txtPesquisaProduto.textProperty().addListener((observable, oldValue, newValue) -> {
//            pesquisaEmpresa();
        });

        txtPesquisaProduto.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvProduto.requestFocus();
            ttvProduto.getSelectionModel().selectFirst();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTituloTab(ViewCadastroProduto.getTituloJanela());
        criarObjetos();
        preencherObjetos();
        escutarTecla();
        fatorarObjetos();
        setStatusFormulario("Pesquisa");
        ServiceCampoPersonalizado.fieldMask(painelViewCadastroProduto);
        Platform.runLater(() -> ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7)));
    }

    ObservableList<TabProdutoVO> produtoVOObservableList;
    FilteredList<TabProdutoVO> produtoVOFilteredList;
    TabProdutoVO produtoVO = new TabProdutoVO();

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

    EventHandler<KeyEvent> eventHandlerCadastroProduto;
    List<Pair> listaTarefa = new ArrayList<>();
    ServiceFormatarDado formatCnpj, formatIe;
    ServiceAlertMensagem alertMensagem;
    String statusFormulario, statusBarTecla, tituloTab = ViewCadastroProduto.getTituloJanela();
    List<SisUnidadeComercialVO> sisUnidadeComercialVOList;
    List<SisSituacaoSistemaVO> sisSituacaoSistemaVOList;
    List<FiscalCstOrigemVO> fiscalCstOrigemVOList;
    List<FiscalIcmsVO> fiscalIcmsVOList;
    List<FiscalPisCofinsVO> fiscalPisVOList;
    List<FiscalPisCofinsVO> fiscalCofinsVOList;

    Task getTaskCadastroProduto() {
        int qtdTarefas = listaTarefa.size();
        Task<Void> voidTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("carregando");
                for (Pair tarefaAtual : listaTarefa) {
                    updateProgress(listaTarefa.indexOf(tarefaAtual), qtdTarefas);
                    Thread.sleep(200);
                    updateMessage(tarefaAtual.getValue().toString());
                    switch (tarefaAtual.getKey().toString()) {
                        case "criarTabelaProduto":
                            TabModel.tabelaProduto();
                            break;
                        case "preencherCboUnidadeComercial":
                            preencherCboUnidadeComercial();
                            break;
                        case "preencherCboSituacaoSistema":
                            preencherCboSituacaoSistema();
                            break;
                        case "preencherCboFiscalOrigem":
                            preencherCboFiscalOrigem();
                            break;
                        case "preencherCboFiscalIcms":
                            preencherCboFiscalIcms();
                            break;
                        case "preencherCboFiscalPis":
                            preencherCboFiscalPis();
                            break;
                        case "preencherCboFiscalCofins":
                            preencherCboFiscalCofins();
                            break;
                        case "carregarListaProduto":
                            carregarListaProduto();
                            break;
                        case "preencherTabelaProduto":
//                            preencherTabelaProduto();
                            break;
                    }
                }
                updateProgress(qtdTarefas, qtdTarefas);
                return null;
            }
        };
        return voidTask;
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
        setStatusBarTecla(statusFormulario);
        atualizaQtdRegistroLocalizado();
    }

    void atualizaQtdRegistroLocalizado() {
        int qtd = produtoVOFilteredList.size();
        lblRegistrosLocalizados.setText(String.format("[%s] %d registro%s localizado%s.", getStatusFormulario(), qtd,
                qtd > 1 ? "s" : "", qtd > 1 ? "s" : ""));
    }

    public String getStatusBarTecla() {
        return statusBarTecla;
    }

    public void setStatusBarTecla(String statusFormulario) {
        switch (statusFormulario.toLowerCase()) {
            case "incluir":
                txtPesquisaProduto.setText("");
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoCadastral.getContent());
                txtCodigo.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_INCLUIR;
                break;
            case "editar":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
//                try {
//                    setEmpresaVO(getEmpresaVO().clone());
//                } catch (CloneNotSupportedException e) {
//                    e.printStackTrace();
//                }
                txtCodigo.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_EDITAR;
                break;
            case "pesquisa":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), false);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), true);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoCadastral.getContent());
                txtPesquisaProduto.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }

    public void preencherCboUnidadeComercial() {
        cboUnidadeComercial.getItems().setAll(new ArrayList<>(new SisUnidadeComercialDAO().getSisUnidadeComercialVOList()));
        cboUnidadeComercial.getSelectionModel().selectFirst();
    }

    public void preencherCboSituacaoSistema() {
        cboSituacaoSistema.getItems().setAll(new ArrayList<>(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVOList()));
        cboSituacaoSistema.getSelectionModel().selectFirst();
    }

    public void preencherCboFiscalOrigem() {
        cboFiscalOrigem.getItems().setAll(new ArrayList<>(new FiscalCstOrigemDAO().getFiscalCstOrigemVOList()));
        cboFiscalOrigem.getSelectionModel().selectFirst();
    }

    public void preencherCboFiscalIcms() {
        cboFiscalIcms.getItems().setAll(new ArrayList<>(new FiscalIcmsDAO().getFiscalIcmsVOList()));
        cboFiscalIcms.getSelectionModel().selectFirst();
    }

    public void preencherCboFiscalPis() {
        cboFiscalPis.getItems().setAll(new ArrayList<>(new FiscalPisCofinsDAO().getFiscalPisCofinsVOList()));
        cboFiscalPis.getSelectionModel().selectFirst();
    }

    public void preencherCboFiscalCofins() {
        cboFiscalCofins.getItems().setAll(new ArrayList<>(new FiscalPisCofinsDAO().getFiscalPisCofinsVOList()));
        cboFiscalCofins.getSelectionModel().selectFirst();
    }

    void carregarListaProduto() {
        produtoVOFilteredList = new FilteredList<>(
                produtoVOObservableList = FXCollections.observableArrayList(new TabProdutoDAO().getTabProdutoVOList())
        );
    }

    void preencherTabelaProduto() {
        if (produtoVOFilteredList == null) {
            carregarListaProduto();
            pesquisaProduto();
        }
        final TreeItem<TabProdutoVO> root = new RecursiveTreeItem<TabProdutoVO>(produtoVOFilteredList, RecursiveTreeObject::getChildren);
        ttvProduto.getColumns().setAll(TabModel.getColunaIdProduto(), TabModel.getColunaCodigo(),
                TabModel.getColunaDescricao(), TabModel.getColunaUndCom(), TabModel.getColunaVarejo(),
                TabModel.getColunaPrecoFabrica(), TabModel.getColunaPrecoConsumidor(),
                TabModel.getColunaSituacaoSistema(), TabModel.getColunaQtdEstoque());
        ttvProduto.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ttvProduto.setRoot(root);
        ttvProduto.setShowRoot(false);
    }

    void pesquisaProduto() {
        String busca = txtPesquisaProduto.getText().toLowerCase().trim();

        produtoVOFilteredList.setPredicate(produto -> {
            if (produto.getCodigo().contains(busca)) return true;
            if (produto.getDescricao().contains(busca)) return true;
            if (produto.getFiscalNcm().contains(busca)) return true;
//            if (produto.getFiscalCestNcmVO().toLowerCase().contains(busca)) return true;
//            if (produto.getTabEnderecoVOList().stream()
//                    .filter(end -> end.getCep().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabEnderecoVOList().stream()
//                    .filter(end -> end.getLogradouro().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabEnderecoVOList().stream()
//                    .filter(end -> end.getNumero().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabEnderecoVOList().stream()
//                    .filter(end -> end.getComplemento().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabEnderecoVOList().stream()
//                    .filter(end -> end.getBairro().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabEnderecoVOList().stream()
//                    .filter(end -> end.getSisMunicipioVO().getDescricao().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabEnderecoVOList().stream()
//                    .filter(end -> end.getSisMunicipioVO().getUfVO().getSigla().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//
//            if (produto.getTabEmailHomePageVOList().stream()
//                    .filter(mail -> mail.getDescricao().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//
//            if (produto.getTabTelefoneVOList().stream()
//                    .filter(tel -> tel.getDescricao().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabTelefoneVOList().stream()
//                    .filter(tel -> tel.getSisTelefoneOperadoraVO().getDescricao().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//
//            if (produto.getTabContatoVOList().stream()
//                    .filter(cont -> cont.getDescricao().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            if (produto.getTabContatoVOList().stream()
//                    .filter(cont -> cont.getTabEmailHomePageVOList().stream()
//                            .filter(contMail -> contMail.getDescricao().toLowerCase().contains(busca))
//                            .count() > 0).findFirst().orElse(null) != null) return true;
//            if (produto.getTabContatoVOList().stream()
//                    .filter(cont -> cont.getTabTelefoneVOList().stream()
//                            .filter(contTel -> contTel.getDescricao().toLowerCase().contains(busca))
//                            .count() > 0).findFirst().orElse(null) != null) return true;
//            if (produto.getTabContatoVOList().stream()
//                    .filter(cont -> cont.getTabTelefoneVOList().stream()
//                            .filter(contTel -> contTel.getSisTelefoneOperadoraVO().getDescricao().toLowerCase().contains(busca))
//                            .count() > 0).findFirst().orElse(null) != null) return true;
            return false;
        });
        preencherTabelaProduto();
    }

}
