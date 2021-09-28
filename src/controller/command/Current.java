package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the current operation.
 * This operation sets the layer that is currently being edited.
 */
public class Current implements ImageCommand {

  private String layerName;

  /**
   * Creates a new Current object.
   * @param layerName the name of the layer to be created
   * @throws IllegalArgumentException if layerName is null
   */
  public Current(String layerName) {
    this.layerName = layerName;
  }

  /**
   * Delegates the current operation to the given LayeredImage.
   * @param image the image to be changed.
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.setCurrent(layerName);
  }
}
