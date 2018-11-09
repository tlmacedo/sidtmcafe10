import br.com.cafeperfeito.sidtmcafe.model.dao.CargoDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.EmpresaDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.UsuarioDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.Cargo;
import br.com.cafeperfeito.sidtmcafe.model.vo.Empresa;
import br.com.cafeperfeito.sidtmcafe.model.vo.Usuario;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestJPA {

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) throws ParseException {
//        EntityManager em = new ConnectionFactory().getEntityManager();


        CargoDAO cargoDAO = new CargoDAO();
        inserirCargo(cargoDAO);
        cargoDAO.getAll(Cargo.class).stream().forEach(System.out::println);

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = new Empresa(true, 2, "08009246000136", false, "042171865",
                "T. L. MACEDO", "CAFE PERFEITO", true, true, true, 1,
                null, sdf.parse("28/04/206 01:00:00"), null, null,
                sdf.parse("28/04/2006 00:00:00"), "213-5 - EMPRESÁRIO (INDIVIDUAL)");
        empresa = empresaDAO.save(empresa);
        empresaDAO.getAll(Empresa.class).stream().forEach(System.out::println);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        inserirUsuario(usuarioDAO);
        usuarioDAO.getAll(Usuario.class).stream().forEach(System.out::println);

        empresa.setUsuarioCadastro(usuarioDAO.getById(Usuario.class, 1L));
        empresaDAO.save(empresa);
        empresaDAO.getAll(Empresa.class).stream().forEach(System.out::println);


//        UsuarioDAO usuarioDAO = new UsuarioDAO();
//
//        Cargo cargo = cargoDAO.getById(Cargo.class, 1L);
//        Usuario usuario = new Usuario("Thiago Macedo", "Thiago", "123456", sdf.parse("02/04/1981 02:00:00"), BigDecimal.valueOf(15000.0), true, cargo, "qg93SUDsMWXgcYjlYFtX4Q==");
//        usuario.setId(2);
//        usuarioDAO.save(usuario);
//        cargo = cargoDAO.getById(Cargo.class, 2L);
//        usuario = new Usuario("Carla Macedo", "Carla", "567890", sdf.parse("21/09/1978 02:00:00"), BigDecimal.valueOf(5000.0), true, cargo, "JZ4WgTIDJiviuZ7agiW2/A==");
//        usuarioDAO.save(usuario);
//
//
//        usuarioDAO.getAll(Usuario.class).stream().forEach(System.out::println);
//
//        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
//
//        colaboradorDAO.getAll(Colaborador.class).stream().forEach(System.out::println);
//

    }

    private static void inserirCargo(CargoDAO cargoDAO) {
        cargoDAO.save(new Cargo("PROGRAMADOR"));
        cargoDAO.save(new Cargo("PROPRIETARIO"));
        cargoDAO.save(new Cargo("GERENTE"));
        cargoDAO.save(new Cargo("VENDEDOR INTERNO"));
        cargoDAO.save(new Cargo("VENDEDOR EXTERNO"));
        cargoDAO.save(new Cargo("CONSULTOR"));
        cargoDAO.save(new Cargo("TECNICO"));
        cargoDAO.save(new Cargo("CONTADOR"));
        cargoDAO.save(new Cargo("MONTADOR"));
        cargoDAO.save(new Cargo("ENTREGADOR"));
        cargoDAO.save(new Cargo("ATENDENTE"));
        cargoDAO.save(new Cargo("TELEFONISTA"));
        cargoDAO.save(new Cargo("MOTORISTA"));
        cargoDAO.save(new Cargo("CONFERENTE"));
        cargoDAO.save(new Cargo("COMPRADOR"));
        cargoDAO.save(new Cargo("FISCAL"));
        cargoDAO.save(new Cargo("ASSISTENTE"));
    }

    private static void inserirUsuario(UsuarioDAO usuarioDAO) throws ParseException {
        CargoDAO cargoDAO = new CargoDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();
        usuarioDAO.save(new Usuario("Thiago Macedo", "thiago", "123456", sdf.parse("28/04/2006 00:00:00"), BigDecimal.valueOf(8000.0),
                true, cargoDAO.getById(Cargo.class, 1L),
                empresaDAO.getById(Empresa.class, 1L),
                "qg93SUDsMWXgcYjlYFtX4Q==", 1));
    }

    private static void inserirEmpresa(EmpresaDAO empresaDAO) throws ParseException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        empresaDAO.save(new Empresa(false, 2, "08009246000136", false, "042171865",
                "T. L. MACEDO", "CAFE PERFEITO", true, true, true, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2006-04-28 01:00:00"), usuarioDAO.getById(Usuario.class, 1L),
                sdf.parse("2018-07-20 16:04:13"), sdf.parse("2006-04-28 00:00:00"), "213-5 - EMPRESÁRIO (INDIVIDUAL)"));


        empresaDAO.save(new Empresa(false, 2, "01703285000190", false, "115298039114",
                "BRASIL ESPRESSO COMERCIO ATACADISTA LTDA.", "BRANCO PERES COMERCIO ATACADISTA LTDA", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-01 18:05:43"), null,
                null, sdf.parse("1997-02-13 00:00:00"), "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "05694248000113", false, "635485405115",
                "SUPREMO ARABICA COMERCIO IMPORTACAO E EXPORTACAO DE MAQUINAS LTDA", "***", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-02 14:18:44"), null,
                null, sdf.parse("2003-06-02 00:00:00"), "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "04561379000160", false, "041008057",
                "J J R COMERCIO DE PRODUTOS ALIMENTICIOS LTDA", "ARMAZEM SANTA CLARA", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-03 16:14:12"), null,
                null, sdf.parse("1966-08-08 00:00:00"), "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "18466212000124", false, "053402766",
                "MENDES E DAMASCENO LTDA", "D R DAMASCENO", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-04 16:15:45"), null,
                null, sdf.parse("2013-07-11 00:00:00"), "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "84509264000165", false, "041278321",
                "MAXPEL COMERCIAL LTDA", "***", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-05 18:35:51"), null,
                null, sdf.parse("1994-01-06 00:00:00"), "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "01487193000110", false, "041096282",
                "B S DISTRIBUICAO E REPRESENTACAO LTDA", "***", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-06 17:53:05", null, null, sdf.parse("1996-10-14 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "47427653000468", false, "041484452",
                "MAKRO ATACADISTA SOCIEDADE ANONIMA", "***", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-07 13:00:46", null, null, sdf.parse("2001-09-04 00:00:00", "205-4 - SOCIEDADE ANÔNIMA FECHADA)"));
        empresaDAO.save(new Empresa(false, 2, "04121789000190", false, "336826969110",
                "REDYAR - OTM TRANSPORTES LTDA", "REDYAR TRANSPORTES", false, false, true, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-08 16:04:15", null, null, sdf.parse("2000-10-09 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "06186733000220", false, "336942124118",
                "EXATA CARGO LTDA", "EXATA CARGO", false, false, true, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-09 14:22:18", null, null, sdf.parse("2006-06-14 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "60541240000125", false, "113551215114",
                "TECNOLOG TRANSPORTE RODO-AEREO E LOGISTICA LTDA.", "TECNOLOG EXPRESS CARGO", false, false, true, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-02-10 16:58:36", null, null, sdf.parse("1989-04-27 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "05518915000107", false, "041775260",
                "ALEMA RESTAURANTES LTDA", "ALEMA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-03-01 01:00:00", null, null, sdf.parse("1985-10-04 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "09812871000184", true, "",
                "CAFERETTO SERVICO DE ALIMENTACAO E COMERCIO LTDA", "CAFERETTO", true, false, false, 7,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-03-08 01:00:00", null, null, sdf.parse("2008-04-28 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "63702476000194", false, "042966829",
                "H L PUBLICACOES LTDA", "LIVRARIA AJURICABA", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-03-25 01:00:00", true, "2018-08-03 14:17:56", "1991-06-11 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "10701631000191", false, "042936829",
                "HR RESTAURANTE LTDA", "PICANHA MANIA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-03-28 01:00:00", null, null, sdf.parse("2009-03-19 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "04336889000133", false, "041143310",
                "AMANDA CABELEIREIROS EIRELI", "AMANDA BEAUTY CENTER", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-04-01 01:00:00", null, null, sdf.parse("1978-08-29 00:00:00", "230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))"));
        empresaDAO.save(new Empresa(false, 2, "05073167000104", false, "041526066",
                "NICE VEICULOS LTDA", "NICE VEICULOS", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-04-24 01:00:00", null, null, sdf.parse("2002-05-17 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "11371389000106", false, "042966400",
                "", "X-MANAUS RESTAURANTE LTDA", "X PICANHA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-04-27 01:00:00", null, null, sdf.parse("2009-12-04 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "06522265000306", false, "042904510",
                "PONTA NEGRA SOLUCOES, LOGISTICAS E TRANSPORTES LTDA", "PONTA NEGRA IMPORT", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-05-11 01:00:00", null, null, sdf.parse("2008-07-23 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "07819538000171", false, "042168295",
                "LUTRA COMERCIO DE ALIMENTOS LTDA", "X PICANHA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-05-26 01:00:00", null, null, sdf.parse("2006-01-25 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "09497477000107", false, "042249333" +
                "M M RESTAURANTE LTDA", "NAJUA RESTAURANTE", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-05-27 01:00:00", null, null, sdf.parse("2008-03-06 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "63740237000129", false, "041189663" +
                "A L PARENTE", "DELIVERY DO DEDE", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-05-27 01:00:00", true, "2018-08-10 15:14:23", "1991-10-04 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "10868702000145", false, "042938856" +
                "F L - COMERCIO DE ALIMENTOS LTDA", "PANIFICADORA PAO DA VILLA", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-06-21 01:00:00", true, "2018-08-03 14:17:31", "2009-05-21 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "10189959000170", false, "041168267" +
                "RIVER JUNGLE HOTEL LTDA", "HOTEL ARIAU", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-06-27 01:00:00", null, null, sdf.parse("1986-01-07 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "09449408000110", false, "042240646" +
                "SPEED A M EQUIPAMENTOS DE COMUNICACAO LTDA", "SPEED A. M. EMPRESARIAL LTDA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-07-12 01:00:00", null, null, sdf.parse("2008-03-07 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "84489269000173", false, "041253477" +
                "HABIPAR PARTICIPACOES LTDA.", "CAESAR BUSINESS MANAUS AMAZONAS", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-07-12 01:00:00", null, null, sdf.parse("1993-06-14 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "10818339000153", false, "042935229" +
                "VIEIRA E VIEIRA COMERCIO DE ARTIGOS ESPORTIVOS LTDA", "PONTO DOS ESPORTES", true, false, false, 7,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-08-17 01:00:00", null, null, sdf.parse("2009-05-13 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "02651424000141", false, "041395433" +
                "CALDAS BUFFET LTDA", "O LENHADOR", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2010-11-10 02:00:00", true, "2018-08-03 14:16:29", "1998-07-16 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "11303271000132", false, "042966760" +
                "CR LOJA DE CONVENIENCIA LTDA", "CR CONVENIENCIA", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-04-07 01:00:00", true, "2018-08-03 14:16:42", "2009-11-10 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "84496694000190", false, "041302567" +
                "IMA COMERCIO DE ALIMENTOS LTDA", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-04-13 01:00:00", null, null, sdf.parse("1993-10-22 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "84496694000432", false, "042940893" +
                "IMA COMERCIO DE ALIMENTOS LTDA", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-05-19 01:00:00", null, null, sdf.parse("2009-01-08 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "66542002003055", false, "042281687" +
                "BLUE TREE HOTELS & RESORTS DO BRASIL S/A.", "BLUE TREE PREMIUM MANAUS", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-05-19 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:16:06", "2010-06-29 00:00:00", "205-4 - SOCIEDADE ANÔNIMA FECHADA)"));
        empresaDAO.save(new Empresa(false, 2, "04920058000104", false, "240105525" +
                "PINHEIRO & CIA LTDA", "PADARIA TRIGO'S", true, false, false, 6,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-07-09 01:00:00", null, null, sdf.parse("2001-10-31 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "04082624000156", false, "000010197" +
                "IRMAOS GONCALVES COMERCIO E INDUSTRIA LTDA.", "SUPERMERCADO IRMAOS GONCALVES", true, false, false, 6,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-07-15 01:00:00", null, null, sdf.parse("1977-04-20 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "15775273000185", false, "041818911" +
                "CHURRASCARIA BUFALO LTDA", "CHURRASCARIA BUFALO", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-07-22 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:16:36", "1986-12-09 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "84496694000351", false, "044005377" +
                "IMA COMERCIO DE ALIMENTOS LTDA", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2011-07-23 01:00:00", null, null, sdf.parse("2005-01-04 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "00512663000195", false, "041017323" +
                "VIA MARCONI VEICULOS LTDA", "WAY", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-02-20 01:00:00", null, null, sdf.parse("1995-03-29 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "02584924002151", false, "053395786" +
                "ICH ADMINISTRACAO DE HOTEIS S.A.", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-04-10 01:00:00", null, null, sdf.parse("2011-04-18 00:00:00", "205-4 - SOCIEDADE ANÔNIMA FECHADA)"));
        empresaDAO.save(new Empresa(false, 2, "09137053000123", false, "042290384" +
                "C C DE OMENA MICHILES", "CAFE DO PONTO", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-04-12 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:16:17", "2007-10-18 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "10939609000346", false, "053209818" +
                "DIEGO BRINGEL AVELINO", "SUBWAY", true, false, false, 6,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-04-29 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:16:58", "2011-11-03 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "10939609000265", false, "04966221" +
                "DIEGO BRINGEL AVELINO", "SUBWAY", true, false, false, 6,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-04-29 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:17:14", "2009-11-05 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "10939609000184", false, "042942519" +
                "DIEGO BRINGEL AVELINO", "SUBWAY", true, false, false, 6,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-04-29 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:17:08", "2009-06-30 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "10964541000193", false, "042949157" +
                "MENEZES & MENEZES, SERVICOS DE ALIMENTACAO LTDA", "SABOR A MI BISTRO", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-10-10 01:00:00", null, null, sdf.parse("2009-07-15 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "67894360000317", true, "",
                "GRAO FRANCHISING E PARTICIPACOES LTDA", "MODELLI", true, false, false, 7,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-10-10 01:00:00", null, null, sdf.parse("1997-05-28 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "11875229000196", false, "042274303" +
                "MT COMERCIO DE ALIMENTOS LTDA", "YOGU MANIA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-11-14 02:00:00", null, null, sdf.parse("2010-04-29 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "60701190411589", true, "",
                "ITAU UNIBANCO S.A.", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-11-26 02:00:00", null, null, sdf.parse("2009-04-29 00:00:00", "205-4 - SOCIEDADE ANÔNIMA FECHADA)"));
        empresaDAO.save(new Empresa(false, 2, "05424423000152", false, "042154316" +
                "DE PASQUAL HOTEIS E TURISMO LTDA.", "COMFORT HOTEL", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-12-17 02:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:16:48", "2002-12-10 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "09256342000141", false, "042902363" +
                "RAL EMPREENDIMENTOS LTDA", "CACHACARIA DO DEDE & EMPORIO", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-12-17 02:00:00", null, null, sdf.parse("2007-12-12 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "16799056000198", false, "053299760" +
                "IGOR DA S.MENDONCA - LANCHONETE", "VIGOR CAFE", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-12-17 02:00:00", null, null, sdf.parse("2012-09-04 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "08975700000294", false, "042960606" +
                "VIFOUR COMERCIO DE CONFECCOES E ALIMENTOS LTDA", "YOGO MANIA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2013-12-18 02:00:00", null, null, sdf.parse("2007-08-08 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "02844344000102", true, "",
                "FUNDACAO AMAZONICA DE AMPARO A PESQUISA E DESENVOLVIMENTO TECNOLOGICO DESEMBARGA", "FPF TECH", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-02-03 02:00:00", null, null, sdf.parse("1998-11-04 00:00:00", "306-9 - FUNDAÇÃO PRIVADA)"));
        empresaDAO.save(new Empresa(false, 2, "84467307000197", false, "041233786" +
                "DINAMICA DISTRIBUIDORA LTDA", "***", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-02-18 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:17:21", "1992-12-22 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "42289025000440", false, "042962943" +
                "INTERCONTINENTAL HOTELS GROUP DO BRASIL LTDA.", "HOTEL HOLIDAY INN MANAUS", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-05-06 01:00:00", null, null, sdf.parse("2009-08-06 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "14629567000136", false, "053247264" +
                "QUALITE ALIMENTOS PREPARADOS LTDA EPP", "QUALITE ALIMENTOS", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-06-04 01:00:00", null, null, sdf.parse("2011-11-17 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "20069719000196", false, "053522249" +
                "FUTURA COMERCIO DE ALIMENTOS E BEBIDAS LTDA", "LA FINESTRA", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-06-07 01:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:17:38", "2014-04-11 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "23033327000408", false, "053522249" +
                "HTS SERVICOS DE HOTELARIA E TURISMO LTDA", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-06-10 01:00:00", null, null, sdf.parse("2004-10-20 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "60701190134305", true, "",
                "ITAU UNIBANCO S.A.", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-06-12 01:00:00", null, null, sdf.parse("1991-12-09 00:00:00", "205-4 - SOCIEDADE ANÔNIMA FECHADA)"));
        empresaDAO.save(new Empresa(false, 2, "23033327000327", false, "053522230" +
                "HTS SERVICOS DE HOTELARIA E TURISMO LTDA", "HTS - ED.ADRIANOPOLIS - FILIAL I", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-06-18 01:00:00", null, null, sdf.parse("2001-05-03 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "19380220000116", false, "053495357" +
                "IVANEIDE CARACAS BEZERRA", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2014-07-15 01:00:00", null, null, sdf.parse("2013-12-09 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "10865719000149", false, "042969271" +
                "B. THOME ARAUJO REFEICOES LTDA-ME", "B.T.A REFEICOES", true, false, false, 2,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-02-09 02:00:00", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-08-03 14:15:55", "2009-05-12 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "10013843000186", false, "042902509" +
                "AMANDA BEAUTY CENTER EIRELI", "AMANDA BEAUTY CENTER", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-03-01 01:00:00", null, null, sdf.parse("2008-06-30 00:00:00", "230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))"));
        empresaDAO.save(new Empresa(false, 2, "60701190159200", true, "",
                "ITAU UNIBANCO S.A.", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-04-22 01:00:00", null, null, sdf.parse("1996-10-30 00:00:00", "205-4 - SOCIEDADE ANÔNIMA FECHADA)"));
        empresaDAO.save(new Empresa(false, 2, "08941325000180", false, "407521997117" +
                "SL CAFES DO BRASIL PROFESSIONAL LTDA", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-07-02 01:00:00", null, null, sdf.parse("2007-06-11 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "04336889000214", false, "042960126" +
                "AMANDA CABELEIREIROS LTDA", "AMANDA BEAUTY CENTER", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-07-28 20:55:43", null, null, sdf.parse("2009-10-23 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "16684606000123", false, "053280520" +
                "RODRIGUES E SMITH COMERCIO DE ALIMENTOS LTDA", "CASA DO PAO DE QUEIJO AMAZONAS SHOPPING", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-08-12 11:50:50", null, null, sdf.parse("2012-08-13 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "60701190057637", true, "",
                "ITAU UNIBANCO S.A.", "***", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-08-14 12:13:43", null, null, sdf.parse("1975-01-08 00:00:00", "205-4 - SOCIEDADE ANÔNIMA FECHADA)"));
        empresaDAO.save(new Empresa(false, 2, "07414941000110", false, "042172342" +
                "PAOZINHO COMERCIO LTDA", "PAOZINHO", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2015-11-12 12:15:35", null, null, sdf.parse("2005-06-01 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "19783732000123", false, "053498771" +
                "NUTRI CASAS DE CHA LTDA.", "CASA DO PAO DE QUEIJO", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2016-01-07 18:04:22", null, null, sdf.parse("2014-02-21 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "08038545001413", false, "053727290" +
                "NATUREZA COMERCIO DE DESCARTAVEIS LTDA", "QUEIROZ", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2016-04-25 15:18:03", null, null, sdf.parse("2015-10-22 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "06981833000248", false, "63585819611" +
                "TORREFACAO NISHIDA SAN LTDA", "***", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-03-14 11:00:07", usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-09-27 10:51:10", "2015-10-15 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "04802134000268", true, "",
                "INDT - INSTITUTO DE DESENVOLVIMENTO TECNOLOGICO", "INDT", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-06-01 15:59:20", null, null, sdf.parse("2002-11-01 00:00:00", "399-9 - ASSOCIAÇÃO PRIVADA)"));
        empresaDAO.save(new Empresa(false, 2, "29260745000171", false, "053984722" +
                "PANIFICADORA ALDEIA DOS PAES EIRELI", "PANIFICADORA ALDEIA DOS PAES", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-06-08 16:01:46", null, null, sdf.parse("2017-12-13 00:00:00", "230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))"));
        empresaDAO.save(new Empresa(false, 2, "04012611000100", false, "041452097" +
                "RONALDO MAIA BARBOSA EIRELI", "DISTRIBUIDORA LONDRINA", true, false, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-07-19 10:13:47", null, null, sdf.parse("2000-08-25 00:00:00", "230-5 - EMPRESA INDIVIDUAL DE RESPONSABILIDADE LIMITADA (DE NATUREZA EMPRESÁRIA))"));
        empresaDAO.save(new Empresa(false, 2, "12564461000176", false, "042295513" +
                "JANAINA F B MATOS", "SHALOM FESTAS", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-10-03 15:26:25", null, null, sdf.parse("2010-09-22 00:00:00", "213-5 - EMPRESÁRIO (INDIVIDUAL))"));
        empresaDAO.save(new Empresa(false, 2, "02392576000177", false, "041395808" +
                "BANDEIRA DE MELO E FILHOS LTDA", "ATACADAO TROPICAL", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-10-03 15:28:41", null, null, sdf.parse("1998-02-04 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));
        empresaDAO.save(new Empresa(false, 2, "00835261000393", false, "053583574" +
                "A.M. DA S RODRIGUES & CIA LTDA", "SUPERMERCADO RODRIGUES", false, true, false, 1,
                usuarioDAO.getById(Usuario.class, 1L), sdf.parse("2018-10-16 14:25:10", null, null, sdf.parse("2013-12-16 00:00:00", "206-2 - SOCIEDADE EMPRESÁRIA LIMITADA)"));

    }

}
