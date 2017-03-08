/*
 * Copyright © 2016 SC and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodic.table.csv;

import java.io.IOException;
import java.io.Reader;

import com.opencsv.CSVReader;

public class TrimCsvReader extends CSVReader {
    public TrimCsvReader(Reader reader) {
        super(reader);
    }

    @Override
    public String[] readNext() throws IOException {
        String[] result = super.readNext();
        if (result != null) {
            for (int i = 0; i < result.length; i++) {
                result[i] = result[i].trim();
            }
        }
        return result;
    }
}
