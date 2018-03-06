package parisnanterre.fr.lexify.settings;

public class Settings {

    public static boolean chrono_enabled;

    public Settings(){
        chrono_enabled = true;
    }

    public boolean isChrono_enabled() {
        return chrono_enabled;
    }

    public void setChrono_enabled(boolean chrono_enabled) {
        this.chrono_enabled = chrono_enabled;
    }
}
