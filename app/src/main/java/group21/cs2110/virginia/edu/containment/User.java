package group21.cs2110.virginia.edu.containment;

import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by Nikhil on 3/30/2015.
 */
public class User {
    private double x;
    private double y;

    private double vx = 0.0;
    private double vy = 0.0;

    private final int COLOR = Color.WHITE;
    private final double RADIUS = 50.0;
    private final double SPEED = 85.0;

    private final Rect hitbox;
    public User(double x, double y) {
        this.x = x;
        this.y = y;

        hitbox = new Rect((int)x, (int)y, (int)(x + 2 * RADIUS), (int)(y + 2 * RADIUS));
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

    public int getColor(){
        return this.COLOR;
    }

    public float getSpeed() {
        return (float)this.SPEED;
    }

    public Rect getHitbox() {
        return this.hitbox;
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

    public boolean intersects(Ghost ghost) {
        return Rect.intersects(ghost.getHitbox(), this.hitbox);
    }
}
