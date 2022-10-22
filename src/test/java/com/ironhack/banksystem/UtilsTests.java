package com.ironhack.banksystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UtilsTests {

    @Test
    public void getYearsBetweenToDates_WorksOk() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = formatter.parse("26-11-2020");
        Date date2 = formatter.parse("26-12-2025");

        assertEquals(5, Utils.getYearsBetweenToDates(date1,date2));
    }

    @Test
    public void getMonthsBetweenToDates_WorksOk() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = formatter.parse("26-01-2020");
        Date date2 = formatter.parse("26-12-2021");

        assertEquals(23, Utils.getMonthsBetweenToDates(date1,date2));
    }
}
