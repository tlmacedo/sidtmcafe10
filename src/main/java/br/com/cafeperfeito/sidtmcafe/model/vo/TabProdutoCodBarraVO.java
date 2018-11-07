package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class TabProdutoCodBarraVO extends RecursiveTreeObject<TabProdutoCodBarraVO> {

    IntegerProperty id;
    StringProperty codBarra;
    Image imgCodBarra;

    public TabProdutoCodBarraVO() {
    }

    public TabProdutoCodBarraVO(String codigoBarra, Image image) {
        this.id = new SimpleIntegerProperty(0);
        this.codBarra = new SimpleStringProperty(codigoBarra);
        this.setImgCodBarra(image);
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

    public String getCodBarra() {
        return codBarraProperty().get();
    }

    public StringProperty codBarraProperty() {
        if (codBarra == null) codBarra = new SimpleStringProperty("");
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        codBarraProperty().set(codBarra);
    }

    public Image getImgCodBarra() {
        return imgCodBarra;
    }

    public void setImgCodBarra(Image imgCodBarra) {
        this.imgCodBarra = imgCodBarra;
    }

    @Override
    public String toString() {
        return codBarraProperty().get();
    }
}
