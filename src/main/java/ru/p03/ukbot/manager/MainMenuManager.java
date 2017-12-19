/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.manager;

import com.google.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updateshandlers.SentCallback;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.bot.infrastructure.IManager;
import ru.p03.bot.schema.Action;
import ru.p03.bot.util.ActionBuilder;
import ru.p03.bot.util.ChatInfoHolder;
import ru.p03.bot.util.InlineKeyboardButtonBuilder;
import ru.p03.bot.util.InlineKeyboardMarkupBuilder;
import ru.p03.classifier.model.ClassifierRepository;
import ru.p03.ukbot.main.Bot;
import ru.p03.ukbot.main.Actions;
import ru.p03.bot.util.SendMessageBuilder;
import ru.p03.bot.util.UpdateUtil;

/**
 *
 * @author altmf
 */
public class MainMenuManager implements IManager, Observer<Update> {

    private IBot bot;
    private DocumentMarshalerAggregator marshalFactory;
    private ClassifierRepository classifierRepository;
    private ChatInfoHolder chatInfoHolder;

    private Bot getBot() {
        return (Bot) bot;
    }

    private void mainMenu(Update update) {
        SendMessage answerMessage = new SendMessageBuilder().html()
                .setReplyMarkup(keyboard(update))
                .setChatId(update)
                .setText("Нажмите на кнопку для начала работы")
                .build();

        try {
            getBot().executeAsync(answerMessage, new SentCallback() {
                @Override
                public void onResult(BotApiMethod method, Serializable response) {
                    Message answerMessage = (Message) response;
                    List<Message> trimMessages = getChatInfoHolder().trimMessages(answerMessage.getChat());
                    getChatInfoHolder().pushMessage(answerMessage);
                    trimMessages.stream().map(UpdateUtil::deleteMessage).forEach((dm) -> {
                        try {
                            getBot().execute(dm);
                        } catch (TelegramApiException ex) {
                            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    );
                    //System.out.print("1");
                }

                @Override
                public void onError(BotApiMethod method, TelegramApiRequestException apiException) {
                    ///System.out.print("1");
                }

                @Override
                public void onException(BotApiMethod method, Exception exception) {
                    //System.out.print("1");
                }

            });
        } catch (TelegramApiException ex) {
            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processCommand(Update update) {
        mainMenu(update);
    }

    @Override
    public void processCallbackQuery(Update update) {
        Action action = new ActionBuilder(marshalFactory).buld(update);
        if (action != null && Actions.OPEN_MAIN.equals(action.getName())) {
            mainMenu(update);
        }
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
        return new InlineKeyboardMarkupBuilder()
                .add(new InlineKeyboardButtonBuilder()
                        .setText("Подать показания")
                        .setCallbackData(new ActionBuilder(getMarshalFactory())
                                .setName(Actions.SET_METERING_DEVICES_RECIRD)
                                .asString())
                        .build())
                .add(new InlineKeyboardButtonBuilder()
                        .setText("Мои приборы учета")
                        .setCallbackData(new ActionBuilder(getMarshalFactory())
                                .setName(Actions.MY_METERING_DEVICES)
                                .asString())
                        .build())
                .add(new InlineKeyboardButtonBuilder()
                        .setText("История")
                        .setCallbackData(new ActionBuilder(getMarshalFactory())
                                .setName(Actions.HISTORY)
                                .asString())
                        .build())
                .add(new InlineKeyboardButtonBuilder()
                        .setText("Зарегистрироваться")
                        .setCallbackData(new ActionBuilder(getMarshalFactory())
                                .setName(Actions.REGISTER_PHONE)
                                .asString())
                        .build())
                .build();
    }

    @Override
    public void onError(Throwable e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onComplete() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DocumentMarshalerAggregator getMarshalFactory() {
        return marshalFactory;
    }

    @Inject
    public void setMarshalFactory(DocumentMarshalerAggregator marshalFactory) {
        this.marshalFactory = marshalFactory;
    }

    public ClassifierRepository getClassifierRepository() {
        return classifierRepository;
    }

    @Inject
    public void setClassifierRepository(ClassifierRepository classifierRepository) {
        this.classifierRepository = classifierRepository;
    }

    public ChatInfoHolder getChatInfoHolder() {
        return chatInfoHolder;
    }

    @Inject
    public void setChatInfoHolder(ChatInfoHolder chatInfoHolder) {
        this.chatInfoHolder = chatInfoHolder;
    }

}
