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

    String comandoSql = "";
    RelEmpresaEnderecoVO relEmpresaEnderecoVO;
    List<RelEmpresaEnderecoVO> relEmpresaEnderecoVOList;

    public RelEmpresaEnderecoVO getRelEmpresaEnderecoVO(int empresa_id, int endereco_id) {
        buscaRelEmpresaEnderecoVO(empresa_id, endereco_id);
        return relEmpresaEnderecoVO;
    }

    public List<RelEmpresaEnderecoVO> getRelEmpresaEnderecoVOList(int empresa_id) {
        buscaRelEmpresaEnderecoVO(empresa_id, 0);
        return relEmpresaEnderecoVOList;
    }

    void buscaRelEmpresaEnderecoVO(int empresa_id, int endereco_id) {
        comandoSql = "SELECT tabEmpresa_id, tabEndereco_id " +
                "FROM relEmpresaEndereco " +
                "WHERE tabEmpresa_id = '" + empresa_id + "' ";
        if (endereco_id > 0) comandoSql += "AND tabEndereco_id = '" + endereco_id + "' ";
        comandoSql += "ORDER BY tabEmpresa_id, tabEndereco_id";

        if (endereco_id == 0) relEmpresaEnderecoVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                relEmpresaEnderecoVO = new RelEmpresaEnderecoVO();
                relEmpresaEnderecoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaEnderecoVO.setTabEndereco_id(rs.getInt("tabEndereco_id"));

                if (endereco_id == 0) relEmpresaEnderecoVOList.add(relEmpresaEnderecoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertrelEmpresaEnderecoVO(Connection conn, int empresa_id, int endereco_id) throws SQLException {
        comandoSql = "INSERT INTO relEmpresaEndereco ";
        comandoSql += "(tabEmpresa_id, tabEndereco_id) ";
        comandoSql += "VALUES(";
        comandoSql += empresa_id + ", ";
        comandoSql += endereco_id + " ";
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void dedeteRelEmpresaEnderecoVO(Connection conn, int empresa_id) throws SQLException {
        comandoSql = "DELETE " +
                "FROM relEmpresaEndereco " +
                "WHERE tabEmpresa_id = '" + empresa_id + "' ";

        getDeleteBancoDados(conn, comandoSql);
    }

}
