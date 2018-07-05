package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.model.TabModel;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.*;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroEmpresa;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
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
import javafx.util.Pair;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCadastroEmpresa extends ServiceVariavelSistema implements Initializable, ModelController, Constants {

    ObservableList<TabContatoVO> listContatoVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> listContatoHomePageVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabEmailHomePageVO> listContatoEmailVOObservableList = FXCollections.observableArrayList();
    ObservableList<TabTelefoneVO> listContatoTelefoneVOObservableList = FXCollections.observableArrayList();

    public AnchorPane painelViewCadastroEmpresa;
    public TitledPane tpnCadastroEmpresa;
    public JFXTextField txtPesquisaEmpresa;
    public TreeTableView<TabEmpresaVO> ttvEmpresa;
    public JFXComboBox cboFiltroPesquisa;
    public Label lblRegistrosLocalizados;
    public TitledPane tpnDadoCadastral;
    public TabPane tpnContatoPrazosCondicoes;
    public TitledPane tpnPessoaContato;
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

        listaTarefa.add(new Pair("carregarTabCargo", "carregando lista cargos"));
        listaTarefa.add(new Pair("carregarSisTipoEndereco", "carregando lista tipo endereço"));
        listaTarefa.add(new Pair("carregarSisTelefoneOperadora", "carregando lista operadoras telefone"));
        listaTarefa.add(new Pair("carregarListaEmpresa", "carregando lista de empresas"));

        listaTarefa.add(new Pair("preencherTabelaEmpresa", "preenchendo tabela empresa"));

        new ServiceSegundoPlano().tarefaAbreCadastroEmpresa(getTaskCadastroEmpresa(), listaTarefa.size());
    }

    @Override
    public void fatorarObjetos() {
        listContatoNome.setItems(listContatoVOObservableList);
        listContatoHomePage.setItems(listContatoHomePageVOObservableList);
        listContatoEmail.setItems(listContatoEmailVOObservableList);
        listContatoTelefone.setItems(listContatoTelefoneVOObservableList);
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsCliente());
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsFornecedor());
        ServiceFormatarDado.fatorarColunaCheckBox(TabModel.getColunaIsTransportadora());
    }

    @Override
    public void escutarTecla() {
        ttvEmpresa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setEmpresaVO(newValue.getValue());
            }
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
                    case DELETE:
                        if (getStatusFormulario().toLowerCase().equals("pesquisa")) break;
                        if (listContatoNome.isFocused())
                            if (event.getCode() == KeyCode.HELP)
                                addContato();
                            else
                                delContato();
                        if (listContatoHomePage.isFocused() || listContatoEmail.isFocused())
                            if (event.getCode() == KeyCode.HELP)
                                addContatoEmailHomePage();
                            else
                                delContatoEmailHomePage();
                        if (listContatoTelefone.isFocused())
                            if (event.getCode() == KeyCode.HELP)
                                addContatoTelefone();
                            else
                                delContatoTelefone();
                        break;
                }
                ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().setOnCloseRequest(event1 -> {
                    if (!getStatusFormulario().toLowerCase().equals("pesquisa")) {
                        event1.consume();
                    }
                });
            }
        };

        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerCadastroEmpresa);

        txtPesquisaEmpresa.textProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        cboFiltroPesquisa.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> pesquisaEmpresa());

        txtPesquisaEmpresa.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            ttvEmpresa.requestFocus();
            ttvEmpresa.getSelectionModel().selectFirst();
        });

        listContatoNome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setContatoVO(newValue);
        });

        empresaVOFilteredList.addListener((InvalidationListener) c -> {
            atualizaQtdRegistroLocalizado();
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
        ServiceCampoPersonalizado.fieldMask(painelViewCadastroEmpresa);
        Platform.runLater(() -> {
            setStatusFormulario("Pesquisa");
            ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7));
        });
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
                            break;
                        case "preencherCboSituacaoSistema":
                            break;
                        case "preencherCboEndUF":
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
    }

    public TabContatoVO getContatoVO() {
        return contatoVO;
    }

    public void setContatoVO(TabContatoVO contato) {
        System.out.printf("contato: [%s]\n", contato);
        if (contato == null) {
            contato = new TabContatoVO();
            System.out.printf("contato == null\n");
        } else {
            System.out.printf("contato != null\n");
        }
        contatoVO = contato;
        System.out.printf("setContatoVO: [%s]\n", contato);
        exibirDadosContato();
    }

    void exibirDadosEmpresa() {
        listContatoVOObservableList.setAll(FXCollections.observableArrayList(getEmpresaVO().getTabContatoVOList()));
        System.out.printf("listContatoVOObservableList: [%s]\n", listContatoVOObservableList);
        if (listContatoVOObservableList.size() > 0)
            listContatoNome.getSelectionModel().selectFirst();
        else
            setContatoVO(null);
    }

    void exibirDadosContato() {
        tpnPessoaContato.setText(String.format("Pessoa de contato"));
        listContatoHomePageVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabEmailHomePageVOList()));
        listContatoEmailVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabEmailHomePageVOList()));
        listContatoTelefoneVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabTelefoneVOList()));
        tpnPessoaContato.setText(String.format("Pessoa de contato: %s",
                !getContatoVO().getDescricao().equals("") ? String.format("[%s]", getContatoVO().getDescricao()) : ""));
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
//        tpnPessoaContato.setText("Pessoa de contato");
        listContatoVOObservableList.setAll(FXCollections.observableArrayList(getEmpresaVO().getTabContatoVOList()));
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
        listContatoVOObservableList.setAll(FXCollections.observableArrayList(getEmpresaVO().getTabContatoVOList()));
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
        alertMensagem = new ServiceAlertMensagem();
        alertMensagem.setStrIco(listContatoEmail.isFocused() ? "ic_web_email_white_24dp.png" : "ic_web_home_page_white_24dp.png");
        alertMensagem.setCabecalho(String.format("Adicionar dados [%s contato]", listContatoEmail.isFocused() ? "email" : "home page"));
        alertMensagem.setPromptText(String.format("%s, qual %s a ser adicionado para o contato: [%s]\nda empresa: [%s] ?",
                USUARIO_LOGADO_APELIDO, listContatoEmail.isFocused() ? "o email" : "a home page", listContatoEmail.isFocused() ?
                        listContatoEmail.getSelectionModel().getSelectedItem() : listContatoHomePage.getSelectionModel().getSelectedItem(),
                getEmpresaVO().getRazao()));
        String contatoEmailHomePage;
        if ((contatoEmailHomePage = alertMensagem.getRetornoAlert_TextField(ServiceFormatarDado.gerarMascara("email",
                120, "?"), "").orElse(null)) == null) return;
        while (!ServiceValidarDado.isEmailHomePageValido(contatoEmailHomePage, listContatoEmail.isFocused()))
            addContatoEmailHomePage();
        getContatoVO().getTabEmailHomePageVOList().add(new TabEmailHomePageVO(contatoEmailHomePage, listContatoEmail.isFocused()));
        listContatoHomePageVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabEmailHomePageVOList()));
        listContatoEmailVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabEmailHomePageVOList()));
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
        listContatoTelefoneVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabTelefoneVOList()));
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
        listContatoTelefoneVOObservableList.setAll(FXCollections.observableArrayList(getContatoVO().getTabTelefoneVOList()));
        listContatoTelefone.getSelectionModel().selectLast();
    }

    boolean validarDados() {
        return (validarDadosEmpresa() && validarEnderecoPrincipal());
    }

    boolean validarDadosEmpresa() {
        boolean result = true;
        return result;
    }

    boolean validarEnderecoPrincipal() {
        boolean result = true;
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