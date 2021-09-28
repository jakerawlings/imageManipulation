package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the create operation.
 * This operation creates a new layer.
 */
public class Create implements ImageCommand {
  private String layerName;

  /**
   * Creates a new Create object.
   * @param layerName the name of the layer to be created
   * @throws IllegalArgumentException if layerName is null
   */
  public Create(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }
    this.layerName = layerName;
  }

  /**
   * Delegates the create operation to the given LayeredImage.
   * @param image the image to be added to
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.addLayer(this.layerName);
  }
}
