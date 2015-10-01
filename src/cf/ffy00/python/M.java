/*
 * Copyright 2015 FFY00
 * 
 * All Rights Reserved
 */

package cf.ffy00.python;

import java.io.File;
import java.util.logging.Level;
import static org.bukkit.Bukkit.getPluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.python.core.PyException;
import org.python.util.PythonInterpreter; 
/**
 *
 * @author FFY00 <FFY00 at ffy00.cf>
 */
public class M extends JavaPlugin {
    
    /*
    * DOCUMENTAÇÃO
    *
    * TODO
    * - Tarefa...
    *
    * LOG
    * - Main Criada...
    */
    
    // Vars Config
    PluginDescriptionFile pl;
    public static File pasta;
    File[] scripts = pasta.listFiles();

    // Metodo De Iniciar Config
    public void setupConfig(){
        pasta = getDataFolder();
        // Config
        if(!new File (pasta, "config.yml").exists()){
            saveDefaultConfig();
        }
    }
    
    // Jython
    public void jython(){
        try {
            PythonInterpreter j = new PythonInterpreter();
            for(int i = 0; i < scripts.length; i++){
                if(scripts[i].getName().substring(scripts.length - 3).equalsIgnoreCase(".py")){
                    j.execfile(scripts[i].getAbsolutePath());
                } else {
                    if(!scripts[i].getName().equals("config.yml")){
                        getLogger().log(Level.WARNING, "Ficheiro {0} encontrado mas não foi carregado pois não tremina em .py ...", new Object[]{scripts[i].getName()});
                    }
                }
            }
        } catch(PyException e){
            getLogger().log(Level.SEVERE, "Intrepetador de Python nao pode ser criado!");
            getLogger().log(Level.INFO, "Desativando :( ...");
            getPluginManager().disablePlugin(this);
        }
    }

    // Liga o Plugin
    @Override
    public void onEnable() {
        pl = getDescription();
        getLogger().log(Level.INFO, "Ativando {0} v{1} por FFY00!", new Object[]{pl.getName(), pl.getVersion()});
        setupConfig();
        jython();
    }

    // Desliga o Plugin
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Desativando {0} v{1} por FFY00 :(", new Object[]{pl.getName(), pl.getVersion()});
    }
    
    // Plugin INCIO
    
    // Plugin FIM
}