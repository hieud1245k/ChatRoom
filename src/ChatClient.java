

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
  public static void main(String[] args) {
    try {
      final Socket soc=new Socket("localhost",9999);
      while(true)
      {
        new Thread(new Runnable() {
          Scanner sc=new Scanner(System.in);
          String msg=sc.nextLine();
          @Override
          public void run() {
            try {
              if(msg.equalsIgnoreCase("bye"))
                System.exit(1);
              PrintWriter out=new PrintWriter(soc.getOutputStream(),true);
              out.println(msg);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }).start();
        new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              Scanner in=new Scanner(soc.getInputStream());
              while(in.hasNextLine()) {
                System.out.println("svr:"+in.nextLine());
              }
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }).start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}