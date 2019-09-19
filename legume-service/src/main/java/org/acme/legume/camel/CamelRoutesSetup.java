

package org.acme.legume.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.engine.AbstractCamelContext;
import org.apache.camel.model.Model;
import org.apache.camel.model.RouteDefinition;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
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

    public void setup() throws Exception {
        messageSender();
    }

    public void start() throws Exception {
        final AbstractCamelContext context = getCamelContex();
        log.info("Starting camel routes");
        for (RouteDefinition routeDefinition : context.getExtension(Model.class).getRouteDefinitions()) {
            context.startRoute(routeDefinition.getId());
        }
    }

    protected boolean autoStartRoutes() {
        return true;
    }

    private void messageSender() throws Exception {
        final RouteBuilder routeBuilder = new RouteBuilder(getCamelContex()) {
            @Override
            public void configure() throws Exception {
                configMessageProducer();
            }

            private void configMessageProducer() {
                final String toUri = "rabbitmq:" + "demo" +
                        "?autoDelete=" + "false" +
                        "&declare=false" +
                        "&addresses=" + host + ":" + port +
                        "&username=" + "rabbitmq" +
                        "&password=" + "rabbitmq" +
                        "&vhost=" + "/demo" +
                        "&routingKey=" + "demo" +
                        "&queue=" + "demo";

                log.info("adding route: {}", toUri);

                from("direct:rabbitMQ")
                        .routeId("MessageProducer")
                        .log("send message")
                        .to(toUri)
                        .autoStartup(false);
            }
        };
        camelContext.addRoutes(routeBuilder);
    }

    private AbstractCamelContext getCamelContex() {
        return (AbstractCamelContext) camelContext; //this cast is safe
    }
}
