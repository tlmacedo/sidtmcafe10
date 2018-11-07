package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUnidadeComercialVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SisUnidadeComercialDAO extends ServiceBuscaBancoDados {

    SisUnidadeComercialVO sisUnidadeComercialVO = null;
    List<SisUnidadeComercialVO> sisUnidadeComercialVOList = null;

    public SisUnidadeComercialVO getSisUnidadeComercialVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisUnidadeComercial WHERE id = ? ");
        return sisUnidadeComercialVO;
    }

    public List<SisUnidadeComercialVO> getSisUnidadeComercialVOList() {
        sisUnidadeComercialVOList = new ArrayList<>();
        getResultSet("SELECT * FROM sisUnidadeComercial ");
        return sisUnidadeComercialVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY sigla ");
        try {
            while (rs.next()) {
                sisUnidadeComercialVO = new SisUnidadeComercialVO();
                sisUnidadeComercialVO.setId(rs.getInt("id"));
                sisUnidadeComercialVO.setDescricao(rs.getString("descricao"));
                sisUnidadeComercialVO.setSigla(rs.getString("sigla"));
                if (sisUnidadeComercialVOList != null) sisUnidadeComercialVOList.add(sisUnidadeComercialVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
