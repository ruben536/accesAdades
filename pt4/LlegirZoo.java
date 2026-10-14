import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Part 2 - Lectura de zoo.xml amb DOM, més els extres (bonus):
 *  - comptar animals
 *  - mostrar només els que mengen "Carn"
 */
public class LlegirZoo {

    public static void main(String[] args) {
        String ruta = args.length > 0 ? args[0] : "zoo.xml";

        try {
            File fitxer = new File(ruta);
            if (!fitxer.exists()) {
                System.err.println("No existeix el fitxer " + ruta + ". Executa primer CrearZoo.");
                return;
            }

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(fitxer);
            doc.getDocumentElement().normalize();

            NodeList animals = doc.getElementsByTagName("animal");

            // --- Part 2: mostrar tots els animals ---
            System.out.println("=== TOTS ELS ANIMALS ===");
            for (int i = 0; i < animals.getLength(); i++) {
                mostrarAnimal((Element) animals.item(i));
            }

            // --- Bonus 1: comptar animals ---
            System.out.println("=== BONUS ===");
            System.out.println("Total d'animals al zoo: " + animals.getLength());
            System.out.println();

            // --- Bonus 2: només els que mengen Carn ---
            System.out.println("Animals que mengen Carn:");
            int carnivors = 0;
            for (int i = 0; i < animals.getLength(); i++) {
                Element animal = (Element) animals.item(i);
                if (text(animal, "aliment").equalsIgnoreCase("Carn")) {
                    mostrarAnimal(animal);
                    carnivors++;
                }
            }
            if (carnivors == 0) {
                System.out.println("(cap)");
            }
        } catch (Exception e) {
            System.err.println("Error llegint l'XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarAnimal(Element animal) {
        System.out.println("Animal #" + animal.getAttribute("id"));
        System.out.println("Nom: " + text(animal, "nom"));
        System.out.println("Espècie: " + text(animal, "especie"));
        System.out.println("Aliment: " + text(animal, "aliment"));
        System.out.println("Edat: " + text(animal, "edat"));
        System.out.println();
    }

    private static String text(Element pare, String etiqueta) {
        NodeList nodes = pare.getElementsByTagName(etiqueta);
        return nodes.getLength() > 0 ? nodes.item(0).getTextContent().trim() : "";
    }
}
