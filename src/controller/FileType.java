package controller;

/**
 * Enumeration for the file types that our program supports.
 */
public enum FileType {
  JPG, JPEG, PNG, BMP, WBMP, GIF, PPM;

  @Override
  public String toString() {
    switch (this) {
      case JPG:
        return "jpg";
      case JPEG:
        return "jpeg";
      case PNG:
        return "png";
      case BMP:
        return "bmp";
      case WBMP:
        return "wbmp";
      case GIF:
        return "gif";
      case PPM:
        return "ppm";
      default:
        break;
    }
    return "";
  }
}
