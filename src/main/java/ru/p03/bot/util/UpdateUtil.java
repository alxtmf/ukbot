/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

/**
 *
 * @author altmf
 */
public class UpdateUtil {

    @Nonnull
    public static User getUserFromUpdate(@Nonnull Update update) {
        return update.getMessage() != null ? update.getMessage().getFrom()
                : update.getCallbackQuery().getFrom();
    }

    @Nonnull
    public static Chat getChatFromUpdate(@Nonnull Update update) {
        return update.getMessage() != null ? update.getMessage().getChat()
                : update.getCallbackQuery().getMessage().getChat();
    }
    
    @Nullable
    public static String getDataFromUpdate(@Nonnull Update update) {
        return update.getCallbackQuery() != null ? update.getCallbackQuery().getData()
                : null;
    }
    
   
    @Nullable
    public static Contact getContactFromUpdate(@Nonnull Update update) {
        return update.getMessage() != null ? update.getMessage().getContact()
                : null;
    }
    
    @Nonnull
    public static DeleteMessage deleteMessage(@Nonnull Update update) throws Exception{
        Message message = update.getMessage();
        if (message == null){
            throw new Exception("No delete null message!");
        }
        DeleteMessage deleteMessage = new DeleteMessage(getChatFromUpdate(update).getId(),
                message.getMessageId());
        return deleteMessage;
    }
    
    @Nonnull
    public static DeleteMessage deleteMessage(@Nonnull Message message){
        DeleteMessage deleteMessage = new DeleteMessage(message.getChatId(), message.getMessageId());
        return deleteMessage;
    }
}
