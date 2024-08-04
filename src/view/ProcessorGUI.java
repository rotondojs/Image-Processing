package view;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.ScrollPaneConstants;

import javax.swing.filechooser.FileNameExtensionFilter;

import model.ImageProcessor;

/**
 * Class that constructs the processorGUI.
 */
public class ProcessorGUI extends JFrame implements ActionListener, ItemListener {

  private JPanel mainPanel;
  private JPanel imageList;
  private JPanel currentImagePanel;
  private JPanel functionPanel;
  private JPanel brightenPanel;
  private JPanel downPanel;
  private JPanel greyPanel;
  private JPanel savePanel;
  private JPanel histogramPanel;

  private JCheckBox[] images;
  private JLabel currentImage;
  private JTextField brightText;
  private JTextField downText;
  private JTextField downText2;
  private JTextField greyText;
  private JTextField saveText;
  private JComboBox<String> extensionList;
  private String[] keys;
  private String[] validExtensions = {".ppm", ".png", ".jpg"};
  private String currentKey;
  private String rgb;
  private String saveName;
  private String extension;
  private String brightVal;
  private double height;
  private double width;

  private int imagesLoaded = 0;

  private JLabel imageLabel;
  private ImageProcessor processor;

  private Histogram histogram;

  /**
   * Constructor that takes in an image processor.
   *
   * @param processor the image processor.
   */
  public ProcessorGUI(ImageProcessor processor) throws IllegalArgumentException {
    super();
    JButton[] functions;
    JScrollPane mainScrollPane;
    if (processor == null) {
      throw new IllegalArgumentException("Input cannot be null.");
    }
    this.processor = processor;
    keys = processor.getLoadKeys().toArray(new String[0]);
    for (int i = 0; i < keys.length; i++) {
      imagesLoaded += 1;
    }
    setTitle("Image Processor");
    setSize(1000, 1000);

    mainPanel = new JPanel();

    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // LEFT PANEL (Image List)----------------------------------------------------------
    imageList = new JPanel();
    imageList.setBorder(BorderFactory.createEmptyBorder(25, 50, 0, 0));
    imageList.setLayout(new BoxLayout(imageList, BoxLayout.Y_AXIS));
    imageList.setSize(50, 400);
    imageList.setMaximumSize(new Dimension(7000, 1080));
    imageList.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    imageList.setBackground(new Color(175, 175, 175));
    imageLabel = new JLabel("----Images----");
    imageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    imageList.add(imageLabel);
    displayImageList();

    imageList.setVisible(true);
    mainPanel.add(imageList);

    // MID PANEL ----------------------------------------------------------
    currentImagePanel = new JPanel();
    currentImagePanel.setSize(300, 400);
    currentImagePanel.setLayout(new BoxLayout(currentImagePanel, BoxLayout.Y_AXIS));
    currentImagePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    currentImagePanel.setBackground(new Color(220, 220, 220));

    currentImagePanel.setVisible(true);
    mainPanel.add(currentImagePanel);

    // right PANEL ----------------------------------------------------------
    functionPanel = new JPanel();
    functionPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 0, 0));
    functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.Y_AXIS));
    functionPanel.setSize(50, 400);
    functionPanel.setMaximumSize(new Dimension(7000, 1080));
    functionPanel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
    functionPanel.setBackground(new Color(175, 175, 175));
    JLabel functionLabel = new JLabel("----Functions----");
    functionLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    functionPanel.add(functionLabel);

    functions = new JButton[14];
    functions[0] = new JButton("Brighten");
    functions[1] = new JButton("Horizontal Flip");
    functions[2] = new JButton("Vertical Flip");
    functions[3] = new JButton("Blur");
    functions[4] = new JButton("Sharpen");
    functions[5] = new JButton("Intensity");
    functions[6] = new JButton("Luma");
    functions[7] = new JButton("Greyscale");
    functions[8] = new JButton("Greyscale Filter");
    functions[9] = new JButton("Sepia");
    functions[10] = new JButton("Value");
    functions[11] = new JButton("load");
    functions[12] = new JButton("Save");
    functions[13] = new JButton("Downscaling");

    for (int i = 0; i < functions.length; i++) {
      functions[i].setAlignmentX(JComponent.CENTER_ALIGNMENT);
      functions[i].addActionListener(this);
      functionPanel.add(functions[i]);
    }

    functionPanel.setVisible(true);
    mainPanel.add(functionPanel);

    if (imagesLoaded > 0) {
      if (keys.length > 0) {
        currentKey = keys[0];
      }
      currentImage = new JLabel(new ImageIcon(processor.getLoad(keys[0]).toBufferedImage()));
      initHistogram();
    }
    setVisible(true);
  }

  /**
   * Performs an action to the image.
   *
   * @param e action event that is to be performed.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Brighten":
        brightenWindow();
        functionUpdate();
        break;
      case "Downscaling":
        getDoubles();
        //processor.downscaling(currentKey, currentKey + "Downscaling", getDoubles(), getDoubles());
        functionUpdate();
        break;
      case "Horizontal Flip":
        processor.horizFlip(currentKey, currentKey + "-horizFlip");
        functionUpdate();
        break;
      case "Vertical Flip":
        processor.vertFlip(currentKey, currentKey + "-vertFlip");
        functionUpdate();
        break;
      case "Blur":
        processor.blur(currentKey, currentKey + "-blur");
        functionUpdate();
        break;
      case "Sharpen":
        processor.sharpen(currentKey, currentKey + "-sharpen");
        functionUpdate();
        break;
      case "Intensity":
        processor.intensity(currentKey, currentKey + "-intensity");
        functionUpdate();
        break;
      case "Luma":
        processor.luma(currentKey, currentKey + "-luma");
        functionUpdate();
        break;
      case "Greyscale":
        greyscaleWindow();
        functionUpdate();
        break;
      case "Greyscale Filter":
        processor.greyscaleByFilter(currentKey, currentKey + "-greyscaleByFilter");
        functionUpdate();
        break;
      case "Sepia":
        processor.sepiaFilter(currentKey, currentKey + "-sepia");
        functionUpdate();
        break;
      case "Value":
        processor.value(currentKey, currentKey + "-value");
        functionUpdate();
        break;
      case "load":
        loadImage();
        functionUpdate();
        break;
      case "Save":
        saveWindow();
        functionUpdate();
        break;
      case "submit":
        brightVal = brightText.getText();
        brighten();
        brightenPanel.setVisible(false);
        functionUpdate();
        break;
      case "ok":
        rgb = greyText.getText();
        produceGreyscale();
        greyPanel.setVisible(false);
        functionUpdate();
        break;
      case "submit.":
        saveName = saveText.getText();
        extension = (String) extensionList.getSelectedItem();
        System.out.println(extension);
        save();
        savePanel.setVisible(false);
        functionUpdate();
        break;
      case "submit!":
        height = Double.valueOf(downText.getText());
        width = Double.valueOf(downText2.getText());
        processor.downscaling(currentKey, currentKey + "Downscaling", height, width);
        downPanel.setVisible(false);
        functionUpdate();
        break;
      default:
        break;
    }
  }

  /**
   * Changes the state of the item and updates the histogram.
   *
   * @param e The item event.
   */
  @Override
  public void itemStateChanged(ItemEvent e) {
    String who = ((JCheckBox) e.getItemSelectable()).getActionCommand();
    String stringIndex = who.substring(who.length() - 1);
    int imageIndex = Integer.parseInt(stringIndex) - 1;
    if (e.getStateChange() == ItemEvent.SELECTED) {
      System.out.print("you checked the box");
      deselectImages(imageIndex);
      currentKey = keys[imageIndex];
      setImage(keys[imageIndex]);
      updateHistogram();
      update();
    }
  }

  /**
   * Brightens an image window.
   */
  private void brightenWindow() {
    brightenPanel = new JPanel();
    brightenPanel.setMaximumSize(new Dimension(300, 100));
    brightenPanel.setSize(new Dimension(50, 7));
    brightenPanel.setLayout(new BoxLayout(brightenPanel, BoxLayout.Y_AXIS));
    brightText = new JTextField("here");
    JLabel label = new JLabel("Enter a number to brighten the image by.");
    JButton submit = new JButton("submit");
    submit.addActionListener(this);
    brightenPanel.add(label);
    brightenPanel.add(brightText);
    brightenPanel.add(submit);
    functionPanel.add(brightenPanel, 2);
    update();
  }

  /**
   * Downscales an image.
   */
  private void getDoubles() {
    downPanel = new JPanel();
    downPanel.setMaximumSize(new Dimension(500, 200));
    downPanel.setSize(new Dimension(50, 7));
    downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.Y_AXIS));
    downText = new JTextField("here");
    JLabel label = new JLabel("Enter a number to downscale the image height by.");

    downPanel = new JPanel();
    downPanel.setMaximumSize(new Dimension(500, 200));
    downPanel.setSize(new Dimension(50, 7));
    downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.Y_AXIS));
    downText2 = new JTextField("here");
    JLabel label2 = new JLabel("Enter a number to downscale the image width by.");
    JButton submit = new JButton("submit!");
    submit.addActionListener(this);

    downPanel.add(label);
    downPanel.add(downText);

    downPanel.add(label2);
    downPanel.add(downText2);
    downPanel.add(submit);

    functionPanel.add(downPanel, 15);
    update();
  }

  /**
   * Turns the image or window grey.
   */
  private void greyscaleWindow() {
    greyPanel = new JPanel();
    greyPanel.setMaximumSize(new Dimension(300, 100));
    greyPanel.setSize(new Dimension(50, 7));
    greyPanel.setLayout(new BoxLayout(greyPanel, BoxLayout.Y_AXIS));
    greyText = new JTextField("here");
    JLabel label = new JLabel("Enter red, green, or blue");
    JButton submit = new JButton("ok");
    submit.addActionListener(this);
    greyPanel.add(label);
    greyPanel.add(greyText);
    greyPanel.add(submit);
    functionPanel.add(greyPanel, 9);
    update();
  }

  /**
   * Calls on the image processor .
   */
  private void saveWindow() {
    savePanel = new JPanel();
    savePanel.setSize(new Dimension(50, 7));
    savePanel.setMaximumSize(new Dimension(300, 100));
    savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.Y_AXIS));
    saveText = new JTextField("here");
    extensionList = new JComboBox<String>(validExtensions);
    JLabel label = new JLabel("Enter the name you want to save the image to");
    JButton submit = new JButton("submit.");
    submit.addActionListener(this);
    extensionList.addActionListener(this);
    savePanel.add(label);
    savePanel.add(saveText);
    savePanel.add(extensionList);
    savePanel.add(submit);
    functionPanel.add(savePanel, 10);
    update();
  }

  /**
   * Turns pixels different shades of grey based on the colors of the pixel.
   */
  private void produceGreyscale() throws IllegalStateException {
    int color;
    if (rgb.equalsIgnoreCase("red")) {
      color = 0;
    } else if (rgb.equalsIgnoreCase("green")) {
      color = 1;
    } else if (rgb.equalsIgnoreCase("blue")) {
      color = 2;
    } else {
      throw new IllegalStateException("Invalid Color");
    }
    processor.greyscale(currentKey, currentKey + "-greyscale", color);
  }

  /**
   * Retrieves an image through the image path.
   */
  private void loadImage() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Valid Image Types(png, jpg, gif, bmp, ppm)", "png", "jpg", "jpeg", "gif", "bmp", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      String path = fchooser.getSelectedFile().getPath();
      processor.load(path.substring(path.lastIndexOf("/") + 1), path);
      imagesLoaded += 1;
      update();
    }
  }

  /**
   * Saves an image.
   */
  private void save() {
    processor.save(currentKey, "images/" + saveName + extension);
  }

  /**
   * Brightens an image.
   */
  private void brighten() {
    brightText.addActionListener(this);
    int brightInt = Integer.parseInt(brightVal);
    processor.brighten(currentKey, currentKey + "-brighten", brightInt);
    brightenPanel.setVisible(false);
  }

  /**
   * Updates the panels and graphics.
   */
  private void update() {
    mainPanel.setVisible(false);
    mainPanel.setVisible(true);
  }

  /**
   * Initiates the Histogram.
   */
  private void initHistogram() {
    histogram = new Histogram(processor, currentKey, (int) Math.round(this.getHeight() * 0.05));
    histogram.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    currentImagePanel.add(histogram, 0);
    update();
  }

  /**
   * Updates the Histogram.
   */
  private void updateHistogram() {
    histogram.updateData(processor, currentKey);
    histogram.repaint();
    update();
  }

  /**
   * Helper method for actionPreformed.
   */
  private void functionUpdate() {
    keys = processor.getLoadKeys().toArray(new String[0]);
    displayImageList();
    update();
  }

  /**
   * Adds images to the list and displays the list.
   */
  private void displayImageList() {
    imageList.removeAll();
    imageList.add(imageLabel);
    images = new JCheckBox[keys.length];
    for (int i = 0; i < keys.length; i++) {
      images[i] = new JCheckBox(keys[i]);
      images[i].setSelected(false);
      images[i].setAlignmentX(JComponent.CENTER_ALIGNMENT);
      images[i].setActionCommand("IM" + (i + 1));
      images[i].addItemListener(this);
      imageList.add(images[i]);
    }
  }

  /**
   * Deselects an image.
   */
  private void deselectImages(int except) {
    for (int i = 0; i < images.length; i++) {
      if (i != except) {
        images[i].setSelected(false);
      }
    }
  }

  /**
   * Gets the image and adds the histogram.
   *
   * @param name The name of the image.
   */
  private void setImage(String name) {
    JScrollPane currentImagePane;
    currentImagePanel.removeAll();
    currentImage = new JLabel(new ImageIcon(processor.getLoad(name).toBufferedImage()));
    currentImagePane = new JScrollPane(currentImage);
    currentImagePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    currentImagePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    currentImagePanel.add(histogram);
    currentImagePanel.add(currentImagePane);
    updateHistogram();
  }
}
