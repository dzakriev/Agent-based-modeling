import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JFrame;

public class GUI extends JFrame implements ActionListener {
    private JButton button1 = new JButton("Create");
    private JTextField input = new JTextField("100",5);
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private Agent[] agents;
    private int agentsCount;
    private int[] activeData;
    private int[] inactiveData;
    private BufferedImage graph;
    private Color activeColor = Color.GREEN;
    private Color inactiveColor = Color.RED;
    private static Agent[] initializeAgents() { //Метод для вызова одного конструктора через другой
        Agent[] agents = new Agent[10];
        for (int i = 0; i < 10; i++){
            agents[i] = new Agent();
        }
        return agents;
    }
    public GUI(Agent[] agents) throws IOException { //Конструктор
        super("Agent population");

        agentsCount = getText();
        this.agents = agents;

        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] items = {
                "Green",
                "Red",
                "Yellow",
                "Blue"
        };
        comboBox1 = new JComboBox(items);
        comboBox2 = new JComboBox(items);
        comboBox2.setSelectedIndex(1);
        comboBox1.setMaximumSize(new Dimension(50, 20));
        comboBox2.setMaximumSize(new Dimension(50, 20));

        button1.addActionListener(this);

        graph = ImageIO.read(new File("icon.png"));

        input.setMaximumSize(new Dimension(50, 26));

        Container container = this.getContentPane();
        GroupLayout layout = new GroupLayout(getContentPane());
        container.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup
                (layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(button1)
                                .addComponent(input))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(comboBox1)
                                .addComponent(comboBox2)
                        )
                );
        layout.setVerticalGroup
                (layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(button1)
                                .addComponent(comboBox1))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(input)
                                .addComponent(comboBox2))
                );

        pack();
    }

    private void setVisible(){
        comboBox1.setVisible(true);
        comboBox2.setVisible(true);
        button1.setVisible(true);
        input.setVisible(true);
    }

    public GUI() throws IOException {
        this((initializeAgents()));
    }




    public void paintComponent() {
        updateColors();
        Graphics g = getGraphics();
        clear();
        for (Agent agent : agents) {
            if (agent.isUser) g.setColor(activeColor);
            else g.setColor(inactiveColor);
            g.fillOval(agent.x + 500, agent.y+350, 10, 10);
        }
        makeChart();
        try {
            createChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setVisible();
    }

    private void updateColors(){
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

    public void makeChart(){
        int[] xs = new int[10];
        int[] ys1 = new int[10];
        int[] ys2 = new int[10];
        for (int i = 0; i < 10; i++) {
            xs[i] = (i + 1) * 20 + 1000;
            ys1[i] = (agentsCount - activeData[i]) * 100 / (agentsCount) + 50;
            ys2[i] = (agentsCount - inactiveData[i]) * 100 / (agentsCount) + 50;
        }
        Graphics g = getGraphics();
        g.setColor(Color.white);
        g.fillRect( 1020, 50, 181, 101);
        g.setColor(Color.black);
        g.drawLine(1020, 151, 1020, 50);
        g.drawLine(1020, 151, 1200, 151);
        g.setColor(inactiveColor);
        g.drawPolyline(xs, ys2, 10);
        g.setColor(activeColor);
        g.drawPolyline(xs, ys1, 10);
    }
    public void createChart() throws IOException {
        String a1 = "Active";
        String a2 = "Inactive";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        // Player 1
        for (int i = 0; i < 10; i++){
            dataset.addValue(inactiveData[i], a2,i+1+"");
            dataset.addValue(activeData[i], a1, i+1+"");
        }
        JFreeChart barChart = ChartFactory.createBarChart("Chart", "Days", "Count",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, inactiveColor);
        plot.getRenderer().setSeriesPaint(1, activeColor);
        ChartUtils.saveChartAsPNG(new File("D://icon.png"), barChart, 650, 400);
        Graphics g = getGraphics();
        graph = ImageIO.read(new File("D://icon.png"));
        g.drawImage(graph, 1000, 200, this);
    }
    public void clear() {
        Graphics g = getGraphics();
        g.setColor(Color.white);
        g.fillRect( 200, 50, 600, 600);
    }

    public void setData(int[] activeData, int[] inactiveData) {
        this.activeData = activeData;
        this.inactiveData = inactiveData;
    }

    public int getText(){ //Возвращает значение из текстового окошка
        return Integer.parseInt(input.getText());
    }
    public void setAgents(Agent[] agents){
        this.agents = agents;
        agentsCount = agents.length;
    }
    public Agent[] getAgents(){
        return agents;
    }
    public int getActiveAgents(){
        int i = 0;
        for (Agent agent : agents) {
            if (agent.isUser) i++;
        }
        return i;
    }
    public int getInactiveAgents(){
        int i = 0;
        for (Agent agent : agents) {
            if (!agent.isUser) i++;
        }
        return i;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Create".equals(e.getActionCommand())){
            int agentsCount = Integer.parseInt(input.getText());
            Main.create(agentsCount);
        }
    }
}
