/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.main;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updateshandlers.SentCallback;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.bot.util.ChatInfoHolder;
import ru.p03.bot.util.UpdateUtil;

/**
 *
 * @author altmf
 */
public class SentCallbackMessageRemover implements SentCallback {
    
    private IBot bot;
    private ChatInfoHolder chatInfoHolder;
    
    public SentCallbackMessageRemover(IBot bot, ChatInfoHolder chatInfoHolder){
        this.bot = bot;
        this.chatInfoHolder = chatInfoHolder;
    }

    private Bot getBot() {
        return (Bot) bot;
    }
    
    public ChatInfoHolder getChatInfoHolder() {
        return chatInfoHolder;
    }

    @Override
    public void onResult(BotApiMethod method, Serializable response) {
        Message answerMessage = (Message) response;
        List<Message> trimMessages = getChatInfoHolder().trimMessages(answerMessage.getChat());
        try {
            if (getBot().getMe().getId().equals(answerMessage.getFrom().getId())) {
                getChatInfoHolder().pushMessage(answerMessage);
            }
        } catch (TelegramApiException ex) {
            Logger.getLogger(SentCallbackMessageRemover.class.getName()).log(Level.SEVERE, null, ex);
        }
        trimMessages.stream().map(UpdateUtil::deleteMessage).forEach((dm) -> {
            try {
                getBot().execute(dm);
            } catch (TelegramApiException ex) {
                Logger.getLogger(SentCallbackMessageRemover.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );
    }

    @Override
    public void onError(BotApiMethod method, TelegramApiRequestException apiException) {

    }

    @Override
    public void onException(BotApiMethod method, Exception exception) {

    }

}
