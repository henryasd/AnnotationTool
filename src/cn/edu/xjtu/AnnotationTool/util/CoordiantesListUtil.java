package cn.edu.xjtu.AnnotationTool.util;

import java.io.*;
import java.util.ArrayList;
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
                        coordinates.getX4()+" "+coordinates.getY4();

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
                    coordinatesList.add(coordinates);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return coordinatesList;
    }
}
