package jarhead.InfoPanel;

import jarhead.Main;
import jarhead.ProgramProperties;
import jarhead.SpringUtilities;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Objects;

public class SettingsPanel extends JPanel {

    private final Main main;

    NumberFormat format = NumberFormat.getInstance();
    NumberFormatter formatter = new NumberFormatter(format);
    private final LinkedList<JTextField> fields = new LinkedList<>();
    private final String[] labels = {"Robot Width", "Robot Length", "Resolution", "Import/Export", "Track Width", "Max Velo", "Max Accel", "Max Angular Velo", "Max Angular Accel"};
    private final ProgramProperties robot;
    SettingsPanel(Main main, ProgramProperties properties){
        this.robot = properties;
        this.main = main;
        this.setOpaque(true);
//        this.setPreferredSize(new Dimension((int) Math.floor(30 * main.scale), (int) Math.floor(40 * main.scale)));
        this.setLayout(new SpringLayout());

        for (String label : labels) {
            JTextField input;
            if(Objects.equals(label, labels[3]))
                input = new JTextField();
            else
                input = new JFormattedTextField(formatter);
            input.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            input.setColumns(10);
//            input.setMaximumSize(new Dimension((int)main.scale*5,10));
            JLabel l = new JLabel(label + ": ", JLabel.TRAILING);
            this.add(l);
            l.setLabelFor(input);
            this.add(input);
            fields.add(input);
        }

        SpringUtilities.makeCompactGrid(this,labels.length,2,6,6,6,6);
        this.setVisible(true);

        for (int i = 0; i < fields.size(); i++) {
            JTextField field = fields.get(i);
            int finalI = i;
            field.addActionListener(e -> {
                robot.prop.setProperty(labels[finalI].replaceAll(" ","_").toUpperCase(), field.getText());
                main.reloadConfig();
                main.setState(JFrame.MAXIMIZED_BOTH);
            });
        }
    }

    public void update(){
        for (int i = 0; i < fields.size(); i++) {
            JTextField field = fields.get(i);
            field.setText(robot.prop.getProperty(labels[i].replaceAll(" ","_").toUpperCase()));
        }
        main.saveConfig();
    }


}
