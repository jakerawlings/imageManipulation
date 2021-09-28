package controller;

/**
 * Represents the set of operations supported by a controller for this program.
 */
public interface ImageController {

  /**
   * Gives control to the controller.
   */
  void goController();

  /**
   * Runs the given script.
   * @param scriptName the file name of the script to be run
   */
  void goScript(String scriptName);

  /**
   * Gives control to the controller, allowing the user to manually input commands.
   */
  void goManual();
}
