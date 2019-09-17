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

package org.acme.legume.camel;

import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class CamelInit {

    private static final Logger log = LoggerFactory.getLogger(CamelInit.class);

    @Inject
    private CamelRoutesSetup routesSetup;

    public void onStartup(@Observes final StartupEvent ev) throws Exception {
        log.info("Initializing camel routes.");
        routesSetup.setup();
        routesSetup.start();
    }

}
