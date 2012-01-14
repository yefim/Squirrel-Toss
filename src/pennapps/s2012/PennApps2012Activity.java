package pennapps.s2012;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PennApps2012Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}