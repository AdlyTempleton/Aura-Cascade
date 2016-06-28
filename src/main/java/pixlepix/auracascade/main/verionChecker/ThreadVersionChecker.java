package pixlepix.auracascade.main.verionChecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import pixlepix.auracascade.main.ConstantMod;

/**
 * Created by Bailey on 5/11/2016.
 * This class handles all version checking and in-game alerts.
 */
public class ThreadVersionChecker extends Thread{
    public ThreadVersionChecker() {
        setName("Aura Cascade Checking Thread");
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        try {
            URL url = new URL(ConstantMod.URL);
            BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
            VersionChecker.version = r.readLine();
            r.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
