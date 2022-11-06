import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics;

public class Snake extends JFrame {
    int width = 640;
    int height = 480;

    Point snake;
    Point comida;

    int widthPoint = 10;
    int heightPoint = 10;

    int direccion = KeyEvent.VK_LEFT;
    long frecuencia = 30;
    ImagenSnake imagenSnake;

    ArrayList<Point> lista = new ArrayList<Point>();

    public Snake() {
        setTitle("Snake");
        setSize(width, height);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - width / 2, dim.height / 2 - height / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Teclas teclas = new Teclas();
        this.addKeyListener(teclas);

        snake = new Point(width / 2, height / 2);
        imagenSnake = new ImagenSnake();
        this.getContentPane().add(imagenSnake);

        startGame();

        setVisible(true);
        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }

    public void crearComida() {
        Random rnd = new Random();

        comida.x = rnd.nextInt(width) + 5;
        if ((comida.x % 5) > 0) {
            comida.x = comida.x - (comida.x % 5);
        }

        if (comida.x < 5) {
            comida.x = comida.x + 10;
        }
        comida.y = rnd.nextInt(width) + 5;
        if ((comida.y % 5) > 0) {
            comida.y = comida.y - (comida.y % 5);
        }

        if (comida.y < 5) {
            comida.y = comida.y + 10;
        }
    }

    public void startGame() {
        comida = new Point(200, 200);
        snake = new Point(width / 2, height / 2);
        crearComida();
        lista = new ArrayList<Point>();
        lista.add(snake);
    }

    public static void main(String[] args) {
        Snake s = new Snake();
    }

    public void actualizar() {
        imagenSnake.repaint();

        lista.add(0, new Point(snake.x, snake.y));
        lista.remove((lista.size() - 1));

        if ((snake.x > (comida.x - 10)) && (snake.x < (comida.x + 10)) && (snake.y > (comida.y - 10))
                && (snake.y > (comida.y - 10))) {
            lista.add(0, new Point(snake.x, snake.y));
            crearComida();
        }
    }

    public class ImagenSnake extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 255));
            g.fillRect(snake.x, snake.y, widthPoint, heightPoint);

            for (int i = 0; i < lista.size(); i++) {
                Point point = (Point) lista.get(i);
                g.fillRect(point.x, point.y, widthPoint, heightPoint);
            }

            g.setColor(new Color(255, 0, 0));
            g.fillRect(comida.x, comida.y, widthPoint, heightPoint);

        }
    }

    public class Teclas extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);

            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direccion != KeyEvent.VK_DOWN) {
                    direccion = KeyEvent.VK_UP;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direccion != KeyEvent.VK_UP) {
                    direccion = KeyEvent.VK_DOWN;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direccion != KeyEvent.VK_RIGHT) {
                    direccion = KeyEvent.VK_LEFT;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direccion != KeyEvent.VK_LEFT) {
                    direccion = KeyEvent.VK_RIGHT;
                }
            }

        }
    }

    public class Momento extends Thread {
        long last = 0;

        public void run() {
            while (true) {
                if ((java.lang.System.currentTimeMillis() - last) > frecuencia) {
                    if (direccion == KeyEvent.VK_UP) {
                        snake.y = snake.y - heightPoint;
                        if (snake.y > height) {
                            snake.y = 0;
                        }
                        if (snake.y < 0) {
                            snake.y = height - heightPoint;
                        }

                    } else if (direccion == KeyEvent.VK_DOWN) {
                        snake.y = snake.y + heightPoint;
                        if (snake.y > height) {
                            snake.y = 0;
                        }
                        if (snake.y < 0) {
                            snake.y = height - heightPoint;
                        }
                    } else if (direccion == KeyEvent.VK_LEFT) {
                        snake.x = snake.x - widthPoint;
                        if (snake.x > width) {
                            snake.x = 0;
                        }
                        if (snake.x < 0) {
                            snake.x = width - widthPoint;
                        }
                    } else if (direccion == KeyEvent.VK_RIGHT) {
                        snake.x = snake.x + widthPoint;
                        if (snake.x > width) {
                            snake.x = 0;
                        }
                        if (snake.x < 0) {
                            snake.x = width - widthPoint;
                        }
                    }
                    actualizar();
                    last = java.lang.System.currentTimeMillis();
                }
            }
        }
    }
}
