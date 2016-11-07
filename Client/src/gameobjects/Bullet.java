package gameobjects;

import configs.GameConfigs;
import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;
import majoolwip.core.components.Collider;
import majoolwip.core.components.GameObject;
import majoolwip.core.fx.Image;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class Bullet extends GameObject {

    Image sprite;

    private int dir;
    private int speed = 500;

    public Bullet(int x, int y, int dir, String tag) {
        this.tag = "bullet" + tag;
        this.dir = dir;
        this.x = x;
        this.y = y;
        switch (dir){
            case GameConfigs.UP:
                sprite = new Image("1945/bullet_up.png");
                break;
            case GameConfigs.DOWN:
                sprite = new Image("1945/bullet_down.png");
                break;
            case GameConfigs.RIGHT:
                sprite = new Image("1945/bullet_right.png");
                break;
            case GameConfigs.LEFT:
                sprite = new Image("1945/bullet_left.png");
                break;
            default:
                break;
        }
        sprite.setAnchorPoint(0.5f, 0.5f);
        this.w = sprite.getWidth();
        this.h = sprite.getHeight();
        addComponent(new Collider());
    }

    @Override
    public void update(GameContainer gc, float dt) {
        switch (dir){
            case GameConfigs.UP:
                this.y -= (int)(dt * speed);
                break;
            case GameConfigs.DOWN:
                this.y += (int)(dt * speed);
                break;
            case GameConfigs.RIGHT:
                this.x += (int)(dt * speed);
                break;
            case GameConfigs.LEFT:
                this.x -= (int)(dt * speed);
                break;
            default:
                break;
        }
        updateComponents(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawBufferredImage(sprite.getBufferedImage(), (int)x - (int)(sprite.getAnchorX() * sprite.width), (int)y- (int)(sprite.getAnchorY() * sprite.height));
        renderComponents(gc, r);
    }

    @Override
    public void componentEvent(String name, GameObject object) {

    }

    @Override
    public void dispose() {

    }
}
