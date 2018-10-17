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
	private String statusCode;
	private StringBuilder header;
	private StringBuilder body;

	protected static final DateFormat HTTP_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

	public DummyResponse(Request request) {
		this.request = request;
	}

	@Override
	public String respond() {

		StringBuilder response = new StringBuilder();

		FileReader arq;
		body = new StringBuilder();
		try {
			File x = buscarPagina();
			request.setUri(x.getAbsolutePath());
			arq = new FileReader(buscarPagina().toString());

			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {

				body.append(linha);
				linha = lerArq.readLine();
			}

			arq.close();
			
			response.append(buildHeader("200 OK"));
			
		} catch (FileNotFoundException e) {
			erro404();
			response.append(buildHeader("404 OK"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		response.append(body.toString());

		// Corpo da resposta
		// sb.append("<html><head><tittle>Dummy
		// Response</tittle></head><body><h1>HttpServer Response</h1>");
		// sb.append("Method: ").append(request.getMethod()).append("<br/>");
		// sb.append("URI: ").append(request.getUri()).append("<br/>");
		// sb.append("Protocol: ").append(request.getProtocol()).append("<br/>");
		// sb.append("</body></html>");

		return response.toString();
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

		// if(dir.isDirectory()) {
		// ArrayList files = new ArrayList(dir.list());
		// }

		// File[] files = dir.listFiles(filter);
		//
		// for(File file: files) {
		// if(file.isDirectory()) {
		//
		// }
		// }

		if (dir.isDirectory()) {
			return new File(dir.getPath() + "\\index.html");
		}

		return new File(dir.getPath());
	}

	private String buildHeader(String code) {

		header = new StringBuilder();

		// Linha de status code
		header.append("HTTP/1.1 ").append(code).append("\r\n");

		// Parte do Cabeçalho
		header.append("Date: ").append(HTTP_DATE_FORMAT.format(new Date())).append("\r\n");
		header.append("Server: Test Server - http://www.augustoigor.com.br").append("\r\n");
		header.append("Connection: Close").append("\r\n");
		header.append("Content-Type: text/html; charset=UTF-8").append("\r\n");
		header.append("\r\n");

		return header.toString();
	}

	private String erro404() {

		FileReader dir;
		try {
			dir = new FileReader(new File("C:\\noOne\\config\\404.html"));

			BufferedReader lerArq = new BufferedReader(dir);

			String linha = lerArq.readLine();

			while (linha != null) {

				body.append(linha);
				linha = lerArq.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return body.toString();

	}

}
