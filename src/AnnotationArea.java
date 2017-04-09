import cn.edu.xjtu.AnnotationTool.util.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 2017/3/30.
 */
public class AnnotationArea extends JPanel {
    private Image image;
    private long mouseNum;
    private long mouseRectNum;
    private long mouseDrag;
    private long mouseRelease;
    private List<Coordinates> list = new ArrayList();
    private File imageFile;
    private Boolean isLeft; //左键为0，右键为1
    private String carPart;
    private Color color;
    private int point = 0;
    private Coordinates tmpCoordinates;
    private Coordinates draggCoordinates;
    private boolean isRect;

    public File getImageFile() {
        return imageFile;
    }

    public boolean getIsRect() {
        return isRect;
    }

    public void setIsRect(boolean rect) {
        isRect = rect;

        repaint();
    }

    public String getCarPart() {
        return carPart;
    }

    public void setCarPart(String carPart) {
        this.carPart = carPart;
        repaint();
    }

    public List getList() {
        return list;
    }

    public void setList(List<Coordinates> list) {
        this.list = list;
        repaint();
    }

    public void setImagePath(File imagePath) {
        this.imageFile = imagePath;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageFile != null) {
            try {
                image = ImageIO.read(imageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            g.drawImage(image, 0, 0, ((BufferedImage) image).getWidth(), ((BufferedImage) image).getHeight(), this);

        }

        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(2.0f));
        for (Coordinates coordinates : list) {
            int x1 = coordinates.getX1();
            int y1 = coordinates.getY1();
            int x2 = coordinates.getX2();
            int y2 = coordinates.getY2();
            int x3 = coordinates.getX3();
            int y3 = coordinates.getY3();
            int x4 = coordinates.getX4();
            int y4 = coordinates.getY4();
            String tmpCarPart = coordinates.getPart();
            color = (tmpCarPart.equals("head")) ? Color.blue : Color.red;
            g2D.setColor(color);

            if (x2 > x1 && y2 > y1) {

                g2D.drawRect(x1, y1, x2 - x1, y2 - y1);

            }
            if (x3 > 0 && y3 > 0) {
                if (x3 >= (x2+x1)/2) {

                    g2D.drawLine(x2, y2, x3, y3);
                    if (y4 > 0 && x4 > 0) {

                        g2D.drawLine(x3, y3, x4, y4);
                        g2D.drawLine(x2, y1, x4, y4);
                    }
                }
                if (x3 < (x1+x2)/2) {
                    g2D.drawLine(x1, y2, x3, y3);
                    if (y4 > 0) {

                        g2D.drawLine(x3, y3, x4, y4);
                        g2D.drawLine(x1, y1, x4, y4);
                    }
                }

            }
            g2D.setColor(Color.green);
            if(x1 > 0 && y1 > 0) {
                g2D.drawOval(x1 - 5, y1 - 5, 10, 10);
            }
            if(x2 > 0 && y2 > 0)
            g2D.drawOval(x2 - 5, y2 - 5, 10, 10);
            if(x3 > 0 && y3 > 0)
            g2D.drawOval(x3 - 5, y3 - 5, 10, 10);
            if(x4 > 0 && y4 > 0)
            g2D.drawOval(x4 - 5, y4 - 5, 10, 10);


        }


    }


    public AnnotationArea(Main mainFrame) {

        mouseNum = 0;
        mouseDrag = 0;
        mouseRelease = 0;
        mouseRectNum = 0;
        isLeft = false;
        carPart = "tail";
        color = Color.red;
        isRect = false;


        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        addMouseListener(new MouseListenerA());
        addMouseMotionListener(new MouseMotionListenerB());


    }

    public void deleteLastCoordinates() {
        if(list.size() > 0) {
            list.remove(list.size() - 1);
        }
        repaint();
    }


