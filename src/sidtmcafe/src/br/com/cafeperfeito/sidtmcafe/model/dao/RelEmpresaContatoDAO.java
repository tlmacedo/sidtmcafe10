package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaContatoVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaContatoDAO extends BuscaBancoDados {

    ResultSet rs;
    RelEmpresaContatoVO relEmpresaContatoVO;
    List<RelEmpresaContatoVO> relEmpresaContatoVOList;
    boolean returnList = false;

    public RelEmpresaContatoVO getRelEmpresaContatoVO(int empresa_id, int contato_id) {
        getResultSet(String.format("SELECT * FROM relEmpresaContato WHERE tabEmpresa_id = %d " +
                "AND tabContato_id = %d ORDER BY tabEmpresa_id, tabContato_id", empresa_id, contato_id), false);
        return relEmpresaContatoVO;
    }

    public List<RelEmpresaContatoVO> getRelEmpresaContatoVOList(int empresa_id) {
        relEmpresaContatoVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM relEmpresaContato WHERE tabEmpresa_id = %d " +
                "ORDER BY tabEmpresa_id, tabContato_id", empresa_id), true);
        return relEmpresaContatoVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relEmpresaContatoVO = new RelEmpresaContatoVO();
                relEmpresaContatoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaContatoVO.setTabContato_id(rs.getInt("tabContato_id"));
                if (returnList) relEmpresaContatoVOList.add(relEmpresaContatoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresaContatoVO(Connection conn, int empresa_id, int contato_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relEmpresaContato (tabEmpresa_id, tabContato_id) " +
                "VALUES(%d, %d)", empresa_id, contato_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaContatoVO(Connection conn, int empresa_id, int contato_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relEmpresaContato WHERE tabEmpresa_id = %d ", empresa_id);
        if (contato_id > 0)
            comandoSql += String.format("AND tabContato = %d", contato_id);
        getDeleteBancoDados(conn, comandoSql);
    }
}
