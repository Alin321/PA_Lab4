/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa_lab4;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab4_exceptii.DirectorInexistentException;
import lab4_exceptii.VolumInexistentException;

/**
 *
 * @author Alin
 */
public class PA_Lab4 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws lab4_exceptii.DirectorInexistentException
     * @throws lab4_exceptii.VolumInexistentException
     */
    public static void main(String[] args)
            throws IOException, DirectorInexistentException, VolumInexistentException {
        
        Aplicatie apl = new Aplicatie();        
        apl.start();
    }
}


