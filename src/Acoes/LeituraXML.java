package Acoes;

import Telas.CredenciaisBanco;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class LeituraXML {
    
    public static File arquivoXML;

    public static void lerXML(File arquivo, CredenciaisBanco credenciais) throws FileNotFoundException {
        
       arquivoXML = arquivo;

       Document doc = fazParseArquivo(arquivo);

       String assinaturaInvoices = assinaturaSQL("invoices");

       String valuesInvoices = adicionaValoresInvoices(doc);
       
       credenciais.insercaoBanco(assinaturaInvoices, valuesInvoices);

       String assinaturaItems = assinaturaSQL("items");
       
       String valuesItems = adicionaValoresItems(doc);

       credenciais.insercaoBanco(assinaturaItems, valuesItems);
       
    }

    public static Document fazParseArquivo(File arquivo) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {

            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(arquivo);

            return doc;

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LeituraXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(LeituraXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeituraXML.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String assinaturaSQL(String nomeTabela) {

        StringBuilder montadorString = new StringBuilder();
        if (nomeTabela.equals("invoices")) {
            montadorString.append("INSERT INTO " + nomeTabela
                    + " (created_at,updated_at,number,model,serie,note_issue_date,note_entry_date,cfop,nfe_Key,cnpj_emit,cnpj_dest,amount,bc_icms_amount,icms_amount,"
                    + "bc_icms_st_amount,icms_st_amount,ecf_serial_number,document_type,ref_nfe,operation_nature,additional_data,"
                            + "arquivo_nfe)\n");

        } else if (nomeTabela.equals("items")) {
            montadorString.append("INSERT INTO " + nomeTabela + " (created_at, updated_at,product_code,quantity,"
                    + "amount,unit_measure,item_type,item_type_id,description,cfop,bc_icms_amount,brute_total_value,"
                    + "icms_aliquot,icms_amount,bc_icms_st_amount,st_aliquot,icms_st_amount,item_sequential_number,"
                    + "cest,barcode,framing_code,situation,tax_situation)\n");
        }

        montadorString.append("VALUES ");

        return montadorString.toString();
    }

    public static String adicionaValoresInvoices(Document doc) throws FileNotFoundException {

        StringBuilder valoresInvoices = new StringBuilder();
        valoresInvoices.append("(now(),now(),");

        NodeList listaNumero = doc.getElementsByTagName("nNF");
        valoresInvoices.append(listaNumero.item(0).getTextContent() + ",");

        NodeList listaDeModelo = doc.getElementsByTagName("mod");
        valoresInvoices.append("'" + listaDeModelo.item(0).getTextContent() + "',");

        NodeList listaDeSerie = doc.getElementsByTagName("serie");
        valoresInvoices.append("'" + listaDeSerie.item(0).getTextContent() + "',");
        
        NodeList listaDataHoraEmissao = doc.getElementsByTagName("dhEmi");
        valoresInvoices.append("'" + listaDataHoraEmissao.item(0).getTextContent()
                .replace("T", " ") + "',");
        
        NodeList listaDataHoraSaidaEntrada = doc.getElementsByTagName("dhSaiEnt");
        valoresInvoices.append("'" + listaDataHoraSaidaEntrada.item(0).getTextContent()
                .replace("T", " ")+ "',");

        NodeList listaDeCFOP = doc.getElementsByTagName("CFOP");
        valoresInvoices.append(listaDeCFOP.item(0).getTextContent() + ",");

        NodeList listaDeChaveAcesso = doc.getElementsByTagName("chNFe");
        valoresInvoices.append("'" + listaDeChaveAcesso.item(0).getTextContent() + "',");
        
        NodeList listaCNPJ = doc.getElementsByTagName("CNPJ");
        
// CNPJ do Emitente:        
        valoresInvoices.append("'" + listaCNPJ.item(0).getTextContent() + "',");
// CNPJ do Destinatário:        
        valoresInvoices.append("'" + listaCNPJ.item(1).getTextContent() + "',");
        

        NodeList listaValor = doc.getElementsByTagName("vNF");
        valoresInvoices.append(listaValor.item(0).getTextContent() + ",");

        NodeList listaIcmsTot = doc.getElementsByTagName("ICMSTot");
        NodeList listaIcmsTotFilhos = listaIcmsTot.item(0).getChildNodes();

        for (int i = 0; i < listaIcmsTotFilhos.getLength(); i++) {
            if (listaIcmsTotFilhos.item(i).getNodeName() == "vBC") {
                valoresInvoices.append(listaIcmsTotFilhos.item(i).getTextContent() + ",");

            } else if (listaIcmsTotFilhos.item(i).getNodeName() == "vICMS") {
                valoresInvoices.append(listaIcmsTotFilhos.item(i).getTextContent() + ",");

            } else if (listaIcmsTotFilhos.item(i).getNodeName() == "vBCST") {
                valoresInvoices.append(listaIcmsTotFilhos.item(i).getTextContent() + ",");

            } else if (listaIcmsTotFilhos.item(i).getNodeName() == "vST") {
                valoresInvoices.append(listaIcmsTotFilhos.item(i).getTextContent() + ",");
            }

        }

        // ecf_serial_number
        valoresInvoices.append("null,");
        //document_type
        valoresInvoices.append("'55 - NF-e',");

        // ref_nfe
        valoresInvoices.append("null,");

        NodeList listaNaturezaOperacao = doc.getElementsByTagName("natOp");
        valoresInvoices.append("'" + listaNaturezaOperacao.item(0).getTextContent() + "',");

        NodeList listaInfAdicionais = doc.getElementsByTagName("infCpl");
        valoresInvoices.append("'" + listaInfAdicionais.item(0).getTextContent() + "',");

        valoresInvoices.append(adicionaArquivo_NFe());
        
        return valoresInvoices.toString();
    }
    
    public static String adicionaArquivo_NFe() throws FileNotFoundException{
        Scanner leitor = new Scanner(arquivoXML);
        
        String xml = "'";
        while (leitor.hasNext()) {
            xml += leitor.nextLine();
            xml += "\n";
        }
        xml += "')";
        
        return xml;
    }

    public static String adicionaValoresItems(Document doc) {

        StringBuilder valoresItems = new StringBuilder();

        NodeList listaCodProd = doc.getElementsByTagName("cProd");

        NodeList listaQuanProd = doc.getElementsByTagName("qCom");

        NodeList listaValorUnProd = doc.getElementsByTagName("vUnCom");

        NodeList listaUnidadeMedida = doc.getElementsByTagName("uTrib");

        NodeList listaNCM = doc.getElementsByTagName("NCM");

        NodeList listaDescricao = doc.getElementsByTagName("xProd");

        NodeList listaCFOP = doc.getElementsByTagName("CFOP");

        NodeList listaBaseCalculoIcms = doc.getElementsByTagName("vBC");

        NodeList listaValorTotalBruto = doc.getElementsByTagName("vProd");

        NodeList listaAliquotaIcms = doc.getElementsByTagName("pICMS");

        NodeList listaValorIcms = doc.getElementsByTagName("vICMS");

        NodeList listaBaseCalculoIcmsST = doc.getElementsByTagName("vBCST");

        NodeList listaAliquotaIcmsST = doc.getElementsByTagName("pICMSST");

        NodeList listaValorIcmsST = doc.getElementsByTagName("vICMSST");

        NodeList listaCEST = doc.getElementsByTagName("CEST");

        NodeList listaCodigoBarra = doc.getElementsByTagName("cEANTrib");

        NodeList listaSituacaoTributaria = doc.getElementsByTagName("CST");

        for (int i = 0; i < listaCodProd.getLength(); i++) {

            if (i != 0) {
                valoresItems.replace(0, valoresItems.length(), "");
            }

            valoresItems.append("(now(),now(),");

            valoresItems.append("'" + listaCodProd.item(i).getTextContent() + "',");

            valoresItems.append(listaQuanProd.item(i).getTextContent() + ",");

            valoresItems.append(listaValorUnProd.item(i).getTextContent() + ",");

            valoresItems.append("'" + listaUnidadeMedida.item(i).getTextContent() + "',");

// item_type            
            valoresItems.append("'00',");

            valoresItems.append(listaNCM.item(i).getTextContent() + ",");

            valoresItems.append("'" + listaDescricao.item(i).getTextContent() + "',");

            valoresItems.append(listaCFOP.item(i).getTextContent() + ",");

            valoresItems.append(listaBaseCalculoIcms.item(i + (3 * i)).getTextContent() + ",");

            valoresItems.append(listaValorTotalBruto.item(i).getTextContent() + ",");

            valoresItems.append(listaAliquotaIcms.item(i).getTextContent() + ",");

            valoresItems.append(listaValorIcms.item(i).getTextContent() + ",");

            try {
                valoresItems.append(listaBaseCalculoIcmsST.item(i).getTextContent() + ",");
            } catch (NullPointerException e) {
                valoresItems.append("0,");
            }

            try {
                valoresItems.append(listaAliquotaIcmsST.item(i).getTextContent() + ",");
            } catch (NullPointerException e) {
                valoresItems.append("0,");
            }
            try {
                valoresItems.append(listaValorIcmsST.item(i).getTextContent() + ",");
            } catch (NullPointerException e) {
                valoresItems.append("0,");
            }

//Numero Item Sequencial            
            valoresItems.append((i + 1) + ",");

            try {
                valoresItems.append(listaCEST.item(i).getTextContent() + ",");
            } catch (NullPointerException e) {
                valoresItems.append("0,");
            }

            valoresItems.append("'" + listaCodigoBarra.item(i).getTextContent() + "',");

// Framing_code
            valoresItems.append("'null',");

//Situation        
            valoresItems.append("0,");

            valoresItems.append("'" + listaSituacaoTributaria.item(i + (i * 3)).getTextContent() + "')");

            if (i != (listaCodProd.getLength() - 1)) {
                valoresItems.append(",");
            }

        }

        return valoresItems.toString();
    }


}
