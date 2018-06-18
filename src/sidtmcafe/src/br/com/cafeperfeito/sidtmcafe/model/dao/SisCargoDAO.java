package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisCargoVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisCargoDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    SisCargoVO sisCargoVO;
    List<SisCargoVO> sisCargoVOList;

    public SisCargoVO getSisCargoVO(int id) {
        buscaSisCargo(id);
        return sisCargoVO;
    }

    public List<SisCargoVO> getSisCargoVOList() {
        buscaSisCargo(0);
        return sisCargoVOList;
    }

    void buscaSisCargo(int id) {
        comandoSql = String.format("SELECT id, descricao FROM sisCargo %sORDER BY descricao",
                id > 0 ? String.format("WHERE id = %d ", id) : "");
        if (id == 0) sisCargoVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisCargoVO = new SisCargoVO();
                sisCargoVO.setId(rs.getInt("id"));
                sisCargoVO.setDescricao(rs.getString("descricao"));

                if (id == 0) sisCargoVOList.add(sisCargoVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
