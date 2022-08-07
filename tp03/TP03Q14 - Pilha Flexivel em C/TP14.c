// -------------------------------------------------------------------------------- //
// @author Pedro Henrique Lopes Costa
// 1/2022
//
// -------------------------------------------------------------------------------- //
// Includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// -------------------------------------------------------------------------------- //
// Definitions
#define MAX_MOVIES 100
#define MAX_FIELD_SIZE 100
#define MAX_KEYWORDS 20
#define MAX_LINE_SIZE 250
#define FDR_PREFIX "tmp/filmes/"

// -------------------------------------------------------------------------------- //
// Structs
typedef struct
{
    int year,
        month,
        day;
} Date;

typedef struct
{
    char name[MAX_FIELD_SIZE],
        original_title[MAX_FIELD_SIZE],
        genre[MAX_FIELD_SIZE],
        original_language[MAX_FIELD_SIZE],
        situation[MAX_FIELD_SIZE],
        keywords[MAX_KEYWORDS][MAX_FIELD_SIZE];
    Date release_date;
    int duration, count_keywords;
    float budget;
} Movie;

// -------------------------------------------------------------------------------- //
// Global variables
Movie movies[MAX_MOVIES];
int count_movies = 0;

// -------------------------------------------------------------------------------- //
// Functions
bool isFim(char *str) { return str[0] == 'F' && str[1] == 'I' && str[2] == 'M'; }

char *remove_line_break(char *line)
{
    while (*line != '\r' && *line != '\n')
        line++;
    *line = '\0';
    return line;
}

char *freadline(char *line, int max_size, FILE *file) { return remove_line_break(fgets(line, max_size, file)); }
char *readline(char *line, int max_size) { return freadline(line, max_size, stdin); }

long int indexOf(char *str, char *search)
{
    long int pos = strcspn(str, search);
    return pos == strlen(str) ? -1 : pos;
}

char *substring(char *string, int position, int length)
{
    char *p;
    int c;
    p = malloc(length + 1);
    if (p == NULL)
    {
        printf("Unable to allocate memory.\n");
        exit(1);
    }
    for (c = 0; c < length; c++)
    {
        *(p + c) = *(string + position - 1);
        string++;
    }
    *(p + c) = '\0';
    return p;
}

void str_replace(char *target, const char *needle, const char *replacement)
{
    char buffer[1024] = {0};
    char *insert_point = &buffer[0];
    const char *tmp = target;
    size_t needle_len = strlen(needle);
    size_t repl_len = strlen(replacement);

    while (1)
    {
        const char *p = strstr(tmp, needle);
        if (p == NULL)
        {
            strcpy(insert_point, tmp);
            break;
        }
        memcpy(insert_point, tmp, p - tmp);
        insert_point += p - tmp;
        memcpy(insert_point, replacement, repl_len);
        insert_point += repl_len;
        tmp = p + needle_len;
    }
    strcpy(target, buffer);
}

int firstDigit(const char *str, int start)
{
    for (int i = start; i != strlen(str); i++)
        if (str[i] >= '0' && str[i] <= '9')
            return i;
    return -1;
}

// Remove tags
char *extractOnlyText(char *html, char *text)
{
    char *start = text;
    int contagem = 0;
    while (*html != '\0')
    {
        if (*html == '<')
        {
            if (
                (*(html + 1) == 'p') ||
                (*(html + 1) == 'b' && *(html + 2) == 'r') ||
                (*(html + 1) == '/' && *(html + 2) == 'h' && *(html + 3) == '1') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'h') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'd'))
                break;
            else
                contagem++;
        }
        else if (*html == '>')
            contagem--;
        else if (contagem == 0 && *html != '"')
        {
            if (*html == '&')
                html = strchr(html, ';');
            else if (*html != '\r' && *html != '\n')
                *text++ = *html;
        }
        html++;
    }
    *text = '\0';
    return *start == ' ' ? start + 1 : start;
}

// -------------------------------------------------------------------------------- //
// Class movie functions
void movie_print(Movie *movie, int j)
{
    printf("[%d] %s %s %02i/%02i/%04i %i %s %s %s %g [", j,
           movie->name,
           movie->original_title,
           movie->release_date.day, movie->release_date.month, movie->release_date.year,
           movie->duration,
           movie->genre,
           movie->original_language,
           movie->situation,
           movie->budget);
    for (int i = 0; i < movie->count_keywords; i++)
    {
        if (i == movie->count_keywords - 1)
            printf("%s]\n", movie->keywords[i]);
        else
            printf("%s, ", movie->keywords[i]);
    }
    if (movie->count_keywords == 0)
        printf("]\n");
}

