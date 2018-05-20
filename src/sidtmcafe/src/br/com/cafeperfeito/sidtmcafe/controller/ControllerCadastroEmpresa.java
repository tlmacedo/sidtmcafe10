package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.model.TabModel;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.*;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroEmpresa;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
                switch (event.getCode()) {
                    case F1:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        setStatusFormulario("incluir");
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        setStatusFormulario("pesquisa");
                        break;
                    case F3:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        switch (getStatusBarTecla()) {
                            case "incluir":
                                break;
                            case "editar":
                                break;
                        }
                        setStatusFormulario("pesquisa");
                        break;
                    case F4:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        setStatusFormulario("editar");
                        break;
                    case F6:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        break;
                    case F7:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        txtPesquisaEmpresa.requestFocus();
                        break;
                    case F8:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        break;
                    case F12:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        fechar();
                        break;
                    case HELP:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        break;
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        break;
                }
            }
        };

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroEmpresa);

        ttvEmpresa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            setTabEmpresaVO(newValue.getValue());
            exibirDadosEmpresa();
        });

        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 0 || newValue.intValue() == oldValue.intValue()) return;
            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(tituloTab))
                ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
        });

//        eventHandlerCadastroEmpresa = new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (!(ControllerPrincipal.ctrlPrincipal.getTabAtual().equals(tituloTab)))
//                    return;
//                switch (event.getCode()) {
//                    case F1:
//                        if (!getStatusBarFormulario().contains(event.getCode().toString())) break;
//                        setTabEmpresaVO(novaEmpresa());
//                        setStatusFormulario("Incluir");
//                        break;
//                    case F2:
//                    case F5:
//                        if (!getStatusBarFormulario().contains(event.getCode().toString())) break;
//                        if (!validarDadosCadastrais()) break;
//
//                        salvarEmpresa();
//                        limparCampoDadoCadastral();
//                        setStatusFormulario("Pesquisa");
//                        carregarListaEmpresa();
//                        preencherTabelaEmpresa();
//                        carregarPesquisaEmpresa(txtPesquisa.getText());
//                        txtPesquisa.requestFocus();
//                        break;
//                    case F3:
//                        if (!getStatusBarFormulario().contains(event.getCode().toString())) break;
//                        switch (getStatusFormulario().toLowerCase()) {
//                            case "incluir":
//                                if (new AlertMensagem("Cancelar inclusão", USUARIO_LOGADO_APELIDO
//                                        + ", deseja cancelar inclusão no cadastro de empresa?",
//                                        "ic_cadastro_empresas_white_24dp.png").getRetornoAlert_YES_NO().get() == ButtonType.NO)
//                                    return;
//                                limparCampoDadoCadastral();
//                                break;
//                            case "editar":
//                                if (new AlertMensagem("Cancelar edição", USUARIO_LOGADO_APELIDO
//                                        + ", deseja cancelar edição do cadastro de empresa?",
//                                        "ic_cadastro_empresas_white_24dp.png").getRetornoAlert_YES_NO().get() == ButtonType.NO)
//                                    return;
//                                limparCampoDadoCadastral();
//                                carregarListaEmpresa();
//                                preencherTabelaEmpresa();
//                                carregarPesquisaEmpresa(txtPesquisa.getText());
//                                setTabEmpresaVO(tabEmpresaVOObservableList.get(indexObservableEmpresa));
//                                break;
//                        }
//                        setStatusFormulario("Pesquisa");
//                        break;
//                    case F4:
//                        if ((!getStatusBarFormulario().contains(event.getCode().toString())) || !(ttvEmpresa.getSelectionModel().getSelectedIndex() >= 0))
//                            break;
//                        indexObservableEmpresa = tabEmpresaVOObservableList.indexOf(getTabEmpresaVO());
//                        setStatusFormulario("Editar");
//                        break;
//                    case F6:
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa") || (!event.isShiftDown())) break;
//                        keyShiftF6();
//                        break;
//                    case F7:
//                        if (!getStatusBarFormulario().contains(event.getCode().toString())) break;
//                        txtPesquisa.requestFocus();
//                        break;
//                    case F8:
//                        if (!getStatusBarFormulario().contains(event.getCode().toString())) break;
//                        cboFiltroPesquisa.requestFocus();
//                        break;
//                    case F12:
//                        if (!getStatusBarFormulario().contains(event.getCode().toString())) break;
//                        fechar();
//                        break;
//                    case HELP:
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                        keyInsert();
//                        break;
//                    case DELETE:
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
//                        keyDelete();
//                        break;
//                }
//            }
//        };
//
//        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerCadastroEmpresa);

        txtPesquisaEmpresa.textProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        cboFiltroPesquisa.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        txtPesquisaEmpresa.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvEmpresa.requestFocus();
            ttvEmpresa.getSelectionModel().select(0);
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
            if (txtCNPJ.getText().replaceAll("\\D", "").length() > 0) {
                System.out.println("txtcnpj: [" + txtCNPJ.getText().replaceAll("\\D", "") + "]");
                txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(txtCNPJ.getText().replaceAll("\\D", ""), txtCNPJ.getPromptText().toLowerCase().replaceAll(".", "")));
            }
        });

//        txtCNPJ.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                String cnpjValue = txtCNPJ.getText().replaceAll("[\\-/., \\[\\]\\(\\)]", "");
//                if ((cnpjValue.length() != 11 && cnpjValue.length() != 14) || (!ValidadarDado.isCnpjCpfValido(cnpjValue))) {
//                    new AlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
//                            + txtCNPJ.getPromptText() + ": " + txtCNPJ.getText() + " é inválido!",
//                            "ic_web_service_err_white_24dp").getRetornoAlert_OK();
//                    txtCNPJ.requestFocus();
//                    return;
//                }
//                if ((new TabEmpresaDAO().getTabEmpresaVO_Simples(cnpjValue)) != null) {
//                    new AlertMensagem("Empresa duplicada", USUARIO_LOGADO_APELIDO + ", o "
//                            + txtCNPJ.getPromptText() + ": " + txtCNPJ.getText() + " já existe no banco de dados!",
//                            "ic_web_service_err_white_24dp").getRetornoAlert_OK();
//                    txtCNPJ.requestFocus();
//                    return;
//                }
//
//                if (cboClassificacaoJuridica.getSelectionModel().getSelectedIndex() == 1) {
//                    listaTarefa = new ArrayList<>();
//                    listaTarefa.add(new Pair("buscarCNPJ", cnpjValue));
//
//                    TabEmpresaVO tabEmpresaVO = new Tarefa().tarefaWsCnpjReceitaWs(listaTarefa);
//                    if (tabEmpresaVO == null)
//                        return;
//                    for (int i = tabEmpresaVO.getTabEnderecoVOList().size(); i < getTabEnderecoVOList().size(); i++) {
//                        tabEmpresaVO.getTabEnderecoVOList().add(getTabEnderecoVOList().get(i));
//                    }
//                    for (TabEmailHomePageVO emailHomePageVO : getTabEmailHomePageVOList())
//                        if (!tabEmpresaVO.getTabEmailHomePageVOList().contains(emailHomePageVO))
//                            tabEmpresaVO.getTabEmailHomePageVOList().add(emailHomePageVO);
//
//                    for (TabTelefoneVO telefoneVO : getTabTelefoneVOList())
//                        if (!tabEmpresaVO.getTabTelefoneVOList().contains(telefoneVO))
//                            tabEmpresaVO.getTabTelefoneVOList().add(telefoneVO);
//
//                    if (getTabEmpresaReceitaFederalVOList().size() > 0) {
//                        if (deletadosTabEmpresaReceitaFederalVOList == null)
//                            deletadosTabEmpresaReceitaFederalVOList = new ArrayList<TabEmpresaReceitaFederalVO>();
//                        for (int i = 0; i < getTabEmpresaReceitaFederalVOList().size(); i++)
//                            for (int j = 0; j < tabEmpresaVO.getTabEmpresaReceitaFederalVOList().size(); j++)
//                                if (getTabEmpresaReceitaFederalVOList().get(i).getStr_Value().toLowerCase().equals(tabEmpresaVO.getTabEmpresaReceitaFederalVOList().get(j).getStr_Value())) {
//                                    tabEmpresaVO.getTabEmpresaReceitaFederalVOList().set(j, getTabEmpresaReceitaFederalVOList().get(i));
//                                } else {
//                                    deletadosTabEmpresaReceitaFederalVOList.add(getTabEmpresaReceitaFederalVOList().get(i));
//                                }
//                    }
//
//
//                    if (getStatusFormulario().toLowerCase().equals("editar")) {
//                        tabEmpresaVO.setId(getTabEmpresaVO().getId());
//                        tabEmpresaVO.setIe(getTabEmpresaVO().getIe());
//                        tabEmpresaVO.setSisSituacaoSistema_id(getTabEmpresaVO().getSisSituacaoSistema_id());
//                        tabEmpresaVO.setSisSituacaoSistemaVO(getTabEmpresaVO().getSisSituacaoSistemaVO());
//                        tabEmpresaVO.setIsLoja(getTabEmpresaVO().isIsLoja());
//                        tabEmpresaVO.setIsCliente(getTabEmpresaVO().isIsCliente());
//                        tabEmpresaVO.setIsFornecedor(getTabEmpresaVO().isIsFornecedor());
//                        tabEmpresaVO.setIsTransportadora(getTabEmpresaVO().isIsTransportadora());
//                        tabEmpresaVO.setUsuarioCadastro_id(getTabEmpresaVO().getUsuarioCadastro_id());
//                        tabEmpresaVO.setUsuarioCadastroVO(getTabEmpresaVO().getUsuarioCadastroVO());
//                        tabEmpresaVO.setDataCadastro(getTabEmpresaVO().getDataCadastro());
//                    }
//
//                    setTabEmpresaVO(tabEmpresaVO);
//                    exibirDadosEmpresa();
//                }
//            }
//        });

        listEndereco.getSelectionModel().selectedIndexProperty().addListener((ov, o, n) -> {
            if (!getStatusFormulario().toLowerCase().equals("pesquisa"))
                if ((o.intValue() >= 0) && (n.intValue() != o.intValue()) && (n.intValue() >= 0))
//                try {
                    guardarEndereco(o.intValue());
//                } catch (Exception ex) {
//                    if (!(ex instanceof IndexOutOfBoundsException))
//                        ex.printStackTrace();
//                }
            if (n == null || n.intValue() < 0) return;
            exibirDadosEndereco();
        });
