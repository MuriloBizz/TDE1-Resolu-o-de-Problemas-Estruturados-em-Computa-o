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

        Pilha pilha = new Pilha(16);
        pilha.empilhar(new Coordenada(xInicial, yInicial));

        int contador = 0;
        int totalPixels = imagem.getWidth() * imagem.getHeight();
        int intervaloFrame = Math.max(1, totalPixels / 200);

        while (pilha.topo != -1) {
            Coordenada c = pilha.desempilhar();
            int x = c.getX();
            int y = c.getY();

            if (x < 0 || x >= imagem.getWidth() || y < 0 || y >= imagem.getHeight()) continue;
            if (imagem.getRGB(x, y) != corFundo) continue;

            imagem.setRGB(x, y, novaCor);

            contador++;
            if (contador % intervaloFrame == 0) {
                frames.add(manipulador.clonar(imagem));
            }

            pilha.empilhar(new Coordenada(x + 1, y));
            pilha.empilhar(new Coordenada(x - 1, y));
            pilha.empilhar(new Coordenada(x, y + 1));
            pilha.empilhar(new Coordenada(x, y - 1));
        }

        frames.add(manipulador.clonar(imagem));
        return frames;
    }
}