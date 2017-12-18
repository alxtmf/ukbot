/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.util;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

/**
 *
 * @author altmf
 */
public class SendMessageBuilder {
    
    private static final String PARSE_MODE_HTML = "HTML";
    private final SendMessage sendMessage = new SendMessage();
    
    public SendMessageBuilder(){
        
    }
    
    public SendMessageBuilder(Update update){
        setChatId(update);
    }
    
    public SendMessageBuilder html(){
        sendMessage.setParseMode(PARSE_MODE_HTML);
        return this;
    }
    
    public SendMessageBuilder setChatId(Update update){
        sendMessage.setChatId(UpdateUtil.getChatFromUpdate(update).getId());
        return this;
    }
    
    public SendMessageBuilder setReplyMarkup(ReplyKeyboard replyKeyboard){
        sendMessage.setReplyMarkup(replyKeyboard);
        return this;
    }
    
    public SendMessageBuilder setText(String text){
        sendMessage.setText(text);
        return this;
    }
    
    public SendMessage build(){
        return sendMessage;
    }
}
