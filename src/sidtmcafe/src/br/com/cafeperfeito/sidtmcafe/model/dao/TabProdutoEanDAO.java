package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoEanVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoEanDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    TabProdutoEanVO tabProdutoEanVO;
    List<TabProdutoEanVO> tabProdutoEanVOList;

    public TabProdutoEanVO getTabProdutoEanVO(int id) {
        buscaTabProdutoEanVO(id, 0);
        return tabProdutoEanVO;
    }

    public List<TabProdutoEanVO> getTabProdutoEanVOList(int produto_id) {
        buscaTabProdutoEanVO(0, produto_id);
        return tabProdutoEanVOList;
    }

    void buscaTabProdutoEanVO(int id, int produto_id) {
        comandoSql = "SELECT id, produto_id, codigoEan ";
        comandoSql += "FROM tabProdutoEan ";
        if (id > 0)
            comandoSql += "WHERE id = " + id + " ";
        else if (produto_id > 0) comandoSql += "WHERE produto_id = " + produto_id + " ";
        comandoSql += "ORDER BY id ";

        if (id == 0) tabProdutoEanVOList = new ArrayList<TabProdutoEanVO>();
        rs = getResultadosBandoDados(comandoSql);

        try {
            while (rs.next()) {
                tabProdutoEanVO = new TabProdutoEanVO();
                tabProdutoEanVO.setId(rs.getInt("id"));
                tabProdutoEanVO.setProduto_id(rs.getInt("produto_id"));
                tabProdutoEanVO.setCodigoEan(rs.getString("codigoEan"));

                if (id == 0) tabProdutoEanVOList.add(tabProdutoEanVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public void updateTabProdutoEanVO(Connection conn, TabProdutoEanVO produtoEanVO) throws SQLException {
        comandoSql = "UPDATE tabProdutoEan SET ";
        comandoSql += "produto_id = " + produtoEanVO.getProduto_id() + ", ";
        comandoSql += "codigoEan =  '" + produtoEanVO.getCodigoEan() + "' ";
        comandoSql += "WHERE id = " + produtoEanVO.getId() + " ";
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProdutoEanVO(Connection conn, TabProdutoEanVO produtoEanVO) throws SQLException {
        comandoSql = "INSERT INTO tabProdutoEan ";
        comandoSql += "(produto_id, codigoEan) ";
        comandoSql += "VALUES ( ";
        comandoSql += produtoEanVO.getProduto_id() + ", ";
        comandoSql += "'" + produtoEanVO.getCodigoEan() + "' ";
        comandoSql += ") ";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabProdutoEanVO(Connection conn, TabProdutoEanVO produtoEanVO) throws SQLException {
        comandoSql = "DELETE ";
        comandoSql += "FROM tabProdutoEan ";
        comandoSql += "WHERE id = " + produtoEanVO.getId();

        getDeleteBancoDados(conn, comandoSql);
    }

}