    public class MouseListenerA extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getButton() == MouseEvent.BUTTON1) {
                isLeft = true;
                if(isRect == false) {
                    mouseNum++;
                    if (mouseNum % 3 == 1) {
                        tmpCoordinates = new Coordinates();
                        tmpCoordinates.setX1(e.getX());
                        tmpCoordinates.setY1(e.getY());
                        tmpCoordinates.setPart(carPart);
                        list.add(tmpCoordinates);
                    }
                    else if (mouseNum % 3 == 2) {
                        tmpCoordinates = list.get(list.size() - 1);
                        tmpCoordinates.setX3(e.getX());
                        tmpCoordinates.setY3(e.getY());

                    }
                    else if (mouseNum % 3 == 0) {
                        tmpCoordinates = list.get(list.size() - 1);
                        tmpCoordinates.setX4(e.getX());
                        tmpCoordinates.setY4(e.getY());
                    }
                }
                else if(isRect == true){
                    tmpCoordinates = new Coordinates();
                    tmpCoordinates.setX1(e.getX());
                    tmpCoordinates.setY1(e.getY());
                    tmpCoordinates.setPart(carPart);
                    list.add(tmpCoordinates);
                }
                repaint();
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                isLeft = false;
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        tmpCoordinates = list.get(i);
                        if (Point2D.distance(e.getX(), e.getY(), tmpCoordinates.getX1(), tmpCoordinates.getY1()) <= 5) {
                            tmpCoordinates.setX1(e.getX());
                            tmpCoordinates.setY1(e.getY());
                            draggCoordinates = tmpCoordinates;
                            repaint();
                            point = 1;
                            break;
                        } else if (Point2D.distance(e.getX(), e.getY(), tmpCoordinates.getX2(), tmpCoordinates.getY2()) <= 5) {
                            tmpCoordinates.setX2(e.getX());
                            tmpCoordinates.setY2(e.getY());
                            draggCoordinates = tmpCoordinates;
                            repaint();
                            point = 2;
                            break;
                        } else if (Point2D.distance(e.getX(), e.getY(), tmpCoordinates.getX3(), tmpCoordinates.getY3()) <= 5) {
                            tmpCoordinates.setX3(e.getX());
                            tmpCoordinates.setY3(e.getY());
                            draggCoordinates = tmpCoordinates;
                            repaint();
                            point = 3;
                            break;
                        } else if (Point2D.distance(e.getX(), e.getY(), tmpCoordinates.getX4(), tmpCoordinates.getY4()) <= 5) {
                            tmpCoordinates.setX4(e.getX());
                            tmpCoordinates.setY4(e.getY());
                            draggCoordinates = tmpCoordinates;
                            repaint();
                            point = 4;
                            break;
                        } else {
                            draggCoordinates = null;
                            point = 0;
                        }

                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                isLeft = true;
                tmpCoordinates = list.get(list.size() - 1);
                if(isRect == false) {
                    mouseRelease++;
                    if (mouseRelease % 3 == 1) {
                        tmpCoordinates.setX2(e.getX());
                        tmpCoordinates.setY2(e.getY());
                    }
                }
                else if(isRect == true){
                    tmpCoordinates.setX2(e.getX());
                    tmpCoordinates.setY2(e.getY());
                }

                repaint();
            }
        }

    }

    public class MouseMotionListenerB extends MouseMotionAdapter {

//        private Coordinates tmpCoordinates;

        @Override
        public void mouseDragged(MouseEvent e) {
            if (isLeft == true) {
                tmpCoordinates = list.get(list.size() - 1);
                if(isRect == false) {
                    if (mouseNum % 3 == 1) {
                        tmpCoordinates.setX2(e.getX());
                        tmpCoordinates.setY2(e.getY());

                    }
                }
                else if(isRect == true){
                    tmpCoordinates.setX2(e.getX());
                    tmpCoordinates.setY2(e.getY());
                }
                repaint();
            }
            else if (isLeft == false ) {
                if (list.size() > 0) {
                    if (draggCoordinates != null) {
                        if (point == 1) {
                            draggCoordinates.setX1(e.getX());
                            draggCoordinates.setY1(e.getY());
                        } else if (point == 2) {
                            draggCoordinates.setX2(e.getX());
                            draggCoordinates.setY2(e.getY());
                        } else if (point == 3) {
                            draggCoordinates.setX3(e.getX());
                            draggCoordinates.setY3(e.getY());
                        } else if (point == 4) {
                            draggCoordinates.setX4(e.getX());
                            draggCoordinates.setY4(e.getY());
                        }
                        repaint();
                    }

                }

            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (isLeft == true && isRect == false) {

                if (list.size() > 0) {
                    tmpCoordinates = list.get(list.size() - 1);
                    if (mouseRelease % 3 == 1) {
                        tmpCoordinates.setX3(e.getX());
                        tmpCoordinates.setY3(e.getY());
                    } else if (mouseRelease % 3 == 2) {
                        tmpCoordinates.setX4(e.getX());
                        tmpCoordinates.setY4(e.getY());
                    }
                }
                repaint();
            }


        }


    }

}
