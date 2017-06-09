import cn.edu.xjtu.AnnotationTool.util.CoordiantesListUtil;
import cn.edu.xjtu.AnnotationTool.util.Coordinates;
import cn.edu.xjtu.AnnotationTool.util.Lane;
import cn.edu.xjtu.AnnotationTool.util.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static cn.edu.xjtu.AnnotationTool.util.ShowWindow.run;

/**
 * Created by Henry on 2017/3/30.
 */
public class Main extends JFrame implements ActionListener{
    private JMenuBar jmenuBar;
    private JMenu fileMenu;
    private JMenu deleteMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem deleteMenuItem;
    private JButton nextButton;
    private JButton previousButton;
    private JButton deleteButton;
    private AnnotationArea annotationArea;
    //private static ExecutorService exec = Executors.newCachedThreadPool();
    private File imageDir;
    private java.util.List<File> fileList;
    private File imageFile;
    private File nextImageFile;
    private int nextButtonNum = 0;
    private JToolBar jToolBar;
    private JButton labelTailSide;
    private JButton labelHeadSide;
    private JButton labelTail;
    private JButton labelHead;

    private JButton labelPerson;
    private JButton labelTricycle;
    private JButton labelSign;
    private JButton labelBike;
    private JButton fengeButton1;
    private JButton fengeButton2;
    private JButton fengeButton3;

    private Image iconImage;
    private JButton jButton;
    private JMenu help;
    private JMenuItem aboutMenuItem;

    private JTextField xsTextField;
    private JLabel xsLabel;
    private JButton okButton;
    private JPanel xsPanel;
    private JPanel carTypePanel;
    private JLabel carTypeLabel;
    private JTextField carTypeTextField;
    private JButton carTypeOkButton;
    private JPanel statusPanel;

    private JButton laneButton;
    private Icon laneIcon;
    private JPanel lanePanel;
    private JLabel laneLabel;
    private JTextField laneTextField;
    private JButton laneOKButton;

    private Icon tailSideIcon, headSideIcon, deleteIcon,nextIcon, previousIcon, tailIcon, headIcon, personIcon, tricycleIcon, signIcon, bikeIcon, fengeIcon;

