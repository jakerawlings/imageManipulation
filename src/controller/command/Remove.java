package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the remove operation.
 * This operation deletes the specified layer.
 */
public class Remove implements ImageCommand {
  String layerName;

  /**
   * Creates a new Remove object.
   * @param layerName the name of the layer to be deleted
   * @throws IllegalArgumentException if layerName is null
   */
  public Remove(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }
    this.layerName = layerName;
  }

  /**
   * Delegates the remove operation to the given LayeredImage.
   * @param image the LayeredImage from which the layer is being deleted.
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.removeLayer(layerName);
  }
}
