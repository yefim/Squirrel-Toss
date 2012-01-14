package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
	private float _x = 0;
	private float _speed = 0;
	private GameView _gameView;
	private Bitmap _bmp;
	private int _width;

	public Background(GameView gameView, Bitmap bmp) {
		_gameView = gameView;
		_bmp = bmp;
		_width = bmp.getWidth();
	}

	private void update() {
		/*if (_x > _gameView.getWidth() - _bmp.getWidth() - _speed) {
			_speed = 0;
		}
		if (_x + _speed < 0) {
			_speed = 0;
		}*/
		_x += _speed;
	}

	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(_bmp, _x, 0, null);
	}

	public void setSpeed(float speed) {
		_speed = speed;
	}
}
