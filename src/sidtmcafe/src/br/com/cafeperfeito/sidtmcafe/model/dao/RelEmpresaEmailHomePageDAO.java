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
        buscaRelEmpresaEmailHomePageVO(empresa_id, emailHoePage_id);
        return relEmpresaEmailHomePageVO;
    }

    public List<RelEmpresaEmailHomePageVO> getRelEmpresaEmailHomePageVOList(int empresa_id) {
        buscaRelEmpresaEmailHomePageVO(empresa_id, 0);
        return relEmpresaEmailHomePageVOList;
    }

    void buscaRelEmpresaEmailHomePageVO(int empresa_id, int emailHomePage_id) {
        comandoSql = "SELECT tabEmpresa_id, tabEmailHomePage_id ";
        comandoSql += "FROM relEmpresaEmailHomePage ";
        comandoSql += "WHERE tabEmpresa_id = " + empresa_id + " ";
        if (emailHomePage_id > 0) comandoSql += "AND tabEmailHomePage_id = " + emailHomePage_id + " ";
        comandoSql += "ORDER BY tabEmpresa_id, tabEmailHomePage_id";

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

    public int insertRelEmpresaEmailHomePageVO(Connection conn, int empresa_id, int emailHomePage_id) throws SQLException {
        comandoSql = "INSERT INTO relEmpresaEmailHomePage ";
        comandoSql += "(tabEmpresa_id, tabEmailHomePage_id) ";
        comandoSql += "VALUES(";
        comandoSql += empresa_id + ", ";
        comandoSql += emailHomePage_id + " ";
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresaEmailHomePageVO(Connection conn, int empresa_id) throws SQLException {
        comandoSql = "DELETE ";
        comandoSql += "FROM relEmpresaEmailHomePage ";
        comandoSql += "WHERE tabEmpresa_id = " + empresa_id + " ";
        getDeleteBancoDados(conn, comandoSql);
    }


}
