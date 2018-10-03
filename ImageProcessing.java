package homework1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ImageProcessing {

	static BufferedImage image;
	private static Scanner input;
	private static Scanner input2;
	private static Scanner output;

	private static int welcomeMessage() {
		System.out.println("Choose process No.:");
		System.out.println("1. Histogram equalization");
		System.out.println("2. Grayscale linar change");
		System.out.println("3. Grayscale stretching");
		System.out.println("4. Quit");
		input = new Scanner(System.in);
		int opt = input.nextInt();
		return opt;
	}

	private static void readFile() throws IOException {
		String filePath;
		File in;
		System.out.println("Enter Image:");
		input2 = new Scanner(System.in);
		filePath = input2.nextLine();
		System.out.println(filePath);
		in = new File(filePath);
		image = ImageIO.read(in);
	}

	private static void outputFile(BufferedImage out) throws IOException {
		String filePath;
		System.out.println("Output Image:");
		output = new Scanner(System.in);
		filePath = output.nextLine();
		File file = new File(filePath);
		ImageIO.write(out, "jpg", file);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		int opt = welcomeMessage();
		while (opt != 4) {
			switch (opt) {
			// Histogram Equalization
			case 1:
				readFile();
				HistogramEqualization hEProcessing = new HistogramEqualization(image);
				BufferedImage hEImage = hEProcessing.getProcessedImage();
				outputFile(hEImage);
				System.out.println("Success！");
				break;
			// Grayscale Linar Change
			case 2:
				readFile();
				int c, d;
				System.out.println("Input process range c d:");
				Scanner parameter1 = new Scanner(System.in);
				c = parameter1.nextInt();
				d = parameter1.nextInt();
				GrayscaleLinarChange lCProcessing = new GrayscaleLinarChange(image, c, d);
				BufferedImage lCImage = lCProcessing.getProcessedImage();
				outputFile(lCImage);
				System.out.println("Success！");
				break;
			// Grayscale Stretching
			case 3:
				readFile();
				int a, b, c1, d1;
				System.out.println("Input process range a b c d:");
				Scanner parameter2 = new Scanner(System.in);
				a = parameter2.nextInt();
				b = parameter2.nextInt();
				c1 = parameter2.nextInt();
				d1 = parameter2.nextInt();
				GrayscaleStretching sProcessing = new GrayscaleStretching(image, a, b, c1, d1);
				BufferedImage sImage = sProcessing.getProcessedImage();
				outputFile(sImage);
				System.out.println("Success！");
				break;
			}
			opt = welcomeMessage();
		}
	}
}