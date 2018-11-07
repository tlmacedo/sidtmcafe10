package br.com.cafeperfeito.sidtmcafe.model.vo;

//import br.com.cafeperfeito.sidtmCafe.service.FormatarDado;

import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TabTelefoneVO extends RecursiveTreeObject<TabTelefoneVO> {

    SisTelefoneOperadoraVO sisTelefoneOperadoraVO;

    IntegerProperty id, sisTelefoneOperadora_id;
    StringProperty descricao;

    public TabTelefoneVO() {

    }

    public TabTelefoneVO(String telefone, SisTelefoneOperadoraVO operadoraVO) {
        this.sisTelefoneOperadoraVO = operadoraVO;
        this.id = new SimpleIntegerProperty(0);
        this.sisTelefoneOperadora_id = new SimpleIntegerProperty(operadoraVO.getId());
        this.descricao = new SimpleStringProperty(telefone);
    }

    public SisTelefoneOperadoraVO getSisTelefoneOperadoraVO() {
        return sisTelefoneOperadoraVO;
    }

    public void setSisTelefoneOperadoraVO(SisTelefoneOperadoraVO sisTelefoneOperadoraVO) {
        this.sisTelefoneOperadoraVO = sisTelefoneOperadoraVO;
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

    public int getSisTelefoneOperadora_id() {
        return sisTelefoneOperadora_idProperty().get();
    }

    public IntegerProperty sisTelefoneOperadora_idProperty() {
        if (sisTelefoneOperadora_id == null) sisTelefoneOperadora_id = new SimpleIntegerProperty(0);
        return sisTelefoneOperadora_id;
    }

    public void setSisTelefoneOperadora_id(int sisTelefoneOperadora_id) {
        sisTelefoneOperadora_idProperty().set(sisTelefoneOperadora_id);
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
        String telefone;
        if ((telefone = descricaoProperty().get().replaceAll("\\D", "")).length() > 0)
            if (sisTelefoneOperadoraVO != null) {
                boolean telFixo = (sisTelefoneOperadoraVO.getTipo() == 0 || (Integer.parseInt(telefone.substring(0, 1)) < 5));
                telefone = String.format("%s %s (%s)", ServiceFormatarDado.getValorFormatado(telefone, 0, telFixo ? "telefone" : "celular", 0),
                        telFixo ? "fixo" : "celular", sisTelefoneOperadoraVO.getDescricao());
            }
        return telefone;
    }

}
