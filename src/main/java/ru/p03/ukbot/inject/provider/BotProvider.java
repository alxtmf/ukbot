/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.inject.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.p03.common.util.QueriesEngine;
import ru.p03.ukbot.main.Bot;

/**
 *
 * @author altmf
 */
public class BotProvider implements Provider<Bot> {

    private static Bot bot;

    @Inject
    public BotProvider(HttpHost proxy) {
        if (bot == null) {
            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();
            if (proxy.getHostName().equals("0.0.0.0")) {
                bot = new Bot();
            } else {
                DefaultBotOptions instance = ApiContext.getInstance(DefaultBotOptions.class);
                RequestConfig rc = RequestConfig.custom().setProxy(proxy).build();
                instance.setRequestConfig(rc);
                bot = new Bot(instance);
            }

            try {
                botsApi.registerBot(bot);
                //AppEnv.getContext().getMenuManager().setBot(bot);
            } catch (TelegramApiRequestException ex) {
                Logger.getLogger(BotProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Bot get() {
        return bot;
    }

}
