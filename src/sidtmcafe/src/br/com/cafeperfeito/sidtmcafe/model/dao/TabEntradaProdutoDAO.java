package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProduto_CodBarraVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import br.com.cafeperfeito.sidtmcafe.service.ServiceImage;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabEntradaProdutoDAO extends ServiceBuscaBancoDados {

    TabEntradaProdutoVO tabEntradaProdutoVO = null;
    List<TabEntradaProdutoVO> tabEntradaProdutoVOList = null;

    public TabEntradaProdutoVO getTabEntradaProdutoVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEntradaProduto WHERE id = ? ");
        return tabEntradaProdutoVO;
    }

    public TabEntradaProdutoVO getTabEntradaProdutoVO(String num_chaveNfe) {
        String comandoSql = "SELECT * FROM tabEntradaProduto ";
        addNewParametro(new Pair<>("String", num_chaveNfe));
        if (num_chaveNfe.length() == 44)
            getResultSet(comandoSql + "WHERE chaveNfe = ? ");
        else
            getResultSet(comandoSql + "WHERE numeroNfe = ? ");
        return tabEntradaProdutoVO;
    }

    public List<TabEntradaProdutoVO> getTabEntradaProdutoVOList() {
        tabEntradaProdutoVOList = new ArrayList<>();
        getResultSet("SELECT * FROM tabEntradaProduto ");
        return tabEntradaProdutoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabEntradaProdutoVO = new TabEntradaProdutoVO();
                tabEntradaProdutoVO.setId(rs.getInt("id"));
                tabEntradaProdutoVO.setLojaDestino_id(rs.getInt("lojaDestino_id"));
                tabEntradaProdutoVO.setChaveNfe(rs.getString("chaveNfe"));
                tabEntradaProdutoVO.setNumeroNfe(rs.getInt("numeroNfe"));
                tabEntradaProdutoVO.setSerieNfe(rs.getInt("serieNfe"));
                tabEntradaProdutoVO.setModeloNfe_id(rs.getInt("modeloNfe_id"));
                tabEntradaProdutoVO.setFornecedor_id(rs.getInt("fornecedor_id"));
                tabEntradaProdutoVO.setDataEmissaoNfe(rs.getTimestamp("dataEmissaoNfe"));
                tabEntradaProdutoVO.setDataEntradaNfe(rs.getTimestamp("dataEntradaNfe"));
                tabEntradaProdutoVO.setStatusNfe_id(rs.getInt("statusNfe_id"));
                if (tabEntradaProdutoVOList != null) tabEntradaProdutoVOList.add(tabEntradaProdutoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabProdutoVO produto) {
//        produto.setSisUnidadeComercialVO(new SisUnidadeComercialDAO().getSisUnidadeComercialVO(produto.getSisUnidadeComercial_id()));
//
//        produto.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(produto.getSisSituacaoSistema_id()));
//
//        produto.setFiscalCstOrigemVO(new FiscalCstOrigemDAO().getFiscalCstOrigemVO(produto.getFiscalCSTOrigem_id()));
//
//        produto.setFiscalIcmsVO(new FiscalIcmsDAO().getFiscalIcmsVO((produto.getFiscalICMS_id())));
//
//        produto.setFiscalPisVO(new FiscalPisCofinsDAO().getFiscalPisCofinsVO(produto.getFiscalPIS_id()));
//
//        produto.setFiscalCofinsVO(new FiscalPisCofinsDAO().getFiscalPisCofinsVO(produto.getFiscalCOFINS_id()));
//
//        produto.setUsuarioCadastroVO(new TabColaboradorDAO().getTabColaboradorVO(produto.getUsuarioCadastro_id(), false));
//
//        produto.setUsuarioAtualizacaoVO(new TabColaboradorDAO().getTabColaboradorVO(produto.getUsuarioAtualizacao_id(), false));
//
//        List<TabProduto_CodBarraVO> codBarraVOList = new ArrayList<>();
//        new RelProduto_CodBarraDAO().getRelProduto_CodBarraVOList(produto.getId()).stream()
//                .forEach(relProduto_codBarraVO -> {
//                    codBarraVOList.add(new TabProduto_CodBarraDAO().getTabProduto_codBarraVO(relProduto_codBarraVO.getTabProduto_CodBarra_id()));
//                });
//        produto.setCodBarraVOList(codBarraVOList);
    }

    public void updateTabEntradaProdutoVO(Connection conn, TabEntradaProdutoVO entradaProdutoVO) throws SQLException {
        String comandoSql = "UPDATE tabEntradaProduto SET " +
                "lojaDestino_id = ?, " +
                "chaveNfe = ?, " +
                "numeroNfe = ?, " +
                "serieNfe = ?, " +
                "modeloNfe_id = ?, " +
                "fornecedor_id = ?, " +
                "dataEmissaoNfe = ?, " +
                "dataEntradaNfe = ?, " +
                "statusNfe_id = ? " +
                "WHERE id = ?";
        addNewParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getLojaDestino_id())));
        addParametro(new Pair<>("String", entradaProdutoVO.getChaveNfe()));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getNumeroNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getSerieNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getModeloNfe_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getFornecedor_id())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaProdutoVO.getDataEmissaoNfe())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaProdutoVO.getDataEntradaNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getStatusNfe_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaProdutoVO(Connection conn, TabEntradaProdutoVO entradaProdutoVO) throws SQLException {
        String comandoSql = "INSERT INTO tabEntradaProduto " +
                "(lojaDestino_id, " +
                "chaveNfe, " +
                "numeroNfe, " +
                "serieNfe, " +
                "modeloNfe_id, " +
                "fornecedor_id, " +
                "dataEmissaoNfe, " +
                "dataEntradaNfe, " +
                "statusNfe_id) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?) ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getLojaDestino_id())));
        addParametro(new Pair<>("String", entradaProdutoVO.getChaveNfe()));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getNumeroNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getSerieNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getModeloNfe_id())));
        System.out.println("001: " + entradaProdutoVO.getFornecedor_id());
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getFornecedor_id())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaProdutoVO.getDataEmissaoNfe())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaProdutoVO.getDataEntradaNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaProdutoVO.getStatusNfe_id())));

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEntradaProdutoVO(Connection conn, int entradaProduto_id) throws SQLException {
        if (entradaProduto_id < 0) entradaProduto_id = entradaProduto_id * (-1);
        String comandoSql = "DELETE FROM tabEntradaProduto WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaProduto_id)));
        getDeleteBancoDados(conn, comandoSql);
    }
}
