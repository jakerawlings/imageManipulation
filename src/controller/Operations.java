package controller;

/**
 * Represents the set of operations that can be performed on an image by the controller.
 */
public interface Operations {

  /**
   * Blurs this image by a set amount.
   */
  void blur();

  /**
   * Sharpens an image by a set amount.
   */
  void sharpen();

  /**
   * Transforms an image to greyscale.
   */
  void greyscale();

  /**
   * Transforms an image to sepia.
   */
  void sepia();

  /**
   * Downsizes an image. Prompts the user to input a new width and height for the image.
   *
   * @throws IllegalArgumentException if width or height are less than 1 or greater than the width *
   *                                  or height of the image currently
   */
  void downscale() throws IllegalArgumentException;

  /**
   * Applies a mosaic effect to an image.
   *
   * @throws IllegalArgumentException if the number of seeds is less than 1 or greater than the
   *                                  number of pixels in this image
   */
  void mosaic() throws IllegalArgumentException;

  /**
   * Creates a new layer for an image.
   */
  void create();

  /**
   * Sets a layer in an image to the current layer. Prompts the user to specify a layer.
   *
   * @throws IllegalArgumentException if no layer with the specified name is found
   */
  void current() throws IllegalArgumentException;

  /**
   * Makes a layer in an image invisible.
   *
   * @throws IllegalArgumentException if no layer with the specified name is found
   */
  void invisible() throws IllegalArgumentException;

  /**
   * Removes a layer from an image.
   *
   * @throws IllegalArgumentException if no layer with the specified name is found
   */
  void remove() throws IllegalArgumentException;

  /**
   * Sets the image in the current layer of an image to be a different image.
   */
  void load();

  /**
   * Replaces all layers in an image with different images.
   *
   * @throws IllegalArgumentException if the given image is null
   */
  void loadLayered() throws IllegalArgumentException;

  /**
   * Saves an image to a file. Prompts the user to choose a file location and file type.
   *
   * @throws IllegalArgumentException if the file cannot be saved to the given location.
   */
  void save() throws IllegalArgumentException;

  /**
   * Saves only the topmost visible layer in an image to a file.
   *
   * @throws IllegalArgumentException if the file cannot be saved to the given location.
   */
  void saveTopmost() throws IllegalArgumentException;

  /**
   * Displays the names of all the layers of an image for the user to see.
   */
  void viewLayers();
}
