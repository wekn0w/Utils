package utils;

import java.util.Arrays;
import java.util.Date;

public class DateUtils
{

    public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    public static final long NANOS_IN_A_DAY = 1000000 * 60 * 60 * 24;

    public static final String[] НАЗВАНИЯ_МЕСЯЦЕВ = {
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь",
            "Октябрь", "Ноябрь", "Декабрь"
    };

    public static final String[] НАЗВАНИЯ_МЕСЯЦЕВ_Р_ПАДЕЖ = {
            "Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября",
            "Октября", "Ноября", "Декабря"
    };

    @SuppressWarnings(value = "deprecation")
    public static int числоПолныхЛетМеждуДатами(Date с, Date по)
    {
        if (с == null || по == null)
            throw new RuntimeException("Дата null");
        int полныхЛет = по.getYear() - с.getYear();
        Date по2 = new Date(по.getTime());
        по2.setYear(с.getYear());
        return clearTime(по2).before(clearTime(с)) ? --полныхЛет : полныхЛет;
    }

    public static long числоПолныхДнейМеждуДатами(Date начало, Date конец)
    {
        try
        {
            return Math.abs(clearTime(начало).getTime() - clearTime(конец).getTime()) / 1000 / 3600 / 24;
        }
        catch (Throwable e)
        {
            return 0;
        }
    }
    
    public static int числоРабочихДнейМеждуДатами(Date f, Date t)
    {
        int c = сравнитьБезВремени(f, t) > 0 ? 1 : -1;
        long days = числоПолныхДнейМеждуДатами(f, t);
        int result = 0;
        for (int i = 0; i < days; ++i)
            if (isРабочийДень(f = добавитьНесколькоДней(f, c * i)))
                ++result;
        return result;
    }

    public static long числоДнейВПериодеВключаяГраницы(Date начало, Date конец)
    {
        if (начало == null || конец == null)
            throw new IllegalArgumentException("Начало и конец периода не могут быть null");
        if (начало.compareTo(конец) > 0)
            throw new IllegalArgumentException("Начало не может быть позже конца периода");
        Date чистоеНачало = clearTime(начало);
        Date чистыйКонец = clearTime(конец);
        long примерно = числоПолныхДнейМеждуДатами(чистоеНачало, чистыйКонец);
        if (примерно == 0)
            return 1;
        Date примерныйКонец = добавитьНесколькоДней(чистоеНачало, (int) примерно);
        if (clearTime(примерныйКонец).compareTo(чистыйКонец) == 0)
            return примерно + 1;
        if (clearTime(примерныйКонец).compareTo(чистыйКонец) > 0)
            return примерно;
        else return примерно + 2;
    }

    public static Date clearTime(Date now)
    {
        if (now == null)
            return null;
        Date clone = (Date) now.clone();
        resetTime(clone);
        return clone;
    }

    public static Date clearSeconds(Date now)
    {
        if (now == null)
            return null;
        Date clone = (Date) now.clone();
        resetSeconds(clone);
        return clone;
    }

    public static String getMonth_РПадеж(int номерМесяцаСНуля)
    {
        if (номерМесяцаСНуля <= 11 && номерМесяцаСНуля >= 0)
        {
            return НАЗВАНИЯ_МЕСЯЦЕВ_Р_ПАДЕЖ[номерМесяцаСНуля];
        }
        else
        {
            return "";
        }
    }

    public static String getMonth(int номерМесяцаСНуля)
    {
        if (номерМесяцаСНуля <= 11 && номерМесяцаСНуля >= 0)
        {
            return НАЗВАНИЯ_МЕСЯЦЕВ[номерМесяцаСНуля];
        }
        else
        {
            return "";
        }
    }

    /**
     * Возвращает номер месяца. Первый месяц начинается с цифры 0.
     * 
     * @param месяц название месяца
     * @return номер месяца
     */
    public static int getMonthНомер(String месяц)
    {
        return Arrays.asList(НАЗВАНИЯ_МЕСЯЦЕВ).indexOf(месяц);
    }

    public static String getДеньНеделиБуквенно(int номерДняНедели)
    {
        switch (номерДняНедели)
        {
            case 0:
                return "ПН";
            case 1:
                return "ВТ";
            case 2:
                return "СР";
            case 3:
                return "ЧТ";
            case 4:
                return "ПТ";
            case 5:
                return "СБ";
            case 6:
                return "ВС";
        }
        return "";
    }

