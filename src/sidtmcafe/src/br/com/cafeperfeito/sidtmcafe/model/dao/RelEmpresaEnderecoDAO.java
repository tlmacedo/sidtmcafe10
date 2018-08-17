package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresaEnderecoVO;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresaEnderecoDAO extends BuscaBancoDados {

    RelEmpresaEnderecoVO relEmpresaEnderecoVO = null;
    List<RelEmpresaEnderecoVO> relEmpresaEnderecoVOList = null;

    public RelEmpresaEnderecoVO getRelEmpresaEnderecoVO(int empresa_id, int endereco_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(endereco_id)));
        getResultSet("SELECT * FROM relEmpresaEndereco WHERE tabEmpresa_id = ? AND tabEndereco_id = ? ");
        return relEmpresaEnderecoVO;
    }

    public List<RelEmpresaEnderecoVO> getRelEmpresaEnderecoVOList(int empresa_id) {
        relEmpresaEnderecoVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresaEndereco WHERE tabEmpresa_id = ? ");
        return relEmpresaEnderecoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabEndereco_id ");
        try {
            while (rs.next()) {
                relEmpresaEnderecoVO = new RelEmpresaEnderecoVO();
                relEmpresaEnderecoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaEnderecoVO.setTabEndereco_id(rs.getInt("tabEndereco_id"));
                if (relEmpresaEnderecoVOList != null) relEmpresaEnderecoVOList.add(relEmpresaEnderecoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresaEndereco(Connection conn, int empresa_id, int endereco_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(endereco_id)));
        String comandoSql = "INSERT INTO relEmpresaEndereco (tabEmpresa_id, tabEndereco_id) VALUES(?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresaEndereco(Connection conn, int empresa_id, int endereco_id) throws SQLException {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        String comandoSql = "DELETE FROM relEmpresaEndereco WHERE tabEmpresa_id = ? ";
        if (endereco_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(endereco_id)));
            comandoSql += "AND tabEndereco_id = ?";
        }
        getDeleteBancoDados(conn, comandoSql);
    }

}
