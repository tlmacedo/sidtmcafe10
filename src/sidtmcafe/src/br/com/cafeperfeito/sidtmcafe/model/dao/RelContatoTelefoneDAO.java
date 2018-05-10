package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelContatoTelefoneVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelContatoTelefoneDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    RelContatoTelefoneVO relContatoTelefoneVO;
    List<RelContatoTelefoneVO> relContatoTelefoneVOList;

    public RelContatoTelefoneVO getRelContatoTelefoneVO(int contato_id, int telefone_id) {
        buscaRelContatoTelefoneVO(contato_id, telefone_id);
        return relContatoTelefoneVO;
    }

    public List<RelContatoTelefoneVO> getRelContatoTelefoneVOList(int contato_id) {
        buscaRelContatoTelefoneVO(contato_id, 0);
        return relContatoTelefoneVOList;
    }

    void buscaRelContatoTelefoneVO(int contato_id, int telefone_id) {
        comandoSql = "SELECT tabContato_id, tabTelefone_id ";
        comandoSql += "FROM relContatoTelefone ";
        comandoSql += "WHERE tabContato_id = " + contato_id + " ";
        if (telefone_id > 0) comandoSql += "AND tabTelefone = " + telefone_id + " ";
        comandoSql += "ORDER BY tabTelefone_id";

        if (telefone_id == 0) relContatoTelefoneVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relContatoTelefoneVO = new RelContatoTelefoneVO();
                relContatoTelefoneVO.setTabContato_id(rs.getInt("tabContato_id"));
                relContatoTelefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));

                if (telefone_id == 0) relContatoTelefoneVOList.add(relContatoTelefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelContatoTelefoneVO(Connection conn, int contato_id, int telefone_id) throws SQLException {
        comandoSql = "INSERT INTO relContatoTelefone ";
        comandoSql += "(tabContato_id, tabTelefone_id) ";
        comandoSql += "VALUES (";
        comandoSql += contato_id + ", ";
        comandoSql += telefone_id + " ";
        comandoSql += ")";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelContatoTelefoneVO(Connection conn, int contato_id) throws SQLException {
        comandoSql = "DELETE ";
        comandoSql += "FROM relContatoTelefone ";
        comandoSql += "WHERE tabContato_id = " + contato_id + " ";

        getDeleteBancoDados(conn, comandoSql);
    }
}
