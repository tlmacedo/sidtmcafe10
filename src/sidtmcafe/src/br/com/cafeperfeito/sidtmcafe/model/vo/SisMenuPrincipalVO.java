package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SisMenuPrincipalVO extends RecursiveTreeObject<SisMenuPrincipalVO> {

    IntegerProperty id, filho_id, tabPane;
    StringProperty descricao, tituloTab, icoMenu, teclaAtalho;

    public SisMenuPrincipalVO() {
    }

    public int getId() {
        return idProperty().get();
    }

    public IntegerProperty idProperty() {
        if (id == null) id = new SimpleIntegerProperty(0);
        return id;
    }

    public void setId(int id) {
        idProperty().set(id);
    }

    public int getFilho_id() {
        return filho_idProperty().get();
    }

    public IntegerProperty filho_idProperty() {
        if (filho_id == null) filho_id = new SimpleIntegerProperty(0);
        return filho_id;
    }

    public void setFilho_id(int filho_id) {
        filho_idProperty().set(filho_id);
    }

    public int getTabPane() {
        return tabPaneProperty().get();
    }

    public IntegerProperty tabPaneProperty() {
        if (tabPane == null) tabPane = new SimpleIntegerProperty(0);
        return tabPane;
    }

    public void setTabPane(int tabPane) {
        tabPaneProperty().set(tabPane);
    }

    public String getDescricao() {
        return descricaoProperty().get();
    }

    public StringProperty descricaoProperty() {
        if (descricao == null) descricao = new SimpleStringProperty("");
        return descricao;
    }

    public void setDescricao(String descricao) {
        descricaoProperty().set(descricao);
    }

    public String getTituloTab() {
        return tituloTabProperty().get();
    }

    public StringProperty tituloTabProperty() {
        if (tituloTab == null) tituloTab = new SimpleStringProperty("");
        return tituloTab;
    }

    public void setTituloTab(String tituloTab) {
        tituloTabProperty().set(tituloTab);
    }

    public String getIcoMenu() {
        return icoMenuProperty().get();
    }

    public StringProperty icoMenuProperty() {
        if (icoMenu == null) icoMenu = new SimpleStringProperty("");
        return icoMenu;
    }

    public void setIcoMenu(String icoMenu) {
        icoMenuProperty().set(icoMenu);
    }

    public String getTeclaAtalho() {
        return teclaAtalhoProperty().get();
    }

    public StringProperty teclaAtalhoProperty() {
        if (teclaAtalho == null) teclaAtalho = new SimpleStringProperty("");
        return teclaAtalho;
    }

    public void setTeclaAtalho(String teclaAtalho) {
        teclaAtalhoProperty().set(teclaAtalho);
    }

    @Override
    public String toString() {
        return tituloTabProperty().get();
    }
}
