package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int height, width;
    protected Rectangle2D.Float hitbox, attackBox;
    protected int maxHP, curHP, damage;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width * 2;
        this.height = height * 2;
    }
    
    protected void showAttackBox(Graphics g){
            g.setColor(Color.PINK);
            g.drawRect((int) attackBox.x, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    protected void showHitbox(Graphics g){
            g.setColor(Color.RED);
            g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void createHitbox(float x, float y, float width, float height){
        hitbox = new Rectangle2D.Float(x, y, width * 2, height * 2);
    }

    public Rectangle2D.Float getHitbox(){
            return hitbox;
    }

    public int getCurHP() {
        return curHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setHP(int hp) {
        maxHP = hp;
        curHP = hp;
    }
}
