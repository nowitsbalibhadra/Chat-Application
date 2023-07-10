import java.io.*;
import java.net.*;

public class server{

    ServerSocket server;
    Socket socket;
    BufferedReader read;
    PrintWriter out;


    public server(){

        try{
            
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Server is waiting...");
            socket = server.accept();
            read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startReading(){
        Runnable r1 = ()->{
            System.out.println("Reader Started...");
            try{

                while(true){

                    String msg = read.readLine();
                
                    if(msg.equals("exit")){
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client: "+msg);
                }
            }catch(Exception e){
                System.out.println("Connection Closed");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting(){
        Runnable r2 = ()->{
            System.out.println("Writter Started...");
            try{
                while(!socket.isClosed()){

                    BufferedReader userRead = new BufferedReader(new InputStreamReader(System.in));
                    String content = userRead.readLine();
                    
                    out.println(content);
                    out.flush();
                    
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

                }
            }catch(Exception e){
                System.out.print("Connection Closed");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Server is Starting...");
        new server();
    }
}