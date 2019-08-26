package ru.koshelev.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Запуск сервера, хранит список текущих подключений
 */
public class Server {
    private List<UserHandler> users = Collections.synchronizedList(new ArrayList<>());
    public Server(){
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(8080);
            System.out.println("Server start");

            while(true){
                socket = server.accept();
                System.out.println("Client connected");
                users.add(new UserHandler(server, socket));
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(socket!=null){
                try{
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(server!=null){
                try{
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
