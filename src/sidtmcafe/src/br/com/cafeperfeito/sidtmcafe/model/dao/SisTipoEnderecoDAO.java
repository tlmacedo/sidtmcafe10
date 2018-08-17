package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTipoEnderecoVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisTipoEnderecoDAO extends BuscaBancoDados {

    ResultSet rs;
    SisTipoEnderecoVO sisTipoEnderecoVO;
    List<SisTipoEnderecoVO> sisTipoEnderecoVOList;
    boolean returnList = false;

    public SisTipoEnderecoVO getSisTipoEnderecoVO(int id) {
        getResultSet(String.format("SELECT * FROM sisTipoEndereco WHERE id = %d ORDER BY id", id), false);
        return sisTipoEnderecoVO;
    }

    public List<SisTipoEnderecoVO> getSisTipoEnderecoVOList() {
        sisTipoEnderecoVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisTipoEndereco ORDER BY id"), true);
        return sisTipoEnderecoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisTipoEnderecoVO = new SisTipoEnderecoVO();
                sisTipoEnderecoVO.setId(rs.getInt("id"));
                sisTipoEnderecoVO.setDescricao(rs.getString("descricao"));
                if (returnList) sisTipoEnderecoVOList.add(sisTipoEnderecoVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