//
//        txtEndCEP.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                String cepValue = txtEndCEP.getText().replaceAll("[\\-/., \\[\\]\\(\\)]", "");
//                if (cepValue.length() != 8) {
//                    new AlertMensagem("Dado inválido!", USUARIO_LOGADO_APELIDO + ", o "
//                            + "CEP: " + txtEndCEP.getText() + " é inválido!",
//                            "ic_web_service_err_white_24dp").getRetornoAlert_OK();
//                    txtEndCEP.requestFocus();
//                    return;
//                }
//                listaTarefa = new ArrayList<>();
//                listaTarefa.add(new Pair("buscarCEP", cepValue));
//
//                TabEnderecoVO tabEnderecoVO = new Tarefa().tarefaWsCepPostmon(listaTarefa);
//                if (tabEnderecoVO == null)
//                    return;
//                int idEnd = listEndereco.getSelectionModel().getSelectedIndex();
//                tabEnderecoVO.setSisTipoEnderecoVO(new SisTipoEnderecoDAO().getSisTipoEnderecoVO(getTabEnderecoVOList().get(idEnd).getSisTipoEndereco_id()));
//                tabEnderecoVO.setSisTipoEndereco_id(tabEnderecoVO.getSisTipoEnderecoVO().getId());
//                getTabEnderecoVOList().set(idEnd, tabEnderecoVO);
//                exibirDadosEndereco();
//                txtEndNumero.requestFocus();
//            }
//        });
//

        chkIeIsento.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
            txtIE.setDisable(newValue);
            if (txtIE.isDisable())
                txtIE.setText("");
            else if (getTabEmpresaVO() != null)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), "ie" + getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
        });

        cboEndUF.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.intValue() < 0) return;
            String mascUF = "ie" + cboEndUF.getSelectionModel().getSelectedItem().getSigla();
            formatIE.setMascara(ServiceFormatarDado.gerarMascara(mascUF, 0, "#"));

            if (getTabEmpresaVO() != null && getTabEmpresaVO().getIe().length() > 0)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), mascUF));

            preencherCboEndMunicipio();
        });
//
//        listContatoNome.getSelectionModel().selectedIndexProperty().addListener((ov, o, n) -> {
//            if (n == null || n.intValue() < 0) return;
//            setTabContatoVO(getTabContatoVOList().get(n.intValue()));
//        });
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

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F3-Excluir]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

    ServiceFormatarDado formatCNPJ_CPF, formatIE;
    List<Pair> listaTarefa = new ArrayList<>();
    ObservableList<TabEmpresaVO> tabEmpresaVOObservableList;
    FilteredList<TabEmpresaVO> tabEmpresaVOFilteredList;
    TabEmpresaVO tabEmpresaVO;
    TabContatoVO tabContatoVO;
    List<TabEnderecoVO> tabEnderecoVOList;
    List<TabEnderecoVO> deletadosTabEnderecoVOList;
    List<TabEmailHomePageVO> tabEmailHomePageVOList;
    List<TabEmailHomePageVO> deletadosTabEmailHomePageVOList;
    List<TabTelefoneVO> tabTelefoneVOList;
    List<TabTelefoneVO> deletadosTabTelefoneVOList;
    List<TabContatoVO> tabContatoVOList;
    List<TabContatoVO> deletadosTabContatoVOList;
    List<TabEmailHomePageVO> tabContatoEmailHomePageVOList;
    List<TabEmailHomePageVO> deletadosTabContatoEmailHomePageVOList;
    List<TabTelefoneVO> tabContatoTelefoneVOList;
    List<TabTelefoneVO> deletadosTabContatoTelefoneVOList;
    List<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOList;
    List<TabEmpresaReceitaFederalVO> deletadosTabEmpresaReceitaFederalVOList;
    List<TabEmpresaReceitaFederalVO> receitaFederalQsaVOList;

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
                cboClassificacaoJuridica.getSelectionModel().select(0);
                this.statusBarTecla = STATUS_BAR_TECLA_INCLUIR;
                break;
            case "editar":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroEmpresa.getContent(), true);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), false);
                txtCNPJ.requestFocus();
                this.statusBarTecla = STATUS_BAR_TECLA_EDITAR;
                break;
            case "pesquisa":
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnCadastroEmpresa.getContent(), false);
                ServiceCampoPersonalizado.fieldDisable((AnchorPane) tpnDadoCadastral.getContent(), true);
                clearFieldDadoCadastral((AnchorPane) tpnDadoCadastral.getContent());
                txtPesquisaEmpresa.requestFocus();
                this.statusBarTecla = STATUS_BAR_TECLA_PESQUISA;
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
        setTabEnderecoVOList(getTabEmpresaVO().getTabEnderecoVOList());
        setTabEmailHomePageVOList(getTabEmpresaVO().getTabEmailHomePageVOList());
        setTabTelefoneVOList(getTabEmpresaVO().getTabTelefoneVOList());
        setTabContatoVOList(getTabEmpresaVO().getTabContatoVOList());
        setTabEmpresaReceitaFederalVOList(getTabEmpresaVO().getTabEmpresaReceitaFederalVOList());
