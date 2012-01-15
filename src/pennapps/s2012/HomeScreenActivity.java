package pennapps.s2012;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreenActivity extends Activity implements OnClickListener {
	public static int screen_height;
	public static int screen_width;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		screen_height = displaymetrics.heightPixels;
		screen_width = displaymetrics.widthPixels;

		this.setContentView(R.layout.home_screen_view);
		Button play = (Button) this.findViewById(R.id.btn_play);
		play.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(v.getContext(), GameActivity.class);
		startActivity(intent);
	}
}