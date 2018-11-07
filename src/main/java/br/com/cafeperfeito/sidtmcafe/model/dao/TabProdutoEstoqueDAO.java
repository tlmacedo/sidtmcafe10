package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoEstoqueVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoEstoqueDAO extends ServiceBuscaBancoDados {

    TabProdutoEstoqueVO codBarraVO = null;
    List<TabProdutoEstoqueVO> codBarraVOList = null;

    public TabProdutoEstoqueVO getEstoqueVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabProdutoEstoque WHERE id = ? ");
        return codBarraVO;
    }


    public List<TabProdutoEstoqueVO> getTabProdutoEstoqueVOList(int produto_id) {
        codBarraVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(produto_id)));
        getResultSet("SELECT * FROM tabProdutoEstoque WHERE tabProduto_id = ? ");
        return codBarraVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                codBarraVO = new TabProdutoEstoqueVO();
                codBarraVO.setId(rs.getInt("id"));
                codBarraVO.setTabProduto_id(rs.getInt("tabProduto_id"));
                codBarraVO.setQtd(rs.getInt("qtd"));
                codBarraVO.setLote(rs.getString("lote"));
                codBarraVO.setValidade(rs.getTimestamp("validade"));
                codBarraVO.setVlrBruto(rs.getBigDecimal("vlrBruto"));
                codBarraVO.setVlrImposto(rs.getBigDecimal("vlrImposto"));
                codBarraVO.setVlrCteBruto(rs.getBigDecimal("vlrCteBruto"));
                codBarraVO.setVlrCteImposto(rs.getBigDecimal("vlrCteImposto"));
                codBarraVO.setVlrCteLiquido(rs.getBigDecimal("vlrCteLiquido"));
                codBarraVO.setVlrLiquido(rs.getBigDecimal("vlrLiquido"));
                codBarraVO.setUsuarioCadastro_id(rs.getInt("usuarioCadastro_id"));
                codBarraVO.setDataCadastro(rs.getTimestamp("dataCadastro"));
                codBarraVO.setDocOrigem(rs.getString("docOrigem"));
                codBarraVO.setChaveNfeEntrada(rs.getString("chaveNfeEntrada"));
                if (codBarraVOList != null) codBarraVOList.add(codBarraVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabProdutoEstoqueVO(Connection conn, TabProdutoEstoqueVO produtoEstoque) throws SQLException {
        String comandoSql = "UPDATE tabProdutoEstoque SET " +
                "tabProduto_id = ?, " +
                "qtd = ?, " +
                "lote = ?, " +
                "validade = ?, " +
                "vlrBruto = ?, " +
                "vlrImposto = ?, " +
                "vlrCteBruto = ?, " +
                "vlrCteImposto = ?, " +
                "vlrCteLiquido = ?, " +
                "vlrLiquido = ?, " +
                "usuarioCadastro_id = ?, " +
                "docOrigem = ?, " +
                "chaveNfeEntrada = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(produtoEstoque.getTabProduto_id())));
        addParametro(new Pair<>("int", String.valueOf(produtoEstoque.getQtd())));
        addParametro(new Pair<>("String", produtoEstoque.getLote()));
        addParametro(new Pair<>("date", produtoEstoque.getValidade().toString()));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrImposto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrCteBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrCteImposto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrCteLiquido())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrLiquido())));
        addParametro(new Pair<>("int", String.valueOf(produtoEstoque.getUsuarioCadastro_id())));
        addParametro(new Pair<>("String", String.valueOf(produtoEstoque.getDocOrigem())));
        addParametro(new Pair<>("String", String.valueOf(produtoEstoque.getChaveNfeEntrada())));
        addParametro(new Pair<>("int", String.valueOf(produtoEstoque.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProdutoEstoqueVO(Connection conn, TabProdutoEstoqueVO produtoEstoque) throws SQLException {
        String comandoSql = "INSERT INTO tabProdutoEstoque (" +
                "tabProduto_id, " +
                "qtd, " +
                "lote, " +
                "validade, " +
                "vlrBruto, " +
                "vlrImposto, " +
                "vlrCteBruto, " +
                "vlrCteImposto, " +
                "vlrCteLiquido, " +
                "vlrLiquido, " +
                "usuarioCadastro_id, " +
                "docOrigem, " +
                "chaveNfeEntrada " +
                ") VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ? " +
                ") ";
        addNewParametro(new Pair<>("int", String.valueOf(produtoEstoque.getTabProduto_id())));
        addParametro(new Pair<>("int", String.valueOf(produtoEstoque.getQtd())));
        addParametro(new Pair<>("String", produtoEstoque.getLote()));
        addParametro(new Pair<>("date", produtoEstoque.getValidade().toString()));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrImposto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrCteBruto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrCteImposto())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrCteLiquido())));
        addParametro(new Pair<>("Decimal", String.valueOf(produtoEstoque.getVlrLiquido())));
        addParametro(new Pair<>("int", String.valueOf(produtoEstoque.getUsuarioCadastro_id())));
        addParametro(new Pair<>("String", String.valueOf(produtoEstoque.getDocOrigem())));
        addParametro(new Pair<>("String", String.valueOf(produtoEstoque.getChaveNfeEntrada())));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabProdutoEstoqueVO(Connection conn, int estoque_id) throws SQLException {
        if (estoque_id < 0) estoque_id = estoque_id * (-1);
        String comandoSql = "DELETE FROM tabProdutoEstoque WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(estoque_id)));
        getDeleteBancoDados(conn, comandoSql);
    }

}
