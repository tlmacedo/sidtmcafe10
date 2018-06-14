package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUFVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisUFDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    SisUFVO sisUFVO;
    List<SisUFVO> sisUFVOList;

    public SisUFVO getSisUFVO(int id) {
        buscaSisUFVO(id, "");
        return sisUFVO;
    }

    public SisUFVO getSisUFVO(String sigla) {
        buscaSisUFVO(0, sigla);
        return sisUFVO;
    }

    public SisUFVO getSisUFVO_DetMunicipio(int id) {
        buscaSisUFVO(id, "");
        if (sisUFVO != null)
            addMunicipiosEmUF(sisUFVO);
        return sisUFVO;
    }

    public List<SisUFVO> getSisUFVOList() {
        buscaSisUFVO(0, "");
        return sisUFVOList;
    }

    public List<SisUFVO> getSisUFVOList_DetMunicipio() {
        buscaSisUFVO(0, "");
        if (sisUFVOList != null)
            for (SisUFVO ufvo : sisUFVOList)
                addMunicipiosEmUF(ufvo);
        return sisUFVOList;
    }

    void buscaSisUFVO(int id, String sigla) {
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
                sisUFVO = new SisUFVO();
                sisUFVO.setId(rs.getInt("id"));
                sisUFVO.setDescricao(rs.getString("descricao"));
                sisUFVO.setSigla(rs.getString("sigla"));

                if (id == 0 && sigla == "") sisUFVOList.add(sisUFVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addMunicipiosEmUF(SisUFVO uf) {
        uf.setMunicipioVOList(new SisMunicipioDAO().getMunicipioVOList(uf.getId()));
    }

}
