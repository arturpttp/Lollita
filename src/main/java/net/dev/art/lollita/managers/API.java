package net.dev.art.lollita.managers;

import java.util.concurrent.TimeUnit;

public class API {

    public static int getInt(String n) {
        try {
            return Integer.parseInt(n);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getTime(int amout, TimeUnit unit) {
        return unit == TimeUnit.SECONDS ? amout * 1000 : unit == TimeUnit.MINUTES ? (amout * 1000) * 60 : unit == TimeUnit.HOURS ? ((amout * 1000) * 60) * 60 : amout;
    }

}
