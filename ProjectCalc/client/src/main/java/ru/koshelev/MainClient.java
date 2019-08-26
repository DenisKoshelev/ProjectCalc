package ru.koshelev;

import ru.koshelev.connect.Connect;
import ru.koshelev.utils.CommandHandler;

import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {
        Connect connect = new Connect();
        CommandHandler commandHandler = new CommandHandler(connect);
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            String string = "";
            System.out.println("Введите команду");
            while (true){
                string = scanner.nextLine();
                if (string.equals("EXIT")){
                    System.out.println("Выход");
                    break;
                } else {
                    commandHandler.processing(string);
                }
            }
            System.exit(0);
        }).start();
    }
}
