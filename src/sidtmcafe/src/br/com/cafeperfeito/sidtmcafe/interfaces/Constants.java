package br.com.cafeperfeito.sidtmcafe.interfaces;

import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;
import javafx.scene.input.*;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public interface Constants {
    String COPYRIGHT = "Café Perfeito " + "\u00a9 " + LocalDate.now().getYear();
    String LOJA_ID = "1";
    int DDD_SISTEMA = 92;

    String REGEX_CNPJ_CPF = "(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})|(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})";
    String REGEX_CPF = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
    String REGEX_EMAIL = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}";
    String REGEX_HOME_PAGE = "[A-Z0-9._%+-]+[A-Z0-9.-]+\\.[A-Z]{2,6}";
    String REGEX_DDD = "(.(\\d{2}.))";
    String REGEX_TELEFONE_DDD = "(.(\\d{2}.))?\\s9?\\d{4}-\\d{4}";
    String REGEX_TELEFONE = "\\d{4}-\\d{4}";
    String REGEX_PONTUACAO = "[!\"$%&'()*+,-./:;_`{|}]";
    Pair<String, String> REGEX_FS_CNPJ = new Pair<String, String>("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})$", "$1.$2.$3/$4-$5");
    Pair<String, String> REGEX_FS_CPF = new Pair<String, String>("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    Pair<String, String> REGEX_FS_CEP = new Pair<String, String>("(\\d{2})(\\d{3})(\\d{3})", "$1.$2-$3");
    Pair<String, String> REGEX_FS_TELEFONE = new Pair<String, String>("(\\d{1})?(\\d{4})(\\d{4})", "$1 $2-$3");
    Pair<String, String> REGEX_FS_NCM = new Pair<String, String>("(\\d{4})(\\d{0,2})(\\d{0,2})", "$1.$2.$3");
    Pair<String, String> REGEX_FS_CEST = new Pair<String, String>("(\\d{2})(\\d{3})(\\d{2})", "$1.$2.$3");
    Pair<String, String> REGEX_FS_NFE_CHAVE = new Pair<String, String>("(\\d{4}+)(\\d{4}?)", "$1 $2 ");
    Pair<String, String> REGEX_FS_NFE_NUMERO = new Pair<String, String>("(\\d{3})?(\\d{3})", "$1.$2");
    Pair<String, String> REGEX_FS_NFE_DOC_ORIGEM = new Pair<String, String>("(\\d{11})(\\d{1})", "$1-$2");

    String CARACTER_DIGITO = "#";
    String CARACTER_UPPER = "U";
    String CARACTER_LOWER = "L";
    String CARACTER_DIGITO_TEXT = "A";
    String CARACTER_INTERROGACAO = "?";
    String CARACTER_ASTERISCO = "*";

    /*
     * Constantes para conexão com banco de dados MySql
     */
    String BD_DRIVER = "com.mysql.cj.jdbc.Driver";
    String BD_DATABASE = "cafeperfeito";
    String BD_HOST = "127.0.0.1";
    String BD_DRIVER_CONN = "jdbc:mysql://";
    String BD_PORTA = ":3306/";
    String BD_USER = "root";
    String BD_PASS = "Tlm487901";
    String BD_URL = BD_DRIVER_CONN + BD_HOST + BD_PORTA + BD_DATABASE + "?useSSL=false";//"?useTimezone=true&serverTimezone=UTC&useSSL=true";
    String BD_DATABASE_STB = BD_HOST + BD_PORTA + BD_DATABASE;

    /*
     *Constants para estilos do sistema
     *///macOS⁩ ▸ ⁨Usuários⁩ ▸ ⁨thiagomacedo⁩ ▸ ⁨Imagens⁩
    String PATH_IMAGE_DOWNLOAD = System.getProperty("user.home") + "/Pictures/";
    //String PATH_IMAGE_DOWNLOAD = "/volumes/macOS/Usuários/thiagomacedo/Imagens/";
    String STYLE_SHEETS = "/style/min/sidtm.min.css";
    String PATH_IMAGE = "/image/";
    String PATH_FAVICON = PATH_IMAGE + "favicon/";
    String PATH_PAINEL = PATH_IMAGE + "painel/";
    String PATH_IC_MENU = PATH_IMAGE + "ico/menu/";
    String PATH_IMG_SPLASH = PATH_IMAGE + "ico/splash/";

    String PATH_IC_BLACK = "ico/black/";
    String PATH_IC_ORANGE = "ico/orange/";
    String PATH_IC_WHITE = "ico/white/";
    String PATH_ICONE = PATH_IMAGE + PATH_IC_ORANGE;

    String[] IMAGE_SPLASH = {
            PATH_IMG_SPLASH + "img_splash_coffe_0.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_1.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_2.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_3.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_4.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_5.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_6.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_7.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_8.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_9.gif",
            PATH_IMG_SPLASH + "img_splash_coffe_10.gif"};
    String IC_CAFE_PERFEITO_240DP = PATH_IMAGE + "ico/ic_cafe_perfeito_240dp.png";
    String PATH_TOKEN_LIB = "/certificado";
    String EXTENSAO_ARQUIVO_TOKEN = "cfg";

    /*
     *FXML do sistema
     */
    String PATH_FXML = "/fxml/";
    String FXML_LOGIN = PATH_FXML + "FxmlLogin.fxml";
    String FXML_LOGIN_TITLE = "Login";
    String FXML_LOGIN_ICON = PATH_ICONE + "ic_cadeado_senha_24dp.png";

    String FXML_PRINCIPAL = PATH_FXML + "FxmlPrincipal.fxml";
    String FXML_PRINCIPAL_TITLE = "Café Perfeito";

    String FXML_PRINCIPAL_ICON_ATIVO = PATH_ICONE + "ic_principal_ativo_24dp.png";
    String FXML_PRINCIPAL_ICON_DESATIVO = PATH_ICONE + "ic_principal_desativo_24dp.png";

    String FXML_CADASTRO_EMPRESA = PATH_FXML + "FxmlCadastroEmpresa.fxml";
//    String FXML_CADASTRO_EMPRESA = PATH_FXML + "teste.fxml";

    String FXML_CADASTRO_PRODUTO = PATH_FXML + "FxmlCadastroProduto.fxml";


    /*
     * Constantes para data e horas
     */
    Locale LOCALE_MY = new Locale("pt", "BR");
    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    DecimalFormat PESO_FORMAT = new DecimalFormat("0.000");
    String LOCAL_TIME_ZONE = "America/Manaus";
    ZoneId MY_ZONE_TIME = ZoneId.of(LOCAL_TIME_ZONE);
    LocalDateTime DATAHORA_LOCAL = LocalDateTime.now().atZone(MY_ZONE_TIME).toLocalDateTime();
    DataFormat DT_DATA = new DataFormat("dd/MM/yyyy");
    DateTimeFormatter DTF_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter DTF_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter DTF_DATAHORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    DateTimeFormatter DTF_DATAHORAFUSO = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ssXXX");
    DateTimeFormatter DTF_MYSQL_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter DTF_MYSQL_DATAHORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter DTF_MYSQL_DATAHORAFUSO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    KeyCombination CODE_KEY_CTRL_ALT_B = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);
    KeyCombination CHAR_KEY_CTRL_ALT_B = new KeyCharacterCombination("b".toLowerCase(), KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);
    KeyCombination CODE_KEY_SHIFT_CTRL_POSITIVO = new KeyCodeCombination(KeyCode.PLUS, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);
    KeyCombination CHAR_KEY_SHIFT_CTRL_POSITIVO = new KeyCharacterCombination("+", KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);
    KeyCombination CODE_KEY_SHIFT_CTRL_NEGATIVO = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);
    KeyCombination CHAR_KEY_SHIFT_CTRL_NEGATIVO = new KeyCharacterCombination("-", KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);



    /*
     *
     * WebServices
     *
     * */

    /*
     * cosmos */
    String WS_COSMOS_URL = "https://api.cosmos.bluesoft.com.br";
    String WS_COSMOS_SER_NCM = "/ncms/";
    String WS_COSMOS_SER_GTINS = "/gtins/";
    String WS_COSMOS_SER_GPCS = "/gpcs/";
    String WS_COSMOS_TOKEN = "o65EDRPgFu7mFNuv5vj5Aw";

    /*
     * receitaws */
    String WS_RECEITAWS_URL = "https://www.receitaws.com.br/v1/cnpj/";
    String WS_RECEITAWS_TOKEN = "1953100c818519b43b895394c25b0fa38525e2800587a8b140a42e6baff7a8af";

    /*
     * webmania */
    String WS_WEBMANIA_URL = "https://webmaniabr.com/api/1/cep/";
    String WS_WEBMANIA_APP_KEY = "GOxHMxSXNbX99szfTE7A6mMDmb26P1Ch";
    String WS_WEBMANIA_APP_SECRET = "kMx5QczId1GqVLbpZ52qgEgfRhiKWFPZfa39IZfp6NZhFmTq";

    /*
     * postmon */
    String WS_POSTMON_URL = "http://api.postmon.com.br/v1/cep/";

    /*
     * portabilidade celular */
    String WS_PORTABILIDADE_CELULAR_USER = "user=tlmacedo";
    String WS_PORTABILIDADE_CELULAR_PASS = "pass=Tlm487901";
    String WS_PORTABILIDADE_CELULAR_URL = String.format("http://consultas.portabilidadecelular.com/painel/consulta_numero.php?%s&%s&search_number=",
            WS_PORTABILIDADE_CELULAR_USER, WS_PORTABILIDADE_CELULAR_PASS);


}
