
package org.acme.legume.camel;

import org.acme.legume.data.LegumeItem;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static java.nio.charset.StandardCharsets.UTF_8;

@ApplicationScoped
public class MessageSender {

    @Inject
    private CamelContext context;

    public String send(final LegumeItem legumeItem) {
        final ProducerTemplate template = context.createProducerTemplate();
        template.sendBody("direct:rabbitMQ", legumeItem.getName().getBytes(UTF_8));
        return legumeItem.getName();
    }

}
