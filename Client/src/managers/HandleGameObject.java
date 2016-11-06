package managers;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public interface HandleGameObject {
    void newClient(int id, String name, int x, int y, int dir);
    void move(int id, int x, int y, int dir);
    void shot(int id);
    void onExit(int id);
    void die(int id);
}
