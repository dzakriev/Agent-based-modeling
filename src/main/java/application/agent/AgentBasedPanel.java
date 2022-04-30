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
import java.util.Arrays;
import java.util.Objects;


public class AgentBasedPanel extends JPanel implements ActionListener {
    private final JButton button1;
    private final JButton[] buttons;
    private final JTextField input1;//Максимальная популяция - 10000
    private final JTextField input2; //Максимум итераций - 15
    private final JComboBox comboBox1;
    private final JComboBox comboBox2;
    private final JLabel habitat;
    private final JLabel graph;
    private Agent[] agents;
    private Agent[][] agentsHistory;
    private int agentsCount;
    private int iterations;
    private int[] activeData;
    private int[] inactiveData;
    private Color activeColor = Color.GREEN;
    private Color inactiveColor = Color.RED;

    public AgentBasedPanel(Agent[] agents) { //Конструктор
        String[] colors = {
                "Green",
                "Red",
                "Yellow",
                "Blue"
        };
        comboBox1 = new JComboBox(colors);
        comboBox2 = new JComboBox(colors);
        comboBox2.setSelectedIndex(1);
        comboBox1.setMaximumSize(new Dimension(70, 20));
        comboBox2.setMaximumSize(new Dimension(70, 20));

        button1 = new JButton("Create");
        button1.addActionListener(this);
        button1.setMaximumSize(new Dimension(50, 26));

        input1 = new JTextField("0", 5);
        input2 = new JTextField("0", 5);
        input1.setMaximumSize(new Dimension(70, 26));
        input2.setMaximumSize(new Dimension(70, 26));
        input1.setMinimumSize(new Dimension(70, 26));
        input2.setMinimumSize(new Dimension(70, 26));

        this.agents = agents;
        agentsCount = getPopulation();
        iterations = getIterations();
        agentsHistory = new Agent[15][];
        for (int i = 0; i < 15; i++) {
            agentsHistory[i] = new Agent[10000];
            for (int j = 0; j < 10000; j++) {
                agentsHistory[i][j] = new Agent();
                Agent.connections.remove(agentsHistory[i][j]);
            }
        }
        for (int j = 0; j < agentsCount; j++) {
            agentsHistory[0][j] = agents[j].copy();
            Agent.connections.remove(agentsHistory[0][j]);
        }
        cycle();

        habitat = new JLabel();
        habitat.setIcon(new ImageIcon(paintAgents(agents)));
        graph = new JLabel();
        graph.setIcon(new ImageIcon(createChart()));

        buttons = new JButton[15];
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Чтобы сделать кнопки по центру, необходимо убрать FlowLayout
        for (int i = 0; i < 15; i++) {
            buttons[i] = new JButton(i + 1 + "");
            buttonsPanel.add(buttons[i]);
            buttons[i].addActionListener(this);
            if (i > iterations - 1) buttons[i].setVisible(false);
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
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(input1Label)
                                        .addGap(30)
                                        .addComponent(input2Label)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(input1)
                                        .addGap(20)
                                        .addComponent(input2)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(comboBox1Label)
                                        .addGap(55)
                                        .addComponent(comboBox2Label)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(comboBox1)
                                        .addGap(20)
                                        .addComponent(comboBox2)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(45)
                                        .addComponent(button1)
                                )
                        )
                        .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(240)
                                        .addComponent(habitatLabel)
                                )
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
                                        .addComponent(input1)
                                        .addComponent(input2)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(comboBox1Label)
                                        .addComponent(comboBox2Label)
                                )
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(comboBox1)
                                        .addComponent(comboBox2)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(20)
                                        .addComponent(button1)
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
        input1.setText("100");
        input2.setText("10");
    }

    public AgentBasedPanel() {
        this((initializeAgents(0)));
    }
    /**
     * Static method for initializing object with no arguments.
     */
    private static Agent[] initializeAgents(int agentsCount) {
        Agent.clearConnection();
        Agent[] agents = new Agent[agentsCount];
        for (int i = 0; i < agentsCount; i++) {
            agents[i] = new Agent();
        }
        return agents;
    }

    /**
     * Paints agents, updates JLabel displaying habitat.
     */
    private BufferedImage paintAgents(Agent[] agents) {
        BufferedImage habitat = new BufferedImage(521, 521, 1);
        Graphics g = habitat.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,521,521);
        updateColors();
        for (int i = 0; i < agentsCount; i++) {
            if (agents[i].isUser) {
                g.setColor(activeColor);
            } else {
                g.setColor(inactiveColor);
            }
            g.fillOval(agents[i].x + 256, agents[i].y + 256, 10, 10);
        }
        return habitat;
    }

    public void paintComponent() {
        habitat.setIcon(new ImageIcon(paintAgents(agents)));
        graph.setIcon(new ImageIcon(createChart()));
    }

    /**
     * Gets values from comboBoxes and sets them as colors for agents.
     */
    private void updateColors() {
        switch ((String) (Objects.requireNonNull(comboBox1.getSelectedItem()))) {
            case ("Red") -> activeColor = Color.RED;
            case ("Blue") -> activeColor = Color.BLUE;
            case ("Yellow") -> activeColor = Color.YELLOW;
            default -> activeColor = Color.GREEN;
        }
        switch ((String) (Objects.requireNonNull(comboBox2.getSelectedItem()))) {
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
        for (int i = 0; i < iterations; i++) {
            dataset.addValue(inactiveData[i], a2, i + 1 + "");
            dataset.addValue(activeData[i], a1, i + 1 + "");
        }
        JFreeChart barChart = ChartFactory.createBarChart("Population", "Days", "Count",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, inactiveColor);
        plot.getRenderer().setSeriesPaint(1, activeColor);
        return barChart.createBufferedImage(650, 400);
    }

    public void setData(int[] activeData, int[] inactiveData) {
        this.activeData = activeData;
        this.inactiveData = inactiveData;
    }

    public int getPopulation() { //Возвращает значение из текстового окошка
        if (Integer.parseInt(input1.getText()) > 10000) {
            input1.setText("10000");
        }
        if (Integer.parseInt(input1.getText()) < 1) {
            input1.setText("0");
        }
        return Integer.parseInt(input1.getText());
    }

    public int getIterations() { //Возвращает значение из текстового окошка
        if (Integer.parseInt(input2.getText()) > 15) {
            input2.setText("15");
        }
        if (Integer.parseInt(input2.getText()) < 1) {
            input2.setText("0");
        }
        return Integer.parseInt(input2.getText());
    }



    public int getActiveAgents() {
        int i = 0;
        for (Agent agent : agents) {
            if (agent.isUser) i++;
        }
        return i;
    }

    public int getInactiveAgents() {
        int i = 0;
        for (Agent agent : agents) {
            if (!agent.isUser) i++;
        }
        return i;
    }

    /**
     * Makes iterations in the model, saves data.
     */
    public void cycle() {
        int[] active_data = new int[iterations];
        int[] inactive_data = new int[iterations];
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < agentsCount; j++) {
                agentsHistory[i][j] = agents[j].copy();
                Agent.connections.remove(agentsHistory[i][j]);
            }
            active_data[i] = getActiveAgents();
            inactive_data[i] = getInactiveAgents();
            if (i != iterations - 1) {
                for (int j = 0; j < agentsCount; j++) {
                    agents[j].update();
                }
            }
        }
        setData(active_data, inactive_data);
    }

    /**
     * Creates agents population.
     */
    public void create(int n) {
        Agent.clearConnection();
        agents = new Agent[n];
        for (int i = 0; i < n; i++) {
            agents[i] = new Agent();
        }
        cycle();
        paintComponent();
        for (int i = 0; i < 15; i++) {
            buttons[i].setVisible(i <= iterations - 1);
        }
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Create".equals(e.getActionCommand())) {
            agentsCount = getPopulation();
            iterations = getIterations();
            create(agentsCount);
        }
        for (int i = 1; i <= 15; i++) {
            if ((i + "").equals(e.getActionCommand())) {
                habitat.setIcon(new ImageIcon(paintAgents(agentsHistory[i - 1])));
            }
        }

    }
}
