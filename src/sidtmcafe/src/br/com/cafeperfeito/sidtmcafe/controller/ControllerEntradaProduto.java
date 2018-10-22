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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ControllerEntradaProduto extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

    File fileArquivoNfe, fileArquivoCte;
    boolean resultValidacaoDados = true;
    String retornoValidacaoDados = "";
    boolean changeTamTitle = false;
    Double hDadosNfe = 148.0, hDetNfe = 93.0, hDetCte = 25.0, hImpCte = 25.0, hTotaisNfe = 622.0, hItemNfe = 407.0, hItemTab = 312.0;
    Double yDetCte = 98.0, yTotaisNfe = 155.0;
    public JFXTextField txtCteFiscalVlrNFe;
    public JFXTextField txtNfeFiscalVlrNFe;
    public AnchorPane painelViewEntradaProduto;
    public TitledPane tpnEntradaNfe;
    public TitledPane tpnNfeDetalhe;
    public TitledPane tpnNfeFiscal;
    public JFXComboBox<TabEmpresaVO> cboNfeLojaDestino;
    public JFXTextField txtNfeChave;
    public JFXTextField txtNfeNumero;
    public JFXTextField txtNfeSerie;
    public JFXComboBox<FiscalModeloNfeCteVO> cboNfeModelo;
    public JFXComboBox<TabEmpresaVO> cboNfeFornecedor;
    public DatePicker dtpNfeEmissao;
    public DatePicker dtpNfeEntrada;
    public JFXTextField txtNfeFiscalControle;
    public JFXTextField txtNfeFiscalOrigem;
    public JFXComboBox<FiscalTributoSefazAmVO> cboNfeFiscalTributo;
    public JFXTextField txtNfeFiscalVlrTributo;
    public JFXTextField txtNfeFiscalVlrMulta;
    public JFXTextField txtNfeFiscalVlrJuros;
    public JFXTextField txtNfeFiscalVlrTaxa;
    public JFXTextField txtNfeFiscalVlrTotal;
    public JFXTextField txtNfeFiscalVlrPercentual;
    public TitledPane tpnCteDetalhe;
    public JFXTextField txtCteChave;
    public JFXComboBox<FiscalFreteTomadorServicoVO> cboCteTomadorServico;
    public JFXTextField txtCteNumero;
    public JFXTextField txtCteSerie;
    public JFXComboBox<FiscalModeloNfeCteVO> cboCteModelo;
    public JFXComboBox<FiscalFreteSituacaoTributariaVO> cboCteSistuacaoTributaria;
    public JFXComboBox<TabEmpresaVO> cboCteTransportadora;
    public DatePicker dtpCteEmissao;
    public JFXTextField txtCteVlrCte;
    public JFXTextField txtCteQtdVolume;
    public JFXTextField txtCtePesoBruto;
    public JFXTextField txtCteVlrBruto;
    public JFXTextField txtCteVlrImposto;
    public JFXTextField txtCteVlrLiquido;
    public TitledPane tpnCteFiscal;
    public JFXTextField txtCteFiscalControle;
    public JFXTextField txtCteFiscalOrigem;
    public JFXComboBox<FiscalTributoSefazAmVO> cboCteFiscalTributo;
    public JFXTextField txtCteFiscalVlrTributo;
    public JFXTextField txtCteFiscalVlrMulta;
    public JFXTextField txtCteFiscalVlrJuros;
    public JFXTextField txtCteFiscalVlrTaxa;
    public JFXTextField txtCteFiscalVlrTotal;
    public JFXTextField txtCteFiscalVlrPercentual;
    public TitledPane tpnItensTotaisNfe;
    public TitledPane tpnCadastroProduto;
    public JFXTextField txtPesquisaProduto;
    public TreeTableView<TabProdutoVO> ttvProduto;
    public Label lblRegistrosLocalizados;
    public TitledPane tpnItensNfe;
    public TreeTableView ttvItensNfe;
    boolean formValidoAbertura = false;

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

        formValidoAbertura = new ServiceSegundoPlano().tarefaAbreCadastro(getTaskEntradaProduto(), listaTarefa.size());
    }

    @Override
    public void fatorarObjetos() {
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void escutarTecla() {
        ttvProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == null) return;
//            if (newValue.getValue().getDescricao().equals(""))
//                setNfeItemVO(newValue.getParent().getValue());
//            else
//                setNfeItemVO(newValue.getValue());
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
                        break;
                    case F2:
                        if (!getStatusBarTecla().contains(event.getCode().toString()))
                            return;
                        if (getStatusFormulario().toLowerCase().replaceAll("\\W", "").contains("novanfe")
                                || getStatusFormulario().toLowerCase().contains("editada")) {
                            if (!validarDadosNfe()) break;
                            if (buscaDuplicidade(getEntradaNfeVO().getChaveNfe(), true)) break;
                            if (tpnCteDetalhe.isExpanded())
                                if (buscaDuplicidade(getEntradaCteVO().getChaveCte(), false)) break;
                            if (salvarDadosNfe()) {
                                setStatusFormulario("salvardados");
                            }
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

//        produtoVOObservableList.addListener((ListChangeListener<TabProdutoVO>) c -> {
//            produtoVOFilteredList = new FilteredList<>(produtoVOObservableList);
//            preencherTabelaProduto();
//            atualizaQtdRegistroLocalizado();
//        });
//
//        produtoVOFilteredList.addListener((ListChangeListener) c -> {
//            atualizaQtdRegistroLocalizado();
//            modelProduto.preencherTabelaProduto();
//        });

        txtNfeChave.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (txtNfeChave.getText().replaceAll("\\D", "").length() < 44) return;
//                if (event.getCode() == KeyCode.ENTER)
//                    if (buscaDuplicidade(txtChaveNfe.getText().replaceAll("\\D", "")))
//                        exibirDadosNfe();
            }
        });

        txtNfeNumero.textProperty().addListener((observable, oldValue, newValue) -> {
            labelContainers();
        });

        txtNfeFiscalVlrTributo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFiscalVlrTributo.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtNfeFiscalVlrMulta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFiscalVlrMulta.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtNfeFiscalVlrJuros.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFiscalVlrJuros.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtNfeFiscalVlrTaxa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFiscalVlrTaxa.isFocused()) return;
                vlrFiscalVlrTotalTributo();
            }
        });

        txtNfeFiscalVlrTotal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFiscalVlrTotalTributo.isFocused()) return;
                vlrFiscalVlrPercentualTributo();
            }
        });

        txtNfeFiscalVlrNFe.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFiscalVlrTotalTributo.isFocused()) return;
                vlrFiscalVlrPercentualTributo();
            }
        });

        txtCteVlrBruto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFreteVlrBruto.isFocused()) return;
                vlrFreteLiquido();
            }
        });

        txtCteVlrImposto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa"))return;
