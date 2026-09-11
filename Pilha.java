public class Pilha {
    private Coordenada[] elementos;
    int topo;

    public Pilha(int capacidade) {
        elementos = new Coordenada[capacidade];
        topo = -1;
    }

    public void empilhar(Coordenada elemento) {
        if(topo == elementos.length - 1) {
        redimensionar();
        }
        elementos[++topo] = elemento;
    }

    public Coordenada desempilhar() {
        if (topo == -1) {
            throw new IllegalStateException("Pilha vazia");
        }
        Coordenada elemento = elementos[topo];
        elementos[topo--] = null; 
        topo--;
        return elemento;
    }
    private void redimensionar() {
        Coordenada[] novoArray = new Coordenada[elementos.length * 2];
        for (int i = 0; i <= topo; i++) {
            novoArray[i] = elementos[i];
        }
        elementos = novoArray;
    }
}