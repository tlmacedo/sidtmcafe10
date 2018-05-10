package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabColaboradorVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabColaboradorDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    TabColaboradorVO tabColaboradorVO;
    List<TabColaboradorVO> tabColaboradorVOList;

    public TabColaboradorVO getTabColaboradorVO_Simples(int id) {
        buscaTabColaboradorVO(id);
        return tabColaboradorVO;
    }

    public TabColaboradorVO getTabColaboradorVO(int id) {
        buscaTabColaboradorVO(id);
        if (tabColaboradorVO != null)
            addDetalheObjeto(tabColaboradorVO);
        return tabColaboradorVO;
    }

    public List<TabColaboradorVO> getTabColaboradorVOList() {
        buscaTabColaboradorVO(0);
        if (tabColaboradorVOList != null)
            for (TabColaboradorVO colaboradorVO : tabColaboradorVOList)
                addDetalheObjeto(colaboradorVO);
        return tabColaboradorVOList;
    }

    void buscaTabColaboradorVO(int id) {
        comandoSql = "SELECT id, nome, apelido, senha, senhaSalt, sisCargo_id, sisSituacaoSistema_id, tabLoja_id " +
                "FROM tabColaborador ";
        if (id > 0) comandoSql += " WHERE id = '" + id + "' ";
        comandoSql += "ORDER BY nome ";

        if (id == 0) tabColaboradorVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabColaboradorVO = new TabColaboradorVO();
                tabColaboradorVO.setId(rs.getInt("id"));
                tabColaboradorVO.setNome(rs.getString("nome"));
                tabColaboradorVO.setApelido(rs.getString("apelido"));
                tabColaboradorVO.setSenha(rs.getString("senha"));
                tabColaboradorVO.setSenhaSalt(rs.getString("senhaSalt"));
                tabColaboradorVO.setSisCargo_id(rs.getInt("sisCargo_id"));
                tabColaboradorVO.setSisSituacaoSistema_id(rs.getInt("sisSituacaoSistema_id"));
                tabColaboradorVO.setTabLoja_id(rs.getInt("tabLoja_id"));

                if (id == 0) tabColaboradorVOList.add(tabColaboradorVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addDetalheObjeto(TabColaboradorVO colaborador) {
        colaborador.setSisCargoVO(new SisCargoDAO().getSisCargoVO(colaborador.getSisCargo_id()));
        colaborador.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(colaborador.getSisSituacaoSistema_id()));
        colaborador.setLojaVO(new TabEmpresaDAO().getTabEmpresaVO(colaborador.getTabLoja_id()));
    }

}
