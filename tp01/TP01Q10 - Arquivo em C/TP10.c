
#include <stdio.h>
#include <stdlib.h>
int main(void)
{
    FILE *arq;
    int tamanho = 0;
    double elemento = 0;
    int i = 0;
    arq = fopen("TP.txt", "wb"); // abrindo arquivo para escrita
    scanf("%i", &tamanho);

    for (int i = 0; i < tamanho; i++)
    {
        scanf("%lf", &elemento);
        fwrite(&elemento, sizeof(double), 1, arq); // gravando no arquivo os valores reais
    }
    fclose(arq); // fechando o arquivo de escrita

    arq = fopen("TP.txt", "rb"); // abrindo arquivo de leitura
    fseek(arq, -8, SEEK_END);    // indo ate o fim do arquivo
    i = -8;                      // variavel auxiliar
    while (tamanho > 0)
    {

        fseek(arq, i, SEEK_END);
        fread(&elemento, 8, 1, arq); // lendo do arquivo o valor na linha
        printf("%g\n", elemento);
        i -= 8; // decrementando a variavel auciliar de 8 em 8 bytes
        tamanho--;
    }

    fclose(arq); // fechando arquivo de leitura
}