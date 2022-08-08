package anchig.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class MinerTotalManager {

    public final static long getDays() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 7, 20);
        final long diff = calendar.getTime().getTime() - new Date().getTime();

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
