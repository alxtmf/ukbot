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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import ru.p03.ukbot.model.RegApartmentMeteringDevice;
import ru.p03.ukbot.model.RegApartmentMeteringSender;
import ru.p03.ukbot.model.RegMeteringDeviceRecords;

/**
 *
 * @author altmf
 */
public class MyMeteringDeviceManager implements IManager, Observer<Update> {

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
            Action action = new ActionBuilder(marshalFactory).buld(update);
            if (action != null && Actions.MY_METERING_DEVICES.equals(action.getName())) {
                ClsCustomer customer = getChatInfoHolder().getCustomer(update);
                Collection<RegApartmentMeteringSender> senders = customer.getRegApartmentMeteringSenderCollection();

                SendMessage answerMessage = new SendMessageBuilder(update).html()
                        .setText(!senders.isEmpty() ? "<b>Мои приборы учета</b>"
                                : "Отсустсвуют данные о ваших квартирах")
                        .build();
                getBot().execute(answerMessage);

                for (RegApartmentMeteringSender sender : senders) {
                    ClsApartment apartment = sender.getIdApartment();
                    Collection<RegApartmentMeteringDevice> devices = apartment.getRegApartmentMeteringDeviceCollection();
                    String text = apartment.toString() + " "
                            + (!devices.isEmpty() ? ". Приборы учета: \n"
                            : "Отсустсвуют данные о приборах учета");
                    int count = 1;
                    for (RegApartmentMeteringDevice device : devices) {
                        text += (count + ". " + device.toShortString() + "\n");
                        ++count;
                    }
                    answerMessage = new SendMessageBuilder(update).html()
                            .setText(text)
                            .build();
                    getBot().execute(answerMessage);
                }
            }

        } catch (TelegramApiException ex) {
            Logger.getLogger(MyMeteringDeviceManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MyMeteringDeviceManager.class.getName()).log(Level.SEVERE, null, ex);
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
        if (action == null || !Actions.MY_METERING_DEVICES.equals(action.getName())) {
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
