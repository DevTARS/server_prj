package br.com.augustoigor.http.utils.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Cliente {

	public static void main(String args[]) throws UnknownHostException, IOException 
	{
		
		// para se conectar ao servidor, cria-se objeto Socket.
		// O primeiro par�metro � o IP ou endere�o da m�quina que
		// se quer conectar e o segundo � a porta da aplica��o.
		// Neste caso, usa-se o IP da m�quina local (127.0.0.1)
		// e a porta da aplica��o ServidorDeEco (12345).
		
		Socket cliente = new Socket("127.0.0.1", 12345);
		System.out.println("O cliente conectou ao servidor");
		
		//Prepara para leitura do teclado
		Scanner teclado = new Scanner(System.in);
		
		//Cria  objeto para enviar a mensagem ao servidor
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		
		//Envia mensagem ao servidor
		while(teclado.hasNextLine())
		{
			
			saida.println(teclado.nextLine());			
		}
		
		saida.close();
		teclado.close();
		cliente.close();
		System.out.println("Fim do cliente!");
		
	}
	
}
