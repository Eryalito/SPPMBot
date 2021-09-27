package com.eryalus.emptybot.comandos.usuario;

import java.util.ArrayList;
import java.util.List;

import com.eryalus.emptybot.comandos.Command;
import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.i18n.StringManager;
import com.eryalus.emptybot.persistence.entities.Person;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Settings extends Command {

    protected static final String COMMAND_NAME = "settings";

    public Settings(Person person) {
        super(person);
    }

    @Override
    public ArrayList<Send> addMessages(ArrayList<Send> ms) {
        Send s = new Send();
        SendMessage sm = new SendMessage();
        sm.setText(translate("message"));
        // set inline keyboard
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        ArrayList<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton but = new InlineKeyboardButton();
        but.setText(translate("inline.text.langs"));
        but.setCallbackData(translate("inline.callback.langs"));
        rowInline.add(but);        
        rowsInline.add(rowInline);

        rowInline = new ArrayList<>();but = new InlineKeyboardButton();
        but.setText(translate("inline.text.close"));
        but.setCallbackData(translate("inline.callback.close"));
        rowInline.add(but);   

        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();
        

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        s.setSendMessage(sm);
        ms.add(s);
        return ms;
    }

    @Override
    public String getCommandName(){
        return "settings";
    }
    
}
