package edu.hw5.Task3;

import java.time.LocalDate;
import org.jetbrains.annotations.Nullable;


interface DateParser {
    @Nullable
    LocalDate parseDate(String date);
}
