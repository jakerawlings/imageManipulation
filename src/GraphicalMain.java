import controller.GraphicsController;
import controller.ImportUtil;
import controller.SimpleGraphicsController;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import model.Layer;
import model.image.Image;
import model.image.LayeredImageImpl;
import view.GraphicalView;
import view.ImageGraphicalView;

/**
 * Class to house the main method for the graphical view.
 */
public class GraphicalMain {

  /**
   * Creates a graphical view for editing an image.
   */
  public static void main(String[] args) throws IOException {
    Image blank = ImportUtil.createBlankImage();
    Layer l = new Layer(blank, 1, "Layer1");
    ArrayList<Layer> layers = new ArrayList<Layer>();
    layers.add(l);
    Image image = new LayeredImageImpl(layers);

    GraphicalView view = new ImageGraphicalView();
    view.setImage(blank);

    GraphicsController controller = new SimpleGraphicsController();
    controller.setImage(blank);
    controller.setView(view);

    JFrame frame = (JFrame) view;
    frame.setVisible(true);
  }
}
