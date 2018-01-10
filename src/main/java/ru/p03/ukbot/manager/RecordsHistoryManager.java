/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.ukbot.manager;

import com.google.inject.Inject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.bot.infrastructure.IManager;
import ru.p03.bot.schema.Action;
import ru.p03.bot.util.ActionBuilder;
import ru.p03.bot.util.ChatInfoHolder;
import ru.p03.bot.util.SendMessageBuilder;
import ru.p03.classifier.model.ClassifierRepository;
import ru.p03.ukbot.main.Actions;
import ru.p03.ukbot.main.Bot;
import ru.p03.ukbot.model.ClsApartment;
import ru.p03.ukbot.model.ClsCustomer;
import ru.p03.ukbot.model.RegApartmentMeteringSender;
import ru.p03.ukbot.model.RegMeteringDeviceRecords;

/**
 *
 * @author altmf
 */
public class RecordsHistoryManager implements IManager, Observer<Update> {

    private IBot bot;
    private DocumentMarshalerAggregator marshalFactory;
    private ClassifierRepository classifierRepository;
    private ChatInfoHolder chatInfoHolder;

    private Bot getBot() {
        return (Bot) bot;
    }

    @Override
    public void processCommand(Update update) {
    }

    @Override
    public void processCallbackQuery(Update update) {
        try {
            SendMessage answerMessage = null;
            Action action = new ActionBuilder(marshalFactory).buld(update);
            if (action != null && Actions.HISTORY.equals(action.getName())) {

                ClsCustomer customer = getChatInfoHolder().getCustomer(update);

                Collection<RegApartmentMeteringSender> senders = customer.getRegApartmentMeteringSenderCollection();

                if (senders.isEmpty()) {
                    answerMessage = new SendMessageBuilder(update).html("Отсустсвуют данные о ваших квартирах")
                            .build();
                    getBot().execute(answerMessage);
                    return;
                }

                Collection<RegMeteringDeviceRecords> records = customer.getRegMeteringDeviceRecordsCollection();
                answerMessage = new SendMessageBuilder(update).html(!records.isEmpty()
                        ? "<b>История показаний:</b>" : "Отсуствует история показаний")
                        .build();
                getBot().execute(answerMessage);

                if (!records.isEmpty()) {
                    for (RegApartmentMeteringSender sender : senders) {
                        ClsApartment apartment = sender.getIdApartment();
                        String text = apartment.toString() + ": \n";

                        int count = 1;
                        for (RegMeteringDeviceRecords record : records) {
                            if (apartment.equals(record.getIdApartmentMeteringDevice().getIdApartment())) {
                                text += (count + ". " + record.toString() + "\n");
                                ++count;
                            }
                        }
                        if (count > 1) {
                            answerMessage = new SendMessageBuilder(update).html()
                                    .setText(text)
                                    .build();
                            getBot().execute(answerMessage);
                        }
                    }
                }
            }

        } catch (TelegramApiException ex) {
            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RecordsHistoryManager.class.getName()).log(Level.SEVERE, null, ex);
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
        if (action == null || !Actions.HISTORY.equals(action.getName())) {
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            processCommand(update);
        } else if (update.hasInlineQuery()) {
            processInlineQuery(update);
        } else if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
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

    public ChatInfoHolder getChatInfoHolder() {
        return chatInfoHolder;
    }

    @Inject
    public void setChatInfoHolder(ChatInfoHolder chatInfoHolder) {
        this.chatInfoHolder = chatInfoHolder;
    }
}
