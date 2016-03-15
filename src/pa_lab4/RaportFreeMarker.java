/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa_lab4;
import freemarker.core.ParseException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alin
 */
public class RaportFreeMarker {
    public Configuration cfg;
    public Template template;
    public Map<String, Object> data;
    public List<String> fav;
    
    public RaportFreeMarker() throws MalformedTemplateNameException, ParseException, IOException{
        cfg = new Configuration();
        template = cfg.getTemplate("src/favorite.ftl");
        data = new HashMap<>();
        fav = new ArrayList<>();
    }
    
    public void creeaza(){
        for(int i=0;i<Favorite.favorite.size();i++){
            fav.add(Favorite.favorite.get(i).toString());
        }
        
        data.put("favorite", fav);
        
        Writer file=null;
        try {
            file = new FileWriter (new File("D:\\MelodiiFavorite.html"));
        } catch (IOException ex) {
            Logger.getLogger(RaportFreeMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            template.process(data, file);
        } catch (TemplateException ex) {
            Logger.getLogger(RaportFreeMarker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RaportFreeMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            file.flush();
        } catch (IOException ex) {
            Logger.getLogger(RaportFreeMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(RaportFreeMarker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
