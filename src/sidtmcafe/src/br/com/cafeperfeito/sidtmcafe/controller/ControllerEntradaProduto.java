package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.service.*;
import br.com.cafeperfeito.sidtmcafe.view.ViewEntradaProduto;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ControllerEntradaProduto extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

    public AnchorPane painelViewEntradaProduto;
    public TitledPane tpnDadoNfe;
    public TitledPane tpnDetalheNfe;
    public JFXComboBox cboLojaDestino;
    public JFXTextField txtChaveNfe;
    public JFXTextField txtNumeroNfe;
    public JFXTextField txtNumeroSerie;
    public JFXComboBox cboFornecedor;
    public DatePicker dtpEmissaoNfe;
    public DatePicker dtpEntradaNfe;
    public TitledPane tpnImpostoNfe;
    public JFXTextField txtFiscalNumeroControle;
    public JFXTextField txtFiscalDocumentoOrigem;
    public JFXComboBox cboFiscalTributo;
    public JFXTextField txtFiscalVlrTributo;
    public JFXTextField txtFiscalVlrMulta;
    public JFXTextField txtFiscalVlrJuros;
    public JFXTextField txtFiscalVlrTaxa;
    public JFXTextField txtFiscalVlrTotalTributo;
    public JFXTextField txtFiscalVlrPercentualTributo;
    public TitledPane tpnDetalheFrete;
    public JFXTextField txtFreteChaveCte;
    public JFXComboBox cboFreteTomadorServico;
    public JFXTextField txtFreteNumeroCte;
    public JFXTextField txtFreteSerieCte;
    public JFXComboBox cboFreteModeloCte;
    public JFXComboBox cboFreteSistuacaoTributaria;
    public JFXComboBox cboFreteTransportadora;
    public DatePicker dtpFreteEmissao;
    public JFXTextField txtFreteVlrNfe;
    public JFXTextField txtFreteQtdVolume;
    public JFXTextField txtFretePesoBruto;
    public JFXTextField txtFreteVlrBruto;
    public JFXTextField txtFreteVlrImposto;
    public JFXTextField txtFreteVlrLiquido;
    public TitledPane tpnFreteImposto;
    public JFXTextField txtFreteFiscalNumeroControle;
    public JFXTextField txtFreteFiscalImpostoDocumentoOrigem;
    public JFXComboBox cboFreteFiscalTributo;
    public JFXTextField txtFreteFiscalVlrTributo;
    public JFXTextField txtFreteFiscalVlrMulta;
    public JFXTextField txtFreteFiscalVlrJuros;
    public JFXTextField txtFreteFiscalVlrTaxa;
    public JFXTextField txtFreteFiscalVlrTotalTributo;
    public JFXTextField txtFreteFiscalVlrPercentualTributo;
    public TitledPane tpnItensTotaisNfe;
    public TitledPane tpnCadastroProduto;
    public JFXTextField txtPesquisaProduto;
    public TreeTableView ttvProduto;
    public Label lblRegistrosLocalizados;
    public TitledPane tpnItensNfe;
    public TreeTableView ttvItensNfe;

    @Override
    public void fechar() {
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getTabs().remove(ViewEntradaProduto.getTab());
        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandlerEntradaProduto);
    }

    @Override
    public void criarObjetos() {
        listaTarefa.add(new Pair<>("criarTabelaProduto", "criando tabela de produto"));
    }

    @Override
    public void preencherObjetos() {
        listaTarefa.add(new Pair("preencherCboLojaDestino", "preenchendo dados das lojas"));
        listaTarefa.add(new Pair("preencherCboTributos", "preenchendo dados tributos SEFAZ-AM"));
        listaTarefa.add(new Pair("preencherCboTomadorServico", "carregando tomador serviço"));
        listaTarefa.add(new Pair("preencherCboModeloNfeCte", "carregando modelo Nfe Cte"));
        listaTarefa.add(new Pair("preencherCboFreteSituacaoTributaria", "carregando situação tributaria de frete"));
//        listaTarefas.add(new Pair("carregarTransportadora", "carregando lista transportadora"));
//        listaTarefas.add(new Pair("carregarListaProduto", "carregando lista de produtos"));


        //        listaTarefa.add(new Pair("preencherCboSituacaoSistema", "preenchendo situaão no istema"));
//        listaTarefa.add(new Pair("preencherCboFiscalCestNcm", "preenchendo dados fiscais de Ncm e Cest"));
//        listaTarefa.add(new Pair("preencherCboFiscalOrigem", "preenchendo dados fiscais de Origem"));
//        listaTarefa.add(new Pair("preencherCboFiscalIcms", "preenchendo dados fiscal ICMS"));
//        listaTarefa.add(new Pair("preencherCboFiscalPis", "preenchendo dados fiscal PIS"));
//        listaTarefa.add(new Pair("preencherCboFiscalCofins", "preenchendo dados fiscal COFINS"));
//        listaTarefa.add(new Pair("carregarListaProduto", "carregando lista de produtos"));
//
//        listaTarefa.add(new Pair("preencherTabelaProduto", "preenchendo tabela produto"));
//
        new ServiceSegundoPlano().tarefaAbreCadastroProduto(getTaskEntradaProduto(), listaTarefa.size());
    }

    @Override
    public void fatorarObjetos() {
//        cboFiscalCestNcm.setCellFactory(new Callback<ListView<FiscalCestNcmVO>, ListCell<FiscalCestNcmVO>>() {
//            @Override
//            public ListCell<FiscalCestNcmVO> call(ListView<FiscalCestNcmVO> param) {
//                final ListCell<FiscalCestNcmVO> cell = new ListCell<FiscalCestNcmVO>() {
//                    @Override
//                    protected void updateItem(FiscalCestNcmVO item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setText("");
//                        } else {
//                            String novoTexto = "";
//                            for (String det : item.getDetalheCestNcm().split(";"))
//                                if (novoTexto == "") novoTexto += det;
//                                else novoTexto += "\r\n" + det;
//                            setText(novoTexto);
//                        }
//                    }
//                };
//                return cell;
//            }
//        });
//        listCodigoBarra.setCellFactory(new Callback<ListView<TabProduto_CodBarraVO>, ListCell<TabProduto_CodBarraVO>>() {
//            @Override
//            public ListCell<TabProduto_CodBarraVO> call(ListView<TabProduto_CodBarraVO> param) {
//                final ListCell<TabProduto_CodBarraVO> cell = new ListCell<TabProduto_CodBarraVO>() {
//                    private ImageView imageView = new ImageView();
//
//                    @Override
//                    protected void updateItem(TabProduto_CodBarraVO item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setText(null);
//                            setGraphic(null);
//                        } else {
//                            if (item.getImgCodBarra() != null)
//                                imageView.setImage(item.getImgCodBarra());
//                            setText(null);
//                            setGraphic(imageView);
//                        }
//                    }
//                };
//                return cell;
//            }
//        });
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void escutarTecla() {
//        ttvProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) return;
//            setProdutoVO(newValue.getValue());
//        });
//
//        ttvProduto.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue && statusFormulario.toLowerCase().equals("pesquisa") && ttvProduto.getSelectionModel().getSelectedItem() != null)
//                setProdutoVO(ttvProduto.getSelectionModel().getSelectedItem().getValue());
//        });
//
//        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.intValue() < 0 || newValue.intValue() == oldValue.intValue()) return;
//            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(tituloTab))
//                ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
//        });

        eventHandlerEntradaProduto = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() < 0)
                    return;
                if (!ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().getText().equals(getTituloTab()))
                    return;
                switch (event.getCode()) {
//                    case F1:
//                        if (!getStatusBarTecla().contains(event.getCode().toString()))
//                            return;
//                        setStatusFormulario("incluir");
//                        setProdutoVO(new TabProdutoVO(0));
//                        break;
//                    case F2:
//                    case F5:
//                        if (!getStatusBarTecla().contains(event.getCode().toString()))
//                            return;
//                        if (!validarDadosProduto()) break;
//                        if (buscaDuplicidadeCode(getProdutoVO().getCodigo(), false)) break;
//                        if (salvarProduto()) {
//                            String tmp = getStatusFormulario().toLowerCase();
//                            setStatusFormulario("pesquisa");
//                            switch (tmp) {
//                                case "incluir":
//                                    produtoVOObservableList.add(new TabProdutoDAO().getTabProdutoVO(getProdutoVO().getId()));
//                                    break;
//                                case "editar":
//                                    produtoVOObservableList.set(produtoVOObservableList.indexOf(ttvProduto.getSelectionModel().getSelectedItem().getValue()),
//                                            new TabProdutoDAO().getTabProdutoVO(getProdutoVO().getId()));
//                                    break;
//                            }
////                            produtoVOFilteredList = (FilteredList<TabProdutoVO>) produtoVOObservableList;
//                        }
//                        pesquisaProduto();
//                        break;
//                    case F3:
//                        if (!getStatusBarTecla().contains(event.getCode().toString()))
//                            return;
//                        boolean statusIncluir = getStatusFormulario().toLowerCase().equals("incluir");
//                        alertMensagem = new ServiceAlertMensagem();
//                        alertMensagem.setStrIco("ic_cadastro_produto_cancel_24dp");
//                        alertMensagem.setCabecalho(String.format("Cancelar %s", statusIncluir ? "inclusão" : "edição"));
//                        alertMensagem.setPromptText(String.format("%s, deseja cancelar %s do cadastro de produto?",
//                                USUARIO_LOGADO_APELIDO, statusIncluir ? "inclusão" : "edição"));
//                        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
//                        if (!statusIncluir)
//                            setProdutoVO(ttvProduto.getSelectionModel().getSelectedItem().getValue());
//                        setStatusFormulario("pesquisa");
//                        break;
//                    case F4:
//                        if (!getStatusBarTecla().contains(event.getCode().toString()))
//                            return;
//                        if (ttvProduto.getSelectionModel().getSelectedIndex() < 0)
//                            break;
//                        setStatusFormulario("editar");
//                        break;
//                    case F6:
//                        if (!getStatusBarTecla().contains(event.getCode().toString()))
//                            return;
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa") || !(event.isShiftDown())) break;
//                        keyShiftF6();
//                        break;
//                    case F7:
//                        if (!getStatusBarTecla().contains(event.getCode().toString()))
//                            return;
//                        txtPesquisaProduto.requestFocus();
//                        break;
//                    case F8:
//                        if (!getStatusBarTecla().contains(event.getCode().toString()))
//                            return;
//                        break;
                    case F12:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        fechar();
                        break;
//                    case HELP:
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        keyInsert();
//                        break;
//                    case DELETE:
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        keyDelete();
//                        break;
//                    case B:
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        if (CODE_KEY_CTRL_ALT_B.match(event) || CHAR_KEY_CTRL_ALT_B.match(event))
//                            addCodeBar("");
//                        break;
//                    case Z:
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        if (CODE_KEY_CTRL_Z.match(event) || CHAR_KEY_CTRL_Z.match(event))
//                            reverseImageProduto();
//                        break;
                }
                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() > 0)
                    ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().setOnCloseRequest(event1 -> {
                        if (!getStatusFormulario().toLowerCase().equals("pesquisa")) {
                            event1.consume();
                        }
                    });
            }
        };

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerEntradaProduto);

