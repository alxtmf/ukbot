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
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.p03.bot.document.spi.DocumentMarshalerAggregator;
import ru.p03.bot.infrastructure.IBot;
import ru.p03.bot.infrastructure.IManager;
import ru.p03.bot.schema.Action;
import ru.p03.bot.util.ActionBuilder;
import ru.p03.bot.util.ChatInfoHolder;
import ru.p03.bot.util.InlineKeyboardButtonBuilder;
import ru.p03.bot.util.InlineKeyboardMarkupBuilder;
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
public class SelectMeteringDeviceRecordManager implements IManager, Observer<Update> {

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
//        SendMessage answerMessage = null;
//        try {
//            Action action = new ActionBuilder(marshalFactory).buld(update);
//            if (action != null && Actions.SET_METERING_DEVICES_RECORD.equals(action.getName())) {
//                ClsCustomer customer = getChatInfoHolder().getCustomer(update);
//                List<ClsApartment> apartments = customer.getApartments();
//
//                if (apartments.isEmpty()) {
//                    answerMessage = new SendMessageBuilder(update).html("Отсустсвуют данные о ваших квартирах")
//                            .build();
//                } else {
//                    answerMessage = new SendMessageBuilder(update).b("Выберите квартиру")
//                            .setReplyMarkup(keyboardApartmentButton(apartments))
//                            .build();
//                }
//
//                getBot().execute(answerMessage);
//            }
//        } catch (TelegramApiException ex) {
//            Logger.getLogger(MainMenuManager.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(SelectMeteringDeviceRecordManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
        if (action == null || !Actions.SET_METERING_DEVICES_RECORD.equals(action.getName())) {
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

    private InlineKeyboardButton apartmentButton(ClsApartment apartment) {
        return new InlineKeyboardButtonBuilder()
                .setText(apartment.toString())
                .setCallbackData(new ActionBuilder(getMarshalFactory())
                        .setName(Actions.SELECT_APARTMENT_FOR_SET_METERING_DEVICES_RECORD)
                        .setValue(apartment.getId())
                        .asString())
                .build();
    }

    private InlineKeyboardMarkup keyboardApartmentButton(List<ClsApartment> apartments) {
        List<InlineKeyboardButton> ikblist = apartments.stream()
                .map(this::apartmentButton)
                .collect(Collectors.toList());
        return new InlineKeyboardMarkupBuilder(ikblist)
                .build();
    }
}
