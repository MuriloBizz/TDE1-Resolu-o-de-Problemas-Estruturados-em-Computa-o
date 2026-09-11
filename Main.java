import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ManipuladorImagem manipulador = new ManipuladorImagem();

        String base = manipulador.obterCaminhoBase();
        BufferedImage imagem = manipulador.carregar(base + File.separator + "entrada_binarizada.png");

        BufferedImage imagemParaPilha = manipulador.clonar(imagem);
        BufferedImage imagemParaFila = manipulador.clonar(imagem);

        int corNova = 0xFF9C27B0; // roxo, exemplo do PDF

        FloodFillPilha floodPilha = new FloodFillPilha(manipulador);
        List<BufferedImage> framesPilha = floodPilha.executar(imagemParaPilha, 0, 0, corNova);

        FloodFillFila floodFila = new FloodFillFila(manipulador);
        List<BufferedImage> framesFila = floodFila.executar(imagemParaFila, 0, 0, corNova);

        manipulador.salvar(imagemParaPilha, base + File.separator + "saida_pilha.png");
        manipulador.salvar(imagemParaFila, base + File.separator + "saida_fila.png");

        exibirAnimacao(framesPilha);
    }

    private static void exibirAnimacao(List<BufferedImage> frames) {
        JFrame janela = new JFrame("Flood Fill - Animação");
        JLabel label = new JLabel();
        janela.add(label);
        janela.setSize(600, 600);
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