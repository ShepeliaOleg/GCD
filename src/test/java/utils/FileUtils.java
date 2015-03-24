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
