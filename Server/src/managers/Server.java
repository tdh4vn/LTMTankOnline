package managers;

import traditionals.TraditionalService;

import java.io.IOException;

/**
 * Created by Tdh4vn on 11/6/2016.
 */
public class Server {

    TraditionalService traditionalService;

    /*
    Hàm khởi tạo
     */
    public Server() throws IOException {
        traditionalService = new TraditionalService();
        Thread thread = new Thread(traditionalService);
        thread.start();
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
