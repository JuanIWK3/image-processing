import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;

public class App extends JFrame {
	private JDesktopPane theDesktop;
	private int[][] matR, matG, matB;

	JFileChooser fc = new JFileChooser();
	String path = "";

	// make the image gray by getting the average of the colors
	public void escalaCinza(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				mat[i][j] = (matR[i][j] + matG[i][j] + matB[i][j]) / 3;
			}
		}

		geraImagem(mat, mat, mat);
	}

	// make the image binary by comparing the pixel color with 127
	public void imagemBinaria(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				if (matR[i][j] > 127) {
					mat[i][j] = 255;
				} else {
					mat[i][j] = 0;
				}
			}
		}

		geraImagem(mat, mat, mat);

	}

	// make the image negative by subtracting the pixel color from 255
	public void imagemNegativa(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				mat[i][j] = 255 - matR[i][j];
			}
		}

		geraImagem(mat, mat, mat);
	}

	// wil generate a tricolor image based on the dominant color of each pixel
	public void corDominante(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];
		int[][] mat2 = new int[matR.length][matR[0].length];
		int[][] mat3 = new int[matR.length][matR[0].length];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				if (matR[i][j] > matG[i][j] && matR[i][j] > matB[i][j]) {
					mat[i][j] = 255;
					mat2[i][j] = 0;
					mat3[i][j] = 0;
				} else if (matG[i][j] > matR[i][j] && matG[i][j] > matB[i][j]) {
					mat[i][j] = 0;
					mat2[i][j] = 255;
					mat3[i][j] = 0;
				} else {
					mat[i][j] = 0;
					mat2[i][j] = 0;
					mat3[i][j] = 255;
				}
			}
		}

		geraImagem(mat, mat2, mat3);
	}

	// make it gray by getting the darkest color of each pixel
	public void escalaCinzaEscuro(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];
		int[][] mat2 = new int[matR.length][matR[0].length];
		int[][] mat3 = new int[matR.length][matR[0].length];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				if (matR[i][j] < matG[i][j] && matR[i][j] < matB[i][j]) {
					mat[i][j] = matR[i][j];
					mat2[i][j] = matR[i][j];
					mat3[i][j] = matR[i][j];
				} else if (matG[i][j] < matR[i][j] && matG[i][j] < matB[i][j]) {
					mat[i][j] = matG[i][j];
					mat2[i][j] = matG[i][j];
					mat3[i][j] = matG[i][j];
				} else {
					mat[i][j] = matB[i][j];
					mat2[i][j] = matB[i][j];
					mat3[i][j] = matB[i][j];
				}
			}
		}

		geraImagem(mat, mat2, mat3);
	}

	// make it gray by getting the lightest color of each pixel
	public void escalaCinzaClaro(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];
		int[][] mat2 = new int[matR.length][matR[0].length];
		int[][] mat3 = new int[matR.length][matR[0].length];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				if (matR[i][j] > matG[i][j] && matR[i][j] > matB[i][j]) {
					mat[i][j] = matR[i][j];
					mat2[i][j] = matR[i][j];
					mat3[i][j] = matR[i][j];
				} else if (matG[i][j] > matR[i][j] && matG[i][j] > matB[i][j]) {
					mat[i][j] = matG[i][j];
					mat2[i][j] = matG[i][j];
					mat3[i][j] = matG[i][j];
				} else {
					mat[i][j] = matB[i][j];
					mat2[i][j] = matB[i][j];
					mat3[i][j] = matB[i][j];
				}
			}
		}

		geraImagem(mat, mat2, mat3);
	}

	// If the color of the pixel is greater than 167, it will mantain the color,
	// else make the pixel gray
	public void userFilter(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];
		int[][] mat2 = new int[matR.length][matR[0].length];
		int[][] mat3 = new int[matR.length][matR[0].length];

		String[] options = { "R", "G", "B" };
		int response = JOptionPane.showOptionDialog(null, "Escolha uma cor", "Filtro",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		String option = options[response];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				if (option == "R") {
					if (matR[i][j] > 167) {
						mat[i][j] = matR[i][j];
						mat2[i][j] = matG[i][j];
						mat3[i][j] = matB[i][j];
					} else {
						mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
						mat2[i][j] = mat[i][j];
						mat3[i][j] = mat[i][j];
					}
				} else if (option == "G") {
					if (matG[i][j] > 167) {
						mat[i][j] = matR[i][j];
						mat2[i][j] = matG[i][j];
						mat3[i][j] = matB[i][j];
					} else {
						mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
						mat2[i][j] = mat[i][j];
						mat3[i][j] = mat[i][j];
					}
				} else {
					if (matB[i][j] > 167) {
						mat[i][j] = matR[i][j];
						mat2[i][j] = matG[i][j];
						mat3[i][j] = matB[i][j];
					} else {
						mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
						mat2[i][j] = mat[i][j];
						mat3[i][j] = mat[i][j];
					}
				}
			}
		}

		geraImagem(mat, mat2, mat3);
	}

	// remove the color selected by the user
	public void removeColor(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];
		int[][] mat2 = new int[matR.length][matR[0].length];
		int[][] mat3 = new int[matR.length][matR[0].length];

		String[] options = { "R", "G", "B" };
		int response = JOptionPane.showOptionDialog(null, "Escolha uma cor", "Filtro",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		String option = options[response];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				if (option == "R") {
					mat[i][j] = 0;
					mat2[i][j] = matG[i][j];
					mat3[i][j] = matB[i][j];
				} else if (option == "G") {
					mat[i][j] = matR[i][j];
					mat2[i][j] = 0;
					mat3[i][j] = matB[i][j];
				} else {
					mat[i][j] = matR[i][j];
					mat2[i][j] = matG[i][j];
					mat3[i][j] = 0;
				}
			}
		}

		geraImagem(mat, mat2, mat3);
	}

	// remove the dominant color of the pixel
	public void removerCorDominante(ActionEvent e) {
		Vector<int[][]> rgbMat = getMatrixRGB();
		matR = rgbMat.elementAt(0);
		matG = rgbMat.elementAt(1);
		matB = rgbMat.elementAt(2);

		int[][] mat = new int[matR.length][matR[0].length];
		int[][] mat2 = new int[matR.length][matR[0].length];
		int[][] mat3 = new int[matR.length][matR[0].length];

		String[] options = { "R", "G", "B" };
		int response = JOptionPane.showOptionDialog(null, "Escolha uma cor", "Filtro",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		String option = options[response];

		for (int i = 0; i < matR.length; i++) {
			for (int j = 0; j < matR[0].length; j++) {
				if (option == "R") {
					if (matR[i][j] > matG[i][j] && matR[i][j] > matB[i][j]) {
						mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
						mat2[i][j] = mat[i][j];
						mat3[i][j] = mat[i][j];
					} else {
						mat[i][j] = matR[i][j];
						mat2[i][j] = matG[i][j];
						mat3[i][j] = matB[i][j];
					}
				} else if (option == "G") {
					if (matG[i][j] > matR[i][j] && matG[i][j] > matB[i][j]) {
						mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
						mat2[i][j] = mat[i][j];
						mat3[i][j] = mat[i][j];
					} else {
						mat[i][j] = matR[i][j];
						mat2[i][j] = matG[i][j];
						mat3[i][j] = matB[i][j];
					}
				} else {
					if (matB[i][j] > matR[i][j] && matB[i][j] > matG[i][j]) {
						mat[i][j] = (int) (0.3 * matR[i][j] + 0.59 * matG[i][j] + 0.11 * matB[i][j]);
						mat2[i][j] = mat[i][j];
						mat3[i][j] = mat[i][j];
					} else {
						mat[i][j] = matR[i][j];
						mat2[i][j] = matG[i][j];
						mat3[i][j] = matB[i][j];
					}
				}
			}
		}

		geraImagem(mat, mat2, mat3);
	}

	public App() {
		super("PhotoIFMG");
		JMenuBar bar = new JMenuBar();
		JMenu addMenu = new JMenu("Abrir");
		JMenuItem fileItem = new JMenuItem("Abir uma imagem de arquivo");
		JMenuItem newFrame = new JMenuItem("Internal Frame");

		addMenu.add(fileItem);
		addMenu.add(newFrame);
		bar.add(addMenu);

		JMenu addMenu2 = new JMenu("Processar");
		JMenuItem item1 = new JMenuItem("Escala de cinza");
		JMenuItem item2 = new JMenuItem("Imagem binária");
		JMenuItem item3 = new JMenuItem("Negativa");
		JMenuItem item4 = new JMenuItem("Cor dominante");
		JMenuItem item5 = new JMenuItem("Cinza escuro");
		JMenuItem item6 = new JMenuItem("Cinza claro");
		JMenuItem item7 = new JMenuItem("Filtro pelo usuário");
		JMenuItem item8 = new JMenuItem("Remover cor pelo usuário");
		JMenuItem item9 = new JMenuItem("Remover cor dominante pelo usuário");

		addMenu2.add(item1);
		addMenu2.add(item2);
		addMenu2.add(item3);
		addMenu2.add(item4);
		addMenu2.add(item5);
		addMenu2.add(item6);
		addMenu2.add(item7);
		addMenu2.add(item8);
		addMenu2.add(item9);

		bar.add(addMenu2);

		setJMenuBar(bar);

		theDesktop = new JDesktopPane();
		getContentPane().add(theDesktop);

		newFrame.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JInternalFrame frame = new JInternalFrame("Exemplo", true,
								true, true, true);
						Container container = frame.getContentPane();
						MyJPanel panel = new MyJPanel();
						container.add(panel, BorderLayout.CENTER);

						frame.pack();
						theDesktop.add(frame);
						frame.setVisible(true);

					}
				});

		// ler imagem
		fileItem.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int result = fc.showOpenDialog(null);
						if (result == JFileChooser.CANCEL_OPTION) {
							return;
						}
						path = fc.getSelectedFile().getAbsolutePath();

						JInternalFrame frame = new JInternalFrame("Exemplo", true,
								true, true, true);
						Container container = frame.getContentPane();
						MyJPanel panel = new MyJPanel();
						container.add(panel, BorderLayout.CENTER);

						frame.pack();
						theDesktop.add(frame);
						frame.setVisible(true);
					}
				});

		item1.addActionListener(this::escalaCinza);
		item2.addActionListener(this::imagemBinaria);
		item3.addActionListener(this::imagemNegativa);
		item4.addActionListener(this::corDominante);
		item5.addActionListener(this::escalaCinzaEscuro);
		item6.addActionListener(this::escalaCinzaClaro);
		item7.addActionListener(this::userFilter);
		item8.addActionListener(this::removeColor);
		item9.addActionListener(this::removerCorDominante);

		setSize(600, 400);
		setVisible(true);
	}

	// ler matrizes da imagem
	public Vector<int[][]> getMatrixRGB() {
		BufferedImage img;
		int[][] rmat = null;
		int[][] gmat = null;
		int[][] bmat = null;
		try {
			img = ImageIO.read(new File(path));

			rmat = new int[img.getHeight()][img.getWidth()];
			gmat = new int[img.getHeight()][img.getWidth()];
			bmat = new int[img.getHeight()][img.getWidth()];

			for (int i = 0; i < img.getHeight(); i++) {
				for (int j = 0; j < img.getWidth(); j++) {
					rmat[i][j] = getPixelData(img, j, i)[0];
					gmat[i][j] = getPixelData(img, j, i)[1];
					bmat[i][j] = getPixelData(img, j, i)[2];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Vector<int[][]> rgb = new Vector<int[][]>();
		rgb.add(rmat);
		rgb.add(gmat);
		rgb.add(bmat);

		return rgb;
	}

	// cria imagem da matriz
	private void geraImagem(int matrix1[][], int matrix2[][], int matrix3[][]) {
		int[] pixels = new int[matrix1.length * matrix1[0].length * 3];
		BufferedImage image = new BufferedImage(matrix1[0].length, matrix1.length, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();
		int pos = 0;
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix1[0].length; j++) {
				pixels[pos] = matrix1[i][j];
				pixels[pos + 1] = matrix2[i][j];
				pixels[pos + 2] = matrix3[i][j];
				pos += 3;
			}
		}
		raster.setPixels(0, 0, matrix1[0].length, matrix1.length, pixels);

		// Abre Janela da imagem
		JInternalFrame frame = new JInternalFrame("Processada", true,
				true, true, true);
		Container container = frame.getContentPane();
		MyJPanel panel = new MyJPanel();
		panel.setImageIcon(new ImageIcon(image));
		container.add(panel, BorderLayout.CENTER);

		frame.pack();
		theDesktop.add(frame);
		frame.setVisible(true);
	}

	public int[][] obterMatrizVermelha() {
		return matR;
	}

	public int[][] obterMatrizVerde() {
		return matG;
	}

	public int[][] obterMatrizAzul() {
		return matB;
	}

	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] {
				(argb >> 16) & 0xff, // red
				(argb >> 8) & 0xff, // green
				(argb) & 0xff // blue
		};

		return rgb;
	}

	class MyJPanel extends JPanel {
		private ImageIcon imageIcon;

		public MyJPanel() {
			imageIcon = new ImageIcon(path);
		}

		public void setImageIcon(ImageIcon i) {
			imageIcon = i;
		}

		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			imageIcon.paintIcon(this, g, 0, 0);
		}

		public Dimension getPreferredSize() {
			return new Dimension(imageIcon.getIconWidth(),
					imageIcon.getIconHeight());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		App app = new App();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}