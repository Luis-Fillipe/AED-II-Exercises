import java.io.*;
import java.net.*;

class TP08 {
   public static String getHtml(String endereco) {
      URL url;
      InputStream is = null;
      BufferedReader br;
      String resp = "", line;

      try {
         url = new URL(endereco);
         is = url.openStream(); // throws an IOException
         br = new BufferedReader(new InputStreamReader(is));

         while ((line = br.readLine()) != null) {
            resp += line + "\n";
         }
      } catch (MalformedURLException mue) {
         mue.printStackTrace();
      } catch (IOException ioe) {
         ioe.printStackTrace();
      }

      try {
         is.close();
      } catch (IOException ioe) {

      }

      return resp;
   }
   /**
    * isVogais - Função contar a quantidade de vezes temos vogais
    * acentuadas ou nao, dentro da string
    * 
    * @return array com o resultado da quantidade entre as posições 0 a 21
    * @param html - codigo fonte do site
    */
   static int[] isVogais(String html, int[] resultado) {

      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'a') {
            resultado[0]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'e') {
            resultado[1]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'i') {
            resultado[2]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'o') {
            resultado[3]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'u') {
            resultado[4]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'á') {
            resultado[5]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'é') {
            resultado[6]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'í') {
            resultado[7]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ó') {
            resultado[8]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ú') {
            resultado[9]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'à') {
            resultado[10]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'è') {
            resultado[11]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ì') {
            resultado[12]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ò') {
            resultado[13]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ù') {
            resultado[14]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ã') {
            resultado[15]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'õ') {
            resultado[16]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'â') {
            resultado[17]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ê') {
            resultado[18]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'î') {
            resultado[19]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'ô') {
            resultado[20]++;
         }
      }
      for (int i = 0; i < html.length(); i++) {
         if ((int) html.charAt(i) == (int) 'û') {
            resultado[21]++;
         }
      }

      return resultado;
   }

   /**
    * isTable - Função contar a quantidade de consoantes presentes na string
    * 
    * @return array com o resultado da quantidade na posição 22
    * @param html - codigo fonte do site
    */
   static int[] isConsoantes(String html, int[] resultado) {
      for (int i = 0; i < html.length(); i++) {
         if (html.charAt(i) >= 'b' && html.charAt(i) <= 'd'
               || html.charAt(i) >= 'f' && html.charAt(i) <= 'h'
               || html.charAt(i) >= 'j' && html.charAt(i) <= 'n'
               || html.charAt(i) >= 'p' && html.charAt(i) <= 't'
               || html.charAt(i) >= 'v' && html.charAt(i) <= 'z') {
            resultado[22]++;
         }
      }
      return resultado;
   }

   /**
    * isTable - Função contar a quantidade de vezes que a tag
    * <table/> aparece na string
    * 
    * @return array com o resultado da quantidade na posição 23
    * @param html - codigo fonte do site
    */
   static int[] isTable(String html, int[] resultado) {
      for (int i = 0; i < html.length(); i++) {
         if (html.charAt(i) == '<' && html.charAt(i + 1) == 't' &&
               html.charAt(i + 2) == 'a' && html.charAt(i + 3) == 'b' &&
               html.charAt(i + 4) == 'l' && html.charAt(i + 5) == 'e' &&
               html.charAt(i + 6) == '>') {
            resultado[23]++;
         }
      }

      return resultado;
   }

   /**
    * isBr - Função contar a quantidade de vezes que a tag <br>
    * aparece na string
    * 
    * @return array com o resultado da quantidade na posição 24
    * @param html - codigo fonte do site
    */
   static int[] isBr(String html, int[] resultado) {

      for (int i = 0; i < html.length(); i++) {
         if (html.charAt(i) == '<' && html.charAt(i + 1) == 'b' &&
               html.charAt(i + 2) == 'r' && html.charAt(i + 3) == '>') {
            resultado[24]++;

         }
      }
      return resultado;
   }

   /**
    * isFim - Função para averiguar a string recebida é igual a FIM
    * 
    * @return retorna true quando a string é FIM e false quando nao é FIM
    * @param palavra - palavra a ser testada
    *                Realizo a verificacao com o objetivo se descobrir se a palavra
    *                é FIM, caso se confirme a execução do programa é paralisada
    */
   static boolean isFim(String palavra) {
      boolean resposta = false;

      if (palavra.charAt(0) == 'F' && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M') {
         resposta = true;
      }

      return resposta;
   }

   static int[] cleanArray(int[] resultado) {
      for (int i = 0; i < resultado.length; i++) {
         resultado[i] = 0;
      }
      return resultado;
   }

   public static void main(String[] args) {
      String endereco, html, nome;
      int[] resultado = new int[25];
      do {
         nome = MyIO.readLine();
         if (isFim(nome) == true) {
            System.exit(0);
         }
         endereco = MyIO.readLine();
         html = getHtml(endereco);
         isVogais(html, resultado);
         isConsoantes(html, resultado);
         isTable(html, resultado);
         isBr(html, resultado);

         // desconsidera as vogais e consoantes presente em table e br
         resultado[22] = resultado[22] - (resultado[23] * 3);
         resultado[22] = resultado[22] - (resultado[24] * 2);
         resultado[0] = resultado[0] - resultado[23];
         resultado[1] = resultado[1] - resultado[23];

         System.out.print("a(" + resultado[0] + ") ");
         System.out.print("e(" + resultado[1] + ") ");
         System.out.print("i(" + resultado[2] + ") ");
         System.out.print("o(" + resultado[3] + ") ");
         System.out.print("u(" + resultado[4] + ") ");
         System.out.print("á(" + resultado[5] + ") ");
         System.out.print("é(" + resultado[6] + ") ");
         System.out.print("í(" + resultado[7] + ") ");
         System.out.print("ó(" + resultado[8] + ") ");
         System.out.print("ú(" + resultado[9] + ") ");
         System.out.print("à(" + resultado[10] + ") ");
         System.out.print("è(" + resultado[11] + ") ");
         System.out.print("ì(" + resultado[12] + ") ");
         System.out.print("ò(" + resultado[13] + ") ");
         System.out.print("ù(" + resultado[14] + ") ");
         System.out.print("ã(" + resultado[15] + ") ");
         System.out.print("õ(" + resultado[16] + ") ");
         System.out.print("â(" + resultado[17] + ") ");
         System.out.print("ê(" + resultado[18] + ") ");
         System.out.print("î(" + resultado[19] + ") ");
         System.out.print("ô(" + resultado[20] + ") ");
         System.out.print("û(" + resultado[21] + ") ");
         System.out.print("consoante(" + resultado[22] + ") ");
         System.out.print("<br>(" + resultado[24] + ") ");
         System.out.print("<table>(" + resultado[23] + ") ");
         System.out.print(nome + "\n");
         cleanArray(resultado);
      } while (isFim(nome) == false);
   }
}
