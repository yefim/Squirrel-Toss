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
		int num = r.nextInt(20);
		if (num > 15) {
			_x = r.nextInt(PennApps2012Activity.screen_width / 2);
			_y = r.nextInt(PennApps2012Activity.screen_width / 10)
					- PennApps2012Activity.screen_width / 8;
		} else {
			_x = r.nextInt(PennApps2012Activity.screen_width)
					+ PennApps2012Activity.screen_width / 2;
			if (num < 7)
				_y = 3 * r.nextInt(PennApps2012Activity.screen_width) / 4;
			else
				_y = r.nextInt(PennApps2012Activity.screen_width / 8)
						- PennApps2012Activity.screen_width / 6;
		}
		_angle_radians = Math.PI / 2;
		_velocity = 5.0;
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

	public boolean onScreen() {
		return _x > 0;
	}

	@Override
	public void move() {
		double xVelocity = _velocity * Math.cos(_angle_radians)
				+ _backgroundXSpeed / 2;
		double yVelocity = _velocity * Math.sin(_angle_radians)
				+ _backgroundYSpeed / 2;

		_x += xVelocity;
		_y += yVelocity;
	}

}
