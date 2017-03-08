/*
 * Copyright © 2016 SC and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodic.table.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.opencsv.bean.CsvBindByPosition;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Element {

    @XmlElement
    @CsvBindByPosition(position = 0)
    private Integer atomicNumber;

    @XmlElement
    @CsvBindByPosition(position = 1)
    private String symbol;

    @XmlElement
    @CsvBindByPosition(position = 2)
    private String name;

    @XmlElement
    @CsvBindByPosition(position = 3)
    private String atomicMass;

    @XmlElement
    @CsvBindByPosition(position = 4)
    private String color;

    @XmlElement
    @CsvBindByPosition(position = 5)
    private String electronicConfiguration;

    @XmlElement
    @CsvBindByPosition(position = 6)
    private String electroNegativity;

    @XmlElement
    @CsvBindByPosition(position = 7)
    private Integer atomicRadius;

    @XmlElement
    @CsvBindByPosition(position = 8)
    private String ionRadius;

    @XmlElement
    @CsvBindByPosition(position = 9)
    private Integer vanDerWaalsRadius;

    @XmlElement
    @CsvBindByPosition(position = 10)
    private String ie1;

    @XmlElement
    @CsvBindByPosition(position = 11)
    private String ea;

    @XmlElement
    @CsvBindByPosition(position = 12)
    private String standardState;

    @XmlElement
    @CsvBindByPosition(position = 13)
    private String bondingType;

    @XmlElement
    @CsvBindByPosition(position = 14)
    private String meltingPoint;

    @XmlElement
    @CsvBindByPosition(position = 15)
    private String boilingPoint;

    @XmlElement
    @CsvBindByPosition(position = 16)
    private String density;

    @XmlElement
    @CsvBindByPosition(position = 17)
    private String metal;

    @XmlElement
    @CsvBindByPosition(position = 18)
    private String yearDiscovered;

    public Integer getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(Integer atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAtomicMass() {
        return atomicMass;
    }

    public void setAtomicMass(String atomicMass) {
        this.atomicMass = atomicMass;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getElectronicConfiguration() {
        return electronicConfiguration;
    }

    public void setElectronicConfiguration(String electronicConfiguration) {
        this.electronicConfiguration = electronicConfiguration;
    }

    public String getElectroNegativity() {
        return electroNegativity;
    }

    public void setElectroNegativity(String electroNegativity) {
        this.electroNegativity = electroNegativity;
    }

    public Integer getAtomicRadius() {
        return atomicRadius;
    }

    public void setAtomicRadius(Integer atomicRadius) {
        this.atomicRadius = atomicRadius;
    }

    public String getIonRadius() {
        return ionRadius;
    }

    public void setIonRadius(String ionRadius) {
        this.ionRadius = ionRadius;
    }

    public Integer getVanDerWaalsRadius() {
        return vanDerWaalsRadius;
    }

    public void setVanDerWaalsRadius(Integer vanDerWaalsRadius) {
        this.vanDerWaalsRadius = vanDerWaalsRadius;
    }

    public String getIe1() {
        return ie1;
    }

    public void setIe1(String ie1) {
        this.ie1 = ie1;
    }

    public String getEa() {
        return ea;
    }

    public void setEa(String ea) {
        this.ea = ea;
    }

    public String getStandardState() {
        return standardState;
    }

    public void setStandardState(String standardState) {
        this.standardState = standardState;
    }

    public String getBondingType() {
        return bondingType;
    }

    public void setBondingType(String bondingType) {
        this.bondingType = bondingType;
    }

    public String getMeltingPoint() {
        return meltingPoint;
    }

    public void setMeltingPoint(String meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    public String getBoilingPoint() {
        return boilingPoint;
    }

    public void setBoilingPoint(String boilingPoint) {
        this.boilingPoint = boilingPoint;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public String getYearDiscovered() {
        return yearDiscovered;
    }

    public void setYearDiscovered(String yearDiscovered) {
        this.yearDiscovered = yearDiscovered;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)  {
            return true;
        }
        if (!(o instanceof Element)) {
            return false;
        }

        Element element = (Element) o;

        return new EqualsBuilder()
                .append(atomicNumber, element.atomicNumber)
                .append(symbol, element.symbol)
                .append(name, element.name)
                .append(atomicMass, element.atomicMass)
                .append(color, element.color)
                .append(electronicConfiguration, element.electronicConfiguration)
                .append(electroNegativity, element.electroNegativity)
                .append(atomicRadius, element.atomicRadius)
                .append(ionRadius, element.ionRadius)
                .append(vanDerWaalsRadius, element.vanDerWaalsRadius)
                .append(ie1, element.ie1)
                .append(ea, element.ea)
                .append(standardState, element.standardState)
                .append(bondingType, element.bondingType)
                .append(meltingPoint, element.meltingPoint)
                .append(boilingPoint, element.boilingPoint)
                .append(density, element.density)
                .append(metal, element.metal)
                .append(yearDiscovered, element.yearDiscovered)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(atomicNumber)
                .append(symbol)
                .append(name)
                .append(atomicMass)
                .append(color)
                .append(electronicConfiguration)
                .append(electroNegativity)
                .append(atomicRadius)
                .append(ionRadius)
                .append(vanDerWaalsRadius)
                .append(ie1)
                .append(ea)
                .append(standardState)
                .append(bondingType)
                .append(meltingPoint)
                .append(boilingPoint)
                .append(density)
                .append(metal)
                .append(yearDiscovered)
                .hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
