/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.p03.ukbot.manager.MainMenuManager;

/**
 *
 * @author timofeevan
 */
public class Main {

    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }
    
    private static Injector injector;

    public static void main(String[] args) {

        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, "Бот. Начало работы");

        if (args.length > 0) {
            //AppEnv.getContext(args[0].replaceFirst("-", ""));//.init(args[0]);
        } else {
            //AppEnv.getContext();//.init();
        }
        
        injector = Guice.createInjector(AppEnv.getContext()); 
        

//        ApiContextInitializer.init();
//        TelegramBotsApi botsApi = new TelegramBotsApi();

        Runnable r = () -> {       
            Bot bot = getInjector().getInstance(Bot.class);
            
            //bot.setInjector(getInjector());
//            Bot bot = null;
//            HttpHost proxy = null;
//            //HttpHost proxy = AppEnv.getContext().getProxyIfAbsetnt();
//            if (proxy == null) {
//                bot = new Bot();
//            } else {
//                DefaultBotOptions instance = ApiContext.getInstance(DefaultBotOptions.class);
//                RequestConfig rc = RequestConfig.custom().setProxy(proxy).build();
//                instance.setRequestConfig(rc);
//                bot = new Bot(instance);
//            }
//
//            try {
//                botsApi.registerBot(bot);
//                //AppEnv.getContext().getMenuManager().setBot(bot);
//            } catch (TelegramApiRequestException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
        };

        r.run();

        while (true) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "bot!");
            try {
                Thread.sleep(80000L);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * @return the injector
     */
    public static Injector getInjector() {
        return injector;
    }
}
