package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProduto_CodBarraVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProduto_CodBarraVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProduto_CodBarraDAO extends BuscaBancoDados {

    ResultSet rs;
    TabProduto_CodBarraVO codBarraVO;
    List<TabProduto_CodBarraVO> codBarraVOList;
    boolean returnList = false;

    public TabProduto_CodBarraVO getTabProduto_codBarraVO(int id) {
        getResultSet(String.format("SELECT * FROM tabProduto_CodBarra WHERE id = %d ORDER BY id", id), false);
        return codBarraVO;
    }

    public TabProduto_CodBarraVO getTabProduto_codBarraVO(String barCode) {
        getResultSet(String.format("SELECT * FROM tabProduto_CodBarra WHERE codBarra = '%s'", barCode), false);
//        if (codBarraVO!=null)
//
        return codBarraVO;
    }

    public List<TabProduto_CodBarraVO> getTabProduto_codBarraVOList(int produto_id) {
        codBarraVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM tabProduto_CodBarra WHERE produto_id = %d ORDER BY id", produto_id), true);
        return codBarraVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                codBarraVO = new TabProduto_CodBarraVO();
                codBarraVO.setId(rs.getInt("id"));
                codBarraVO.setCodBarra(rs.getString("codBarra"));
                if (returnList) codBarraVOList.add(codBarraVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabProduto_CodBarraVO(Connection conn, TabProduto_CodBarraVO codBarraVO) throws SQLException {
        String comandoSql = String.format("UPDATE tabProduto_CodBarra SET codBarra = '%s' WHERE id = %d",
                codBarraVO.getCodBarra(), codBarraVO.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProduto_CodBarraVO(Connection conn, TabProduto_CodBarraVO codBarraVO, int produto_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabProduto_CodBarra (codBarra) " +
                "VALUES('%s')", codBarraVO.getCodBarra());
        int produto_CodBarra_id = getInsertBancoDados(conn, comandoSql);
        if (produto_id > 0)
            new RelProduto_CodBarraDAO().insertRelProduto_CodBarraVO(conn, produto_id, produto_CodBarra_id);
        return produto_CodBarra_id;
    }

    public void deleteTabProduto_CodBarraVO(Connection conn, int codBarra_id, int produto_id) throws SQLException {
        if (codBarra_id < 0) codBarra_id = codBarra_id * (-1);
        if (produto_id > 0)
            new RelProduto_CodBarraDAO().deleteRelProduto_CodBarraVO(conn, produto_id, codBarra_id);
        String comandoSql = String.format("DELETE FROM tabProduto_CodBarra WHERE id = %d", codBarra_id);
        getDeleteBancoDados(conn, comandoSql);
    }
}