//        txtPesquisaProduto.textProperty().addListener((observable, oldValue, newValue) -> {
//            pesquisaProduto();
//        });
//
//        txtPesquisaProduto.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() != KeyCode.ENTER) return;
//            ttvProduto.requestFocus();
//            ttvProduto.getSelectionModel().selectFirst();
//        });
//
//        cboFiscalCestNcm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (!cboFiscalCestNcm.isFocused()) return;
//            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//            if (newValue == null) return;
//            Platform.runLater(() -> {
//                txtFiscalNcm.setText(newValue.getNcm());
//                txtFiscalCest.setText(newValue.getCest());
//            });
//        });
//
//        listCodBarraVOObservableList.addListener((ListChangeListener) c -> {
//            listCodigoBarra.setItems(listCodBarraVOObservableList.stream()
//                    .filter(codBarra -> codBarra.getId() >= 0)
//                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
//        });
//
////        produtoVOObservableList.addListener((ListChangeListener<TabProdutoVO>) c -> {
////            produtoVOFilteredList = new FilteredList<>(produtoVOObservableList);
////            preencherTabelaProduto();
////            atualizaQtdRegistroLocalizado();
////        });
//
//        produtoVOFilteredList.addListener((ListChangeListener) c -> {
//            atualizaQtdRegistroLocalizado();
//            preencherTabelaProduto();
//        });
//
//        txtFiscalNcm.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!txtFiscalNcm.isFocused()) return;
//            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//            if (newValue == null) return;
//            Platform.runLater(() -> {
//                cboFiscalCestNcm.getItems().clear();
//                cboFiscalCestNcm.setItems(new FiscalCestNcmDAO()
//                        .getFiscalCestNcmVOList(newValue)
//                        .stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
//            });
//        });
//
//        txtPrecoFabrica.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtPrecoFabrica.isFocused()) return;
//                vlrConsumidor();
//                vlrLucroBruto();
//            }
//        });
//
//        txtMargem.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtMargem.isFocused()) {
//                    return;
//                }
//                vlrConsumidor();
//                vlrLucroBruto();
//            }
//        });
//
//        txtPrecoVenda.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtPrecoVenda.isFocused()) return;
//                vlrMargem();
//                vlrLucroBruto();
//            }
//        });
//
//        txtComissaoPorc.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtComissaoPorc.isFocused()) return;
//                vlrComissaoReal();
//                vlrLucroBruto();
//            }
//        });
//
//        txtPrecoUltimoImpostoSefaz.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//            if (!txtPrecoUltimoImpostoSefaz.isFocused()) return;
//            vlrLucroBruto();
//        });
//
//        txtPrecoUltimoFrete.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtPrecoUltimoFrete.isFocused()) return;
//                vlrLucroBruto();
//            }
//        });
//
//        imgCirculo.setOnDragOver(event -> {
//            if (imgCirculo.isDisabled()) return;
//            Dragboard board = event.getDragboard();
//            if (board.hasFiles())
//                if (Pattern.compile(REGEX_IMAGENS_EXTENSAO).matcher(board.getFiles().get(0).toPath().toString()).find())
//                    event.acceptTransferModes(TransferMode.ANY);
//        });
//
//        imgCirculo.setOnDragDropped(event -> {
//            if (imgCirculo.isDisabled()) return;
//            try {
//                Dragboard board = event.getDragboard();
//                addImageProduto(new Image(new FileInputStream(board.getFiles().get(0))));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTituloTab(ViewEntradaProduto.getTituloJanela());
        criarObjetos();
        preencherObjetos();
        escutarTecla();
        fatorarObjetos();
        setStatusFormulario("Pesquisa");
        ServiceCampoPersonalizado.fieldMask(painelViewEntradaProduto);
        Platform.runLater(() -> ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7)));
    }

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

    EventHandler<KeyEvent> eventHandlerEntradaProduto;
    Pattern p;
    List<Pair> listaTarefa = new ArrayList<>();
    ServiceFormatarDado formatPeso;
    ServiceAlertMensagem alertMensagem;
    String statusFormulario, statusBarTecla, tituloTab = ViewEntradaProduto.getTituloJanela();

    Task getTaskEntradaProduto() {
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
//                        case "criarTabelaProduto":
//                            TabModel.tabelaProduto();
//                            break;
                        case "preencherCboLojaDestino":
                            preencherCboLojaDestino();
                            break;
                        case "preencherCboTributos":
                            preencherCboTributos();
                            break;
                        case "preencherCboTomadorServico":
                            preencherCboTomadorServico();
                            break;
                        case "preencherCboModeloNfeCte":
                            preencherCboModeloNfeCte();
                            break;
                        case "preencherCboFreteSituacaoTributaria":
                            preencherCboFreteSituacaoTributaria();
                            break;
//                        case "preencherCboSituacaoSistema":
//                            preencherCboSituacaoSistema();
//                            break;
//                        case "preencherCboFiscalCestNcm":
//                            preencherCboFiscalCestNcm();
//                            break;
//                        case "preencherCboFiscalOrigem":
//                            preencherCboFiscalOrigem();
//                            break;
//                        case "preencherCboFiscalIcms":
//                            preencherCboFiscalIcms();
//                            break;
//                        case "preencherCboFiscalPis":
//                            preencherCboFiscalPis();
//                            break;
//                        case "preencherCboFiscalCofins":
//                            preencherCboFiscalCofins();
//                            break;
//                        case "carregarListaProduto":
//                            carregarListaProduto();
//                            break;
//                        case "preencherTabelaProduto":
//                            preencherTabelaProduto();
//                            break;
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
//        int qtd = produtoVOFilteredList.size();
//        lblRegistrosLocalizados.setText(String.format("[%s] %d registro%s localizado%s.", getStatusFormulario(), qtd,
//                qtd > 1 ? "s" : "", qtd > 1 ? "s" : ""));
    }

    public String getStatusBarTecla() {
        return statusBarTecla;
    }

    public void setStatusBarTecla(String statusFormulario) {
        switch (statusFormulario.toLowerCase()) {
            case "incluir":
//                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
//                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
//                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoCadastral.getContent());
//                txtCodigo.requestFocus();
//                statusBarTecla = STATUS_BAR_TECLA_INCLUIR;
                break;
            case "editar":
//                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
//                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
//                try {
//                    setProdutoVO(getProdutoVO().clone());
//                } catch (CloneNotSupportedException e) {
//                    e.printStackTrace();
//                }
//                txtCodigo.requestFocus();
//                statusBarTecla = STATUS_BAR_TECLA_EDITAR;
                break;
            case "pesquisa":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoNfe.getContent(), false);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), false);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnItensNfe.getContent(), true);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoNfe.getContent());
                cboLojaDestino.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }


    void preencherCboLojaDestino() {
        cboLojaDestino.getItems().setAll(new ArrayList<>(new TabEmpresaDAO().getTabEmpresaVOList(true)));
    }

    void preencherCboTributos() {
        cboFiscalTributo.getItems().setAll(new ArrayList<>(new FiscalTributoSefazAmDAO().getFiscalTributoSefazAmVOList()));
        cboFreteFiscalTributo.getItems().setAll(new ArrayList<>(new FiscalTributoSefazAmDAO().getFiscalTributoSefazAmVOList()));
    }

    void preencherCboTomadorServico() {
        cboFreteTomadorServico.getItems().setAll(new ArrayList(new FiscalFreteTomadorServicoDAO().getFiscalFreteTomadorServicoVOList()));
    }

    void preencherCboModeloNfeCte() {
        cboFreteModeloCte.getItems().setAll(new ArrayList(new FiscalModeloNfeCteDAO().getFiscalModeloNfeCteVOList()));
    }

    void preencherCboFreteSituacaoTributaria() {
        cboFreteSistuacaoTributaria.getItems().setAll(new ArrayList(new FiscalFreteSituacaoTributariaDAO().getFiscalFreteSituacaoTributariaVOList()));
    }

//    public void carregarSituacaoTributaria() {
//        cboFreteSistuacaoTributaria.getItems().clear();
//        cboFreteSistuacaoTributaria.getItems().setAll(new SisFreteSituacaoTributariaDAO().getSisFreteSituacaoTributariaVOList());
//        //cboFreteSistuacaoTributaria.getSelectionModel().select(0);
//    }
//
//    public void carregarTransportadora() {
//        cboFreteTransportadora.getItems().clear();
//        cboFreteTransportadora.getItems().setAll(new TabEmpresaDAO().getEmpresaVOList());
//        //cboFreteTransportadora.getSelectionModel().select(0);
//    }
//
//    public void carregarListaProduto() {
//        produtoVOObservableList = FXCollections.observableArrayList(new TabProdutoDAO().getProdutoVOList());
//    }

}
