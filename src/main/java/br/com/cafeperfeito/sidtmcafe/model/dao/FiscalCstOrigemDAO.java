package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalCstOrigemVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FiscalCstOrigemDAO extends ServiceBuscaBancoDados {

    FiscalCstOrigemVO fiscalCstOrigemVO = null;
    List<FiscalCstOrigemVO> fiscalCstOrigemVOList = null;

    public FiscalCstOrigemVO getFiscalCstOrigemVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalCstOrigem WHERE id = ? ");
        return fiscalCstOrigemVO;
    }

    public List<FiscalCstOrigemVO> getFiscalCstOrigemVOList() {
        fiscalCstOrigemVOList = new ArrayList<>();
        getResultSet("SELECT * FROM fiscalCstOrigem ");
        return fiscalCstOrigemVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                fiscalCstOrigemVO = new FiscalCstOrigemVO();
                fiscalCstOrigemVO.setId(rs.getInt("id"));
                fiscalCstOrigemVO.setDescricao(rs.getString("descricao"));
                if (fiscalCstOrigemVOList != null) fiscalCstOrigemVOList.add(fiscalCstOrigemVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
