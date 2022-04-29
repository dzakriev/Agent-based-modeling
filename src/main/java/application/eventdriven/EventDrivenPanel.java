package application.eventdriven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class EventDrivenPanel extends JPanel implements ActionListener {
    private JButton startButton;
    private JLabel canvas;
    private EventModel model;
    private JTextField iterations;

    //Integer.parseInt(iterations.getText())
    public EventDrivenPanel() throws InterruptedException {
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        iterations = new JTextField("10");
        iterations.setMaximumSize(new Dimension(20,20));
        model = new EventModel();
        canvas = new JLabel();
        canvas.setIcon(new ImageIcon(drawBlank()));
        //Метод model.Cycle вернет список команд, которые передаются методу drawModel, который вернет BufferedImage,
        //Который переведется в ImageIcon и установится иконкой лейбла канвас.
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(canvas)
                .addComponent(iterations)
                .addComponent(startButton)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(100)
                .addComponent(canvas)
                .addComponent(iterations)
                .addComponent(startButton)
        );

    }
    public void animateCanvas(String input) throws InterruptedException {
        /*
        exit
        addRight
        addLeft
        onBoard
        toRight
        toLeft
         */
        String[] commands = input.split("\n");
        BufferedImage image;
        Graphics g;
        Graphics gg = getGraphics();
        for (String command : commands) {
            if (command.equals("toLeft")){
                image = drawBlank();
                g = image.getGraphics();
                g.setColor(Color.BLACK);
                g.fillOval(500,80,95,10);
                canvas.setIcon(new ImageIcon(image));
                paint(gg);

                System.out.println(1);
                Thread.sleep(1000);

                image = drawBlank();
                g = image.getGraphics();
                g.setColor(Color.BLACK);
                g.fillOval(400,80,95,10);
                canvas.setIcon(new ImageIcon(image));
                paint(gg);

                System.out.println(3);
                Thread.sleep(1000);

                image = drawBlank();
                g = image.getGraphics();
                g.setColor(Color.BLACK);
                g.fillOval(300,80,95,10);
                canvas.setIcon(new ImageIcon(image));
                paint(gg);

                System.out.println(2);
                Thread.sleep(1000);
                image = drawBlank();
                g = image.getGraphics();
                g.setColor(Color.BLACK);
                g.fillOval(200,80,95,10);
                canvas.setIcon(new ImageIcon(image));
                paint(gg);

                System.out.println(4);
                Thread.sleep(1000);
            }
        }
    }
    public BufferedImage drawBlank(){
        BufferedImage image = new BufferedImage(800, 200, 1);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800,200);
        g.setColor(Color.BLACK);
        g.fillRect(0,95,200,105);
        g.fillRect(600,95, 200, 105);
        g.setColor(Color.BLUE);
        g.fillRect(200,100,400,100);
        return image;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            animateCanvas(model.Cycle(10));
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
