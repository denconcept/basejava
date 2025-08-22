package com.basejava.webapp.util;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class XmlLocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String str) {
        return LocalDate.parse(str);
    }

    @Override
    public String marshal(LocalDate ld) {
        return ld.toString();
    }
}
