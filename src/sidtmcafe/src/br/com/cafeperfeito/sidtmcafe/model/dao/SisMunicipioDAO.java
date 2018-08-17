package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisMunicipioVO;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisMunicipioDAO extends BuscaBancoDados {

    SisMunicipioVO sisMunicipioVO = null;
    List<SisMunicipioVO> sisMunicipioVOList = null;

    public SisMunicipioVO getSisMunicipioVO(int id, boolean addUfVO) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisMunicipio WHERE id = ? ");
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
        addNewParametro(new Pair<>("String", find[0]));
        String comandoSql = "SELECT * FROM sisMunicipio WHERE descricao= ? ";
        if (find[1] != null) {
            addParametro(new Pair<>("int", find[1]));
            comandoSql += "AND sisUf_id = ? ";
        }
        getResultSet(comandoSql);
        if (addUfVO)
            addDetalheObjeto(sisMunicipioVO);
        return sisMunicipioVO;
    }

    public List<SisMunicipioVO> getMunicipioVOList(int uf_id) {
        sisMunicipioVOList = new ArrayList<>();
        addNewParametro(new Pair<>("int", String.valueOf(uf_id)));
        getResultSet("SELECT * FROM sisMunicipio WHERE sisUF_id = ? ");
        return sisMunicipioVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY isCapital DESC, descricao ");
        try {
            while (rs.next()) {
                sisMunicipioVO = new SisMunicipioVO();
                sisMunicipioVO.setId(rs.getInt("id"));
                sisMunicipioVO.setDescricao(rs.getString("descricao"));
                sisMunicipioVO.setSisUF_id(rs.getInt("sisUF_id"));
                sisMunicipioVO.setIsCapital(rs.getInt("isCapital"));
                sisMunicipioVO.setIbge_id(rs.getInt("ibge_id"));
                sisMunicipioVO.setDdd(rs.getInt("ddd"));
                if (sisMunicipioVOList != null) sisMunicipioVOList.add(sisMunicipioVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addDetalheObjeto(SisMunicipioVO municipio) {
        municipio.setUfVO(new SisUfDAO().getSisUfVO(municipio.getSisUF_id()));
    }
}
