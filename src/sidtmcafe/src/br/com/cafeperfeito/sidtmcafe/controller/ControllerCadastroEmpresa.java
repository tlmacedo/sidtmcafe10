package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.model.TabModel;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.*;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroEmpresa;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Pair;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.DTF_DATA;
import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.DTF_DATAHORA;

public class ControllerCadastroEmpresa extends ServiceVariavelSistema implements Initializable, ModelController {

    public AnchorPane painelViewCadastroEmpresa;
    public TitledPane tpnCadastroEmpresa;
    public JFXTextField txtPesquisaEmpresa;
    public TreeTableView<TabEmpresaVO> ttvEmpresa;
    public JFXComboBox cboFiltroPesquisa;
    public Label lblRegistrosLocalizados;
    public TitledPane tpnDadoCadastral;
    public JFXComboBox cboClassificacaoJuridica;
    public JFXTextField txtCNPJ;
    public JFXCheckBox chkIeIsento;
    public JFXTextField txtIE;
    public JFXComboBox<SisSituacaoSistemaVO> cboSituacaoSistema;
    public JFXTextField txtRazao;
    public JFXTextField txtFantasia;
    public JFXCheckBox chkIsCliente;
    public JFXCheckBox chkIsFornecedor;
    public JFXCheckBox chkIsTransportadora;
    public Label lblDataCadastro;
    public Label lblDataCadastroDiff;
    public Label lblDataAtualizacao;
    public Label lblDataAtualizacaoDiff;
    public ListView<TabEnderecoVO> listEndereco;
    public JFXTextField txtEndCEP;
    public JFXTextField txtEndLogradouro;
    public JFXTextField txtEndNumero;
    public JFXTextField txtEndComplemento;
    public JFXTextField txtEndBairro;
    public JFXComboBox<SisUFVO> cboEndUF;
    public JFXComboBox<SisMunicipioVO> cboEndMunicipio;
    public JFXTextField txtEndPontoReferencia;
    public JFXListView<TabEmpresaReceitaFederalVO> listAtividadePrincipal;
    public JFXListView<TabEmpresaReceitaFederalVO> listAtividadeSecundaria;
    public Label lblDataAbertura;
    public Label lblDataAberturaDiff;
    public Label lblNaturezaJuridica;
    public JFXListView listInformacoesReceita;
    public TabPane tpnContatoPrazosCondicoes;
    public JFXListView<TabEmailHomePageVO> listHomePage;
    public JFXListView<TabEmailHomePageVO> listEmail;
    public JFXListView<TabTelefoneVO> listTelefone;
    public JFXListView<TabContatoVO> listContatoNome;
    public JFXListView<TabEmailHomePageVO> listContatoHomePage;
    public JFXListView<TabEmailHomePageVO> listContatoEmail;
    public JFXListView<TabTelefoneVO> listContatoTelefone;

