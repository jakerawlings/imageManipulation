package model.image;

import controller.FileType;
import controller.ImportUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import model.Layer;
import model.pixel.Pixel;
import model.pixel.TransparentPixelImpl;

/**
 * Implementation of LayeredImage that represents an image with multiple layers.
 */
public class LayeredImageImpl implements LayeredImage {

  private ArrayList<Layer> layers;


  /**
   * Constructs a LayeredImageImpl object.
   *
   * @param layers an Arraylist of layers that represents the layers in this LayeredImageImpl
   * @throws IllegalArgumentException if layers is null
   */
  public LayeredImageImpl(ArrayList<Layer> layers) throws IllegalArgumentException {
    if (layers == null) {
      throw new IllegalArgumentException("Layers cannot be null");
    } else if (layers.size() > 0) {
      layers.get(0).setIsBeingEdited(true);
    }
    this.layers = layers;
  }

  @Override
  public void blurImage() {
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        Image newImage = l.getImage();
        newImage.blurImage();
        l.setImage(newImage);
      }
    }
  }

  @Override
  public void sharpenImage() {
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        Image newImage = l.getImage();
        newImage.sharpenImage();
        l.setImage(newImage);
      }
    }
  }

  @Override
  public void toGreyscale() {
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        Image newImage = l.getImage();
        newImage.toGreyscale();
        l.setImage(newImage);
      }
    }
  }

  @Override
  public void toSepia() {
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        Image newImage = l.getImage();
        newImage.toSepia();
        l.setImage(newImage);
      }
    }
  }

  @Override
  public ArrayList<ArrayList<Pixel>> getPixels() {
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        pixels = l.getImage().getPixels();
      }
    }
    return pixels;
  }

  @Override
  public Pixel getPixelAt(int x, int y) {
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        try {
          Pixel curPixel = l.getImage().getPixels().get(x).get(y);
          return new TransparentPixelImpl(curPixel.getRed(), curPixel.getGreen(),
              curPixel.getBlue(), curPixel.getAlpha());
        } catch (IndexOutOfBoundsException e) {
          throw new IllegalArgumentException("Invalid arguments");
        }
      }
    }
    return null;
  }

  @Override
  public void exportPPM(String fileName) throws FileNotFoundException {
    File newFile = new File(fileName);
    newFile.mkdir();
    File textFile = new File(fileName + "\\" + "layers.txt");
    PrintWriter pw = new PrintWriter(textFile);
    StringBuilder sb = new StringBuilder();
    for (Layer l : layers) {
      l.getImage().exportPPM(fileName);
      sb.append("Layer " + l.getLayerNumber() + ": " + l.getName() + "\n");
    }
    pw.println(sb);
    pw.close();
  }

  @Override
  public void exportFile(String fileName, FileType fileType) throws IOException {
    File newFile = new File(fileName);
    newFile.mkdir();
    File textFile = new File(fileName + "\\" + "layers.txt");
    PrintWriter pw = new PrintWriter(textFile);
    StringBuilder sb = new StringBuilder();
    sb.append(fileType.toString() + "\n");
    for (Layer l : layers) {
      l.getImage().exportFile(
          fileName + "/" + l.getName(), fileType);
      sb.append(l.getName() + "\n");
    }
    pw.println(sb);
    pw.close();
  }

  @Override
  public void setCurrent(String layerName) throws IllegalArgumentException {
    boolean isValidName = false;
    for (Layer l : layers) {
      if (l.getName().equals(layerName)) {
        l.setIsBeingEdited(true);
        isValidName = true;
      } else {
        l.setIsBeingEdited(false);
      }
    }
    if (!isValidName) {
      throw new IllegalArgumentException("Could not find layer named " + layerName);
    }
  }

  @Override
  public void addLayer(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null");
    }
    Image blank = ImportUtil.createBlankImage();
    Layer newLayer = new Layer(blank, this.layers.size() + 1, layerName);
    this.layers.add(newLayer);
    for (Layer l : layers) {
      l.setIsBeingEdited(false);
    }
    newLayer.setIsBeingEdited(true);
  }

  @Override
  public void removeLayer(String layerName) throws IllegalArgumentException {
    boolean isValidName = false;
    ArrayList<Layer> newLayers = new ArrayList<Layer>();
    for (Layer l : layers) {
      if (l.getName().equals(layerName)) {
        isValidName = true;
      } else {
        newLayers.add(l);
      }
    }
    if (!isValidName) {
      throw new IllegalArgumentException("Could not find layer named " + layerName);
    }
    if (newLayers.size() == 0) {
      throw new IllegalArgumentException("Cannot remove last layer of a layered image");
    }
    this.layers = newLayers;
  }

  @Override
  public void loadImage(Image image) {
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        l.setImage(image);
      }
    }
  }

  @Override
  public void setInvisible(String layerName) throws IllegalArgumentException {
    boolean isValidName = false;
    for (int i = 0; i < layers.size(); i += 1) {
      Layer l = getLayerAt(i);
      if (l.getName().equals(layerName)) {
        l.setIsVisible(false);
        Image newImage = l.getImage();
        newImage.makeTransparent();
        l.setImage(newImage);
        l.setIsBeingEdited(false);
        setLayerAt(i, l);
        try {
          Layer newCurrent = getLayerAt(i + 1);
          newCurrent.setIsBeingEdited(true);
          setLayerAt(i + 1, newCurrent);
        } catch (IndexOutOfBoundsException e) {
          throw new IllegalArgumentException("Cannot make last layer invisible.");
        }
        isValidName = true;
      }
    }
    if (!isValidName) {
      throw new IllegalArgumentException("Could not find layer named " + layerName);
    }
  }

  @Override
  public void makeTransparent() {
    for (Layer l : layers) {
      Image newImage = l.getImage();
      newImage.makeTransparent();
      l.setImage(newImage);
      l.setIsVisible(false);
    }
  }

  @Override
  public BufferedImage toBufferedImage() {
    for (Layer l : layers) {
      if (l.getIsBeingEdited() && l.getIsVisible()) {
        Image image = l.getImage();
        return image.toBufferedImage();
      }
    }
    return null;
  }

  @Override
  public void downscale(int width, int height) throws IllegalArgumentException {
    for (Layer l : layers) {
      Image image = l.getImage();
      image.downscale(width, height);
      l.setImage(image);
    }
  }

  @Override
  public void mosaic(int seeds) throws IllegalArgumentException {
    for (Layer l : layers) {
      if (l.getIsBeingEdited()) {
        Image image = l.getImage();
        image.mosaic(seeds);
        l.setImage(image);
      }
    }
  }

  @Override
  public void exportTopmost(String fileName, FileType fileType) throws IOException {
    for (Layer l : layers) {
      if (l.getIsBeingEdited() && l.getIsVisible()) {
        Image image = l.getImage();
        image.exportFile(fileName, fileType);
      }
    }
  }

  @Override
  public ArrayList<Layer> getLayers() {
    ArrayList<Layer> layersCopy = new ArrayList<Layer>();
    for (int i = 0; i < layers.size(); i += 1) {
      layersCopy.add(getLayerAt(i));
    }
    return layersCopy;
  }

  @Override
  public Layer getLayerAt(int index) throws IllegalArgumentException {
    if (index < 0 || index > layers.size()) {
      throw new IllegalArgumentException("Invalid index");
    }
    Layer l = layers.get(index);
    return new Layer(l.getImage(), l.getLayerNumber(), l.getName());
  }

  @Override
  public void setLayerAt(int index, Layer layer) throws IllegalArgumentException {
    if (index < 0 || index > layers.size() || layer == null) {
      throw new IllegalArgumentException("Invalid argument");
    }
    layers.set(index, layer);
  }

  @Override
  public void replaceLayeredImage(LayeredImage image) throws IllegalArgumentException {
    for (Layer l : layers) {
      this.removeLayer(l.getName());
    }
    for (Layer layer : image.getLayers()) {
      this.layers.add(layer);
    }
    setCurrent(image.getLayerAt(0).getName());
  }

  @Override
  public boolean equals(Object other) {
    boolean same = true;
    if (this == other) {
      return true;
    } else if (other instanceof LayeredImageImpl) {
      LayeredImageImpl o = (LayeredImageImpl) other;
      for (int i = 0; i < layers.size(); i += 1) {
        if (!this.getLayerAt(i).equals(o.getLayerAt(i))) {
          same = false;
        }
      }
    } else {
      return false;
    }
    return same;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.layers);
  }
}
