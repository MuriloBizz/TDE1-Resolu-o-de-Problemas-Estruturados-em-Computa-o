import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FloodFillFila {

    private ManipuladorImagem manipulador;

    public FloodFillFila(ManipuladorImagem manipulador) {
        this.manipulador = manipulador;
    }

    public List<BufferedImage> executar(BufferedImage imagem, int xInicial, int yInicial, int novaCor) {
        List<BufferedImage> frames = new ArrayList<>();

        int corFundo = imagem.getRGB(xInicial, yInicial);
        if (corFundo == novaCor) return frames;

        MinhaFila<Ponto> fila = new MinhaFila<>(imagem.getWidth() * imagem.getHeight());
        fila.enfileirar(new Ponto(xInicial, yInicial));

        int contador = 0;
        int intervaloFrame = 1;

        while (!fila.vazia()) {
            Ponto p = fila.desenfileirar();
            int x = p.getX();
            int y = p.getY();

            if (!manipulador.dentroDosLimites(imagem, x, y)) continue;
            if (imagem.getRGB(x, y) != corFundo) continue;

            imagem.setRGB(x, y, novaCor);

            contador++;
            if (contador % intervaloFrame == 0) {
                frames.add(manipulador.clonar(imagem));
            }

            fila.enfileirar(new Ponto(x + 1, y));
            fila.enfileirar(new Ponto(x - 1, y));
            fila.enfileirar(new Ponto(x, y + 1));
            fila.enfileirar(new Ponto(x, y - 1));
        }

        frames.add(manipulador.clonar(imagem));
        return frames;
    }
}