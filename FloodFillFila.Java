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

        Fila fila = new Fila(16);
        fila.enfileirar(new Coordenada(xInicial, yInicial));

        int contador = 0;

        int totalPixels = imagem.getWidth() * imagem.getHeight();
        int intervaloFrame = Math.max(1, totalPixels / 200);

        while (fila.tamanho != 0){
            Coordenada c = fila.desenfileirar();
            int x = c.getX();
            int y = c.getY();

            if (x < 0 || x >= imagem.getWidth() || y < 0 || y >= imagem.getHeight()) continue;
            if (imagem.getRGB(x, y) != corFundo) continue;

            imagem.setRGB(x, y, novaCor);

            contador++;
            if (contador % intervaloFrame == 0) {
                frames.add(manipulador.clonar(imagem));
            }

            fila.enfileirar(new Coordenada(x + 1, y));
            fila.enfileirar(new Coordenada(x - 1, y));
            fila.enfileirar(new Coordenada(x, y + 1));
            fila.enfileirar(new Coordenada(x, y - 1));
        }

        frames.add(manipulador.clonar(imagem)); 
        return frames;
    }
}