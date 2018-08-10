package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabInformacaoReceitaFederalVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TabInformacaoReceitaFederalDAO extends BuscaBancoDados {

    ResultSet rs;
    TabInformacaoReceitaFederalVO tabInformacaoReceitaFederalVO;
    List<TabInformacaoReceitaFederalVO> tabInformacaoReceitaFederalVOList;
    boolean returnList = false;

    public TabInformacaoReceitaFederalVO getTabInformacaoReceitaFederalVO(int id) {
        getResultSet(String.format("SELECT * FROM tabInformacaoReceitaFederal WHERE id = %d ORDER BY isAtividadePrincipal, id", id), false);
        return tabInformacaoReceitaFederalVO;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabInformacaoReceitaFederalVO = new TabInformacaoReceitaFederalVO();
                tabInformacaoReceitaFederalVO.setId(rs.getInt("id"));
                tabInformacaoReceitaFederalVO.setIsAtividadePrincipal(rs.getInt("isAtividadePrincipal"));
                tabInformacaoReceitaFederalVO.setStr_Key(rs.getString("str_Key"));
                tabInformacaoReceitaFederalVO.setStr_Value(rs.getString("str_Value"));
                if (returnList) tabInformacaoReceitaFederalVOList.add(tabInformacaoReceitaFederalVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabInformacaoReceitaFederalVO(Connection conn, TabInformacaoReceitaFederalVO informacaoRF) throws SQLException {
        String comandoSql = String.format("UPDATE tabInformacaoReceitaFederal SET isAtividadePrincipal = %d, " +
                        "str_Key = '%s', str_Value = '%s' WHERE id = %d",
                informacaoRF.getIsAtividadePrincipal(),
                informacaoRF.getStr_Key(), informacaoRF.getStr_Value(),
                informacaoRF.getId());
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabInformacaoReceitaFederalVO(Connection conn, TabInformacaoReceitaFederalVO informacaoRF, int empresa_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabInformacaoReceitaFederal (isAtividadePrincipal, str_Key, str_Value) VALUES (%d, '%s', '%s')",
                informacaoRF.getIsAtividadePrincipal(), informacaoRF.getStr_Key(), informacaoRF.getStr_Value());
        int informacaoRf_id = getInsertBancoDados(conn, comandoSql);
        new RelEmpresaInformacaoRfDAO().insertRelEmpresaInformacaoRfVO(conn, empresa_id, informacaoRf_id);
        return informacaoRf_id;
    }

    public void deleteTabInformacaoReceitaFederalVO(Connection conn, TabInformacaoReceitaFederalVO informacaoRF, int empresa_id) throws SQLException {
        if (empresa_id < 0) empresa_id = empresa_id * (-1);
        new RelEmpresaInformacaoRfDAO().deleteRelEmpresaInformacaoRfVO(conn, empresa_id, informacaoRF.getId());
        String comandoSql = String.format("DELETE FROM tabInformacaoReceitaFederal WHERE id = %d", informacaoRF.getId());
        getDeleteBancoDados(conn, comandoSql);
    }
}
