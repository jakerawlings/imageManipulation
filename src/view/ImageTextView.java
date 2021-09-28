package view;

import java.io.IOException;
import model.image.Image;

/**
 * Implementation of SimpleImageTextView that represents a text view for an image.
 * Gives support for rendering messages as well as rendering images in a very simple
 * way.
 */
public class ImageTextView implements SimpleImageTextView {
  private Image image;
  private Appendable ap;

  /**
   * Constructs an ImageTextView object.
   * @param i the image that this view displays
   * @param ap an Appendable object used to display the information
   * @throws IllegalArgumentException if any argument is null
   */
  public ImageTextView(Image i, Appendable ap) throws IllegalArgumentException {
    if (i == null || ap == null) {
      throw new IllegalArgumentException("argument cannot be null");
    }
    this.image = i;
    this.ap = ap;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("width: " + image.getPixels().size()
        + "\nheight: " + image.getPixels().get(0).size() + "\n");

    for (int i = 0; i < image.getPixels().size(); i += 1) {
      sb.append("\n NEXT COLUMN \n \n");
      for (int j = 0; j < image.getPixels().get(i).size(); j += 1) {
        sb.append(image.getPixelAt(i, j).toString());
        sb.append("\n");
      }
    }

    return sb.toString();
  }

  @Override
  public void renderMessage(String message) throws IOException {
    ap.append(message + "\n");
  }
}
