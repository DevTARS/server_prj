package br.com.augustoigor.http.utils.response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.augustoigor.http.utils.request.Request;

public class DummyResponse implements Response {

	private Request request;
	
	protected static final DateFormat HTTP_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
	
	public DummyResponse(Request request) {
		this.request = request;
	}
	
	@Override
	public String respond() {
		
		
		StringBuilder sb = new StringBuilder();
		
		// Linha de status code
		sb.append("HTTP/1.1 200 OK").append("\r\n");
		
		// Parte do Cabeçalho
		sb.append("Date: ").append(HTTP_DATE_FORMAT.format(new Date())).append("\r\n");
		sb.append("Server: Test Server - http://www.augustoigor.com.br").append("\r\n");
		sb.append("Connection: Close").append("\r\n");
		sb.append("Content-Type: text/html; charset=UTF-8").append("\r\n");
		sb.append("\r\n");
		
		FileReader arq;
		try {
			arq = new FileReader(buscarPagina().toString());
		
		BufferedReader lerArq = new BufferedReader(arq);
		
		String linha = lerArq.readLine();
		
		while(linha != null) {
			
			sb.append(linha);
			linha = lerArq.readLine();
		}
		
		arq.close();
//		sb.append(buscarPagina().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Corpo da resposta
//		sb.append("<html><head><tittle>Dummy Response</tittle></head><body><h1>HttpServer Response</h1>");
//		sb.append("Method: ").append(request.getMethod()).append("<br/>");
//		sb.append("URI: ").append(request.getUri()).append("<br/>");
//		sb.append("Protocol: ").append(request.getProtocol()).append("<br/>");
//		sb.append("</body></html>");
		
		return sb.toString();
	}
	
	private File buscarPagina() {
		
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".html");
			}
		};
		
		File dir = new File("C:\\noOne\\www" + request.getUri().replace("/", "\\"));
		
		if (filter.accept(dir)) {
			return dir;
		}
		
//		if(dir.isDirectory()) {
//			ArrayList files = new ArrayList(dir.list());
//		}
		
//		File[] files = dir.listFiles(filter);
//		
//		for(File file: files) {
//			if(file.isDirectory()) {
//				
//			}
//		}
		
		return new File(dir.getPath() + "\\index.html");
	}


}
