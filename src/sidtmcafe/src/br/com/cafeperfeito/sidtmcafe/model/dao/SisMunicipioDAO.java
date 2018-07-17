package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMunicipioVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisUfVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisMunicipioDAO extends BuscaBancoDados {

    ResultSet rs;
    SisMunicipioVO sisMunicipioVO;
    List<SisMunicipioVO> sisMunicipioVOList;
    boolean returnList = false;

    public SisMunicipioVO getSisMunicipioVO(int id, boolean addUfVO) {
        getResultSet(String.format("SELECT * FROM sisMunicipio WHERE id = %d ORDER BY isCapital DESC, descricao", id), false);
        if (addUfVO)
            addDetalheObjeto(sisMunicipioVO);
        return sisMunicipioVO;
    }

    public SisMunicipioVO getSisMunicipioVO(String municipio, boolean addUfVO) {
        String[] find = new String[2];
        int i = 0;
        for (String achado : municipio.split("-")) {
            find[i] = achado;
            i++;
        }
        getResultSet(String.format("SELECT * FROM sisMunicipio WHERE descricao = '%s' %sORDER BY isCapital DESC, descricao",
                find[0], find[1] != null ? String.format("AND sisUf_id = %d ", Integer.parseInt(find[1])) : ""), false);
        if (addUfVO)
            addDetalheObjeto(sisMunicipioVO);
        return sisMunicipioVO;
    }

    public List<SisMunicipioVO> getMunicipioVOList(int uf_id) {
        sisMunicipioVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisMunicipio WHERE sisUF_id = %d ORDER BY isCapital DESC, descricao", uf_id), true);
        return sisMunicipioVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisMunicipioVO = new SisMunicipioVO();
                sisMunicipioVO.setId(rs.getInt("id"));
                sisMunicipioVO.setDescricao(rs.getString("descricao"));
                sisMunicipioVO.setSisUF_id(rs.getInt("sisUF_id"));
                sisMunicipioVO.setIsCapital(rs.getInt("isCapital"));
                sisMunicipioVO.setIbge_id(rs.getInt("ibge_id"));
                sisMunicipioVO.setDdd(rs.getInt("ddd"));
                if (returnList) sisMunicipioVOList.add(sisMunicipioVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addDetalheObjeto(SisMunicipioVO municipio) {
        municipio.setUfVO(new SisUfDAO().getSisUfVO(municipio.getSisUF_id()));
    }
}
