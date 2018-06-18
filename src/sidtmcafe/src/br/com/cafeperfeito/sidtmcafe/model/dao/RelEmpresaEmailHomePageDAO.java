package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaEmailHomePageVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaEmailHomePageDAO extends BuscaBancoDados {

    ResultSet rs;
    RelEmpresaEmailHomePageVO relEmpresaEmailHomePageVO;
    List<RelEmpresaEmailHomePageVO> relEmpresaEmailHomePageVOList;

    public RelEmpresaEmailHomePageVO getRelEmpresaEmailHomePageVO(int empresa_id, int emailHoePage_id) {
        getResultSet(String.format("SELECT * FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = %d " +
                "AND tabEmailHomePage_id = %d ORDER BY tabEmpresa_id, tabEmailHomePage_id", empresa_id, emailHoePage_id));
        return relEmpresaEmailHomePageVO;
    }

    public List<RelEmpresaEmailHomePageVO> getRelEmpresaEmailHomePageVOList(int empresa_id) {
        getResultSet(String.format("SELECT * FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = %d " +
                "ORDER BY tabEmpresa_id, tabEmailHomePage_id", empresa_id));
        return relEmpresaEmailHomePageVOList;
    }

    void getResultSet(String comandoSql) {
        boolean returnList = false;
        rs = getResultadosBandoDados(comandoSql);
        try {
            if (rs.last())
                if (returnList = (rs.getRow() > 1))
                    relEmpresaEmailHomePageVOList = new ArrayList<>();
            rs.beforeFirst();
            while (rs.next()) {
                relEmpresaEmailHomePageVO = new RelEmpresaEmailHomePageVO();
                relEmpresaEmailHomePageVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaEmailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));
                if (returnList) relEmpresaEmailHomePageVOList.add(relEmpresaEmailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelEmpresaEmailHomePage(Connection conn, int empresa_id, int emailHomePage_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relEmpresaEmailHomePage (tabEmpresa_id, tabEmailHomePage_id) " +
                "VALUES(%d, %d)", empresa_id, emailHomePage_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresaEmailHomePage(Connection conn, int empresa_id, int emailHome_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = %d ", empresa_id);
        if (emailHome_id > 0)
            comandoSql += String.format("AND tabEmailHomePage_id = %d", emailHome_id);
        getDeleteBancoDados(conn, comandoSql);
    }


}
