package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.SisMenuPrincipalDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMenuPrincipalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceComandoTecladoMouse;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewPrincipal;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.controls.JFXTreeView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerPrincipal extends ServiceVariavelSistema implements Initializable, ModelController, Constants {
    public BorderPane painelViewPrincipal;
    public JFXToolbar statusBar_ViewPrincipal;
    public TabPane tabPaneViewPrincipal;
    public Label lblImageLogoViewPrincipal;
    public JFXTreeView treeMenuViewPrincipal;
    public Label lblBotaoRetraiMenuViewPrincipal;
    public Label lblBotaoExpandeMenuViewPrincipal;
    public Label lblCopyRight;

    @Override
    public void fechar() {
        ViewPrincipal.getStage().close();
    }

    @Override
    public void criarObjetos() {

    }

    @Override
    public void preencherObjeros() {
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
    }

    @Override
    public void fatorarObjetos() {

    }

    @Override
    public void escutarTeclar() {
        lblBotaoExpandeMenuViewPrincipal.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> expandeMenuItem(true));

        lblBotaoRetraiMenuViewPrincipal.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> expandeMenuItem(false));

        painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (CODE_KEY_SHIFT_CTRL_POSITIVO.match(event) || CHAR_KEY_SHIFT_CTRL_POSITIVO.match(event))
                lblBotaoExpandeMenuViewPrincipal.fireEvent(ServiceComandoTecladoMouse.clickMouse(1));
            if (CODE_KEY_SHIFT_CTRL_NEGATIVO.match(event) || CHAR_KEY_SHIFT_CTRL_NEGATIVO.match(event))
                lblBotaoRetraiMenuViewPrincipal.fireEvent(ServiceComandoTecladoMouse.clickMouse(1));
            if (event.getCode() == KeyCode.F12)
                if (tabPaneViewPrincipal.getTabs().size() == 0)
                    fechar();
                else
                    ;//pergunta se vai fechar a tab

        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjeros();
        fatorarObjetos();
        escutarTeclar();
    }

    void expandeMenuItem(boolean expande) {
        for (int i = 0; i < treeMenuViewPrincipal.getExpandedItemCount(); i++) {
            treeMenuViewPrincipal.getTreeItem(i).setExpanded(expande);
        }
    }

}
