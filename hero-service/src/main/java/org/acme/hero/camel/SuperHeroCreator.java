package org.acme.hero.camel;

import org.acme.hero.model.CapeType;
import org.acme.hero.model.Hero;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
@Named("superHeroCreator")
public class SuperHeroCreator implements Processor {

    private static final Logger log = LoggerFactory.getLogger(SuperHeroCreator.class);

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional(REQUIRED)
    public void process(final Exchange exchange) throws Exception {
        final String legumeItem = exchange.getMessage().getBody(String.class);

        log.info("Legume received: {}", legumeItem);

        final Hero hero = Hero.builder()
                .name("SUPER-" + legumeItem)
                .capeType(CapeType.SUPERMAN)
                .build();

        log.info("hero created: {}", entityManager.merge(hero));
    }
}
