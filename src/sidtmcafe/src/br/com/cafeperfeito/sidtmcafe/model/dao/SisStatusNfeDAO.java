package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisStatusNfeVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.List;

public class SisStatusNfeDAO extends ServiceBuscaBancoDados {

    SisStatusNfeVO sisStatusNfeVO = null;
    List<SisStatusNfeVO> sisStatusNfeVOList = null;

    public SisStatusNfeVO getSisStatusNfeVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisStatusNfe WHERE id = ? ");
        return sisStatusNfeVO;
    }

    public SisStatusNfeVO getSisStatusNfeVO(String descricao) {
        addNewParametro(new Pair<>("String", descricao));
        getResultSet("SELECT * FROM sisStatusNfe WHERE descricao = ? ");
        return sisStatusNfeVO;
    }

    public List<SisStatusNfeVO> getSisStatusNfeVOList() {
        getResultSet("SELECT * FROM sisStatusNfe ");
        return sisStatusNfeVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                sisStatusNfeVO = new SisStatusNfeVO();
                sisStatusNfeVO.setId(rs.getInt("id"));
                sisStatusNfeVO.setDescricao(rs.getString("descricao"));
                if (sisStatusNfeVOList != null) sisStatusNfeVOList.add(sisStatusNfeVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }


}
