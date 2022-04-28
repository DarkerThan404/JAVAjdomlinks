package cz.cuni.mff.java.hw.jdomlinks;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Main {

    public static void main(String[] argv) {

        try(InputStream in = new BufferedInputStream(new BufferedInputStream(System.in))){
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build( in);
            Element rootNode = doc.getRootElement();
            List<Element> rootList = rootNode.getChildren("section");
            Stack<List<Element>> stack = new Stack<>();
            stack.add(rootList);
            while(stack.size() > 0){
                var poppedSections = stack.pop();
                for (Element target : poppedSections) {
                    List<Element> nested = target.getChildren("section");
                    if(nested.size() > 0) {
                        stack.add(nested);
                    }
                    var Title = target.getChildren("title");
                    for(Element titleTarget : Title){
                       var descendants = titleTarget.getDescendants();
                       String title = "";
                       for (Content desc : descendants){
                           if(desc instanceof Text){
                               Text result = (Text) desc;
                               title += result.getText();
                           }
                       }
                       System.out.println(title);
                    }

                }
            }
        } catch (IOException | JDOMException ex) {
            System.out.println(ex.getMessage());
        }


    }
}

