package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUfVO;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisUfDAO extends BuscaBancoDados {

    SisUfVO sisUfVO = null;
    List<SisUfVO> sisUfVOList = null;

    public SisUfVO getSisUfVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisUf WHERE id = ? ");
        return sisUfVO;
    }

    public SisUfVO getSisUfVO(String sigla) {
        addNewParametro(new Pair<>("String", sigla));
        getResultSet("SELECT * FROM sisUf WHERE sigla = ? ");
        return sisUfVO;
    }

    public List<SisUfVO> getSisUfVOList() {
        sisUfVOList = new ArrayList<>();
        getResultSet("SELECT * FROM sisUf ");
        for (SisUfVO ufVO : sisUfVOList)
            addMunicipiosEmUF(ufVO);
        return sisUfVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY sigla ");
        try {
            while (rs.next()) {
                sisUfVO = new SisUfVO();
                sisUfVO.setId(rs.getInt("id"));
                sisUfVO.setDescricao(rs.getString("descricao"));
                sisUfVO.setSigla(rs.getString("sigla"));
                if (sisUfVOList != null) sisUfVOList.add(sisUfVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addMunicipiosEmUF(SisUfVO uf) {
        uf.setMunicipioVOList(new SisMunicipioDAO().getMunicipioVOList(uf.getId()));
    }
}
