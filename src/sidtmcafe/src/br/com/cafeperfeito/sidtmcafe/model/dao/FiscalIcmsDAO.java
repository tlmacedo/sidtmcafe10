package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalIcmsVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiscalIcmsDAO extends BuscaBancoDados {

    ResultSet rs;
    FiscalIcmsVO fiscalIcmsVO;
    List<FiscalIcmsVO> fiscalIcmsVOList;
    boolean returnList = false;

    public FiscalIcmsVO getFiscalIcmsVO(int id) {
        getResultSet(String.format("SELECT * FROM fiscalIcms WHERE id = %d ORDER BY id", id), false);
        return fiscalIcmsVO;
    }

    public List<FiscalIcmsVO> getFiscalIcmsVOList() {
        fiscalIcmsVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM fiscalIcms ORDER BY id"), true);
        return fiscalIcmsVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                fiscalIcmsVO = new FiscalIcmsVO();
                fiscalIcmsVO.setId(rs.getInt("id"));
                fiscalIcmsVO.setDescricao(rs.getString("descricao"));
                if (returnList) fiscalIcmsVOList.add(fiscalIcmsVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
