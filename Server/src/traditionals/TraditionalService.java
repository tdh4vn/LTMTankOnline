package traditionals;

import models.Tank;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class TraditionalService implements Runnable{
    private static final int LENGTH = 256;
    private final int PORT = 5556;
    private Random rd = new Random();
    private ServerSocketChannel ssc;
    private Selector selector;
    private ByteBuffer buf = ByteBuffer.allocate(LENGTH);

    private HashMap<String, Tank> tanks = new HashMap<>();

    private int currentID = 1000;

    public TraditionalService() throws IOException {
        this.ssc = ServerSocketChannel.open();
        this.ssc.socket().bind(new InetSocketAddress(PORT));
        this.ssc.configureBlocking(false);
        this.selector = Selector.open();
        this.ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void broadcast(byte[] event) throws IOException {

        ByteBuffer msgBuf=ByteBuffer.wrap(event);
        for(SelectionKey key : selector.keys()) {
            if(key.isValid() && key.channel() instanceof SocketChannel) {
                SocketChannel sch=(SocketChannel) key.channel();
                sch.write(msgBuf);
                msgBuf.rewind();
            }
        }

    }

    private int newID(){
        return currentID++;
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
        String address = (new StringBuilder( sc.socket().getInetAddress().toString() )).append(":").append( sc.socket().getPort() ).toString();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ, address);

        byte[] bytes = new byte[LENGTH];
        int id = newID();
        int x = rd.nextInt(800);
        int y = rd.nextInt(600);
        copyToBytes(intToByteArray(0), 0, bytes);
        copyToBytes(intToByteArray(id), 4, bytes);
        copyToBytes(intToByteArray(x), 8, bytes);
        copyToBytes(intToByteArray(y), 12, bytes);
        ByteBuffer newClient = ByteBuffer.wrap(bytes);
        System.out.println("Accepted client from: " + address);
        sc.write(newClient);
        sendAllTanksOnlineToNewClient(sc);
        tanks.put(String.valueOf(id), new Tank(id, "", x, y, 2));
    }

    private void sendAllTanksOnlineToNewClient(SocketChannel sc) throws IOException {
        ByteBuffer[] allClient = new ByteBuffer[tanks.size()];
        int count = 0;
        for(Map.Entry<String, Tank> entry : tanks.entrySet()) {
            byte[] bytes1 = new byte[LENGTH];
            System.out.println(entry.getValue().getName() + entry.getValue().getX() +"  "+ entry.getValue().getY());
            copyToBytes(intToByteArray(11), 0, bytes1);
            copyToBytes(intToByteArray(entry.getValue().getId()), 4, bytes1);
            copyToBytes(intToByteArray(entry.getValue().getX()), 8, bytes1);
            copyToBytes(intToByteArray(entry.getValue().getY()), 12, bytes1);
            copyToBytes(intToByteArray(entry.getValue().getDir()), 16, bytes1);
            int lenOfName = entry.getValue().getName().getBytes().length;
            copyToBytes(intToByteArray(lenOfName), 20, bytes1);
            copyToBytes(entry.getValue().getName().getBytes(), 24, bytes1);
            allClient[count] = ByteBuffer.wrap(bytes1);
            count++;
        }
        sc.write(allClient);
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel ch = (SocketChannel) key.channel();

        buf.clear();
        int read = 0;
        try {
            while((read = ch.read(buf)) > 0) {
                buf.flip();
                byte[] bytes = new byte[buf.limit()];
                buf.get(bytes);
                buf.clear();
                if(read<0) {
                    ch.close();
                } else {
                    handleMessage(ch, bytes);
                    broadcast(bytes);
                }
            }
        } catch (IOException e) {
            ch.close();
            //e.printStackTrace();
        }

    }

    private void handleMessage(SocketChannel ch, byte[] bytes) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int type = byteBuffer.getInt();
        int id = byteBuffer.getInt(4);
        if (type == 2){
            int x = byteBuffer.getInt(8);
            int y = byteBuffer.getInt(12);
            int dir = byteBuffer.getInt(16);
            tanks.get(String.valueOf(id)).setX(x);
            tanks.get(String.valueOf(id)).setY(y);
            tanks.get(String.valueOf(id)).setDir(dir);
        } else if (type == 1){
            if (tanks.get(String.valueOf(id)) != null){
                int length = byteBuffer.getInt(20);
                tanks.get(String.valueOf(id)).setName(new String(bytes, 24, length));
            }

        } else if (type == 5){
            if (tanks.get(String.valueOf(id)) != null){
                System.out.println("Player " + tanks.get(String.valueOf(id)).getName() + " exit!");
                tanks.remove(String.valueOf(id));
            }
        } else if (type == 4){
            tanks.remove(String.valueOf(id));
        } else if (type == 6){
            //replay
            sendAllTanksOnlineToNewClient(ch);
            int length = byteBuffer.getInt(20);
            tanks.put(String.valueOf(id), new Tank(id, new String(bytes, 24, length), byteBuffer.getInt(8), byteBuffer.getInt(12), byteBuffer.getInt(16)));
        }
    }

    private void copyToBytes(byte[] bytes, int off, byte[] target){
        for (int i = 0; i < bytes.length; i++){
            target[off + i] = bytes[i];
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Server starting on port " + PORT);

            Iterator<SelectionKey> iter;
            SelectionKey key;
            while(this.ssc.isOpen()) {
                selector.select();
                iter=this.selector.selectedKeys().iterator();
                while(iter.hasNext()) {
                    key = iter.next();
                    iter.remove();
                    if(key.isAcceptable()) this.handleAccept(key);
                    if(key.isReadable()) this.handleRead(key);
                }
            }
        } catch(Exception e) {
            System.out.println("IOException, server of port " +PORT+ " terminating. Stack trace:");
            e.printStackTrace();
        }
    }

    private byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
