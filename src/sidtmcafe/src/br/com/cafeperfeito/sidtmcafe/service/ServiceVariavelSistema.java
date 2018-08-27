package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.model.dao.TabColaboradorDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabColaboradorVO;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.*;

public class ServiceVariavelSistema {
    public static String USUARIO_LOGADO_ID;
    public static String USUARIO_LOGADO_NOME;
    public static String USUARIO_LOGADO_APELIDO;
    public static LocalDateTime DATA_HORA;
    public static LocalDate USUARIO_LOGADO_DATA;
    public static LocalTime USUARIO_LOGADO_HORA;
    public static String DATA_HORA_STR;
    public static String USUARIO_LOGADO_DATA_STR;
    public static String USUARIO_LOGADO_HORA_STR;

    public static void newServiceVariavelSistema(TabColaboradorVO colaboradorVO) {
        if (colaboradorVO == null)
            colaboradorVO = new TabColaboradorDAO().getTabColaboradorVO(1, false);
        ServiceVariavelSistema.USUARIO_LOGADO_ID = String.valueOf(colaboradorVO.getId());
        ServiceVariavelSistema.USUARIO_LOGADO_NOME = colaboradorVO.getNome();
        ServiceVariavelSistema.USUARIO_LOGADO_APELIDO = colaboradorVO.getApelido();
        ServiceVariavelSistema.DATA_HORA = LocalDateTime.now();
        ServiceVariavelSistema.DATA_HORA_STR = ServiceVariavelSistema.DATA_HORA.format(DTF_DATAHORA);
        ServiceVariavelSistema.USUARIO_LOGADO_DATA = LocalDate.now();
        ServiceVariavelSistema.USUARIO_LOGADO_DATA_STR = ServiceVariavelSistema.USUARIO_LOGADO_DATA.format(DTF_DATA);
        ServiceVariavelSistema.USUARIO_LOGADO_HORA = LocalTime.now();
        ServiceVariavelSistema.USUARIO_LOGADO_HORA_STR = ServiceVariavelSistema.USUARIO_LOGADO_HORA.format(DTF_HORA);
    }
}

