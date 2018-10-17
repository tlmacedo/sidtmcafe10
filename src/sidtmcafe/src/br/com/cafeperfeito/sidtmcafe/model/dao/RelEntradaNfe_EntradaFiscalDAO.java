package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEntradaNfe_EntradaFiscalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEntradaNfe_EntradaFiscalDAO extends ServiceBuscaBancoDados {
    RelEntradaNfe_EntradaFiscalVO relEntradaProduto_entradaProdutoFiscalVO = null;
    List<RelEntradaNfe_EntradaFiscalVO> relEntradaProduto_entradaProdutoFiscalVOList = null;

    public RelEntradaNfe_EntradaFiscalVO getRelEntradaNfe_EntradaFiscalVO(int entradaNfe_id, int entradaFiscal_id) {
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        addParametro(new Pair<>("int", String.valueOf(entradaFiscal_id)));
        getResultSet("SELECT * FROM relEntradaNfe_EntradaFiscal WHERE tabEntradaNfe_id = ? AND tabEntradaFiscal_id = ? ");
        return relEntradaProduto_entradaProdutoFiscalVO;
    }

    public List<RelEntradaNfe_EntradaFiscalVO> getRelEntradaNfe_EntradaFiscalVOList(int entradaNfe_id) {
        relEntradaProduto_entradaProdutoFiscalVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        getResultSet("SELECT * FROM relEntradaNfe_EntradaFiscal WHERE tabEntradaNfe_id = ? ");
        return relEntradaProduto_entradaProdutoFiscalVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEntradaNfe_id, tabEntradaFiscal_id ");
        try {
            while (rs.next()) {
                relEntradaProduto_entradaProdutoFiscalVO = new RelEntradaNfe_EntradaFiscalVO();
                relEntradaProduto_entradaProdutoFiscalVO.setTabEntradaNfe_id(rs.getInt("tabEntradaNfe_id"));
                relEntradaProduto_entradaProdutoFiscalVO.setTabEntradaFiscal_id(rs.getInt("tabEntradaFiscal_id"));
                if (relEntradaProduto_entradaProdutoFiscalVOList != null)
                    relEntradaProduto_entradaProdutoFiscalVOList.add(relEntradaProduto_entradaProdutoFiscalVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEntradaNfe_EntradaFiscalVO(Connection conn, int entradaNfe_id, int entradaFiscal_id) throws SQLException {
        String comandoSql = "INSERT INTO relEntradaNfe_EntradaFiscal " +
                "(tabEntradaNfe_id, " +
                "tabEntradaFiscal_id) " +
                "VALUES (" +
                "?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        addParametro(new Pair<>("int", String.valueOf(entradaFiscal_id)));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEntradaNfe_EntradaFiscalVO(Connection conn, int entradaNfe_id, int entradaFiscal_id) throws SQLException {
        String comandoSql = "DELETE FROM relEntradaNfe_EntradaFiscal " +
                "WHERE tabEntradaNfe_id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        if (entradaFiscal_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(entradaFiscal_id)));
            comandoSql += "AND tabEntradaFiscal_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
