package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoEanVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoEanDAO extends BuscaBancoDados {

    ResultSet rs;
    TabProdutoEanVO tabProdutoEanVO;
    List<TabProdutoEanVO> tabProdutoEanVOList;
    boolean returnList = false;

    public TabProdutoEanVO getTabProdutoEanVO(int id) {
        getResultSet(String.format("SELECT * FROM tabProdutoEan WHERE id = %d ORDER BY id", id), false);
        return tabProdutoEanVO;
    }

    public List<TabProdutoEanVO> getTabProdutoEanVOList(int produto_id) {
        tabProdutoEanVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM tabProdutoEan WHERE produto_id = %d ORDER BY id", produto_id), true);
        return tabProdutoEanVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabProdutoEanVO = new TabProdutoEanVO();
                tabProdutoEanVO.setId(rs.getInt("id"));
                tabProdutoEanVO.setProduto_id(rs.getInt("produto_id"));
                tabProdutoEanVO.setCodigoEan(rs.getString("codigoEan"));
                if (returnList) tabProdutoEanVOList.add(tabProdutoEanVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public void updateTabProdutoEanVO(Connection conn, TabProdutoEanVO produtoEan) throws SQLException {
        String comandoSql = String.format("UPDATE tabProdutoEan SET produto_id = %d, codigoEan = '%s', WHERE id = %d",
                produtoEan.getProduto_id(), produtoEan.getCodigoEan(), produtoEan.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProdutoEanVO(Connection conn, TabProdutoEanVO produtoEan) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabProdutoEan (produto_id, codigoEan) " +
                "VALUES(%d, '%s')", produtoEan.getProduto_id(), produtoEan.getCodigoEan());
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabProdutoEanVO(Connection conn, int produtoEan_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM tabProdutoEan WHERE id = %d", produtoEan_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
