package pennapps.s2012;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.util.AttributeSet;
import android.util.Log;

public class PennApps2012Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
        XmlPullParser parser = this.getResources().getXml(R.layout.home_screen_view);
        
        //this.setContentView(hs);
        this.setContentView(R.layout.home_screen_view);
    }
}