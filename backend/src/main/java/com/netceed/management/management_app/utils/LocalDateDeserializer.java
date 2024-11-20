package com.netceed.management.management_app.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
       String date = p.getText();
       System.out.println("Data a ser formatada: " + date);

       try{
           return LocalDate.parse(date, formatter);
       }catch (DateTimeParseException e){
           throw ctxt.weirdStringException(date, LocalDate.class, e.getMessage());
       }
    }
}
