package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresa_ContatoVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresa_ContatoDAO extends ServiceBuscaBancoDados {

    RelEmpresa_ContatoVO relEmpresa_contatoVO = null;
    List<RelEmpresa_ContatoVO> relEmpresa_contatoVOList = null;

    public RelEmpresa_ContatoVO getRelEmpresa_contatoVO(int empresa_id, int contato_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(contato_id)));
        getResultSet("SELECT * FROM relEmpresa_Contato WHERE tabEmpresa_id = ? AND tabContato_id = ? ");
        return relEmpresa_contatoVO;
    }

    public List<RelEmpresa_ContatoVO> getRelEmpresa_contatoVOList(int empresa_id) {
        relEmpresa_contatoVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresa_Contato WHERE tabEmpresa_id = ? ");
        return relEmpresa_contatoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabContato_id");
        try {
            while (rs.next()) {
                relEmpresa_contatoVO = new RelEmpresa_ContatoVO();
                relEmpresa_contatoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresa_contatoVO.setTabContato_id(rs.getInt("tabContato_id"));
                if (relEmpresa_contatoVOList != null) relEmpresa_contatoVOList.add(relEmpresa_contatoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresa_contatoVO(Connection conn, int empresa_id, int contato_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(contato_id)));
        String comandoSql = "INSERT INTO relEmpresa_Contato (tabEmpresa_id, tabContato_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresa_contatoVO(Connection conn, int empresa_id, int contato_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        String comandoSql = "DELETE FROM relEmpresa_Contato WHERE tabEmpresa_id = ? ";
        if (contato_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(contato_id)));
            comandoSql += "AND tabContato = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
