class Celula {
   public int elemento;
   public Celula inf, sup, esq, dir;

   public Celula(){
      this(0);
   }

   public Celula(int elemento){
      this(elemento, null, null, null, null);
   }

   public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
      this.elemento = elemento;
      this.inf = inf;
      this.sup = sup;
      this.esq = esq;
      this.dir = dir;
   }
}

class Matrix{
   
   private Celula inicio;
   private int linhas, colunas;
    
   /*Construtor vazio*/
   public Matrix()throws Exception{
      this(2,2);
   }
   
   public Matrix(int linha, int coluna)throws Exception{
      if(linha <= 0 || coluna <= 0 )
         throw new Exception ("ERRO!"); 
         
      this.linhas = linha;
      this.colunas = coluna;
      
      this.inicio = new Celula();
      Celula aux = this.inicio;
      //Set das colunas 
      for(int j = 0; j < colunas; j++){
         Celula nova = new Celula();
         aux.dir = nova;
         nova.esq = aux;
         aux = aux.dir;
      }
      Celula superior = this.inicio;
      
      //Caminhar pelas linhas da matriz 
      for(int i = 0; i < linhas-1; i++){
         Celula lateral = new Celula();//Criacao primeiro elemento da linha
         lateral.sup = superior;
         superior.inf = lateral;
         aux = lateral;
         
         //Caminhar pelas colunas da matriz 
         for(int j = 0; j < colunas-1; j++){
            superior = superior.dir;//mover para proximo elemento da coluna
            Celula nova = new Celula();   
            nova.esq = lateral;
            lateral.dir = nova;
            superior.inf = nova;
            nova.sup = superior;
            lateral = nova;//mover para proximo elemento da linha
         }
         superior = aux; 
      }
      superior = null;
      aux = null; 
   }
   
   /*Funcao para setar elementos conforme leitura de entrada*/
   public void setElementosLeitura(){
      Celula l = this.inicio;
      for(int i = 0; i < linhas; i++){
         Celula c = l;
         for(int j = 0; j < colunas; j++){
            c.elemento = MyIO.readInt();
            c = c.dir;
         }  
         l = l.inf;   
      }
   }
   
   /*Funcao para setar elemento conforme posicao e dado recebido*/
   public void setElemento(int linha, int coluna, int valor)throws Exception{
       if(this.colunas < coluna || this.linhas < linha || 0 > coluna || 0 > linha )
         throw new Exception ("ERRO!");
      Celula l = this.inicio;
      for(int i = 0; i < coluna; i++, l = l.dir );
      for(int i = 0; i < linha; i++, l = l.inf );
      l.elemento = valor;
   }

   /*Funcao para obter elemento conforme posicao */
   public int getElemento(int linha, int coluna)throws Exception{
       if(this.colunas < coluna || this.linhas < linha || 0 > coluna || 0 > linha )
         throw new Exception ("ERRO!");
      Celula l = this.inicio;
      for(int i = 0; i < coluna; i++, l = l.dir );
      for(int i = 0; i < linha; i++, l = l.inf );
      return l.elemento;
   }

   public void mostrar(){
      Celula l = this.inicio;
      for(int i = 0; i < linhas; i++){
         Celula c = l;
         for(int j = 0; j < colunas; j++){
            System.out.print(c.elemento+" ");
            c = c.dir;
         }  
         System.out.println("");
         l = l.inf;   
      }
   }
   
   /*Metodo para mostrar diagonal principal de uma matriz */   
   public void showDiagonalPrincipal()throws Exception{
      if(this.colunas != this.linhas)
         throw new Exception ("ERRO!");
         
      Celula i = inicio;
      do{
         MyIO.print(i.elemento+" ");
         i = i.dir;
         if(i != null)
            i = i.inf;
      }while(i != null);
      MyIO.println("");
   }
   
   /*Metodo para mostrar diagonal secundaria de uma matriz */
   public void showDiagonalSecundaria()throws Exception{
      if(this.colunas != this.linhas)
         throw new Exception ("ERRO!");
         
      Celula i = inicio;
      for(int x = 1; x < colunas; x++){//encontrar ultimo elemento da primeira linha
         i = i.dir;
      }
      do{
         MyIO.print(i.elemento+" ");
         i = i.esq;
         if(i != null)
            i = i.inf;
      }while(i != null);
      MyIO.println("");
   }
   
   public Matrix soma(Matrix b)throws Exception{
      return soma(this, b);
   }

   /*Metodo para somar duas matrizes */
   public Matrix soma(Matrix a, Matrix b)throws Exception{
      Matrix s = null;
      
      if(a.linhas == b.linhas && a.colunas == b.colunas){
         s = new Matrix(a.linhas, a.colunas);
         Celula i1 = a.inicio;
         Celula i2 = b.inicio;
         Celula is = s.inicio;
         for(int x = 0; x < linhas; x++){
            Celula j1 = i1;
            Celula j2 = i2;
            Celula js = is;
            
            for(int y = 0; y < colunas; y++){
               js.elemento = j1.elemento + j2.elemento;
            
               j1 = j1.dir;
               j2 = j2.dir;
               js = js.dir;
            }
            i1 = i1.inf;
            i2 = i2.inf;
            is = is.inf;
         }
      
      }   
      return s;
   }
   
   public Matrix multiplicacao(Matrix b)throws Exception{
      return multiplicacao(this, b);
   }
   
   /*Metodo para multiplicar matriz*/
   public Matrix multiplicacao(Matrix a, Matrix b)throws Exception{
      Matrix m = null;
      int num = 0;
      
      if(b.linhas == a.colunas){
         m = new Matrix(a.linhas, b.colunas);//Criacao matriz com numero de linhas de a e colunas de b
         Celula i1 = a.inicio;
         Celula i2 = b.inicio;
         Celula im = m.inicio;
         
         for(int i = 0; i < a.linhas; i++){
            Celula j1 = i1;
            Celula j2 = i2;
            Celula jm = im;
            
            for(int j = 0; j < b.colunas; j++){
               Celula k1 = j1;
               Celula k2 = j2;
               for(int k = 0; k < a.colunas; k++){
                  num += k2.elemento * k1.elemento;//Somar elemento com multiplicacao de elementos
                  k1 = k1.dir;//mover coluna de a
                  k2 = k2.inf;//mover linha de b
               }
               jm.elemento = num;
               num = 0;
               j2 = j2.dir;//Movimentacao colunas de b
               jm = jm.dir;//Movimentacao colunas de resposta
            }
            i1 = i1.inf;//Movimentacao linhas de a
            im = im.inf;//movimentacao linhas de resposta
         }
      
      }   
      return m;
   }
}
class TP16{
   public static void main(String[] args)throws Exception{
      int quantidade = MyIO.readInt();

      for(int x = 0; x < quantidade; x++){
         int linhas  = MyIO.readInt();
         int colunas = MyIO.readInt();
         Matrix m1 = new Matrix(linhas, colunas);
         m1.setElementosLeitura();//Set elementos m1
         m1.showDiagonalPrincipal();
         m1.showDiagonalSecundaria();

         linhas  = MyIO.readInt();//Ler linhas de m2
         colunas = MyIO.readInt();//Ler colunas de m2
         Matrix m2 = new Matrix(linhas, colunas);
         m2.setElementosLeitura();//Set elementos m2
         m1.soma(m2).mostrar();
         m1.multiplicacao(m2).mostrar();
      }
     
   }
}