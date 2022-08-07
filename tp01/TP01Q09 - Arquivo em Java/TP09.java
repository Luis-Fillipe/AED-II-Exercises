
import java.io.*;
import java.util.Locale;

public class TP09 {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        double numero;
        int tamanho = MyIO.readInt(); // leio a quantidade de numeros
        int aux = tamanho;
        int i = 0;
        try {
            RandomAccessFile ref = new RandomAccessFile("TP.txt", "rw");// abro o arquivo pra escrita
            for (i = 1; i <= tamanho; i++) {
                numero = MyIO.readDouble();
                ref.writeDouble(numero);//gravo os numeros lidos dentro do arquivo
            }
            ref.close();//fecho o arquivo de escrita
        } catch (IOException ex) {
            System.out.println("erro");
        }
        try {
            RandomAccessFile raf = new RandomAccessFile("TP.txt", "r");//abro o arquivo para leitura

            while (aux > 0) {//defino a quantidade de vezes que deve ser lido
                raf.seek((aux - 1) * 8);//seek para ler de trás pra frente
                numero = raf.readDouble();//valor encontrado
                if (numero == (int) numero) {//caso o valor seja inteiro, defino sua exibição como inteiro
                    System.out.println((int) numero);
                } else {
                    System.out.println(numero);//exibição valor real
                }

                aux--;
            }

            raf.close();//fecho o arquivo de leitura
        } catch (IOException ex) {
            System.out.println("erro");
        }

    }
}
