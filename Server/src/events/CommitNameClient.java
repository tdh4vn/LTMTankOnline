package events;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class CommitNameClient extends AbstractEvent {
    private String name;

    public CommitNameClient(int eventType) {
        super(eventType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
