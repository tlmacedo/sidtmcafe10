package br.com.cafeperfeito.sidtmcafe.model.model;

import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.VBox;

public class TabModel {

    static TreeTableColumn<TabProdutoVO, Integer> colunaIdProduto;
    static TreeTableColumn<TabProdutoVO, String> colunaCodigo;
    static TreeTableColumn<TabProdutoVO, String> colunaDescricao;
    static TreeTableColumn<TabProdutoVO, String> colunaUndCom;
    static TreeTableColumn<TabProdutoVO, String> colunaPrecoFabrica;
    static TreeTableColumn<TabProdutoVO, String> colunaPrecoVenda;
    static TreeTableColumn<TabProdutoVO, Integer> colunaQtdEstoque;
    static TreeTableColumn<TabProdutoVO, String> colunaSituacaoSistema;
    static TreeTableColumn<TabProdutoVO, Integer> colunaVarejo;

    static TreeTableColumn<TabEmpresaVO, Integer> colunaIdEmpresa;
    static TreeTableColumn<TabEmpresaVO, String> colunaCnpj;
    static TreeTableColumn<TabEmpresaVO, String> colunaIe;
    static TreeTableColumn<TabEmpresaVO, String> colunaRazao;
    static TreeTableColumn<TabEmpresaVO, String> colunaFantasia;
    static TreeTableColumn<TabEmpresaVO, String> colunaEndereco;
    static TreeTableColumn<TabEmpresaVO, String> colunaEndLogradouro;
    static TreeTableColumn<TabEmpresaVO, String> colunaEndNumero;
    static TreeTableColumn<TabEmpresaVO, String> colunaEndComplemento;
    static TreeTableColumn<TabEmpresaVO, String> colunaEndBairro;
    static TreeTableColumn<TabEmpresaVO, String> colunaEndUFMunicipio;
    static TreeTableColumn<TabEmpresaVO, Boolean> colunaIsCliente;
    static TreeTableColumn<TabEmpresaVO, Boolean> colunaIsFornecedor;
    static TreeTableColumn<TabEmpresaVO, Boolean> colunaIsTransportadora;
    static TreeTableColumn<TabInformacaoReceitaFederalVO, String> colunaQsaKey;
    static TreeTableColumn<TabInformacaoReceitaFederalVO, String> colunaQsaValue;

    public static TreeTableColumn<TabProdutoVO, Integer> getColunaIdProduto() {
        return colunaIdProduto;
    }

    public static TreeTableColumn<TabProdutoVO, String> getColunaCodigo() {
        return colunaCodigo;
    }

    public static TreeTableColumn<TabProdutoVO, String> getColunaDescricao() {
        return colunaDescricao;
    }

    public static TreeTableColumn<TabProdutoVO, String> getColunaUndCom() {
        return colunaUndCom;
    }

    public static TreeTableColumn<TabProdutoVO, String> getColunaPrecoFabrica() {
        return colunaPrecoFabrica;
    }

    public static TreeTableColumn<TabProdutoVO, String> getColunaPrecoVenda() {
        return colunaPrecoVenda;
    }

    public static TreeTableColumn<TabProdutoVO, Integer> getColunaQtdEstoque() {
        return colunaQtdEstoque;
    }

    public static TreeTableColumn<TabProdutoVO, String> getColunaSituacaoSistema() {
        return colunaSituacaoSistema;
    }

    public static TreeTableColumn<TabProdutoVO, Integer> getColunaVarejo() {
        return colunaVarejo;
    }


