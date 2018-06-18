package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalCstOrigemVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiscalCstOrigemDAO extends BuscaBancoDados {

    ResultSet rs;
    FiscalCstOrigemVO fiscalCstOrigemVO;
    List<FiscalCstOrigemVO> fiscalCstOrigemVOList;

    public FiscalCstOrigemVO getFiscalCstOrigemVO(int id) {
        getResultSet(String.format("SELECT * FROM fiscalCstOrigem WHERE id = %d ORDER BY id", id));
        return fiscalCstOrigemVO;
    }

    public List<FiscalCstOrigemVO> getFiscalCstOrigemVOList() {
        getResultSet(String.format("SELECT * FROM fiscalCstOrigem ORDER BY id"));
        return fiscalCstOrigemVOList;
    }

    void getResultSet(String comandoSql) {
        boolean returnList = false;
        rs = getResultadosBandoDados(comandoSql);
        try {
            if (rs.last())
                if (returnList = (rs.getRow() > 1))
                    fiscalCstOrigemVOList = new ArrayList<>();
            rs.beforeFirst();
            while (rs.next()) {
                fiscalCstOrigemVO = new FiscalCstOrigemVO();
                fiscalCstOrigemVO.setId(rs.getInt("id"));
                fiscalCstOrigemVO.setDescricao(rs.getString("descricao"));
                if (returnList) fiscalCstOrigemVOList.add(fiscalCstOrigemVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
