package managers;

import configs.GameConfigs;
import controls.HandleEvent;
import gameobjects.Tank;
import gameobjects.TankEnemy;
import majoolwip.core.AbstractGame;
import majoolwip.core.GameContainer;
import majoolwip.core.Renderer;
import majoolwip.core.Window;
import tranditionals.Traditional;
import views.PlayScreen;

import javax.swing.*;
import java.util.HashMap;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class GameManager extends AbstractGame implements HandleGameObject {


    private static GameManager _sharePointer;

    private Traditional traditional;
    private HashMap<String, TankEnemy> tanks = new HashMap<>();
    private Tank myTank;

    private Window.CloseWindowListener closeWindowListener
            = new Window.CloseWindowListener() {
        @Override
        public void onCloseWindow() {
            traditional.exit();
        }
    };

    public static GameManager getInstance(){
        return _sharePointer;
    }


    public GameManager() {
        Traditional.create(new HandleEvent(this));
        traditional = Traditional.getInstance();
        push(new PlayScreen());
        inputNameAndLogin();
    }

    public void inputNameAndLogin(){
        String name = JOptionPane.showInputDialog("Nhập tên");
        traditional.login(name);
        restartGame();
    }

    @Override
    public void init(GameContainer gc) {

    }

    @Override
    public void update(GameContainer gc, float dt) {
        peek().update(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        peek().render(gc, r);
    }

    public static void openGameWindow() {
        GameManager gameManager = new GameManager();
        GameManager._sharePointer = gameManager;
        GameContainer gc = new GameContainer(gameManager);
        gc.setOnWindowCloseListener(gameManager.closeWindowListener);
        gc.setWidth(GameConfigs.WINDOW_WIDTH);
        gc.setHeight(GameConfigs.WINDOW_HEIGHT);
        gc.setScale(1);
        gc.setClearScreen(true);
        gc.setLightEnable(false);
        gc.setDynamicLights(false);
        gc.start();
    }

    @Override
    public void newClient(int id, String name, int x, int y, int dir) {
        if (isMyClient(id)){
            myTank = new Tank(x, y, name);
            peek().getManager().addObject(myTank);
        } else {
            TankEnemy tankEnemy = new TankEnemy(x, y, dir, id, name);
            tanks.put(String.valueOf(id), tankEnemy);
            peek().getManager().addObject(tankEnemy);
        }
    }

    @Override
    public void move(int id, int x, int y, int dir) {
        System.out.println("ID" +  id);
        if (tanks.get(String.valueOf(id)) == null){
            System.out.println("null");
            TankEnemy tankEnemy = new TankEnemy(x, y, dir, id, "Unknown");
            tanks.put(String.valueOf(id), tankEnemy);
        } else {
            //System.out.println(tanks.get(id).getX());
            tanks.get(String.valueOf(id)).move(x, y, dir);
        }
    }

    @Override
    public void shot(int id) {
        if (isMyClient(id)){
            myTank.shot();
        } else {
            tanks.get(String.valueOf(id)).shot();
        }
    }

    @Override
    public void onExit(int id) {
        peek().getManager().removeObject(tanks.get(String.valueOf(id)).getTag());
        tanks.remove(String.valueOf(id));
    }

    @Override
    public void die(int id) {
        if (peek().getManager() != null && tanks.get(String.valueOf(id)) != null){
            peek().getManager().removeObject(tanks.get(String.valueOf(id)).getTag());
            tanks.remove(String.valueOf(id));
        }
    }

    public void restartGame(){
        tanks.clear();
    }

    public HashMap<String, TankEnemy> getTanks() {
        return tanks;
    }

    public Tank getMyTank() {
        return myTank;
    }

    private boolean isMyClient(int id){
        return id == ClientInfo.getInstance().getId();
    }
}
