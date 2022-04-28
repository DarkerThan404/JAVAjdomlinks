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
            var rootNodeChildrens = rootNode.getChildren("section");
            String currentSectionTitle = "";
            Map<String, SectionTuple> dict = new HashMap<>();
            for (Element section : rootNodeChildrens){
                currentSectionTitle = getFullTitle(section.getChildren("title"));
                System.out.println(currentSectionTitle);
                var stringId = getEmptyStringOrId(section);
                if(!stringId.equals("")){
                    addTuple(stringId,currentSectionTitle, "","", dict);
                }
                var descendants = section.getDescendants();
                for(Content descendant : descendants){
                    if(descendant instanceof Element){
                        Element descElement = (Element) descendant;
                        if(descElement.getName().equals("section")){
                            currentSectionTitle = getFullTitle(descElement.getChildren("title"));
                            //System.out.println(currentSectionTitle);
                        }
                        if(descElement.getName().equals("link")){
                            var id = descElement.getAttribute("linkend").getValue();
                            var linkText = getFullLinkText(descElement);
                            System.out.println("Id :" + id + " Section: " + currentSectionTitle + " linktext: " + linkText);
                            addTuple(id,"",currentSectionTitle,linkText, dict);
                        }

                        var id = descElement.getAttribute("id");
                        if(id != null){
                            //System.out.println("Id: {"+ id.getValue() + "} in section: " + currentSectionTitle);
                            addTuple(id.getValue(),currentSectionTitle, "","", dict);
                        }
                    }
                }
            }

            for(Map.Entry<String, SectionTuple> entry : dict.entrySet()){
                System.out.println("Key = " + entry.getKey() + ", Value = {SectionIn: " + entry.getValue().SectionIn + ", SectionOut: " + entry.getValue().SectionOut + "," +
                        "LinkText: " + entry.getValue().LinkText + "}");
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

    private static String getEmptyStringOrId(Element element){
        var result = "";
        var interRes = element.getAttribute("id");
        if(interRes != null){
            result = interRes.getValue();
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

    private static void addTuple(String key, String sectionIn, String sectionOut, String linkText, Map<String, SectionTuple> dict){
        if(dict.containsKey(key)){
            var out = dict.get(key);
            if(out.SectionIn.equals("")){
                out.SectionIn = sectionIn;
            }
            if(out.SectionOut.equals("")){
                out.SectionOut = sectionOut;
            }

            if(out.LinkText.equals("")){
                out.LinkText = linkText;
            }
            dict.put(key, out);
        } else {
            dict.put(key, new SectionTuple(sectionIn, sectionOut, linkText));
        }
    }
}
/*
Stack<List<Element>> stack = new Stack<>();
            stack.add(rootList);

            while(stack.size() > 0){
                var poppedSections = stack.pop();
                for (Element target : poppedSections) {
                    List<Element> nested = target.getChildren("section");
                    if(nested.size() > 0) {
                        stack.add(nested);
                    }
                    var desc = target.getDescendants();
                    for(var des : desc){
                        if(des instanceof Element){
                            Element el = (Element) des;
                            if(el.getName().equals("link")){
                                var id = el.getAttribute("linkend");
                                String stringId = "";
                                if(id != null){
                                    stringId = id.toString();
                                    System.out.println("Id of link: " + stringId);
                                }
                            }
                        }
                        //System.out.println(des);
                    }
                    var Title = target.getChildren("title");
                    String title1 = "";
                    for(Element titleTarget : Title){
                         title1 = getFullTitle(titleTarget);
                    }
                    System.out.println(title1);
                }
            }

            for (var descendant : rootNodeDescendants) {
                if(descendant instanceof Element){
                    Element elementDesc = (Element) descendant;
                    if(elementDesc.getName().equals("title")){
                        sectionTitle = getFullTitle(elementDesc);
                        System.out.println(sectionTitle);
                    }
                }
            }
 */

