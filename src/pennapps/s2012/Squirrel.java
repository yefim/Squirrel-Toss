package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

public class Squirrel extends Sprite {
	private int _fuel = 100;
	private boolean _inFreeFall, _catchingUp, _nearGround = true;
	// dv = dt * a
	private final double GRAVITY_CONSTANT = 4.9;
	private final double GRAVITY_DROP = GRAVITY_CONSTANT / GameThread.FPS;
	private final double TILT_ANGLE_RADIANS = 0.1;
	private final double BOOST = 1.0;
	private final double SLOW_DOWN = 0.01;
	private final float POS_X = (float) (PennApps2012Activity.screen_width / 4.0);
	private final float POS_Y = (float) (PennApps2012Activity.screen_height / 2.0);
	private final double MAX_MIN_ANGLE = 65.0;
	private float _bottom;
	private boolean _done;
	private boolean _stop;
	static final double CATCH_UP_SPEED = 5.0;

	public Squirrel(Bitmap bmp) {
		super(bmp);
		_x = 0;
		_y = PennApps2012Activity.screen_height-80;
	}

	public boolean intersects(Sprite other) {
		int height = this._bmp.getHeight();
		int width = this._bmp.getWidth();
		int pos_x = (int) POS_X;
		int pos_y = (int) POS_Y;
		int oneFourthHeight = height / 4;
		int oneFourthWidth = width / 4;

		Rect thisRect = new Rect(pos_x + oneFourthWidth, pos_y
				+ oneFourthHeight, pos_x + width - oneFourthWidth, pos_y
				+ height - oneFourthHeight);
		Rect otherRect = new Rect((int) other._x, (int) other._y,
				(int) other._x + _width, (int) other._y + _height);

		return Rect.intersects(thisRect, otherRect);
	}

	public void setPosition(double x, double y) {
		double deltaX = x - _width / 2 - _x;
		double deltaY = y - _width / 2 - _y;
		_x += deltaX;
		_y += deltaY;

		_velocity = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		_angle_radians = Math.atan2(deltaY, deltaX);

		_inFreeFall = false;
	}

	public void inFreeFall() {
		_inFreeFall = true;
		_x = POS_X;
		_y = POS_Y;
	}

	public void update() {
		move();
		accelerate_due_to_gravity();
		decelerate();
		rotate();
		if (_bottom < 400)
			_nearGround = true;
		else
			_nearGround = false;
		if (_y <= POS_Y)
			_nearGround = false;
		Log.d("Squirrel", "Bottom: "+_bottom);
	}

	public boolean isNearGround() {
		return _nearGround;
	}

	public void setBottom(float bottom) {
		_bottom = bottom;
	}

	public void move() {
		if (_done) {
			_x += getXSpeed() / 8;
			Log.d("Squirrel", "about to slow");
			_velocity -= 8;
			if (_velocity < 0)
				_stop = true;
		}
		_x += _velocity * Math.cos(_angle_radians) / 4;
		_y += _velocity * Math.sin(_angle_radians) / 4;
		if (!_done && _y >= 630) {
			_y = 630;
			_x = POS_X;
			_angle_radians = 0.0;
			_done = true;
		}
	}

	public void tiltUp() {
		if (_done || _angle_radians <= Math.toRadians(-MAX_MIN_ANGLE))
			return;
		_angle_radians -= TILT_ANGLE_RADIANS;
		_fuel -= 1;
	}

	public void tiltDown() {
		if (_done || _angle_radians >= Math.toRadians(MAX_MIN_ANGLE))
			return;
		_angle_radians += TILT_ANGLE_RADIANS;
		_fuel -= 1;
	}
	public void increaseVelocity() {
		_velocity += 60 * SLOW_DOWN;
	}
	
	public void boost() {
		_velocity += BOOST;
		_fuel -= 1;
	}

	private void accelerate_due_to_gravity() {
		if (!_done) {
			// break up velocity into X and Y, reduce Y velocity
			// then combine them again and adjust the angle
			double xVelocity = _velocity * Math.cos(_angle_radians);
			double yVelocity = _velocity * Math.sin(_angle_radians);
			yVelocity += GRAVITY_DROP;
			_velocity = Math
					.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
			_angle_radians = Math.atan2(yVelocity, xVelocity);
		}
	}

	public boolean isDone() {
		return _done;
	}

	private void decelerate() {
		if (_velocity <= 0)
			return;
		_velocity -= SLOW_DOWN;
	}

	public void onDraw(Canvas canvas) {
		if (_inFreeFall) {
			update();
			canvas.drawBitmap(_bmp, _matrix, null);
		} else {
			canvas.drawBitmap(_bmp, _x, _y, null);
		}
	}

	private void rotate() {
		float rotation_degrees = (float) Math.toDegrees(_angle_radians);
		_matrix.setRotate(rotation_degrees, _bmp.getWidth() / 2,
				_bmp.getHeight() / 2);
		if (_nearGround)
			if (_done)
				_matrix.postTranslate(_x, _y);
			else
				_matrix.postTranslate(POS_X, _y);
		else
			_matrix.postTranslate(POS_X, POS_Y);
	}

	public boolean isInFreeFall() {
		return this._inFreeFall;
	}

	public int getFuel() {
		return _fuel;
	}

	public boolean isStopped() {
		// TODO Auto-generated method stub
		return _stop;
	}
}