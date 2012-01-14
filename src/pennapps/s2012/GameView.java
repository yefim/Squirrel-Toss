package pennapps.s2012;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder _holder;
	private GameThread _gameThread;
	private Sprite _sprite;
	private Background _background;

	public GameView(Context context) {
		super(context);
		_holder = getHolder();
		_holder.addCallback(this);
		_gameThread = new GameThread(this);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.squirrel_small);
		_sprite = new Sprite(this, bmp);
		bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.background_stand_in);
		_background = new Background(this, bmp);
	}

	public boolean onTouchEvent(MotionEvent event) {
		synchronized (event) {
			if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN ) {
				_sprite.setPosition(event.getX(), event.getY());
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				_sprite.inFreeFall();
				_background.setSpeed(-1*(float)_sprite.getXSpeed());
			}

		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		_background.onDraw(canvas);
		_sprite.onDraw(canvas);
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