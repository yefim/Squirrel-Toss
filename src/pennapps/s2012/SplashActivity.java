package pennapps.s2012;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
	boolean _active = true;
	int _splashTime = 3000;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while(_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this, PennApps2012Activity.class); 
					startActivity(intent);
					stop();
				}
			}
		};
		splashThread.start();
	}
	
}
