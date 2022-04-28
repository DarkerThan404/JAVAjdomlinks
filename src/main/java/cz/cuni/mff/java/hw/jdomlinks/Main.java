package cz.cuni.mff.java.hw.jdomlinks;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] argv) {

        try(InputStream in = new BufferedInputStream(new BufferedInputStream(System.in))){
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build( in);
            Element rootNode = doc.getRootElement();
            var rootNodeDescendants = rootNode.getDescendants();
            String currentSectionTitle = "";
            List<SectionTuple> orderedList = new ArrayList<>();
            Map<String, String> IDdictionary = new HashMap<>();
            for(Content desc : rootNodeDescendants){
                if(desc instanceof Element){
                    Element element = (Element) desc;
                    if(element.getName().equals("section")){
                        currentSectionTitle = getFullTitle(element.getChildren("title"));
                    }

                    if(element.getName().equals("link")){
                        var id = element.getAttribute("linkend").getValue();
                        var linkText = getFullLinkText(element);
                        orderedList.add(new SectionTuple(id,currentSectionTitle,linkText));
                    }

                    var id = element.getAttribute("id");
                    if(id != null){
                        IDdictionary.put(id.getValue(),currentSectionTitle);
                    }

                }
            }

            String currentSection = "";
            for(var Tuple: orderedList){
                if(!currentSection.equals(Tuple.InSection)){
                    currentSection = Tuple.InSection;
                    System.out.println(Tuple.InSection + ":");
                }
                System.out.println("    " + Tuple.LinkText + " (" + IDdictionary.get(Tuple.ID) + ")");

            }

        } catch (IOException | JDOMException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static String getFullTitle(List<Element> titles){
        var result = "";
        for(Element title : titles){
            var descendants = title.getDescendants();
            for (Content desc : descendants){
                if(desc instanceof Text){
                    Text resultText = (Text) desc;
                    result += resultText.getText();
                }
            }
        }
        return result;
    }

    private static String getFullLinkText(Element element){
        var result = "";
        var descendants = element.getDescendants();
        for(Content desc : descendants){
            if( desc instanceof Text){
                Text resultText = (Text) desc;
                result += resultText.getText();
            }
        }
        return result;
    }
}

