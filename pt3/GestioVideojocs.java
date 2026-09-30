import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestioVideojocs {

    private static final String FITXER = "videojocs.dat";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Videojoc> videojocs = carregarVideojocs();

        int opcio;
        do {
            System.out.println("\n--- Gestió de Videojocs ---");
            System.out.println("1. Afegir videojoc");
            System.out.println("2. Llistar tots els videojocs");
            System.out.println("3. Cercar videojocs per títol");
            System.out.println("4. Actualitzar un videojoc");
            System.out.println("5. Eliminar un videojoc");
            System.out.println("6. Sortir");
            opcio = llegirEnter("Tria una opció: ");

            switch (opcio) {
                case 1: afegir(videojocs); break;
                case 2: llistar(videojocs); break;
                case 3: cercar(videojocs); break;
                case 4: actualitzar(videojocs); break;
                case 5: eliminar(videojocs); break;
                case 6:
                    desarVideojocs(videojocs);
                    System.out.println("Canvis desats. Sortint del programa...");
                    break;
                default: System.out.println("Opció incorrecta. Torna-ho a provar.");
            }
        } while (opcio != 6);

        sc.close();
    }

    // ---------- Operacions CRUD ----------

    private static void afegir(ArrayList<Videojoc> videojocs) {
        System.out.print("Títol: ");
        String titol = sc.nextLine();
        System.out.print("Gènere: ");
        String genere = sc.nextLine();
        int any = llegirEnter("Any de llançament: ");
        System.out.print("Plataforma (PC, PlayStation, Xbox, Switch...): ");
        String plataforma = sc.nextLine();
        double preu = llegirDouble("Preu: ");

        videojocs.add(new Videojoc(titol, genere, any, plataforma, preu));
        desarVideojocs(videojocs);
        System.out.println("Videojoc afegit i desat correctament.");
    }

    private static void llistar(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs desats.");
            return;
        }
        System.out.println("--- Llista de videojocs ---");
        for (int i = 0; i < videojocs.size(); i++) {
            System.out.println((i + 1) + ". " + videojocs.get(i));
        }
    }

    private static void cercar(ArrayList<Videojoc> videojocs) {
        System.out.print("Text a cercar en el títol: ");
        String text = sc.nextLine().toLowerCase();
        boolean trobat = false;
        for (Videojoc v : videojocs) {
            if (v.getTitol().toLowerCase().contains(text)) {
                System.out.println(v);
                trobat = true;
            }
        }
        if (!trobat) {
            System.out.println("Cap videojoc coincideix amb la cerca.");
        }
    }

    private static void actualitzar(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs per actualitzar.");
            return;
        }
        llistar(videojocs);
        int num = llegirEnter("Número del videojoc a actualitzar: ");
        if (num < 1 || num > videojocs.size()) {
            System.out.println("Número no vàlid.");
            return;
        }
        Videojoc v = videojocs.get(num - 1);
        System.out.println("(Deixa buit per mantenir el valor actual)");

        System.out.print("Títol [" + v.getTitol() + "]: ");
        String titol = sc.nextLine();
        if (!titol.isBlank()) v.setTitol(titol);

        System.out.print("Gènere [" + v.getGenere() + "]: ");
        String genere = sc.nextLine();
        if (!genere.isBlank()) v.setGenere(genere);

        System.out.print("Any [" + v.getAnyLlancament() + "]: ");
        String any = sc.nextLine();
        if (!any.isBlank()) {
            try { v.setAnyLlancament(Integer.parseInt(any.trim())); }
            catch (NumberFormatException e) { System.out.println("Any no vàlid, es manté l'anterior."); }
        }

        System.out.print("Plataforma [" + v.getPlataforma() + "]: ");
        String plataforma = sc.nextLine();
        if (!plataforma.isBlank()) v.setPlataforma(plataforma);

        System.out.print("Preu [" + v.getPreu() + "]: ");
        String preu = sc.nextLine();
        if (!preu.isBlank()) {
            try { v.setPreu(Double.parseDouble(preu.trim().replace(',', '.'))); }
            catch (NumberFormatException e) { System.out.println("Preu no vàlid, es manté l'anterior."); }
        }

        desarVideojocs(videojocs);
        System.out.println("Videojoc actualitzat i desat.");
    }

    private static void eliminar(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs per eliminar.");
            return;
        }
        llistar(videojocs);
        int num = llegirEnter("Número del videojoc a eliminar: ");
        if (num < 1 || num > videojocs.size()) {
            System.out.println("Número no vàlid.");
            return;
        }
        Videojoc eliminat = videojocs.remove(num - 1);
        desarVideojocs(videojocs);
        System.out.println("Eliminat: " + eliminat.getTitol());
    }

    // ---------- Persistència ----------

    @SuppressWarnings("unchecked")
    public static ArrayList<Videojoc> carregarVideojocs() {
        ArrayList<Videojoc> videojocs = new ArrayList<>();
        File fitxer = new File(FITXER);
        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                videojocs = (ArrayList<Videojoc>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant videojocs: " + e.getMessage());
            }
        }
        return videojocs;
    }

    public static void desarVideojocs(ArrayList<Videojoc> videojocs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(videojocs);
        } catch (IOException e) {
            System.out.println("Error desant videojocs: " + e.getMessage());
        }
    }

    // ---------- Entrada segura ----------

    private static int llegirEnter(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introdueix un número enter vàlid.");
            }
        }
    }

    private static double llegirDouble(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Introdueix un número vàlid.");
            }
        }
    }
}
