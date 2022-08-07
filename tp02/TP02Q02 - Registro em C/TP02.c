
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

typedef struct s_Movie
{
    // parametros
    char nome[250];
    char tituloOriginal[250];
    char data[15];
    int duracao;
    char genero[200];
    char idioma[50];
    char situacao[50];
    float orcamento;
    char **keywords;
    int tamanhoKeywords;

} Movie;

void readLine(char *linha)
{
    char tmp = '-';
    int i = 0;
    scanf("%c", &tmp);
    while (i < 399 && tmp != '\n')
    {
        linha[i] = tmp;
        linha[i + 1] = '\0';
        i++;
        scanf("%c", &tmp);
    }
}

/**
    Funcao para reservar espaco para guardar cadeia de caracteres.
    @return area reservada, se houver; NULL, caso contrario
    @param size - quantidade de dados
 */
char *newChar(int size)
{
    return ((char *)malloc((size + 1) * sizeof(char)));
} // fim IO_new_chars ( )

/**
    Funcao para converter caractere para caracteres.
    @return cadeia com o resultado
    @param x     - caractere
 */
char *toString(char x)
{
    char *buffer = newChar(100);
    sprintf(buffer, "%c", x); // variante do printf( )
    return (buffer);
} // fim IO_toString_c ( )

/**
 * removeParenteses - Retira () da string
 * @param palavra
 * @return - String tratada
 */
char *removeParenteses(char *palavra)
{
    char *resultado = (char *)malloc(200);
    char *parse = (char *)malloc(100);

    for (int i = 0; i < 10; i++)
    {
        strcpy(parse, toString(palavra[i]));
        strcat(resultado, parse);
    }
    free(parse);
    return resultado;
}

/**.
 * removeGenres - Retira & da string
 * @param palavra
 * @return - String tratada
 */
char *removeGenres(char *palavra)
{

    char *resultado = (char *)malloc(200);
    char *parse = (char *)malloc(100);

    for (int i = 0; i < strlen(palavra); i++)
    {
        if (palavra[i] != '&')
        {
            strcpy(parse, toString(palavra[i]));
            strcat(resultado, parse);
        }
        else
        {
            i += 5;
        }
    }
    free(parse);
    return resultado;
}

/** 
 * removeEspaços - Funcao para remover os espacos de identacao
 * @param original - palavra recebida
 * @param i - quantidade de espaços a serem ignorados
 * return - palavra sem os espacos de identacao
*/
char *removeEspacos(int i, char *original)
{
    char *resultado = (char *)malloc(200);
    char *parse = (char *)malloc(100);
    for (i; i < strlen(original); i++)
    {
        strcpy(parse, toString(original[i]));
        strcat(resultado, parse);
    }
    free(parse);
    return resultado;
}

/**
 * convertHoras - Recebe as horas do filme e converte em minutos
 *
 * @param resultado - Horas a serem convertidas
 * @return Minutos
 */
int convertHoras(char *resultado)
{
    char *aux = (char *)malloc(200);
    char *ponte = (char *)malloc(200);
    int minutos = 0;
    for (int i = 1; i < strlen(resultado); i++)
    { // Percorrer a String

        if (resultado[i] == 'm' && i == 1)
        { // Caso o filme tenha apenas minutos, ja é feita sua conversao
          // para int e saio do for
            minutos = (int)resultado[i];
            i = strlen(resultado);
        }
        else
        {
            if (resultado[i] == 'h')
            {                                                // Caso o filme tenha uma ou mais horas
                minutos = ((int)resultado[i - 1] - 48) * 60; // Conversao da quantidade de horas em minutos
            }
            else
            {
                if (resultado[i] == 'm')
                { // Apos converter as horas vamos aos minutos
                    if (resultado[i - 2] == 32)
                    { // se o filme tiver apenas um algarismo em minutos

                        minutos += ((int)resultado[i - 1] - 48); // Parse para int
                    }
                    else
                    {
                        aux = aux + resultado[i - 2] + resultado[i - 1]; // se o filme tiver dois algarismo de minutos

                        strcpy(ponte, toString(resultado[i - 2]));
                        strcat(aux, ponte);

                        strcpy(ponte, toString(resultado[i - 1]));
                        strcat(aux, ponte);

                        minutos = minutos + (atoi(aux));
                    }
                }
            }
        }
    }
    // free(aux);
    free(ponte);
    return minutos; // Retorno os minutos convertidos
}

