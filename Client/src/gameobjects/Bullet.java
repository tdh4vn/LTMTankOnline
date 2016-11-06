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
        this.tag = tag;
        this.dir = dir;
        this.x = x;
        this.y = y;
        switch (dir){
            case GameConfigs.UP:
                sprite = new Image("Bullets/bulletGreenUp.png");
                sprite.setAnchorPoint(0.5f, 0.0f);
                break;
            case GameConfigs.DOWN:
                sprite = new Image("Bullets/bulletGreenDown.png");
                sprite.setAnchorPoint(1.0f, 0.0f);
                break;
            case GameConfigs.RIGHT:
                sprite = new Image("Bullets/bulletGreenRight.png");
                sprite.setAnchorPoint(0.0f, 0.5f);
                break;
            case GameConfigs.LEFT:
                sprite = new Image("Bullets/bulletGreenLeft.png");
                sprite.setAnchorPoint(1.0f, 0.5f);
                break;
            default:
                break;
        }

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
        r.drawImage(sprite, (int)this.x, (int)this.y);
        renderComponents(gc, r);
    }

    @Override
    public void componentEvent(String name, GameObject object) {

    }

    @Override
    public void dispose() {

    }
}
