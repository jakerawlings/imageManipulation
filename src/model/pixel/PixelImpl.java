package model.pixel;

import java.util.Objects;

/**
 * An implementation of the Pixel interface to represent pixels as a series of
 * red, green, and blue values.
 */
public class PixelImpl implements Pixel {

  protected int red;
  protected int green;
  protected int blue;

  /**
   * Creates a new {@code PixelImpl} object with the given parameters.
   * @param red an int [1, 255] representing the red value of this pixel
   * @param green an int [1, 255] representing the green value of this pixel
   * @param blue an int [1, 255] representing the blue value of this pixel
   * @throws IllegalArgumentException if any of the given parameters are not within [1, 255]
   */
  public PixelImpl(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || red > 255
        || green < 0 || green > 255
        || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("Invalid argument");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    return red;
  }

  @Override
  public void setRed(int red) throws IllegalArgumentException {
    if (red < 0 || red > 255) {
      throw new IllegalArgumentException("RGB value cannot be less than 0 or greater than 255");
    }
    this.red = red;
  }

  @Override
  public int getGreen() {
    return green;
  }

  @Override
  public void setGreen(int green) throws IllegalArgumentException {
    if (green < 0 || green > 255) {
      throw new IllegalArgumentException("RGB value cannot be less than 0 or greater than 255");
    }
    this.green = green;
  }

  @Override
  public int getBlue() {
    return blue;
  }

  @Override
  public int getAlpha() {
    return 255;
  }

  @Override
  public void setBlue(int blue) throws IllegalArgumentException {
    if (blue < 0 || blue > 255) {
      throw new IllegalArgumentException("RGB value cannot be less than 0 or greater than 255");
    }
    this.blue = blue;
  }

  @Override
  public void setAlpha(int alpha) throws IllegalArgumentException {
    if (alpha < 0 || alpha > 255) {
      throw new IllegalArgumentException("Alpha value cannot be less than 0 or greater than 255");
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other instanceof PixelImpl) {
      PixelImpl o = (PixelImpl) other;
      return this.red == o.red
          && this.green == o.green
          && this.blue == o.blue;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  @Override
  public String toString() {
    return "R: " + this.red + " G: " + this.green + " B: " + this.blue;
  }
}
