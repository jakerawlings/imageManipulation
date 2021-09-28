package model.image;

import controller.FileType;
import java.io.IOException;
import java.util.ArrayList;
import model.Layer;

/**
 * Represents an image that supports multiple layers.
 */
public interface LayeredImage extends Image {

  /**
   * Sets the specified layer to the layer that is being edited.
   * @param layerName the name of the layer to be set as current
   * @throws IllegalArgumentException if no layer with the specified name is found
   */
  void setCurrent(String layerName) throws IllegalArgumentException;

  /**
   * Adds a layer to this LayeredImage and sets it as the current layer.
   * @param layerName the name to be given to the new layer
   * @throws IllegalArgumentException if layerName is null
   */
  void addLayer(String layerName) throws IllegalArgumentException;

  /**
   * Deletes the specified layer from this LayeredImage.
   * @param layerName the name of the layer to be deleted
   * @throws IllegalArgumentException if no layer with the specified name is found
   */
  void removeLayer(String layerName) throws IllegalArgumentException;

  /**
   * Sets the image in the current layer to the given Image.
   * @param image the image to be set to the current layer
   */
  void loadImage(Image image);

  /**
   * Makes the image in the specified layer completely transparent.
   * @param layerName the name of the layer to be made transparent
   * @throws IllegalArgumentException if no layer with the specified name is found
   */
  void setInvisible(String layerName) throws IllegalArgumentException;

  /**
   * Exports the topmost visible layer to a file with the specified name and file type.
   * @param fileName the name to be given to the exported file
   * @param fileType the type of exported file
   * @throws IOException if the image fails to be exported
   */
  void exportTopmost(String fileName, FileType fileType) throws IOException;

  /**
   * Gets the layers that make up this LayeredImage.
   * @return this image's layers
   */
  ArrayList<Layer> getLayers();

  /**
   * Gets the layer at the specified index.
   * @param index the index of the layer to be returned
   * @return the specified layer
   * @throws IllegalArgumentException if index is invalid
   */
  Layer getLayerAt(int index) throws IllegalArgumentException;

  /**
   * Sets the specified layer to the given layer in this LayeredImage.
   * @param index the layer to be replaced
   * @param layer the layer to be set
   * @throws IllegalArgumentException if index is invalid or layer is null
   */
  void setLayerAt(int index, Layer layer) throws IllegalArgumentException;

  /**
   * Replaces this LayeredImage with the given LayeredImage.
   * @param image the image to replace this image
   * @throws IllegalArgumentException if the given image is null
   */
  void replaceLayeredImage(LayeredImage image) throws IllegalArgumentException;
}
