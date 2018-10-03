package br.com.augustoigor.http.utils.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {

	public final static Logger logger = Logger.getLogger(Cliente.class.toString());
	
	public final static String HTTP_VERSION = "HTTP/1.1";
	
	private String host;
	private int port;
	
	public Cliente(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getURIRawContent(String path) throws UnknownHostException, IOException {
		Socket socket = null;
		
		try {
			//Abrir conexão
			socket = new Socket(this.host, this.port);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Envia a requisição
			out.println("GET "+ path + " " + HTTP_VERSION);
			out.println("Host: " + this.host);
			out.println("Connection: Close");
			out.println();
			
			boolean loop = true;
			StringBuffer sb = new StringBuffer();
			
			while(loop) {
				if (in.ready()) {
					int i = 0;
					while((i = in.read()) != -1) {
						sb.append((char) i);
					}
					loop = false;
				}
			}
			return sb.toString();
			
		} finally {
			if(socket != null) {
				socket.close();
			}
		}
	}
	
	public static void main(String args[]) throws UnknownHostException, IOException 
	{
		
		Cliente client = new Cliente("www.augustoigor.com.br", 80);
		
		try {
			System.out.println(client.getURIRawContent("/blog/"));
		} catch (UnknownHostException e) {
			logger.log(Level.SEVERE, "Host desconhecido!", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erro de entrada e saída!", e);
		}
		
	}
	
}
