package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalModeloNfeCteVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisStatusNfeVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmpresaVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaNfeVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabEntradaNfeDAO extends ServiceBuscaBancoDados {

    TabEntradaNfeVO tabEntradaNfeVO = null;
    List<TabEntradaNfeVO> tabEntradaNfeVOList = null;

    public TabEntradaNfeVO getTabEntradaNfeVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEntradaNfe WHERE id = ? ");
        if (tabEntradaNfeVO != null)
            addObjetosPesquisa(tabEntradaNfeVO);
        return tabEntradaNfeVO;
    }

    public TabEntradaNfeVO getTabEntradaNfeVO(String num_chaveNfe) {
        String comandoSql = "SELECT * FROM tabEntradaNfe ";
        addNewParametro(new Pair<>("String", num_chaveNfe));
        if (num_chaveNfe.length() == 44)
            getResultSet(comandoSql + "WHERE chaveNfe = ? ");
        else
            getResultSet(comandoSql + "WHERE numeroNfe = ? ");
        if (tabEntradaNfeVO != null)
            addObjetosPesquisa(tabEntradaNfeVO);
        return tabEntradaNfeVO;
    }

    public List<TabEntradaNfeVO> getTabEntradaNfeVOList() {
        tabEntradaNfeVOList = new ArrayList<>();
        getResultSet("SELECT * FROM tabEntradaNfe ");
        if (tabEntradaNfeVO != null)
            for (TabEntradaNfeVO entradaNfe : tabEntradaNfeVOList)
                addObjetosPesquisa(entradaNfe);
        return tabEntradaNfeVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabEntradaNfeVO = new TabEntradaNfeVO();
                tabEntradaNfeVO.setId(rs.getInt("id"));
                tabEntradaNfeVO.setLojaDestino_id(rs.getInt("lojaDestino_id"));
                tabEntradaNfeVO.setChaveNfe(rs.getString("chaveNfe"));
                tabEntradaNfeVO.setNumeroNfe(rs.getInt("numeroNfe"));
                tabEntradaNfeVO.setSerieNfe(rs.getInt("serieNfe"));
                tabEntradaNfeVO.setModeloNfe_id(rs.getInt("modeloNfe_id"));
                tabEntradaNfeVO.setFornecedor_id(rs.getInt("fornecedor_id"));
                tabEntradaNfeVO.setDataEmissaoNfe(rs.getTimestamp("dataEmissaoNfe"));
                tabEntradaNfeVO.setDataEntradaNfe(rs.getTimestamp("dataEntradaNfe"));
                tabEntradaNfeVO.setStatusNfe_id(rs.getInt("statusNfe_id"));
                if (tabEntradaNfeVOList != null) tabEntradaNfeVOList.add(tabEntradaNfeVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEntradaNfeVO entradaNfe) {
        entradaNfe.setLojaDestinoVO(new TabEmpresaDAO().getTabEmpresaVO(entradaNfe.getLojaDestino_id()));
        entradaNfe.setModeloNfeVO(new FiscalModeloNfeCteDAO().getFiscalModeloNfeCteVO(entradaNfe.getModeloNfe_id()));
        entradaNfe.setFornecedorVO(new TabEmpresaDAO().getTabEmpresaVO(entradaNfe.getFornecedor_id()));
        entradaNfe.setStatusNfeVO(new SisStatusNfeDAO().getSisStatusNfeVO(entradaNfe.getStatusNfe_id()));
    }

    public void updateTabEntradaNfeVO(Connection conn, TabEntradaNfeVO entradaNfe) throws SQLException {
        String comandoSql = "UPDATE tabEntradaNfe SET " +
                "lojaDestino_id = ?, " +
                "chaveNfe = ?, " +
                "numeroNfe = ?, " +
                "serieNfe = ?, " +
                "modeloNfe_id = ?, " +
                "fornecedor_id = ?, " +
                "dataEmissaoNfe = ?, " +
                "dataEntradaNfe = ?, " +
                "statusNfe_id = ? " +
                "WHERE id = ?";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe.getLojaDestino_id())));
        addParametro(new Pair<>("String", entradaNfe.getChaveNfe()));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getNumeroNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getSerieNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getModeloNfe_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getFornecedor_id())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaNfe.getDataEmissaoNfe())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaNfe.getDataEntradaNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getStatusNfe_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaNfeVO(Connection conn, TabEntradaNfeVO entradaNfe) throws SQLException {
        String comandoSql = "INSERT INTO tabEntradaNfe " +
                "(lojaDestino_id, " +
                "chaveNfe, " +
                "numeroNfe, " +
                "serieNfe, " +
                "modeloNfe_id, " +
                "fornecedor_id, " +
                "dataEmissaoNfe, " +
                "dataEntradaNfe, " +
                "sisStatusNfe_id) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?) ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe.getLojaDestino_id())));
        addParametro(new Pair<>("String", entradaNfe.getChaveNfe()));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getNumeroNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getSerieNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getModeloNfe_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getFornecedor_id())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaNfe.getDataEmissaoNfe())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaNfe.getDataEntradaNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfe.getStatusNfe_id())));

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEntradaNfeVO(Connection conn, int entradaNfe_id) throws SQLException {
        if (entradaNfe_id < 0) entradaNfe_id = entradaNfe_id * (-1);
        String comandoSql = "DELETE FROM tabEntradaNfe WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        getDeleteBancoDados(conn, comandoSql);
    }
}
