package br.com.cafeperfeito.sidtmcafe;

import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewLogin;
import br.com.cafeperfeito.sidtmcafe.view.ViewPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServiceVariavelSistema.newServiceVariavelSistema(null);
        new ViewPrincipal().openViewPrincipal();
//        new ViewLogin().openViewLogin(false);
    }
}
