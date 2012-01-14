package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {
	private float _x = 0, _y = 0;
	private float _xSpeed, _ySpeed;
	private GameView _gameView;
	private Bitmap _bmp;
	private int _width, _height;
	private boolean _inFreeFall;
	// dv = dt * a  
	private final float GRAVITY_DROP = 9.8f/GameThread.FPS;

	public Sprite(GameView gameView, Bitmap bmp) {
		_gameView = gameView;
		_bmp = bmp;
		_width = bmp.getWidth();
		_height = bmp.getHeight();
	}
	
	public void setPosition(float x, float y) {
		float deltaX = x - _width/2 - _x;
		float deltaY = y - _width/2 - _y;
		_x += deltaX;
		_y += deltaY;
		_xSpeed = deltaX;
		_ySpeed = deltaY;
		_inFreeFall = false;
		
	}
	
	public void inFreeFall(){
		_inFreeFall = true;
	}

	private void update() {
		if (_x > _gameView.getWidth() - _bmp.getWidth() - _xSpeed) {
			_xSpeed = 0;
			_ySpeed = 0;
		}
		if (_x + _xSpeed < 0) {
			_xSpeed = 0;
			_ySpeed = 0;
		}
		_x += _xSpeed;
		_y += _ySpeed;
		accelerate();
	}
	private void accelerate() {
		this._ySpeed += GRAVITY_DROP;
	}
	public void onDraw(Canvas canvas) {
		if(_inFreeFall)
			update();
		canvas.drawBitmap(_bmp, _x, _y, null);
	}

	public float getSpeed() {
		return _xSpeed;
	}
}