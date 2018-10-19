package br.com.cafeperfeito.sidtmcafe.model.model;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.dao.TabProdutoDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.TabProdutoEstoqueDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmpresaVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabInformacaoReceitaFederalVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoEstoqueVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.DTF_DATA;

public class TabModel {

    static TreeTableView<TabProdutoVO> ttvProduto;
    static Label lblRegistrosLocalizados;
    static ObservableList<TabProdutoVO> produtoVOObservableList;
    static FilteredList<TabProdutoVO> produtoVOFilteredList;

    public TabModel() {
    }

    public static TreeTableView<TabProdutoVO> getTtvProduto() {
        return ttvProduto;
    }

    public static void setTtvProduto(TreeTableView<TabProdutoVO> ttvProduto) {
        TabModel.ttvProduto = ttvProduto;
    }

    public static Label getLblRegistrosLocalizados() {
        return lblRegistrosLocalizados;
    }

    public static void setLblRegistrosLocalizados(Label lblRegistrosLocalizados) {
        TabModel.lblRegistrosLocalizados = lblRegistrosLocalizados;
    }

    public static ObservableList<TabProdutoVO> getProdutoVOObservableList() {
        return produtoVOObservableList;
    }

    public static void setProdutoVOObservableList(ObservableList<TabProdutoVO> produtoVOObservableList) {
        TabModel.produtoVOObservableList = produtoVOObservableList;
    }

    public static FilteredList<TabProdutoVO> getProdutoVOFilteredList() {
        return produtoVOFilteredList;
    }

    public static void setProdutoVOFilteredList(FilteredList<TabProdutoVO> produtoVOFilteredList) {
        TabModel.produtoVOFilteredList = produtoVOFilteredList;
    }

    static TreeTableColumn<TabProdutoVO, String> colunaIdProduto;
    static TreeTableColumn<TabProdutoVO, String> colunaCodigo;
    static TreeTableColumn<TabProdutoVO, String> colunaDescricao;
    static TreeTableColumn<TabProdutoVO, String> colunaUndCom;
    static TreeTableColumn<TabProdutoVO, String> colunaPrecoFabrica;
    static TreeTableColumn<TabProdutoVO, String> colunaPrecoVenda;
    static TreeTableColumn<TabProdutoVO, Integer> colunaQtdEstoque;
    static TreeTableColumn<TabProdutoVO, String> colunaSituacaoSistema;
    static TreeTableColumn<TabProdutoVO, Integer> colunaVarejo;
    static TreeTableColumn<TabProdutoVO, String> colunaLote;
    static TreeTableColumn<TabProdutoVO, String> colunaValidade;

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

    public static TreeTableColumn<TabProdutoVO, String> getColunaIdProduto() {
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

    public static TreeTableColumn<TabProdutoVO, String> getColunaLote() {
        return colunaLote;
    }

    public static void setColunaLote(TreeTableColumn<TabProdutoVO, String> colunaLote) {
        TabModel.colunaLote = colunaLote;
    }

    public static TreeTableColumn<TabProdutoVO, String> getColunaValidade() {
        return colunaValidade;
    }

    public static void setColunaValidade(TreeTableColumn<TabProdutoVO, String> colunaValidade) {
        TabModel.colunaValidade = colunaValidade;
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
            lblId.setPrefWidth(48);
            colunaIdProduto = new TreeTableColumn<TabProdutoVO, String>();
            colunaIdProduto.setGraphic(lblId);
            colunaIdProduto.setPrefWidth(48);
            colunaIdProduto.setStyle("-fx-alignment: center-right;");
            colunaIdProduto.setCellValueFactory(param -> {
                if (param.getValue().getValue().getId() == 0)
                    return new SimpleStringProperty("");
                return param.getValue().getValue().idProperty().asString();
            });

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
            colunaUndCom.setCellValueFactory(param -> {
                if (param.getValue().getValue().getSisUnidadeComercial_id() == 0)
                    return new SimpleStringProperty("");
                return param.getValue().getValue().getSisUnidadeComercialVO().siglaProperty();
            });

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
            colunaPrecoFabrica.setCellValueFactory(param -> new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getPrecoFabrica().setScale(2).toString(), 0, "moeda", 2)));

