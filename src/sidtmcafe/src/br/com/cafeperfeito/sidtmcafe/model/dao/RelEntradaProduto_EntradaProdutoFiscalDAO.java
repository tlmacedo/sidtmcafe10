package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEntradaProduto_EntradaProdutoFiscalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEntradaProduto_EntradaProdutoFiscalDAO extends ServiceBuscaBancoDados {
    RelEntradaProduto_EntradaProdutoFiscalVO relEntradaProduto_entradaProdutoFiscalVO = null;
    List<RelEntradaProduto_EntradaProdutoFiscalVO> relEntradaProduto_entradaProdutoFiscalVOList = null;

    public RelEntradaProduto_EntradaProdutoFiscalVO getRelEntradaProduto_EntradaProdutoFiscalVO(int entrada_id, int fiscal_id) {
        addNewParametro(new Pair<>("int", String.valueOf(entrada_id)));
        addParametro(new Pair<>("int", String.valueOf(fiscal_id)));
        getResultSet("SELECT * FROM relEntradaProduto_EntradaProdutoFiscal WHERE tabEntradaProduto_id = ? AND tabEntradaProduto_Fiscal_id = ? ");
        return relEntradaProduto_entradaProdutoFiscalVO;
    }

    public List<RelEntradaProduto_EntradaProdutoFiscalVO> getRelEntradaProduto_EntradaProdutoFiscalVOList(int entrada_id) {
        relEntradaProduto_entradaProdutoFiscalVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(entrada_id)));
        getResultSet("SELECT * FROM relEntradaProduto_EntradaProdutoFiscal WHERE tabEntradaProduto_id = ? ");
        return relEntradaProduto_entradaProdutoFiscalVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEntradaProduto_id, tabEntradaProduto_Fiscal_id ");
        try {
            while (rs.next()) {
                relEntradaProduto_entradaProdutoFiscalVO = new RelEntradaProduto_EntradaProdutoFiscalVO();
                relEntradaProduto_entradaProdutoFiscalVO.setTabEntradaProduto_id(rs.getInt("tabEntradaProduto_id"));
                relEntradaProduto_entradaProdutoFiscalVO.setTabEntradaProdutoFiscal_id(rs.getInt("tabEntradaProduto_Fiscal_id"));
                if (relEntradaProduto_entradaProdutoFiscalVOList != null)
                    relEntradaProduto_entradaProdutoFiscalVOList.add(relEntradaProduto_entradaProdutoFiscalVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEntradaProduto_EntradaProdutoFiscalVO(Connection conn, int entrada_id, int fiscal_id) throws SQLException {
        String comandoSql = "INSERT INTO relEntradaProduto_EntradaProdutoFiscal " +
                "(tabEntradaProduto_id, " +
                "tabEntradaProduto_Fiscal_id) " +
                "VALUES (" +
                "?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(entrada_id)));
        addParametro(new Pair<>("int", String.valueOf(fiscal_id)));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEntradaProduto_EntradaProdutoFiscalVO(Connection conn, int entrada_id, int fiscal_id) throws SQLException {
        String comandoSql = "DELETE FROM relEntradaProduto_EntradaProdutoFiscal " +
                "WHERE tabEntradaProduto_id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entrada_id)));
        if (fiscal_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(fiscal_id)));
            comandoSql += "AND tabEntradaProduto_Fiscal_if = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
