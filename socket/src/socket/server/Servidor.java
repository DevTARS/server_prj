package socket.server;

/**
 * Classe Servidor - responsável por criar a conexão e receber as conexões dos clientes. Exibe a informação
 * enviada pelo cliente.
 */

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Servidor {

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
