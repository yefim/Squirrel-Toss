package pennapps.s2012;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
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
				R.drawable.trees);
		_backgrounds[1] = new Background(this, bmp, _squirrel, false);
		bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.clouds_small);
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
		_squirrel.increaseVelocity();
		_acornsEaten++;
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
		_squirrel.onDraw(canvas);	
		for (int i = 0; i < _acorns.size(); i++) {
			_acorns.get(i).onDraw(canvas);
		}
		_scorebar.onDraw(canvas);
		if(!_squirrel.isInFreeFall()){
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(60);
			paint.setTypeface(Typeface.MONOSPACE);
			canvas.drawText("Toss that squirrel!", HomeScreenActivity.screen_width/4, HomeScreenActivity.screen_height/4, paint);
		}
			
		if(_squirrel.isStopped()) { 
			Paint paint = new Paint(); 
			paint.setColor(Color.WHITE); 
			paint.setTypeface(Typeface.MONOSPACE);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.game_over);
			canvas.drawBitmap(bmp, HomeScreenActivity.screen_width*3/8-20, HomeScreenActivity.screen_height*3/8, null);
			//canvas.drawRect(PennApps2012Activity.screen_width*3/8-20, PennApps2012Activity.screen_height*3/8, 5*PennApps2012Activity.screen_width/8+20, PennApps2012Activity.screen_height*5/8, paint);
			paint.setColor(Color.BLACK); 
			paint.setTextSize(60); 
			paint.setARGB(255, 52, 143, 188);
			canvas.drawText("Game Over", HomeScreenActivity.screen_width*3/8, HomeScreenActivity.screen_height*4/8, paint);
			
			paint.setTextSize(30); 
			canvas.drawText("Acorns: "+_acornsEaten, HomeScreenActivity.screen_width*3/8+80, HomeScreenActivity.screen_height*9/16, paint);
		}
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
	public Background[] getBackgrounds() {
		return _backgrounds;
	}

}