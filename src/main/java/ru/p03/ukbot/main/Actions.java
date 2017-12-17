/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.main;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.util.ActionBuilder;
import ru.p03.bot.util.InlineKeyboardButtonBuilder;
import ru.p03.bot.util.SendMessageBuilder;

/**
 *
 * @author altmf
 */
public class Actions {

    public static final String HISTORY = "HST";
    public static final String MY_METERING_DEVICES = "MMD";
    public static final String SET_METERING_DEVICES_RECIRD = "MDR";
    public static final String OPEN_MAIN = "OM";

    public static InlineKeyboardButton mainMenuButton(DocumentMarshalerAggregator marshalFactory) {
        return new InlineKeyboardButtonBuilder()
                .setText("Главное меню")
                .setCallbackData(new ActionBuilder(marshalFactory)
                        .setName(Actions.OPEN_MAIN)
                        .asString())
                .build();
    }

    public static SendMessage errorMessage(Update update) {
        return new SendMessageBuilder().html()
                .setChatId(update)
                .setText("Ой, что-то пошло не так, попробуйте еще раз или перейдите в главное меню")
                .build();
    }
}
