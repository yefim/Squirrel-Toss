package pennapps.s2012;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.view.View.OnClickListener;

public class GameActivity extends Activity implements OnClickListener {
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector _gestureDetector;
	View.OnTouchListener _gestureListener;
	private GameView _gameView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		_gestureDetector = new GestureDetector(new MyGestureDetector());
		_gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return _gestureDetector.onTouchEvent(event);
			}
		};
		
		_gameView = new GameView(this);
		this.setContentView(_gameView);
		_gameView.setOnClickListener((OnClickListener) GameActivity.this);
		_gameView.setOnTouchListener(_gestureListener);
		
	}
	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH)
					return false;
				// top to bottom swipe or downward swipe
				if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					_gameView.getSprite().tiltUp();
					Log.d("GameActivity", "SWIPED UP!");
				} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					_gameView.getSprite().tiltDown();
					Log.d("GameActivity", "swiped down :(");
				}
					
			} catch (Exception e) {
				// do nothing
			}
			return false;
		}
	}
	@Override
	public void onClick(View v) {
	}
}
