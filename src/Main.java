import controller.GraphicsController;
import controller.ImageController;
import controller.ImportUtil;
import controller.SimpleGraphicsController;
import controller.SimpleImageController;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JFrame;
import model.Layer;
import model.image.LayeredImage;
import model.image.LayeredImageImpl;
import view.GraphicalView;
import view.ImageGraphicalView;
import view.ImageTextView;

/**
 * Class to house the main method.
 */
public class Main {

  /**
   * Creates an image and a view and gives control to the controller.
   */
  public static void main(String[] args) {

    ArrayList<Layer> layers = new ArrayList<Layer>();
    layers.add(new Layer(ImportUtil.createBlankImage(), 1, "layer1"));
    LayeredImage layeredImage = new LayeredImageImpl(layers);

    switch (args[0]) {
      case "-script":
        ImageController controllerScript = new SimpleImageController(layeredImage,
            new InputStreamReader(System.in), new ImageTextView(layeredImage, System.out));
        try {
          controllerScript.goScript(args[1]);
          System.exit(0);
        } catch (IndexOutOfBoundsException e) {
          throw new IllegalArgumentException("Could not find script");
        }
        break;
      case "-text":
        ImageController controllerText = new SimpleImageController(layeredImage,
            new InputStreamReader(System.in), new ImageTextView(layeredImage, System.out));
        controllerText.goManual();
        break;
      case "-interactive":
        GraphicalView view = new ImageGraphicalView();
        view.setImage(layeredImage);

        GraphicsController controller = new SimpleGraphicsController();
        controller.setImage(layeredImage);
        controller.setView(view);

        JFrame frame = (JFrame) view;
        frame.setVisible(true);
        break;
      default:
        throw new IllegalArgumentException("Invalid command line argument");
    }
  }
}
