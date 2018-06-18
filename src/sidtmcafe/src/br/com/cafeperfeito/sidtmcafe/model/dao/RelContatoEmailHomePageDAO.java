package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelContatoEmailHomePageVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelContatoEmailHomePageDAO extends BuscaBancoDados {

    ResultSet rs;
    RelContatoEmailHomePageVO relContatoEmailHomePageVO;
    List<RelContatoEmailHomePageVO> relContatoEmailHomePageVOList;

    public RelContatoEmailHomePageVO getRelContatoEmailHomePageVO(int contato_id, int emailHomePage_id) {
        getResultSet(String.format("SELECT * FROM relContatoEmailHomePage WHERE tabContato_id = %d " +
                "AND tabEmailHomePage_id = %d ORDER BY tabContato_id, tabEmailHomePage_id", contato_id, emailHomePage_id));
        return relContatoEmailHomePageVO;
    }

    public List<RelContatoEmailHomePageVO> getRelContatoEmailHomePageVOList(int contato_id) {
        getResultSet(String.format("SELECT * FROM relContatoEmailHomePage WHERE tabContato_id = %d " +
                "ORDER BY tabContato_id, tabEmailHomePage_id", contato_id));
        return relContatoEmailHomePageVOList;
    }

    void getResultSet(String comandoSql) {
        boolean returnList = false;
        rs = getResultadosBandoDados(comandoSql);
        try {
            if (rs.last())
                if (returnList = (rs.getRow() > 1))
                    relContatoEmailHomePageVOList = new ArrayList<>();
            rs.beforeFirst();
            while (rs.next()) {
                relContatoEmailHomePageVO = new RelContatoEmailHomePageVO();
                relContatoEmailHomePageVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContatoEmailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));
                if (returnList) relContatoEmailHomePageVOList.add(relContatoEmailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelContatoEmailHomePageVO(Connection conn, int contato_id, int emailHomePage_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relContatoEmailHomePage (tabContato_id, tabEmailHomePage_id) " +
                "VALUES(%d, %d)", contato_id, emailHomePage_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContatoEmailHomePageVO(Connection conn, int contato_id, int emailHomePage_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relContatoEmailHomePage WHERE tabContato_id = %d ", contato_id);
        if (emailHomePage_id > 0)
            comandoSql += String.format("AND tabEmailHomePage_id = %d ", emailHomePage_id);
        getDeleteBancoDados(conn, comandoSql);
    }
}
