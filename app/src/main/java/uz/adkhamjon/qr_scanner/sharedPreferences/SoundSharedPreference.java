package uz.adkhamjon.qr_scanner.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SoundSharedPreference {
    SharedPreferences prefs;
    private static SoundSharedPreference sharePreference;
    SharedPreferences.Editor editor;

    public static SoundSharedPreference getInstance(Context context) {
        if (sharePreference != null)
            return sharePreference;
        else return sharePreference = new SoundSharedPreference(context);
    }

    private SoundSharedPreference(Context context) {
        prefs = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
    }

    public void setLang(String lang) {
        editor = prefs.edit();
        editor.putString("lang", lang);
        editor.apply();
    }

    public String getLang() {
        return prefs.getString("lang", "en");
    }

    public void setTarget(boolean target) {
        editor = prefs.edit();
        editor.putBoolean("target", target);
        editor.apply();
    }

    public boolean getTarget() {
        return prefs.getBoolean("target", false);
    }

    public void setHasLang(boolean hasLang) {
        editor = prefs.edit();
        editor.putBoolean("hasLang", hasLang);
        editor.apply();
    }

    public boolean getHaslang() {
        return prefs.getBoolean("hasLang", false);
    }
}
