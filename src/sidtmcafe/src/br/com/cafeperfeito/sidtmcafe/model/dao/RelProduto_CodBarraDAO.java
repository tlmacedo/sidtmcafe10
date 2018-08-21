package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelProduto_CodBarraVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelProduto_CodBarraDAO extends ServiceBuscaBancoDados {

    RelProduto_CodBarraVO relProduto_codBarraVO = null;
    List<RelProduto_CodBarraVO> relProduto_codBarraVOList = null;

    public RelProduto_CodBarraVO getRelProduto_CodBarraVO(int produto_id, int codBarra_id) {
        String comandoSql = "SELECT * FROM relProduto_CodBarra ";
        if (produto_id > 0 && codBarra_id > 0) {
            addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
            addParametro(new Pair<>("int", String.valueOf(codBarra_id)));
            comandoSql += "WHERE tabProduto_id = ? AND tabProduto_CodBarra_id = ? ";
        } else {
            if (produto_id > 0) {
                addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
                comandoSql += "tabProduto_id = ? ";
            } else {
                addNewParametro(new Pair<>("int", String.valueOf(codBarra_id)));
                comandoSql += "tabProduto_CodBarra_id = ? ";
            }
        }
        getResultSet(comandoSql);
        return relProduto_codBarraVO;
    }

    public List<RelProduto_CodBarraVO> getRelProduto_CodBarraVOList(int produto_id) {
        relProduto_codBarraVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
        getResultSet("SELECT * FROM relProduto_CodBarra WHERE tabProduto_id = ? ");
        return relProduto_codBarraVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabProduto_id, tabProduto_CodBarra_id ");
        try {
            while (rs.next()) {
                relProduto_codBarraVO = new RelProduto_CodBarraVO();
                relProduto_codBarraVO.setTabProduto_id(rs.getInt("tabProduto_id"));
                relProduto_codBarraVO.setTabProduto_CodBarra_id(rs.getInt("tabProduto_CodBarra_id"));
                if (relProduto_codBarraVOList != null) relProduto_codBarraVOList.add(relProduto_codBarraVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelProduto_CodBarraVO(Connection conn, int produto_id, int codBarra_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
        addParametro(new Pair<>("int", String.valueOf(codBarra_id)));
        String comandoSql = "INSERT INTO relProduto_CodBarra (tabProduto_id, tabProduto_CodBarra_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelProduto_CodBarraVO(Connection conn, int produto_id, int codBarra_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
        String comandoSql = "DELETE FROM relProduto_CodBarra WHERE tabProduto_id = ? ";
        if (codBarra_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(codBarra_id)));
            comandoSql += "AND tabProdutoCodBarra = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }

}
