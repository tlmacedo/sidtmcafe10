package br.com.cafeperfeito.sidtmcafe.view;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.service.ServiceOpenView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class ViewLogin extends Application implements Constants {

    private static Stage stage;

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
        stage.getIcons().setAll(new Image(this.getClass().getResource(FXML_LOGIN_ICON).toString()));
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().setAll(getClass().getResource(STYLE_SHEETS).toString());
        //scene.getRoot().getStylesheets().add(FXML_LOGIN_STYLE.toString());

        new ServiceOpenView(stage, showAndWait);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ViewLogin.stage = primaryStage;
        openViewLogin(false);
    }
}
