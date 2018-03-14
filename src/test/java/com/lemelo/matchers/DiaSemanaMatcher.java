package com.lemelo.matchers;

import com.lemelo.utils.MyDates;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

    private Integer diaSemana;

    public DiaSemanaMatcher(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void describeTo(Description description) {

    }

    protected boolean matchesSafely(Date data) {
        return MyDates.validarDiaSemana(data, diaSemana);
    }
}
