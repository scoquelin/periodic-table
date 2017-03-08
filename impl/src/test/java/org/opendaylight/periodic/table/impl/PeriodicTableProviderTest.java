/*
 * Copyright © 2016 SC and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodic.table.impl;

import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.oxm.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendaylight.periodic.table.model.Element;
import org.opendaylight.periodic.table.model.PeriodicTable;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class PeriodicTableProviderTest {
    private static final String DEFAULT_CHARSET = "UTF-8";

    @DataProvider
    public static Object[][] csvColumnSeparators() {
        return new Object[][] {
                { "," },
                { " , " },
        };
    }

    @UseDataProvider("csvColumnSeparators")
    @Test
    public void testReadPeriodicTableFromCsv(String csvColumnSeparator) throws IOException {
        //Setup
        PeriodicTableProvider periodicTableProvider = new PeriodicTableProvider(null);

        //Given
        String csvLineForHydrogen = getCsvLineForHydrogen(csvColumnSeparator);
        InputStream inputStream = IOUtils.toInputStream(csvLineForHydrogen, DEFAULT_CHARSET);

        //When
        PeriodicTable periodicTable = periodicTableProvider.readPeriodicTableFrom(inputStream);

        //Then
        assertThat(periodicTable.getElements()).hasSize(1)
                .containsExactly(getHydrogenElement())
                .extracting("atomicNumber", "symbol", "name", "atomicMass", "color", "electronicConfiguration", "electroNegativity", "atomicRadius",
                            "ionRadius", "vanDerWaalsRadius", "ie1", "ea", "standardState", "bondingType", "meltingPoint", "boilingPoint",
                            "density", "metal", "yearDiscovered")
                .contains(
                        tuple(Hydrogen.ATOMIC_NUMBER, Hydrogen.SYMBOL, Hydrogen.NAME, Hydrogen.ATOMIC_MASS, Hydrogen.COLOR, Hydrogen.ELECTRONIC_CONFIG, Hydrogen.ELECTRO_NEGATIVITY, Hydrogen.ATOMIC_RADIUS,
                                StringUtils.trimToNull(Hydrogen.ION_RADIUS), Hydrogen.VAN_DER_WAALS_RADIUS, Hydrogen.IE1, Hydrogen.EA, Hydrogen.STANDARD_STATE, Hydrogen.BONDING_TYPE, Hydrogen.MELTING_POINT, Hydrogen.BOILING_POINT,
                                Hydrogen.DENSITY, Hydrogen.METAL, Hydrogen.YEAR_DISCOVERED));
    }

    @Test
    public void testSavePeriodicTableAsJson() throws IOException, JAXBException {
        //Setup
        PeriodicTableProvider periodicTableProvider = new PeriodicTableProvider(null);
        PeriodicTable periodicTable = getPeriodicTableWithHydrogenOnly();
        
        //Given
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //When
        periodicTableProvider.savePeriodicTable(periodicTable, MediaType.APPLICATION_JSON.getMediaType(), byteArrayOutputStream);

        //Then
        Map<String, Object> properties = new HashMap<>(2);
        properties.put(JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        properties.put(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
        JAXBContext context = org.eclipse.persistence.jaxb.JAXBContext.newInstance("org.opendaylight.periodic.table.model", PeriodicTable.class.getClassLoader(), properties);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        assertThat(unmarshaller.unmarshal(IOUtils.toInputStream(new String(byteArrayOutputStream.toByteArray()), DEFAULT_CHARSET))).isEqualTo(periodicTable);
    }

    @Test
    public void testSavePeriodicTableAsXml() throws IOException, JAXBException {
        //Setup
        PeriodicTableProvider periodicTableProvider = new PeriodicTableProvider(null);
        PeriodicTable periodicTable = getPeriodicTableWithHydrogenOnly();

        //Given
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //When
        periodicTableProvider.savePeriodicTable(periodicTable, MediaType.APPLICATION_XML.getMediaType(), byteArrayOutputStream);

        //Then
        Map<String, Object> properties = new HashMap<>(2);
        properties.put(JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);
        properties.put(JAXBContextProperties.JSON_WRAPPER_AS_ARRAY_NAME, true);
        JAXBContext context = org.eclipse.persistence.jaxb.JAXBContext.newInstance("org.opendaylight.periodic.table.model", PeriodicTable.class.getClassLoader(), properties);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        assertThat(unmarshaller.unmarshal(IOUtils.toInputStream(new String(byteArrayOutputStream.toByteArray()), DEFAULT_CHARSET))).isEqualTo(periodicTable);
    }

    @Test
    public void testInit() throws FileNotFoundException {
        //Given
        PeriodicTableProvider periodicTableProvider = new PeriodicTableProvider(null);

        //When
        periodicTableProvider.init();

        //Then
        assertThat(periodicTableProvider.getPeriodicTable().getElements()).hasSize(118);
    }

    private String getCsvLineForHydrogen(String csvColumnSeparator) {
        return Stream.of(Hydrogen.ATOMIC_NUMBER, Hydrogen.SYMBOL, Hydrogen.NAME, Hydrogen.ATOMIC_MASS, Hydrogen.COLOR, Hydrogen.ELECTRONIC_CONFIG, Hydrogen.ELECTRO_NEGATIVITY, Hydrogen.ATOMIC_RADIUS,
                Hydrogen.ION_RADIUS, Hydrogen.VAN_DER_WAALS_RADIUS, Hydrogen.IE1, Hydrogen.EA, Hydrogen.STANDARD_STATE, Hydrogen.BONDING_TYPE, Hydrogen.MELTING_POINT, Hydrogen.BOILING_POINT,
                Hydrogen.DENSITY, Hydrogen.METAL, Hydrogen.YEAR_DISCOVERED)
                .map(String::valueOf)
                .collect(Collectors.joining(csvColumnSeparator));
    }

    private PeriodicTable getPeriodicTableWithHydrogenOnly() {
        PeriodicTable periodicTable = new PeriodicTable();
        periodicTable.setElements(Collections.singletonList(getHydrogenElement()));
        return periodicTable;
    }
    
    private Element getHydrogenElement() {
        Element element = new Element();
        element.setAtomicNumber(Hydrogen.ATOMIC_NUMBER);
        element.setSymbol(Hydrogen.SYMBOL);
        element.setName(Hydrogen.NAME);
        element.setAtomicMass(Hydrogen.ATOMIC_MASS);
        element.setColor(Hydrogen.COLOR);
        element.setElectronicConfiguration(Hydrogen.ELECTRONIC_CONFIG);
        element.setElectroNegativity(Hydrogen.ELECTRO_NEGATIVITY);
        element.setAtomicRadius(Hydrogen.ATOMIC_RADIUS);
        //Empty String will be mapped to null by the CSV reader
        element.setIonRadius(StringUtils.trimToNull(Hydrogen.ION_RADIUS));
        element.setVanDerWaalsRadius(Hydrogen.VAN_DER_WAALS_RADIUS);
        element.setIe1(Hydrogen.IE1);
        element.setEa(Hydrogen.EA);
        element.setStandardState(Hydrogen.STANDARD_STATE);
        element.setBondingType(Hydrogen.BONDING_TYPE);
        element.setMeltingPoint(Hydrogen.MELTING_POINT);
        element.setBoilingPoint(Hydrogen.BOILING_POINT);
        element.setDensity(Hydrogen.DENSITY);
        element.setMetal(Hydrogen.METAL);
        element.setYearDiscovered(Hydrogen.YEAR_DISCOVERED);
        return element;
    }

    static class Hydrogen {
        static final Integer ATOMIC_NUMBER = 1;
        static final String SYMBOL = "H";
        static final String NAME = "Hydrogen";
        static final String ATOMIC_MASS = "1.00794(4)";
        static final String COLOR = "FFFFFF";
        static final String ELECTRONIC_CONFIG = "1s1";
        static final String ELECTRO_NEGATIVITY = "2.20";
        static final Integer ATOMIC_RADIUS = 37;
        static final String ION_RADIUS = "";
        static final Integer VAN_DER_WAALS_RADIUS = 120;
        static final String IE1 = "1312";
        static final String EA = "-73";
        static final String STANDARD_STATE = "gas";
        static final String BONDING_TYPE = "diatomic";
        static final String MELTING_POINT = "14";
        static final String BOILING_POINT = "20";
        static final String DENSITY = "8.99E-05";
        static final String METAL = "nonmetal";
        static final String YEAR_DISCOVERED = "1766";

        private Hydrogen() {
            //Don't new me as this class is just holding constants
        }
    }
}
