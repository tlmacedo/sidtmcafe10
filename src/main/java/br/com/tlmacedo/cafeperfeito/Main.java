package br.com.tlmacedo.cafeperfeito;

import br.com.tlmacedo.cafeperfeito.service.ServiceAbreVariaveisSistema;
import br.com.tlmacedo.cafeperfeito.view.ViewLogin;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ServiceAbreVariaveisSistema();
        Locale.setDefault(new Locale(ServiceAbreVariaveisSistema.tConfig.getMyLocale().substring(0, 2), ServiceAbreVariaveisSistema.tConfig.getMyLocale().substring(3)));


        new ViewLogin().openViewLogin(false);

//        new ViewPrincipal().openViewPrincipal();
    }
}
