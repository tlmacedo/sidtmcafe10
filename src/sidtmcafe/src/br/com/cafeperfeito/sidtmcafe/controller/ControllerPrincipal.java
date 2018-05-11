package br.com.cafeperfeito.sidtmcafe.controller;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.ModelController;
import br.com.cafeperfeito.sidtmcafe.model.dao.SisMenuPrincipalDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.TabColaboradorDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMenuPrincipalVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabColaboradorVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceComandoTecladoMouse;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewPrincipal;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.controls.JFXTreeView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        atualizarStatusBarPrincipal();
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

        treeMenuViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER)
                treeMenuViewPrincipal.fireEvent(ServiceComandoTecladoMouse.clickMouse(2));
        });

        treeMenuViewPrincipal.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (treeMenuViewPrincipal.getSelectionModel().getSelectedIndex() < 0) return;
            SisMenuPrincipalVO item;
            if ((item = treeMenuViewPrincipal.getSelectionModel().getSelectedItem().getValue()) == null) return;
            if (!item.getDescricao().toLowerCase().equals("sair") && !(event.getClickCount() == 2)) return;
            int tabId = 0;
            if ((tabId = tabExistente(item.getDescricao())) == 0) {
                switch (item.getDescricao().toLowerCase()) {
                    case "sair":
                        fechar();
                        break;
                    case "empresa":
                        System.out.println("abre empresa");
                        break;
                    case "produto":
                        System.out.println("abre produto");
                        break;
                    case "entradaproduto":
                        System.out.println("abre entradaproduto");
                        break;
                }
            }
            System.out.println("tabId: [" + tabId + "]");
            if (tabPaneViewPrincipal.getTabs().size() > 0)
                tabPaneViewPrincipal.getSelectionModel().select(tabId);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjeros();
        fatorarObjetos();
        escutarTeclar();
    }

    Timeline timeline;

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
        return 0;
    }

    void atualizarStatusBarPrincipal() {
        if (USUARIO_LOGADO_ID == null)
            preencheVariaveisUsuario();
        stbUsuarioLogado.setText("Usuário [" + USUARIO_LOGADO_ID + "]: " + USUARIO_LOGADO_APELIDO);

        Tooltip tooltip = new Tooltip("banco de dados: [" + BD_DATABASE_STB + "]    horario_log: " + USUARIO_LOGADO_HORA_STR);

        stbHorario.setTooltip(tooltip);
        //stbHorario.setGraphic(new ImageView(getClass().getResource("/image/ico/ic_relogio_orange_18dp.png").toString()));

        timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> stbHorario.setText(LocalTime.now().format(DTF_HORA))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    void preencheVariaveisUsuario() {
        TabColaboradorVO colaboradorVO = new TabColaboradorDAO().getTabColaboradorVO(1);
        USUARIO_LOGADO_ID = String.valueOf(colaboradorVO.getId());
        USUARIO_LOGADO_NOME = colaboradorVO.getNome();
        USUARIO_LOGADO_APELIDO = colaboradorVO.getApelido();
        DATA_HORA = LocalDateTime.now();
        DATA_HORA_STR = DATA_HORA.format(DTF_DATAHORA);
        USUARIO_LOGADO_DATA = LocalDate.now();
        USUARIO_LOGADO_DATA_STR = USUARIO_LOGADO_DATA.format(DTF_DATA);
        USUARIO_LOGADO_HORA = LocalTime.now();
        USUARIO_LOGADO_HORA_STR = USUARIO_LOGADO_HORA.format(DTF_HORA);
    }


}