//                if (!txtFreteVlrImposto.isFocused()) return;
                vlrFreteLiquido();
            }
        });

        txtCteFiscalVlrTributo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFreteFiscalVlrTributo.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtCteFiscalVlrMulta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFreteFiscalVlrMulta.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtCteFiscalVlrJuros.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFreteFiscalVlrJuros.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtCteFiscalVlrTaxa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                if (!txtFreteFiscalVlrTaxa.isFocused()) return;
                vlrFreteFiscalVlrTotalTributo();
            }
        });

        txtCteFiscalVlrTotal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFreteFiscalVlrTotalTributo.isFocused()) return;
                vlrFreteFiscalVlrPercentualTributo();
            }
        });

        txtCteVlrCte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                //if (!txtFiscalVlrTotalTributo.isFocused()) return;
                vlrFreteFiscalVlrPercentualTributo();
            }
        });

        tpnNfeFiscal.expandedProperty().addListener((observable, oldValue, newValue) -> {
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
                tpnNfeFiscal.setText("");
            }
            organizaPosicoes();
        });

        tpnCteDetalhe.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hItemTab -= 98.0;
                hTotaisNfe -= 98.0;
                hItemNfe -= 98.0;
                yTotaisNfe += 98.0;
                hDadosNfe += 98.0;
                hDetCte += 98.0;
                if (tpnCteFiscal.isExpanded()) {
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
                if (tpnCteFiscal.isExpanded()) {
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

        tpnCteFiscal.expandedProperty().addListener((observable, oldValue, newValue) -> {
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

        tpnEntradaNfe.setOnDragOver(event -> {
            if (tpnEntradaNfe.isDisable()) return;
            Dragboard board = event.getDragboard();
            if (board.hasFiles())
                if (Pattern.compile(REGEX_EXTENSAO_NFE).matcher(board.getFiles().get(0).toPath().toString()).find())
                    event.acceptTransferModes(TransferMode.ANY);
        });

        tpnEntradaNfe.setOnDragDropped(event -> {
            if (tpnEntradaNfe.isDisable()) return;
            try {
                Dragboard board = event.getDragboard();
                File arquivoXml;
                if ((arquivoXml = new File(String.valueOf(board.getFiles().get(0)))).exists())
                    validaXmlNfeCte(arquivoXml);
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
        Platform.runLater(() -> {
            if (!formValidoAbertura)
                fechar();
            ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7));
        });
    }

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Limpar Campos]  [F2-Salvar Dados Nf-e]  [F7-Chave Nf-e]  [F8-Chave Ct-e]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_DADOS_SALVOS = "[F1-NOVA Nf-e]  [F2-Finalizar Nf-e]  [F7-Pesquisa Produto]  [F8-Intes Nf-e]  [Ctrl + F9-Transmitir Nf-e]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_FINALIZADA = "[F1-Nova Nf-e]  [F2-Enviar Nf-e]  [F4-Editar Nf-e]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_ENVIADA = "[F1-Nova Nf-e]  [F3-Cancelar Nf-e]  [F12-Sair]  ";

    EventHandler<KeyEvent> eventHandlerEntradaProduto;
    TNfeProc tNfeProc;
    CteProc cteProc;
    Pattern p;
    TabModel modelProduto;
    ObservableList<TabEmpresaVO> empresaVOObservableList;
    ObservableList<TabProdutoVO> produtoVOObservableList = FXCollections.observableArrayList();
    FilteredList<TabProdutoVO> produtoVOFilteredList;
    TabEntradaNfeVO entradaNfeVO;
    TabEntradaCteVO entradaCteVO;
    TabEntradaFiscalVO entradaFiscalNfeVO, entradaFiscalCteVO;
    TabEntradaNfeItemVO nfeItemVO;
    List<Pair> listaTarefa = new ArrayList<>();
    ServiceAlertMensagem alertMensagem;
    String statusFormulario, statusBarTecla, tituloTab = ViewEntradaProduto.getTituloJanela();
    int statusNfe = 0;

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
        tpnEntradaNfe.setText(String.format("Dados da nf-e                 status:[%s]", statusFormulario));
        TabModel.atualizaRegistrosProdutos();
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
        switch (getStatusFormulario().toLowerCase().replaceAll("\\W", "")) {
            case "novanfe":
            case "editada":
                ServiceCampoPersonalizado.fieldDisable(painelViewEntradaProduto, true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnEntradaNfe.getContent(), false);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnEntradaNfe.getContent());
                cboNfeLojaDestino.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                setStatusNfe(0);
                break;
            case "salvardados":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnEntradaNfe.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnItensTotaisNfe.getContent(), false);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnItensTotaisNfe.getContent());
                txtPesquisaProduto.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_DADOS_SALVOS;
                setStatusNfe(1);
                break;

//            case "incluir":
////                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
////                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
////                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoCadastral.getContent());
////                txtCodigo.requestFocus();
////                statusBarTecla = STATUS_BAR_TECLA_INCLUIR;
//                break;
//            case "editar":
////                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroProduto.getContent(), true);
////                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
////                try {
////                    setProdutoVO(getProdutoVO().clone());
////                } catch (CloneNotSupportedException e) {
////                    e.printStackTrace();
////                }
////                txtCodigo.requestFocus();
////                statusBarTecla = STATUS_BAR_TECLA_EDITAR;
//                break;
//            case "novanfe":
//                ServiceCampoPersonalizado.fieldDisable(painelViewEntradaProduto, true);
//                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoNfe.getContent(), false);
//                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoNfe.getContent());
//                cboLojaDestino.requestFocus();
//                setStatusNfe(2);
//                statusBarTecla = STATUS_BAR_TECLA_NOVA_NFE;
//
//                break;
//            case "incluindo":
//                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoNfe.getContent(), true);
//                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnItensTotaisNfe.getContent(), false);
//                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnItensTotaisNfe.getContent());
//                txtPesquisaProduto.requestFocus();
//                setStatusNfe(1);
//                statusBarTecla = STATUS_BAR_TECLA_INCLUINDO;
//                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }

    void preencherCbosEmpresas() {
        empresaVOObservableList = FXCollections.observableArrayList(new TabEmpresaDAO().getTabEmpresaVOList());
        cboNfeLojaDestino.getItems().setAll(new ArrayList<>(empresaVOObservableList.filtered(loja -> loja.isIsLoja())));
        cboNfeFornecedor.getItems().setAll(new ArrayList(empresaVOObservableList.filtered(fornecedor -> fornecedor.isIsFornecedor())));
        cboCteTransportadora.getItems().setAll(new ArrayList(empresaVOObservableList.filtered(transportadora -> transportadora.isIsTransportadora())));
    }

    void preencherCboTributos() {
        cboNfeFiscalTributo.getItems().setAll(new ArrayList<>(new FiscalTributoSefazAmDAO().getFiscalTributoSefazAmVOList()));
        cboCteFiscalTributo.getItems().setAll(new ArrayList<>(new FiscalTributoSefazAmDAO().getFiscalTributoSefazAmVOList()));
    }

    void preencherCboTomadorServico() {
        cboCteTomadorServico.getItems().setAll(new ArrayList(new FiscalFreteTomadorServicoDAO().getFiscalFreteTomadorServicoVOList()));
    }

    void preencherCboModeloNfeCte() {
        cboNfeModelo.getItems().setAll(new ArrayList(new FiscalModeloNfeCteDAO().getFiscalModeloNfeCteVOList()));
        cboCteModelo.getItems().setAll(new ArrayList(new FiscalModeloNfeCteDAO().getFiscalModeloNfeCteVOList()));
    }

    void preencherCboFreteSituacaoTributaria() {
        cboCteSistuacaoTributaria.getItems().setAll(new ArrayList(new FiscalFreteSituacaoTributariaDAO().getFiscalFreteSituacaoTributariaVOList()));
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

    public TabEntradaNfeVO getEntradaNfeVO() {
        return entradaNfeVO;
    }

    public void setEntradaNfeVO(TabEntradaNfeVO entradaNfe) {
        if (entradaNfe == null)
            entradaNfe = new TabEntradaNfeVO();
        entradaNfeVO = entradaNfe;
        //exibirDadosProduto();
    }

    public TabEntradaFiscalVO getEntradaFiscalNfeVO() {
        return entradaFiscalNfeVO;
    }

    public void setEntradaFiscalNfeVO(TabEntradaFiscalVO entradaFiscalNfe) {
        if (entradaFiscalNfe == null)
            entradaFiscalNfe = new TabEntradaFiscalVO();
        this.entradaFiscalNfeVO = entradaFiscalNfe;
    }

    public TabEntradaFiscalVO getEntradaFiscalCteVO() {
        return entradaFiscalCteVO;
    }

    public void setEntradaFiscalCteVO(TabEntradaFiscalVO entradaFiscalCte) {
        if (entradaFiscalCte == null)
            entradaFiscalCte = new TabEntradaFiscalVO();
        this.entradaFiscalCteVO = entradaFiscalCte;
    }

    public TabEntradaCteVO getEntradaCteVO() {
        return entradaCteVO;
    }

    public void setEntradaCteVO(TabEntradaCteVO entradaCte) {
        if (entradaCte == null)
            entradaCte = new TabEntradaCteVO();
        this.entradaCteVO = entradaCte;
    }

    public TabEntradaNfeItemVO getNfeItemVO() {
        return nfeItemVO;
    }

    public void setNfeItemVO(TabEntradaNfeItemVO nfeItem) {
        if (nfeItem == null)
            nfeItem = new TabEntradaNfeItemVO();
        this.nfeItemVO = nfeItemVO;
    }

    void labelContainers() {
        tpnNfeDetalhe.setText(String.format("Detalhe da nf-e [%s]", txtNfeNumero.getText()));

        if (tpnNfeFiscal.isExpanded())
            tpnNfeFiscal.setText("Informações de imposto");
        else
            tpnNfeFiscal.setText("Nf-e sem imposto");

        if (tpnCteDetalhe.isExpanded()) {
            tpnCteDetalhe.setText("Detalhe do frete");
            if (tpnCteFiscal.isExpanded())
                tpnCteFiscal.setText("Informações de imposto no frete");
            else
                tpnCteFiscal.setText("frete sem imposto");
        } else {
            tpnCteDetalhe.setText("Nf-e sem frete");
        }
    }

    void validaXmlNfeCte(File arquivoXml) {
        alertMensagem = new ServiceAlertMensagem();
        if (!arquivoXml.exists()) return;
        boolean isNfe = false;
        try {
            FileInputStream inputStream = new FileInputStream(arquivoXml);
            if ((isNfe = arquivoXml.getName().contains("nfe"))) {
                fileArquivoNfe = arquivoXml;
                tNfeProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(inputStream), TNfeProc.class);
                String chaveNfe = tNfeProc.getNFe().getInfNFe().getId().replaceAll("\\D", "");
                if (buscaDuplicidade(chaveNfe, isNfe)) return;
                txtNfeChave.setText(chaveNfe);
                guardaCopiaArquivoXml(arquivoXml);
                exibirDadosXmlNfe();
            } else {
                fileArquivoCte = arquivoXml;
                cteProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(inputStream), CteProc.class);
                String chaveCte = cteProc.getCTe().getInfCte().getId().replaceAll("\\D", "");
                if (buscaDuplicidade(chaveCte, isNfe)) return;
                txtCteChave.setText(chaveCte);
                guardaCopiaArquivoXml(arquivoXml);
                procurarArquivoNfeVinculadoNfe();
                exibirDadosXmlCte();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            alertMensagem.setStrIco("ic_xml_24dp.png");
            alertMensagem.setCabecalho(String.format("Arquivo inválido"));
            alertMensagem.setPromptText(String.format("%s, arquivo %s inválido!", USUARIO_LOGADO_APELIDO, isNfe ? "Nf-e" : "Ct-e"));
            alertMensagem.getRetornoAlert_OK();
            txtCteChave.requestFocus();
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

    void exibirDadosXmlNfe() {
        cboNfeLojaDestino.getSelectionModel().select(cboNfeLojaDestino.getItems().stream()
                .filter(lojaDestino -> lojaDestino.getCnpj().equals(tNfeProc.getNFe().getInfNFe().getDest().getCNPJ()))
                .findFirst().orElse(null));
        cboNfeFornecedor.getSelectionModel().select(cboNfeFornecedor.getItems().stream()
                .filter(fornecedor -> fornecedor.getCnpj().equals(tNfeProc.getNFe().getInfNFe().getEmit().getCNPJ()))
                .findFirst().orElse(null));
        txtNfeChave.setText(tNfeProc.getNFe().getInfNFe().getId().replaceAll("\\D", ""));
        txtNfeNumero.setText(tNfeProc.getNFe().getInfNFe().getIde().getNNF());
        txtNfeSerie.setText(tNfeProc.getNFe().getInfNFe().getIde().getSerie());
        cboNfeModelo.getSelectionModel().select(cboNfeModelo.getItems().stream()
                .filter(modelo -> modelo.getDescricao().equals(tNfeProc.getNFe().getInfNFe().getIde().getMod()))
                .findFirst().orElse(null));
        dtpNfeEmissao.setValue(LocalDate.parse(tNfeProc.getNFe().getInfNFe().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));
        dtpNfeEntrada.setValue(LocalDate.now());

        tpnNfeFiscal.setExpanded(true);
        //txtNfeFiscalVlrNFe.setText(new BigDecimal(Double.parseDouble(tNfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF())).setScale(2).toString());
        txtNfeFiscalVlrNFe.setText(tNfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF());
    }

    void exibirDadosXmlCte() {
        tpnCteDetalhe.setExpanded(true);
        cboCteTomadorServico.getSelectionModel().select(cboCteTomadorServico.getItems().stream()
                .filter(tomadorServico -> tomadorServico.getId() == Integer.parseInt(cteProc.getCTe().getInfCte().getIde().getToma3().getToma()))
                .findFirst().orElse(null));
        txtCteNumero.setText(cteProc.getCTe().getInfCte().getIde().getNCT());
        txtCteSerie.setText(cteProc.getCTe().getInfCte().getIde().getSerie());
        cboCteModelo.getSelectionModel().select(cboCteModelo.getItems().stream()
                .filter(modeloCTe -> modeloCTe.getDescricao().equals(cteProc.getCTe().getInfCte().getIde().getMod()))
                .findFirst().orElse(null));
        cboCteSistuacaoTributaria.getSelectionModel().select(cboCteSistuacaoTributaria.getItems().stream()
                .filter(sitTributaria -> sitTributaria.getId() == Integer.parseInt(cteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getCST()))
                .findFirst().orElse(null));
        cboCteTransportadora.getSelectionModel().select(cboCteTransportadora.getItems().stream()
                .filter(transportadora -> transportadora.getCnpj().equals(cteProc.getCTe().getInfCte().getEmit().getCNPJ()))
                .findFirst().orElse(null));
        dtpCteEmissao.setValue(LocalDate.parse(cteProc.getCTe().getInfCte().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));
        txtCteVlrCte.setText(cteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getVBC());
        txtCtePesoBruto.setText(new BigDecimal(Double.parseDouble(cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ().get(0).getQCarga())).setScale(2).toString());
        List<TCTe.InfCte.InfCTeNorm.InfCarga.InfQ> list = cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getTpMed().toLowerCase().equals("volume"))
                txtCteQtdVolume.setText(new BigDecimal(Double.parseDouble(cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ().get(i).getQCarga())).setScale(0).toString());
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
        txtCteVlrBruto.setText(new BigDecimal(vlrB).setScale(2).toString());
        txtCteVlrImposto.setText(new BigDecimal(vlrI).setScale(2, RoundingMode.HALF_UP).toString());
        txtCteVlrLiquido.setText(new BigDecimal(vlrT).setScale(2, RoundingMode.HALF_UP).toString());

        tpnCteFiscal.setExpanded(true);
        txtCteFiscalVlrNFe.setText(cteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getVBC());

        String chaveNFe;
        if ((chaveNFe = cteProc.getCTe().getInfCte().getInfCTeNorm().getInfDoc().getInfNFe().get(0).getChave()) != null) {
            txtNfeChave.setText(chaveNFe);

        }
    }

    void vlrFiscalVlrTotalTributo() {
        Double vlrTributo = ServiceFormatarDado.getDoubleFromTextField(txtNfeFiscalVlrTributo.getText());
        Double vlrMulta = ServiceFormatarDado.getDoubleFromTextField(txtNfeFiscalVlrMulta.getText());
        Double vlrJuros = ServiceFormatarDado.getDoubleFromTextField(txtNfeFiscalVlrJuros.getText());
        Double vlrTaxa = ServiceFormatarDado.getDoubleFromTextField(txtNfeFiscalVlrTaxa.getText());
        Double vlrTotalTributo;
        vlrTotalTributo = vlrTributo + vlrMulta + vlrJuros + vlrTaxa;
        txtNfeFiscalVlrTotal.setText(new BigDecimal(vlrTotalTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFreteFiscalVlrTotalTributo() {
        Double vlrFreteTotalTributo;
        Double vlrFreteTributo = ServiceFormatarDado.getDoubleFromTextField(txtCteFiscalVlrTributo.getText());
        Double vlrFreteMulta = ServiceFormatarDado.getDoubleFromTextField(txtCteFiscalVlrMulta.getText());
        Double vlrFreteJuros = ServiceFormatarDado.getDoubleFromTextField(txtCteFiscalVlrJuros.getText());
        Double vlrFreteTaxa = ServiceFormatarDado.getDoubleFromTextField(txtCteFiscalVlrTaxa.getText());
        vlrFreteTotalTributo = vlrFreteTributo + vlrFreteMulta + vlrFreteJuros + vlrFreteTaxa;
        txtCteFiscalVlrTotal.setText(new BigDecimal(vlrFreteTotalTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFiscalVlrPercentualTributo() {
        Double vlrTotalNfe = ServiceFormatarDado.getDoubleFromTextField(txtNfeFiscalVlrNFe.getText());
        Double vlrTotalTributo = ServiceFormatarDado.getDoubleFromTextField(txtNfeFiscalVlrTotal.getText());
        Double vlrPercTributo;
        if (vlrTotalNfe == 0.)
            vlrPercTributo = 0.;
        else
            vlrPercTributo = (vlrTotalTributo * 100) / vlrTotalNfe;
        txtNfeFiscalVlrPercentual.setText(new BigDecimal(vlrPercTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFreteFiscalVlrPercentualTributo() {
        Double vlrFretePercTributo;
        Double vlrTotalCte = ServiceFormatarDado.getDoubleFromTextField(txtCteVlrCte.getText());
        Double vlrFreteTotalTributo = ServiceFormatarDado.getDoubleFromTextField(txtCteFiscalVlrTotal.getText());
        if (vlrTotalCte == 0.)
            vlrFretePercTributo = 0.;
        else
            vlrFretePercTributo = (vlrFreteTotalTributo * 100) / vlrTotalCte;
        txtCteFiscalVlrPercentual.setText(new BigDecimal(vlrFretePercTributo).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrFreteLiquido() {
        Double vlrFreteBruto = ServiceFormatarDado.getDoubleFromTextField(txtCteVlrBruto.getText());
        Double vlrFreteImposto = ServiceFormatarDado.getDoubleFromTextField(txtCteVlrImposto.getText());
        Double vlrFreteLiquido;
        vlrFreteLiquido = vlrFreteBruto + vlrFreteImposto;
        txtCteVlrLiquido.setText(new BigDecimal(vlrFreteLiquido).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void organizaPosicoes() {
        tpnItensTotaisNfe.setPrefHeight(hTotaisNfe);
        tpnItensTotaisNfe.setLayoutY(yTotaisNfe);
        tpnEntradaNfe.setPrefHeight(hDadosNfe);
        tpnCteDetalhe.setLayoutY(yDetCte);
        tpnCteDetalhe.setPrefHeight(hDetCte);
        tpnCteFiscal.setPrefHeight(hImpCte);
        tpnItensNfe.setPrefHeight(hItemNfe);
        ttvItensNfe.setPrefHeight(hItemTab);
        tpnNfeDetalhe.setPrefHeight(hDetNfe);
        labelContainers();
    }

    boolean buscaDuplicidade(String num_chaveNfe, boolean isNfe) {
        num_chaveNfe = num_chaveNfe.replaceAll("\\D", "");
        String detalhe = "";
        TabEntradaNfeVO entNfeVO = null;
        TabEntradaCteVO entCteVO = null;
        if (isNfe) {
            if ((entNfeVO = new TabEntradaNfeDAO().getTabEntradaNfeVO(num_chaveNfe)) == null)
                return false;
        } else {
            if ((entCteVO = new TabEntradaCteDAO().getTabEntradaCteVO(num_chaveNfe)) == null)
                return false;
        }

        if (num_chaveNfe.length() == 44)
            detalhe = "a chave ";
        if (num_chaveNfe.length() > 0 && num_chaveNfe.length() < 9)
            detalhe = "o número ";

        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setCabecalho(String.format("%s %s já existe!", detalhe, isNfe ? "da Nf-e " : "do Ct-e "));
        alertMensagem.setPromptText(String.format("%s, %s %s: [%s]\njá está cadastrado no sistema!\nDeseja visualizar %s?",
                USUARIO_LOGADO_APELIDO, detalhe, isNfe ? "da Nf-e " : "do Ct-e", num_chaveNfe, isNfe ? " a Nf-e" : "o Ct-e"));
        alertMensagem.setStrIco("ic_atencao_triangulo_24dp");
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.YES) {
            System.out.println("Botao YES");
        } else {
            setStatusFormulario("Nova Nfe");
        }
        return true;
    }

    boolean validarDadosNfe() {
        resultValidacaoDados = true;
        if (resultValidacaoDados == true && !(cboNfeLojaDestino.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "loja de destino da Nfe";
            cboNfeLojaDestino.requestFocus();
        }
        if (resultValidacaoDados == true && !(txtNfeChave.getText().replaceAll("\\W", "").length() == 44)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "chave da Nfe";
            txtNfeChave.requestFocus();
        }
        if (resultValidacaoDados == true && !(txtNfeNumero.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "número Nfe";
            txtNfeNumero.requestFocus();
        }
        if (resultValidacaoDados == true && !(txtNfeSerie.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "número de série da Nfe";
            txtNfeSerie.requestFocus();
        }
        if (resultValidacaoDados == true && !(cboNfeFornecedor.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "fornecedor da Nfe";
            cboNfeFornecedor.requestFocus();
        }
        if (resultValidacaoDados == true && !(dtpNfeEmissao.getValue().isBefore(LocalDate.now()))) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "data de emissão da Nfe";
            dtpNfeEmissao.requestFocus();
        }
        if (resultValidacaoDados == true && tpnNfeFiscal.isExpanded()) {
            validarDadosNfeImposto();
        }
        if (resultValidacaoDados == true && tpnCteDetalhe.isExpanded()) {
            validarDadosCte();
        }
        if (!resultValidacaoDados) {
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho(String.format("Dados inválido [%s]", retornoValidacaoDados));
            alertMensagem.setStrIco("ic_atencao_triangulo_24dp");
            alertMensagem.setPromptText(String.format("%s, %s incompleto(a) ou invalido(a)!",
                    USUARIO_LOGADO_APELIDO, retornoValidacaoDados));
            alertMensagem.getRetornoAlert_OK();
        } else guardarDadosNfe();
        return resultValidacaoDados;
    }

    boolean validarDadosNfeImposto() {
        resultValidacaoDados = true;
        if (resultValidacaoDados == true && !(txtNfeFiscalControle.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "número de controle imposto Nfe";
            txtNfeFiscalControle.requestFocus();
        }
        if (resultValidacaoDados == true && !(txtNfeFiscalOrigem.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "documento de origem imposto Nfe";
            txtNfeFiscalOrigem.requestFocus();
        }
        if (resultValidacaoDados == true && !(cboNfeFiscalTributo.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "descrição do tributo imposto Nfe";
            cboNfeFiscalTributo.requestFocus();
        }
        if (resultValidacaoDados) guardarDadosNfeFiscal();
        return resultValidacaoDados;
    }

    boolean validarDadosCte() {
        resultValidacaoDados = true;
        if (resultValidacaoDados == true && !(txtCteChave.getText().replaceAll("\\W", "").length() == 44)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "chave do Cte";
            txtCteChave.requestFocus();
        }
        if (resultValidacaoDados == true && !(cboCteTomadorServico.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "tomador de serviço do Cte";
            cboCteTomadorServico.requestFocus();
        }
        if (resultValidacaoDados == true && !(txtCteNumero.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "número do Cte";
            txtCteNumero.requestFocus();
        }
        if (resultValidacaoDados == true && !(txtCteSerie.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "número de série do Cte";
            txtCteSerie.requestFocus();
        }
        if (resultValidacaoDados == true && !(cboCteModelo.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "modelo do Cte";
            cboCteModelo.requestFocus();
        }
        if (resultValidacaoDados == true && !(cboCteSistuacaoTributaria.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "situação tributária do Cte";
            cboCteSistuacaoTributaria.requestFocus();
        }
        if (resultValidacaoDados == true && !(cboCteTransportadora.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "transportadora do Cte";
            cboCteTransportadora.requestFocus();
        }
        if (resultValidacaoDados == true && !(dtpCteEmissao.getValue().isBefore(LocalDate.now()))) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "data de emissão do Cte";
            dtpCteEmissao.requestFocus();
        }
        if (resultValidacaoDados == true && tpnCteFiscal.isExpanded()) {
            validarDadosCteImposto();
        }
        if (resultValidacaoDados) guardarDadosCte();
        return resultValidacaoDados;
    }

    boolean validarDadosCteImposto() {
        resultValidacaoDados = true;
        if (resultValidacaoDados == true && !(txtCteFiscalControle.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "número de controle imposto Cte";
            txtCteFiscalControle.requestFocus();
        }
        if (resultValidacaoDados == true && !(txtCteFiscalOrigem.getText().length() >= 1)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "documento de origem imposto Cte";
            txtCteFiscalOrigem.requestFocus();
        }
        if (resultValidacaoDados == true && !(cboCteFiscalTributo.getSelectionModel().getSelectedIndex() >= 0)) {
            resultValidacaoDados = false;
            retornoValidacaoDados += "descrição do tributo imposto Cte";
            cboCteFiscalTributo.requestFocus();
        }
        if (resultValidacaoDados) guardarDadosCteFiscal();
        return resultValidacaoDados;
    }

    boolean guardarDadosNfe() {
        try {
            setEntradaNfeVO(getEntradaNfeVO());
            getEntradaNfeVO().setLojaDestino_id(cboNfeLojaDestino.getSelectionModel().getSelectedItem().getId());
            getEntradaNfeVO().setChaveNfe(txtNfeChave.getText());
            getEntradaNfeVO().setNumeroNfe(Integer.parseInt(txtNfeNumero.getText()));
            getEntradaNfeVO().setSerieNfe(Integer.parseInt(txtNfeSerie.getText()));
            getEntradaNfeVO().setModeloNfeVO(cboNfeModelo.getSelectionModel().getSelectedItem());
            getEntradaNfeVO().setModeloNfe_id(getEntradaNfeVO().getModeloNfeVO().getId());
            getEntradaNfeVO().setFornecedorVO(cboNfeFornecedor.getSelectionModel().getSelectedItem());
            getEntradaNfeVO().setFornecedor_id(getEntradaNfeVO().getFornecedorVO().getId());
            getEntradaNfeVO().setDataEmissaoNfe(Timestamp.valueOf(dtpNfeEmissao.getValue().atStartOfDay()));
            getEntradaNfeVO().setDataEntradaNfe(Timestamp.valueOf(dtpNfeEntrada.getValue().atTime(LocalTime.now())));
            getEntradaNfeVO().setStatusNfe_id(getStatusNfe());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean guardarDadosNfeFiscal() {
        try {
            setEntradaFiscalNfeVO(getEntradaFiscalNfeVO());
            getEntradaFiscalNfeVO().setNumControle(txtNfeFiscalControle.getText());
            getEntradaFiscalNfeVO().setDocOrigem(txtNfeFiscalOrigem.getText());
            getEntradaFiscalNfeVO().setTributoSefazAmVO(cboNfeFiscalTributo.getSelectionModel().getSelectedItem());
            getEntradaFiscalNfeVO().setTributoSefazAM_id(getEntradaFiscalNfeVO().getTributoSefazAmVO().getId());
            getEntradaFiscalNfeVO().setVlrNfe(new BigDecimal(txtNfeFiscalVlrNFe.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalNfeVO().setVlrTributo(new BigDecimal(txtNfeFiscalVlrTributo.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalNfeVO().setVlrMulta(new BigDecimal(txtNfeFiscalVlrMulta.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalNfeVO().setVlrJuros(new BigDecimal(txtNfeFiscalVlrJuros.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalNfeVO().setVlrTaxa(new BigDecimal(txtNfeFiscalVlrTaxa.getText().replace(".", "").replace(",", ".")));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean guardarDadosCte() {
        try {
            setEntradaCteVO(getEntradaCteVO());
            getEntradaCteVO().setChaveCte(txtCteChave.getText());
            getEntradaCteVO().setTomadorServicoVO(cboCteTomadorServico.getSelectionModel().getSelectedItem());
            getEntradaCteVO().setTomadorServico_id(getEntradaCteVO().getTomadorServicoVO().getId());
            getEntradaCteVO().setNumeroCte(Integer.parseInt(txtCteNumero.getText().replaceAll("\\D", "")));
            getEntradaCteVO().setSerieCte(Integer.parseInt(txtCteSerie.getText().replaceAll("\\D", "")));
            getEntradaCteVO().setModeloCteVO(cboCteModelo.getSelectionModel().getSelectedItem());
            getEntradaCteVO().setModeloCte_id(getEntradaCteVO().getModeloCteVO().getId());
            getEntradaCteVO().setSituacaoTributariaVO(cboCteSistuacaoTributaria.getSelectionModel().getSelectedItem());
            getEntradaCteVO().setSituacaoTributaria_id(cboCteSistuacaoTributaria.getSelectionModel().getSelectedItem().getId());
            getEntradaCteVO().setTransportadoraVO(cboCteTransportadora.getSelectionModel().getSelectedItem());
            getEntradaCteVO().setTransportadora_id(cboCteTransportadora.getSelectionModel().getSelectedItem().getId());
            getEntradaCteVO().setDataEmissaoCte(Timestamp.valueOf(dtpCteEmissao.getValue().atStartOfDay()));
            System.out.println("txtCteVlrCte.getText().replace(\".\", \"\").replace(\",\", \".\"): [" + txtCteVlrCte.getText().replace(".", "").replace(",", ".") + "]");
            System.out.println("new BigDecimal(txtCteVlrCte.getText().replace(\".\", \"\").replace(\",\", \".\")): [" + new BigDecimal(txtCteVlrCte.getText().replace(".", "").replace(",", ".")) + "]");
            getEntradaCteVO().setVlrCte(new BigDecimal(txtCteVlrCte.getText().replace(".", "").replace(",", ".")));
            getEntradaCteVO().setQtdVolume(Integer.parseInt(txtCteQtdVolume.getText()));
            getEntradaCteVO().setPesoBruto(new BigDecimal(txtCtePesoBruto.getText().replace(".", "").replace(",", ".")));
            getEntradaCteVO().setVlrFreteBruto(new BigDecimal(txtCteVlrBruto.getText().replace(".", "").replace(",", ".")));
            getEntradaCteVO().setVlrImpostoFrete(new BigDecimal(txtCteVlrImposto.getText().replace(".", "").replace(",", ".")));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean guardarDadosCteFiscal() {
        try {
            setEntradaFiscalCteVO(getEntradaFiscalCteVO());
            getEntradaFiscalCteVO().setNumControle(txtCteFiscalControle.getText());
            getEntradaFiscalCteVO().setDocOrigem(txtCteFiscalOrigem.getText());
            getEntradaFiscalCteVO().setTributoSefazAmVO(cboCteFiscalTributo.getSelectionModel().getSelectedItem());
            getEntradaFiscalCteVO().setTributoSefazAM_id(getEntradaFiscalCteVO().getTributoSefazAmVO().getId());
            getEntradaFiscalCteVO().setVlrNfe(new BigDecimal(txtCteFiscalVlrNFe.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalCteVO().setVlrTributo(new BigDecimal(txtCteFiscalVlrTributo.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalCteVO().setVlrMulta(new BigDecimal(txtCteFiscalVlrMulta.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalCteVO().setVlrJuros(new BigDecimal(txtCteFiscalVlrJuros.getText().replace(".", "").replace(",", ".")));
            getEntradaFiscalCteVO().setVlrTaxa(new BigDecimal(txtCteFiscalVlrTaxa.getText().replace(".", "").replace(",", ".")));
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
            if (getEntradaNfeVO().getId() == 0)
                getEntradaNfeVO().setId(new TabEntradaNfeDAO().insertTabEntradaNfeVO(conn, getEntradaNfeVO()));
            else
                new TabEntradaNfeDAO().updateTabEntradaNfeVO(conn, getEntradaNfeVO());

            if (tpnNfeFiscal.isExpanded())
                if (getEntradaFiscalNfeVO().getId() == 0)
                    getEntradaFiscalNfeVO().setId(new TabEntradaFiscalDAO().insertTabEntradaFiscalVO(conn, getEntradaFiscalNfeVO(), true, getEntradaNfeVO().getId()));
                else
                    new TabEntradaFiscalDAO().updateTabEntradaFiscalVO(conn, getEntradaFiscalNfeVO());
            if (tpnCteDetalhe.isExpanded()) {
                if (getEntradaCteVO().getId() == 0)
                    getEntradaCteVO().setId(new TabEntradaCteDAO().insertTabEntradaCteVO(conn, getEntradaCteVO(), getEntradaNfeVO().getId()));
                else
                    new TabEntradaCteDAO().updateTabEntradaCteVO(conn, getEntradaCteVO());
                if (tpnCteFiscal.isExpanded()) {
                    if (getEntradaFiscalCteVO().getId() == 0)
                        getEntradaFiscalCteVO().setId(new TabEntradaFiscalDAO().insertTabEntradaFiscalVO(conn, getEntradaFiscalCteVO(), false, getEntradaCteVO().getId()));
                    else
                        new TabEntradaFiscalDAO().updateTabEntradaFiscalVO(conn, getEntradaFiscalCteVO());
                }
            }
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
