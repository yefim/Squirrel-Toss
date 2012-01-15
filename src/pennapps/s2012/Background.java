package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Background {
	final private float SLOW_CONSTANT = 0.5f;
	private float _x = 0, _y = 0;
	private GameView _gameView;
	private Bitmap _bmp;
	private int _width;
	private boolean _tileUp = false;
	private int _height;
	private Squirrel _leader;

	public Background(GameView gameView, Squirrel leader, Bitmap bmp) {
		_gameView = gameView;
		_bmp = bmp;
		_width = bmp.getWidth();
		_height = bmp.getHeight();
		_leader = leader;
		_y = PennApps2012Activity.screen_height - _height;
	}

	public Background(GameView gameView, Bitmap bmp, Squirrel leader,
			boolean tileUp) {
		this(gameView, leader, bmp);
		_tileUp = tileUp;
	}

	private void update() {
		if (_leader.isInFreeFall()) {
			if (!_leader.isDone())
				_x -= _leader.getXSpeed() / 4;

			_x %= _width;
			if (!_leader.isNearGround()) {
				_y -= _leader.getYSpeed() / 4;
				if (_tileUp) {
					_y %= _height;
				}
			}
		}
	}

	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(_bmp, _x, _y, null);
		if (_width + _x < _gameView.getWidth()) {
			canvas.drawBitmap(_bmp, _width + _x, _y, null);
		}
		if (_tileUp) {
			if (_y + _height > PennApps2012Activity.screen_height) {
				canvas.drawBitmap(_bmp, _x, _y - _height, null);
				canvas.drawBitmap(_bmp, _width + _x, _y - _height, null);
			} else {
				canvas.drawBitmap(_bmp, _x, _y + _height, null);
				canvas.drawBitmap(_bmp, _width + _x, _y + _height, null);
			}
		}
	}

	public float getY() {
		return _y;
	}

	public float getXSpeed() {
		return -_leader.getXSpeed();
	}

	public float getYSpeed() {
		return -_leader.getYSpeed();
	}
}
