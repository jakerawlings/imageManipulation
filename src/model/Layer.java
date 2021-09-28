package model;

import java.util.ArrayList;
import java.util.Objects;
import model.image.Image;
import model.image.ImageImpl;
import model.pixel.Pixel;

/**
 * Represents a layer in a multi-layered image.
 */
public class Layer {

  private Image image;
  private int layerNumber;
  private boolean isVisible;
  private boolean isBeingEdited;
  private String name;

  /**
   * Constructs a Layer object.
   *
   * @param image       the image of this layer
   * @param layerNumber the number of this layer
   * @param name        the name of this layer
   * @throws IllegalArgumentException if layerNumber is less than 1 or any other arguments are null
   */
  public Layer(Image image, int layerNumber, String name)
      throws IllegalArgumentException {
    if (image == null || layerNumber < 1 || name == null) {
      throw new IllegalArgumentException("Invalid argument");
    }
    this.image = image;
    this.layerNumber = layerNumber;
    this.isVisible = true;
    this.isBeingEdited = false;
    this.name = name;
  }

  /**
   * Gets the image stored in this layer.
   *
   * @return a copy of this layer's image
   */
  public Image getImage() {
    ArrayList<ArrayList<Pixel>> pixelsCopy = image.getPixels();
    return new ImageImpl(pixelsCopy);
  }

  /**
   * Sets this layer's image.
   *
   * @param image the image to be set
   */
  public void setImage(Image image) {
    this.image = image;
  }

  /**
   * Gets whether this layer is currently being edited.
   *
   * @return if this layer is being edited
   */
  public boolean getIsBeingEdited() {
    return this.isBeingEdited;
  }

  /**
   * Sets if this layer is being edited.
   *
   * @param bool boolean to be set as this layer's isBeingEdited value
   */
  public void setIsBeingEdited(boolean bool) {
    this.isBeingEdited = bool;
  }

  /**
   * Gets whether this layer is visible.
   *
   * @return if this layer is visible
   */
  public boolean getIsVisible() {
    return this.isVisible;
  }

  /**
   * Sets if this layer is visible.
   *
   * @param bool boolean to be set as this layer's isVisible value
   */
  public void setIsVisible(boolean bool) {
    this.isVisible = bool;
  }

  /**
   * Gets this layer's name.
   *
   * @return this layer's name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets this layer's number.
   *
   * @return this layer's number
   */
  public int getLayerNumber() {
    return layerNumber;
  }

  @Override
  public boolean equals(Object other) {
    boolean same = true;
    if (this == other) {
      return true;
    } else if (other instanceof Layer) {
      Layer o = (Layer) other;
      return this.isVisible == o.isVisible
          && this.isBeingEdited == o.isBeingEdited
          && this.layerNumber == o.layerNumber
          && this.image.equals(o.image)
          && this.name.equals(o.name);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.isVisible, this.image, this.isBeingEdited,
        this.layerNumber, this.name);
  }
}
