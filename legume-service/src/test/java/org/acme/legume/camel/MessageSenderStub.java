
package org.acme.legume.camel;

import org.acme.legume.data.LegumeItem;
import org.apache.camel.CamelContext;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.inject.Singleton;

@Alternative
@Priority(1)
@Singleton
public class MessageSenderStub extends MessageSender {

    public String send(final LegumeItem legumeItem) {
        return "";
    }
}
