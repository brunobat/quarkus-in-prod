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

package org.acme.legume.resource.camel;

import org.acme.legume.camel.CamelRoutesSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

@Alternative
@Priority(1)
@Singleton
public class SetupTestCamelRoutes extends CamelRoutesSetup {

    private static final Logger log = LoggerFactory.getLogger(SetupTestCamelRoutes.class);

    @Override
    protected boolean autoStartRoutes() {
        return false;
    }

    @Override
    public void start() {
        log.info("RabbitMQ route disabled tests");
    }
}
