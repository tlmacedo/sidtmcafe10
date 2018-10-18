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
                tabEntradaNfeItemVO.setTabEntradaNfe_id(rs.getInt("tabEntradaNfe_id"));
                tabEntradaNfeItemVO.setTabProduto_id(rs.getInt("tabProduto_id"));
                tabEntradaNfeItemVO.setCodProduto(rs.getString("codProduto"));
                tabEntradaNfeItemVO.setDescricao(rs.getString("descricao"));
                tabEntradaNfeItemVO.setPeso(rs.getBigDecimal("peso"));
                tabEntradaNfeItemVO.setQtd(rs.getInt("qtd"));
                tabEntradaNfeItemVO.setVlrFabrica(rs.getBigDecimal("vlrFabrica"));
                tabEntradaNfeItemVO.setVlrTotalBruto(rs.getBigDecimal("vlrTotalBruto"));
                tabEntradaNfeItemVO.setVlrImposto(rs.getBigDecimal("vlrImposto"));
                tabEntradaNfeItemVO.setVlrDesconto(rs.getBigDecimal("vlrDesconto"));
                tabEntradaNfeItemVO.setVlrLiquido(rs.getBigDecimal("vlrLiquido"));
                tabEntradaNfeItemVO.setEstoque(rs.getInt("estoque"));
                tabEntradaNfeItemVO.setVarejo(rs.getInt("varejo"));
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
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getTabProduto_id())));
        addParametro(new Pair<>("String", String.valueOf(entradaNfeItem.getCodProduto())));
        addParametro(new Pair<>("String", String.valueOf(entradaNfeItem.getDescricao())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getPeso())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getQtd())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrFabrica())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrTotalBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrImposto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrDesconto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrLiquido())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getEstoque())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getVarejo())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getId())));

        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEntradaNfeItemVO(Connection conn, TabEntradaNfeItemVO entradaNfeItem) throws SQLException {
        String comandoSql = "INSERT INTO tabEntradaNfeItem (" +
                "tabEntradaNfe_id, " +
                "tabProduto_id, " +
                "codProduto, " +
                "descricao, " +
                "peso, " +
                "qtd, " +
                "vlrFabrica, " +
                "vlrTotalBruto, " +
                "vlrImposto, " +
                "vlrDesconto, " +
                "vlrLiquido, " +
                "estoque, " +
                "varejo) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ? " +
                ") ";
        addNewParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getTabEntradaNfe_id())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getTabProduto_id())));
        addParametro(new Pair<>("String", String.valueOf(entradaNfeItem.getCodProduto())));
        addParametro(new Pair<>("String", String.valueOf(entradaNfeItem.getDescricao())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getPeso())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getQtd())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrFabrica())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrTotalBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrImposto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrDesconto())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getVlrLiquido())));
        addParametro(new Pair<>("Decimal", String.valueOf(entradaNfeItem.getEstoque())));
        addParametro(new Pair<>("int", String.valueOf(entradaNfeItem.getVarejo())));

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEntradaNfeItemVO(Connection conn, int id) throws SQLException {
        if (id < 0) id = id * (-1);
        String comandoSql = "DELETE FROM tabEntradaNfeItem WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getDeleteBancoDados(conn, comandoSql);
    }

}
