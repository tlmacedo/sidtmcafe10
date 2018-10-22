package br.com.cafeperfeito.sidtmcafe;

import br.com.cafeperfeito.sidtmcafe.model.vo.ConfigSistemaVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import br.com.cafeperfeito.sidtmcafe.service.ServiceXmlUtil;
import br.com.cafeperfeito.sidtmcafe.view.ViewLogin;
import br.com.cafeperfeito.sidtmcafe.view.ViewPrincipal;
import br.com.cafeperfeito.sidtmcafe.xsd.configSistema.informacaoBasica.TInformacaoBasica;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Locale;

public class Main extends Application {

    public static ConfigSistemaVO configSistema;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String xml = ServiceXmlUtil.leXml(new FileInputStream("/Volumes/150GB-Development/Java/Intellij/sidtmcafe10/src/sidtmcafe/resources/xml/configSistema.xml"));
        TInformacaoBasica inf = ServiceXmlUtil.xmlToObject(xml, TInformacaoBasica.class);
        configSistema = new ConfigSistemaVO(inf);

        Locale.setDefault(configSistema.getMyLocale());


        //new ViewLogin().openViewLogin(false);

        ServiceVariavelSistema.newServiceVariavelSistema(null);
        new ViewPrincipal().openViewPrincipal();
    }
}
