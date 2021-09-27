package com.eryalus.emptybot.comandos.usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eryalus.emptybot.comandos.Command;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.i18n.StringManager;
import com.eryalus.emptybot.persistence.entities.Person;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ChangeLanguageMenu extends Command {

    protected static final String[] LANG_KEY = new String[]{"en", "es"};

    public ChangeLanguageMenu(Person person) {
        super(person);
    }

    @Override
    public ArrayList<Send> addMessages(ArrayList<Send> ms) {
        Send s = new Send();
        SendMessage sm = new SendMessage();
        HashMap<String, String> transParams = new HashMap<>();
        transParams.put("lang", stringManager.translate("langs." + (person.getLang()!= null ? person.getLang() : StringManager.DEFAULT_LANGUAGE)));
        sm.setText(translate("message", transParams));
        // set inline keyboard
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        ArrayList<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton but = new InlineKeyboardButton();
        but.setText(translate("inline.text.back"));
        but.setCallbackData(translate("inline.callback.back"));
        rowInline.add(but);        
        rowsInline.add(rowInline);
        for(String key : LANG_KEY){
            but = new InlineKeyboardButton();
            but.setText(stringManager.translate("langs."+key));
            but.setCallbackData("/change-language "+key);
            rowInline = new ArrayList<>();
            rowInline.add(but);        
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        s.setSendMessage(sm);
        ms.add(s);
        return ms;
    }
    
    @Override
    public String getCommandName(){
        return "change-language-menu";
    }
}
