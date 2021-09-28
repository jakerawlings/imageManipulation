package model;


import java.util.ArrayList;
import model.image.Image;
import model.image.ImageImpl;
import model.pixel.Pixel;

/**
 * Represents a programatically generated image.
 */
public abstract class GeneratableImage {
  protected ArrayList<ArrayList<Pixel>> pixels;

  /**
   * Generates the image representation of this {@code GenerateableImage}.
   * @return this {@code GenerateableImage} as an {@link Image}
   */
  public Image generateImage() {
    return new ImageImpl(this.pixels);
  }

}
