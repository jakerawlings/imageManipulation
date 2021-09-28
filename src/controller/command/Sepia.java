package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the sepia operation.
 * This operation sets the image in the layer that is currently being edited to sepia.
 */
public class Sepia implements ImageCommand {

  /**
   * Delegates the sepia operation to the given LayeredImage.
   * @param image the LayeredImage to be changed.
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.toSepia();
  }
}
