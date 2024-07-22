import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class App extends JFrame {
	private Filters filters = new Filters();
	private JDesktopPane theDesktop;
	private int[][] matR, matG, matB;

	JFileChooser fc = new JFileChooser();
	String path = "";

	// make the image gray by getting the average of the colors
	public void grayScale(ActionEvent e) {
		int[][] mat = filters.grayScale(getMatrixRGB());
		geraImagem(mat, mat, mat);
	}

	// make it gray by getting the darkest color of each pixel
	public void darkGrayScale(ActionEvent e) {
		int[][] mat = filters.darkGrayScale(getMatrixRGB());
		geraImagem(mat, mat, mat);
	}

	// make it gray by getting the lightest color of each pixel
	public void lightGrayScale(ActionEvent e) {
		int[][] mat = filters.lightGrayScale(getMatrixRGB());
		geraImagem(mat, mat, mat);
	}

	// make the image binary by comparing the pixel color with 127
	public void binary(ActionEvent e) {
		int[][] mat = filters.binary(getMatrixRGB());
		geraImagem(mat, mat, mat);
	}

	// make the image negative by subtracting the pixel color from 255
	public void negative(ActionEvent e) {
		int[][] mat = filters.negative(getMatrixRGB());
		geraImagem(mat, mat, mat);
	}

	// wil generate a tricolor image based on the dominant color of each pixel
	public void dominantColor(ActionEvent e) {
		int[][][] mat = filters.dominantColor(getMatrixRGB());
		geraImagem(mat[0], mat[1], mat[2]);
	}

	// If the color of the pixel is greater than 167, it will mantain the color,
	// else make the pixel gray
	public void userFilter(ActionEvent e) {
		int[][][] mat = filters.userFilter(getMatrixRGB());
		geraImagem(mat[0], mat[1], mat[2]);
	}

	// remove the color selected by the user
	public void removeColor(ActionEvent e) {
		int[][][] mat = filters.removeColor(getMatrixRGB());
		geraImagem(mat[0], mat[1], mat[2]);
	}

	// remove the dominant color of the pixel
	public void removerCorDominante(ActionEvent e) {
		int[][][] mat = filters.removeDominantColor(getMatrixRGB());
		geraImagem(mat[0], mat[1], mat[2]);
	}

	public void smoothByAverage(ActionEvent e) {
		int size = Integer.parseInt(JOptionPane.showInputDialog("Tamanho da matriz: "));
		int[][] mat = filters.smoothByAverage(getMatrixRGB(), size);
		geraImagem(mat, mat, mat);
	}

	public void smoothByMedian(ActionEvent e) {
		int size = Integer.parseInt(JOptionPane.showInputDialog("Tamanho da matriz: "));
		int[][] mat = filters.smoothByMedian(getMatrixRGB(), size);
		geraImagem(mat, mat, mat);
	}

	public void sobel(ActionEvent e) {
		int[][] mat = filters.sobel(getMatrixRGB());
		geraImagem(mat, mat, mat);
	}

	public void gaussianFilter(ActionEvent e) {
		int size = Integer.parseInt(JOptionPane.showInputDialog("Tamanho da matriz: "));
		Vector<int[][]> mat = filters.gaussianFilter(getMatrixRGB(), size);
		geraImagem(mat.get(0), mat.get(1), mat.get(2));
	}

	public void gradientFilter(ActionEvent e) {
		int[][] mat = filters.gradientFilter(getMatrixRGB());
		geraImagem(mat, mat, mat);
	}

	public void insertImage(ActionEvent e) {
		Vector<int[][]> firstImageRGB = getMatrixRGB();
		Vector<int[][]> firstImageBlurred = filters.gaussianFilter(firstImageRGB, 8);

		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}

		String path2 = fc.getSelectedFile().getAbsolutePath();

		Vector<int[][]> secondImageRGB = getMatrixRGB(path2);

		Vector<int[][]> finalImage;
		Vector<int[][]> finalImageBlurred;

		finalImage = filters.insertImage(firstImageRGB, secondImageRGB);
		finalImageBlurred = filters.insertImage(firstImageBlurred, secondImageRGB);

		geraImagem(finalImage.get(0), finalImage.get(1), finalImage.get(2));
		geraImagem(finalImageBlurred.get(0), finalImageBlurred.get(1), finalImageBlurred.get(2));
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

		// Filters menu
		JMenu addMenu2 = new JMenu("Filters");
		JMenuItem item1 = new JMenuItem("Escala de cinza");
		JMenuItem item2 = new JMenuItem("Imagem binária");
		JMenuItem item3 = new JMenuItem("Negativa");
		JMenuItem item4 = new JMenuItem("Cor dominante");
		JMenuItem item5 = new JMenuItem("Cinza escuro");
		JMenuItem item6 = new JMenuItem("Cinza claro");
		JMenuItem item7 = new JMenuItem("Filtro pelo usuário");
		JMenuItem item8 = new JMenuItem("Remover cor pelo usuário");
		JMenuItem item9 = new JMenuItem("Remover cor dominante pelo usuário");
		JMenuItem item10 = new JMenuItem("Suavizar por média");
		JMenuItem item11 = new JMenuItem("Suavizar por mediana");
		JMenuItem item12 = new JMenuItem("Filtro de borda");
		JMenuItem item13 = new JMenuItem("Filtro de gauss");
		JMenuItem item14 = new JMenuItem("Filtro de gradiente");
		JMenuItem item15 = new JMenuItem("Insert Image");

		addMenu2.add(item1);
		addMenu2.add(item2);
		addMenu2.add(item3);
		addMenu2.add(item4);
		addMenu2.add(item5);
		addMenu2.add(item6);
		addMenu2.add(item7);
		addMenu2.add(item8);
		addMenu2.add(item9);
		addMenu2.add(item10);
		addMenu2.add(item11);
		addMenu2.add(item12);
		addMenu2.add(item13);
		addMenu2.add(item14);
		addMenu2.add(item15);

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

		item1.addActionListener(this::grayScale);
		item2.addActionListener(this::binary);
		item3.addActionListener(this::negative);
		item4.addActionListener(this::dominantColor);
		item5.addActionListener(this::darkGrayScale);
		item6.addActionListener(this::lightGrayScale);
		item7.addActionListener(this::userFilter);
		item8.addActionListener(this::removeColor);
		item9.addActionListener(this::removerCorDominante);
		item10.addActionListener(this::smoothByAverage);
		item11.addActionListener(this::smoothByMedian);
		item12.addActionListener(this::sobel);
		item13.addActionListener(this::gaussianFilter);
		item14.addActionListener(this::gradientFilter);
		item15.addActionListener(this::insertImage);

		setSize(600, 400);
		setVisible(true);
	}

	public Vector<int[][]> getMatrixRGB() {
		return getMatrixRGB(path);
	}

	public Vector<int[][]> getMatrixRGB(String path) {
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
