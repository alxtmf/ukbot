/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.manager;

import com.google.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.bot.infrastructure.IManager;
import ru.p03.bot.schema.Action;
import ru.p03.bot.util.ActionBuilder;
import ru.p03.bot.util.InlineKeyboardMarkupBuilder;
import ru.p03.bot.util.KeyboardButtonBuilder;
import ru.p03.bot.util.ReplyKeyboardMarkupBuilder;
import ru.p03.bot.util.SendMessageBuilder;
import ru.p03.bot.util.UpdateUtil;
import ru.p03.classifier.model.ClassifierRepository;
import ru.p03.ukbot.main.Actions;
import ru.p03.ukbot.main.Bot;
import ru.p03.ukbot.main.Main;
import ru.p03.ukbot.model.ClsCustomer;
import ru.p03.ukbot.model.repository.ClassifierRepositoryImpl;

/**
 *
 * @author altmf
 */
public class RegisterPhoneManager implements IManager, Observer<Update> {

    private IBot bot;
    private DocumentMarshalerAggregator marshalFactory;
    private ClassifierRepository classifierRepository;

    private Bot getBot() {
        return (Bot) bot;
    }

    @Override
    public void processCommand(Update update) {
    }

    @Override
    public void processCallbackQuery(Update update) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkupBuilder()
                .add(new KeyboardButtonBuilder()
                        .setText("Подтвердить номер телефона")
                        .setRequestContact(true)
                        .build())
                .build();
        SendMessage answerMessage = new SendMessageBuilder().html()
                .setChatId(update)
                .setText("Для того, чтобы зарегистрироваться подтвердите ваш номер телефона")
                .setReplyMarkup(replyKeyboardMarkup)
                .build();

        try {
            getBot().execute(answerMessage);
        } catch (TelegramApiException ex) {
            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
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

        Action action = new ActionBuilder(marshalFactory).buld(update);

        if (action == null || !Actions.REGISTER_PHONE.equals(action.getName())) {
            Contact contact = UpdateUtil.getContactFromUpdate(update);
            if (contact != null) {
                registerContact(update, contact);
            }
        } else {

            if (update.hasMessage() && update.getMessage().hasText()) {
                processCommand(update);
            } else if (update.hasInlineQuery()) {
                processInlineQuery(update);
            } else if (update.hasCallbackQuery()) {
                processCallbackQuery(update);
            }
        }
    }

    private void registerContact(Update update, Contact contact) {
        User user = UpdateUtil.getUserFromUpdate(update);

        try {
            ClsCustomer customer = ((ClassifierRepositoryImpl) getClassifierRepository())
                    .findPhone(contact.getPhoneNumber());
            if (customer != null) {
                customer.setIdTelegram(user.getId());
                getClassifierRepository().edit(customer);
                SendMessage answerMessage = new SendMessageBuilder(update).html()
                        .setText("Ваша учетная запись активирована, можете приступать к работе")
                        .setReplyMarkup(new ReplyKeyboardMarkupBuilder().buildRemove())
                        .build();

                getBot().execute(answerMessage);

                io.reactivex.Observable<Update> observableUpdate = Observable.create((oe) -> {
                    oe.onNext(update);
                    oe.onComplete();
                });

                MainMenuManager mainMenuManager = Main.getInjector().getInstance(MainMenuManager.class);
                observableUpdate.subscribe(mainMenuManager);

            } else {
                SendMessage answerMessage = new SendMessageBuilder(update).html()
                        .setText("Ваш номер телефона не известен и вы не можете быть зарегистрированы.")
                        .setReplyMarkup(new ReplyKeyboardMarkupBuilder().buildRemove())
                        .build();

                getBot().execute(answerMessage);

                answerMessage = new SendMessageBuilder(update).html()
                        .setText("Обратитесь к администратору")
                        .setReplyMarkup(new InlineKeyboardMarkupBuilder()
                                .add(Actions.feedbackMenuButton(marshalFactory))
                                .build())
                        .build();

                getBot().execute(answerMessage);
            }
        } catch (TelegramApiException ex) {
            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RegisterPhoneManager.class.getName()).log(Level.SEVERE, null, ex);
            try {
                getBot().execute(Actions.errorMessage(update));
            } catch (TelegramApiException ex1) {
                Logger.getLogger(RegisterPhoneManager.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
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
}
