package br.com.cafeperfeito.sidtmcafe.view;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;

public class ViewCadastroProduto implements Constants {
    static String tituloJanela;
    static Tab tabCadastroProduto;

    public static String getTituloJanela() {
        return tituloJanela;
    }

    public static void setTituloJanela(String tituloJanela) {
        ViewCadastroProduto.tituloJanela = tituloJanela;
    }

    public static Tab getTabCadastroProduto() {
        return tabCadastroProduto;
    }

    public static void setTabCadastroProduto(Tab tabCadastroProduto) {
        ViewCadastroProduto.tabCadastroProduto = tabCadastroProduto;
    }

    @SuppressWarnings("Duplicates")
    public Tab openTabCadastroProduto(String tituloJanela) {
        setTituloJanela(tituloJanela);
        Parent parent;

        try {
            parent = FXMLLoader.load(getClass().getResource(FXML_CADASTRO_PRODUTO));
            parent.getStylesheets().setAll(getClass().getResource(STYLE_SHEETS).toString());

            setTabCadastroProduto(new Tab(tituloJanela));
            tabCadastroProduto.setContent(parent);
            return tabCadastroProduto;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
