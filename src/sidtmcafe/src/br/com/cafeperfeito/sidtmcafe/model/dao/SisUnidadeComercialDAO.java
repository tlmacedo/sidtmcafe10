package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUnidadeComercialVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SisUnidadeComercialDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    SisUnidadeComercialVO sisUnidadeComercialVO;
    List<SisUnidadeComercialVO> sisUnidadeComercialVOList;

    public SisUnidadeComercialVO getSisUnidadeComercialVO(int id) {
        buscaSisUnidadeComercialVO(id);
        return sisUnidadeComercialVO;
    }

    public List<SisUnidadeComercialVO> getSisUnidadeComercialVOList() {
        buscaSisUnidadeComercialVO(0);
        return sisUnidadeComercialVOList;
    }

    void buscaSisUnidadeComercialVO(int id) {
        comandoSql = "SELECT id, descricao, sigla ";
        comandoSql += "FROM sisUnidadeComercial ";
        if (id > 0) comandoSql += "WHERE id = " + id + " ";
        comandoSql += "ORDER BY sigla ";

        if (id == 0) sisUnidadeComercialVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);

        try {
            while (rs.next()) {
                sisUnidadeComercialVO = new SisUnidadeComercialVO();
                sisUnidadeComercialVO.setId(rs.getInt("id"));
                sisUnidadeComercialVO.setDescricao(rs.getString("descricao"));
                sisUnidadeComercialVO.setSigla(rs.getString("sigla"));

                if (id == 0) sisUnidadeComercialVOList.add(sisUnidadeComercialVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
