package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelContatoTelefoneVO;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelContatoTelefoneDAO extends BuscaBancoDados {

    RelContatoTelefoneVO relContatoTelefoneVO = null;
    List<RelContatoTelefoneVO> relContatoTelefoneVOList=null;

    public RelContatoTelefoneVO getRelContatoTelefoneVO(int contato_id, int telefone_id) {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        getResultSet("SELECT * FROM relContatoTelefone WHERE tabContato_id = ? AND tabTelefone_id = ? ");
        return relContatoTelefoneVO;
    }

    public List<RelContatoTelefoneVO> getRelContatoTelefoneVOList(int contato_id) {
        relContatoTelefoneVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        getResultSet("SELECT * FROM relContatoTelefone WHERE tabContato_id = ? ");
        return relContatoTelefoneVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabContato_id, tabTelefone_id ");
        try {
            while (rs.next()) {
                relContatoTelefoneVO = new RelContatoTelefoneVO();
                relContatoTelefoneVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContatoTelefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));
                if (relContatoTelefoneVOList != null) relContatoTelefoneVOList.add(relContatoTelefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelContatoTelefoneVO(Connection conn, int contato_id, int telefone_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        String comandoSql = "INSERT INTO relContatoTelefone (tabContato_id, tabTelefone_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContatoTelefoneVO(Connection conn, int contato_id, int telefone_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        String comandoSql = "DELETE FROM relContatoTelefone WHERE tabContato_id = ? ";
        if (telefone_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(telefone_id)));
            comandoSql += "AND tabTelefone_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
