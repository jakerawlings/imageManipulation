package controller.command;

import controller.ImportUtil;
import java.io.IOException;
import model.image.Image;
import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the load operation.
 * This operation imports the image specified by the given file name
 * and sets it as the image for the layer currently being edited.
 */
public class Load implements ImageCommand {
  private String fileName;

  /**
   * Creates a new Load object.
   * @param fileName the name of the file to be imported
   * @throws IllegalArgumentException if fileName is null
   */
  public Load(String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("File name cannot be null");
    }
    this.fileName = fileName;
  }

  /**
   * Delegates the load operation to the given LayeredImage.
   * @param image the LayeredImage to which the specified image is imported.
   */
  @Override
  public void goCmd(LayeredImage image) {
    try {
      Image importedImage = ImportUtil.importFile(fileName);
      image.loadImage(importedImage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
