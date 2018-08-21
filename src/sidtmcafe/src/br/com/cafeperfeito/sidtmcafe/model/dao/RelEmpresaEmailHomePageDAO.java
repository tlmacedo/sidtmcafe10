package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaEmailHomePageVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaEmailHomePageDAO extends ServiceBuscaBancoDados {

    RelEmpresaEmailHomePageVO relEmpresaEmailHomePageVO = null;
    List<RelEmpresaEmailHomePageVO> relEmpresaEmailHomePageVOList = null;

    public RelEmpresaEmailHomePageVO getRelEmpresaEmailHomePageVO(int empresa_id, int emailHoePage_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHoePage_id)));
        getResultSet("SELECT * FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = ? AND tabEmailHomePage_id = ? ");
        return relEmpresaEmailHomePageVO;
    }

    public List<RelEmpresaEmailHomePageVO> getRelEmpresaEmailHomePageVOList(int empresa_id) {
        relEmpresaEmailHomePageVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = ? ");
        return relEmpresaEmailHomePageVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabEmailHomePage_id ");
        try {
            while (rs.next()) {
                relEmpresaEmailHomePageVO = new RelEmpresaEmailHomePageVO();
                relEmpresaEmailHomePageVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaEmailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));
                if (relEmpresaEmailHomePageVOList != null) relEmpresaEmailHomePageVOList.add(relEmpresaEmailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresaEmailHomePage(Connection conn, int empresa_id, int emailHomePage_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(emailHomePage_id)));
        String comandoSql = "INSERT INTO relEmpresaEmailHomePage (tabEmpresa_id, tabEmailHomePage_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresaEmailHomePage(Connection conn, int empresa_id, int emailHome_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        String comandoSql = "DELETE FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = ? ";
        if (emailHome_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(emailHome_id)));
            comandoSql += "AND tabEmailHomePage_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }


}