            Label lblPrecoCons = new Label("Preço Cons.");
            lblPrecoCons.setPrefWidth(90);
            colunaPrecoVenda = new TreeTableColumn<TabProdutoVO, String>();
            colunaPrecoVenda.setGraphic(lblPrecoCons);
            colunaPrecoVenda.setPrefWidth(90);
            colunaPrecoVenda.setStyle("-fx-alignment: center-right;");
            colunaPrecoVenda.setCellValueFactory(param -> new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getPrecoVenda().setScale(2).toString(), 0, "moeda", 2)));

            Label lblSituacaoSistema = new Label("Situação");
            lblSituacaoSistema.setPrefWidth(100);
            colunaSituacaoSistema = new TreeTableColumn<TabProdutoVO, String>();
            colunaSituacaoSistema.setGraphic(lblSituacaoSistema);
            colunaSituacaoSistema.setPrefWidth(100);
            colunaSituacaoSistema.setCellValueFactory(param -> {
                if (param.getValue().getValue().getSisSituacaoSistema_id() == 0)
                    return new SimpleStringProperty("");
                return param.getValue().getValue().getSisSituacaoSistemaVO().descricaoProperty();
            });

            Label lblEstoque = new Label("Estoque");
            lblEstoque.setPrefWidth(65);
            colunaQtdEstoque = new TreeTableColumn<TabProdutoVO, Integer>();
            colunaQtdEstoque.setGraphic(lblEstoque);
            colunaQtdEstoque.setPrefWidth(65);
            colunaQtdEstoque.setStyle("-fx-alignment: center-right;");
            colunaQtdEstoque.setCellValueFactory(param -> param.getValue().getValue().estoqueProperty().asObject());

            Label lblLote = new Label("Lote");
            lblLote.setPrefWidth(105);
            colunaLote = new TreeTableColumn<TabProdutoVO, String>();
            colunaLote.setGraphic(lblLote);
            colunaLote.setPrefWidth(105);
            colunaLote.setStyle("-fx-alignment: center;");
            colunaLote.setCellValueFactory(param -> {
                if (param.getValue().getValue().loteProperty() == null)
                    return new SimpleStringProperty("");
                return param.getValue().getValue().loteProperty();
            });

            Label lblValidade = new Label("Validade");
            lblValidade.setPrefWidth(105);
            colunaValidade = new TreeTableColumn<TabProdutoVO, String>();
            colunaValidade.setGraphic(lblValidade);
            colunaValidade.setPrefWidth(105);
            colunaValidade.setStyle("-fx-alignment: center-right;");
            colunaValidade.setCellValueFactory(param -> {
                if (param.getValue().getValue().getValidade() == null)
                    return new SimpleStringProperty("");
                return new SimpleStringProperty(DTF_DATA.format(param.getValue().getValue().getValidade().toLocalDateTime().toLocalDate()));
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void escutaListaProduto() {
        ttvProduto.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER)
                    event.consume();
            }
        });
        produtoVOFilteredList.addListener((ListChangeListener<TabProdutoVO>) c -> {
            preencherTabelaProduto();
        });
        ttvProduto.setRowFactory(param -> {
            TreeTableRow<TabProdutoVO> row = new TreeTableRow<TabProdutoVO>() {
                @Override
                protected void updateItem(TabProdutoVO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        getStyleClass().remove("produto-estoque");
                    } else if (item.getDescricao().equals("")) {
                        if (!getStyleClass().contains("produto-estoque"))
                            getStyleClass().add("produto-estoque");
                    } else {
                        getStyleClass().remove("produto-estoque");
                    }
                }
            };
            return row;
        });

    }


    public static void escutaListaEmpresa() {
        ttvProduto.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER)
                    event.consume();
            }
        });
        produtoVOFilteredList.addListener((ListChangeListener<TabProdutoVO>) c -> {
            preencherTabelaProduto();
        });
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
                    return new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getCnpj(), 0, "cnpj", 0));
                else
                    return new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().getCnpj(), 0, "cpf", 0));
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
                    return new SimpleStringProperty(ServiceFormatarDado.getValorFormatado(param.getValue().getValue().ieProperty().getValue(), 0, "ie" + param.getValue().getValue().getTabEnderecoVOList().get(0).getSisMunicipioVO().getUfVO().getSigla(), 0));
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

    public static void preencherTabelaProduto() {
        atualizaRegistrosProdutos();
        //final TreeItem<TabProdutoVO> root = new RecursiveTreeItem<TabProdutoVO>(produtoVOFilteredList, RecursiveTreeObject::getChildren);
        TreeItem<TabProdutoVO> root = new TreeItem<TabProdutoVO>();
        for (TabProdutoVO prod : produtoVOFilteredList) {
            final int[] estoqTot = {0};
            TreeItem<TabProdutoVO> itemProd = new TreeItem<TabProdutoVO>(prod);
            new TabProdutoEstoqueDAO().getTabProdutoEstoqueVOList(prod.getId()).stream()
                    .forEach(estqProd -> {
                        itemProd.getChildren().add(new TreeItem<TabProdutoVO>(new TabProdutoVO(estqProd.getId(), estqProd.getQtd(), estqProd.getLote(), estqProd.getValidade())));
                        estoqTot[0] += estqProd.getQtd();
                    });
            itemProd.getValue().setEstoque(estoqTot[0]);
            root.getChildren().add(itemProd);
        }

        ttvProduto.getColumns().setAll(TabModel.getColunaIdProduto(), TabModel.getColunaCodigo(),
                TabModel.getColunaDescricao(), TabModel.getColunaUndCom(), TabModel.getColunaVarejo(),
                TabModel.getColunaPrecoFabrica(), TabModel.getColunaPrecoVenda(),
                TabModel.getColunaSituacaoSistema(), TabModel.getColunaQtdEstoque(),
                TabModel.getColunaLote(), TabModel.getColunaValidade());
        ttvProduto.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ttvProduto.setRoot(root);
        ttvProduto.setShowRoot(false);
    }

    public static void pesquisaProduto(String busca) {
        produtoVOFilteredList.setPredicate(produto -> {
            if (produto.getCodigo().toLowerCase().contains(busca)) return true;
            if (produto.getDescricao().toLowerCase().contains(busca)) return true;
            if (produto.getNcm().toLowerCase().contains(busca)) return true;
            if (produto.getCest().toLowerCase().contains(busca)) return true;
            if (produto.getCodBarraVOList().stream()
                    .filter(codBarra -> codBarra.getCodBarra().toLowerCase().contains(busca))
                    .findFirst().orElse(null) != null) return true;
            return false;
        });
    }

    public static void atualizaRegistrosProdutos() {
        int qtd = produtoVOFilteredList.size();
        lblRegistrosLocalizados.setText(String.format("%d registro%s localizado%s.", qtd,
                qtd > 1 ? "s" : "", qtd > 1 ? "s" : ""));
    }

}
