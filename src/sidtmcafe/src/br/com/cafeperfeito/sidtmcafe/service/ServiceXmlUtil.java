package br.com.cafeperfeito.sidtmcafe.service;

import br.inf.portalfiscal.cte.PL_CTE_300_NT2018_002.cte_v300.TCTe;
import br.inf.portalfiscal.cte.PL_CTE_300_NT2018_002.procCTe_v300.CteProc;
import br.inf.portalfiscal.nfe_400.xsdPL_009.consStatServ_v400.TConsStatServ;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class ServiceXmlUtil {

    private static final String STATUS = "TConsStatServ";
    private static final String SITUACAO_NFE = "TConsSitNFe";
    private static final String ENVIO_NFE = "TEnviNFe";
    private static final String DIST_DFE = "DistDFeInt";
    private static final String INUTILIZACAO = "TInutNFe";
    private static final String NFEPROC = "TNfeProc";
    private static final String EVENTO = "TEnvEvento";
    private static final String TPROCEVENTO = "TProcEvento";
    private static final String TCONSRECINFE = "TConsReciNFe";
    private static final String TConsCad = "TConsCad";
    private static final String TPROCINUT = "TProcInutNFe";
    private static final String RETORNO_ENVIO = "TRetEnviNFe";
    private static final String SITUACAO_NFE_RET = "TRetConsSitNFe";

    private static final String CTE = "TCTe";
    private static final String CTEPROC = "CteProc";

    public static String leXml(String arquivo) {
        StringBuilder xml = new StringBuilder();
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));
            String linha;

            while ((linha = in.readLine()) != null) {
                System.out.println(linha);
                xml.append(linha);
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Ler Xml: " + e.getMessage());
        }
        return xml.toString();
    }

    public static <T> T xmlToObject(String xml, Class<T> classe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), classe).getValue();
    }

    @SuppressWarnings("Duplicates")
    public static <T> String objectToXml(Object obj) throws JAXBException {

        JAXBContext context = null;
        JAXBElement<?> element = null;

        switch (obj.getClass().getSimpleName()) {

            case STATUS:
                context = JAXBContext.newInstance(TConsStatServ.class);
                element = new br.inf.portalfiscal.nfe_400.xsdPL_009.consStatServ_v400.ObjectFactory().createConsStatServ((TConsStatServ) obj);
                break;


            case CTEPROC:
                context = JAXBContext.newInstance(CteProc.class);
                element = new br.inf.portalfiscal.cte.PL_CTE_300_NT2018_002.cte_v300.ObjectFactory().createCTe((TCTe) obj);
                break;


//            case ENVIO_NFE:
//                context = JAXBContext.newInstance(TEnviNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.enviNFe.ObjectFactory().createEnviNFe((TEnviNFe) obj);
//                break;
//
//            case RETORNO_ENVIO:
//                context = JAXBContext.newInstance(TRetEnviNFe.class);
//                element = XsdUtil.enviNfe.createTRetEnviNFe((TRetEnviNFe) obj);
//                break;
//
//            case SITUACAO_NFE:
//                context = JAXBContext.newInstance(TConsSitNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.consSitNFe.ObjectFactory().createConsSitNFe((TConsSitNFe) obj);
//                break;
//
//            case DIST_DFE:
//                context = JAXBContext.newInstance(DistDFeInt.class);
//                element = new br.inf.portalfiscal.nfe.schema.distdfeint.ObjectFactory().createDistDFeInt((DistDFeInt) obj);
//                break;
//
//            case TCONSRECINFE:
//                context = JAXBContext.newInstance(TConsReciNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.consReciNFe.ObjectFactory().createConsReciNFe((TConsReciNFe) obj);
//                break;
//
//            case TConsCad:
//                context = JAXBContext.newInstance(TConsCad.class);
//                element = new br.inf.portalfiscal.nfe.schema.consCad.ObjectFactory().createConsCad((TConsCad) obj);
//                break;
//
//            case INUTILIZACAO:
//                context = JAXBContext.newInstance(TInutNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.inutNFe.ObjectFactory().createInutNFe((TInutNFe) obj);
//                break;
//
//            case SITUACAO_NFE_RET:
//                context = JAXBContext.newInstance(TRetConsSitNFe.class);
//                element = new br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.ObjectFactory().createRetConsSitNFe((TRetConsSitNFe) obj);
//                break;
//
//            case TPROCEVENTO:
//                switch (obj.getClass().getName()) {
//                    case TPROCCANCELAR:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TProcEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envEventoCancNFe.ObjectFactory().createTProcEvento((br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TProcEvento) obj);
//                        break;
//                    case TPROCCCE:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envcce.TProcEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envcce.ObjectFactory().createTProcEvento((br.inf.portalfiscal.nfe.schema.envcce.TProcEvento) obj);
//                        break;
//                    case TPROCEPEC:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envEpec.TProcEvento.class);
//                        element = XsdUtil.epec.createTProcEvento((br.inf.portalfiscal.nfe.schema.envEpec.TProcEvento) obj);
//                        break;
//                }
//
//                break;
//
//            case NFEPROC:
//                context = JAXBContext.newInstance(TNfeProc.class);
//                element = XsdUtil.enviNfe.createTNfeProc((TNfeProc) obj);
//                break;
//
//            case TPROCINUT:
//                context = JAXBContext.newInstance(TProcInutNFe.class);
//                element = XsdUtil.inutNfe.createTProcInutNFe((TProcInutNFe) obj);
//                break;
//
//            case EVENTO:
//                switch (obj.getClass().getName()) {
//                    case CANCELAR:
//                        context = JAXBContext.newInstance(TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envEventoCancNFe.ObjectFactory().createEnvEvento((TEnvEvento) obj);
//                        break;
//                    case CCE:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envcce.ObjectFactory().createEnvEvento((br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento) obj);
//                        break;
//                    case EPEC:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envEpec.TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envEpec.ObjectFactory().createEnvEvento((br.inf.portalfiscal.nfe.schema.envEpec.TEnvEvento) obj);
//                        break;
//                    case MANIFESTAR:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento.class);
//                        element = new br.inf.portalfiscal.nfe.schema.envConfRecebto.ObjectFactory().createEnvEvento((br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento) obj);
//                        break;
//                }
//                break;
//
//            case TProtNFe:
//                switch (obj.getClass().getName()) {
//                    case TProtEnvi:
//                        context = JAXBContext.newInstance(TProtNFe.class);
//                        element = XsdUtil.enviNfe.createTProtNFe((br.inf.portalfiscal.nfe.schema_4.enviNFe.TProtNFe) obj);
//                        break;
//                    case TProtCons:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.TProtNFe.class);
//                        element = XsdUtil.retConsSitNfe.createTProtNFe((br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.TProtNFe) obj);
//                        break;
//                    case TProtReci:
//                        context = JAXBContext.newInstance(br.inf.portalfiscal.nfe.schema_4.retConsReciNFe.TProtNFe.class);
//                        element = XsdUtil.retConsReciNfe.createTProtNFe((br.inf.portalfiscal.nfe.schema_4.retConsReciNFe.TProtNFe) obj);
//                        break;
//                }
//                break;
//
//            default:
//                throw new NfeException("Objeto não mapeado no XmlUtil:" + obj.getClass().getSimpleName());
        }
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty("jaxb.encoding", "Unicode");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        StringWriter sw = new StringWriter();

//        if (obj.getClass().getSimpleName().equals(ENVIO_NFE) || obj.getClass().getSimpleName().equals(NFEPROC)) {
//            CDATAContentHandler cdataHandler = new CDATAContentHandler(sw, "utf-8");
//            marshaller.marshal(element, cdataHandler);
//        } else {
        marshaller.marshal(element, sw);
//        }
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(sw.toString());

//        if ((obj.getClass().getSimpleName().equals(TPROCEVENTO))) {
//            return replacesNfe(xml.toString().replaceAll("procEvento", "procEventoNFe"));
//        } else {
        return replacesNfe(xml.toString());
//        }

    }

    @SuppressWarnings("Duplicates")
    private static String replacesNfe(String xml) {
        xml = xml.replaceAll("ns2:", "");
        xml = xml.replaceAll("<!\\[CDATA\\[<!\\[CDATA\\[", "<!\\[CDATA\\[");
        xml = xml.replaceAll("\\]\\]>\\]\\]>", "\\]\\]>");
        xml = xml.replaceAll("ns3:", "");
        xml = xml.replaceAll("&lt;", "<");
        xml = xml.replaceAll("&amp;", "&");
        xml = xml.replaceAll("&gt;", ">");
        xml = xml.replaceAll("<Signature>", "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">");
        xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        xml = xml.replaceAll(" xmlns=\"\" xmlns:ns3=\"http://www.portalfiscal.inf.br/nfe\"", "");
        return xml;
    }

}
