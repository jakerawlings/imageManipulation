package model;

import java.awt.Color;
import java.util.ArrayList;
import model.pixel.Pixel;
import model.pixel.PixelImpl;

/**
 * Class that allows for the configurable generation of a checkerboard image.
 */
public class Checkerboard extends GeneratableImage {

  /**
   * Constructs an instance of a {@Code Checkerboard} using the given parameters.
   *
   * @param w  the width, in squares, of the checkerboard
   * @param h  the height, in squares, of the checkerboard
   * @param s  the size, in pixels, of each square
   * @param c1 the first color in the sequence when generating the checkerboard
   * @param c2 the second color in the sequence when generating the checkerboard
   * @throws IllegalArgumentException if the given width, height, or size is not positive; or if
   *                                  either of the given colors are null
   */
  public Checkerboard(int w, int h, int s, Color c1, Color c2) throws IllegalArgumentException {
    if (w < 1 || h < 1 || s < 1 || c1 == null || c2 == null) {
      throw new IllegalArgumentException("Invalid parameter");
    }
    super.pixels = generatePixels(w, h, s, c1, c2);
  }

  private ArrayList<ArrayList<Pixel>> generatePixels(int w, int h, int s, Color c1, Color c2) {
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();
    Color curColor = c1;
    for (int i = 0; i < w * s; i += 1) {
      if (i % s == 0) {
        curColor = changeColor(curColor, c1, c2);
      }
      pixels.add(new ArrayList<>());
      for (int j = 0; j < h; j += 1) {
        curColor = changeColor(curColor, c1, c2);
        for (int k = 0; k < s; k += 1) {
          pixels.get(i).add(
              new PixelImpl(curColor.getRed(), curColor.getGreen(), curColor.getBlue()));
        }
      }
    }
    return pixels;
  }

  private Color changeColor(Color curColor, Color c1, Color c2) {
    if (curColor == c1) {
      return c2;
    } else {
      return c1;
    }
  }
}
