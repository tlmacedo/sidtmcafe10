package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
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
import javafx.beans.InvalidationListener;
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

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ControllerCadastroEmpresa extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

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
    public JFXComboBox<SisUfVO> cboEndUF;
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
    public TitledPane tpnEndereco;

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

        new ServiceSegundoPlano().tarefaAbreCadastroEmpresa(getTaskCadastroEmpresa(), listaTarefa.size());

        formatCnpj = new ServiceFormatarDado();
//        formatCnpj.maskField(txtCNPJ, ServiceFormatarDado.gerarMascara("cnpj", 0, "#"));
        formatIe = new ServiceFormatarDado();
//        formatIE.maskField(txtIE, ServiceFormatarDado.gerarMascara("ie", 0, "#"));
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
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 0 || newValue.intValue() == oldValue.intValue()) return;
            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(tituloTab))
                ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
        });

        empresaVOFilteredList.addListener((InvalidationListener) c -> {
            atualizaQtdRegistroLocalizado();
        });

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
                        setStatusFormulario("incluir");
//                        setEmpresaVO(new TabEmpresaVO(1));
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        listEndereco.getSelectionModel().selectFirst();
                        if (!validarDados()) break;
                        if (buscaDuplicidade()) break;
                        salvarEmpresa();
                        if (getStatusFormulario().toLowerCase().equals("editar"))
//                            empresaVOObservableList.set(
//                                    empresaVOObservableList.indexOf(empresaVOObservableList.stream()
//                                            .filter(empList -> empList.getCnpj().equals(getEmpresaVO().getCnpj()))
//                                            .findFirst().orElse(null))
//                                    , new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
                            empresaVOObservableList.set(empresaVOObservableList.indexOf(ttvEmpresa.getSelectionModel().getSelectedItem().getValue())
                                    , getEmpresaVO());
                        else if (getStatusFormulario().toLowerCase().equals("incluir"))
                            //empresaVOObservableList.add(new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
                            empresaVOObservableList.add(getEmpresaVO());


                        setStatusFormulario("pesquisa");
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
//                                setEmpresaVO(ttvEmpresa.getSelectionModel().getSelectedItem().getValue());
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
                        break;
                    case F6:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa") || !(event.isShiftDown())) break;
//                        keyShiftF6();
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
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        if (listEndereco.isFocused())
                            if (event.getCode() == KeyCode.HELP)
                                addEndereco();
                            else
                                delEndereco();
                        if (listHomePage.isFocused() || listEmail.isFocused())
                            if (event.getCode() == KeyCode.HELP)
                                addEmailHomePage();
                            else
                                delEmailHomePage();
                        break;
                }
            }
        };

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroEmpresa);


        ttvEmpresa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                setEmpresaVO(newValue.getValue());
        });

        txtPesquisaEmpresa.textProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        cboFiltroPesquisa.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        txtPesquisaEmpresa.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvEmpresa.requestFocus();
            ttvEmpresa.getSelectionModel().selectFirst();
        });

        cboClassificacaoJuridica.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            txtIE.setPromptText(newValue.intValue() == 0 ? "RG" : "IE");
            txtRazao.setPromptText(newValue.intValue() == 0 ? "Nome" : "Razão");
            txtFantasia.setPromptText(newValue.intValue() == 0 ? "Apelido" : "Fantasia");
            txtCNPJ.setPromptText(newValue.intValue() == 0 ? "C.P.F." : "C.N.P.J.");
            formatCnpj.setMascara(ServiceFormatarDado.gerarMascara(newValue.intValue() == 0 ? "cpf" : "cnpj", 0, "#"));
            if (txtCNPJ.getText().replaceAll("\\D", "").length() > 0)
                txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(txtCNPJ.getText().replaceAll("\\D", ""), txtCNPJ.getPromptText().toLowerCase().replaceAll(".", "")));
        });

