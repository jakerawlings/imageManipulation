package model.image;

import controller.FileType;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;
import model.pixel.Pixel;
import model.pixel.TransparentPixelImpl;

/**
 * An implementation for creating and altering images.
 */
public class ImageImpl implements Image {

  protected ArrayList<ArrayList<Pixel>> pixels;

  /**
   * Creates a new {@code ImageImpl} object.
   *
   * @param pixels an ArrayList of Arraylists of Pixels representing the pixels that make up this
   *               image
   * @throws IllegalArgumentException if pixels is null
   */
  public ImageImpl(ArrayList<ArrayList<Pixel>> pixels) throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Image pixels cannot be null");
    }
    if (!(isValidImage(pixels))) {
      throw new IllegalArgumentException("Image must be rectangular");
    }
    this.pixels = pixels;
  }

  private boolean isValidImage(ArrayList<ArrayList<Pixel>> pixels) {
    boolean isValid = true;
    for (int i = 0; i < pixels.size(); i += 1) {
      int height = pixels.get(0).size();
      if (pixels.get(i).size() != height) {
        isValid = false;
      }
    }
    return isValid;
  }

  @Override
  public void blurImage() {
    ArrayList<ArrayList<Double>> filter = new ArrayList();
    ArrayList<Double> col1 = new ArrayList(Arrays.asList(.0625, .125, .0625));
    ArrayList<Double> col2 = new ArrayList(Arrays.asList(.125, .25, .125));
    ArrayList<Double> col3 = col1;

    filter.add(col1);
    filter.add(col2);
    filter.add(col3);

    applyFilter(filter);
  }

  @Override
  public void sharpenImage() {
    ArrayList<ArrayList<Double>> filter = new ArrayList();
    ArrayList<Double> col1 = new ArrayList(Arrays.asList(-.125, -.125, -.125, -.125, -.125));
    ArrayList<Double> col2 = new ArrayList(Arrays.asList(-.125, .25, .25, .25, -.125));
    ArrayList<Double> col3 = new ArrayList(Arrays.asList(-.125, .25, 1.0, .25, -.125));
    ArrayList<Double> col4 = col2;
    ArrayList<Double> col5 = col1;

    filter.add(col1);
    filter.add(col2);
    filter.add(col3);
    filter.add(col4);
    filter.add(col5);

    applyFilter(filter);
  }


  private void applyFilter(ArrayList<ArrayList<Double>> ft) throws IllegalArgumentException {
    if (ft.size() % 2 == 0) {
      throw new IllegalArgumentException("Filter's width cannot be even");
    }
    for (int i = 0; i < ft.size(); i += 1) {
      if (ft.get(i).size() != ft.size()) {
        throw new IllegalArgumentException("Filter must be a square matrix");
      }
    }
    ArrayList<ArrayList<Pixel>> resultMatrix = new ArrayList<>();
    for (int i = 0; i < pixels.size(); i += 1) {
      resultMatrix.add(new ArrayList<Pixel>());
      for (int j = 0; j < pixels.get(i).size(); j += 1) {
        resultMatrix.get(i).add(
            new TransparentPixelImpl(multiplyMatrices(ft, filterHelp(ft, "red", i, j)),
                multiplyMatrices(ft, filterHelp(ft, "green", i, j)),
                multiplyMatrices(ft, filterHelp(ft, "blue", i, j)),
                getPixelAt(i, j).getAlpha())); // changed this to support transparency
      }
    }
    this.pixels = resultMatrix;
  }

  private ArrayList<ArrayList<Integer>> filterHelp(ArrayList<ArrayList<Double>> ft, String color,
      int x, int y) {
    ArrayList<ArrayList<Integer>> pixelMatrix = new ArrayList<>();
    for (int i = 0; i < ft.size(); i++) {
      pixelMatrix.add(new ArrayList<Integer>());
    }
    int dim = (ft.size() - 1) / 2;
    for (int i = 0, j = -dim; i < ft.size(); i += 1, j += 1) {
      for (int k = -dim; k <= dim; k += 1) {
        int newX = x + k;
        int newY = y + j;
        switch (color) {
          case "red":
            try {
              pixelMatrix.get(i).add(pixels.get(newX).get(newY).getRed());
            } catch (IndexOutOfBoundsException e) {
              pixelMatrix.get(i).add(0);
            }
            break;
          case "green":
            try {
              pixelMatrix.get(i).add(pixels.get(newX).get(newY).getGreen());
            } catch (IndexOutOfBoundsException e) {
              pixelMatrix.get(i).add(0);
            }
            break;
          case "blue":
            try {
              pixelMatrix.get(i).add(pixels.get(newX).get(newY).getBlue());
            } catch (IndexOutOfBoundsException e) {
              pixelMatrix.get(i).add(0);
            }
            break;
          default:
            break;
        }
      }
    }
    return pixelMatrix;
  }

  private int multiplyMatrices(ArrayList<ArrayList<Double>> ft,
      ArrayList<ArrayList<Integer>> pm) {
    int result = 0;
    for (int i = 0; i < pm.size(); i += 1) {
      for (int j = 0; j < pm.get(i).size(); j += 1) {
        double current = (pm.get(i).get(j) * (ft.get(i).get(j)));
        result += current;
      }
    }
    if (result > 255) {
      return 255;
    } else if (result < 0) {
      return 0;
    } else {
      return result;
    }
  }

  @Override
  public void toGreyscale() {
    ArrayList<ArrayList<Double>> ct = new ArrayList<>();
    ct.add(new ArrayList<Double>(Arrays.asList(0.2126, 0.2126, 0.2126)));
    ct.add(new ArrayList<Double>(Arrays.asList(0.7152, 0.7152, 0.7152)));
    ct.add(new ArrayList<Double>(Arrays.asList(0.0722, 0.0722, 0.0722)));

    applyColorTransformation(ct);
  }

  @Override
  public void toSepia() {
    ArrayList<ArrayList<Double>> ct = new ArrayList<>();
    ArrayList<Double> col1 = new ArrayList<Double>(Arrays.asList(.393, .349, .272));
    ArrayList<Double> col2 = new ArrayList<Double>(Arrays.asList(.769, .686, .534));
    ArrayList<Double> col3 = new ArrayList<Double>(Arrays.asList(.189, .168, .131));

    ct.add(col1);
    ct.add(col2);
    ct.add(col3);

    applyColorTransformation(ct);
  }

  private void applyColorTransformation(ArrayList<ArrayList<Double>> ct)
      throws IllegalArgumentException {
    if (ct.size() != 3) {
      throw new IllegalArgumentException("The given color transformation must be a 3 by 3 matrix");
    }
    for (int i = 0; i < ct.size(); i += 1) {
      if (ct.get(i).size() != ct.size()) {
        throw
            new IllegalArgumentException("The given color transformation must be a 3 by 3 matrix");
      }
    }
    for (int i = 0; i < pixels.size(); i += 1) {
      for (int j = 0; j < pixels.get(i).size(); j += 1) {
        Pixel curPixel = pixels.get(i).get(j);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(curPixel.getRed());
        colors.add(curPixel.getGreen());
        colors.add(curPixel.getBlue());
        ArrayList<Integer> newColors = transformVector(ct, colors);
        curPixel.setRed(newColors.get(0));
        curPixel.setGreen(newColors.get(1));
        curPixel.setBlue(newColors.get(2));
      }
    }
  }

  private ArrayList<Integer> transformVector(ArrayList<ArrayList<Double>> ct,
      ArrayList<Integer> colors) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < 3; i += 1) {
      int toAdd = (int) ((ct.get(0).get(i) * colors.get(0))
          + ct.get(1).get(i) * colors.get(1)
          + ct.get(2).get(i) * colors.get(2));
      if (toAdd > 255) {
        toAdd = 255;
      } else if (toAdd < 0) {
        toAdd = 0;
      }
      result.add(toAdd);
    }
    return result;
  }

  @Override
  public ArrayList<ArrayList<Pixel>> getPixels() {
    ArrayList<ArrayList<Pixel>> pixelCopy = new ArrayList<>();
    for (int i = 0; i < this.pixels.size(); i += 1) {
      pixelCopy.add(new ArrayList<Pixel>());
      for (int j = 0; j < this.pixels.get(i).size(); j += 1) {
        pixelCopy.get(i).add(getPixelAt(i, j));
      }
    }
    return pixelCopy;
  }

  @Override
  public Pixel getPixelAt(int x, int y) throws IllegalArgumentException {
    try {
      Pixel curPixel = this.pixels.get(x).get(y);
      return new TransparentPixelImpl(curPixel.getRed(), curPixel.getGreen(), curPixel.getBlue(),
          curPixel.getAlpha()); // changed this to support transparency
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid arguments");
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other instanceof ImageImpl) {
      ImageImpl o = (ImageImpl) other;
      if (pixels.size() != o.pixels.size()) {
        return false;
      }
      boolean isSame = true;
      for (int i = 0; i < pixels.size(); i += 1) {
        for (int j = 0; j < pixels.get(i).size(); j += 1) {
          if ((pixels.get(i).size() != o.pixels.get(i).size())
              || !(pixels.get(i).get(j).equals(((ImageImpl) other).pixels.get(i).get(j)))) {
            isSame = false;
          }
        }
      }
      return isSame;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pixels);
  }

  @Override
  public void exportFile(String filename, FileType fileType) throws IOException {
    if (fileType == FileType.PPM) {
      exportPPM(filename);
    } else {
      File newFile = new File(filename + "." + fileType.toString());
      FileOutputStream outputStream = new FileOutputStream(newFile);
      BufferedImage output;

      if (fileType == FileType.JPEG || fileType == FileType.JPG) {
        output = new BufferedImage(pixels.size(), pixels.get(0).size(), BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = output.getRaster();
        for (int i = 0; i < pixels.size(); i++) {
          for (int j = 0; j < pixels.get(0).size(); j++) {
            raster.setSample(i, j, 0, getPixelAt(i, j).getRed());
            raster.setSample(i, j, 1, getPixelAt(i, j).getGreen());
            raster.setSample(i, j, 2, getPixelAt(i, j).getBlue());
          }
        }
      } else {
        output = toBufferedImage();
      }
      ImageIO.write(output, fileType.toString(), outputStream);
    }
  }

  @Override
  public void exportPPM(String filename) throws FileNotFoundException {
    File newFile = new File(filename + ".ppm");
    PrintWriter output = new PrintWriter(newFile);
    StringBuilder sb = new StringBuilder();
    sb.append("P3" + System.lineSeparator() + "# " + filename + System.lineSeparator()
        + pixels.size() + " " + getMaxHeight() + System.lineSeparator() + "255");

    for (int i = 0; i < pixels.get(0).size(); i += 1) {
      sb.append(System.lineSeparator());
      for (int j = 0; j < pixels.size(); j += 1) {
        sb.append(" " + getPixelAt(j, i).getRed());
        sb.append(" " + getPixelAt(j, i).getGreen());
        sb.append(" " + getPixelAt(j, i).getBlue());
      }
    }
    output.print(sb);
    output.close();
  }

  private int getMaxHeight() {
    int curMax = 0;
    for (int i = 0; i < pixels.size(); i += 1) {
      if (pixels.get(i).size() > curMax) {
        curMax = pixels.get(i).size();
      }
    }
    return curMax;
  }

  @Override
  public void makeTransparent() {
    for (int i = 0; i < pixels.size(); i += 1) {
      for (int j = 0; j < pixels.get(i).size(); j += 1) {
        pixels.get(i).get(j).setAlpha(0);
      }
    }
  }

  @Override
  public BufferedImage toBufferedImage() {
    BufferedImage output;

    output = new BufferedImage(pixels.size(), pixels.get(0).size(),
        BufferedImage.TYPE_INT_ARGB);
    WritableRaster raster = output.getRaster();
    for (int i = 0; i < pixels.size(); i++) {
      for (int j = 0; j < pixels.get(0).size(); j++) {
        raster.setSample(i, j, 0, getPixelAt(i, j).getRed());
        raster.setSample(i, j, 1, getPixelAt(i, j).getGreen());
        raster.setSample(i, j, 2, getPixelAt(i, j).getBlue());
        raster.setSample(i, j, 3, getPixelAt(i, j).getAlpha());
      }
    }
    return output;
  }

  @Override
  public void downscale(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0
        || width > this.pixels.size() || height > this.pixels.get(0).size()) {
      throw new IllegalArgumentException("Invalid argument");
    }
    ArrayList<ArrayList<Pixel>> newPixels = new ArrayList<>();
    for (int i = 0; i < width; i += 1) {
      newPixels.add(new ArrayList<Pixel>());
      for (int j = 0; j < height; j += 1) {
        double xRatio = (double) i / (double) width;
        double yRatio = (double) j / (double) height;
        double x = xRatio * this.pixels.size();
        double y = yRatio * this.pixels.get(0).size();
        int red = computeColorValue(x, y, "red");
        int green = computeColorValue(x, y, "green");
        int blue = computeColorValue(x, y, "blue");
        Pixel newPixel = new TransparentPixelImpl(red, green, blue,
            pixels.get((int) x).get((int) y).getAlpha());
        newPixels.get(i).add(newPixel);
      }
    }
    this.pixels = newPixels;
  }

  private int computeColorValue(double x, double y, String color) {
    Pixel roundedDown = getPixelAt((int) Math.floor(x), (int) Math.floor(y));
    Pixel xUpYDown = getPixelAt(nextInt(x), (int) Math.floor(y));
    Pixel xDownYUp = getPixelAt((int) Math.floor(x), nextInt(y));
    Pixel roundedUp = getPixelAt(nextInt(x), nextInt(y));

    switch (color) {
      case "red":
        double m = xUpYDown.getRed() * (x - Math.floor(x))
            + roundedDown.getRed() * (nextInt(x) - x);
        double n = roundedUp.getRed() * (x - Math.floor(x))
            + xDownYUp.getRed() * (nextInt(x) - x);
        return (int) (n * (y - Math.floor(y)) + m * (nextInt(y) - y));
      case "green":
        double o = xUpYDown.getGreen() * (x - Math.floor(x))
            + roundedDown.getGreen() * (nextInt(x) - x);
        double p = roundedUp.getGreen() * (x - Math.floor(x))
            + xDownYUp.getGreen() * (nextInt(x) - x);
        return (int) (o * (y - Math.floor(y)) + p * (nextInt(y) - y));
      case "blue":
        double q = xUpYDown.getBlue() * (x - Math.floor(x))
            + roundedDown.getBlue() * (nextInt(x) - x);
        double r = roundedUp.getBlue() * (x - Math.floor(x))
            + xDownYUp.getBlue() * (nextInt(x) - x);
        return (int) (q * (y - Math.floor(y)) + r * (nextInt(y) - y));
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }

  private int nextInt(double d) {
    if (Math.ceil(d) == d) {
      return (int) (d + 1);
    } else {
      return (int) Math.ceil(d);
    }
  }

  @Override
  public void mosaic(int seeds) throws IllegalArgumentException {
    if (seeds <= 0 || seeds > pixels.size() * pixels.get(0).size()) {
      throw new IllegalArgumentException("Invalid number of seeds");
    }
    Map<Pixel, Point> seedList = makeSeedList(seeds);
    Map<Pixel, ArrayList<Pixel>> seedMap = new HashMap<>();

    for (Pixel p : seedList.keySet()) {
      seedMap.put(p, new ArrayList<Pixel>());
      seedMap.get(p).add(p);
    }

    for (int j = 0; j < pixels.size(); j += 1) {
      for (int k = 0; k < pixels.get(j).size(); k += 1) {
        Pixel curPixel = pixels.get(j).get(k);
        if (!(seedList.containsKey(curPixel))) {
          Pixel closestSeed = closestSeed(j, k, seedList);
          seedMap.get(closestSeed).add(curPixel);
        }
      }
    }

    for (ArrayList<Pixel> list : seedMap.values()) {
      int numPixels = 0;
      int avgRed = 0;
      int avgGreen = 0;
      int avgBlue = 0;
      for (Pixel p : list) {
        avgRed += p.getRed();
        avgGreen += p.getGreen();
        avgBlue += p.getBlue();
        numPixels += 1;
      }
      avgRed = avgRed / numPixels;
      avgGreen = avgGreen / numPixels;
      avgBlue = avgBlue / numPixels;
      for (Pixel pixel : list) {
        pixel.setRed(avgRed);
        pixel.setGreen(avgGreen);
        pixel.setBlue(avgBlue);
      }
    }
  }

  private Map<Pixel, Point> makeSeedList(int seeds) {
    Map<Pixel, Point> seedList = new HashMap<>();
    Random rand = new Random();
    for (int i = 0; i < seeds; i += 1) {
      int x = rand.nextInt(pixels.size());
      int y = rand.nextInt(pixels.get(0).size());
      Pixel randPixel = getPixelAt(x, y);
      seedList.put(randPixel, new Point(x, y));
    }
    return seedList;
  }

  private Pixel closestSeed(int x, int y, Map<Pixel, Point> seedList) {
    Pixel curLeast = null;
    Double curLeastDist = -1.0;
    for (Pixel p : seedList.keySet()) {
      Point coords = seedList.get(p);
      Double dist = Math.sqrt(Math.pow((coords.getX() - x), 2) + Math.pow((coords.getY() - y), 2));
      if (dist <= curLeastDist || curLeastDist == -1) {
        curLeastDist = dist;
        curLeast = p;
      }
    }
    return curLeast;
  }
}
