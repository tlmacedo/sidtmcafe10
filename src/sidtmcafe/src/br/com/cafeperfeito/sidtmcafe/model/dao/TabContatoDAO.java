package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TabContatoDAO extends BuscaBancoDados {

    ResultSet rs;
    TabContatoVO tabContatoVO;

    public TabContatoVO getTabContatoVO(int id, boolean getDetalheContato) {
        getResultSet(String.format("SELECT * FROM tabContato WHERE id = %d ORDER BY descricao", id));
        if (getDetalheContato)
            if (tabContatoVO != null)
                addObjetosPesquisa(tabContatoVO);
        return tabContatoVO;
    }

    void getResultSet(String comandoSql) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabContatoVO = new TabContatoVO();
                tabContatoVO.setId(rs.getInt("id"));
                tabContatoVO.setDescricao(rs.getString("descricao"));
                tabContatoVO.setSisCargo_id(rs.getInt("sisCargo_id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabContatoVO contatoVO) {
        contatoVO.setSisCargoVO(new SisCargoDAO().getSisCargoVO(contatoVO.getSisCargo_id()));

        List<RelContatoEmailHomePageVO> relContatoEmailHomePageVOList = new RelContatoEmailHomePageDAO().getRelContatoEmailHomePageVOList(contatoVO.getId());
        List<TabEmailHomePageVO> tabEmailHomePageVOList = new ArrayList<>();
        if (relContatoEmailHomePageVOList != null)
            for (int i = 0; i < relContatoEmailHomePageVOList.size(); i++)
                tabEmailHomePageVOList.add(new TabEmailHomePageDAO().getTabEmailHomePageVO(relContatoEmailHomePageVOList.get(i).getTabEmailHomePage_id()));
        contatoVO.setTabEmailHomePageVOList(tabEmailHomePageVOList);

        List<RelContatoTelefoneVO> relContatoTelefoneVOList = new RelContatoTelefoneDAO().getRelContatoTelefoneVOList(contatoVO.getId());
        List<TabTelefoneVO> tabTelefoneVOList = new ArrayList<>();
        if (relContatoEmailHomePageVOList != null)
            for (int i = 0; i < relContatoTelefoneVOList.size(); i++)
                tabTelefoneVOList.add(new TabTelefoneDAO().getTabTelefoneVO(relContatoTelefoneVOList.get(i).getTabTelefone_id()));
        contatoVO.setTabTelefoneVOList(tabTelefoneVOList);

    }

    public void updateTabContatoVO(Connection conn, TabContatoVO contato) throws SQLException {
        String comandoSql = String.format("UPDATE tabContato SET descricao = '%s', sisCargo_id = %d WHERE id = %d",
                contato.getDescricao(), contato.getSisCargo_id(), contato.getId());
        verificaDetalhes(conn, contato, contato.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabContatoVO(Connection conn, TabContatoVO contato, int empresa_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabContato (descricao, sisCargo_id) VALUES('%s', %d)",
                contato.getDescricao(), contato.getSisCargo_id());
        int contato_id = getInsertBancoDados(conn, comandoSql);
        new RelEmpresaContatoDAO().insertRelEmpresaContatoVO(conn, empresa_id, contato_id);
        verificaDetalhes(conn, contato, contato_id);
        return contato_id;
    }

    public void deleteTabContatoVO(Connection conn, TabContatoVO contato, int empresa_id) throws SQLException {
        verificaDetalhes(conn, contato, contato.getId());
        if (contato.getId() < 0) contato.setId(contato.getId() * (-1));
        new RelEmpresaContatoDAO().deleteRelEmpresaContatoVO(conn, empresa_id, contato.getId());
        String comandoSql = String.format("DELETE FROM tabContato WHERE id = %d", contato.getId());
        getDeleteBancoDados(conn, comandoSql);
    }

    void verificaDetalhes(Connection conn, TabContatoVO contato, int contato_id) {
        if (contato_id < 0) {
            contato.getTabEmailHomePageVOList().stream()
                    .filter(contatoEmailHomePage -> contatoEmailHomePage.getId() > 0)
                    .forEach(contatoEmailHomePage -> {
                        try {
                            new TabEmailHomePageDAO().deleteEmailHomePageVO(conn, contatoEmailHomePage.getId(), 0, (contato_id * (-1)));
                        } catch (SQLException ex) {
                            throw new RuntimeException("Erro deleteTabcontatoVO.emailHomePage ===>> ", ex);
                        }
                    });
            contato.getTabTelefoneVOList().stream()
                    .filter(contatoTelefone -> contatoTelefone.getId() > 0)
                    .forEach(contatoTelefone -> {
                        try {
                            new TabTelefoneDAO().deleteTabTelefoneVO(conn, contatoTelefone.getId(), 0, (contato_id * (-1)));
                        } catch (SQLException ex) {
                            throw new RuntimeException("Erro deleteTabContatoVO.telefone ===>> ", ex);
                        }
                    });
        } else {
            contato.getTabEmailHomePageVOList().stream().sorted(Comparator.comparing(TabEmailHomePageVO::getId))
                    .forEach(contatoEmailHomePage -> {
                        try {
                            if (contatoEmailHomePage.getId() < 0)
                                new TabEmailHomePageDAO().deleteEmailHomePageVO(conn, contatoEmailHomePage.getId(), 0, contatoEmailHomePage.getId());
                            else if (contatoEmailHomePage.getId() > 0)
                                new TabEmailHomePageDAO().updateTabEmailHomePageVO(conn, contatoEmailHomePage);
                            else
                                contatoEmailHomePage.setId(new TabEmailHomePageDAO().insertEmailHomePageVO(conn, contatoEmailHomePage, 0, contato_id));
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro insertTabContatoVO.emailHomePage ===>> ", ex);
                        }
                    });
            contato.getTabTelefoneVOList().stream().sorted(Comparator.comparing(TabTelefoneVO::getId))
                    .forEach(contatoTelefone -> {
                        try {
                            if (contatoTelefone.getId() < 0)
                                new TabTelefoneDAO().deleteTabTelefoneVO(conn, contatoTelefone.getId(), 0, contatoTelefone.getId());
                            else if (contatoTelefone.getId() > 0)
                                new TabTelefoneDAO().updateTabTelefoneVO(conn, contatoTelefone);
                            else
                                contatoTelefone.setId(new TabTelefoneDAO().insertTabTelefoneVO(conn, contatoTelefone, 0, contato_id));
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro insertTabContatoVO.telefone ===>> ", ex);
                        }
                    });
        }
    }
}
