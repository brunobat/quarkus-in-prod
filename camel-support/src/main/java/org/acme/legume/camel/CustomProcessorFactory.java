package org.acme.legume.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.NamedNode;
import org.apache.camel.Processor;
import org.apache.camel.model.SendDefinition;
import org.apache.camel.spi.ProcessorFactory;
import org.apache.camel.spi.RouteContext;

import java.util.Map;

public class CustomProcessorFactory implements ProcessorFactory {
    @Override
    public Processor createChildProcessor(
        final RouteContext routeContext, final NamedNode definition, final boolean mandatory) throws Exception {
        return null;
    }

    @Override
    public Processor createProcessor(final RouteContext routeContext, final NamedNode definition)
        throws Exception {

        if (definition instanceof SendDefinition) {
            Endpoint endpoint = resolveEndpoint(routeContext, (SendDefinition) definition);
            return new CustomSendProcessor(endpoint, ((SendDefinition) definition).getPattern());
        }

        return null;
    }

    @Override
    public Processor createProcessor(
        final CamelContext camelContext, final String definitionName, final Map<String, Object> args) throws Exception {
        return null;
    }

    public Endpoint resolveEndpoint(RouteContext context, SendDefinition definition) {
        if (definition.getEndpoint() == null) {
            if (definition.getEndpointProducerBuilder() == null) {
                return context.resolveEndpoint(definition.getEndpointUri(), (String) null);
            } else {
                return definition.getEndpointProducerBuilder().resolve(context.getCamelContext());
            }
        } else {
            return definition.getEndpoint();
        }
    }
}
