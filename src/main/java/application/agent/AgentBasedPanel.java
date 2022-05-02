package application.agent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;


public class AgentBasedPanel extends JPanel implements ActionListener {
    private final JButton[] historyButtons;
    private final JTextField agentsCountInput;//Максимальная популяция - 10000
    private final JTextField iterationsCountInput; //Максимум итераций - 15
    private final JComboBox<String> activeColorComboBox;
    private final JComboBox<String> inactiveColorComboBox;
    private final JLabel habitat;
    private final JLabel graph;
    private AgentModel model;
    private Color activeColor = Color.GREEN;
    private Color inactiveColor = Color.RED;

    public AgentBasedPanel() { //Конструктор
        String[] colors = {
                "Green",
                "Red",
                "Yellow",
                "Blue"
        };
        activeColorComboBox = new JComboBox<>(colors);
        inactiveColorComboBox = new JComboBox<>(colors);
        inactiveColorComboBox.setSelectedIndex(1);
        activeColorComboBox.setMaximumSize(new Dimension(70, 20));
        inactiveColorComboBox.setMaximumSize(new Dimension(70, 20));

        JButton createButton = new JButton("Create");
        createButton.addActionListener(this);
        createButton.setMaximumSize(new Dimension(50, 26));

        agentsCountInput = new JTextField("0");
        iterationsCountInput = new JTextField("0");
        agentsCountInput.setMaximumSize(new Dimension(70, 26));
        iterationsCountInput.setMaximumSize(new Dimension(70, 26));

        model = new AgentModel(0, 0);

        habitat = new JLabel();
        habitat.setIcon(new ImageIcon(paintAgents(model.getAgents())));
        graph = new JLabel();
        graph.setIcon(new ImageIcon(createChart()));

        historyButtons = new JButton[15];
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Чтобы сделать кнопки по центру, необходимо убрать FlowLayout
        for (int i = 0; i < 15; i++) {
            historyButtons[i] = new JButton(i + 1 + "");
            buttonsPanel.add(historyButtons[i]);
            historyButtons[i].addActionListener(this);
            if (i > getIterations() - 1) historyButtons[i].setVisible(false);
        }

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JLabel comboBox2Label = new JLabel("Inactive");
        JLabel comboBox1Label = new JLabel("Active");
        JLabel input2Label = new JLabel("Iterations");
        JLabel input1Label = new JLabel("Population");
        JLabel habitatLabel = new JLabel("Habitat");
        layout.setHorizontalGroup
                (layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(input1Label)
                                        .addGap(30)
                                        .addComponent(input2Label)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(agentsCountInput)
                                        .addGap(20)
                                        .addComponent(iterationsCountInput)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(comboBox1Label)
                                        .addGap(55)
                                        .addComponent(comboBox2Label)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(activeColorComboBox)
                                        .addGap(20)
                                        .addComponent(inactiveColorComboBox)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(createButton)
                                )
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(habitatLabel)
                                .addComponent(habitat)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(graph)
                                .addComponent(buttonsPanel)
                        )

                );
        layout.setVerticalGroup
                (layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(input1Label)
                                        .addComponent(input2Label)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(agentsCountInput)
                                        .addComponent(iterationsCountInput)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(comboBox1Label)
                                        .addComponent(comboBox2Label)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(activeColorComboBox)
                                        .addComponent(inactiveColorComboBox)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(20)
                                        .addComponent(createButton)
                                )
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(habitatLabel)
                                .addComponent(habitat)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40)
                                .addComponent(graph)
                                .addComponent(buttonsPanel)
                        )
                );
        agentsCountInput.setText("100");
        iterationsCountInput.setText("10");
    }

    /**
     * Paints agents, updates JLabel displaying habitat.
     */
    private BufferedImage paintAgents(ArrayList<Agent> agents) {
        BufferedImage habitat = new BufferedImage(521, 521, 1);
        Graphics g = habitat.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 521, 521);
        updateColors();
        for (int i = 0; i < getPopulation(); i++) {
            if (agents.get(i).isUser()) {
                g.setColor(activeColor);
            } else {
                g.setColor(inactiveColor);
            }
            g.fillOval(agents.get(i).getX() + 256, agents.get(i).getY() + 256, 10, 10);
        }
        return habitat;
    }

    public void paintComponent() {
        habitat.setIcon(new ImageIcon(paintAgents(model.getAgents())));
        graph.setIcon(new ImageIcon(createChart()));
    }

    /**
     * Gets values from comboBoxes and sets them as colors for agents.
     */
    private void updateColors() {
        switch ((String) (Objects.requireNonNull(activeColorComboBox.getSelectedItem()))) {
            case ("Red") -> activeColor = Color.RED;
            case ("Blue") -> activeColor = Color.BLUE;
            case ("Yellow") -> activeColor = Color.YELLOW;
            default -> activeColor = Color.GREEN;
        }
        switch ((String) (Objects.requireNonNull(inactiveColorComboBox.getSelectedItem()))) {
            case ("Green") -> inactiveColor = Color.GREEN;
            case ("Blue") -> inactiveColor = Color.BLUE;
            case ("Yellow") -> inactiveColor = Color.YELLOW;
            default -> inactiveColor = Color.RED;
        }
    }

    /**
     * Creates a barChart using JFreeChart library
     */
    public BufferedImage createChart() {
        String a1 = "Active";
        String a2 = "Inactive";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < getIterations(); i++) {
            dataset.addValue(model.getInactiveData()[i], a2, i + 1 + "");
            dataset.addValue(model.getActiveData()[i], a1, i + 1 + "");
        }
        JFreeChart barChart = ChartFactory.createBarChart("Population", "Days", "Count",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getDomainAxis().setLowerMargin(0.01);
        plot.getDomainAxis().setUpperMargin(0.01);
        plot.setBackgroundPaint(Color.WHITE);
        plot.getRenderer().setSeriesPaint(0, inactiveColor);
        plot.getRenderer().setSeriesPaint(1, activeColor);
        return barChart.createBufferedImage(750, 400);
    }

    /**
     * Parses agents count text field value, sets maximum at 10000 and minimum at 0, if it's not integer sets it at 100.
     */
    public int getPopulation() {
        try {
            Integer.parseInt(agentsCountInput.getText());
        } catch (NumberFormatException ex) {
            agentsCountInput.setText("100");
            return 100;
        }
        if (Integer.parseInt(agentsCountInput.getText()) > 10000) {
            agentsCountInput.setText("10000");
            return 10000;
        }
        if (Integer.parseInt(agentsCountInput.getText()) < 1) {
            agentsCountInput.setText("0");
            return 0;
        }
        return Integer.parseInt(agentsCountInput.getText());
    }

    /**
     * Parses iterations text field value, sets maximum at 15 and minimum at 1, if it's not integer sets it at 10.
     */
    public int getIterations() {
        try {
            Integer.parseInt(iterationsCountInput.getText());
        } catch (NumberFormatException ex) {
            iterationsCountInput.setText("10");
            return 10;
        }
        if (Integer.parseInt(iterationsCountInput.getText()) > 15) {
            iterationsCountInput.setText("15");
            return 15;
        }
        if (Integer.parseInt(iterationsCountInput.getText()) < 1) {
            iterationsCountInput.setText("0");
            return 0;
        }
        return Integer.parseInt(iterationsCountInput.getText());
    }


    /**
     * Creates agents population.
     */
    public void create(int n) {
        model = new AgentModel(n, getIterations());
        paintComponent();
        for (int i = 0; i < 15; i++) {
            historyButtons[i].setVisible(i <= getIterations() - 1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Create".equals(e.getActionCommand())) {
            create(getPopulation());
        }
        for (int i = 1; i <= 15; i++) {
            if ((i + "").equals(e.getActionCommand())) {
                habitat.setIcon(new ImageIcon(paintAgents(model.getHistory().get(i - 1))));
            }
        }

    }
}
