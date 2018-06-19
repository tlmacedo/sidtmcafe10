package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaEnderecoVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaEnderecoDAO extends BuscaBancoDados {

    ResultSet rs;
    RelEmpresaEnderecoVO relEmpresaEnderecoVO;
    List<RelEmpresaEnderecoVO> relEmpresaEnderecoVOList;
    boolean returnList = false;

    public RelEmpresaEnderecoVO getRelEmpresaEnderecoVO(int empresa_id, int endereco_id) {
        getResultSet(String.format("SELECT * FROM relEmpresaEndereco WHERE tabEmpresa_id = %d " +
                "AND tabEndereco_id = %d ORDER BY tabEmpresa_id, tabEndereco_id", empresa_id, endereco_id), false);
        return relEmpresaEnderecoVO;
    }

    public List<RelEmpresaEnderecoVO> getRelEmpresaEnderecoVOList(int empresa_id) {
        relEmpresaEnderecoVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM relEmpresaEndereco WHERE tabEmpresa_id = %d " +
                "ORDER BY tabEmpresa_id, tabEndereco_id", empresa_id), true);
        return relEmpresaEnderecoVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relEmpresaEnderecoVO = new RelEmpresaEnderecoVO();
                relEmpresaEnderecoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaEnderecoVO.setTabEndereco_id(rs.getInt("tabEndereco_id"));
                if (returnList) relEmpresaEnderecoVOList.add(relEmpresaEnderecoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelEmpresaEndereco(Connection conn, int empresa_id, int endereco_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO relEmpresaEndereco (tabEmpresa_id, tabEndereco_id) " +
                "VALUES(%d, %d)", empresa_id, endereco_id);
        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresaEndereco(Connection conn, int empresa_id, int endereco_id) throws SQLException {
        String comandoSql = String.format("DELETE FROM relEmpresaEndereco WHERE tabEmpresa_id = %d ", empresa_id);
        if (endereco_id > 0)
            comandoSql = String.format("AND tabEndereco_id = %d", endereco_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
