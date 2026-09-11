import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

public class ManipuladorImagem {

    public String obterCaminhoBase() throws URISyntaxException {
        File localizacao = new File(
            getClass().getProtectionDomain().getCodeSource().getLocation().toURI()
        );

        if (localizacao.isDirectory()) {
            return localizacao.getPath();
        } else {
            return localizacao.getParent();
        }
    }

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

}