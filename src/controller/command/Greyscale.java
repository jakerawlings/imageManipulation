package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the greyscale operation.
 * This operation sets the image in the layer that is currently being edited to greyscale.
 */
public class Greyscale implements ImageCommand {

  /**
   * Delegates the greyscale operation to the given LayeredImage.
   * @param image the image to be greyed.
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.toGreyscale();
  }
}
