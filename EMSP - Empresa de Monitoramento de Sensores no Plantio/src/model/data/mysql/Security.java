package model.data.mysql;

public class Security {
		private static final int DESLOCAMENTO = 6; // Avançar 6 posições
		
		// Método para encriptar: avança cada letra em 4 posições
		public static String encrypt(String text) {
	        StringBuilder resultado = new StringBuilder(); // Para construir a nova string
	        for (char c : text.toCharArray()) { // Para cada caractere no texto
	            if (Character.isLetter(c)) { // Só muda se for letra
	                char base = Character.isUpperCase(c) ? 'A' : 'a'; // Base para maiúscula ou minúscula
	                char novo = (char) ((c - base + DESLOCAMENTO) % 26 + base); // Calcula nova posição
	                resultado.append(novo); // Adiciona à nova string
	            } else {
	                resultado.append(c); // Mantém espaços ou pontuação
	            }
	        }
	        return resultado.toString(); // Retorna a string encriptada
	    }
		
		// Método para decriptar: volta 4 posições (inverso da encriptação)
		public static String decrypt(String text) {
	        StringBuilder resultado = new StringBuilder();
	        for (char c : text.toCharArray()) {
	            if (Character.isLetter(c)) {
	                char base = Character.isUpperCase(c) ? 'A' : 'a';
	                char novo = (char) ((c - base - DESLOCAMENTO + 26) % 26 + base); // Subtrai e ajusta
	                resultado.append(novo);
	            } else {
	                resultado.append(c);
	            }
	        }
	        return resultado.toString();
	    }
}
