package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTipoEnderecoVO;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisTipoEnderecoDAO extends BuscaBancoDados {

    SisTipoEnderecoVO sisTipoEnderecoVO = null;
    List<SisTipoEnderecoVO> sisTipoEnderecoVOList = null;

    public SisTipoEnderecoVO getSisTipoEnderecoVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisTipoEndereco WHERE id = ? ");
        return sisTipoEnderecoVO;
    }

    public List<SisTipoEnderecoVO> getSisTipoEnderecoVOList() {
        sisTipoEnderecoVOList = new ArrayList<>();
        getResultSet("SELECT * FROM sisTipoEndereco ");
        return sisTipoEnderecoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                sisTipoEnderecoVO = new SisTipoEnderecoVO();
                sisTipoEnderecoVO.setId(rs.getInt("id"));
                sisTipoEnderecoVO.setDescricao(rs.getString("descricao"));
                if (sisTipoEnderecoVOList != null) sisTipoEnderecoVOList.add(sisTipoEnderecoVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
