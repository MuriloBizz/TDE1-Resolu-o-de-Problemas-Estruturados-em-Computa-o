import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ManipuladorImagem manipulador = new ManipuladorImagem();

        String base = manipulador.obterCaminhoBase();
        String caminhoCompleto = base + File.separator + "entrada_binarizada.png";
        System.out.println("Tentando carregar: " + caminhoCompleto);

        BufferedImage imagem = manipulador.carregar(caminhoCompleto);

        BufferedImage imagemParaPilha = manipulador.clonar(imagem);
        BufferedImage imagemParaFila = manipulador.clonar(imagem);

        int corNova = 0xFF9C27B0; 

        int xInicial = imagem.getWidth() / 2;
        int yInicial = 10;

        int corPixelInicial = imagem.getRGB(xInicial, yInicial);
        System.out.println("Cor do pixel inicial: " + Integer.toHexString(corPixelInicial));

        FloodFillPilha floodPilha = new FloodFillPilha(manipulador);
        List<BufferedImage> framesPilha = floodPilha.executar(imagemParaPilha, xInicial, yInicial, corNova);

        FloodFillFila floodFila = new FloodFillFila(manipulador);
        List<BufferedImage> framesFila = floodFila.executar(imagemParaFila, xInicial, yInicial, corNova);

        manipulador.salvar(imagemParaPilha, base + File.separator + "saida_pilha.png");
        manipulador.salvar(imagemParaFila, base + File.separator + "saida_fila.png");

        exibirAnimacao(framesPilha, "Flood Fill - Pilha", 100, 50);
        exibirAnimacao(framesFila, "Flood Fill - Fila", 100, 700);
    }

    private static void exibirAnimacao(List<BufferedImage> frames, String titulo, int posX, int posY) {
        JFrame janela = new JFrame(titulo);
        JLabel label = new JLabel();
        janela.add(label);
        janela.setSize(600, 600);
        janela.setLocation(posX, posY);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);

        int[] indice = {0};

        Timer timer = new Timer(50, null);
        timer.addActionListener(e -> {
            if (indice[0] >= frames.size()) {
                timer.stop();
                return;
            }
            BufferedImage frame = frames.get(indice[0]);
            label.setIcon(new ImageIcon(frame.getScaledInstance(500, 500, Image.SCALE_FAST)));
            label.repaint();
            indice[0]++;
        });
        timer.start();
    }
}