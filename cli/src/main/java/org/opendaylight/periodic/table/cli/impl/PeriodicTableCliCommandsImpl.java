/*
 * Copyright © 2016 SC and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodic.table.cli.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.periodic.table.cli.api.PeriodicTableCliCommands;

public class PeriodicTableCliCommandsImpl implements PeriodicTableCliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(PeriodicTableCliCommandsImpl.class);
    private final DataBroker dataBroker;

    public PeriodicTableCliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("PeriodicTableCliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}