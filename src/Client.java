import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket(Credentials.HOST, Credentials.PORT);
        System.out.println("Client is running");
        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the artist name:");
        while (!(inFromUser.readLine()).equals("stop")) {
            String artistName = inFromUser.readLine();
            System.out.println("You entered " + artistName);
            outToServer.println(artistName);
            String serverMessage = inFromServer.readLine();
            System.out.println("FROM SERVER: " + serverMessage);
        }
        inFromServer.close();
        inFromUser.close();
        outToServer.close();
        clientSocket.close();
    }
}
