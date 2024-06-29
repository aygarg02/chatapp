import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client{
   JTextArea incoming;
   JTextField outgoing;
   BufferedReader bf;

   PrintWriter writer;
   public static void main(String[] args) {
    Client cli=new Client();
    cli.go();
   }

    public void go(){
        JFrame frame=new JFrame(" this java based client and server model");
        JPanel panel=new JPanel();
        incoming =new JTextArea(15,20);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qJScrollPane=new JScrollPane(incoming);
        qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing=new JTextField(20);
        JButton sendButton=new JButton("send");
        sendButton.addActionListener(new sendButtonListner());
        panel.add(qJScrollPane);
        panel.add(outgoing);
        panel.add(sendButton);
        setup();
        Thread t=new Thread(new incoming());
        t.start();

        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setSize(200,200);
        frame.setVisible(true);
        







    }
    private void setup() {
        try{
            Socket sock=new Socket("127.0.0.1",3000);
           InputStreamReader reader= new InputStreamReader(sock.getInputStream());
           bf=new BufferedReader(reader);
           writer=new PrintWriter(sock.getOutputStream());




        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    class sendButtonListner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                writer.println(outgoing.getText());
                writer.flush();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            outgoing.setText("");


         



        }
        }
        class incoming implements Runnable{

            @Override
            public void run() {
           try{
            while(true){
                String message;
                while((message=bf.readLine())!=null){
                    incoming.append(message+" "+"\n");

                }

            }
           }catch(Exception ex){
           ex.printStackTrace();}
            }
            
        }

    }

    