    public Main() {
        jmenuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        deleteMenu = new JMenu("Delete");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        deleteMenuItem = new JMenuItem("Delete Last");
        imageDir = new File("");
        fileList = new ArrayList<File>();
        imageFile = new File("");
        nextImageFile = new File("");
        jToolBar = new JToolBar();
        help = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");

        tailSideIcon = new ImageIcon(getClass().getResource("/icon/tailSideIcon.png"));
        headSideIcon = new ImageIcon(getClass().getResource("/icon/headSideIcon.png"));
        deleteIcon = new ImageIcon(getClass().getResource("/icon/deleteIcon.png"));
        nextIcon = new ImageIcon(getClass().getResource("/icon/nextIcon.png"));
        previousIcon = new ImageIcon(getClass().getResource("/icon/previousIcon.png"));
        tailIcon = new ImageIcon(getClass().getResource("/icon/tailIcon.png"));
        headIcon = new ImageIcon(getClass().getResource("/icon/headIcon.png"));
        personIcon = new ImageIcon(getClass().getResource("/icon/personIcon.png"));
        tricycleIcon = new ImageIcon(getClass().getResource("/icon/tricycleIcon.png"));
        signIcon = new ImageIcon(getClass().getResource("/icon/signIcon.png"));
        bikeIcon = new ImageIcon(getClass().getResource("/icon/bikeIcon.png"));
        fengeIcon = new ImageIcon(getClass().getResource("/icon/fenge.png"));
        laneIcon = new ImageIcon(getClass().getResource("/icon/lane.png"));

        labelTailSide = new JButton("Tail Side",tailSideIcon);
        labelHeadSide = new JButton("Head Side",headSideIcon);
        labelTail = new JButton("Tail",tailIcon);
        labelTail.setPreferredSize(new Dimension(80,30));
        labelHead = new JButton("Head", headIcon);
        labelHead.setPreferredSize(new Dimension(80,30));
        labelPerson = new JButton("Person",personIcon);
        labelPerson.setPreferredSize(new Dimension(100,30));
        labelSign = new JButton("Traffic Sign",signIcon);
        labelSign.setPreferredSize(new Dimension(150,30));
        labelTricycle = new JButton("Tricycle",tricycleIcon);
        labelTricycle.setPreferredSize(new Dimension(150,30));
        labelBike = new JButton("Bike", bikeIcon);
        labelBike.setPreferredSize(new Dimension(100,30));
        laneButton = new JButton("Lane",laneIcon);
        laneButton.setPreferredSize(new Dimension(100,30));
        deleteButton = new JButton("Delete Last", deleteIcon);
        deleteButton.setPreferredSize(new Dimension(150,30));
        nextButton = new JButton("Next",nextIcon);
        nextButton.setPreferredSize(new Dimension(100,30));
        previousButton = new JButton("Previous",previousIcon);
        fengeButton1 = new JButton("",fengeIcon);
        fengeButton2 = new JButton("",fengeIcon);
        fengeButton3 = new JButton("",fengeIcon);

        xsLabel = new JLabel("Traffic Sign:");
        xsTextField = new JTextField(10);
        okButton = new JButton("OK");
        xsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        carTypeLabel = new JLabel("Vehicle Type:");
        carTypeTextField = new JTextField(10);
        carTypeOkButton = new JButton("OK");
        laneLabel = new JLabel("Lane Type:");
        laneTextField = new JTextField(20);
        laneOKButton = new JButton("OK");
        lanePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lanePanel.add(laneLabel);
        lanePanel.add(laneTextField);
        lanePanel.add(laneOKButton);

        xsPanel.add(xsLabel);
        xsPanel.add(xsTextField);
        xsPanel.add(okButton);
        xsPanel.setSize(400,20);
        carTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        carTypePanel.add(carTypeLabel);
        carTypePanel.add(carTypeTextField);
        carTypePanel.add(carTypeOkButton);
        carTypePanel.setSize(400,20);
//        carTypePanel.add(xsPanel);
        statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        statusPanel.add(carTypePanel);
        statusPanel.add(new JLabel("        "));
        statusPanel.add(xsPanel);
        statusPanel.add(new JLabel("        "));
        statusPanel.add(lanePanel);





        labelTailSide.setToolTipText("标注车尾和侧面，快捷键ALT + Q");
        labelHeadSide.setToolTipText("标标注车头和侧面，快捷键ALT + W");
        labelTail.setToolTipText("标注车尾，快捷键 ALT + E");
        labelHead.setToolTipText("标注车头，快捷键 ALT + R");

        labelPerson.setToolTipText("标注行人，快捷键 ALT + T");
        labelTricycle.setToolTipText("标注三轮车，快捷键 ALT + F");
        labelSign.setToolTipText("标注交通标志，快捷键 ALT + G");
        labelBike.setToolTipText("标注自行车，快捷键 ALT + C");

        deleteButton.setToolTipText("删除上一个标注, ALT + A");
        nextButton.setToolTipText("下一张图片, 快捷键 ALT + D");
        previousButton.setToolTipText("上一张图片, 快捷键 ALT + S");
        laneButton.setToolTipText("标注道路边线，快捷键 ALT + V");

        jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        jToolBar.add(labelTailSide);
        jToolBar.add(labelHeadSide);
        jToolBar.add(labelTail);
        jToolBar.add(labelHead);
        jToolBar.add(fengeButton1);
        jToolBar.add(labelPerson);
        jToolBar.add(labelTricycle);
        jToolBar.add(labelBike);
        jToolBar.add(fengeButton2);
        jToolBar.add(labelSign);
        jToolBar.add(laneButton);
        jToolBar.add(fengeButton3);
        jToolBar.add(deleteButton);
        jToolBar.add(previousButton);
        jToolBar.add(nextButton);

        labelTail.addActionListener(this);
        labelHead.addActionListener(this);
        labelHeadSide.addActionListener(this);
        labelTailSide.addActionListener(this);
        labelPerson.addActionListener(this);
        labelTricycle.addActionListener(this);
        labelSign.addActionListener(this);
        labelBike.addActionListener(this);
        laneButton.addActionListener(this);

        fileMenu.add(openMenuItem);
        openMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(this);
        deleteMenu.add(deleteMenuItem);
        deleteMenuItem.addActionListener(this);
        help.add(aboutMenuItem);
        aboutMenuItem.addActionListener(this);
        jmenuBar.add(fileMenu);
        jmenuBar.add(deleteMenu);
        jmenuBar.add(help);
        help.addActionListener(this);
        deleteMenu.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);
        deleteButton.addActionListener(this);
        okButton.addActionListener(this);
        xsTextField.addActionListener(this);
        carTypeOkButton.addActionListener(this);
        carTypeTextField.addActionListener(this);
        laneTextField.addActionListener(this);
        laneOKButton.addActionListener(this);

