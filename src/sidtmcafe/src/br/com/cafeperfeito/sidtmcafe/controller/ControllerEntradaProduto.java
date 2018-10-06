package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class ControllerEntradaProduto extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

    File fileArquivoNfe, fileArquivoCte;
    boolean changeTamTitle = false;
    Double hDadosNfe = 148.0, hDetNfe = 93.0, hDetCte = 25.0, hImpCte = 25.0, hTotaisNfe = 622.0, hItemNfe = 407.0, hItemTab = 312.0;
    Double yDetCte = 98.0, yTotaisNfe = 155.0;
    //    Double hTDadosNfe = 150.0, hTDetNfe = 930.0, hTImpNfe = 25.0, hTDetCte = 25.0, hTImpCte = 25.0, hTTotaisNfe = 605.0, hTItemNfe = 375.0;
//    Double yTDetCte = 98.0, yTTotaisNfe = 160.0, yTItemNfe = 180.0;
    boolean isNfe;
    public JFXTextField txtFreteFiscalVlrNFe;
    public JFXTextField txtFiscalVlrNFe;
    public AnchorPane painelViewEntradaProduto;
    public TitledPane tpnDadoNfe;
    public TitledPane tpnDetalheNfe;
    public JFXComboBox<TabEmpresaVO> cboLojaDestino;
    public JFXTextField txtChaveNfe;
    public JFXTextField txtNumeroNfe;
    public JFXTextField txtNumeroSerie;
    public JFXComboBox<FiscalModeloNfeCteVO> cboModeloNfe;
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
        ttvProduto.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

            }
        });

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
                    case F1:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        setStatusFormulario("Nova Nfe");
                        if (getStatusBarTecla().toLowerCase().contains("incluindo"))
                            setEntradaProdutoVO(new TabEntradaProdutoVO());
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        if (getStatusBarTecla().toLowerCase().contains("salvar")) {
                            if (!validarDadosProduto()) break;
                            if (buscaDuplicidade("entrada_produto", String.valueOf(getEntradaProdutoVO().getNumeroNfe())))
                                break;
                            if (salvarDadosNfe()) {
                                setStatusFormulario("incluindo");
                            }
//                        if (salvarDadosNfe()) {
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
                        }
                        break;
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
                        if (getStatusBarTecla().toLowerCase().contains("-salvar"))
                            txtPesquisaProduto.requestFocus();
                        if (getStatusBarTecla().toLowerCase().contains("-incluir"))
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

        txtChaveNfe.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER)
                    if (buscaDuplicidade(txtChaveNfe.getText().replaceAll("\\D", "")))
                        exibirDadosNfe();
            }
        });

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

        tpnImpostoNfe.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hItemTab -= 37.0;
                hTotaisNfe -= 37.0;
                hItemNfe -= 37.0;
                yTotaisNfe += 37.0;
                hDadosNfe += 37.0;
                yDetCte += 37.0;
                hDetNfe += 37.0;
            } else {
                hItemTab += 37.0;
                hTotaisNfe += 37.0;
                hItemNfe += 37.0;
                yTotaisNfe -= 37.0;
                hDadosNfe -= 37.0;
                yDetCte -= 37.0;
                hDetNfe -= 37.0;
            }
            organizaPosicoes();
        });

        tpnDetalheFrete.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hItemTab -= 98.0;
                hTotaisNfe -= 98.0;
                hItemNfe -= 98.0;
                yTotaisNfe += 98.0;
                hDadosNfe += 98.0;
                hDetCte += 98.0;
                if (tpnFreteImposto.isExpanded()) {
                    hItemTab -= 37.0;
                    hTotaisNfe -= 37.0;
                    hItemNfe -= 37.0;
                    yTotaisNfe += 37.0;
                    hDadosNfe += 37.0;
                    hImpCte += 37.0;
                }
            } else {
                hItemTab += 98.0;
                hTotaisNfe += 98.0;
                hItemNfe += 98.0;
                yTotaisNfe -= 98.0;
                hDadosNfe -= 98.0;
                hDetCte -= 98.0;
                if (tpnFreteImposto.isExpanded()) {
                    hItemTab += 37.0;
                    hTotaisNfe += 37.0;
                    hItemNfe += 37.0;
                    yTotaisNfe -= 37.0;
                    hDadosNfe -= 37.0;
                    hImpCte -= 37.0;
                }
            }
            organizaPosicoes();
        });

        tpnFreteImposto.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hItemTab -= 37.0;
                hTotaisNfe -= 37.0;
                hItemNfe -= 37.0;
                yTotaisNfe += 37.0;
                hDadosNfe += 37.0;
                hDetCte += 37.0;
                hImpCte += 37.0;
            } else {
                hItemTab += 37.0;
                hTotaisNfe += 37.0;
                hItemNfe += 37.0;
                yTotaisNfe -= 37.0;
                hDadosNfe -= 37.0;
                hDetCte -= 37.0;
                hImpCte -= 37.0;
            }
            organizaPosicoes();
        });

