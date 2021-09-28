package view;

import controller.Operations;
import model.image.Image;

/**
 * Represents a graphical view for an image, with the ability to allow the user to interactively
 * make changes to the image.
 */
public interface GraphicalView {

  /**
   * Displays the given image in the view.
   *
   * @param img the image to be displayed
   * @throws IllegalArgumentException if img is null
   */
  void setImage(Image img) throws IllegalArgumentException;

  /**
   * Sets all buttons in this {@code GraphicalView} to have their appropriate operations.
   *
   * @param op the operations to be set
   */
  void setOperations(Operations op);

  /**
   * Creates a text box with the given text to be visually seen by the user. Used to prompt the user
   * for input.
   *
   * @param prompt the text to be displayed
   * @return the {@code String} to be displayed
   */
  String createTextBox(String prompt);

  /**
   * Shows a popup with the given message in the view. Used to provide information to the user.
   *
   * @param prompt the {@code String} to be displayed
   */
  void createPopup(String prompt);

  /**
   * Refreshes / revalidates this {@code GraphicalView}. Used to recalculate the layout when
   * altering components.
   */
  void refresh();
}