    @SuppressWarnings(value = "deprecation")
    public static Date получитьДатуБезВремениСуток(Date дата)
    {
        if (дата == null)
            return null;
        Date новая = new Date(дата.getTime() / 1000 * 1000);
        новая.setMinutes(0);
        новая.setHours(0);
        новая.setSeconds(0);
        return новая;
    }

    @SuppressWarnings(value = "deprecation")
    public static Date началоМесяца(int номерМесяцаСНуля, int год)
    {
        int месяц1 = (номерМесяцаСНуля + 1) % 12 - 1;
        int год1 = год + (номерМесяцаСНуля + 1) / 12;
        return получитьДатуБезВремениСуток(new Date(год1 - 1900, месяц1, 1));
    }

    @SuppressWarnings(value = "deprecation")
    public static Date точныйКонецДня(int номерДня, int номерМесяцаСНуля, int год)
    {
        int месяц1 = (номерМесяцаСНуля + 1) % 12 - 1;
        int год1 = год + (номерМесяцаСНуля + 1) / 12;
        Date началоСледующего = добавитьНесколькоДней(получитьДатуБезВремениСуток(new Date(год1 - 1900,
                месяц1,
                номерДня)),
                1);
        return new Date(началоСледующего.getTime() - 1);
    }

    @SuppressWarnings(value = "deprecation")
    public static Date точныйКонецДня(Date дата)
    {
        if (дата == null)
            return null;
        Date безВремени = clearTime(дата);
        Date началоСледующего = добавитьНесколькоДней(безВремени, 1);
        return new Date(началоСледующего.getTime() - 1);
    }

    @SuppressWarnings(value = "deprecation")
    public static Date точноеНачалоДня(int номерДня, int номерМесяцаСНуля, int год)
    {
        int месяц1 = (номерМесяцаСНуля + 1) % 12 - 1;
        int год1 = год + (номерМесяцаСНуля + 1) / 12;
        return получитьДатуБезВремениСуток(new Date(год1 - 1900, месяц1, номерДня));
    }

    public static Date точныйКонецМесяца(int номерМесяцаСНуля, int год)
    {
        Date началоСледующего = началоМесяца(номерМесяцаСНуля + 1, год);
        return new Date(началоСледующего.getTime() - 1);
    }

    public static Date началоГодаВерное(int год)
    {
        return началоМесяца(0, год);
    }

    public static Date точныйКонецГода(int год)
    {
        Date началоСледующего = началоМесяца(12, год);
        return new Date(началоСледующего.getTime() - 1);
    }

    public static Date конецМесяца(int номерМесяцаСНуля, int год)
    {
        Date началоСледующего = началоМесяца(номерМесяцаСНуля + 1, год);
        return предыдущаяДатаБезСекунд(началоСледующего);
    }

    public static Date предыдущаяДатаБезСекунд(Date текущая)
    {
        return добавитьНесколькоДней(получитьДатуБезВремениСуток(текущая), -1);
    }

    @SuppressWarnings("deprecation")
    public static Date добавитьНесколькоСекунд(Date текущая, int числоСекунд)
    {
        if (текущая == null)
            return null;
        Date result = new Date(текущая.getYear(),
                текущая.getMonth(),
                текущая.getDate(),
                текущая.getHours(),
                текущая.getMinutes(),
                текущая.getSeconds() + числоСекунд);
        clearMillis(result);
        return result;
    }

    @SuppressWarnings("deprecation")
    public static Date добавитьНесколькоМинут(Date текущая, int числоМинут)
    {
        if (текущая == null)
            return null;
        Date result = new Date(текущая.getYear(),
                текущая.getMonth(),
                текущая.getDate(),
                текущая.getHours(),
                текущая.getMinutes() + числоМинут,
                текущая.getSeconds());
        clearMillis(result);
        return result;
    }

    @SuppressWarnings("deprecation")
    public static Date добавитьНесколькоДней(Date текущая, int числоДней)
    {
        if (числоДней == 0)
            return текущая;
        if (текущая == null)
            return null;
        Date result = new Date(текущая.getYear(),
                текущая.getMonth(),
                текущая.getDate() + числоДней,
                текущая.getHours(),
                текущая.getMinutes(),
                текущая.getSeconds());
        clearMillis(result);
        return result;
    }

