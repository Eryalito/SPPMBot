/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.estados;

import com.eryalus.emptybot.comandos.usuario.ChangeLanguage;
import com.eryalus.emptybot.comandos.usuario.ChangeLanguageMenu;
import com.eryalus.emptybot.comandos.usuario.Help;
import com.eryalus.emptybot.comandos.usuario.Settings;
import com.eryalus.emptybot.comandos.usuario.Start;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.i18n.StringManager;
import com.eryalus.emptybot.persistence.entities.Person;
import com.eryalus.emptybot.persistence.repositories.RepositoryManager;
import com.eryalus.emptybot.principal.BotTelegram;
import java.util.ArrayList;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 *
 * @author eryalus
 */
public class EstadoCallback extends Estado {

    private final Chat CHAT;
    private final CallbackQuery MESSAGE;
    private Boolean delete = true;

    public Boolean getDelete() {
        return delete;
    }

    public EstadoCallback(Chat chat,CallbackQuery m, BotTelegram bot, Person person) {
        super(bot, person);
        CHAT = chat;
        MESSAGE = m;
    }

    @Override
    public ArrayList<Send> response(ArrayList<Send> ms) {
        String texto = MESSAGE.getData();
        String txt = texto.toLowerCase().trim();
        if (txt.equals("/start") || txt.equals("start")) {
            ms = new Start(CHAT, PARENT, person).addMessages(ms);
        } else if (txt.equals("/help") || txt.equals("help")) {
            ms = new Help(person).addMessages(ms);
        } else if(txt.equals("/"+stringManager.translate("commands.settings.key"))){ // /settings
            ms = new Settings(person).addMessages(ms);
        } else if (txt.equals("/"+StringManager.getInstance(person.getLang()).translate("commands.change-language-menu.key"))){
            ms = new ChangeLanguageMenu(person).addMessages(ms);
        } else if (txt.startsWith("/"+StringManager.getInstance(person.getLang()).translate("commands.change-language.key"))){
            ms = new ChangeLanguage(texto, person).addMessages(ms);
            // person is updated, reload entities
            person = RepositoryManager.getPersonRepository().findById(person.getId());
            stringManager = StringManager.getInstance(person.getLang());
        } else if (txt.equals("/nothing")){
            // NOOP, just to delete the message
        } else {
            // if no command is launch DO NOT delete the message
            delete = false;
        }
        return ms;
    }

}
