/*
 Aluno: Luis Fillipe Magalhaes Conrado Pereira
 Matricula: 753192
*/
public class TP03 {
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
     * encrypt - Função para encriptar uma string seguindo o modelo de
     *           ciframento de Cesar com chave 3
     * 
     * @return retorna a string encriptada
     * @param palavra - palavra a ser encriptada
     *                Realizo a verificacao com o objetivo de descobrir se a palavra
     *                é FIM, caso se confirme a execução do programa é paralisada.
     *                Se a palavra nao for FIM, eu realizo a encriptação de letra 
     *                por letra, utilizando tabela ASCII
     */
    static String encrypt(String palavra) {
        String resposta = "";
        if (isFim(palavra) == true) {
            System.exit(0);
        } else {
            for (int i = 0; i < palavra.length(); i++) {
                resposta = resposta + (char) ((int) palavra.charAt(i) + 3);

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
        
        String palavra;
        MyIO.setCharset("ISO-8859-1");
        do {
            palavra = MyIO.readLine();//ler a linha da string
            palavra = encrypt(palavra);//encriptar
            MyIO.println(palavra);//mostrar resultado
        } while (isFim(palavra) == false);//verificar FIM

    }
}