    public static int числоКварталовМеждуДатами(Date датаОт, Date датаДо, boolean толькоПолных)
    {
        Date чистаяОт = clearTime(датаОт);
        Date чистаяДо = clearTime(датаДо);
        for (int i = 1;; i++)
        {
            Date черезIКварталов = endOfPeriodOfNMonths(чистаяОт, i * 3);
            if (черезIКварталов.compareTo(чистаяДо) > 0)
                if (толькоПолных)
                    return i - 1;
                else return i;
            if (черезIКварталов.compareTo(чистаяДо) == 0)
                return i;
        }
    }

    public static int числоМесяцевМеждуДатами(Date датаОт, Date датаДо, boolean толькоПолных)
    {
        Date чистаяОт = clearTime(датаОт);
        Date чистаяДо = clearTime(датаДо);
        for (int i = 1;; i++)
        {
            Date черезIМесяцев = endOfPeriodOfNMonths(чистаяОт, i);
            if (черезIМесяцев.compareTo(чистаяДо) > 0)
                if (толькоПолных)
                    return i - 1;
                else return i;
            if (черезIМесяцев.compareTo(чистаяДо) == 0)
                return i;
        }
    }

    @SuppressWarnings(value = "deprecation")
    public static int getКоличествоМесяцевНеПолныйЗаПолный(Date начало, Date конец)
    {
        int мес = (конец.getYear() - начало.getYear()) * 12 + конец.getMonth() - начало.getMonth();
        if (конец.getDate() > начало.getDate())
            мес++;
        return мес;
    }

    public static Date previousDay(Date date)
    {
        return добавитьНесколькоДней(date, -1);
    }

    @SuppressWarnings("deprecation")
    public static Date firstOfTheMonth(Date anyDayInMonth)
    {
        Date first = (Date) anyDayInMonth.clone();
        first.setDate(1);
        return first;
    }

    @SuppressWarnings("deprecation")
    public static Date lastDateOfMonth(Date anyDayInMonth)
    {
        Date firstOfNextMonth = firstOfNextMonth(anyDayInMonth);
        return previousDay(firstOfNextMonth);
    }

    private static long safeInMillis(Date date)
    {
        return date != null ? date.getTime() : 0;
    }

    @SuppressWarnings("deprecation")
    public static Date shiftDate(Date date, int shift)
    {
        Date result = (Date) date.clone();
        result.setDate(date.getDate() + shift);
        return result;
    }

    @SuppressWarnings("deprecation")
    public static void resetTime(Date date)
    {
        clearMillis(date);
        if (!(date instanceof java.sql.Date))
        {
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
        }
    }

    @SuppressWarnings("deprecation")
    public static void resetSeconds(Date date)
    {
        clearMillis(date);
        if (!(date instanceof java.sql.Date))
        {
            date.setSeconds(0);
        }
    }

    private static void clearMillis(Date date)
    {
        long milliseconds = safeInMillis(date);
        milliseconds = (milliseconds / 1000) * 1000;
        date.setTime(milliseconds);
    }

    @SuppressWarnings("deprecation")
    public static Date moveOneDayForward(Date date)
    {
        if (date == null)
            return null;
        date.setDate(date.getDate() + 1);
        return date;
    }

    @SuppressWarnings("deprecation")
    public static Date firstOfNextMonth(Date date)
    {
        Date firstOfNextMonth = null;
        if (date != null)
            firstOfNextMonth = new Date(date.getYear(), date.getMonth() + 1, 1);
        return firstOfNextMonth;
    }

    @SuppressWarnings("deprecation")
    public static Date addMonth(Date thisDate, int month)
    {
        Date date = clearTime(thisDate);
        Date firstOfNextMonth = null;
        if (date != null)
        {
            int year = date.getYear() + month / 12;
            firstOfNextMonth = new Date(year, date.getMonth() + month % 12, 1);

            if (lastDateOfMonth(firstOfNextMonth).getDate() < date.getDate())
                firstOfNextMonth = lastDateOfMonth(new Date(year, date.getMonth() + month % 12, 1));
            else firstOfNextMonth = new Date(year, date.getMonth() + month % 12, date.getDate());
        }
        if (firstOfNextMonth != null && firstOfNextMonth.getMonth() == date.getMonth()
                && firstOfNextMonth.getYear() == date.getYear())
            firstOfNextMonth = shiftDate(firstOfNextMonth, (int) Math.signum(month));
        return firstOfNextMonth;
    }

