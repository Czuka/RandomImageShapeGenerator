package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;

import java.awt.geom.AffineTransform;

public class RandomImageGenerator {

    String[] supported_extensions = new String[]{"jpg", "png", "gif"};


    public void startGenerator() {
        display("- - - - IMAGE DATASET GENERATOR - - - -\n");

        //Keep running the program until user exits the tool. This will allow to generate multiple datasets at once.
        while (true) {
            int approach;
            int no_of_files;
            int dataset_size;
            int exit_choice;
            long startTime = System.currentTimeMillis();


            Scanner scanner = new Scanner(System.in);

            display("\nEnter full path of folder in which you would like to generate dataset. "
                    + "\ne.g. D:\\100ImagesSet\\ \nPath:");

            String path = scanner.nextLine();

            display("\nSelect your approach:\n"
                    + "1: Generate by number of files\n"
                    + "2: Generate by size\n"
                    + "3: Generate by number of files & size"
                    + "\nEnter your choice (1, 2 or 3): ");

            approach = scanner.nextInt();

            if(approach == 3) {
                display("\nWhich extensions do you want?\n"
                        + "1: jpg\n"
                        + "2: png\n"
                        + "3: jpg & png\n"
                        + "Your choice: ");

            }
            else {
                display("\nWhich extensions do you want?\n"
                        + "1: jpg\n"
                        + "2: png\n"
                        + "3: jpg & png\n"
                        + "4: gif\n"
                        + "5: jpg, png, gif, jpeg (All formats)\n"
                        + "Your choice: ");
            }

            int extension_choice = scanner.nextInt();

            switch (extension_choice) {
                case 1:
                    supported_extensions = new String[]{"jpg"};
                    break;

                case 2:
                    supported_extensions = new String[]{"png"};
                    break;

                case 3:
                    supported_extensions = new String[]{"jpg", "png"};
                    break;

                case 4:
                    supported_extensions = new String[]{"gif"};
                    break;

                case 5:
                    supported_extensions = new String[]{"jpg", "png", "jpeg", "gif"};
                    break;

                default:
                    supported_extensions = new String[]{"jpg", "png", "jpeg", "gif"};
                    break;
            }

            System.out.println("Give a name of the file: ");
            String fileName = "";

            while (fileName.isEmpty()) {
                if (scanner.hasNextLine()) {
                    fileName = scanner.nextLine();
                } else {
                    // Handle the case where no input is available
                    // You can choose to display an error message or take any other appropriate action
                    System.out.println("No input found. Please try again.");
                }
            }

            display("\nSelect your shape :\n"
                    + "1: dot \n"
                    + "2: line \n"
                    + "3: sqare \n"
                    + "4: rectangle\n"
                    + "5: circle  \n"
                    + " \n"
                    + "\nEnter your choice (1, 2, 3 or 4): ");

            int type = scanner.nextInt();




            switch (approach) {
                case 1:
                    display("\nEnter number of files: ");
                    no_of_files = scanner.nextInt();
                    display("Your dataset is being generated at: " + path + "\nPlease wait. It may take a while...");
                    startTime = System.currentTimeMillis();
                    generateByNumberOfFiles(no_of_files, path,fileName, type);
                    break;

                case 2:
                    display("\nEnter size of dataset in MB: ");
                    dataset_size = scanner.nextInt();
                    startTime = System.currentTimeMillis();
                    display("Your dataset is being generated at: " + path + "\nPlease wait. It may take a while...");
                    generateBySize(dataset_size, path,fileName,type);
                    break;

                case 3:
                    display("\nEnter number of files: ");
                    no_of_files = scanner.nextInt();

                    display("\nEnter size of dataset in MB: ");
                    dataset_size = scanner.nextInt();

                    startTime = System.currentTimeMillis();
                    display("Your dataset is being generated at: " + path + "\nPlease wait. It may take a while...");
                    generateBySizeCount(dataset_size, no_of_files, path,fileName, type);
                    break;
            }




            display("\n- - - - SUCCESS - - - -\n"
                    + "\nTime taken (secs): " + (System.currentTimeMillis() - startTime) / 1000 + "\nPress 1 to generate another dataset. Any other key to exit.\nEnter: ");

            exit_choice = scanner.nextInt();

            if (exit_choice == 1) {
                continue;
            } else {
                break;
            }
        }
    }

    private void generateByNumberOfFiles(int no_of_files, String path,String fileName,int type) {
        //Random rand = new Random();

        while (no_of_files > 0) {
            width = 1000;   //rand.nextInt(2000) + 100;
            height = 1000;  //rand.nextInt(2000) + 100;
            generateImage(path,fileName, type);
            no_of_files--;
        }
    }

    private void generateBySize(int dataset_size_mb, String path,String fileName, int type) {
        long generated_size_bytes;
        long dataset_size_bytes = dataset_size_mb * 1024L * 1024L;
        Random rand = new Random();

        while (dataset_size_bytes > 0) {
            width = rand.nextInt(2000) + 100;
            height = rand.nextInt(2000) + 100;
            generated_size_bytes = generateImage(path, fileName, type);
            dataset_size_bytes = dataset_size_bytes - generated_size_bytes;
        }
    }

