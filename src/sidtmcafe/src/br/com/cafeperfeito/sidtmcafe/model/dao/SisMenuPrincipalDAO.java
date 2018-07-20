package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMenuPrincipalVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisMenuPrincipalDAO extends BuscaBancoDados {

    ResultSet rs;
    SisMenuPrincipalVO menuPrincipalVO;
    List<SisMenuPrincipalVO> menuPrincipalVOList;
    boolean returnList = false;

    public SisMenuPrincipalVO getMenuPrincipalVO(int id) {
        getResultSet(String.format("SELECT * FROM sisMenuPrincipal WHERE id = %d ORDER BY id", id), false);
        return menuPrincipalVO;
    }

    public SisMenuPrincipalVO getMenuPrincipalVO(String teclaAtalho) {
        getResultSet(String.format("SELECT * FROM sisMenuPrincipal WHERE teclaAtalho = '%s' ORDER BY id", teclaAtalho), false);
        return menuPrincipalVO;
    }

    public List<SisMenuPrincipalVO> getMenuPrincipalVOList() {
        menuPrincipalVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisMenuPrincipal ORDER BY id"), true);
        return menuPrincipalVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
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
                if (returnList)
                    menuPrincipalVOList.add(menuPrincipalVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
