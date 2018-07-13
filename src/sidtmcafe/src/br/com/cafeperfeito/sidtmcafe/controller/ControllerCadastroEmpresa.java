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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Pair;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    int contador = 1;
    ObservableList<TabEnderecoVO> listEnderecoVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> listEmailHomePageVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabTelefoneVO> listTelefoneVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabContatoVO> listContatoVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> listContatoEmailHomePageVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabTelefoneVO> listContatoTelefoneVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmpresaReceitaFederalVO> listReceitaFederalVOObservableList = FXCollections.observableArrayList();
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
    public TitledPane tpnEndereco;
    public JFXListView<TabEnderecoVO> listEndereco;
    public JFXTextField txtEndCEP;
    public JFXTextField txtEndLogradouro;
    public JFXTextField txtEndNumero;
    public JFXTextField txtEndComplemento;
    public JFXTextField txtEndBairro;
    public JFXComboBox<SisUfVO> cboEndUF;
    public JFXComboBox<SisMunicipioVO> cboEndMunicipio;
    public JFXTextField txtEndPontoReferencia;
    public TitledPane tpnPessoaContato;
    public JFXListView<TabEmailHomePageVO> listHomePage;
    public JFXListView<TabEmailHomePageVO> listEmail;
    public JFXListView<TabTelefoneVO> listTelefone;
    public JFXListView<TabContatoVO> listContatoNome;
    public JFXListView<TabEmailHomePageVO> listContatoHomePage;
    public JFXListView<TabEmailHomePageVO> listContatoEmail;
    public JFXListView<TabTelefoneVO> listContatoTelefone;
    public Label lblNaturezaJuridica;
    public Label lblDataAbertura;
    public Label lblDataAberturaDiff;
    public Label lblDataCadastro;
    public Label lblDataCadastroDiff;
    public Label lblDataAtualizacao;
    public Label lblDataAtualizacaoDiff;
    public JFXListView<TabEmpresaReceitaFederalVO> listAtividadePrincipal;
    public JFXListView<TabEmpresaReceitaFederalVO> listAtividadeSecundaria;
    public JFXListView<TabEmpresaReceitaFederalVO> listInformacoesReceita;
    public TitledPane tpnReceitaAtividadePrincipal;
    public TitledPane tpnReceitaAtividadeSecundaria;
    public TitledPane tpnReceitaInformacao;

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
        formatCnpj.maskField(txtCNPJ, ServiceFormatarDado.gerarMascara("cnpj", 0, "#"));
        formatIe = new ServiceFormatarDado();
        formatIe.maskField(txtIE, ServiceFormatarDado.gerarMascara("ie", 0, "#"));
    }

    @Override
    public void fatorarObjetos() {
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsCliente());
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsFornecedor());
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsTransportadora());
//        listInformacoesReceita.setCellFactory(new Callback<JFXListView<TabEmpresaReceitaFederalVO>, ListCell<TabEmpresaReceitaFederalVO>>() {
//            @Override
//            public ListCell<TabEmpresaReceitaFederalVO> call(JFXListView<TabEmpresaReceitaFederalVO> param) {
//                final ListCell<TabEmpresaReceitaFederalVO> cell = new ListCell<TabEmpresaReceitaFederalVO>() {
//                    @Override
//                    public void updateItem(TabEmpresaReceitaFederalVO item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item == null) setText(null);
//                        else {
//                            String novoTexto = "";
//                            for (String det : item.getDetalheReceitaFederal().split(";"))
//                                if (novoTexto == "") novoTexto += det;
//                                else novoTexto += "\r\n" + det;
//                            setText(novoTexto);
//                        }
//                    }
//                };
//                return cell;
//            }
//        });
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
        ttvEmpresa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setEmpresaVO(newValue.getValue());
            }
        });

        ttvEmpresa.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (oldValue && !newValue && statusFormulario.toLowerCase().equals("pesquisa"))
//                setEmpresaVO(null);
            if (newValue && statusFormulario.toLowerCase().equals("pesquisa") && ttvEmpresa.getSelectionModel().getSelectedItem() != null)
                setEmpresaVO(ttvEmpresa.getSelectionModel().getSelectedItem().getValue());

        });

        //noinspection Duplicates
        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 0 || newValue.intValue() == oldValue.intValue()) return;
            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(tituloTab))
                ControllerPrincipal.ctrlPrincipal.atualizarStatusBarTeclas(getStatusBarTecla());
        });

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
                        setEmpresaVO(new TabEmpresaVO(1));
                        break;
                    case F2:
                    case F5:
                        if (!getStatusBarTecla().contains(event.getCode().toString())) break;
                        listEndereco.getSelectionModel().selectFirst();
                        if (!validarDados()) break;
                        if (buscaDuplicidade()) break;
                        salvarEmpresa();
                        if (getStatusFormulario().toLowerCase().equals("editar"))
                            empresaVOObservableList.set(empresaVOObservableList.indexOf(ttvEmpresa.getSelectionModel().getSelectedItem().getValue()),
                                    new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
                        else if (getStatusFormulario().toLowerCase().equals("incluir"))
                            empresaVOObservableList.add(new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getId()));
                        setStatusFormulario("pesquisa");
                        break;
                    case F3:
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
                        break;
                    case F6:
                        verificaFocus();