//        exibirDadosEmpresa();
    }

    public List<TabEnderecoVO> getTabEnderecoVOList() {
        return tabEnderecoVOList;
    }

    public void setTabEnderecoVOList(List tabEnderecoVOList) {
        if (tabEnderecoVOList == null)
            tabEnderecoVOList = FXCollections.observableArrayList(new TabEnderecoVO(1));
        this.tabEnderecoVOList = tabEnderecoVOList;
        atualizaListaEndereco();
    }

    void atualizaListaEndereco() {
        listEndereco.getItems().clear();
        if (getTabEnderecoVOList() != null) {
            listEndereco.getItems().setAll(getTabEnderecoVOList());
            listEndereco.getSelectionModel().select(0);
        }
    }

    public List<TabEmailHomePageVO> getTabEmailHomePageVOList() {
        return tabEmailHomePageVOList;
    }

    public void setTabEmailHomePageVOList(List tabEmailHomePageVOList) {
        if (tabEmailHomePageVOList == null)
            tabEmailHomePageVOList = new ArrayList<>();
        this.tabEmailHomePageVOList = tabEmailHomePageVOList;
        atualizaListaEmailHomePage();
    }

    void atualizaListaEmailHomePage() {
        listHomePage.getItems().clear();
        listEmail.getItems().clear();
        if (getTabEmailHomePageVOList() != null)
            for (TabEmailHomePageVO emailHomeVO : getTabEmailHomePageVOList())
                if (emailHomeVO.isIsEmail())
                    listEmail.getItems().add(emailHomeVO);
                else
                    listHomePage.getItems().add(emailHomeVO);
    }

    public List<TabTelefoneVO> getTabTelefoneVOList() {
        return tabTelefoneVOList;
    }

    public void setTabTelefoneVOList(List tabTelefoneVOList) {
        if (tabTelefoneVOList == null)
            tabTelefoneVOList = new ArrayList<>();
        this.tabTelefoneVOList = tabTelefoneVOList;
        atualizaListaTelefone();
    }

    void atualizaListaTelefone() {
        listTelefone.getItems().clear();
        if (getTabTelefoneVOList() != null)
            listTelefone.getItems().setAll(getTabTelefoneVOList());
    }

    public List<TabContatoVO> getTabContatoVOList() {
        return tabContatoVOList;
    }

    public void setTabContatoVOList(List tabContatoVOList) {
        if (tabContatoVOList == null)
            tabContatoVOList = new ArrayList<>();
        this.tabContatoVOList = tabContatoVOList;
        atualizaListaContato();
    }

    void atualizaListaContato() {
        listContatoNome.getItems().clear();
        listContatoHomePage.getItems().clear();
        listContatoEmail.getItems().clear();
        listContatoTelefone.getItems().clear();
        if (getTabContatoVOList() != null) {
            listContatoNome.getItems().setAll(getTabContatoVOList());
            listContatoNome.getSelectionModel().select(0);
        }
    }

    public TabContatoVO getTabContatoVO() {
        return tabContatoVO;
    }

    public void setTabContatoVO(TabContatoVO tabContatoVO) {
        if (tabContatoVO == null)
            tabContatoVO = new TabContatoVO();
        this.tabContatoVO = tabContatoVO;
        setTabContatoEmailHomePageVOList(getTabContatoVO().getTabEmailHomePageVOList());
        setTabContatoTelefoneVOList(getTabContatoVO().getTabTelefoneVOList());
    }

    public List<TabEmailHomePageVO> getTabContatoEmailHomePageVOList() {
        return tabContatoEmailHomePageVOList;
    }

    public void setTabContatoEmailHomePageVOList(List<TabEmailHomePageVO> tabContatoEmailHomePageVOList) {
        if (tabContatoEmailHomePageVOList == null)
            tabContatoEmailHomePageVOList = new ArrayList<TabEmailHomePageVO>();
        this.tabContatoEmailHomePageVOList = tabContatoEmailHomePageVOList;
//        atualizaListaContatoEmailHomePage();
    }

    public List<TabTelefoneVO> getTabContatoTelefoneVOList() {
        return tabContatoTelefoneVOList;
    }

    public void setTabContatoTelefoneVOList(List<TabTelefoneVO> tabContatoTelefoneVOList) {
        if (tabContatoTelefoneVOList == null)
            tabContatoTelefoneVOList = new ArrayList<TabTelefoneVO>();
        this.tabContatoTelefoneVOList = tabContatoTelefoneVOList;
//        atualizaListaContatoTelefone();
    }

    public List<TabEmpresaReceitaFederalVO> getTabEmpresaReceitaFederalVOList() {
        return tabEmpresaReceitaFederalVOList;
    }

    public void setTabEmpresaReceitaFederalVOList(List<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOList) {
        if (tabEmpresaReceitaFederalVOList == null)
            tabEmpresaReceitaFederalVOList = new ArrayList<TabEmpresaReceitaFederalVO>();
        if (receitaFederalQsaVOList == null)
            receitaFederalQsaVOList = new ArrayList<TabEmpresaReceitaFederalVO>();

        this.tabEmpresaReceitaFederalVOList = tabEmpresaReceitaFederalVOList;
//        atualizaListaReceitaFederal();
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
        cboFiltroPesquisa.getSelectionModel().select(0);
    }

    public void preencherCboClassificacaoJuridica() {
        cboClassificacaoJuridica.getItems().clear();
        cboClassificacaoJuridica.getItems().add(0, "FÍSICA");
        cboClassificacaoJuridica.getItems().add(1, "JURÍDICA");
        cboClassificacaoJuridica.getSelectionModel().select(1);
    }

    public void preencherCboSituacaoSistema() {
        cboSituacaoSistema.getItems().clear();
        if ((sisSituacaoSistemaVOList = new ArrayList<SisSituacaoSistemaVO>(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVOList())) == null)
            return;
        cboSituacaoSistema.getItems().setAll(sisSituacaoSistemaVOList);
    }

    public void preencherCboEndUF() {
        cboEndUF.getItems().clear();
        if ((sisUFVOList = new ArrayList<SisUFVO>(new SisUFDAO().getSisUFVOList_DetMunicipios())) == null)
            return;
        cboEndUF.getItems().setAll(sisUFVOList);
    }

    void pesquisaEmpresa() {
        String busca = txtPesquisaEmpresa.getText().toLowerCase().trim();
        int filtro = cboFiltroPesquisa.getSelectionModel().getSelectedIndex();
        if (busca.length() > 0)
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
                return false;
            });
        else
            tabEmpresaVOFilteredList.setPredicate(empresa -> true);
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

    void preencherCboEndMunicipio() {
        cboEndMunicipio.getItems().clear();
        if (cboEndUF.getSelectionModel().getSelectedIndex() < 0) return;
        if ((sisMunicipioVOList = cboEndUF.getSelectionModel().getSelectedItem().getMunicipioVOList()) == null) return;
        cboEndMunicipio.getItems().setAll(sisMunicipioVOList);
    }

    public void carregarTabCargo() {
        sisCargoVOList = new ArrayList<SisCargoVO>(new SisCargoDAO().getSisCargoVOList());
    }

    public void carregarSisTipoEndereco() {
        sisTipoEnderecoVOList = new ArrayList<SisTipoEnderecoVO>(new SisTipoEnderecoDAO().getSisTipoEnderecoVOList());
    }

    public void carregarSisTelefoneOperadora() {
        sisTelefoneOperadoraVOList = new ArrayList<SisTelefoneOperadoraVO>(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVOList());
    }

//    TabEmpresaVO novaEmpresa() {
//        TabEnderecoVO enderecoVO = new TabEnderecoVO(1);
//        TabEmpresaVO empresaVO = new TabEmpresaVO();
//        empresaVO.setTabEnderecoVOList(new ArrayList<>());
//        empresaVO.getTabEnderecoVOList().add(enderecoVO);
//        return empresaVO;
//    }

    void exibirDadosEmpresa() {
        if (getTabEmpresaVO() == null) return;
        if (getTabEmpresaVO().isIsEmpresa()) {
            cboClassificacaoJuridica.getSelectionModel().select(1);
            txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getCnpj(), "cnpj"));
            txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), "ie" + getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
        } else {
            cboClassificacaoJuridica.getSelectionModel().select(0);
            txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getCnpj(), "cpf"));
            txtIE.setText(ServiceFormatarDado.getValorFormatado(getTabEmpresaVO().getIe(), "ie"));
        }
        chkIeIsento.setSelected(getTabEmpresaVO().isIeIsento());

//        for (int i = 0; i < cboSituacaoSistema.getItems().size(); i++) {
//            cboSituacaoSistema.getSelectionModel().select(i);
//            if (cboSituacaoSistema.getItems().get(i).getId() == getTabEmpresaVO().getSisSituacaoSistema_id())
//                break;
//        }

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
        if (getTabEnderecoVOList() == null) {
            limparEndereco();
            return;
        }
        idEnderecoAtual = listEndereco.getSelectionModel().getSelectedIndex();
        TabEnderecoVO enderecoVO = getTabEnderecoVOList().get(idEnderecoAtual);
        txtEndCEP.setText(ServiceFormatarDado.getValorFormatado(enderecoVO.getCep(), "cep"));
        txtEndLogradouro.setText(enderecoVO.getLogradouro());
        txtEndNumero.setText(enderecoVO.getNumero());
        txtEndComplemento.setText(enderecoVO.getComplemento());
        txtEndBairro.setText(enderecoVO.getBairro());
        txtEndPontoReferencia.setText(enderecoVO.getPontoReferencia());

//        for (int i = 0; i < cboEndUF.getItems().size(); i++) {
//            cboEndUF.getSelectionModel().select(i);
//            if (cboEndUF.getItems().get(i).getId() == enderecoVO.getSisMunicipioVO().getUfVO().getId())
//                break;
//        }

        cboEndUF.getSelectionModel().select(enderecoVO.getSisMunicipioVO().getUfVO());

