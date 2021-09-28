package controller.command;

import controller.FileType;
import controller.ImportUtil;
import java.io.IOException;
import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the save operation.
 * This operation exports a LayeredImage to a folder with the given name
 * and exports each layer in the given file type.
 */
public class Save implements ImageCommand {

  private String fileName;
  private FileType fileType;

  /**
   * Creates a new Save object.
   * @param fileName the name to be given to the resulting folder
   * @param fileType the file type that each layer is exported as
   * @throws IllegalArgumentException if fileName is null or fileType cannot be recognized
   */
  public Save(String fileName, String fileType) throws IllegalArgumentException {
    if (fileName == null || fileType == null) {
      throw new IllegalArgumentException("arguments cannot be null");
    }
    this.fileName = fileName;
    this.fileType = ImportUtil.setFileType(fileType);
  }

  /**
   * Delegates the save operation to the given LayeredImage.
   * @param image the LayeredImage to be exported.
   */
  @Override
  public void goCmd(LayeredImage image) {
    try {
      image.exportFile(fileName, fileType);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
