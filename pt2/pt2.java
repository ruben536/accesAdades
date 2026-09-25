import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class pt2 {

    public static void main(String[] args) {

        String fitxerEntrada = "entrada.txt";
        String fitxerXifrat = "xifrat.txt";
        String fitxerDesxifrat = "desxifrat.txt";

        int clau = demanarClau();

        System.out.println();
        xifrarFitxer(fitxerEntrada, fitxerXifrat, clau);

        System.out.println();
        desxifrarFitxer(fitxerXifrat, fitxerDesxifrat, clau);
    }

    /**
     * Demana la clau de xifrat/desxifrat per consola.
     * Si l'usuari no introdueix un número vàlid, s'utilitza una clau per defecte.
     */
    private static int demanarClau() {
        Scanner scanner = new Scanner(System.in);
        int clauPerDefecte = 3;

        System.out.print("Introdueix la clau de xifrat (número enter, per defecte " + clauPerDefecte + "): ");
        String linia = scanner.nextLine().trim();
        
        if (linia.isEmpty()) {
            System.out.println("No s'ha introduït cap valor. S'utilitzarà la clau per defecte: " + clauPerDefecte);
            return clauPerDefecte;
        }

        try {
            return Integer.parseInt(linia);
        } catch (NumberFormatException e) {
            System.out.println("Valor no vàlid. S'utilitzarà la clau per defecte: " + clauPerDefecte);
            return clauPerDefecte;
        }
    }

    /**
     * Inverteix l'ordre dels caràcters d'una línia.
     * Exemple: "Hola món" -> "nóm aloH"
     */
    private static String invertirLinia(String linia) {
        return new StringBuilder(linia).reverse().toString();
    }

    /**
     * Desplaça cada caràcter de la línia 'desplacament' posicions en Unicode.
     * Un desplacament negatiu desfà l'efecte d'un de positiu del mateix valor.
     */
    private static String desplacarCaracters(String linia, int desplacament) {
        StringBuilder resultat = new StringBuilder();
        for (char c : linia.toCharArray()) {
            resultat.append((char) (c + desplacament));
        }
        return resultat.toString();
    }

    /**
     * Llegeix fitxerEntrada línia per línia, inverteix cada línia,
     * aplica el xifrat Cèsar (desplaçament +clau) i escriu el resultat a fitxerSortida.
     */
    private static void xifrarFitxer(String fitxerEntrada, String fitxerSortida, int clau) {
        System.out.println("Xifrant '" + fitxerEntrada + "' amb clau " + clau + "...");

        int numLinies = 0;

        try (
            BufferedReader br = new BufferedReader(new FileReader(fitxerEntrada));
            BufferedWriter bw = new BufferedWriter(new FileWriter(fitxerSortida))
        ) {
            String linia;
            while ((linia = br.readLine()) != null) {
                String invertida = invertirLinia(linia);
                String xifrada = desplacarCaracters(invertida, clau);

                bw.write(xifrada);
                bw.newLine();
                numLinies++;
            }

            System.out.println("Xifrat completat: " + numLinies + " línia(es) escrites a '" + fitxerSortida + "'.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: el fitxer '" + fitxerEntrada + "' no existeix.");
        } catch (IOException e) {
            System.out.println("Error d'entrada/sortida durant el xifrat: " + e.getMessage());
        }
    }

    /**
     * Llegeix fitxerEntrada (ja xifrat) línia per línia, desfà el desplaçament
     * Cèsar (desplaçament -clau) i torna a invertir la línia per recuperar
     * el missatge original. Escriu el resultat a fitxerSortida.
     */
    private static void desxifrarFitxer(String fitxerEntrada, String fitxerSortida, int clau) {
        System.out.println("Desxifrant '" + fitxerEntrada + "' amb clau " + clau + "...");

        int numLinies = 0;

        try (
            BufferedReader br = new BufferedReader(new FileReader(fitxerEntrada));
            BufferedWriter bw = new BufferedWriter(new FileWriter(fitxerSortida))
        ) {
            String linia;
            while ((linia = br.readLine()) != null) {
                String desplacada = desplacarCaracters(linia, -clau);
                String original = invertirLinia(desplacada);

                bw.write(original);
                bw.newLine();
                numLinies++;
            }

            System.out.println("Desxifrat completat: " + numLinies + " línia(es) escrites a '" + fitxerSortida + "'.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: el fitxer '" + fitxerEntrada + "' no existeix.");
        } catch (IOException e) {
            System.out.println("Error d'entrada/sortida durant el desxifrat: " + e.getMessage());
        }
    }
}