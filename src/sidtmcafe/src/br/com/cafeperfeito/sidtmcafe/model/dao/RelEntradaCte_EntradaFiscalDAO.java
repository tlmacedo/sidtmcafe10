package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEntradaCte_EntradaFiscalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEntradaCte_EntradaFiscalDAO extends ServiceBuscaBancoDados {

    RelEntradaCte_EntradaFiscalVO relEntradaProduto_entradaProdutoFiscalVO = null;
    List<RelEntradaCte_EntradaFiscalVO> relEntradaProduto_entradaProdutoFiscalVOList = null;

    public RelEntradaCte_EntradaFiscalVO getRelEntradaCte_EntradaFiscalVO(int entradaCte_id, int entradaFiscal_id) {
        addNewParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
        addParametro(new Pair<>("int", String.valueOf(entradaFiscal_id)));
        getResultSet("SELECT * FROM relEntradaCte_EntradaFiscal WHERE tabEntradaCte_id = ? AND tabEntradaFiscal_id = ? ");
        return relEntradaProduto_entradaProdutoFiscalVO;
    }

    public List<RelEntradaCte_EntradaFiscalVO> getRelEntradaCte_EntradaFiscalVOList(int entradaCte_id) {
        relEntradaProduto_entradaProdutoFiscalVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
        getResultSet("SELECT * FROM relEntradaCte_EntradaFiscal WHERE tabEntradaCte_id = ? ");
        return relEntradaProduto_entradaProdutoFiscalVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEntradaCte_id, tabEntradaFiscal_id ");
        try {
            while (rs.next()) {
                relEntradaProduto_entradaProdutoFiscalVO = new RelEntradaCte_EntradaFiscalVO();
                relEntradaProduto_entradaProdutoFiscalVO.setTabEntradaCte_id(rs.getInt("tabEntradaCte_id"));
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

    public int insertRelEntradaCte_EntradaFiscalVO(Connection conn, int entradaCte_id, int entradaFiscal_id) throws SQLException {
        String comandoSql = "INSERT INTO relEntradaCte_EntradaFiscal " +
                "(tabEntradaCte_id, " +
                "tabEntradaFiscal_id) " +
                "VALUES (" +
                "?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
        addParametro(new Pair<>("int", String.valueOf(entradaFiscal_id)));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEntradaCte_EntradaFiscalVO(Connection conn, int entradaCte_id, int entradaFiscal_id) throws SQLException {
        String comandoSql = "DELETE FROM relEntradaCte_EntradaFiscal " +
                "WHERE tabEntradaCte_id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaCte_id)));
        if (entradaFiscal_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(entradaFiscal_id)));
            comandoSql += "AND tabEntradaFiscal_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
