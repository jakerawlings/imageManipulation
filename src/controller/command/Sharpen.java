package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the sharpen operation.
 * This operation sharpens the image in the layer that is currently being edited.
 */
public class Sharpen implements ImageCommand {

  /**
   * Delegates the sharpen operation to the given LayeredImage.
   * @param image the LayeredImage to be sharpened.
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.sharpenImage();
  }
}
