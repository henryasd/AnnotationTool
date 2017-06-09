package cn.edu.xjtu.AnnotationTool.util;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henry on 2017/4/4.
 */
public class CoordiantesListUtil {
//    private List<Coordinates> coordinatesList;

    public static void saveList(List<Coordinates> coordinatesList, File imageFile){
//        System.out.println("coordinatesList:"+coordinatesList);
        String annotationPath = imageFile.getParent()+"\\annotation";
        File annotationDir = new File(annotationPath);
        if(!annotationDir.exists()){
            annotationDir.mkdir();
        }
        int indexOfPoint = imageFile.getName().indexOf(".");

        String  imageTxtName = imageFile.getName();
        String imageTxtPath = annotationPath+"\\"+imageTxtName+".txt";
        File imageTxtFile = new File(imageTxtPath);
        try {
            PrintWriter imageTxtFileWriter = new PrintWriter(imageTxtFile);
            for(Coordinates coordinates: coordinatesList){
                String coordinatesStr = coordinates.getPart()+" "+coordinates.getX1()+" "+coordinates.getY1()+" "+
                        coordinates.getX2()+" "+coordinates.getY2()+" "+
                        coordinates.getX3()+" "+coordinates.getY3()+" "+
                        coordinates.getX4()+" "+coordinates.getY4()+" "+coordinates.getLabel1()+" "+coordinates.getLabel2()+" "+coordinates.getLabel3();

                imageTxtFileWriter.println(coordinatesStr);
            }
            imageTxtFileWriter.flush();
            imageTxtFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public static List<Coordinates> readCoordiantesList(File imageFile){
        String annotationPath = imageFile.getParent()+"\\annotation";
        //int indexOfPoint = imageFile.getName().indexOf(".");
        String  imageName = imageFile.getName();
        File imageTxtFile = new File(annotationPath+"\\"+imageName+".txt");
        List<Coordinates> coordinatesList = new ArrayList<Coordinates>();
        if(imageTxtFile.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(imageTxtFile));

                String str = null;
                while((str = br.readLine()) != null) {
                    String[] coordinatesStr = str.split(" ");
                    Coordinates coordinates = new Coordinates();
                    coordinates.setPart(coordinatesStr[0]);
                    coordinates.setX1(Integer.parseInt(coordinatesStr[1]));
                    coordinates.setY1(Integer.parseInt(coordinatesStr[2]));
                    coordinates.setX2(Integer.parseInt(coordinatesStr[3]));
                    coordinates.setY2(Integer.parseInt(coordinatesStr[4]));
                    coordinates.setX3(Integer.parseInt(coordinatesStr[5]));
                    coordinates.setY3(Integer.parseInt(coordinatesStr[6]));
                    coordinates.setX4(Integer.parseInt(coordinatesStr[7]));
                    coordinates.setY4(Integer.parseInt(coordinatesStr[8]));
                    coordinates.setLabel1(Integer.parseInt(coordinatesStr[9]));
                    coordinates.setLabel2(Integer.parseInt(coordinatesStr[10]));
                    coordinates.setLabel3(Integer.parseInt(coordinatesStr[11]));
                    coordinatesList.add(coordinates);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return coordinatesList;
    }
    public static void saveLaneList(List<Lane> laneList, File imageFile){
//        System.out.println("coordinatesList:"+coordinatesList);
        String annotationPath = imageFile.getParent()+"\\LaneAnnotation";
        File annotationDir = new File(annotationPath);
        if(!annotationDir.exists()){
            annotationDir.mkdir();
        }
        int indexOfPoint = imageFile.getName().indexOf(".");

        String  imageTxtName = imageFile.getName();
        String imageTxtPath = annotationPath+"\\"+imageTxtName+".txt";
        File imageTxtFile = new File(imageTxtPath);
        try {
            PrintWriter imageTxtFileWriter = new PrintWriter(imageTxtFile);
            for(Lane lane: laneList){
                StringBuilder pointStr = new StringBuilder();
                for(Point2D point2D:lane.getPointList()){
                    pointStr.append((int)point2D.getX()).append(" ").append((int)point2D.getY()).append(" ");
                }
                String laneStr = lane.getObject()+" "+lane.getLabel()+" "+pointStr.toString()+lane.getLabel1()+" "+lane.getLabel2()+
                        " "+lane.getLabel3();
                imageTxtFileWriter.println(laneStr);
            }
            imageTxtFileWriter.flush();
            imageTxtFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public static List<Lane> readLaneList(File imageFile){
        String annotationPath = imageFile.getParent()+"\\LaneAnnotation";
        //int indexOfPoint = imageFile.getName().indexOf(".");
        String  imageName = imageFile.getName();
        File imageTxtFile = new File(annotationPath+"\\"+imageName+".txt");
        List<Lane> laneList = new LinkedList<Lane>();
        if(imageTxtFile.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(imageTxtFile));

                String str = "";
                while((str = br.readLine()) != null) {
                    String[] laneStr = str.split(" ");
                    Lane lane = new Lane();
                    lane.setObject(laneStr[0]);
                    lane.setLabel(Integer.parseInt(laneStr[1]));
                    List<Point2D> point2DList = new LinkedList<Point2D>();
                    for(int i = 2;i<laneStr.length-3; i=i+2){
                        point2DList.add(new Point(Integer.parseInt(laneStr[i]),Integer.parseInt(laneStr[i+1])));
                    }
                    lane.setPointList(point2DList);
                    laneList.add(lane);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return laneList;
    }
}
