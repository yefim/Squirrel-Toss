package pennapps.s2012;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;

public class PennApps2012Activity extends Activity implements OnClickListener, OnKeyListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //this.setContentView(R.layout.home_screen_view);
        this.setContentView(R.layout.home_screen_view);
        Button play = (Button) this.findViewById(R.id.btn_play);
        play.setOnClickListener(this);
    }
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void onClick(View v) {
		Log.d("Clicked","in click");
		Intent intent = new Intent();
        intent.setClass(v.getContext(), GameActivity.class);
        startActivity(intent);

	}
}