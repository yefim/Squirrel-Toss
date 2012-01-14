package pennapps.s2012;

import android.graphics.Canvas;

public class GameThread extends Thread {
	static final long FPS = 60;
	private GameView _view;
	private boolean _running = false; 

	public GameThread(GameView view) {
		_view = view;
	}

	public void setRunning(boolean run) {
		_running = run;
	}

	@Override
	public void run() {
		long ticksPS = 1000 / FPS, startTime, sleepTime;
		while (_running) {
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try {
				c = _view.getHolder().lockCanvas();
				synchronized (_view.getHolder()) {
					_view.onDraw(c);
				}
			} finally {
				if (c != null) {
					_view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0)
					sleep(sleepTime);
				else
					sleep(10);
			} catch (Exception e) {
			}

		}
	}
}
