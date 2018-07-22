package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUnidadeComercialVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SisUnidadeComercialDAO extends BuscaBancoDados {

    ResultSet rs;
    SisUnidadeComercialVO sisUnidadeComercialVO;
    List<SisUnidadeComercialVO> sisUnidadeComercialVOList;
    boolean returnList = false;

    public SisUnidadeComercialVO getSisUnidadeComercialVO(int id) {
        getResultSet(String.format("SELECT * FROM sisUnidadeComercial WHERE id = %d ORDER BY sigla", id), false);
        return sisUnidadeComercialVO;
    }

    public List<SisUnidadeComercialVO> getSisUnidadeComercialVOList() {
        sisUnidadeComercialVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisUnidadeComercial ORDER BY sigla"), true);
        return sisUnidadeComercialVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisUnidadeComercialVO = new SisUnidadeComercialVO();
                sisUnidadeComercialVO.setId(rs.getInt("id"));
                sisUnidadeComercialVO.setDescricao(rs.getString("descricao"));
                sisUnidadeComercialVO.setSigla(rs.getString("sigla"));
                if (returnList) sisUnidadeComercialVOList.add(sisUnidadeComercialVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
