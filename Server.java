import java.net.*;
import java.io.*;
class Server{

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;
    public Server(){
       try {
        server = new ServerSocket(7777);
        System.out.println("Server is ready to accept the request.");
        System.out.println("Waiting...");
        socket = server.accept();

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

        startReading();
        startWriting();

       } catch (Exception e) {
        e.printStackTrace();
       }
       
    }

    public void startReading(){
        //thread
        Runnable r1 =()->{

            System.out.println("Reader started...");

            while(true){
                try {
                String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Client terminated the chat!");
                    break;
                }

                System.out.println("Client : "+msg);
                }catch (Exception e) {
                    e.printStackTrace();
                }    
            }
        };

        new Thread(r1).start();

    }

    public void startWriting(){
        //thread for sending msg
        Runnable r2 =()->{
           System.out.println("Writer started...");
           while(true){
            try {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();

                out.println(content);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
           }
        };

        new Thread(r2).start();
    }
    public static void main(String[] args){
        System.out.println("This is server.. Going to start a server");
        new Server();
    }
}