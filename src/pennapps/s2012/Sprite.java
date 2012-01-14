package pennapps.s2012;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

public abstract class Sprite {
	float _x = 0, _y = 0;
	double _velocity;
	Bitmap _bmp;
	Matrix _matrix;
	int _width, _height;
	double _angle_radians = 0.0f;
	
	public Sprite(Bitmap bmp) {
		_bmp = bmp;
		_width = bmp.getWidth();
		_height = bmp.getHeight();
		_matrix = new Matrix();
	}
	public float getYSpeed() {
		return (float) (_velocity*Math.sin(_angle_radians));
	}
	public float getXSpeed() {
		return (float) (_velocity*Math.cos(_angle_radians));
	}
	public abstract void onDraw(Canvas canvas);
	public abstract void update();
	public abstract void move();
	
}
