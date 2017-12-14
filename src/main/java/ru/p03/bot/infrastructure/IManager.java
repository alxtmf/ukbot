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
public interface IManager {
    public void processCommand(Update update);
    public void processCallbackQuery(Update update);
    public void processInlineQuery(Update update);
    public void setBot(IBot bot);
}
