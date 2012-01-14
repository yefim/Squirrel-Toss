package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Display;

public class Background {
	private float _x = 0, _y = 0;
	private float _xSpeed = 0, _ySpeed = 0;
	private GameView _gameView;
	private Bitmap _bmp;
	private int _width;
	private boolean _tileUp = false;
	private int _height;
	public Background(GameView gameView, Bitmap bmp) {
		_gameView = gameView;
		_bmp = bmp;
		_width = bmp.getWidth();
		_height = bmp.getHeight();
		_y = PennApps2012Activity.screen_height - _height;
	}

	public Background(GameView gameView, Bitmap bmp, boolean tileUp) {
		this(gameView, bmp);
		_tileUp = tileUp;
	}

	private void update() {
		_x += _xSpeed;
		_x %= _width;
		_y += _ySpeed;
		if (_tileUp){
			if(_y > _height){
				Log.d("Background", "y: "+_y + "x: "+_x);
				_y %= _height;
				Log.d("Background", "y: "+_y + "x: "+_x);
			}
		}
	}

	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(_bmp, _x, _y, null);
		if (_width + _x < _gameView.getWidth())
			canvas.drawBitmap(_bmp, _width + _x, _y, null);
		if (_tileUp) {
			canvas.drawBitmap(_bmp, _x, _y + - PennApps2012Activity.screen_height, null);
			canvas.drawBitmap(_bmp, _width + _x, _y + - PennApps2012Activity.screen_height, null);
		}

	}

	public void setSpeed(float xSpeed, float ySpeed) {
		_xSpeed = xSpeed;
		_ySpeed = ySpeed;
	}
}
