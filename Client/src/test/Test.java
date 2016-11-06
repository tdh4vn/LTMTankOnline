package test;

import configs.GameConfigs;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class Test {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost",5555);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            while(true){
                byte[] bytes = new byte[256];
                in.readFully(bytes);
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                int result = byteBuffer.getInt();
                int rs = byteBuffer.getInt(4);
                System.out.println(result + "  " + rs);

            }
        }
        catch (IOException ex) {}
        catch (RuntimeException ex) {}
    }
}
