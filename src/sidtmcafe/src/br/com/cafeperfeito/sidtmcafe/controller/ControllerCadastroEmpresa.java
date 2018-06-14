package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.SisMunicipioDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.SisSituacaoSistemaDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.SisUFDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.TabEmpresaDAO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
//                        if (!validarDados()) break;
//                        if (buscaDuplicidade(getEmpresaVO().getCnpj().replaceAll("\\D", ""))) break;
//                        salvarEmpresa();
//                        if (getStatusFormulario().toLowerCase().equals("editar"))
//                            empresaVOObservableList.set(
//                                    empresaVOObservableList.indexOf(empresaVOObservableList.stream()
//                                            .filter(empList -> empList.getCnpj().equals(getEmpresaVO().getCnpj()))
//                                            .findFirst().orElse(null))
//                                    , new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
//                        else if (getStatusFormulario().toLowerCase().equals("incluir"))
//                            empresaVOObservableList.add(new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
//
//
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
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        keyInsert();
                        break;
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
//                        keyDelete();
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
//            ttvEmpresa.getSelectionModel().selectFirst();
            ttvEmpresa.getFocusModel().focus(0);
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

//        txtEndCEP.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                String valueCep = txtEndCEP.getText().replaceAll("\\D", "");
//                if (valueCep.length() != 8) {
//                    new ServiceAlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
//                            + "CEP: " + txtEndCEP.getText() + " é inválido!",
//                            "ic_web_service_err_white_24dp").getRetornoAlert_OK();
//                    txtEndCEP.requestFocus();
//                    return;
//                } else {
//                    TabEnderecoVO enderecoBuscaCEP = getEnderecoVO();
//                    if ((enderecoBuscaCEP = new ServiceConsultaWebServices().getEnderecoCep_postmon(new Pair<>(enderecoBuscaCEP, valueCep))) == null) {
//                        new ServiceAlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
//                                + "CEP: " + txtEndCEP.getText() + " não foi localizado na base de dados!",
//                                "ic_web_service_err_white_24dp").getRetornoAlert_OK();
//                        txtEndCEP.requestFocus();
//                        return;
//                    } else {
//                        setEnderecoVO(enderecoBuscaCEP);
//                        txtEndNumero.requestFocus();
//                    }
//                }
//            }
//        });
//
//        chkIeIsento.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//            txtIE.setDisable(newValue);
//            if (newValue)
//                txtIE.setText("");
//            else if (getEmpresaVO() != null)
//                txtIE.setText(ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + getEmpresaVO().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
//        });

        cboEndUF.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SisUFVO> observable, SisUFVO oldValue, SisUFVO newValue) -> {
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

    EventHandler<KeyEvent> eventHandlerCadastroEmpresa;
    List<Pair> listaTarefa = new ArrayList<>();
    ServiceFormatarDado formatCnpj, formatIe;
    String statusFormulario, statusBarTecla, tituloTab = ViewCadastroEmpresa.getTituloJanela();
    FilteredList<TabEmpresaVO> empresaVOFilteredList;
    TabEmpresaVO empresaVO;
    TabEnderecoVO enderecoVO;
    TabContatoVO contatoVO;

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

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
//                try {
//                    setEmpresaVO(getEmpresaVO().clone());
//                } catch (CloneNotSupportedException e) {
//                    e.printStackTrace();
//                }
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
        empresaVOFilteredList = new FilteredList<>(FXCollections.observableArrayList(new TabEmpresaDAO().getTabEmpresaVOList(false)));
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
            endereco = new TabEnderecoVO(1);
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

        lblNaturezaJuridica.setText("Natureza Júridica: " + (getEmpresaVO().getNaturezaJuridica() == null ? "sem natureza júridica" : getEmpresaVO().getNaturezaJuridica()));
        lblDataAbertura.setText("data abertura: " + ((getEmpresaVO().getDataAbertura() == null) ? "sem data abertura" : getEmpresaVO().getDataAbertura().toLocalDate().format(DTF_DATA)));
        lblDataAberturaDiff.setText("tempo de abertura: " + ((getEmpresaVO().getDataAbertura() == null) ? "sem data abertura" : ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAbertura().toLocalDate(), null)));
        lblDataCadastro.setText("data cadastro: " + ((getEmpresaVO().getDataCadastro() == null) ? "" : getEmpresaVO().getDataCadastro().toLocalDateTime().format(DTF_DATAHORA) + " [" + getEmpresaVO().getUsuarioCadastroVO().getApelido() + "]"));
        lblDataCadastroDiff.setText("tempo de cadastro: " + ((getEmpresaVO().getDataCadastro() == null) ? "" : ServiceDataHora.getIntervaloData(getEmpresaVO().getDataCadastro().toLocalDateTime().toLocalDate(), null)));
        lblDataAtualizacao.setText("data atualização: " + ((getEmpresaVO().getDataAtualizacao() == null) ? "sem atualização" : getEmpresaVO().getDataAtualizacao().toLocalDateTime().format(DTF_DATAHORA) + " [" + getEmpresaVO().getUsuarioCadastroVO().getApelido() + "]"));
        lblDataAtualizacaoDiff.setText("tempo de atualização: " + ((getEmpresaVO().getDataAtualizacao() == null) ? "sem atualização" : ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAtualizacao().toLocalDateTime().toLocalDate(), null)));

        listEndereco.getItems().setAll(getEmpresaVO().getTabEnderecoVOList());
        listEndereco.getSelectionModel().selectFirst();

        listHomePage.getItems().setAll(getEmpresaVO().getTabEmailHomePageVOList().stream()
                .filter(home -> home.getId() >= 0 && !home.isIsEmail())
                .collect(Collectors.toList()));
        listEmail.getItems().setAll(getEmpresaVO().getTabEmailHomePageVOList().stream()
                .filter(email -> email.getId() >= 0 && email.isIsEmail())
                .collect(Collectors.toList()));
        listTelefone.getItems().setAll(getEmpresaVO().getTabTelefoneVOList().stream()
                .filter(tel -> tel.getId() >= 0)
                .collect(Collectors.toList()));

        listContatoNome.getItems().setAll(getEmpresaVO().getTabContatoVOList().stream()
                .filter(contato -> contato.getId() >= 0)
                .collect(Collectors.toList()));
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

//    boolean guardarContato(TabContatoVO contAntigo) {
//        try {
//            contAntigo.setTabTelefoneVOList(listco);
//        } catch (Exception ex){
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
}