    @SuppressWarnings("deprecation")
    public static Date addMonthДляСрокаСтрахования(Date date, int month)
    {
        Date addMonth = addMonth(date, month);
        if (date.getDate() == addMonth.getDate())
            addMonth = добавитьНесколькоДней(addMonth, -1);
        return addMonth;
    }

    @SuppressWarnings("deprecation")
    public static Date addYears(Date date, int years)
    {
        Date firstOfNextMonth = null;
        if (date != null)
        {
            firstOfNextMonth = clearTime(new Date(date.getYear() + years,
                    date.getMonth(),
                    date.getDate()));
            if (is29Февраля(date))
                return previousDay(firstOfNextMonth);
        }
        return firstOfNextMonth;
    }

    public static Date addYearMinusDay(Date date)
    {
        Date addMonth = addMonthДляСрокаСтрахования(date, 12);
        return addMonth;
    }

    public static boolean isNow(Date date)
    {
        Date now = new Date();
        resetTime(now);
        resetTime(date);
        return Equaler.equals(now, date);
    }

    @SuppressWarnings("deprecation")
    public static Date getFirstDayOfYear(Date date)
    {
        Date date1 = new Date(date.getYear(), 0, 1);
        return date1;
    }

    @SuppressWarnings("deprecation")
    public static Date getLastDayOfYear(Date date)
    {
        Date date1 = new Date(date.getYear(), 11, 31);
        return date1;
    }

    public static Long getNowYear()
    {
        return getYear(new Date());
    }

    @SuppressWarnings("deprecation")
    public static Long getYear(Date date)
    {
        if (date == null)
            return null;
        return Long.valueOf(date.getYear() + 1900);
    }

    public static int сравнитьБезВремени(Date d1, Date d2)
    {
        if (d1==null && d2 == null)
            return 0;
        if (d1==null)
            return 1;
        if (d2==null)
            return -1;
        Date date1 = clearTime(d1);
        Date date2 = clearTime(d2);
        return date1.compareTo(date2);
    }

    public static int сравнитьБезСекунд(Date d1, Date d2)
    {
        Date date1 = clearSeconds(d1);
        Date date2 = clearSeconds(d2);
        return date1.compareTo(date2);
    }

    private static int междуКоэфициент(Date d, Date d1, Date d2)
    {
        Date nd = clearTime(d);
        Date nd1 = d1 != null ? clearTime(d1) : new Date(Long.MIN_VALUE);
        Date nd2 = d2 != null ? clearTime(d2) : new Date(Long.MAX_VALUE);

        return nd1.compareTo(nd) * nd.compareTo(nd2);
    }

    public static boolean междуБезВремени(Date d, Date d1, Date d2)
    {
        return междуКоэфициент(d, d1, d2) > 0;
    }

    public static boolean включительноМеждуБезВремени(Date d, Date d1, Date d2)
    {
        return междуКоэфициент(d, d1, d2) >= 0;
    }

    public static Date endOfPeriodOfNMonths(Date startOfPeriod, int months)
    {
        Date endOfPeriod = addMonth(startOfPeriod, months);
        if (endOfPeriod.getDate() < startOfPeriod.getDate())
            return endOfPeriod;
        return previousDay(endOfPeriod);
    }

    private static int разницаВМесяцах(Date start, Date end)
    {
        start = clearTime(start);
        end = clearTime(end);

        int m1 = start.getYear() * 12 + start.getMonth();
        int m2 = end.getYear() * 12 + end.getMonth();

        return m2 - m1;
    }

    public static int точноеКоличествоМесяцевМеждуДвумяДатамиОкруглВверх(Date start, Date end)
    {
        if (start == null || end == null)
            return 0;

        int p = разницаВМесяцах(start, end);
        if (end.getDate() > start.getDate())
            p++;

        return p;
    }

    public static int точноеПолноеКоличествоМесяцевМеждуДвумяДатами(Date start, Date end)
    {
        int p = разницаВМесяцах(start, end);
        if (p > 0 && end.getDate() < start.getDate())
            p--;

        return p;
    }

