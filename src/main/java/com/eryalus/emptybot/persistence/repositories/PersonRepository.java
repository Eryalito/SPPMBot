package com.eryalus.emptybot.persistence.repositories;

import com.eryalus.emptybot.persistence.entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.telegram.telegrambots.meta.api.objects.Chat;





public class PersonRepository extends Repository<Person>{

    public PersonRepository(EntityManager em) {
        super(Person.class, em);
    }

    /**
     * Find the user by its telegramID. A new Person will be created or its data will be updated without changing the state.
     * @param p
     * @return
     */
    public Person addIfNotExists(Person p){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Person tp = findByTelegramId(p.getTelegramId());
        if(tp == null){
            p.setState(0); // Default initial state
            em.persist(p);
            tp = p;
        }else{
            tp.setState(p.getState());
            tp = em.merge(tp);
        }
        transaction.commit();
        return tp;
    }

    public Person findByTelegramId(Long id){
        try{
            return (Person) em.createQuery("SELECT p FROM Person p where p.telegramId = :tId").setParameter("tId", id).getSingleResult();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        return null;
    }

    
    public Person findByTelegramIdOrCreate(Chat chat){
        Person p = new Person(chat);
        return this.addIfNotExists(p);
    }

    public List<Person> findBySendLog(){
        return em.createQuery("SELECT distinct p FROM Person p where p.sendLog = true").getResultList();
    }
}
