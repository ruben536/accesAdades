import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

/**
 * Part 1 - Creació del fitxer zoo.xml amb DOM.
 */
public class CrearZoo {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Arrel <zoo>
            Element zoo = doc.createElement("zoo");
            doc.appendChild(zoo);

            // Dades dels animals: id, nom, espècie, aliment, edat
            Object[][] animals = {
                {1, "Pingüí",   "Aptenodytes forsteri", "Peix",    5},
                {2, "Lleó",     "Panthera leo",         "Carn",    8},
                {3, "Elefant",  "Loxodonta africana",   "Herba",  20},
                {4, "Girafa",   "Giraffa camelopardalis", "Fulles", 12},
                {5, "Tigre",    "Panthera tigris",      "Carn",    7}
            };

            for (Object[] a : animals) {
                zoo.appendChild(crearAnimal(doc, (int) a[0], (String) a[1],
                        (String) a[2], (String) a[3], (int) a[4]));
            }

            // Escriure el document al fitxer amb sagnat
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            File fitxer = new File("zoo.xml");
            transformer.transform(new DOMSource(doc), new StreamResult(fitxer));

            System.out.println("Fitxer creat correctament: " + fitxer.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error creant l'XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Element crearAnimal(Document doc, int id, String nom,
                                       String especie, String aliment, int edat) {
        Element animal = doc.createElement("animal");
        animal.setAttribute("id", String.valueOf(id));

        animal.appendChild(crearElement(doc, "nom", nom));
        animal.appendChild(crearElement(doc, "especie", especie));
        animal.appendChild(crearElement(doc, "aliment", aliment));
        animal.appendChild(crearElement(doc, "edat", String.valueOf(edat)));

        return animal;
    }

    private static Element crearElement(Document doc, String etiqueta, String text) {
        Element e = doc.createElement(etiqueta);
        e.setTextContent(text);
        return e;
    }
}
