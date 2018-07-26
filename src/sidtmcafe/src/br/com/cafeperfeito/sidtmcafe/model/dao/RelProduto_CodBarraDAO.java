package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelProduto_CodBarraVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelProduto_CodBarraDAO extends BuscaBancoDados {

    ResultSet rs;
    RelProduto_CodBarraVO relProduto_codBarraVO;
    List<RelProduto_CodBarraVO> relProduto_codBarraVOList;
    boolean returnList = false;

    public RelProduto_CodBarraVO getRelProduto_CodBarraVO(int produto_id, int codBarra_id) {
        getResultSet(String.format("SELECT * FROM relProduto_CodBarra WHERE %s ORDER BY tabProduto_id, tabProduto_CodBarra_id",
                (produto_id > 0 && codBarra_id > 0) ? String.format("tabProduto_id = %d AND tabProduto_CodBarra_id = %d",
                        produto_id, codBarra_id) : (produto_id > 0 ? String.format("tabProduto_id = %d", produto_id) :
                        String.format("tabProduto_CodBarra_id = %d", codBarra_id))),
                false);
        return relProduto_codBarraVO;
    }

    public List<RelProduto_CodBarraVO> getRelProduto_CodBarraVOList(int produto_id) {
        relProduto_codBarraVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM relProduto_CodBarra WHERE tabProduto_id = %d " +
                "ORDER BY tabProduto_id, tabProduto_CodBarra_id", produto_id), true);
        return relProduto_codBarraVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relProduto_codBarraVO = new RelProduto_CodBarraVO();
                relProduto_codBarraVO.setTabProduto_id(rs.getInt("tabProduto_id"));
                relProduto_codBarraVO.setTabProduto_CodBarra_id(rs.getInt("tabProduto_CodBarra_id"));
                if (returnList) relProduto_codBarraVOList.add(relProduto_codBarraVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelProduto_CodBarraVO(Connection conn, int produto_id, int codBarra_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relProduto_CodBarra (tabProduto_id, tabProduto_CodBarra_id) " +
                "VALUES(%d, %d)", produto_id, codBarra_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelProduto_CodBarraVO(Connection conn, int produto_id, int codBarra_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relProduto_CodBarra WHERE tabProduto_id = %d ", produto_id);
        if (codBarra_id > 0)
            comandoSql += String.format("AND tabProdutoCodBarra = %d", codBarra_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
