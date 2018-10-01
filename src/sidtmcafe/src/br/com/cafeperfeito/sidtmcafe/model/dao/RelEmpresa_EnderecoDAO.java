package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.RelEmpresa_EnderecoVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelEmpresa_EnderecoDAO extends ServiceBuscaBancoDados {

    RelEmpresa_EnderecoVO relEmpresa_enderecoVO = null;
    List<RelEmpresa_EnderecoVO> relEmpresa_enderecoVOList = null;

    public RelEmpresa_EnderecoVO getRelEmpresa_enderecoVO(int empresa_id, int endereco_id) {
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(endereco_id)));
        getResultSet("SELECT * FROM relEmpresa_Endereco WHERE tabEmpresa_id = ? AND tabEndereco_id = ? ");
        return relEmpresa_enderecoVO;
    }

    public List<RelEmpresa_EnderecoVO> getRelEmpresa_enderecoVOList(int empresa_id) {
        relEmpresa_enderecoVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getResultSet("SELECT * FROM relEmpresa_Endereco WHERE tabEmpresa_id = ? ");
        return relEmpresa_enderecoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tabEmpresa_id, tabEndereco_id ");
        try {
            while (rs.next()) {
                relEmpresa_enderecoVO = new RelEmpresa_EnderecoVO();
                relEmpresa_enderecoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresa_enderecoVO.setTabEndereco_id(rs.getInt("tabEndereco_id"));
                if (relEmpresa_enderecoVOList != null) relEmpresa_enderecoVOList.add(relEmpresa_enderecoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertRelEmpresa_endereco(Connection conn, int empresa_id, int endereco_id) throws SQLException {
        String comandoSql = "INSERT INTO relEmpresa_Endereco (tabEmpresa_id, tabEndereco_id) VALUES(?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        addParametro(new Pair<>("int", String.valueOf(endereco_id)));
        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresa_endereco(Connection conn, int empresa_id, int endereco_id) throws SQLException {
        String comandoSql = "DELETE FROM relEmpresa_Endereco WHERE tabEmpresa_id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        if (endereco_id > 0) {
            addParametro(new Pair<>("int", String.valueOf(endereco_id)));
            comandoSql += "AND tabEndereco_id = ?";
        }
        getDeleteBancoDados(conn, comandoSql);
    }

}
