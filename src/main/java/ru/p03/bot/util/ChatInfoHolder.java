/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.p03.bot.util;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.p03.classifier.model.ClassifierRepository;
import ru.p03.ukbot.model.ClsCustomer;
import ru.p03.ukbot.model.repository.ClassifierRepositoryImpl;

/**
 *
 * @author altmf
 */
public class ChatInfoHolder {
    
    private ClassifierRepository classifierRepository;

    private Map<Long, List<Message>> userMessages = new HashMap<>();
    private Map<Integer, ClsCustomer> customers = new HashMap<>();
    
    public boolean containsCustomer(@Nonnull Update update){
        Integer id = UpdateUtil.getUserFromUpdate(update).getId();
        return customers.containsKey(id);
    }
    
    public void pushCustomer(@Nonnull Update update) throws Exception{
        Integer id = UpdateUtil.getUserFromUpdate(update).getId();
        ClsCustomer customer = ((ClassifierRepositoryImpl) classifierRepository).findTelegramId(id);
        if (customer == null){
            throw new Exception("Non register user!");
        }
        customers.put(id, customer);
    }
    
    @Nonnull
    public ClsCustomer getCustomer(@Nonnull Update update) throws Exception{
        Integer id = UpdateUtil.getUserFromUpdate(update).getId();
        ClsCustomer customer = customers.get(id);
        if (customer == null){
            customer = ((ClassifierRepositoryImpl) classifierRepository).findTelegramId(id);
            if (customer == null){
                throw new Exception("Non register user!");
            }else{
               customers.put(id, customer); 
            }
        }
        return customer;
    }
    
//    public void pushCustomer(@Nonnull Update update, @Nonnull ClsCustomer customer) {
//        Integer id = UpdateUtil.getUserFromUpdate(update).getId();
//        customers.put(id, customer);
//    }

    public void pushMessage(@Nonnull Update update) {
        Message message = update.getMessage();
        if (message != null) {
            Chat chat = UpdateUtil.getChatFromUpdate(update);
            push(chat, message);
        }
    }
    

    public void pushMessage(@Nonnull Message message) {
        Chat chat = message.getChat();
        push(chat, message);
    }
    
    private void push(Chat chat, Message message){
        if (userMessages.containsKey(chat.getId())) {
            userMessages.get(chat.getId()).add(message);
        } else {
            userMessages.put(chat.getId(), new ArrayList<>(Arrays.asList(message)));
        }
    }

    public List<Message> trimMessages(@Nonnull Chat chat) {
        List<Message> get = get(chat);
        List<Message> trimed = new ArrayList<>(get.size());
        trimed.addAll(get);
        get.clear();
        return trimed;
    }

    private List<Message> get(Chat chat) {
        return userMessages.get(chat.getId()) == null ? new ArrayList<>() : userMessages.get(chat.getId());
    }

    public ClassifierRepository getClassifierRepository() {
        return classifierRepository;
    }

    @Inject
    public void setClassifierRepository(ClassifierRepository classifierRepository) {
        this.classifierRepository = classifierRepository;
    }
}
