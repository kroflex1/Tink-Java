package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int MONTHS_PER_YEAR = 12;
    private static final int NUMBER_OF_TERRIFYING_FRIDAY = 13;

    public static List<String> getAllFridays13th(int year) {
        List<String> result = new ArrayList<>();
        int currentMonth = 1;
        while (currentMonth <= MONTHS_PER_YEAR) {
            LocalDate currentDate = LocalDate.of(year, currentMonth, NUMBER_OF_TERRIFYING_FRIDAY);
            if (currentDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                result.add(currentDate.format(FORMATTER));
            }
            ++currentMonth;
        }
        return result;
    }

    public static LocalDate getNearestNextFriday13th(LocalDate startDate) {
        LocalDate currentDate = startDate;
        while (true) {
            if (currentDate.getDayOfMonth() == NUMBER_OF_TERRIFYING_FRIDAY &&
                currentDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                return currentDate;
            }
            currentDate =
                currentDate.with(TemporalAdjusters.firstDayOfNextMonth()).plusDays(NUMBER_OF_TERRIFYING_FRIDAY - 1);
        }
    }

//    private static List<LocalDate> getNextFridays13th(LocalDate startDate) {
//        List<LocalDate> result = new ArrayList<>();
//        int year = startDate.getYear();
//        int currentMonth = startDate.getMonthValue();
//        while (currentMonth <= 12) {
//            LocalDate currentDate = LocalDate.of(year, currentMonth, 13);
//            if (currentDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
//                result.add(currentDate);
//            }
//            ++currentMonth;
//        }
//        return result;
//    }

    private Task2() {

    }
}
