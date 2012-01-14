package pennapps.s2012;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class HomeScreenView extends SurfaceView implements SurfaceHolder.Callback  {

	private MainThread _thread;
	
	public HomeScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.getHolder().addCallback(this);
		
		_thread = new MainThread(this.getHolder(), this);
		
		this.setBackgroundResource(R.drawable.bg_big);
		this.setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		_thread.setRunning(true);
		_thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// for a clean shut down of the thread
		// blocks the thread and waits for it to die
		boolean retry = true;
		while (retry) {
			try {
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// keep the while loop running
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("HomeScreen",event.getX() + ", " + event.getY());
		//if (event.getAction() == MotionEvent.ACTION_UP) {
		//	Log.d("HomeScreen","Swiped UP!");
		_thread.setRunning(false);
		return super.onTouchEvent(event);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		
	}
}
