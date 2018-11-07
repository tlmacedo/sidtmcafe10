package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMenuPrincipalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisMenuPrincipalDAO extends ServiceBuscaBancoDados {

    SisMenuPrincipalVO menuPrincipalVO = null;
    List<SisMenuPrincipalVO> menuPrincipalVOList = null;

    public SisMenuPrincipalVO getMenuPrincipalVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisMenuPrincipal WHERE id = ? ");
        return menuPrincipalVO;
    }

    public SisMenuPrincipalVO getMenuPrincipalVO(String teclaAtalho) {
        addNewParametro(new Pair<>("String", teclaAtalho));
        getResultSet("SELECT * FROM sisMenuPrincipal WHERE teclaAtalho = ? ");
        return menuPrincipalVO;
    }

    public List<SisMenuPrincipalVO> getMenuPrincipalVOList() {
        menuPrincipalVOList = new ArrayList<>();
        getResultSet("SELECT * FROM sisMenuPrincipal ");
        return menuPrincipalVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                menuPrincipalVO = new SisMenuPrincipalVO();
                menuPrincipalVO.setId(rs.getInt("id"));
                menuPrincipalVO.setDescricao(rs.getString("descricao"));
                menuPrincipalVO.setTituloTab(rs.getString("tituloTab"));
                menuPrincipalVO.setFilho_id(rs.getInt("filho_id"));
                menuPrincipalVO.setIcoMenu(rs.getString("icoMenu"));
                menuPrincipalVO.setTabPane(rs.getBoolean("tabPane"));
                menuPrincipalVO.setTeclaAtalho(rs.getString("teclaAtalho"));
                if (menuPrincipalVOList != null) menuPrincipalVOList.add(menuPrincipalVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
