/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.principal;

import com.eryalus.emptybot.acciones.AccionAdmin;
import com.eryalus.emptybot.acciones.AccionNoAdmin;
import com.eryalus.emptybot.acciones.AccionNoAdminCallBack;
import com.eryalus.emptybot.acciones.Action;
import com.eryalus.emptybot.persistence.entities.Person;
import com.eryalus.emptybot.persistence.repositories.RepositoryManager;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 *
 * @author eryalus
 */
public class RespuestaConsulta extends Thread {

    private Thread hilo = null;
    private final Update UPDATE;
    private final BotTelegram PARENT;
    private String texto_log = "";
    private String error = "";

    public RespuestaConsulta(Update update, BotTelegram parent) {
        this.UPDATE = update;
        this.PARENT = parent;
    }

    public String error(Exception exception) {
        error += exception.getMessage() + "\n";
        StackTraceElement[] sts = exception.getStackTrace();
        for (StackTraceElement st : sts) {
            error += st.toString() + "\n";
        }
        return error;
    }

    @Override
    public void run() {
        if (UPDATE.hasMessage()) {

            try {
                Action accion;
                boolean done = false;
                Person person = RepositoryManager.getPersonRepository().findByTelegramIdOrCreate(UPDATE.getMessage().getChat());
                if (person != null && person.getAdmin()) {
                    accion = new AccionAdmin(UPDATE, PARENT, person);
                    done = accion.action();
                }
                if (person != null && !done) {
                    accion = new AccionNoAdmin(UPDATE, PARENT, person);
                    accion.action();
                }
            } catch (Exception ex) {

            }
        } else if (UPDATE.hasCallbackQuery()) {
            CallbackQuery callbackQuery = UPDATE.getCallbackQuery();
            Person person = RepositoryManager.getPersonRepository().findByTelegramIdOrCreate(callbackQuery.getMessage().getChat());
            if(person != null){
                try {
                    Action accion = new AccionNoAdminCallBack(callbackQuery, PARENT, person);
                    accion.action();
                } catch (Exception ex) {

                }
            }
        }
        PARENT.remove(this);
    }

    @Override
    public void start() {
        if (hilo == null) {
            hilo = new Thread(this, "Hilo de respuesta");
            hilo.start();
        }
    }
}
