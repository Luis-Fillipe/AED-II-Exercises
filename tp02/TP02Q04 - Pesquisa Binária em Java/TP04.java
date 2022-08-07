
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

class Filme {
	String nome;
	String tituloOriginal;
	Date data;
	int duracao;
	String genero;
	String idioma;
	String situacao;
	float orcamento;
	String[] palavraChave;
	String conteudo = "";

	public Filme() {

		this.nome = "";
		this.tituloOriginal = "";
		// this.data = 08/04/2003;
		this.duracao = 0;
		this.orcamento = 0;
		this.genero = "";
		this.idioma = "";
		this.situacao = "";
		this.orcamento = 0;
		this.palavraChave = new String[30];

	}

	/**
	 * removeTags - Utilizado para remover tags html em toda String
	 * 
	 * @param original - String com as tags
	 * @return String sem as tags
	 */
	String removeTags(String original) {
		String remover = "";
		for (int i = 0; i < original.length(); i++) { // tags de abertuda e fechamento <>

			if (original.charAt(i) == '<') {
				while (original.charAt(i) != '>')
					i++;
			} else {
				remover += original.charAt(i);
			}
		}
		return remover;
	}

	/**
	 * searchOnFile - Metodo para percorrer o arquivo e pegar os elementos
	 * ate encontrar uma <section class>
	 * 
	 * @param nome: nome do arquivo
	 */
	void searchOnFile(String nome) throws ParseException {
		String arquivo = "/tmp/filmes/"; // caminho no verde
		nome = arquivo + nome;
		SimpleDateFormat padrao = new SimpleDateFormat("dd/MM/yyyy");
		try (BufferedReader br = new BufferedReader(new FileReader(nome))) {
			String line = "";
			while (!line.contains("<section class=\"content_score\">")) {

				if (line.contains("<h2 class=\"")) { // encontra o titulo do filme
					line = br.readLine().trim();
					this.nome = removeTags(line);

				} else if (line.contains("\"release\"")) {// encontra a data
					line = br.readLine().trim();

					this.data = padrao.parse(removeCountry(line));

				} else if (line.contains("<span class=\"genres\"")) {// encontra o genero

					line = br.readLine().trim();
					line = br.readLine().trim();
					line = removeTags(line);
					line = removeGenres(line); // faz o tratamento
					this.genero = line;

				} else if (line.contains("\"runtime\"")) {// encontra a duração
					line = br.readLine().trim();
					line = br.readLine().trim();
					this.duracao = convertHoras(line);

				} else if (line.contains("class=\"wrap\"")) {// encontra o titulo original
					line = removeTags(line);
					line = removeTitle(line);// faz o tratamento
					this.tituloOriginal = line.trim();

				} else if (this.tituloOriginal == "") {
					this.tituloOriginal = this.nome;
				}

				else if (line.contains("<strong><bdi>Situação")) {// encontra a situação
					line = removeTags(line);
					line = removeSituation(line);// faz o tratamento
					this.situacao = line.trim();
				} else if (line.contains("<strong><bdi>Idioma original</bdi>")) {// encontra o idioma original
					line = removeTags(line);
					line = removeLanguage(line);// faz o tratamento
					this.idioma = line.trim();

				} else if (line.contains("<strong><bdi>Orçamento</bdi></strong>")) {// encontra o orçamento
					line = removeTags(line);

					line = line.trim();

					this.orcamento = convertOrcamento(line);

				} else if (line.contains("<h4><bdi>")) {
					line = br.readLine().trim();
					line = br.readLine().trim();
					line = br.readLine().trim();
					line = br.readLine().trim();
					if (line.contains("<p><bdi>")) {// caso não tenha palavra chave
						// so ignora

					} else {
						int i = 0;

						while (line.contains("<li><a href=\"")) {// encontra as palavras chaves
							line = removeTags(line);

							this.palavraChave[i] = line;
							line = br.readLine().trim();
							line = br.readLine().trim();
							i++;

						}

					}

				}
				line = br.readLine().trim();
			}

		} catch (IOException ex) {
			System.out.println("erro");
		}
	}

