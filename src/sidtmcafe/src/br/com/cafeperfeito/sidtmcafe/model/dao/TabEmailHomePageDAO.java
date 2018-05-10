package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmailHomePageVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabEmailHomePageDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    TabEmailHomePageVO tabEmailHomePageVO;

    public TabEmailHomePageVO getTabEmailHomePageVO(int id) {
        buscaTabEmailHomePageVO(id);
        return tabEmailHomePageVO;
    }

    void buscaTabEmailHomePageVO(int id) {
        comandoSql = "SELECT id, descricao, isEmail " +
                "FROM tabEmailHomePage " +
                "WHERE id = '" + id + "' ";
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabEmailHomePageVO = new TabEmailHomePageVO();
                tabEmailHomePageVO.setId(rs.getInt("id"));
                tabEmailHomePageVO.setDescricao(rs.getString("descricao"));
                tabEmailHomePageVO.setIsEmail(rs.getBoolean("isEmail"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public void updateTabEmailHomePageVO(Connection conn, TabEmailHomePageVO emailHomePageVO) throws SQLException {
        comandoSql = "UPDATE tabEmailHomePage SET ";
        comandoSql += "descricao = '" + emailHomePageVO.getDescricao() + "' ";
        comandoSql += "WHERE id = " + emailHomePageVO.getId();

        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEmailHomePageVO(Connection conn, TabEmailHomePageVO emailHomePageVO) throws SQLException {
        comandoSql = "INSERT INTO tabEmailHomePage ";
        comandoSql += "(descricao, isEmail) ";
        comandoSql += "VALUES(";
        comandoSql += "'" + emailHomePageVO.getDescricao() + "', ";
        comandoSql += emailHomePageVO.isIsEmail() + " ";
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEmailHomePageVO(Connection conn, TabEmailHomePageVO emailHomePageVO) throws SQLException {
        comandoSql = "DELETE " +
                "FROM tabEmailHomePage " +
                "WHERE id = '" + emailHomePageVO.getId() + "' ";

        getDeleteBancoDados(conn, comandoSql);
    }

}
