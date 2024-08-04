# OOD (CS 3500)

Image Processing Project

## Table of Contents

- [Contributors](#contributors)
- [Process](#process)
- [Model](#model)
- [Controller](#controller)
- [GUI](#gui)
- [Tests](#tests)
- [Problems](#problems)
- [Image Sources](#image sources)

## Contributors

- John Luke Mewborne
- John Rotondo

## Process

We started by designing the image class and deciding on how to represent
each pixel. In the model package you will notice an ImageProcessor
interface and a Processor class. This class controls the functionality of the
image with the interface containing documentation. Then we just had to add
a controller. We decided to use a map for the commands and map each
command to a corresponding class that will carry out said command. For part 2 we
added the matrixUtil class which represents a matrix and its functions.
This includes regular matrix multiplication and corresponding index
multiplication. The MatrixUtil class is used by filtering methods in the
Processor class. For adding downscaling we did not have to change any part 
of the code from the past, this method was more about building upon what we 
already had. For downscaling we had to add new GUI functionality and another
command line to allow it to show up in the processorGUI. In the downscaling 
method we had to convert many lists and variables to doubles, then back to integers
due to having to find the ceiling and the floor values of a pixel, on the downscaled
image, that would be between two pixels on the original image.

## Model

In the model package are the definitions for an Image. The Image
class represents the image and its pixels. The ImageProcessor
interface contains documentation for the public methods used by
Processor. ImageUtil is used for reading .pmm files and contains the
main method. The Processor class alters Images. The MatrixUtil class
is used for creating and using matrices for arithmetic.

## Controller

The Controller Package contains the commands and Controller class and
associated Interface for handling user input. The commands package within
this package contains classes for every command. The ControllerInterface
is used to define the method for starting the processor and the
CommandInterface is for running commands through the command classes.

## GUI

The GUI is located in the view package. The GUI provides a simple
user interface that allows loading altering and saving images. In addition,
it displays a histogram for the image a user is currently editing.

## Tests

There are test files for the controller, Image, ImageUtil, MatrixUtil, and
processor.

## Problems

Not to many issues. Any problem that did come up resulted from not
maintaining consistency throughout the project, which then had to be fixed
at a later time. For the filters we had a hard time aligning the matrices
so that they could be properly multiplied.

## Image Sources

- Cloud Image
    - https://aminoapps.com/c/anime/page/blog/anime-pixel-art/D7tP_uL1okExZ4jPbGXpQax50zq68b
- Mario Image
    - https://donaldantonacker.wordpress.com/2015/02/02/hello-world/