package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoCodBarraVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import br.com.cafeperfeito.sidtmcafe.service.ServiceImage;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoDAO extends ServiceBuscaBancoDados {

    TabProdutoVO tabProdutoVO = null;
    List<TabProdutoVO> tabProdutoVOList = null;

    public TabProdutoVO getTabProdutoVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabProduto WHERE id = ? ");
        if (tabProdutoVO != null)
            addObjetosPesquisa(tabProdutoVO);
        return tabProdutoVO;
    }

    public TabProdutoVO getTabProdutoVO(String codigo) {
        addNewParametro(new Pair<>("String", codigo));
        getResultSet("SELECT * FROM tabProduto WHERE codigo = ? ");
        if (tabProdutoVO != null)
            addObjetosPesquisa(tabProdutoVO);
        return tabProdutoVO;
    }

    public TabProdutoVO getTabProduto_CodBarraVO(String codBarra) {
        TabProdutoCodBarraVO codBarraVO;
        if ((codBarraVO = new TabProdutoCodBarraDAO().getTabProdutoCodBarraVO(codBarra)) == null) return null;
        int produto_id = new RelProduto_CodBarraDAO().getRelProduto_CodBarraVO(0, codBarraVO.getId()).getTabProduto_id();
        if (produto_id == 0) return null;
        addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
        getResultSet("SELECT * FROM tabProduto WHERE id = ? ");
        if (tabProdutoVO != null)
            addObjetosPesquisa(tabProdutoVO);
        return tabProdutoVO;
    }

    public List<TabProdutoVO> getTabProdutoVOList() {
        tabProdutoVOList = new ArrayList<>();
        getResultSet("SELECT * FROM tabProduto ");
        if (tabProdutoVO != null)
            for (TabProdutoVO produtoVO : tabProdutoVOList)
                addObjetosPesquisa(produtoVO);
        return tabProdutoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY descricao ");
        try {
            while (rs.next()) {
                tabProdutoVO = new TabProdutoVO();
                tabProdutoVO.setId(rs.getInt("id"));
                tabProdutoVO.setCodigo(rs.getString("codigo"));
                tabProdutoVO.setDescricao(rs.getString("descricao"));
                tabProdutoVO.setPeso(rs.getBigDecimal("peso"));
                tabProdutoVO.setSisUnidadeComercial_id(rs.getInt("sisUnidadeComercial_id"));
                tabProdutoVO.setSisSituacaoSistema_id(rs.getInt("sisSituacaoSistema_id"));
                tabProdutoVO.setPrecoFabrica(rs.getBigDecimal("precoFabrica"));
                tabProdutoVO.setPrecoVenda(rs.getBigDecimal("precoVenda"));
                tabProdutoVO.setVarejo(rs.getInt("varejo"));
                tabProdutoVO.setPrecoUltimoImpostoSefaz(rs.getBigDecimal("precoUltimoImpostoSEFAZ"));
                tabProdutoVO.setPrecoUltimoFrete(rs.getBigDecimal("precoUltimoFrete"));
                tabProdutoVO.setComissao(rs.getBigDecimal("comissao"));
                tabProdutoVO.setNcm(rs.getString("ncm"));
                tabProdutoVO.setCest(rs.getString("cest"));
                tabProdutoVO.setFiscalCSTOrigem_id(rs.getInt("fiscalCstOrigem_id"));
                tabProdutoVO.setFiscalICMS_id(rs.getInt("fiscalIcms_id"));
                tabProdutoVO.setFiscalPIS_id(rs.getInt("fiscalPis_id"));
                tabProdutoVO.setFiscalCOFINS_id(rs.getInt("fiscalCofins_id"));
                tabProdutoVO.setNfeGenero(rs.getString("nfeGenero"));
                tabProdutoVO.setUsuarioCadastro_id(rs.getInt("usuarioCadastro_id"));
                tabProdutoVO.setDataCadastro(rs.getTimestamp("dataCadastro"));
                tabProdutoVO.setUsuarioAtualizacao_id(rs.getInt("usuarioAtualizacao_id"));
                tabProdutoVO.setDataAtualizacao(rs.getTimestamp("dataAtualizacao"));
                tabProdutoVO.setImgProduto(ServiceImage.getImageFromInputStream(rs.getBinaryStream("imgProduto")));
                if (tabProdutoVOList != null) tabProdutoVOList.add(tabProdutoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabProdutoVO produto) {
        produto.setSisUnidadeComercialVO(new SisUnidadeComercialDAO().getSisUnidadeComercialVO(produto.getSisUnidadeComercial_id()));

        produto.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(produto.getSisSituacaoSistema_id()));

        produto.setFiscalCstOrigemVO(new FiscalCstOrigemDAO().getFiscalCstOrigemVO(produto.getFiscalCSTOrigem_id()));

        produto.setFiscalIcmsVO(new FiscalIcmsDAO().getFiscalIcmsVO((produto.getFiscalICMS_id())));

        produto.setFiscalPisVO(new FiscalPisCofinsDAO().getFiscalPisCofinsVO(produto.getFiscalPIS_id()));

        produto.setFiscalCofinsVO(new FiscalPisCofinsDAO().getFiscalPisCofinsVO(produto.getFiscalCOFINS_id()));

        produto.setUsuarioCadastroVO(new TabColaboradorDAO().getTabColaboradorVO(produto.getUsuarioCadastro_id(), false));

        produto.setUsuarioAtualizacaoVO(new TabColaboradorDAO().getTabColaboradorVO(produto.getUsuarioAtualizacao_id(), false));

        List<TabProdutoCodBarraVO> codBarraVOList = new ArrayList<>();
        new RelProduto_CodBarraDAO().getRelProduto_CodBarraVOList(produto.getId()).stream()
                .forEach(relProduto_codBarraVO -> {
                    codBarraVOList.add(new TabProdutoCodBarraDAO().getTabProdutoCodBarraVO(relProduto_codBarraVO.getTabProduto_CodBarra_id()));
                });
        produto.setCodBarraVOList(codBarraVOList);
    }

    public void updateTabProdutoVO(Connection conn, TabProdutoVO produtoVO) throws SQLException {
        String comandoSql = "UPDATE tabProduto SET " +
                "codigo = ?, " +                     //1
                "descricao = ?, " +                  //2
                "peso = ?, " +                       //3
                "sisUnidadeComercial_id = ?, " +     //4
                "sisSituacaoSistema_id = ?, " +      //5
                "precoFabrica = ?, " +               //6
                "precoVenda = ?, " +                 //7
                "varejo = ?, " +                     //8
                "precoUltimoImpostoSEFAZ = ?, " +    //9
                "precoUltimoFrete = ?, " +           //10
                "comissao = ?, " +                   //11
                "ncm = ?, " +                        //13
                "cest = ?, " +                       //14
                "fiscalCSTOrigem_id = ?, " +         //15
                "fiscalICMS_id = ?, " +              //16
                "fiscalPIS_id = ?, " +               //17
                "fiscalCOFINS_id = ?, " +            //18
                "nfeGenero = ?, " +                  //19
                "usuarioAtualizacao_id = ?, " +      //20
                "imgProduto = ? " +                  //21
                "WHERE id = ? ";                     //22
        addNewParametro(new Pair<>("int", String.valueOf(produtoVO.getCodigo())));
        addParametro(new Pair<>("String", produtoVO.getDescricao()));
        addParametro(new Pair<>("Decimal", produtoVO.getPeso().toString()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getSisUnidadeComercialVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getSisSituacaoSistemaVO().getId())));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoFabrica().toString()));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoVenda().toString()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getVarejo())));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoUltimoImpostoSefaz().toString()));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoUltimoFrete().toString()));
        addParametro(new Pair<>("Decimal", produtoVO.getComissao().toString()));
        addParametro(new Pair<>("String", produtoVO.getNcm()));
        addParametro(new Pair<>("String", produtoVO.getCest()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalCstOrigemVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalIcmsVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalPisVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalCofinsVO().getId())));
        addParametro(new Pair<>("String", produtoVO.getNfeGenero()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getUsuarioAtualizacao_id())));
        image[0] = ServiceImage.getInputStreamFromImage(produtoVO.getImgProduto());
        addParametro(new Pair<>("blob0", "image"));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProdutoVO(Connection conn, TabProdutoVO produtoVO) throws SQLException {
        String comandoSql = "INSERT INTO tabProduto " +
                "(codigo, " +                    //1
                "descricao, " +                  //2
                "peso, " +                       //3
                "sisUnidadeComercial_id, " +     //4
                "sisSituacaoSistema_id, " +      //5
                "precoFabrica, " +               //6
                "precoVenda, " +                 //7
                "varejo, " +                     //8
                "precoUltimoImpostoSEFAZ, " +    //9
                "precoUltimoFrete, " +           //10
                "comissao, " +                   //11
                "ncm, " +                        //13
                "cest, " +                       //14
                "fiscalCSTOrigem_id, " +         //15
                "fiscalICMS_id, " +              //16
                "fiscalPIS_id, " +               //17
                "fiscalCOFINS_id, " +            //18
                "nfeGenero, " +                  //19
                "usuarioCadastro_id, " +         //20
                "imgProduto) " +                 //21
                "VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?) ";
        addNewParametro(new Pair<>("int", String.valueOf(produtoVO.getCodigo())));
        addParametro(new Pair<>("String", produtoVO.getDescricao()));
        addParametro(new Pair<>("Decimal", produtoVO.getPeso().toString()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getSisUnidadeComercialVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getSisSituacaoSistemaVO().getId())));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoFabrica().toString()));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoVenda().toString()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getVarejo())));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoUltimoImpostoSefaz().toString()));
        addParametro(new Pair<>("Decimal", produtoVO.getPrecoUltimoFrete().toString()));
        addParametro(new Pair<>("Decimal", produtoVO.getComissao().toString()));
        addParametro(new Pair<>("String", produtoVO.getNcm()));
        addParametro(new Pair<>("String", produtoVO.getCest()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalCstOrigemVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalIcmsVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalPisVO().getId())));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getFiscalCofinsVO().getId())));
        addParametro(new Pair<>("String", produtoVO.getNfeGenero()));
        addParametro(new Pair<>("int", String.valueOf(produtoVO.getUsuarioCadastro_id())));
        image[0] = ServiceImage.getInputStreamFromImage(produtoVO.getImgProduto());
        addParametro(new Pair<>("blob0", "image"));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabProdutoVO(Connection conn, int produto_id) throws SQLException {
        if (produto_id < 0) produto_id = produto_id * (-1);
        String comandoSql = "DELETE FROM tabProduto WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
        getDeleteBancoDados(conn, comandoSql);
    }
}
