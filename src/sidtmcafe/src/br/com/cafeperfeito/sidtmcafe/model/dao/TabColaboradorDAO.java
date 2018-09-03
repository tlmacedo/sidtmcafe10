package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabColaboradorVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabColaboradorDAO extends ServiceBuscaBancoDados {

    TabColaboradorVO tabColaboradorVO = null;
    List<TabColaboradorVO> tabColaboradorVOList = null;

    public TabColaboradorVO getTabColaboradorVO(int id, boolean getDetalhe) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabColaborador WHERE id = ? ");
        if (getDetalhe)
            addDetalheObjeto(tabColaboradorVO);
        return tabColaboradorVO;
    }

    public List<TabColaboradorVO> getTabColaboradorVOList() {
        tabColaboradorVOList = new ArrayList<>();
        getResultSet("SELECT * FROM tabColaborador ");
        for (TabColaboradorVO colaborador : tabColaboradorVOList)
            addDetalheObjeto(colaborador);
        return tabColaboradorVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY nome ");
        try {
            while (rs.next()) {
                tabColaboradorVO = new TabColaboradorVO();
                tabColaboradorVO.setId(rs.getInt("id"));
                tabColaboradorVO.setNome(rs.getString("nome"));
                tabColaboradorVO.setApelido(rs.getString("apelido"));
                tabColaboradorVO.setSenha(rs.getString("senha"));
                tabColaboradorVO.setSisCargo_id(rs.getInt("sisCargo_id"));
                tabColaboradorVO.setSisSituacaoSistema_id(rs.getInt("sisSituacaoSistema_id"));
                tabColaboradorVO.setTabLoja_id(rs.getInt("tabLoja_id"));
                if (tabColaboradorVOList != null) tabColaboradorVOList.add(tabColaboradorVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addDetalheObjeto(TabColaboradorVO colaborador) {
        if (colaborador == null) return;
        colaborador.setSisCargoVO(new SisCargoDAO().getSisCargoVO(colaborador.getSisCargo_id()));
        colaborador.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(colaborador.getSisSituacaoSistema_id()));
        colaborador.setLojaVO(new TabEmpresaDAO().getTabEmpresaVO(colaborador.getTabLoja_id()));
    }

}
