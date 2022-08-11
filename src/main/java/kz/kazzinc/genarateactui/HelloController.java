package kz.kazzinc.genarateactui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController  implements Initializable {

    @FXML
    private ComboBox findArea;
    @FXML
    private TextField NumAct;
    @FXML
    private TextField imei1;
    @FXML
    private TextField imei2;
    @FXML
    private TextField EmplData;
    @FXML
    private Button click;
    @FXML
    private Tab tanOne;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        findArea.getItems().setAll("ПГУ ОР","ПУ БЗР", "ПГУ ГПР", "ПГУ БВР", "ПГУ ГКР", "ПУ СМР", "ПУ ВШТ", "ПУ СХО","ПУ ШСО");
        findArea.getSelectionModel().selectFirst();
        NumAct.setPromptText("Введите номер акта"); //to set the hint text
        NumAct.getParent().requestFocus(); //to not setting the focus on that node so that the hint will display immediately

        findArea.setOnMouseEntered(event -> {
            findArea.setStyle("-fx-background-color: #a1a1a1");
        });
        findArea.setOnMouseExited(event -> findArea.setStyle("-fx-background-color: #cfcfcf"));

        click.setOnMouseEntered(event -> {
            click.setStyle("-fx-background-color: #a1a1a1");
        });
        click.setOnMouseExited(event -> click.setStyle("-fx-background-color: #cfcfcf"));
    }

    public void findAreaClick(ActionEvent actionEvent) {
        //String selectedValue = (String) findArea.getSelectionModel().getSelectedItem();
    }
    public void Click(ActionEvent actionEvent) {
        String area;
        String empl;

        String selectedValue = (String) findArea.getSelectionModel().getSelectedItem();
        area = selectedValue;

        empl=(EmplData.getText().equals("")) ? "Вы не ввели данные о человеке" : EmplData.getText();

        if(area==null){
            area="Вы не выбрали участок";
        }
        else if(area.equals("ПГУ БВР")){
            area="Акулов А.А.– Начальник ПГУ БВР.\n";
        }
        else if(area.equals("ПГУ ОР")){
            area="Азмун Р.К.– Начальник ПГУ ОР.\n";
        }
        else if(area.equals("ПУ БЗР")){
            area="Азмун Р.К.– Начальник ПГУ БЗР.\n";
        }
        else if(area.equals("ПУ ШПиШВ")){
            area="Татыев А.Н.– Начальник ПУ ШПиШВ.\n";
        }
        else if(area.equals("ПУ ВШТ")){
            area="Татыев А.Н.– Начальник ПУ ВШТ.\n";
        }
        else if(area.equals("ПГУ ГКР")){
            area="Скрипченко С.В.– Начальник ПГУ ГКР.\n";
        }
        else if(area.equals("ПГУ ГПР")){
            area="Заруднев Д.А.– Начальник ПГУ ГПР МР.\n";
        }
        else if(area.equals("ПУ СМР")){
            area="Томилов А.А.– Начальник ПУ СМР.\n";
        }
        else if(area.equals("ПУ СХО")){
            area="Королев А.С.– Начальник ПУ СХО.\n";
        }
        else if(area.equals("ПУ ШСО")){
            area="Татыев Т.Н.–  Начальник ПУ ШСО.\n";
        }

        generateAct(area, NumAct.getText(),empl, imei1.getText(), imei2.getText());

    }

    private void generateAct(String area, String numact, String EmplData, String imei1, String imei2){

        try {
            XWPFDocument doc = new XWPFDocument(
                    OPCPackage.open("src/outAct.docx"));
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);

                        if (text != null && text.contains("date")) {
                            text = text.replace("date", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));//your content
                            r.setText(text, 0);
                        }

                        if (text != null && text.contains("numact")) {
                            text = text.replace("numact", numact);//your content
                            r.setText(text, 0);
                        }

                        if (text != null && text.contains("boss")) {
                            text = text.replace("boss", area);//your content
                            r.setText(text, 0);
                        }

                        if (text != null && text.contains("nach")) {
                            String[] nach = area.split(" ");
                            text = text.replace("nach", nach[1].split("\\.")[0]+"."+nach[1].split("\\.")[1]+" "+nach[0]);//your content
                            r.setText(text, 0);
                        }

                        if (text != null && text.contains("work")) {
                            text = text.replace("work", EmplData);//your content
                            r.setText(text, 0);
                        }

                        if (text != null && text.contains("one")) {
                            text = text.replace("one", imei1);//your content
                            r.setText(text, 0);
                        }

                        if (text != null && text.contains("two")) {
                            text = text.replace("two", imei2);//your content
                            r.setText(text, 0);
                        }

                        if (text != null && text.contains("empl")) {
                            String[] Empl = EmplData.split(" ");
                            text = text.replace("empl", Empl[1]+" "+Empl[0]);//your content
                            r.setText(text, 0);
                        }
                    }
                }
            }

            File file = new File("акт выдачи.docx");

            FileOutputStream outputStream = new FileOutputStream(file);
            doc.write(outputStream);
            outputStream.close();

            modalWin(file);

        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    protected void modalWin(File file){
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Файл сгенерирован");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setWidth(300);
        dialogStage.setHeight(300);
        dialogStage.getIcons().add(new Image("file:src/icon.png"));

        Text textTitle = new Text();
        textTitle.setText("Выберите что сделать с файлом");
        textTitle.setStyle("-fx-font-size:18");

        Button openFile = new Button();
        openFile.setText("Открыть файл");
        openFile.setPadding(new Insets(10,10,10,10));
        openFile.setStyle("-fx-font-size: 14");

        Label label = new Label();
        Label label1 = new Label();

        Button openDir = new Button();
        openDir.setText("Открыть расположение файла");
        openDir.setPadding(new Insets(10,10,10,10));
        openDir.setStyle("-fx-font-size: 14");
        openDir.setLayoutX(250);

        VBox vbox = new VBox(textTitle,label1, openFile,label,openDir);
        vbox.setAlignment(Pos.CENTER);

        dialogStage.setScene(new Scene(vbox));
        dialogStage.show();

        openFile.setOnAction(actionEvent -> {
            try {
                Runtime.getRuntime().exec("powershell.exe -Command \"start '"+file.getAbsolutePath()+"'");
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        openDir.setOnAction(actionEvent -> {
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + file.getAbsolutePath());
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}