//        txtCNPJ.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                String valueCnpj = txtCNPJ.getText().replaceAll("\\D", "");
//                if (!ServiceValidarDado.isCnpjCpfValido(valueCnpj)) {
//                    new ServiceAlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
//                            + txtCNPJ.getPromptText() + ": " + txtCNPJ.getText() + " é inválido!",
//                            "ic_web_service_err_white_24dp").getRetornoAlert_OK();
//                    txtCNPJ.requestFocus();
//                    return;
//                } else if (buscaDuplicidade(valueCnpj)) {
//                    txtCNPJ.requestFocus();
//                    return;
//                } else {
//                    TabEmpresaVO buscaEmpresaCNPJ;
//                    if ((buscaEmpresaCNPJ = new ServiceConsultaWebServices().getSistuacaoCNPJ_receitaWs(getEmpresaVO(), valueCnpj)) == null) {
//                        new ServiceAlertMensagem("Dado não localizada!", USUARIO_LOGADO_APELIDO + ", o "
//                                + "C.N.P.J.: " + txtCNPJ.getText() + " não foi localizado na base de dados!",
//                                "ic_web_service_err_white_24dp").getRetornoAlert_OK();
//                        txtEndCEP.requestFocus();
//                        return;
//                    } else {
//                        setEmpresaVO(buscaEmpresaCNPJ);
//                        txtRazao.requestFocus();
//                    }
//
//                }
//            }
//        });

        listEndereco.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!getStatusFormulario().toLowerCase().equals("pesquisa"))
                if (oldValue != null && newValue != oldValue)
                    guardarEndereco(oldValue);
            if (newValue != null && newValue != oldValue)
                setEnderecoVO(newValue);
        });

        listContatoNome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (!getStatusFormulario().toLowerCase().equals("pesquisa"))
