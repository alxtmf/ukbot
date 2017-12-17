/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

/**
 *
 * @author altmf
 */
public class ReplyKeyboardMarkupBuilder {

    final private ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    final private List<KeyboardRow> keyboardRows = new ArrayList<>();
    
    public ReplyKeyboardMarkupBuilder(){
        
    }
    
    public ReplyKeyboardMarkupBuilder(List<KeyboardButton> buttons){
        KeyboardRow row = new KeyboardRow();
        row.addAll(buttons);
        add(row);
    }
    
    public ReplyKeyboardMarkupBuilder(KeyboardButton... buttons){
        KeyboardRow row = new KeyboardRow();
        row.addAll(Arrays.asList(buttons));
        add(row);
    }

    
    public ReplyKeyboardMarkupBuilder (KeyboardRow row) {
        keyboardRows.add(row); 
    }

    public ReplyKeyboardMarkupBuilder (KeyboardRow... rows) {
        keyboardRows.addAll(Arrays.asList(rows));
    }
    
    public ReplyKeyboardMarkupBuilder add(KeyboardButton button){
        KeyboardRow row = new KeyboardRow();
        row.add(button);
        add(row);
        return this;
    }

    public ReplyKeyboardMarkupBuilder add(KeyboardRow row) {
        keyboardRows.add(row); 
        return this;
    }

    public ReplyKeyboardMarkupBuilder add(KeyboardRow... rows) {
        keyboardRows.addAll(Arrays.asList(rows));
        return this;
    }
    
    public ReplyKeyboardMarkupBuilder add(List<KeyboardButton> buttons){
        KeyboardRow row = new KeyboardRow();
        row.addAll(buttons);
        add(row);
        return this;
    }
    
    public ReplyKeyboardMarkupBuilder add(KeyboardButton... buttons){
        KeyboardRow row = new KeyboardRow();
        row.addAll(Arrays.asList(buttons));
        add(row);
        return this;
    }


    public ReplyKeyboardMarkup build() {
        markup.setKeyboard(keyboardRows);
        return markup;
    }
    
    public ReplyKeyboardRemove buildRemove(){
        ReplyKeyboardRemove rkr = new ReplyKeyboardRemove();
        return rkr;
    }

}
