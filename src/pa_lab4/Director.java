/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa_lab4;

import java.io.File;
import lab4_exceptii.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Alin
 */
public class Director {

    static private List<String> directoare;
    static private String cale;
    static private char volum;
    static public final String separator = "\\";
    static public final String separatorVolum = ":\\\\";

    Director() {
        directoare = new ArrayList<>();
        directoare.add("C");
        cale = "" + directoare.get(0) + separatorVolum;
    }

    public String getCale() {
        return cale;
    }

    public void schimbaVolum(char volum)
            throws VolumInexistentException {

        try {
            switch (volum) {
                case 'D':
                    cale = "" + "D" + separatorVolum;
                    this.volum = volum;
                    directoare.clear();
                    directoare.add("D");
                    break;
                case 'C':
                    cale = "" + "C" + separatorVolum;
                    this.volum = volum;
                    directoare.clear();
                    directoare.add("C");
                    break;

                default:
                    throw new VolumInexistentException("Volum inexistent!");
            }
        } catch (VolumInexistentException e) {
            System.out.println(e);
        }
    }

    public void schimbaDirectoareMultiple(String dir) {
        String[] aux = dir.split("/");
        for (int i = 0; i < aux.length; i++) {
            
            String caleAux;
            caleAux = cale;
            caleAux += separator + aux[i];
            try {
                if (Files.exists(Paths.get(caleAux))) {
                    if (directoare.size() == 1) {
                        cale += aux[i];
                        directoare.add(aux[i]);
                    } else {
                        cale += separator + aux[i];
                        directoare.add(aux[i]);
                    }
                } else {
                    throw new DirectorInexistentException("Directorul nu exista!");
                }

            } catch (DirectorInexistentException e) {
                System.out.println(e);
            }
        }
    }

    public void schimbaDirector(String dir)
            throws DirectorInexistentException {
 
        if (dir.indexOf("/") != -1) {
            schimbaDirectoareMultiple(dir);
        }
        String caleAux;
        caleAux = cale;
        caleAux += separator + dir;

        try {
            if (Files.exists(Paths.get(caleAux))) {
                if (directoare.size() == 1) {
                    cale += dir;
                    directoare.add(dir);
                } else {
                    cale += separator + dir;
                    directoare.add(dir);
                }
            } else {
                throw new DirectorInexistentException("Directorul nu exista!");
            }

        } catch (DirectorInexistentException e) {
            System.out.println(e);
        }
    }

    public void schimbaDirectorRoot() {
        if (directoare.size() == 2) {
            directoare.remove(1);
            cale = "" + directoare.get(0) + separatorVolum;
        } else if (directoare.size() == 1) {
            System.out.println("Calea nu mai poate fi schimbata!");
        } else {
            directoare.remove(directoare.size() - 1);
            cale = "" + directoare.get(0) + separatorVolum;

            for (int i = 1; i < directoare.size() - 1; i++) {
                cale += directoare.get(i) + separator;
            }

            cale += directoare.get(directoare.size() - 1);
        }
    }

}