/**
 * convertOrcamento - Tratar a string recebida tirando as , e converter para
 * float
 *
 * @param valor - String de valor
 * @return - float de resultado
 */
float convertOrcamento(char *valor)
{
    char *aux = (char *)malloc(200);
    char *ponte = (char *)malloc(200);
    if (valor[10] == '-')
    { // Caso não temos o valor de orcamento setamos 0.0
        aux = "0.0";
    }
    else
    {
        for (int i = 0; i < strlen(valor); i++)
        {
            if (valor[i] == ',')
            {
                // Somente para ignorar a virgula do valor
            }
            else
            {

                strcpy(ponte, toString(valor[i]));
                strcat(aux, ponte);
            }
        }
    }
    // consertar exibição
    free(ponte);
    return atof(aux); // Conversao de String tratada para Float
}

/**
 * removeTags - Utilizado para remover tags html em toda String
 *
 * @param original - String com as tags
 * @return String sem as tags
 */
char *removeTags(char *original, int espaco)
{
    char *parse = (char *)malloc(200);
    char *ponte = (char *)malloc(100);

    int j = 0;
    // printf("%s", original);
    for (int i = 0; i < strlen(original); i++)
    { // tags de abertuda e fechamento <>

        if (original[i] == '<')
        {
            while (original[i] != '>')
            {
                i++;
            }
        }
        else
        {
            strcpy(ponte, toString(original[i]));
            strcat(parse, ponte);
            // printf("\n%s", parse);
        }
    }

    return removeEspacos(espaco, parse);
}

void setKeyWords(Movie *filme, char *palavra, int tamanho, int i)
{
    filme->keywords[i] = (char *)malloc(tamanho + 1 * sizeof(char));
    strncpy(filme->keywords[i], palavra, tamanho);
}



void searchOnFile(char *palavra, Movie *filme)
{

    FILE *arq;
    char *result;
    char linha[500];
    char local[100];
    int tamanho = 0;
    int i = 0;
    int check = 1;
    strcpy(local, "/tmp/filmes/");
    strcat(local, palavra);

    arq = fopen(local, "rt");        // abrindo arquivo de leitura
    result = fgets(linha, 500, arq); // o 'fgets' lê até 99 caracteres ou até o '\n'

    while (!strstr(result, "<section class=\"content_score\">"))
    {
        if (strstr(result, "<h2 class=\""))
        {
            result = fgets(linha, 500, arq);
            strcpy(result, removeTags(result, 6));//envio o 6 para a inicialização do for pulando os espaços de identação

            tamanho = strlen(result);
            result[tamanho - 1] = '\0';
            strcpy(filme->nome, result);
        }
        else if (strstr(result, "\"release\""))
        {
            result = fgets(linha, 500, arq);
            strncpy(filme->data, removeParenteses(removeTags(result, 8)), strlen(result));
        }
        else if (strstr(result, "<span class=\"genres\""))
        {
            result = fgets(linha, 500, arq);
            result = fgets(linha, 500, arq);

            strcpy(result, removeTags(result, 6));
            strcpy(result, removeGenres(result));
            tamanho = strlen(result);
            result[tamanho - 1] = '\0';
            strcpy(filme->genero, result);
        }
        else if (strstr(result, "\"runtime\""))
        {
            result = fgets(linha, 500, arq);
            result = fgets(linha, 500, arq);
            filme->duracao = convertHoras(removeTags(result, 6)), strlen(result);
        }
        else if (strstr(result, "class=\"wrap\""))
        {
            strcpy(result, removeTags(result, 21));
            tamanho = strlen(result);
            result[tamanho - 1] = '\0';
            strcpy(filme->tituloOriginal, result);
            check --;
        }
        else if (strstr(result, "<strong><bdi>Situação"))
        {
            strcpy(result, removeTags(result, 15));
            tamanho = strlen(result);
            result[tamanho - 1] = '\0';
            strcpy(filme->situacao, result);
        }
        else if (strstr(result, "<strong><bdi>Idioma original</bdi>"))
        {

            strcpy(result, removeTags(result, 18));
            tamanho = strlen(result);
            result[tamanho - 1] = '\0';
            strcpy(filme->idioma, result);
        }
        else if (strstr(result, "<strong><bdi>Orçamento</bdi></strong>"))
        {
            filme->orcamento = convertOrcamento(removeTags(result, 14));
        }
        else if (strstr(result, "<h4><bdi>"))
        {

            result = fgets(linha, 500, arq);
            result = fgets(linha, 500, arq);
            result = fgets(linha, 500, arq);
            result = fgets(linha, 500, arq);
            if (strstr(result, "<p><bdi>"))
            {
            }
            else
            {
                filme->keywords = (char **)malloc(30 * sizeof(char));
                while (strstr(result, "<li><a href=\""))
                { // encontra as palavras chaves

                    result = removeTags(result, 8);
                    tamanho = strlen(result);
                    result[tamanho - 1] = '\0';

                    setKeyWords(filme, result, strlen(result), i);

                    result = fgets(linha, 500, arq);
                    result = fgets(linha, 500, arq);
                    i++;
                }
            }
            filme->tamanhoKeywords = i;
        }

        result = fgets(linha, 500, arq);
    }

    fclose(arq);
    // tratando filme sem titulo original
    if (check == 1)
    {
        strcpy(filme->tituloOriginal, filme->nome);
    }
    
}

