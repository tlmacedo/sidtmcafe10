package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalTributoSefazAmVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FiscalTributoSefazAmDAO extends ServiceBuscaBancoDados {
    FiscalTributoSefazAmVO fiscalTributoSefazAmVO = null;
    List<FiscalTributoSefazAmVO> fiscalTributoSefazAmVOList = null;

    public FiscalTributoSefazAmVO getFiscalTributoSefazAmVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalTributoSefazAm WHERE id = ? ");
        return fiscalTributoSefazAmVO;
    }

    public List<FiscalTributoSefazAmVO> getFiscalTributoSefazAmVOList() {
        fiscalTributoSefazAmVOList = new ArrayList<>();
        getResultSet("SELECT * FROM fiscalTributoSefazAm ");
        return fiscalTributoSefazAmVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                fiscalTributoSefazAmVO = new FiscalTributoSefazAmVO();
                fiscalTributoSefazAmVO.setId(rs.getInt("id"));
                fiscalTributoSefazAmVO.setDescricao(rs.getString("descricao"));
                if (fiscalTributoSefazAmVOList != null) fiscalTributoSefazAmVOList.add(fiscalTributoSefazAmVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
