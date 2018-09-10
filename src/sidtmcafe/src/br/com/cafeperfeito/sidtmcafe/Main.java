package br.com.cafeperfeito.sidtmcafe;

import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.view.ViewLogin;
import br.com.cafeperfeito.sidtmcafe.view.ViewPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.LOCALE_MY;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(LOCALE_MY);

//        new ViewLogin().openViewLogin(false);

        ServiceVariavelSistema.newServiceVariavelSistema(null);
        new ViewPrincipal().openViewPrincipal();
    }
}
