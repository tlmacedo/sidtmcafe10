package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaContatoVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaContatoDAO extends ServiceBuscaBancoDados {

    RelEmpresaContatoVO relEmpresaContatoVO = null;
    List<RelEmpresaContatoVO> relEmpresaContatoVOList = null;

    public RelEmpresaContatoVO getRelEmpresaContatoVO(int empresa_id, int contato_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(contato_id)));
        getResultSet("SELECT * FROM relEmpresaContato WHERE tabEmpresa_id = ? AND tabContato_id = ? ");
        return relEmpresaContatoVO;
    }

    public List<RelEmpresaContatoVO> getRelEmpresaContatoVOList(int empresa_id) {
        relEmpresaContatoVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresaContato WHERE tabEmpresa_id = ? ");
        return relEmpresaContatoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabContato_id");
        try {
            while (rs.next()) {
                relEmpresaContatoVO = new RelEmpresaContatoVO();
                relEmpresaContatoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaContatoVO.setTabContato_id(rs.getInt("tabContato_id"));
                if (relEmpresaContatoVOList != null) relEmpresaContatoVOList.add(relEmpresaContatoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresaContatoVO(Connection conn, int empresa_id, int contato_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(contato_id)));
        String comandoSql = "INSERT INTO relEmpresaContato (tabEmpresa_id, tabContato_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaContatoVO(Connection conn, int empresa_id, int contato_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        String comandoSql = "DELETE FROM relEmpresaContato WHERE tabEmpresa_id = ? ";
        if (contato_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(contato_id)));
            comandoSql += "AND tabContato = ? ";
        }
        getDeleteBancoDados(conn, comandoSql);
    }
}
