
import java.util.Random;

public class TP04 {
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

    /**
     * changeLetter - Função para trocar caracteres de uma palavra usando random com chave 4,
     *                  recebo 2 caracteres e procuro a incidencia do primeiro caracter na string recebida
     *                  caso encontrado, é substituido o primeiro caractere recebido pelo segundo
     * @return retorna a string com caracteres trocados
     * @param palavra - palavra a ser testada
     * @param letterOne - primeira letra que sera buscada na string
     * @param letterTwo - segunda letra que sera susbtituida caso a primeira seja encontrada na string
     *                Realizo a verificacao com o objetivo de descobrir se a palavra
     *                é FIM, caso se confirme a execução do programa é paralisada.
     *                Se a palavra nao for FIM, eu realizo a encriptação de letra
     *                por letra, utilizando tabela ASCII
     */
    static String changeLetter(String palavra, char letterOne, char letterTwo) {
        String resposta = "";
        if (isFim(palavra) == true) {
            System.exit(0);
        } else {
            for (int i = 0; i < palavra.length(); i++) {
                if (palavra.charAt(i) != letterOne) {
                    resposta = resposta + palavra.charAt(i);
                } else {
                    resposta = resposta + letterTwo;
                }

            }
        }

        return resposta;
    }

    /**
     * Método principal
     * 
     * @param args
     */
    public static void main(String[] args) {
        Random gerador = new Random();
        gerador.setSeed(4);
        String palavra;

        MyIO.setCharset("ISO-8859-1");
        do {
            char letterOne = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            char letterTwo = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            palavra = MyIO.readLine();// ler a linha da string
            palavra = changeLetter(palavra, letterOne, letterTwo);// substituir caractere
            MyIO.println(palavra);// mostrar resultado
        } while (isFim(palavra) == false);// verificar FIM

    }
}