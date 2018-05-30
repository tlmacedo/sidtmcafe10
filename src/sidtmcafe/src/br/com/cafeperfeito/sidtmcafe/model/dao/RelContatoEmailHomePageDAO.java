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

    String comandoSql = "";
    RelContatoEmailHomePageVO relContatoEmailHomePageVO;
    List<RelContatoEmailHomePageVO> relContatoEmailHomePageVOList;

    public RelContatoEmailHomePageVO getRelContatoEmailHomePageVO(int contato_id, int emailHomePage_id) {
        buscaRelContatoEmailHomePageVO(contato_id, emailHomePage_id);
        return relContatoEmailHomePageVO;
    }

    public List<RelContatoEmailHomePageVO> getRelContatoEmailHomePageVOList(int contato_id) {
        buscaRelContatoEmailHomePageVO(contato_id, 0);
        return relContatoEmailHomePageVOList;
    }

    void buscaRelContatoEmailHomePageVO(int contato_id, int emailHomePage_id) {
        comandoSql = "SELECT tabContato_id, tabEmailHomePage_id " +
                "FROM RelContatoEmailHomePage " +
                "WHERE tabContato_id = '" + contato_id + "' ";
        if (emailHomePage_id > 0) comandoSql += " AND tabEmailHomePage_id = '" + emailHomePage_id + "' ";
        comandoSql += "ORDER BY tabEmailHomePage_id ";

        if (emailHomePage_id == 0) relContatoEmailHomePageVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relContatoEmailHomePageVO = new RelContatoEmailHomePageVO();
                relContatoEmailHomePageVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContatoEmailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));

                if (emailHomePage_id == 0) relContatoEmailHomePageVOList.add(relContatoEmailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelContatoEmailHomePageVO(Connection conn, int contato_id, int emailHomePage_id) throws SQLException {
        comandoSql = "INSERT INTO relContatoEmailHomePage ";
        comandoSql += "(tabContato_id, tabEmailHomePage_id) ";
        comandoSql += "VALUES (";
        comandoSql += contato_id + ", ";
        comandoSql += emailHomePage_id;
        comandoSql += ") ";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContatoEmailHomePageVO(Connection conn, int contato_id) throws SQLException {
        comandoSql = "DELETE ";
        comandoSql += "FROM relContatoEmailHomePage ";
        comandoSql += "WHERE tabContato_id = " + contato_id + " ";

        getDeleteBancoDados(conn, comandoSql);
    }
}
