package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.model.TabModel;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.*;
import br.com.cafeperfeito.sidtmcafe.view.ViewEntradaProduto;
import br.inf.portalfiscal.xsd.cte.procCTe.CteProc;
import br.inf.portalfiscal.xsd.cte.procCTe.TCTe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNfeProc;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ControllerEntradaProduto extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

    File fileArquivoNfeCte;
    public JFXTextField txtFreteFiscalVlrNFe;
    public JFXTextField txtFiscalVlrNFe;
    public AnchorPane painelViewEntradaProduto;
    public TitledPane tpnDadoNfe;
    public TitledPane tpnDetalheNfe;
    public JFXComboBox<TabEmpresaVO> cboLojaDestino;
    public JFXTextField txtChaveNfe;
    public JFXTextField txtNumeroNfe;
    public JFXTextField txtNumeroSerie;
    public JFXComboBox<TabEmpresaVO> cboFornecedor;
    public DatePicker dtpEmissaoNfe;
    public DatePicker dtpEntradaNfe;
    public TitledPane tpnImpostoNfe;
    public JFXTextField txtFiscalNumeroControle;
    public JFXTextField txtFiscalDocumentoOrigem;
    public JFXComboBox<FiscalTributoSefazAmVO> cboFiscalTributo;
    public JFXTextField txtFiscalVlrTributo;
    public JFXTextField txtFiscalVlrMulta;
    public JFXTextField txtFiscalVlrJuros;
    public JFXTextField txtFiscalVlrTaxa;
    public JFXTextField txtFiscalVlrTotalTributo;
    public JFXTextField txtFiscalVlrPercentualTributo;
    public TitledPane tpnDetalheFrete;
    public JFXTextField txtFreteChaveCte;
    public JFXComboBox<FiscalFreteTomadorServicoVO> cboFreteTomadorServico;
    public JFXTextField txtFreteNumeroCte;
    public JFXTextField txtFreteSerieCte;
    public JFXComboBox<FiscalModeloNfeCteVO> cboFreteModeloCte;
    public JFXComboBox<FiscalFreteSituacaoTributariaVO> cboFreteSistuacaoTributaria;
    public JFXComboBox<TabEmpresaVO> cboFreteTransportadora;
    public DatePicker dtpFreteEmissao;
    public JFXTextField txtFreteVlrCte;
    public JFXTextField txtFreteQtdVolume;
    public JFXTextField txtFretePesoBruto;
    public JFXTextField txtFreteVlrBruto;
    public JFXTextField txtFreteVlrImposto;
    public JFXTextField txtFreteVlrLiquido;
    public TitledPane tpnFreteImposto;
    public JFXTextField txtFreteFiscalNumeroControle;
    public JFXTextField txtFreteFiscalImpostoDocumentoOrigem;
    public JFXComboBox<FiscalTributoSefazAmVO> cboFreteFiscalTributo;
    public JFXTextField txtFreteFiscalVlrTributo;
    public JFXTextField txtFreteFiscalVlrMulta;
    public JFXTextField txtFreteFiscalVlrJuros;
    public JFXTextField txtFreteFiscalVlrTaxa;
    public JFXTextField txtFreteFiscalVlrTotalTributo;
    public JFXTextField txtFreteFiscalVlrPercentualTributo;
    public TitledPane tpnItensTotaisNfe;
    public TitledPane tpnCadastroProduto;
    public JFXTextField txtPesquisaProduto;
    public TreeTableView<TabProdutoVO> ttvProduto;
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
        listaTarefa.add(new Pair("criarTabelaProduto", "criando tabela de produto"));
    }

    @Override
    public void preencherObjetos() {
        listaTarefa.add(new Pair("preencherCboTributos", "preenchendo dados tributos SEFAZ-AM"));
        listaTarefa.add(new Pair("preencherCboTomadorServico", "carregando tomador serviço"));
        listaTarefa.add(new Pair("preencherCboModeloNfeCte", "carregando modelo Nfe Cte"));
        listaTarefa.add(new Pair("preencherCboFreteSituacaoTributaria", "carregando situação tributaria de frete"));
        listaTarefa.add(new Pair("preencherCbosEmpresas", "carregando dados de empresas"));
        listaTarefa.add(new Pair("carregarListaProduto", "carregando lista de produtos"));
        listaTarefa.add(new Pair("vinculandoObjetosTabela", "vinculando objetos a tableMoel"));
        listaTarefa.add(new Pair("preencherTabelaProduto", "preenchendo tabela produto"));


        //        listaTarefa.add(new Pair("preencherCboSituacaoSistema", "preenchendo situaão no istema"));
//        listaTarefa.add(new Pair("preencherCboFiscalCestNcm", "preenchendo dados fiscais de Ncm e Cest"));
//        listaTarefa.add(new Pair("preencherCboFiscalOrigem", "preenchendo dados fiscais de Origem"));
//        listaTarefa.add(new Pair("preencherCboFiscalIcms", "preenchendo dados fiscal ICMS"));
//        listaTarefa.add(new Pair("preencherCboFiscalPis", "preenchendo dados fiscal PIS"));
//        listaTarefa.add(new Pair("preencherCboFiscalCofins", "preenchendo dados fiscal COFINS"));
//
//
        new ServiceSegundoPlano().tarefaAbreCadastro(getTaskEntradaProduto(), listaTarefa.size());
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

        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 0 || newValue.intValue() == oldValue.intValue()) return;
            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(tituloTab))
                ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
        });

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
                    case F7:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        txtPesquisaProduto.requestFocus();
                        break;
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

        txtPesquisaProduto.textProperty().addListener((observable, oldValue, newValue) -> {
            modelProduto.pesquisaProduto(newValue.toLowerCase().trim());
        });

        txtPesquisaProduto.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvProduto.requestFocus();
            ttvProduto.getSelectionModel().selectFirst();
        });

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
//            modelProduto.preencherTabelaProduto();
//        });

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

        txtFiscalVlrTributo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFiscalVlrTributo.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtFiscalVlrMulta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFiscalVlrMulta.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtFiscalVlrJuros.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFiscalVlrJuros.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtFiscalVlrTaxa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFiscalVlrTaxa.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtFiscalVlrTotalTributo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFiscalVlrTotalTributo.isFocused()) return;
                vlrFiscalVlrPercentualTributo();
            }
        });

        txtFiscalVlrNFe.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFiscalVlrTotalTributo.isFocused()) return;
                vlrFiscalVlrPercentualTributo();
            }
        });

        txtFreteVlrBruto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFreteVlrBruto.isFocused()) return;
                vlrFreteLiquido();
            }
        });

        txtFreteVlrImposto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa"))return;
                if (!txtFreteVlrImposto.isFocused()) return;
                vlrFreteLiquido();
            }
        });

        txtFreteFiscalVlrTributo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFreteFiscalVlrTributo.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtFreteFiscalVlrMulta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFreteFiscalVlrMulta.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtFreteFiscalVlrJuros.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFreteFiscalVlrJuros.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtFreteFiscalVlrTaxa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtFreteFiscalVlrTaxa.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtFreteFiscalVlrTotalTributo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFreteFiscalVlrTotalTributo.isFocused()) return;
                vlrFreteFiscalVlrPercentualTributo();
            }
        });

        txtFreteVlrCte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFiscalVlrTotalTributo.isFocused()) return;
                vlrFreteFiscalVlrPercentualTributo();
            }
        });

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

        txtChaveNfe.setOnDragOver(event -> {
            if (txtChaveNfe.isDisable()) return;
            Dragboard board = event.getDragboard();
            if (board.hasFiles())
                if (Pattern.compile(REGEX_EXTENSAO_NFE).matcher(board.getFiles().get(0).toPath().toString()).find())
                    event.acceptTransferModes(TransferMode.ANY);
        });
        txtChaveNfe.setOnDragDropped(event -> {
            if (txtChaveNfe.isDisable()) return;
            try {
                Dragboard board = event.getDragboard();
                validaXmlNfeCte(true, new FileInputStream(fileArquivoNfeCte = board.getFiles().get(0)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        txtFreteChaveCte.setOnDragOver(event -> {
            if (txtFreteChaveCte.isDisable()) return;
            Dragboard board = event.getDragboard();
            if (board.hasFiles())
                if (Pattern.compile(REGEX_EXTENSAO_NFE).matcher(board.getFiles().get(0).toPath().toString()).find())
                    event.acceptTransferModes(TransferMode.ANY);
        });
        txtFreteChaveCte.setOnDragDropped(event -> {
            if (txtFreteChaveCte.isDisable()) return;
            try {
                Dragboard board = event.getDragboard();
                validaXmlNfeCte(false, new FileInputStream(fileArquivoNfeCte = board.getFiles().get(0)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F2-Incluir Itens]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F1-Novo]  [F2-Finalizar NFe]  [F3-Cancelar inclusão]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";

    EventHandler<KeyEvent> eventHandlerEntradaProduto;
    TNfeProc tNfeProc;
    CteProc cteProc;
    Pattern p;
    TabModel modelProduto;
    ObservableList<TabEmpresaVO> empresaVOObservableList;
    ObservableList<TabProdutoVO> produtoVOObservableList = FXCollections.observableArrayList();
    FilteredList<TabProdutoVO> produtoVOFilteredList;
    List<Pair> listaTarefa = new ArrayList<>();
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
                        case "vinculandoObjetosTabela":
                            modelProduto = new TabModel();
                            modelProduto.setLblRegistrosLocalizados(lblRegistrosLocalizados);
                            modelProduto.setTtvProduto(ttvProduto);
                            modelProduto.setProdutoVOObservableList(produtoVOObservableList);
                            modelProduto.setProdutoVOFilteredList(produtoVOFilteredList);
                            modelProduto.escutaListaProduto();
                            break;
                        case "criarTabelaProduto":
                            modelProduto.tabelaProduto();
                            break;
                        case "preencherCbosEmpresas":
                            preencherCbosEmpresas();
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
                        case "carregarListaProduto":
                            carregarListaProduto();
                            break;
                        case "preencherTabelaProduto":
                            modelProduto.preencherTabelaProduto();
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
                ServiceCampoPersonalizado.fieldDisable(painelViewEntradaProduto, true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoNfe.getContent(), false);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoNfe.getContent());
                cboLojaDestino.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }

    void preencherCbosEmpresas() {
        empresaVOObservableList = FXCollections.observableArrayList(new TabEmpresaDAO().getTabEmpresaVOList());
        cboLojaDestino.getItems().setAll(new ArrayList<>(empresaVOObservableList.filtered(loja -> loja.isIsLoja())));
        cboFornecedor.getItems().setAll(new ArrayList(empresaVOObservableList.filtered(fornecedor -> fornecedor.isIsFornecedor())));
        cboFreteTransportadora.getItems().setAll(new ArrayList(empresaVOObservableList.filtered(transportadora -> transportadora.isIsTransportadora())));
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

    void carregarListaProduto() {
        produtoVOFilteredList = new FilteredList<>(produtoVOObservableList = FXCollections.observableArrayList(new TabProdutoDAO().getTabProdutoVOList()));
    }

    void msgConfirmaGuardarArquivoRepositorio(boolean isNfe, FileInputStream arquivo) {
        alertMensagem.setStrIco("ic_repositorio_git_24dp.png");
        alertMensagem.setCabecalho(String.format("Repositorio"));
        alertMensagem.setPromptText(String.format("%s, deseja guardar arquivo %s no repositorio do sistema?",
                USUARIO_LOGADO_APELIDO, isNfe ? "Nf-e" : "Ct-e"));
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        File file = new File(String.valueOf(arquivo));
        System.out.println("Move arquivo: [" + file.getName().toString() + "]");
    }

    boolean validaXmlNfeCte(boolean isNfe, FileInputStream arquivo) {
        alertMensagem = new ServiceAlertMensagem();
        if (!fileArquivoNfeCte.exists()) return false;
        try {
            if (isNfe) {
                tNfeProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(arquivo), TNfeProc.class);
                txtChaveNfe.setText(tNfeProc.getNFe().getInfNFe().getId().replaceAll("\\D", ""));
                msgConfirmaGuardarArquivoRepositorio(isNfe, arquivo);
            } else {
                cteProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(arquivo), CteProc.class);
                txtFreteChaveCte.setText(cteProc.getCTe().getInfCte().getId().replaceAll("\\D", ""));
                msgConfirmaGuardarArquivoRepositorio(isNfe, arquivo);
                exibirDadosXmlCte();
            }
        } catch (Exception ex) {
            alertMensagem.setStrIco("ic_xml_24dp.png");
            alertMensagem.setCabecalho(String.format("Arquivo inválido"));
            alertMensagem.setPromptText(String.format("%s, arquivo %s inválido!", USUARIO_LOGADO_APELIDO, isNfe ? "Nf-e" : "Ct-e"));
            alertMensagem.getRetornoAlert_OK();
            txtFreteChaveCte.requestFocus();
            return false;
        }
        return true;
    }

    void exibirDadosXmlNfe() {
        cboLojaDestino.getSelectionModel().select(cboLojaDestino.getItems().stream()
                .filter(lojaDestino -> lojaDestino.getCnpj().equals(tNfeProc.getNFe().getInfNFe().getDest().getCNPJ()))
                .findFirst().orElse(null));
        cboFornecedor.getSelectionModel().select(cboFornecedor.getItems().stream()
                .filter(fornecedor -> fornecedor.getCnpj().equals(tNfeProc.getNFe().getInfNFe().getEmit().getCNPJ()))
                .findFirst().orElse(null));
        cboFreteTransportadora.getSelectionModel().select(cboFreteTransportadora.getItems().stream()
                .filter(transportadora -> transportadora.getCnpj().equals(tNfeProc.getNFe().getInfNFe().getTransp().getTransporta().getCNPJ()))
                .findFirst().orElse(null));
        txtNumeroNfe.setText(tNfeProc.getNFe().getInfNFe().getIde().getNNF());
        txtNumeroSerie.setText(tNfeProc.getNFe().getInfNFe().getIde().getSerie());
        dtpEmissaoNfe.setValue(LocalDate.parse(tNfeProc.getNFe().getInfNFe().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));
        dtpEntradaNfe.setValue(LocalDate.now());
    }

    void exibirDadosXmlCte() {
        cboFreteTomadorServico.getSelectionModel().select(cboFreteTomadorServico.getItems().stream()
                .filter(tomadorServico -> tomadorServico.getId() == Integer.parseInt(cteProc.getCTe().getInfCte().getIde().getToma3().getToma()))
                .findFirst().orElse(null));
        txtFreteNumeroCte.setText(cteProc.getCTe().getInfCte().getIde().getNCT());
        txtFreteSerieCte.setText(cteProc.getCTe().getInfCte().getIde().getSerie());
        cboFreteModeloCte.getSelectionModel().select(cboFreteModeloCte.getItems().stream()
                .filter(modeloCTe -> modeloCTe.getDescricao().equals(cteProc.getCTe().getInfCte().getIde().getMod()))
                .findFirst().orElse(null));
        cboFreteSistuacaoTributaria.getSelectionModel().select(cboFreteSistuacaoTributaria.getItems().stream()
                .filter(sitTributaria -> sitTributaria.getId().equals(cteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getCST()))
                .findFirst().orElse(null));
        cboFreteTransportadora.getSelectionModel().select(cboFreteTransportadora.getItems().stream()
                .filter(transportadora -> transportadora.getCnpj().equals(cteProc.getCTe().getInfCte().getEmit().getCNPJ()))
                .findFirst().orElse(null));
        dtpFreteEmissao.setValue(LocalDate.parse(cteProc.getCTe().getInfCte().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));
        txtFreteVlrCte.setText(cteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getVBC());
        txtFretePesoBruto.setText(new BigDecimal(Double.parseDouble(cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ().get(0).getQCarga())).setScale(2).toString());
        List<TCTe.InfCte.InfCTeNorm.InfCarga.InfQ> list = cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getTpMed().toLowerCase().equals("volume"))
                txtFreteQtdVolume.setText(new BigDecimal(Double.parseDouble(cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ().get(i).getQCarga())).setScale(0).toString());
        List<TCTe.InfCte.VPrest.Comp> list1 = cteProc.getCTe().getInfCte().getVPrest().getComp();
        Double vlr = 0., vlrB = 0., vlrI = 0., vlrT = 0.;
        for (int i = 0; i < list1.size(); i++) {
            vlr = Double.parseDouble(cteProc.getCTe().getInfCte().getVPrest().getComp().get(i).getVComp());
            vlrT += vlr;
            if (list1.get(i).getXNome().toLowerCase().equals("frete peso")) {
                vlrB = vlr;
                continue;
            }
            vlrI += vlr;
        }
        txtFreteVlrBruto.setText(new BigDecimal(vlrB).setScale(2).toString());
        txtFreteVlrImposto.setText(new BigDecimal(vlrI).setScale(2, RoundingMode.HALF_UP).toString());
        txtFreteVlrLiquido.setText(new BigDecimal(vlrT).setScale(2, RoundingMode.HALF_UP).toString());

        String chaveNFe;
        if ((chaveNFe = cteProc.getCTe().getInfCte().getInfCTeNorm().getInfDoc().getInfNFe().get(0).getChave()) != null) {
            txtChaveNfe.setText(chaveNFe);

        }


    }


    void vlrFiscalVlrTotalTributo() {
        Double vlrTributo = ServiceFormatarDado.getDoubleFromTextField(txtFiscalVlrTributo.getText());
        Double vlrMulta = ServiceFormatarDado.getDoubleFromTextField(txtFiscalVlrMulta.getText());
        Double vlrJuros = ServiceFormatarDado.getDoubleFromTextField(txtFiscalVlrJuros.getText());
        Double vlrTaxa = ServiceFormatarDado.getDoubleFromTextField(txtFiscalVlrTaxa.getText());
        Double vlrTotalTributo;
        vlrTotalTributo = vlrTributo + vlrMulta + vlrJuros + vlrTaxa;
        txtFiscalVlrTotalTributo.setText(new BigDecimal(vlrTotalTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFreteFiscalVlrTotalTributo() {
        Double vlrFreteTotalTributo;
        Double vlrFreteTributo = ServiceFormatarDado.getDoubleFromTextField(txtFreteFiscalVlrTributo.getText());
        Double vlrFreteMulta = ServiceFormatarDado.getDoubleFromTextField(txtFreteFiscalVlrMulta.getText());
        Double vlrFreteJuros = ServiceFormatarDado.getDoubleFromTextField(txtFreteFiscalVlrJuros.getText());
        Double vlrFreteTaxa = ServiceFormatarDado.getDoubleFromTextField(txtFreteFiscalVlrTaxa.getText());
        vlrFreteTotalTributo = vlrFreteTributo + vlrFreteMulta + vlrFreteJuros + vlrFreteTaxa;
        txtFreteFiscalVlrTotalTributo.setText(new BigDecimal(vlrFreteTotalTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFiscalVlrPercentualTributo() {
        Double vlrTotalNfe = ServiceFormatarDado.getDoubleFromTextField(txtFiscalVlrNFe.getText());
        Double vlrTotalTributo = ServiceFormatarDado.getDoubleFromTextField(txtFiscalVlrTotalTributo.getText());
        Double vlrPercTributo;
        if (vlrTotalNfe == 0.)
            vlrPercTributo = 0.;
        else
            vlrPercTributo = (vlrTotalTributo * 100) / vlrTotalNfe;
        txtFiscalVlrPercentualTributo.setText(new BigDecimal(vlrPercTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFreteFiscalVlrPercentualTributo() {
        Double vlrFretePercTributo;
        Double vlrTotalCte = ServiceFormatarDado.getDoubleFromTextField(txtFreteVlrCte.getText());
        Double vlrFreteTotalTributo = ServiceFormatarDado.getDoubleFromTextField(txtFreteFiscalVlrTotalTributo.getText());
        if (vlrTotalCte == 0.)
            vlrFretePercTributo = 0.;
        else
            vlrFretePercTributo = (vlrFreteTotalTributo * 100) / vlrTotalCte;
        txtFreteFiscalVlrPercentualTributo.setText(new BigDecimal(vlrFretePercTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFreteLiquido() {
        Double vlrFreteBruto = ServiceFormatarDado.getDoubleFromTextField(txtFreteVlrBruto.getText());
        Double vlrFreteImposto = ServiceFormatarDado.getDoubleFromTextField(txtFreteVlrImposto.getText());
        Double vlrFreteLiquido;
        vlrFreteLiquido = vlrFreteBruto + vlrFreteImposto;
        txtFreteVlrLiquido.setText(new BigDecimal(vlrFreteLiquido).setScale(2, RoundingMode.HALF_UP).toString());
    }

}
