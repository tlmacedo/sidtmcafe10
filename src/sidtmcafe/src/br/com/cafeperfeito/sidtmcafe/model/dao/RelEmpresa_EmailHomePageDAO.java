package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresa_EmailHomePageVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresa_EmailHomePageDAO extends ServiceBuscaBancoDados {

    RelEmpresa_EmailHomePageVO relEmpresa_emailHomePageVO = null;
    List<RelEmpresa_EmailHomePageVO> relEmpresa_emailHomePageVOList = null;

    public RelEmpresa_EmailHomePageVO getRelEmpresa_emailHomePageVO(int empresa_id, int emailHoePage_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHoePage_id)));
        getResultSet("SELECT * FROM relEmpresa_EmailHomePage WHERE tabEmpresa_id = ? AND tabEmailHomePage_id = ? ");
        return relEmpresa_emailHomePageVO;
    }

    public List<RelEmpresa_EmailHomePageVO> getRelEmpresa_emailHomePageVOList(int empresa_id) {
        relEmpresa_emailHomePageVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresa_EmailHomePage WHERE tabEmpresa_id = ? ");
        return relEmpresa_emailHomePageVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabEmailHomePage_id ");
        try {
            while (rs.next()) {
                relEmpresa_emailHomePageVO = new RelEmpresa_EmailHomePageVO();
                relEmpresa_emailHomePageVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresa_emailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));
                if (relEmpresa_emailHomePageVOList != null) relEmpresa_emailHomePageVOList.add(relEmpresa_emailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresa_emailHomePage(Connection conn, int empresa_id, int emailHomePage_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
        String comandoSql = "INSERT INTO relEmpresa_EmailHomePage (tabEmpresa_id, tabEmailHomePage_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresa_emailHomePage(Connection conn, int empresa_id, int emailHome_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        String comandoSql = "DELETE FROM relEmpresa_EmailHomePage WHERE tabEmpresa_id = ? ";
        if (emailHome_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(emailHome_id)));
            comandoSql += "AND tabEmailHomePage_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }


}
