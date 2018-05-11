package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMenuPrincipalVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisMenuPrincipalDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    SisMenuPrincipalVO menuPrincipalVO;
    List<SisMenuPrincipalVO> menuPrincipalVOList;

    public SisMenuPrincipalVO getMenuPrincipalVO(int idSisMenuPrincipalVO) {
        buscaSisMenuPrincipalVO(idSisMenuPrincipalVO, "");
        return menuPrincipalVO;
    }

    public SisMenuPrincipalVO getMenuPrincipalVO(String teclaAtalho) {
        buscaSisMenuPrincipalVO(-1, teclaAtalho);
        return menuPrincipalVO;
    }

    public List<SisMenuPrincipalVO> getMenuPrincipalVOList() {
        buscaSisMenuPrincipalVO(-1, "");
        return menuPrincipalVOList;
    }

    void buscaSisMenuPrincipalVO(int idSisMenuPrincipalVO, String teclaAtalho) {
        comandoSql = "SELECT * FROM sisMenuPrincipal ";
        if (idSisMenuPrincipalVO > 0) comandoSql += "WHERE id '" + idSisMenuPrincipalVO + "' ";
        if (teclaAtalho != "") {
            if (!comandoSql.contains("WHERE")) {
                comandoSql += "WHERE ";
            } else {
                comandoSql += "AND ";
            }
            comandoSql += "teclaAtalho = '" + teclaAtalho + "' ";
        }
        comandoSql += "ORDER BY id ";

        menuPrincipalVOList = new ArrayList<>();
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

                menuPrincipalVOList.add(menuPrincipalVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
