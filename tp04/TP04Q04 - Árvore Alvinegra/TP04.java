
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

class NoAN {
	public boolean cor;
	public Filme elemento;
	public NoAN esq, dir;

	public NoAN(Filme elemento) {
		this(elemento.clone(), false, null, null);
	}

	public NoAN(Filme elemento, boolean cor) {
		this(elemento.clone(), cor, null, null);
	}

	public NoAN(Filme elemento, boolean cor, NoAN esq, NoAN dir) {
		this.cor = cor;
		this.elemento = elemento.clone();
		this.esq = esq;
		this.dir = dir;
	}
}

/**
 * Algoritmo de ordenacao Quicksort
 */
class Alvinegra {
	private NoAN raiz;
	private int contador;

	/**
	 * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
	 */
	public Alvinegra() throws Exception {
		raiz = null;
		contador = 0;
	}

	public void inserir(Filme elemento) throws Exception {
		// Se a arvore estiver vazia
		if (raiz == null) {
			raiz = new NoAN(elemento);
			

			// Senao, se a arvore tiver um elemento
		} else if (raiz.esq == null && raiz.dir == null) {
			if (elemento.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) < 0) {
				raiz.esq = new NoAN(elemento);
				
						
			} else {
				raiz.dir = new NoAN(elemento);
				
						
			}

			// Senao, se a arvore tiver dois elementos (raiz e dir)
		} else if (raiz.esq == null) {
			if (elemento.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) < 0) {
				raiz.esq = new NoAN(elemento);
				
						
			} else if (elemento.getTituloOriginal().compareTo(raiz.dir.elemento.getTituloOriginal()) < 0) {
				raiz.esq = new NoAN(raiz.elemento);
				raiz.elemento = elemento;

			} else {
				raiz.esq = new NoAN(raiz.elemento);
				raiz.elemento = raiz.dir.elemento;
				raiz.dir.elemento = elemento;
			}
			raiz.esq.cor = raiz.dir.cor = false;

