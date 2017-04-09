import cn.edu.xjtu.AnnotationTool.util.CoordiantesListUtil;
import cn.edu.xjtu.AnnotationTool.util.Coordinates;

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
    private Image iconImage;
    private JButton jButton;
    private JMenu help;
    private JMenuItem aboutMenuItem;

    private Icon tailSideIcon, headSideIcon, deleteIcon,nextIcon, previousIcon, tailIcon, headIcon;

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

        labelTailSide = new JButton("Tail Side",tailSideIcon);
        labelHeadSide = new JButton("Head Side",headSideIcon);
        labelTail = new JButton("Tail",tailIcon);
        labelTail.setPreferredSize(new Dimension(80,30));
        labelHead = new JButton("Head", headIcon);
        labelHead.setPreferredSize(new Dimension(80,30));
        deleteButton = new JButton("Delete Last", deleteIcon);
        nextButton = new JButton("Next",nextIcon);
        nextButton.setPreferredSize(new Dimension(80,30));
        previousButton = new JButton("Previous",previousIcon);

        labelTailSide.setToolTipText("标注车尾和侧面，快捷键ALT + Q");
        labelHeadSide.setToolTipText("标标注车头和侧面，快捷键ALT + W");
        labelTail.setToolTipText("标注车尾，快捷键 ALT + E");
        labelHead.setToolTipText("标注车头，快捷键 ALT + R");
        deleteButton.setToolTipText("删除上一个标注, ALT + A");
        nextButton.setToolTipText("下一张图片, 快捷键 ALT + D");
        previousButton.setToolTipText("上一张图片, 快捷键 ALT + S");

        jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        jToolBar.add(labelTailSide);
        jToolBar.add(labelHeadSide);
        jToolBar.add(labelTail);
        jToolBar.add(labelHead);
        jToolBar.add(deleteButton);
        jToolBar.add(previousButton);
        jToolBar.add(nextButton);

        labelTail.addActionListener(this);
        labelHead.addActionListener(this);
        labelHeadSide.addActionListener(this);
        labelTailSide.addActionListener(this);
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

        labelTailSide.setMnemonic(KeyEvent.VK_Q);
        labelHeadSide.setMnemonic(KeyEvent.VK_W);
        labelTail.setMnemonic(KeyEvent.VK_E);
        labelHead.setMnemonic(KeyEvent.VK_R);
        deleteButton.setMnemonic(KeyEvent.VK_A);
        previousButton.setMnemonic(KeyEvent.VK_S);
        nextButton.setMnemonic(KeyEvent.VK_D);


        setJMenuBar(jmenuBar);

        setLayout(new BorderLayout());
        annotationArea = new AnnotationArea(this);
        JScrollPane jsp = new JScrollPane(annotationArea);
        add(jsp,BorderLayout.CENTER);
        add(jToolBar,BorderLayout.NORTH);

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
        if(e.getSource() == aboutMenuItem){
            System.out.println("about....");
            opAbout();
        }
        if(e.getSource() == labelTail){
            opLabelTail();
        }
        else if(e.getSource() == labelHead){
            opLabelHead();
        }
        if(e.getSource() == labelHeadSide){
            opLabelHeadSideButt();
        }
        if(e.getSource() == labelTailSide){
            opLabelTailSideButt();
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
            CoordiantesListUtil.saveList(coordinatesList,imageFile);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"保存成功","提示",JOptionPane.INFORMATION_MESSAGE);

        }
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
            annotationArea.setIsRect(false);
            setTitle("汽车标注工具 - "+imageFile.getName());
            List<Coordinates> coordinatesListExisted = new ArrayList<Coordinates>();
            if((coordinatesListExisted = CoordiantesListUtil.readCoordiantesList(imageFile))!=null){
                annotationArea.setList(coordinatesListExisted);

            }
        }

    }

    private void opAbout() {
        JOptionPane.showMessageDialog(null,"Ver: 1.0.0.0\nBy Liheng Wu\nAIAR, VCL\nApr.9.2017","About",JOptionPane.INFORMATION_MESSAGE);
    }



    private void opDelete() {
        annotationArea.deleteLastCoordinates();
    }

    private void opLabelHead(){
        if(jButton != null) {
            jButton.setForeground(Color.BLACK);
        }
        jButton = labelHead;
        if(jButton != null) {
            jButton.setForeground(Color.ORANGE);
        }
        annotationArea.setCarPart("head");
        annotationArea.setIsRect(true);
    }
    private void opLabelTail(){
        if(jButton != null) {
            jButton.setForeground(Color.BLACK);
        }
        jButton = labelTail;
        if(jButton != null) {
            jButton.setForeground(Color.ORANGE);
        }
        annotationArea.setCarPart("tail");
        annotationArea.setIsRect(true);
    }
    private void opLabelHeadSideButt(){
        if(jButton != null) {
            jButton.setForeground(Color.BLACK);
        }
        jButton = labelHeadSide;
        if(jButton != null) {
            jButton.setForeground(Color.ORANGE);
        }
        annotationArea.setIsRect(false);
        annotationArea.setCarPart("head");
    }
    private void opLabelTailSideButt(){
        if(jButton != null) {
            jButton.setForeground(Color.BLACK);
        }
        jButton = labelTailSide;
        if(jButton != null) {
            jButton.setForeground(Color.ORANGE);
        }
        annotationArea.setCarPart("tail");
        annotationArea.setIsRect(false);
    }
    private void operatePreviouseButton(){
        if(jButton != null) {
            jButton.setForeground(Color.ORANGE);
        }
        List<Coordinates> coordinatesList = annotationArea.getList();

        imageFile = annotationArea.getImageFile();
        CoordiantesListUtil.saveList(coordinatesList,imageFile);
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
            imageFile = fileList.get((fileIndex));
            List<Coordinates> coordinatesListExisted = new ArrayList<Coordinates>();
            annotationArea.setImagePath(imageFile);
            setTitle("汽车标注工具 - " + imageFile.getName());
            if ((coordinatesListExisted = CoordiantesListUtil.readCoordiantesList(imageFile)) != null) {
                annotationArea.setList(coordinatesListExisted);
            }
        }
    }
    private void operateNextButton(){
        if(jButton != null){
            jButton.setForeground(Color.ORANGE);
        }
        List<Coordinates> coordinatesList = annotationArea.getList();
        if(coordinatesList.size() > 0) {
            CoordiantesListUtil.saveList(coordinatesList, imageFile);
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
        setTitle("汽车标注工具 - "+imageFile.getName());
        if((coordinatesListExisted = CoordiantesListUtil.readCoordiantesList(imageFile))!=null){
            annotationArea.setList(coordinatesListExisted);

        }

    }
}






