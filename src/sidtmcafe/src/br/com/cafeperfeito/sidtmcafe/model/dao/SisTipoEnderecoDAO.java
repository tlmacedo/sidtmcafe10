package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTipoEnderecoVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisTipoEnderecoDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    SisTipoEnderecoVO sisTipoEnderecoVO;
    List<SisTipoEnderecoVO> sisTipoEnderecoVOList;

    public SisTipoEnderecoVO getSisTipoEnderecoVO(int id) {
        buscaSisTipoEnderecoVO(id);
        return sisTipoEnderecoVO;
    }

    public List<SisTipoEnderecoVO> getSisTipoEnderecoVOList() {
        buscaSisTipoEnderecoVO(0);
        return sisTipoEnderecoVOList;
    }

    void buscaSisTipoEnderecoVO(int id) {
        comandoSql = "SELECT id, descricao " +
                "FROM sisTipoEndereco ";
        if (id > 0) comandoSql += "WHERE id = '" + id + "' ";
        comandoSql += "ORDER BY id ";

        if (id == 0) sisTipoEnderecoVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisTipoEnderecoVO = new SisTipoEnderecoVO();
                sisTipoEnderecoVO.setId(rs.getInt("id"));
                sisTipoEnderecoVO.setDescricao(rs.getString("descricao"));

                if (id == 0) sisTipoEnderecoVOList.add(sisTipoEnderecoVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
