package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisSituacaoSistemaVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisSituacaoSistemaDAO extends ServiceBuscaBancoDados {

    SisSituacaoSistemaVO sisSituacaoSistemaVO = null;
    List<SisSituacaoSistemaVO> sisSituacaoSistemaVOList = null;

    public SisSituacaoSistemaVO getSisSituacaoSistemaVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisSituacaoSistema WHERE id = ? ");
        return sisSituacaoSistemaVO;
    }

    public List<SisSituacaoSistemaVO> getSisSituacaoSistemaVOList() {
        sisSituacaoSistemaVOList = new ArrayList<>();
        getResultSet("SELECT * FROM sisSituacaoSistema ");
        return sisSituacaoSistemaVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY descricao ");
        try {
            while (rs.next()) {
                sisSituacaoSistemaVO = new SisSituacaoSistemaVO();
                sisSituacaoSistemaVO.setId(rs.getInt("id"));
                sisSituacaoSistemaVO.setDescricao(rs.getString("descricao"));
                sisSituacaoSistemaVO.setClassificacao(rs.getInt("classificacao"));
                if (sisSituacaoSistemaVOList != null) sisSituacaoSistemaVOList.add(sisSituacaoSistemaVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
