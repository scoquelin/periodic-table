/*
 * Copyright © 2016 SC and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodic.table.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.oxm.MediaType;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.periodic.table.csv.TrimCsvReader;
import org.opendaylight.periodic.table.model.Element;
import org.opendaylight.periodic.table.model.PeriodicTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

public class PeriodicTableProvider {
    private static final Logger LOG = LoggerFactory.getLogger(PeriodicTableProvider.class);
    private static final String PERIODIC_TABLE_CSV_FILE_PATH = "/pt-data2.csv";

    private final DataBroker dataBroker;

    private PeriodicTable periodicTable;

    public PeriodicTableProvider(final DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() throws FileNotFoundException {
        LOG.info("PeriodicTableProvider Session Initiated");

        //Maps CSV file to a periodic table with elements
        periodicTable = readPeriodicTableFrom(PeriodicTableProvider.class.getResourceAsStream(PERIODIC_TABLE_CSV_FILE_PATH));

        //Print periodic table as JSON to standard output
        savePeriodicTable(periodicTable, MediaType.APPLICATION_JSON.getMediaType(), System.out);

        //Print periodic table as XML to standard output
        savePeriodicTable(periodicTable, MediaType.APPLICATION_XML.getMediaType(), System.out);
        
        //uncomment (along with the main method) to get the periodic table saved as a JSON file in /src/main/resources
        //savePeriodicTable(periodicTable, MediaType.APPLICATION_JSON.getMediaType(), new FileOutputStream(new File("impl/src/main/resources/PeriodicTable.json")));
        
        //uncomment (along with the main method) to get the periodic table saved as a XML file in /src/main/resources
        //savePeriodicTable(periodicTable, MediaType.APPLICATION_JSON.getMediaType(), new FileOutputStream(new File("impl/src/main/resources/PeriodicTable.xml")));
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("PeriodicTableProvider Closed");
    }

    public PeriodicTable getPeriodicTable() {
        return periodicTable;
    }

    /**
     * Read a CSV file stream containing a list of {@link Element} and maps it to a {@link PeriodicTable} model object.
     *
     * @param inputStream The CSV file stream containing elements.
     * @return a periodic table object with elements
     */
    PeriodicTable readPeriodicTableFrom(InputStream inputStream) {
        ColumnPositionMappingStrategy<Element> strategy =
                new ColumnPositionMappingStrategy<>();
        strategy.setType(Element.class);
        List<Element> elements = new CsvToBean<Element>().parse(strategy, new TrimCsvReader(new InputStreamReader(inputStream)));
        //Log each element at INFO level
        elements.forEach(element -> LOG.info("{}", element));
        PeriodicTable periodicTable = new PeriodicTable();
        periodicTable.setElements(elements);
        return periodicTable;
    }

    /**
     * Save a {@link PeriodicTable} object to an {@link OutputStream}. Could be standard output, a file, etc.
     *
     * @param periodicTable The periodic table to be saved.
     * @param format The format in which the object will be serialized provided as a {@link MediaType}
     * @param outputStream The output stream to redirect the serialized object to.
     */
    void savePeriodicTable(PeriodicTable periodicTable, String format, OutputStream outputStream) {
        try {
            Map<String, Object> properties = new HashMap<>(2);
            properties.put(JAXBContextProperties.MEDIA_TYPE, format);
            properties.put(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
            JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContext.newInstance("org.opendaylight.periodic.table.model", PeriodicTable.class.getClassLoader(), properties);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(periodicTable, outputStream);
        } catch (JAXBException e) {
            LOG.error("Unable to marshal periodic table to {}", format, e);
        }
    }

//    public static void main(String[] args) throws FileNotFoundException {
//        PeriodicTableProvider periodicTableProvider = new PeriodicTableProvider(null);
//        periodicTableProvider.init();
//    }
}
