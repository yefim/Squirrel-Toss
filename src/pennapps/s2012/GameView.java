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
	private Background[] _backgrounds;

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
		_backgrounds = new Background[2];
		_backgrounds[1] = new Background(this, bmp, false);
		bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.sky_stand_in);
		_backgrounds[0] = new Background(this, bmp, true);
	}

	public boolean onTouchEvent(MotionEvent event) {
		synchronized (event) {
			/*if (_sprite.isInFreeFall()) {
				
			} else {*/
				if (event.getAction() == MotionEvent.ACTION_MOVE
						|| event.getAction() == MotionEvent.ACTION_DOWN) {
					_sprite.setPosition(event.getX(), event.getY());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					_sprite.inFreeFall();
					for(int i=0; i<_backgrounds.length; i++)
<<<<<<< Updated upstream
						_backgrounds[i].setSpeed((-1 * _sprite.getXSpeed()), -1 * _sprite.getYSpeed());
=======
						_backgrounds[i].setSpeed(-1 * _sprite.getXSpeed(), -1 * _sprite.getYSpeed());
>>>>>>> Stashed changes
				}
			//}

		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		for(int i=0; i<_backgrounds.length; i++)
			_backgrounds[i].onDraw(canvas);
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