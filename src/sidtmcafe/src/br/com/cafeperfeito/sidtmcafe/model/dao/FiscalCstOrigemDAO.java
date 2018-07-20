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
    boolean returnList = false;

    public FiscalCstOrigemVO getFiscalCstOrigemVO(int id) {
        getResultSet(String.format("SELECT * FROM fiscalCstOrigem WHERE id = %d ORDER BY id", id), false);
        return fiscalCstOrigemVO;
    }

    public List<FiscalCstOrigemVO> getFiscalCstOrigemVOList() {
        fiscalCstOrigemVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM fiscalCstOrigem ORDER BY id"), true);
        return fiscalCstOrigemVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                fiscalCstOrigemVO = new FiscalCstOrigemVO();
                fiscalCstOrigemVO.setId(rs.getInt("id"));
                fiscalCstOrigemVO.setDescricao(rs.getString("descricao"));
                if (returnList) fiscalCstOrigemVOList.add(fiscalCstOrigemVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
