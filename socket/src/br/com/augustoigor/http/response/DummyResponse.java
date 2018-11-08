package br.com.augustoigor.http.response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Map.Entry;

import br.com.augustoigor.http.request.Request;
import br.com.augustoigor.http.utils.Propriedades;

public class DummyResponse implements Response {

	/**
     * HTTP status code after processing, e.g. "200 OK", Status.OK
     */
    private IStatus status;

    /**
     * MIME type of content, e.g. "text/html"
     */
    private String mimeType;

    /**
     * Data of the response, may be null.
     */
    private InputStream data;

    private long contentLength;


	
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

		SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            if (this.status == null) {
                throw new Error("sendResponse(): Status can't be null.");
            }
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, new ContentType(this.mimeType).getEncoding())), false);
            pw.append("HTTP/1.1 ").append(this.status.getDescription()).append(" \r\n");
            if (this.mimeType != null) {
                printHeader(pw, "Content-Type", this.mimeType);
            }
            if (getHeader("date") == null) {
                printHeader(pw, "Date", gmtFrmt.format(new Date()));
            }
            for (Entry<String, String> entry : this.header.entrySet()) {
                printHeader(pw, entry.getKey(), entry.getValue());
            }
            for (String cookieHeader : this.cookieHeaders) {
                printHeader(pw, "Set-Cookie", cookieHeader);
            }
            if (getHeader("connection") == null) {
                printHeader(pw, "Connection", (this.keepAlive ? "keep-alive" : "close"));
            }
            if (getHeader("content-length") != null) {
                setUseGzip(false);
            }
            if (useGzipWhenAccepted()) {
                printHeader(pw, "Content-Encoding", "gzip");
                setChunkedTransfer(true);
            }
            long pending = this.data != null ? this.contentLength : 0;
            if (this.requestMethod != Method.HEAD && this.chunkedTransfer) {
                printHeader(pw, "Transfer-Encoding", "chunked");
            } else if (!useGzipWhenAccepted()) {
                pending = sendContentLengthHeaderIfNotAlreadyPresent(pw, pending);
            }
            pw.append("\r\n");
            pw.flush();
            sendBodyWithCorrectTransferAndEncoding(outputStream, pending);
            outputStream.flush();
            NanoHTTPD.safeClose(this.data);
	
		
/////////////////////////////////////////////////////////////////////////////////////////////////		
		
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
			
			statusCode = "200 OK";
			
		} catch (FileNotFoundException e) {
			erro404();
			statusCode = "404 OK";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.append((buildHeader(statusCode)));
		response.append(body.toString());

		return response.toString();
	}

	private File buscarPagina() {

		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".html");
			}
		};

		File dir = new File(Propriedades.diretorio + request.getUri().replace("/", "\\"));

		if (filter.accept(dir)) {
			return dir;
		}

		if (dir.isDirectory()) {
			return new File(dir.getPath() + "\\index.html");
		}

		return new File(dir.getPath());
	}

	private String buildHeader(String code) {

		header = new StringBuilder();

		// Linha de status code
		header.append("HTTP/1.1 ").append(code).append("\r\n");

		// Parte do Cabecalho
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
			dir = new FileReader(new File(Propriedades.pageNotFound));

			BufferedReader lerArq = new BufferedReader(dir);

			String linha = lerArq.readLine();

			while (linha != null) {

				body.append(linha);
				linha = lerArq.readLine();
			}
			
			lerArq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return body.toString();

	}

}