//        tpnFreteImposto.expandedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                tpnDetalheFrete.setPrefHeight(155.0);
//                tpnItensTotaisNfe.setLayoutY(tpnItensTotaisNfe.getScaleY());
//            } else {
//                tpnDetalheFrete.setPrefHeight(155.0 - 37.0);
//            }
//        });

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
                if (Pattern.compile(REGEX_EXTENSAO_NFE).matcher(board.getFiles().get(0).toPath().toString()).find()) {
                    isNfe = true;
                    event.acceptTransferModes(TransferMode.ANY);
                }
        });
        txtChaveNfe.setOnDragDropped(event -> {
            if (txtChaveNfe.isDisable()) return;
            try {
                Dragboard board = event.getDragboard();
                if ((fileArquivoNfe = new File(String.valueOf(board.getFiles().get(0)))).exists())
                    validaXmlNfeCte(fileArquivoNfe);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        txtFreteChaveCte.setOnDragOver(event -> {
            if (txtFreteChaveCte.isDisable()) return;
            Dragboard board = event.getDragboard();
            if (board.hasFiles())
                if (Pattern.compile(REGEX_EXTENSAO_NFE).matcher(board.getFiles().get(0).toPath().toString()).find()) {
                    isNfe = false;
                    event.acceptTransferModes(TransferMode.ANY);
                }
        });
        txtFreteChaveCte.setOnDragDropped(event -> {
            if (txtFreteChaveCte.isDisable()) return;
            try {
                Dragboard board = event.getDragboard();
                if ((fileArquivoCte = new File(String.valueOf(board.getFiles().get(0)))).exists())
                    validaXmlNfeCte(fileArquivoCte);
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
        setStatusFormulario("Nova Nfe");
        ServiceCampoPersonalizado.fieldMask(painelViewEntradaProduto);
        Platform.runLater(() -> ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7)));
    }

    static String STATUS_BAR_TECLA_NOVA_NFE = "[F1-Limpa Campos]  [F2-Salvar Dados Nf-e]  [F7-Chave Nfe]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_INCLUINDO = "[F1-Nova Nf-e]  [F2-Incluir Nf-e]  [F5-Autorizar Nfe]  [F12-Sair]  ";
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
    TabEntradaProdutoVO entradaProdutoVO;
    TabEntradaProduto_Fiscal_NfeVO fiscal_nfeVO;
    List<Pair> listaTarefa = new ArrayList<>();
    ServiceAlertMensagem alertMensagem;
    String statusFormulario, statusBarTecla, tituloTab = ViewEntradaProduto.getTituloJanela();
    int statusNfe = 8;

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
        tpnDadoNfe.setText(String.format("Dados da nf-e                 status:[%s]", statusFormulario));
    }

    public int getStatusNfe() {
        return statusNfe;
    }

    public void setStatusNfe(int statusNfe) {
        this.statusNfe = statusNfe;
    }

    public String getStatusBarTecla() {
        return statusBarTecla;
    }

    public void setStatusBarTecla(String statusFormulario) {
        switch (statusFormulario.toLowerCase().replaceAll("\\W", "")) {
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
            case "novanfe":
                ServiceCampoPersonalizado.fieldDisable(painelViewEntradaProduto, true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoNfe.getContent(), false);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoNfe.getContent());
                cboLojaDestino.requestFocus();
                setStatusNfe(2);
                statusBarTecla = STATUS_BAR_TECLA_NOVA_NFE;

                break;
            case "incluindo":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoNfe.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnItensTotaisNfe.getContent(), false);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnItensTotaisNfe.getContent());
                txtPesquisaProduto.requestFocus();
                setStatusNfe(1);
                statusBarTecla = STATUS_BAR_TECLA_INCLUINDO;
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

    void guardaCopiaArquivoXml(File arquivoXml) {
//        alertMensagem.setStrIco("ic_repositorio_git_24dp.png");
//        alertMensagem.setCabecalho(String.format("Repositorio"));
//        alertMensagem.setPromptText(String.format("%s, deseja guardar arquivo %s no repositorio do sistema?",
//                USUARIO_LOGADO_APELIDO, isNfe ? "Nf-e" : "Ct-e"));
//        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        try {
            Files.copy(arquivoXml.toPath(),
                    Paths.get(PATH_DIR_XML_NFE_CTE + (arquivoXml.getName().contains("nfe")
                            ? "nfe" : "cte") + "/in", arquivoXml.getName()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public TabEntradaProdutoVO getEntradaProdutoVO() {
        return entradaProdutoVO;
    }

    public void setEntradaProdutoVO(TabEntradaProdutoVO entradaProduto) {
//        if (entradaProduto == null)
//            entradaProduto = new TabEntradaProdutoVO();
        entradaProdutoVO = entradaProduto;
        //exibirDadosProduto();
    }

    public TabEntradaProduto_Fiscal_NfeVO getFiscal_nfeVO() {
        return fiscal_nfeVO;
    }

    public void setFiscal_nfeVO(TabEntradaProduto_Fiscal_NfeVO fiscal_nfeVO) {
        if (fiscal_nfeVO == null)
            fiscal_nfeVO = new TabEntradaProduto_Fiscal_NfeVO();
        this.fiscal_nfeVO = fiscal_nfeVO;
    }

    boolean buscaDuplicidade(String num_chaveNfe) {
        setEntradaProdutoVO(new TabEntradaProdutoDAO().getTabEntradaProdutoVO(num_chaveNfe));
        return (getEntradaProdutoVO() != null);
    }

    void validaXmlNfeCte(File arquivoXml) {
        alertMensagem = new ServiceAlertMensagem();
        if (!arquivoXml.exists()) return;
        try {
            FileInputStream inputStream = new FileInputStream(arquivoXml);
            if (arquivoXml.getName().contains("nfe")) {
                tNfeProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(inputStream), TNfeProc.class);
                String chaveNfe = tNfeProc.getNFe().getInfNFe().getId().replaceAll("\\D", "");
                String numeroNfe = tNfeProc.getNFe().getInfNFe().getIde().getNNF();
                if (buscaDuplicidade(chaveNfe)) return;
                txtChaveNfe.setText(chaveNfe);
                guardaCopiaArquivoXml(arquivoXml);
                carregarDadosXmlNfe();
            } else {
                cteProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(inputStream), CteProc.class);
                String chaveCte = cteProc.getCTe().getInfCte().getId().replaceAll("\\D", "");
                String numeroCte = cteProc.getCTe().getInfCte().getIde().getNCT();
                if (buscaDuplicidade(chaveCte)) return;
                txtFreteChaveCte.setText(chaveCte);
                guardaCopiaArquivoXml(arquivoXml);
                exibirDadosXmlCte();
                procurarArquivoNfeVinculadoNfe();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            alertMensagem.setStrIco("ic_xml_24dp.png");
            alertMensagem.setCabecalho(String.format("Arquivo inválido"));
            alertMensagem.setPromptText(String.format("%s, arquivo %s inválido!", USUARIO_LOGADO_APELIDO, isNfe ? "Nf-e" : "Ct-e"));
            alertMensagem.getRetornoAlert_OK();
            txtFreteChaveCte.requestFocus();
        }
    }

    void procurarArquivoNfeVinculadoNfe() {
        Path configFilePath = Paths.get(fileArquivoCte.getPath().replace(fileArquivoCte.getName(), ""));
        try {
            if ((fileArquivoNfe = Files.walk(configFilePath)
                    .filter(s -> s.getFileName().toString().contains(cteProc.getCTe().getInfCte().getInfCTeNorm().getInfDoc().getInfNFe().get(0).getChave()))
                    .findFirst().get().toFile()).exists())
                validaXmlNfeCte(fileArquivoNfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void carregarDadosXmlNfe() {
        entradaProdutoVO = new TabEntradaProdutoVO();
        entradaProdutoVO.setLojaDestino_id(new TabEmpresaDAO().getTabEmpresaVO(tNfeProc.getNFe().getInfNFe().getDest().getCNPJ()).getId());
        entradaProdutoVO.setChaveNfe(tNfeProc.getNFe().getInfNFe().getId().replaceAll("\\D", ""));
        entradaProdutoVO.setNumeroNfe(Integer.parseInt(tNfeProc.getNFe().getInfNFe().getIde().getNNF()));
        entradaProdutoVO.setSerieNfe(Integer.parseInt(tNfeProc.getNFe().getInfNFe().getIde().getSerie()));
        entradaProdutoVO.setModeloNfe_id(new FiscalModeloNfeCteDAO().getFiscalModeloNfeCteVO(tNfeProc.getNFe().getInfNFe().getIde().getMod()).getId());
        entradaProdutoVO.setFornecedor_id(new TabEmpresaDAO().getTabEmpresaVO(tNfeProc.getNFe().getInfNFe().getEmit().getCNPJ()).getId());
        entradaProdutoVO.setDataEmissaoNfe(Timestamp.valueOf(LocalDateTime.parse(tNfeProc.getNFe().getInfNFe().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE)));
        entradaProdutoVO.setDataEntradaNfe(Timestamp.valueOf(LocalDateTime.now()));
        setFiscal_nfeVO(null);
        tpnImpostoNfe.setExpanded(true);
        System.out.println("0: [" + tNfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF() + "]");
        System.out.println("1: [" + Double.parseDouble(tNfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()) + "]");
        System.out.println("2: [" + new BigDecimal(Double.parseDouble(tNfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF())).setScale(2) + "]");
        fiscal_nfeVO.setVlrNfe(new BigDecimal(Double.parseDouble(tNfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF())).setScale(2));
        exibirDadosNfe();
    }

    void exibirDadosNfe() {
        cboLojaDestino.getSelectionModel().select(cboLojaDestino.getItems().stream()
                .filter(lojaDestino -> lojaDestino.getId() == entradaProdutoVO.getLojaDestino_id())
                .findFirst().orElse(null));
        cboFornecedor.getSelectionModel().select(cboFornecedor.getItems().stream()
                .filter(fornecedor -> fornecedor.getId() == entradaProdutoVO.getFornecedor_id())
                .findFirst().orElse(null));
//        cboFreteTransportadora.getSelectionModel().select(cboFreteTransportadora.getItems().stream()
//                .filter(transportadora -> transportadora.getId() == entradaProduto.)
//                .findFirst().orElse(null));
        txtNumeroNfe.setText(String.valueOf(entradaProdutoVO.getNumeroNfe()));
        txtNumeroSerie.setText(String.valueOf(entradaProdutoVO.getSerieNfe()));
        cboModeloNfe.getSelectionModel().select(cboModeloNfe.getItems().stream()
                .filter(modelo -> modelo.getId() == entradaProdutoVO.getModeloNfe_id())
                .findFirst().orElse(null));
        dtpEmissaoNfe.setValue(LocalDate.parse(DTF_MYSQL_DATA.format(entradaProdutoVO.getDataEmissaoNfe().toLocalDateTime())));
        dtpEntradaNfe.setValue(LocalDate.now());
        txtFiscalVlrNFe.setText(fiscal_nfeVO.getVlrNfe().setScale(2).toString());
    }

    void carregarDadosXmlCte() {

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

    void organizaPosicoes() {
        tpnItensTotaisNfe.setPrefHeight(hTotaisNfe);
        tpnItensTotaisNfe.setLayoutY(yTotaisNfe);
        tpnDadoNfe.setPrefHeight(hDadosNfe);
        tpnDetalheFrete.setLayoutY(yDetCte);
        tpnDetalheFrete.setPrefHeight(hDetCte);
        tpnFreteImposto.setPrefHeight(hImpCte);
        tpnItensNfe.setPrefHeight(hItemNfe);
        ttvItensNfe.setPrefHeight(hItemTab);
        tpnDetalheNfe.setPrefHeight(hDetNfe);
    }

    boolean buscaDuplicidade(String tipDuplic, String busca) {
        try {
            String detalhe = "";
            switch (tipDuplic.toLowerCase().replaceAll("\\W", "")) {
                case "entrada_produto":
                    TabEntradaProdutoVO entradaProdutoVO;
                    if (busca.length() == 44) {
                        detalhe = "chave da Nf-e ";
                        txtChaveNfe.requestFocus();
                    } else {
                        detalhe = "número da Nf-e ";
                        txtNumeroNfe.requestFocus();
                    }
                    if ((entradaProdutoVO = new TabEntradaProdutoDAO().getTabEntradaProdutoVO(busca)) == null)
                        return false;
            }
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho(String.format("%s duplicado", detalhe));
            alertMensagem.setPromptText(String.format("%s, o %s: [%s] já está cadastrado no sistema!",
                    USUARIO_LOGADO_APELIDO, detalhe, busca));
            alertMensagem.setStrIco("ic_atencao_triangulo_24dp");
            alertMensagem.getRetornoAlert_OK();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    boolean validarDadosProduto() {
        boolean result = true;
        String dado = "";
        if (!(result = ((cboLojaDestino.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado += "loja destino";
            cboLojaDestino.requestFocus();
        }
        if (!(result = (txtChaveNfe.getText().replaceAll("\\W", "").length() == 44 && result == true))) {
            dado += "nota fiscal";
            txtChaveNfe.requestFocus();
        }
        if (!(result = (txtNumeroNfe.getText().length() >= 1 && result == true))) {
            dado += "número Nfe";
            txtNumeroNfe.requestFocus();
        }
        if (!(result = (txtNumeroSerie.getText().length() >= 1 && result == true))) {
            dado += "número Nfe";
            txtNumeroSerie.requestFocus();
        }
        if (!(result = ((cboFornecedor.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado += "fornecedor";
            cboFornecedor.requestFocus();
        }
        if (!(result = ((dtpEmissaoNfe.getValue().isBefore(LocalDate.now())) && result == true))) {
            dado += "data de emissão";
            dtpEmissaoNfe.requestFocus();
        }
        if (!result) {
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho(String.format("Dados inválido [%s]", dado));
            alertMensagem.setStrIco("ic_atencao_triangulo_24dp");
            alertMensagem.setPromptText(String.format("%s, %s incompleto(a) ou invalido(a)!",
                    USUARIO_LOGADO_APELIDO, dado));
            alertMensagem.getRetornoAlert_OK();
        } else guardarEntradaProduto();
        return result;
    }

    boolean guardarEntradaProduto() {
        try {
            getEntradaProdutoVO().setLojaDestino_id(cboLojaDestino.getSelectionModel().getSelectedItem().getId());
            getEntradaProdutoVO().setChaveNfe(txtChaveNfe.getText());
            getEntradaProdutoVO().setNumeroNfe(Integer.parseInt(txtNumeroNfe.getText()));
            getEntradaProdutoVO().setSerieNfe(Integer.parseInt(txtNumeroSerie.getText()));
            getEntradaProdutoVO().setModeloNfeVO(cboModeloNfe.getSelectionModel().getSelectedItem());
            getEntradaProdutoVO().setModeloNfe_id(getEntradaProdutoVO().getModeloNfe_id());
            getEntradaProdutoVO().setFornecedorVO(cboFornecedor.getSelectionModel().getSelectedItem());
            getEntradaProdutoVO().setFornecedor_id(getEntradaProdutoVO().getFornecedor_id());
            getEntradaProdutoVO().setDataEmissaoNfe(Timestamp.valueOf(dtpEmissaoNfe.getValue().atStartOfDay()));
            getEntradaProdutoVO().setDataEntradaNfe(Timestamp.valueOf(dtpEntradaNfe.getValue().atTime(LocalTime.now())));
            getEntradaProdutoVO().setStatusNfe_id(getStatusNfe());
//            getEntradaProdutoVO().setFiscal_nfeVO(new TabEntradaProduto_Fiscal_NfeVO());
//            getEntradaProdutoVO().setFrete_nfeVO(new TabEntradaProduto_FreteVO());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean salvarDadosNfe() {
        Connection conn = ConnectionFactory.getConnection();
        try {
            conn.setAutoCommit(false);
            if (getEntradaProdutoVO().getId() == 0)
                getEntradaProdutoVO().setId(new TabEntradaProdutoDAO().insertTabEntradaProdutoVO(conn, getEntradaProdutoVO()));
            else
                new TabEntradaProdutoDAO().updateTabEntradaProdutoVO(conn, getEntradaProdutoVO());

//            getEntradaProdutoVO().getCodBarraVOList().stream().sorted(Comparator.comparing(TabProduto_CodBarraVO::getId))
//                    .forEach(codBarra -> {
//                        try {
//                            if (codBarra.getId() < 0)
//                                new TabProduto_CodBarraDAO().deleteTabProduto_CodBarraVO(conn, codBarra.getId(),
//                                        getEntradaProdutoVO().getId());
//                            else if (codBarra.getId() > 0)
//                                new TabProduto_CodBarraDAO().updateTabProduto_CodBarraVO(conn, codBarra);
//                            else
//                                codBarra.setId(new TabProduto_CodBarraDAO().insertTabProduto_CodBarraVO(conn, codBarra,
//                                        getEntradaProdutoVO().getId()));
//                        } catch (Exception ex) {
//                            throw new RuntimeException("Erro no código de barras ===>>", ex);
//                        }
//                    });
            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
        return true;
    }


}
