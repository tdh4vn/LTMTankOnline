package managers;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class ClientInfo {

    private String name;
    private int id;
    private int startX,startY;

    private static ClientInfo ourInstance = new ClientInfo();

    public static ClientInfo getInstance() {
        return ourInstance;
    }

    private ClientInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
