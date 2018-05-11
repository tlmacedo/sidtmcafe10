package br.com.cafeperfeito.sidtmcafe.view;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.dao.TabColaboradorDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabColaboradorVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceOpenView;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ViewPrincipal extends Application implements Constants {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public void openViewPrincipal() {
        this.stage = new Stage();
        Parent parent;
        Scene scene = null;

        try {
            parent = FXMLLoader.load(getClass().getResource(FXML_PRINCIPAL));
            scene = new Scene(parent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle(FXML_PRINCIPAL_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().setAll(new Image(getClass().getResource(FXML_PRINCIPAL_ICON).toString()));
        scene.getStylesheets().setAll(getClass().getResource(STYLE_SHEETS).toString());

        new ServiceOpenView(stage, false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabColaboradorVO colaboradorVO = new TabColaboradorDAO().getTabColaboradorVO(1);
        ServiceVariavelSistema.USUARIO_LOGADO_ID = String.valueOf(colaboradorVO.getId());
        ServiceVariavelSistema.USUARIO_LOGADO_NOME = colaboradorVO.getNome();
        ServiceVariavelSistema.USUARIO_LOGADO_APELIDO = colaboradorVO.getApelido();
        ServiceVariavelSistema.DATA_HORA = LocalDateTime.now();
        ServiceVariavelSistema.DATA_HORA_STR = ServiceVariavelSistema.DATA_HORA.format(DTF_DATAHORA);
        ServiceVariavelSistema.USUARIO_LOGADO_DATA = LocalDate.now();
        ServiceVariavelSistema.USUARIO_LOGADO_DATA_STR = ServiceVariavelSistema.USUARIO_LOGADO_DATA.format(DTF_DATA);
        ServiceVariavelSistema.USUARIO_LOGADO_HORA = LocalTime.now();
        ServiceVariavelSistema.USUARIO_LOGADO_HORA_STR = ServiceVariavelSistema.USUARIO_LOGADO_HORA.format(DTF_HORA);

        openViewPrincipal();
    }

}
