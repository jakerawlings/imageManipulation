package controller.command;

import model.image.LayeredImage;

/**
 * This interface represents a high-level command. Each implementation of this interface
 * represents an operation that can be performed on a LayeredImage.
 */
public interface ImageCommand {

  /**
   * Delegates the operation to the given LayerImage.
   * @param image the LayeredImage on which the operation is performed
   */
  void goCmd(LayeredImage image);

}
