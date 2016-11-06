package gameobjects;

import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;
import majoolwip.core.components.GameObject;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class TankEnemy extends Tank {
    public TankEnemy(int x, int y, int dir, int id, String name) {
        super(x, y, name);
        this.dir = dir;
        this.setId(id);
        this.setTag(String.valueOf(id));
        rotateForDir();
    }


    @Override
    public void update(GameContainer gc, float dt) {
        updateBullet(gc, dt);
        updateComponents(gc, dt);
    }

    public void move(int x, int y, int dir){
        System.out.println("MOVE" + id + "-" + this.x + "-" + this.y);
        this.x = x;
        this.y = y;
        this.dir = dir;
        rotateForDir();
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        super.render(gc, r);
        System.out.println(id + "-" + this.x + "-" + this.y);
    }

    private void rotateForDir(){
        switch (dir){
            case UP:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);
                barrle = barrleDir[dir % 2];
                barrle.setAnchorPoint(0.5f, 1.0f);
                break;
            case DOWN:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);

                barrle = barrleDir[dir % 2];
                barrle.setAnchorPoint(0.5f, 0.0f);
                break;
            case LEFT:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);

                barrle = barrleDir[dir % 2];
                barrle.setAnchorPoint(1.0f, 0.5f);
                break;
            case RIGHT:
                sprite = tanksDir[dir];
                sprite.setAnchorPoint(0.5f, 0.5f);

                barrle = barrleDir[dir % 2];
                barrle.setAnchorPoint(0.0f, 0.5f);
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
