/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eryalus.emptybot.comandos;

import com.eryalus.emptybot.data.Send;
import com.eryalus.emptybot.persistence.entities.Person;
import java.util.ArrayList;

import java.util.ArrayList;

/**
 *
 * @author eryalus
 */
public abstract class Command {

    static final Integer MINUTOS_MINIMOS = 5,
            MAXIMO_BOTONES = 10; //a ser posible par para cuadrar botones

    protected final Person person;

    public Command(Person person){
        this.person = person;
    }

    /**
     *
     * @param ms
     * @return
     */
    public abstract ArrayList<Send> addMessages(ArrayList<Send> ms);
}
