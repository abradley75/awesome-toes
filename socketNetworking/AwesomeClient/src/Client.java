import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {
	static ObjectInputStream m_inStream;
	static ObjectOutputStream m_outStream;
	public static void main(String[] args) throws ClassNotFoundException {
		Socket MyClient;
	    try {
	           MyClient = new Socket("127.0.0.1", 8080);
	           m_outStream = new ObjectOutputStream(MyClient.getOutputStream());

	           m_inStream = new ObjectInputStream(MyClient.getInputStream());
	           
	           String msg = (String) m_inStream.readObject();
	           System.out.println(msg);
	           
	           m_outStream.reset();
	           m_outStream.writeObject("board:7,7");
	           System.out.println("MSG SENT");
	           m_outStream.flush();
	           
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }

	}

}
