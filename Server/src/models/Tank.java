package models;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class Tank {
    private int id;
    private String name;
    private int x;
    private int y;
    private int dir;

    public Tank(int id, String name, int x, int y, int dir) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
