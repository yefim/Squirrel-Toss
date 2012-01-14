package pennapps.s2012;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder _holder;
	private GameThread _gameThread;
	private Squirrel _squirrel;
	private ArrayList<Acorn> _acorns;
	private Background[] _backgrounds;
	private int viewWidth = 0;
	private int viewHeight = 0;

	public GameView(Context context) {
		super(context);
		_holder = getHolder();
		_holder.addCallback(this);
		_gameThread = new GameThread(this);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.squirrel_small);
		_squirrel = new Squirrel(bmp);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background_stand_in);
		_backgrounds = new Background[2];
		_backgrounds[1] = new Background(this, bmp, false);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sky_stand_in);
		_backgrounds[0] = new Background(this, bmp, true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.acorn);
		_acorns = new ArrayList<Acorn>();
		setUpAcorns(bmp);
	}
	private void setUpAcorns(Bitmap a) {
		for (int i = 0; i < 10; i++) {
			_acorns.add(new Acorn(a));
		}
	}
	public Squirrel getSquirrel() {
		return _squirrel;
	}

	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		viewWidth = xNew;
		viewHeight = yNew;
	}

	public float getX() {
		return viewWidth;
	}
	public float getY() {
		return viewHeight;
	}

	public boolean onTouchEvent(MotionEvent event) {
		synchronized (event) {
			if (!_squirrel.isInFreeFall()) {
				if (event.getAction() == MotionEvent.ACTION_MOVE
						|| event.getAction() == MotionEvent.ACTION_DOWN) {
					_squirrel.setPosition(event.getX(), event.getY());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					_squirrel.inFreeFall();
					for (int i = 0; i < _backgrounds.length; i++)
						_backgrounds[i].setSpeed(-1 * _squirrel.getXSpeed(), -1
								* _squirrel.getYSpeed());
				}
			}
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		for (int i = 0; i < _backgrounds.length; i++)
			_backgrounds[i].onDraw(canvas);
		_squirrel.onDraw(canvas);
		for (Acorn a : _acorns)
			a.onDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		_gameThread.setRunning(true);
		_gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}