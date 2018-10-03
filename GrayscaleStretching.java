package homework1;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

public class GrayscaleStretching {
	int width;
	int height;
	BufferedImage rowImage;
	BufferedImage processedImage;
	int[] pixels;
	int a, b, c, d;
	double k1, k2, k3;

	GrayscaleStretching(BufferedImage image, int a, int b, int c, int d) throws InterruptedException {
		rowImage = image;
		width = rowImage.getWidth();
		height = rowImage.getHeight();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		stretching();
	}

	public BufferedImage getProcessedImage() {
		return processedImage;
	}

	// Find three slopes
	private void findk() {
		k1 = (double) c / a;
		k2 = (double) (d - c) / (b - a);
		k3 = (double) (255 - d) / (255 - b);
	}

	// Changing function
	private int findNewGray(int oldGray) {
		int newGray;
		if (oldGray < a)
			newGray = (int) (k1 * oldGray);
		else if (oldGray > b)
			newGray = (int) (k3 * (oldGray - b) + d);
		else
			newGray = (int) (k2 * (oldGray - a) + c);
		return newGray;
	}

	// Image Processing
	private void stretching() throws InterruptedException {
		int i, j, oldGray, newGray;
		pixels = new int[width * height];
		PixelGrabber pG = new PixelGrabber(rowImage, 0, 0, width, height, pixels, 0, width);
		pG.grabPixels();
		findk();
		processedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		for (i = 0; i < height - 1; i++)
			for (j = 0; j < width; j++) {
				oldGray = pixels[i * width + j] & 0xFF;
				newGray = findNewGray(oldGray);
				int rgb = (255 << 24) | (newGray << 16) | (newGray << 8) | (newGray);
				processedImage.setRGB(j, i, rgb);
			}
	}
}
