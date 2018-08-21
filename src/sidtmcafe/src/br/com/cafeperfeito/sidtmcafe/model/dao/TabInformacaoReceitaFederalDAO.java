package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabInformacaoReceitaFederalVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TabInformacaoReceitaFederalDAO extends ServiceBuscaBancoDados {

    TabInformacaoReceitaFederalVO tabInformacaoReceitaFederalVO = null;
    List<TabInformacaoReceitaFederalVO> tabInformacaoReceitaFederalVOList = null;

    public TabInformacaoReceitaFederalVO getTabInformacaoReceitaFederalVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabInformacaoReceitaFederal WHERE id = ? ");
        return tabInformacaoReceitaFederalVO;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY isAtividadePrincipal, id");
        try {
            while (rs.next()) {
                tabInformacaoReceitaFederalVO = new TabInformacaoReceitaFederalVO();
                tabInformacaoReceitaFederalVO.setId(rs.getInt("id"));
                tabInformacaoReceitaFederalVO.setIsAtividadePrincipal(rs.getInt("isAtividadePrincipal"));
                tabInformacaoReceitaFederalVO.setStr_Key(rs.getString("str_Key"));
                tabInformacaoReceitaFederalVO.setStr_Value(rs.getString("str_Value"));
                if (tabInformacaoReceitaFederalVOList != null)
                    tabInformacaoReceitaFederalVOList.add(tabInformacaoReceitaFederalVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public void updateTabInformacaoReceitaFederalVO(Connection conn, TabInformacaoReceitaFederalVO informacaoRF) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(informacaoRF.getIsAtividadePrincipal())));
        addParametro(new Pair<>("String", informacaoRF.getStr_Key()));
        addParametro(new Pair<>("String", informacaoRF.getStr_Value()));
        addParametro(new Pair<>("int", String.valueOf(informacaoRF.getId())));
        String comandoSql = "UPDATE tabInformacaoReceitaFederal SET isAtividadePrincipal = ?, str_Key = ?, str_Value = ? WHERE id = ?";
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabInformacaoReceitaFederalVO(Connection conn, TabInformacaoReceitaFederalVO informacaoRF, int empresa_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(informacaoRF.getIsAtividadePrincipal())));
        addParametro(new Pair<>("String", informacaoRF.getStr_Key()));
        addParametro(new Pair<>("String", informacaoRF.getStr_Value()));
        String comandoSql = "INSERT INTO tabInformacaoReceitaFederal (isAtividadePrincipal, str_Key, str_Value) VALUES (?, ?, ?)";
        int informacaoRf_id = getInsertBancoDados(conn, comandoSql);
        new RelEmpresaInformacaoRfDAO().insertRelEmpresaInformacaoRfVO(conn, empresa_id, informacaoRf_id);
        return informacaoRf_id;
    }

    public void deleteTabInformacaoReceitaFederalVO(Connection conn, TabInformacaoReceitaFederalVO informacaoRF, int empresa_id) throws SQLException {
        if (empresa_id < 0) empresa_id = empresa_id * (-1);
        new RelEmpresaInformacaoRfDAO().deleteRelEmpresaInformacaoRfVO(conn, empresa_id, informacaoRF.getId());
        addNewParametro(new Pair<>("int", String.valueOf(informacaoRF.getId())));
        String comandoSql = "DELETE FROM tabInformacaoReceitaFederal WHERE id = ? ";
        getDeleteBancoDados(conn, comandoSql);
    }
}
