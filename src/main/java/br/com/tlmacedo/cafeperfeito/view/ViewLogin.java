package br.com.tlmacedo.cafeperfeito.view;

import br.com.tlmacedo.cafeperfeito.interfaces.Constants;
import br.com.tlmacedo.cafeperfeito.service.ServiceOpenView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ViewLogin implements Constants {

    static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public void openViewLogin(boolean showAndWait) {
        this.stage = new Stage();
        Parent parent;
        Scene scene = null;

        try {
            parent = FXMLLoader.load(getClass().getResource(FXML_LOGIN));
            scene = new Scene(parent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle(FXML_LOGIN_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().setAll(new Image(getClass().getResource(FXML_LOGIN_ICON).toString()));
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().setAll(getClass().getResource(SIS_CSS_STYLE_SHEETS).toString());

        new ServiceOpenView(stage, showAndWait);

    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        //ViewLogin.stage = primaryStage;
//        openViewLogin(false);
//    }
}
