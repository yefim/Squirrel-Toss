package pennapps.s2012;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(new GameView(this));

	}
}
