package br.com.tlmacedo.cafeperfeito.service;

import br.com.tlmacedo.cafeperfeito.xsd.sistema.config.TConfig;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ServiceAbreVariaveisSistema {
    public static TConfig tConfig;

    public ServiceAbreVariaveisSistema() {
        try {
            FileInputStream arqConfiSistema = new FileInputStream(getClass().getClassLoader().getResource("xml/configSistema.xml").getFile());
            String xml = ServiceXmlUtil.leXml(arqConfiSistema);
            tConfig = ServiceXmlUtil.xmlToObject(xml, TConfig.class);
            System.out.println(tConfig.toString());
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public TConfig gettConfig() {
//        return tConfig;
//    }
//
//    public void settConfig(TConfig tConfig) {
//        this.tConfig = tConfig;
//    }
}
