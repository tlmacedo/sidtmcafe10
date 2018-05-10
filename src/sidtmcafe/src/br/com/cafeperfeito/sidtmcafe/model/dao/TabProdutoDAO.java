package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoEanVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabProdutoDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    TabProdutoVO tabProdutoVO;
    List<TabProdutoVO> tabProdutoVOList;

    public TabProdutoVO getTabProdutoVO(int id) {
        buscaTabProdutoVO(id);
        if (tabProdutoVO != null)
            addObjetosPesquisa(tabProdutoVO);
        return tabProdutoVO;
    }

    public List<TabProdutoVO> getTabProdutoVOList() {
        buscaTabProdutoVO(0);
        if (tabProdutoVOList != null)
            for (TabProdutoVO produtoVO : tabProdutoVOList)
                addObjetosPesquisa(produtoVO);
        return tabProdutoVOList;
    }

    void buscaTabProdutoVO(int id) {
        comandoSql = "SELECT id, codigo, descricao, peso, sisUnidadeComercial_id, sisSituacaoSistema_id, precoFabrica, precoVenda, varejo, ";
        comandoSql += "precoUltimoFrete, comissao, fiscalNcm, fiscalCestNcm_id, fiscalCSTOrigem_id, fiscalICMS_id, fiscalPIS_id, fiscalCOFINS_id, ";
        comandoSql += "nfeGenero, usuarioCadastro_id, dataCadastro, usuarioAtualizacao_id, dataAtualizacao ";
        comandoSql += "FROM tabProduto ";
        if (id > 0) comandoSql += "WHERE id = " + id + " ";
        comandoSql += "ORDER BY descricao ";

        if (id == 0) tabProdutoVOList = new ArrayList<TabProdutoVO>();
        System.out.println("comandoSql: [" + comandoSql + "]");
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
                tabProdutoVO.setFiscalNcm(rs.getString("fiscalNcm"));
                tabProdutoVO.setFiscalCestNcm_id(rs.getInt("fiscalCestncm_id"));
                tabProdutoVO.setFiscalCSTOrigem_id(rs.getInt("fiscalCSTOrigem_id"));
                tabProdutoVO.setFiscalICMS_id(rs.getInt("fiscalICMS_id"));
                tabProdutoVO.setFiscalPIS_id(rs.getInt("fiscalPIS_id"));
                tabProdutoVO.setFiscalCOFINS_id(rs.getInt("fiscalCOFINS_id"));
                tabProdutoVO.setNfeGenero(rs.getString("nfeGenero"));
                tabProdutoVO.setUsuarioCadastro_id(rs.getInt("usuarioCadastro_id"));
                tabProdutoVO.setDataCadastro(rs.getTimestamp("dataCadastro"));
                tabProdutoVO.setUsuarioAtualizacao_id(rs.getInt("usuarioAtualizacao_id"));
                tabProdutoVO.setDataAtualizacao(rs.getTimestamp("dataAtualizacao"));

                if (id == 0) tabProdutoVOList.add(tabProdutoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    void addObjetosPesquisa(TabProdutoVO produto) {
        produto.setSisUnidadeComercialVO(new SisUnidadeComercialDAO().getSisUnidadeComercialVO(produto.getSisUnidadeComercial_id()));

        produto.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(produto.getSisSituacaoSistema_id()));

        produto.setFiscalCestNcmVO(new FiscalCestNcmDAO().getFiscalCestNcmVO(produto.getFiscalCestNcm_id()));

        produto.setFiscalCSTOrigemVO(new FiscalCSTOrigemDAO().getFiscalCSTOrigemVO(produto.getFiscalCSTOrigem_id()));

        produto.setFiscalICMSVO(new FiscalICMSDAO().getFiscalICMSVO(produto.getFiscalICMS_id()));

        produto.setFiscalPISVO(new FiscalPISCOFINSDAO().getFiscalPISCOFINSVO(produto.getFiscalPIS_id()));

        produto.setFiscalCOFINSVO(new FiscalPISCOFINSDAO().getFiscalPISCOFINSVO(produto.getFiscalCOFINS_id()));

        produto.setUsuarioCadastroVO(new TabColaboradorDAO().getTabColaboradorVO_Simples(produto.getUsuarioCadastro_id()));

        produto.setUsuarioAtualizacaoVO(new TabColaboradorDAO().getTabColaboradorVO_Simples(produto.getUsuarioAtualizacao_id()));

        List<TabProdutoEanVO> tabProdutoEanVOList = new ArrayList<TabProdutoEanVO>(new TabProdutoEanDAO().getTabProdutoEanVOList(produto.getId()));

        produto.setTabProdutoEanVOList(tabProdutoEanVOList);
    }

    public void updateTabProdutoVO(Connection conn, TabProdutoVO produtoVO) throws SQLException {
        comandoSql = "UPDATE tabProduto SET ";
        comandoSql += "codigo = '" + produtoVO.getCodigo() + "', ";
        comandoSql += "descricao = '" + produtoVO.getDescricao() + "', ";
        comandoSql += "peso = " + produtoVO.getPeso() + ", ";
        comandoSql += "sisUnidadeComercial_id = " + produtoVO.getSisUnidadeComercial_id() + ", ";
        comandoSql += "sisSituacaoSistema_id = " + produtoVO.getSisSituacaoSistema_id() + ", ";
        comandoSql += "precoFabrica = " + produtoVO.getPrecoFabrica() + ", ";
        comandoSql += "precoVenda = " + produtoVO.getPrecoVenda() + ", ";
        comandoSql += "varejo = " + produtoVO.getVarejo() + ", ";
        comandoSql += "precoUltimoFrete = " + produtoVO.getPrecoUltimoFrete() + ", ";
        comandoSql += "comissao = " + produtoVO.getComissao() + ", ";
        comandoSql += "fiscalNcm = '" + produtoVO.getFiscalNcm() + "', ";
        comandoSql += "fiscalCestNcm_id = " + produtoVO.getFiscalCestNcm_id() + ", ";
        comandoSql += "fiscalCSTOrigem_id = " + produtoVO.getFiscalCSTOrigem_id() + ", ";
        comandoSql += "fiscalICMS_id = " + produtoVO.getFiscalICMS_id() + ", ";
        comandoSql += "fiscalPIS_id = " + produtoVO.getFiscalPIS_id() + ", ";
        comandoSql += "fiscalCOFINS_id = " + produtoVO.getFiscalCOFINS_id() + ", ";
        comandoSql += "nfeGenero = '" + produtoVO.getNfeGenero() + "', ";
        comandoSql += "usuarioAtualizacao_id = " + produtoVO.getUsuarioAtualizacao_id() + " ";
        comandoSql += "WHERE id = " + produtoVO.getId() + " ";

        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabProdutoVO(Connection conn, TabProdutoVO produtoVO) throws SQLException {
        comandoSql = "INSERT INTO tabProduto ";
        comandoSql += "(codigo, descricao, peso, sisUnidadeComercial_id, sisSituacaoSistema_id, precoFabrica, precoVenda, varejo, ";
        comandoSql += "precoUltimoFrete, comissao, fiscalNcm, fiscalCestNcm_id, fiscalCSTOrigem_id, fiscalICMS_id, fiscalPIS_id, fiscalCOFINS_id, ";
        comandoSql += "nfeGenero, usuarioCadastro_id) ";
        comandoSql += "VALUES ( ";
        comandoSql += "'" + produtoVO.getCodigo() + "', ";
        comandoSql += "'" + produtoVO.getDescricao() + "', ";
        comandoSql += produtoVO.getPeso() + ", ";
        comandoSql += produtoVO.getSisUnidadeComercial_id() + ", ";
        comandoSql += produtoVO.getSisSituacaoSistema_id() + ", ";
        comandoSql += produtoVO.getPrecoFabrica() + ", ";
        comandoSql += produtoVO.getPrecoVenda() + ", ";
        comandoSql += produtoVO.getVarejo() + ", ";
        comandoSql += produtoVO.getPrecoUltimoFrete() + ", ";
        comandoSql += produtoVO.getComissao() + ", ";
        comandoSql += "'" + produtoVO.getFiscalNcm() + "', ";
        comandoSql += produtoVO.getFiscalCestNcm_id() + ", ";
        comandoSql += produtoVO.getFiscalCSTOrigem_id() + ", ";
        comandoSql += produtoVO.getFiscalICMS_id() + ", ";
        comandoSql += produtoVO.getFiscalPIS_id() + ", ";
        comandoSql += produtoVO.getFiscalCOFINS_id() + ", ";
        comandoSql += "'" + produtoVO.getNfeGenero() + "', ";
        comandoSql += produtoVO.getUsuarioCadastro_id() + " ";
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabProdutoVO(Connection conn, TabProdutoVO produtoVO) throws SQLException {
        comandoSql = "DELETE ";
        comandoSql += "FROM tabProduto ";
        comandoSql += "WHERE id = " + produtoVO.getId();

        getDeleteBancoDados(conn, comandoSql);
    }
}
