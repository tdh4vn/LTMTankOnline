package controls;

import managers.ClientInfo;
import managers.HandleGameObject;
import run.Client;

import java.nio.ByteBuffer;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class HandleEvent {
    HandleGameObject handleGameObject;

    public HandleEvent(HandleGameObject handleGameObject) {
        this.handleGameObject = handleGameObject;
    }
    public void handleEvent(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int type = byteBuffer.getInt();
        int id = byteBuffer.getInt(4);
        switch (type){
            case 0:{
                    ClientInfo.getInstance().setId(id);
                    ClientInfo.getInstance().setStartX(byteBuffer.getInt(8));
                    ClientInfo.getInstance().setStartY(byteBuffer.getInt(12));
                    break;
                }
            case 1:{
                    int length = byteBuffer.getInt(20);
                    System.out.println("New client : " + id);
                    handleGameObject.newClient(id, new String(bytes, 24, length), byteBuffer.getInt(8), byteBuffer.getInt(12), 2);
                    break;
                }

            case 11:{
                    int lengthOfNameOtherClient = byteBuffer.getInt(20);
                    System.out.println("Create client : " + id);
                    handleGameObject.newClient(id, new String(bytes, 24, lengthOfNameOtherClient), byteBuffer.getInt(8), byteBuffer.getInt(12),  byteBuffer.getInt(16));
                    break;
                }

            case 2:
                //System.out.println("Player " + id + " move to : " + byteBuffer.getInt(8) + "-"+ byteBuffer.getInt(12) + "-" + byteBuffer.getInt(16));
                handleGameObject.move(id, byteBuffer.getInt(8), byteBuffer.getInt(12), byteBuffer.getInt(16));
                break;
            case 3:
                handleGameObject.shot(id);
                break;
            case 4:
                handleGameObject.die(id);
                break;
            case 5:
                handleGameObject.onExit(id);
                break;
            case 6:{
                    int length = byteBuffer.getInt(20);
                    System.out.println("New client : " + id);
                    handleGameObject.newClient(id, new String(bytes, 24, length), byteBuffer.getInt(8), byteBuffer.getInt(12), 2);
                }
            default:
                break;
        }
    }
}
