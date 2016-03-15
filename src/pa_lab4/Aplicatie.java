/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa_lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab4_exceptii.*;

/**
 *
 * @author Alin
 */
public class Aplicatie implements Serializable {

    private String[] comanda;

    private Director cd = new Director();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Shell shell = new Shell();

    public Aplicatie() {
        comanda = new String[2];
        comanda[0] = "";
        comanda[1] = "";
    }

    public static void salveazaFavorite() throws IOException, UnsupportedEncodingException, FileNotFoundException {
        PrintWriter wr = null;
        try {
            wr = new PrintWriter("D:\\favorite.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Aplicatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < Favorite.favorite.size(); i++) {
            wr.println(Favorite.favorite.get(i).toString());
            wr.write(Favorite.favorite.get(i).toString());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("D:\\favorite.txt"), "utf-8"))) {
            for (int i = 0; i < Favorite.favorite.size(); i++) {
                writer.write(Favorite.favorite.get(i).toString()+"\n");
            }
        }
    }

    public static void incarcaFavorite() throws FileNotFoundException, IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\favorite.txt"));

            String text = br.readLine();

            while (text != null) {
                Favorite.favorite.add(new File(text));
                text = br.readLine();
            }
        } catch (FileNotFoundException e) {

        }

    }

    public void afiseazaMeniu() {
        System.out.println("Introduceti una din comenzile:");
        System.out.print((char) 27 + "[32mcd " + (char) 27 + "[0margument: Schimba directorul curent daca [argument] exista / .. pentru a reveni in root  \n");
        System.out.print((char) 27 + "[32mlist " + (char) 27 + "[0margument: Listeaza fisierele audio din [argument] / directorul curent daca [argument] nu este specificat \n");
        System.out.print((char) 27 + "[32mplay " + (char) 27 + "[0margument: Ruleaza [argumet] daca este o aplicatie audio si daca aceasta exista (directorul curent) \n");
        System.out.print((char) 27 + "[32minfo " + (char) 27 + "[0margument: Afiseaza informatii generale despre [argument] daca este aplicatie audio si daca exista \n");
        System.out.print((char) 27 + "[32mfind " + (char) 27 + "[0margument: Cauta [argument] in directorul curent si subdiectoare \n");
        System.out.print((char) 27 + "[32mfav " + (char) 27 + "[0margument: Adauga [argument] in lista de melodii favorite \n");
        System.out.print((char) 27 + "[32mraport" + (char) 27 + "[0m: Creeaza rapoarte ce contin informatii in legatura cu lista de melodii favorite \n");
        System.out.print((char) 27 + "[32mexit" + (char) 27 + "[0m: Inchide aplicatia \n");
    }

    public void start() throws VolumInexistentException, DirectorInexistentException, IOException {
        incarcaFavorite();
        afiseazaMeniu();
        while (true) {
            comanda[0] = "";
            comanda[1] = "";
            System.out.println();
            System.out.println("Directorul curent: " + cd.getCale());
            String comandaAux = br.readLine();
            int j = 0;

            for (int i = 0; i < comandaAux.length(); i++) {
                if (comandaAux.charAt(i) != ' ' && j == 0) {
                    comanda[0] += comandaAux.charAt(i);
                } else if (comandaAux.charAt(i) != ' ' || j != 0) {
                    comanda[1] += comandaAux.charAt(i);
                } else {
                    j++;
                }
            }
            try {
                if (comanda[0].equals("cd")) {
                    if (comanda.length != 2) {
                        throw new ComandaInvalidaException("Numar de argumente invalid!");
                    } else {
                        shell.schimbaDirector(comanda[1]);
                    }
                } else if (comanda[0].equals("list")) {
                    if (comanda.length != 2) {
                        shell.listeazaFisiere();
                    } else {
                        shell.listeazaFisiere(comanda[1]);
                    }
                } else if (comanda[0].equals("play")) {
                    if (comanda.length != 2) {
                        throw new ComandaInvalidaException("Numar de argumente invalid!");
                    } else {
                        shell.ruleazaFisier(comanda[1]);
                    }
                } else if (comanda[0].equals("info")) {
                    if (comanda.length != 2) {
                        throw new ComandaInvalidaException("Numar de argumente invalid!");
                    } else {
                        shell.afiseazaInfo(comanda[1]);
                    }
                } else if (comanda[0].equals("find")) {
                    if (comanda.length != 2) {
                        throw new ComandaInvalidaException("Numar de argumente invalid!");
                    } else {
                        int i = shell.gasesteFisier(comanda[1], cd.getCale());
                        if (i == 0) {
                            System.out.print("Nu exista nici un fisier cu acest continut!");
                        }
                    }
                } else if (comanda[0].equals("fav")) {
                    if (comanda.length != 2) {
                        throw new ComandaInvalidaException("Numar de argumente invalid!");
                    } else {
                        shell.adaugaFavorite(comanda[1]);
                    }
                } else if (comanda[0].equals("raport")) {
                    shell.creeazaRaport();
                } else if (comanda[0].equals("exit")) {
                    break;
                } else {
                    throw new ComandaInvalidaException("Comanda introdusa este invalida!");
                }

            } catch (ComandaInvalidaException e) {
                System.out.println(e);
            }

        }

    }

}
