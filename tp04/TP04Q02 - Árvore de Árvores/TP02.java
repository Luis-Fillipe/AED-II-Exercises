
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.lang.Math;

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
			System.out.println("erro leitura");
			System.out.println(nome);
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
	String print() {
		SimpleDateFormat padrao = new SimpleDateFormat("dd/MM/yyyy");
		String saida = (getNome() + " " + getTituloOriginal() + " " + padrao.format(getData()) + " " + getDuracao()
				+ " " + getGenero() + " " + getIdioma() + " " + getSituacao() + " " + getOrcamento() + " "
				+ imprimirKeywords());
		return saida;
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
			} else if (i + 1 == palavraChave.length || palavraChave[i + 1] == null) { // caso o nao tenha proximo dado
																						// no array insira ]
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

class No2 {
	public Filme elemento; // Conteudo do no.
	public No2 esq; // No da esquerda.
	public No2 dir; // No da direita.

	No2(Filme elemento) {
		this.elemento = elemento.clone();
		this.esq = this.dir = null;
	}
}

class No {
	public char elemento;
	public No dir;
	public No esq;
	public No2 link;

	No(char elemento) {
		this.elemento = elemento;
		this.esq = this.dir = null;
		this.link = null;
	}
}

/**
 * Algoritmo de ordenacao Quicksort
 */
class ArvoreArvore {
	private No raiz;
	private int contador;

	/**
	 * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
	 */
	public ArvoreArvore() throws Exception {
		raiz = null;
		contador = 0;
		String letras = "DRZXVBFPUIGEJLHTAWSOMNKCYQ";

		for (int i = 0; i < letras.length(); i++) {
			inicializar(letras.charAt(i));
		}
	}

	/*
	 * inicializar - Inicializo os nos da arvore com cada caracter
	 */
	public void inicializar(char elemento) throws Exception {
		raiz = inicializar(elemento, raiz);
	}

	private No inicializar(char elemento, No i) throws Exception {
		if (i == null) {
			i = new No(elemento);
		} else {
			if (elemento > i.elemento) {
				++contador;
				i.dir = inicializar(elemento, i.dir);
			} else if (elemento < i.elemento) {
				++contador;
				i.esq = inicializar(elemento, i.esq);
			} else {
				throw new Exception("Erro ao inserir!");
			}
		}
		return i;
	}

	public void caminharCentral() {

		caminharCentral(raiz);

	}

	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq); // Elementos da esquerda.
			System.out.println(i.elemento + " "); // Conteudo do no.
			caminharCentral(i.dir); // Elementos da direita.
		}
	}

	public void inserir(Filme elemento) throws Exception {
		// System.out.println(elemento.getTituloOriginal());
		inserir(elemento, raiz);
	}

	public void inserir(Filme elemento, No i) throws Exception {
		if (i == null) {
			throw new Exception("Erro ao inserir: caractere invalido!");

		} else if (elemento.getTituloOriginal().charAt(0) < i.elemento) {
			++contador;
			// System.out.println(i.elemento);
			inserir(elemento, i.esq);

		} else if (elemento.getTituloOriginal().charAt(0) > i.elemento) {
			++contador;
			// System.out.println(i.elemento);
			inserir(elemento, i.dir);

		} else {
			// System.out.println(i.elemento);
			i.link = inserirNo2(elemento, i.link);
		}
	}

	private No2 inserirNo2(Filme elemento, No2 i) throws Exception {
		if (i == null) {
			// System.out.println("cheguei e inseri");
			i = new No2(elemento);

		} else if (i.elemento.getTituloOriginal().compareTo(elemento.getTituloOriginal()) > 0) {
			++contador;
			// System.out.println("esq "+i.elemento.getTituloOriginal());
			i.esq = inserirNo2(elemento, i.esq);

		} else if (i.elemento.getTituloOriginal().compareTo(elemento.getTituloOriginal()) < 0) {
			++contador;
			// System.out.println("dir "+i.elemento.getTituloOriginal());
			i.dir = inserirNo2(elemento, i.dir);

		} else {
			throw new Exception("Erro ao inserir: elemento existente!");
		}

		return i;
	}

	private No2 maiorEsq(No2 i, No2 j) {

		// Encontrou o maximo da subarvore esquerda.
		if (j.dir == null) {
			i.elemento = j.elemento.clone(); // Substitui i por j.
			j = j.esq; // Substitui j por j.ESQ.

			// Existe no a direita.
		} else {
			// Caminha para direita.
			j.dir = maiorEsq(i, j.dir);
		}
		return j;
	}

	public int getContador() {
		return contador;
	}

	public boolean pesquisar(String elemento) {
		System.out.print("raiz");
		boolean resultado = primeiraArvore(raiz, elemento);
		if (!resultado) {
			System.out.println("  NAO");
		}
		return resultado;
	}

	private boolean pesquisar(No2 i, String elemento) {
		boolean resultado;
		if (i == null) {
			resultado = false;
		} else if (i.elemento.getTituloOriginal().compareTo(elemento) < 0) {
			contador++;
			System.out.print(" dir");
			resultado = pesquisar(i.dir, elemento);
		} else if (i.elemento.getTituloOriginal().compareTo(elemento) > 0) {
			contador++;
			System.out.print(" esq");
			resultado = pesquisar(i.esq, elemento);
		} else {
			resultado = true;
			System.out.print("  SIM\n");
		}
		return resultado;
	}

	public boolean primeiraArvore(No i, String element) {
		boolean resultado = false;
		if (i != null) {
			resultado = pesquisar(i.link, element);
			if (!resultado) {
				System.out.print("  ESQ");
				resultado = primeiraArvore(i.esq, element);
			}
			if (!resultado) {
				System.out.print("  DIR");
				resultado = primeiraArvore(i.dir, element);
			}
		}
		return resultado;
	}

}

