package br.com.cafeperfeito.sidtmcafe.view;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;

import java.io.IOException;

public class ViewCadastroEmpresa implements Constants {

    static String tituloJanela;

    public static String getTituloJanela() {
        return tituloJanela;
    }

    public static void setTituloJanela(String tituloJanela) {
        ViewCadastroEmpresa.tituloJanela = tituloJanela;
    }

    @SuppressWarnings("Duplicates")
    public Tab openTabCadastroEmpresa(String tituloJanela) {
        setTituloJanela(tituloJanela);
        Parent parent;
        Scene scene = null;

        try {
            parent = FXMLLoader.load(getClass().getResource(FXML_CADASTRO_EMPRESA));
            parent.getStylesheets().setAll(getClass().getResource(STYLE_SHEETS).toString());

            Tab tab = new Tab(tituloJanela);
            tab.setContent(parent);
            return tab;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
