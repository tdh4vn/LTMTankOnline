package traditionals;

import events.AbstractEvent;

import java.io.IOException;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public interface SendInterface {
    void broadcast(byte[] event) throws IOException;
}