    @Override
    public void fechar() {
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getTabs().remove(ViewCadastroEmpresa.getTab());
        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroEmpresa);
    }

    @Override
    public void criarObjetos() {
        listaTarefa.add(new Pair("criarTabelaEmpresa", "criando tabela empresas"));
    }

    @Override
    public void preencherObjetos() {
        listaTarefa.add(new Pair("preencherCboFiltroPesquisa", "preenchendo filtros pesquisa"));
        listaTarefa.add(new Pair("preencherCboClassificacaoJuridica", "preenchendo classificações jurídicas"));
        listaTarefa.add(new Pair("preencherCboSituacaoSistema", "preenchendo situações do sistema"));
        listaTarefa.add(new Pair("preencherCboEndUF", "preenchendo dados UF"));

        listaTarefa.add(new Pair("carregarTabCargo", "carregando lista cargos"));
        listaTarefa.add(new Pair("carregarSisTipoEndereco", "carregando lista tipo endereço"));
        listaTarefa.add(new Pair("carregarSisTelefoneOperadora", "carregando lista operadoras telefone"));
        listaTarefa.add(new Pair("carregarListaEmpresa", "carregando lista de empresas"));

        listaTarefa.add(new Pair("preencherTabelaEmpresa", "preenchendo tabela empresa"));

        new ServiceSegundoPlano().tarefaAbreCadastroEmpresa(this, listaTarefa);

        formatCNPJ_CPF = new ServiceFormatarDado();
        formatCNPJ_CPF.maskField(txtCNPJ, ServiceFormatarDado.gerarMascara("cnpj", 0, "#"));
        formatIE = new ServiceFormatarDado();
        formatIE.maskField(txtIE, ServiceFormatarDado.gerarMascara("ie", 0, "#"));

    }

    @Override
    public void fatorarObjetos() {
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsCliente());
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsFornecedor());
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsTransportadora());
        listInformacoesReceita.setCellFactory(new Callback<ListView<TabEmpresaReceitaFederalVO>, ListCell<TabEmpresaReceitaFederalVO>>() {
            @Override
            public ListCell<TabEmpresaReceitaFederalVO> call(ListView<TabEmpresaReceitaFederalVO> param) {
                final ListCell<TabEmpresaReceitaFederalVO> cell = new ListCell<TabEmpresaReceitaFederalVO>() {
                    @Override
                    public void updateItem(TabEmpresaReceitaFederalVO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) setText(null);
                        else {
                            String novoTexto = "";
                            for (String det : item.getDetalheReceitaFederal().split(";"))
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

    @Override
    public void escutarTecla() {

        ttvEmpresa.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.intValue() < 0) return;
            setTabEmpresaVO(ttvEmpresa.getTreeItem(newValue.intValue()).getValue());
        });

//        ttvEmpresa.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.booleanValue())
//                if (ttvEmpresa.getSelectionModel().getSelectedItem().getValue() == null
//                        || ttvEmpresa.getSelectionModel().getSelectedIndex() < 0) return;
//            setTabEmpresaVO(ttvEmpresa.getSelectionModel().getSelectedItem().getValue());
//        });

        eventHandlerCadastroEmpresa = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() < 0)
                    return;
                if (!ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().getText().equals(getTituloTab()))
                    return;
                ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().setOnCloseRequest(event1 -> {
                    if (!getStatusFormulario().toLowerCase().equals("pesquisa")) {
                        event1.consume();
                    }
                });
                switch (event.getCode()) {
                    case F1:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        setTabEmpresaVO(new TabEmpresaVO(1));
                        setStatusFormulario("incluir");
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        if (!validarDados()) break;
                        if (buscaDuplicidade(getTabEmpresaVO().getCnpj().replaceAll("\\D", ""))) break;
                        salvarEmpresa();
                        setStatusFormulario("pesquisa");
                        carregarListaEmpresa();
                        pesquisaEmpresa();
                        break;
                    case F3:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        switch (getStatusFormulario().toLowerCase()) {
                            case "incluir":
                                if (new ServiceAlertMensagem("Cancelar inclusão", USUARIO_LOGADO_APELIDO
                                        + ", deseja cancelar inclusão no cadastro de empresa?",
                                        "ic_cadastro_empresas_white_24dp.png").getRetornoAlert_YES_NO().get() == ButtonType.NO)
                                    return;
                                break;
                            case "editar":
                                if (new ServiceAlertMensagem("Cancelar edição", USUARIO_LOGADO_APELIDO
                                        + ", deseja cancelar edição do cadastro de empresa?",
                                        "ic_cadastro_empresas_white_24dp.png").getRetornoAlert_YES_NO().get() == ButtonType.NO)
                                    return;
                                setTabEmpresaVO(ttvEmpresa.getSelectionModel().getSelectedItem().getValue());
                                break;
                            default:
                                return;
                        }
                        setStatusFormulario("pesquisa");
                        break;
                    case F4:
                        if (!getStatusBarTecla().contains(event.getCode().toString()) || !(ttvEmpresa.getSelectionModel().getSelectedIndex() >= 0))
                            break;
                        setStatusFormulario("editar");
                        indexObservableEmpresa = tabEmpresaVOObservableList.indexOf(getTabEmpresaVO());
                        break;
                    case F6:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa") || !(event.isShiftDown())) break;
                        keyShiftF6();
                        break;
                    case F7:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        txtPesquisaEmpresa.requestFocus();
                        break;
                    case F8:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        cboFiltroPesquisa.requestFocus();
                        break;
                    case F12:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
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
                }
            }
        };

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroEmpresa);

        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 0 || newValue.intValue() == oldValue.intValue()) return;
            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(tituloTab))
                ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
        });

        txtPesquisaEmpresa.textProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        cboFiltroPesquisa.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        txtPesquisaEmpresa.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvEmpresa.requestFocus();
            ttvEmpresa.getSelectionModel().selectFirst();
            ttvEmpresa.getFocusModel().focus(0);
        });

        cboClassificacaoJuridica.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 1) {
                txtIE.setPromptText("IE");
                txtRazao.setPromptText("Razão");
                txtFantasia.setPromptText("Fantasia");
                txtCNPJ.setPromptText("C.N.P.J.");
                formatCNPJ_CPF.setMascara(ServiceFormatarDado.gerarMascara("cnpj", 0, "#"));
            } else {
                txtIE.setPromptText("RG");
                txtRazao.setPromptText("Nome");
                txtFantasia.setPromptText("Apelido");
                txtCNPJ.setPromptText("C.P.F.");
                formatCNPJ_CPF.setMascara(ServiceFormatarDado.gerarMascara("cpf", 0, "#"));
            }
            if (txtCNPJ.getText().replaceAll("\\D", "").length() > 0)
                txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(txtCNPJ.getText().replaceAll("\\D", ""), txtCNPJ.getPromptText().toLowerCase().replaceAll(".", "")));
        });

        txtCNPJ.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String valueCnpj = txtCNPJ.getText().replaceAll("\\D", "");
                if (!ServiceValidarDado.isCnpjCpfValido(valueCnpj)) {
                    new ServiceAlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
                            + txtCNPJ.getPromptText() + ": " + txtCNPJ.getText() + " é inválido!",
                            "ic_web_service_err_white_24dp").getRetornoAlert_OK();
                    txtCNPJ.requestFocus();
                    return;
                } else if (buscaDuplicidade(valueCnpj)) {
                    txtCNPJ.requestFocus();
                    return;
                } else {
                    TabEmpresaVO empresaVO = getTabEmpresaVO();
                    //guardarEndereco(getTabEnderecoVOObservableList().get(0));
                    if ((empresaVO = new ServiceConsultaWebServices().getSistuacaoCNPJ_receitaWs(empresaVO, valueCnpj)) == null) {
                        new ServiceAlertMensagem("Dado não localizada!", USUARIO_LOGADO_APELIDO + ", o "
                                + "C.N.P.J.: " + txtCNPJ.getText() + " não foi localizado na base de dados!",
                                "ic_web_service_err_white_24dp").getRetornoAlert_OK();
                        txtEndCEP.requestFocus();
                        return;
                    } else {
                        setTabEmpresaVO(empresaVO);
                        txtCNPJ.requestFocus();
                    }

                }
            }
        });

        listEndereco.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!getStatusFormulario().toLowerCase().equals("pesquisa"))
                if ((oldValue.intValue() >= 0) && (newValue.intValue() != oldValue.intValue()) && (newValue.intValue() >= 0))
                    guardarEndereco(idEnderecoAtual);
            if (newValue == null || newValue.intValue() < 0) return;
            exibirDadosEndereco();
        });

        txtEndCEP.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String valueCep = txtEndCEP.getText().replaceAll("\\D", "");
                if (valueCep.length() != 8) {
                    new ServiceAlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
                            + "CEP: " + txtEndCEP.getText() + " é inválido!",
                            "ic_web_service_err_white_24dp").getRetornoAlert_OK();
                    txtEndCEP.requestFocus();
                    return;
                } else {
                    TabEnderecoVO enderecoVO = getTabEnderecoVOObservableList().get(idEnderecoAtual);
                    if ((enderecoVO = new ServiceConsultaWebServices().getEnderecoCep_postmon(new Pair<>(enderecoVO, valueCep))) == null) {
                        new ServiceAlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
                                + "CEP: " + txtEndCEP.getText() + " não foi localizado na base de dados!",
                                "ic_web_service_err_white_24dp").getRetornoAlert_OK();
                        txtEndCEP.requestFocus();
                        return;
                    } else {
                        getTabEnderecoVOObservableList().set(idEnderecoAtual, enderecoVO);
                        //exibirDadosEndereco();
                        txtEndNumero.requestFocus();
                    }
                }
            }
        });

        chkIeIsento.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
            txtIE.setDisable(newValue);
            if (newValue)
                txtIE.setText("");
            else if (getTabEmpresaVO() != null)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), "ie" + getTabEnderecoVOObservableList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
        });

        cboEndUF.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            cboEndMunicipio.getItems().clear();
            if (newValue == null || newValue.intValue() < 0) return;
            cboEndMunicipio.getItems().setAll(cboEndUF.getSelectionModel().getSelectedItem().getMunicipioVOList());
            String mascUF = "ie" + cboEndUF.getSelectionModel().getSelectedItem().getSigla();
            formatIE.setMascara(ServiceFormatarDado.gerarMascara(mascUF, 0, "#"));

            if (getTabEmpresaVO() != null && getTabEmpresaVO().getIe().length() > 0)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), mascUF));
        });

        listContatoNome.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.intValue() < 0) return;
            setTabContatoVO(listContatoNome.getItems().get(newValue.intValue()));
        });

        tabEnderecoVOObservableList.addListener((ListChangeListener<TabEnderecoVO>) c -> {
            listEndereco.getItems().clear();
            if (getTabEnderecoVOObservableList().size() == 0) return;
            getTabEnderecoVOObservableList().stream()
                    .filter(end -> end.getId() >= 0)
                    .forEach(end -> {
                        listEndereco.getItems().add(end);
                    });
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) {
                listEndereco.getSelectionModel().selectFirst();
            } else {
                while (c.next())
                    if (c.wasAdded())
                        listEndereco.getSelectionModel().selectLast();
                    else if (c.wasRemoved())
                        listEndereco.getSelectionModel().selectFirst();
            }
        });

        tabEmailHomePageVOObservableList.addListener((ListChangeListener<TabEmailHomePageVO>) c -> {
            listHomePage.getItems().clear();
            listEmail.getItems().clear();
            if (getTabEmailHomePageVOObservableList().size() == 0) return;
            getTabEmailHomePageVOObservableList().stream()
                    .filter(emailHome -> emailHome.getId() >= 0)
                    .forEach(emailHome -> {
                        if (emailHome.isIsEmail())
                            listEmail.getItems().add(emailHome);
                        else
                            listHomePage.getItems().add(emailHome);
                    });
        });

        tabTelefoneVOObservableList.addListener((ListChangeListener<TabTelefoneVO>) c -> {
            listTelefone.getItems().clear();
            if (getTabTelefoneVOObservableList().size() == 0) return;
            getTabTelefoneVOObservableList().stream()
                    .filter(tel -> tel.getId() >= 0)
                    .forEach(tel -> {
                        listTelefone.getItems().add(tel);
                    });
        });

        tabContatoVOObservableList.addListener((ListChangeListener<TabContatoVO>) c -> {
            listContatoNome.getItems().clear();
            if (getTabContatoVOObservableList().size() == 0) {
                setTabContatoVO(null);
                return;
            }
            getTabContatoVOObservableList().stream()
                    .filter(contato -> contato.getId() >= 0)
                    .forEach(contato -> {
                        listContatoNome.getItems().add(contato);
                    });
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) {
                listContatoNome.getSelectionModel().selectFirst();
            } else {
                while (c.next())
                    if (c.wasRemoved())
                        listContatoNome.getSelectionModel().selectFirst();
                    else if (c.wasAdded())
                        listContatoNome.getSelectionModel().selectLast();
            }

        });

        tabContatoEmailHomePageVOObservableList.addListener((ListChangeListener<TabEmailHomePageVO>) c -> {
            listContatoHomePage.getItems().clear();
            listContatoEmail.getItems().clear();
            if (getTabContatoEmailHomePageVOObservableList().size() == 0) return;
            getTabContatoEmailHomePageVOObservableList().stream()
                    .filter(contatoHome -> contatoHome.getId() >= 0)
                    .forEach(contatoEmailHome -> {
                        if (contatoEmailHome.isIsEmail())
                            listContatoEmail.getItems().add(contatoEmailHome);
                        else
                            listContatoHomePage.getItems().add(contatoEmailHome);
                    });
        });

        tabContatoTelefoneVOObservableList.addListener((ListChangeListener<TabTelefoneVO>) c -> {
            listContatoTelefone.getItems().clear();
            if (getTabContatoTelefoneVOObservableList().size() == 0) return;
            getTabContatoTelefoneVOObservableList().stream()
                    .filter(contatoTel -> contatoTel.getId() >= 0)
                    .forEach(contatoTel -> {
                        listContatoTelefone.getItems().add(contatoTel);
                    });
        });

        tabEmpresaReceitaFederalVOObservableList.addListener((ListChangeListener<TabEmpresaReceitaFederalVO>) c -> {
            listAtividadePrincipal.getItems().clear();
            listAtividadeSecundaria.getItems().clear();
            listInformacoesReceita.getItems().clear();
            if (getTabEmpresaReceitaFederalVOObservableList().size() == 0) return;
            getTabEmpresaReceitaFederalVOObservableList().stream()
                    .filter(receita -> receita.getId() >= 0)
                    .forEach(receita -> {
                        if (receita.getIsAtividadePrincipal() == 1)
                            listAtividadePrincipal.getItems().add(receita);
                        else if (receita.getIsAtividadePrincipal() < 1)
                            listAtividadeSecundaria.getItems().add(receita);
                        else if (receita.getIsAtividadePrincipal() > 1)
                            listInformacoesReceita.getItems().add(receita);
                    });
        });

    }

    @Override
    @SuppressWarnings("Duplicates")
    public void initialize(URL location, ResourceBundle resources) {
        setTituloTab(ViewCadastroEmpresa.getTituloJanela());
        criarObjetos();
        preencherObjetos();
        escutarTecla();
        fatorarObjetos();

        ServiceCampoPersonalizado.fieldMask(painelViewCadastroEmpresa);

        Platform.runLater(() -> {
            setStatusFormulario("Pesquisa");
            ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7));
        });
    }

    int idEnderecoAtual = 0;
    int qtdRegistrosLocalizados = 0;
    int indexObservableEmpresa = 0;
    String tituloTab = ViewCadastroEmpresa.getTituloJanela();
    EventHandler<KeyEvent> eventHandlerCadastroEmpresa;
    String statusFormulario, statusBarTecla;

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";


    boolean isEndereco, isEmpresa, isEmail, isHome, isContato, isTelefone;
    ServiceFormatarDado formatCNPJ_CPF, formatIE;
    List<Pair> listaTarefa = new ArrayList<>();
    ObservableList<TabEmpresaVO> tabEmpresaVOObservableList;
    FilteredList<TabEmpresaVO> tabEmpresaVOFilteredList;
    TabEmpresaVO tabEmpresaVO;
    TabContatoVO tabContatoVO;
    ObservableList<TabEnderecoVO> tabEnderecoVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> tabEmailHomePageVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabTelefoneVO> tabTelefoneVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabContatoVO> tabContatoVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> tabContatoEmailHomePageVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabTelefoneVO> tabContatoTelefoneVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOObservableList = FXCollections.observableArrayList();

    List<SisUFVO> sisUFVOList;
    List<SisMunicipioVO> sisMunicipioVOList;
    List<SisSituacaoSistemaVO> sisSituacaoSistemaVOList;
    List<SisCargoVO> sisCargoVOList;
    List<SisTipoEnderecoVO> sisTipoEnderecoVOList;
    List<SisTelefoneOperadoraVO> sisTelefoneOperadoraVOList;

    public int getQtdRegistrosLocalizados() {
        return qtdRegistrosLocalizados;
    }

    public void setQtdRegistrosLocalizados(int qtdRegistrosLocalizados) {
        this.qtdRegistrosLocalizados = qtdRegistrosLocalizados;
        atualizaQtdRegistroLocalizado();
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
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroEmpresa.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
                clearFieldDadoCadastral((AnchorPane) tpnDadoCadastral.getContent());
                cboClassificacaoJuridica.requestFocus();
                cboClassificacaoJuridica.getSelectionModel().selectFirst();
                statusBarTecla = STATUS_BAR_TECLA_INCLUIR;
                break;
            case "editar":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroEmpresa.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
                try {
                    setTabEmpresaVO(getTabEmpresaVO().clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                txtCNPJ.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_EDITAR;
                break;
            case "pesquisa":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroEmpresa.getContent(), false);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), true);
                clearFieldDadoCadastral((AnchorPane) tpnDadoCadastral.getContent());
                txtPesquisaEmpresa.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }

    void atualizaQtdRegistroLocalizado() {
        lblRegistrosLocalizados.setText("[" + getStatusFormulario() + "] " + getQtdRegistrosLocalizados() + " registro(s) localizado(s).");
    }

    void clearFieldDadoCadastral(AnchorPane anchorPane) {
        ServiceCampoPersonalizado.fieldClear(anchorPane);
    }

    public TabEmpresaVO getTabEmpresaVO() {
        return tabEmpresaVO;
    }

    public void setTabEmpresaVO(TabEmpresaVO tabEmpresaVO) {
        if (tabEmpresaVO == null)
            tabEmpresaVO = new TabEmpresaVO();
        this.tabEmpresaVO = tabEmpresaVO;
//        System.out.println("\n\n\ngetTabEmpresaVO: " + getTabEmpresaVO());
//        System.out.println("getTabEnderecoVOList: " + getTabEmpresaVO().getTabEnderecoVOList());
        setTabEnderecoVOObservableList(getTabEmpresaVO().getTabEnderecoVOList());
//        System.out.println("getTabEmailHomePageVOList: " + getTabEmpresaVO().getTabEmailHomePageVOList());
        setTabEmailHomePageVOObservableList(getTabEmpresaVO().getTabEmailHomePageVOList());
//        System.out.println("getTabTelefoneVOList: " + getTabEmpresaVO().getTabTelefoneVOList());
        setTabTelefoneVOObservableList(getTabEmpresaVO().getTabTelefoneVOList());
//        System.out.println("getTabContatoVOList: " + getTabEmpresaVO().getTabContatoVOList());
        setTabContatoVOObservableList(getTabEmpresaVO().getTabContatoVOList());
//        System.out.println("getTabEmpresaReceitaFederalVOList: " + getTabEmpresaVO().getTabEmpresaReceitaFederalVOList());
        setTabEmpresaReceitaFederalVOObservableList(getTabEmpresaVO().getTabEmpresaReceitaFederalVOList());
        exibirDadosEmpresa();
    }

    public ObservableList<TabEnderecoVO> getTabEnderecoVOObservableList() {
        return tabEnderecoVOObservableList;
    }

    public void setTabEnderecoVOObservableList(List<TabEnderecoVO> tabEnderecoVOObservableList) {
        if (tabEnderecoVOObservableList.size() == 0 || tabEnderecoVOObservableList == null)
            tabEnderecoVOObservableList = new ArrayList<>();
        this.tabEnderecoVOObservableList.setAll(FXCollections.observableArrayList(tabEnderecoVOObservableList));
    }

    public ObservableList<TabEmailHomePageVO> getTabEmailHomePageVOObservableList() {
        return tabEmailHomePageVOObservableList;
    }

    public void setTabEmailHomePageVOObservableList(List<TabEmailHomePageVO> tabEmailHomePageVOObservableList) {
        if (tabEmailHomePageVOObservableList.size() == 0 || tabEmailHomePageVOObservableList == null)
            tabEmailHomePageVOObservableList = new ArrayList<>();
        this.tabEmailHomePageVOObservableList.setAll(FXCollections.observableArrayList(tabEmailHomePageVOObservableList));
    }

    public ObservableList<TabTelefoneVO> getTabTelefoneVOObservableList() {
        return tabTelefoneVOObservableList;
    }

    public void setTabTelefoneVOObservableList(List<TabTelefoneVO> tabTelefoneVOObservableList) {
        if (tabTelefoneVOObservableList.size() == 0 || tabTelefoneVOObservableList == null)
            tabTelefoneVOObservableList = new ArrayList<>();
        this.tabTelefoneVOObservableList.setAll(FXCollections.observableArrayList(tabTelefoneVOObservableList));
    }

    public ObservableList<TabContatoVO> getTabContatoVOObservableList() {
        return tabContatoVOObservableList;
    }

    public void setTabContatoVOObservableList(List<TabContatoVO> tabContatoVOObservableList) {
        if (tabContatoVOObservableList.size() == 0 || tabContatoVOObservableList == null)
            tabContatoVOObservableList = new ArrayList<>();
        this.tabContatoVOObservableList.setAll(FXCollections.observableArrayList(tabContatoVOObservableList));
    }

    public TabContatoVO getTabContatoVO() {
        return tabContatoVO;
    }

    public void setTabContatoVO(TabContatoVO tabContatoVO) {
        if (tabContatoVO == null) {
            tabContatoVO = new TabContatoVO();
        }
        this.tabContatoVO = tabContatoVO;
//        System.out.println("\ngetTabContatoVO: " + getTabContatoVO());
//        System.out.println("Contato.getTabEmailHomePageVOList: " + getTabContatoVO().getTabEmailHomePageVOList());
        setTabContatoEmailHomePageVOObservableList(getTabContatoVO().getTabEmailHomePageVOList());
//        System.out.println("Contato.getTabTelefoneVOList: " + getTabContatoVO().getTabTelefoneVOList());
        setTabContatoTelefoneVOObservableList(getTabContatoVO().getTabTelefoneVOList());
    }

    public ObservableList<TabEmailHomePageVO> getTabContatoEmailHomePageVOObservableList() {
        return tabContatoEmailHomePageVOObservableList;
    }

    public void setTabContatoEmailHomePageVOObservableList(List<TabEmailHomePageVO> tabContatoEmailHomePageVOObservableList) {
        if (tabContatoEmailHomePageVOObservableList.size() == 0 || tabContatoEmailHomePageVOObservableList == null)
            tabContatoEmailHomePageVOObservableList = new ArrayList<>();
        this.tabContatoEmailHomePageVOObservableList.setAll(FXCollections.observableArrayList(tabContatoEmailHomePageVOObservableList));
    }

    public ObservableList<TabTelefoneVO> getTabContatoTelefoneVOObservableList() {
        return tabContatoTelefoneVOObservableList;
    }

    public void setTabContatoTelefoneVOObservableList(List<TabTelefoneVO> tabContatoTelefoneVOObservableList) {
        if (tabContatoTelefoneVOObservableList.size() == 0 || tabContatoTelefoneVOObservableList == null)
            tabContatoTelefoneVOObservableList = new ArrayList<>();
        this.tabContatoTelefoneVOObservableList.setAll(FXCollections.observableArrayList(tabContatoTelefoneVOObservableList));
    }

    public ObservableList<TabEmpresaReceitaFederalVO> getTabEmpresaReceitaFederalVOObservableList() {
        return tabEmpresaReceitaFederalVOObservableList;
    }

    public void setTabEmpresaReceitaFederalVOObservableList(List<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOObservableList) {
        if (tabEmpresaReceitaFederalVOObservableList.size() == 0 || tabEmpresaReceitaFederalVOObservableList == null)
            tabEmpresaReceitaFederalVOObservableList = new ArrayList<>();
        this.tabEmpresaReceitaFederalVOObservableList.setAll(FXCollections.observableArrayList(tabEmpresaReceitaFederalVOObservableList));
    }

    public void carregarListaEmpresa() {
        tabEmpresaVOFilteredList = new FilteredList<>(tabEmpresaVOObservableList = FXCollections.observableArrayList(new TabEmpresaDAO().getTabEmpresaVOList(false)), empresa -> true);
    }

    public void preencherCboFiltroPesquisa() {
        cboFiltroPesquisa.getItems().clear();
        cboFiltroPesquisa.getItems().add(0, "");
        cboFiltroPesquisa.getItems().add(1, "Clientes");
        cboFiltroPesquisa.getItems().add(2, "Fornecedores");
        cboFiltroPesquisa.getItems().add(3, "Transportadoras");
        cboFiltroPesquisa.getSelectionModel().selectFirst();
    }

    public void preencherCboClassificacaoJuridica() {
        cboClassificacaoJuridica.getItems().clear();
        cboClassificacaoJuridica.getItems().add(0, "FÍSICA");
        cboClassificacaoJuridica.getItems().add(1, "JURÍDICA");
        cboClassificacaoJuridica.getSelectionModel().select(1);
    }

    public void preencherCboSituacaoSistema() {
        cboSituacaoSistema.getItems().clear();
        if ((sisSituacaoSistemaVOList = new ArrayList<>(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVOList())) == null)
            return;
        cboSituacaoSistema.getItems().setAll(sisSituacaoSistemaVOList);
    }

    public void preencherCboEndUF() {
        cboEndUF.getItems().clear();
        if ((sisUFVOList = new ArrayList<>(new SisUFDAO().getSisUFVOList_DetMunicipios())) == null)
            return;
        cboEndUF.getItems().setAll(sisUFVOList);
    }

    void pesquisaEmpresa() {
        String busca = txtPesquisaEmpresa.getText().toLowerCase().trim();
        int filtro = cboFiltroPesquisa.getSelectionModel().getSelectedIndex();
        if (busca.length() == 0)
            tabEmpresaVOFilteredList.setPredicate(empresa -> true);
        else
            tabEmpresaVOFilteredList.setPredicate(empresa -> {
                if (filtro > 0) {
                    if (filtro == 1 && !empresa.isIsCliente()) return false;
                    if (filtro == 2 && !empresa.isIsFornecedor()) return false;
                    if (filtro == 3 && !empresa.isIsTransportadora()) return false;
                }
                if (empresa.getCnpj().contains(busca)) return true;
                if (empresa.getIe().contains(busca)) return true;
                if (empresa.getRazao().toLowerCase().contains(busca)) return true;
                if (empresa.getFantasia().toLowerCase().contains(busca)) return true;
                if (empresa.getTabEnderecoVOList().get(0).getCep().toLowerCase().contains(busca)) return true;
                if (empresa.getTabEnderecoVOList().get(0).getLogradouro().toLowerCase().contains(busca)) return true;
                if (empresa.getTabEnderecoVOList().get(0).getNumero().toLowerCase().contains(busca)) return true;
                if (empresa.getTabEnderecoVOList().get(0).getComplemento().toLowerCase().contains(busca)) return true;
                if (empresa.getTabEnderecoVOList().get(0).getBairro().toLowerCase().contains(busca)) return true;
                return false;
            });
        preencherTabelaEmpresa();
    }

    public void preencherTabelaEmpresa() {
        if (tabEmpresaVOFilteredList == null)
            pesquisaEmpresa();
        setQtdRegistrosLocalizados(tabEmpresaVOFilteredList.size());
        final TreeItem<TabEmpresaVO> root = new RecursiveTreeItem<TabEmpresaVO>(tabEmpresaVOFilteredList, RecursiveTreeObject::getChildren);
        ttvEmpresa.getColumns().setAll(TabModel.getColunaIdEmpresa(), TabModel.getColunaCnpj(), TabModel.getColunaIe(),
                TabModel.getColunaRazao(), TabModel.getColunaFantasia(), TabModel.getColunaEndereco(),
                TabModel.getColunaIsCliente(), TabModel.getColunaIsFornecedor(), TabModel.getColunaIsTransportadora());
        ttvEmpresa.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ttvEmpresa.setRoot(root);
        ttvEmpresa.setShowRoot(false);
    }

    void isEmpresaIsEmail() {
        isEndereco = (listEndereco.isFocused());
        isEmail = (listEmail.isFocused() || listContatoEmail.isFocused());
        isHome = (listHomePage.isFocused() || listContatoHomePage.isFocused());
        isEmpresa = (listEmail.isFocused() || listHomePage.isFocused() || listTelefone.isFocused());
        isContato = (listContatoNome.isFocused() || listContatoEmail.isFocused() || listContatoHomePage.isFocused() || listContatoTelefone.isFocused());
        isTelefone = (listTelefone.isFocused() || listContatoTelefone.isFocused());

    }

    public void carregarTabCargo() {
        sisCargoVOList = new ArrayList<>(new SisCargoDAO().getSisCargoVOList());
    }

    public void carregarSisTipoEndereco() {
        sisTipoEnderecoVOList = new ArrayList<>(new SisTipoEnderecoDAO().getSisTipoEnderecoVOList());
    }

    public void carregarSisTelefoneOperadora() {
        sisTelefoneOperadoraVOList = new ArrayList<>(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVOList());
    }

    void exibirDadosEmpresa() {
        if (getTabEmpresaVO() == null) return;
        if (getTabEmpresaVO().isIsEmpresa()) {
            cboClassificacaoJuridica.getSelectionModel().select(1);
            txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getCnpj(), "cnpj"));
            txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), "ie" + getTabEnderecoVOObservableList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
        } else {
            cboClassificacaoJuridica.getSelectionModel().selectFirst();
            txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getCnpj(), "cpf"));
            txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), "ie"));
        }
        chkIeIsento.setSelected(getTabEmpresaVO().isIeIsento());

        cboSituacaoSistema.getSelectionModel().select(getTabEmpresaVO().getSisSituacaoSistemaVO());
        txtRazao.setText(getTabEmpresaVO().getRazao());
        txtFantasia.setText(getTabEmpresaVO().getFantasia());
        chkIsCliente.setSelected(getTabEmpresaVO().isIsCliente());
        chkIsFornecedor.setSelected(getTabEmpresaVO().isIsFornecedor());
        chkIsTransportadora.setSelected(getTabEmpresaVO().isIsTransportadora());

        lblNaturezaJuridica.setText("Natureza Júridica: " + getTabEmpresaVO().getNaturezaJuridica());
        lblDataAbertura.setText("data abertura: ");
        lblDataAberturaDiff.setText("tempo de abertura: ");
        if (getTabEmpresaVO().getDataAbertura() != null) {
            lblDataAbertura.setText("data abertura: " + getTabEmpresaVO().getDataAbertura().toLocalDate().format(DTF_DATA));
            lblDataAberturaDiff.setText("tempo de abertura: " + ServiceDataHora.getIntervaloData(getTabEmpresaVO().getDataAbertura().toLocalDate(), null));
        }

        lblDataCadastro.setText("data cadastro: ");
        lblDataCadastroDiff.setText("tempo de cadastro: ");
        lblDataAtualizacao.setText("data atualização: ");
        lblDataAtualizacaoDiff.setText("tempo de atualização: ");
        if (getTabEmpresaVO().getDataCadastro() != null) {//if (!getStatusFormulario().toLowerCase().equals("incluir")) {
            lblDataCadastro.setText("data cadastro: " + getTabEmpresaVO().getDataCadastro().toLocalDateTime().format(DTF_DATAHORA) + " [" + getTabEmpresaVO().getUsuarioCadastroVO().getApelido() + "]");
            lblDataCadastroDiff.setText("tempo de cadastro: " + ServiceDataHora.getIntervaloData(getTabEmpresaVO().getDataCadastro().toLocalDateTime().toLocalDate(), null));

            if (getTabEmpresaVO().getDataAtualizacao() != null) {
                lblDataAtualizacao.setText("data atualização: " + getTabEmpresaVO().getDataAtualizacao().toLocalDateTime().format(DTF_DATAHORA) + " [" + getTabEmpresaVO().getUsuarioAtualizacaoVO().getApelido() + "]");
                lblDataAtualizacaoDiff.setText("tempo de atualização: " + ServiceDataHora.getIntervaloData(getTabEmpresaVO().getDataAtualizacao().toLocalDateTime().toLocalDate(), null));
            }
        }
    }

    void exibirDadosEndereco() {
        if (getTabEnderecoVOObservableList() == null) {
            limparEndereco();
            return;
        }
        idEnderecoAtual = getTabEnderecoVOObservableList().indexOf(listEndereco.getSelectionModel().getSelectedItem());
        TabEnderecoVO enderecoVO = getTabEnderecoVOObservableList().get(idEnderecoAtual);
        cboEndUF.getSelectionModel().select(enderecoVO.getSisMunicipioVO().getUfVO());
        txtEndCEP.setText(ServiceFormatarDado.getValorFormatado(enderecoVO.getCep(), "cep"));
        txtEndLogradouro.setText(enderecoVO.getLogradouro());
        txtEndNumero.setText(enderecoVO.getNumero());
        txtEndComplemento.setText(enderecoVO.getComplemento());
        txtEndBairro.setText(enderecoVO.getBairro());
        txtEndPontoReferencia.setText(enderecoVO.getPontoReferencia());
        cboEndMunicipio.getSelectionModel().select(enderecoVO.getSisMunicipioVO());
    }

    void limparEndereco() {
        txtEndCEP.setText("");
        txtEndLogradouro.setText("");
        txtEndNumero.setText("");
        txtEndComplemento.setText("");
        txtEndBairro.setText("");
        txtEndPontoReferencia.setText("");
        cboEndUF.getSelectionModel().selectFirst();
    }

    void limparContato() {
        listContatoHomePage.getItems().clear();
        listContatoEmail.getItems().clear();
        listContatoTelefone.getItems().clear();
    }

    boolean validarDados() {
        if (validarDetalheEmpresa() && validarEnderecoPrincipal()) return true;
        return false;
    }

    boolean buscaDuplicidade(String busca) {
        int idBusca;
        try {
            idBusca = new TabEmpresaDAO().getTabEmpresaVO_Simples(busca).getId();
        } catch (Exception ex) {
            idBusca = -1;
        }

        if (idBusca == -1)
            return false;
        if (idBusca != getTabEmpresaVO().getId()) {
            new ServiceAlertMensagem("Empresa duplicada", USUARIO_LOGADO_APELIDO + ", o "
                    + txtCNPJ.getPromptText() + ": " + txtCNPJ.getText() + " já existe no banco de dados!",
                    "ic_web_service_err_white_24dp").getRetornoAlert_OK();
            txtCNPJ.requestFocus();
            return true;
        }
        return false;
    }

    boolean validarDetalheEmpresa() {
        boolean result = true;
        if (!(result = ServiceValidarDado.isCnpjCpfValido(txtCNPJ.getText().replaceAll("\\D", ""))))
            txtCNPJ.requestFocus();
        if (!(result = (txtRazao.getText().length() >= 3 && result == true)))
            txtRazao.requestFocus();
        if (!(result = (txtFantasia.getText().length() >= 3 && result == true)))
            txtRazao.requestFocus();

        if (txtIE.getText().length() == 0) chkIeIsento.isSelected();
        else chkIeIsento.setSelected(false);

        if (!(result = ((chkIsCliente.isSelected() || chkIsFornecedor.isSelected() || chkIsTransportadora.isSelected()) && result == true)))
            chkIsCliente.requestFocus();

        if (!result)
            new ServiceAlertMensagem("Dados inválido!",
                    USUARIO_LOGADO_APELIDO + ", precisa de dados válidos para empresa",
                    "ic_dados_invalidos_white_24dp.png").getRetornoAlert_OK();
        else result = guardarEmpresa();
        return result;
    }

    boolean validarEnderecoPrincipal() {
        boolean result = true;

        for (int i = 0; i < getTabEmpresaReceitaFederalVOObservableList().size(); i++)
            if (getTabEmpresaReceitaFederalVOObservableList().get(i).getStr_Key().toLowerCase().equals("situacao"))
                if (!getTabEmpresaReceitaFederalVOObservableList().get(i).getStr_Value().equals("ativa")) {
                    limparEndereco();
                    return true;
                }

        if (!(result = (txtEndCEP.getText().replaceAll("\\D", "").length() == 8 && result == true)))
            txtEndCEP.requestFocus();

        if (!(result = (txtEndLogradouro.getText().length() >= 3 && result == true)))
            txtEndLogradouro.requestFocus();

        if (!(result = (txtEndNumero.getText().length() >= 1 && result == true)))
            txtEndNumero.requestFocus();

        if (!(result = (txtEndBairro.getText().length() >= 3 && result == true)))
            txtEndBairro.requestFocus();

        if (!result)
            new ServiceAlertMensagem("Endereço inválido!",
                    USUARIO_LOGADO_APELIDO + ", precisa endereço válido para empresa",
                    "ic_endereco_invalido_white_24dp.png").getRetornoAlert_OK();
        else result = guardarEndereco(idEnderecoAtual);
        return result;
    }

    boolean guardarEndereco(int index) {
        TabEnderecoVO endAnt;
        try {
            if (index < 0) return false;
            endAnt = getTabEnderecoVOObservableList().get(index);
            endAnt.setCep(txtEndCEP.getText().replaceAll("\\D", ""));
            endAnt.setLogradouro(txtEndLogradouro.getText());
            endAnt.setNumero(txtEndNumero.getText());
            endAnt.setComplemento(txtEndComplemento.getText());
            endAnt.setBairro(txtEndBairro.getText());
            endAnt.setPontoReferencia(txtEndPontoReferencia.getText());
            endAnt.setSisMunicipioVO(cboEndMunicipio.getSelectionModel().getSelectedItem());
            endAnt.setSisMunicipio_id(cboEndMunicipio.getSelectionModel().getSelectedItem().getId());
            //getTabEnderecoVOObservableList().set(index, endAnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean guardarEmpresa() {
        try {
            getTabEmpresaVO().setIsEmpresa(cboClassificacaoJuridica.getSelectionModel().getSelectedIndex() == 1);
            getTabEmpresaVO().setCnpj(txtCNPJ.getText().replaceAll("\\D", ""));
            getTabEmpresaVO().setIe(txtIE.getText().replaceAll("\\D", ""));
            getTabEmpresaVO().setRazao(txtRazao.getText());
            getTabEmpresaVO().setFantasia(txtFantasia.getText());
            getTabEmpresaVO().setIsCliente(chkIsCliente.isSelected());
            getTabEmpresaVO().setIsFornecedor(chkIsFornecedor.isSelected());
            getTabEmpresaVO().setIsTransportadora(chkIsTransportadora.isSelected());
            getTabEmpresaVO().setSisSituacaoSistemaVO(cboSituacaoSistema.getSelectionModel().getSelectedItem());
            getTabEmpresaVO().setSisSituacaoSistema_id(cboSituacaoSistema.getSelectionModel().getSelectedItem().getId());
            getTabEmpresaVO().setUsuarioCadastro_id(Integer.parseInt(USUARIO_LOGADO_ID));
            getTabEmpresaVO().setUsuarioAtualizacao_id(Integer.parseInt(USUARIO_LOGADO_ID));

            Pattern p = Pattern.compile("\\d{2}/\\d{2}\\d{4}");
            Matcher m = p.matcher(lblDataAbertura.getText());
            while (m.find())
                getTabEmpresaVO().setDataAbertura(Date.valueOf(m.group()));

            getTabEmpresaVO().setNaturezaJuridica(lblNaturezaJuridica.getText().substring(19));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    boolean retornoDelete(ServiceAlertMensagem alertMensagem) {
        return (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.YES);
    }

    void keyDelete() {
        isEmpresaIsEmail();
        int index;
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
        if (listEndereco.isFocused() && listEndereco.getSelectionModel().getSelectedItem() != null) {
            TabEnderecoVO enderecoVO = listEndereco.getSelectionModel().getSelectedItem();
            if (enderecoVO.getSisTipoEndereco_id() == 1) {
                new ServiceAlertMensagem("proteção de dados!",
                        USUARIO_LOGADO_APELIDO + ", o endereço principal não pode ser vlrIdDeletado!",
                        "ic_dados_invalidos_white_24dp.png").getRetornoAlert_OK();
            } else {
                alertMensagem.setCabecalho("Deletar dados [endereço]");
                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o endereço\n["
                        + listEndereco.getSelectionModel().getSelectedItem() + "]\nda empresa: " + txtRazao.getText() + "?");
                alertMensagem.setStrIco("ic_endereco_add_white_24dp.png");
                if (!retornoDelete(alertMensagem)) return;
                if (enderecoVO.getId() == 0) {
                    getTabEnderecoVOObservableList().remove(enderecoVO);
                } else {
                    index = getTabEnderecoVOObservableList().indexOf(enderecoVO);
                    enderecoVO.setId(enderecoVO.getId() * (-1));
                    getTabEnderecoVOObservableList().set(index, enderecoVO);
                }
            }
            return;
        }
        if (isHome || isEmail) {
            if (isEmail) {
                alertMensagem.setCabecalho("Deletar dados [email]");
                alertMensagem.setStrIco("ic_web_email_white_24dp.png");
                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o email [");
            }
            if (isHome) {
                alertMensagem.setCabecalho("Deletar dados [home page]");
                alertMensagem.setStrIco("ic_web_home_page_white_24dp.png");
                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar a home page [");
            }
            TabEmailHomePageVO emailHomePageVO = null;
            if (listHomePage.isFocused())
                emailHomePageVO = listHomePage.getSelectionModel().getSelectedItem();
            if (listEmail.isFocused())
                emailHomePageVO = listEmail.getSelectionModel().getSelectedItem();
            if (listContatoHomePage.isFocused())
                emailHomePageVO = listContatoHomePage.getSelectionModel().getSelectedItem();
            if (listContatoEmail.isFocused())
                emailHomePageVO = listContatoEmail.getSelectionModel().getSelectedItem();
            if (emailHomePageVO != null) {
                if (isEmpresa) {
                    index = getTabEmailHomePageVOObservableList().indexOf(emailHomePageVO);
                    alertMensagem.setPromptText(alertMensagem.getPromptText() + emailHomePageVO + "]\nda empresa: "
                            + txtRazao.getText() + "?");
                } else {
                    index = getTabContatoEmailHomePageVOObservableList().indexOf(emailHomePageVO);
                    alertMensagem.setPromptText(alertMensagem.getPromptText() + emailHomePageVO + "]\ndo contato: "
                            + listContatoNome.getSelectionModel().getSelectedItem() + "?");
                }
                if (!retornoDelete(alertMensagem)) return;
                if (emailHomePageVO.getId() == 0) {
                    if (isEmpresa) getTabEmailHomePageVOObservableList().remove(emailHomePageVO);
                    else getTabContatoTelefoneVOObservableList().remove(emailHomePageVO);
                } else {
                    emailHomePageVO.setId(emailHomePageVO.getId() * (-1));
                    if (isEmpresa) getTabEmailHomePageVOObservableList().set(index, emailHomePageVO);
                    else getTabContatoEmailHomePageVOObservableList().set(index, emailHomePageVO);
                }
            }
            return;
        }

        if (isTelefone) {
            TabTelefoneVO telefoneVO = null;
            if (listTelefone.isFocused())
                telefoneVO = listTelefone.getSelectionModel().getSelectedItem();
            if (listContatoTelefone.isFocused())
                telefoneVO = listContatoTelefone.getSelectionModel().getSelectedItem();
            alertMensagem.setCabecalho("Deletar dados [telefone]");
            alertMensagem.setStrIco("ic_telefone_white_24dp.png");
            if (telefoneVO != null) {
                if (isContato) {
                    index = getTabContatoTelefoneVOObservableList().indexOf(telefoneVO);
                    alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o telefone ["
                            + telefoneVO + "]\ndo contato: " + listContatoNome.getSelectionModel().getSelectedItem() + "?");
                } else {
                    index = getTabTelefoneVOObservableList().indexOf(telefoneVO);
                    alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o telefone ["
                            + telefoneVO + "]\nda empresa: " + txtRazao.getText() + "?");
                }
                if (!retornoDelete(alertMensagem)) return;
                if (telefoneVO.getId() == 0) {
                    if (isContato) getTabContatoTelefoneVOObservableList().remove(telefoneVO);
                    else getTabTelefoneVOObservableList().remove(telefoneVO);
                } else {
                    telefoneVO.setId(telefoneVO.getId() * (-1));
                    if (isContato) getTabContatoTelefoneVOObservableList().set(index, telefoneVO);
                    else getTabTelefoneVOObservableList().set(index, telefoneVO);
                }
            }
            return;
        }

        if (isContato) {
            TabContatoVO contatoVO = null;
            if ((contatoVO = listContatoNome.getSelectionModel().getSelectedItem()) != null) {
                index = getTabContatoVOObservableList().indexOf(contatoVO);
                alertMensagem.setCabecalho("Deletar dados [contato]");
                alertMensagem.setStrIco("ic_telefone_white_24dp.png");
                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o contato ["
                        + contatoVO + "]\nda empresa: " + txtRazao.getText() + "?");
                if (!retornoDelete(alertMensagem)) return;
                if (contatoVO.getId() == 0) {
                    getTabContatoVOObservableList().remove(contatoVO);
                } else {
                    contatoVO.setId(contatoVO.getId() * (-1));
                    getTabContatoVOObservableList().set(index, contatoVO);
                }
            }
            return;
        }

    }

    void keyInsert() {
        isEmpresaIsEmail();

        if (isEndereco) {
            TabEnderecoVO enderecoVO = null;
            if ((enderecoVO = addEndereco()) == null) return;
            getTabEmpresaVO().getTabEnderecoVOList().add(enderecoVO);
            getTabEnderecoVOObservableList().add(enderecoVO);
            listEndereco.getSelectionModel().selectLast();
            txtEndCEP.requestFocus();
            return;
        }

        if (isEmail || isHome) {
            TabEmailHomePageVO emailHomePageVO = null;
            if (!isEmpresa)
                if (listContatoNome.getSelectionModel().getSelectedItem() == null) {
                    new ServiceAlertMensagem("Contato inválido!",
                            USUARIO_LOGADO_APELIDO + ", precisa escolher para qual contato vai ser adicionado o email/homepage",
                            "ic_dados_invalidos_white_24dp.png").getRetornoAlert_OK();
                    listContatoNome.requestFocus();
                    return;
                }
            if ((emailHomePageVO = addEditEmailHomePage(null)) == null) return;
            while (!ServiceValidarDado.isEmailHomePageValido(emailHomePageVO.getDescricao(), isEmail))
                if ((emailHomePageVO = addEditEmailHomePage(null)) == null) return;
            if (isEmpresa) {
                getTabEmpresaVO().getTabEmailHomePageVOList().add(emailHomePageVO);
                getTabEmailHomePageVOObservableList().add(emailHomePageVO);
            } else {
                getTabContatoVO().getTabEmailHomePageVOList().add(emailHomePageVO);
                getTabContatoEmailHomePageVOObservableList().add(emailHomePageVO);
            }
            return;
        }

        if (isTelefone) {
            TabTelefoneVO telefoneVO = null;
            if (!isEmpresa)
                if (listContatoNome.getSelectionModel().getSelectedItem() == null) {
                    new ServiceAlertMensagem("Contato inválido!",
                            USUARIO_LOGADO_APELIDO + ", precisa escolher para qual contato vai ser adicionado o telefone",
                            "ic_dados_invalidos_white_24dp.png").getRetornoAlert_OK();
                    listContatoNome.requestFocus();
                    return;
                }
            if ((telefoneVO = addEditTelefone(null)) == null) return;
            while (!ServiceValidarDado.isTelefoneValido(telefoneVO.getDescricao()))
                if ((telefoneVO = addEditTelefone(null)) == null) return;
            if (isEmpresa) {
                getTabEmpresaVO().getTabTelefoneVOList().add(telefoneVO);
                getTabTelefoneVOObservableList().add(telefoneVO);
            } else {
                getTabContatoVO().getTabTelefoneVOList().add(telefoneVO);
                getTabContatoTelefoneVOObservableList().add(telefoneVO);
            }
            return;
        }

        if (isContato) {
            TabContatoVO contatoVO = null;
            if ((contatoVO = addEditContato(contatoVO)) == null) return;
            getTabEmpresaVO().getTabContatoVOList().add(contatoVO);
            getTabContatoVOObservableList().add(contatoVO);
            return;
        }
    }

    void keyShiftF6() {
        isEmpresaIsEmail();
        int index;
        if (isHome || isEmail) {
            TabEmailHomePageVO emailHomeEdit = null;
            if (isEmpresa) {
                if (isEmail) {
                    emailHomeEdit = listEmail.getSelectionModel().getSelectedItem();
                } else {
                    emailHomeEdit = listHomePage.getSelectionModel().getSelectedItem();
                }
            } else {
                if (isEmail) {
                    emailHomeEdit = listContatoEmail.getSelectionModel().getSelectedItem();
                } else {
                    emailHomeEdit = listContatoHomePage.getSelectionModel().getSelectedItem();
                }
            }
            if (emailHomeEdit == null) return;
            if (isEmpresa) index = getTabEmailHomePageVOObservableList().indexOf(emailHomeEdit);
            else index = getTabContatoEmailHomePageVOObservableList().indexOf(emailHomeEdit);
            if ((emailHomeEdit = addEditEmailHomePage(emailHomeEdit)) == null) return;
            if (isEmpresa) getTabEmailHomePageVOObservableList().set(index, emailHomeEdit);
            else getTabContatoEmailHomePageVOObservableList().set(index, emailHomeEdit);
        }
        if (isTelefone) {
            TabTelefoneVO telefoneEdit = null;
            if (isEmpresa) telefoneEdit = listTelefone.getSelectionModel().getSelectedItem();
            else telefoneEdit = listContatoTelefone.getSelectionModel().getSelectedItem();
            if (telefoneEdit == null) return;
            if (isEmpresa) index = getTabTelefoneVOObservableList().indexOf(telefoneEdit);
            else index = getTabContatoTelefoneVOObservableList().indexOf(telefoneEdit);
            if ((telefoneEdit = addEditTelefone(telefoneEdit)) == null) return;
            if (isEmpresa) getTabTelefoneVOObservableList().set(index, telefoneEdit);
            else getTabContatoTelefoneVOObservableList().set(index, telefoneEdit);
        }
    }

    TabEnderecoVO addEndereco() {
        List<SisTipoEnderecoVO> list = sisTipoEnderecoVOList.stream().filter(tp ->
                getTabEnderecoVOObservableList().stream()
                        .filter(end -> tp.getDescricao().equals(end.getSisTipoEnderecoVO().getDescricao())).count() == 0
        ).collect(Collectors.toList());
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_endereco_add_white_24dp.png");
        if (list.size() <= 0) {
            alertMensagem.setCabecalho("Endereço não disponivél");
            alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", a empresa " + txtRazao.getText()
                    + " não tem disponibilidade de endereço!\nAtualize algum endereço já existente!");
            alertMensagem.getRetornoAlert_OK();
            return null;
        }
        alertMensagem.setCabecalho("Adicionar dados [endereço]");
        alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", selecione o tipo endereço");
        Object tipEnd = null;
        try {
            tipEnd = alertMensagem.getRetornoAlert_ComboBox(list).get();
            if (tipEnd == null)
                return null;
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException))
                ex.printStackTrace();
            return null;
        }
        int idMunicipio = listEndereco.getItems().get(0).getSisMunicipio_id();
        TabEnderecoVO enderecoVO = new TabEnderecoVO(((SisTipoEnderecoVO) tipEnd).getId(), idMunicipio);
        return enderecoVO;
    }

    TabEmailHomePageVO addEditEmailHomePage(TabEmailHomePageVO emailHomePageVO) {
        isEmpresaIsEmail();
        String tipoInclusao, donoEmailHomePage, emailHomePageAdicao;
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();

        if (isEmpresa) donoEmailHomePage = "a empresa: " + txtRazao.getText();
        else donoEmailHomePage = "o contato: " + listContatoNome.getSelectionModel().getSelectedItem();
        if (isEmail) tipoInclusao = "email";
        else tipoInclusao = "home page";

        if (isEmail) alertMensagem.setStrIco("ic_web_email_white_24dp.png");
        else alertMensagem.setStrIco("ic_web_home_page_white_24dp.png");
        if (emailHomePageVO == null) {
            alertMensagem.setCabecalho("Adicionar dados [" + tipoInclusao + "]");
            alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual " + tipoInclusao + " a ser adicionada para " + donoEmailHomePage + " ?");
        } else {
            alertMensagem.setCabecalho("Editar informações [" + tipoInclusao + "]");
            alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual alteração será feita no " + tipoInclusao + " d" + donoEmailHomePage + " ?");
        }
        try {
            if (emailHomePageVO == null)
                emailHomePageAdicao = alertMensagem
                        .getRetornoAlert_TextField(ServiceFormatarDado.gerarMascara("email", 80, "?"), "").get();
            else
                emailHomePageAdicao = alertMensagem
                        .getRetornoAlert_TextField(ServiceFormatarDado.gerarMascara("homepage", 80, "?"),
                                emailHomePageVO.getDescricao()).get();

            if (emailHomePageAdicao == null || emailHomePageAdicao.equals(""))
                return null;
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException))
                ex.printStackTrace();
            return null;
        }
        if (emailHomePageVO == null) emailHomePageVO = new TabEmailHomePageVO(emailHomePageAdicao, isEmail);
        else emailHomePageVO.setDescricao(emailHomePageAdicao);

        return emailHomePageVO;
    }

    TabTelefoneVO addEditTelefone(TabTelefoneVO telefoneVO) {
        isEmpresaIsEmail();
        String tipoInclusao, donoTelefone, textPreLoader = "";
        Pair<String, Object> pairTelefoneAdicao = null;
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
        if (isEmpresa) donoTelefone = "a empresa: " + txtRazao.getText();
        else donoTelefone = "o contato: " + listContatoNome.getSelectionModel().getSelectedItem();
        tipoInclusao = "telefone";
        alertMensagem.setStrIco("ic_telefone_white_24dp.png");
        if (telefoneVO == null) {
            alertMensagem.setCabecalho("Adicionar dados [" + tipoInclusao + "]");
            alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual " + tipoInclusao + " a ser adicionado para " + donoTelefone + " ?");
        } else {
            alertMensagem.setCabecalho("Editar informações [" + tipoInclusao + "]");
            alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual alteração será feita no " + tipoInclusao + " d" + donoTelefone + " ?");
            textPreLoader = telefoneVO.getDescricao();
        }
        try {
            pairTelefoneAdicao = alertMensagem
                    .getRetornoAlert_TextFieldEComboBox(sisTelefoneOperadoraVOList, "telefone", textPreLoader).get();
            if (pairTelefoneAdicao == null || pairTelefoneAdicao.getKey().equals(""))
                return null;
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException))
                ex.printStackTrace();
            return null;
        }
        if (telefoneVO == null)
            telefoneVO = new TabTelefoneVO(pairTelefoneAdicao.getKey(),
                    (SisTelefoneOperadoraVO) pairTelefoneAdicao.getValue());
        else {
            telefoneVO.setDescricao(pairTelefoneAdicao.getKey());
            telefoneVO.setSisTelefoneOperadoraVO((SisTelefoneOperadoraVO) pairTelefoneAdicao.getValue());
            telefoneVO.setSisTelefoneOperadora_id(telefoneVO.getSisTelefoneOperadoraVO().getId());
        }

        return telefoneVO;
    }

    TabContatoVO addEditContato(TabContatoVO contatoVO) {
        isEmpresaIsEmail();
        String tipoInclusao, textPreLoader = "";
        Pair<String, Object> pairContatoAdicao = null;
        ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
        tipoInclusao = "contato";
        alertMensagem.setStrIco("ic_contato_add_white_24dp.png");
        if (contatoVO == null) {
            alertMensagem.setCabecalho("Adicionar dados [" + tipoInclusao + "]");
            alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual contato a ser adicionado para empresa: " + txtRazao.getText() + " ?");
        } else {
            alertMensagem.setCabecalho("Editar informações [" + tipoInclusao + "]");
            alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual alteração será feita no contato da empresa: " + txtRazao.getText() + " ?");
            textPreLoader = contatoVO.getDescricao();
        }
        try {
            pairContatoAdicao = alertMensagem
                    .getRetornoAlert_TextFieldEComboBox(sisCargoVOList, ServiceFormatarDado
                            .gerarMascara("", 40, "@"), textPreLoader).get();
            if (pairContatoAdicao == null || pairContatoAdicao.getKey().equals(""))
                return null;
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException))
                ex.printStackTrace();
            return null;
        }
        if (contatoVO == null)
            contatoVO = new TabContatoVO(pairContatoAdicao.getKey(), (SisCargoVO) pairContatoAdicao.getValue());
        else {
            contatoVO.setDescricao(pairContatoAdicao.getKey());
            contatoVO.setSisCargoVO((SisCargoVO) pairContatoAdicao.getValue());
            contatoVO.setSisCargo_id(contatoVO.getSisCargoVO().getId());
        }

        return contatoVO;
    }

    void salvarEmpresa() {
        Connection conn = ConnectionFactory.getConnection();
        try {
            conn.setAutoCommit(false);

            if (getTabEmpresaVO().getId() == 0)
                getTabEmpresaVO().setId(new TabEmpresaDAO().insertTabEmpresaVO(conn, getTabEmpresaVO()));
            else
                new TabEmpresaDAO().updateTabEmpresaVO(conn, getTabEmpresaVO());
            int idEmpresa = getTabEmpresaVO().getId();

            new RelEmpresaEnderecoDAO().dedeteRelEmpresaEnderecoVO(conn, idEmpresa);
            getTabEnderecoVOObservableList().stream().forEach(end -> {
                try {
                    if (end.getId() < 0) {
                        new TabEnderecoDAO().deleteTabEnderecoVO(conn, end);
                        getTabEnderecoVOObservableList().remove(end);
                    } else {
                        if (end.getId() > 0)
                            new TabEnderecoDAO().updateTabEnderecoVO(conn, end);
                        else
                            end.setId(new TabEnderecoDAO().insertTabEnderecoVO(conn, end));
                        new RelEmpresaEnderecoDAO().insertrelEmpresaEnderecoVO(conn, idEmpresa, end.getId());
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    //throw new RuntimeException("Erro getTabEnderecoVOObservableList ", ex);
                }
            });

            new RelEmpresaEmailHomePageDAO().dedeteRelEmpresaEmailHomePageVO(conn, idEmpresa);
            getTabEmailHomePageVOObservableList().stream().forEach(emailHome -> {
                try {
                    if (emailHome.getId() < 0) {
                        new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, emailHome);
                        getTabEmailHomePageVOObservableList().remove(emailHome);
                    } else {
                        if (emailHome.getId() > 0)
                            new TabEmailHomePageDAO().updateTabEmailHomePageVO(conn, emailHome);
                        else
                            emailHome.setId(new TabEmailHomePageDAO().insertTabEmailHomePageVO(conn, emailHome));
                        new RelEmpresaEmailHomePageDAO().insertRelEmpresaEmailHomePageVO(conn, idEmpresa, emailHome.getId());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //throw new RuntimeException("Erro getTabEmailHomePageVOObservableList ", ex);
                }
            });

            new RelEmpresaTelefoneDAO().deleteRelEmpresaTelefoneVO(conn, idEmpresa);
            getTabTelefoneVOObservableList().stream().forEach(telefone -> {
                try {
                    if (telefone.getId() < 0) {
                        new TabTelefoneDAO().deleteTabTelefoneVO(conn, telefone);
                        getTabTelefoneVOObservableList().remove(telefone);
                    } else {
                        if (telefone.getId() > 0)
                            new TabTelefoneDAO().updateTabTelefoneVO(conn, telefone);
                        else
                            telefone.setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, telefone));
                        new RelEmpresaTelefoneDAO().insertRelEmpresaTelefoneVO(conn, idEmpresa, telefone.getId());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //throw new RuntimeException("Erro getTabTelefoneVOObservableList ", ex);
                }
            });

            new RelEmpresaContatoDAO().deleteRelEmpresaContatoVO(conn, idEmpresa);
            getTabContatoVOObservableList().stream().forEach(contato -> {
                int idContato;
                try {
                    if (contato.getId() < 0) {
                        idContato = contato.getId() * (-1);
                        new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn, idContato);
                        new RelContatoTelefoneDAO().deleteRelContatoTelefoneVO(conn, idContato);
                        contato.getTabEmailHomePageVOList().stream().filter(contatoEmailHome -> contatoEmailHome.getId() != 0)
                                .forEach(contatoEmailHome -> {
                                    try {
                                        new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, contatoEmailHome);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        //throw new RuntimeException("Erro getTabContatoEmailHomePageVOObservableList ", ex);
                                    }
                                });
                        contato.getTabTelefoneVOList().stream().filter(contatoTelefone -> contatoTelefone.getId() != 0)
                                .forEach(contatoTelefone -> {
                                    try {
                                        new TabTelefoneDAO().deleteTabTelefoneVO(conn, contatoTelefone);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        //throw new RuntimeException("Erro getTabContatoTelefoneVOObservableList ", ex);
                                    }
                                });
                        new TabContatoDAO().deleteTabContatoVO(conn, contato);
                        getTabContatoVOObservableList().remove(contato);
                    } else {
                        if (contato.getId() > 0)
                            new TabContatoDAO().updateTabContatoVO(conn, contato);
                        else
                            contato.setId(new TabContatoDAO().insertTabContatoVO(conn, contato));
                        idContato = contato.getId();
                        new RelEmpresaContatoDAO().insertRelEmpresaContatoVO(conn, idEmpresa, idContato);

                        contato.getTabEmailHomePageVOList().stream()
                                .forEach(contatoEmailHome -> {
                                    try {
                                        if (contatoEmailHome.getId() < 0) {
                                            new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, contatoEmailHome);
                                            contato.getTabEmailHomePageVOList().remove(contatoEmailHome);
                                        } else {
                                            if (contatoEmailHome.getId() > 0)
                                                new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, contatoEmailHome);
                                            else
                                                contatoEmailHome.setId(new TabEmailHomePageDAO().insertTabEmailHomePageVO(conn, contatoEmailHome));
                                            new RelContatoEmailHomePageDAO().insertRelContatoEmailHomePageVO(conn, idContato, contatoEmailHome.getId());
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        //throw new RuntimeException("Erro getTabContatoEmailHomePageVOObservableList ", ex);
                                    }
                                });
                        contato.getTabTelefoneVOList().stream()
                                .forEach(contatoTelefone -> {
                                    try {
                                        if (contatoTelefone.getId() < 0) {
                                            new TabTelefoneDAO().deleteTabTelefoneVO(conn, contatoTelefone);
                                            contato.getTabTelefoneVOList().remove(contatoTelefone);
                                        } else {
                                            if (contatoTelefone.getId() > 0)
                                                new TabTelefoneDAO().updateTabTelefoneVO(conn, contatoTelefone);
                                            else
                                                contatoTelefone.setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, contatoTelefone));
                                            new RelContatoTelefoneDAO().insertRelContatoTelefoneVO(conn, idContato, contatoTelefone.getId());
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        //throw new RuntimeException("Erro getTabContatoTelefoneVOObservableList ", ex);
                                    }
                                });


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //throw new RuntimeException("Erro getTabContatoVOObservableList ", ex);
                }
            });

            getTabEmpresaReceitaFederalVOObservableList().stream()
                    .forEach(receita -> {
                        try {
                            receita.setTabEmpresa_id(idEmpresa);
                            if (receita.getId() < 0) {
                                new TabEmpresaReceitaFederalDAO().deleteTabEmpresaReceitaFederalVO(conn, receita);
                            } else {
                                if (receita.getId() > 0)
                                    new TabEmpresaReceitaFederalDAO().updateTabEmpresaReceitaFederalVO(conn, receita);
                                else
                                    receita.setId(new TabEmpresaReceitaFederalDAO().insertTabEmpresaReceitaFederalVO(conn, receita));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            //throw new RuntimeException("Erro getTabEmpresaReceitaFederalVOObservableList ", ex);
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
        }
    }

}


//            while (c.next()) {
//                if (c.wasPermutated()) {
//                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
//                        System.out.println("c.wasPermutated()      c.getFrom():[" + c.getFrom() + "]      c.getTo():[" + c.getTo() + "]");
//                        //permutate
//                    }
//                } else if (c.wasUpdated()) {
//                    System.out.println("c.wasUpdated(): [" + c.wasUpdated() + "]");
//                    //update item
//                } else {
//                    for (TabEmailHomePageVO remitem : c.getRemoved()) {
//                        System.out.println("remitem: [" + remitem + "]");
//                    }
//                    for (TabEmailHomePageVO additem : c.getAddedSubList()) {
//                        System.out.println("additem: [" + additem + "]");
//                    }
//                }
//            }
/*
            while (c.next()) {
                if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        System.out.println("c.wasPermutated()      c.getFrom():[" + c.getFrom() + "]      c.getTo():[" + c.getTo() + "]");
                        //permutate
                    }
                } else if (c.wasUpdated()) {
                    System.out.println("c.wasUpdated(): [" + c.wasUpdated() + "]");
                } else {
                    for (TabEmailHomePageVO remitem : c.getRemoved()) {
                        if (remitem.isIsEmail()) {
                            System.out.println("listContatoEmail: " + remitem);
                            listContatoEmail.getItems().remove(remitem);
                        } else {
                            System.out.println("listContatoHomePage: " + remitem);
                            listContatoHomePage.getItems().remove(remitem);
                        }
                    }
                    for (TabEmailHomePageVO additem : c.getAddedSubList()) {
                        if (additem.isIsEmail()) {
                            System.out.println("listContatoEmail: " + additem);
                            listContatoEmail.getItems().add(additem);
                        } else {
                            System.out.println("listContatoHomePage: " + additem);
                            listContatoHomePage.getItems().add(additem);
                        }
                    }
                }
            }

 */