public class TP02 {
	static boolean isFim(String palavra) {
		boolean resposta = false;
		if (palavra.equals("FIM")) {
			resposta = true;
		}
		return resposta;
	}

	static String clearTag(String palavra, int i) {
		String newWord = "";
		while (i < palavra.length()) {
			newWord += palavra.charAt(i);
			i++;
		}
		return newWord;
	}

	public static void main(String[] args) throws ParseException, Exception {

		Locale.setDefault(new Locale("en", "US"));
		String arquivo;
		ArvoreArvore arvore = new ArvoreArvore();
		int tamanho;
		Scanner sc = new Scanner(System.in);
		long tempoInicial = System.currentTimeMillis();
		do {
			arquivo = MyIO.readLine(); // ler a linha da string
			if (isFim(arquivo) == true) {

			} else {
				Filme movie = new Filme();
				movie.searchOnFile(arquivo);
				arvore.inserir(movie);
			}
		} while (isFim(arquivo) == false);

		tamanho = MyIO.readInt(); // ler a linha da string
		for (int i = 0; i < tamanho; i++) {
			arquivo = MyIO.readLine(); // ler a linha da string
			if (arquivo.charAt(0) == 'I' && arquivo.charAt(1) == ' ') {
				Filme movie = new Filme();
				movie.searchOnFile(clearTag(arquivo, 2));
				arvore.inserir(movie);
			}
		}

		do {
			arquivo = MyIO.readLine(); // ler a linha da string
			if (isFim(arquivo) == true) {
			} else {
				System.out.println("=> " + arquivo);
				arvore.pesquisar(arquivo);
			}

		} while (isFim(arquivo) == false);

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("753192_arvoreArvore.txt"));
			double tempoFinal = (System.currentTimeMillis() - tempoInicial) / 1000.0;
			bw.append("753192" + "\t" + tempoFinal + "\t" + arvore.getContador());
			bw.close(); // fecho o arquivo de escrita
		} catch (IOException ex) {
			System.out.println("erro abrir arquivo");
		}

	}
}
