package com.github.rmannibucau.blog.dao.internal;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by jlmonteiro on 05/02/14.
 */
@ApplicationScoped
public class DataSourceProducer {

    @PersistenceContext
    private EntityManager em;

    @Produces @RequestScoped
    public EntityManager create() {
        return em;
    }

}
