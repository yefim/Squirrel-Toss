package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class Sprite {
	private float _x = 0, _y = 0;
	//private float _xSpeed, _ySpeed;
	private double _velocity;
	private GameView _gameView;
	private Bitmap _bmp;
	private Matrix _matrix;
	private int _width, _height;
	private boolean _inFreeFall;
	private double _angle_radians = 0.0f;
	// dv = dt * a
	private final double GRAVITY_CONSTANT = 4.9;
	private final double GRAVITY_DROP = GRAVITY_CONSTANT/GameThread.FPS;
	private final double TILT_ANGLE_RADIANS = 1.0;
	private final double BOOST = 1.0;
	private final double SLOW_DOWN = 0.005;

	public Sprite(GameView gameView, Bitmap bmp) {
		_gameView = gameView;
		_bmp = bmp;
		_width = bmp.getWidth();
		_height = bmp.getHeight();
		_matrix = new Matrix();
	}
	
	public void setPosition(double x, double y) {
		double deltaX = x - _width/2 - _x;
		double deltaY = y - _width/2 - _y;
		_x += deltaX;
		_y += deltaY;
		
		_velocity = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		_angle_radians = Math.atan2(deltaY, deltaX); 
		
		_inFreeFall = false;
		
	}
	
	public void inFreeFall(){
		_inFreeFall = true;
	}

	private void update() {
		move();
		accelerate_due_to_gravity();
		decelerate();
		rotate();
	}
	private void move() {
		double xVelocity = _velocity*Math.cos(_angle_radians);
		double yVelocity = _velocity*Math.sin(_angle_radians);
		
		if (_x > _gameView.getWidth() - _bmp.getWidth() - xVelocity) {
			this._inFreeFall = false;
			_velocity = 0;
		}
		if (_x + xVelocity < 0) {
			this._inFreeFall = false;
			_velocity = 0;
		}
		_x += xVelocity;
		_y += yVelocity;
	}
	public void tiltUp() {
		_angle_radians -= TILT_ANGLE_RADIANS;
	}
	public void tiltDown() {
		_angle_radians += TILT_ANGLE_RADIANS;
	}
	public void boost() {
		_velocity += BOOST;
	}
	private void accelerate_due_to_gravity() {
		// break up velocity into X and Y, reduce Y velocity,
		// then combine them again and adjust the angle
		double xVelocity = _velocity*Math.cos(_angle_radians);
		double yVelocity = _velocity*Math.sin(_angle_radians);
		yVelocity += GRAVITY_DROP;
		_velocity = Math.sqrt(xVelocity*xVelocity + yVelocity*yVelocity);
		_angle_radians = Math.atan2(yVelocity, xVelocity);
	}
	
	private void decelerate() {
		if (_velocity <= 0) return;
		_velocity -= SLOW_DOWN;
	}
	
	public void onDraw(Canvas canvas) {
		if(_inFreeFall) {
			update();	
			canvas.drawBitmap(_bmp, _matrix, null);
		} else {
			canvas.drawBitmap(_bmp, _x, _y, null);
		}
	}
	
	private void rotate() {
		float rotation_degrees = (float)Math.toDegrees(_angle_radians);
		_matrix.setRotate(rotation_degrees,_bmp.getWidth()/2,_bmp.getHeight()/2);
		_matrix.postTranslate(_x,_y);
	}
	
	public float getYSpeed() {
		return (float) (_velocity*Math.sin(_angle_radians));
	}
	
	public boolean isInFreeFall() {
		return this._inFreeFall;
	}
	
	public float getXSpeed() {
		return (float) (_velocity*Math.cos(_angle_radians));
	}
}