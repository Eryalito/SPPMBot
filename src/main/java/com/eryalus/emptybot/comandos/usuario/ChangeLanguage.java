package com.eryalus.emptybot.comandos.usuario;

import java.util.ArrayList;
import java.util.List;

import com.eryalus.emptybot.comandos.Command;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.i18n.StringManager;
import com.eryalus.emptybot.persistence.entities.Person;
import com.eryalus.emptybot.persistence.repositories.RepositoryManager;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ChangeLanguage extends ChangeLanguageMenu {

    private final String text;

    public ChangeLanguage(String text, Person person) {
        super(person);
        this.text = text;
    }

    @Override
    public ArrayList<Send> addMessages(ArrayList<Send> ms) {
        // process text to find the new lang
        String processedText = text.trim();
        String[] parts = processedText.split(" ");
        if(parts.length==2){
            // aka "/key lang"
            String newLang = parts[1]; // lang
            boolean validLang = false;
            for(String key : super.LANG_KEY){
                if(key.equals(newLang)){
                    validLang = true;
                    break;
                }
            }
            person.setLang(newLang);
            person = RepositoryManager.getPersonRepository().save(person);
            // update lang entities
            this.stringManager = StringManager.getInstance(person.getLang());
        }
        return super.addMessages(ms);
    }
    
    @Override
    public String getCommandName(){
        return "change-language-menu";
    }
}
