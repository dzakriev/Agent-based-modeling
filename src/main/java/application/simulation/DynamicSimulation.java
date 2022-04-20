package application.simulation;

import application.agent.Agent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DynamicSimulation extends JPanel implements ActionListener {
    private JButton button1;
    private JTextField input1;
    private JTextField input2;
    private JTextField input3;
    private JTextField input4;
    private JTextField input5;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JLabel img1;
    private JLabel img2;
    private JLabel img3;
    private JLabel img4;
    private JLabel graph;

    public DynamicSimulation() {
        label1 = new JLabel("Population");
        label2 = new JLabel("Infectious");
        label3 = new JLabel("ContactRateInfectious");
        label4 = new JLabel("AverageIncubationTime");
        label5 = new JLabel("AverageIllnessDuration");
        label6 = new JLabel("Healthy");
        label7 = new JLabel("Exposed");
        label8 = new JLabel("Ill");
        label9 = new JLabel("Recovered");
        label10 = new JLabel("ExposedRate");
        label11 = new JLabel("InfectRate");
        label12 = new JLabel("RecoveredRate");
        img1 = new JLabel(".....");
        img2 = new JLabel(".....");
        img3 = new JLabel(".....");
        img4 = new JLabel(".....");
        graph = new JLabel("xxxxxxx");
        input1 = new JTextField("10000", 5);
        input2 = new JTextField("0,6", 5);
        input3 = new JTextField("1,25", 5);
        input4 = new JTextField("10", 5);
        input5 = new JTextField("15", 5);
        input1.setMaximumSize(new Dimension(70, 26));
        input2.setMaximumSize(new Dimension(70, 26));
        input3.setMaximumSize(new Dimension(70, 26));
        input4.setMaximumSize(new Dimension(70, 26));
        input5.setMaximumSize(new Dimension(70, 26));
        input1.setMinimumSize(new Dimension(70, 26));
        input2.setMinimumSize(new Dimension(70, 26));
        input3.setMinimumSize(new Dimension(70, 26));
        input4.setMinimumSize(new Dimension(70, 26));
        input5.setMinimumSize(new Dimension(70, 26));
        button1 = new JButton("Start");
        button1.addActionListener(this);
        button1.setMaximumSize(new Dimension(50, 26));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup
                (layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label1)
                                .addComponent(input1)
                                .addComponent(label2)
                                .addComponent(input2)
                                .addComponent(label3)
                                .addComponent(input3)
                                .addComponent(label4)
                                .addComponent(input4)
                                .addComponent(label5)
                                .addComponent(input5)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label6)
                                .addComponent(img1)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label10)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label7)
                                .addComponent(img2)
                                .addComponent(graph)
                                .addComponent(button1)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label11)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label8)
                                .addComponent(img3)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label12)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(label9)
                                .addComponent(img4)
                        )
                );
        layout.setVerticalGroup
                (layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label1)
                                .addComponent(input1)
                                .addComponent(label2)
                                .addComponent(input2)
                                .addComponent(label3)
                                .addComponent(input3)
                                .addComponent(label4)
                                .addComponent(input4)
                                .addComponent(label5)
                                .addComponent(input5)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label6)
                                .addComponent(img1)

                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label10)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label7)
                                .addComponent(img2)
                                .addComponent(graph)
                                .addComponent(button1)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label11)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label8)
                                .addComponent(img3)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label12)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(label9)
                                .addComponent(img4)
                        )
                );
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}