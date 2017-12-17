/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.main;

import com.google.inject.Singleton;
import io.reactivex.Observable;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.ukbot.manager.AddMeteringDeviceRecordManager;
import ru.p03.ukbot.manager.MainMenuManager;
import ru.p03.ukbot.manager.MyMeteringDeviceManager;
import ru.p03.ukbot.manager.RecordsHistoryManager;
import ru.p03.ukbot.manager.RegisterPhoneManager;

/**
 *
 * @author timofeevan
 */
@Singleton
public class Bot extends TelegramLongPollingBot implements IBot {

    private static final String TOKEN = "509858146:AAFvNkgB8RmN4mkpdtYrdAXh36tQplUQWqU";
    private static final String USERNAME = "sampleukbot";

    public Bot() {
    }

    public Bot(DefaultBotOptions options) {
        super(options);       
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {

        io.reactivex.Observable<Update> observableUpdate = Observable.create((oe) -> {
            oe.onNext(update);
            oe.onComplete();
        });

        MainMenuManager mainMenuManager = Main.getInjector().getInstance(MainMenuManager.class);
        AddMeteringDeviceRecordManager addMeteringDeviceRecordManager = Main.getInjector()
                .getInstance(AddMeteringDeviceRecordManager.class);
        MyMeteringDeviceManager myMeteringDeviceManager = Main.getInjector()
                .getInstance(MyMeteringDeviceManager.class);
        RecordsHistoryManager recordsHistoryManager = Main.getInjector()
                .getInstance(RecordsHistoryManager.class);
        RegisterPhoneManager registerPhoneManager = Main.getInjector()
                .getInstance(RegisterPhoneManager.class);
        
        observableUpdate.subscribe(mainMenuManager);
        observableUpdate.subscribe(addMeteringDeviceRecordManager);
        observableUpdate.subscribe(myMeteringDeviceManager);
        observableUpdate.subscribe(recordsHistoryManager);
        observableUpdate.subscribe(registerPhoneManager);

    }

}