    public static int числоПолныхЛетМеждуДатамиN(Date с, Date по)
    {
        if (с == null || по == null)
            throw new RuntimeException("Дата null");
        int полныхЛет = по.getYear() - с.getYear();
        Date по2 = new Date(по.getTime());
        по2.setDate(с.getDate());
        по2.setMonth(с.getMonth());
        return clearTime(по).before(clearTime(по2)) ? --полныхЛет : полныхЛет;
    }

    @SuppressWarnings("deprecation")
    public static int числоНеполныхЛетМеждуДатами(Date с, Date по)
    {
        if (с == null || по == null)
            throw new RuntimeException("Дата null");
        int полныхЛет = по.getYear() - с.getYear();
        Date по2 = new Date(по.getTime());
        по2.setDate(с.getDate());
        по2.setMonth(с.getMonth());
        return clearTime(по2).compareTo(clearTime(по)) < 0 ? ++полныхЛет : полныхЛет;
    }

    public static Date addMinutes(Date date, int minutes)
    {
        if (date == null)
            return null;
        return new Date(date.getTime() + minutes * 60000);
    }

    public static Date min(Date... dates)
    {
        Date min = null;
        for (Date date : dates)
        {
            if (date == null)
                continue;
            if (min == null)
                min = date;
            else
            {
                if (min.compareTo(date) > 0)
                    min = date;
            }
        }
        return min;
    }

    public static Date max(Date... dates)
    {
        Date max = null;
        for (Date date : dates)
        {
            if (date == null)
                continue;
            if (max == null)
                max = date;
            else
            {
                if (max.compareTo(date) < 0)
                    max = date;
            }
        }
        return max;
    }

    @SuppressWarnings("deprecation")
    public static Date получитьДатуБезСекунд(Date date)
    {
        if (date == null)
        {
            return null;
        }
        else
        {
            Date result = new Date(date.getTime());
            result.setSeconds(0);
            return result;
        }
    }

    @SuppressWarnings("deprecation")
    public static Date setЧасы(Date date, int часы)
    {
        if (date != null)
        {
            date.setHours(часы);
        }
        return date;
    }

    @SuppressWarnings("deprecation")
    public static Date setМинуты(Date date, int минуты)
    {
        if (date != null)
        {
            date.setMinutes(минуты);
        }
        return date;
    }

    public static boolean isСрокПопадаетНаВисокосныйГод(Date датаС, Date датаПо)
    {
        if (датаС.getYear() == датаПо.getYear())
        {
            if (!isВисокосныйГод(датаС.getYear())) { return false; }

            if (сравнитьБезВремени(датаПо, new Date(датаС.getYear(), 1, 29)) < 0) { return false; }

            if (сравнитьБезВремени(датаС, new Date(датаПо.getYear(), 1, 29)) >= 0) { return false; }

            return true;
        }
        else if (isВисокосныйГод(датаС.getYear())
                && (сравнитьБезВремени(датаС, new Date(датаС.getYear(), 1, 29)) <= 0))
        {
            return true;
        }
        else if (isВисокосныйГод(датаПо.getYear())
                && (сравнитьБезВремени(датаПо, new Date(датаПо.getYear(), 1, 29)) >= 0))
        {
            return true;
        }
        else
        {
            for (int year = датаС.getYear() + 1; year < датаПо.getYear(); year++)
            {
                if (isВисокосныйГод(year)) { return true; }
            }
        }
        return false;
    }

    public static boolean isВисокосныйГод(int год)
    {
        return (год % 4 == 0) && ((год % 100 != 0) || (год % 400 == 0));
    }

    private static boolean isРабочийДень(Date date)
    {
        int деньНедели = date.getDay();
        return деньНедели > 0 && деньНедели < 6;
    }

    public static Date добавитьНесколькоРабочихДней(Date date, int days)
    {
        if (days == 0 || date == null)
            return date;
        int cnt = Math.abs(days);
        while (cnt > 0)
        {
            date = добавитьНесколькоДней(date, days > 0 ? 1 : -1);
            if (isРабочийДень(date))
                cnt--;
        }
        return date;
    }

    public static boolean is29Февраля(Date date)
    {
        return (date.getDate() == 29) && (date.getMonth() == 1);
    }
    
    public static void установитьВремяИзВ(Date из,Date в)
    {
        в.setMinutes(из.getMinutes());
        в.setHours(из.getHours());
        в.setSeconds(из.getSeconds());
    }
}