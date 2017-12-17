/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 *
 * @author altmf
 */
public class InlineKeyboardMarkupBuilder {

    final private InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
    final private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    
    public InlineKeyboardMarkupBuilder(){
        
    }
    
    public InlineKeyboardMarkupBuilder(List<InlineKeyboardButton> buttons){
        add(buttons);
    }
    
    public InlineKeyboardMarkupBuilder(InlineKeyboardButton... buttons){
        add(buttons);
    }
    
    public InlineKeyboardMarkupBuilder(List<InlineKeyboardButton>... buttons){
        add(buttons);
    }

    public InlineKeyboardMarkupBuilder add(List<InlineKeyboardButton> buttons) {
        keyboard.add(buttons);
        return this;
    }

    public InlineKeyboardMarkupBuilder add(InlineKeyboardButton... buttons) {
        keyboard.add(Arrays.asList(buttons));
        return this;
    }

    public InlineKeyboardMarkupBuilder add(List<InlineKeyboardButton>... rows) {
        Arrays.asList(rows).stream()
                .forEach(r -> keyboard.add(r));
        return this;
    }

    public InlineKeyboardMarkup build() {
        markup.setKeyboard(keyboard);
        return markup;
    }

}
