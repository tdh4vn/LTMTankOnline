package gameobjects;

import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;
import majoolwip.core.components.Collider;
import majoolwip.core.components.GameObject;
import majoolwip.core.fx.Image;

import java.awt.*;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class TankEnemy extends Tank {
    public TankEnemy(int x, int y, int dir, int id, String name) {
        this.id = id;
        this.x = x;
        this.y = y;

        tanksDir = new Image[]{
                new Image("1945/tank_armor_up_c1_t1.png"),
                new Image("1945/tank_armor_left_c1_t1.png"),
                new Image("1945/tank_armor_down_c1_t1.png"),
                new Image("1945/tank_armor_right_c1_t1.png")
        };

        if (dir >=4){
            dir = 2;
        }
        this.dir = dir;
        sprite = tanksDir[dir];
        sprite.setAnchorPoint(0.5f, 0.5f);
        this.h = sprite.height;
        this.w = sprite.width;
        this.name = name;
        this.setTag(String.valueOf(id));
        rotateForDir();
        addComponent(new Collider());
    }


    @Override
    public void update(GameContainer gc, float dt) {
        updateBullet(gc, dt);
        //updateComponents(gc, dt);
    }

    public void move(int x, int y, int dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
        rotateForDir();
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawBufferredImage(sprite.getBufferedImage(), (int)x - (int)(sprite.getAnchorX() * sprite.width), (int)y- (int)(sprite.getAnchorY() * sprite.height));
        r.drawString(name, Color.WHITE.hashCode(), (int)x, (int)y + (int)getH() / 2);
        renderBullet(gc, r);
    }

    private void rotateForDir(){
        switch (dir){
            case UP:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);
                break;
            case DOWN:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);

                break;
            case LEFT:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);

                break;
            case RIGHT:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);

                break;
            default:
                break;
        }
    }

    public void removeBullet(Bullet bullet){
        bullets.remove(bullet);
    }

    @Override
    public void componentEvent(String name, GameObject object) {

    }
}