void movie_readHtml(char *filename)
{
    FILE *html_file;
    char *line_html = NULL;
    size_t len = 0;
    ssize_t read;

    html_file = fopen(filename, "r");

    if (html_file == NULL)
        exit(EXIT_FAILURE);

    // ------------------------------------ //

    // Creating movie variables
    char *name = NULL,
         *original_title = NULL,
         *genre = NULL,
         *original_language = NULL,
         *situation = NULL,
         *keywords = NULL;

    Date release_date;

    release_date.day = 0;
    int duration = -1;
    float budget = -1;

    // ------------------------------------ //

    // Read HTML line by line
    while ((read = getline(&line_html, &len, html_file)) != -1)
    {

        // --------------------------- //
        // Find movie name
        if (name == NULL)
        {
            if (strstr(line_html, "<title>") != NULL)
            {
                name = strstr(line_html, "<title>") + 7;
                strcpy(movies[count_movies].name, name);
                str_replace(movies[count_movies].name, "&#8212;", "—");
                movies[count_movies].name[strlen(movies[count_movies].name) - 46] = '\0';
            }
        }

        // --------------------------- //
        // Find movie original title
        if (original_title == NULL)
        {
            if (strstr(line_html, "<p class=\"wrap\">") != NULL)
            {
                original_title = strstr(line_html, "</strong> ") + 10;
                original_title[strlen(original_title) - 5] = '\0';
                strcpy(movies[count_movies].original_title, original_title);
            }
        }

        // --------------------------- //
        // Find movie release date
        if (release_date.day == 0)
        {
            if (strstr(line_html, "<span class=\"release\">") != NULL)
            {
                // Skip one line
                read = getline(&line_html, &len, html_file);
                char *day, *month, *year;
                day = substring(line_html, 9, 2);
                month = substring(line_html, 12, 2);
                year = substring(line_html, 15, 4);
                movies[count_movies].release_date.day = atoi(day);
                movies[count_movies].release_date.month = atoi(month);
                movies[count_movies].release_date.year = atoi(year);
            }
        }

        // --------------------------- //
        // Find movie duration
        if (duration == -1)
        {
            if (strstr(line_html, "<span class=\"runtime\">") != NULL)
            {
                // Skip two lines
                read = getline(&line_html, &len, html_file);
                read = getline(&line_html, &len, html_file);
                int h_pos = indexOf(line_html, "h"),
                    hours = 0,
                    minutes = 0;
                if (h_pos != -1)
                    hours = atoi(substring(line_html, firstDigit(line_html, 0), h_pos));
                minutes = atoi(substring(line_html, firstDigit(line_html, h_pos == -1 ? 0 : h_pos), strlen(line_html) - 1));
                duration = (hours * 60) + minutes;
                movies[count_movies].duration = duration;
            }
        }

        // -------------------------- //
        // Find movie genres
        if (genre == NULL)
        {
            if (strstr(line_html, "<span class=\"genres\">") != NULL)
            {
                // Skip two lines
                read = getline(&line_html, &len, html_file);
                read = getline(&line_html, &len, html_file);
                extractOnlyText(line_html, movies[count_movies].genre);
                genre = substring(movies[count_movies].genre, 7, strlen(movies[count_movies].genre));
                strcpy(movies[count_movies].genre, genre);
            }
        }

        // --------------------------- //
        // Find movie original language
        if (original_language == NULL)
        {
            if (strstr(line_html, "<bdi>Idioma original</bdi>") != NULL)
            {
                strcpy(movies[count_movies].original_language, line_html);
                original_language = substring(movies[count_movies].original_language, 50, strlen(line_html) - 54);
                strcpy(movies[count_movies].original_language, original_language);
            }
        }

        // --------------------------- //
        // Find movie situation
        if (situation == NULL)
        {
            if (strstr(line_html, "<bdi>Situação</bdi>") != NULL)
            {
                strcpy(movies[count_movies].situation, line_html);
                situation = substring(movies[count_movies].situation, 44, strlen(line_html) - 44);
                strcpy(movies[count_movies].situation, situation);
            }
        }

        // --------------------------- //
        // Find movie budget
        if (budget == -1)
        {
            if (strstr(line_html, "<bdi>Orçamento</bdi>") != NULL)
            {
                char *p_budget, e_budget[strlen(line_html)];
                strcpy(e_budget, line_html);
                p_budget = substring(e_budget, 45, strlen(line_html) - 49);
                if (!strcmp(p_budget, "-"))
                    movies[count_movies].budget = 0;
                else
                {
                    strcpy(e_budget, p_budget);
                    str_replace(e_budget, "$", "");
                    str_replace(e_budget, ",", "");
                    movies[count_movies].budget = atof(e_budget);
                }
            }
        }

        // --------------------------- //
        // Find movie keywords
        if (keywords == NULL)
        {
            if (strstr(line_html, "<h4><bdi>Palavras-chave</bdi></h4>") != NULL)
            {
                // Skip two lines until keywords starts
                for (int i = 0; i < 2; i++)
                    read = getline(&line_html, &len, html_file);
                char tmp_line[strlen(line_html)];
                strcpy(tmp_line, line_html);
                keywords = substring(tmp_line, 5, strlen(line_html) - 5);

                if (strcmp(keywords, "<p><bdi>Nenhuma palavra-chave foi adicionada.</bdi></p>"))
                {
                    // Skip more two lines until keywords starts
                    for (int x = 0; x < 2; x++)
                        read = getline(&line_html, &len, html_file);
                    while (true)
                    {
                        if (strstr(line_html, "</ul>") != NULL)
                            break;
                        if (strstr(line_html, "<li>") != NULL)
                        {
                            extractOnlyText(line_html, tmp_line);
                            keywords = substring(tmp_line, 9, strlen(line_html) - 8);
                            strcpy(movies[count_movies].keywords[movies[count_movies].count_keywords++], keywords);
                        }
                        read = getline(&line_html, &len, html_file);
                    }
                }
            }
        }

        // ------------------------------------ //
        // Verify variables still "null"
        if (original_title == NULL)
            strcpy(movies[count_movies].original_title, movies[count_movies].name);
    }

    // ------------------------------------ //
    fclose(html_file);
    if (line_html)
        free(line_html);
}

