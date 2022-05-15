import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Database db;
    private final Socket clientSocket;
    private final int clientId;

    //Constructor
    public ClientHandler(Socket socket, int clientId, Database db) {
        this.clientSocket = socket;
        this.clientId = clientId;
        this.db = db;
    }

    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientMessage;
            boolean listening = true;
            while (listening) {
                clientMessage = inFromClient.readLine();
                if (clientMessage == null || clientMessage.equals("stop")) {
                    System.out.println("Client " + clientId + " has disconnected");
                    outToClient.println("Connection closed, Goodbye!");
                    listening = false;
                } else {
                    System.out.println("Client sent the artist name " + clientMessage);
                    int titlesNum = db.getTitles(clientMessage);
                    outToClient.println("Number of titles: " + titlesNum + " records found");
                }
            }
            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}