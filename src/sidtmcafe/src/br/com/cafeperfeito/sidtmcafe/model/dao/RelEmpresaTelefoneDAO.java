package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaTelefoneVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaTelefoneDAO extends ServiceBuscaBancoDados {

    RelEmpresaTelefoneVO relEmpresaTelefoneVO = null;
    List<RelEmpresaTelefoneVO> relEmpresaTelefoneVOList = null;

    public RelEmpresaTelefoneVO getRelEmpresaTelefoneVO(int empresa_id, int telefone_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        getResultSet("SELECT * FROM relEmpresaTelefone WHERE tabEmpresa_id = ? AND tabTelefone_id = ? ");
        return relEmpresaTelefoneVO;
    }

    public List<RelEmpresaTelefoneVO> getRelEmpresaTelefoneVOList(int empresa_id) {
        relEmpresaTelefoneVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresaTelefone WHERE tabEmpresa_id = ? ");
        return relEmpresaTelefoneVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabTelefone_id ");
        try {
            while (rs.next()) {
                relEmpresaTelefoneVO = new RelEmpresaTelefoneVO();
                relEmpresaTelefoneVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaTelefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));
                if (relEmpresaTelefoneVOList != null) relEmpresaTelefoneVOList.add(relEmpresaTelefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresaTelefoneVO(Connection conn, int empresa_id, int telefone_id) throws SQLException {
        String comandoSql = "INSERT INTO relEmpresaTelefone " +
                "(tabEmpresa_id, " +
                "tabTelefone_id) " +
                "VALUES (" +
                "?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaTelefoneVO(Connection conn, int empresa_id, int telefone_id) throws SQLException {
        String comandoSql = "DELETE FROM relEmpresaTelefone " +
                "WHERE tabEmpresa_id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        if (telefone_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(telefone_id)));
            comandoSql += "AND tabTelefone_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