//                if (oldValue != null && newValue != oldValue)
//                    guardarContato(oldValue);
            if (newValue != null && newValue != oldValue)
                setContatoVO(newValue);
        });

        txtEndCEP.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            String valueCep;
            if (event.getCode() == KeyCode.ENTER && (valueCep = txtEndCEP.getText().replaceAll("\\D", "")).length() == 8) {
                TabEnderecoVO enderecoBuscaCEP;
                if ((enderecoBuscaCEP = new ServiceConsultaWebServices()
                        .getEnderecoCep_postmon(getEnderecoVO().getId(), getEnderecoVO().getSisTipoEndereco_id(), valueCep)) == null) {
                    System.out.println(enderecoBuscaCEP);
                    alertMensagem = new ServiceAlertMensagem();
                    alertMensagem.setCabecalho("Dado inválido!");
                    alertMensagem.setPromptText(String.format("%s, o cep: [%s] não foi localizado na base de dados!",
                            USUARIO_LOGADO_APELIDO, txtEndCEP.getText()));
                    alertMensagem.setStrIco("ic_web_service_err_white_24dp.png");
                    alertMensagem.getRetornoAlert_OK();
                    txtEndCEP.requestFocus();
                } else {
                    System.out.println(enderecoBuscaCEP);
                    setEnderecoVO(enderecoBuscaCEP);
                    exibirDadosEndereco();
                    txtEndNumero.requestFocus();
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

        cboEndUF.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SisUfVO> observable, SisUfVO oldValue, SisUfVO newValue) -> {
            if (newValue == null) return;
            cboEndMunicipio.getItems().setAll(newValue.getMunicipioVOList());
            cboEndMunicipio.getSelectionModel().selectFirst();
            formatIe.setMascara(ServiceFormatarDado.gerarMascara("ie" + newValue.getSigla(), 0, "#"));

            if (getEmpresaVO() != null && getEmpresaVO().getIe().length() > 0)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + newValue.getSigla()));
        });

        empresaVOFilteredList.addListener((ListChangeListener<TabEmpresaVO>) c -> {
            atualizaQtdRegistroLocalizado();
        });
    }

    @Override
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

    ObservableList<TabEmpresaVO> empresaVOObservableList;
    FilteredList<TabEmpresaVO> empresaVOFilteredList;
    TabEmpresaVO empresaVO;
    TabEnderecoVO enderecoVO;
    TabContatoVO contatoVO;

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

    EventHandler<KeyEvent> eventHandlerCadastroEmpresa;
    List<Pair> listaTarefa = new ArrayList<>();
    ServiceFormatarDado formatCnpj, formatIe;
    ServiceAlertMensagem alertMensagem;
    String statusFormulario, statusBarTecla, tituloTab = ViewCadastroEmpresa.getTituloJanela();

    Task getTaskCadastroEmpresa() {
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
                        case "criarTabelaEmpresa":
                            TabModel.tabelaEmpresa();
                            break;
                        case "preencherCboFiltroPesquisa":
                            preencherCboFiltroPesquisa();
                            break;
                        case "preencherCboClassificacaoJuridica":
                            preencherCboClassificacaoJuridica();
                            break;
                        case "preencherCboSituacaoSistema":
                            preencherCboSituacaoSistema();
                            break;
                        case "preencherCboEndUF":
                            preencherCboEndUF();
                            break;
                        case "carregarListaEmpresa":
                            carregarListaEmpresa();
                            break;
                        case "preencherTabelaEmpresa":
                            preencherTabelaEmpresa();
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
        lblRegistrosLocalizados.setText(String.format("[%s] %d registro(s) localizado(s).", getStatusFormulario(), empresaVOFilteredList.size()));
    }

    public String getStatusBarTecla() {
        return statusBarTecla;
    }

    public void setStatusBarTecla(String statusFormulario) {
        switch (statusFormulario.toLowerCase()) {
            case "incluir":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroEmpresa.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoCadastral.getContent());
                cboClassificacaoJuridica.requestFocus();
                cboClassificacaoJuridica.getSelectionModel().selectFirst();
                txtIE.setDisable(chkIeIsento.isSelected());
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
                txtIE.setDisable(chkIeIsento.isSelected());
                statusBarTecla = STATUS_BAR_TECLA_EDITAR;
                break;
            case "pesquisa":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroEmpresa.getContent(), false);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), true);
                ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnDadoCadastral.getContent());
                txtPesquisaEmpresa.requestFocus();
                statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
                break;
        }
        ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
    }


    void preencherCboFiltroPesquisa() {
        cboFiltroPesquisa.getItems().setAll(List.of("", "CLIENTES", "FORNECEDORES", "TRANSPORTADORAS"));
        cboFiltroPesquisa.getSelectionModel().selectFirst();
    }

    void preencherCboClassificacaoJuridica() {
        cboClassificacaoJuridica.getItems().setAll(List.of("FÍSICA", "JURÍDICA"));
        cboClassificacaoJuridica.getSelectionModel().selectLast();
    }

    void preencherCboSituacaoSistema() {
        cboSituacaoSistema.getItems().setAll(new ArrayList<>(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVOList()));
        cboSituacaoSistema.getSelectionModel().selectFirst();
    }

    void preencherCboEndUF() {
        cboEndUF.getItems().setAll(new ArrayList<>(new SisUFDAO().getSisUFVOList_DetMunicipio()));
    }

    void carregarListaEmpresa() {
        empresaVOFilteredList = new FilteredList<>(
                empresaVOObservableList = FXCollections.observableArrayList(new TabEmpresaDAO().getTabEmpresaVOList(false))
        );
    }

    void preencherTabelaEmpresa() {
        if (empresaVOFilteredList == null) {
            carregarListaEmpresa();
            pesquisaEmpresa();
        }
        final TreeItem<TabEmpresaVO> root = new RecursiveTreeItem<TabEmpresaVO>(empresaVOFilteredList, RecursiveTreeObject::getChildren);
        ttvEmpresa.getColumns().setAll(TabModel.getColunaIdEmpresa(), TabModel.getColunaCnpj(), TabModel.getColunaIe(),
                TabModel.getColunaRazao(), TabModel.getColunaFantasia(), TabModel.getColunaEndereco(),
                TabModel.getColunaIsCliente(), TabModel.getColunaIsFornecedor(), TabModel.getColunaIsTransportadora());
        ttvEmpresa.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ttvEmpresa.setRoot(root);
        ttvEmpresa.setShowRoot(false);
    }

    void pesquisaEmpresa() {
        String busca = txtPesquisaEmpresa.getText().toLowerCase().trim();
        int filtro = cboFiltroPesquisa.getSelectionModel().getSelectedIndex();

        if (busca.length() == 0 && filtro == 0)
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
                if (empresa.getTabEnderecoVOList().stream()
                        .filter(end -> end.getCep().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabEnderecoVOList().stream()
                        .filter(end -> end.getLogradouro().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabEnderecoVOList().stream()
                        .filter(end -> end.getNumero().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabEnderecoVOList().stream()
                        .filter(end -> end.getComplemento().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabEnderecoVOList().stream()
                        .filter(end -> end.getBairro().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabEnderecoVOList().stream()
                        .filter(end -> end.getSisMunicipioVO().getDescricao().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabEnderecoVOList().stream()
                        .filter(end -> end.getSisMunicipioVO().getUfVO().getSigla().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;

                if (empresa.getTabEmailHomePageVOList().stream()
                        .filter(mail -> mail.getDescricao().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;

                if (empresa.getTabTelefoneVOList().stream()
                        .filter(tel -> tel.getDescricao().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabTelefoneVOList().stream()
                        .filter(tel -> tel.getSisTelefoneOperadoraVO().getDescricao().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;

                if (empresa.getTabContatoVOList().stream()
                        .filter(cont -> cont.getDescricao().toLowerCase().contains(busca))
                        .findFirst().orElse(null) != null) return true;
                if (empresa.getTabContatoVOList().stream()
                        .filter(cont -> cont.getTabEmailHomePageVOList().stream()
                                .filter(contMail -> contMail.getDescricao().toLowerCase().contains(busca))
                                .count() > 0).findFirst().orElse(null) != null) return true;
                if (empresa.getTabContatoVOList().stream()
                        .filter(cont -> cont.getTabTelefoneVOList().stream()
                                .filter(contTel -> contTel.getDescricao().toLowerCase().contains(busca))
                                .count() > 0).findFirst().orElse(null) != null) return true;
                if (empresa.getTabContatoVOList().stream()
                        .filter(cont -> cont.getTabTelefoneVOList().stream()
                                .filter(contTel -> contTel.getSisTelefoneOperadoraVO().getDescricao().toLowerCase().contains(busca))
                                .count() > 0).findFirst().orElse(null) != null) return true;
                return false;
            });
        preencherTabelaEmpresa();
    }

    public TabEmpresaVO getEmpresaVO() {
        return empresaVO;
    }

    public void setEmpresaVO(TabEmpresaVO empresa) {
        if (empresa == null)
            empresa = new TabEmpresaVO();
        empresaVO = empresa;
        exibirDadosEmpresa();
    }

    public TabEnderecoVO getEnderecoVO() {
        return enderecoVO;
    }

    public void setEnderecoVO(TabEnderecoVO endereco) {
        if (endereco == null)
            endereco = new TabEnderecoVO(1, 0);
        enderecoVO = endereco;
        exibirDadosEndereco();
    }

    public TabContatoVO getContatoVO() {
        return contatoVO;
    }

    public void setContatoVO(TabContatoVO contato) {
        if (contato == null)
            contato = new TabContatoVO();
        contatoVO = contato;
        exibirDadosContato();
    }

    void exibirDadosEmpresa() {
        txtCNPJ.setText(getEmpresaVO().isIsEmpresa() ? ServiceFormatarDado.getValorFormatado(getEmpresaVO().getCnpj(), "cnpj") : ServiceFormatarDado.getValorFormatado(getEmpresaVO().getCnpj(), "cpf"));
        txtIE.setText(getEmpresaVO().isIsEmpresa() ? ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + getEmpresaVO().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()) : ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie"));
        chkIeIsento.setSelected(getEmpresaVO().isIeIsento());
        cboSituacaoSistema.getSelectionModel().select(getEmpresaVO().getSisSituacaoSistemaVO());
        txtRazao.setText(getEmpresaVO().getRazao());
        txtFantasia.setText(getEmpresaVO().getFantasia());
        chkIsCliente.setSelected(getEmpresaVO().isIsCliente());
        chkIsFornecedor.setSelected(getEmpresaVO().isIsFornecedor());
        chkIsTransportadora.setSelected(getEmpresaVO().isIsTransportadora());

        lblNaturezaJuridica.setText(String.format("Natureza Júridica: %s",
                getEmpresaVO().getNaturezaJuridica() == null ? "sem natureza júridica" : getEmpresaVO().getNaturezaJuridica()));
        lblDataAbertura.setText(String.format("data abertura: %s",
                getEmpresaVO().getDataAbertura() == null ? "null" : getEmpresaVO().getDataAbertura().toLocalDate().format(DTF_DATA)));
        lblDataAberturaDiff.setText(String.format("tempo de abertura: %s",
                getEmpresaVO().getDataAbertura() == null ? "sem data abertura" :
                        ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAbertura().toLocalDate(), null)));
        lblDataCadastro.setText(String.format("data cadastro: %s [%s]",
                getEmpresaVO().getDataCadastro() == null ? "" : getEmpresaVO().getDataCadastro().toLocalDateTime().format(DTF_DATAHORA),
                getEmpresaVO().getUsuarioCadastroVO().getApelido()));
        lblDataCadastroDiff.setText(String.format("tempo de cadastro: %s",
                getEmpresaVO().getDataCadastro() == null ? "" :
                        ServiceDataHora.getIntervaloData(getEmpresaVO().getDataCadastro().toLocalDateTime().toLocalDate(), null)));
        lblDataAtualizacao.setText(String.format("data atualização: %s [%s]",
                getEmpresaVO().getDataAtualizacao() == null ? "sem atualização" : getEmpresaVO().getDataAtualizacao().toLocalDateTime().format(DTF_DATAHORA),
                getEmpresaVO().getUsuarioCadastroVO().getApelido()));
        lblDataAtualizacaoDiff.setText(String.format("tempo de atualização: %s",
                getEmpresaVO().getDataAtualizacao() == null ? "sem atualização" :
                        ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAtualizacao().toLocalDateTime().toLocalDate(), null)));

        atualizaListaEndereco();
        listEndereco.getSelectionModel().selectFirst();

        atualizaListaEmailHomePage();

        atualizaListaTelefone();

        atualizaListaContato();
        listContatoNome.getSelectionModel().selectFirst();
    }

    void exibirDadosEndereco() {
        tpnEndereco.setText(listEndereco.getItems().size() + " Endereços cadastrados ");
        if (getEnderecoVO() == null) return;
        tpnEndereco.setText(listEndereco.getItems().size() + " Endereços cadastrados: [" + getEnderecoVO() + "]");
        txtEndCEP.setText(ServiceFormatarDado.getValorFormatado(getEnderecoVO().getCep(), "cep"));
        txtEndLogradouro.setText(getEnderecoVO().getLogradouro());
        txtEndNumero.setText(getEnderecoVO().getNumero());
        txtEndComplemento.setText(getEnderecoVO().getComplemento());
        txtEndBairro.setText(getEnderecoVO().getBairro());
        txtEndPontoReferencia.setText(getEnderecoVO().getPontoReferencia());
        cboEndUF.getSelectionModel().select(cboEndUF.getItems().stream()
                .filter(uf -> uf.getSigla().equals(getEnderecoVO().getSisMunicipioVO().getUfVO().getSigla()))
                .findFirst().orElse(null));
        cboEndMunicipio.getSelectionModel().select(cboEndMunicipio.getItems().stream()
                .filter(municipio -> municipio.getDescricao().equals(getEnderecoVO().getSisMunicipioVO().getDescricao()))
                .findFirst().orElse(null));
    }

    void exibirDadosContato() {
        tpnPessoaContato.setText("Pessoa de contato");
        if (getContatoVO() == null) return;
        tpnPessoaContato.setText(String.format("Pessoa de contato: [%s]", getContatoVO().getDescricao()));
        listContatoHomePage.getItems().setAll(getContatoVO().getTabEmailHomePageVOList().stream()
                .filter(contHome -> contHome.getId() >= 0 && !contHome.isIsEmail())
                .collect(Collectors.toList()));
        listContatoEmail.getItems().setAll(getContatoVO().getTabEmailHomePageVOList().stream()
                .filter(contEmail -> contEmail.getId() >= 0 && contEmail.isIsEmail())
                .collect(Collectors.toList()));
        listContatoTelefone.getItems().setAll(getContatoVO().getTabTelefoneVOList().stream()
                .filter(contTel -> contTel.getId() >= 0)
                .collect(Collectors.toList()));
    }

    boolean guardarEmpresa() {
        try {
            getEmpresaVO().setIsEmpresa(cboClassificacaoJuridica.getSelectionModel().getSelectedIndex() == 1);
            getEmpresaVO().setCnpj(txtCNPJ.getText().replaceAll("\\D", ""));
            getEmpresaVO().setIe(txtIE.getText().replaceAll("\\D", ""));
            getEmpresaVO().setIeIsento(chkIeIsento.isSelected());
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
            if (m.find())
                getEmpresaVO().setDataAbertura(Date.valueOf(m.group()));

            getEmpresaVO().setNaturezaJuridica(lblNaturezaJuridica.getText().substring(19));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    boolean guardarEndereco(TabEnderecoVO endAntigo) {
        try {
            endAntigo.setCep((txtEndCEP.getText() == null) ? "" : txtEndCEP.getText().replaceAll("\\D", ""));
            endAntigo.setLogradouro(txtEndLogradouro.getText());
            endAntigo.setNumero(txtEndNumero.getText());
            endAntigo.setComplemento(txtEndComplemento.getText());
            endAntigo.setBairro(txtEndBairro.getText());
            endAntigo.setPontoReferencia(txtEndPontoReferencia.getText());
            endAntigo.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(cboEndMunicipio.getSelectionModel().getSelectedItem().getId()));
            endAntigo.setSisMunicipio_id(cboEndMunicipio.getSelectionModel().getSelectedItem().getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    List<SisTipoEnderecoVO> getEnderecoDisponivel() {
        return new ArrayList<>(new SisTipoEnderecoDAO().getSisTipoEnderecoVOList()).stream()
                .filter(tp -> getEmpresaVO().getTabEnderecoVOList().stream()
                        .filter(end -> tp.getDescricao().equals(end.getSisTipoEnderecoVO().getDescricao()))
                        .count() == 0)
                .collect(Collectors.toList());
    }

    void delEndereco() {
        if (getEnderecoVO() == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setCabecalho(getEnderecoVO().getSisTipoEndereco_id() == 1 ? "Proteção de dados!" : "Deletar dados [endereço]");
        alertMensagem.setPromptText(getEnderecoVO().getSisTipoEndereco_id() == 1 ?
                String.format("%s, o endereço principal não pode ser deletado!", USUARIO_LOGADO_APELIDO) :
                String.format("%s, deseja deletar o endereço: [%s]\nda empresa: [%s] ?", USUARIO_LOGADO_APELIDO, getEnderecoVO(), txtRazao.getText()));
        alertMensagem.setStrIco(getEnderecoVO().getSisTipoEndereco_id() == 1 ? "ic_dados_invalidos_white_24dp.png" : "ic_endereco_invalido_white_24dp.png");
        if (getEnderecoVO().getSisTipoEndereco_id() == 1) {
            guardarEndereco(getEnderecoVO());
            alertMensagem.getRetornoAlert_OK();
            return;
        } else {
            if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
            limparEndereco();
            if (getEnderecoVO().getId() == 0)
                getEmpresaVO().getTabEnderecoVOList().remove(getEnderecoVO());
            else
                getEnderecoVO().setId(getEnderecoVO().getId() * (-1));
            atualizaListaEndereco();
            listEndereco.getSelectionModel().selectFirst();

        }
    }

    void addEndereco() {
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_endereco_add_white_24dp.png");
        if (getEnderecoDisponivel().size() <= 0) {
            alertMensagem.setCabecalho("Endereço não disponivél");
            alertMensagem.setPromptText(String.format("%s, a empresa: [%s] não endereço disponível!\nAtualize algum endereço já existente!",
                    USUARIO_LOGADO_APELIDO, txtRazao.getText()));
            alertMensagem.getRetornoAlert_OK();
            return;
        }
        alertMensagem.setCabecalho("Adicionar dados [endereço]");
        alertMensagem.setPromptText(String.format("%s, selecione o tipo endereço que vai ser adicionado\na empresa: [%s]", USUARIO_LOGADO_APELIDO, txtRazao.getText()));
        Object obj;
        if ((obj = alertMensagem.getRetornoAlert_ComboBox(getEnderecoDisponivel()).orElse(null)) == null) return;
        int idMunicipio = getEmpresaVO().getTabEnderecoVOList().size() > 0 ? getEmpresaVO().getTabEnderecoVOList().get(0).getSisMunicipio_id() : 0;
        getEmpresaVO().getTabEnderecoVOList().add(new TabEnderecoVO(((SisTipoEnderecoVO) obj).getId(), idMunicipio));
        atualizaListaEndereco();
        listEndereco.getSelectionModel().selectLast();
        txtEndCEP.requestFocus();
    }

    void delEmailHomePage() {
        alertMensagem = new ServiceAlertMensagem();
        TabEmailHomePageVO emailHomePage = listEmail.isFocused() ?
                listEmail.getSelectionModel().getSelectedItem() :
                listHomePage.getSelectionModel().getSelectedItem();
        alertMensagem.setStrIco(listEmail.isFocused() ? "ic_web_email_white_24dp.png" : "ic_web_home_page_white_24dp.png");
        alertMensagem.setCabecalho(String.format("Deletar dados [%s]", listEmail.isFocused() ? "email" : "home page"));
        alertMensagem.setPromptText(String.format("%s, deseja deletar %s: [%s]\nda empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, listEmail.isFocused() ? "email" : "home page",
                listEmail.isFocused() ? listEmail.getSelectionModel().getSelectedItem() : listHomePage.getSelectionModel().getSelectedItem(),
                txtRazao.getText()));
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        if (emailHomePage.getId() == 0)
            getEmpresaVO().getTabEmailHomePageVOList().remove(emailHomePage);
        else
            getEmpresaVO().getTabEmailHomePageVOList().get(getEmpresaVO().getTabEmailHomePageVOList().indexOf(emailHomePage))
                    .setId(emailHomePage.getId() * (-1));
        atualizaListaEmailHomePage();
    }

    void addEmailHomePage() {
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco(listEmail.isFocused() ? "ic_web_email_white_24dp.png" : "ic_web_home_page_white_24dp.png");
        alertMensagem.setCabecalho(String.format("Adicionar dados [%s]", listEmail.isFocused() ? "email" : "home page"));
        alertMensagem.setPromptText(String.format("%s, qual %s a ser adicionado para a empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, listEmail.isFocused() ? "email" : "home page", txtRazao.getText()));
        String mailHome;
        if ((mailHome = alertMensagem.getRetornoAlert_TextField(
                ServiceFormatarDado.gerarMascara("email", 120, "?"), "")
                .orElse(null)) == null) return;
        while (!ServiceValidarDado.isEmailHomePageValido(mailHome, listEmail.isFocused()))
            addEmailHomePage();
        getEmpresaVO().getTabEmailHomePageVOList().add(new TabEmailHomePageVO(mailHome, listEmail.isFocused()));
        atualizaListaEmailHomePage();


    }

    void atualizaListaEndereco() {
        listEndereco.getItems().setAll(getEmpresaVO().getTabEnderecoVOList().stream()
                .filter(end -> end.getId() >= 0)
                .collect(Collectors.toList()));
    }

    void atualizaListaEmailHomePage() {
        listHomePage.getItems().setAll(getEmpresaVO().getTabEmailHomePageVOList().stream()
                .filter(home -> home.getId() >= 0 && !home.isIsEmail())
                .collect(Collectors.toList()));
        listEmail.getItems().setAll(getEmpresaVO().getTabEmailHomePageVOList().stream()
                .filter(email -> email.getId() >= 0 && email.isIsEmail())
                .collect(Collectors.toList()));
    }

    void atualizaListaTelefone() {
        listTelefone.getItems().setAll(getEmpresaVO().getTabTelefoneVOList().stream()
                .filter(tel -> tel.getId() >= 0)
                .collect(Collectors.toList()));
    }

    void atualizaListaContato() {
        listContatoNome.getItems().setAll(getEmpresaVO().getTabContatoVOList().stream()
                .filter(contato -> contato.getId() >= 0)
                .collect(Collectors.toList()));
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

    boolean validarDados() {
        return (validarDadosEmpresa() && validarEnderecoPrincipal());
    }

    boolean validarDadosEmpresa() {
        boolean result = true;
        if (!(result = ServiceValidarDado.isCnpjCpfValido(txtCNPJ.getText().replaceAll("\\D", ""))))
            txtCNPJ.requestFocus();
        if (!(result = (txtRazao.getText().length() >= 3 && result == true)))
            txtRazao.requestFocus();
        if (!(result = (txtFantasia.getText().length() >= 3 && result == true)))
            txtFantasia.requestFocus();
        chkIeIsento.setSelected(txtIE.getLength() == 0);
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
        if (getEmpresaVO().getTabEmpresaReceitaFederalVOList().size() > 0)
            if (!getEmpresaVO().getTabEmpresaReceitaFederalVOList().stream()
                    .filter(receita -> receita.getStr_Key().toLowerCase().equals("situacao"))
                    .findFirst().orElse(null).getStr_Value().toLowerCase().equals("ativa")) {
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
        else result = guardarEndereco(getEnderecoVO());
        return result;
    }

    boolean buscaDuplicidade() {
        String valueCnpj = txtCNPJ.getText().replaceAll("\\D", "");
        try {
            if (getEmpresaVO().getId() != new TabEmpresaDAO().getTabEmpresaVO_Simples(valueCnpj).getId()) {
                alertMensagem = new ServiceAlertMensagem();
                alertMensagem.setCabecalho("C.N.P.J. duplicado");
                alertMensagem.setPromptText(String.format("%s, o C.N.P.J.: [%s] já está cadastrado no sistema!",
                        USUARIO_LOGADO_APELIDO, txtCNPJ.getText()));
                alertMensagem.setStrIco("ic_web_service_err_white_24dp.png");
                alertMensagem.getRetornoAlert_OK();
                txtCNPJ.requestFocus();
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    void salvarEmpresa() {
        Connection conn = ConnectionFactory.getConnection();
        try {
            conn.setAutoCommit(false);

            if (getEmpresaVO().getId() == 0)
                getEmpresaVO().setId(new TabEmpresaDAO().insertTabEmpresaVO(conn, getEmpresaVO()));
            else
                new TabEmpresaDAO().updateTabEmpresaVO(conn, getEmpresaVO());

            getEmpresaVO().getTabEnderecoVOList().stream().sorted(Comparator.comparing(TabEnderecoVO::getId))
                    .forEach(endereco -> {
                        try {
                            if (endereco.getId() < 0)
                                new TabEnderecoDAO().deleteEnderecoEmpresa(conn, endereco.getId(), getEmpresaVO().getId());
                            else if (endereco.getId() > 0)
                                new TabEnderecoDAO().updateEnderecoEmpresa(conn, endereco, getEmpresaVO().getId());
                            else
                                endereco.setId(new TabEnderecoDAO().insertEnderecoEmpresa(conn, endereco, getEmpresaVO().getId()));
                        } catch (SQLException ex) {
                            throw new RuntimeException("Erro no endereco ===>> ", ex);
                        }
                    });
//            getEmpresaVO().getTabEmailHomePageVOList().stream().sorted(Comparator.comparing(TabEmailHomePageVO::getId))
//                    .forEach(mailHome -> {
//                        try {
//                            if (mailHome.getId() < 0)
//                                new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, mailHome.getId(), getEmpresaVO().getId());
//                        } catch (Exception ex) {
//                            throw new RuntimeException("Erro no emailHome ===>>", ex);
//                        }
//                    });


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