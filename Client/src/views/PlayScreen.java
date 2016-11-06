package views;

import gameobjects.Tank;
import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;
import majoolwip.core.components.ObjectManager;
import majoolwip.core.components.State;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class PlayScreen extends State {

    public PlayScreen() {
        //manager.addObject(new Tank(0, 0, "Tdh4vn"));
    }

    @Override
    public void update(GameContainer gc, float dt) {
        manager.updateObjects(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        manager.renderObjects(gc, r);
    }

    @Override
    public void dipose() {
        manager.diposeObjects();
    }

    public ObjectManager getManager()
    {
        return manager;
    }

    public void setManager(ObjectManager manager)
    {
        this.manager = manager;
    }
}