    private void generateBySizeCount(int dataset_size_mb_in, int no_of_files_in, String path_in,String fileName, int type) {

        if(supported_extensions.length == 1) {
            if(supported_extensions[0].equals("jpg")) {
                int individual_size_kb = (int)((float)dataset_size_mb_in / no_of_files_in) * 1000;
                width = height = jpgHashmap.get(individual_size_kb);
                runLoop(no_of_files_in, path_in, fileName, type);
            }
            else if(supported_extensions[0].equals("png")){
                int individual_size_kb = (int)((float)dataset_size_mb_in / no_of_files_in) * 1000;
                width = height = pngHashmap.get(individual_size_kb);
                runLoop(no_of_files_in, path_in, fileName, type);
            }
        }
        else {
            int part1 = no_of_files_in/2;
            int part2 = no_of_files_in - part1;

            int individual_size_kb = (int)((float)(dataset_size_mb_in/2) / part1) * 1000;
            width = height = jpgHashmap.get(individual_size_kb);
            supported_extensions = new String[]{"jpg"};
            runLoop(part1, path_in,fileName, type);

            individual_size_kb = (int)((float)(dataset_size_mb_in/2) / part2) * 1000;
            width = height = pngHashmap.get(individual_size_kb);
            supported_extensions = new String[]{"png"};
            runLoop(part2, path_in,fileName, type);
        }


    }

    private void runLoop(int no_of_files_in, String path_in,String fileName, int type) {
        while(no_of_files_in > 0) {
            generateImage(path_in,fileName, type);
            no_of_files_in--;
        }
    }

    //Mapping between image size and pixels needed. Don't ask me how did I get these values! ;)
    HashMap<Integer, Integer> jpgHashmap = new HashMap<Integer, Integer>() {
        {
            put(1000, 1000);

//            put(100, 290);
//            put(200, 415);
//            put(500, 660);
//            put(2000, 1325);
//            put(3000, 1625);
//            put(4000, 1880);
//            put(5000, 2100);
//            put(10000, 2970);
//            put(20000, 4205);
        }
    };

    //Mapping between image size and pixels needed. Don't ask me how did I get these values! ;)
    HashMap<Integer, Integer> pngHashmap = new HashMap<Integer, Integer>() {
        {
            put(1000, 1000);

//            put(100, 160);
//            put(200, 225);
//            put(500, 355);
//            put(2000, 705);
//            put(3000, 875);
//            put(4000, 1015);
//            put(5000, 1135);
//            put(10000, 1595);
//            put(20000, 2265);
        }
    };

    int width = 1000;
    int height = 1000;

    int yShape;
    int xShape;
    int yLocation ;
    int xLocation ;


