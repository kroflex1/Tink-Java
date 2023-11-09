package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task8 {

    //Нечетной длины
    public static boolean solve1(String word) {
        Pattern pattern = Pattern.compile("[01]([01][01])*");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    //Начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину
    public static boolean solve2(String word) {
        Pattern pattern = Pattern.compile("(0([01][01])*)|(1[01]([01][01])*)");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    //Количество 0 кратно 3
    public static boolean solve3(String word) {
        Pattern pattern = Pattern.compile("(1*01*01*01*)+");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    //Любая строка, кроме 11 или 111
    public static boolean solve4(String word) {
        Pattern badPattern = Pattern.compile("1{2,3}");
        Matcher badMatcher = badPattern.matcher(word);
        Pattern pattern = Pattern.compile("[01]+");
        Matcher matcher = pattern.matcher(word);
        return !badMatcher.matches() && matcher.matches();
    }

//    //Каждый нечетный символ равен 1
//    public static boolean solve5(String word) {
//        Pattern pattern = Pattern.compile("(1[01]?)*");
//        Matcher matcher = pattern.matcher(word);
//        return matcher.matches();
//    }

    //Содержит не менее двух 0 и не более одной 1
    public static boolean solve6(String word) {
        Pattern pattern = Pattern.compile("(10{2,})|(0+10+)|(0{2,}1)");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }



    private Task8() {

    }
}
