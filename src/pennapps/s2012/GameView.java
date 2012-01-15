package pennapps.s2012;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder _holder;
	private GameThread _gameThread;
    private TextView _statusText;
	private Squirrel _squirrel;
	
	private SoundPool sounds;
	private MediaPlayer music;
	private int wee;
	
	private ArrayList<Acorn> _acorns;
	private int _acornsEaten = 0;
	private ScoreBar _scorebar;
	
	private Background[] _backgrounds;
	private int viewWidth = 0;
	private int viewHeight = 0;

	public GameView(Context context) {
		super(context);
		_holder = getHolder();
		_holder.addCallback(this);
		_gameThread = new GameThread(this);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.squirrel_small);
		_squirrel = new Squirrel(bmp);
		
		_backgrounds = new Background[2];
		bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.background_stand_in);
		_backgrounds[1] = new Background(this, bmp, _squirrel, false);
		bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.sky_stand_in);
		_backgrounds[0] = new Background(this, bmp, _squirrel, true);
		
		_acorns = new ArrayList<Acorn>();
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.score_bar);
		_scorebar = new ScoreBar(_squirrel, bmp);
		loadSound(context);
		playMusic();
	}
	private void loadSound(Context context) {
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		wee = sounds.load(context, R.raw.wee, 1);
		music = MediaPlayer.create(context, R.raw.popcorn);
	}
	public void playMusic() {
		if(!music.isPlaying()) {
			music.seekTo(0);
			music.start();
			music.setLooping(true);
		}
	}
	public void pauseMusic() {
		if (music.isPlaying())
			music.pause();
	}
	public void playWee() {
		sounds.play(wee, 1, 1, 1, 0, 1);
	}

	public void addAcorn() {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.acorn);
		_acorns.add(new Acorn(bmp));
	}
	
	public ArrayList<Acorn> getAcorns() {
		return _acorns;
	}

	public void acornEaten(){
		_acornsEaten++;
	}
	
	public Squirrel getSquirrel() {
		return _squirrel;
	}
	public void setTextView(TextView newView) {
        _statusText = newView;
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
	
	public float getBackgroundXSpeed() {
		return _backgrounds[0].getXSpeed();
	}
	
	public float getBackgroundYSpeed() {
		return _backgrounds[0].getYSpeed();
	}

	public boolean onTouchEvent(MotionEvent event) {
		synchronized (event) {
			if (!_squirrel.isInFreeFall()) {
				if (event.getAction() == MotionEvent.ACTION_MOVE
						|| event.getAction() == MotionEvent.ACTION_DOWN) {
					_squirrel.setPosition(event.getX(), event.getY());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					_squirrel.inFreeFall();
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
		if (_squirrel.getAltitude() <= 0) {
			Log.d("GameView", "game over");
			//Log.d("GameView",_statusText.toString());
			//_statusText.setText("Game Over\nAcorns Eaten: " + _acornsEaten);
			//_statusText.setVisibility(View.VISIBLE);
			//_gameThread.setRunning(false);
			return;
		}
		_squirrel.onDraw(canvas);
		for (int i = 0; i < _acorns.size(); i++) {
			_acorns.get(i).onDraw(canvas);
		}
		_scorebar.onDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		_gameThread.setRunning(true);
		_gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.release();
		boolean retry = true;
		_gameThread.setRunning(false);
		while (retry) {
			try {
				_gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
	public void release() {
		sounds.release();
		music.stop();
		music.release();
	}

}