//        for (int i = 0; i < cboEndMunicipio.getItems().size(); i++) {
//            cboEndMunicipio.getSelectionModel().select(i);
//            if (cboEndMunicipio.getItems().get(i).getId() == enderecoVO.getSisMunicipio_id())
//                break;
//        }

        cboEndMunicipio.getSelectionModel().select(enderecoVO.getSisMunicipioVO());
    }
//
//    void atualizaListaContatoEmailHomePage() {
//        listContatoHomePage.getItems().clear();
//        listContatoEmail.getItems().clear();
//        if (getTabContatoEmailHomePageVOList() == null) return;
//        getTabContatoVO().setTabEmailHomePageVOList(getTabContatoEmailHomePageVOList());
//        for (TabEmailHomePageVO contatoEmailHomeVO : getTabContatoEmailHomePageVOList())
//            if (contatoEmailHomeVO.isIsEmail())
//                listContatoEmail.getItems().add(contatoEmailHomeVO);
//            else
//                listContatoHomePage.getItems().add(contatoEmailHomeVO);
//    }
//
//    void atualizaListaContatoTelefone() {
//        listContatoTelefone.getItems().clear();
//        if (getTabContatoTelefoneVOList() == null)
//            return;
//        getTabContatoVO().setTabTelefoneVOList(getTabContatoTelefoneVOList());
//        listContatoTelefone.getItems().setAll(getTabContatoTelefoneVOList());
//    }
//
//    void atualizaListaReceitaFederal() {
//        listAtividadePrincipal.getItems().clear();
//        listAtividadeSecundaria.getItems().clear();
//        listInformacoesReceita.getItems().clear();
//        if (getTabEmpresaReceitaFederalVOList() == null) return;
//        for (TabEmpresaReceitaFederalVO receitaFederalVO : getTabEmpresaReceitaFederalVOList())
//            if (receitaFederalVO.getIsAtividadePrincipal() == 0) {
//                listInformacoesReceita.getItems().add(receitaFederalVO);
//            } else if (receitaFederalVO.getIsAtividadePrincipal() == 1) {
//                listAtividadePrincipal.getItems().add(receitaFederalVO);
//            } else {
//                listAtividadeSecundaria.getItems().add(receitaFederalVO);
//            }
//        fatorarComboInformacoesReceita();
//
//    }
//
//    void fatorarComboInformacoesReceita() {
//        listInformacoesReceita.setCellFactory(
//                new Callback<ListView<TabEmpresaReceitaFederalVO>, ListCell<TabEmpresaReceitaFederalVO>>() {
//                    @Override
//                    public ListCell<TabEmpresaReceitaFederalVO> call(ListView<TabEmpresaReceitaFederalVO> param) {
//                        final ListCell<TabEmpresaReceitaFederalVO> cell = new ListCell<TabEmpresaReceitaFederalVO>() {
//                            @Override
//                            public void updateItem(TabEmpresaReceitaFederalVO item, boolean empty) {
//                                super.updateItem(item, empty);
//                                if (item != null) {
//                                    if (getIndex() == -1) {
//                                        setText(item.toString());
//                                    } else {
//                                        String textoCombo = "";
//                                        for (String detalheInformacaoReceita : item.getDetalheReceitaFederal().split(";")) {
//                                            if (textoCombo != "")
//                                                textoCombo += "\r\n";
//                                            textoCombo += detalheInformacaoReceita;
//                                        }
//                                        setText(textoCombo);
//                                    }
//                                } else {
//                                    setText(null);
//                                }
//                            }
//                        };
//                        return cell;
//                    }
//                });
//
//    }
//
//    void limparCampoDadoCadastral() {
//        PersonalizarCampo.clearField((AnchorPane) tpnDadoCadastral.getContent());
//    }

    void limparEndereco() {
        txtEndCEP.setText("");
        txtEndLogradouro.setText("");
        txtEndNumero.setText("");
        txtEndComplemento.setText("");
        txtEndBairro.setText("");
        txtEndPontoReferencia.setText("");
        cboEndUF.getSelectionModel().select(0);
    }
