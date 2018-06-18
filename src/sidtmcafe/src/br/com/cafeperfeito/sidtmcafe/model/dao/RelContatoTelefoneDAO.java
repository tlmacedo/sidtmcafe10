package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelContatoTelefoneVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelContatoTelefoneDAO extends BuscaBancoDados {

    ResultSet rs;
    RelContatoTelefoneVO relContatoTelefoneVO;
    List<RelContatoTelefoneVO> relContatoTelefoneVOList;

    public RelContatoTelefoneVO getRelContatoTelefoneVO(int contato_id, int telefone_id) {
        getResultSet(String.format("SELECT * FROM relContatoTelefone WHERE tabContato_id = %d " +
                "AND tabTelefone_id = %d ORDER BY tabContato_id, tabTelefone_id", contato_id, telefone_id));
        return relContatoTelefoneVO;
    }

    public List<RelContatoTelefoneVO> getRelContatoTelefoneVOList(int contato_id) {
        getResultSet(String.format("SELECT * FROM relContatoTelefone WHERE tabContato_id = %d " +
                "ORDER BY tabContato_id, tabTelefone_id", contato_id));
        return relContatoTelefoneVOList;
    }

    void getResultSet(String comandoSql) {
        boolean returnList = false;
        rs = getResultadosBandoDados(comandoSql);
        try {
            if (rs.last())
                if (returnList = (rs.getRow() > 1))
                    relContatoTelefoneVOList = new ArrayList<>();
            rs.beforeFirst();
            while (rs.next()) {
                relContatoTelefoneVO = new RelContatoTelefoneVO();
                relContatoTelefoneVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContatoTelefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));
                if (returnList) relContatoTelefoneVOList.add(relContatoTelefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelContatoTelefoneVO(Connection conn, int contato_id, int telefone_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relContatoTelefone (tabContato_id, tabTelefone_id) " +
                "VALUES(%d, %d)", contato_id, telefone_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContatoTelefoneVO(Connection conn, int contato_id, int telefone_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relContatoTelefone WHERE tabContato_id = %d ", contato_id);
        if (telefone_id > 0)
            comandoSql += String.format("AND tabTelefone_id = %d", telefone_id);
        getDeleteBancoDados(conn, comandoSql);
    }
}
