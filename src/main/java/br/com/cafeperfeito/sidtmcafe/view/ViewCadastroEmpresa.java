package br.com.cafeperfeito.sidtmcafe.view;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;

public class ViewCadastroEmpresa implements Constants {

    static String tituloJanela;
    static Tab tab;

    public static String getTituloJanela() {
        return tituloJanela;
    }

    public static void setTituloJanela(String tituloJanela) {
        ViewCadastroEmpresa.tituloJanela = tituloJanela;
    }

    public static Tab getTab() {
        return tab;
    }

    public static void setTab(Tab tab) {
        ViewCadastroEmpresa.tab = tab;
    }

    @SuppressWarnings("Duplicates")
    public Tab openTabCadastroEmpresa(String tituloJanela) {
        setTituloJanela(tituloJanela);
        Parent parent;

        try {
            parent = FXMLLoader.load(getClass().getResource(FXML_CADASTRO_EMPRESA));
            parent.getStylesheets().setAll(getClass().getResource(SIS_CSS_STYLE_SHEETS).toString());

            setTab(new Tab(tituloJanela));
            getTab().setContent(parent);
            return getTab();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