    public static TreeTableColumn<TabEmpresaVO, Integer> getColunaIdEmpresa() {
        return colunaIdEmpresa;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaCnpj() {
        return colunaCnpj;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaIe() {
        return colunaIe;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaRazao() {
        return colunaRazao;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaFantasia() {
        return colunaFantasia;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaEndereco() {
        return colunaEndereco;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaEndLogradouro() {
        return colunaEndLogradouro;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaEndNumero() {
        return colunaEndNumero;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaEndComplemento() {
        return colunaEndComplemento;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaEndBairro() {
        return colunaEndBairro;
    }

    public static TreeTableColumn<TabEmpresaVO, String> getColunaEndUFMunicipio() {
        return colunaEndUFMunicipio;
    }

    public static TreeTableColumn<TabEmpresaVO, Boolean> getColunaIsCliente() {
        return colunaIsCliente;
    }

    public static TreeTableColumn<TabEmpresaVO, Boolean> getColunaIsFornecedor() {
        return colunaIsFornecedor;
    }

    public static TreeTableColumn<TabEmpresaVO, Boolean> getColunaIsTransportadora() {
        return colunaIsTransportadora;
    }

    public static void tabelaProduto() {
        try {
            Label lblId = new Label("id");
            lblId.setPrefWidth(28);
            colunaIdProduto = new TreeTableColumn<TabProdutoVO, Integer>();
            colunaIdProduto.setGraphic(lblId);
            colunaIdProduto.setPrefWidth(28);
            colunaIdProduto.setStyle("-fx-alignment: center-right;");
            colunaIdProduto.setCellValueFactory(param -> param.getValue().getValue().idProperty().asObject());

            Label lblCodigo = new Label("Código");
            lblCodigo.setPrefWidth(60);
            colunaCodigo = new TreeTableColumn<TabProdutoVO, String>();
            colunaCodigo.setGraphic(lblCodigo);
            colunaCodigo.setPrefWidth(60);
            colunaCodigo.setStyle("-fx-alignment: center-right;");
            colunaCodigo.setCellValueFactory(param -> param.getValue().getValue().codigoProperty());

            Label lblDescricao = new Label("Descrição");
            lblDescricao.setPrefWidth(350);
            colunaDescricao = new TreeTableColumn<TabProdutoVO, String>();
            colunaDescricao.setGraphic(lblDescricao);
            colunaDescricao.setPrefWidth(350);
            colunaDescricao.setCellValueFactory(param -> param.getValue().getValue().descricaoProperty());

            Label lblUndComercial = new Label("Und Com");
            lblUndComercial.setPrefWidth(70);
            colunaUndCom = new TreeTableColumn<TabProdutoVO, String>();
            colunaUndCom.setGraphic(lblUndComercial);
            colunaUndCom.setPrefWidth(70);
            colunaUndCom.setCellValueFactory(param -> param.getValue().getValue().getSisUnidadeComercialVO().siglaProperty());

            Label lblVarejo = new Label("Varejo");
            lblVarejo.setPrefWidth(50);
            colunaVarejo = new TreeTableColumn<TabProdutoVO, Integer>();
            colunaVarejo.setGraphic(lblVarejo);
            colunaVarejo.setPrefWidth(50);
            colunaVarejo.setStyle("-fx-alignment: center-right;");
            colunaVarejo.setCellValueFactory(param -> param.getValue().getValue().varejoProperty().asObject());

            Label lblPrecoFab = new Label("Preço Fab.");
            lblPrecoFab.setPrefWidth(90);
            colunaPrecoFabrica = new TreeTableColumn<TabProdutoVO, String>();
            colunaPrecoFabrica.setGraphic(lblPrecoFab);
            colunaPrecoFabrica.setPrefWidth(90);
            colunaPrecoFabrica.setStyle("-fx-alignment: center-right;");
            colunaPrecoFabrica.setCellValueFactory(param -> new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getPrecoFabrica().setScale(2).toString(), "moeda2")));

            Label lblPrecoCons = new Label("Preço Cons.");
            lblPrecoCons.setPrefWidth(90);
            colunaPrecoVenda = new TreeTableColumn<TabProdutoVO, String>();
            colunaPrecoVenda.setGraphic(lblPrecoCons);
            colunaPrecoVenda.setPrefWidth(90);
            colunaPrecoVenda.setStyle("-fx-alignment: center-right;");
            colunaPrecoVenda.setCellValueFactory(param -> new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getPrecoVenda().setScale(2).toString(), "moeda2")));

            Label lblSituacaoSistema = new Label("Situação");
            lblSituacaoSistema.setPrefWidth(100);
            colunaSituacaoSistema = new TreeTableColumn<TabProdutoVO, String>();
            colunaSituacaoSistema.setGraphic(lblSituacaoSistema);
            colunaSituacaoSistema.setPrefWidth(100);
            colunaSituacaoSistema.setCellValueFactory(param -> param.getValue().getValue().getSisSituacaoSistemaVO().descricaoProperty());

            Label lblEstoque = new Label("Estoque");
            lblEstoque.setPrefWidth(65);
            colunaQtdEstoque = new TreeTableColumn<TabProdutoVO, Integer>();
            colunaQtdEstoque.setGraphic(lblEstoque);
            colunaQtdEstoque.setPrefWidth(65);
//            colunaQtdEstoque.setStyle("-fx-alignment: center-right;");
//            colunaQtdEstoque.setCellValueFactory(new SimpleIntegerProperty(0));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void tabelaEmpresa() {
        try {
            Label lblId = new Label("id");
            lblId.setPrefWidth(28);
            colunaIdEmpresa = new TreeTableColumn<TabEmpresaVO, Integer>();
            colunaIdEmpresa.setGraphic(lblId);
            colunaIdEmpresa.setPrefWidth(28);
            colunaIdEmpresa.setStyle("-fx-alignment: center-right;");
            colunaIdEmpresa.setCellValueFactory(param -> param.getValue().getValue().idProperty().asObject());

            Label lblCnpj = new Label("C.N.P.J / C.P.F.");
            lblCnpj.setPrefWidth(110);
            colunaCnpj = new TreeTableColumn<TabEmpresaVO, String>();
            colunaCnpj.setGraphic(lblCnpj);
            colunaCnpj.setPrefWidth(110);
            colunaCnpj.setStyle("-fx-alignment: center-right;");

            colunaCnpj.setCellValueFactory(param -> {
                if (param.getValue().getValue().isIsEmpresa())
                    return new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getCnpj(), "cnpj"));
                else
                    return new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getCnpj(), "cpf"));
            });

            Label lblIe = new Label(("IE / RG"));
            lblIe.setPrefWidth(90);
            colunaIe = new TreeTableColumn<TabEmpresaVO, String>();
            colunaIe.setGraphic(lblIe);
            colunaIe.setPrefWidth(90);
            colunaIe.setStyle("-fx-alignment: center-right;");
            colunaIe.setCellValueFactory(param -> {
                if (param.getValue().getValue().isIeIsento())
                    return new SimpleStringProperty("Isento");
                if (param.getValue().getValue().getTabEnderecoVOList() != null)
                    return new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().ieProperty().getValue(), "ie" + param.getValue().getValue().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla()));
                return param.getValue().getValue().ieProperty();
            });

            Label lblRazao = new Label("Razão / Nome");
            lblRazao.setPrefWidth(250);
            colunaRazao = new TreeTableColumn<TabEmpresaVO, String>();
            colunaRazao.setGraphic(lblRazao);
            colunaRazao.setPrefWidth(250);
            colunaRazao.setCellValueFactory(param -> param.getValue().getValue().razaoProperty());

            Label lblFantasia = new Label("Fantasia / Apelido");
            lblFantasia.setPrefWidth(150);
            colunaFantasia = new TreeTableColumn<TabEmpresaVO, String>();
            colunaFantasia.setGraphic(lblFantasia);
            colunaFantasia.setPrefWidth(150);
            colunaFantasia.setCellValueFactory(param -> param.getValue().getValue().fantasiaProperty());

            colunaEndereco = new TreeTableColumn<TabEmpresaVO, String>("Endereço");
            colunaEndereco.setStyle("-fx-alignment: center;");

            Label lblEndLogradouro = new Label("Logradouro");
            lblEndLogradouro.setPrefWidth(170);
            colunaEndLogradouro = new TreeTableColumn<TabEmpresaVO, String>();
            colunaEndLogradouro.setGraphic(lblEndLogradouro);
            colunaEndLogradouro.setPrefWidth(170);
            colunaEndLogradouro.setCellValueFactory(param -> {
                if (param.getValue().getValue().getTabEnderecoVOList() != null)
                    return param.getValue().getValue().getTabEnderecoVOList().get(0).logradouroProperty();
                return new SimpleStringProperty("");
            });

            Label lblEndNumero = new Label("Número");
            lblEndNumero.setPrefWidth(40);
            colunaEndNumero = new TreeTableColumn<TabEmpresaVO, String>();
            colunaEndNumero.setGraphic(lblEndNumero);
            colunaEndNumero.setPrefWidth(40);
            colunaEndNumero.setStyle("-fx-alignment: center-right;");
            colunaEndNumero.setCellValueFactory(param -> {
                if (param.getValue().getValue().getTabEnderecoVOList() != null)
                    return param.getValue().getValue().getTabEnderecoVOList().get(0).numeroProperty();
                return new SimpleStringProperty("");
            });

            Label lblEndComplemento = new Label("Complemento");
            lblEndComplemento.setPrefWidth(150);
            colunaEndComplemento = new TreeTableColumn<TabEmpresaVO, String>();
            colunaEndComplemento.setGraphic(lblEndComplemento);
            colunaEndComplemento.setPrefWidth(150);
            colunaEndComplemento.setCellValueFactory(param -> {
                if (param.getValue().getValue().getTabEnderecoVOList() != null)
                    if (param.getValue().getValue().getTabEnderecoVOList().get(0).complementoProperty() != null)
                        return param.getValue().getValue().getTabEnderecoVOList().get(0).complementoProperty();
                return new SimpleStringProperty("");
            });

            Label lblEndBairro = new Label("Bairro");
            lblEndBairro.setPrefWidth(95);
            colunaEndBairro = new TreeTableColumn<TabEmpresaVO, String>();
            colunaEndBairro.setGraphic(lblEndBairro);
            colunaEndBairro.setPrefWidth(95);
            colunaEndBairro.setCellValueFactory(param -> {
                if (param.getValue().getValue().getTabEnderecoVOList() != null)
                    if (param.getValue().getValue().getTabEnderecoVOList().get(0).bairroProperty() != null)
                        return param.getValue().getValue().getTabEnderecoVOList().get(0).bairroProperty();
                return new SimpleStringProperty("");
            });

            Label lblEndUFMunicipio = new Label("UF - Cidade");
            lblEndUFMunicipio.setPrefWidth(75);
            colunaEndUFMunicipio = new TreeTableColumn<TabEmpresaVO, String>();
            colunaEndUFMunicipio.setGraphic(lblEndUFMunicipio);
            colunaEndUFMunicipio.setPrefWidth(75);
            colunaEndUFMunicipio.setCellValueFactory(param -> {
                if (param.getValue().getValue().getTabEnderecoVOList() != null)
                    return new SimpleStringProperty(
                            param.getValue().getValue().getTabEnderecoVOList().get(0).getSisMunicipioVO().descricaoProperty() + " - " +
                                    param.getValue().getValue().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().siglaProperty());
                return new SimpleStringProperty("");
            });

            colunaEndereco.getColumns().addAll(colunaEndLogradouro, colunaEndNumero,
                    colunaEndComplemento, colunaEndBairro);

            VBox vBoxIsCliente = new VBox();
            Label lblImgIsCliente = new Label();
            lblImgIsCliente.getStyleClass().add("lbl_ico_cliente");
            lblImgIsCliente.setPrefSize(24, 24);
            Label lblIsCliente = new Label("Cliente");
            vBoxIsCliente.setAlignment(Pos.CENTER);
            vBoxIsCliente.getChildren().addAll(lblImgIsCliente, lblIsCliente);
            colunaIsCliente = new TreeTableColumn<TabEmpresaVO, Boolean>();
            colunaIsCliente.setPrefWidth(55);
            colunaIsCliente.setGraphic(vBoxIsCliente);
            colunaIsCliente.setCellValueFactory(param -> param.getValue().getValue().isClienteProperty());

            VBox vBoxIsFornecedor = new VBox();
            Label lblImgIsFornecedor = new Label();
            lblImgIsFornecedor.getStyleClass().add("lbl_ico_fornecedor");
            lblImgIsFornecedor.setPrefSize(24, 24);
            Label lblIsFornecedor = new Label("Forn.");
            vBoxIsFornecedor.setAlignment(Pos.CENTER);
            vBoxIsFornecedor.getChildren().addAll(lblImgIsFornecedor, lblIsFornecedor);
            colunaIsFornecedor = new TreeTableColumn<TabEmpresaVO, Boolean>();
            colunaIsFornecedor.setPrefWidth(55);
            colunaIsFornecedor.setGraphic(vBoxIsFornecedor);
            colunaIsFornecedor.setCellValueFactory(param -> param.getValue().getValue().isFornecedorProperty());

            VBox vBoxIsTransportadora = new VBox();
            Label lblImgIsTransportadora = new Label();
            lblImgIsTransportadora.getStyleClass().add("lbl_ico_transportadora");
            lblImgIsTransportadora.setPrefSize(24, 24);
            Label lblIsTransportadora = new Label("Transp.");
            vBoxIsTransportadora.setAlignment(Pos.CENTER);
            vBoxIsTransportadora.getChildren().addAll(lblImgIsTransportadora, lblIsTransportadora);
            colunaIsTransportadora = new TreeTableColumn<TabEmpresaVO, Boolean>();
            colunaIsTransportadora.setPrefWidth(55);
            colunaIsTransportadora.setGraphic(vBoxIsTransportadora);
            colunaIsTransportadora.setCellValueFactory(param -> param.getValue().getValue().isTransportadoraProperty());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public static void tabelaQsaReceita() {
//        try {
//            Label lblQsaKey = new Label("Item");
//            lblQsaKey.setPrefWidth(100);
//            colunaQsaKey = new TreeTableColumn<TabEmpresaReceitaFederalVO, String>();
//            colunaQsaKey.setGraphic(lblQsaKey);
//            colunaQsaKey.setPrefWidth(100);
//            colunaQsaKey.setCellValueFactory(param -> {
//                return param.getValue().getValue().str_KeyProperty();
//            });
//
//            Label lblQsaValue = new Label("Detalhe");
//            lblQsaValue.setPrefWidth(250);
//            colunaQsaValue = new TreeTableColumn<TabEmpresaReceitaFederalVO, String>();
//            colunaQsaValue.setGraphic(lblQsaValue);
//            colunaQsaValue.setPrefWidth(250);
//            colunaQsaValue.setCellValueFactory(param -> {
//                return param.getValue().getValue().str_ValueProperty();
//            });
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }


}
