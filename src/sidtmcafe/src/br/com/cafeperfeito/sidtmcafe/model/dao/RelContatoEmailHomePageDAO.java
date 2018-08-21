package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelContatoEmailHomePageVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelContatoEmailHomePageDAO extends ServiceBuscaBancoDados {

    RelContatoEmailHomePageVO relContatoEmailHomePageVO = null;
    List<RelContatoEmailHomePageVO> relContatoEmailHomePageVOList = null;

    public RelContatoEmailHomePageVO getRelContatoEmailHomePageVO(int contato_id, int emailHomePage_id) {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
        getResultSet("SELECT * FROM relContatoEmailHomePage WHERE tabContato_id = ? AND tabEmailHomePage_id = ? ");
        return relContatoEmailHomePageVO;
    }

    public List<RelContatoEmailHomePageVO> getRelContatoEmailHomePageVOList(int contato_id) {
        relContatoEmailHomePageVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        getResultSet("SELECT * FROM relContatoEmailHomePage WHERE tabContato_id = ? ");
        return relContatoEmailHomePageVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabContato_id, tabEmailHomePage_id ");
        try {
            while (rs.next()) {
                relContatoEmailHomePageVO = new RelContatoEmailHomePageVO();
                relContatoEmailHomePageVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContatoEmailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));
                if (relContatoEmailHomePageVOList != null) relContatoEmailHomePageVOList.add(relContatoEmailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelContatoEmailHomePageVO(Connection conn, int contato_id, int emailHomePage_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
        String comandoSql = "INSERT INTO relContatoEmailHomePage (tabContato_id, tabEmailHomePage_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContatoEmailHomePageVO(Connection conn, int contato_id, int emailHomePage_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        String comandoSql = "DELETE FROM relContatoEmailHomePage WHERE tabContato_id = ? ";
        if (emailHomePage_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
            comandoSql += "AND tabEmailHomePage_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
