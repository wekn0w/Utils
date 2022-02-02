package utils;

import core.IObkectComarator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Equaler {
    public static <T> boolean equals(T a, T b) {
        if (a == null)
            return b == null;
        return b == null ? false : a.equals(b);
    }

    public static <T> boolean equals(T a, T b, IObkectComarator<? super T> сравниватель) {
        if (a == null)
            return b == null;
        return b == null ? false : сравниватель.равны(a, b);
    }

    public static boolean equals(boolean a, boolean b) {
        return b == a;
    }

    public static boolean equals(int a, int b) {
        return b == a;
    }

    public static <T> boolean equals(List<T> a, List<T> b, boolean поэлементно) {
        return Equaler.equals(a, b, IObkectComarator.STANDART, поэлементно);
    }

    public static <T> boolean equalsCollections(Collection<T> коллекция1,
                                                Collection<T> коллекция2,
                                                IObkectComarator<? super T> сравниватель) {
        if (коллекция1 == null)
            return коллекция2 == null;
        if (коллекция2 == null)
            return false;
        if (коллекция1.size() != коллекция2.size())
            return false;
        ArrayList<T> клон2 = new ArrayList<T>(коллекция2);
        LCollections<T> коллекции = new LCollections<T>(сравниватель);
        for (T object : коллекция1) {
            if (коллекции.getЭлемент(клон2, object, true) == null)
                return false;
        }
        return true;
    }

    public static <T> void assertEquals(Collection<T> эталон,
                                        Collection<T> сравниваемый,
                                        IObkectComarator<? super T> сравниватель) {
        if (эталон == null && сравниваемый != null)
            throw new IllegalArgumentException("Ожидалась коллекция NULL");
        if (сравниваемый == null)
            throw new IllegalArgumentException("Ожидалась коллекция не NULL");
        ArrayList<T> клон = new ArrayList<T>(сравниваемый);
        LCollections<T> коллекции = new LCollections<T>(сравниватель);
        for (T t : эталон) {
            if (коллекции.getЭлемент(клон, t, true) == null)
                throw new IllegalArgumentException("Для элемента " + t.toString()
                        + " эталона не найдено соответствия в тестируемой коллекции");
        }
        if (!клон.isEmpty())
            throw new IllegalArgumentException("В сравниваемой коллекции элементов больше, чем в эталоне");
    }

    public static <T> boolean equals(List<T> a,
                                     List<T> b,
                                     IObkectComarator<? super T> сравниватель,
                                     boolean сУчетомПорядка) {
        if (!сУчетомПорядка)
            return equalsCollections(a, b, сравниватель);
        else return одинаковыеСписки(a, b, сравниватель);
    }

    private static <T> boolean одинаковыеСписки(List<T> список1,
                                                List<T> список2,
                                                IObkectComarator<? super T> сравниватель) {
        if (список1 == null)
            return список2 == null;
        if (список2 == null)
            return false;
        if (список1.size() != список2.size())
            return false;
        for (int i = 0; i < список1.size(); i++)
            if (!Equaler.equals(список1.get(i), список2.get(i), сравниватель))
                return false;
        return true;
    }

    public static boolean equalsBase(Object a, Object b) {
        if (a == null)
            return b == null;
        return b == null ? false : a == b;
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null)
            return b == null;
        return b == null ? false : a.equalsIgnoreCase(b);
    }

}