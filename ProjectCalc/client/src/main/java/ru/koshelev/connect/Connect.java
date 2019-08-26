package ru.koshelev.connect;

import ru.koshelev.commons.FinalString;
import ru.koshelev.commons.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Connect усанавливает соединение с сервером, передает сообщения между клиентом и сервером
 */
public class Connect {
    private final String HOST = "localhost";
    private final int PORT = 8080;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Object object;
    private FinalString finalString = new FinalString();


    public Connect(){
        initSocket();
    }

    /**
     * Инициализирует потоки ввода-вывода,
     * принимает сообщения от сервера и распределяет между методами, в зависимости от класса
     */
    private void initSocket(){
        try{
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            new Thread(()->{
                while (true){
                    try{
                        object = in.readObject();
                        if(object instanceof Message){
                            Message message = (Message) object;
                            readMessage(message.getTopic(), message.getText());
                        } else{
                            System.out.println("Неизвестный объект");
                        }
                    } catch (ClassNotFoundException | IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Обрабатывает сообщения типа Message от сервера
     * @param topic заголовок сообщения
     * @param text текст сообщения
     */
    private void readMessage(String topic, String text){
        if (topic.equals(finalString.getERROR())){
            System.out.println(topic + ": " + text);
        } else if(topic.equals(finalString.getSUCCESS())){
            System.out.println(text);
        }
    }

    public void sendMessage(String topic, String text){
        Message message = new Message(topic, text);
        try {
            out.writeObject(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String topic, Object object){
        Message message = new Message(topic, object);
        try {
            out.writeObject(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String topic, String text, Object object){
        Message message = new Message(topic, text, object);
        try {
            out.writeObject(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
