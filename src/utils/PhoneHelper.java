package utils;

public class PhoneHelper
{

    public String trimNumeric(String телефон)
    {
        if (телефон == null)
            return null;
        StringBuilder b = new StringBuilder();
        char[] charArray = телефон.toCharArray();
        for (char c : charArray)
        {
            if (isNumeric(c))
                b.append(c);
        }

        if (b.length() == 11 && b.charAt(0) == '8')
            return "7" + b.substring(1);

        if (b.length() == 0)
            return null;
        return b.toString();
    }

    private boolean isNumeric(char c)
    {
        return (c >= '0' && c <= '9');
    }
}
