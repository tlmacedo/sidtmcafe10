package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaFiscalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;

public class TabEntradaFiscalDAO extends ServiceBuscaBancoDados {

    TabEntradaFiscalVO tabEntradaProduto_FiscalVO = null;

    public TabEntradaFiscalVO getTabEntradaFiscalVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEntradaFiscal WHERE id = ? ");
        return tabEntradaProduto_FiscalVO;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabEntradaProduto_FiscalVO = new TabEntradaFiscalVO();
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

    public void updateTabEntradaFiscalVO(Connection conn, TabEntradaFiscalVO entradaFiscalVO) throws SQLException {
        String comandoSql = "UPDATE tabEntradaFiscal SET " +
                "numControle = ?, " +
                "docOrigem = ?, " +
                "fiscalTributoSefazAm_id = ?, " +
                "vlrNfe = ?, " +
                "vlrTributo = ?, " +
                "vlrMulta = ?, " +
                "vlrJuros = ?, " +
                "vlrTaxa = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("String", entradaFiscalVO.getNumControle()));
        addParametro(new Pair<>("String", entradaFiscalVO.getDocOrigem()));
        addParametro(new Pair<>("int", String.valueOf(entradaFiscalVO.getTributoSefazAM_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrNfe())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrTributo())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrMulta())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrJuros())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrTaxa())));
        addParametro(new Pair<>("int", String.valueOf(entradaFiscalVO.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaFiscalVO(Connection conn, TabEntradaFiscalVO entradaFiscalVO, boolean isNfe, int nfeOrCte_id) throws SQLException {
        String comandoSql = "INSERT INTO tabEntradaFiscal (" +
                "numControle, " +
                "docOrigem, " +
                "fiscalTributoSefazAm_id, " +
                "vlrNfe, " +
                "vlrTributo, " +
                "vlrMulta, " +
                "vlrJuros, " +
                "vlrTaxa " +
                ") VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?) ";
        addNewParametro(new Pair<>("String", entradaFiscalVO.getNumControle()));
        addParametro(new Pair<>("String", entradaFiscalVO.getDocOrigem().replaceAll("\\D", "")));
        addParametro(new Pair<>("int", String.valueOf(entradaFiscalVO.getTributoSefazAM_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrNfe())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrTributo())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrMulta())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrJuros())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaFiscalVO.getVlrTaxa())));
        int fiscal_id = getInsertBancoDados(conn, comandoSql);
        if (isNfe)
            new RelEntradaNfe_EntradaFiscalDAO().insertRelEntradaNfe_EntradaFiscalVO(conn, nfeOrCte_id, fiscal_id);
        else
            new RelEntradaCte_EntradaFiscalDAO().insertRelEntradaCte_EntradaFiscalVO(conn, nfeOrCte_id, fiscal_id);
        return fiscal_id;
    }

    public void deleteTabEntradaFiscalVO(Connection conn, int entradaFiscal_id) throws SQLException {
        if (entradaFiscal_id < 0) entradaFiscal_id = entradaFiscal_id * (-1);
        String comandoSql = "DELETE FROM tabEntradaFiscal WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaFiscal_id)));
        getDeleteBancoDados(conn, comandoSql);
    }

}
