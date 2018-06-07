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
import javafx.beans.value.ObservableValue;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
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
    public JFXListView<TabEnderecoVO> listEndereco;
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
    public TitledPane tpnPessoaContato;

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
                        setEmpresaVO(new TabEmpresaVO(1));
                        setStatusFormulario("incluir");
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        if (!validarDados()) break;
                        if (buscaDuplicidade(getEmpresaVO().getCnpj().replaceAll("\\D", ""))) break;
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
                                setEmpresaVO(ttvEmpresa.getSelectionModel().getSelectedItem().getValue());
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
                        indexObservableEmpresa = empresaVOObservableList.indexOf(getEmpresaVO());
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

        ttvEmpresa.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                setEmpresaVO(ttvEmpresa.getTreeItem(newValue.intValue()).getValue());
        });

//        ttvEmpresa.focusedProperty().addListener((observable, oldValue, newValue) -> {
//
//            if (newValue)
//                if (ttvEmpresa.getSelectionModel().getSelectedIndex() >= 0)
//                    setEmpresaVO(ttvEmpresa.getSelectionModel().getSelectedItem().getValue());
//        });

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
                    TabEmpresaVO empresaVO = getEmpresaVO();
                    //guardarEndereco(getTabEnderecoVOList().get(0));
                    if ((empresaVO = new ServiceConsultaWebServices().getSistuacaoCNPJ_receitaWs(empresaVO, valueCnpj)) == null) {
                        new ServiceAlertMensagem("Dado não localizada!", USUARIO_LOGADO_APELIDO + ", o "
                                + "C.N.P.J.: " + txtCNPJ.getText() + " não foi localizado na base de dados!",
                                "ic_web_service_err_white_24dp").getRetornoAlert_OK();
                        txtEndCEP.requestFocus();
                        return;
                    } else {
                        setEmpresaVO(empresaVO);
                        txtCNPJ.requestFocus();
                    }

                }
            }
        });

        listEndereco.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!getStatusFormulario().toLowerCase().equals("pesquisa"))
                if (oldValue != null && newValue != oldValue && newValue != null)
                    guardarEndereco();
            if (newValue != null)
                setEnderecoVO(newValue);
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
                    TabEnderecoVO enderecoVO = getEmpresaVO().getTabEnderecoVOList().get(idEnderecoAtual);
                    if ((enderecoVO = new ServiceConsultaWebServices().getEnderecoCep_postmon(new Pair<>(enderecoVO, valueCep))) == null) {
                        new ServiceAlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
                                + "CEP: " + txtEndCEP.getText() + " não foi localizado na base de dados!",
                                "ic_web_service_err_white_24dp").getRetornoAlert_OK();
                        txtEndCEP.requestFocus();
                        return;
                    } else {
                        getEmpresaVO().getTabEnderecoVOList().set(idEnderecoAtual, enderecoVO);
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
            else if (getEmpresaVO() != null)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + getEmpresaVO().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
        });

        cboEndUF.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SisUFVO> observable, SisUFVO oldValue, SisUFVO newValue) -> {
            if (newValue == null) return;
            cboEndMunicipio.getItems().clear();
            cboEndMunicipio.getItems().setAll(newValue.getMunicipioVOList());
            String mascUF = "ie" + newValue.getSigla();
            formatIE.setMascara(ServiceFormatarDado.gerarMascara(mascUF, 0, "#"));

            if (getEmpresaVO() != null && getEmpresaVO().getIe().length() > 0)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), mascUF));
        });

        listContatoNome.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.intValue() < 0) return;
            setContatoVO(listContatoNome.getItems().get(newValue.intValue()));
        });

        enderecoVOObservableList.addListener((ListChangeListener) c -> {
            listEndereco.getItems().setAll(getEnderecoVOObservableList().stream()
                    .filter(end -> end.getId() >= 0)
                    .collect(Collectors.toList()));
        });

        emailHomePageVOObservableList.addListener((ListChangeListener) c -> {
            listHomePage.getItems().setAll(getEmailHomePageVOObservableList().stream()
                    .filter(home -> home.getId() >= 0 && !home.isIsEmail())
                    .collect(Collectors.toList()));
            listEmail.getItems().setAll(getEmailHomePageVOObservableList().stream()
                    .filter(mail -> mail.getId() >= 0 && mail.isIsEmail())
                    .collect(Collectors.toList()));
        });

        telefoneVOObservableList.addListener((ListChangeListener) c -> {
            listTelefone.getItems().setAll(getTelefoneVOObservableList().stream()
                    .filter(tel -> tel.getId() >= 0)
                    .collect(Collectors.toList()));
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

    int contadorMeu = 0;

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
    ObservableList<TabEmpresaVO> empresaVOObservableList;
    FilteredList<TabEmpresaVO> empresaVOFilteredList;
    TabEmpresaVO empresaVO;
    TabEnderecoVO enderecoVO;
    TabContatoVO contatoVO;
    ObservableList<TabEnderecoVO> enderecoVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> emailHomePageVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabTelefoneVO> telefoneVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabContatoVO> contatoVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> contatoEmailHomePageVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabTelefoneVO> contatoTelefoneVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmpresaReceitaFederalVO> empresaReceitaFederalVOObservableList;

    List<SisUFVO> uFVOList;
    List<SisSituacaoSistemaVO> situacaoSistemaVOList;
    List<SisCargoVO> cargoVOList;
    List<SisTipoEnderecoVO> tipoEnderecoVOList;
    List<SisTelefoneOperadoraVO> telefoneOperadoraVOList;

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
                    setEmpresaVO(getEmpresaVO().clone());
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

    public TabEmpresaVO getEmpresaVO() {
        return empresaVO;
    }

    public void setEmpresaVO(TabEmpresaVO empresaVO) {
        this.empresaVO = new TabEmpresaVO();
        if (empresaVO != null)
            this.empresaVO = empresaVO;
        exibirDadosEmpresa();
    }

    public ObservableList<TabEnderecoVO> getEnderecoVOObservableList() {
        return enderecoVOObservableList;
    }

    public void setEnderecoVOObservableList(List<TabEnderecoVO> enderecoVOList) {
        this.enderecoVOObservableList.clear();
        if (enderecoVOList.size() > 0)
            this.enderecoVOObservableList.setAll(FXCollections.observableArrayList(enderecoVOList));
    }

    public TabEnderecoVO getEnderecoVO() {
        return enderecoVO;
    }

    public void setEnderecoVO(TabEnderecoVO enderecoVO) {
        this.enderecoVO = new TabEnderecoVO();
        if (enderecoVO != null)
            this.enderecoVO = enderecoVO;
        exibirDadosEndereco();
    }

    public ObservableList<TabEmailHomePageVO> getEmailHomePageVOObservableList() {
        return emailHomePageVOObservableList;
    }

    public void setEmailHomePageVOObservableList(List<TabEmailHomePageVO> emailHomePageVOList) {
        this.emailHomePageVOObservableList.clear();
        if (emailHomePageVOList.size() > 0)
            this.emailHomePageVOObservableList.setAll(FXCollections.observableArrayList(emailHomePageVOList));
    }

    public ObservableList<TabTelefoneVO> getTelefoneVOObservableList() {
        return telefoneVOObservableList;
    }

    public void setTelefoneVOObservableList(List<TabTelefoneVO> telefoneVOList) {
        this.telefoneVOObservableList.clear();
        if (telefoneVOList.size() > 0)
            this.telefoneVOObservableList.setAll(FXCollections.observableArrayList(telefoneVOList));
    }

    public ObservableList<TabContatoVO> getContatoVOObservableList() {
        return contatoVOObservableList;
    }

    public void setContatoVOObservableList(List<TabContatoVO> contatoVOList) {
        this.contatoVOObservableList.clear();
        if (contatoVOList.size() > 0)
            this.contatoVOObservableList.setAll(FXCollections.observableArrayList(contatoVOList));
    }

    public TabContatoVO getContatoVO() {
        return contatoVO;
    }

    public void setContatoVO(TabContatoVO contatoVO) {
        this.contatoVO = new TabContatoVO();
        if (contatoVO != null)
            this.contatoVO = contatoVO;
    }

    public ObservableList<TabEmailHomePageVO> getContatoEmailHomePageVOObservableList() {
        return contatoEmailHomePageVOObservableList;
    }

    public void setContatoEmailHomePageVOObservableList(List<TabEmailHomePageVO> contatoEmailHomePageVOList) {
        this.contatoEmailHomePageVOObservableList.clear();
        if (contatoEmailHomePageVOList.size() > 0)
            this.contatoEmailHomePageVOObservableList = FXCollections.observableArrayList(contatoEmailHomePageVOList);
    }

    public ObservableList<TabTelefoneVO> getContatoTelefoneVOObservableList() {
        return contatoTelefoneVOObservableList;
    }

    public void setContatoTelefoneVOObservableList(List<TabTelefoneVO> contatoTelefoneVOList) {
        this.contatoTelefoneVOObservableList.clear();
        if (contatoTelefoneVOList.size() > 0)
            this.contatoTelefoneVOObservableList = FXCollections.observableArrayList(contatoTelefoneVOList);
    }

    public ObservableList<TabEmpresaReceitaFederalVO> getEmpresaReceitaFederalVOObservableList() {
        return empresaReceitaFederalVOObservableList;
    }

    public void setEmpresaReceitaFederalVOObservableList(List<TabEmpresaReceitaFederalVO> empresaReceitaFederalVOList) {
        this.empresaReceitaFederalVOObservableList.clear();
        if (empresaReceitaFederalVOList.size() > 0)
            this.empresaReceitaFederalVOObservableList = FXCollections.observableArrayList(empresaReceitaFederalVOList);
    }

    public void carregarListaEmpresa() {
        empresaVOFilteredList = new FilteredList<>(empresaVOObservableList = FXCollections.observableArrayList(new TabEmpresaDAO().getTabEmpresaVOList(false)), empresa -> true);
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
        if ((situacaoSistemaVOList = new ArrayList<>(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVOList())) == null)
            return;
        cboSituacaoSistema.getItems().setAll(situacaoSistemaVOList);
    }

    public void preencherCboEndUF() {
        cboEndUF.getItems().clear();
        if ((uFVOList = new ArrayList<>(new SisUFDAO().getSisUFVOList_DetMunicipios())) != null)
            cboEndUF.getItems().setAll(uFVOList);
    }

    void pesquisaEmpresa() {
        String busca = txtPesquisaEmpresa.getText().toLowerCase().trim();
        int filtro = cboFiltroPesquisa.getSelectionModel().getSelectedIndex();
        if (busca.length() == 0)
            empresaVOFilteredList.setPredicate(empresa -> true);
        else
            empresaVOFilteredList.setPredicate(empresa -> {
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
        if (empresaVOFilteredList == null)
            pesquisaEmpresa();
        setQtdRegistrosLocalizados(empresaVOFilteredList.size());
        final TreeItem<TabEmpresaVO> root = new RecursiveTreeItem<TabEmpresaVO>(empresaVOFilteredList, RecursiveTreeObject::getChildren);
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
        cargoVOList = new ArrayList<>(new SisCargoDAO().getSisCargoVOList());
    }

    public void carregarSisTipoEndereco() {
        tipoEnderecoVOList = new ArrayList<>(new SisTipoEnderecoDAO().getSisTipoEnderecoVOList());
    }

    public void carregarSisTelefoneOperadora() {
        telefoneOperadoraVOList = new ArrayList<>(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVOList());
    }

    void exibirDadosEmpresa() {
        if (getEmpresaVO() == null) {
            clearFieldDadoCadastral((AnchorPane) tpnDadoCadastral.getContent());
            return;
        }
        setEnderecoVOObservableList(getEmpresaVO().getTabEnderecoVOList());
        listEndereco.getSelectionModel().selectFirst();
        setEmailHomePageVOObservableList(getEmpresaVO().getTabEmailHomePageVOList());
        setTelefoneVOObservableList(getEmpresaVO().getTabTelefoneVOList());
        setContatoVOObservableList(getEmpresaVO().getTabContatoVOList());
        //atualizaListaReceitaFederal();

        cboClassificacaoJuridica.getSelectionModel().select(getEmpresaVO().isIsEmpresa() ? 1 : 2);
        txtCNPJ.setText(getEmpresaVO().isIsEmpresa() ? ServiceFormatarDado.getValorFormatado(getEmpresaVO().getCnpj(), "cnpj") : ServiceFormatarDado.getValorFormatado(getEmpresaVO().getCnpj(), "cpf"));
        txtIE.setText(getEmpresaVO().isIsEmpresa() ? ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + getEmpresaVO().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()) : ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie"));
        chkIeIsento.setSelected(getEmpresaVO().isIeIsento());
        cboSituacaoSistema.getSelectionModel().select(getEmpresaVO().getSisSituacaoSistemaVO());
        txtRazao.setText(getEmpresaVO().getRazao());
        txtFantasia.setText(getEmpresaVO().getFantasia());
        chkIsCliente.setSelected(getEmpresaVO().isIsCliente());
        chkIsFornecedor.setSelected(getEmpresaVO().isIsFornecedor());
        chkIsTransportadora.setSelected(getEmpresaVO().isIsTransportadora());

        lblNaturezaJuridica.setText("Natureza Júridica: " + getEmpresaVO().getNaturezaJuridica());
        lblDataAbertura.setText("data abertura: " + ((getEmpresaVO().getDataAbertura() != null) ? getEmpresaVO().getDataAbertura().toLocalDate().format(DTF_DATA) : ""));
        lblDataAberturaDiff.setText("tempo de abertura: " + ((getEmpresaVO().getDataAbertura() != null) ? ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAbertura().toLocalDate(), null) : ""));
        lblDataCadastro.setText("data cadastro: " + ((getEmpresaVO().getDataCadastro() != null) ? getEmpresaVO().getDataCadastro().toLocalDateTime().format(DTF_DATAHORA) + " [" + getEmpresaVO().getUsuarioCadastroVO().getApelido() + "]" : ""));
        lblDataCadastroDiff.setText("tempo de cadastro: " + ((getEmpresaVO().getDataCadastro() != null) ? ServiceDataHora.getIntervaloData(getEmpresaVO().getDataCadastro().toLocalDateTime().toLocalDate(), null) : ""));
        lblDataAtualizacao.setText("data atualização: " + ((getEmpresaVO().getDataAtualizacao() != null) ? getEmpresaVO().getDataAtualizacao().toLocalDateTime().format(DTF_DATAHORA) + " [" + getEmpresaVO().getUsuarioCadastroVO().getApelido() + "]" : ""));
        lblDataAtualizacaoDiff.setText("tempo de atualização: " + ((getEmpresaVO().getDataAtualizacao() != null) ? ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAtualizacao().toLocalDateTime().toLocalDate(), null) : ""));
    }

    void exibirDadosEndereco() {
        if (getEnderecoVO() == null) {
            limparEndereco();
            return;
        }
        cboEndUF.getSelectionModel().select(buscaUF(getEnderecoVO().getSisMunicipioVO().getUfVO().getSigla()));
        txtEndCEP.setText(ServiceFormatarDado.getValorFormatado(getEnderecoVO().getCep(), "cep"));
        txtEndLogradouro.setText(getEnderecoVO().getLogradouro());
        txtEndNumero.setText(getEnderecoVO().getNumero());
        txtEndComplemento.setText(getEnderecoVO().getComplemento());
        txtEndBairro.setText(getEnderecoVO().getBairro());
        txtEndPontoReferencia.setText(getEnderecoVO().getPontoReferencia());
        cboEndMunicipio.getSelectionModel().select(buscaMunicipio(getEnderecoVO().getSisMunicipioVO().getDescricao()));
    }

    SisUFVO buscaUF(String siglaUF) {
        return uFVOList.stream().filter(uf -> uf.getSigla().equals(siglaUF)).findFirst().orElse(null);
    }

    SisMunicipioVO buscaMunicipio(String municipio) {
        return cboEndMunicipio.getItems().stream().filter(municip -> municip.getDescricao().equals(municipio)).findFirst().orElse(null);
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
        if (idBusca != getEmpresaVO().getId()) {
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

        try {

            TabEmpresaReceitaFederalVO situacaoEmpresa = getEmpresaVO().getTabEmpresaReceitaFederalVOList().stream()
                    .filter(receita -> receita.getStr_Key().toLowerCase().equals("situacao")).collect(Collectors.toList()).get(0);
            if (!situacaoEmpresa.getStr_Value().equals("ativa")) {
                limparEndereco();
                return true;
            }
        } catch (Exception ex) {
            if (!(ex instanceof IndexOutOfBoundsException)) {
                ex.printStackTrace();
                return false;
            }
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
        //else result = guardarEndereco(idEnderecoAtual);
        return result;
    }

    boolean guardarEndereco() {
        TabEnderecoVO endAnt;
        try {
//            if (index < 0) return false;
            endAnt = getEmpresaVO().getTabEnderecoVOList().get(0);
            endAnt.setCep(txtEndCEP.getText().replaceAll("\\D", ""));
            endAnt.setLogradouro(txtEndLogradouro.getText());
            endAnt.setNumero(txtEndNumero.getText());
            endAnt.setComplemento(txtEndComplemento.getText());
            endAnt.setBairro(txtEndBairro.getText());
            endAnt.setPontoReferencia(txtEndPontoReferencia.getText());
            endAnt.setSisMunicipioVO(cboEndMunicipio.getSelectionModel().getSelectedItem());
            endAnt.setSisMunicipio_id(cboEndMunicipio.getSelectionModel().getSelectedItem().getId());
            //getTabEnderecoVOList().set(index, endAnt);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean guardarEmpresa() {
        try {
            getEmpresaVO().setIsEmpresa(cboClassificacaoJuridica.getSelectionModel().getSelectedIndex() == 1);
            getEmpresaVO().setCnpj(txtCNPJ.getText().replaceAll("\\D", ""));
            getEmpresaVO().setIe(txtIE.getText().replaceAll("\\D", ""));
            getEmpresaVO().setRazao(txtRazao.getText());
            getEmpresaVO().setFantasia(txtFantasia.getText());
            getEmpresaVO().setIsCliente(chkIsCliente.isSelected());
            getEmpresaVO().setIsFornecedor(chkIsFornecedor.isSelected());
            getEmpresaVO().setIsTransportadora(chkIsTransportadora.isSelected());
            getEmpresaVO().setSisSituacaoSistemaVO(cboSituacaoSistema.getSelectionModel().getSelectedItem());
            getEmpresaVO().setSisSituacaoSistema_id(cboSituacaoSistema.getSelectionModel().getSelectedItem().getId());
            getEmpresaVO().setUsuarioCadastro_id(Integer.parseInt(USUARIO_LOGADO_ID));
            getEmpresaVO().setUsuarioAtualizacao_id(Integer.parseInt(USUARIO_LOGADO_ID));

            Pattern p = Pattern.compile("\\d{2}/\\d{2}\\d{4}");
            Matcher m = p.matcher(lblDataAbertura.getText());
            while (m.find())
                getEmpresaVO().setDataAbertura(Date.valueOf(m.group()));

            getEmpresaVO().setNaturezaJuridica(lblNaturezaJuridica.getText().substring(19));
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
                    getEmpresaVO().getTabEnderecoVOList().remove(enderecoVO);
                } else {
                    index = getEmpresaVO().getTabEnderecoVOList().indexOf(enderecoVO);
                    enderecoVO.setId(enderecoVO.getId() * (-1));
                    getEmpresaVO().getTabEnderecoVOList().set(index, enderecoVO);
                }
                //atualizaListaEndereco();
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
                    index = getEmpresaVO().getTabEmailHomePageVOList().indexOf(emailHomePageVO);
                    alertMensagem.setPromptText(alertMensagem.getPromptText() + emailHomePageVO + "]\nda empresa: "
                            + txtRazao.getText() + "?");
                } else {
                    index = getContatoVO().getTabEmailHomePageVOList().indexOf(emailHomePageVO);
                    alertMensagem.setPromptText(alertMensagem.getPromptText() + emailHomePageVO + "]\ndo contato: "
                            + listContatoNome.getSelectionModel().getSelectedItem() + "?");
                }
                if (!retornoDelete(alertMensagem)) return;
                if (emailHomePageVO.getId() == 0) {
                    if (isEmpresa) getEmpresaVO().getTabEmailHomePageVOList().remove(emailHomePageVO);
                    else getContatoVO().getTabEmailHomePageVOList().remove(emailHomePageVO);
                } else {
                    emailHomePageVO.setId(emailHomePageVO.getId() * (-1));
                    if (isEmpresa) getEmpresaVO().getTabEmailHomePageVOList().set(index, emailHomePageVO);
                    else getContatoVO().getTabEmailHomePageVOList().set(index, emailHomePageVO);
                }
//                if (isEmpresa)
//                    //atualizaListaEmailHomePage();
//                else
//                    //atualizaListaContatoEmailHomePage();
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
                    index = getContatoVO().getTabTelefoneVOList().indexOf(telefoneVO);
                    alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o telefone ["
                            + telefoneVO + "]\ndo contato: " + listContatoNome.getSelectionModel().getSelectedItem() + "?");
                } else {
                    index = getEmpresaVO().getTabTelefoneVOList().indexOf(telefoneVO);
                    alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o telefone ["
                            + telefoneVO + "]\nda empresa: " + txtRazao.getText() + "?");
                }
                if (!retornoDelete(alertMensagem)) return;
                if (telefoneVO.getId() == 0) {
                    if (isContato) getContatoVO().getTabTelefoneVOList().remove(telefoneVO);
                    else getEmpresaVO().getTabTelefoneVOList().remove(telefoneVO);
                } else {
                    telefoneVO.setId(telefoneVO.getId() * (-1));
                    if (isContato) getContatoVO().getTabTelefoneVOList().set(index, telefoneVO);
                    else getEmpresaVO().getTabTelefoneVOList().set(index, telefoneVO);
                }
//                if (isEmpresa)
//                    atualizaListaTelefone();
//                else
//                    atualizaListaContatoTelefone();
            }
            return;
        }

        if (isContato) {
            TabContatoVO contatoVO = null;
            if ((contatoVO = listContatoNome.getSelectionModel().getSelectedItem()) != null) {
                index = getEmpresaVO().getTabContatoVOList().indexOf(contatoVO);
                alertMensagem.setCabecalho("Deletar dados [contato]");
                alertMensagem.setStrIco("ic_telefone_white_24dp.png");
                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", deseja deletar o contato ["
                        + contatoVO + "]\nda empresa: " + txtRazao.getText() + "?");
                if (!retornoDelete(alertMensagem)) return;
                if (contatoVO.getId() == 0) {
                    getEmpresaVO().getTabContatoVOList().remove(contatoVO);
                } else {
                    contatoVO.setId(contatoVO.getId() * (-1));
                    getEmpresaVO().getTabContatoVOList().set(index, contatoVO);
                }
                //atualizaListaContato();
            }
            return;
        }

    }

    void keyInsert() {
        isEmpresaIsEmail();

        if (isEndereco) {
            TabEnderecoVO enderecoVO = null;
            if ((enderecoVO = addEndereco()) == null) return;
            getEmpresaVO().getTabEnderecoVOList().add(enderecoVO);
            //atualizaListaEndereco();
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
                getEmpresaVO().getTabEmailHomePageVOList().add(emailHomePageVO);
                //atualizaListaEmailHomePage();
            } else {
                getContatoVO().getTabEmailHomePageVOList().add(emailHomePageVO);
                //atualizaListaContatoEmailHomePage();
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
                getEmpresaVO().getTabTelefoneVOList().add(telefoneVO);
                //atualizaListaTelefone();
            } else {
                getContatoVO().getTabTelefoneVOList().add(telefoneVO);
                //atualizaListaContatoTelefone();
            }
            return;
        }

        if (isContato) {
            TabContatoVO contatoVO = null;
            if ((contatoVO = addEditContato(contatoVO)) == null) return;
            getContatoVOObservableList().add(contatoVO);
            getEmpresaVO().getTabContatoVOList().add(contatoVO);
            //atualizaListaContato();
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
            if (isEmpresa) index = getEmpresaVO().getTabEmailHomePageVOList().indexOf(emailHomeEdit);
            else index = getContatoVO().getTabEmailHomePageVOList().indexOf(emailHomeEdit);
            if ((emailHomeEdit = addEditEmailHomePage(emailHomeEdit)) == null) return;
            if (isEmpresa) {
                getEmpresaVO().getTabEmailHomePageVOList().set(index, emailHomeEdit);
                //atualizaListaEmailHomePage();
            } else {
                getContatoVO().getTabEmailHomePageVOList().set(index, emailHomeEdit);
                //atualizaListaContatoEmailHomePage();
            }
        }
        if (isTelefone) {
            TabTelefoneVO telefoneEdit = null;
            if (isEmpresa) telefoneEdit = listTelefone.getSelectionModel().getSelectedItem();
            else telefoneEdit = listContatoTelefone.getSelectionModel().getSelectedItem();
            if (telefoneEdit == null) return;
            if (isEmpresa) index = getEmpresaVO().getTabTelefoneVOList().indexOf(telefoneEdit);
            else index = getContatoVO().getTabTelefoneVOList().indexOf(telefoneEdit);
            if ((telefoneEdit = addEditTelefone(telefoneEdit)) == null) return;
            if (isEmpresa) {
                getEmpresaVO().getTabTelefoneVOList().set(index, telefoneEdit);
                //atualizaListaTelefone();
            } else {
                getContatoVO().getTabTelefoneVOList().set(index, telefoneEdit);
                //atualizaListaContatoTelefone();
            }
        }
    }

    TabEnderecoVO addEndereco() {
        List<SisTipoEnderecoVO> list = tipoEnderecoVOList.stream()
                .filter(tp -> getEmpresaVO().getTabEnderecoVOList().stream()
                        .filter(end -> tp.getDescricao().equals(end.getSisTipoEnderecoVO().getDescricao()))
                        .count() == 0)
                .collect(Collectors.toList());
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
                    .getRetornoAlert_TextFieldEComboBox(telefoneOperadoraVOList, "telefone", textPreLoader).get();
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
                    .getRetornoAlert_TextFieldEComboBox(cargoVOList, ServiceFormatarDado
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

            if (getEmpresaVO().getId() == 0)
                getEmpresaVO().setId(new TabEmpresaDAO().insertTabEmpresaVO(conn, getEmpresaVO()));
            else
                new TabEmpresaDAO().updateTabEmpresaVO(conn, getEmpresaVO());
            int idEmpresa = getEmpresaVO().getId();

            new RelEmpresaEnderecoDAO().dedeteRelEmpresaEnderecoVO(conn, idEmpresa);
            getEmpresaVO().getTabEnderecoVOList().stream()
                    .forEach(end -> {
                        try {
                            if (end.getId() < 0) {
                                new TabEnderecoDAO().deleteTabEnderecoVO(conn, end.getId());
                                getEmpresaVO().getTabEnderecoVOList().remove(end);
                            } else {
                                if (end.getId() > 0)
                                    new TabEnderecoDAO().updateTabEnderecoVO(conn, end);
                                else
                                    end.setId(new TabEnderecoDAO().insertTabEnderecoVO(conn, end));
                                new RelEmpresaEnderecoDAO().insertrelEmpresaEnderecoVO(conn, idEmpresa, end.getId());
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException("Erro getTabEnderecoVOList ", ex);
                        }
                    });

            new RelEmpresaEmailHomePageDAO().dedeteRelEmpresaEmailHomePageVO(conn, idEmpresa);
            getEmpresaVO().getTabEmailHomePageVOList().stream()
                    .forEach(emailHome -> {
                        try {
                            if (emailHome.getId() < 0) {
                                new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, emailHome.getId());
                                getEmpresaVO().getTabEmailHomePageVOList().remove(emailHome);
                            } else {
                                if (emailHome.getId() > 0)
                                    new TabEmailHomePageDAO().updateTabEmailHomePageVO(conn, emailHome);
                                else
                                    emailHome.setId(new TabEmailHomePageDAO().insertTabEmailHomePageVO(conn, emailHome));
                                new RelEmpresaEmailHomePageDAO().insertRelEmpresaEmailHomePageVO(conn, idEmpresa, emailHome.getId());
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro getTabEmailHomePageVOList ", ex);
                        }
                    });

            new RelEmpresaTelefoneDAO().deleteRelEmpresaTelefoneVO(conn, idEmpresa);
            getEmpresaVO().getTabTelefoneVOList().stream()
                    .forEach(telefone -> {
                        try {
                            if (telefone.getId() < 0) {
                                new TabTelefoneDAO().deleteTabTelefoneVO(conn, telefone.getId());
                                getEmpresaVO().getTabTelefoneVOList().remove(telefone);
                            } else {
                                if (telefone.getId() > 0)
                                    new TabTelefoneDAO().updateTabTelefoneVO(conn, telefone);
                                else
                                    telefone.setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, telefone));
                                new RelEmpresaTelefoneDAO().insertRelEmpresaTelefoneVO(conn, idEmpresa, telefone.getId());
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro getTabTelefoneVOList ", ex);
                        }
                    });

            new RelEmpresaContatoDAO().deleteRelEmpresaContatoVO(conn, idEmpresa);
            getEmpresaVO().getTabContatoVOList().stream()
                    .forEach(contato -> {
                        try {
                            new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn, contato.getId());
                            new RelContatoTelefoneDAO().deleteRelContatoTelefoneVO(conn, contato.getId());
                            if (contato.getId() < 0) {
                                List<RelContatoEmailHomePageVO> relContatoEmailHomePageVOS =
                                        new RelContatoEmailHomePageDAO().getRelContatoEmailHomePageVOList(contato.getId());
                                new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn, contato.getId());
                                relContatoEmailHomePageVOS.stream()
                                        .forEach(contatoEmailHome -> {
                                            try {
                                                new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, contatoEmailHome.getTabEmailHomePage_id());
                                            } catch (Exception ex) {
                                                throw new RuntimeException("Erro getTabContatoEmailHomePageVOList ", ex);
                                            }
                                        });

                                List<RelContatoTelefoneVO> relContatoTelefoneVOS =
                                        new RelContatoTelefoneDAO().getRelContatoTelefoneVOList(contato.getId());
                                new RelContatoTelefoneDAO().deleteRelContatoTelefoneVO(conn, contato.getId());
                                relContatoTelefoneVOS.stream()
                                        .forEach(contatoTelefone -> {
                                            try {
                                                new TabTelefoneDAO().deleteTabTelefoneVO(conn, contatoTelefone.getTabTelefone_id());
                                            } catch (Exception ex) {
                                                throw new RuntimeException("Erro getTabContatoTelefoneVOList ", ex);
                                            }
                                        });

                                new TabContatoDAO().deleteTabContatoVO(conn, contato.getId());
                                getEmpresaVO().getTabContatoVOList().remove(contato);
                            } else {
                                new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn, contato.getId());
                                new RelContatoTelefoneDAO().deleteRelContatoTelefoneVO(conn, contato.getId());
                                if (contato.getId() > 0)
                                    new TabContatoDAO().updateTabContatoVO(conn, contato);
                                else
                                    contato.setId(new TabContatoDAO().insertTabContatoVO(conn, contato));
                                new RelEmpresaContatoDAO().insertRelEmpresaContatoVO(conn, idEmpresa, contato.getId());

                                contato.getTabEmailHomePageVOList().stream()
                                        .forEach(contatoEmailHome -> {
                                            try {
                                                if (contatoEmailHome.getId() < 0) {
                                                    new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, contatoEmailHome.getId());
                                                    contato.getTabEmailHomePageVOList().remove(contatoEmailHome);
                                                } else {
                                                    if (contatoEmailHome.getId() > 0)
                                                        new TabEmailHomePageDAO().updateTabEmailHomePageVO(conn, contatoEmailHome);
                                                    else
                                                        contatoEmailHome.setId(new TabEmailHomePageDAO().insertTabEmailHomePageVO(conn, contatoEmailHome));
                                                    new RelContatoEmailHomePageDAO().insertRelContatoEmailHomePageVO(conn, contato.getId(), contatoEmailHome.getId());
                                                }
                                            } catch (Exception ex) {
                                                throw new RuntimeException("Erro getTabContatoEmailHomePageVOList ", ex);
                                            }
                                        });
                                contato.getTabTelefoneVOList().stream()
                                        .forEach(contatoTelefone -> {
                                            try {
                                                if (contatoTelefone.getId() < 0) {
                                                    new TabTelefoneDAO().deleteTabTelefoneVO(conn, contatoTelefone.getId());
                                                    contato.getTabTelefoneVOList().remove(contatoTelefone);
                                                } else {
                                                    if (contatoTelefone.getId() > 0)
                                                        new TabTelefoneDAO().updateTabTelefoneVO(conn, contatoTelefone);
                                                    else
                                                        contatoTelefone.setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, contatoTelefone));
                                                    new RelContatoTelefoneDAO().insertRelContatoTelefoneVO(conn, contato.getId(), contatoTelefone.getId());
                                                }
                                            } catch (Exception ex) {
                                                throw new RuntimeException("Erro getTabContatoTelefoneVOList ", ex);
                                            }
                                        });


                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            //throw new RuntimeException("Erro getTabContatoVOList ", ex);
                        }
                    });

            getEmpresaVO().getTabEmpresaReceitaFederalVOList().stream()
                    .forEach(receita -> {
                        try {
                            if (receita.getId() < 0) {
                                new TabEmpresaReceitaFederalDAO().deleteTabEmpresaReceitaFederalVO(conn, receita.getId());
                            } else {
                                receita.setTabEmpresa_id(idEmpresa);
                                if (receita.getId() > 0)
                                    new TabEmpresaReceitaFederalDAO().updateTabEmpresaReceitaFederalVO(conn, receita);
                                else
                                    receita.setId(new TabEmpresaReceitaFederalDAO().insertTabEmpresaReceitaFederalVO(conn, receita));
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro getTabEmpresaReceitaFederalVOList ", ex);
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