//
//    boolean validarDadosCadastrais() {
//        if (!validarDadosEmpresa()) return false;
//
//        if (!validarDadosEndPrincipal()) return false;
//
//        return true;
//    }
//
//    boolean validarDadosEmpresa() {
//        boolean result = true;
//        String valCnpj = txtCNPJ.getText().replaceAll("[\\-/. \\[\\]]", "");
//        if ((valCnpj.length() != 11 && valCnpj.length() != 14) & (!ValidadarDado.isCnpjCpfValido(valCnpj))) {
//            txtCNPJ.requestFocus();
//            result = false;
//        }
//        if (txtRazao.getText().length() == 0 & result == true) {
//            txtRazao.requestFocus();
//            result = false;
//        }
//        if (txtFantasia.getText().length() == 0 & result == true) {
//            txtFantasia.requestFocus();
//            result = false;
//        }
//
//        int tipEmpresa = 0;
//        if (chkIsCliente.isSelected()) tipEmpresa++;
//        if (chkIsFornecedor.isSelected()) tipEmpresa++;
//        if (chkIsTransportadora.isSelected()) tipEmpresa++;
//
//        if (tipEmpresa == 0 & result == true) {
//            chkIsCliente.requestFocus();
//            result = false;
//        }
//
//        if (!result)
//            new AlertMensagem("Dados inválido!",
//                    USUARIO_LOGADO_APELIDO + ", precisa de dados válidos para empresa",
//                    "ic_dados_invalidos_white_24dp.png").getRetornoAlert_OK();
//        else result = guardarEmpresa();
//        return result;
//    }
//
//    boolean validarDadosEndPrincipal() {
//        boolean result = true;
//        guardarEndereco(idEnderecoAtual);
//
//        for (int i = 0; i < getTabEmpresaReceitaFederalVOList().size(); i++)
//            if (getTabEmpresaReceitaFederalVOList().get(i).getStr_Key().toLowerCase().equals("situacao"))
//                if (!getTabEmpresaReceitaFederalVOList().get(i).getStr_Value().equals("ativa")) {
//                    limparEndereco();
//                    return true;
//                }
//
//        if (txtEndCEP.getText().replaceAll("[\\-/. \\[\\]]", "").length() != 8 || txtEndCEP.getText().length() == 0) {
//            txtEndCEP.requestFocus();
//            result = false;
//        }
//        if (txtEndLogradouro.getText().length() == 0 & result == true) {
//            txtEndLogradouro.requestFocus();
//            result = false;
//        }
//        if (txtEndNumero.getText().length() == 0 & result == true) {
//            txtEndNumero.requestFocus();
//            result = false;
//        }
//        if (txtEndBairro.getText().length() == 0 & result == true) {
//            txtEndBairro.requestFocus();
//            result = false;
//        }
//        if (!result)
//            new AlertMensagem("Endereço inválido!",
//                    USUARIO_LOGADO_APELIDO + ", precisa endereço válido para empresa",
//                    "ic_endereco_invalido_white_24dp.png").getRetornoAlert_OK();
//        //else result = guardarEndereco(listEndereco.getSelectionModel().getSelectedIndex());
//        return result;
//    }

    boolean guardarEndereco(int index) {
        try {
            if (index < 0) return false;
            //int idEnd = getTabEnderecoVOList().indexOf(listEndereco.getItems().get(index));
            TabEnderecoVO endAntVO = getTabEnderecoVOList().get(index);
            endAntVO.setSisTipoEnderecoVO(listEndereco.getItems().get(index).getSisTipoEnderecoVO());
            endAntVO.setSisTipoEndereco_id(listEndereco.getItems().get(index).getSisTipoEndereco_id());
            endAntVO.setCep(txtEndCEP.getText().replaceAll("[\\-/. \\[\\]]", ""));
            endAntVO.setLogradouro(txtEndLogradouro.getText());
            endAntVO.setNumero(txtEndNumero.getText());
            endAntVO.setComplemento(txtEndComplemento.getText());
            endAntVO.setBairro(txtEndBairro.getText());
            endAntVO.setPontoReferencia(txtEndPontoReferencia.getText());
            endAntVO.setSisMunicipio_id(cboEndMunicipio.getSelectionModel().getSelectedItem().getId());
            endAntVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(cboEndMunicipio.getSelectionModel().getSelectedItem().getId()));
            getTabEnderecoVOList().set(index, endAntVO);
            listEndereco.getItems().set(index, endAntVO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
//
//    boolean guardarEmpresa() {
//        try {
//            getTabEmpresaVO().setIsEmpresa(cboClassificacaoJuridica.getSelectionModel().getSelectedIndex());
//            getTabEmpresaVO().setCnpj(txtCNPJ.getText().replaceAll("[\\-/. \\[\\]]", ""));
//            getTabEmpresaVO().setIe(txtIE.getText().replaceAll("[\\-/. \\[\\]]", ""));
//            getTabEmpresaVO().setRazao(txtRazao.getText());
//            getTabEmpresaVO().setFantasia(txtFantasia.getText());
//            getTabEmpresaVO().setIsCliente(chkIsCliente.isSelected());
//            getTabEmpresaVO().setIsFornecedor(chkIsFornecedor.isSelected());
//            getTabEmpresaVO().setIsTransportadora(chkIsTransportadora.isSelected());
//            getTabEmpresaVO().setSisSituacaoSistema_id(cboSituacaoSistema.getSelectionModel().getSelectedItem().getId());
//            getTabEmpresaVO().setUsuarioCadastro_id(Integer.parseInt(USUARIO_LOGADO_ID));
//            getTabEmpresaVO().setUsuarioAtualizacao_id(Integer.parseInt(USUARIO_LOGADO_ID));
//
//            LocalDate ldAbertura = LocalDate.parse(lblDataAbertura.getText().substring(15, 25), DTF_DATA);
//            getTabEmpresaVO().setDataAbertura(Date.valueOf(ldAbertura));
//
//            getTabEmpresaVO().setNaturezaJuridica(lblNaturezaJuridica.getText().substring(19));
//        } catch (Exception ex) {
//            return false;
//        }
//        return true;
//    }
//
//    void deletaEndereco(TabEnderecoVO enderecoVO) {
//        if (enderecoVO.getId() != 0) {
//            if (deletadosTabEnderecoVOList == null)
//                deletadosTabEnderecoVOList = new ArrayList<TabEnderecoVO>();
//            deletadosTabEnderecoVOList.add(enderecoVO);
//        }
//        getTabEnderecoVOList().remove(enderecoVO);
//        atualizaListaEndereco();
//    }
//
//    void deletaContato(TabContatoVO contatoVO) {
//        if (contatoVO.getId() != 0) {
//            if (deletadosTabContatoVOList == null)
//                deletadosTabContatoVOList = new ArrayList<TabContatoVO>();
//            deletadosTabContatoVOList.add(listContatoNome.getSelectionModel().getSelectedItem());
//        }
//        if (deletadosTabContatoEmailHomePageVOList != null)
//            for (int i = 0; i < contatoVO.getTabEmailHomePageVOList().size(); i++)
//                deletadosTabContatoEmailHomePageVOList.remove(contatoVO.getTabEmailHomePageVOList().get(i));
//        if (deletadosTabContatoTelefoneVOList != null)
//            for (int i = 0; i < contatoVO.getTabTelefoneVOList().size(); i++)
//                deletadosTabContatoTelefoneVOList.remove(contatoVO.getTabEmailHomePageVOList().get(i));
//        getTabContatoVOList().remove(listContatoNome.getSelectionModel().getSelectedItem());
//        atualizaListaContato();
//    }
//
//    void deletaEmailHomePage(boolean isEmpresa, TabEmailHomePageVO emailHomePageVO) {
//        if (isEmpresa) {
//            if (emailHomePageVO.getId() != 0) {
//                if (deletadosTabEmailHomePageVOList == null)
//                    deletadosTabEmailHomePageVOList = new ArrayList<TabEmailHomePageVO>();
//                deletadosTabEmailHomePageVOList.add(emailHomePageVO);
//            }
//            getTabEmailHomePageVOList().remove(emailHomePageVO);
//            atualizaListaEmailHomePage();
//        } else {
//            if (emailHomePageVO.getId() != 0) {
//                if (deletadosTabContatoEmailHomePageVOList == null)
//                    deletadosTabContatoEmailHomePageVOList = new ArrayList<TabEmailHomePageVO>();
//                deletadosTabContatoEmailHomePageVOList.add(emailHomePageVO);
//            }
//            getTabContatoVO().getTabEmailHomePageVOList().remove(emailHomePageVO);
//            atualizaListaContatoEmailHomePage();
//        }
//    }
//
//    void deletaTelefone(boolean isEmpresa, TabTelefoneVO telefoneVO) {
//        if (isEmpresa) {
//            if (telefoneVO.getId() != 0) {
//                if (deletadosTabTelefoneVOList == null)
//                    deletadosTabTelefoneVOList = new ArrayList<TabTelefoneVO>();
//                deletadosTabTelefoneVOList.add(telefoneVO);
//            }
//            getTabTelefoneVOList().remove(telefoneVO);
//            atualizaListaTelefone();
//        } else {
//            if (telefoneVO.getId() != 0) {
//                if (deletadosTabContatoTelefoneVOList == null)
//                    deletadosTabContatoTelefoneVOList = new ArrayList<TabTelefoneVO>();
//                deletadosTabContatoTelefoneVOList.add(telefoneVO);
//            }
//            getTabContatoVO().getTabTelefoneVOList().remove(telefoneVO);
//            atualizaListaContatoTelefone();
//        }
//    }
//
//    void keyDelete() {
//        boolean isEmpresa = listHomePage.isFocused() || listEmail.isFocused() || listTelefone.isFocused();
//
//        if ((listEndereco.isFocused()) && (listEndereco.getSelectionModel().getSelectedItem().getSisTipoEnderecoVO().getId() != 1))
//            deletaEndereco(listEndereco.getSelectionModel().getSelectedItem());
//
//        if (listHomePage.isFocused() && listHomePage.getSelectionModel().getSelectedIndex() >= 0)
//            deletaEmailHomePage(isEmpresa, listHomePage.getSelectionModel().getSelectedItem());
//
//        if (listEmail.isFocused() && listEmail.getSelectionModel().getSelectedIndex() >= 0)
//            deletaEmailHomePage(isEmpresa, listEmail.getSelectionModel().getSelectedItem());
//
//        if (listTelefone.isFocused() && listTelefone.getSelectionModel().getSelectedIndex() >= 0)
//            deletaTelefone(isEmpresa, listTelefone.getSelectionModel().getSelectedItem());
//
//        if (listContatoNome.isFocused() && listContatoNome.getSelectionModel().getSelectedIndex() >= 0)
//            deletaContato(listContatoNome.getSelectionModel().getSelectedItem());
//
//        if ((listContatoHomePage.isFocused()) && (listContatoHomePage.getSelectionModel().getSelectedIndex() >= 0))
//            deletaEmailHomePage(isEmpresa, listContatoHomePage.getSelectionModel().getSelectedItem());
//
//        if ((listContatoEmail.isFocused()) && (listContatoEmail.getSelectionModel().getSelectedIndex() >= 0))
//            deletaEmailHomePage(isEmpresa, listContatoEmail.getSelectionModel().getSelectedItem());
//
//        if ((listContatoTelefone.isFocused() && (listContatoTelefone.getSelectionModel().getSelectedIndex() >= 0)))
//            deletaTelefone(isEmpresa, listContatoTelefone.getSelectionModel().getSelectedItem());
//    }
//
//    void keyInsert() {
//        if (listEndereco.isFocused()) {
//            TabEnderecoVO enderecoVO = null;
//            guardarEndereco(listEndereco.getSelectionModel().getSelectedIndex());
//            if ((enderecoVO = addEndereco()) == null) return;
//            getTabEnderecoVOList().add(enderecoVO);
//            atualizaListaEndereco();
//            listEndereco.getSelectionModel().selectLast();
//            txtEndCEP.requestFocus();
//        }
//        if (listHomePage.isFocused() || listEmail.isFocused() || listContatoHomePage.isFocused() || listContatoEmail.isFocused()) {
//            boolean isEmail = (listEmail.isFocused() || listContatoEmail.isFocused());
//            boolean isEmpresa = (listHomePage.isFocused() || listEmail.isFocused());
//            TabEmailHomePageVO emailHomePageVO = null;
//            if (!isEmpresa) {
//                if (!(listContatoNome.getSelectionModel().getSelectedIndex() >= 0)) {
//                    new AlertMensagem("Contato inválido!",
//                            USUARIO_LOGADO_APELIDO + ", precisa escolher para qual contato vai ser adicionado o email/homepage",
//                            "ic_dados_invalidos_white_24dp.png").getRetornoAlert_OK();
//                    listContatoNome.requestFocus();
//                    return;
//                }
//            }
//            if ((emailHomePageVO = addEditEmailHomePage(isEmpresa, isEmail, emailHomePageVO)) == null) return;
//            if (isEmpresa) {
//                getTabEmailHomePageVOList().add(emailHomePageVO);
//                atualizaListaEmailHomePage();
//            } else {
//                getTabContatoEmailHomePageVOList().add(emailHomePageVO);
//                atualizaListaContatoEmailHomePage();
//            }
//        }
//        if (listTelefone.isFocused() || listContatoTelefone.isFocused()) {
//            boolean isEmpresa = listTelefone.isFocused();
//            TabTelefoneVO telefoneVO = null;
//            if (!isEmpresa)
//                if (!(listContatoNome.getSelectionModel().getSelectedIndex() >= 0)) {
//                    new AlertMensagem("Contato inválido!",
//                            USUARIO_LOGADO_APELIDO + ", precisa escolher para qual contato vai ser adicionado o telefone",
//                            "ic_dados_invalidos_white_24dp.png").getRetornoAlert_OK();
//                    listContatoNome.requestFocus();
//                    return;
//                }
//            if ((telefoneVO = addEditTelefone(isEmpresa, telefoneVO)) == null) return;
//            if (isEmpresa) {
//                getTabTelefoneVOList().add(telefoneVO);
//                atualizaListaTelefone();
//            } else {
//                getTabContatoVO().getTabTelefoneVOList().add(telefoneVO);
//                atualizaListaContatoTelefone();
//            }
//        }
//        if (listContatoNome.isFocused()) {
//            TabContatoVO contatoVO = null;
//            if ((contatoVO = addEditContato(contatoVO)) == null) return;
//            getTabContatoVOList().add(contatoVO);
//            atualizaListaContato();
//            listContatoNome.getSelectionModel().selectLast();
//        }
//    }
//
//    void keyShiftF6() {
//        if (listHomePage.isFocused() || listEmail.isFocused() || listContatoHomePage.isFocused() || listContatoEmail.isFocused()) {
//            TabEmailHomePageVO emailHomePageVO = new TabEmailHomePageVO();
//            TabEmailHomePageVO emailEdit = null;
//            boolean isEmail = (listEmail.isFocused() || listContatoEmail.isFocused());
//            boolean isEmpresa = (listHomePage.isFocused() || listEmail.isFocused());
//            if (isEmpresa) {
//                if (isEmail) {
//                    if (listEmail.getSelectionModel().getSelectedIndex() >= 0) {
//                        emailEdit = listEmail.getSelectionModel().getSelectedItem();
//                    }
//                } else {
//                    if (listHomePage.getSelectionModel().getSelectedIndex() >= 0) {
//                        emailEdit = listHomePage.getSelectionModel().getSelectedItem();
//                    }
//                }
//            } else {
//                if (!isEmail) {
//                    if (listContatoHomePage.getSelectionModel().getSelectedIndex() >= 0)
//                        emailEdit = listContatoHomePage.getSelectionModel().getSelectedItem();
//                } else {
//                    if (listContatoEmail.getSelectionModel().getSelectedIndex() >= 0)
//                        emailEdit = listContatoEmail.getSelectionModel().getSelectedItem();
//                }
//            }
//            if ((emailEdit == null) || (emailHomePageVO = addEditEmailHomePage(isEmpresa, isEmail, emailEdit)) == null)
//                return;
//            if (isEmpresa) {
//                getTabEmailHomePageVOList().set(getTabEmailHomePageVOList().indexOf(emailEdit), emailHomePageVO);
//                atualizaListaEmailHomePage();
//            } else {
//                getTabContatoEmailHomePageVOList().set(getTabContatoEmailHomePageVOList().indexOf(emailEdit), emailHomePageVO);
//                atualizaListaContatoEmailHomePage();
//            }
//        }
//
//        if (listTelefone.isFocused() || listContatoTelefone.isFocused()) {
//            TabTelefoneVO telefoneVO = new TabTelefoneVO();
//            TabTelefoneVO telefoneEdit = null;
//            boolean isEmpresa = (listTelefone.isFocused());
//            if (isEmpresa) {
//                if (listTelefone.getSelectionModel().getSelectedIndex() >= 0)
//                    telefoneEdit = listTelefone.getSelectionModel().getSelectedItem();
//            } else {
//                if (listContatoTelefone.getSelectionModel().getSelectedIndex() >= 0)
//                    telefoneEdit = listContatoTelefone.getSelectionModel().getSelectedItem();
//            }
//            if ((telefoneEdit == null) || (telefoneVO = addEditTelefone(isEmpresa, telefoneEdit)) == null) return;
//            if (isEmpresa) {
//                getTabTelefoneVOList().set(getTabTelefoneVOList().indexOf(telefoneEdit), telefoneVO);
//                atualizaListaTelefone();
//            } else {
//                getTabContatoTelefoneVOList().set(getTabContatoTelefoneVOList().indexOf(telefoneEdit), telefoneVO);
//                atualizaListaContatoTelefone();
//            }
//        }
//
//        if (listContatoNome.isFocused()) {
//            TabContatoVO contatoVO = new TabContatoVO();
//            TabContatoVO contatoEdit = null;
//            if (listContatoNome.getSelectionModel().getSelectedIndex() >= 0)
//                contatoEdit = listContatoNome.getSelectionModel().getSelectedItem();
//            if ((contatoEdit == null) || (contatoVO = addEditContato(contatoEdit)) == null) return;
//            getTabContatoVOList().set(getTabContatoVOList().indexOf(contatoEdit), contatoVO);
//            atualizaListaContato();
//        }
//
//    }
//
//    List<SisTipoEnderecoVO> getTipoEnderecoDisponivel() {
//        List<SisTipoEnderecoVO> endDisponivel = new ArrayList<SisTipoEnderecoVO>();
//        for (SisTipoEnderecoVO tipEnd : sisTipoEnderecoVOList) {
//            int exite = 0;
//            for (int i = 0; i < getTabEnderecoVOList().size(); i++) {
//                if (tipEnd.getDescricao().equals(getTabEnderecoVOList().get(i).getSisTipoEnderecoVO().getDescricao()))
//                    exite = 1;
//            }
//            if (exite == 0) endDisponivel.add(tipEnd);
//        }
//        return endDisponivel;
//    }
//
//    TabEnderecoVO addEndereco() {
//        TabEnderecoVO enderecoVO = new TabEnderecoVO(1);
//        if (getTabEmpresaVO().getTabEnderecoVOList().get(0).getSisTipoEndereco_id() == 1) {
//            List<SisTipoEnderecoVO> list = getTipoEnderecoDisponivel();
//            if (list.size() <= 0) {
//                new AlertMensagem("Endereço não disponivél",
//                        USUARIO_LOGADO_APELIDO + ", a empresa " + txtRazao.getText()
//                                + " não tem disponibilidade de endereço!\nAtualize algum endereço já existente!",
//                        "ic_endereco_add_white_24dp.png").getRetornoAlert_OK();
//                return null;
//            }
//            Object o = null;
//            try {
//                o = new AlertMensagem("Adicionar dados [endereço]",
//                        USUARIO_LOGADO_APELIDO + ", selecione o tipo endereço",
//                        "ic_endereco_add_white_24dp.png").getRetornoAlert_ComboBox(list).get();
//            } catch (Exception ex) {
//                if (!(ex instanceof NoSuchElementException)) ex.printStackTrace();
//            }
//            if (o == null) return null;
//            enderecoVO.setSisTipoEnderecoVO(new SisTipoEnderecoDAO().getSisTipoEnderecoVO(((SisTipoEnderecoVO) o).getId()));
//            enderecoVO.setSisTipoEndereco_id(enderecoVO.getSisTipoEnderecoVO().getId());
//            enderecoVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(listEndereco.getItems().get(0).getSisMunicipio_id()));
//            enderecoVO.setSisMunicipio_id(enderecoVO.getSisMunicipioVO().getId());
//        }
//        return enderecoVO;
//    }
//
//    TabEmailHomePageVO addEditEmailHomePage(boolean isEmpresa, boolean isEmail, TabEmailHomePageVO emailHomePageVO) {
//        String tipoInclusao, donoEmailHomePage, strIco, emailHomePageAdicao;
//        if (isEmpresa)
//            donoEmailHomePage = "a empresa: " + txtRazao.getText();
//        else
//            donoEmailHomePage = "o contato: " + listContatoNome.getSelectionModel().getSelectedItem();
//        if (isEmail) {
//            tipoInclusao = "email";
//            strIco = "ic_web_email_white_24dp.png";
//        } else {
//            tipoInclusao = "home page";
//            strIco = "ic_web_home_page_white_24dp.png";
//        }
//        try {
//            if (emailHomePageVO == null) {
//                emailHomePageVO = new TabEmailHomePageVO();
//                emailHomePageVO.setId(0);
//                emailHomePageVO.setIsEmail(isEmail);
//                emailHomePageAdicao = new AlertMensagem("Adicionar dados [" + tipoInclusao + "]",
//                        USUARIO_LOGADO_APELIDO + ", qual " + tipoInclusao + " a ser adicionada para " + donoEmailHomePage + " ?",
//                        strIco).getRetornoAlert_TextField(FormatarDado.gerarMascara("", 80, "?"), "").get();
//            } else {
//                emailHomePageAdicao = new AlertMensagem("Editar informações [" + tipoInclusao + "]",
//                        USUARIO_LOGADO_APELIDO + ", qual alteração será feita no " + tipoInclusao + " d" + donoEmailHomePage + " ?",
//                        strIco).getRetornoAlert_TextField(FormatarDado.gerarMascara("", 80, "?"),
//                        emailHomePageVO.getDescricao()).get();
//            }
//            if (emailHomePageAdicao == null || emailHomePageAdicao.equals("") || emailHomePageAdicao.equals(emailHomePageVO.getDescricao()))
//                return null;
//        } catch (Exception ex) {
//            if (!(ex instanceof NoSuchElementException))
//                ex.printStackTrace();
//            return null;
//        }
//        emailHomePageVO.setDescricao(emailHomePageAdicao);
//        if ((isEmail) && (!emailHomePageAdicao.contains("@"))) {
//            new AlertMensagem("Dados inválidos", USUARIO_LOGADO_APELIDO
//                    + ", o email informado é inválido!", "ic_msg_alerta_triangulo_white_24dp.png").getRetornoAlert_OK();
//            addEditEmailHomePage(isEmpresa, isEmail, emailHomePageVO);
//            return null;
//        }
//        if ((!isEmail) && (emailHomePageAdicao.contains("@"))) {
//            new AlertMensagem("Dados inválidos", USUARIO_LOGADO_APELIDO
//                    + ", a home page informada é inválida!", "ic_msg_alerta_triangulo_white_24dp.png").getRetornoAlert_OK();
//            addEditEmailHomePage(isEmpresa, isEmail, emailHomePageVO);
//            return null;
//        }
//
//        return emailHomePageVO;
//    }
//
//    TabTelefoneVO addEditTelefone(boolean isEmpresa, TabTelefoneVO telefoneVO) {
//        String donoTelefone, strIco;
//        if (isEmpresa)
//            donoTelefone = "a empresa: " + txtRazao.getText();
//        else
//            donoTelefone = "o contato: " + listContatoNome.getSelectionModel().getSelectedItem();
//
//        strIco = "ic_telefone_white_24dp.png";
//
//        Pair<String, Object> pairTelefone = null;
//        AlertMensagem alertMensagem = new AlertMensagem();
//        alertMensagem.setPromptTextField("telefone");
//        alertMensagem.setPromptCombo("Operadora");
//        alertMensagem.setStrIco(strIco);
//        try {
//            if (telefoneVO == null) {
//                alertMensagem.setCabecalho("Adicionar dados [telefone]");
//                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual telefone a ser adicionado para " + donoTelefone + " ?");
//                telefoneVO = new TabTelefoneVO();
//                telefoneVO.setId(0);
//                pairTelefone = alertMensagem.getRetornoAlert_TextFieldEComboBox(sisTelefoneOperadoraVOList,
//                        FormatarDado.gerarMascara("telefone", 9, "#"), "").get();
//            } else {
//                alertMensagem.setCabecalho("Editar informações [telefone]");
//                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual alteração será feita no telefone d" + donoTelefone + " ?");
//                pairTelefone = alertMensagem.getRetornoAlert_TextFieldEComboBox(sisTelefoneOperadoraVOList,
//                        FormatarDado.gerarMascara("telefone", 9, "#"), telefoneVO.getDescricao()).get();
//            }
//            if (pairTelefone == null || pairTelefone.getKey().equals("") ||
//                    ((pairTelefone.getKey().equals(telefoneVO.getDescricao())) && (((SisTelefoneOperadoraVO) pairTelefone.getValue()).getId() == telefoneVO.getSisTelefoneOperadora_id())))
//                return null;
//        } catch (Exception ex) {
//            if (!(ex instanceof NoSuchElementException))
//                ex.printStackTrace();
//            return null;
//        }
//        telefoneVO.setDescricao(pairTelefone.getKey().toString().replaceAll("[\\-/.() \\[\\]]", ""));
//        if (telefoneVO.getDescricao().length() < 8 || telefoneVO.getDescricao().length() > 9) {
//            new AlertMensagem("Dados inválidos", USUARIO_LOGADO_APELIDO
//                    + ", o número de telefone informado é inválido!", "ic_msg_alerta_triangulo_white_24dp.png").getRetornoAlert_OK();
//            addEditTelefone(isEmpresa, telefoneVO);
//            return null;
//        }
//
//        telefoneVO.setSisTelefoneOperadoraVO((SisTelefoneOperadoraVO) pairTelefone.getValue());
//        telefoneVO.setSisTelefoneOperadora_id(telefoneVO.getSisTelefoneOperadoraVO().getId());
//
//        return telefoneVO;
//    }
//
//    TabContatoVO addEditContato(TabContatoVO contatoVO) {
//        String strIco;
//        strIco = "ic_contato_add_white_24dp.png";
//
//        Pair<String, Object> pairContato = null;
//        AlertMensagem alertMensagem = new AlertMensagem();
//        alertMensagem.setPromptTextField("Contato");
//        alertMensagem.setPromptCombo("Cargo");
//        alertMensagem.setStrIco(strIco);
//        try {
//            if (contatoVO == null) {
//                alertMensagem.setCabecalho("Adicionar dados [contato]");
//                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual contato a ser adicionado para empresa: " + txtRazao.getText() + " ?");
//                contatoVO = new TabContatoVO();
//                contatoVO.setId(0);
//                pairContato = alertMensagem.getRetornoAlert_TextFieldEComboBox(sisCargoVOList,
//                        FormatarDado.gerarMascara("", 40, "@"), "").get();
//            } else {
//                alertMensagem.setCabecalho("Editar informações [contato]");
//                alertMensagem.setPromptText(USUARIO_LOGADO_APELIDO + ", qual alteração será feita no contato da empresa: " + txtRazao.getText() + " ?");
//                pairContato = alertMensagem.getRetornoAlert_TextFieldEComboBox(sisCargoVOList,
//                        FormatarDado.gerarMascara("", 40, "@"), contatoVO.getDescricao()).get();
//            }
//            if (pairContato == null || pairContato.getKey().equals("") ||
//                    ((pairContato.getKey().equals(contatoVO.getDescricao())) && (((SisCargoVO) pairContato.getValue()).getId() == contatoVO.getSisCargo_id())))
//                return null;
//        } catch (Exception ex) {
//            if (!(ex instanceof NoSuchElementException))
//                ex.printStackTrace();
//            return null;
//        }
//        contatoVO.setDescricao(pairContato.getKey().toString());
//
//        contatoVO.setSisCargoVO((SisCargoVO) pairContato.getValue());
//        contatoVO.setSisCargo_id(contatoVO.getSisCargoVO().getId());
//
//        return contatoVO;
//    }
//
//    void salvarEmpresa() {
//        Connection conn = ConnectionFactory.getConnection(); // cria conexao com banco de dados
//        try {
//            conn.setAutoCommit(false);
//
//            if (getTabEmpresaVO().getId() == 0)  // se id empresa==0 (empresa nova) ele inclui no banco
//                getTabEmpresaVO().setId(new TabEmpresaDAO().insertTabEmpresaVO(conn, getTabEmpresaVO()));
//            else  // se id empresa != 0 (empresa no cadastro) ele atualiza os dados dela no banco
//                new TabEmpresaDAO().updateTabEmpresaVO(conn, getTabEmpresaVO());
//
//            new RelEmpresaEnderecoDAO().dedeteRelEmpresaEnderecoVO(conn, getTabEmpresaVO().getId()); // apaga todos relacionamentos da empresa com enderecos
//            if (deletadosTabEnderecoVOList != null) // se tiver algum endereco deletado abre para percorrer lista de enderecos deletados
//                for (int i = 0; i < deletadosTabEnderecoVOList.size(); i++)  // percorre lista de enderecos deletados
//                    new TabEnderecoDAO().deleteTabEnderecoVO(conn, deletadosTabEnderecoVOList.get(i)); // deleta endereco no banco de dados
//            for (int i = 0; i < getTabEnderecoVOList().size(); i++) { // percorre lista de enderecos da empresa
//                if (getTabEnderecoVOList().get(i).getId() == 0)  // se id endereco==0 (endereco novo) ele inclui novo endereco no banco
//                    getTabEnderecoVOList().get(i).setId(new TabEnderecoDAO().insertTabEnderecoVO(conn, getTabEnderecoVOList().get(i)));
//                else  // se id endereco!=0 (endereco já no cadastro) ele atualiza dados do endereco
//                    new TabEnderecoDAO().updateTabEnderecoVO(conn, getTabEnderecoVOList().get(i));
//                // gera novo relacionamento entre empresa e endereco
//                new RelEmpresaEnderecoDAO().insertrelEmpresaEnderecoVO(conn, getTabEmpresaVO().getId(), getTabEnderecoVOList().get(i).getId());
//            }
//
//            new RelEmpresaEmailHomePageDAO().dedeteRelEmpresaEmailHomePageVO(conn, getTabEmpresaVO().getId()); // apaga todos relacionamentos da empresa com email e homePage
//            if (deletadosTabEmailHomePageVOList != null) // se tiver algum email ou home page deletado abre para percorrer lista de emails e homePage deletados
//                for (int i = 0; i < deletadosTabEmailHomePageVOList.size(); i++)  // percorre lista de emails e homePage deletados
//                    new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, deletadosTabEmailHomePageVOList.get(i)); // deleta email e homePage no banco de dados
//            for (int i = 0; i < getTabEmailHomePageVOList().size(); i++) { // percorre lista de email e homePage da empresa
//                if (getTabEmailHomePageVOList().get(i).getId() == 0)  // se id emailHomePage==0 (emailHomePage novo) ele inclui novo emailHomePage no banco
//                    getTabEmailHomePageVOList().get(i).setId(new TabEmailHomePageDAO().insertTabEmailHomePageVO(conn, getTabEmailHomePageVOList().get(i)));
//                else  // se id emailHomePage!=0 (emailHomePage já no cadastro) ele atualiza dados do emailHomePage
//                    new TabEmailHomePageDAO().updateTabEmailHomePageVO(conn, getTabEmailHomePageVOList().get(i));
//                // gera novo relacionamento entre empresa e emailHomePage
//                new RelEmpresaEmailHomePageDAO().insertRelEmpresaEmailHomePageVO(conn, getTabEmpresaVO().getId(), getTabEmailHomePageVOList().get(i).getId());
//            }
//
//            new RelEmpresaTelefoneDAO().deleteRelEmpresaTelefoneVO(conn, getTabEmpresaVO().getId());
//            if (deletadosTabTelefoneVOList != null)
//                for (int i = 0; i < deletadosTabTelefoneVOList.size(); i++)
//                    new TabTelefoneDAO().deleteTabTelefoneVO(conn, deletadosTabTelefoneVOList.get(i));
//            for (int i = 0; i < getTabTelefoneVOList().size(); i++) {
//                if (getTabTelefoneVOList().get(i).getId() == 0)
//                    getTabTelefoneVOList().get(i).setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, getTabTelefoneVOList().get(i)));
//                else
//                    new TabTelefoneDAO().updateTabTelefoneVO(conn, getTabTelefoneVOList().get(i));
//                new RelEmpresaTelefoneDAO().insertRelEmpresaTelefoneVO(conn, getTabEmpresaVO().getId(), getTabTelefoneVOList().get(i).getId());
//            }
//
//            new RelEmpresaContatoDAO().deleteRelEmpresaContatoVO(conn, getTabEmpresaVO().getId());
//            if (deletadosTabContatoVOList != null)
//                for (int i = 0; i < deletadosTabContatoVOList.size(); i++) {
//                    setTabContatoVO(deletadosTabContatoVOList.get(i));
//                    new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn, getTabContatoVO().getId());
//                    new RelContatoTelefoneDAO().deleteRelContatoTelefoneVO(conn, getTabContatoVO().getId());
//                    new TabContatoDAO().deleteTabContatoVO(conn, getTabContatoVO());
//                    if (getTabContatoEmailHomePageVOList() != null)
//                        for (int j = 0; j < getTabContatoEmailHomePageVOList().size(); j++)
//                            if (getTabContatoEmailHomePageVOList().get(j).getId() > 0)
//                                new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, getTabContatoEmailHomePageVOList().get(j));
//                    if (getTabContatoTelefoneVOList() != null)
//                        for (int j = 0; j < getTabContatoTelefoneVOList().size(); j++)
//                            if (getTabContatoTelefoneVOList().get(j).getId() > 0)
//                                new TabTelefoneDAO().deleteTabTelefoneVO(conn, getTabContatoTelefoneVOList().get(j));
//                }
//
//            for (int i = 0; i < getTabContatoVOList().size(); i++) {
//                setTabContatoVO(getTabContatoVOList().get(i));
//                if (getTabContatoVO().getId() == 0)
//                    getTabContatoVO().setId(new TabContatoDAO().insertTabContatoVO(conn, getTabContatoVO()));
//                else
//                    new TabContatoDAO().updateTabContatoVO(conn, getTabContatoVO());
//                new RelEmpresaContatoDAO().insertRelEmpresaContatoVO(conn, getTabEmpresaVO().getId(), getTabContatoVO().getId());
//
//                new RelContatoEmailHomePageDAO().deleteRelContatoEmailHomePageVO(conn, getTabContatoVO().getId());
//                if (deletadosTabContatoEmailHomePageVOList != null)
//                    for (int j = 0; j < deletadosTabContatoEmailHomePageVOList.size(); j++)
//                        new TabEmailHomePageDAO().deleteTabEmailHomePageVO(conn, deletadosTabContatoEmailHomePageVOList.get(j));
//                for (int j = 0; j < getTabContatoEmailHomePageVOList().size(); j++) {
//                    if (getTabContatoEmailHomePageVOList().get(j).getId() == 0)
//                        getTabContatoEmailHomePageVOList().get(j).setId(new TabEmailHomePageDAO().insertTabEmailHomePageVO(conn, getTabContatoEmailHomePageVOList().get(j)));
//                    else
//                        new TabEmailHomePageDAO().updateTabEmailHomePageVO(conn, getTabContatoEmailHomePageVOList().get(j));
//                    new RelContatoEmailHomePageDAO().insertRelContatoEmailHomePageVO(conn, getTabContatoVO().getId(), getTabContatoEmailHomePageVOList().get(j).getId());
//                }
//
//                new RelContatoTelefoneDAO().deleteRelContatoTelefoneVO(conn, getTabContatoVO().getId());
//                if (deletadosTabContatoTelefoneVOList != null)
//                    for (int j = 0; j < deletadosTabContatoTelefoneVOList.size(); j++)
//                        new TabTelefoneDAO().deleteTabTelefoneVO(conn, deletadosTabContatoTelefoneVOList.get(j));
//                for (int j = 0; j < getTabContatoTelefoneVOList().size(); j++) {
//                    if (getTabContatoTelefoneVOList().get(j).getId() == 0)
//                        getTabContatoTelefoneVOList().get(j).setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, getTabContatoTelefoneVOList().get(j)));
//                    else
//                        new TabTelefoneDAO().updateTabTelefoneVO(conn, getTabContatoTelefoneVOList().get(j));
//                    new RelContatoTelefoneDAO().insertRelContatoTelefoneVO(conn, getTabContatoVO().getId(), getTabContatoTelefoneVOList().get(j).getId());
//                }
//            }
//
//            if (deletadosTabEmpresaReceitaFederalVOList != null)
//                for (int i = 0; i < deletadosTabEmpresaReceitaFederalVOList.size(); i++)
//                    new TabEmpresaReceitaFederalDAO().deleteTabEmpresaReceitaFederalVO(conn, deletadosTabEmpresaReceitaFederalVOList.get(i));
//            for (int i = 0; i < getTabEmpresaReceitaFederalVOList().size(); i++) {
//                getTabEmpresaReceitaFederalVOList().get(i).setTabEmpresa_id(getTabEmpresaVO().getId());
//                if (getTabEmpresaReceitaFederalVOList().get(i).getId() == 0)
//                    getTabEmpresaReceitaFederalVOList().get(i).setId(new TabEmpresaReceitaFederalDAO().insertTabEmpresaReceitaFederalVO(conn, getTabEmpresaReceitaFederalVOList().get(i)));
//                else
//                    new TabEmpresaReceitaFederalDAO().updateTabEmpresaReceitaFederalVO(conn, getTabEmpresaReceitaFederalVOList().get(i));
//            }
//
//            conn.commit();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            try {
//                conn.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
