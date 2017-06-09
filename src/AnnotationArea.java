import cn.edu.xjtu.AnnotationTool.util.Coordinates;
import cn.edu.xjtu.AnnotationTool.util.Lane;
import cn.edu.xjtu.AnnotationTool.util.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * Created by Henry on 2017/3/30.
 */

/**
 *
 */
public class AnnotationArea extends JPanel {
    private Image image;
    private long mouseNum;
//    private long mouseRectNum;
//    private long mouseDrag;
//    private long mouseRelease;
    private List<Coordinates> list = new ArrayList();
    private File imageFile;
    private Boolean isLeft; //左键为0，右键为1
//    private String carPart;
    private Color color;
    private int point = 0;
    private Coordinates tmpCoordinates;
    private Coordinates draggCoordinates;
//    private boolean isRect;
    private int imgWidth;
    private int imgHeight;
    private Model model;
    private int xsLabel;
    private int carLabel;
    private List<Lane> laneList;
    private String laneLabel;
    private Point2D tmpPoint;
    private final static int MULTI_CLICK_INTERVAL = (int) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
    private java.util.Timer timer;
    public String getLaneLabel() {
        return laneLabel;
    }

    public void setLaneLabel(String laneLabel) {
        this.laneLabel = laneLabel;
        if(!laneLabel.isEmpty()){
            Lane tmpLane = laneList.get(laneList.size()-1);
            tmpLane.setLabel(Integer.parseInt(laneLabel));
        }
        repaint();
    }

    public void setCarLabel(int carLabel) {
        this.carLabel = carLabel;
        if(!list.isEmpty()){
            tmpCoordinates = list.get(list.size()-1);
//            if(tmpCoordinates.getPart().equals("traffic_sign")){
//                tmpCoordinates.setLabel1(xsLabel);
//
//            }
            if(tmpCoordinates.getPart().equals("head") || tmpCoordinates.getPart().equals("tail")){
                System.out.println("Car Label:"+carLabel);
                tmpCoordinates.setLabel1(carLabel);
            }
            repaint();

        }
    }

