package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabColaboradorVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabColaboradorDAO extends BuscaBancoDados {

    ResultSet rs;
    TabColaboradorVO tabColaboradorVO;
    List<TabColaboradorVO> tabColaboradorVOList;
    boolean returnList = false;

    public TabColaboradorVO getTabColaboradorVO(int id, boolean getDetalhe) {
        getResultSet(String.format("SELECT * FROM tabColaborador WHERE id = %d ORDER BY nome", id), false);
        if (getDetalhe)
            addDetalheObjeto(tabColaboradorVO);
        return tabColaboradorVO;
    }

    public List<TabColaboradorVO> getTabColaboradorVOList() {
        tabColaboradorVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM tabColaborador ORDER BY nome"), true);
        for (TabColaboradorVO colaborador : tabColaboradorVOList)
            addDetalheObjeto(colaborador);
        return tabColaboradorVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
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
                if (returnList)
                    tabColaboradorVOList.add(tabColaboradorVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addDetalheObjeto(TabColaboradorVO colaborador) {
        if (colaborador == null) return;
        colaborador.setSisCargoVO(new SisCargoDAO().getSisCargoVO(colaborador.getSisCargo_id()));
        colaborador.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(colaborador.getSisSituacaoSistema_id()));
        colaborador.setLojaVO(new TabEmpresaDAO().getTabEmpresaVO(colaborador.getTabLoja_id()));
    }

}
