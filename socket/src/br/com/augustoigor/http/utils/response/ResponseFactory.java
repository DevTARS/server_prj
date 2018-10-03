package br.com.augustoigor.http.utils.response;

import br.com.augustoigor.http.utils.request.Request;

public class ResponseFactory {

	/**
	 * Retorna a resposta adequada ao request
	 * @param request request
	 * @return resposta de acordo com o request
	 */
	public static Response createResponse(Request request) {
		// TODO: Colocar outros tipos de response
		
		return new DummyResponse(request);
	}
}
