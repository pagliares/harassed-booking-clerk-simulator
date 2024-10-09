// HBCForm.java
// Provides simple forms for I/O, harassed booking clerk simulation
// Hand-coded, so should run if java.awt is present

package hbcsimulator;

import executive.Executive;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HBCForm extends Frame {
  // default values for simulation parameters
  private int meanInterArrivalTimePersonalEnquirer = 8;
  private int meanServiceTimePersonalEnquirer = 10;
  private int meanInterArrivalTimePhoneCallers = 10;
  private int meanServiceTimePhoneCallers = 12;
  private int simulationDuration = 100;  
  private int obsInt = 10;

  // Windows spawned by menu choices
  private About a;
  private MenuItem itemAbout;
  private SimpleHelp h;
  private MenuItem itemHelp;
  private Results r;
  private MenuItem itemResults;
  private MenuItem itemRun;

  // private HBCRunTime output;
  // Text fields
  private TextField persAField;
  private TextField persSField;
  private TextField phoneAField;
  private TextField phoneSField;
  private TextField simulationDurationField;

  // Labels
  private Label persALabel;
  private Label persSLabel;
  private Label phoneALabel;
  private Label phoneSLabel;
  private Label simDurLabel;
  private Label obsIntLabel;

// Check boxes
  private CheckboxGroup obsIntChecks;
  private Checkbox check10;
  private Checkbox check20;
  private Checkbox check40;

  // Button
  private Button saveButton;

  private GridBagLayout gbLayout;
  private GridBagConstraints gbConstraints;
  public HBC3 thisSimulation;


  public HBCForm(String title) {
    setBackground(Color.white);
    setTitle(title);
    makeMenuBar();
    setForeground(Color.red);
    makeHBCIPForm();
    setLocation(200, 200);
    setSize(400, 250);
    show();
  }

  private void makeMenuBar(){
    MenuBar bar = new MenuBar();
    Menu fileMenu = new Menu("File");
    Menu helpMenu = new Menu("Help");

    itemRun = new MenuItem("Run simulation");
    itemRun.setEnabled(true);
    fileMenu.add(itemRun);

    itemResults = new MenuItem("See Results");
    itemResults.setEnabled(false);
    fileMenu.add(itemResults);

    fileMenu.add("Exit");

    itemAbout = new MenuItem("About...");
    itemHelp = new MenuItem("Basic help");
    helpMenu.add(itemHelp);
    helpMenu.add(itemAbout);

    bar.add(fileMenu);
    bar.add(helpMenu);
    setMenuBar(bar);
  }

  private void makeHBCIPForm(){
    gbLayout = new GridBagLayout();
    gbConstraints = new GridBagConstraints();
    setLayout(gbLayout);

    makeLabels();
    makeFields();
    makeCheckBoxes();
    makeButton();
  }

  private void makeLabels(){
    gbConstraints.gridx = 1;
    gbConstraints.ipadx = 10;
    gbConstraints.anchor = GridBagConstraints.WEST;

    gbConstraints.gridy = 0;
    persALabel = new Label("PERSONAL ENQUIRERS: mean inter-arrival time");
    gbLayout.setConstraints(persALabel, gbConstraints);
    add(persALabel);

    gbConstraints.gridy = 1;
    persSLabel = new Label("PERSONAL ENQUIRERS: mean service time");
    gbLayout.setConstraints(persSLabel, gbConstraints);
    add(persSLabel);

    gbConstraints.gridy = 2;
    phoneALabel = new Label("PHONE CALLERS: mean inter-arrival time");
    gbLayout.setConstraints(phoneALabel, gbConstraints);
    add(phoneALabel);

    gbConstraints.gridy = 3;
    phoneSLabel = new Label("PHONE CALLERS: mean service time");
    gbLayout.setConstraints(phoneSLabel, gbConstraints);
    add(phoneSLabel);

    gbConstraints.gridy = 5;
    obsIntLabel = new Label("OBSERVATION INTERVAL");
    obsIntLabel.setForeground(java.awt.Color.black);
    gbLayout.setConstraints(obsIntLabel, gbConstraints);
    add(obsIntLabel);

    gbConstraints.gridy = 7;
    simDurLabel = new Label("SIMULATION DURATION");
    simDurLabel.setForeground(java.awt.Color.blue);
    gbLayout.setConstraints(simDurLabel, gbConstraints);
    add(simDurLabel);
  }

  private void makeFields(){
    gbConstraints.gridx = 2;
    gbConstraints.fill = GridBagConstraints.BOTH;
    gbConstraints.gridwidth = GridBagConstraints.REMAINDER;
    gbConstraints.anchor = GridBagConstraints.WEST;

    gbConstraints.gridy = 0;
    persAField = new TextField(Integer.toString(meanInterArrivalTimePersonalEnquirer));
    gbLayout.setConstraints(persAField, gbConstraints);
    add(persAField);

    gbConstraints.gridy = 1;
    persSField = new TextField(Integer.toString(meanServiceTimePersonalEnquirer));
    gbLayout.setConstraints(persSField, gbConstraints);
    add(persSField);

    gbConstraints.gridy = 2;
    phoneAField = new TextField(Integer.toString(meanInterArrivalTimePhoneCallers));
    gbLayout.setConstraints(phoneAField, gbConstraints);
    add(phoneAField);

    gbConstraints.gridy = 3;
    phoneSField = new TextField(Integer.toString(meanServiceTimePhoneCallers));
    gbLayout.setConstraints(phoneSField, gbConstraints);
    add(phoneSField);

    gbConstraints.gridy = 7;
    simulationDurationField = new TextField(Integer.toString(simulationDuration));
    simulationDurationField.setForeground(java.awt.Color.blue);
    gbLayout.setConstraints(simulationDurationField, gbConstraints);
    add(simulationDurationField);
  }

  private void makeCheckBoxes() {
    obsIntChecks = new CheckboxGroup();
    check10 = new Checkbox("10 mins", true, obsIntChecks);
    check10.setForeground(java.awt.Color.black);
    check20 = new Checkbox("20 mins", false, obsIntChecks);
    check20.setForeground(java.awt.Color.black);
    check40 = new Checkbox("40 mins", false, obsIntChecks);
    check40.setForeground(java.awt.Color.black);

    gbConstraints.ipadx = 0;
    gbConstraints.gridx = 2;
    gbConstraints.gridy = 4;
    gbLayout.setConstraints(check10, gbConstraints);
    add(check10);
    gbConstraints.gridy = 5;
    gbLayout.setConstraints(check20, gbConstraints);
    add(check20);

    gbConstraints.gridy = 6;
    gbLayout.setConstraints(check40, gbConstraints);
    add(check40);
  }

  private void makeButton(){
    saveButton = new Button("Save");
    saveButton.setForeground(Color.black);
    gbConstraints.gridx = 1;
    gbConstraints.gridy = 8;
    gbConstraints.gridwidth = 2;
    gbLayout.setConstraints(saveButton, gbConstraints);
    add(saveButton);

    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveValues();
      }
    });
  }

  private void saveValues() {
    try {
      meanInterArrivalTimePersonalEnquirer = Integer.parseInt(persAField.getText());
      meanServiceTimePersonalEnquirer = Integer.parseInt(persSField.getText());
      meanInterArrivalTimePhoneCallers = Integer.parseInt(phoneAField.getText());
      meanServiceTimePhoneCallers = Integer.parseInt(phoneSField.getText());
      simulationDuration = Integer.parseInt(simulationDurationField.getText());
      obsInt = getObservationDuration();
      System.out.println("Values saved successfully!");
    } catch (NumberFormatException e) {
      System.out.println("ERROR: Please enter valid numeric values in all fields.");
    }
  }

  public void setAboutState(boolean state) {
    itemAbout.setEnabled(state);
  }

  public void setHelpState(boolean state){
    itemHelp.setEnabled(state);
  }

  public int getObsInt(){
    return obsInt;
  }

  public boolean processEvent(Event e){
    if (e.id == Event.WINDOW_DESTROY) {
      removeFrame();
      return true;
    }
    return false;
  }

  public void removeFrame() {
    setVisible(false);
    dispose();
    System.exit(0);
  }

  public boolean action (Event e, Object o){
    if (e.target instanceof MenuItem) {
      if (e.arg.equals ("Exit"))
        removeFrame();
      else if (e.arg.equals(itemRun.getLabel())) {
        itemRun.setEnabled(false);
       //output = new HBCRunTime();
        System.out.println("\nSETTINGS");
        System.out.println(" - PERSONAL ENQUIRERS mean inter-arrival time: " + persAField.getText());
        System.out.println(" - PHONE CALLERS mean inter-arrival time: " + phoneAField.getText());
        System.out.println(" - PHONE CALLERS mean service time: " + phoneSField.getText());
        System.out.println(" - OBSERVATION INTERVAL: " + obsIntChecks.getSelectedCheckbox().getLabel());
        System.out.println(" - SIMULATION DURATION: " + simulationDurationField.getText());
        System.out.println();
        thisSimulation = new HBC3();
        itemResults.setEnabled(true);
      }
      else if (e.arg.equals(itemAbout.getLabel())) {
        a = new About(this);
        setAboutState(false);
      }
      else if (e.arg.equals(itemHelp.getLabel())) {
        h = new SimpleHelp(this);
        setHelpState(false);
      }
      else if (e.arg.equals(itemResults.getLabel()))
        r = new Results(this);
    }
    else if(e.target instanceof TextField) {
      int temp = 0;
      try {
        temp = Integer.parseInt(persAField.getText());
        meanInterArrivalTimePersonalEnquirer = temp;
      }
      catch (Exception exc) {
        persAField.setText(Integer.toString(meanInterArrivalTimePersonalEnquirer));
      }
      try {
        temp = Integer.parseInt(persSField.getText());
        meanServiceTimePersonalEnquirer = temp;
      }
      catch (Exception exc) {
        persSField.setText(Integer.toString(meanServiceTimePersonalEnquirer));
      }
      try {
        temp = Integer.parseInt(phoneAField.getText());
        meanInterArrivalTimePhoneCallers = temp;
      }
      catch (Exception exc) {
        phoneAField.setText(Integer.toString(meanInterArrivalTimePhoneCallers));
      }
      try {
        temp = Integer.parseInt(phoneSField.getText());
        meanServiceTimePhoneCallers = temp;
      }
      catch (Exception exc) {
        phoneSField.setText(Integer.toString(meanServiceTimePhoneCallers));
      }
      try {
        temp = Integer.parseInt(simulationDurationField.getText());
        simulationDuration = temp;
      }
      catch (Exception exc) {
        simulationDurationField.setText(Integer.toString(simulationDuration));
      }
      show();
    }
    return true;
  }

  public int getObservationDuration() {
    int temp = 10;
    if (check20.getState())
      temp = 20;
    else if (check40.getState())
      temp = 40;
    return temp;
  }

  public int getMeanInterArrivalTimePersonalEnquirer(){
    return meanInterArrivalTimePersonalEnquirer;
  }

  public int getMeanServiceTimePersonalEnquirer() {
    return meanServiceTimePersonalEnquirer;
  }

  public int getMeanInterArrivalTimePhoneCallers() {
    return meanInterArrivalTimePhoneCallers;
  }

  public int getMeanServiceTimePhoneCallers(){
    return meanServiceTimePhoneCallers;
  }

  public int getSimulationDuration(){
    return simulationDuration;
  }

  public void showSimPars() {
    System.out.println("                        IAT     Serve");
    System.out.println("PERSONAL ENQUIRERS ..    "+ meanInterArrivalTimePersonalEnquirer + "      " + meanServiceTimePersonalEnquirer);
    System.out.println("PHONE CALLERS ..         "+ meanInterArrivalTimePhoneCallers + "      " + meanServiceTimePhoneCallers);
  }
}

