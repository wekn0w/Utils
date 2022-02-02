package utils;

import core.IObkectComarator;
import core.WebObjectSId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class LCollections<T> {
    private IObkectComarator<? super T> сравниватель = IObkectComarator.STANDART;

    public LCollections(IObkectComarator<? super T> сравниватель) {
        this.сравниватель = сравниватель;
    }

    public static <T> List<T> сделатьЛист(T... object) {
        List<T> list = new ArrayList<T>();
        if (object == null)
            return list;
        for (int i = 0; i < object.length; i++) {
            list.add(object[i]);
        }
        return list;
    }

    public static <T> List<T> листИзОдного(T объект) {
        List<T> result = new ArrayList<T>(1);
        result.add(объект);
        return result;
    }

    public static <T> boolean isПусто(List<T> коллекция) {
        return коллекция == null || коллекция.isEmpty();
    }

    public static <T> boolean isПусто(T... коллекция) {
        return коллекция == null || коллекция.length == 0;
    }

    public static <T extends WebObjectSId> void replaceOrAddПоэлементно(List<T> гдеОбновляемИлиДобавляем,
                                                                        List<T> чтоОбновляем) {
        for (Iterator<T> iter = чтоОбновляем.iterator(); iter.hasNext(); ) {
            T объект = iter.next();
            LCollections.replaceOrAdd(гдеОбновляемИлиДобавляем, объект);
        }
    }

    public static <T extends WebObjectSId> void replaceOrAdd(List<T> list, T obj) {
        if (!replace(list, obj))
            list.add(obj);
    }

    public static <T extends WebObjectSId> boolean replace(List<T> list, T obj) {
        return new LCollections<T>(IObkectComarator.BY_EQUALS).заменить(list, obj);
    }

    public static List getПервыеNЭлементов(List list, int count) {
        if (list == null)
            return null;
        if (list.isEmpty())
            return list;
        int n = count + 1;
        if (n < 0)
            n = 0;
        if (n > list.size())
            n = list.size();
        return list.subList(0, n);
    }

    public static <T extends WebObjectSId> void replaceOrAddПоId(List<T> list, T obj) {
        if (!replaceПоId(list, obj))
            list.add(obj);
    }

    public static <T extends WebObjectSId> boolean replaceПоId(List<T> list, T obj) {
        return new LCollections<T>(IObkectComarator.BY_ID).заменить(list, obj);
    }

    public boolean заменить(List<T> list, T obj) {
        int index = 0;
        for (T element : list) {
            if (сравниватель.равны(element, obj)) {
                list.set(index, obj);
                return true;
            }
            index++;
        }
        return false;
    }

    public void setСравниватель(IObkectComarator<? super T> сравниватель) {
        this.сравниватель = сравниватель;
    }

    public static <T> boolean спискиОдинаковыСУчетомПорядка(List<T> lhs,
                                                            List<T> rhs,
                                                            Comparator<T> comparator) {
        if (lhs.size() != rhs.size())
            return false;
        int i = 0;
        for (Iterator<T> iter = lhs.iterator(); iter.hasNext(); ) {
            T element = iter.next();
            if (comparator.compare(element, rhs.get(i++)) != 0)
                return false;
        }
        return true;
    }

    public T getЭлемент(Collection<T> список, T объект, boolean удалять) {
        for (Iterator<T> iter = список.iterator(); iter.hasNext(); ) {
            T element = iter.next();
            if (сравниватель.равны(element, объект)) {
                if (удалять)
                    iter.remove();
                return element;
            }
        }
        return null;
    }

    public static <T> List<T> копировать(List<T> исходный) {
        return new ArrayList<T>(исходный);
    }

    // не кидает indexOutOfBounds. Выбирает элементы [indexFrom, indexTo), пока
    // индекс не выйдет
    // за пределы массива
    public static <T> List<T> subList(List<T> list, int indexFrom, int indexTo) {
        List<T> result = new ArrayList<T>();
        int size = list.size();

        if (indexFrom >= size)
            return result;

        for (int i = indexFrom; i < indexTo && i < size; ++i)
            result.add(list.get(i));

        return result;
    }

    public static <T> boolean isAllNotNull(T... перечисление) {
        for (T t : перечисление)
            if (t == null)
                return false;
        return true;
    }

    public static <T> T getLast(List<T> list) {
        if (list == null || list.isEmpty())
            return null;
        return list.get(list.size() - 1);
    }

    public static <T> boolean isПусто(Collection<T> коллекция) {
        return коллекция == null || коллекция.isEmpty();
    }

    public static <T> boolean isХотьОдинЭлементПервогоСодержитсяВоВтором(List<T> первый,
                                                                         List<T> второй) {
        for (T element : первый) {
            if (второй.contains(element)) {
                return true;
            }
        }
        return false;
    }

}