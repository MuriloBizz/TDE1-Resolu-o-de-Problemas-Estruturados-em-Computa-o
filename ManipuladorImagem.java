import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ManipuladorImagem {

    public BufferedImage carregar(String caminho) throws IOException {
        return ImageIO.read(new File(caminho));
    }

    public void salvar(BufferedImage imagem, String caminho) throws IOException {
        ImageIO.write(imagem, "png", new File(caminho));
    }

    public BufferedImage clonar(BufferedImage original) {
        BufferedImage copia = new BufferedImage(
            original.getWidth(), original.getHeight(), original.getType()
        );
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                copia.setRGB(x, y, original.getRGB(x, y));
            }
        }
        return copia;
    }

    public boolean dentroDosLimites(BufferedImage imagem, int x, int y) {
        return x >= 0 && x < imagem.getWidth() && y >= 0 && y < imagem.getHeight();
    }
}