package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.model.vo.TabEstoqueVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;

import java.util.List;

public class TabEstoqueDAO extends ServiceBuscaBancoDados {

    TabEstoqueVO tabEstoqueVO = null;
    List<TabEstoqueVO> tabEstoqueVOList = null;


    void buscaTabEstoqueVO(int id, int produto_id) {
    }
}
