package utils;

import org.apache.xerces.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by serhiist on 2/18/2015.
 */
public class XMLUtils {
    private static final String GAMES_TAG = "game";

    public static ArrayList<String> getNodeValuesByName(NodeList nodeList, String nodeName){
        ArrayList<String> nodeValues = new ArrayList<>();
        NodeList listOfValues = ((DeferredElementImpl) nodeList).getElementsByTagName(nodeName);
        for (int i = 0; i < listOfValues.getLength(); i++){
            String nodeValue = listOfValues.item(i).getFirstChild().getNodeValue();
            if (nodeValue != null)
                nodeValues.add(nodeValue);
        }
        return nodeValues;
    }

    public static NodeList getNodeListByTag(Document dom, String tag){
        return dom.getDocumentElement().getElementsByTagName(tag);
    }

    public static NodeList getGamesNodeList(Document dom){
        return getNodeListByTag(dom, GAMES_TAG);
    }
}
