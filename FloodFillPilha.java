import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FloodFillPilha {

    private ManipuladorImagem manipulador;

    public FloodFillPilha(ManipuladorImagem manipulador) {
        this.manipulador = manipulador;
    }

    public List<BufferedImage> executar(BufferedImage imagem, int xInicial, int yInicial, int novaCor) {
        List<BufferedImage> frames = new ArrayList<>();

        int corFundo = imagem.getRGB(xInicial, yInicial);
        if (corFundo == novaCor) return frames;

        MinhaPilha<Ponto> pilha = new MinhaPilha<>(16); // capacidade inicial pequena, cresce sozinha
        pilha.empilhar(new Ponto(xInicial, yInicial));

        int contador = 0;
        int intervaloFrame = 1;

        while (!pilha.vazia()) {
            Ponto p = pilha.desempilhar();
            int x = p.getX();
            int y = p.getY();

            if (!manipulador.dentroDosLimites(imagem, x, y)) continue;
            if (imagem.getRGB(x, y) != corFundo) continue;

            imagem.setRGB(x, y, novaCor);

            contador++;
            if (contador % intervaloFrame == 0) {
                frames.add(manipulador.clonar(imagem));
            }

            pilha.empilhar(new Ponto(x + 1, y));
            pilha.empilhar(new Ponto(x - 1, y));
            pilha.empilhar(new Ponto(x, y + 1));
            pilha.empilhar(new Ponto(x, y - 1));
        }

        frames.add(manipulador.clonar(imagem));
        return frames;
    }
}