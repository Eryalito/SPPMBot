/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.comandos.usuario;

import com.eryalus.emptybot.comandos.Command;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.persistence.entities.Person;
import com.eryalus.emptybot.principal.BotTelegram;
import com.vdurmont.emoji.EmojiParser;
import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 *
 * @author eryalus
 */
public class Start extends Command {

    private static final String TXT = "Bienvenido al bot :smile:";
   private final Chat CHAT;
    private final BotTelegram PARENT;

    public Start(Chat CHAT, BotTelegram PARENT, Person person) {
        super(person);
        this.CHAT = CHAT;
        this.PARENT = PARENT;
    }
    @Override
    public ArrayList<Send> addMessages(ArrayList<Send> ms) {
        SendMessage m = new SendMessage();
        m.setText(EmojiParser.parseToUnicode(TXT));
        Send s = new Send();
        s.setSendMessage(m);
        ms.add(s);
        ms = new Help(person).addMessages(ms);
        return ms;
    }
}
