package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresa_TelefoneVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresa_TelefoneDAO extends ServiceBuscaBancoDados {

    RelEmpresa_TelefoneVO relEmpresa_telefoneVO = null;
    List<RelEmpresa_TelefoneVO> relEmpresa_telefoneVOList = null;

    public RelEmpresa_TelefoneVO getRelEmpresa_telefoneVO(int empresa_id, int telefone_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        getResultSet("SELECT * FROM relEmpresa_Telefone WHERE tabEmpresa_id = ? AND tabTelefone_id = ? ");
        return relEmpresa_telefoneVO;
    }

    public List<RelEmpresa_TelefoneVO> getRelEmpresa_telefoneVOList(int empresa_id) {
        relEmpresa_telefoneVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresa_Telefone WHERE tabEmpresa_id = ? ");
        return relEmpresa_telefoneVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabTelefone_id ");
        try {
            while (rs.next()) {
                relEmpresa_telefoneVO = new RelEmpresa_TelefoneVO();
                relEmpresa_telefoneVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresa_telefoneVO.setTabTelefone_id(rs.getInt("tabTelefone_id"));
                if (relEmpresa_telefoneVOList != null) relEmpresa_telefoneVOList.add(relEmpresa_telefoneVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresa_telefoneVO(Connection conn, int empresa_id, int telefone_id) throws SQLException {
        String comandoSql = "INSERT INTO relEmpresa_Telefone " +
                "(tabEmpresa_id, " +
                "tabTelefone_id) " +
                "VALUES (" +
                "?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(telefone_id)));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresa_telefoneVO(Connection conn, int empresa_id, int telefone_id) throws SQLException {
        String comandoSql = "DELETE FROM relEmpresa_Telefone " +
                "WHERE tabEmpresa_id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        if (telefone_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(telefone_id)));
            comandoSql += "AND tabTelefone_id = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
