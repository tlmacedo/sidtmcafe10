package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalModeloNfeCteVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FiscalModeloNfeCteDAO extends ServiceBuscaBancoDados {

    FiscalModeloNfeCteVO fiscalModeloNfeCteVO = null;
    List<FiscalModeloNfeCteVO> fiscalModeloNfeCteVOList = null;

    public FiscalModeloNfeCteVO getFiscalModeloNfeCteVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalModeloNfeCte WHERE id = ? ");
        return fiscalModeloNfeCteVO;
    }

    public FiscalModeloNfeCteVO getFiscalModeloNfeCteVO(String descricao) {
        addNewParametro(new Pair<>("String", descricao));
        getResultSet("SELECT * FROM fiscalModeloNfeCte WHERE descricao = ? ");
        return fiscalModeloNfeCteVO;
    }

    public List<FiscalModeloNfeCteVO> getFiscalModeloNfeCteVOList() {
        fiscalModeloNfeCteVOList = new ArrayList<>();
        getResultSet("SELECT * FROM fiscalModeloNfeCte ");
        return fiscalModeloNfeCteVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                fiscalModeloNfeCteVO = new FiscalModeloNfeCteVO();
                fiscalModeloNfeCteVO.setId(rs.getInt("id"));
                fiscalModeloNfeCteVO.setDescricao(rs.getString("descricao"));
                if (fiscalModeloNfeCteVOList != null)
                    fiscalModeloNfeCteVOList.add(fiscalModeloNfeCteVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
