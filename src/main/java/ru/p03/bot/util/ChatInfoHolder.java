/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 *
 * @author altmf
 */
public class ChatInfoHolder {

    private Map<Long, List<Message>> userMessages = new HashMap<>();

    public void pushMessage(@Nonnull Update update) {
        Message message = update.getMessage();
        if (message != null) {
            Chat chat = UpdateUtil.getChatFromUpdate(update);
            push(chat, message);
//            if (userMessages.containsKey(chat.getId())) {
//                userMessages.get(chat).add(message);
//            } else {
//                userMessages.put(chat.getId(), new ArrayList<>(Arrays.asList(message)));
//            }
        }
    }

    public void pushMessage(@Nonnull Message message) {
        Chat chat = message.getChat();
        push(chat, message);
//        if (userMessages.containsKey(chat.getId())) {
//            userMessages.get(chat.getId()).add(message);
//        } else {
//            userMessages.put(chat.getId(), new ArrayList<>(Arrays.asList(message)));
//        }
    }
    
    private void push(Chat chat, Message message){
        if (userMessages.containsKey(chat.getId())) {
            userMessages.get(chat.getId()).add(message);
        } else {
            userMessages.put(chat.getId(), new ArrayList<>(Arrays.asList(message)));
        }
    }

    public List<Message> trimMessages(@Nonnull Chat chat) {
        List<Message> get = get(chat);
        List<Message> trimed = new ArrayList<>(get.size());
        trimed.addAll(get);
        get.clear();
        return trimed;
    }

    private List<Message> get(Chat chat) {
        return userMessages.get(chat.getId()) == null ? new ArrayList<>() : userMessages.get(chat.getId());
    }
}
