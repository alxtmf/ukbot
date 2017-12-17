/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.bot.util;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

/**
 *
 * @author timofeevan
 */
public class KeyboardButtonBuilder {

    private final KeyboardButton button;
    
    public KeyboardButtonBuilder(){
        this.button = new KeyboardButton();
    }

    
    public KeyboardButtonBuilder setText(String text){
        button.setText(text); 
        return this;
    }
    
    public KeyboardButtonBuilder setRequestContact(Boolean b){
        button.setRequestContact(b); 
        return this;
    }
    
    public KeyboardButtonBuilder setRequestLocation(Boolean b){
        button.setRequestLocation(b); 
        return this;
    }
    
    public KeyboardButton build(){
        return button;
    }
}