// Simple class to illustrate use of Dialog boxes
class About extends Dialog {
  private Button OKButton;
  private Label text;
  private Panel p, p1;
  private HBCForm parent;

  public About(Frame f) {
    super(f, "About this simulation", false);
    parent = (HBCForm)f;

    OKButton = new Button("OK");
    p = new Panel();
    p1 = new Panel();
    text = new Label(" Written by M. Pidd, August 1997 ");

    p.add(text);
    p1.add(OKButton);

    add("Center", p);
    add("South", p1);
    setSize(250, 100);
    setLocation(230, 230);
    show();
  }

  public boolean processEvent(Event e) {
    if (e.id == Event.WINDOW_DESTROY) {
      removeDialog();
      return true;
    }
    return false;
  }

  public boolean action(Event e, Object o) {
    if (e.target == OKButton)
      removeDialog();
      return true;
  }

  public void removeDialog() {
    setVisible(false);
    dispose();
    parent.setAboutState(true);
  }
}

// Simple help class
class SimpleHelp extends Dialog {
  private Button OKButton;
  private TextArea t;
  private Panel p;
  private HBCForm parent;

  public SimpleHelp(Frame f) {
    super(f, "Just a little help information", false);
    parent = (HBCForm)f;

    String s = "This is a simple java program to illustrate how the language\n" +
             "may be used to build three-phase discrete simulation programs.\n" +
             "As its example it uses the Harassed Booking Clerk simulation\n" +
             "from Computer Simulation in Management Science, M. Pidd, John\n" +
             "Wiley, 1998. \n\n" +
             "This is a simulation of a theatre booking office in which then\n" +
             "clerks have two tasks:\n" +
             "   (1). serving personal enquirers, who arrive in person at the theatre\n" +
             "   (2).  answering the phone when it rings.\n" +
             "To keep things simple, it assumes that waiting enquirers and phone callers\n" +
             "will wait their turn and do not renege.\n\n" +
             "There are similar programs available in Turbo Pascal, C, C++ and\n" +
             "Visual Basic. They can be found on Mike Pidd's personal homepage\n" +
             "at Lancaster University.\n\n" +
             "The Lancaster University web-site URL is www.lancs.ac.uk\n" +
             "- following the menus from there will take you to Mike Pidd's personal homepage.";

    t = new TextArea(s, 7, 25);
    t.setBackground(java.awt.Color.lightGray);
    t.setForeground(java.awt.Color.black);
    t.setEditable(false);
    add("Center", t);

    OKButton = new Button("OK");
    p = new Panel();
    p.add(OKButton);
    add("South", p);

    setSize(400, 250);
    setLocation(230, 230);
    show();
  }