    private long generateImage(String path_name, String fileName,int type) {
        Random rand = new Random();
        String extension = supported_extensions[rand.nextInt(supported_extensions.length)];

        long timeTracker = System.currentTimeMillis();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        File f = null;
        // setting all RGBA to 255 gives us white background
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int a = ( 255); //alpha
                int r = ( 255); //red
                int g = ( 255); //green
                int b = ( 255); //blue

                int p = (a << 24) | (r << 16) | (g << 8) | b; //pixel

                img.setRGB(x, y, p);
            }
        }



        switch (type){
            case 1:
                yShape = ((int)(Math.random() *(15))+15);
                img = drawWheel(img , yShape, -1);
                break;
            case 2:
                int line = ((int)(Math.random() *2));
                switch (line){
                    case 0:
                        yShape = ((int)(Math.random() *(40-19))+19);
                        xShape = ((int)(Math.random() *(width/4-100))+100);
                        img = drawRect(img, yShape, xShape);
                        break;
                    case 1:
                        yShape = ((int)(Math.random() *(height/4-100))+100);
                        xShape = ((int)(Math.random() *(40-19))+19);
                        img = drawRect(img, yShape, xShape);
                        break;
                }
                break;
            case 3:
                yShape = ((int)(Math.random() *(width/4)-40)+40);
                xShape = yShape;
                img = drawRect(img, yShape, xShape);
                break;
            case 4:
                yShape = ((int)(Math.random() *(width/4)-40)+40);
                xShape = ((int)(Math.random() *(height/4-40))+40);
                img = drawRect(img, yShape, xShape);
                break;
            case 5:
                yShape = ((int)(Math.random() *(200))+50);
                xShape = ((int)(Math.random() *(yShape)));
                img = drawWheel(img , yShape, xShape);
                break;
        }



        //  drawing rectangle shapes



        try {
            if (!(new File(path_name).exists())) {
                new File(path_name).mkdir();
            }


            f = new File(path_name + File.separator
                    + generateName(fileName) + "." + extension);
            ImageIO.write(img, extension, f);

            display("Generated file: " + f.getAbsolutePath() + ""
                    + "\nSize (bytes): " + f.length() + "\tTime (secs): " + (System.currentTimeMillis() - timeTracker)/1000 + "\tDimensions: " + height + " x " + width );

            dimensionEngine(width, height, f.length());

            return f.length();
        } catch (IOException e) {
            display("Error while writing to file: " + e, true);
            return -1;
        }
    }

    int jumpInPixels[] = new int[] {100, 50, 25, 10, 5};

    public void dimensionEngine(int widthIn, int heightEngineIn, long sizeInBytes) {
        //Not implemented
    }

    //Always generate unique name
    private Set<String> identifiers = new HashSet<>();

    public int index =0;


    private String generateName(String fileName) {


        String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz12345674890";

        index = 1+index ;

        StringBuilder builder = new StringBuilder();

        while (builder.toString().length() == 0) {

            for (int i = 0; i < fileName.length(); i++) {
                builder.append(fileName.charAt(i));
            }
            int j;
            if (index<10){
                j=3;
            } else if (index<100) {
                j=2;
            }else if (index<1000) {
                j=1;
            }else {
                j=0;
            }
            for (int k = 0; k < j; k++){
                builder.append('0');
            }
            String stringIndex = Integer.toString(index);
            for (int k = 0; k < stringIndex.length(); k++){
                builder.append(stringIndex.charAt(k));
            }

            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }

        return builder.toString();
    }

    private void display(String msg) {
        System.out.println(msg);
    }

    private void display(String msg, boolean exit) {
        System.out.println(msg);
        if (exit) {
            System.exit(0);
        }
    }

    //Driver function
    public static void main(String[] args) {
        RandomImageGenerator RMI = new RandomImageGenerator();
        RMI.startGenerator();
    }



    public BufferedImage drawRect(BufferedImage img,int yShape,int xShape){

        yLocation = (int)(Math.random() *(height-yShape));
        xLocation = (int)(Math.random() *(width-xShape));

        for (int y = yLocation; y < yShape; y++) {
            for (int x = xLocation; x < xLocation+xShape; x++) {
                int a = ( 1); //alpha
                int r = ( 1); //red
                int g = ( 1); //green
                int b = ( 1); //blue

                int p = (a << 24) | (r << 16) | (g << 8) | b; //pixel

                img.setRGB(x, y, p);
            }
        }
        return img;
    }

    public BufferedImage drawWheel(BufferedImage img, int radiusOutside, int radiusInside){

        yLocation = ((int)(Math.random() *(height-radiusOutside/2))+radiusOutside/2);
        xLocation = ((int)(Math.random() *(width-radiusOutside/2))+radiusOutside/2);



        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int distanceX = x - yLocation;
                int distanceY = y - xLocation;
                double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);


                if (distance < radiusOutside && distance > radiusInside) {
                    int a = ( 1); //alpha
                    int r = ( 1); //red
                    int g = ( 1); //green
                    int b = ( 1); //blue

                    int p = (a << 24) | (r << 16) | (g << 8) | b; //pixel
                    img.setRGB(x, y, p);
                }
            }
        }
        return img;
    }



    public BufferedImage drawAngleRect(BufferedImage img, int yShape, int xShape, double angle) {

        int yLocation = (int) (Math.random() * (height - yShape));
        int xLocation = (int) (Math.random() * (width - xShape));

        double radianAngle = Math.toRadians(angle);

        AffineTransform transform = new AffineTransform();
        transform.translate(xLocation, yLocation);
        transform.rotate(radianAngle);

        for (int y = 0; y < yShape; y++) {
            for (int x = 0; x < xShape; x++) {
                double transformedX = transform.getScaleX() * x + transform.getShearX() * y + transform.getTranslateX();
                double transformedY = transform.getShearY() * x + transform.getScaleY() * y + transform.getTranslateY();

                int pixelX = (int) Math.round(transformedX);
                int pixelY = (int) Math.round(transformedY);

                if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
                    int a = 255; // alpha
                    int r = 1; // red
                    int g = 1; // green
                    int b = 1; // blue

                    int p = (a << 24) | (r << 16) | (g << 8) | b; // pixel

                    img.setRGB(pixelX, pixelY, p);
                }
            }
        }

        return img;
    }



    public BufferedImage drawRect(BufferedImage img, int yShape, int xShape, double angle) {
        int height = img.getHeight();
        int width = img.getWidth();

        int yLocation = (int) (Math.random() * (height - yShape));
        int xLocation = (int) (Math.random() * (width - xShape));

        double radianAngle = Math.toRadians(angle);

        AffineTransform transform = new AffineTransform();
        transform.translate(xLocation, yLocation);
        transform.rotate(radianAngle);

        for (int y = 0; y < yShape; y++) {
            for (int x = 0; x < xShape; x++) {
                double transformedX = transform.getScaleX() * x + transform.getShearX() * y + transform.getTranslateX();
                double transformedY = transform.getShearY() * x + transform.getScaleY() * y + transform.getTranslateY();

                int pixelX = (int) Math.round(transformedX);
                int pixelY = (int) Math.round(transformedY);

                if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
                    int a = 255; // alpha
                    int r = 1; // red
                    int g = 1; // green
                    int b = 1; // blue

                    int p = (a << 24) | (r << 16) | (g << 8) | b; // pixel

                    img.setRGB(pixelX, pixelY, p);
                }
            }
        }
        return img;
    }
}