package tranditionals;

import configs.GameConfigs;
import controls.HandleEvent;
import managers.ClientInfo;

import java.io.*;
import java.net.Socket;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class Traditional implements Runnable{
    public static final int LENGTH = 256;

    public static Traditional _pointer;
    public static void create(HandleEvent handleEvent){
        _pointer = new Traditional(handleEvent);
    }

    public static Traditional getInstance(){
        return _pointer;
    }

    DataInputStream in;
    DataOutputStream out;
    Socket socket;
    Thread thread;
    Thread threadSend;
    HandleEvent handleEvent;

    private Traditional(HandleEvent handleEvent) {
        this.handleEvent = handleEvent;
        try {
            socket = new Socket(GameConfigs.SERVER_IP, GameConfigs.PORT);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        threadSend = new Thread();
        runTraditionalService();
    }

    public void runTraditionalService(){
        if (thread != null){
            thread.start();
        }
    }

    private synchronized void broadcast(byte[] bytes){
        try {
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveToPosition(int x, int y, int dir){
        byte[] bytes = new byte[LENGTH];
        copyToBytes(intToByteArray(2), 0, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getId()), 4, bytes);
        copyToBytes(intToByteArray(x), 8, bytes);
        copyToBytes(intToByteArray(y), 12, bytes);
        copyToBytes(intToByteArray(dir), 16, bytes);
        broadcast(bytes);
    }

    public void login(String name){
        byte[] bytes = new byte[LENGTH];
        copyToBytes(intToByteArray(1), 0, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getId()), 4, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getStartX()), 8, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getStartY()), 12, bytes);
        copyToBytes(intToByteArray(4), 16, bytes);
        copyToBytes(intToByteArray(name.getBytes().length), 20, bytes);
        copyToBytes(name.getBytes(), 24, bytes);
        broadcast(bytes);
    }

    public void replay(String name){
        byte[] bytes = new byte[LENGTH];
        copyToBytes(intToByteArray(6), 0, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getId()), 4, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getStartX()), 8, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getStartY()), 12, bytes);
        copyToBytes(intToByteArray(4), 16, bytes);
        copyToBytes(intToByteArray(name.getBytes().length), 20, bytes);
        copyToBytes(name.getBytes(), 24, bytes);
        broadcast(bytes);
    }

    public void shot(){
        byte[] bytes = new byte[LENGTH];
        copyToBytes(intToByteArray(3), 0, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getId()), 4, bytes);
        broadcast(bytes);
    }

    public void die(){
        byte[] bytes = new byte[LENGTH];
        copyToBytes(intToByteArray(4), 0, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getId()), 4, bytes);
        broadcast(bytes);
    }


    @Override
    public void run() {
        while(true){
            try {
                byte[] bytes = new byte[LENGTH];
                in.readFully(bytes);
                this.handleEvent.handleEvent(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void copyToBytes(byte[] bytes, int off, byte[] target){
        for (int i = 0; i < bytes.length; i++){
            target[off + i] = bytes[i];
        }
    }

    private byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

    public void exit() {
        byte[] bytes = new byte[LENGTH];
        copyToBytes(intToByteArray(5), 0, bytes);
        copyToBytes(intToByteArray(ClientInfo.getInstance().getId()), 4, bytes);
        broadcast(bytes);
    }
}
