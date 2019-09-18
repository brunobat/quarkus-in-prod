
package org.acme.legume.camel;

import org.acme.legume.data.LegumeItem;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

@ApplicationScoped
public class MessageSender {
    @Inject
    private CamelContext context;

    public String send(final LegumeItem legumeItem) {
        final ProducerTemplate template = context.createProducerTemplate();
        final String json = toJson(legumeItem);
        template.sendBody("direct:rabitMQ", json.getBytes(UTF_8));
        return json;
    }

    String toJson(final LegumeItem message) {
        final StringWriter sw = new StringWriter();
        JsonbBuilder.create().toJson(message, sw);
        return sw.toString();
    }
}
