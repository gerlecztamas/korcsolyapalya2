package Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class XmlReader {

    public static JSONArray read(String path) {

        JSONArray elemek = new JSONArray();

        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File(path);
            Document doc = db.parse(f);
            doc.normalize();
            ArrayList<String> tagNames = new ArrayList<>();

            NodeList nodeList = doc.getElementsByTagName("elem");
            for(int i = 0; i < nodeList.getLength(); i++){
                Element el = (Element) nodeList.item(i);
                JSONObject elem = new JSONObject();

                NodeList childList = el.getChildNodes();

                for(int j = 0; j < childList.getLength(); j++){
                    if(childList.item(j).hasChildNodes()){
                        Element tag = (Element) childList.item(j);
                        if(!tagNames.contains(tag.getTagName())) {
                            tagNames.add(tag.getTagName());
                        }
                    }
                }

                if(!tagNames.isEmpty()){
                    for (String tagName : tagNames) {
                        String tul = el.getElementsByTagName(tagName).item(0).getTextContent();
                        elem.put(tagName, tul);
                    }
                    elemek.put(elem);
                }
            }

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return elemek;
    }

}

