package phonebook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class ViewController implements Initializable{

	@FXML
	TableView table;

	@FXML
	TextField inputLastName;

	@FXML
	TextField inputFirstName;

	@FXML
	TextField inputEmail;

	@FXML
	Button addNewContactButton;

	@FXML
	StackPane menuPane;

	@FXML
	Pane contactPane;

	@FXML
	Pane exportPane;

	@FXML
	TextField inputExportName;

	@FXML
	Button exportButton;
	
	@FXML
	AnchorPane anchor;

	@FXML
	SplitPane mainSplit;
	
	DB db = new DB();
	
	private final String MENU_CONTACTS = "Kontaktok";
	private final String MENU_LIST = "Lista";
	private final String MENU_EXPORT = "Export�l�s";
	private final String MENU_EXIT = "Kil�p�s";

	public final ObservableList<Person> data = FXCollections.observableArrayList();

	@FXML
	public void addContact(ActionEvent event) {
		String email = inputEmail.getText();
		if (email.length() > 3 && email.contains("@") && email.contains(".")) {
			Person newPerson = new Person(inputLastName.getText(), inputFirstName.getText(), email);
			data.add(newPerson);
			db.addContact(newPerson);
			inputLastName.clear();
			inputFirstName.clear();
			inputEmail.clear();
		} else {
			alert("Adj meg egy �rv�nyes e-mail c�met!");
		}
	}

	@FXML
	public void exportList(ActionEvent event) {
		String fileName = inputExportName.getText();
		if (fileName != null && !fileName.equals("")) {
			PdfGeneration pdfCreator = new PdfGeneration();
			pdfCreator.pdfGeneration(fileName, data);
		} else {
			alert("Adj meg egy f�jlnevet!");
		}
	}

	public void setTableData() {
		TableColumn lastNameCol = new TableColumn("Vezet�kn�v");
		lastNameCol.setMinWidth(120);
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

		lastNameCol.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Person, String> t) {
						Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
						actualPerson.setLastName(t.getNewValue());
						db.updateContact(actualPerson);
					}
				}
				);

		TableColumn firstNameCol = new TableColumn("Keresztn�v");
		firstNameCol.setMinWidth(120);
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

		firstNameCol.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Person, String> t) {
						Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
						actualPerson.setFirstName(t.getNewValue());
						db.updateContact(actualPerson);
					}
				}
				);

		TableColumn emailCol = new TableColumn("Email c�m");
		emailCol.setMinWidth(190);
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

		emailCol.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
					@Override
					public void handle(TableColumn.CellEditEvent<Person, String> t) {
						Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
						actualPerson.setEmail(t.getNewValue());
						db.updateContact(actualPerson);

					}
				}
				);
		
		TableColumn removeCol = new TableColumn("T�rl�s");
		removeCol.setMinWidth(100);
		Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory =
				new Callback<TableColumn<Person, String>, TableCell<Person, String>>()
				{

					@Override
					public TableCell call( final TableColumn<Person, String> param) 
					{
						final TableCell<Person, String> cell = new TableCell<Person, String>()
						{
							final Button btn = new Button("T�rl�s");
							
							@Override
							public void updateItem(String item, boolean empty) 
							{
								super.updateItem(item, empty);
								if ( empty )
								{
									setGraphic(null);
									setText(null);
								}
								else
								{
									btn.setOnAction( (ActionEvent event) ->
									{
										Person person = getTableView().getItems().get( getIndex() );
										data.remove(person);
										db.removeContact(person);
									});
									setGraphic(btn);
									setText(null);
								}
							}
						};
						
						return cell;
					}
			
				};
				
		removeCol.setCellFactory(cellFactory);
		
		table.getColumns().addAll(lastNameCol, firstNameCol, emailCol, removeCol);
		
		data.addAll(db.getAllContacts());
		
		table.setItems(data);

	}

	private void setMenuData() {
		TreeItem<String> treeItemRoot1 = new TreeItem<>("Men�");
		TreeView<String> treeView = new TreeView<>(treeItemRoot1);
		treeView.setShowRoot(false);

		TreeItem<String>  nodeItemA = new TreeItem<>(MENU_CONTACTS);
		TreeItem<String>  nodeItemB = new TreeItem<>(MENU_EXIT);

		nodeItemA.setExpanded(true);

		Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/contact.png")));
		Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/download.png")));

		TreeItem<String>  nodeItemA1 = new TreeItem<>(MENU_LIST, contactsNode);
		TreeItem<String>  nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

		treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);
		nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);

		menuPane.getChildren().add(treeView);

		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem<String> selectedItem = (TreeItem<String>) newValue;
				String selectedMenu;
				selectedMenu = selectedItem.getValue();

				if (selectedMenu != null) {
					System.out.println();
					switch(selectedMenu) {
					case MENU_CONTACTS:
						try {
							selectedItem.setExpanded(true);
						} catch (Exception e) {
						}
						break;
					case MENU_LIST:
						contactPane.setVisible(true);
						exportPane.setVisible(false);
						break;
					case MENU_EXPORT:
						contactPane.setVisible(false);
						exportPane.setVisible(true);
						break;
					case MENU_EXIT :
						System.exit(0);
						break;
					}
				}
			}
		});

	}

	private void alert(String text) {
		mainSplit.setDisable(true);
		mainSplit.setOpacity(0.4);
		
		Label label = new Label(text);
		Button alertButton = new Button("OK");
		VBox vb = new VBox(label, alertButton);
		vb.setAlignment(Pos.CENTER);
		
		alertButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainSplit.setDisable(false);
				mainSplit.setOpacity(1);
				vb.setVisible(false);
			}
		});
		
		anchor.getChildren().add(vb);
		anchor.setTopAnchor(vb, 300.0);
		anchor.setLeftAnchor(vb, 300.0);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTableData();
		setMenuData();
	}




}
