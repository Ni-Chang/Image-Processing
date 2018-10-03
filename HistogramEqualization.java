package homework1;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

public class HistogramEqualization {
	int width;
	int height;
	BufferedImage rowImage;
	BufferedImage processedImage;
	int[] pixels;
	int[] pixelNum = new int[256];
	double[] cumulativeDistribution = new double[256];

	HistogramEqualization(BufferedImage image) throws InterruptedException {
		rowImage = image;
		width = rowImage.getWidth();
		height = rowImage.getHeight();
		histogramEqualization();
	}

	public BufferedImage getProcessedImage() {
		return processedImage;
	}

	// Find Cumulative Distribution Function
	private void countPixels() throws InterruptedException {
		int i, j;
		pixels = new int[width * height];
		PixelGrabber pG = new PixelGrabber(rowImage, 0, 0, width, height, pixels, 0, width);
		pG.grabPixels();
		for (i = 0; i < height - 1; i++) {
			for (j = 0; j < width - 1; j++)
				pixelNum[pixels[i * width + j] & 0xff]++;
		}
		cumulativeDistribution[0] = (double) pixelNum[0] / (width * height) * 255;
		for (i = 1; i < 256; i++)
			cumulativeDistribution[i] = cumulativeDistribution[i - 1] + (double) pixelNum[i] / (width * height) * 255;
	}

	// Image Processing
	private void histogramEqualization() throws InterruptedException {
		int i, j, oldGray, newGray, rgb;
		countPixels();
		processedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		for (i = 0; i < height - 1; i++)
			for (j = 0; j < width - 1; j++) {
				oldGray = pixels[i * width + j] & 0xFF;
				newGray = (int) cumulativeDistribution[oldGray];
				rgb = (255 << 24) | (newGray << 16) | (newGray << 8) | (newGray);
				processedImage.setRGB(j, i, rgb);
			}
	}
}
