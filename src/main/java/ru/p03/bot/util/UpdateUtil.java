/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.util;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

/**
 *
 * @author altmf
 */
public class UpdateUtil {

    public static User getUserFromUpdate(Update update) {
        return update.getMessage() != null ? update.getMessage().getFrom()
                : update.getCallbackQuery().getFrom();
    }

    public static Chat getChatFromUpdate(Update update) {
        return update.getMessage() != null ? update.getMessage().getChat()
                : update.getCallbackQuery().getMessage().getChat();
    }
    
    public static String getDataFromUpdate(Update update) {
        return update.getCallbackQuery() != null ? update.getCallbackQuery().getData()
                : null;
    }
    
    public static Contact getContactFromUpdate(Update update) {
        return update.getMessage() != null ? update.getMessage().getContact()
                : null;
    }
}
