package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.model.vo.TabEstoqueVO;

import java.sql.ResultSet;
import java.util.List;

public class TabEstoqueDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    TabEstoqueVO tabEstoqueVO;
    List<TabEstoqueVO> tabEstoqueVOList;


    void buscaTabEstoqueVO(int id, int produto_id) {
    }
}
