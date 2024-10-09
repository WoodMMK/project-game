package Entities;

/**
 *
 * @author tansan
 */
public abstract class Entity {

    protected int x, y, hight, width;


    public Entity(int x, int y, int width, int hight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.hight = hight;
    }

}
