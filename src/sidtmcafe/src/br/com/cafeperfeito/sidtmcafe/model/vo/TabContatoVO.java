package br.com.cafeperfeito.sidtmcafe.model.vo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class TabContatoVO extends RecursiveTreeObject<TabContatoVO> {

    SisCargoVO sisCargoVO;
    List<TabEmailHomePageVO> tabEmailHomePageVOList;
    List<TabTelefoneVO> tabTelefoneVOList;

    IntegerProperty id, sisCargo_id;
    StringProperty descricao;

    public TabContatoVO() {
    }

    public TabContatoVO(String contato, SisCargoVO cargoVO) {
        this.sisCargoVO = cargoVO;
        this.id = new SimpleIntegerProperty(0);
        this.sisCargo_id = new SimpleIntegerProperty(cargoVO.getId());
        this.descricao = new SimpleStringProperty(contato);
        this.tabEmailHomePageVOList = new ArrayList<>();
        this.tabTelefoneVOList = new ArrayList<>();
    }


    public SisCargoVO getSisCargoVO() {
        return sisCargoVO;
    }

    public void setSisCargoVO(SisCargoVO sisCargoVO) {
        this.sisCargoVO = sisCargoVO;
    }

    public List<TabEmailHomePageVO> getTabEmailHomePageVOList() {
        return tabEmailHomePageVOList;
    }

    public void setTabEmailHomePageVOList(List<TabEmailHomePageVO> tabEmailHomePageVOList) {
        this.tabEmailHomePageVOList = tabEmailHomePageVOList;
    }

    public List<TabTelefoneVO> getTabTelefoneVOList() {
        return tabTelefoneVOList;
    }

    public void setTabTelefoneVOList(List<TabTelefoneVO> tabTelefoneVOList) {
        this.tabTelefoneVOList = tabTelefoneVOList;
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

    public int getSisCargo_id() {
        return sisCargo_idProperty().get();
    }

    public IntegerProperty sisCargo_idProperty() {
        if (sisCargo_id == null) sisCargo_id = new SimpleIntegerProperty(0);
        return sisCargo_id;
    }

    public void setSisCargo_id(int sisCargo_id) {
        sisCargo_idProperty().set(sisCargo_id);
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

    @Override
    public String toString() {
        return descricaoProperty().get() + " (" + getSisCargoVO().getDescricao() + ")";
    }
}
