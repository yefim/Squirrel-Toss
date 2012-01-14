package pennapps.s2012;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Acorn extends Sprite {
	private float _backgroundXSpeed;
	private float _backgroundYSpeed;

	public Acorn(Bitmap bmp) {
		super(bmp);
		Random r = new Random();
		_x = r.nextInt(PennApps2012Activity.screen_width);
		_y = r.nextInt(PennApps2012Activity.screen_height);
		_angle_radians = Math.PI - 0.3;
		_velocity = 1.0;
	}

	public void setBackgroundVelocity(float xSpeed, float ySpeed) {
		_backgroundXSpeed = xSpeed;
		_backgroundYSpeed = ySpeed;
	}

	@Override
	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(_bmp, _x, _y, null);
	}

	@Override
	public void update() {
		move();
	}

	@Override
	public void move() {
		double xVelocity = _velocity * Math.cos(_angle_radians) + _backgroundXSpeed;
		double yVelocity = _velocity * Math.sin(_angle_radians) + _backgroundYSpeed;

		_x += xVelocity;
		_y += yVelocity;
	}

}
