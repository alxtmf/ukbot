/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.infrastructure;

import org.telegram.telegrambots.api.objects.Update;

/**
 *
 * @author altmf
 */
public interface IBot {
    public void onUpdateReceived(Update update);
}
