package controller;

import java.io.IOException;
import java.util.ArrayList;
import model.Layer;
import model.image.Image;
import model.image.LayeredImage;
import model.image.LayeredImageImpl;
import view.GraphicalView;

/**
 * Implementation of {@link GraphicsController} which handles user input for the graphical view.
 */
public class SimpleGraphicsController implements GraphicsController, Operations {

  private Image image;
  private GraphicalView view;

  @Override
  public void setImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    this.image = image;
  }

  @Override
  public void setView(GraphicalView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
    view.setOperations(this);
  }

  @Override
  public void blur() {
    image.blurImage();
    view.setImage(this.image);
  }

  @Override
  public void sharpen() {
    image.sharpenImage();
    view.setImage(this.image);
  }

  @Override
  public void greyscale() {
    image.toGreyscale();
    view.setImage(this.image);
  }

  @Override
  public void sepia() {
    image.toSepia();
    view.setImage(this.image);
  }

  @Override
  public void downscale() throws IllegalArgumentException {
    int width = Integer.parseInt(view.createTextBox("Please enter the new width"));
    int height = Integer.parseInt(view.createTextBox("Please enter the new height"));
    try {
      image.downscale(width, height);
    } catch (IllegalArgumentException e) {
      view.createPopup("Could not downscale image");
    }
    view.setImage(this.image);
  }

  @Override
  public void mosaic() throws IllegalArgumentException {
    int seeds = Integer.parseInt(view.createTextBox("Please enter the number of seeds"));
    try {
      image.mosaic(seeds);
    } catch (IllegalArgumentException e) {
      view.createPopup("Invalid number of seeds");
    }
    view.setImage(this.image);
  }

  @Override
  public void create() {
    if (!(image instanceof LayeredImage)) {
      convertToLayered();
    }
    String layerName =
        view.createTextBox("Please enter the name of the layer to be created");
    LayeredImage li = (LayeredImage) this.image;
    li.addLayer(layerName);
    setImage(li);
    view.setImage(this.image);
  }

  @Override
  public void current() {
    if (!(image instanceof LayeredImage)) {
      view.createPopup("Cannot set current layer of a non-layered image");
    } else {
      String layerName =
          view.createTextBox("Please enter the name of the layer to be made current");
      LayeredImage li = (LayeredImage) this.image;
      try {
        li.setCurrent(layerName);
      } catch (IllegalArgumentException e) {
        view.createPopup("The layer you entered could not be found");
      }
      setImage(li);
      view.setImage(this.image);
    }
  }

  @Override
  public void invisible() {
    if (!(image instanceof LayeredImage)) {
      image.makeTransparent();
    } else {
      String layerName =
          view.createTextBox("Please enter the name of the layer to be made transparent");
      LayeredImage li = (LayeredImage) this.image;
      try {
        li.setInvisible(layerName);
      } catch (IllegalArgumentException e) {
        view.createPopup("The layer you entered could not be found");
      }
      setImage(li);
    }
    view.setImage(image);
  }

  @Override
  public void remove() {
    if (!(image instanceof LayeredImage)) {
      view.createPopup("Cannot remove from a non-layered image");
    } else {
      String layerName =
          view.createTextBox("Please enter the name of the layer to be removed");
      LayeredImage li = (LayeredImage) this.image;
      try {
        li.removeLayer(layerName);
      } catch (IllegalArgumentException e) {
        view.createPopup("The layer you entered could not be removed");
      }
      setImage(li);
      view.setImage(this.image);
    }
  }

  @Override
  public void load() {
    if (!(image instanceof LayeredImage)) {
      convertToLayered();
    }
    String fileName =
        view.createTextBox("Please enter the name of the file to be imported");
    LayeredImage li = (LayeredImage) this.image;
    try {
      li.loadImage(ImportUtil.importFile(fileName));
    } catch (IOException e) {
      view.createPopup("Could not load the specified file");
    }
    setImage(li);
    view.setImage(this.image);
  }

  @Override
  public void loadLayered() throws IllegalArgumentException {
    String fileName =
        view.createTextBox("Please enter the name of the layered file to be imported");
    try {
      setImage(ImportUtil.importLayeredFile(fileName));
    } catch (IOException e) {
      view.createPopup("Could not load the specified file");
    }
    view.setImage(this.image);
  }

  @Override
  public void save() throws IllegalArgumentException {
    String fileName =
        view.createTextBox("Please enter the the name you wish to save this file to");
    String fileString =
        view.createTextBox("Please enter the file type you wish to save this file as");
    try {
      FileType fileType = ImportUtil.setFileType(fileString);
      image.exportFile(fileName, fileType);
    } catch (IOException | IllegalArgumentException e) {
      view.createPopup("Could not export file");
    }
  }

  @Override
  public void saveTopmost() throws IllegalArgumentException {
    String fileName =
        view.createTextBox("Please enter the the name you wish to save this file to");
    String fileString =
        view.createTextBox("Please enter the file type you wish to save this file as");
    if (!(image instanceof LayeredImage)) {
      try {
        FileType fileType = ImportUtil.setFileType(fileString);
        image.exportFile(fileName, fileType);
      } catch (IOException | IllegalArgumentException e) {
        view.createPopup("Could not export file");
      }
    }
    LayeredImage li = (LayeredImage) this.image;
    try {
      FileType fileType = ImportUtil.setFileType(fileString);
      li.exportTopmost(fileName, fileType);
    } catch (IOException | IllegalArgumentException e) {
      view.createPopup("Could not export file");
    }
  }

  @Override
  public void viewLayers() {
    if (!(image instanceof LayeredImage)) {
      view.createPopup("No layers in this image");
    } else {
      LayeredImage li = (LayeredImage) this.image;
      StringBuilder sb = new StringBuilder();
      sb.append("Layers:\n");
      for (Layer l : li.getLayers()) {
        sb.append(l.getName() + "\n");
      }
      view.createPopup(sb.toString());
    }
  }

  private void convertToLayered() {
    ArrayList<Layer> layers = new ArrayList<Layer>();
    Layer layer = new Layer(this.image, 1, "Layer1");
    layers.add(layer);
    LayeredImage newImage = new LayeredImageImpl(layers);
    setImage(newImage);
  }
}
