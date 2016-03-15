/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa_lab4;

import freemarker.core.ParseException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab4_exceptii.DirectorInexistentException;
import lab4_exceptii.FisierNonAudioException;
import lab4_exceptii.FisierNonExistentException;
import lab4_exceptii.VolumInexistentException;
import net.sf.dynamicreports.report.exception.DRException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import static pa_lab4.Aplicatie.salveazaFavorite;

/**
 *
 * @author Alin
 */
public class Shell {

    private Favorite fav = new Favorite();
    private Director dr = new Director();
    private static List<String> listaFisiereAudio;
    private static List<File> listaAbsoluta;
    public int nrFisiere = 0;

    public Shell() {
        listaFisiereAudio = new ArrayList<>();
        listaAbsoluta = new ArrayList<>();
    }

    public void schimbaDirector(String arg) throws DirectorInexistentException, VolumInexistentException {
        if (arg.indexOf("/") != -1) {
            dr.schimbaDirectoareMultiple(arg);
        } else if (arg.equals("..")) {
            dr.schimbaDirectorRoot();
        } else if (arg.equals("C")) {
            dr.schimbaVolum('C');
        } else if (arg.equals("D")) {
            dr.schimbaVolum('D');
        } else {
            dr.schimbaDirector(arg);
        }
        listaFisiereAudio.clear();
        listaAbsoluta.clear();
        File director = new File(dr.getCale());

        File[] listaFisiere = director.listFiles();

        for (File file : listaFisiere) {
            if (file.isFile() && isAudio(file)) {
                listaAbsoluta.add(file);
                listaFisiereAudio.add(file.getName());
            }
        }
    }

    public boolean isAudio(File f) {
        String aux = f.toString();

        if (aux.endsWith(".mp3") || aux.endsWith(".flav") || aux.endsWith(".wav") || aux.endsWith(".ogg") || aux.endsWith(".wav")) {
            return true;
        }

        return false;
    }

    public void listeazaFisiere(String arg) {
        listaFisiereAudio.clear();
        listaAbsoluta.clear();
        String caleNoua = dr.getCale() + "\\" + arg;
        File director = new File(caleNoua);

        File[] listaFisiere = director.listFiles();

        for (File file : listaFisiere) {
            if (file.isFile() && isAudio(file)) {
                System.out.println(file.getName());
            }
        }
    }

    public void listeazaFisiere() {
        listaFisiereAudio.clear();
        listaAbsoluta.clear();
        File director = new File(dr.getCale());

        File[] listaFisiere = director.listFiles();

        for (File file : listaFisiere) {
            if (file.isFile() && isAudio(file)) {
                listaAbsoluta.add(file);
                listaFisiereAudio.add(file.getName());
            }
        }

        afiseazaLista();
    }

    public void afiseazaLista() {
        for (int i = 0; i < listaFisiereAudio.size(); i++) {
            System.out.println(listaFisiereAudio.get(i));
        }
    }

    public boolean existaFisier(String arg) {
        File director = new File(dr.getCale());

        File[] listaFisiere = director.listFiles();

        for (File file : listaFisiere) {
            if (file.isFile() && isAudio(file) && file.toString().indexOf(arg) != -1) {
                return true;
            }
        }
        return false;
    }

    public void ruleazaFisier(String arg) throws IOException {
        Desktop dk;
        dk = Desktop.getDesktop();
        File f = new File(arg);
        afiseazaLista();
        try {
            if (isAudio(f) && existaFisier(arg)) {
                //   dk.open(listaAbsoluta.get(listaFisiereAudio.indexOf(f)));
                String caleAux = "";
                caleAux += dr.getCale() + "\\" + arg;
                dk.open(new File(caleAux));
            } else if (isAudio(f) == false) {
                throw new FisierNonAudioException("Fisierul nu este audio!");
            } else if (existaFisier(arg) == false) {
                throw new FisierNonExistentException("Fisierul nu exista!");
            }
        } catch (FisierNonAudioException | FisierNonExistentException e) {
            System.out.println(e);
        }
    }

    public void afiseazaInfo(String arg) {
        if (isAudio(new File(arg))) {
            try {
                File f = new File(arg);
                String caleAux = "" + dr.getCale() + "\\" + arg;
                InputStream input = new FileInputStream(new File(caleAux));
                ContentHandler handler = new DefaultHandler();
                Metadata metadata = new Metadata();
                Parser parser = new Mp3Parser();
                ParseContext parseCtx = new ParseContext();
                parser.parse(input, handler, metadata, parseCtx);

                System.out.println();
                System.out.println("Titlu: " + metadata.get("title"));
                System.out.println("Artisti: " + metadata.get("xmpDM:artist"));
                System.out.println("Compositor : " + metadata.get("xmpDM:composer"));
                System.out.println("Gen : " + metadata.get("xmpDM:genre"));
                System.out.println("Album : " + metadata.get("xmpDM:album"));
                System.out.println("An : " + metadata.get("xmpDM:year"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            }
        }
    }

    public int gasesteFisier(String arg, String director) {
        File caleAux = new File(director);

        File[] fisiere = caleAux.listFiles();

        for (File f : fisiere) {
            if (f.isFile() && f.toString().contains(arg)) {
                System.out.println(f.getAbsolutePath());
                nrFisiere++;
            } else if (f.isDirectory()) {
                gasesteFisier(arg, f.getAbsolutePath());
            }
        }
        return nrFisiere;
    }

    public void adaugaFavorite(String arg) throws IOException {
        Favorite.favorite.add(new File(arg));
        System.out.println("Adaugat cu succes!");

        salveazaFavorite();
    }

    public void creeazaRaport() {
        for (int i = 0; i < fav.favorite.size(); i++) {
            System.out.println(fav.favorite.get(i));
        }
        RaportFreeMarker raport = null;
        try {
            raport = new RaportFreeMarker();
        } catch (ParseException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        }
        raport.creeaza();
        
        Desktop dk;
        dk = Desktop.getDesktop();
        try {
            dk.open(new File("D:\\MelodiiFavorite.html"));
        } catch (IOException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* 
        RaportPDF raportPdf = new RaportPDF();
              
         try {
         raportPdf.creeaza();
         } catch (DRException ex) {
         Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
         }
    
         */
    }
}
