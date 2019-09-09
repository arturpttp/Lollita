package net.dev.art.lollita.managers;

public class StringManager {

    public static boolean contains(String[] args, String needed) {
        for (String arg : args) {
            if (arg.contains(needed))
                return true;
        }
        return false;
    }


}
