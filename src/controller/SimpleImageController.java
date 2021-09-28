package controller;

import controller.command.Blur;
import controller.command.Create;
import controller.command.Current;
import controller.command.Greyscale;
import controller.command.ImageCommand;
import controller.command.Invisible;
import controller.command.Load;
import controller.command.LoadLayered;
import controller.command.Remove;
import controller.command.Save;
import controller.command.SaveTopmost;
import controller.command.Sepia;
import controller.command.Sharpen;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.image.LayeredImage;
import view.SimpleImageTextView;

/**
 * Implementation of ImageController that represents a controller for this image editing program.
 * Serves to process user input and delegate operations to the model and view.
 */
public class SimpleImageController implements ImageController {

  private final LayeredImage image;
  private final Readable rd;
  private final SimpleImageTextView view;

  /**
   * Constructs a SimpleImageController object.
   *
   * @param image the image used by the controller to perform operations
   * @param rd    a readable object used by this controller to read user input
   * @param view  a view object used by the controller to display updated information
   * @throws IllegalArgumentException if any argument is null
   */
  public SimpleImageController(LayeredImage image, Readable rd, SimpleImageTextView view)
      throws IllegalArgumentException {
    if (image == null || rd == null || view == null) {
      throw new IllegalArgumentException("argument cannot be null");
    }
    this.image = image;
    this.rd = rd;
    this.view = view;
  }

  /**
   * Gives control to the controller, processes user input, and delegates operations to the image
   * and view.
   */
  @Override
  public void goController() {
    printMessage("Please enter \"script [scriptName]\" to use a script"
        + " or \"manual\" to input commands manually.");
    Scanner scan = new Scanner(rd);
    while (scan.hasNext()) {
      String in = scan.next();

      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        printMessage("Program quit.");
        return;
      } else if (in.equalsIgnoreCase("script")) {
        goScript(scan.next());
      } else if (in.equalsIgnoreCase("manual")) {
        printMessage("Please enter a command.");
        executeCommands(scan);
      } else {
        printMessage("Invalid input. Please enter \"script [scriptName]\" to use a script"
            + " or \"manual\" to input commands manually.");
      }
    }
  }

  @Override
  public void goScript(String scriptName) {
    try {
      File script = new File(scriptName);
      Scanner s = new Scanner(script);
      executeCommands(s);
      printMessage("Script complete.");
    } catch (FileNotFoundException e) {
      printMessage("Script not found. Please try again.");
    }
  }

  @Override
  public void goManual() {
    Scanner scan = new Scanner(rd);
    printMessage("Please enter a command.");
    executeCommands(scan);
  }

  private void executeCommands(Scanner scan) {
    Map<String, Function<Scanner, ImageCommand>> knownCommands;

    knownCommands = new HashMap<>();
    knownCommands.put("blur", s -> new Blur());
    knownCommands.put("sharpen", s -> new Sharpen());
    knownCommands.put("greyscale", s -> new Greyscale());
    knownCommands.put("sepia", s -> new Sepia());
    knownCommands.put("current", s -> new Current(s.next()));
    knownCommands.put("create", s -> new Create(s.next()));
    knownCommands.put("remove", s -> new Remove(s.next()));
    knownCommands.put("load", s -> new Load(s.next()));
    knownCommands.put("save", s -> new Save(s.next(), s.next()));
    knownCommands.put("invisible", s -> new Invisible(s.next()));
    knownCommands.put("saveTopmost", s -> new SaveTopmost(s.next(), s.next()));
    knownCommands.put("loadLayered", s -> new LoadLayered(s.next()));

    while (scan.hasNext()) {
      ImageCommand imageCommand;
      String in = scan.next();
      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        printMessage("Manual mode exited. Please enter \"script [scriptName]\" to use a script "
            + " or \"manual\" to input commands manually.");
        return;
      }
      Function<Scanner, ImageCommand> cmd =
          knownCommands.getOrDefault(in, null);
      if (cmd == null) {
        printMessage("Invalid command. Please enter a valid command.");
      } else {
        try {
          imageCommand = cmd.apply(scan);
          imageCommand.goCmd(image);
          printMessage("Command complete. Please enter another command.");
        } catch (IllegalArgumentException e) {
          printMessage("Invalid argument given to command. Please try again.");
        }
      }
    }
  }

  private void printMessage(String message) {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
