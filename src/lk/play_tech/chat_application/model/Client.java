package lk.play_tech.chat_application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Client {
    private String name;
    private int port;
    private ServerSocket serverSocket;
    private Socket accept;
    private Socket imgSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private InputStream imgInputStream;
    private OutputStream imgOutputStream;
    private String message = "";

    public Client(int port) {
        this.port = port;
    }

    public void acceptConnection() throws IOException {
        serverSocket = new ServerSocket(port);
        this.accept = serverSocket.accept();
    }

    public void acceptImgConnection(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.imgSocket = serverSocket.accept();
    }

    public void setInputAndOutput() throws IOException {
        this.dataInputStream = new DataInputStream(accept.getInputStream());
        this.dataOutputStream = new DataOutputStream(accept.getOutputStream());
    }

    public void setImageInputAndOutput() throws IOException {
        this.imgInputStream = imgSocket.getInputStream();
        this.imgOutputStream = imgSocket.getOutputStream();
    }

    public void processTextMessage() throws IOException {
        while (!message.equals("exit")) {
            message = "Client 1 : " + dataInputStream.readUTF();
            System.out.println(message);
            String typeName = dataInputStream.getClass().getTypeName();
            System.out.println(typeName);

            if (message.equals("Client 1 : exit")) {
                accept = null;
                return;
            }
//            sendTextMessage(message);
        }
    }

    private void sendTextMessage(String message) throws IOException {
        if (this.accept != null) {
            this.dataOutputStream.writeUTF(message.trim());
            this.dataOutputStream.flush();
        }
    }

//    @Override
//    public void run() {
//        try {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
