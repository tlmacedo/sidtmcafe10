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

    public FiscalIcmsVO getFiscalIcmsVO(int id) {
        getResultSet(String.format("SELECT * FROM fiscalIcms WHERE id = %d ORDER BY id", id));
        return fiscalIcmsVO;
    }

    public List<FiscalIcmsVO> getFiscalIcmsVOList() {
        getResultSet(String.format("SELECT * FROM fiscalIcms ORDER BY id"));
        return fiscalIcmsVOList;
    }

    void getResultSet(String comandoSql) {
        boolean returnList = false;
        rs = getResultadosBandoDados(comandoSql);
        try {
            if (rs.last())
                if (returnList = (rs.getRow() > 1))
                    fiscalIcmsVOList = new ArrayList<>();
            rs.beforeFirst();
            while (rs.next()) {
                fiscalIcmsVO = new FiscalIcmsVO();
                fiscalIcmsVO.setId(rs.getInt("id"));
                fiscalIcmsVO.setDescricao(rs.getString("descricao"));
                if (returnList) fiscalIcmsVOList.add(fiscalIcmsVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
