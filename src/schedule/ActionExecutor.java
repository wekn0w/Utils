package schedule;

import java.util.Date;

public class ActionExecutor
{
    public void execute()
    {
        try
        {
            System.out.println("Action complete at "+new Date());
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
