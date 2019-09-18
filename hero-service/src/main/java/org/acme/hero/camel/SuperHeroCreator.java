package org.acme.hero.camel;

import org.acme.hero.data.LegumeItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("superHeroCreator")
public class SuperHeroCreator implements Processor {

    private static final Logger log = LoggerFactory.getLogger(SuperHeroCreator.class);

    @Override
    public void process(final Exchange exchange) throws Exception {
        final LegumeItem legumeItem = exchange.getMessage().getBody(LegumeItem.class);
        log.info("Legume received: {}", legumeItem);

    }
}
