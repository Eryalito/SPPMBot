/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.comandos.usuario;

import java.util.ArrayList;

import com.eryalus.emptybot.comandos.Command;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.persistence.entities.Person;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


/**
 *
 * @author eryalus
 */
public class Help extends Command {

    public Help(Person person) {
        super(person);
    }

    public static final String HELP_TXT = "<b>Comandos</b>\n"
            + "\n/start - Muestra el mensaje de bienvenida."
            + "\n/help - Muestra este mensaje de ayuda."
            ;

    @Override
    public ArrayList<Send> addMessages(ArrayList<Send> ms) {
        SendMessage m = new SendMessage();
        m.setText(HELP_TXT);
        Send s = new Send();
        s.setSendMessage(m);
        ms.add(s);
        return ms;
    }

}
