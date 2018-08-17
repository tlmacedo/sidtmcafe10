package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.model.vo.TabEstoqueVO;

import java.sql.ResultSet;
import java.util.List;

public class TabEstoqueDAO extends BuscaBancoDados {

    TabEstoqueVO tabEstoqueVO = null;
    List<TabEstoqueVO> tabEstoqueVOList = null;


    void buscaTabEstoqueVO(int id, int produto_id) {
    }
}