// -------------------------------------------------------------------------------- //
// Celula
typedef struct Celula
{
    Movie elemento;
    struct Celula *prox;
} Celula;

Celula *new_celula(Movie elemento)
{
    Celula *temp = (Celula *)malloc(sizeof(Celula));
    temp->elemento = elemento;
    temp->prox = NULL;
    return temp;
}

// -------------------------------------------------------------------------------- //
// Pilha
typedef struct Pilha
{
    struct Celula *topo;
    int size;
} Pilha;

Pilha new_pilha()
{
    Pilha temp;
    temp.topo = NULL;
    temp.size = 0;
    return temp;
}

int tamanho(Pilha *p)
{
    return p->size;
}

void inserir(Pilha *p, Movie elemento)
{
    Celula *temp = new_celula(elemento);
    temp->prox = p->topo;
    p->topo = temp;
    p->size++;
}

void remover(Pilha *p)
{
    if (p->topo == NULL)
    {
        printf("\nA pilha esta vazia!\n");
    }
    Celula *temp = p->topo;
    p->topo = p->topo->prox;
    p->size--;
    free(temp);
}

void print_pilha(Pilha *p)
{
    Celula *i;
    int j = 0;
    for (i = p->topo; i != NULL; i = i->prox, j++)
    {
        movie_print(&i->elemento, j);
    }
}

void print_pilha2(Pilha *p, Celula *i, int j)
{
    if (i != NULL)
    {
        print_pilha2(p, i->prox, --j);
        movie_print(&i->elemento, j);
    }
}

char *parse(char *original)
{
    char *saida = (char *)malloc((strlen(original) + 1) * sizeof(char));
    strcpy(saida, original);
    char *a;
    strstr(saida, " ");
    saida++;
    saida++;
    return saida;
}

// -------------------------------------------------------------------------------- //
int main()
{
    size_t prefix_size = strlen(FDR_PREFIX);
    char line[MAX_LINE_SIZE];
    int size = 0;
    strcpy(line, FDR_PREFIX);
    readline(line + prefix_size, MAX_LINE_SIZE);
    Pilha pilha = new_pilha();
    while (!isFim(line + prefix_size))
    {
        movie_readHtml(line);
        inserir(&pilha, movies[count_movies++]);

        //count_movies++;

        readline(line + prefix_size, MAX_LINE_SIZE);
    }
    scanf("%d", &size);
    getchar();
    int contador = 0;
    for (int i = 0; i < size; i++)
    {
        readline(line, MAX_LINE_SIZE);
        if ((strcmp(line, "R") == 0)||(strcmp(line, "R ") == 0))
        {
            char *nome = (char *)malloc((strlen(pilha.topo->elemento.name) + 1) * sizeof(char));
            strcpy(nome, pilha.topo->elemento.name);
            remover(&pilha);
            printf("(R) %s\n", nome);
            free(nome);
        }

        else
        {
            char caminho[] = "/tmp/filmes/";
            strcpy(line, parse(line));
            strcat(caminho, line);
            movie_readHtml(caminho);
            inserir(&pilha, movies[count_movies++]);
        }
    }
    print_pilha2(&pilha, pilha.topo, pilha.size);
    return EXIT_SUCCESS;
}