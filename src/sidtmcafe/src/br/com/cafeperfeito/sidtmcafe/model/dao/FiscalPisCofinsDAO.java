package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalPisCofinsVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiscalPisCofinsDAO extends BuscaBancoDados {
    ResultSet rs;
    FiscalPisCofinsVO fiscalPisCofinsVO;
    List<FiscalPisCofinsVO> fiscalPisCofinsVOList;
    boolean returnList = false;

    public FiscalPisCofinsVO getFiscalPisCofinsVO(int id) {
        getResultSet(String.format("SELECT * FROM fiscalPisCofins WHERE id = %d ORDER BY id", id), false);
        return fiscalPisCofinsVO;
    }

    public List<FiscalPisCofinsVO> getFiscalPisCofinsVOList() {
        fiscalPisCofinsVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM fiscalPisCofins ORDER BY id"), true);
        return fiscalPisCofinsVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                fiscalPisCofinsVO = new FiscalPisCofinsVO();
                fiscalPisCofinsVO.setId(rs.getInt("id"));
                fiscalPisCofinsVO.setDescricao(rs.getString("descricao"));
                if (returnList) fiscalPisCofinsVOList.add(fiscalPisCofinsVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
