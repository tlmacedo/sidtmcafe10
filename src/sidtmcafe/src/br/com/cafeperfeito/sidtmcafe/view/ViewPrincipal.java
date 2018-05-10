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
        openViewPrincipal();
    }

}
