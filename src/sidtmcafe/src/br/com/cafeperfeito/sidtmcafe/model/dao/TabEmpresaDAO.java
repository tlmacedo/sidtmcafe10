package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TabEmpresaDAO extends BuscaBancoDados implements Constants {

    ResultSet rs;
    TabEmpresaVO tabEmpresaVO;
    List<TabEmpresaVO> tabEmpresaVOList;
    boolean returnList = false;

    public TabEmpresaVO getTabEmpresaVO(int id) {
        getResultSet(String.format("SELECT * FROM tabEmpresa WHERE id = %d ORDER BY razao", id), false);
        if (tabEmpresaVO != null)
            addObjetosPesquisa(tabEmpresaVO);
        return tabEmpresaVO;
    }

    public TabEmpresaVO getTabEmpresaVO(String cnpj) {
        getResultSet(String.format("SELECT * FROM tabEmpresa WHERE cnpj = '%s' ORDER BY razao", cnpj), false);
        if (tabEmpresaVO != null)
            addObjetosPesquisa(tabEmpresaVO);
        return tabEmpresaVO;
    }

    public List<TabEmpresaVO> getTabEmpresaVOList(boolean isLoja) {
        tabEmpresaVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM tabEmpresa %sORDER BY razao",
                isLoja ? String.format("WHERE isLoja = %b ", 1) : ""), true);
        if (tabEmpresaVOList != null)
            for (TabEmpresaVO empresa : tabEmpresaVOList)
                addObjetosPesquisa(empresa);
        return tabEmpresaVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabEmpresaVO = new TabEmpresaVO();
                tabEmpresaVO.setId(rs.getInt("id"));
                tabEmpresaVO.setIsEmpresa(rs.getBoolean("isEmpresa"));
                tabEmpresaVO.setCnpj(rs.getString("cnpj"));
                tabEmpresaVO.setIeIsento(rs.getBoolean("ieIsento"));
                tabEmpresaVO.setIe(rs.getString("ie"));
                tabEmpresaVO.setRazao(rs.getString("razao"));
                tabEmpresaVO.setFantasia(rs.getString("fantasia"));
                tabEmpresaVO.setIsLoja(rs.getBoolean("isLoja"));
                tabEmpresaVO.setIsCliente(rs.getBoolean("isCliente"));
                tabEmpresaVO.setIsFornecedor(rs.getBoolean("isFornecedor"));
                tabEmpresaVO.setIsTransportadora(rs.getBoolean("isTransportadora"));
                tabEmpresaVO.setSisSituacaoSistema_id(rs.getInt("sisSituacaoSistema_id"));
                tabEmpresaVO.setUsuarioCadastro_id(rs.getInt("usuarioCadastro_id"));
                tabEmpresaVO.setDataCadastro(rs.getTimestamp("dataCadastro"));
                tabEmpresaVO.setUsuarioAtualizacao_id(rs.getInt("usuarioAtualizacao_id"));
                tabEmpresaVO.setDataAtualizacao(rs.getTimestamp("dataAtualizacao"));
                tabEmpresaVO.setDataAbertura(rs.getDate("dataAbertura"));
                tabEmpresaVO.setNaturezaJuridica(rs.getString("naturezaJuridica"));
                if (returnList) tabEmpresaVOList.add(tabEmpresaVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEmpresaVO empresa) {
        empresa.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(empresa.getSisSituacaoSistema_id()));
        empresa.setUsuarioCadastroVO(new TabColaboradorDAO().getTabColaboradorVO(empresa.getUsuarioCadastro_id(), false));
        empresa.setUsuarioAtualizacaoVO(new TabColaboradorDAO().getTabColaboradorVO(empresa.getUsuarioAtualizacao_id(), false));
        empresa.setTabEmpresaReceitaFederalVOList(new TabEmpresaReceitaFederalDAO().getTabEmpresaReceitaFederalVOList(empresa.getId()));

        List<TabEnderecoVO> tabEnderecoVOList = new ArrayList<>();
        new RelEmpresaEnderecoDAO().getRelEmpresaEnderecoVOList(empresa.getId())
                .forEach(relEmpresaEndereco -> {
                    tabEnderecoVOList.add(new TabEnderecoDAO().getTabEnderecoVO(relEmpresaEndereco.getTabEndereco_id()));
                });
        empresa.setTabEnderecoVOList(tabEnderecoVOList);

        List<TabEmailHomePageVO> tabEmailHomePageVOList = new ArrayList<>();
        new RelEmpresaEmailHomePageDAO().getRelEmpresaEmailHomePageVOList(empresa.getId())
                .forEach(relEmpresaEmailHomePage -> {
                    tabEmailHomePageVOList.add(new TabEmailHomePageDAO().getTabEmailHomePageVO(relEmpresaEmailHomePage.getTabEmailHomePage_id()));
                });
        empresa.setTabEmailHomePageVOList(tabEmailHomePageVOList);

        List<TabTelefoneVO> tabTelefoneVOList = new ArrayList<>();
        new RelEmpresaTelefoneDAO().getRelEmpresaTelefoneVOList(empresa.getId())
                .forEach(relEmpresaTelefone -> {
                    tabTelefoneVOList.add(new TabTelefoneDAO().getTabTelefoneVO(relEmpresaTelefone.getTabTelefone_id()));
                });
        empresa.setTabTelefoneVOList(tabTelefoneVOList);

        List<TabContatoVO> tabContatoVOList = new ArrayList<TabContatoVO>();
        new RelEmpresaContatoDAO().getRelEmpresaContatoVOList(empresa.getId()).stream()
                .forEach(relEmpresaContato -> {
                    tabContatoVOList.add(new TabContatoDAO().getTabContatoVO(relEmpresaContato.getTabContato_id(), true));
                });
        empresa.setTabContatoVOList(tabContatoVOList);
    }

    public void updateTabEmpresaVO(Connection conn, TabEmpresaVO empresa) throws SQLException {
        String comandoSql = String.format("UPDATE tabEmpresa SET isEmpresa = %b, cnpj = '%s', ieIsento = %b, " +
                        "ie = '%s', razao = '%s', fantasia = '%s', isLoja = %b, isCliente = %b, isFornecedor = %b, " +
                        "isTransportadora = %b, sisSituacaoSistema_id = %d, usuarioAtualizacao_id = %d, " +
                        "dataAtualizacao = '%s', dataAbertura = '%s', naturezaJuridica = '%s' WHERE id = %d",
                empresa.isIsEmpresa(), empresa.getCnpj(), empresa.isIeIsento(), empresa.getIe(), empresa.getRazao(),
                empresa.getFantasia(), empresa.isIsLoja(), empresa.isIsCliente(), empresa.isIsFornecedor(),
                empresa.isIsTransportadora(), empresa.getSisSituacaoSistema_id(), empresa.getUsuarioAtualizacao_id(),
                DTF_MYSQL_DATAHORA.format(LocalDateTime.now()), empresa.getDataAbertura(), empresa.getNaturezaJuridica(),
                empresa.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEmpresaVO(Connection conn, TabEmpresaVO empresa) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabEmpresa (isEmpresa, cnpj, ieIsento, ie, " +
                        "razao, fantasia, isLoja, isCliente, isFornecedor, isTransportadora, sisSituacaoSistema_id, " +
                        "usuarioCadastro_id, dataAbertura, naturezaJuridica) VALUES(%b, '%s', %b, '%s', '%s', '%s', " +
                        "%b, %b, %b, %b, %d, %d, '%s', '%s')",
                empresa.isIsEmpresa(), empresa.getCnpj().replaceAll("[\\D]", ""),
                empresa.isIeIsento(), empresa.getIe().replaceAll("[\\D]", ""),
                empresa.getRazao().trim().replaceAll("'", "\'"),
                empresa.getFantasia().trim().replaceAll("'", "\'"),
                empresa.isIsLoja(), empresa.isIsCliente(), empresa.isIsFornecedor(),
                empresa.isIsTransportadora(), empresa.getSisSituacaoSistema_id(),
                empresa.getUsuarioCadastro_id(), empresa.getDataAbertura(),
                empresa.getNaturezaJuridica().trim().replaceAll("'", "\'"));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEmpresaVO(Connection conn, int empresa_id) throws SQLException {
        if (empresa_id < 0) empresa_id = empresa_id * (-1);
        String comandoSql = String.format("DELETE FROM tabEmpresa WHERE id = %d", empresa_id);
        getDeleteBancoDados(conn, comandoSql);
    }


}
