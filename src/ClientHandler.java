import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    final Database db;
    final Socket clientSocket;
    final int clientId;

    //Constructor
    public ClientHandler(Socket socket, int clientId, Database db) {
        clientSocket = socket;
        this.clientId = clientId;
        this.db = db;
    }

    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientMessage;
            while (!(clientMessage = inFromClient.readLine()).equals("stop")) {
                System.out.println("Client sent the artist name " + clientMessage);
                int titlesNum = db.getTitles(clientMessage);
                outToClient.println("Number of titles: " + titlesNum + " records found");
            }
            System.out.println("Client " + clientId + " has disconnected");
            outToClient.println("Connection closed, Goodbye!");
            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
