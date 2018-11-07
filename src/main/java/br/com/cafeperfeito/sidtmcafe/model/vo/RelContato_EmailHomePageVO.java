package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RelContato_EmailHomePageVO extends RecursiveTreeObject<RelContato_EmailHomePageVO> {

    IntegerProperty tabContato_id, tabEmailHomePage_id;

    public RelContato_EmailHomePageVO() {
    }

    public int getTabContato_id() {
        return tabContato_idProperty().get();
    }

    public IntegerProperty tabContato_idProperty() {
        if (tabContato_id == null) tabContato_id = new SimpleIntegerProperty(0);
        return tabContato_id;
    }

    public void setTabContato_id(int tabContato_id) {
        tabContato_idProperty().set(tabContato_id);
    }

    public int getTabEmailHomePage_id() {
        return tabEmailHomePage_idProperty().get();
    }

    public IntegerProperty tabEmailHomePage_idProperty() {
        if (tabEmailHomePage_id == null) tabEmailHomePage_id = new SimpleIntegerProperty(0);
        return tabEmailHomePage_id;
    }

    public void setTabEmailHomePage_id(int tabEmailHomePage_id) {
        tabEmailHomePage_idProperty().set(tabEmailHomePage_id);
    }

}
