package controller.command;

import controller.ImportUtil;
import java.io.IOException;
import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the loadLayer operation.
 * This operation replaces the current LayeredImage with the specified
 * imported LayeredImage.
 */
public class LoadLayered implements ImageCommand {
  String fileName;

  /**
   * Creates a new LoadLayered object.
   * @param fileName the name of the file to be imported
   * @throws IllegalArgumentException if fileName is null
   */
  public LoadLayered(String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("File name cannot be null");
    }
    this.fileName = fileName;
  }

  /**
   * Delegates the loadLayered operation to the given LayeredImage.
   * @param image the LayeredImage to which the specified image is imported.
   */
  @Override
  public void goCmd(LayeredImage image) {
    LayeredImage layeredImage;
    try {
      layeredImage = ImportUtil.importLayeredFile(fileName);
      image.replaceLayeredImage(layeredImage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
