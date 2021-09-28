package model.image;

import controller.FileType;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.pixel.Pixel;

/**
 * Represents an image and includes all operations that any representation of an image must
 * support.
 */
public interface Image {

  /**
   * Blurs this image by a set amount.
   */
  void blurImage();

  /**
   * Sharpens this image by a set amount.
   */
  void sharpenImage();

  /**
   * Transforms this image to greyscale.
   */
  void toGreyscale();

  /**
   * Transforms this image to sepia.
   */
  void toSepia();

  /**
   * Exports this image as a PPM with the given file name.
   *
   * @param fileName the name to be given to the exported file
   */
  void exportPPM(String fileName) throws FileNotFoundException;

  /**
   * Exports this {@code TransparentImage} to the given file with the given {@link FileType}.
   *
   * @param filename the name or path of the file to be created
   * @param fileType the type of image file to be created
   */
  void exportFile(String filename, FileType fileType) throws IOException;

  /**
   * Gets all the pixels in this image.
   *
   * @return an {@code ArrayList<ArrayList<Pixel>>} of all pixels in this image
   */
  ArrayList<ArrayList<Pixel>> getPixels();

  /**
   * Gets the pixel at the specified coordinates in this image.
   *
   * @param x the x-coordinate of the pixel to be returned
   * @param y the y-coordinate of the pixel to be returned
   * @return the {@link Pixel} at the specified location
   */
  Pixel getPixelAt(int x, int y) throws IllegalArgumentException;

  /**
   * Makes all of the pixels in this image completely transparent.
   */
  void makeTransparent();

  /**
   * Converts this image to a {@code BufferedImage}.
   *
   * @return the appropriate {@code BufferedImage} corresponding to this image.
   */
  BufferedImage toBufferedImage();

  /**
   * Downsizes this image to the given width and height.
   *
   * @param width  the width of the downsized image
   * @param height the height ot the downsized image
   * @throws IllegalArgumentException if width or height are less than 1 or greater than the width
   *                                  or height of the image currently
   */
  void downscale(int width, int height) throws IllegalArgumentException;

  /**
   * Applies a mosaic filter to this image using the given number of seeds.
   *
   * @param seeds the number of seeds to use to the mosaic computation
   * @throws IllegalArgumentException if the number of seeds is less than 1 or greater than the
   *                                  number of pixels in this image
   */
  void mosaic(int seeds) throws IllegalArgumentException;
}
