package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUfVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisUFDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    SisUfVO sisUfVO;
    List<SisUfVO> sisUFVOList;

    public SisUfVO getSisUFVO(int id) {
        buscaSisUF(id, "");
        return sisUfVO;
    }

    public SisUfVO getSisUFVO(String sigla) {
        buscaSisUF(0, sigla);
        return sisUfVO;
    }

    public SisUfVO getSisUFVO_DetMunicipio(int id) {
        buscaSisUF(id, "");
        if (sisUfVO != null)
            addMunicipiosEmUF(sisUfVO);
        return sisUfVO;
    }

    public List<SisUfVO> getSisUFVOList() {
        buscaSisUF(0, "");
        return sisUFVOList;
    }

    public List<SisUfVO> getSisUFVOList_DetMunicipio() {
        buscaSisUF(0, "");
        if (sisUFVOList != null)
            for (SisUfVO ufvo : sisUFVOList)
                addMunicipiosEmUF(ufvo);
        return sisUFVOList;
    }

    void buscaSisUF(int id, String sigla) {
        comandoSql = "SELECT id, descricao, sigla " +
                "FROM sisUF ";
        if (id > 0) comandoSql += "WHERE id = '" + id + "' ";
        if (sigla != "") {
            if (!comandoSql.contains("WHERE")) {
                comandoSql += "WHERE ";
            } else {
                comandoSql += "AND ";
            }
            comandoSql += "sigla = '" + sigla + "' ";
        }
        comandoSql += "ORDER BY sigla ";

        if (id == 0 && sigla == "") sisUFVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisUfVO = new SisUfVO();
                sisUfVO.setId(rs.getInt("id"));
                sisUfVO.setDescricao(rs.getString("descricao"));
                sisUfVO.setSigla(rs.getString("sigla"));

                if (id == 0 && sigla == "") sisUFVOList.add(sisUfVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addMunicipiosEmUF(SisUfVO uf) {
        uf.setMunicipioVOList(new SisMunicipioDAO().getMunicipioVOList(uf.getId()));
    }

}
