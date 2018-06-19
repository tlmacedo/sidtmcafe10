package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmpresaReceitaFederalVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabEmpresaReceitaFederalDAO extends BuscaBancoDados {

    ResultSet rs;
    TabEmpresaReceitaFederalVO tabEmpresaReceitaFederalVO;
    List<TabEmpresaReceitaFederalVO> tabEmpresaReceitaFederalVOList;
    boolean returnList = false;

    public TabEmpresaReceitaFederalVO getTabEmpresaReceitaFederalVO(int id) {
        getResultSet(String.format("SELECT * FROM tabEmpresaReceitaFederal WHERE id = %d ORDER BY isAtividadePrincipal, id", id), false);
        return tabEmpresaReceitaFederalVO;
    }

    public List<TabEmpresaReceitaFederalVO> getTabEmpresaReceitaFederalVOList(int empresa_id) {
        tabEmpresaReceitaFederalVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM tabEmpresaReceitaFederal WHERE tabEmpresa_id = %d ORDER BY isAtividadePrincipal, id", empresa_id), true);
        return tabEmpresaReceitaFederalVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabEmpresaReceitaFederalVO = new TabEmpresaReceitaFederalVO();
                tabEmpresaReceitaFederalVO.setId(rs.getInt("id"));
                tabEmpresaReceitaFederalVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                tabEmpresaReceitaFederalVO.setIsAtividadePrincipal(rs.getInt("isAtividadePrincipal"));
                tabEmpresaReceitaFederalVO.setStr_Key(rs.getString("str_Key"));
                tabEmpresaReceitaFederalVO.setStr_Value(rs.getString("str_Value"));
                if (returnList) tabEmpresaReceitaFederalVOList.add(tabEmpresaReceitaFederalVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public void updateTabEmpresaReceitaFederalVO(Connection conn, TabEmpresaReceitaFederalVO tabEmpresaReceitaFederalVO) throws SQLException {
        String comandoSql = String.format("UPDATE tabEmpresaReceitaFederal SET tabEmpresa_id = %d, isAtividadePrincipal = %d, " +
                        "str_Key = '%s', str_Valur = '%s' WHERE id = %d",
                tabEmpresaReceitaFederalVO.getTabEmpresa_id(), tabEmpresaReceitaFederalVO.getIsAtividadePrincipal(),
                tabEmpresaReceitaFederalVO.getStr_Key(), tabEmpresaReceitaFederalVO.getStr_Value(),
                tabEmpresaReceitaFederalVO.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEmpresaReceitaFederalVO(Connection conn, TabEmpresaReceitaFederalVO tabEmpresaReceitaFederalVO) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabEmpresaReceitaFederal (tabEmpresa_id, isAtividadePrincipal, " +
                        "str_Key, str_Value) VALUES (%d, %d, '%s', '%s')",
                tabEmpresaReceitaFederalVO.getTabEmpresa_id(), tabEmpresaReceitaFederalVO.getIsAtividadePrincipal(),
                tabEmpresaReceitaFederalVO.getStr_Key(), tabEmpresaReceitaFederalVO.getStr_Value());
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteTabEmpresaReceitaFederalVO(Connection conn, int empresaReceitaFederal_id) throws SQLException {
        if (empresaReceitaFederal_id < 0) empresaReceitaFederal_id = empresaReceitaFederal_id * (-1);
        String comandoSql = String.format("DELETE FROM tabEmpresaReceitaFederal WHERE id = %d", empresaReceitaFederal_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
