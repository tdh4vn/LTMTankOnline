package gameobjects;

import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;
import majoolwip.core.components.*;
import majoolwip.core.fx.Image;
import managers.ClientInfo;
import tranditionals.Traditional;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class Tank extends GameObject {
    private static final float COUNTDOWN_SHOT = 1.0f;

    private float countTimeShot = 0.0f;

    private boolean isReadyShot = true;

    public static final int UP = 0;
    public static final int DOWN = 2;
    public static final int LEFT = 1;
    public static final int RIGHT = 3;

    private Traditional traditional = Traditional.getInstance();

    protected ArrayList<Bullet> bullets = new ArrayList<>();

    protected int id;

    protected int speed = 200;

    protected Image sprite;
    protected String name;
    protected int dir = 2;
    protected Image[] tanksDir;


    public Tank(){

    }

    public Tank(int x, int y, String name) {
        setTag("mytank");
        this.id = ClientInfo.getInstance().getId();
        this.x = x;
        this.y = y;
        tanksDir = new Image[]{
                new Image("1945/tank_armor_up_c2_t1.png"),
                new Image("1945/tank_armor_left_c2_t1.png"),
                new Image("1945/tank_armor_down_c2_t1.png"),
                new Image("1945/tank_armor_right_c2_t1.png")
        };

        sprite = tanksDir[dir];
        sprite.setAnchorPoint(0.5f, 0.5f);

        this.h = sprite.height;
        this.w = sprite.width;
        this.name = name;
        addComponent(new Collider());
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(gc.getInput().isKey(KeyEvent.VK_UP)) {
            dir = UP;
            sprite = tanksDir[dir];
            sprite.setAnchorPoint(0.5f, 0.5f);
            y -= speed * dt;
            if(y < 16) {
                y = 16;
            }
            traditional.moveToPosition((int)x, (int)y, dir);
        }

        if(gc.getInput().isKey(KeyEvent.VK_DOWN)) {
            dir = DOWN;
            sprite = tanksDir[dir];
            sprite.setAnchorPoint(0.5f, 0.5f);
            y += speed * dt;
            if(y + h > gc.getHeight() - 16) {
                y = gc.getHeight() - h - 16;
            }
            traditional.moveToPosition((int)x, (int)y, dir);
        }

        if (gc.getInput().isKey(KeyEvent.VK_LEFT)){
            dir = LEFT;
            sprite = tanksDir[dir];
            sprite.setAnchorPoint(0.5f, 0.5f);
            x -= speed * dt;
            if(x < 16) {
               x = 16;
            }
            traditional.moveToPosition((int)x, (int)y, dir);
        }

        if(gc.getInput().isKey(KeyEvent.VK_RIGHT)) {
            dir = RIGHT;
            sprite = tanksDir[dir];
            sprite.setAnchorPoint(0.5f, 0.5f);
            x += speed * dt;
            if(x + w > gc.getWidth() -16) {
                x = gc.getWidth() - w - 16;
            }
            traditional.moveToPosition((int)x, (int)y, dir);
        }
        if (!isReadyShot){
            countTimeShot+=dt;
        }
        if (countTimeShot >= COUNTDOWN_SHOT){
            isReadyShot = true;
        }

        if (gc.getInput().isKey(KeyEvent.VK_SPACE) && isReadyShot){
            traditional.shot();
            isReadyShot = false;
            countTimeShot = 0.0f;
        }
        updateBullet(gc, dt);
        updateComponents(gc, dt);
    }

    public void updateBullet(GameContainer gc, float dt) {
        for (Bullet b : bullets){
            b.update(gc, dt);
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawBufferredImage(sprite.getBufferedImage(), (int)x - (int)(sprite.getAnchorX() * sprite.width), (int)y- (int)(sprite.getAnchorY() * sprite.height));
        r.drawString(name, Color.WHITE.hashCode(), (int)x, (int)y + (int)getH() / 2);
        renderBullet(gc, r);
    }

    public void renderBullet(GameContainer gc, Renderer r) {
        for (Bullet b : bullets){
            b.render(gc, r);
        }
    }

    @Override
    public void componentEvent(String name, GameObject object) {
        if (object instanceof Bullet){
            String tag = ((Bullet)object).getTag();
            int idTankShotBullet = Integer.parseInt(tag.substring(6, 10));
            System.out.println(idTankShotBullet);
            System.out.println(tag);
            if (idTankShotBullet != ClientInfo.getInstance().getId()){
                traditional.die();
            }

        }
    }

    @Override
    public void dispose() {

    }

    public void shot(){
        bullets.add(new Bullet((int)this.x, (int)this.y, dir, String.valueOf(id)));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
