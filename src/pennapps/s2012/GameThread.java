package pennapps.s2012;

import java.util.ArrayList;
import java.util.Random;

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
		Random r = new Random();
		while (_running) {
			Canvas c = null;
			startTime = System.currentTimeMillis();
			if (_view.getSquirrel().isInFreeFall() && r.nextInt(100) < 15)
				_view.addAcorn();
			ArrayList<Acorn> acorns = _view.getAcorns();
			for (int i = 0; i < acorns.size(); i++) {
				acorns.get(i).setBackgroundVelocity(
						_view.getBackgroundXSpeed(),
						_view.getBackgroundYSpeed());
				if (_view.getSquirrel().intersects(acorns.get(i))) {
					_view.acornEaten();
					acorns.remove(i);
					i -= 1;
				} else if (!acorns.get(i).onScreen()) {
					acorns.remove(i);
					i--;
				}
			}
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
