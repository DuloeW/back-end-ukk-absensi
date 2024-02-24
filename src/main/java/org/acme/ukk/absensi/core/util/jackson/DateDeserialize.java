package org.acme.ukk.absensi.core.util.jackson;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.acme.ukk.absensi.core.util.FormatUtil;

import java.time.LocalDate;

public class DateDeserialize extends StdConverter<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        return FormatUtil.convertStringToLocalDate(s);
    }
}
