package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
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
import javafx.beans.value.ChangeListener;
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
import javafx.util.Callback;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerCadastroProduto extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

    ObservableList<TabProduto_CodBarraVO> listCodBarraVOObservableList = FXCollections.observableArrayList();
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
    public JFXTextField txtPrecoVenda;
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
    public JFXListView<TabProduto_CodBarraVO> listCodigoBarra;
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
        listaTarefa.add(new Pair("preencherCboFiscalCestNcm", "preenchendo dados fiscais de Ncm e Cest"));
        listaTarefa.add(new Pair("preencherCboFiscalOrigem", "preenchendo dados fiscais de Origem"));
        listaTarefa.add(new Pair("preencherCboFiscalIcms", "preenchendo dados fiscal ICMS"));
        listaTarefa.add(new Pair("preencherCboFiscalPis", "preenchendo dados fiscal PIS"));
        listaTarefa.add(new Pair("preencherCboFiscalCofins", "preenchendo dados fiscal COFINS"));
        listaTarefa.add(new Pair("carregarListaProduto", "carregando lista de produtos"));

        listaTarefa.add(new Pair("preencherTabelaProduto", "preenchendo tabela produto"));

        new ServiceSegundoPlano().tarefaAbreCadastroProduto(getTaskCadastroProduto(), listaTarefa.size());
    }

    @Override
    public void fatorarObjetos() {
        cboFiscalCestNcm.setCellFactory(new Callback<ListView<FiscalCestNcmVO>, ListCell<FiscalCestNcmVO>>() {
            @Override
            public ListCell<FiscalCestNcmVO> call(ListView<FiscalCestNcmVO> param) {
                final ListCell<FiscalCestNcmVO> cell = new ListCell<FiscalCestNcmVO>() {
                    @Override
                    protected void updateItem(FiscalCestNcmVO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) setText(null);
                        else {
                            String novoTexto = "";
                            for (String det : item.getDetalheCestNcm().split(";"))
                                if (novoTexto == "") novoTexto += det;
                                else novoTexto += "\r\n" + det;
                            setText(novoTexto);
                        }
                    }
                };
                return cell;
            }
        });
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void escutarTecla() {
        ttvProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            setProdutoVO(newValue.getValue());
        });

        ttvProduto.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && statusFormulario.toLowerCase().equals("pesquisa") && ttvProduto.getSelectionModel().getSelectedItem() != null)
                setProdutoVO(ttvProduto.getSelectionModel().getSelectedItem().getValue());
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
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        setStatusFormulario("incluir");
                        setProdutoVO(new TabProdutoVO(0));
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        if (!validarDadosProduto()) break;
                        if (buscaDuplicidadeCode(getProdutoVO().getCodigo(), false)) break;
                        if (salvarProduto()) {
                            switch (getStatusFormulario().toLowerCase()) {
                                case "incluir":
                                    produtoVOObservableList.add(new TabProdutoDAO().getTabProdutoVO(getProdutoVO().getId()));
                                    break;
                                case "editar":
                                    produtoVOObservableList.set(produtoVOObservableList.indexOf(ttvProduto.getSelectionModel().getSelectedItem().getValue()),
                                            new TabProdutoDAO().getTabProdutoVO(getProdutoVO().getId()));
                                    break;
                            }
                            setStatusFormulario("pesquisa");
                        }
                        break;
                    case F3:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        boolean statusIncluir = getStatusFormulario().toLowerCase().equals("incluir");
                        alertMensagem = new ServiceAlertMensagem();
                        alertMensagem.setStrIco("ic_cadastro_produto_cancel_24dp");
                        alertMensagem.setCabecalho(String.format("Cancelar %s", statusIncluir ? "inclusão" : "edição"));
                        alertMensagem.setPromptText(String.format("%s, deseja cancelar %s do cadastro de produto?",
                                USUARIO_LOGADO_APELIDO, statusIncluir ? "inclusão" : "edição"));
                        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
                        if (!statusIncluir)
                            setProdutoVO(ttvProduto.getSelectionModel().getSelectedItem().getValue());
                        setStatusFormulario("pesquisa");
                        break;
                    case F4:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        if (ttvProduto.getSelectionModel().getSelectedIndex() < 0)
                            break;
                        setStatusFormulario("editar");
                        break;
                    case F6:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        if (getStatusFormulario().toLowerCase().equals("pesquisa") || !(event.isShiftDown())) break;
                        keyShiftF6();
                        break;
                    case F7:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        txtPesquisaProduto.requestFocus();
                        break;
                    case F8:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        //cboFiltroPesquisa.requestFocus();
                        break;
                    case F12:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        fechar();
                        break;
                    case HELP:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        keyInsert();
                        break;
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        keyDelete();
                        break;
                    case B:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        if (CODE_KEY_CTRL_ALT_B.match(event) || CHAR_KEY_CTRL_ALT_B.match(event))
                            addCodeBar();
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
            pesquisaProduto();
        });

        txtPesquisaProduto.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvProduto.requestFocus();
            ttvProduto.getSelectionModel().selectFirst();
        });

        cboFiscalCestNcm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            txtFiscalNcm.setText(ServiceFormatarDado.getValorFormatado(newValue.getNcm(), "ncm"));
            txtFiscalCest.setText(ServiceFormatarDado.getValorFormatado(newValue.getCest(), "cest"));
        });

        listCodBarraVOObservableList.addListener((ListChangeListener) c -> {
            listCodigoBarra.setItems(listCodBarraVOObservableList.stream()
                    .filter(codBarra -> codBarra.getId() >= 0)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        txtFiscalNcm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            cboFiscalCestNcm.setItems(new FiscalCestNcmDAO()
                    .getFiscalCestNcmVOList(newValue.replaceAll("\\D", ""))
                    .stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        txtPrecoFabrica.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!txtPrecoFabrica.isFocused()) return;
                vlrConsumidor();
                vlrLucroBruto();
            }
        });

        txtMargem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!txtMargem.isFocused()) {
                    return;
                }
                vlrConsumidor();
                vlrLucroBruto();
            }
        });

        txtPrecoVenda.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!txtPrecoVenda.isFocused()) return;
                vlrMargem();
                vlrLucroBruto();
            }
        });

        txtComissaoPorc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!txtComissaoPorc.isFocused()) return;
                vlrComissaoReal();
                vlrLucroBruto();
            }
        });

        txtPrecoUltimoFrete.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!txtPrecoUltimoFrete.isFocused()) return;
                vlrLucroBruto();
            }
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
    ServiceFormatarDado formatPeso;
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
                        case "preencherCboFiscalCestNcm":
                            preencherCboFiscalCestNcm();
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
                            preencherTabelaProduto();
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
                try {
                    setProdutoVO(getProdutoVO().clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
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

    public void preencherCboFiscalCestNcm() {
        cboFiscalCestNcm.getItems().setAll(new ArrayList<>(new FiscalCestNcmDAO().getFiscalCestNcmVOList(null)));
        cboFiscalCestNcm.getSelectionModel().selectFirst();
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
                TabModel.getColunaPrecoFabrica(), TabModel.getColunaPrecoVenda(),
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
            if (produto.getFiscalCestNcmVO().getNcm().contains(busca)) return true;
            if (produto.getFiscalCestNcmVO().getCest().contains(busca)) return true;
            if (produto.getCodBarraVOList().stream()
                    .filter(codBarra -> codBarra.getCodBarra().contains(busca))
                    .findFirst().orElse(null) != null) return true;
            return false;
        });
        preencherTabelaProduto();
    }

    public TabProdutoVO getProdutoVO() {
        return produtoVO;
    }

    public void setProdutoVO(TabProdutoVO produto) {
        if (produto == null)
            produto = new TabProdutoVO();
        produtoVO = produto;
        exibirDadosProduto();
    }

    void exibirDadosProduto() {
        txtCodigo.setText(getProdutoVO().getCodigo());
        txtDescricao.setText(getProdutoVO().getDescricao());
        txtPeso.setText(ServiceFormatarDado.getValorFormatado(String.valueOf(getProdutoVO().getPeso()), "peso3"));
        cboUnidadeComercial.getSelectionModel().select(getProdutoVO().getSisUnidadeComercialVO());
        cboSituacaoSistema.getSelectionModel().select(getProdutoVO().getSisSituacaoSistemaVO());
        txtPrecoFabrica.setText(ServiceFormatarDado.getValorFormatado(String.valueOf(getProdutoVO().getPrecoFabrica()), "moeda2"));
        txtPrecoVenda.setText(ServiceFormatarDado.getValorFormatado(String.valueOf(getProdutoVO().getPrecoVenda()), "moeda2"));
        txtVarejo.setText(String.valueOf(getProdutoVO().getVarejo()));
        txtPrecoUltimoFrete.setText(ServiceFormatarDado.getValorFormatado(String.valueOf(getProdutoVO().getPrecoUltimoFrete()), "moeda2"));
        txtComissaoPorc.setText(ServiceFormatarDado.getValorFormatado(String.valueOf(getProdutoVO().getComissao()), "moeda2"));
        if (getProdutoVO().getFiscalCestNcm_id() == 0)
            cboFiscalCestNcm.getSelectionModel().select(-1);
        else
            cboFiscalCestNcm.getSelectionModel().select(cboFiscalCestNcm.getItems().stream()
                    .filter(cestNcm -> cestNcm.getId() == getProdutoVO().getFiscalCestNcm_id())
                    .findFirst().orElse(null));
        txtFiscalNcm.setText(getProdutoVO().getNcm());
        txtFiscalCest.setText(getProdutoVO().getCest());

        cboFiscalOrigem.getSelectionModel().select(getProdutoVO().getFiscalCstOrigemVO());
        cboFiscalIcms.getSelectionModel().select(getProdutoVO().getFiscalIcmsVO());
        cboFiscalPis.getSelectionModel().select(getProdutoVO().getFiscalPisVO());
        cboFiscalCofins.getSelectionModel().select(getProdutoVO().getFiscalCofinsVO());
        txtFiscalGenero.setText(getProdutoVO().getNfeGenero());
        listCodBarraVOObservableList.setAll(getProdutoVO().getCodBarraVOList());
        lblDataCadastro.setText(String.format("data de cadastro%s",
                getProdutoVO().getDataCadastro() == null ? "" : String.format(": %s [%s]", DTF_DATAHORA.format(getProdutoVO().getDataCadastro().toLocalDateTime()),
                        getProdutoVO().getUsuarioCadastroVO())));
        lblDataCadastroDiff.setText(String.format("tempo de cadastro%s",
                getProdutoVO().getDataCadastro() == null ? "" : String.format(": %s", ServiceDataHora.getIntervaloData(getProdutoVO().getDataCadastro().toLocalDateTime().toLocalDate(), null))));
        lblDataAtualizacao.setText(String.format("data de atualização%s",
                getProdutoVO().getDataAtualizacao() == null ? "" : String.format(": %s [%s]", DTF_DATAHORA.format(getProdutoVO().getDataAtualizacao().toLocalDateTime()),
                        getProdutoVO().getUsuarioAtualizacaoVO())));
        lblDataAtualizacaoDiff.setText(String.format("tempo de atualização%s",
                getProdutoVO().getDataAtualizacao() == null ? "" : String.format(": %s", ServiceDataHora.getIntervaloData(getProdutoVO().getDataAtualizacao().toLocalDateTime().toLocalDate(), null))));
    }

    void keyShiftF6() {
        if (listCodigoBarra.isFocused())
            updateCodeBar();
    }

    void keyInsert() {
        if (listCodigoBarra.isFocused())
            addCodeBar();
    }

    void keyDelete() {
        if (listCodigoBarra.isFocused())
            delCodeBar();
    }

    void addCodeBar() {
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_barcode_add_24dp");
        alertMensagem.setCabecalho(String.format("Adicionar dados [código de barras]"));
        alertMensagem.setPromptText(String.format("%s, qual o código de barras a ser adicionado para o produto: [%s] ?",
                USUARIO_LOGADO_APELIDO, getProdutoVO()));
        String codBarras;
        if ((codBarras = alertMensagem.getRetornoAlert_TextField(
                ServiceFormatarDado.gerarMascara("barcode", 13, "#"), "")
                .orElse(null)) == null) return;
        if (buscaDuplicidadeCode(codBarras, true)) return;
        String strNcm = "";
        guardarProduto();
        if (!(strNcm = new ServiceConsultaWebServices().getProdutoNcmCest_WsEanCosmos(getProdutoVO(), codBarras)).equals("")) {
            getProdutoVO().setFiscalCestNcmVO(new FiscalCestNcmDAO().getFiscalCestNcmVO(strNcm));
            getProdutoVO().setFiscalCestNcm_id(getProdutoVO().getFiscalCestNcmVO().getId());
        }
        exibirDadosProduto();
        getProdutoVO().getCodBarraVOList().add(new TabProduto_CodBarraVO(codBarras));
        listCodBarraVOObservableList.setAll(getProdutoVO().getCodBarraVOList());
    }

    void delCodeBar() {
        TabProduto_CodBarraVO codBarraVO = null;
        codBarraVO = listCodigoBarra.getSelectionModel().getSelectedItem();
        if (codBarraVO == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_barcode_remove_24dp");
        alertMensagem.setCabecalho(String.format("Deletar dados [código de barras]"));
        alertMensagem.setPromptText(String.format("%s, deseja deletar o código de barras: [%s]\ndo produto: [%s] ?",
                USUARIO_LOGADO_APELIDO, codBarraVO, getProdutoVO()));
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        if (codBarraVO.getId() == 0)
            getProdutoVO().getCodBarraVOList().remove(codBarraVO);
        else
            getProdutoVO().getCodBarraVOList().get(getProdutoVO().getCodBarraVOList().indexOf(codBarraVO))
                    .setId(codBarraVO.getId() * (-1));
        listCodBarraVOObservableList.setAll(getProdutoVO().getCodBarraVOList());
    }

    void updateCodeBar() {
        TabProduto_CodBarraVO codBarraVO;
        codBarraVO = listCodigoBarra.getSelectionModel().getSelectedItem();
        if (codBarraVO == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_barcode_update_24dp");
        alertMensagem.setCabecalho(String.format("Editar dados [código de barras]"));
        alertMensagem.setPromptText(String.format("%s, deseja editar o código de barras: [%s]\ndo produto: [%s] ?",
                USUARIO_LOGADO_APELIDO, codBarraVO, txtDescricao.getText()));
        String codBarras;
        if ((codBarras = alertMensagem.getRetornoAlert_TextField(
                ServiceFormatarDado.gerarMascara("barcode", 13, "#"), codBarraVO.getCodBarra())
                .orElse(null)) == null) return;
        codBarraVO.setCodBarra(codBarras);
        listCodBarraVOObservableList.setAll(getProdutoVO().getCodBarraVOList());
    }

    boolean buscaDuplicidadeCode(String buscaDuplicidade, boolean barCode) {
        TabProdutoVO duplicProduto;
        try {
            if (barCode) {
                if ((duplicProduto = new TabProdutoDAO().getTabProduto_CodBarraVO(buscaDuplicidade)) == null)
                    return false;
            } else {
                if ((duplicProduto = new TabProdutoDAO().getTabProdutoVO(buscaDuplicidade)) == null)
                    return false;
            }
            if (listCodBarraVOObservableList.contains(buscaDuplicidade) || getProdutoVO().getId() != duplicProduto.getId()) {
                alertMensagem = new ServiceAlertMensagem();
                alertMensagem.setCabecalho(String.format("Código%s duplicado", barCode ? " de barras" : ""));
                alertMensagem.setPromptText(String.format("%s, o código%s: [%s] já está cadastrado no sistema para o produto: [%s]!",
                        USUARIO_LOGADO_APELIDO, barCode ? " de barras" : "",
                        buscaDuplicidade, duplicProduto.getDescricao()));
                alertMensagem.setStrIco("ic_atencao_triangulo_24dp");
                alertMensagem.getRetornoAlert_OK();
                txtCodigo.requestFocus();
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    boolean validarDadosProduto() {
        boolean result = true;
        String dado = null;
        if (!(result = (txtCodigo.getText().length() >= 1 && result == true))) {
            dado = "código";
            txtCodigo.requestFocus();
        }
        if (!(result = (txtDescricao.getText().length() >= 3 && result == true))) {
            dado = "descrição";
            txtDescricao.requestFocus();
        }
        if (!(result = (txtFiscalNcm.getText().length() >= 1 && result == true))) {
            dado = "descrição";
            txtFiscalNcm.requestFocus();
        }
        if (!(result = (Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replace(",", ".")) > 0 && result == true))) {
            dado = "preço de venda";
            txtPrecoVenda.requestFocus();
        }
        if (!(result = ((cboUnidadeComercial.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado = "und comercial";
            cboFiscalOrigem.requestFocus();
        }
        if (!(result = ((cboSituacaoSistema.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado = "sit. sistema";
            cboFiscalOrigem.requestFocus();
        }
        if (!(result = ((cboFiscalOrigem.getSelectionModel().getSelectedIndex() >= 0
                || cboFiscalIcms.getSelectionModel().getSelectedIndex() >= 0
                || cboFiscalPis.getSelectionModel().getSelectedIndex() >= 0
                || cboFiscalCofins.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado = "fiscal";
            cboFiscalOrigem.requestFocus();
        }
        if (!result) {
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho(String.format("Dados inválido [%s]", dado));
            alertMensagem.setPromptText(String.format("%s, %s incompleto(a) ou invalido(a) para o produto: [%s]", USUARIO_LOGADO_APELIDO, dado, txtDescricao.getText()));
            alertMensagem.setStrIco("ic_atencao_triangulo_24dp");
            alertMensagem.getRetornoAlert_OK();
        } else result = guardarProduto();
        return result;
    }

    boolean guardarProduto() {
        try {
            getProdutoVO().setCodigo(txtCodigo.getText());
            getProdutoVO().setDescricao(txtDescricao.getText());
            getProdutoVO().setPeso(Double.parseDouble(txtPeso.getText().replace(".", "").replace(",", ".")));
            getProdutoVO().setSisUnidadeComercialVO(cboUnidadeComercial.getSelectionModel().getSelectedItem());
            getProdutoVO().setSisUnidadeComercial_id(getProdutoVO().getSisUnidadeComercialVO().getId());
            getProdutoVO().setSisSituacaoSistemaVO(cboSituacaoSistema.getSelectionModel().getSelectedItem());
            getProdutoVO().setSisSituacaoSistema_id(getProdutoVO().getSisSituacaoSistemaVO().getId());
            getProdutoVO().setPrecoFabrica(Double.parseDouble(txtPrecoFabrica.getText().replace(".", "").replace(",", ".")));
            getProdutoVO().setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replace(",", ".")));
            getProdutoVO().setVarejo(Integer.parseInt(txtVarejo.getText().replaceAll("\\D", "")));
            getProdutoVO().setPrecoUltimoFrete(Double.parseDouble(txtPrecoUltimoFrete.getText().replace(".", "").replace(",", ".")));
            getProdutoVO().setComissao(Double.parseDouble(txtComissaoPorc.getText().replace(".", "").replace(",", ".")));
            if (cboFiscalCestNcm.getSelectionModel().getSelectedIndex() >= 0) {
                getProdutoVO().setFiscalCestNcmVO(cboFiscalCestNcm.getSelectionModel().getSelectedItem());
                getProdutoVO().setFiscalCestNcm_id(getProdutoVO().getFiscalCestNcmVO().getId());
            }
            if (!txtFiscalNcm.getText().equals(""))
                getProdutoVO().setNcm(txtFiscalNcm.getText().replaceAll("\\D", ""));
            if (!txtFiscalCest.getText().equals(""))
                getProdutoVO().setCest(txtFiscalCest.getText().replaceAll("\\D", ""));

            if (cboFiscalOrigem.getSelectionModel().getSelectedIndex() >= 0) {
                getProdutoVO().setFiscalCstOrigemVO(cboFiscalOrigem.getSelectionModel().getSelectedItem());
                getProdutoVO().setFiscalCSTOrigem_id(getProdutoVO().getFiscalCstOrigemVO().getId());
            }
            if (cboFiscalIcms.getSelectionModel().getSelectedIndex() >= 0) {
                getProdutoVO().setFiscalIcmsVO(cboFiscalIcms.getSelectionModel().getSelectedItem());
                getProdutoVO().setFiscalICMS_id(getProdutoVO().getFiscalIcmsVO().getId());
            }
            if (cboFiscalPis.getSelectionModel().getSelectedIndex() >= 0) {
                getProdutoVO().setFiscalPisVO(cboFiscalPis.getSelectionModel().getSelectedItem());
                getProdutoVO().setFiscalPIS_id(getProdutoVO().getFiscalPisVO().getId());
            }
            if (cboFiscalCofins.getSelectionModel().getSelectedIndex() >= 0) {
                getProdutoVO().setFiscalCofinsVO(cboFiscalCofins.getSelectionModel().getSelectedItem());
                getProdutoVO().setFiscalCOFINS_id(getProdutoVO().getFiscalCofinsVO().getId());
            }
            getProdutoVO().setNfeGenero(txtFiscalGenero.getText().replaceAll("\\D", ""));
            getProdutoVO().setUsuarioCadastro_id(Integer.parseInt(USUARIO_LOGADO_ID));
            getProdutoVO().setUsuarioAtualizacao_id(Integer.parseInt(USUARIO_LOGADO_ID));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean salvarProduto() {
        Connection conn = ConnectionFactory.getConnection();
        try {
            conn.setAutoCommit(false);

            if (getProdutoVO().getId() == 0)
                getProdutoVO().setId(new TabProdutoDAO().insertTabProdutoVO(conn, getProdutoVO()));
            else
                new TabProdutoDAO().updateTabProdutoVO(conn, getProdutoVO());

            getProdutoVO().getCodBarraVOList().stream().sorted(Comparator.comparing(TabProduto_CodBarraVO::getId))
                    .forEach(codBarra -> {
                        try {
                            if (codBarra.getId() < 0)
                                new TabProduto_CodBarraDAO().deleteTabProduto_CodBarraVO(conn, codBarra.getId(),
                                        getProdutoVO().getId());
                            else if (codBarra.getId() > 0)
                                new TabProduto_CodBarraDAO().updateTabProduto_CodBarraVO(conn, codBarra);
                            else
                                codBarra.setId(new TabProduto_CodBarraDAO().insertTabProduto_CodBarraVO(conn, codBarra,
                                        getProdutoVO().getId()));
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro no código de barras ===>>", ex);
                        }
                    });
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    void vlrConsumidor() {
        Double prcFabrica = Double.parseDouble(txtPrecoFabrica.getText().replace(".", "").replace(",", "."));
        Double margem = Double.parseDouble(txtMargem.getText().replace(".", "").replace(",", "."));
        Double prcConsumidor = 0.;
        if (margem.equals(0.)) prcConsumidor = prcFabrica;
        else prcConsumidor = (prcFabrica * (1. + (margem / 100.)));
        txtPrecoVenda.setText(ServiceFormatarDado.getValueMoeda(BigDecimal.valueOf(prcConsumidor).setScale(2, RoundingMode.HALF_UP).toString(), 2));
    }

    void vlrMargem() {
        Double prcFabrica = Double.parseDouble(txtPrecoFabrica.getText().replace(".", "").replace(",", "."));
        Double prcConsumidor = Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replace(",", "."));
        Double margem = 0.;
        if (prcConsumidor.equals(prcFabrica))
            margem = 0.;
        else
            margem = (((prcConsumidor - prcFabrica) * 100.) / prcFabrica);
        txtMargem.setText(ServiceFormatarDado.getValueMoeda(BigDecimal.valueOf(margem).setScale(2, RoundingMode.HALF_UP).toString(), 2));
    }

    void vlrLucroBruto() {
        Double prcFabrica = Double.parseDouble(txtPrecoFabrica.getText().replace(".", "").replace(",", "."));
        Double prcConsumidor = Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replace(",", "."));
        Double lucroBruto;
        if (prcConsumidor.equals(prcFabrica)) lucroBruto = 0.;
        else lucroBruto = (prcConsumidor - prcFabrica);
        txtLucroBruto.setText(ServiceFormatarDado.getValueMoeda(BigDecimal.valueOf(lucroBruto).setScale(2, RoundingMode.HALF_UP).toString(), 2));
        vlrLucroLiq();
    }

    void vlrLucroLiq() {
        Double lucroBruto = Double.parseDouble(txtLucroBruto.getText().replace(".", "").replace(",", "."));
        Double ultimoFrete = Double.parseDouble(txtPrecoUltimoFrete.getText().replace(".", "").replace(",", "."));
        Double comissaoReal = Double.parseDouble(txtComissaoReal.getText().replace(".", "").replace(",", "."));
        Double lucroLiquido;
        if (lucroBruto.equals(0.)) lucroLiquido = 0.;
        else lucroLiquido = (lucroBruto - (ultimoFrete + comissaoReal));
        txtLucroLiquido.setText(ServiceFormatarDado.getValueMoeda(BigDecimal.valueOf(lucroLiquido).setScale(2, RoundingMode.HALF_UP).toString(), 2));
        vlrLucratividade();
    }

    void vlrLucratividade() {
        Double prcConsumidor = Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replace(",", "."));
        Double lucroLiquido = Double.parseDouble(txtLucroLiquido.getText().replace(".", "").replace(",", "."));
        Double lucratividade;
        if (lucroLiquido.equals(0.)) lucratividade = 0.;
        else lucratividade = ((lucroLiquido * 100.) / prcConsumidor);
        txtLucratividade.setText(ServiceFormatarDado.getValueMoeda(BigDecimal.valueOf(lucratividade).setScale(2, RoundingMode.HALF_UP).toString(), 2));
    }

    void vlrComissaoReal() {
        Double prcConsumidor = Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replace(",", "."));
        Double comissaoPorc = Double.parseDouble(txtComissaoPorc.getText().replace(".", "").replace(",", "."));
        Double comissaoReal;
        if (comissaoPorc.equals(0.)) comissaoReal = 0.;
        else comissaoReal = prcConsumidor * (comissaoPorc / 100.);
        txtComissaoReal.setText(ServiceFormatarDado.getValueMoeda(BigDecimal.valueOf(comissaoReal).setScale(2, RoundingMode.HALF_UP).toString(), 2));
    }

}
