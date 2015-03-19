package utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Created by serhiist on 2/18/2015.
 */
public class FileUtils {
    public static final String GAMES_CONFIGURATION_XML_PATH = "library/XML/GameInfo/Games_wpl2.xml";
    public static final String PREPAIDCARD_CVS_PATH = WebDriverFactory.getPathToDownloadsFolder() + "prepaidcard.csv";

    public static String[] getPrePaidCardNumberAndPinFromExportedFile(){
        BufferedReader reader;
        String line;
        String spliter = ",";
        String[] result = new String[2];
        try {
            reader = new BufferedReader(new FileReader(PREPAIDCARD_CVS_PATH));
            reader.readLine();
            line = reader.readLine();
            result[0] = line.split(spliter)[1];
            result[1] = line.split(spliter)[2];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Document getXMLDocument(String path) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document dom = null;
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            dom = documentBuilder.parse(path);
        } catch (ParserConfigurationException e) {
            AbstractTest.failTest("XML parser configuration error: " + e.getMessage());
        } catch (SAXException e) {
            AbstractTest.failTest("XML not parsed: " + e.getMessage());
        } catch (IOException e) {
            AbstractTest.failTest("XML not parsed: " + e.getMessage());
        }
        AbstractTest.assertTrue(dom != null, "XML file: " + path + "is read");
        return dom;
    }

    public static Document getGamesXMLDocument(){
        return getXMLDocument(DataContainer.getDriverData().getCurrentUrl() + GAMES_CONFIGURATION_XML_PATH);
    }
}
