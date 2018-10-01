package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelContato_TelefoneVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelContato_TelefoneDAO extends ServiceBuscaBancoDados {

    RelContato_TelefoneVO relContato_telefoneVO = null;
    List<RelContato_TelefoneVO> relContato_telefoneVOList = null;

    public RelContato_TelefoneVO getRelContato_telefoneVO(int contato_id, int telefone_id) {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        getResultSet("SELECT * FROM relContato_Telefone WHERE tabContato_id = ? AND tabTelefone_id = ? ");
        return relContato_telefoneVO;
    }

    public List<RelContato_TelefoneVO> getRelContato_telefoneVOList(int contato_id) {
        relContato_telefoneVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        getResultSet("SELECT * FROM relContato_Telefone WHERE tabContato_id = ? ");
        return relContato_telefoneVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabContato_id, tabTelefone_id ");
        try {
            while (rs.next()) {
                relContato_telefoneVO = new RelContato_TelefoneVO();
                relContato_telefoneVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContato_telefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));
                if (relContato_telefoneVOList != null) relContato_telefoneVOList.add(relContato_telefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelContato_telefoneVO(Connection conn, int contato_id, int telefone_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        String comandoSql = "INSERT INTO relContato_Telefone (tabContato_id, tabTelefone_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContato_telefoneVO(Connection conn, int contato_id, int telefone_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(contato_id)));
        String comandoSql = "DELETE FROM relContato_Telefone WHERE tabContato_id = ? ";
        if (telefone_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(telefone_id)));
            comandoSql += "AND tabTelefone_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