        labelTailSide.setMnemonic(KeyEvent.VK_Q);
        labelHeadSide.setMnemonic(KeyEvent.VK_W);
        labelTail.setMnemonic(KeyEvent.VK_E);
        labelHead.setMnemonic(KeyEvent.VK_R);
        deleteButton.setMnemonic(KeyEvent.VK_A);
        previousButton.setMnemonic(KeyEvent.VK_S);
        nextButton.setMnemonic(KeyEvent.VK_D);
        labelPerson.setMnemonic(KeyEvent.VK_T);
        labelTricycle.setMnemonic(KeyEvent.VK_F);
        labelSign.setMnemonic(KeyEvent.VK_G);
        labelBike.setMnemonic(KeyEvent.VK_C);
        laneButton.setMnemonic(KeyEvent.VK_V);



        setJMenuBar(jmenuBar);

        setLayout(new BorderLayout());
        annotationArea = new AnnotationArea(this);
//        annotationArea.setLayout(null);
//        annotationArea.add(xsPanel);
//        xsPanel.setBounds(annotationArea.getWidth()-200,50,200,50);

        JScrollPane jsp = new JScrollPane(annotationArea);
        add(jsp,BorderLayout.CENTER);
        add(jToolBar,BorderLayout.NORTH);
        add(statusPanel,BorderLayout.SOUTH);
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
        run(new Main());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == laneButton){
            switchModel(laneButton,Model.LANE);
        }
        if(e.getSource() == labelBike){
            switchModel(labelBike,Model.BIKE);
        }
        if(e.getSource() == labelPerson){
            switchModel(labelPerson,Model.PERSON);
        }
        if(e.getSource() == labelSign){
            switchModel(labelSign,Model.SIGN);
        }
        if(e.getSource() == labelTricycle){
            switchModel(labelTricycle,Model.TRICYCLE);
        }
        if(e.getSource() == aboutMenuItem){
            System.out.println("about....");
            opAbout();
        }
        if(e.getSource() == labelTail){
            switchModel(labelTail,Model.TAIL);
        }
        else if(e.getSource() == labelHead){
            switchModel(labelHead,Model.HEAD);
        }
        if(e.getSource() == labelHeadSide){
            switchModel(labelHeadSide,Model.HEADSIDE);
        }
        if(e.getSource() == labelTailSide){
            switchModel(labelTailSide,Model.TAILSIDE);
        }
        if(e.getSource() == nextButton){
            operateNextButton();
        }
        if(e.getSource() == previousButton){
            operatePreviouseButton();
        }
        if(e.getSource() == deleteMenuItem || e.getSource() == deleteButton){
            opDelete();
        }
        if(e.getSource() == saveMenuItem) {
            List<Coordinates> coordinatesList = annotationArea.getList();
            List<Lane> laneList = annotationArea.getLaneList();
            CoordiantesListUtil.saveList(coordinatesList,imageFile);
            CoordiantesListUtil.saveLaneList(laneList,imageFile);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"保存成功","提示",JOptionPane.INFORMATION_MESSAGE);

        }
        else if(e.getSource() == okButton || e.getSource() == xsTextField){
            String speed = xsTextField.getText();
            annotationArea.setxsLabel(Integer.parseInt(speed));
        }
        else if(e.getSource() == okButton || e.getSource() == xsTextField){
            String speed = xsTextField.getText();
            annotationArea.setxsLabel(Integer.parseInt(speed));
        }
        else if(e.getSource() == laneOKButton || e.getSource() == laneTextField){
            String label = laneTextField.getText();
            annotationArea.setLaneLabel(label);
        }

//        else if(e.getSource() == xsTextField){
//            String speed = xsTextField.getText();
//            annotationArea.setBbLabel(Integer.parseInt(speed));
//        }
        else if(e.getSource() == carTypeOkButton || e.getSource() == carTypeTextField){
            String label = carTypeTextField.getText();
            annotationArea.setCarLabel(Integer.parseInt(label));
        }
