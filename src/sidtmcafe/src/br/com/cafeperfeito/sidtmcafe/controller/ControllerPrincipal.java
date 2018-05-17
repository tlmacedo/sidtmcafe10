package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.SisMenuPrincipalDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMenuPrincipalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceComandoTecladoMouse;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroEmpresa;
import br.com.cafeperfeito.sidtmcafe.view.ViewCadastroProduto;
import br.com.cafeperfeito.sidtmcafe.view.ViewPrincipal;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.controls.JFXTreeView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerPrincipal extends ServiceVariavelSistema implements Initializable, ModelController, Constants {
    public BorderPane painelViewPrincipal;
    public JFXToolbar statusBar_ViewPrincipal;
    public TabPane tabPaneViewPrincipal;
    public Label lblImageLogoViewPrincipal;
    public JFXTreeView<SisMenuPrincipalVO> treeMenuViewPrincipal;
    public Label lblBotaoRetraiMenuViewPrincipal;
    public Label lblBotaoExpandeMenuViewPrincipal;
    public Label lblCopyRight;
    public Label stbUsuarioLogado;
    public Label stbTeclasTela;
    public Label stbHorario;

    Timeline timeline;
    public static ControllerPrincipal ctrlPrincipal;
    EventHandler<KeyEvent> eventHandlerPricipal;

    @Override
    public void fechar() {
        ViewPrincipal.getStage().close();
    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjetos() {
        lblCopyRight.setText(COPYRIGHT);
        List<SisMenuPrincipalVO> sisMenuPrincipalVOList = new SisMenuPrincipalDAO().getMenuPrincipalVOList();
        TreeItem[] treeItems = new TreeItem[sisMenuPrincipalVOList.size() + 1];
        treeItems[0] = new TreeItem();
        for (SisMenuPrincipalVO menu : sisMenuPrincipalVOList) {
            int i = menu.getId();
            if (menu.getIcoMenu() == null)
                treeItems[i] = new TreeItem(menu);
            else
                treeItems[i] = new TreeItem(menu, new ImageView(getClass().getResource(PATH_ICONE + menu.getIcoMenu()).toString()));
            treeItems[i].setExpanded(true);
            treeItems[menu.getFilho_id()].getChildren().add(treeItems[i]);
        }
        treeMenuViewPrincipal.setRoot(treeItems[0]);
        treeMenuViewPrincipal.setShowRoot(false);

        atualizarStatusBarPrincipal();
    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTecla() {
        lblBotaoExpandeMenuViewPrincipal.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> expandeMenuItem(true));

        lblBotaoRetraiMenuViewPrincipal.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> expandeMenuItem(false));

        eventHandlerPricipal = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (CODE_KEY_SHIFT_CTRL_POSITIVO.match(event) || CHAR_KEY_SHIFT_CTRL_POSITIVO.match(event))
                    lblBotaoExpandeMenuViewPrincipal.fireEvent(ServiceComandoTecladoMouse.clickMouse(1));
                if (CODE_KEY_SHIFT_CTRL_NEGATIVO.match(event) || CHAR_KEY_SHIFT_CTRL_NEGATIVO.match(event))
                    lblBotaoRetraiMenuViewPrincipal.fireEvent(ServiceComandoTecladoMouse.clickMouse(1));
                if (event.getCode() == KeyCode.F12)
                    if (tabPaneViewPrincipal.getTabs().size() == 0)
                        fechar();
                    else
                        ;//pergunta se vai fechar a tab
                if (event.isShiftDown() && event.isControlDown()) {
                    if (event.getCode() == KeyCode.SHIFT || event.getCode() == KeyCode.CONTROL) return;
                    SisMenuPrincipalVO menu;
                    if ((menu = new SisMenuPrincipalDAO().getMenuPrincipalVO("ctrl+shift+" + event.getCode())) != null)
                        addTab(menu);
                }
            }
        };

        painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerPricipal);

        tabPaneViewPrincipal.getTabs().addListener(new ListChangeListener<Tab>() {
            @Override
            public void onChanged(Change<? extends Tab> c) {
                String icon = FXML_PRINCIPAL_ICON_BLACK;
                if (tabPaneViewPrincipal.getTabs().size() > 0)
                    icon = FXML_PRINCIPAL_ICON_ORANGE;
                lblImageLogoViewPrincipal.setVisible(tabPaneViewPrincipal.getTabs().size() == 0);

                ViewPrincipal.getStage().getIcons().setAll(new Image(getClass().getResource(icon).toString()));
            }
        });

        treeMenuViewPrincipal.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (treeMenuViewPrincipal.getSelectionModel().getSelectedIndex() < 0) return;
            SisMenuPrincipalVO item;
            if ((item = treeMenuViewPrincipal.getSelectionModel().getSelectedItem().getValue()) == null) return;
            if (!item.getDescricao().toLowerCase().equals("sair") && !(event.getClickCount() == 2)) return;
            addTab(item);
        });

        treeMenuViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER)
                treeMenuViewPrincipal.fireEvent(ServiceComandoTecladoMouse.clickMouse(2));
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ctrlPrincipal = this;
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();

    }

    void expandeMenuItem(boolean expande) {
        for (int i = 0; i < treeMenuViewPrincipal.getExpandedItemCount(); i++) {
            treeMenuViewPrincipal.getTreeItem(i).setExpanded(expande);
        }
    }

    int tabExistente(String tabNome) {
        for (int i = 0; i < tabPaneViewPrincipal.getTabs().size(); i++) {
            if (tabPaneViewPrincipal.getTabs().get(i).getText().equals(tabNome))
                return i;
        }
        return -1;
    }

    void addTab(SisMenuPrincipalVO item) {
        int tabId;
        if ((tabId = tabExistente(item.getTituloTab())) < 0) {
            Tab tab = null;
            switch (item.getDescricao().toLowerCase()) {
                case "sair":
                    fechar();
                    break;
                case "empresa":
                    tab = new ViewCadastroEmpresa().openTabCadastroEmpresa(item.getTituloTab());
                    break;
                case "produto":
                    tab = new ViewCadastroProduto().openTabCadastroProduto(item.getTituloTab());
                    break;
                case "entradaproduto":
                    System.out.println("abre entradaproduto");
                    break;
            }
            if (tab != null) {
                tabPaneViewPrincipal.getTabs().add(tab);
                tabId = (tabPaneViewPrincipal.getTabs().size() - 1);
            }
        }
        if (tabPaneViewPrincipal.getTabs().size() > 0) tabPaneViewPrincipal.getSelectionModel().select(tabId);
    }

    void atualizarStatusBarPrincipal() {
        stbUsuarioLogado.setText("Usuário [" + USUARIO_LOGADO_ID + "]: " + USUARIO_LOGADO_APELIDO);

        Tooltip tooltip = new Tooltip("banco de dados: [" + BD_DATABASE_STB + "]    horario_log: " + USUARIO_LOGADO_HORA_STR);

        stbHorario.setTooltip(tooltip);

        timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> stbHorario.setText(LocalTime.now().format(DTF_HORA))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void atualizarStatusBarTeclas(String statusBarTeclas) {
        stbTeclasTela.setText(statusBarTeclas);
    }

}
