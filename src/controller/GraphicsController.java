package controller;

import model.image.Image;
import view.GraphicalView;

/**
 * Represents a controller for an image with a graphics-based view.
 */
public interface GraphicsController {

  /**
   * Sets the given image to be used by the controller.
   *
   * @param image the image to be set
   * @throws IllegalArgumentException if image is null
   */
  void setImage(Image image) throws IllegalArgumentException;

  /**
   * Sets the given view to be used by the controller.
   *
   * @param view the view to be set
   * @throws IllegalArgumentException if view if null
   */
  void setView(GraphicalView view) throws IllegalArgumentException;
}
