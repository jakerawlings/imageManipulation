package model.pixel;

import java.util.Objects;

/**
 * Represents a type of {@link Pixel} that supports transparency.
 */
public class TransparentPixelImpl extends PixelImpl {

  private int alpha;

  /**
   * Constructs a {@code TransparentPixelImpl} with the given qualities.
   *
   * @param red   the red value of this pixel
   * @param green the green value of this pixel
   * @param blue  the blue value of this pixel
   * @param alpha the transparency value of this pixel
   * @throws IllegalArgumentException if any given int is less than 0 or greater than 255
   */
  public TransparentPixelImpl(int red, int green, int blue, int alpha)
      throws IllegalArgumentException {
    super(red, green, blue);
    if (alpha < 0 || alpha > 255) {
      throw new IllegalArgumentException("alpha value must be between 0 and 255");
    }
    this.alpha = alpha;
  }

  /**
   * Constructs a {@code TransparentPixelImpl} with the given qualities. Sets the alpha value to
   * 255.
   *
   * @param red   the red value of this pixel
   * @param green the green value of this pixel
   * @param blue  the blue value of this pixel
   * @throws IllegalArgumentException if any given int is less than 0 or greater than 255
   */
  public TransparentPixelImpl(int red, int green, int blue)
      throws IllegalArgumentException {
    super(red, green, blue);
    this.alpha = 255;
  }

  @Override
  public String toString() {
    return "R: " + this.red + " G: " + this.green + " B: " + this.blue + " A: " + this.alpha;
  }

  @Override
  public int getAlpha() {
    return alpha;
  }

  @Override
  public void setAlpha(int alpha) throws IllegalArgumentException {
    super.setAlpha(alpha);
    this.alpha = alpha;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other instanceof TransparentPixelImpl) {
      TransparentPixelImpl o = (TransparentPixelImpl) other;
      return this.red == o.red
          && this.green == o.green
          && this.blue == o.blue
          && this.alpha == o.alpha;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue, this.alpha);
  }
}
