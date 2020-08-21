import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args) throws IOException{
	    int port=8000;//Normally a unused port.
        int appId=0;//Running app id. 0 is app select.
        DataInputStream dis;
        DataOutputStream dos;
        ServerSocket server;
        Socket socket;
        String ic="";//Input container

        double appFloat64bit0=0;
        double appFloat64bit1=0;
        double appFloat64bit2=0;

        server=new ServerSocket(port);
        socket=server.accept();
        dis=new DataInputStream(socket.getInputStream());
        dos=new DataOutputStream(socket.getOutputStream());
        while(!socket.isClosed()){
            ic=dis.readUTF();
            if(ic.equalsIgnoreCase("exit")){
                dos.writeUTF("Closing connection.");
                socket.close();
            }else if(appId<1){
                if(ic.equals("aircalc")){
                    dos.writeUTF("Welcome to Annual interest rate calculator version 0.1.\\r\\nEnter annual interest rate.");
                    appId=1;
                }else{
                    dos.writeUTF("Error. Unknown command.");
                }
            }else if(appId==1){
                if(appFloat64bit0==0){
                    try{
                        appFloat64bit0=Double.parseDouble(ic);
                        dos.writeUTF("Enter number of years.");
                    }catch(Exception e){
                        dos.writeUTF("Not valid value. Try again");
                    }
                }else if(appFloat64bit1==0){
                    try{
                        appFloat64bit1=Double.parseDouble(ic);
                        dos.writeUTF("Enter loan amount.");
                    }catch(Exception e){
                        dos.writeUTF("Not valid value. Try again");
                    }
                }else if(appFloat64bit2==0){
                    try{
                        appFloat64bit2=Double.parseDouble(ic);

                        double result=appFloat64bit0;

                        for(int i=0;i<appFloat64bit1;i++){
                            result=result*(1+(appFloat64bit2/100));
                        }

                        dos.writeUTF("Loan with interest rate: "+result);
                        appId=0;
                        appFloat64bit0=0;
                        appFloat64bit0=1;
                        appFloat64bit0=2;
                    }catch(Exception e){
                        dos.writeUTF("Not valid value. Try again");
                    }
                }
            }
        }
    }
}