  public boolean processEvent(Event e) {
    if (e.id == Event.WINDOW_DESTROY) {
      removeDialog();
      return true;
    }
    return false;
  }

  public boolean action(Event e, Object o) {
    if (e.target == OKButton)
      removeDialog();
    return true;
  }

  public void removeDialog() {
    setVisible(false);
    dispose();
    parent.setHelpState(true);
  }
}

// Simple class to display simulation results
class Results extends Dialog {
  private Button OKButton;
  private TextArea t;
  private Panel p;
  private HBCForm parent;

  public Results(Frame f) {
    super(f, "Simulation results", false);
    String s;
    parent = (HBCForm)f;

    s = getResults();
    t = new TextArea(s, 7, 25);
    t.setEditable(false);
    t.setBackground(java.awt.Color.lightGray);
    t.setForeground(java.awt.Color.black);
    add("Center", t);

    OKButton = new Button("OK");
    p = new Panel();
    p.add(OKButton);
    add("South", p);

    setSize(350, 200);
    setLocation(230, 230);
    show();
  }

  private String getResults() {
    String temp;
    temp = "SIMULATION OVER AFTER "  + Executive.getCurrentClockTime()+ "\n\n" +
           "PERSONAL ENQUIRERS: " + PersonalCustomer.personalCustomersWaitingForService.getNumAdded() + " arrivals" +
           " & " +  PersonalCustomer.personalCustomersServed.getNumAdded() + " departures \n" +
           "PHONE CALLERS: " + PhoneEnquirer.phoneEnquirersWaitingForService.getNumAdded() + " arrivals" +
           " & " +  PhoneEnquirer.phoneEnquirersServed.getNumAdded() + " departures \n\n"+
           "Queues for service as simulation ends: \n" +
           "PERSONAL ENQUIRERS: " + PersonalCustomer.personalCustomersWaitingForService.size() +
           ", PHONE CALLERS: " + PhoneEnquirer.phoneEnquirersWaitingForService.size();
    return temp;
  }

  public boolean processEvent(Event e) {
    if (e.id == Event.WINDOW_DESTROY) {
      removeDialog();
      return true;
    }
    return false;
  }

  public boolean action(Event e, Object o) {
    if (e.target == OKButton)
      removeDialog();
    return true;
  }

  public void removeDialog() {
    setVisible(false);
    dispose();
  }
}
