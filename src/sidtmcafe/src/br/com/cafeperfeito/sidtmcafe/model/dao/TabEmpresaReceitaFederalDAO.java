package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmpresaReceitaFederalVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabEmpresaReceitaFederalDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    TabEmpresaReceitaFederalVO tabEmpresaReceitaFederalVO;
    List<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOList;

    public TabEmpresaReceitaFederalVO getTabEmpresaReceitaFederalVO(int id) {
        buscaTabEmpresaReceitaFederalVO(id, 0);
        return tabEmpresaReceitaFederalVO;
    }

    public List<TabEmpresaReceitaFederalVO> getTabEmpresaReceitaFederalVOList(int empresa_id) {
        buscaTabEmpresaReceitaFederalVO(0, empresa_id);
        return tabEmpresaReceitaFederalVOList;
    }

    void buscaTabEmpresaReceitaFederalVO(int id, int empresa_id) {
        comandoSql = "SELECT id, tabEmpresa_id, isAtividadePrincipal, str_Key, str_Value ";
        comandoSql += "FROM tabEmpresaReceitaFederal ";
        if (id > 0) {
            comandoSql += "WHERE id = '" + id + "' ";
        } else {
            if (empresa_id > 0)
                comandoSql += "WHERE tabEmpresa_id = '" + empresa_id + "' ";
        }
        comandoSql += "ORDER BY isAtividadePrincipal, id ";

        if (id == 0) tabEmpresaReceitaFederalVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabEmpresaReceitaFederalVO = new TabEmpresaReceitaFederalVO();
                tabEmpresaReceitaFederalVO.setId(rs.getInt("id"));
                tabEmpresaReceitaFederalVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                tabEmpresaReceitaFederalVO.setIsAtividadePrincipal(rs.getInt("isAtividadePrincipal"));
                tabEmpresaReceitaFederalVO.setStr_Key(rs.getString("str_Key"));
                tabEmpresaReceitaFederalVO.setStr_Value(rs.getString("str_Value"));

                if (id == 0) tabEmpresaReceitaFederalVOList.add(tabEmpresaReceitaFederalVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    public void updateTabEmpresaReceitaFederalVO(Connection conn, TabEmpresaReceitaFederalVO tabEmpresaReceitaFederalVO) throws SQLException {
        comandoSql = "UPDATE tabEmpresaReceitaFederal SET ";
        comandoSql += "tabEmpresa_id = " + tabEmpresaReceitaFederalVO.getTabEmpresa_id() + ", ";
        comandoSql += "isAtividadePrincipal = " + tabEmpresaReceitaFederalVO.getIsAtividadePrincipal() + ", ";
        comandoSql += "str_Key = '" + tabEmpresaReceitaFederalVO.getStr_Key() + "', ";
        comandoSql += "str_Value = '" + tabEmpresaReceitaFederalVO.getStr_Value() + "' ";
        comandoSql += "WHERE id = " + tabEmpresaReceitaFederalVO.getId() + " ";
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEmpresaReceitaFederalVO(Connection conn, TabEmpresaReceitaFederalVO tabEmpresaReceitaFederalVO) throws SQLException {
        comandoSql = "INSERT INTO tabEmpresaReceitaFederal ";
        comandoSql += "(tabEmpresa_id, isAtividadePrincipal, str_Key, str_Value) ";
        comandoSql += "VALUES (";
        comandoSql += tabEmpresaReceitaFederalVO.getTabEmpresa_id() + ", ";
        comandoSql += tabEmpresaReceitaFederalVO.getIsAtividadePrincipal() + ", ";
        comandoSql += "'" + tabEmpresaReceitaFederalVO.getStr_Key() + "', ";
        comandoSql += "'" + tabEmpresaReceitaFederalVO.getStr_Value() + "' ";
        comandoSql += ") ";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEmpresaReceitaFederalVO(Connection conn, TabEmpresaReceitaFederalVO tabEmpresaReceitaFederalVO) throws SQLException {
        comandoSql = "DELETE ";
        comandoSql += "FROM tabEmpresaReceitaFederal ";
        comandoSql += "WHERE id = " + tabEmpresaReceitaFederalVO.getId() + " ";
        getDeleteBancoDados(conn, comandoSql);
    }

}
