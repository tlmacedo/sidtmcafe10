package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelContato_EmailHomePageVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelContato_EmailHomePageDAO extends ServiceBuscaBancoDados {

    RelContato_EmailHomePageVO relContato_emailHomePageVO = null;
    List<RelContato_EmailHomePageVO> relContato_emailHomePageVOList = null;

    public RelContato_EmailHomePageVO getRelContato_EmailHomePageVO(int contato_id, int emailHomePage_id) {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
        getResultSet("SELECT * FROM relContato_EmailHomePage WHERE tabContato_id = ? AND tabEmailHomePage_id = ? ");
        return relContato_emailHomePageVO;
    }

    public List<RelContato_EmailHomePageVO> getRelContato_emailHomePageVOList(int contato_id) {
        relContato_emailHomePageVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        getResultSet("SELECT * FROM relContato_EmailHomePage WHERE tabContato_id = ? ");
        return relContato_emailHomePageVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabContato_id, tabEmailHomePage_id ");
        try {
            while (rs.next()) {
                relContato_emailHomePageVO = new RelContato_EmailHomePageVO();
                relContato_emailHomePageVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContato_emailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));
                if (relContato_emailHomePageVOList != null)
                    relContato_emailHomePageVOList.add(relContato_emailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelContato_emailHomePageVO(Connection conn, int contato_id, int emailHomePage_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
        String comandoSql = "INSERT INTO relContato_EmailHomePage (tabContato_id, tabEmailHomePage_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContato_emailHomePageVO(Connection conn, int contato_id, int emailHomePage_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        String comandoSql = "DELETE FROM relContato_EmailHomePage WHERE tabContato_id = ? ";
        if (emailHomePage_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
            comandoSql += "AND tabEmailHomePage_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
