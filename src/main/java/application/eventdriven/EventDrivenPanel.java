package application.eventdriven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class EventDrivenPanel extends JPanel implements ActionListener, Runnable {
    private final JButton startButton;
    private final JButton stopButton;
    private final JLabel canvas;
    private EventModel model;
    private final JTextField iterationsCountInput;
    private Thread thread = new Thread(this);

    public EventDrivenPanel() {
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        JLabel iterationsLabel = new JLabel("Iterations count");
        iterationsCountInput = new JTextField("10");
        iterationsCountInput.setMaximumSize(new Dimension(120, 20));
        model = new EventModel();
        canvas = new JLabel();
        canvas.setIcon(new ImageIcon(drawBlank()));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(canvas)
                .addComponent(iterationsLabel)
                .addComponent(iterationsCountInput)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addComponent(stopButton)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(100)
                .addComponent(canvas)
                .addComponent(iterationsLabel)
                .addComponent(iterationsCountInput)
                .addGroup(layout.createParallelGroup()
                        .addComponent(startButton)
                        .addComponent(stopButton)
                )
        );

    }

    /**
     * Animates model in a different thread.
     */
    public void animateCanvas(String input) throws InterruptedException {
        String[] commands = input.split("\n");
        BufferedImage image;
        Graphics g;
        canvas.setIcon(new ImageIcon(drawBlank()));
        for (int i = 0; i < commands.length; i++) {
            switch (commands[i]) {
                case "toLeft":
                    for (int j = 0; j < 4; j++) {
                        image = drawBlank();
                        g = image.getGraphics();
                        g.setColor(Color.ORANGE);
                        g.fillRect(image.getWidth() * 4 / 6 - j * image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                        g.setColor(Color.GREEN);
                        for (int z = 0; z < model.getHistory().get(i); z++) {
                            g.fillOval(image.getWidth() * 4 / 6 - j * image.getWidth() / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                        }
                        canvas.setIcon(new ImageIcon(image));
                        Thread.sleep(1000);
                    }
                    break;

                case "toRight":
                    for (int j = 0; j < 4; j++) {
                        image = drawBlank();
                        g = image.getGraphics();
                        g.setColor(Color.ORANGE);
                        g.fillRect(image.getWidth() / 6 + j * image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                        g.setColor(Color.GREEN);
                        for (int z = 0; z < model.getHistory().get(i); z++) {
                            g.fillOval(image.getWidth() / 6 + j * image.getWidth() / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                        }
                        canvas.setIcon(new ImageIcon(image));
                        Thread.sleep(1000);
                    }
                    break;

                case "exitRight":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() * 4 / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    g.setColor(Color.GREEN);
                    for (int z = 0; z < model.getHistory().get(i); z++) {
                        g.fillOval(image.getWidth() * 5 / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    Thread.sleep(1000);

                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() * 4 / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    canvas.setIcon(new ImageIcon(image));
                    canvas.setVisible(true);
                    Thread.sleep(1000);
                    break;

                case "exitLeft":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    g.setColor(Color.GREEN);
                    for (int z = 0; z < model.getHistory().get(i); z++) {
                        g.fillOval(image.getWidth() / 6 - (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    Thread.sleep(1000);

                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);
                    canvas.setIcon(new ImageIcon(image));
                    Thread.sleep(1000);
                    break;

                case "addRight":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() * 4 / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                    g.setColor(Color.GREEN);
                    for (int z = 0; z < model.getHistory().get(i); z++) {
                        g.fillOval(image.getWidth() * 5 / 6 + (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    Thread.sleep(1000);
                    break;

                case "addLeft":
                    image = drawBlank();
                    g = image.getGraphics();
                    g.setColor(Color.ORANGE);
                    g.fillRect(image.getWidth() / 6, image.getHeight() * 2 / 5 + 10, image.getWidth() / 6, 10);

                    g.setColor(Color.GREEN);
                    for (int z = 0; z < model.getHistory().get(i); z++) {
                        g.fillOval(image.getWidth() / 6 - (z + 1) * 25, image.getHeight() * 2 / 5, 10, 10);
                    }

                    canvas.setIcon(new ImageIcon(image));
                    Thread.sleep(1000);
                    break;

                case "onBoard":
                    break;
            }
        }
        canvas.setIcon(new ImageIcon(drawBlank()));
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
    }

    public BufferedImage drawBlank() {
        BufferedImage image = new BufferedImage(800, 200, 1);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 95, image.getWidth() / 6, image.getHeight() / 2 + 5);
        g.fillRect(image.getWidth() * 5 / 6, 95, image.getWidth() / 6, image.getHeight() / 2 + 5);
        g.setColor(Color.BLUE);
        g.fillRect(image.getWidth() / 6, 100, image.getWidth() * 4 / 6, image.getHeight() / 2);
        return image;
    }

    public int getIterationsCountInput() {
        try
        {
            Integer.parseInt(iterationsCountInput.getText());
        } catch (NumberFormatException ex)
        {
            iterationsCountInput.setText("10");
            return 10;
        }
        if (Integer.parseInt(iterationsCountInput.getText()) > 20) {
            iterationsCountInput.setText("20");
            return 20;
        }
        if (Integer.parseInt(iterationsCountInput.getText()) < 1) {
            iterationsCountInput.setText("1");
            return 1;
        }
        return Integer.parseInt(iterationsCountInput.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Start".equals(e.getActionCommand())) {
            thread = new Thread(this);
            thread.start();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        if ("Stop".equals(e.getActionCommand())) {
            thread.interrupt();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            canvas.setIcon(new ImageIcon(drawBlank()));
        }
    }

    @Override
    public void run() {
        try {
            animateCanvas(model.Cycle(getIterationsCountInput()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        model = new EventModel();
    }
}
