package pennapps.s2012;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;
import android.graphics.Picture;

public class ScoreBar {
	private Squirrel _squirrel;
	private Bitmap _bmp;
	private final double VELOCITY_SCALE = 5.0;
	private final double ALTITUDE_SCALE = 1000.0;
	private final double DISTANCE_SCALE = 100.0;
	
	public ScoreBar(Squirrel s, Bitmap b) {
		_squirrel = s;
		_bmp = b;
	}
	
	public void onDraw(Canvas canvas) {
		paintBar(canvas);
		paintScores(canvas);
	}
	private void paintBar(Canvas c) {
		c.drawBitmap(_bmp, 0, 0, new Paint());
	}
	private void paintScores(Canvas c) {
		Paint scores = new Paint();
		scores.setARGB(255, 52, 143, 188);
		scores.setTextSize(30);
		scores.setTypeface(Typeface.MONOSPACE);
		String scoresText = this.getScoresText();
		c.drawText(scoresText, 10, 30, scores);
	}
	private String getScoresText() {
		NumberFormat f = new DecimalFormat("#0.00");
		double vel  = _squirrel.getVelocity()/VELOCITY_SCALE;
		double alt  = _squirrel.getAltitude()/ALTITUDE_SCALE;
		double dist =_squirrel.getDistance()/DISTANCE_SCALE;
		int fuel = _squirrel.getFuel();
		return "Velocity: " + f.format(vel) + " m/s Altitude: " + f.format(alt) + " m Distance: " + f.format(dist) + " m Fuel: " + fuel + "%"; 
	}
	
}
