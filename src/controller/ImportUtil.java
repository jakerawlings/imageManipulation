package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.Layer;
import model.image.Image;
import model.image.ImageImpl;
import model.image.LayeredImage;
import model.image.LayeredImageImpl;
import model.pixel.Pixel;
import model.pixel.PixelImpl;
import model.pixel.TransparentPixelImpl;

/**
 * A utility class containing methods to import various types of {@link Image} from a file.
 */
public class ImportUtil {


  private static Image importPPM(String filename) {
    Scanner sc = null;

    try {
      sc = new Scanner(new FileInputStream(filename + ".ppm"));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<ArrayList<Pixel>>();
    for (int i = 0; i < width; i++) {
      pixels.add(new ArrayList<>());
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels.get(j).add(new PixelImpl(r, g, b));
      }
    }
    return new ImageImpl(pixels);
  }

  /**
   * Converts a file of any type listed in FileType to the ImageImpl representation of an image.
   *
   * @param filename the name of the file to be imported
   * @return an ImageImpl representing the image being imported
   * @throws IOException if reading the file fails
   */
  public static Image importFile(String filename) throws IOException {
    if (filename.length() > 3
        && filename.substring(filename.length() - 3).equalsIgnoreCase("ppm")) {
      return importPPM(filename.substring(0, filename.length() - 4));
    } else {
      BufferedImage inputImage = ImageIO.read(new File(filename));
      ArrayList<ArrayList<Pixel>> importedPixels = new ArrayList<>();

      for (int i = 0; i < inputImage.getWidth(); i += 1) {
        importedPixels.add(new ArrayList<>());
        for (int j = 0; j < inputImage.getHeight(); j += 1) {
          Color curColor = new Color(inputImage.getRGB(i, j), true);
          importedPixels.get(i).add(
              new TransparentPixelImpl(curColor.getRed(), curColor.getGreen(), curColor.getBlue(),
                  curColor.getAlpha()));
        }
      }
      return new ImageImpl(importedPixels);
    }
  }

  /**
   * Converts an exported LayeredImageImpl back into the LayeredImageImpl representation of an
   * image.
   *
   * @param filename the name of the folder that represents the exported layeredImageImpl
   * @return a LayerImageImpl representing the image being imported
   * @throws IOException if reading the file fails
   */
  public static LayeredImage importLayeredFile(String filename) throws IOException {
    File file = new File(filename + "\\" + "layers.txt");
    Scanner s = new Scanner(file);
    FileType fileType = setFileType(s.next());
    String layerName;
    ArrayList<Layer> layers = new ArrayList<Layer>();
    int numLayers = 1;
    while (s.hasNext()) {
      layerName = s.next();
      layers.add(new Layer(ImportUtil.importFile(
          filename + "\\" + layerName + "." + fileType.toString()),
          numLayers, layerName));
      numLayers += 1;
    }
    return new LayeredImageImpl(layers);
  }

  /**
   * Returns the corresponding FileType based on the given String.
   *
   * @param fileType String representing a FileType
   * @return the FileType represented by fileType
   * @throws IllegalArgumentException if given String does not match any FileType
   */
  public static FileType setFileType(String fileType) throws IllegalArgumentException {
    switch (fileType.toLowerCase()) {
      case "jpg":
        return FileType.JPG;
      case "jpeg":
        return FileType.JPEG;
      case "png":
        return FileType.PNG;
      case "bmp":
        return FileType.BMP;
      case "wbmp":
        return FileType.WBMP;
      case "gif":
        return FileType.GIF;
      case "ppm":
        return FileType.PPM;
      default:
        throw new IllegalArgumentException("Invalid file type given");
    }
  }

  /**
   * Creates a completely white placeholder image.
   *
   * @return an {@link ImageImpl} consisting of only white pixels.
   */
  public static Image createBlankImage() {
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();
    for (int i = 0; i < 500; i += 1) {
      pixels.add(new ArrayList<Pixel>());
      for (int j = 0; j < 500; j += 1) {
        pixels.get(i).add(new TransparentPixelImpl(255, 255, 255, 255));
      }
    }
    return new ImageImpl(pixels);
  }
}
