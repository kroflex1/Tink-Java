package edu.hw10.Task1;

import java.lang.annotation.*;

@Target(value = ElementType.PARAMETER)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Min {
    int minValue();
}
