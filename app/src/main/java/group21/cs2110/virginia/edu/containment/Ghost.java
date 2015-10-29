package group21.cs2110.virginia.edu.containment;

import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by Nikhil on 4/13/2015.
 */
public class Ghost {
    private double x;
    private double y;

    private double vx;
    private double vy;

    public static final double RADIUS = 30.0;
    public static final double SPEED = 40.0;

    private final int color = Color.RED;

    private final Rect hitbox;

    public Ghost(double x, double y) {
        this.x = x;
        this.y = y;
        hitbox = new Rect((int)x, (int)y, (int)(x + 2 * RADIUS), (int)(y + 2 * RADIUS));
    }

    public int getColor(){
        return this.color;
    }

    public Rect getHitbox() {
        return this.hitbox;

    }

    public float getX() {
        return (float)this.x;
    }

    public float getY() {
        return (float)this.y;
    }

    public float getRadius() {
        return (float)this.RADIUS;
    }

    public double getSpeed() {
        return this.SPEED;
    }

    public void setXVelocity(double vx) {
        this.vx = vx;
    }

    public void setYVelocity(double vy) {
        this.vy = vy;
    }

    public void updatePosition(int FPS) {
        this.x += this.vx / FPS;
        this.y += this.vy / FPS;

        hitbox.set((int)x, (int)y, (int)(x + 2 * RADIUS), (int)(y + 2 * RADIUS));
    }

    public boolean intersects(User user) {
        return Rect.intersects(this.hitbox, user.getHitbox());
    }

    public boolean intersects(Ghost ghost) {
        return Rect.intersects(this.hitbox, ghost.getHitbox());
    }
}
