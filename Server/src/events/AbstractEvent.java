package events;

import java.io.*;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public abstract class AbstractEvent implements Serializable{
    private int eventType;
    private int id;
    private long timeSend;

    public AbstractEvent(int eventType) {
        receiveTimeSend();
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void receiveTimeSend(){
        timeSend = System.currentTimeMillis();
    }

    public byte[] toBytes(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return new byte[0];
    }

    public static AbstractEvent createFromBytes(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            AbstractEvent o = (AbstractEvent) in.readObject();
            return o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return null;
    }
}
