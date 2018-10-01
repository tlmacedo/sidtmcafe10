package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabEmpresaDAO extends ServiceBuscaBancoDados implements Constants {

    TabEmpresaVO tabEmpresaVO = null;
    List<TabEmpresaVO> tabEmpresaVOList = null;

    public TabEmpresaVO getTabEmpresaVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEmpresa WHERE id = ? ");
        if (tabEmpresaVO != null)
            addObjetosPesquisa(tabEmpresaVO);
        return tabEmpresaVO;
    }

    public TabEmpresaVO getTabEmpresaVO(String cnpj) {
        addNewParametro(new Pair<>("String", cnpj.replaceAll("\\D", "")));
        getResultSet("SELECT * FROM tabEmpresa WHERE cnpj = ? ");
        if (tabEmpresaVO != null)
            addObjetosPesquisa(tabEmpresaVO);
        return tabEmpresaVO;
    }

    public List<TabEmpresaVO> getTabEmpresaVOList() {
        tabEmpresaVOList = new ArrayList<>();
        String comandoSql = "SELECT * FROM tabEmpresa ";
        getResultSet(comandoSql);
        if (tabEmpresaVOList != null)
            for (TabEmpresaVO empresa : tabEmpresaVOList)
                addObjetosPesquisa(empresa);
        return tabEmpresaVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY razao ");
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
                if (tabEmpresaVOList != null) tabEmpresaVOList.add(tabEmpresaVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEmpresaVO empresa) {
        empresa.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(empresa.getSisSituacaoSistema_id()));
        empresa.setUsuarioCadastroVO(new TabColaboradorDAO().getTabColaboradorVO(empresa.getUsuarioCadastro_id(), false));
        empresa.setUsuarioAtualizacaoVO(new TabColaboradorDAO().getTabColaboradorVO(empresa.getUsuarioAtualizacao_id(), false));

        List<TabEnderecoVO> tabEnderecoVOList = new ArrayList<>();
        new RelEmpresa_EnderecoDAO().getRelEmpresa_enderecoVOList(empresa.getId())
                .forEach(relEmpresaEndereco -> {
                    relEmpresaEndereco.getTabEndereco_id();
                    tabEnderecoVOList.add(new TabEnderecoDAO().getTabEnderecoVO(relEmpresaEndereco.getTabEndereco_id()));
                });
        empresa.setTabEnderecoVOList(tabEnderecoVOList);

        List<TabEmailHomePageVO> tabEmailHomePageVOList = new ArrayList<>();
        new RelEmpresa_EmailHomePageDAO().getRelEmpresa_emailHomePageVOList(empresa.getId())
                .forEach(relEmpresaEmailHomePage -> {
                    tabEmailHomePageVOList.add(new TabEmailHomePageDAO().getTabEmailHomePageVO(relEmpresaEmailHomePage.getTabEmailHomePage_id()));
                });
        empresa.setTabEmailHomePageVOList(tabEmailHomePageVOList);

        List<TabTelefoneVO> tabTelefoneVOList = new ArrayList<>();
        new RelEmpresa_TelefoneDAO().getRelEmpresa_telefoneVOList(empresa.getId())
                .forEach(relEmpresaTelefone -> {
                    tabTelefoneVOList.add(new TabTelefoneDAO().getTabTelefoneVO(relEmpresaTelefone.getTabTelefone_id()));
                });
        empresa.setTabTelefoneVOList(tabTelefoneVOList);

        List<TabContatoVO> tabContatoVOList = new ArrayList<>();
        new RelEmpresa_ContatoDAO().getRelEmpresa_contatoVOList(empresa.getId()).stream()
                .forEach(relEmpresaContato -> {
                    tabContatoVOList.add(new TabContatoDAO().getTabContatoVO(relEmpresaContato.getTabContato_id(), true));
                });
        empresa.setTabContatoVOList(tabContatoVOList);

        List<TabInformacaoReceitaFederalVO> tabInformacaoReceitaFederalVOList = new ArrayList<>();
        new RelEmpresa_InformacaoRfDAO().getRelEmpresa_informacaoRfVOList(empresa.getId()).stream()
                .forEach(relInformacaoRf -> {
                    tabInformacaoReceitaFederalVOList.add(new TabInformacaoReceitaFederalDAO().getTabInformacaoReceitaFederalVO(relInformacaoRf.getTabInformacaoReceitaFederal_id()));
                });
        empresa.setTabInformacaoReceitaFederalVOList(tabInformacaoReceitaFederalVOList);
    }

    public void updateTabEmpresaVO(Connection conn, TabEmpresaVO empresa) throws SQLException {
        String comandoSql = "UPDATE tabEmpresa SET " +
                "isEmpresa = ?, " +
                "cnpj = ?, " +
                "ieIsento = ?, " +
                "ie = ?, " +
                "razao = ?, " +
                "fantasia = ?, " +
                "isLoja = ?, " +
                "isCliente = ?, " +
                "isFornecedor = ?, " +
                "isTransportadora = ?, " +
                "sisSituacaoSistema_id = ?, " +
                "usuarioAtualizacao_id = ?, " +
                "dataAbertura = ?, " +
                "naturezaJuridica = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("boolean", empresa.isIsEmpresa() ? "true" : "false"));
        addParametro(new Pair<>("String", empresa.getCnpj().replaceAll("\\D", "")));
        addParametro(new Pair<>("boolean", empresa.isIeIsento() ? "true" : "false"));
        addParametro(new Pair<>("String", empresa.getIe().replaceAll("\\D", "")));
        addParametro(new Pair<>("String", empresa.getRazao().trim().replaceAll("'", "''")));
        addParametro(new Pair<>("String", empresa.getFantasia().trim().replaceAll("'", "''")));
        addParametro(new Pair<>("boolean", empresa.isIsLoja() ? "true" : "false"));
        addParametro(new Pair<>("boolean", empresa.isIsCliente() ? "true" : "false"));
        addParametro(new Pair<>("boolean", empresa.isIsFornecedor() ? "true" : "false"));
        addParametro(new Pair<>("boolean", empresa.isIsTransportadora() ? "true" : "false"));
        addParametro(new Pair<>("int", String.valueOf(empresa.getSisSituacaoSistema_id())));
        addParametro(new Pair<>("int", String.valueOf(empresa.getUsuarioAtualizacao_id())));
        addParametro(new Pair<>("date", empresa.getDataAbertura().toString()));
        addParametro(new Pair<>("String", empresa.getNaturezaJuridica().trim().replaceAll("'", "''")));
        addParametro(new Pair<>("int", String.valueOf(empresa.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEmpresaVO(Connection conn, TabEmpresaVO empresa) throws SQLException {
        String comandoSql = "INSERT INTO tabEmpresa " +
                "(isEmpresa, " +
                "cnpj, " +
                "ieIsento, " +
                "ie, " +
                "razao, " +
                "fantasia, " +
                "isLoja, " +
                "isCliente, " +
                "isFornecedor, " +
                "isTransportadora, " +
                "sisSituacaoSistema_id, " +
                "usuarioCadastro_id, " +
                "dataAbertura, " +
                "naturezaJuridica) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?, ?) ";
        addNewParametro(new Pair<>("boolean", empresa.isIsEmpresa() ? "true" : "false"));
        addParametro(new Pair<>("String", empresa.getCnpj().replaceAll("\\D", "")));
        addParametro(new Pair<>("boolean", empresa.isIeIsento() ? "true" : "false"));
        addParametro(new Pair<>("String", empresa.getIe().replaceAll("\\D", "")));
        addParametro(new Pair<>("String", empresa.getRazao().trim().replaceAll("'", "''")));
        addParametro(new Pair<>("String", empresa.getFantasia().trim().replaceAll("'", "''")));
        addParametro(new Pair<>("boolean", empresa.isIsLoja() ? "true" : "false"));
        addParametro(new Pair<>("boolean", empresa.isIsCliente() ? "true" : "false"));
        addParametro(new Pair<>("boolean", empresa.isIsFornecedor() ? "true" : "false"));
        addParametro(new Pair<>("boolean", empresa.isIsTransportadora() ? "true" : "false"));
        addParametro(new Pair<>("int", String.valueOf(empresa.getSisSituacaoSistema_id())));
        addParametro(new Pair<>("int", String.valueOf(empresa.getUsuarioCadastro_id())));
        addParametro(new Pair<>("date", empresa.getDataAbertura().toString()));
        addParametro(new Pair<>("String", empresa.getNaturezaJuridica().trim().replaceAll("'", "''")));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEmpresaVO(Connection conn, int empresa_id) throws SQLException {
        String comandoSql = "DELETE FROM tabEmpresa " +
                "WHERE id = ? ";
        if (empresa_id < 0) empresa_id = empresa_id * (-1);
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getDeleteBancoDados(conn, comandoSql);
    }


}
