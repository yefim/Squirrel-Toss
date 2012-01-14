package pennapps.s2012;

import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	private boolean _running;
	private SurfaceHolder _surfaceHolder;
	private HomeScreenView _homeScreen;
	private static final String TAG = MainThread.class.getSimpleName();
	
	public MainThread(SurfaceHolder surfaceHolder, HomeScreenView homeScreen) {
		super();
		_surfaceHolder = surfaceHolder;
		_homeScreen = homeScreen;
	}
	public void setRunning(boolean running) {
		_running = running;
	}
	@Override
	public void run() {
		long tickCount = 0L;
		Log.d(TAG, "Starting game loop");
		while (_running) {
			tickCount += 1;
			//System.out.println("testing main thread");
			// update game state
			// render state to the screen
		}
		Log.d(TAG, "Game loop executed " + tickCount + " times");
	}
}
