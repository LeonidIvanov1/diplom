package tablemaker;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TableMaker {

    private static ArrayList<Table> tables = new ArrayList<Table>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File("tables.xml"), handler);

        for (Table table : tables) {
            System.out.println(table.createTableInfo());
        }
    }

    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startDocument() throws SAXException {

        }

        @Override
        public void endDocument() throws SAXException {

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("table")) {
                Table table = new Table(attributes.getValue("table-name"));
                tables.add(table);
            } else if (qName.equalsIgnoreCase("column")) {
                TableColumn tableColumn = new TableColumn(attributes.getValue("name"), attributes.getValue("type"));
                tables.get(tables.size() - 1).addColumn(tableColumn);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {

        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

        }
    }


}
