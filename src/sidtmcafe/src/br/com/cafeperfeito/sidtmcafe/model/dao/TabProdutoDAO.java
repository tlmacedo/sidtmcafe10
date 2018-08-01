package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProduto_CodBarraVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoDAO extends BuscaBancoDados {

    ResultSet rs;
    TabProdutoVO tabProdutoVO;
    List<TabProdutoVO> tabProdutoVOList;
    boolean returnList = false;

    public TabProdutoVO getTabProdutoVO(int id) {
        getResultSet(String.format("SELECT * FROM tabProduto WHERE id = %d ORDER BY descricao", id), false);
        if (tabProdutoVO != null)
            addObjetosPesquisa(tabProdutoVO);
        return tabProdutoVO;
    }

    public TabProdutoVO getTabProdutoVO(String codigo) {
        getResultSet(String.format("SELECT * FROM tabProduto WHERE codigo = '%s' ORDER BY descricao", codigo), false);
        if (tabProdutoVO != null)
            addObjetosPesquisa(tabProdutoVO);
        return tabProdutoVO;
    }

    public TabProdutoVO getTabProduto_CodBarraVO(String codBarra) {
        TabProduto_CodBarraVO codBarraVO;
        if ((codBarraVO = new TabProduto_CodBarraDAO().getTabProduto_codBarraVO(codBarra)) == null) return null;
        int produto_id = new RelProduto_CodBarraDAO().getRelProduto_CodBarraVO(0, codBarraVO.getId()).getTabProduto_id();
        if (produto_id == 0) return null;
        getResultSet(String.format("SELECT * FROM tabProduto WHERE id = %d ORDER BY descricao", produto_id), false);
        if (tabProdutoVO != null)
            addObjetosPesquisa(tabProdutoVO);
        return tabProdutoVO;
    }

    public List<TabProdutoVO> getTabProdutoVOList() {
        tabProdutoVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM tabProduto ORDER BY descricao"), true);
        if (tabProdutoVO != null)
            for (TabProdutoVO produtoVO : tabProdutoVOList)
                addObjetosPesquisa(produtoVO);
        return tabProdutoVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabProdutoVO = new TabProdutoVO();
                tabProdutoVO.setId(rs.getInt("id"));
                tabProdutoVO.setCodigo(rs.getString("codigo"));
                tabProdutoVO.setDescricao(rs.getString("descricao"));
                tabProdutoVO.setPeso(rs.getDouble("peso"));
                tabProdutoVO.setSisUnidadeComercial_id(rs.getInt("sisUnidadeComercial_id"));
                tabProdutoVO.setSisSituacaoSistema_id(rs.getInt("sisSituacaoSistema_id"));
                tabProdutoVO.setPrecoFabrica(rs.getDouble("precoFabrica"));
                tabProdutoVO.setPrecoVenda(rs.getDouble("precoVenda"));
                tabProdutoVO.setVarejo(rs.getInt("varejo"));
                tabProdutoVO.setPrecoUltimoFrete(rs.getDouble("precoUltimoFrete"));
                tabProdutoVO.setComissao(rs.getDouble("comissao"));
                tabProdutoVO.setFiscalCestNcm_id(rs.getInt("fiscalCestncm_id"));
                tabProdutoVO.setNcm(rs.getString("ncm"));
                tabProdutoVO.setCest(rs.getString("cest"));
                tabProdutoVO.setFiscalCSTOrigem_id(rs.getInt("fiscalCSTOrigem_id"));
                tabProdutoVO.setFiscalICMS_id(rs.getInt("fiscalICMS_id"));
                tabProdutoVO.setFiscalPIS_id(rs.getInt("fiscalPIS_id"));
                tabProdutoVO.setFiscalCOFINS_id(rs.getInt("fiscalCOFINS_id"));
                tabProdutoVO.setNfeGenero(rs.getString("nfeGenero"));
                tabProdutoVO.setUsuarioCadastro_id(rs.getInt("usuarioCadastro_id"));
                tabProdutoVO.setDataCadastro(rs.getTimestamp("dataCadastro"));
                tabProdutoVO.setUsuarioAtualizacao_id(rs.getInt("usuarioAtualizacao_id"));
                tabProdutoVO.setDataAtualizacao(rs.getTimestamp("dataAtualizacao"));
                if (returnList) tabProdutoVOList.add(tabProdutoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabProdutoVO produto) {
        produto.setSisUnidadeComercialVO(new SisUnidadeComercialDAO().getSisUnidadeComercialVO(produto.getSisUnidadeComercial_id()));

        produto.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(produto.getSisSituacaoSistema_id()));

        produto.setFiscalCestNcmVO(new FiscalCestNcmDAO().getFiscalCestNcmVO(produto.getFiscalCestNcm_id()));

        produto.setFiscalCstOrigemVO(new FiscalCstOrigemDAO().getFiscalCstOrigemVO(produto.getFiscalCSTOrigem_id()));

        produto.setFiscalIcmsVO(new FiscalIcmsDAO().getFiscalIcmsVO((produto.getFiscalICMS_id())));

        produto.setFiscalPisVO(new FiscalPisCofinsDAO().getFiscalPisCofinsVO(produto.getFiscalPIS_id()));

        produto.setFiscalCofinsVO(new FiscalPisCofinsDAO().getFiscalPisCofinsVO(produto.getFiscalCOFINS_id()));

        produto.setUsuarioCadastroVO(new TabColaboradorDAO().getTabColaboradorVO(produto.getUsuarioCadastro_id(), false));

        produto.setUsuarioAtualizacaoVO(new TabColaboradorDAO().getTabColaboradorVO(produto.getUsuarioAtualizacao_id(), false));

        List<TabProduto_CodBarraVO> codBarraVOList = new ArrayList<>();
        new RelProduto_CodBarraDAO().getRelProduto_CodBarraVOList(produto.getId()).stream()
                .forEach(relProduto_codBarraVO -> {
                    codBarraVOList.add(new TabProduto_CodBarraDAO().getTabProduto_codBarraVO(relProduto_codBarraVO.getTabProduto_CodBarra_id()));
                });
        produto.setCodBarraVOList(codBarraVOList);
    }

    public void updateTabProdutoVO(Connection conn, TabProdutoVO produto) throws SQLException {
        String comandoSql = String.format("UPDATE tabProduto SET codigo = '%s', descricao = '%s', peso = %f, " +
                        "sisUnidadeComercial_id = %d, sisSituacaoSistema_id = %d, precoFabrica = %f, precoVenda = %f, " +
                        "varejo = %d, precoUltimoFrete = %f, comissao = %f, ncm = '%s', cest = '%s', fiscalCestNcm_id = %d, " +
                        "fiscalCstOrigem_id = %d, fiscalIcms_id = %d, fiscalPis_id = %d, fiscalCofins_id = %d, " +
                        "nfeGenero = '%s', usuarioAtualizacao_id = %d WHERE id = %d",
                produto.getCodigo(), produto.getDescricao(), produto.getPeso(), produto.getSisUnidadeComercial_id(),
                produto.getSisSituacaoSistema_id(), produto.getPrecoFabrica(), produto.getPrecoVenda(),
                produto.getVarejo(), produto.getPrecoUltimoFrete(), produto.getComissao(), produto.getNcm(),
                produto.getCest(), produto.getFiscalCestNcm_id(), produto.getFiscalCSTOrigem_id(),
                produto.getFiscalICMS_id(), produto.getFiscalPIS_id(), produto.getFiscalCOFINS_id(),
                produto.getNfeGenero(), produto.getUsuarioAtualizacao_id(), produto.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProdutoVO(Connection conn, TabProdutoVO produtoVO) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabProduto (codigo, descricao, peso, sisUnidadeComercial_id, " +
                        "sisSituacaoSistema_id, precoFabrica, precoVenda, varejo, precoUltimoFrete, comissao, ncm, cest, " +
                        "fiscalCestNcm_id, fiscalCSTOrigem_id, fiscalICMS_id, fiscalPIS_id, fiscalCOFINS_id, nfeGenero, " +
                        "usuarioCadastro_id) VALUES('%s', '%s', %f, %d, %d, %f, %f, %d, %f, %f, '%s', '%s', %d, %d, %d, %d, " +
                        "%d, '%s', %d)",
                produtoVO.getCodigo(), produtoVO.getDescricao(), produtoVO.getPeso(), produtoVO.getSisUnidadeComercial_id(),
                produtoVO.getSisSituacaoSistema_id(), produtoVO.getPrecoFabrica(), produtoVO.getPrecoVenda(),
                produtoVO.getVarejo(), produtoVO.getPrecoUltimoFrete(), produtoVO.getComissao(), produtoVO.getNcm(),
                produtoVO.getCest(), produtoVO.getFiscalCestNcm_id(), produtoVO.getFiscalCSTOrigem_id(),
                produtoVO.getFiscalICMS_id(), produtoVO.getFiscalPIS_id(), produtoVO.getFiscalCOFINS_id(),
                produtoVO.getNfeGenero(), produtoVO.getUsuarioCadastro_id());
        System.out.printf("Peso; [%s]\n", produtoVO.getPeso());
        System.out.printf("setPrecoFabrica; [%s]\n", produtoVO.getPrecoFabrica());
        System.out.printf("setPrecoVenda; [%s]\n", produtoVO.getPrecoVenda());
        System.out.printf("setPrecoUltimoFrete; [%s]\n", produtoVO.getPrecoUltimoFrete());
        System.out.printf("setComissao; [%s]\n", produtoVO.getComissao());
        System.out.printf("comandoSql: [%s]", comandoSql);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabProdutoVO(Connection conn, int produto_id) throws SQLException {
        if (produto_id < 0) produto_id = produto_id * (-1);
        String comandoSql = String.format("DELETE FROM tabProduto WHERE id = %d", produto_id);
        getDeleteBancoDados(conn, comandoSql);
    }
}
