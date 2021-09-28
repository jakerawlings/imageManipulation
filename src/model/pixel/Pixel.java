package model.pixel;

/**
 * Represents a pixel.
 */
public interface Pixel {

  /**
   * Gets the red value of this pixel.
   * @return the red value of this pixel
   */
  public int getRed();

  /**
   * Gets the green value of this pixel.
   * @return the green value of this pixel
   */
  public int getGreen();

  /**
   * Gets the blue value of this pixel.
   * @return the blue value of this pixel
   */
  public int getBlue();

  /**
   * Gets the alpha value of this pixel.
   * @return the alpha value of this pixel
   */
  public int getAlpha();

  /**
   * Sets the red value of this pixel to the given value.
   * @param red the value to be set as this pixel's red value
   * @throws IllegalArgumentException if the given value is less than 0 or greater than 255
   */
  public void setRed(int red) throws IllegalArgumentException;

  /**
   * Sets the green value of this pixel to the given value.
   * @param green the value to be set as this pixel's green value
   * @throws IllegalArgumentException if the given value is less than 0 or greater than 255
   */
  public void setGreen(int green) throws IllegalArgumentException;

  /**
   * Sets the blue value of this pixel to the given value.
   * @param blue the value to be set as this pixel's blue value
   * @throws IllegalArgumentException if the given value is less than 0 or greater than 255
   */
  public void setBlue(int blue) throws IllegalArgumentException;

  /**
   * Sets the alpha value of this pixel to the given value.
   * @param alpha the value to be set as this pixel's alpha value
   * @throws IllegalArgumentException if the given value is less than 0 or greater than 255
   */
  public void setAlpha(int alpha) throws IllegalArgumentException;
}
