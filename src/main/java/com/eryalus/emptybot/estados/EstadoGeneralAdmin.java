/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.estados;

import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.persistence.entities.Person;
import com.eryalus.emptybot.principal.BotTelegram;
import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author eryalus
 */
public class EstadoGeneralAdmin extends Estado {

    private final Chat CHAT;
    private final Message MESSAGE;

    public EstadoGeneralAdmin(Chat chat, Message m, BotTelegram bot, Person person) {
        super(bot, person);
        CHAT = chat;
        MESSAGE = m;
    }

    @Override
    public ArrayList<Send> response(ArrayList<Send> ms) {
        String texto = MESSAGE.getText();
        String txt = texto.toLowerCase().trim();


        return ms;
    }

}
