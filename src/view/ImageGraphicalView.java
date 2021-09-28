package view;

import controller.Operations;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.image.Image;

/**
 * Implementation of {@link GraphicalView} that shows a graphical view of an image.
 */
public class ImageGraphicalView extends JFrame implements GraphicalView {

  private final JMenuBar menuBar;
  private JLabel image;

  private JMenuItem importImage;
  private JMenuItem importLayered;
  private JMenuItem exportImage;
  private JMenuItem exportTopmost;
  private JMenuItem current;
  private JMenuItem blur;
  private JMenuItem sharp;
  private JMenuItem mosaic;
  private JMenuItem grey;
  private JMenuItem sepia;
  private JMenuItem downscale;
  private JMenuItem invisible;
  private JMenuItem create;
  private JMenuItem remove;
  private JMenuItem viewLayers;

  /**
   * Constructor for a {@code ImageGraphicalView}. Builds all menus and graphics and sets all
   * components to default values.
   */
  public ImageGraphicalView() {
    super();
    this.setTitle("Image Editor");
    this.setLocation(430, 50);
    this.setSize(650, 650);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    menuBar = new JMenuBar();
    createFileMenu();
    createEditMenu();
    createLayersMenu();
    this.setJMenuBar(menuBar);
    menuBar.setVisible(true);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new FlowLayout());

    this.add(mainPanel);
    mainPanel.setVisible(true);

    this.image = new JLabel();
  }

  @Override
  public void setImage(Image img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("image cannot be null");
    }

    this.remove(this.image);

    BufferedImage newImage = img.toBufferedImage();
    this.image = new JLabel(new ImageIcon(newImage));
    this.add(this.image);
    this.revalidate();
  }

  @Override
  public void setOperations(Operations op) {
    blur.addActionListener(e -> op.blur());
    sharp.addActionListener(e -> op.sharpen());
    grey.addActionListener(e -> op.greyscale());
    sepia.addActionListener(e -> op.sepia());
    mosaic.addActionListener(e -> op.mosaic());
    downscale.addActionListener(e -> op.downscale());
    importImage.addActionListener(e -> op.load());
    importLayered.addActionListener(e -> op.loadLayered());
    exportImage.addActionListener(e -> op.save());
    current.addActionListener(e -> op.current());
    invisible.addActionListener(e -> op.invisible());
    create.addActionListener(e -> op.create());
    remove.addActionListener(e -> op.remove());
    exportTopmost.addActionListener(e -> op.saveTopmost());
    viewLayers.addActionListener(e -> op.viewLayers());
  }

  @Override
  public String createTextBox(String prompt) {
    return JOptionPane.showInputDialog(prompt);
  }

  @Override
  public void createPopup(String prompt) {
    JOptionPane.showMessageDialog(null, prompt);
  }

  @Override
  public void refresh() {
    this.revalidate();
  }

  private void createFileMenu() {
    JMenu file = new JMenu("File");
    menuBar.add(file);

    importImage = new JMenuItem("Import Image");
    file.add(importImage);

    importLayered = new JMenuItem("Import Layered Image");
    file.add(importLayered);

    exportImage = new JMenuItem("Export Image");
    file.add(exportImage);

    exportTopmost = new JMenuItem("Export Top Layer");
    file.add(exportTopmost);
  }

  private void createEditMenu() {
    JMenu edit = new JMenu("Edit");
    menuBar.add(edit);

    JMenu filter = new JMenu("Filter");
    edit.add(filter);

    blur = new JMenuItem("Blur");
    filter.add(blur);

    sharp = new JMenuItem("Sharpen");
    filter.add(sharp);

    mosaic = new JMenuItem("Mosaic");
    filter.add(mosaic);

    JMenu transform = new JMenu("Color Transformation");
    edit.add(transform);

    grey = new JMenuItem("Greyscale");
    transform.add(grey);

    sepia = new JMenuItem("Sepia");
    transform.add(sepia);

    downscale = new JMenuItem("Downscale Image");
    edit.add(downscale);
  }

  private void createLayersMenu() {
    JMenu layers = new JMenu("Layers");
    menuBar.add(layers);

    current = new JMenuItem("Set Current Layer");
    layers.add(current);

    create = new JMenuItem("Create New Layer");
    layers.add(create);

    remove = new JMenuItem("Remove Layer");
    layers.add(remove);

    invisible = new JMenuItem("Make Layer Invisible");
    layers.add(invisible);

    viewLayers = new JMenuItem("View Layers");
    layers.add(viewLayers);
  }

  @Override
  public JMenuBar getJMenuBar() {
    return this.menuBar;
  }

  @Override
  public Component getComponent(int n) {
    return this.image;
  }
}
