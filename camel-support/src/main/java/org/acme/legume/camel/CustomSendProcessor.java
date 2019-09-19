package org.acme.legume.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.ExchangePattern;
import org.apache.camel.impl.engine.DefaultProducerCache;
import org.apache.camel.processor.SendProcessor;

public class CustomSendProcessor extends SendProcessor {
    public CustomSendProcessor(final Endpoint destination) {
        super(destination);
    }

    public CustomSendProcessor(final Endpoint destination, final ExchangePattern pattern) {
        super(destination, pattern);
    }

    @Override
    protected void doStart() throws Exception {
        if (producerCache == null) {
            producerCache = new DefaultProducerCache(this, camelContext, 0);
        }

        super.doStart();
    }
}