			// Senao, se a arvore tiver dois elementos (raiz e esq)
		} else if (raiz.dir == null) {
			if (elemento.getTituloOriginal().compareTo(raiz.elemento.getTituloOriginal()) > 0) {
				raiz.dir = new NoAN(elemento);

			} else if (elemento.getTituloOriginal().compareTo(raiz.esq.elemento.getTituloOriginal()) > 0) {
				raiz.dir = new NoAN(raiz.elemento);
				raiz.elemento = elemento;

			} else {
				raiz.dir = new NoAN(raiz.elemento);
				raiz.elemento = raiz.esq.elemento;
				raiz.esq.elemento = elemento;
			}
			raiz.esq.cor = raiz.dir.cor = false;

			// Senao, a arvore tem tres ou mais elementos
		} else {
			inserir(elemento, null, null, null, raiz);
		}
		raiz.cor = false;
	}

	private void inserir(Filme elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
		if (i == null) {
			if (elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) < 0) {
				i = pai.esq = new NoAN(elemento, true);
			} else {
				i = pai.dir = new NoAN(elemento, true);
			}
			if (pai.cor == true) {
				balancear(bisavo, avo, pai, i);
			}
		} else {
			// Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
			if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
				i.cor = true;
				i.esq.cor = i.dir.cor = false;
				if (i == raiz) {
					i.cor = false;
				} else if (pai.cor == true) {
					balancear(bisavo, avo, pai, i);
				}
			}
			if (elemento.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) < 0) {
				inserir(elemento, avo, pai, i, i.esq);
			} else if (elemento.getTituloOriginal().compareTo(i.elemento.getTituloOriginal()) > 0) {
				inserir(elemento, avo, pai, i, i.dir);
			} else {
				throw new Exception("Erro inserir (elemento repetido)!");
			}
		}
	}

	private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
		// Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
		if (pai.cor == true) {
		   // 4 tipos de reequilibrios e acoplamento
		   if (pai.elemento.getTituloOriginal().compareTo(avo.elemento.getTituloOriginal()) > 0) { // rotacao a esquerda ou direita-esquerda
			  if (i.elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) > 0) {
				 avo = rotacaoEsq(avo);
			  } else {
				 avo = rotacaoDirEsq(avo);
			  }
		   } else { // rotacao a direita ou esquerda-direita
			  if (i.elemento.getTituloOriginal().compareTo(pai.elemento.getTituloOriginal()) < 0) {
				 avo = rotacaoDir(avo);
			  } else {
				 avo = rotacaoEsqDir(avo);
			  }
		   }
		   if (bisavo == null) {
			  raiz = avo;
		   } else if (avo.elemento.getTituloOriginal().compareTo(bisavo.elemento.getTituloOriginal()) < 0) {
			  bisavo.esq = avo;
		   } else {
			  bisavo.dir = avo;
		   }
		   // reestabelecer as cores apos a rotacao
		   avo.cor = false;
		   avo.esq.cor = avo.dir.cor = true;
		} 
	 }

	private NoAN rotacaoDir(NoAN no) {
		NoAN noEsq = no.esq;
		NoAN noEsqDir = noEsq.dir;

		noEsq.dir = no;
		no.esq = noEsqDir;

		return noEsq;
	}

	private NoAN rotacaoEsq(NoAN no) {
		NoAN noDir = no.dir;
		NoAN noDirEsq = noDir.esq;

		noDir.esq = no;
		no.dir = noDirEsq;
		return noDir;
	}

	private NoAN rotacaoDirEsq(NoAN no) {
		no.dir = rotacaoDir(no.dir);
		return rotacaoEsq(no);
	}

	private NoAN rotacaoEsqDir(NoAN no) {
		no.esq = rotacaoEsq(no.esq);
		return rotacaoDir(no);
	}

	public void pesquisar(String elemento) {
		System.out.print("raiz ");
		boolean resp = pesquisar(elemento, raiz);
		if (resp == true) {
			System.out.print("SIM\n");
		} else {
			System.out.print("NAO\n");
		}
	}

	private boolean pesquisar(String elemento, NoAN i) {
		boolean resp;
		if (i == null) {
			resp = false;

		} else if (elemento.compareTo(i.elemento.getTituloOriginal()) == 0) {
			++contador;
			resp = true;

		} else if (i.elemento.getTituloOriginal().compareTo(elemento) > 0) {
			++contador;
			System.out.print("esq ");
			resp = pesquisar(elemento, i.esq);

		} else {
			++contador;
			System.out.print("dir ");
			resp = pesquisar(elemento, i.dir);
		}
		return resp;
	}

	public void caminharCentral() {

		caminharCentral(raiz);

	}

	private void caminharCentral(NoAN i) {
		if (i != null) {
			caminharCentral(i.esq); // Elementos da esquerda.
			System.out.println(i.elemento.getTituloOriginal() + " "); // Conteudo do no.
			caminharCentral(i.dir); // Elementos da direita.
		}
	}

	public int getContador() {
		return contador;
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
		Alvinegra arvore = new Alvinegra();
		int tamanho;
		Scanner sc = new Scanner(System.in);
		long tempoInicial = System.currentTimeMillis();
		
		do {
			arquivo = sc.nextLine(); // ler a linha da string
			if (isFim(arquivo) == true) {

			} else {
				Filme movie = new Filme();
				movie.searchOnFile(arquivo);
				arvore.inserir(movie);
			}

		} while (isFim(arquivo) == false);
		do {
			arquivo = sc.nextLine(); // ler a linha da string
			if (isFim(arquivo) == true) {
			} else {
				System.out.println(arquivo);
				arvore.pesquisar(arquivo);
			}

		} while (isFim(arquivo) == false);

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("753192_arvoreAlvinegra.txt"));
			double tempoFinal = (System.currentTimeMillis() - tempoInicial) / 1000.0;
			bw.append("753192" + "\t" + tempoFinal + "\t" + arvore.getContador());
			bw.close(); // fecho o arquivo de escrita
		} catch (IOException ex) {
			System.out.println("erro abrir arquivo");
		}

	}
}
