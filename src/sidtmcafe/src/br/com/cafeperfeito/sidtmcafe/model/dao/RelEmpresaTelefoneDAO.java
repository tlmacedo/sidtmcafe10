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

    String comandoSql = "";
    RelEmpresaTelefoneVO relEmpresaTelefoneVO;
    List<RelEmpresaTelefoneVO> relEmpresaTelefoneVOList;

    public RelEmpresaTelefoneVO getRelEmpresaTelefoneVO(int empresa_id, int telefone_id) {
        buscaRelEmpresaTelefoneVO(empresa_id, telefone_id);
        return relEmpresaTelefoneVO;
    }

    public List<RelEmpresaTelefoneVO> getRelEmpresaTelefoneVOList(int empresa_id) {
        buscaRelEmpresaTelefoneVO(empresa_id, 0);
        return relEmpresaTelefoneVOList;
    }

    void buscaRelEmpresaTelefoneVO(int empresa_id, int telefone_id) {
        comandoSql = "SELECT tabEmpresa_id, tabTelefone_id " +
                "FROM relEmpresaTelefone " +
                "WHERE tabEmpresa_id = '" + empresa_id + "' ";
        if (telefone_id > 0) comandoSql += "AND tabTelefone_id = '" + telefone_id + "' ";
        comandoSql += "ORDER BY tabEmpresa_id, tabTelefone_id ";

        rs = getResultadosBandoDados(comandoSql);
        if (telefone_id == 0) relEmpresaTelefoneVOList = new ArrayList<>();
        try {
            while (rs.next()) {
                relEmpresaTelefoneVO = new RelEmpresaTelefoneVO();
                relEmpresaTelefoneVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaTelefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));

                if (telefone_id == 0) relEmpresaTelefoneVOList.add(relEmpresaTelefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelEmpresaTelefoneVO(Connection conn, int empresa_id, int telefone_id) throws SQLException {
        comandoSql = "INSERT INTO relEmpresaTelefone ";
        comandoSql += "(tabEmpresa_id, tabTelefone_id) ";
        comandoSql += "VALUES (";
        comandoSql += empresa_id + ", ";
        comandoSql += telefone_id + " ";
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaTelefoneVO(Connection conn, int empresa_id) throws SQLException {
        comandoSql = "DELETE " +
                "FROM relEmpresaTelefone " +
                "WHERE tabEmpresa_id = '" + empresa_id + "' ";

        getDeleteBancoDados(conn, comandoSql);
    }
}
