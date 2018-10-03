package br.com.augustoigor.http.utils.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Request {

	private String method;
	private String uri;
	private String protocol;
	
	public Request() {
	}
	
	public void parse(String input) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(input));
		String line = null;
		int lineNumber = 0;
		while ((line = br.readLine()) != null) {
			System.out.println(lineNumber + " " + line);
			if(lineNumber == 0) {
				String[] values = line.split(" ");
				if(values.length == 3) {
					this.method = values[0];
					this.uri = values[1];
					this.protocol = values[2];
					
				} // TODO: Tratar erro
			} else {
				// TODO: Recuperar os cabeçalhos e corpo
			}
			lineNumber++;
		}
	}

	/**
	 * Method recovery
	 * 
	 * @return metodo TODO: Converter para Enum
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * URI recovery
	 * 
	 * @return uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Protocol recovery
	 * 
	 * @return TODO: fazer validação
	 */
	public String getProtocol() {
		return protocol;
	}
	
}
