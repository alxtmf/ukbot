/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.main;

import ru.p03.ukbot.inject.provider.BotProvider;
import ru.p03.ukbot.inject.provider.QueriesEngineProvider;
import ru.p03.common.util.QueriesEngineConfig;
import com.google.inject.AbstractModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.p03.common.util.QueriesEngine;
import org.apache.http.HttpHost;
import ru.p03.bot.document.spi.DocumentMarshalFactory;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.classifier.model.ClassifierRepository;
import ru.p03.ukbot.manager.MainMenuManager;
import ru.p03.ukbot.model.repository.ClassifierRepositoryImpl;
import ru.p03.ukbot.inject.annotation.*;
import ru.p03.ukbot.model.repository.*;
import ru.p03.classifier.model.RegRepository;
import ru.p03.ukbot.inject.provider.DocumentMarshalerAggregatorProvider;
import ru.p03.bot.document.spi.DocumentMarshalerAggregatorConfig;
import ru.p03.bot.document.spi.DocumentMarshaller;
import ru.p03.bot.document.spi.JsonDocumentMarshallerImpl;
import ru.p03.bot.schema.Action;
import ru.p03.ukbot.manager.RegisterPhoneManager;
import ru.p03.ukbot.model.ClsDocType;

/**
 *
 * @author altmf
 */
public class AppEnv extends AbstractModule {
    
    public static String ROOT_PATH = "ROOT_PATH";
    public static String PROXY_HOST = "PROXY_HOST";
    public static String PROXY_PORT = "PROXY_PORT";
    public static String PROXY_USE = "PROXY_USE";
    
    private Map environments = new HashMap();
    
    private static AppEnv appEnv = null;
    
    public static AppEnv getContext(){
        if (appEnv == null){
            appEnv = new AppEnv();
            appEnv.init();
        }
        return appEnv;
    }
        

    private QueriesEngineConfig getQueriesEngineConfig(){
        
        String unitName = "UK";
        String dbUrl = "jdbc:h2:" + getRootPath() + File.separator + "db" + 
                File.separator + "UK;AUTO_SERVER=TRUE"; //<property name=\"javax.persistence.jdbc.url\" value=\
        Map hm = new HashMap();
        
        QueriesEngineConfig conf = new QueriesEngineConfig(unitName, dbUrl, hm);
        return conf;
    }
    
    private DocumentMarshalerAggregatorConfig getDocumentMarshalerAggregatorConfig(){
        DocumentMarshaller mrsh = new JsonDocumentMarshallerImpl(Action.class, ClsDocType.ACTION);
        DocumentMarshalerAggregatorConfig config = new DocumentMarshalerAggregatorConfig(ClsDocType.types(),
                Arrays.asList(mrsh));
        return config;
    }

    @Override
    protected void configure() {
        bind(QueriesEngine.class).toProvider(QueriesEngineProvider.class);
        bind(QueriesEngineConfig.class).toInstance(getQueriesEngineConfig());
        bind(ClassifierRepository.class).to(ClassifierRepositoryImpl.class);
        
        bind(IBot.class).toProvider(BotProvider.class);
        //bind(IBot.class).to(Bot.class);
        bind(HttpHost.class).toInstance(getProxyIfAbsetnt());
        bind(MainMenuManager.class).toInstance(getMainMenuManager());
        
        bind(RegRepository.class).annotatedWith(RegApartmentMeteringDeviceRepositoryAnnotation.class)
            .to(RegApartmentMeteringDeviceRepository.class);
        bind(RegRepository.class).annotatedWith(RegApartmentMeteringSenderRepositoryAnnotation.class)
            .to(RegApartmentMeteringSenderRepository.class);
        bind(RegRepository.class).annotatedWith(RegMeteringDeviceRecordsRepositoryAnnotation.class)
            .to(RegMeteringDeviceRecordsRepository.class);
        
        bind(DocumentMarshalerAggregator.class).toProvider(DocumentMarshalerAggregatorProvider.class);
        bind(DocumentMarshalerAggregatorConfig.class).toInstance(getDocumentMarshalerAggregatorConfig());
    }   
    
    public MainMenuManager getMainMenuManager(){
        return new MainMenuManager();
    }
        
    public String getRootPath() {
        return (String) environments.get(ROOT_PATH);
    }

    public HttpHost getProxyIfAbsetnt() {
        if (environments.get(PROXY_HOST) != null && environments.get(PROXY_PORT) != null
                && environments.get(PROXY_USE) != null && "true".equalsIgnoreCase((String) environments.get(PROXY_USE))) {
            try {
                HttpHost proxy = new HttpHost((String) environments.get(PROXY_HOST), Integer.valueOf((String) environments.get(PROXY_PORT)));
                return proxy;
            } catch (NumberFormatException ex) {
                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new HttpHost("0.0.0.0");
    }
    
    public void init() {
        initProperties(null);
    }
    
    private Properties initProperties(String propFileName) {
        Properties properties = null;
        File fProp = null;
        String propDir = null;
        if (propFileName == null) {
            propFileName = "conf.properties";
            propDir = "conf";

            fProp = new File(propDir + File.separator + propFileName);
        } else {
            fProp = new File(propFileName);
        }

        if (!fProp.exists()) {
            Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "not exists: " + fProp.getAbsolutePath());
            fProp = new File(".." + "/" + propDir + "/" + propFileName);
            if (!fProp.exists()) {
                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "not exists: " + fProp.getAbsolutePath());
                fProp = new File("...." + "/" + propDir + "/" + propFileName);
                if (!fProp.exists()) {
                    Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "not exists: " + fProp.getAbsolutePath());
                    fProp = new File(propFileName);
                    Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, "default: " + fProp.getAbsolutePath());
                }
            }
        }
        if (fProp.exists()) {
            try (InputStream fis = new FileInputStream(fProp);) {
                properties = new Properties();
                properties.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, entry.getKey() + " = " + entry.getValue());
                }
                environments.putAll(properties);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AppEnv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return properties;
    }

}