    public void setxsLabel(int xsLabel) {
        this.xsLabel = xsLabel;
        if(!list.isEmpty()){
            tmpCoordinates = list.get(list.size()-1);
            if(tmpCoordinates.getPart().equals("traffic_sign")){
                System.out.println("XSLabel: "+xsLabel);
                tmpCoordinates.setLabel2(xsLabel);

            }
//            else if(tmpCoordinates.getPart().equals("head") || tmpCoordinates.getPart().equals("tail")){
//                tmpCoordinates.setLabel1(xsLabel);
//            }
            repaint();

        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
        mouseNum = 0;
    }

//    public void setMouseRelease(long mouseRelease) {
//        this.mouseRelease = mouseRelease;
//    }

    public void setMouseNum(long mouseNum) {
        this.mouseNum = mouseNum;
    }

    public File getImageFile() {
        return imageFile;
    }

//    public boolean getIsRect() {
//        return isRect;
//    }

//    public void setIsRect(boolean rect) {
//        isRect = rect;
//
//        repaint();
//    }

//    public String getCarPart() {
//        return carPart;
//    }
//
//    public void setCarPart(String carPart) {
//        this.carPart = carPart;
//        repaint();
//    }

    public List getList() {
        return list;
    }

    public void setList(List<Coordinates> list) {
        this.list = list;
        repaint();
    }

    public void setImagePath(File imagePath) {
        this.imageFile = imagePath;
        setMouseNum(0);
//        setMouseRelease(0);
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
            imgWidth = ((BufferedImage) image).getWidth();
            imgHeight = ((BufferedImage) image).getHeight();
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            if(imgWidth > dimension.width || imgHeight > dimension.height){
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"图片尺寸太大","提示",JOptionPane.INFORMATION_MESSAGE);
            }

            g.drawImage(image, 0, 0, ((BufferedImage) image).getWidth(), ((BufferedImage) image).getHeight(), this);

        }

        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(2.0f));
        for (Coordinates coordinates : list) {
            String tmpCarPart = coordinates.getPart();
            int x1 = coordinates.getX1();
            int y1 = coordinates.getY1();
            int x2 = coordinates.getX2();
            int y2 = coordinates.getY2();
            int x3 = coordinates.getX3();
            int y3 = coordinates.getY3();
            int x4 = coordinates.getX4();
            int y4 = coordinates.getY4();
//            int label = coordinates.getLabel1();

//            x1 = x1 > imgWidth ? imgWidth:x1;
//            x2 = x2 > imgWidth ? imgWidth:x2;
//            x3 = x3 > imgWidth ? imgWidth:x3;
//            x4 = x4 > imgWidth ? imgWidth:x4;
//            x1 = x1 < 0 ? 0:x1;
//            x2 = x2 < 0 ? 0:x2;
//            x3 = x3 < 0 ? 0:x3;
//            x4 = x4 < 0 ? 0:x4;
//
//            y1 = y1 > imgHeight ? imgHeight:y1;
//            y2 = y2 > imgHeight ? imgHeight:y2;
//            y3 = y3 > imgHeight ? imgHeight:y3;
//            y4 = y4 > imgHeight ? imgHeight:y4;
//            y1 = y1 < 0 ? 0:y1;
//            y2 = y2 < 0 ? 0:y2;
//            y3 = y3 < 0 ? 0:y3;
//            y4 = y4 < 0 ? 0:y4;

            color = switchColor(tmpCarPart);
//            color = (tmpCarPart.equals("head")) ? Color.blue : Color.red;
            g2D.setColor(color);
//            g2D.setFont();

            if(tmpCarPart.equals("head") || tmpCarPart.equals("tail")) {
                g2D.drawString("Vehicle Type: "+coordinates.getLabel1(),x1+2,y1-2);
                if (x2 >= x1 && y2 >= y1) {
//                    if (Point2D.distance(x1, y1, x2, y2) <= 1) {
//                        x2 = x2 + 10;
//                        y2 = y2 + 10;
//                    }
                    if(x1 > 0 || y1 >0) {
                        g2D.drawRect(x1, y1, x2 - x1, y2 - y1);
                    }

                }
                if (x3 > 0 && y3 > 0) {
                    if (x3 >= (x2 + x1) / 2) {

                        g2D.drawLine(x2, y2, x3, y3);
                        if (y4 > 0 && x4 > 0) {

                            g2D.drawLine(x3, y3, x4, y4);
                            g2D.drawLine(x2, y1, x4, y4);
                        }
                    }
                    if (x3 < (x1 + x2) / 2) {
                        g2D.drawLine(x1, y2, x3, y3);
                        if (y4 > 0) {

                            g2D.drawLine(x3, y3, x4, y4);
                            g2D.drawLine(x1, y1, x4, y4);
                        }
                    }

                }
            }
            else if(tmpCarPart.equals("person") || tmpCarPart.equals("tricycle")){
                if (x2 >= x1 && y2 >= y1) {
//                    if (Point2D.distance(x1, y1, x2, y2) <= 1) {
//                        x2 = x2 + 10;
//                        y2 = y2 + 10;
//                    }
                    g2D.drawRect(x1, y1, x2 - x1, y2 - y1);

                }

            }
            else if(tmpCarPart.equals("traffic_sign")){
                if (x2 >= x1 && y2 >= y1) {
//                    if (Point2D.distance(x1, y1, x2, y2) <= 1) {
//                        x2 = x2 + 10;
//                        y2 = y2 + 10;
//                    }
                    g2D.drawString("Traffic Sign: "+coordinates.getLabel2(),x1+2,y1-2);
                    g2D.drawRect(x1, y1, x2 - x1, y2 - y1);

                }

            }
            else if(tmpCarPart.equals("bike")){
                if(x1 > 0 && y1 > 0 && x2 > 0 && y2 > 0 && x2 == x1 && y2 > y1) {
//                    System.out.println("before drawstring....");
//                    g2D.drawString("hahahahahaahahah",x1,y1);
//                    System.out.println("after drawstring....");
                    g2D.drawLine(x1, y1, x2, y2);
                    if(x3 > 0 && y3 > 0 && x3 > x2) {
                        g2D.drawLine(x2, y2, x3, y3);
                        int x = x3 + (x1 - x2);
                        int y  = y3 + (y1 - y2);
                        if(x > 0 && y > 0 && x < imgWidth && y < imgHeight){
                            g2D.drawLine(x3, y3, x, y);
                            g2D.drawLine(x1, y1, x, y);
                        }
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
        for(Lane lane: laneList) {
            String obj = lane.getObject();
            int label = lane.getLabel();
            List<Point2D> point2DList = lane.getPointList();
            g2D.setColor(Color.BLACK);

            if(!point2DList.isEmpty()) {
                for (int i = 0; i < point2DList.size(); i++) {
                    System.out.println("draw point "+ i);
                    g2D.drawOval((int) point2DList.get(i).getX() - 5, (int) point2DList.get(i).getY()-5, 10, 10);
                    if(i < point2DList.size()-1 ) {
                        g2D.drawLine((int) point2DList.get(i).getX(), (int) point2DList.get(i).getY(), (int) point2DList.get(i + 1).getX(), (int) point2DList.get(i + 1).getY());
                    }
                }
//                if((int)tmpPoint.getX() != 0 || (int)tmpPoint.getY() != 0){
//
//                    g2D.drawLine((int)point2DList.get(point2DList.size()-1).getX(),(int)point2DList.get(point2DList.size()-1).getY(),(int)tmpPoint.getX(),(int)tmpPoint.getY());
//                    g2D.drawOval((int)tmpPoint.getX() - 5, (int)tmpPoint.getY() -5 , 10 , 10);
//                }

//                g2D.drawOval((int) point2DList.get(point2DList.size()-1).getX() - 5, (int) point2DList.get(point2DList.size()-1).getY(), 10, 10);
            }
            g2D.drawString("Lane: "+ label,(int)point2DList.get(0).getX()-6,(int)point2DList.get(0).getY()-6);
        }


    }

    private Color switchColor(String tmpCarPart) {
        color = Color.red;
        if(tmpCarPart.equals("head")) {
            color = Color.BLUE;
        }
        else if(tmpCarPart.equals("tail")){
            color = Color.RED;
        }
        else if(tmpCarPart.equals("person")){
            color = Color.YELLOW;
        }
        else if(tmpCarPart.equals("tricycle")){
            color = Color.MAGENTA;

        }
        else if(tmpCarPart.equals("traffic_sign")){
            color = Color.CYAN;
        }
        else if(tmpCarPart.equals("bike")){
            color = Color.GREEN;
        }
        return color;
    }


    public AnnotationArea(Main mainFrame) {

        mouseNum = 0;
//        mouseDrag = 0;
//        mouseRelease = 0;
//        mouseRectNum = 0;
        isLeft = false;
//        carPart = "tail";
        color = Color.red;
//        isRect = false;
        imageFile = null;
        model = Model.TAILSIDE;
        tmpPoint = new Point(0,0);

        laneList = new LinkedList<Lane>();
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
//        timer = new Timer(MULTI_CLICK_INTERVAL,this);

        addMouseListener(new MouseListenerA());
        addMouseMotionListener(new MouseMotionListenerB());


    }

    public void deleteLastCoordinates() {
        mouseNum = 0;
        if(model != Model.LANE){
            if(list.size() > 0) {
                list.remove(list.size() - 1);

//            mouseDrag = 0;
//            mouseRelease = 0;
//            mouseRectNum = 0;
            }
        }
        else {
            if(laneList.size() > 0){
                laneList.remove(laneList.size()-1);
            }
        }

        repaint();
    }

    public void setLaneList(List<Lane> laneList) {
        this.laneList = laneList;
    }

    public List<Lane> getLaneList() {
        return laneList;
    }


    public class MouseListenerA extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = prepareXY(e.getX(),imgWidth);
            int y = prepareXY(e.getY(),imgHeight);
            if(model == Model.BIKE && e.getButton() == MouseEvent.BUTTON1){
                mouseNum ++;
                if(mouseNum%3 == 1){
                    generateP1("bike", x, y);
                }
                else if(mouseNum%3 == 2){
                    if(!list.isEmpty()) {
                        tmpCoordinates = list.get(list.size() - 1);
                        tmpCoordinates.setX2(tmpCoordinates.getX1());
                        int ytmp = y > tmpCoordinates.getY1()? y:(tmpCoordinates.getY1());
                            tmpCoordinates.setY2(ytmp);

                    }
                }
                else if(mouseNum%3 == 0){
                    if(!list.isEmpty()) {
                        tmpCoordinates = list.get(list.size() - 1);
                        int xtmp = x > tmpCoordinates.getX3() ? x : (tmpCoordinates.getX3());

                        tmpCoordinates.setX3(xtmp);
                        tmpCoordinates.setY3(y);
//                        tmpCoordinates.setX4(tmpCoordinates.getX3()+(tmpCoordinates.getX1()-tmpCoordinates.getX2()));
//                        tmpCoordinates.setY4(tmpCoordinates.getY3()-(tmpCoordinates.getY2()-tmpCoordinates.getY1()));

                    }
                }
            }
            else if(model == Model.LANE &&e.getButton() == MouseEvent.BUTTON1){

                if(e.getClickCount() == 2){
                    mouseNum = 0;
                    return;
                }
                else if(e.getClickCount() == 1) {
                    mouseNum ++;
                    if (mouseNum == 1) {
                        Lane tmpLane = new Lane();
                        tmpLane.setObject("lane");
                        List<Point2D> pointList = new LinkedList<Point2D>();

                        pointList.add(new Point(x, y));
                        tmpLane.setPointList(pointList);
                        laneList.add(tmpLane);

                    }
                    else if (mouseNum > 1) {
                        if (!laneList.isEmpty()) {
                            Lane tmpLane = laneList.get(laneList.size() - 1);

                            tmpLane.getPointList().add(new Point(x, y));
                        }
                    }
                    System.out.println("click "+ mouseNum);

                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = prepareXY(e.getX(),imgWidth);
            int y = prepareXY(e.getY(),imgHeight);

            if (e.getButton() == MouseEvent.BUTTON1) {
                isLeft = true;
                if(model == Model.PERSON){
                    generateP1("person", x, y);
                }
                else if(model == Model.TRICYCLE){
                    generateP1("tricycle", x, y);
                }
                else if(model == Model.SIGN){
                    generateP1("traffic_sign", x, y);
                }
                else if(model == Model.TAIL){
                    generateP1("tail",x,y);
                }
                else if(model == Model.HEAD){
                    generateP1("head",x,y);
                }
                else if(model == Model.TAILSIDE){
                    mouseNum ++;
                    generateCoor(mouseNum,"tail",x,y);
                    System.out.println("tail side mousePress "+mouseNum);

                }
                else if(model == Model.HEADSIDE){
                    mouseNum ++;
                    generateCoor(mouseNum,"head",x,y);

                }


                repaint();
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                isLeft = false;
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        tmpCoordinates = list.get(i);
                        if (Point2D.distance(x, y, tmpCoordinates.getX1(), tmpCoordinates.getY1()) <= 5) {

                            tmpCoordinates.setX1(x);
                            tmpCoordinates.setY1(y);
                            draggCoordinates = tmpCoordinates;
                            repaint();
                            point = 1;
                            break;
                        } else if (Point2D.distance(x, y, tmpCoordinates.getX2(), tmpCoordinates.getY2()) <= 5) {

                            tmpCoordinates.setX2(x);
                            tmpCoordinates.setY2(y);
                            draggCoordinates = tmpCoordinates;
                            repaint();
                            point = 2;
                            break;
                        } else if (Point2D.distance(e.getX(), e.getY(), tmpCoordinates.getX3(), tmpCoordinates.getY3()) <= 5) {

                            tmpCoordinates.setX3(x);
                            tmpCoordinates.setY3(y);
                            draggCoordinates = tmpCoordinates;
                            repaint();
                            point = 3;
                            break;
                        } else if (Point2D.distance(e.getX(), e.getY(), tmpCoordinates.getX4(), tmpCoordinates.getY4()) <= 5) {

                            tmpCoordinates.setX4(x);
                            tmpCoordinates.setY4(y);
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
                if(laneList.size()>0){

                }
            }
        }

        private void generateCoor(long mouseNum, String label,int x,int y) {

            if (mouseNum % 3 == 1) {
                generateP1(label, x, y);
            }
            else if (mouseNum % 3 == 2) {
                tmpCoordinates = list.get(list.size() - 1);
                tmpCoordinates.setX3(x);
                tmpCoordinates.setY3(y);

            }
            else if (mouseNum % 3 == 0) {
                tmpCoordinates = list.get(list.size() - 1);
                tmpCoordinates.setX4(x);
                tmpCoordinates.setY4(y);
            }
        }

        private void generateP1(String object, int x, int y) {
            tmpCoordinates = new Coordinates();

            tmpCoordinates.setX1(x);
            tmpCoordinates.setY1(y);
            tmpCoordinates.setPart(object);
            list.add(tmpCoordinates);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int x = prepareXY(e.getX(),imgWidth);
            int y = prepareXY(e.getY(),imgHeight);

            if (e.getButton() == MouseEvent.BUTTON1 && !list.isEmpty()) {
                isLeft = true;

                tmpCoordinates = list.get(list.size() - 1);

                if(model == Model.PERSON || model == Model.SIGN || model == Model.TRICYCLE || model == Model.TAIL ||
                        model == Model.HEAD){
//                    int x = e.getX();
//                    int y = e.getY();

                    if (Point2D.distance(tmpCoordinates.getX1(), tmpCoordinates.getY1(), x, y) <= 1) {
                        tmpCoordinates.setX2(x+10);
                        tmpCoordinates.setY2(y+10);
                    }
                    else {
                        tmpCoordinates.setX2(x);
                        tmpCoordinates.setY2(y);
                    }
                }
                else if(model == Model.HEADSIDE || model == Model.TAILSIDE){
                    if(mouseNum%3 == 1){
                        System.out.println("release .......");

                        if (Point2D.distance(tmpCoordinates.getX1(), tmpCoordinates.getY1(), x, y) <= 1) {
                            tmpCoordinates.setX2(x+10);
                            tmpCoordinates.setY2(y+10);
                        }
                        else {
                            tmpCoordinates.setX2(x);
                            tmpCoordinates.setY2(y);
                        }
                    }
                }
////                else if(model == )
//                else if(isRect == false) {
//                    mouseRelease++;
//                    if (mouseRelease % 3 == 1) {
//                        tmpCoordinates.setX2(e.getX());
//                        tmpCoordinates.setY2(e.getY());
//                    }
//                }
//                else if(isRect == true){
//                    tmpCoordinates.setX2(e.getX());
//                    tmpCoordinates.setY2(e.getY());
//                }

                repaint();
            }
        }

//        private void generateP2(int x, int y) {
//            tmpCoordinates.setX2(x);
//            tmpCoordinates.setY2(y);
//        }

    }

    public class MouseMotionListenerB extends MouseMotionAdapter {

//        private Coordinates tmpCoordinates;

        @Override
        public void mouseDragged(MouseEvent e) {

            System.out.println("mouseDragged 1");
            System.out.println(""+e.getButton());
            int x = prepareXY(e.getX(),imgWidth);
            int y = prepareXY(e.getY(),imgHeight);
            if (isLeft) {
                System.out.println("mouseDragged left");
                if (!list.isEmpty()) {
                    tmpCoordinates = list.get(list.size() - 1);
                    if (model == Model.PERSON || model == Model.SIGN || model == Model.TRICYCLE || model == Model.HEAD || model == Model.TAIL) {

                        tmpCoordinates.setX2(x);
                        tmpCoordinates.setY2(y);

                    } else if (model == Model.HEADSIDE || model == Model.TAILSIDE) {
                        if (mouseNum % 3 == 1) {

                            tmpCoordinates.setX2(x);
                            tmpCoordinates.setY2(y);
                            System.out.println("Tail side || Head side mouseDragged");

                        }
                    }
//                else if(model == Model.HEAD || model == Model.TAIL){
//                    tmpCoordinates.setX2(e.getX());
//                    tmpCoordinates.setY2(e.getY());
//                }
//                repaint();
                }

            }
            else if (!isLeft){
                System.out.println("mouseDragged right");
                if (list.size() > 0) {
                    if (draggCoordinates != null) {

                        if(model != Model.BIKE) {
                            if (point == 1) {

                                    draggCoordinates.setX1(x);
                                    draggCoordinates.setY1(y);

                            } else if (point == 2) {
//                                if (e.getX() > draggCoordinates.getX1() && e.getY() > draggCoordinates.getY2()) {
                                    draggCoordinates.setX2(x);
                                    draggCoordinates.setY2(y);
//                                }
                            } else if (point == 3) {
                                draggCoordinates.setX3(x);
                                draggCoordinates.setY3(y);
                            } else if (point == 4) {
                                draggCoordinates.setX4(x);
                                draggCoordinates.setY4(y);
                            }
                        }
                        else if(model == Model.BIKE){
                            if (point == 1) {
                                if (e.getX() < draggCoordinates.getX3() && e.getY() < draggCoordinates.getY2()) {
                                    draggCoordinates.setX1(x);
                                    draggCoordinates.setY1(y);
                                    draggCoordinates.setX2(x);
                                }
                            } else if (point == 2) {
                                if (e.getX() < draggCoordinates.getX3() && e.getY() > draggCoordinates.getY1()) {
                                    draggCoordinates.setX1(x);
                                    draggCoordinates.setX2(x);
                                    draggCoordinates.setY2(y);
                                }
                            } else if (point == 3) {
                                if(e.getX() > draggCoordinates.getX1()) {

                                    draggCoordinates.setX3(x);
                                    draggCoordinates.setY3(y);
                                }
                            }

                        }
//                        repaint();
                    }

                }
                if(laneList.size()>0){
                    for(int i = 0; i < laneList.size(); i++){
                        List<Point2D> pointList = laneList.get(i).getPointList();
                        for(int j = 0; j < pointList.size(); j++){

                            if(Point2D.distance(x,y,pointList.get(j).getX(),pointList.get(j).getY()) < 10){
                                pointList.get(j).setLocation(x,y);

                            }
                        }
                    }
                }

            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = prepareXY(e.getX(),imgWidth);
            int y = prepareXY(e.getY(),imgHeight);

//            if(model == Model.BIKE){
//                if(!list.isEmpty()) {
//                    tmpCoordinates = list.get(list.size() - 1);
//                    if(mouseNum%3 == 1) {
//                        tmpCoordinates.setX2(e.getX());
//                        tmpCoordinates.setY2(e.getY());
//                    }
//                    if(mouseNum%3 == 2){
//                        tmpCoordinates.setX3(e.getX());
//                        tmpCoordinates.setY3(e.getY());
//                    }
//                }
//
//            }

//             if (e.getButton() == MouseEvent.BUTTON1) {
                if(model == Model.TAILSIDE || model == Model.HEADSIDE) {
                    if (list.size() > 0) {
                        tmpCoordinates = list.get(list.size() - 1);
                        if (mouseNum % 3 == 1) {
                            tmpCoordinates.setX3(x);
                            tmpCoordinates.setY3(y);
                        } else if (mouseNum % 3 == 2) {
                            tmpCoordinates.setX4(x);
                            tmpCoordinates.setY4(y);
                        }
                    }
                }
                else if(model == Model.BIKE){
                    if (list.size() > 0) {
                        tmpCoordinates = list.get(list.size() - 1);
                        if (mouseNum % 3 == 1) {

                            tmpCoordinates.setX2(tmpCoordinates.getX1());
                            int ytmp = y > tmpCoordinates.getY1() ? y : tmpCoordinates.getY1();
                            tmpCoordinates.setY2(ytmp);
                        } else if (mouseNum % 3 == 2) {
                            if(x > tmpCoordinates.getX2()) {
                                tmpCoordinates.setX3(x);
                                tmpCoordinates.setY3(y);
                            }
                        }
                    }
                }
//                if(model == Model.LANE){
//                    if(!laneList.isEmpty()){
//                        Lane lane = laneList.get(laneList.size()-1);
//                        List<Point2D> point2DList = lane.getPointList();
//
////                        point2DList.add(point);
//                        if(!lane.getPointList().isEmpty()){
////                            Point2D point = point2DList.get(point2DList.size()-1);
//                            int x = e.getX() > imgWidth? imgWidth: e.getX();
//                            int y = e.getY() > imgHeight? imgHeight:e.getY();
//                            if(point2DList.size() == 1) {
//                                Point point = new Point(x,y);
//                                lane.getPointList().add(point);
//                            }
//                            else if (point2DList.size() > 1){
//                              point2DList.get(point2DList.size()-1).setLocation(x,y);
//                            }
////                            tmpPoint.setLocation(x,y);
////                            point2DList.add(point2DList.size()-1, new Point(x,y));
//
//                        }
//                    }
//                }
                repaint();
//            }


        }


    }
    public int prepareXY(int xy, int bound){

        if(xy < 0){
            xy = 0;
        }
        if(xy > bound){
            xy = bound;
        }
            return xy;
    }

}
