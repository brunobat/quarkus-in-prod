/*
 * Talkdesk Confidential
 *
 * Copyright (C) Talkdesk Inc. 2019
 *
 * The source code for this program is not published or otherwise divested
 * of its trade secrets, irrespective of what has been deposited with the
 * U.S. Copyright Office. Unauthorized copying of this file, via any medium
 * is strictly prohibited.
 */

package org.acme.camel;

import io.quarkus.runtime.StartupEvent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.engine.AbstractCamelContext;
import org.apache.camel.model.Model;
import org.apache.camel.model.RouteDefinition;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class CamelRoutesSetup {
    private static final Logger log = LoggerFactory.getLogger(CamelRoutesSetup.class);

    @Inject
    private CamelContext camelContext;

    @Inject
    @ConfigProperty(name = "org.acme.camel.setup.rabbitmq.host")
    private String host;

    @Inject
    @ConfigProperty(name = "org.acme.camel.setup.rabbitmq.port")
    private String port;

    @Inject
    @ConfigProperty(name = "org.acme.camel.setup.rabbitmq.username")
    private String username;

    @Inject
    @ConfigProperty(name = "org.acme.camel.setup.rabbitmq.password")
    private String password;

    public void onStartup(@Observes final StartupEvent ev) throws Exception {
        log.info("Initializing camel routes.");
        setup();
        start();
    }

    private void setup() throws Exception {
        final RouteBuilder routeBuilder = new RouteBuilder(getCamelContex()) {
            @Override
            public void configure() throws Exception {
                configMessageProducer();
            }

            private void configMessageProducer() {
                final String toUri = "rabbitmq:" + "direct" +
                        "?autoDelete=" + "false" +
                        "&declare=false" +
                        "&addresses=" + host + ":" + port +
                        "&username=" + username +
                        "&password=" + password;

                from("direct:rabbitMQ")
                        .routeId("MessageProducer")
                        .to(toUri)
                        .autoStartup(autoStartRoutes());
            }
        };
        camelContext.addRoutes(routeBuilder);
    }

    protected boolean autoStartRoutes() {
        return true;
    }

    private void start() throws Exception {
        final AbstractCamelContext context = getCamelContex();
        log.info("Starting camel routes");
        for (RouteDefinition routeDefinition : context.getExtension(Model.class).getRouteDefinitions()) {
            context.startRoute(routeDefinition.getId());
        }
    }

    private AbstractCamelContext getCamelContex() {
        return (AbstractCamelContext) camelContext; //this cast is safe
    }
}
