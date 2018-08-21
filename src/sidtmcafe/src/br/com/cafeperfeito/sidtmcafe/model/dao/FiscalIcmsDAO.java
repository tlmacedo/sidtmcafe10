package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalIcmsVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FiscalIcmsDAO extends ServiceBuscaBancoDados {

    FiscalIcmsVO fiscalIcmsVO = null;
    List<FiscalIcmsVO> fiscalIcmsVOList = null;

    public FiscalIcmsVO getFiscalIcmsVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalIcms WHERE id = ? ");
        return fiscalIcmsVO;
    }

    public List<FiscalIcmsVO> getFiscalIcmsVOList() {
        fiscalIcmsVOList = new ArrayList<>();
        getResultSet("SELECT * FROM fiscalIcms ");
        return fiscalIcmsVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                fiscalIcmsVO = new FiscalIcmsVO();
                fiscalIcmsVO.setId(rs.getInt("id"));
                fiscalIcmsVO.setDescricao(rs.getString("descricao"));
                if (fiscalIcmsVOList!=null) fiscalIcmsVOList.add(fiscalIcmsVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
