/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.manager;

import com.google.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.bot.infrastructure.IManager;
import ru.p03.bot.util.ActionBuilder;
import ru.p03.bot.util.InlineKeyboardButtonBuilder;
import ru.p03.bot.util.UpdateUtil;
import ru.p03.classifier.model.ClassifierRepository;
import ru.p03.ukbot.main.Bot;
import ru.p03.ukbot.main.Actions;
import ru.p03.bot.util.SendMessageBuilder;

/**
 *
 * @author altmf
 */
public class MainMenuManager implements IManager, Observer<Update> {

    private IBot bot;
    private DocumentMarshalerAggregator marshalFactory;
    private ClassifierRepository classifierRepository;

    private Bot getBot() {
        return (Bot) bot;
    }

    @Override
    public void processCommand(Update update) {
        SendMessage answerMessage = new SendMessageBuilder().html()
                .setReplyMarkup(keyboard(update))
                .setChatId(update)
                .setText("Нажмите на кнопку для начала работы")
                .build();

        try {
            getBot().execute(answerMessage);
        } catch (TelegramApiException ex) {
            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processCallbackQuery(Update update) {
    }

    @Override
    public void processInlineQuery(Update update) {
    }

    @Override
    @Inject
    public void setBot(IBot bot) {
        this.bot = bot;
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            processCommand(update);
        } else if (update.hasInlineQuery()) {
            processInlineQuery(update);
        } else if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        }
    }

    private InlineKeyboardMarkup keyboard(Update update) {
        final InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Arrays.asList(new InlineKeyboardButtonBuilder()
                .setText("Подать показания")
                .setCallbackData(new ActionBuilder(getMarshalFactory())
                        .setName(Actions.SET_METERING_DEVICES_RECIRD)
                        .asString())
                .build()));
        keyboard.add(Arrays.asList(new InlineKeyboardButtonBuilder()
                .setText("Мои приборы учета")
                .setCallbackData(new ActionBuilder(getMarshalFactory())
                        .setName(Actions.MY_METERING_DEVICES)
                        .asString())
                .build()));
        keyboard.add(Arrays.asList(new InlineKeyboardButtonBuilder()
                .setText("История")
                .setCallbackData(new ActionBuilder(getMarshalFactory())
                        .setName(Actions.HISTORY)
                        .asString())
                .build()//,
        //Actions.mainMenuButton(getMarshalFactory())
        ));
        markup.setKeyboard(keyboard);
        return markup;
    }

    @Override
    public void onError(Throwable e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onComplete() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the marshalFactory
     */
    public DocumentMarshalerAggregator getMarshalFactory() {
        return marshalFactory;
    }

    /**
     * @param marshalFactory the marshalFactory to set
     */
    @Inject
    public void setMarshalFactory(DocumentMarshalerAggregator marshalFactory) {
        this.marshalFactory = marshalFactory;
    }

    /**
     * @return the classifierRepository
     */
    public ClassifierRepository getClassifierRepository() {
        return classifierRepository;
    }

    /**
     * @param classifierRepository the classifierRepository to set
     */
    @Inject
    public void setClassifierRepository(ClassifierRepository classifierRepository) {
        this.classifierRepository = classifierRepository;
    }

}
