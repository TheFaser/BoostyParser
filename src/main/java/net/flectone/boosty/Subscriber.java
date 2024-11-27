package net.flectone.boosty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public record Subscriber(String name,
                         String email,
                         String type,
                         String levelPrice,
                         String userPrice,
                         String totalMoney,
                         Date startDate,
                         Date endDate,
                         String levelName) {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Subscriber parse(String string) {
        String[] values = string.replace("\"", "").split(";");

        if (values.length < 9) return null;

        String name = values[0];
        String email = values[1];
        String type = values[2];
        String levelPrice = values[3];
        String userPrice = values[4];
        String totalMoney = values[5];
        String stringStartDate = values[6];
        String stringEndDate = values[7];
        String levelName = values[8];

        Date start = null;
        Date end = null;

        try {
            start = SIMPLE_DATE_FORMAT.parse(stringStartDate);
            end = stringEndDate.equalsIgnoreCase("-") ? null : SIMPLE_DATE_FORMAT.parse(stringEndDate);
        } catch (ParseException ignored) {}

        if (start == null) return null;

        return new Subscriber(name, email, type, levelPrice, userPrice, totalMoney, start, end, levelName);
    }
}
