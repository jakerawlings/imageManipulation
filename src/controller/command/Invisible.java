package controller.command;

import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the invisible operation.
 * This operation makes the image in the layer that is currently being edited
 * completely transparent.
 */
public class Invisible implements ImageCommand {
  String layerName;

  /**
   * Creates a new Invisible object.
   * @param layerName the name of the layer to be created
   * @throws IllegalArgumentException if layerName is null
   */
  public Invisible(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }
    this.layerName = layerName;
  }

  /**
   * Delegates the invisible operation to the given LayeredImage.
   * @param image the LayeredImage to be made transparent.
   */
  @Override
  public void goCmd(LayeredImage image) {
    image.setInvisible(layerName);
  }
}
