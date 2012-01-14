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
		_x = r.nextInt(PennApps2012Activity.screen_width) + PennApps2012Activity.screen_width/4;
		_y = -PennApps2012Activity.screen_width/4;
		_angle_radians = 0.0;
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

	public boolean onScreen() {
		return _x > 0 && _y < PennApps2012Activity.screen_height;
	}

	@Override
	public void move() {
		double xVelocity = _velocity * Math.cos(_angle_radians)
				+ _backgroundXSpeed;
		double yVelocity = _velocity * Math.sin(_angle_radians)
				+ _backgroundYSpeed;

		_x += xVelocity;
		_y += yVelocity;
	}

}
