/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getgitdata;

/**
 *
 * @author Masud
 */


import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.transform.OutputKeys;
public class XMLparser {
    public static String pathtowrite =  "I:/Dev/NetbeanProjects/data/getgitdata/Eclipse_Platform_UI_patch.xml";
    public static String pathtoread =  "I:/Dev/NetbeanProjects/data/getgitdata/Eclipse_Platform_UI.xml";
  // public static String pathtoread =  "I:/Dev/NetbeanProjects/data/getgitdata/tom_sample.xml";
   
    public static void main(String[] args){
       
       XMLparser xmlp = new XMLparser();
     //  xmlp.writetoXML(pathtowrite);
       xmlp.processXML(pathtoread);
       
   }
public void processXML(String filepath){
    GetGitData ggd = new GetGitData();
    
    try {
  
      File inputFile = new File(filepath);
      DocumentBuilderFactory dbFactory 
         = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      
 //     System.out.println("Root element :"+ doc.getDocumentElement().getNodeName());
      NodeList nList = doc.getElementsByTagName("table");
 //     System.out.println("----------------------------");
      
      
      //writing to file
      DocumentBuilderFactory dbFactory_write = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder_write = dbFactory_write.newDocumentBuilder();
         Document doc_write = dBuilder_write.newDocument();
         // root element
         Element rootElement_write = doc_write.createElement("project");
         rootElement_write.setAttribute("name", "Tomcat");
         doc_write.appendChild(rootElement_write);
         
         
      for (int temp = 0; temp < nList.getLength(); temp++) {
         Node nNode = nList.item(temp);
        // System.out.println("\nCurrent Element :" 
         //   + nNode.getNodeName());
         if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            //System.out.println("table elements : "+ eElement.getAttribute("column"));
            String column0r = eElement.getElementsByTagName("column").item(0).getTextContent();
            String column1r = eElement.getElementsByTagName("column").item(1).getTextContent();
            String column2r = eElement.getElementsByTagName("column").item(2).getTextContent();
            String column3r = eElement.getElementsByTagName("column").item(3).getTextContent();
            String column4r = eElement.getElementsByTagName("column").item(4).getTextContent();
            String column5r = eElement.getElementsByTagName("column").item(5).getTextContent();
            String column6r = eElement.getElementsByTagName("column").item(6).getTextContent();
            String column7r = eElement.getElementsByTagName("column").item(7).getTextContent();
            String column8r = eElement.getElementsByTagName("column").item(8).getTextContent();
            String column9r = eElement.getElementsByTagName("column").item(9).getTextContent();
//            String column10r = eElement.getElementsByTagName("column").item(10).getTextContent();
            
            /* System.out.println("Column #"+column0);
             System.out.println("Column #"+column1);
             System.out.println("Column #"+column2);
             System.out.println("Column #"+column3);
             System.out.println("Column #"+column4);
             System.out.println("Column #"+column5);
             System.out.println("Column #"+column6);
             System.out.println("Column #"+column7);
             System.out.println("Column #"+column8);
             System.out.println("Column #"+column9);
             System.out.println("Column #"+column10);*/
             
             System.out.println("Path for bug_id: "+column1r);
             //runn command to get patch here
             String commit_ssh = ggd.getPaerntsGitCommand("gitparent.bat","tomcat",column7r);
             String[] commit_parts = commit_ssh.trim().split("\n");
             if(commit_parts.length<4){
                 System.out.println("Cannot find parent commit for this bug id: "+column1r);
                 continue;//skip ambiguous bug reported commit
             }
            String[] two_commits = commit_parts[3].trim().split(" ");
            String commit_sh1 = two_commits[0].trim();
            String commit_sh2 = two_commits[1].trim();
            
            String patch_results = ggd.getPatchGitCommand("git_dif_patch.bat", commit_sh1,commit_sh2);
//            System.out.println("######################Start path#######################");
//             System.out.println("Path codes: \n"+patch_results);
//            System.out.println("######################End patch#######################");
            
            //writie path to xml file
            
             // setting attribute to element
         if(patch_results==null){
             System.out.println("Patch is null for bug id : "+column1r);
             patch_results="";
         }
         Element table = doc_write.createElement("table");
         rootElement_write.appendChild(table);
         
         Attr attr = doc_write.createAttribute("name");
         attr.setValue("Tomcat");
         table.setAttributeNode(attr);
         Element column = doc_write.createElement("column");
         Attr attrType = doc_write.createAttribute("name");
         attrType.setValue("id");
         column.setAttributeNode(attrType);
         column.appendChild(doc_write.createTextNode(column0r));
         table.appendChild(column);

        // Attr attr1 = doc.createAttribute("name");
         //attr1.setValue("Tomcat");
         //table.setAttributeNode(attr);
         Element column1 = doc_write.createElement("column");
         Attr attrType1 = doc_write.createAttribute("name");
         attrType1.setValue("bug_id");
         column1.setAttributeNode(attrType1);
         column1.appendChild(doc_write.createTextNode(column1r));
         table.appendChild(column1);
         
         Element column2 = doc_write.createElement("column");
         Attr attrType2 = doc_write.createAttribute("name");
         attrType2.setValue("summary");
         column2.setAttributeNode(attrType2);
         column2.appendChild(doc_write.createTextNode(column2r));
         table.appendChild(column2);
         
           Element column3 = doc_write.createElement("column");
         Attr attrType3 = doc_write.createAttribute("name");
         attrType3.setValue("description");
         column3.setAttributeNode(attrType3);
         column3.appendChild(doc_write.createTextNode(column3r));
         table.appendChild(column3);
         
           Element column4 = doc_write.createElement("column");
         Attr attrType4 = doc_write.createAttribute("name");
         attrType4.setValue("report_time");
         column4.setAttributeNode(attrType4);
         column4.appendChild(doc_write.createTextNode(column4r));
         table.appendChild(column4);
         
           Element column5 = doc_write.createElement("column");
         Attr attrType5 = doc_write.createAttribute("name");
         attrType5.setValue("report_timestamp");
         column5.setAttributeNode(attrType5);
         column5.appendChild(doc_write.createTextNode(column5r));
         table.appendChild(column5);
         
           Element column6 = doc_write.createElement("column");
         Attr attrType6 = doc_write.createAttribute("name");
         attrType6.setValue("status");
         column6.setAttributeNode(attrType6);
         column6.appendChild(doc_write.createTextNode(column6r));
         table.appendChild(column6);
         
         Element column7 = doc_write.createElement("column");
         Attr attrType7 = doc_write.createAttribute("name");
         attrType7.setValue("commit");
         column7.setAttributeNode(attrType7);
         column7.appendChild(doc_write.createTextNode(column7r));
         table.appendChild(column7);
         
         Element column8 = doc_write.createElement("column");
         Attr attrType8 = doc_write.createAttribute("name");
         attrType8.setValue("commit_timestamp");
         column8.setAttributeNode(attrType8);
         column8.appendChild(doc_write.createTextNode(column8r));
         table.appendChild(column8);
         
         Element column9 = doc_write.createElement("column");
         Attr attrType9 = doc_write.createAttribute("name");
         attrType9.setValue("files");
         column9.setAttributeNode(attrType9);
         column9.appendChild(doc_write.createTextNode(column9r));
         table.appendChild(column9);
         
         Element column10 = doc_write.createElement("column");
         Attr attrType10 = doc_write.createAttribute("name");
         attrType10.setValue("patch");
         column10.setAttributeNode(attrType10);
         column10.appendChild(doc_write.createTextNode(patch_results));
         table.appendChild(column10);
         
        /* Element column11 = doc_write.createElement("column");
         Attr attrType11 = doc_write.createAttribute("name");
         attrType11.setValue("patch");
         column11.setAttributeNode(attrType11);
         column11.appendChild(doc_write.createTextNode(patch_results));
         table.appendChild(column11);*/
         
         // write the content into xml file
         TransformerFactory transformerFactory =
         TransformerFactory.newInstance();
         Transformer transformer =
         transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        try{ 
         DOMSource source = new DOMSource(doc_write);
         //   System.out.println("Patch Results are: "+patch_results);
         StreamResult result = new StreamResult(new File(pathtowrite));
         transformer.transform(source, result);
        }catch(Exception e){
            e.printStackTrace();
        }
         // Output to console for testing
      /*   StreamResult consoleResult =
         new StreamResult(System.out);
         transformer.transform(source, consoleResult);*/
         
         }
      }
   } catch (Exception e) {
      e.printStackTrace();
   }
}
   public void writetoXML(String pathtowrite){
        try {
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();
         // root element
         Element rootElement = doc.createElement("project");
         rootElement.setAttribute("name", "Tomcat");
         doc.appendChild(rootElement);

         //  supercars element
         for(int i=0;i<10;i++){
         Element table = doc.createElement("table");
         rootElement.appendChild(table);

         // setting attribute to element
         Attr attr = doc.createAttribute("name");
         attr.setValue("Tomcat");
         table.setAttributeNode(attr);
         Element column = doc.createElement("column");
         Attr attrType = doc.createAttribute("name");
         attrType.setValue("id");
         column.setAttributeNode(attrType);
         column.appendChild(doc.createTextNode("1222"));
         table.appendChild(column);

        // Attr attr1 = doc.createAttribute("name");
         //attr1.setValue("Tomcat");
         //table.setAttributeNode(attr);
         Element column1 = doc.createElement("column");
         Attr attrType1 = doc.createAttribute("name");
         attrType1.setValue("bug_id");
         column1.setAttributeNode(attrType1);
         column1.appendChild(doc.createTextNode("658hj"));
         table.appendChild(column1);
         
         Element column2 = doc.createElement("column");
         Attr attrType2 = doc.createAttribute("name");
         attrType2.setValue("bug_id");
         column2.setAttributeNode(attrType2);
         column2.appendChild(doc.createTextNode("658hj"));
         table.appendChild(column2);
         
           Element column3 = doc.createElement("column");
         Attr attrType3 = doc.createAttribute("name");
         attrType3.setValue("summary");
         column3.setAttributeNode(attrType3);
         column3.appendChild(doc.createTextNode("fgrgergr sum"));
         table.appendChild(column3);
         
           Element column4 = doc.createElement("column");
         Attr attrType4 = doc.createAttribute("name");
         attrType4.setValue("description");
         column4.setAttributeNode(attrType4);
         column4.appendChild(doc.createTextNode("description"));
         table.appendChild(column4);
         
           Element column5 = doc.createElement("column");
         Attr attrType5 = doc.createAttribute("name");
         attrType5.setValue("report_time");
         column5.setAttributeNode(attrType5);
         column5.appendChild(doc.createTextNode("description"));
         table.appendChild(column5);
         
           Element column6 = doc.createElement("column");
         Attr attrType6 = doc.createAttribute("name");
         attrType6.setValue("report_timestamp");
         column6.setAttributeNode(attrType6);
         column6.appendChild(doc.createTextNode("report_timestamp"));
         table.appendChild(column6);
         
         Element column7 = doc.createElement("column");
         Attr attrType7 = doc.createAttribute("name");
         attrType7.setValue("status");
         column7.setAttributeNode(attrType7);
         column7.appendChild(doc.createTextNode("status"));
         table.appendChild(column7);
         
         Element column8 = doc.createElement("column");
         Attr attrType8 = doc.createAttribute("name");
         attrType8.setValue("commit");
         column8.setAttributeNode(attrType8);
         column8.appendChild(doc.createTextNode("commit"));
         table.appendChild(column8);
         
         Element column9 = doc.createElement("column");
         Attr attrType9 = doc.createAttribute("name");
         attrType8.setValue("commit_timestamp");
         column8.setAttributeNode(attrType8);
         column8.appendChild(doc.createTextNode("commit_timestamp"));
         table.appendChild(column8);
         
         Element column10 = doc.createElement("column");
         Attr attrType10 = doc.createAttribute("name");
         attrType10.setValue("files");
         column10.setAttributeNode(attrType10);
         column10.appendChild(doc.createTextNode("files"));
         table.appendChild(column10);
         
         Element column11 = doc.createElement("column");
         Attr attrType11 = doc.createAttribute("name");
         attrType11.setValue("patch");
         column11.setAttributeNode(attrType11);
         column11.appendChild(doc.createTextNode("patch code here"));
         table.appendChild(column11);
        }
         /* Element carname1 = doc.createElement("carname");
         Attr attrType1 = doc.createAttribute("type");
         attrType1.setValue("sports");
         carname1.setAttributeNode(attrType1);
         carname1.appendChild(
         doc.createTextNode("Ferrari 202"));
         supercar.appendChild(carname1);*/

         // write the content into xml file
         TransformerFactory transformerFactory =
         TransformerFactory.newInstance();
         Transformer transformer =
         transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
         
         DOMSource source = new DOMSource(doc);
         StreamResult result =
         new StreamResult(new File(pathtowrite));
         transformer.transform(source, result);
         // Output to console for testing
         StreamResult consoleResult =
         new StreamResult(System.out);
         transformer.transform(source, consoleResult);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}