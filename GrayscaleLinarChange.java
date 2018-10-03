package homework1;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

public class GrayscaleLinarChange {
	int width;
	int height;
	BufferedImage rowImage;
	BufferedImage processedImage;
	int[] pixels;
	int a, b, c, d;
	double k;

	GrayscaleLinarChange(BufferedImage image, int c, int d) throws InterruptedException {
		rowImage = image;
		width = rowImage.getWidth();
		height = rowImage.getHeight();
		this.c = c;
		this.d = d;
		linarChange();
	}

	public BufferedImage getProcessedImage() {
		return processedImage;
	}

	// Find maximum and minimum grayscale
	private void findabk() {
		int i, a, b;
		a = b = pixels[0] & 0xFF;
		for (i = 1; i < (width * height); i++) {
			if ((pixels[i] & 0xFF) < a)
				a = pixels[i] & 0xFF;
			if ((pixels[i] & 0xFF) > b)
				b = pixels[i] & 0xFF;
		}
		this.a = a;
		this.b = b;
		k = (double) (d - c) / (b - a);
	}

	// Image Processing
	private void linarChange() throws InterruptedException {
		int i, j, oldGray, newGray;
		pixels = new int[width * height];
		PixelGrabber pG = new PixelGrabber(rowImage, 0, 0, width, height, pixels, 0, width);
		pG.grabPixels();
		findabk();
		processedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		for (i = 0; i < height - 1; i++)
			for (j = 0; j < width; j++) {
				oldGray = pixels[i * width + j] & 0xFF;
				newGray = (int) (k * (oldGray - a) + c);
				int rgb = (255 << 24) | (newGray << 16) | (newGray << 8) | (newGray);
				processedImage.setRGB(j, i, rgb);
			}
	}
}
