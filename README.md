# Image Manipulation Tool

This program serves as a tool to import, manipulate, and export images.

This program supports text-based, script input, and graphical interaction. To select your prefered method of intereaction, simply pass one of the following arguments when running the program:

text-based: -text

script input: -script [scriptname]

graphical: -interactive


To use any of the commands you can type your commands or navigate through any of the menus in the top left of the graphical view. If a command requires additional input, a text prompt will appear and you can input your desired value. If the requested command cannot be executed with the given input, an error message will appear. Be sure to remember to include the file extension when entering the file name for load. Below is a list of all of the supported commands

Supported Commands:

- blur
- create [fileName]
- current [layerName]
- greyscale
- invisible [layerName]
- load [fileName]
- loadLayered [fileName]
- remove [layerName]
- save [fileName][fileType]
- saveTopmost [fileName]
- sepia
- sharpen
- see current layers
- mosaic [seedNumber]
- downscale [width] [height]
