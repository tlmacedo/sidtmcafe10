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
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ControllerCadastroProduto extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

    ObservableList<TabProdutoCodBarraVO> listCodBarraVOObservableList = FXCollections.observableArrayList();
    boolean formValidoAbertura = false;
    public AnchorPane painelViewCadastroProduto;
    public TitledPane tpnCadastroProduto;
    public JFXTextField txtPesquisaProduto;
    public TreeTableView<TabProdutoVO> ttvProduto;
    public Label lblStatus;
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
    public JFXTextField txtPrecoUltimoImpostoSefaz;
    public JFXTextField txtPrecoUltimoFrete;
    public JFXTextField txtComissaoReal;
    public JFXTextField txtLucroLiquido;
    public JFXTextField txtLucratividade;
    public Label lblDataCadastro;
    public Label lblDataCadastroDiff;
    public Label lblDataAtualizacao;
    public Label lblDataAtualizacaoDiff;
    public JFXListView<TabProdutoCodBarraVO> listCodigoBarra;
    public JFXComboBox<FiscalCestNcmVO> cboFiscalCestNcm;
    public JFXTextField txtFiscalGenero;
    public JFXTextField txtFiscalNcm;
    public JFXTextField txtFiscalCest;
    public JFXComboBox<FiscalCstOrigemVO> cboFiscalOrigem;
    public JFXComboBox<FiscalIcmsVO> cboFiscalIcms;
    public JFXComboBox<FiscalPisCofinsVO> cboFiscalPis;
    public JFXComboBox<FiscalPisCofinsVO> cboFiscalCofins;
    public Circle imgCirculo;

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
        listaTarefa.add(new Pair("vinculandoObjetosTabela", "vinculando objetos a tableMoel"));

        listaTarefa.add(new Pair("preencherTabelaProduto", "preenchendo tabela produto"));

        formValidoAbertura = new ServiceSegundoPlano().tarefaAbreCadastro(getTaskCadastroProduto(), listaTarefa.size());
    }

    @Override
    public void fatorarObjetos() {
        ttvProduto.setRowFactory(param -> {
            TreeTableRow<TabProdutoVO> row = new TreeTableRow<TabProdutoVO>() {
                @Override
                protected void updateItem(TabProdutoVO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        getStyleClass().remove("produto-estoque");
                    } else if (item.getDescricao().equals("")) {
                        if (!getStyleClass().contains("produto-estoque"))
                            getStyleClass().add("produto-estoque");
                    } else {
                        getStyleClass().remove("produto-estoque");
                    }
                }
            };
            return row;
        });
        cboFiscalCestNcm.setCellFactory(new Callback<ListView<FiscalCestNcmVO>, ListCell<FiscalCestNcmVO>>() {
            @Override
            public ListCell<FiscalCestNcmVO> call(ListView<FiscalCestNcmVO> param) {
                final ListCell<FiscalCestNcmVO> cell = new ListCell<FiscalCestNcmVO>() {
                    @Override
                    protected void updateItem(FiscalCestNcmVO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
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
        listCodigoBarra.setCellFactory(new Callback<ListView<TabProdutoCodBarraVO>, ListCell<TabProdutoCodBarraVO>>() {
            @Override
            public ListCell<TabProdutoCodBarraVO> call(ListView<TabProdutoCodBarraVO> param) {
                final ListCell<TabProdutoCodBarraVO> cell = new ListCell<TabProdutoCodBarraVO>() {
                    private ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(TabProdutoCodBarraVO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            if (item.getImgCodBarra() != null)
                                imageView.setImage(item.getImgCodBarra());
                            setText(null);
                            setGraphic(imageView);
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
            if (newValue.getValue().getDescricao().equals(""))
                setProdutoVO(newValue.getParent().getValue());
            else
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
                            String tmp = getStatusFormulario().toLowerCase();
                            setStatusFormulario("pesquisa");
                            switch (tmp) {
                                case "incluir":
                                    produtoVOObservableList.add(new TabProdutoDAO().getTabProdutoVO(getProdutoVO().getId()));
                                    break;
                                case "editar":
                                    produtoVOObservableList.set(produtoVOObservableList.indexOf(ttvProduto.getSelectionModel().getSelectedItem().getValue()),
                                            new TabProdutoDAO().getTabProdutoVO(getProdutoVO().getId()));
                                    break;
                            }
                            TabModel.setProdutoVOFilteredList(produtoVOFilteredList = new FilteredList<>(produtoVOObservableList));
                            TabModel.pesquisaProduto(txtPesquisaProduto.getText());
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
                            addCodeBar("");
                        break;
                    case Z:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        if (CODE_KEY_CTRL_Z.match(event) || CHAR_KEY_CTRL_Z.match(event))
                            reverseImageProduto();
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
            TabModel.pesquisaProduto(newValue.toLowerCase().trim());
        });

        txtPesquisaProduto.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvProduto.requestFocus();
            ttvProduto.getSelectionModel().selectFirst();
        });

        cboFiscalCestNcm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!cboFiscalCestNcm.isFocused()) return;
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
            if (newValue == null) return;
            Platform.runLater(() -> {
                txtFiscalNcm.setText(newValue.getNcm());
                txtFiscalCest.setText(newValue.getCest());
            });
        });

        listCodBarraVOObservableList.addListener((ListChangeListener) c -> {
            listCodigoBarra.setItems(listCodBarraVOObservableList.stream()
                    .filter(codBarra -> codBarra.getId() >= 0)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

//        produtoVOObservableList.addListener((ListChangeListener<TabProdutoVO>) c -> {
//            produtoVOFilteredList = new FilteredList<>(produtoVOObservableList);
//            preencherTabelaProduto();
//            atualizaQtdRegistroLocalizado();
//        });

//        produtoVOFilteredList.addListener((ListChangeListener) c -> {
//            atualizaQtdRegistroLocalizado();
//            TabModel.preencherTabelaProduto();
//        });

        txtFiscalNcm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!txtFiscalNcm.isFocused()) return;
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
            if (newValue == null) return;
            Platform.runLater(() -> {
                cboFiscalCestNcm.getItems().clear();
                cboFiscalCestNcm.setItems(new FiscalCestNcmDAO()
                        .getFiscalCestNcmVOList(newValue)
                        .stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
            });
        });

        txtPrecoFabrica.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtPrecoFabrica.isFocused()) return;
                vlrConsumidor();
                vlrLucroBruto();
            }
        });

        txtMargem.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
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
                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtPrecoVenda.isFocused()) return;
                vlrMargem();
                vlrLucroBruto();
            }
        });

        txtComissaoPorc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtComissaoPorc.isFocused()) return;
                vlrComissaoReal();
                vlrLucroBruto();
            }
        });

        txtPrecoUltimoImpostoSefaz.textProperty().addListener((observable, oldValue, newValue) -> {
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
            if (!txtPrecoUltimoImpostoSefaz.isFocused()) return;
            vlrLucroBruto();
        });

        txtPrecoUltimoFrete.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
                if (!txtPrecoUltimoFrete.isFocused()) return;
                vlrLucroBruto();
            }
        });

        imgCirculo.setOnDragOver(event -> {
            if (imgCirculo.isDisabled()) return;
            Dragboard board = event.getDragboard();
            if (board.hasFiles())
                if (Pattern.compile(REGEX_EXTENSAO_IMAGENS).matcher(board.getFiles().get(0).toPath().toString()).find())
                    event.acceptTransferModes(TransferMode.ANY);
        });

        imgCirculo.setOnDragDropped(event -> {
            if (imgCirculo.isDisabled()) return;
            try {
                Dragboard board = event.getDragboard();
                addImageProduto(new Image(new FileInputStream(board.getFiles().get(0))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
        Platform.runLater(() -> {
            if (!formValidoAbertura)
                fechar();
            ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7));
        });
    }

    ObservableList<TabProdutoVO> produtoVOObservableList = FXCollections.observableArrayList();
    FilteredList<TabProdutoVO> produtoVOFilteredList;
    TabProdutoVO produtoVO = new TabProdutoVO();

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

    EventHandler<KeyEvent> eventHandlerCadastroProduto;
    Pattern p;
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
                        case "vinculandoObjetosTabela":
                            TabModel.setLblRegistrosLocalizados(lblRegistrosLocalizados);
                            TabModel.setTtvProduto(ttvProduto);
                            TabModel.setProdutoVOObservableList(produtoVOObservableList);
                            TabModel.setProdutoVOFilteredList(produtoVOFilteredList);
                            TabModel.escutaListaProduto();
                            break;
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
                            TabModel.preencherTabelaProduto();
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
        lblStatus.setText(String.format("[%s]", getStatusFormulario()));
        TabModel.atualizaRegistrosProdutos();
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

    void preencherCboUnidadeComercial() {
        cboUnidadeComercial.getItems().setAll(new ArrayList<>(new SisUnidadeComercialDAO().getSisUnidadeComercialVOList()));
    }

    void preencherCboSituacaoSistema() {
        cboSituacaoSistema.getItems().setAll(new ArrayList<>(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVOList()));
    }

    void preencherCboFiscalCestNcm() {
        cboFiscalCestNcm.getItems().setAll(new ArrayList<>(new FiscalCestNcmDAO().getFiscalCestNcmVOList("")));
    }

    void preencherCboFiscalOrigem() {
        cboFiscalOrigem.getItems().setAll(new ArrayList<>(new FiscalCstOrigemDAO().getFiscalCstOrigemVOList()));
    }

    void preencherCboFiscalIcms() {
        cboFiscalIcms.getItems().setAll(new ArrayList<>(new FiscalIcmsDAO().getFiscalIcmsVOList()));
    }

    void preencherCboFiscalPis() {
        cboFiscalPis.getItems().setAll(new ArrayList<>(new FiscalPisCofinsDAO().getFiscalPisCofinsVOList()));
    }

    void preencherCboFiscalCofins() {
        cboFiscalCofins.getItems().setAll(new ArrayList<>(new FiscalPisCofinsDAO().getFiscalPisCofinsVOList()));
    }

    void carregarListaProduto() {
        produtoVOFilteredList = new FilteredList<>(
                produtoVOObservableList = FXCollections.observableArrayList(new TabProdutoDAO().getTabProdutoVOList())
        );
    }

//    void preencherTabelaProduto() {
//        if (produtoVOFilteredList == null) {
//            carregarListaProduto();
//            pesquisaProduto();
//        }
//        final TreeItem<TabProdutoVO> root = new RecursiveTreeItem<TabProdutoVO>(produtoVOFilteredList, RecursiveTreeObject::getChildren);
//        ttvProduto.getColumns().setAll(TabModel.getColunaIdProduto(), TabModel.getColunaCodigo(),
//                TabModel.getColunaDescricao(), TabModel.getColunaUndCom(), TabModel.getColunaVarejo(),
//                TabModel.getColunaPrecoFabrica(), TabModel.getColunaPrecoVenda(),
//                TabModel.getColunaSituacaoSistema(), TabModel.getColunaQtdEstoque());
//        ttvProduto.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        ttvProduto.setRoot(root);
//        ttvProduto.setShowRoot(false);
//    }
//
//    void pesquisaProduto() {
//        String busca = txtPesquisaProduto.getText().toLowerCase().trim();
//
//        produtoVOFilteredList.setPredicate(produto -> {
//            if (produto.getCodigo().toLowerCase().contains(busca)) return true;
//            if (produto.getDescricao().toLowerCase().contains(busca)) return true;
//            if (produto.getNcm().toLowerCase().contains(busca)) return true;
//            if (produto.getCest().toLowerCase().contains(busca)) return true;
//            if (produto.getCodBarraVOList().stream()
//                    .filter(codBarra -> codBarra.getCodBarra().toLowerCase().contains(busca))
//                    .findFirst().orElse(null) != null) return true;
//            return false;
//        });
//        preencherTabelaProduto();
//    }

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
        txtPeso.setText(getProdutoVO().getPeso().toString());
        txtPrecoFabrica.setText(getProdutoVO().getPrecoFabrica().toString());
        txtPrecoVenda.setText(getProdutoVO().getPrecoVenda().toString());
        txtVarejo.setText(String.valueOf(getProdutoVO().getVarejo()));
        txtPrecoUltimoImpostoSefaz.setText(getProdutoVO().getPrecoUltimoImpostoSefaz().toString());
        txtPrecoUltimoFrete.setText(getProdutoVO().getPrecoUltimoFrete().toString());
        txtComissaoPorc.setText(getProdutoVO().getComissao().toString());
        txtFiscalNcm.setText(getProdutoVO().getNcm());
        txtFiscalCest.setText(getProdutoVO().getCest());
        vlrMargem();
        vlrLucroBruto();
        vlrComissaoReal();

        cboUnidadeComercial.getSelectionModel().select(cboUnidadeComercial.getItems().stream()
                .filter(undComercial -> undComercial.getId() == getProdutoVO().getSisUnidadeComercial_id())
                .findFirst().orElse(null));

        cboSituacaoSistema.getSelectionModel().select(cboSituacaoSistema.getItems().stream()
                .filter(sitSistema -> sitSistema.getId() == getProdutoVO().getSisSituacaoSistema_id())
                .findFirst().orElse(null));

        cboFiscalOrigem.getSelectionModel().select(cboFiscalOrigem.getItems().stream()
                .filter(origem -> origem.getId() == getProdutoVO().getFiscalCSTOrigem_id())
                .findFirst().orElse(null));

        cboFiscalIcms.getSelectionModel().select(cboFiscalIcms.getItems().stream()
                .filter(icms -> icms.getId() == getProdutoVO().getFiscalICMS_id())
                .findFirst().orElse(null));

        cboFiscalPis.getSelectionModel().select(cboFiscalPis.getItems().stream()
                .filter(pis -> pis.getId() == getProdutoVO().getFiscalPIS_id())
                .findFirst().orElse(null));

        cboFiscalCofins.getSelectionModel().select(cboFiscalCofins.getItems().stream()
                .filter(cofins -> cofins.getId() == getProdutoVO().getFiscalCOFINS_id())
                .findFirst().orElse(null));

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
        refreshImageProduto();
    }

    void keyShiftF6() {

    }

    void keyInsert() {
        if (listCodigoBarra.isFocused())
            addCodeBar("");
    }

    void keyDelete() {
        if (listCodigoBarra.isFocused())
            delCodeBar();
    }

    void reverseImageProduto() {
        if (getProdutoVO().getImgProduto() != null && getProdutoVO().getImgProdutoBack() != null) {
            getProdutoVO().setImgProduto(getProdutoVO().getImgProdutoBack());
            getProdutoVO().setImgProdutoBack(null);
        }
        refreshImageProduto();
    }

    void addImageProduto(Image image) {
        if (getProdutoVO().getImgProduto() != null)
            getProdutoVO().setImgProdutoBack(getProdutoVO().getImgProduto());
        getProdutoVO().setImgProduto(ServiceImage.getImageResized(image,
                SIS_PRODUTO_IMAGE_WIDTH, Constants.SIS_PRODUTO_IMAGE_HEIGHT));
        refreshImageProduto();
    }

    void refreshImageProduto() {
        imgCirculo.setFill(FUNDO_RADIAL_GRADIENT);
        if (getProdutoVO().getImgProduto() == null) return;
        imgCirculo.setFill(new ImagePattern(getProdutoVO().getImgProduto()));
    }

    void addCodeBar(String temp) {
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_barcode_add_24dp");
        alertMensagem.setCabecalho(String.format("Adicionar dados [código de barras]"));
        alertMensagem.setPromptText(String.format("%s, qual o código de barras a ser adicionado para o produto: [%s] ?",
                USUARIO_LOGADO_APELIDO, getProdutoVO()));
        String codBarra;
        if ((codBarra = alertMensagem.getRetornoAlert_TextField(
                "barcode", temp)
                .orElse(null)) == null) return;
        if (codBarra.length() < 13)
            codBarra = String.format("%013d", Long.parseLong(codBarra));
        if (!ServiceValidarDado.isEan13Valido(codBarra)) {
            addCodeBar(codBarra);
            return;
        }
        if (buscaDuplicidadeCode(codBarra, true)) return;
        if (!ServiceConsultaWebServices.getProdutoNcmCest_WsEanCosmos(getProdutoVO(), codBarra).equals("")) {
            txtDescricao.setText(getProdutoVO().getDescricao());
            txtFiscalNcm.setText(getProdutoVO().getNcm());
            txtFiscalCest.setText(getProdutoVO().getCest());
        }
        listCodBarraVOObservableList.setAll(getProdutoVO().getCodBarraVOList());
        refreshImageProduto();
    }


    void delCodeBar() {
        TabProdutoCodBarraVO codBarraVO = null;
        codBarraVO = listCodigoBarra.getSelectionModel().getSelectedItem();
        if (codBarraVO == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_barcode_remove_24dp");
        alertMensagem.setCabecalho(String.format("Deletar dados [código de barras]"));
        alertMensagem.setPromptText(String.format("%s, deseja deletar o código de barras: [%s]\ndo produto: [%s] ?",
                USUARIO_LOGADO_APELIDO, codBarraVO, getProdutoVO().getDescricao()));
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        if (codBarraVO.getId() == 0)
            getProdutoVO().getCodBarraVOList().remove(codBarraVO);
        else
            getProdutoVO().getCodBarraVOList().get(getProdutoVO().getCodBarraVOList().indexOf(codBarraVO))
                    .setId(codBarraVO.getId() * (-1));
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
        String dado = "";
        if (!(result = (txtCodigo.getText().length() >= 1 && result == true))) {
            dado += "código";
            txtCodigo.requestFocus();
        }
        if (!(result = (txtDescricao.getText().length() >= 3 && result == true))) {
            dado += "descrição";
            txtDescricao.requestFocus();
        }
        if (!(result = (txtFiscalNcm.getText().length() >= 1 && result == true))) {
            dado += "descrição";
            txtFiscalNcm.requestFocus();
        }
        if (!(result = (Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replace(",", ".")) > 0 && result == true))) {
            dado += "preço de venda";
            txtPrecoVenda.requestFocus();
        }
        if (!(result = ((cboUnidadeComercial.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado += "und comercial";
            cboUnidadeComercial.requestFocus();
        }
        if (!(result = ((cboSituacaoSistema.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado += "sit. sistema";
            cboSituacaoSistema.requestFocus();
        }
        if (!(result = ((cboFiscalOrigem.getSelectionModel().getSelectedIndex() >= 0
                || cboFiscalIcms.getSelectionModel().getSelectedIndex() >= 0
                || cboFiscalPis.getSelectionModel().getSelectedIndex() >= 0
                || cboFiscalCofins.getSelectionModel().getSelectedIndex() >= 0) && result == true))) {
            dado += "fiscal";
            cboFiscalIcms.requestFocus();
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
            getProdutoVO().setPeso(ServiceFormatarDado.getBigDecimalFromTextField(txtPeso.getText()));

            getProdutoVO().setSisUnidadeComercialVO(cboUnidadeComercial.getSelectionModel().getSelectedItem());
            getProdutoVO().setSisUnidadeComercial_id(getProdutoVO().getSisUnidadeComercialVO().getId());
            getProdutoVO().setSisSituacaoSistemaVO(cboSituacaoSistema.getSelectionModel().getSelectedItem());
            getProdutoVO().setSisSituacaoSistema_id(getProdutoVO().getSisSituacaoSistemaVO().getId());
            getProdutoVO().setPrecoFabrica(ServiceFormatarDado.getBigDecimalFromTextField(txtPrecoFabrica.getText()));
            getProdutoVO().setPrecoVenda(ServiceFormatarDado.getBigDecimalFromTextField(txtPrecoVenda.getText()));
            getProdutoVO().setVarejo(Integer.parseInt(txtVarejo.getText().replaceAll("\\D", "")));
            getProdutoVO().setPrecoUltimoImpostoSefaz(ServiceFormatarDado.getBigDecimalFromTextField(txtPrecoUltimoImpostoSefaz.getText()));
            getProdutoVO().setPrecoUltimoFrete(ServiceFormatarDado.getBigDecimalFromTextField(txtPrecoUltimoFrete.getText()));
            getProdutoVO().setComissao(ServiceFormatarDado.getBigDecimalFromTextField(txtComissaoPorc.getText()));
            getProdutoVO().setNcm(txtFiscalNcm.getText().equals("") ? "" : txtFiscalNcm.getText().replaceAll("\\D", ""));
            getProdutoVO().setCest(txtFiscalCest.getText().equals("") ? "" : txtFiscalCest.getText().replaceAll("\\D", ""));
            getProdutoVO().setFiscalCstOrigemVO(cboFiscalOrigem.getSelectionModel().getSelectedItem());
            getProdutoVO().setFiscalIcmsVO(cboFiscalIcms.getSelectionModel().getSelectedItem());
            getProdutoVO().setFiscalPisVO(cboFiscalPis.getSelectionModel().getSelectedItem());
            getProdutoVO().setFiscalCofinsVO(cboFiscalCofins.getSelectionModel().getSelectedItem());
            getProdutoVO().setNfeGenero(txtFiscalGenero.getText().equals("") ? "" : txtFiscalGenero.getText().replaceAll("\\D", ""));
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

            getProdutoVO().getCodBarraVOList().stream().sorted(Comparator.comparing(TabProdutoCodBarraVO::getId))
                    .forEach(codBarra -> {
                        try {
                            if (codBarra.getId() < 0)
                                new TabProdutoCodBarraDAO().deleteTabProdutoCodBarraVO(conn, codBarra.getId(),
                                        getProdutoVO().getId());
                            else if (codBarra.getId() > 0)
                                new TabProdutoCodBarraDAO().updateTabProdutoCodBarraVO(conn, codBarra);
                            else
                                codBarra.setId(new TabProdutoCodBarraDAO().insertTabProdutoCodBarraVO(conn, codBarra,
                                        getProdutoVO().getId()));
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro no código de barras ===>>", ex);
                        }
                    });
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

    void vlrConsumidor() {
        Double prcFabrica = ServiceFormatarDado.getDoubleFromTextField(txtPrecoFabrica.getText());
        Double margem = ServiceFormatarDado.getDoubleFromTextField(txtMargem.getText());
        Double prcConsumidor;
        prcConsumidor = ((prcFabrica * margem) / 100) + prcFabrica;
        txtPrecoVenda.setText(new BigDecimal(prcConsumidor).setScale(2, RoundingMode.HALF_UP).toString());
    }

    void vlrMargem() {
        Double prcFabrica = ServiceFormatarDado.getDoubleFromTextField(txtPrecoFabrica.getText());
        Double prcConsumidor = ServiceFormatarDado.getDoubleFromTextField(txtPrecoVenda.getText());
        Double margem;
        margem = (((prcConsumidor - prcFabrica) * 100) / prcFabrica);
        try {
            txtMargem.setText(new BigDecimal(margem).setScale(2, RoundingMode.HALF_UP).toString());
        } catch (Exception ex) {
            if (ex instanceof NullPointerException)
                txtMargem.setText(BigDecimal.ZERO.setScale(2).toString());
        }
    }

    void vlrLucroBruto() {
        Double prcFabrica = ServiceFormatarDado.getDoubleFromTextField(txtPrecoFabrica.getText());
        Double prcConsumidor = ServiceFormatarDado.getDoubleFromTextField(txtPrecoVenda.getText());
        Double lucroBruto;
        lucroBruto = (prcConsumidor - prcFabrica);
        txtLucroBruto.setText(new BigDecimal(lucroBruto).setScale(2, RoundingMode.HALF_UP).toString());
        vlrLucroLiq();
    }

    void vlrLucroLiq() {
        Double lucroBruto = ServiceFormatarDado.getDoubleFromTextField(txtLucroBruto.getText());
        Double ultimoImposto = ServiceFormatarDado.getDoubleFromTextField(txtPrecoUltimoImpostoSefaz.getText());
        Double ultimoFrete = ServiceFormatarDado.getDoubleFromTextField(txtPrecoUltimoFrete.getText());
        Double comissaoReal = ServiceFormatarDado.getDoubleFromTextField(txtComissaoReal.getText());
        Double lucroLiquido;
        lucroLiquido = (lucroBruto - (ultimoFrete + comissaoReal + ultimoImposto));
        txtLucroLiquido.setText(new BigDecimal(lucroLiquido).setScale(2, RoundingMode.HALF_UP).toString());
        vlrLucratividade();
    }

    void vlrLucratividade() {
        Double prcConsumidor = ServiceFormatarDado.getDoubleFromTextField(txtPrecoVenda.getText());
        Double lucroLiquido = ServiceFormatarDado.getDoubleFromTextField(txtLucroLiquido.getText());
        Double lucratividade;
        lucratividade = ((lucroLiquido / prcConsumidor) * 100);
        try {
            txtLucratividade.setText(new BigDecimal(lucratividade).setScale(2, RoundingMode.HALF_UP).toString());
        } catch (Exception ex) {
            if (ex instanceof NullPointerException)
                txtLucratividade.setText(BigDecimal.ZERO.setScale(2).toString());
        }
    }

    void vlrComissaoReal() {
        Double prcConsumidor = ServiceFormatarDado.getDoubleFromTextField(txtPrecoVenda.getText());
        Double comissaoPorc = ServiceFormatarDado.getDoubleFromTextField(txtComissaoPorc.getText());
        Double comissaoReal;
        comissaoReal = (prcConsumidor * (comissaoPorc / 100));
        txtComissaoReal.setText(new BigDecimal(comissaoReal).setScale(2, RoundingMode.HALF_UP).toString());
    }

}
