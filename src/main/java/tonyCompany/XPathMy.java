import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.*;


public class XPathMy {

    public static void main(String[] args) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // включаем поддержку пространства имен XML
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            doc = builder.parse("./Developers.xml");

            // Создаем объект XPathFactory
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Получаем экзмепляр XPath для создания XPathExpression выражений
            XPath xpath = xpathFactory.newXPath();

            String devName = getDeveloperNameById(doc, xpath, 1);
            System.out.println("Имя разработчика с id = 1: " + devName);

            List<String> names = getDevelopersWithAge(doc, xpath, 23);
            System.out.println("Разработчики, младше 23 лет: "
                    + names.toString());

            List<String> middleDevelopers = getMiddleDevelopers(doc, xpath);
            System.out.println("Работают на позиции Middle Developer: " +
                    middleDevelopers.toString());

            Set<String> languages = getLanguages(doc, xpath);
            System.out.println("Языки программирования: " +
                    languages.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e ){
            e.printStackTrace();
        }
        catch (SAXException e ){
            e.printStackTrace();
        }

    }

    private static List<String> getMiddleDevelopers(Document doc, XPath xpath) {
        List<String> list = new ArrayList<String>();
        try {
            //создаем объект XPathExpression
            XPathExpression xPathExpression = xpath.compile(
                    "/Developers/Developer[position='Middle']/name/text()"
            );
            // получаем список всех тегов, которые отвечают условию
            NodeList nodes = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            // проходим по списку и получаем значение с помощью getNodeValue()
            for (int i = 0; i < nodes.getLength(); i++)
                list.add(nodes.item(i).getNodeValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }


    private static List<String> getDevelopersWithAge(Document doc, XPath xpath, int age) {
        List<String> list = new ArrayList<String>();
        try {
            // получаем список всех узлов, которые отвечают условию
            XPathExpression xPathExpression = xpath.compile(
                    "/Developers/Developer[age<" + age + "]/name/text()"
            );
            NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++)
                list.add(nodeList.item(i).getNodeValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }


    private static String getDeveloperNameById(Document doc, XPath xpath, int id) {
        String devName = null;
        try {
            XPathExpression xPathExpression = xpath.compile(
                    "/Developers/Developer[@id='" + id + "']/name/text()"
            );
            devName = (String) xPathExpression.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return devName;
    }

    private static Set<String> getLanguages(Document doc, XPath xpath) {
        Set<String> set = new HashSet<String>();
        try {
            //создаем объект XPathExpression
            XPathExpression xPathExpression = xpath.compile(
                    "/Developers/Developer[language!='null']/language/text()"
            );
            // получаем список всех тегов, которые отвечают условию
            NodeList nodes = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            // проходим по списку и получаем значение с помощью getNodeValue()
            for (int i = 0; i < nodes.getLength(); i++)
                set.add(nodes.item(i).getNodeValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return set;
    }


}