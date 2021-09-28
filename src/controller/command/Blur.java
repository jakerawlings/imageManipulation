package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the blur operation.
 * This operation blurs the layer that is currently being edited.
 */
public class Blur implements ImageCommand {

  /**
   * Delegates the blur operation to the given LayeredImage.
   * @param image the image to be blurred
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.blurImage();
  }
}


