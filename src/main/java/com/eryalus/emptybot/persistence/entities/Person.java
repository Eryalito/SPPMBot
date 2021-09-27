package com.eryalus.emptybot.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.telegram.telegrambots.meta.api.objects.Chat;


@Entity
@Table(name = "person")
public class Person implements DefaultEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "telegramId")
    private Long telegramId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "username")
    private String username;

    @Column(name = "state")
    private int state;

    @Column(name = "admin")
    private Boolean admin = false;

    @Column(name = "send_log")
    private Boolean sendLog = false;

    @Column(name = "lang")
    private String lang;

    public Person() {
    }

    public Person(long telegramId, String name, String surname, String username, int state) {
        this.telegramId = telegramId;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.state = state;
    }

    public Person(Chat chat){
        this.telegramId = chat.getId();
        this.name = chat.getFirstName();
        this.surname = chat.getLastName();
        this.username = chat.getUserName();
    }

    
    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean isAdmin() {
        return this.admin;
    }

    public Boolean isSendLog() {
        return this.sendLog;
    }

    public Boolean getSendLog() {
        return this.sendLog;
    }

    public void setSendLog(Boolean sendLog) {
        this.sendLog = sendLog;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getTelegramId() {
        return this.telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Person id(long id) {
        setId(id);
        return this;
    }

    public Person telegramId(long telegramId) {
        setTelegramId(telegramId);
        return this;
    }

    public Person name(String name) {
        setName(name);
        return this;
    }

    public Person surname(String surname) {
        setSurname(surname);
        return this;
    }

    public Person username(String username) {
        setUsername(username);
        return this;
    }

    public Person state(int state) {
        setState(state);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", telegramId='" + getTelegramId() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", username='" + getUsername() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }

}
