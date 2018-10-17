package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEntradaNfe_EntradaCteVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEntradaNfe_EntradaCteDAO extends ServiceBuscaBancoDados {

    RelEntradaNfe_EntradaCteVO relEntradaNfe_entradaCteVO = null;
    List<RelEntradaNfe_EntradaCteVO> relEntradaNfe_entradaCteVOList = null;

    public RelEntradaNfe_EntradaCteVO getRelEntradaNfe_EntradaCteVO(int entradaNfe_id, int entradaCte_id) {
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        addParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
        getResultSet("SELECT * FROM relEntradaNfe_EntradaCte WHERE tabEntradaNfe_id = ? AND tabEntradaCte_id = ? ");
        return relEntradaNfe_entradaCteVO;
    }

    public List<RelEntradaNfe_EntradaCteVO> getRelEntradaNfe_EntradaCteVOList(int entradaNfe_id) {
        relEntradaNfe_entradaCteVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        getResultSet("SELECT * FROM relEntradaNfe_EntradaCte WHERE tabEntradaNfe_id = ? ");
        return relEntradaNfe_entradaCteVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEntradaNfe_id, tabEntradaCte_id ");
        try {
            while (rs.next()) {
                relEntradaNfe_entradaCteVO = new RelEntradaNfe_EntradaCteVO();
                relEntradaNfe_entradaCteVO.setTabEntradaNfe_id(rs.getInt("tabEntradaNfe_id"));
                relEntradaNfe_entradaCteVO.setTabEntradaCte_id(rs.getInt("tabEntradaCte_id"));
                if (relEntradaNfe_entradaCteVOList != null)
                    relEntradaNfe_entradaCteVOList.add(relEntradaNfe_entradaCteVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEntradaNfe_EntradaCteVO(Connection conn, int entradaNfe_id, int entradaCte_id) throws SQLException {
        String comandoSql = "INSERT INTO relEntradaNfe_EntradaCte " +
                "(tabEntradaNfe_id, " +
                "tabEntradaCte_id) " +
                "VALUES (" +
                "?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        addParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEntradaNfe_EntradaCteVO(Connection conn, int entradaNfe_id, int entradaCte_id) throws SQLException {
        String comandoSql = "DELETE FROM relEntradaNfe_EntradaCte " +
                "WHERE tabEntradaNfe_id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        if (entradaCte_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
            comandoSql += "AND tabEntradaCte_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
