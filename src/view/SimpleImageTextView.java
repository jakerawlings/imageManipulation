package view;

import java.io.IOException;

/**
 * Represents a simple text view for an image. Contains all necessary functionality of a simple
 * representation of an image.
 */
public interface SimpleImageTextView {

  /**
   * Displays the given message to the appendable in this view.
   *
   * @param message the message to be displayed
   * @throws IOException if writing to the appendable fails
   */
  void renderMessage(String message) throws IOException;

}
