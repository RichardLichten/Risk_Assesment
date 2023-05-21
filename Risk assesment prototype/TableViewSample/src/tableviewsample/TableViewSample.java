import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class TableViewSample extends Application {
 
    private TableView<Risk> table = new TableView<Risk>();
    
    private final ObservableList<Risk> data =
            FXCollections.observableArrayList(
            new Risk("Hydraulics", "Maintenance", "Penetrating media", "Pray", "1", "0", "4","No"),
            new Risk("Laser source", "Operation", "Eye damage", "Pray", "3", "2","4","No"),
            new Risk("Spindle", "Operation", "Risk of retraction", "Pray", "4", "1","4","Yes"),
            new Risk("Live parts", "All Stages", "Electrocution", "Pray", "5", "3","4", "No"),
            new Risk("Spindle", "Maintenance", "Pinch hazard", "Pray", "6","2","4","No"));
    final HBox hb = new HBox();
    
    private TableView<Metadata> tableb = new TableView<Metadata>();
    
    private final ObservableList<Metadata> datab =
            FXCollections.observableArrayList(
            new Metadata("Project", "Turning Machine"),
            new Metadata("Project Number", "L51345"),
            new Metadata("Machine Type", "Machine"),
            new Metadata("Intended Use", "The machine is approved for turning and machining of workpieces with the diameter of max. 200 mm."),
            new Metadata("Misuse", "The machine is approved for turning and machining of workpieces with the diameter of max. 200 mm."), 
            new Metadata("Machine Limits", "3m * 2m *2.50m, 10 years"),
            new Metadata("Created by", "Alfred Schneider"),
            new Metadata("Created at", "11.2.2022")  
            );
    final HBox hc = new HBox();
    final FileChooser fileChooser = new FileChooser();


    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Risk Assessment");
        stage.setWidth(1920);
        stage.setHeight(1080);
        BorderPane borderPane = new BorderPane();
        
        final Label label = new Label("Risk Assessment");
        label.setFont(new Font("Arial", 14));
        
        final Label labelB = new Label("Add New Risk");
        labelB.setFont(new Font("Arial", 14));
        
        final Label labelC = new Label("Intended Use, Machine Limits and Metadata");
        labelC.setFont(new Font("Arial", 14));
        
        //Browser
        
        Button buttonURL = new Button("DIN NORM RESEARCH");
        Button buttonURLb = new Button("Machine Directive 2006");
        Button buttonURLc = new Button("Low Voltage Directive");
        Button buttonURLd = new Button("EMC Directive");

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        browser.setPrefSize(1500, 2000);
        buttonURL.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String url = "http://www.beuth.de";
                // Load a page from remote url.
                webEngine.load(url);
            }
        });

        buttonURLb.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String urlb = "https://eur-lex.europa.eu/eli/dir/2006/42/2019-07-26";
                // Load HTML String
                webEngine.load(urlb);
            }
        });

        buttonURLc.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String urlb = "https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=CELEX%3A32014L0035";
                // Load HTML String
                webEngine.load(urlb);
            }
        });
        
                buttonURLd.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String urlb = "https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=CELEX:32014L0030";
                // Load HTML String
                webEngine.load(urlb);
            }
        });
        
        
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
        
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
      
        
        // Create MenuItems
        MenuItem menuLoadPicturea = new MenuItem("Load Picture Small 75*55");
        MenuItem menuLoadPictureb = new MenuItem("Load Picture Small 55*75");
        MenuItem menuLoadPicturec = new MenuItem("Load Picture Large 145*107");
        MenuItem menuSavePicture = new MenuItem("Save Picture");
        MenuItem exitItem = new MenuItem("Exit");
        
        MenuItem menuResetPicture = new MenuItem("Clear Picture");
        MenuItem menuResetCounter = new MenuItem("Reset Counter");
        MenuItem menuCounterUp = new MenuItem("Counter +");
        MenuItem menuCounterDown = new MenuItem("Counter -");
        
        MenuItem helpBox = new MenuItem("Help");
               
        // Add menuItems to the Menus
        fileMenu.getItems().addAll(menuLoadPicturea, menuLoadPictureb, menuLoadPicturec,menuSavePicture,exitItem);
        editMenu.getItems().addAll(menuResetPicture, menuResetCounter,menuCounterUp,menuCounterDown);
        helpMenu.getItems().addAll(helpBox);
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu,helpMenu);
        
 /////////////Tabelle Risk
        table.setEditable(true);
        table.setMinHeight(700);
        Callback<TableColumn, TableCell> cellFactory =
             new Callback<TableColumn, TableCell>() {
                 public TableCell call(TableColumn p) {
                    return new EditingCell();
                 }
             };
 
        TableColumn hazardCol = new TableColumn("Hazard Area");
        hazardCol.setMinWidth(100);
        hazardCol.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("area"));
        hazardCol.setCellFactory(cellFactory);
        hazardCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setArea(t.getNewValue());
                }
             }
        );
 
        
        TableColumn riskCol = new TableColumn("Machine Risk");
        riskCol.setMinWidth(200);
        riskCol.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("risk"));
        riskCol.setCellFactory(cellFactory);
        riskCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setRisk(t.getNewValue());
                }
             }
        );
 

 
        TableColumn liveStageCol = new TableColumn("Stage");
        liveStageCol.setMinWidth(100);
        liveStageCol.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("stage"));
        liveStageCol.setCellFactory(cellFactory);
        liveStageCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setStage(t.getNewValue());
                }
            }
        );
 
        TableColumn riskReductionCol = new TableColumn("Risk Reduction Measures, Directives and Norms");
        riskReductionCol.setMinWidth(900);
        riskReductionCol.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("measure"));
        riskReductionCol.setCellFactory(cellFactory);
        riskReductionCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setMeasure(t.getNewValue());
                }
            }
        );
 
        TableColumn riskIn = new TableColumn("Risk In");
        riskIn.setMinWidth(100);
        riskIn.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("riskin"));
        riskIn.setCellFactory(cellFactory);
        riskIn.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setRiskin(t.getNewValue());
                }
            }
        );
        
        TableColumn riskOut = new TableColumn("Risk Out");
        riskOut.setMinWidth(100);
        riskOut.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("riskout"));
        riskOut.setCellFactory(cellFactory);
        riskOut.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setRiskout(t.getNewValue());
                }
            }
        );
        
        TableColumn riskPL = new TableColumn("PL");
        riskPL.setMinWidth(100);
        riskPL.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("riskpl"));
        riskPL.setCellFactory(cellFactory);
        riskPL.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setRiskpl(t.getNewValue());
                }
            }
        );
        
        TableColumn riskMinimized = new TableColumn("Risk minimized?");
        riskMinimized.setMinWidth(200);
        riskMinimized.setCellValueFactory(
            new PropertyValueFactory<Risk, String>("riskminimized"));
        riskMinimized.setCellFactory(cellFactory);
        riskMinimized.setOnEditCommit(
            new EventHandler<CellEditEvent<Risk, String>>() {
                @Override
                public void handle(CellEditEvent<Risk, String> t) {
                    ((Risk) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setRiskminimized(t.getNewValue());
                }
            }
        );
        
        
        table.setItems(data);
        table.getColumns().addAll(hazardCol,liveStageCol, riskCol,  riskIn,  riskReductionCol, riskOut,riskPL,riskMinimized);
 
       
        
        final TextField addArea = new TextField();
        addArea.setPromptText("Area");
        addArea.setMaxWidth(100);
        
        final TextField addRisk = new TextField();
        addRisk.setPromptText("Risk");
        addRisk.setMinWidth(200);
        
        final TextField addStage = new TextField();
        addStage.setMaxWidth(100);
        addStage.setPromptText("Stage");
       
        final TextArea addMeasure = new TextArea();
        addMeasure.setMaxWidth(900);
        addMeasure.setPromptText("Measure(s)\rNorms");
        //addMeasure.appendText("\nNorms");
        addMeasure.setPrefRowCount(10);
        addMeasure.setPrefColumnCount(100);
        addMeasure.setWrapText(true);
     
        
        final TextField addRiskIn = new TextField();
        addRiskIn.setMaxWidth(100);
        addRiskIn.setPromptText("RiskIn");
        final TextField addRiskOut = new TextField();
        addRiskOut.setMaxWidth(riskReductionCol.getPrefWidth());
        addRiskOut.setPromptText("RiskOut");
        final TextField addRiskPL = new TextField();
        addRiskPL.setMaxWidth(riskReductionCol.getPrefWidth());
        addRiskPL.setPromptText("RiskPL");
        final TextField addRiskMinimized = new TextField();
        addRiskMinimized.setMaxWidth(riskReductionCol.getPrefWidth());
        addRiskMinimized.setPromptText("RiskPL");
 
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Risk(
                        addArea.getText(),
                        addRisk.getText(),
                        addStage.getText(),
                        addMeasure.getText().replaceAll("(\r\n|\n)", "*"),
                        addRiskIn.getText(),
                        addRiskOut.getText(),
                        addRiskPL.getText(),
                        addRiskMinimized.getText()));
                addArea.clear();
                addRisk.clear();
                addStage.clear();
                addMeasure.clear();
                addRiskIn.clear();
                addRiskOut.clear();
                addRiskPL.clear();
                addRiskMinimized.clear();
            }
        });
 
        final Button pdfButton = new Button("Export to pdf");
        pdfButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                        //
           try {
      com.lowagie.text.Font font = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 9, com.lowagie.text.Font.BOLDITALIC);
      com.lowagie.text.Font fonta = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 9, com.lowagie.text.Font.NORMAL);
      Document doc = new Document(PageSize.A4.rotate());
      fileChooser.setTitle("Export to pdf");
      fileChooser.getExtensionFilters().clear();
      fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );
      
      File file = fileChooser.showSaveDialog(stage);
      PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
      
      //Kommentare
      
      Paragraph para = new Paragraph("Risk Assesment", font);
      Paragraph leer = new Paragraph("", font);
      //Metadata
      PdfPTable pdftablea = new PdfPTable(2);
      pdftablea.setWidthPercentage(100);
      
      // setting column widths
      pdftablea.setWidths(new float[] {6.0f, 20.0f});
      PdfPCell cellb = new PdfPCell();
      
      for( int i = 0; i < tableb.getItems().size(); i ++ ){
      for( int j = 0; j < tableb.getColumns().size(); j ++ ){
      String cd=tableb.getColumns().get(j).getCellObservableValue(i).getValue().toString();
       cellb.setPhrase(new Phrase(cd, fonta));
            
      pdftablea.addCell(cellb);}}
       ////Schluss nötig
       cellb.setPhrase(new Phrase("", font));
      pdftablea.addCell("");

      
      //RR Risk Table
      PdfPTable pdftableb = new PdfPTable(8);
      pdftableb.setWidthPercentage(105);
      
      // setting column widths
      pdftableb.setWidths(new float[] {4.0f, 4.0f, 4.0f, 2.0f,18.0f, 2.0f, 2.0f, 3.0f});
      PdfPCell cell = new PdfPCell();
      // table headers
      cell.setPhrase(new Phrase("Hazard Area", font));
      pdftableb.addCell(cell);
      cell.setPhrase(new Phrase("Stage", font));
      pdftableb.addCell(cell);
      cell.setPhrase(new Phrase("Machine Risk", font));
      pdftableb.addCell(cell);
      cell.setPhrase(new Phrase("Risk In", font));
      pdftableb.addCell(cell);
      cell.setPhrase(new Phrase("Risk Reduction Measures and Norms", font));
      pdftableb.addCell(cell);
      cell.setPhrase(new Phrase("Risk Out", font));
      pdftableb.addCell(cell);
      cell.setPhrase(new Phrase("PL", font));
      pdftableb.addCell(cell);
      cell.setPhrase(new Phrase("Minimized", font));
      pdftableb.addCell(cell);
      for( int i = 0; i < table.getItems().size(); i ++ ){
      for( int j = 0; j < table.getColumns().size(); j ++ ){
      String cd=table.getColumns().get(j).getCellObservableValue(i).getValue().toString();
       cell.setPhrase(new Phrase(cd, fonta));
            
      pdftableb.addCell(cell);}}
       ////Schluss nötig
       cell.setPhrase(new Phrase("DOBuz", font));
      pdftableb.addCell("DOBuz");
      pdftableb.addCell("DOBuz");
      pdftableb.addCell("DOBuz");
      pdftableb.addCell("DOBuz");
    
      System.out.println("und");
      System.out.println(table.getItems().size());
      System.out.println(table.getColumns().size());
      
      
      // adding table to document
      doc.open();
      //HeaderFooter header = new HeaderFooter(new Phrase("Turning Machine P200X"), false); string S= writer.getPageNumber().toString;
    
      //HeaderFooter footer = new HeaderFooter(new Phrase("vvv",fonta)), false);
      HeaderFooter footer = new HeaderFooter(
      new Phrase("Risk Assessment: "+" pg.", fonta),true);
  
      footer.setAlignment(Element.ALIGN_RIGHT);
      footer.setBorder(0);
     
      //doc.setHeader(header);
      doc.setFooter(footer);
      doc.add(para);
      pdftablea.setSpacingBefore(10);
      doc.add(pdftablea);
      ///RB
      doc.newPage();
      pdftableb.setSpacingBefore(10);
      doc.add(pdftableb);

      doc.close();
      writer.close();
      System.out.println("PDF using OpenPDF created successfully");
    } catch (DocumentException | FileNotFoundException r) {
      // TODO Auto-generated catch block
      r.printStackTrace();
    }
    }            
                        
          
        });
 
        final Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
             System.out.println("Did soemthing");
             fileChooser.setTitle("Save File as");
             fileChooser.getExtensionFilters().clear();
             fileChooser.getExtensionFilters().addAll(
             new FileChooser.ExtensionFilter("PRA", "*.pra")
            );
      
            File file = fileChooser.showSaveDialog(stage);
            
                try {
                 
                FileWriter writer = new FileWriter(file);
                for (Risk risk : data) {
            writer.write(risk.getStage() +  "," +risk.getArea()+"," +risk.getRisk()+"," +risk.getMeasure()+"," +risk.getRiskin()+"," +risk.getRiskout()+"," +risk.getRiskpl()+"," +risk.getRiskminimized()+"\r");
        }
        writer.close();
           


    } catch (FileNotFoundException xy) {
        xy.printStackTrace();
    } catch (IOException xy) {
        xy.printStackTrace();
    }
              
                             try {
                 
                FileWriter writerb = new FileWriter(file+"m");
                for (Metadata metadata : datab) {
            writerb.write(metadata.getDataname() +  "," +metadata.getDatatext()+"\r");
        }
        writerb.close();
           


    } catch (FileNotFoundException xy) {
        xy.printStackTrace();
    } catch (IOException xy) {
        xy.printStackTrace();
    } 
                
                

                
            }
        });
        
        final Button loadButton = new Button("Load");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Did soemthing");
                fileChooser.setTitle("Load Project");
                fileChooser.getExtensionFilters().clear();
                fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PRA", "*.pra")
                );
      
                File file = fileChooser.showOpenDialog(stage);
                
                data.clear();
                datab.clear();
                                try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                //BufferedReader objReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                String [] names = line.split(",");

            // Add the student to the list
            
            data.add(new Risk(names[0], names[1],names[2],names[3],names[4],names[5],names[6],names[7]));
            
        }
        table.setItems(data);
    } catch (FileNotFoundException xy) {
        xy.printStackTrace();
    } catch (IOException xy) {
        xy.printStackTrace();
    }
                
                                try {
                BufferedReader reader = new BufferedReader(new FileReader(file+"m"));
                String line;
                while ((line = reader.readLine()) != null) {
                String [] names = line.split(",");

            // Add the student to the list
            
            datab.add(new Metadata(names[0], names[1]));
            
        }
        tableb.setItems(datab);
    } catch (FileNotFoundException xy) {
        xy.printStackTrace();
    } catch (IOException xy) {
        xy.printStackTrace();
    }
                                
           
            }
        });
        
        hb.getChildren().addAll(addArea, addStage, addRisk, addRiskIn, addMeasure, addRiskOut, addRiskPL, addRiskMinimized, addButton, pdfButton,loadButton,saveButton);
        hb.setSpacing(3);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table,labelB, hb);
        
        //Table B
        tableb.setEditable(true);
        tableb.setMinHeight(700);
        
        
        
 
        TableColumn metaName = new TableColumn("");
        metaName.setMinWidth(100);
        metaName.setCellValueFactory(
            new PropertyValueFactory<Metadata, String>("dataname"));
        //metaName.setCellFactory(cellFactory);

 
       TableColumn metaCon = new TableColumn("");
        metaCon.setMinWidth(1500);
        metaCon.setCellValueFactory(
            new PropertyValueFactory<Metadata, String>("datatext"));
        metaCon.setCellFactory(cellFactory);
        metaCon.setOnEditCommit(
            new EventHandler<CellEditEvent<Metadata, String>>() {
                @Override
                public void handle(CellEditEvent<Metadata, String> t) {
                    ((Metadata) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setDatatext(t.getNewValue());
                }
             }
        );

 
        tableb.getColumns().addAll(metaName,metaCon);
        tableb.setItems(datab);
        final VBox vboxb = new VBox();
        vboxb.setSpacing(5);
        vboxb.setPadding(new Insets(10, 0, 0, 10));
        vboxb.getChildren().addAll(labelC, tableb);
        
        final VBox vboxc = new VBox();
        vboxc.setSpacing(5);
        vboxc.setPadding(new Insets(10, 0, 0, 10));
        vboxc.getChildren().addAll(buttonURL, buttonURLb, buttonURLc, buttonURLd, browser);
        
        //Restliche UI
        
        TabPane tabPane = new TabPane();
        Tab tab = new Tab();
        tab.setText("Risk Assessment");
        tab.setContent(vbox);
        Tab tabb = new Tab();
        tabb.setText("Machine Limits and Metadata");
        tabb.setContent(vboxb);
        Tab tabc = new Tab();
        tabc.setContent (vboxc);
        tabc.setText("Directives and Norms");
        
        tabPane.getTabs().add(tab);
        tabPane.getTabs().add(tabb);
        tabPane.getTabs().add(tabc);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        // add tab pane
        borderPane.setCenter(tabPane);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        // added menu bar
        borderPane.setTop(menuBar);

        // add border Pane
       
        ((Group) scene.getRoot()).getChildren().addAll(borderPane);
 

        
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
 
    public static class Risk {
 
        private final SimpleStringProperty area;
        private final SimpleStringProperty risk;
        private final SimpleStringProperty stage;
        private final SimpleStringProperty measure;
        private final SimpleStringProperty riskin;
        private final SimpleStringProperty riskout;
        private final SimpleStringProperty riskpl;
        private final SimpleStringProperty riskminimized;
 
        private Risk(String fName, String lName, String email, String lmeasure, String lriskin, String lriskout, String lriskpl, String lriskminimized) {
            this.area = new SimpleStringProperty(fName);
            this.risk = new SimpleStringProperty(email);
            this.stage = new SimpleStringProperty(lName);
            this.measure = new SimpleStringProperty(lmeasure);
            this.riskin = new SimpleStringProperty(lriskin);
            this.riskout = new SimpleStringProperty(lriskout);
            this.riskpl = new SimpleStringProperty(lriskpl);
            this.riskminimized = new SimpleStringProperty(lriskminimized);
        }
 
        public String getArea() {
            return area.get();
        }
 
        public void setArea(String fName) {
            area.set(fName);
        }
 
        public String getRisk() {
            return risk.get();
        }
 
        public void setRisk(String fName) {
            risk.set(fName);
        }
 
        public String getStage() {
            return stage.get();
        }
 
        public void setStage(String fName) {
            stage.set(fName);
        }
        
        public String getMeasure() {
        return measure.get();
        }
 
        public void setMeasure(String fName) {
            measure.set(fName);
        }
        
        public String getRiskin() {
        return riskin.get();
        }
 
        public void setRiskin(String fName) {
            riskin.set(fName);
        }
    
        public String getRiskout() {
        return riskout.get();
        }
 
        public void setRiskout(String fName) {
            riskout.set(fName);
        }
        
        public String getRiskpl() {
        return riskpl.get();
        }
 
        public void setRiskpl(String fName) {
        riskpl.set(fName);
        }
        
        public String getRiskminimized() {
        return riskminimized.get();
        }
 
        public void setRiskminimized(String fName) {
        riskminimized.set(fName);
        }
    
    }
    
     public static class Metadata {
 
        private final SimpleStringProperty dataname;
        private final SimpleStringProperty datatext;

 
        private Metadata(String fName, String lName) {
            this.dataname = new SimpleStringProperty(fName);
            this.datatext = new SimpleStringProperty(lName);

        }
 
        public String getDataname() {
            return dataname.get();
        }
 
        public void setDataname(String fName) {
            dataname.set(fName);
        }
 
        public String getDatatext () {
            return datatext.get();
        }
 
        public void setDatatext (String fName) {
            datatext.set(fName);
        }
 
           
    }
     
    class EditingCellb extends TableCell<Metadata, String> {
 
        private TextField textField;
 
        public EditingCellb() {
        }
 
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(null);
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
    
    class EditingCell extends TableCell<Metadata, String> {
 
        private TextField textField;
 
        public EditingCell() {
        }
 
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(null);
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    private static void write(ObservableList<Risk> data, Path file) {
        try {

            // write object to file
            OutputStream fos = Files.newOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<Risk>(data));
            oos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static ObservableList<Risk> read(Path file) {
        try {
            InputStream in = Files.newInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(in);
            List<Risk> list = (List<Risk>) ois.readObject() ;

            return FXCollections.observableList(list);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FXCollections.emptyObservableList();
    }


}


