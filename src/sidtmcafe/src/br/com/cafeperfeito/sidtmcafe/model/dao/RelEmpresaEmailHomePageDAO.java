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

    String comandoSql = "";
    RelEmpresaEmailHomePageVO relEmpresaEmailHomePageVO;
    List<RelEmpresaEmailHomePageVO> relEmpresaEmailHomePageVOList;

    public RelEmpresaEmailHomePageVO getRelEmpresaEmailHomePageVO(int empresa_id, int emailHoePage_id) {
        buscaRelEmpresaEmailHomePage(empresa_id, emailHoePage_id);
        return relEmpresaEmailHomePageVO;
    }

    public List<RelEmpresaEmailHomePageVO> getRelEmpresaEmailHomePageVOList(int empresa_id) {
        buscaRelEmpresaEmailHomePage(empresa_id, 0);
        return relEmpresaEmailHomePageVOList;
    }

    void buscaRelEmpresaEmailHomePage(int empresa_id, int emailHomePage_id) {
        comandoSql = String.format("SELECT tabEmpresa_id, tabEmailHomePage_id " +
                        "FROM relEmpresaEmailHomePage " +
                        "WHERE tabEmpresa_id = %d %s" +
                        "ORDER BY tabEmpresa_id, tabEmailHomePage_id",
                empresa_id,
                emailHomePage_id > 0 ? String.format("And tabEmailHomePage_id = %d ", emailHomePage_id) : "");

        if (emailHomePage_id == 0) relEmpresaEmailHomePageVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relEmpresaEmailHomePageVO = new RelEmpresaEmailHomePageVO();
                relEmpresaEmailHomePageVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaEmailHomePageVO.setTabEmailHomePage_id(rs.getInt("tabEmailHomePage_id"));

                if (emailHomePage_id == 0) relEmpresaEmailHomePageVOList.add(relEmpresaEmailHomePageVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    public int insertRelEmpresaEmailHomePage(Connection conn, int empresa_id, int emailHomePage_id) throws SQLException {
        comandoSql = String.format("INSERT INTO relEmpresaEmailHomePage (tabEmpresa_id, tabEmailHomePage_id) VALUES(%d, %d)",
                empresa_id,
                emailHomePage_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresaEmailHomePage(Connection conn, int empresa_id, int emailHome_id) throws SQLException {
        if (emailHome_id > 0)
            comandoSql = String.format("DELETE FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = %d AND tabEmailHomePage_id = %d",
                    empresa_id, emailHome_id);
        else
            comandoSql = String.format("DELETE FROM relEmpresaEmailHomePage WHERE tabEmpresa_id = %d",
                    empresa_id);
        getDeleteBancoDados(conn, comandoSql);
    }


}
