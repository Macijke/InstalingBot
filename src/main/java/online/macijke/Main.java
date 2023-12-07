package online.macijke;

import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Main {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
    private static String fileNamed;
    private static String previousWord = "";
    private static int counter = 2;
    public void doAJob(Main main, Tesseract tesseract, Tesseract tesseractDE, Robot robot) throws Exception {
        String screenText;
        for (int i = 0; i < counter; i++) {
            File listOfWords = new File("D:\\slowkaBaza.txt");
            Map<String, String> mapWords = main.getWordsMap(listOfWords);
            Thread.sleep(1000);
            main.roboSS(680, 250, 600, 110);
            screenText = tesseract.doOCR(new File("C:\\BOT\\" + fileNamed + ".jpg")).trim();
            System.out.println(screenText);
            String word = mapWords.get(screenText);
            if (previousWord.equals(word)) {
                System.out.println("Słówko się powtarza kolejny raz!");
                System.exit(0);
            }
            if (word==null) {
                main.addWordToList(screenText, robot, main, tesseractDE);
                i--;
                continue;
            }
            StringSelection selection = new StringSelection(word);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            robot.mouseMove(935, 435);
            Thread.sleep(200);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(200);
            robot.keyPress(KeyEvent.VK_CONTROL);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_V);
            Thread.sleep(100);
            robot.keyRelease(KeyEvent.VK_V);
            Thread.sleep(100);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(200);
            robot.mouseMove(954, 546);
            Thread.sleep(200);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(800);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(250);
            if (new File("C:\\BOT\\" + fileNamed + ".jpg").delete()) System.out.println("Wyczyszczono pamięć!");
            previousWord = word;
        }
    }

    public void addWordToList(String screenText, Robot robot, Main main, Tesseract tesseractDE) throws Exception {
        new File("C:\\BOT\\" + fileNamed + ".jpg").delete();
        Thread.sleep(200);
        robot.mouseMove(954, 546);
        Thread.sleep(200);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(400);
        main.roboSS(680, 250, 600, 60);
        Thread.sleep(900);
        String value = tesseractDE.doOCR(new File("C:\\BOT\\" + fileNamed + ".jpg")).trim();
        appendToFile("D:\\slowkaBaza.txt", screenText, value);
        Thread.sleep(200);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(400);
    }
    public void roboSS(int x, int y, int width, int height) throws Exception {
        Calendar now = Calendar.getInstance();
        Robot robot = new Robot();
        //START: 680x
        //       250y
        //END:   1280x; +600
        //       360y;  +110
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(x,y,width,height));
        ImageIO.write(screenShot, "JPG", new File("C:\\BOT\\"+formatter.format(now.getTime())+".jpg"));
        fileNamed = formatter.format(now.getTime());
    }

    public Map<String, String> getWordsMap(File file) throws IOException {
        Map<String, String> stringMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] strings;
            strings = line.split("-");
            stringMap.put(strings[0], strings[1]);
        }
        return stringMap;
    }

    public void appendToFile(String fileName, String key, String value) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(key+"-"+value);
        bw.newLine();
        bw.close();
    }

    public static void main(String[] args) throws AWTException {
//        JFrame jFrame = new JFrame();
//        jFrame.setFont(new Font("Arial", Font.PLAIN, 35));
//        jFrame.setLayout(new FlowLayout());
//        jFrame.setBounds(0,0, 350, 425);
//        jFrame.setPreferredSize(new Dimension(350, 425));
//        jFrame.setVisible(true);
//        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Robot robot = new Robot();
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("pol");
        Tesseract tesseractDE = new Tesseract();
        tesseractDE.setLanguage("deu");
        Main main = new Main();

//        JLabel lInfo = new JLabel("F6 aby uruchomić bota.\n");
//        lInfo.setVisible(true);
//        lInfo.setBounds(0,0, 350, 50);
//        jFrame.add(lInfo);
//
//        JLabel lInfoStop = new JLabel("F7 aby zatrzymać bota.");
//        lInfoStop.setVisible(true);
//        lInfoStop.setBounds(0,200, 350, 50);
//        jFrame.add(lInfoStop);
        try {
            tesseract.setDatapath(".\\Tess4J\\tessdata");
            tesseractDE.setDatapath(".\\Tess4J\\tessdata");
            main.doAJob(main, tesseract, tesseractDE, robot);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
