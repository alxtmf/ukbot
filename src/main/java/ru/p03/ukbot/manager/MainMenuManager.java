/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.manager;

import com.google.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.bot.infrastructure.IManager;
import ru.p03.bot.util.UpdateUtil;
import ru.p03.ukbot.main.Bot;

/**
 *
 * @author altmf
 */
public class MainMenuManager implements IManager, Observer<Update> {

    private IBot bot;

    private Bot getBot() {
        return (Bot) bot;
    }

    @Override
    public void processCommand(Update update) {
    }

    @Override
    public void processCallbackQuery(Update update) {
    }

    @Override
    public void processInlineQuery(Update update) {
    }

    @Override
    @Inject
    public void setBot(IBot bot) {
        this.bot = bot;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Update update) {

        SendMessage answerMessage = new SendMessage();
        answerMessage.setParseMode("HTML");
        answerMessage.setChatId(UpdateUtil.getChatFromUpdate(update).getId());
        answerMessage.setText("Hello");

        try {
            getBot().execute(answerMessage);
        } catch (TelegramApiException ex) {
            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            processCommand(update);
        } else if (update.hasInlineQuery()) {
            processInlineQuery(update);
        } else if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        }
    }

    @Override
    public void onError(Throwable e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onComplete() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
