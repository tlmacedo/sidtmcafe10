package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUfVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisUfDAO extends BuscaBancoDados {

    ResultSet rs;
    SisUfVO sisUfVO;
    List<SisUfVO> sisUfVOList;
    boolean returnList = false;

    public SisUfVO getSisUfVO(int id) {
        getResultSet(String.format("SELECT * FROM sisUf WHERE id = %d ORDER BY sigla", id), false);
        return sisUfVO;
    }

    public SisUfVO getSisUfVO(String sigla) {
        getResultSet(String.format("SELECT * FROM sisUf WHERE sigla = '%s' ORDER BY sigla", sigla), false);
        return sisUfVO;
    }

    public List<SisUfVO> getSisUfVOList() {
        sisUfVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisUf ORDER BY sigla"), true);
        for (SisUfVO ufVO : sisUfVOList)
            addMunicipiosEmUF(ufVO);
        return sisUfVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisUfVO = new SisUfVO();
                sisUfVO.setId(rs.getInt("id"));
                sisUfVO.setDescricao(rs.getString("descricao"));
                sisUfVO.setSigla(rs.getString("sigla"));
                if (returnList) sisUfVOList.add(sisUfVO);
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
