package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalFreteTomadorServicoVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FiscalFreteTomadorServicoDAO extends ServiceBuscaBancoDados {

    FiscalFreteTomadorServicoVO fiscalFreteTomadorServicoVO = null;
    List<FiscalFreteTomadorServicoVO> fiscalFreteTomadorServicoVOList = null;

    public FiscalFreteTomadorServicoVO getFiscalFreteTomadorServicoVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalFreteTomadorServico WHERE id = ? ");
        return fiscalFreteTomadorServicoVO;
    }

    public List<FiscalFreteTomadorServicoVO> getFiscalFreteTomadorServicoVOList() {
        fiscalFreteTomadorServicoVOList = new ArrayList<>();
        getResultSet("SELECT * FROM fiscalFreteTomadorServico ");
        return fiscalFreteTomadorServicoVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                fiscalFreteTomadorServicoVO = new FiscalFreteTomadorServicoVO();
                fiscalFreteTomadorServicoVO.setId(rs.getInt("id"));
                fiscalFreteTomadorServicoVO.setDescricao(rs.getString("descricao"));
                if (fiscalFreteTomadorServicoVOList != null)
                    fiscalFreteTomadorServicoVOList.add(fiscalFreteTomadorServicoVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
