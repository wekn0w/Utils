package core;

import utils.DateUtils;
import utils.Equaler;

import java.util.Date;

public interface IObkectComarator<T> {
    boolean равны(T один, T другой);

    IObkectComarator<Object> STANDART = new IObkectComarator<Object>() {
        public boolean равны(Object один, Object другой) {
            return Equaler.equals(один, другой);
        }
    };
    IObkectComarator<IWebObjectSId> BY_EQUALS = new IObkectComarator<IWebObjectSId>() {
        public boolean равны(IWebObjectSId один, IWebObjectSId другой) {
            return Equaler.equals(один, другой);
        }
    };

    IObkectComarator<IWebObjectSId> BY_ID = new IObkectComarator<IWebObjectSId>() {
        public boolean равны(IWebObjectSId один, IWebObjectSId другой) {
            if (один == null || другой == null)
                return Equaler.equals(один, другой);
            return Equaler.equals(один.getId(), другой.getId());
        }
    };

    IObkectComarator<IWithName> ByName = new IObkectComarator<IWithName>() {
        public boolean равны(IWithName один, IWithName другой) {
            if (один == null)
                return другой == null;
            return другой == null ? false : один.getNazvanie().equals(другой.getNazvanie());
        }
    };

    IObkectComarator<Date> ByDatesWithoutTime = new IObkectComarator<Date>() {
        public boolean равны(Date один, Date другой) {
            return DateUtils.получитьДатуБезВремениСуток(один)
                    .equals(DateUtils.получитьДатуБезВремениСуток(другой));
        }
    };
    IObkectComarator<String> ByStringIgnoreCase = new IObkectComarator<String>() {
        @Override
        public boolean равны(String один, String другой) {
            return один.equalsIgnoreCase(другой);
        }
    };

    IObkectComarator<Object> ByBaseCompare = new IObkectComarator<Object>() {
        @Override
        public boolean равны(Object один, Object другой) {
            return Equaler.equalsBase(один, другой);
        }
    };
}
