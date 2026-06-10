package com.agrotech.agro_tech_system.domain.exception;

public class ValidacaoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ValidacaoException(String mensagem) {
		super(mensagem);
     }
}