//        else if(e.getSource() == carTypeTextField){
//
//        }
        if(e.getSource() == openMenuItem){
            JFileChooser jFileChooser = new JFileChooser();
            int rval = jFileChooser.showOpenDialog(this);
            if(rval == JFileChooser.APPROVE_OPTION){
                imageDir = jFileChooser.getCurrentDirectory();
                imageFile = jFileChooser.getSelectedFile();

                File[] fileArrays = imageDir.listFiles();
                if(fileArrays != null) {
                    for (File file : fileArrays) {
                        if(file.isFile()) {
                            fileList.add(file);
                        }
                    }
                }

            }
            annotationArea.setImagePath(imageFile);
//            annotationArea.setIsRect(false);
            setTitle("AnnottionTool - "+imageFile.getName());
            List<Coordinates> coordinatesListExisted = new ArrayList<Coordinates>();
            List<Lane> laneList = new LinkedList<Lane>();
            if((coordinatesListExisted = CoordiantesListUtil.readCoordiantesList(imageFile))!=null){
                annotationArea.setList(coordinatesListExisted);

            }
            if((laneList = CoordiantesListUtil.readLaneList(imageFile))!=null){
                annotationArea.setLaneList(laneList);
            }
        }

    }
    private void switchModel(JButton button, Model model){
        if(jButton != null){
            jButton.setForeground(Color.BLACK);
        }
        jButton = button;
        jButton.setForeground(Color.ORANGE);
        annotationArea.setModel(model);

    }



    private void opAbout() {
        JOptionPane.showMessageDialog(null,"Ver: 3.0.0.0\nBy Liheng Wu\nForward&XJTU\nApr.9.2017","About",JOptionPane.INFORMATION_MESSAGE);
    }



    private void opDelete() {
        annotationArea.deleteLastCoordinates();
    }


    private void operatePreviouseButton(){
        xsTextField.setText("");
        carTypeTextField.setText("");
        laneTextField.setText("");
        if(jButton != null) {
            jButton.setForeground(Color.ORANGE);
        }
        List<Coordinates> coordinatesList = annotationArea.getList();
        List<Lane> laneList = annotationArea.getLaneList();
        imageFile = annotationArea.getImageFile();
        CoordiantesListUtil.saveList(coordinatesList,imageFile);
        CoordiantesListUtil.saveLaneList(laneList,imageFile);
        int fileIndex = fileList.indexOf(imageFile);
        if(fileIndex > 0){
            fileIndex --;
        }
        else if(fileIndex == 0){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"这是第一张图片","提示",JOptionPane.INFORMATION_MESSAGE);
        }
        if(fileIndex >= 0){
            CoordiantesListUtil.saveList(coordinatesList,imageFile);
            CoordiantesListUtil.saveLaneList(laneList,imageFile);
            imageFile = fileList.get((fileIndex));
            List<Coordinates> coordinatesListExisted = new ArrayList<Coordinates>();
            List<Lane>laneListExisted = new LinkedList<Lane>();
            annotationArea.setImagePath(imageFile);
            setTitle("Annotation Tool - " + imageFile.getName());
            if ((coordinatesListExisted = CoordiantesListUtil.readCoordiantesList(imageFile)) != null) {
                annotationArea.setList(coordinatesListExisted);
            }
            if((laneListExisted = CoordiantesListUtil.readLaneList(imageFile)) != null){
                annotationArea.setLaneList(laneListExisted);
            }
        }
    }
    private void operateNextButton(){
        xsTextField.setText("");
        carTypeTextField.setText("");
        laneTextField.setText("");
        if(jButton != null){
            jButton.setForeground(Color.ORANGE);
        }
        List<Coordinates> coordinatesList = annotationArea.getList();
        List<Lane> laneList = annotationArea.getLaneList();
        if(coordinatesList.size() >= 0) {
            CoordiantesListUtil.saveList(coordinatesList, imageFile);
        }
        if(laneList.size() >=0 ){
            CoordiantesListUtil.saveLaneList(laneList,imageFile);
        }
        int fileIndex = fileList.indexOf(imageFile);
        if(++fileIndex > fileList.size()-1){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"图片已标记完","提示",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        imageFile = fileList.get((fileIndex));
        List<Coordinates> coordinatesListExisted = new ArrayList<Coordinates>();
        annotationArea.setImagePath(imageFile);
        setTitle("Annotation Tool - "+imageFile.getName());
        List<Lane> laneListExisted = new LinkedList<Lane>();
        if((coordinatesListExisted = CoordiantesListUtil.readCoordiantesList(imageFile))!=null){
            annotationArea.setList(coordinatesListExisted);

        }
        if((laneListExisted = CoordiantesListUtil.readLaneList(imageFile))!=null){
            annotationArea.setLaneList(laneListExisted);
        }

    }
}






