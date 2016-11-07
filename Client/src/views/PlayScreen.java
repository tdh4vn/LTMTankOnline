package views;

import configs.GameConfigs;
import gameobjects.Tank;
import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;
import majoolwip.core.components.ObjectManager;
import majoolwip.core.components.State;
import majoolwip.core.fx.Image;
import majoolwip.core.fx.ImageTile;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class PlayScreen extends State {

    Image[][] map = new Image[37][50];

    public PlayScreen() {
        initMap();
    }

    @Override
    public void update(GameContainer gc, float dt) {
        manager.updateObjects(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (int i = 0; i < 37; i++){
            for (int j = 0; j < 50; j++){
                if (GameConfigs.MAP[i*50 + j] == 1){
                    r.drawImage(map[i][j], j * 16 + 8, i * 16 + 8);
                }
            }
        }
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

    public void initMap(){
        for (int i = 0; i < 37; i++){
            for (int j = 0; j < 50; j++){
                if (GameConfigs.MAP[i*50 + j] == 1){
                    map[i][j] = new Image("1945/wall_steel.png");
                }
            }
        }
    }
}
