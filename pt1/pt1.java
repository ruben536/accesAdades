import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class pt1 {

    public static void main(String[] args) {
        String nomFitxer = "text.txt";

        int numCaracters = 0;
        int numLinies = 0;
        int numParaules = 0;
        boolean dinsParaula = false;
        int[] frequencia = new int[65536];

        boolean fitxerNoBuit = false;
        boolean ultimCaracterEraSaltDeLinia = true; // per gestionar l'última línia

        try (FileReader fr = new FileReader(nomFitxer)) {

            int c;

            while ((c = fr.read()) != -1) {
                char caracter = (char) c;
                fitxerNoBuit = true;

                // 1. Comptar caràcters (sense comptar salts de línia)
                if (caracter != '\n' && caracter != '\r') {
                    numCaracters++;
                }

                // 2. Comptar línies (comptem els \n)
                if (caracter == '\n') {
                    numLinies++;
                }

                // 3. Comptar paraules
                boolean esSeparador = (caracter == ' ' || caracter == '\t' ||
                                        caracter == '\n' || caracter == '\r');

                if (!esSeparador) {
                    if (!dinsParaula) {
                        numParaules++;
                        dinsParaula = true;
                    }
                } else {
                    dinsParaula = false;
                }

                // 4. Comptar freqüència de caràcters (sense espais, tabs ni salts de línia)
                if (!esSeparador) {
                    frequencia[caracter]++;
                }

                ultimCaracterEraSaltDeLinia = (caracter == '\n');
            }

            // Si el fitxer no és buit i no acaba amb salt de línia,
            // l'última línia també s'ha de comptar
            if (fitxerNoBuit && !ultimCaracterEraSaltDeLinia) {
                numLinies++;
            }

            // Trobar el caràcter més repetit
            char caracterMesRepetit = ' ';
            int maxFrequencia = 0;

            for (int i = 0; i < frequencia.length; i++) {
                if (frequencia[i] > maxFrequencia) {
                    maxFrequencia = frequencia[i];
                    caracterMesRepetit = (char) i;
                }
            }

            // Mostrar resultats
            System.out.println("Nombre de caràcters: " + numCaracters);
            System.out.println("Nombre de línies: " + numLinies);
            System.out.println("Nombre de paraules: " + numParaules);

            if (maxFrequencia > 0) {
                System.out.println("Caràcter més repetit: '" + caracterMesRepetit +
                                    "' (" + maxFrequencia + " vegades)");
            } else {
                System.out.println("No s'ha trobat cap caràcter per analitzar.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: el fitxer '" + nomFitxer + "' no existeix.");
        } catch (IOException e) {
            System.out.println("Error de lectura del fitxer: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Error: no tens permisos per accedir al fitxer '" + nomFitxer + "'.");
        }
    }
}