/**
 * imprimirSaida - Metodo para imprimir a saida igual pub.out
 * 
 * @param filme - struct
 */
void imprimirSaida(Movie *filme)
{
    int i = 0;
    char *resultado = (char *)malloc(300 * sizeof(char));
    strcpy(resultado, "[");
    if (filme->tamanhoKeywords == 0)
    {
        strcat(resultado, "]");
    }

    for (i = 0; i < filme->tamanhoKeywords; i++)
    {
        if (i + 1 >= filme->tamanhoKeywords)
        {
            strcpy(resultado, (strcat(resultado, strcat(filme->keywords[i], "]"))));
            i++;
        }
        else
        {
            // caso tenha palavra na posicao insira a palavra e uma ,
            strcpy(resultado, (strcat(resultado, strcat(filme->keywords[i], ", "))));
        }
    }

    printf("%s %s %s %d %s %s %s %g %s\n", filme->nome, filme->tituloOriginal, filme->data, filme->duracao, filme->genero, filme->idioma, filme->situacao, filme->orcamento, resultado);
    free(resultado);
}
/**
 * isFim - Função para averiguar a string recebida é igual a FIM
 *
 * @return retorna true quando a string é FIM e false quando nao é FIM
 * @param palavra - palavra a ser testada
 *                Realizo a verificacao com o objetivo se descobrir se a palavra
 *                é FIM, caso se confirme a execução do programa é paralisada
 */
static bool isFim(char *palavra)
{

    bool resposta = false;

    if (strstr(palavra, "FIM") == 0)
    {
        resposta = true;
    }

    return resposta;
}

/**
 * desalloc - Metodo para desalocar o espaço utilizado do filme
 * 
 * @param filme - struct
 */
void desalloc(Movie *filme)
{
    free(filme->keywords);
}

void takeMovies(int i, char *filme, char **filmes)
{
    filmes[i] = (char *)malloc(strlen(filme) * sizeof(char));
    strcpy(filmes[i], filme);
}

int main(void)
{

    char palavra[400];
    bool resposta = false;
    char **filmes;
    int i = 0;
    //filmes = (char **)malloc(31 * sizeof(char));

    /*do
    {
        readLine(palavra);
        if (isFim(palavra) == false)
        {
            
            break;
        }
        takeMovies(i, palavra, filmes);
        
        i++;
    }while(isFim(palavra) == true);*/
    
    for (int j = 0; j < 30; j++)
    {
        Movie filme;
        readLine(palavra);
        searchOnFile(palavra, &filme);
        
        imprimirSaida(&filme);
        desalloc(&filme);
    }
    /*for (int j = 0; j < i; j++)
    {
        printf("%s\n",filmes[j]);
    }*/
    
}
