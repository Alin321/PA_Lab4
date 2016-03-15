/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa_lab4;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.exception.DRException;
/**
 *
 * @author Alin
 */
public class RaportPDF {

    public void creeaza() throws DRException {
        FileOutputStream file=null;
        try {
            file = new FileOutputStream ("D:\\favorite.pdf");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RaportPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        report()
                .columns(col.column("Melodii preferate", "mel", type.stringType()))
                .title(cmp.text("Lista Cu Melodii Preferate"))
                .setDataSource(Favorite.favorite)
                .toPdf(file);

    }
}
