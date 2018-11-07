package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisCargoVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisCargoDAO extends ServiceBuscaBancoDados {

    SisCargoVO sisCargoVO = null;
    List<SisCargoVO> sisCargoVOList = null;

    public SisCargoVO getSisCargoVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisCargo WHERE id = ? ");
        return sisCargoVO;
    }

    public List<SisCargoVO> getSisCargoVOList() {
        sisCargoVOList = new ArrayList<>();
        getResultSet("SELECT * FROM sisCargo ");
        return sisCargoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY descricao ");
        try {
            while (rs.next()) {
                sisCargoVO = new SisCargoVO();
                sisCargoVO.setId(rs.getInt("id"));
                sisCargoVO.setDescricao(rs.getString("descricao"));
                if (sisCargoVOList != null) sisCargoVOList.add(sisCargoVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
