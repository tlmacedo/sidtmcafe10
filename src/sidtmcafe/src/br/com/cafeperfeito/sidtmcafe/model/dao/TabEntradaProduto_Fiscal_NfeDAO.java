package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalTributoSefazAmVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaProduto_Fiscal_NfeVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TabEntradaProduto_Fiscal_NfeDAO extends ServiceBuscaBancoDados {

    TabEntradaProduto_Fiscal_NfeVO tabEntradaProduto_fiscal_nfeVO = null;

    public TabEntradaProduto_Fiscal_NfeVO getTabEntradaProduto_fiscal_nfeVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEntradaProduto_Fiscal_Nfe WHERE id = ? ");
        return tabEntradaProduto_fiscal_nfeVO;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabEntradaProduto_fiscal_nfeVO = new TabEntradaProduto_Fiscal_NfeVO();
                tabEntradaProduto_fiscal_nfeVO.setId(rs.getInt("id"));
                tabEntradaProduto_fiscal_nfeVO.setNumControle(rs.getString("numControle"));
                tabEntradaProduto_fiscal_nfeVO.setDocOrigem(rs.getString("docOrigem"));
                tabEntradaProduto_fiscal_nfeVO.setTributoSefazAM_id(rs.getInt("fiscalTributoSefazAm_id"));
                tabEntradaProduto_fiscal_nfeVO.setTributoSefazAmVO(new FiscalTributoSefazAmDAO()
                        .getFiscalTributoSefazAmVO(tabEntradaProduto_fiscal_nfeVO.getTributoSefazAM_id()));
                tabEntradaProduto_fiscal_nfeVO.setVlrNfe(rs.getBigDecimal("vlrNfe"));
                tabEntradaProduto_fiscal_nfeVO.setVlrTributo(rs.getBigDecimal("vlrTributo"));
                tabEntradaProduto_fiscal_nfeVO.setVlrMulta(rs.getBigDecimal("vlrMulta"));
                tabEntradaProduto_fiscal_nfeVO.setVlrJuros(rs.getBigDecimal("vlrJuros"));
                tabEntradaProduto_fiscal_nfeVO.setVlrTaxa(rs.getBigDecimal("vlrTaxa"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabEntradaProduto_Fiscal_NfeVO(Connection conn, TabEntradaProduto_Fiscal_NfeVO entradaProduto_fiscal_nfeVO) throws SQLException {
        String comandoSql = "UPDATE tabEntradaProduto_Fiscal_Nfe SET " +
                "numControle = ?, " +
                "docOrigem = ?, " +
                "fiscalTributoSefazAm_id = ?, " +
                "vlrNfe = ?, " +
                "vlrTributo = ?, " +
                "vlrMulta = ?, " +
                "vlrJuros = ?, " +
                "vlrTaxa = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("String", entradaProduto_fiscal_nfeVO.getNumControle()));
        addParametro(new Pair<>("String", entradaProduto_fiscal_nfeVO.getDocOrigem()));
        addParametro(new Pair<>("int", String.valueOf(entradaProduto_fiscal_nfeVO.getTributoSefazAM_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrNfe())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrTributo())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrMulta())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrJuros())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrTaxa())));
        addParametro(new Pair<>("int", String.valueOf(entradaProduto_fiscal_nfeVO.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaProduto_Fiscal_NfeVO(Connection conn, TabEntradaProduto_Fiscal_NfeVO entradaProduto_fiscal_nfeVO) throws SQLException {
        String comandoSql = "INSERT INTO tabEntradaProduto_Fiscal_Nfe " +
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
        addNewParametro(new Pair<>("String", entradaProduto_fiscal_nfeVO.getNumControle()));
        addParametro(new Pair<>("String", entradaProduto_fiscal_nfeVO.getDocOrigem()));
        addParametro(new Pair<>("int", String.valueOf(entradaProduto_fiscal_nfeVO.getTributoSefazAM_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrNfe())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrTributo())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrMulta())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrJuros())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaProduto_fiscal_nfeVO.getVlrTaxa())));
        addParametro(new Pair<>("int", String.valueOf(entradaProduto_fiscal_nfeVO.getId())));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEntradaProduto_Fiscal_NfeVO(Connection conn, int entradaProduto_fiscal_nfe_id) throws SQLException {
        if (entradaProduto_fiscal_nfe_id < 0) entradaProduto_fiscal_nfe_id = entradaProduto_fiscal_nfe_id * (-1);
        String comandoSql = "DELETE FROM tabEntradaProduto_Fiscal_Nfe WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaProduto_fiscal_nfe_id)));
        getDeleteBancoDados(conn, comandoSql);
    }

}
