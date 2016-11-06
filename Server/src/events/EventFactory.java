package events;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class EventFactory {
    public static final int COMMIT_NAME = 0;
    public static final int DISCONNECT = 1;
    public static AbstractEvent createEvent(int typeOfEvent){
        switch (typeOfEvent){
            case DISCONNECT:
                return new DisconnectEvent(DISCONNECT);
            case COMMIT_NAME:
                return new CommitNameClient(COMMIT_NAME);
            default:
                return null;
        }
    }
}
