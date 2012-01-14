package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
	private float _x = 0, _y = 0;
	private float _xSpeed = 0, _ySpeed = 0;
	private GameView _gameView;
	private Bitmap _bmp;
	private int _width;
	private boolean _tileUp=false;
	private int _height;

	public Background(GameView gameView, Bitmap bmp) {
		_gameView = gameView;
		_bmp = bmp;
		_width = bmp.getWidth();
		_height = bmp.getHeight();
	}
	
	public Background(GameView gameView, Bitmap bmp, boolean tileUp) {
		_gameView = gameView;
		_bmp = bmp;
		_width = bmp.getWidth();
		_tileUp = tileUp;
	}

	private void update() {
		_x += _xSpeed;
		_x %= _width;
		_y += _ySpeed;
		if(_tileUp)
			_y %= _height;
	}

	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(_bmp, _x, 0, null);
		if(_width+_x<_gameView.getWidth())
			canvas.drawBitmap(_bmp, _width+_x, 0, null);
		if(_tileUp && _height+_y<_gameView.getHeight())
			canvas.drawBitmap(_bmp, 0, _height+_y, null);
		
	}
	
	public void setSpeed(float xSpeed, float ySpeed) {
		_xSpeed = xSpeed;
		_ySpeed = ySpeed;
	}
}
