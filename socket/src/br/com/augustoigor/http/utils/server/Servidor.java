package br.com.augustoigor.http.utils.server;

/**
 * Classe Servidor - responsável por criar a conexão e receber as conexões dos clientes. Exibe a informação
 * enviada pelo cliente.
 */

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.javafx.util.Logging;

import socket.utils.request.Request;

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
			InputSream input = null;
			OutputStream output = null;
			try {
				socket = ss.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				
				String requestString = convertStreamToString(input);
				logger.info("Conexão recebida. Conteúdo:\n" + requestString);
				Request request = new Request();
			}
		}
		
	}
	
	public static void main(String[] args)  throws IOException{		
		
		//Cria um socket na porta 12345
		ServerSocket servidor = new ServerSocket (12345);
		System.out.println("Porta 12345 aberta!");
		
		// Aguarda alguém se conectar. A execução do servidor
		// fica bloqueada na chamada do método accept da classe
		// ServerSocket. Quando alguém se conectar ao servidor, o
		// método desbloqueia e retorna com um objeto da classe
		// Socket, que é uma porta da comunicação.
		
		System.out.print("Aguardando conexão do cliente...");		
		Socket cliente = servidor.accept();
	
		System.out.println("Nova conexao com o cliente " + cliente.getInetAddress().getHostAddress());
		
		
		//Recebe a mensagem enviada pelo cliente
		Scanner s = new Scanner(cliente.getInputStream());
		
		//Exibe mensagem no console
		while(s.hasNextLine())
		{
			System.out.println(s.nextLine());
		
		}
		
		//Finaliza objetos
		s.close();
		cliente.close();
		servidor.close();
		System.out.println("Fim do Servidor!");
	}

	
}
