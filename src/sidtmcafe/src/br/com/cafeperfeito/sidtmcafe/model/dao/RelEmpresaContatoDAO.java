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

    String comandoSql = "";
    RelEmpresaContatoVO relEmpresaContatoVO;
    List<RelEmpresaContatoVO> relEmpresaContatoVOList;

    public RelEmpresaContatoVO getRelEmpresaContatoVO(int empresa_id, int contato_id) {
        buscaRelEmpresaContatoVO(empresa_id, contato_id);
        return relEmpresaContatoVO;
    }

    public List<RelEmpresaContatoVO> getRelEmpresaContatoVOList(int empresa_id) {
        buscaRelEmpresaContatoVO(empresa_id, 0);
        return relEmpresaContatoVOList;
    }

    void buscaRelEmpresaContatoVO(int empresa_id, int contato_id) {
        comandoSql = "SELECT tabEmpresa_id, tabContato_id " +
                "FROM relEmpresaContato " +
                "WHERE tabEmpresa_id = '" + empresa_id + "' ";
        if (contato_id > 0) comandoSql += "AND tabContato_id = '" + contato_id + "' ";
        comandoSql += "ORDER BY tabEmpresa_id, tabContato_id ";

        rs = getResultadosBandoDados(comandoSql);
        if (contato_id == 0) relEmpresaContatoVOList = new ArrayList<>();
        try {
            while (rs.next()) {
                relEmpresaContatoVO = new RelEmpresaContatoVO();
                relEmpresaContatoVO.setTabEmpresa_id(rs.getInt("tabEmpresa_id"));
                relEmpresaContatoVO.setTabContato_id(rs.getInt("tabContato_id"));

                if (contato_id == 0) relEmpresaContatoVOList.add(relEmpresaContatoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public int insertRelEmpresaContatoVO(Connection conn, int empresa_id, int contato_id) throws SQLException {
        comandoSql = "INSERT INTO relEmpresaContato ";
        comandoSql += "(tabEmpresa_id, tabContato_id) ";
        comandoSql += "VALUES (";
        comandoSql += empresa_id + ", ";
        comandoSql += contato_id + " ";
        comandoSql += ") ";

        return getInsertBancoDados(conn, comandoSql);
    }

    public void deleteRelEmpresaContatoVO(Connection conn, int empresa_id) throws SQLException {
        comandoSql = "DELETE " +
                "FROM relEmpresaContato " +
                "WHERE tabEmpresa_id = '" + empresa_id + "' ";

        getDeleteBancoDados(conn, comandoSql);
    }
}
