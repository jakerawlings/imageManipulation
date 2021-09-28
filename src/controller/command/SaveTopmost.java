package controller.command;

import controller.FileType;
import controller.ImportUtil;
import java.io.IOException;
import model.image.LayeredImage;

/**
 * Implementation of ImageCommand that represents the saveTopmost operation.
 * This operation exports the topmost visible layer to the specified file format.
 */
public class SaveTopmost implements ImageCommand {

  private String fileName;
  private FileType fileType;

  /**
   * Creates a new SaveTopMost object.
   * @param fileName the name to be given to the resulting file
   * @param fileType the file type that the image is exported to
   * @throws IllegalArgumentException if fileName is null or fileType cannot be recognized
   */
  public SaveTopmost(String fileName, String fileType) throws IllegalArgumentException {
    if (fileName == null || fileType == null) {
      throw new IllegalArgumentException("arguments cannot be null");
    }
    this.fileName = fileName;
    this.fileType = ImportUtil.setFileType(fileType);
  }

  /**
   * Delegates the saveTopmost operation to the given LayeredImage.
   * @param image the LayeredImage to be exported.
   */
  @Override
  public void goCmd(LayeredImage image) {
    try {
      image.exportTopmost(fileName, fileType);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