	/**
	 * removeContry - Retira o () depois da data
	 * 
	 * @param data - recebida
	 * @return - data tratada
	 */
	String removeCountry(String data) {
		String resposta = "";
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) != 32) {
				resposta = resposta + data.charAt(i);
			} else {
				i = data.length();
			}
		}

		return resposta;
	}

	/**
	 * .
	 * removeGenres - Retira & da string
	 * 
	 * @param palavra
	 * @return - String tratada
	 */
	String removeGenres(String palavra) {
		String resposta = "";
		for (int i = 0; i < palavra.length(); i++) {
			if (palavra.charAt(i) != '&') {
				resposta = resposta + palavra.charAt(i);
			} else {
				i += 5;
			}
		}
		return resposta;
	}

	/**
	 * removeTitle - Remove a escrita "titulo original" antes do titulo
	 * 
	 * @param palavra
	 * @return - Titulo tratado
	 */
	String removeTitle(String palavra) {
		String resposta = "";
		for (int i = 15; i < palavra.length(); i++) {

			resposta = resposta + palavra.charAt(i);

		}

		return resposta;
	}

	String removeSituation(String palavra) {
		String resposta = "";
		for (int i = 9; i < palavra.length(); i++) {
			resposta = resposta + palavra.charAt(i);
		}

		return resposta;
	}

	/**
	 * removeLanguage - Remove a escrita "Idioma original" antes do idioma de fato
	 * 
	 * @param palavra
	 * @return - idioma tratado
	 */
	String removeLanguage(String palavra) {
		String resposta = "";
		for (int i = 16; i < palavra.length(); i++) {
			resposta = resposta + palavra.charAt(i);
		}

		return resposta;
	}

	/**
	 * convertHoras - Recebe as horas do filme e converte em minutos
	 * 
	 * @param resultado - Horas a serem convertidas
	 * @return Minutos
	 */
	int convertHoras(String resultado) {
		String aux = "";
		int minutos = 0;
		for (int i = 1; i < resultado.length(); i++) { // Percorrer a String

			if (resultado.charAt(i) == 'm' && i == 1) {// Caso o filme tenha apenas minutos, ja é feita sua conversao
														// para int e saio do for
				aux = aux + resultado.charAt(i - 1); // -1 para pegar o numero anterior a 'm'
				minutos += Integer.parseInt(aux);
				i = resultado.length();
			} else {
				if (resultado.charAt(i) == 'h') {// Caso o filme tenha uma ou mais horas
					minutos = ((int) resultado.charAt(i - 1) - 48) * 60; // Conversao da quantidade de horas em minutos
				} else {
					if (resultado.charAt(i) == 'm') { // Apos converter as horas vamos aos minutos
						if (resultado.charAt(i - 2) == 32) {// se o filme tiver apenas um algarismo em minutos
							aux = aux + resultado.charAt(i - 1);
							minutos += Integer.parseInt(aux); // Parse para int
						} else {
							aux = aux + resultado.charAt(i - 2) + resultado.charAt(i - 1);// se o filme tiver dois
																							// algarismo de minutos
							minutos += Integer.parseInt(aux);
						}

					}
				}
			}

		}
		return minutos;// Retorno os minutos convertidos
	}

	/**
	 * convertOrcamento - Tratar a string recebida tirando as , e converter para
	 * float
	 * 
	 * @param valor - String de valor
	 * @return - float de resultado
	 */
	float convertOrcamento(String valor) {
		String aux = "";

		if (valor.charAt(10) == '-') { // Caso não temos o valor de orcamento setamos 0.0
			aux = "0.0";
		} else {
			for (int i = 11; i < valor.length(); i++) {
				if (valor.charAt(i) == ',') {
					// Somente para ignorar a virgula do valor
				} else {
					aux = aux + valor.charAt(i);
				}

			}
		}

		return Float.parseFloat(aux); // Conversao de String tratada para Float
	}

	/**
	 * print - Metodo para imprimir as informacoes na tela
	 */
	void print() {
		SimpleDateFormat padrao = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(this.nome + " " + getTituloOriginal() + " " + padrao.format(getData()) + " " + getDuracao()
				+ " " + getGenero() + " " + getIdioma() + " " + getSituacao() + " " + getOrcamento() + " "
				+ imprimirKeywords());
	}

	/**
	 * imprimirKeywords - Funcao para imprimir as keywords segundo o padrao pub.out
	 * 
	 * @return keywords
	 */
	String imprimirKeywords() {
		String saida = "[";
		for (int i = 0; i < palavraChave.length; i++) {
			if (palavraChave[i] == null) {
				saida = saida + "]";
				i = palavraChave.length;
			} else if (palavraChave[i + 1] == null) { // caso o nao tenha proximo dado no array insira ]
				saida = saida + palavraChave[i] + "]";
				i = palavraChave.length;
			} else if (palavraChave[i] != null) { // caso tenha palavra na posicao insira a palavra e uma ,
				saida = saida + palavraChave[i] + ", ";
			}

		}
		return saida;
	}

	/**
	 * clean - zera os dados do array de keywords
	 */
	void clean() {
		for (int i = 0; i < palavraChave.length; i++) {
			palavraChave[i] = "null";
		}
	}

	Filme(String nome, String tituloOriginal, Date data, int duracao, float orcamento, String[] palavrachave) {

		this.nome = nome;
		this.tituloOriginal = tituloOriginal;
		this.data = data;
		this.duracao = duracao;
		this.orcamento = orcamento;
		for (int i = 0; i < palavraChave.length; i++) {
			this.palavraChave[i] = palavrachave[i];
		}
		//

	}

	public Date getData() {

		return data;
	}

	public int getDuracao() {
		return duracao;
	}

	public String getNome() {
		return nome;
	}

	public float getOrcamento() {
		return orcamento;
	}

	public String[] getPalavraChave() {
		for (int i = 0; i < palavraChave.length; i++) {
			if (palavraChave[i] != null) {
				System.out.println(palavraChave[i]);
			}
		}
		return palavraChave;
	}

	public String getTituloOriginal() {
		return tituloOriginal;
	}

	public String getConteudo() {
		return conteudo;
	}

	public String getGenero() {
		return genero;
	}

	public String getIdioma() {
		return idioma;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setData(String data) {

	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setOrcamento(float orcamento) {
		this.orcamento = orcamento;
	}

	public void setPalavraChave(String[] palavraChave) {
		this.palavraChave = palavraChave;
	}

	public void setTituloOriginal(String tituloOriginal) {
		this.tituloOriginal = tituloOriginal;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Filme clone() {
		Filme cloned = new Filme();

		cloned.nome = this.nome;
		cloned.tituloOriginal = this.tituloOriginal;
		cloned.data = this.data;
		cloned.duracao = this.duracao;
		cloned.genero = this.genero;
		cloned.idioma = this.idioma;
		cloned.situacao = this.situacao;
		cloned.orcamento = this.orcamento;
		cloned.palavraChave = this.palavraChave;

		return cloned;
	}
}

public class TP04 {
	static boolean isFim(String palavra) {
		boolean resposta = false;
		if (palavra.equals("FIM")) {
			resposta = true;
		}

		return resposta;
	}

	static int pesqSeq(Filme[] movies, String filme) {
		boolean resp = false;
		int n = movies.length;
		String nome;
		int i = 0;
		for (i = 0; i < n; i++) {
			nome = movies[i].getNome();
			if (nome.contains(filme)) {
				resp = true;

				System.out.println("SIM");
				i = n;
			}
		}
		if (resp == false) {
			System.out.println("NAO");
		}
		return i;
	}

	public static int pesqBin(Filme[] movies, String filme){
		boolean resp = false;
		int contador = 0;
		int dir = (movies.length - 1), esq = 0, meio;
		String nome;
		
		while (esq <= dir){
		   meio = (esq + dir) / 2;
		   nome = movies[meio].getNome();
		   if(nome.compareTo(filme) == 0){
			  resp = true;
			  esq = dir + 1;
		   } else if (nome.compareTo(filme) > 0) {
			  esq = meio + 1;
		   } else {
			  dir = meio - 1;
		   }
		   contador++;
		}
		return contador;
	 }

	public static void main(String[] args) throws ParseException {
		
		Locale.setDefault(new Locale("en", "US"));
		String arquivo;
		int i = 0;
		int contador = 0;
		Filme[] filmes = new Filme[30];
		MyIO.setCharset("UTF-8");
		do {
			arquivo = MyIO.readLine(); // ler a linha da string
			if (isFim(arquivo) == true) {

			} else {
				Filme movie = new Filme();

				movie.searchOnFile(arquivo);
				// movie.print();
				filmes[i] = movie.clone();
				i++;
				movie.clean();
			}

		} while (isFim(arquivo) == false);
		long tempoInicial = System.currentTimeMillis();
		do {
			arquivo = MyIO.readLine(); // ler a linha da string
			if (isFim(arquivo) == true) {

			} else {
				contador += pesqSeq(filmes, arquivo);
			}
		} while (isFim(arquivo) == false);
		try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("753192_Sequencial.txt"));
			double tempoFinal = (System.currentTimeMillis() - tempoInicial)/1000.0;
			bw.append("753192" + "\t" + tempoFinal + "\t" + contador);
			bw.close();// fecho o arquivo de escrita
		} catch (IOException ex) {
			System.out.println("erro");
		}
	}
}
