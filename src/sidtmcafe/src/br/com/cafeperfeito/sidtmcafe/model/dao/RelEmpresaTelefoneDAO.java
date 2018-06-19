package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaTelefoneVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaTelefoneDAO extends BuscaBancoDados {

    ResultSet rs;
    RelEmpresaTelefoneVO relEmpresaTelefoneVO;
    List<RelEmpresaTelefoneVO> relEmpresaTelefoneVOList;
    boolean returnList = false;

    public RelEmpresaTelefoneVO getRelEmpresaTelefoneVO(int empresa_id, int telefone_id) {
        getResultSet(String.format("SELECT * FROM relEmpresaTelefone WHERE tabEmpresa_id = %d " +
                "AND tabTelefone_id = %d ORDER BY tabEmpresa_id, tabTelefone_id", empresa_id, telefone_id), false);
        return relEmpresaTelefoneVO;
    }

    public List<RelEmpresaTelefoneVO> getRelEmpresaTelefoneVOList(int empresa_id) {
        relEmpresaTelefoneVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM relEmpresaTelefone WHERE tabEmpresa_id = %d " +
                "ORDER BY tabEmpresa_id, tabTelefone_id", empresa_id), true);
        return relEmpresaTelefoneVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relEmpresaTelefoneVO = new RelEmpresaTelefoneVO();
                relEmpresaTelefoneVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaTelefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));
                if (returnList) relEmpresaTelefoneVOList.add(relEmpresaTelefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelEmpresaTelefoneVO(Connection conn, int empresa_id, int telefone_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relEmpresaTelefone (tabEmpresa_id, tabTelefone_id) " +
                "VALUES(%d, %d)", empresa_id, telefone_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaTelefoneVO(Connection conn, int empresa_id, int telefone_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relEmpresaTelefone WHERE tabEmpresa_id %d ", empresa_id);
        if (telefone_id > 0)
            comandoSql += String.format("AND tabTelefone_id = %d", telefone_id);
        getDeleteBancoDados(conn, comandoSql);
    }
}
