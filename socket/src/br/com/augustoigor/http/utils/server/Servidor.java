package br.com.augustoigor.http.utils.server;

import java.io.BufferedReader;

/**
 * Classe Servidor - responsável por criar a conexão e receber as conexões dos clientes. Exibe a informação
 * enviada pelo cliente.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.augustoigor.http.utils.request.Request;
import br.com.augustoigor.http.utils.response.DummyResponse;
import br.com.augustoigor.http.utils.response.Response;

public class Servidor {
	
	private final static Logger logger = Logger.getLogger(Servidor.class
			.toString());

	private String host;
	private int port;
	
	public Servidor(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}
	
	@SuppressWarnings("resource")
	public void serve() {
		
		ServerSocket ss = null;
		logger.info("Iniciando servidor no endereço: " + this.host + ":" + this.port);
		
		try {
			ss = new ServerSocket(port, 1, InetAddress.getByName(host));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erro ao iniciar servidor!", e);
			return;
		}
		logger.info("Conexão com o servidor aberta no endereço: " + this.host + ":" + this.port);
		
		while(true) {
			logger.info("Aguardando conexões...");
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = ss.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				
				String requestString = convertStreamToString(input);
				logger.info("Conexão recebida. Conteúdo:\n" + requestString);
				Request request = new Request();
				request.parse(requestString);
				
				Response response = new DummyResponse(request);
				String responseString = response.respond();
				logger.info("Resposta enviada. Conteúdo:\n" + responseString);
				output.write(responseString.getBytes());
				
				socket.close();
				
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Erro ao executar servidor!", e);
				continue;
			}
		}
		
	}
	
	private String convertStreamToString(InputStream is) {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[2048];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is));
				int i = reader.read(buffer);
				writer.write(buffer, 0, i);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Erro ao converter stream para string", e);
				return "";
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	public static void main(String[] args)  throws IOException{		
		
		Servidor server = new Servidor("localhost", 8091);
		server.serve();
	}

	
}