//                        if (getStatusFormulario().toLowerCase().equals("pesquisa") || !(event.isShiftDown())) break;
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
                        keyInsert();
                        break;
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        keyDelete();
                        break;
//                        if (listEndereco.isFocused())
//                            if (event.getCode() == KeyCode.HELP)
//                                addEndereco();
//                            else
//                                delEndereco();
//                        if (listHomePage.isFocused() || listEmail.isFocused())
//                            if (event.getCode() == KeyCode.HELP)
//                                addEmailHomePage();
//                            else
//                                delEmailHomePage();
//                        if (listTelefone.isFocused())
//                            if (event.getCode() == KeyCode.HELP)
//                                addTelefone();
//                            else
//                                delTelefone();
//                        if (listContatoNome.isFocused())
//                            if (event.getCode() == KeyCode.HELP)
//                                addContato();
//                            else
//                                delContato();
//                        if (listContatoHomePage.isFocused() || listContatoEmail.isFocused())
//                            if (event.getCode() == KeyCode.HELP)
//                                addContatoEmailHomePage();
//                            else
//                                delContatoEmailHomePage();
//                        if (listContatoTelefone.isFocused())
//                            if (event.getCode() == KeyCode.HELP)
//                                addContatoTelefone();
//                            else
//                                delContatoTelefone();
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

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroEmpresa);

        txtPesquisaEmpresa.textProperty().addListener((observable, oldValue, newValue) -> {
            pesquisaEmpresa();
        });

        cboFiltroPesquisa.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            pesquisaEmpresa();
        });

        txtPesquisaEmpresa.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvEmpresa.requestFocus();
            ttvEmpresa.getSelectionModel().selectFirst();
        });

        cboClassificacaoJuridica.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            txtCNPJ.setPromptText(newValue.intValue() == 0 ? "C.P.F." : "C.N.P.J.");
            txtIE.setPromptText(newValue.intValue() == 0 ? "RG" : "IE");
            txtRazao.setPromptText(newValue.intValue() == 0 ? "Nome" : "Razão");
            txtFantasia.setPromptText(newValue.intValue() == 0 ? "Apelido" : "Fantasia");
            formatCnpj.setMascara(ServiceFormatarDado.gerarMascara(newValue.intValue() == 0 ? "cpf" : "cnpj", 0, "#"));
            if (txtCNPJ.getLength() > 0)
                txtCNPJ.setText(ServiceFormatarDado.getValorFormatado(txtCNPJ.getText().replaceAll("\\D", ""), txtCNPJ.getPromptText().toLowerCase().replace(".", "")));
        });

        txtCNPJ.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String valueCnpj;
                if ((valueCnpj = txtCNPJ.getText().replaceAll("\\D", "")).length() == 0) return;
                if (!ServiceValidarDado.isCnpjCpfValido(valueCnpj)) {
                    alertMensagem = new ServiceAlertMensagem();
                    alertMensagem.setCabecalho("Dado inválido!");
                    alertMensagem.setPromptText(String.format("%s, o %s: [%s] informado é inválido!",
                            USUARIO_LOGADO_APELIDO, txtCNPJ.getPromptText(), txtCNPJ.getText()));
                    alertMensagem.setStrIco("ic_web_service_err_white_24dp.png");
                    alertMensagem.getRetornoAlert_OK();
                    txtCNPJ.requestFocus();
                    return;
                } else if (buscaDuplicidade()) {
                    txtCNPJ.requestFocus();
                    return;
                } else {
                    TabEmpresaVO buscaEmpresaCNPJ;
                    if ((buscaEmpresaCNPJ = new ServiceConsultaWebServices().getSistuacaoCNPJ_receitaWs(getEmpresaVO(), valueCnpj)) == null) {
                        alertMensagem = new ServiceAlertMensagem();
                        alertMensagem.setCabecalho("Retorno inválido!");
                        alertMensagem.setPromptText(String.format("%s, o %s: [%s] informado, não foi localizado na base de dados!",
                                USUARIO_LOGADO_APELIDO, txtCNPJ.getPromptText(), txtCNPJ.getText()));
                        alertMensagem.setStrIco("ic_web_service_err_white_24dp.png");
                        alertMensagem.getRetornoAlert_OK();
                        txtCNPJ.requestFocus();
                        return;
                    } else {
                        setEmpresaVO(buscaEmpresaCNPJ);
                        txtRazao.requestFocus();
                    }
                }
            }
        });

        listEndereco.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!getStatusFormulario().toLowerCase().equals("pesquisa"))
                if (oldValue != null && newValue != oldValue)
                    guardarEndereco(oldValue);
            if (newValue != null && newValue != oldValue)
                setEnderecoVO(newValue);
        });

        txtEndCEP.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            String valueCep;
            if (event.getCode() == KeyCode.ENTER && (valueCep = txtEndCEP.getText().replaceAll("\\D", "")).length() == 8) {
                TabEnderecoVO enderecoBuscaCEP;
                if ((enderecoBuscaCEP = new ServiceConsultaWebServices()
                        .getEnderecoCep_postmon(getEnderecoVO().getId(), getEnderecoVO().getSisTipoEndereco_id(), valueCep)) == null) {
                    alertMensagem = new ServiceAlertMensagem();
                    alertMensagem.setCabecalho("Dado inválido!");
                    alertMensagem.setPromptText(String.format("%s, o cep: [%s] não foi localizado na base de dados!",
                            USUARIO_LOGADO_APELIDO, txtEndCEP.getText()));
                    alertMensagem.setStrIco("ic_web_service_err_white_24dp.png");
                    alertMensagem.getRetornoAlert_OK();
                    txtEndCEP.requestFocus();
                } else {
                    setEnderecoVO(enderecoBuscaCEP);
                    txtEndNumero.requestFocus();
                }
            }
        });

        cboEndUF.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SisUfVO> observable, SisUfVO oldValue, SisUfVO newValue) -> {
            if (newValue == null) return;
            cboEndMunicipio.getItems().setAll(newValue.getMunicipioVOList());
            cboEndMunicipio.getSelectionModel().selectFirst();
            formatIe.setMascara(ServiceFormatarDado.gerarMascara("ie" + newValue.getSigla(), 0, "#"));
            if (getEmpresaVO() != null && getEmpresaVO().getIe().length() > 0)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + newValue.getSigla()));
        });

        listContatoNome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setContatoVO(newValue);
        });

        empresaVOFilteredList.addListener((InvalidationListener) c -> {
            atualizaQtdRegistroLocalizado();
        });

        chkIeIsento.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (getStatusFormulario().toLowerCase().equals("pesquisa")) return;
            txtIE.setDisable(newValue);
            if (newValue)
                txtIE.setText("");
            else if (getEmpresaVO() != null)
                txtIE.setText(ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + getEmpresaVO().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
        });

        listEnderecoVOObservableList.addListener((ListChangeListener) c -> {
            listEndereco.setItems(listEnderecoVOObservableList.stream()
                    .filter(endereco -> endereco.getId() >= 0)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        listEmailHomePageVOObservableList.addListener((ListChangeListener) c -> {
            listHomePage.setItems(listEmailHomePageVOObservableList.stream()
                    .filter(homePage -> homePage.getId() >= 0 && !homePage.isIsEmail())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            listEmail.setItems(listEmailHomePageVOObservableList.stream()
                    .filter(email -> email.getId() >= 0 && email.isIsEmail())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        listTelefoneVOObservableList.addListener((ListChangeListener) c -> {
            listTelefone.setItems(listTelefoneVOObservableList.stream()
                    .filter(telefone -> telefone.getId() >= 0)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        listContatoVOObservableList.addListener((ListChangeListener) c -> {
            listContatoNome.setItems(listContatoVOObservableList.stream()
                    .filter(contato -> contato.getId() >= 0)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        listContatoEmailHomePageVOObservableList.addListener((ListChangeListener) c -> {
            listContatoHomePage.setItems(listContatoEmailHomePageVOObservableList.stream()
                    .filter(contatoHomePage -> contatoHomePage.getId() >= 0 && !contatoHomePage.isIsEmail())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            listContatoEmail.setItems(listContatoEmailHomePageVOObservableList.stream()
                    .filter(contatoEmail -> contatoEmail.getId() >= 0 && contatoEmail.isIsEmail())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        listContatoTelefoneVOObservableList.addListener((ListChangeListener) c -> {
            listContatoTelefone.setItems(listContatoTelefoneVOObservableList.stream()
                    .filter(contatoTelefone -> contatoTelefone.getId() >= 0)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        listReceitaFederalVOObservableList.addListener((ListChangeListener) c -> {
            listAtividadePrincipal.setItems(listReceitaFederalVOObservableList.stream()
                    .filter(principal -> principal.getIsAtividadePrincipal() == 1)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            tpnReceitaAtividadePrincipal.setText(String.format("%d %s",
                    listAtividadePrincipal.getItems().size(),
                    listAtividadePrincipal.getItems().size() > 1 ? "atividades principais" : "atividade principal"));
            listAtividadeSecundaria.setItems(listReceitaFederalVOObservableList.stream()
                    .filter(secundaria -> secundaria.getIsAtividadePrincipal() == 0)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            tpnReceitaAtividadeSecundaria.setText(String.format("%d %s",
                    listAtividadeSecundaria.getItems().size(),
                    listAtividadeSecundaria.getItems().size() > 1 ? "atividades secundarias" : "atividade secundaria"));
            listInformacoesReceita.setItems(listReceitaFederalVOObservableList.stream()
                    .filter(informacao -> informacao.getIsAtividadePrincipal() == 2)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            tpnReceitaInformacao.setText(String.format("%d %s",
                    listInformacoesReceita.getItems().size(),
                    listInformacoesReceita.getItems().size() > 1 ? "Informações complementares" : "Informação complementar"));
        });


    }


    void verificaFocus() {
        KeyboardFocusManager focusManager =
                KeyboardFocusManager.getCurrentKeyboardFocusManager();
        focusManager.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String prop = evt.getPropertyName();
                if (!prop.equals("focusOwner")) return;
                Component component = (Component) evt.getNewValue();
                System.out.printf("Focus no componente [%s]", component.getName());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTituloTab(ViewCadastroEmpresa.getTituloJanela());
        criarObjetos();
        preencherObjetos();
        escutarTecla();
        fatorarObjetos();
        setStatusFormulario("Pesquisa");
        ServiceCampoPersonalizado.fieldMask(painelViewCadastroEmpresa);
        Platform.runLater(() -> ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7)));
    }

    ObservableList<TabEmpresaVO> empresaVOObservableList;
    FilteredList<TabEmpresaVO> empresaVOFilteredList;
    TabEmpresaVO empresaVO = new TabEmpresaVO();
    TabEnderecoVO enderecoVO = new TabEnderecoVO();
    TabContatoVO contatoVO = new TabContatoVO();

    static String STATUS_BAR_TECLA_PESQUISA = "[F1-Novo]  [F4-Editar]  [F7-Pesquisar]  [F12-Sair]  ";
    static String STATUS_BAR_TECLA_EDITAR = "[F3-Cancelar edição]  [F5-Atualizar]  ";
    static String STATUS_BAR_TECLA_INCLUIR = "[F2-Incluir]  [F3-Cancelar inclusão]  ";

    EventHandler<KeyEvent> eventHandlerCadastroEmpresa;
    List<Pair> listaTarefa = new ArrayList<>();
    ServiceFormatarDado formatCnpj, formatIe;
    ServiceAlertMensagem alertMensagem;
    String statusFormulario, statusBarTecla, tituloTab = ViewCadastroEmpresa.getTituloJanela();
    Boolean isEndereco, isEmail, isHomePage, isTelefone, isEmpresa, isContato;

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
        int qtd = empresaVOFilteredList.size();
        lblRegistrosLocalizados.setText(String.format("[%s] %d registro%s localizado%s.", getStatusFormulario(), qtd,
                qtd > 1 ? "s" : "", qtd > 1 ? "s" : ""));
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
        cboEndUF.getItems().setAll(new ArrayList<>(new SisUfDAO().getSisUfVOList()));
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
        cboClassificacaoJuridica.getSelectionModel().select(getEmpresaVO().isIsEmpresa() ? 1 : 0);
        txtCNPJ.setText(getEmpresaVO().isIsEmpresa() ? ServiceFormatarDado.getValorFormatado(getEmpresaVO().getCnpj(), "cnpj") : ServiceFormatarDado.getValorFormatado(getEmpresaVO().getCnpj(), "cpf"));
        txtIE.setText(getEmpresaVO().isIsEmpresa() ? ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie" + getEmpresaVO().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()) : ServiceFormatarDado.getValorFormatado(getEmpresaVO().getIe(), "ie"));
        chkIeIsento.setSelected(getEmpresaVO().isIeIsento());
        cboSituacaoSistema.getSelectionModel().select(getEmpresaVO().getSisSituacaoSistemaVO());
        txtRazao.setText(getEmpresaVO().getRazao());
        txtFantasia.setText(getEmpresaVO().getFantasia());
        chkIsCliente.setSelected(getEmpresaVO().isIsCliente());
        chkIsFornecedor.setSelected(getEmpresaVO().isIsFornecedor());
        chkIsTransportadora.setSelected(getEmpresaVO().isIsTransportadora());

        listEnderecoVOObservableList.setAll(getEmpresaVO().getTabEnderecoVOList());
        listEndereco.getSelectionModel().selectFirst();

        listEmailHomePageVOObservableList.setAll(getEmpresaVO().getTabEmailHomePageVOList());
        listTelefoneVOObservableList.setAll(getEmpresaVO().getTabTelefoneVOList());

        listContatoVOObservableList.setAll(getEmpresaVO().getTabContatoVOList());
        listContatoNome.getSelectionModel().selectFirst();

        lblNaturezaJuridica.setText(String.format("natureza júridica%s",
                getEmpresaVO().getNaturezaJuridica() == null ? "" : String.format(": %s", getEmpresaVO().getNaturezaJuridica())));
        lblDataAbertura.setText(String.format("data de abertura%s",
                getEmpresaVO().getDataAbertura() == null ? "" : String.format(": %s", DTF_DATA.format(getEmpresaVO().getDataAbertura().toLocalDate()))));
        lblDataAberturaDiff.setText(String.format("tempo de abertura%s",
                getEmpresaVO().getDataAbertura() == null ? "" : String.format(": %s", ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAbertura().toLocalDate(), null))));
        lblDataCadastro.setText(String.format("data de cadastro%s",
                getEmpresaVO().getDataCadastro() == null ? "" : String.format(": %s [%s]", DTF_DATAHORA.format(getEmpresaVO().getDataCadastro().toLocalDateTime()),
                        getEmpresaVO().getUsuarioCadastroVO())));
        lblDataCadastroDiff.setText(String.format("tempo de cadastro%s",
                getEmpresaVO().getDataCadastro() == null ? "" : String.format(": %s", ServiceDataHora.getIntervaloData(getEmpresaVO().getDataCadastro().toLocalDateTime().toLocalDate(), null))));
        lblDataAtualizacao.setText(String.format("data de atualização%s",
                getEmpresaVO().getDataAtualizacao() == null ? "" : String.format(": %s [%s]", DTF_DATAHORA.format(getEmpresaVO().getDataAtualizacao().toLocalDateTime()),
                        getEmpresaVO().getUsuarioAtualizacaoVO())));
        lblDataAtualizacaoDiff.setText(String.format("tempo de atualização%s",
                getEmpresaVO().getDataAtualizacao() == null ? "" : String.format(": %s", ServiceDataHora.getIntervaloData(getEmpresaVO().getDataAtualizacao().toLocalDateTime().toLocalDate(), null))));

        listReceitaFederalVOObservableList.setAll(getEmpresaVO().getTabEmpresaReceitaFederalVOList());
    }

    void exibirDadosEndereco() {
        int qtd = listEndereco.getItems().size();
        tpnEndereco.setText(String.format("%d %s%s",
                listEnderecoVOObservableList.size(),
                listEnderecoVOObservableList.size() > 1 ? "endereços cadastrados" : "endereço cadastrado",
                getEnderecoVO() == null ? "" : String.format(" [%s]", getEnderecoVO())));
        if (getEnderecoVO() == null) return;
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
        listContatoEmailHomePageVOObservableList.setAll(getContatoVO().getTabEmailHomePageVOList());
        listContatoTelefoneVOObservableList.setAll(getContatoVO().getTabTelefoneVOList());
        tpnPessoaContato.setText(String.format("pessoa de contato:%s",
                getContatoVO().getDescricao().equals("") ? "" : String.format(" [%s]", getContatoVO().getDescricao())));
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
            endAntigo.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(cboEndMunicipio.getSelectionModel().getSelectedItem().getId(), true));
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

    void tipoAddDelete() {
        isEndereco = listEndereco.isFocused();
        isEmail = (listEmail.isFocused() || listContatoEmail.isFocused());
        isHomePage = (listHomePage.isFocused() || listContatoHomePage.isFocused());
        isTelefone = (listTelefone.isFocused() || listContatoTelefone.isFocused());
        isEmpresa = (listEndereco.isFocused() || listHomePage.isFocused() || listEmail.isFocused() || listTelefone.isFocused());
        isContato = (listContatoNome.isFocused() || listContatoHomePage.isFocused() || listContatoEmail.isFocused() || listContatoTelefone.isFocused());
    }

    void keyInsert() {
        tipoAddDelete();
        if (isEndereco) {
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setStrIco("ic_endereco_add_orange_24dp.png");
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
            listEnderecoVOObservableList.setAll(getEmpresaVO().getTabEnderecoVOList());
            listEndereco.getSelectionModel().selectLast();
            txtEndCEP.requestFocus();
        }
        if (isEmail || isHomePage) {
            System.out.println("isEmail || isHomePage");
            addEmailHomePage("");
        }
    }

    void addEmailHomePage(String temp) {
        System.out.println("addEmailHomePage()");
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco(isEmail ? "ic_web_email_white_24dp.png" : "ic_web_home_page_white_24dp.png");
        alertMensagem.setCabecalho(String.format("Adicionar dados [%s%s]", isEmail ? "email" : "home page", isContato ? " contato" : ""));
        alertMensagem.setPromptText(String.format("%s, qual %s %sa empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO,
                isEmail ? "o email a ser adicionado" : "a home page a ser adicionada",
                isContato ? String.format(" para o contato: [%s]\nd", isContato ? getContatoVO() : "") : "\npar ",
                txtRazao.getText()));
        String emailHomePage = temp;
        if ((emailHomePage = alertMensagem.getRetornoAlert_TextField(
                ServiceFormatarDado.gerarMascara("email", 120, "?"), emailHomePage)
                .orElse(null)) == null) return;
        if (!ServiceValidarDado.isEmailHomePageValido(emailHomePage, isEmail, true))
            addEmailHomePage(emailHomePage);
        if (!ServiceValidarDado.isEmailHomePageValido(emailHomePage, isEmail, false)) return;
        //ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.INSERT));
        if (isEmpresa) {
            getEmpresaVO().getTabEmailHomePageVOList().add(new TabEmailHomePageVO(emailHomePage, isEmail));
            listEmailHomePageVOObservableList.setAll(getEmpresaVO().getTabEmailHomePageVOList());
        } else {
            getContatoVO().getTabEmailHomePageVOList().add(new TabEmailHomePageVO(emailHomePage, isEmail));
            listContatoEmailHomePageVOObservableList.setAll(getContatoVO().getTabEmailHomePageVOList());
        }
    }

    void keyDelete() {
        tipoAddDelete();
        if (isEndereco) {
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
                listEnderecoVOObservableList.setAll(getEmpresaVO().getTabEnderecoVOList());
                listEndereco.getSelectionModel().selectFirst();
            }
        }
        if (isEmail || isHomePage) {
            TabEmailHomePageVO emailHomePage = null;
            if (isEmpresa)
                emailHomePage = isEmail ? listEmail.getSelectionModel().getSelectedItem() : listHomePage.getSelectionModel().getSelectedItem();
            else
                emailHomePage = isEmail ? listContatoEmail.getSelectionModel().getSelectedItem() : listContatoHomePage.getSelectionModel().getSelectedItem();
            if (emailHomePage == null) return;
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setStrIco(isEmail ? "ic_web_email_white_24dp.png" : "ic_web_home_page_white_24dp.png");
            alertMensagem.setCabecalho(String.format("Deletar dados [%s]", isEmail ? "email" : "home page"));
            alertMensagem.setPromptText(String.format("%s, deseja deletar %s: [%s]%s\nda empresa: [%s] ?",
                    USUARIO_LOGADO_APELIDO,
                    isEmail ? "o email" : "a home page",
                    emailHomePage,
                    isContato ? String.format(" para o contato: [%s]", getContatoVO()) : "",
                    txtRazao.getText()));
            if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
            if (isEmpresa) {
                if (emailHomePage.getId() == 0)
                    getEmpresaVO().getTabEmailHomePageVOList().remove(emailHomePage);
                else
                    getEmpresaVO().getTabEmailHomePageVOList().get(getEmpresaVO().getTabEmailHomePageVOList().indexOf(emailHomePage))
                            .setId(emailHomePage.getId() * (-1));
                listEmailHomePageVOObservableList.setAll(getEmpresaVO().getTabEmailHomePageVOList());
            } else {
                if (emailHomePage.getId() == 0)
                    getContatoVO().getTabEmailHomePageVOList().remove(emailHomePage);
                else
                    getContatoVO().getTabEmailHomePageVOList().get(getContatoVO().getTabEmailHomePageVOList().indexOf(emailHomePage))
                            .setId(emailHomePage.getId() * (-1));
                listContatoEmailHomePageVOObservableList.setAll(getContatoVO().getTabEmailHomePageVOList());
            }
        }
    }

    void delTelefone() {
        TabTelefoneVO telefone;
        telefone = listTelefone.getSelectionModel().getSelectedItem();
        if (telefone == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_telefone_white_24dp.png");
        alertMensagem.setCabecalho(String.format("Deletar dados [telefone]"));
        alertMensagem.setPromptText(String.format("%s, deseja deletar o telefone: [%s]\nda empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, telefone, txtRazao.getText()));
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        if (telefone.getId() == 0)
            getEmpresaVO().getTabTelefoneVOList().remove(telefone);
        else
            getEmpresaVO().getTabTelefoneVOList().get(getEmpresaVO().getTabTelefoneVOList().indexOf(telefone))
                    .setId(telefone.getId() * (-1));
        //*listTelefoneVOObservableList.setAll(FXCollections.observableArrayList(getEmpresaVO().getTabTelefoneVOList()));
    }

    void addTelefone() {
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_telefone_white_24dp.png");
        alertMensagem.setCabecalho("Adicionar dados [telefone]");
        alertMensagem.setPromptText(String.format("%s, qual telefone a ser adicionado para empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, txtRazao.getText()));
        Pair<String, Object> pair;
        if ((pair = alertMensagem.getRetornoAlert_TextFieldEComboBox(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVOList(),
                "telefone", "").orElse(null)) == null) return;
        getEmpresaVO().getTabTelefoneVOList().add(new TabTelefoneVO(pair.getKey(), (SisTelefoneOperadoraVO) pair.getValue()));
        //*listTelefoneVOObservableList.setAll(FXCollections.observableArrayList(getEmpresaVO().getTabTelefoneVOList()));
        listTelefone.getSelectionModel().selectLast();
    }

    void delContato() {
        TabContatoVO contato;
        contato = listContatoNome.getSelectionModel().getSelectedItem();
        if (contato == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_contato_del_orange_24dp.png");
        alertMensagem.setCabecalho(String.format("Deletar dados [contato]"));
        alertMensagem.setPromptText(String.format("%s, deseja deletar o contato: [%s]\nda empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, contato, getEmpresaVO().getRazao()));
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        if (contato.getId() == 0)
            getEmpresaVO().getTabContatoVOList().remove(contato);
        else
            getEmpresaVO().getTabContatoVOList().get(getEmpresaVO().getTabContatoVOList().indexOf(contato))
                    .setId(contato.getId() * (-1));
        tpnPessoaContato.setText("Pessoa de contato");
        //*listContatoVOObservableList.setAll(FXCollections.observableArrayList(getEmpresaVO().getTabContatoVOList()));
    }

    void addContato() {
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_contato_add_orange_24dp.png");
        alertMensagem.setCabecalho("Adicionar dados [contato]");
        alertMensagem.setPromptText(String.format("%s, qual contato a ser adicionado para empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, getEmpresaVO().getRazao()));
        Pair<String, Object> pair;
        if ((pair = alertMensagem.getRetornoAlert_TextFieldEComboBox(new SisCargoDAO().getSisCargoVOList(),
                ServiceFormatarDado.gerarMascara("", 40, "@"), "").orElse(null)) == null) return;
        getEmpresaVO().getTabContatoVOList().add(new TabContatoVO(pair.getKey(), (SisCargoVO) pair.getValue()));
        tpnPessoaContato.setText("Pessoa de contato");
        //*listContatoVOObservableList.setAll(FXCollections.observableArrayList(getEmpresaVO().getTabContatoVOList()));
        listContatoNome.getSelectionModel().selectLast();
    }

    void delContatoEmailHomePage() {
        TabEmailHomePageVO contatoEmailHomePage;
        contatoEmailHomePage = listContatoEmail.isFocused() ?
                listContatoEmail.getSelectionModel().getSelectedItem() :
                listContatoHomePage.getSelectionModel().getSelectedItem();
        if (contatoEmailHomePage == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco(listContatoEmail.isFocused() ? "ic_web_email_white_24dp.png" : "ic_web_home_page_white_24dp.png");
        alertMensagem.setCabecalho(String.format("Deletar dados [%s contato]", listContatoEmail.isFocused() ? "email" : "home page"));
        alertMensagem.setPromptText(String.format("%s, deseja deletar %s: [%s] para o contato: [%s]\nda empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, listContatoEmail.isFocused() ? "o email" : "a home page", listContatoEmail.isFocused() ?
                        listContatoEmail.getSelectionModel().getSelectedItem() : listContatoHomePage.getSelectionModel().getSelectedItem(),
                getContatoVO().getDescricao(), getEmpresaVO().getRazao()));
    }

    void addContatoEmailHomePage() {
    }

    void delContatoTelefone() {
        TabTelefoneVO contatoTelefone;
        contatoTelefone = listContatoTelefone.getSelectionModel().getSelectedItem();
        if (contatoTelefone == null) return;
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_telefone_white_24dp.png");
        alertMensagem.setCabecalho(String.format("Deletar dados [telefone contato]"));
        alertMensagem.setPromptText(String.format("%s, deseja deletar o telefone: [%s] do contato: [%s]\nda empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, contatoTelefone, getContatoVO().getDescricao(), getEmpresaVO().getRazao()));
        if (alertMensagem.getRetornoAlert_YES_NO().get() == ButtonType.NO) return;
        if (contatoTelefone.getId() == 0)
            getContatoVO().getTabTelefoneVOList().remove(contatoTelefone);
        else
            getContatoVO().getTabTelefoneVOList().get(getContatoVO().getTabEmailHomePageVOList().indexOf(contatoTelefone))
                    .setId(contatoTelefone.getId() * (-1));
        //*listContatoTelefoneVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabTelefoneVOList()));
    }

    void addContatoTelefone() {
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco("ic_telefone_white_24dp.png");
        alertMensagem.setCabecalho("Adicionar dados [telefone contato]");
        alertMensagem.setPromptText(String.format("%s, qual telefone a ser adicionado para o contato: [%s]\nda empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, getContatoVO().getDescricao(), getEmpresaVO().getRazao()));
        Pair<String, Object> pair;
        if ((pair = alertMensagem.getRetornoAlert_TextFieldEComboBox(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVOList(),
                "telefone", "").orElse(null)) == null) return;
        getContatoVO().getTabTelefoneVOList().add(new TabTelefoneVO(pair.getKey(), (SisTelefoneOperadoraVO) pair.getValue()));
        //*listContatoTelefoneVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabTelefoneVOList()));
        listContatoTelefone.getSelectionModel().selectLast();
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
        if (!result) {
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Dados inválido!");
            alertMensagem.setPromptText(String.format("%s, precisa de dados válidos para empresa!", USUARIO_LOGADO_APELIDO));
            alertMensagem.setStrIco("ic_dados_invalidos_white_24dp.png");
            alertMensagem.getRetornoAlert_OK();
        } else result = guardarEmpresa();
        return result;
    }

    boolean validarEnderecoPrincipal() {
        boolean result = true;
        String campoInvalido = "";
        TabEmpresaReceitaFederalVO empresaReceitaFederal;

        if ((empresaReceitaFederal = getEmpresaVO().getTabEmpresaReceitaFederalVOList().stream()
                .filter(receita -> receita.getStr_Key().toLowerCase().equals("situacao"))
                .findFirst().orElse(null)) != null)
            if (!empresaReceitaFederal.getStr_Value().toLowerCase().equals("ativa")) {
                limparEndereco();
                return true;
            }
        if (!(result = (txtEndCEP.getText().replaceAll("\\D", "").length() == 8 && result == true))) {
            txtEndCEP.requestFocus();
            campoInvalido = "cep valido!";
        }
        if (!(result = (txtEndLogradouro.getText().length() >= 3 && result == true))) {
            txtEndLogradouro.requestFocus();
            campoInvalido = "logradouro com no mínimo de 3 digitos!";
        }
        if (!(result = (txtEndNumero.getText().length() >= 1 && result == true))) {
            txtEndNumero.requestFocus();
            campoInvalido = "número com no mínimo 1 digito!";
        }
        if (!(result = (txtEndBairro.getText().length() >= 3 && result == true))) {
            txtEndBairro.requestFocus();
            campoInvalido = "bairro com no mínimo de 3 digitos!";
        }
        if (!result) {
            alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Endereço inválido!");
            alertMensagem.setPromptText(String.format("%s, precisa informar %s", USUARIO_LOGADO_APELIDO, campoInvalido));
            alertMensagem.setStrIco("ic_endereco_invalido_white_24dp.png");
            alertMensagem.getRetornoAlert_OK();
        } else result = guardarEndereco(getEnderecoVO());
        return result;
    }

    boolean buscaDuplicidade() {
        TabEmpresaVO duplicEmpresa;
        try {
            if ((duplicEmpresa = new TabEmpresaDAO().getTabEmpresaVO(getEmpresaVO().getRazao().replaceAll("\\D", ""))) == null)
                return false;
            if (getEmpresaVO().getId() != duplicEmpresa.getId()) {
                alertMensagem = new ServiceAlertMensagem();
                alertMensagem.setCabecalho("C.N.P.J. duplicado");
                alertMensagem.setPromptText(String.format("%s, o C.N.P.J.: [%s] já está cadastrado no sistema!",
                        USUARIO_LOGADO_APELIDO, getEmpresaVO().getRazao()));
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
                                new TabEnderecoDAO().deleteTabEnderecoVO(conn, endereco.getId(), getEmpresaVO().getId());
                            else if (endereco.getId() > 0)
                                new TabEnderecoDAO().updateTabEnderecoVO(conn, endereco);
                            else
                                endereco.setId(new TabEnderecoDAO().insertTabEnderecoVO(conn, endereco, getEmpresaVO().getId()));
                        } catch (SQLException ex) {
                            throw new RuntimeException("Erro no endereco ===>> ", ex);
                        }
                    });
            getEmpresaVO().getTabEmailHomePageVOList().stream().sorted(Comparator.comparing(TabEmailHomePageVO::getId))
                    .forEach(emailHome -> {
                        try {
                            if (emailHome.getId() < 0)
                                new TabEmailHomePageDAO().deleteEmailHomePageVO(conn, emailHome.getId(),
                                        getEmpresaVO().getId(), 0);
                            else if (emailHome.getId() > 0)
                                new TabEmailHomePageDAO().updateTabEmailHomePageVO(conn, emailHome);
                            else
                                emailHome.setId(new TabEmailHomePageDAO().insertEmailHomePageVO(conn, emailHome,
                                        getEmpresaVO().getId(), 0));
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro no emailHome ===>>", ex);
                        }
                    });
            getEmpresaVO().getTabTelefoneVOList().stream().sorted(Comparator.comparing(TabTelefoneVO::getId))
                    .forEach(telefone -> {
                        try {
                            if (telefone.getId() < 0)
                                new TabTelefoneDAO().deleteTabTelefoneVO(conn, telefone.getId(),
                                        getEmpresaVO().getId(), 0);
                            else if (telefone.getId() > 0)
                                new TabTelefoneDAO().updateTabTelefoneVO(conn, telefone);
                            else
                                telefone.setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, telefone,
                                        getEmpresaVO().getId(), 0));
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro no telefone ==>>", ex);
                        }
                    });
            getEmpresaVO().getTabContatoVOList().stream().sorted(Comparator.comparing(TabContatoVO::getId))
                    .forEach(contato -> {
                        try {
                            if (contato.getId() < 0)
                                new TabContatoDAO().deleteTabContatoVO(conn, contato, getEmpresaVO().getId());
                            else if (contato.getId() > 0)
                                new TabContatoDAO().updateTabContatoVO(conn, contato);
                            else
                                contato.setId(new TabContatoDAO().insertTabContatoVO(conn, contato, getEmpresaVO().getId()));
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro no contato ===>> ", ex);
                        }
                    });
            new TabEmpresaReceitaFederalDAO().deleteTabEmpresaReceitaFederalVO(conn, getEmpresaVO().getId());
            getEmpresaVO().getTabEmpresaReceitaFederalVOList().stream().sorted(Comparator.comparing(TabEmpresaReceitaFederalVO::getIsAtividadePrincipal))
                    .forEach(receita -> {
                        try {
                            receita.setTabEmpresa_id(getEmpresaVO().getId());
                            new TabEmpresaReceitaFederalDAO().insertTabEmpresaReceitaFederalVO(conn, receita);
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro no dados receita federal ===>> ", ex);
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