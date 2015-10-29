package group21.cs2110.virginia.edu.containment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;


/**
 * TODO: document your custom view class.
 */
public class GameView extends View {
    private static final int FPS = 30; // How many times the screen updates per second
    private static final int GHOST_TIMER = 10; // How long before another ghost is created

    private final Paint mPaint = new Paint();
    private double cx = 50.0;
    private double cy = 50.0;
    private final double radius = 30.0;

    private User player;

    private ArrayList<Ghost> listOfGhosts = new ArrayList<Ghost>();

    private RefreshHandler handler = new RefreshHandler();

    private int WIDTH;
    private int HEIGHT;

    private int counter = 0; // Frames that have elapsed
    private long startTime = 0;
    private int ghostsKilled = 0;

    public GameView(Context context) {
        super(context);
        handler.sleep(500);

        getScreenDimensions();

        player = new User(WIDTH / 2.0, HEIGHT / 2.0);

        startTime = System.currentTimeMillis();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handler.sleep(500);

        getScreenDimensions();
        player = new User(WIDTH / 2.0, HEIGHT / 2.0);

        startTime = System.currentTimeMillis();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        handler.sleep(500);
        getScreenDimensions();

        player = new User(WIDTH / 2.0, HEIGHT / 2.0);

        startTime = System.currentTimeMillis();

    }

    public void getScreenDimensions() {
        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        this.WIDTH = size.x;
        this.HEIGHT = size.y;

        Log.i("WIDTH HEIGHT", "Height = " + HEIGHT + " WIDTH = " + WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(player.getColor());

        canvas.drawCircle(player.getX(), player.getY(), player.getRadius(), mPaint);

        for(Ghost g : listOfGhosts) {
            mPaint.setColor(g.getColor());
            canvas.drawCircle(g.getX(), g.getY(), g.getRadius(), mPaint);
        }

        long elapsed = System.currentTimeMillis() - startTime;

        int seconds = (int)(elapsed/1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize((float)60.0);
        canvas.drawText(String.format("%02d:%02d", minutes, seconds), (float)50.0, (float)60.0, mPaint);
    }

    public void updateGame() {
        if(getHeight() == 0 || getWidth() == 0) {
            handler.sleep(1000 / FPS);
            return;
        } else {

            if(counter % (FPS * GHOST_TIMER) == 0) {
                Ghost g;
                Random rand = new Random();
                do {
                    int randX = rand.nextInt(WIDTH);
                    int randY = rand.nextInt(HEIGHT);

                    g = new Ghost(randX, randY);
                } while(g.intersects(player));

                listOfGhosts.add(g);
            }

            player.updatePosition(FPS);

            updateGhostVelocities();
            handler.sleep(1000/FPS);
        }
    }
    public void updateGhostVelocities() {
        for(Ghost g : listOfGhosts) {
            float dx = player.getX() - g.getX();
            float dy = player.getY() - g.getY();

            float mag = (float) Math.sqrt(dx * dx + dy * dy);

            dx /= mag;
            dy /= mag;

            g.setXVelocity((double)(dx * g.getSpeed()));
            g.setYVelocity((double) (dy * g.getSpeed()));

            g.updatePosition(FPS);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            float speed = player.getSpeed();
            if(x > WIDTH * .8) {
                if(y > HEIGHT * .66) {
                    player.setYVelocity(speed);
                    player.setXVelocity(0.0);
                } else if(y > HEIGHT * .33) {
                    player.setXVelocity(speed);
                    player.setYVelocity(0.0);
                } else if(y > 0) {
                    player.setYVelocity(-1 * speed);
                    player.setXVelocity(0.0);
                }
            } else if(x < WIDTH * .2) {
                player.setXVelocity(-1 * speed);
                player.setYVelocity(0.0);
            }


        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            player.setXVelocity(0.0);
            player.setYVelocity(0.0);
        }

        return super.onTouchEvent(event);
    }
    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            //Log.e("Update", "Message received");
            GameView.this.updateGame();
            GameView.this.invalidate(); // Refresh Paint

            counter++;
        }

        public void sleep(long delay) {
            this.removeMessages(0);
            this.sendMessageDelayed(obtainMessage(0), delay);
        }
    }


}
