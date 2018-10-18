package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEntradaNfeItemVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabEntradaNfeItemDAO extends ServiceBuscaBancoDados {

    TabEntradaNfeItemVO tabEntradaNfeItemVO = null;
    List<TabEntradaNfeItemVO> tabEntradaNfeItemVOList = null;

    public TabEntradaNfeItemVO getTabEntradaNfeItemVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEntradaNfeItem WHERE id = ? ");
        return tabEntradaNfeItemVO;
    }

    public TabEntradaNfeItemVO getTabEntradaNfeItemVO_NfeId(int entradaNfe_id) {
        String comandoSql = "SELECT * FROM tabEntradaNfeItem ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfe_id)));
        getResultSet(comandoSql + "WHERE tabEntradaNfe_id = ? ");
        return tabEntradaNfeItemVO;
    }

    public List<TabEntradaNfeItemVO> getTabEntradaNfeItemVOList() {
        tabEntradaNfeItemVOList = new ArrayList<>();
        getResultSet("SELECT * FROM tabEntradaNfeItem ");
        return tabEntradaNfeItemVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabEntradaNfeItemVO = new TabEntradaNfeItemVO();
                tabEntradaNfeItemVO.setId(rs.getInt("id"));
                tabEntradaNfeItemVO.setLojaDestino_id(rs.getInt("lojaDestino_id"));
                tabEntradaNfeItemVO.setChaveNfe(rs.getString("chaveNfe"));
                tabEntradaNfeItemVO.setNumeroNfe(rs.getInt("numeroNfe"));
                tabEntradaNfeItemVO.setSerieNfe(rs.getInt("serieNfe"));
                tabEntradaNfeItemVO.setModeloNfe_id(rs.getInt("modeloNfe_id"));
                tabEntradaNfeItemVO.setFornecedor_id(rs.getInt("fornecedor_id"));
                tabEntradaNfeItemVO.setDataEmissaoNfe(rs.getTimestamp("dataEmissaoNfe"));
                tabEntradaNfeItemVO.setDataEntradaNfe(rs.getTimestamp("dataEntradaNfe"));
                tabEntradaNfeItemVO.setStatusNfe_id(rs.getInt("statusNfe_id"));
                if (tabEntradaNfeItemVOList != null) tabEntradaNfeItemVOList.add(tabEntradaNfeItemVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEntradaNfeItemVO entradaNfeItem) {
//        entradaNfeItem.setLojaDestinoVO(new TabEmpresaDAO().getTabEmpresaVO(entradaNfeItem.getLojaDestino_id()));
//        entradaNfeItem.setModeloNfeVO(new FiscalModeloNfeCteDAO().getFiscalModeloNfeCteVO(entradaNfeItem.getModeloNfe_id()));
//        entradaNfeItem.setFornecedorVO(new TabEmpresaDAO().getTabEmpresaVO(entradaNfeItem.getFornecedor_id()));
//        entradaNfeItem.setStatusNfeVO(new SisStatusNfeDAO().getSisStatusNfeVO(entradaNfeItem.getStatusNfe_id()));
    }

    public void updateTabEntradaNfeItemVO(Connection conn, TabEntradaNfeItemVO entradaNfeItem) throws SQLException {
        String comandoSql = "UPDATE tabEntradaNfeItem SET " +
                "tabEntradaNfe_id = ?, " +
                "tabProduto_id = ?, " +
                "codProduto = ?, " +
                "descricao = ?, " +
                "peso = ?, " +
                "qtd = ?, " +
                "vlrFabrica = ?, " +
                "vlrTotalBruto = ?, " +
                "vlrImposto = ?, " +
                "vlrDesconto = ?, " +
                "vlrLiquido = ?, " +
                "estoque = ?, " +
                "varejo = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getTabEntradaNfe_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getTabProduto_id())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getCodProduto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getDescricao())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getPeso())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getQtd())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrFabrica())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrTotalBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrImposto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrDesconto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrLiquido())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getEstoque())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVarejo())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getId())));

        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaNfeItemVO(Connection conn, TabEntradaNfeItemVO entradaNfeItem) throws SQLException {
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
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getLojaDestino_id())));
        addParametro(new Pair<>("String", entradaNfeItem.getChaveNfe()));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getNumeroNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getSerieNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getModeloNfe_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getFornecedor_id())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaNfeItem.getDataEmissaoNfe())));
        addParametro(new Pair<>("timestamp", String.valueOf(entradaNfeItem.getDataEntradaNfe())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getStatusNfe_id())));

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEntradaNfeItemVO(Connection conn, int entradaProduto_id) throws SQLException {
        if (entradaProduto_id < 0) entradaProduto_id = entradaProduto_id * (-1);
        String comandoSql = "DELETE FROM tabEntradaNfe WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaProduto_id)));
        getDeleteBancoDados(conn, comandoSql);
    }

}
