import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {
    ArrayList<Object> ClinetOutputStream;
    public static void main(String[] args) {
        Server s=new Server();
        s.go();
    }
    public void go(){
        ClinetOutputStream=new ArrayList<>();
        try{
            ServerSocket sock=new ServerSocket(3000);
            while(true){
                Socket newS=sock.accept();
                PrintWriter pr=new PrintWriter(newS.getOutputStream());
                ClinetOutputStream.add(pr);

                Thread t=new Thread(new ClientHandler(newS));
                t.start();

            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    class ClientHandler implements Runnable{
Socket newSocket;
BufferedReader bf;

        ClientHandler(Socket newSocket){
            try{
            
            this.newSocket=newSocket;
            InputStreamReader st=new InputStreamReader(newSocket.getInputStream());
            bf=new BufferedReader(st);
        }


            catch(Exception ex){ex.printStackTrace();}
        }

        @Override
        public void run() {
          String message;
          try{
            while((message=bf.readLine())!=null){
            System.out.println(message);
            tellEveryone(message);
            }
        }
            catch(Exception ex){
                ex.printStackTrace();
            }
          


System.out.println("i am goin to end ");
        }

        private void tellEveryone(String message) {
        Iterator<Object> it= ClinetOutputStream.iterator();
        while (it.hasNext()) {
            try{
                PrintWriter writer=(PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            
        }

        }
        
    }
    
}


// sever has 2 parts listing from all the other parts ans sending to all the other clients that we wanted to have
// life sift 2 sal magti ha give it give whatever it demands now i have the target to make myself hardworking as much as possible