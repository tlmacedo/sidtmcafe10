package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaProduto_FiscalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;

public class TabEntradaProduto_FiscalDAO extends ServiceBuscaBancoDados {

    TabEntradaProduto_FiscalVO tabEntradaProduto_FiscalVO = null;

    public TabEntradaProduto_FiscalVO getTabEntradaProduto_FiscalVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEntradaProduto_Fiscal WHERE id = ? ");
        return tabEntradaProduto_FiscalVO;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabEntradaProduto_FiscalVO = new TabEntradaProduto_FiscalVO();
                tabEntradaProduto_FiscalVO.setId(rs.getInt("id"));
                tabEntradaProduto_FiscalVO.setNumControle(rs.getString("numControle"));
                tabEntradaProduto_FiscalVO.setDocOrigem(rs.getString("docOrigem"));
                tabEntradaProduto_FiscalVO.setTributoSefazAM_id(rs.getInt("fiscalTributoSefazAm_id"));
                tabEntradaProduto_FiscalVO.setTributoSefazAmVO(new FiscalTributoSefazAmDAO()
                        .getFiscalTributoSefazAmVO(tabEntradaProduto_FiscalVO.getTributoSefazAM_id()));
                tabEntradaProduto_FiscalVO.setVlrNfe(rs.getBigDecimal("vlrNfe"));
                tabEntradaProduto_FiscalVO.setVlrTributo(rs.getBigDecimal("vlrTributo"));
                tabEntradaProduto_FiscalVO.setVlrMulta(rs.getBigDecimal("vlrMulta"));
                tabEntradaProduto_FiscalVO.setVlrJuros(rs.getBigDecimal("vlrJuros"));
                tabEntradaProduto_FiscalVO.setVlrTaxa(rs.getBigDecimal("vlrTaxa"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabEntradaProduto_FiscalVO(Connection conn, TabEntradaProduto_FiscalVO tabEntradaProduto_fiscalVO) throws SQLException {
        String comandoSql = "UPDATE tabEntradaProduto_Fiscal SET " +
                "numControle = ?, " +
                "docOrigem = ?, " +
                "fiscalTributoSefazAm_id = ?, " +
                "vlrNfe = ?, " +
                "vlrTributo = ?, " +
                "vlrMulta = ?, " +
                "vlrJuros = ?, " +
                "vlrTaxa = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("String", tabEntradaProduto_fiscalVO.getNumControle()));
        addParametro(new Pair<>("String", tabEntradaProduto_fiscalVO.getDocOrigem()));
        addParametro(new Pair<>("int", String.valueOf(tabEntradaProduto_fiscalVO.getTributoSefazAM_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(tabEntradaProduto_fiscalVO.getVlrNfe())));
        addParametro(new Pair<>("Decimal", String.valueOf(tabEntradaProduto_fiscalVO.getVlrTributo())));
        addParametro(new Pair<>("Decimal", String.valueOf(tabEntradaProduto_fiscalVO.getVlrMulta())));
        addParametro(new Pair<>("Decimal", String.valueOf(tabEntradaProduto_fiscalVO.getVlrJuros())));
        addParametro(new Pair<>("Decimal", String.valueOf(tabEntradaProduto_fiscalVO.getVlrTaxa())));
        addParametro(new Pair<>("int", String.valueOf(tabEntradaProduto_fiscalVO.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaProduto_FiscalVO(Connection conn, TabEntradaProduto_FiscalVO fiscalVO, int entrada_id) throws SQLException {
        String comandoSql = "INSERT INTO tabEntradaProduto_Fiscal " +
                "numControle, " +
                "docOrigem, " +
                "fiscalTributoSefazAm_id, " +
                "vlrNfe, " +
                "vlrTributo, " +
                "vlrMulta, " +
                "vlrJuros, " +
                "vlrTaxa " +
                "VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?) ";
        addNewParametro(new Pair<>("String", fiscalVO.getNumControle()));
        addParametro(new Pair<>("String", fiscalVO.getDocOrigem()));
        addParametro(new Pair<>("int", String.valueOf(fiscalVO.getTributoSefazAM_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(fiscalVO.getVlrNfe())));
        addParametro(new Pair<>("Decimal", String.valueOf(fiscalVO.getVlrTributo())));
        addParametro(new Pair<>("Decimal", String.valueOf(fiscalVO.getVlrMulta())));
        addParametro(new Pair<>("Decimal", String.valueOf(fiscalVO.getVlrJuros())));
        addParametro(new Pair<>("Decimal", String.valueOf(fiscalVO.getVlrTaxa())));
        addParametro(new Pair<>("int", String.valueOf(fiscalVO.getId())));
        int fiscal_id = getInsertBancoDados(conn, comandoSql);
        new RelEntradaProduto_EntradaProdutoFiscalDAO().insertRelEntradaProduto_EntradaProdutoFiscalVO(conn, entrada_id, fiscal_id);
        return fiscal_id;
    }

    public void deleteTabEntradaProduto_FiscalVO(Connection conn, int entradaProduto_fiscal_id) throws SQLException {
        if (entradaProduto_fiscal_id < 0) entradaProduto_fiscal_id = entradaProduto_fiscal_id * (-1);
        String comandoSql = "DELETE FROM tabEntradaProduto_Fiscal WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaProduto_fiscal_id)));
        getDeleteBancoDados(conn, comandoSql);
    }

}
