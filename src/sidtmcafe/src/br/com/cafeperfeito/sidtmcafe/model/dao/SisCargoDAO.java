package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisCargoVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisCargoDAO extends BuscaBancoDados {

    ResultSet rs;
    SisCargoVO sisCargoVO;
    List<SisCargoVO> sisCargoVOList;
    boolean returnList = false;

    public SisCargoVO getSisCargoVO(int id) {
        getResultSet(String.format("SELECT * FROM sisCargo WHERE id = %d ORDER BY descricao", id), false);
        return sisCargoVO;
    }

    public List<SisCargoVO> getSisCargoVOList() {
        sisCargoVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisCargo ORDER BY descricao"), true);
        return sisCargoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisCargoVO = new SisCargoVO();
                sisCargoVO.setId(rs.getInt("id"));
                sisCargoVO.setDescricao(rs.getString("descricao"));
                if (returnList) sisCargoVOList.add(sisCargoVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
