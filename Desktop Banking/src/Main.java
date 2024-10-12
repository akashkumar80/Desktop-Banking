import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;


public class Main extends Application{
	private static database_query query=new database_query();
	
	public static Stage primaryStage;
	public void start(Stage primaryStage) throws Exception{
		Text front=new Text("Welcome");
		
		front.setFill(Color.RED);
		front.setFont(new Font(30));
		
		StackPane root=new StackPane();
		
		root.setBackground(new Background(new BackgroundFill(
				Color.BLUE,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		
		root.getChildren().add(front);
		Main.primaryStage=primaryStage;
		primaryStage.setTitle("Banking System");
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
		primaryStage.show();
		PauseTransition pause=new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event->{
			Login();
		});
		pause.play();
		
	}
	
	public static void Login() {
		GridPane root=new GridPane();
		Text head=new Text("Login");
		head.setId("heading");
		Label user_label=new Label("Username");
		Label Pass_label=new Label("Password");
		TextField username=new TextField();
		PasswordField password=new PasswordField();
		username.setPromptText("Enter Username");
		password.setPromptText("Enter Password");
		Button Login=new Button("Login");
		Label errorlabel=new Label();
		errorlabel.setId("Error");
		Login.setOnAction(event->{
			errorlabel.setText("");
			String usern=username.getText();
			String pass=password.getText();
			
			if(usern.isEmpty() || pass.isEmpty()) {
				errorlabel.setText("All Field are mandatory");
				return;
			}
			try {
				if (query.login_query(usern,pass)) {
					errorlabel.setText("Success");
					Accounts(usern);
				}else {
					errorlabel.setText("Incorrent Credentials");
				}
			}catch(SQLException e) {
				errorlabel.setText(e.getMessage());
			}
		});
		
		Button Signup=new Button("SignUp");
		Signup.setOnAction(Event->Signup_page());
		root.addRow(0, head);
		root.addRow(1, user_label,username);
		root.addRow(2, Pass_label,password);
		root.addRow(3, errorlabel);
		root.addRow(4, Signup,Login);
		
		
		//Style
		root.setAlignment(Pos.CENTER);
		root.setHgap(80);
		root.setVgap(10);
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		GridPane.setColumnSpan(head,2);
		GridPane.setColumnSpan(errorlabel, 2);
		
		GridPane.setHalignment(head, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(head, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Signup, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Signup, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Login, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Login, javafx.geometry.VPos.CENTER);
		root.getStylesheets().add("Style.css");
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
		
	}
	
	
	public static void Signup_page() {
		GridPane root=new GridPane();
		Text head=new Text("Signup");
		head.setId("heading");
		Label Name_label=new Label("Username");
		Label Mobile_label=new Label("Mobile No.");
		Label Email_label=new Label("Email-ID");
		Label Pass_label=new Label("Password");
		
		TextField Username=new TextField();
		Username.setPromptText("Enter Full Name");
		PasswordField Pass=new PasswordField();
		Pass.setPromptText("Enter Password");
		TextField Mobile=new TextField();
		Mobile.setPromptText("Enter MObile No.");
		TextField Email=new TextField();
		Email.setPromptText("Enter Email-ID");
		
		Label errorlabel=new Label();
		errorlabel.setId("Error");
		
		Button Signup=new Button("Signup");
		Signup.setOnAction(event->{
			errorlabel.setText("");
			String username=Username.getText();
			String password=Pass.getText();
			String email=Email.getText();
			String phone=Mobile.getText();
			
			if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
				errorlabel.setText("All field are required");
				return;
			}
			if (!email.matches("^[A-Za-z0-9+_.,]+@(.+)$")) {
				errorlabel.setText("Invalid email");
				return;
			}
			if (phone.length()<10) {
				errorlabel.setText("Invalid Mobile");
				return;
			}
			try {
				query.adduser(username,password,email,phone);
				errorlabel.setText("Success,Please Login now");
			}catch(SQLException e) {
				errorlabel.setText("Error:-"+e.getMessage());
			}
		});
		
		Button Login=new Button("Login");
		Login.setOnAction(event->Login());
		
		root.addRow(0, head);
		root.addRow(1, Name_label,Username);
		root.addRow(2, Mobile_label,Mobile);
		root.addRow(3, Email_label,Email);
		root.addRow(4, Pass_label,Pass);
		root.addRow(5, errorlabel);
		root.addRow(6, Login,Signup);
		root.getStylesheets().add("Style.css");
		
		//Style
		//Style
		root.setAlignment(Pos.CENTER);
		root.setHgap(80);
		root.setVgap(8);
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		GridPane.setColumnSpan(head,2);
		GridPane.setColumnSpan(errorlabel, 2);
				
		GridPane.setHalignment(head, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(head, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Signup, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Signup, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Login, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Login, javafx.geometry.VPos.CENTER);
		
		
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
	}
	
	public static void Accounts(String username) throws SQLException {
		ResultSet accounts=null;
		
		VBox account_list=new VBox();
		ScrollPane root=new ScrollPane(account_list);
		root.setFitToWidth(true);
		Label errorlabel=new Label();
		
		
		Button create_account=new Button("Create New Account");
		create_account.setOnAction(event->{
			create_account(username);
		});
		
		try {
			accounts=query.get_account_list(username);
		}catch(SQLException e) {
			errorlabel.setText("Error:-"+e.getMessage());
		}
		if(!accounts.next()) {
			VBox box=new VBox();
			Label not_found=new Label("Account not Found");
			box.getChildren().add(not_found);
			box.setAlignment(Pos.CENTER);
			box.setPadding(new Insets(20,20,20,20));
			account_list.getChildren().add(box);
			
		}else {
		do {
			String A_type=accounts.getString("account_type");
			int balance=accounts.getInt("balance");
			int a_id=accounts.getInt("account_id");
			VBox list1=new VBox();
			Label Account_type=new Label("Account Type:-"+A_type+" Account");
			Label Balance=new Label("Available Balance="+balance);
			Label Account_ID=new Label("Account No:-"+a_id);
			Button view_detail=new Button("View Detail");
			view_detail.setOnAction(event->{
				Detail_account_page(username,balance,a_id,A_type);
			});
			list1.getChildren().addAll(Account_type,Balance,Account_ID,view_detail);
			list1.setAlignment(Pos.CENTER);
			list1.setPadding(new Insets(20,20,20,20));
			list1.setStyle(
		            "-fx-border-color: black; " +    // Border color
		            "-fx-border-width: 2px; " +      // Border width
		            "-fx-border-style: solid;"       // Border style
		        );
			account_list.getChildren().add(list1);
		}while(accounts.next());}
		Button logout=new Button("Logout");
		logout.setOnAction(event->{
			Login();
		});
		account_list.getChildren().addAll(errorlabel,create_account,logout);
		
		
		//Styling
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		account_list.setPadding(new Insets(20));
		account_list.setSpacing(20);
		account_list.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		account_list.setAlignment(Pos.CENTER);
		root.getStylesheets().add("Style.css");
		
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
		
	}
	
	public static void create_account(String username) {
		Label head=new Label("Create a New Account");
		head.setId("heading");
		Button back=new Button("Back");
		back.setOnAction(event->{
			try {
				Accounts(username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Label errorlabel=new Label();
		errorlabel.setId("Error");
		ObservableList<String> types=FXCollections.observableArrayList("Saving","Current");
		ListView<String> listview=new ListView<String>(types);
		Button create=new Button("Craete Account");
		create.setOnAction(event->{
			errorlabel.setText("");
			try {
				query.create_accounts(username,listview.getSelectionModel().getSelectedItem());
				Accounts(username);
			}catch(SQLException e) {
				errorlabel.setText("Error:-"+e.getMessage());
			}
			
		});
		
		GridPane root=new GridPane();
		root.getStylesheets().add("Style.css");
		root.addRow(0, head);
		root.addRow(1, listview);
		root.addRow(2, errorlabel);
		root.addRow(3, back,create);
		
		//Style
		root.setAlignment(Pos.CENTER);
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		GridPane.setColumnSpan(listview,2);
		GridPane.setColumnSpan(head, 2);
		GridPane.setColumnSpan(errorlabel, 2);
		root.setHgap(80);
		root.setVgap(10);
		listview.setMaxHeight(50);
		
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
	}
	
	public static void Detail_account_page(String Username,int Balance,int ACC_No,String ACC_Type) {
		VBox root=new VBox();
		Label balance_label=new Label("Balance:-"+Balance);
		Label acc_no_label=new Label("Account No:-"+ACC_No);
		Label acc_type_label=new Label("Account_Type:-"+ACC_Type);
		Button view_tran=new Button("View All Transaction");
		view_tran.setOnAction(event->{
			view_transaction(Username,Balance,ACC_No,ACC_Type);
		});
		Button deposite=new Button("Deposite Money");
		deposite.setOnAction(event->{
			Deposit_money(Username,Balance,ACC_No,ACC_Type);
		});
		Button withdraw=new Button("Withdraw");
		withdraw.setOnAction(event->{
			Withdraw_money(Username,Balance,ACC_No,ACC_Type);
		});
		Button transfer=new Button("Transfer Money to other account");
		transfer.setOnAction(event->{
			transfer(Username,Balance,ACC_No,ACC_Type);
		});
		Button back=new Button("Back");
		back.setOnAction(event->{
			try {
				Accounts(Username);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		root.getChildren().addAll(balance_label,acc_no_label,acc_type_label,transfer,view_tran,deposite,withdraw,back);
		
		//Style
		root.getStylesheets().add("Style.css");
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10));
		root.setSpacing(20);
		
		
		ScrollPane root1=new ScrollPane(root);
		root1.setFitToWidth(true);
		root1.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		Scene scene=new Scene(root1,600,300);
		primaryStage.setScene(scene);
	}
	
	public static void Deposit_money(String Username,int Balance,int ACC_No,String ACC_Type) {
		String tran_type="Deposit";
		int balance=Balance;
		GridPane root=new GridPane();
		Label errorlabel=new Label("");
		Label amount=new Label("Amount");
		TextField Amount=new TextField();
		Amount.setPromptText("Enter Amount to Deposite");
		Label description=new Label("Enter Purpose:-");
		TextField Description=new TextField();
		Description.setPromptText("Enter Purpose or Description of Transaction");
		Button Submit=new Button("Deposit");
		Submit.setOnAction(event->{
			
			errorlabel.setText("");
			if(Amount.getText().length()==0) {
				errorlabel.setText("Please Enter Amount");
				return;
			}
			int am=Integer.parseInt(Amount.getText());
			String descp=Description.getText();
			try {
				query.deposit(ACC_No, am);
				query.transaction(ACC_No, am, tran_type, descp);
				Detail_account_page(Username,Balance+am,ACC_No,ACC_Type);
			}catch(SQLException e) {
				errorlabel.setText("Error:-"+e.getMessage());
				return;
			}catch(Exception e) {
				errorlabel.setText("Error:-"+e.getMessage());
			}
			
		});
		Button Back=new Button("Back");
		Back.setOnAction(event->{
			Detail_account_page(Username,balance,ACC_No,ACC_Type);
		});
		Label head=new Label("Deposit");
		head.setId("heading");
		errorlabel.setId("Error");
		root.addRow(0, head);
		root.addRow(1, amount,Amount);
		root.addRow(2, description,Description);
		root.addRow(3, errorlabel);
		root.addRow(4, Back,Submit);
		
		//Style
		root.getStylesheets().add("Style.css");
		root.setHgap(50);
		root.setVgap(10);
		root.setAlignment(Pos.CENTER);
		GridPane.setColumnSpan(head, 2);
		GridPane.setColumnSpan(errorlabel, 2);
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		GridPane.setHalignment(head, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(head, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Back, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Back, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Submit, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Submit, javafx.geometry.VPos.CENTER);
		
		
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
	}
	
	public static void Withdraw_money(String Username,int Balance,int ACC_No,String ACC_Type) {
		String tran_type="Withdraw";
		int balance=Balance;
		GridPane root=new GridPane();
		Label errorlabel=new Label("");
		Label amount=new Label("Amount");
		TextField Amount=new TextField();
		Amount.setPromptText("Enter Amount to Wirhdraw");
		Label description=new Label("Enter Purpose:-");
		TextField Description=new TextField();
		Description.setPromptText("Enter Purpose or Description of Transaction");
		Button Submit=new Button("Withdraw");
		Submit.setOnAction(event->{
			errorlabel.setText("");
			if(Amount.getText().length()==0) {
				errorlabel.setText("Please Enter Amount");
				return;
			}
			int am=Integer.parseInt(Amount.getText());
			String descp=Description.getText();
			if (Balance<am) {
				errorlabel.setText("Insufficient Balance");
				return;
			}
			try {
				query.Withdraw(ACC_No, am);
				query.transaction(ACC_No, am, tran_type, descp);
				Detail_account_page(Username,Balance-am,ACC_No,ACC_Type);
			}catch(SQLException e) {
				errorlabel.setText("Error:-"+e.getMessage());
				return;
			}catch(Exception e) {
				errorlabel.setText("Error:-"+e.getMessage());
			}
			
		});
		Button Back=new Button("Back");
		Back.setOnAction(event->{
			Detail_account_page(Username,balance,ACC_No,ACC_Type);
		});
		Label head=new Label("Withdraw");
		errorlabel.setId("Error");
		head.setId("heading");
		root.addRow(0, head);
		root.addRow(1, amount,Amount);
		root.addRow(2, description,Description);
		root.addRow(3, errorlabel);
		root.addRow(4, Back,Submit);
		
		//Style
		root.getStylesheets().add("Style.css");
		root.setHgap(50);
		root.setVgap(10);
		root.setAlignment(Pos.CENTER);
		GridPane.setColumnSpan(head, 2);
		GridPane.setColumnSpan(errorlabel, 2);
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		GridPane.setHalignment(head, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(head, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Back, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Back, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Submit, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Submit, javafx.geometry.VPos.CENTER);
		
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
	}
	
	
	public static void view_transaction(String Username,int Balance,int ACC_No,String ACC_Type) {
		GridPane root=new GridPane();
		ResultSet result=null;
		int i=1;
		Label errorlabel=new Label("");
		try{
			GridPane r=new GridPane();
			i=0;
			Label head=new Label("All Transaction");
			r.addRow(i, head);
			GridPane.setColumnSpan(head, 5);
			i=i+1;
			result=query.view_trans(ACC_No);
			if (result.next()) {
				r.addRow(i, new Label("Transaction ID"),new Label("Amount"),new Label("Type"),new Label("Description"),new Label("Time"));
				i=i+1;
			}else {
				errorlabel.setText("No Transaction Found");
			}
			do {
				i=i+1;
				Label tran_id=new Label(""+result.getInt("transaction_id"));
				Label amount=new Label(""+result.getInt("amount"));
				Label type=new Label(result.getString("transaction_type"));
				Label desc=new Label(result.getString("description"));
				Label time=new Label(""+result.getDate("created_at")+" "+result.getTime("created_at"));
				
				r.addRow(i, tran_id,amount,type,desc,time );
				
			}while(result.next());
			
			r.setHgap(30);
			r.setStyle(
		            "-fx-border-color: #3498db; " + // Border color
		            "-fx-border-width: 2px; " +     // Border width
		            "-fx-border-style: solid; " +    // Border style
		            "-fx-background-color: #ffffff; " + // Background color
		            "-fx-padding: 10px; " +           // Padding inside the GridPane
		            "-fx-hgap: 10px; " +              // Horizontal gap
		            "-fx-vgap: 10px; "                 // Vertical gap
		        );
			r.setAlignment(Pos.CENTER);
			GridPane.setHalignment(head, javafx.geometry.HPos.CENTER);
			GridPane.setValignment(head, javafx.geometry.VPos.CENTER);
			
			root.addRow(0, r);
			GridPane r1=new GridPane();
			i=0;
			Label head1=new Label("All Transaction");
			r1.addRow(i, head1);
			GridPane.setColumnSpan(head1, 5);
			result=query.view_fund_transfer(ACC_No);
			if (result.next()) {
				i=i+1;
				r1.addRow(i, new Label("Transaction ID"),new Label("From Account"),new Label("To Account"),new Label("Amount"),new Label("Time"),new Label("Status"));
			}else {
				errorlabel.setText("No Fund Transfer Found");
			}
			do {
				i=i+1;
				Label tran_id=new Label(""+result.getInt("transfer_id"));
				Label from=new Label(""+result.getInt("from_account_id"));
				Label to=new Label(result.getString("to_account_id"));
				Label amount=new Label(result.getString("amount"));
				Label time=new Label(""+result.getDate("transfer_date")+" "+result.getTime("transfer_date"));
				Label status=new Label(result.getString("status"));
				
				r1.addRow(i, tran_id,from,to,amount,time,status);
			}while(result.next());
			
			r1.setHgap(30);
			r1.setStyle(
		            "-fx-border-color: #3498db; " + // Border color
		            "-fx-border-width: 2px; " +     // Border width
		            "-fx-border-style: solid; " +    // Border style
		            "-fx-background-color: #ffffff; " + // Background color
		            "-fx-padding: 10px; " +           // Padding inside the GridPane
		            "-fx-hgap: 10px; " +              // Horizontal gap
		            "-fx-vgap: 10px; "                 // Vertical gap
		        );
			r1.setAlignment(Pos.CENTER);
			GridPane.setHalignment(head1, javafx.geometry.HPos.CENTER);
			GridPane.setValignment(head1, javafx.geometry.VPos.CENTER);
			
			root.addRow(1, r1);
		}catch(SQLException e) {
			errorlabel.setText("Error:-"+e.getMessage());
		}
		Button back=new Button("Back");
		back.setOnAction(event->{
			Detail_account_page(Username,Balance,ACC_No,ACC_Type);
		});
		root.addRow(2, errorlabel);
		root.addRow(3, back);
		ScrollPane root1=new ScrollPane(root);
		//Style
		root.getStylesheets().add("Style.css");
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		root.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);
		
		root.setVgap(10);
		
		root1.setFitToWidth(true);
		StackPane root2=new StackPane();
		root2.getChildren().add(root1);
		root2.setAlignment(Pos.CENTER);
		root2.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		
		
		Scene scene=new Scene(root2,600,300);
		primaryStage.setScene(scene);
		
		
	}

	public static void transfer(String Username,int Balance,int ACC_No,String ACC_Type) {
		
		Label errorlabel=new Label("");
		Label acc_no=new Label("Beneficial Account No:-");
		Label amount=new Label("Amount");
		
		TextField Target_ACC=new TextField();
		Target_ACC.setPromptText("Enter Targer Account number");
		
		TextField Amount=new TextField();
		Amount.setPromptText("Enter Amount to Transfer");
		
		Button Transfer=new Button("Transfer");
		Transfer.setOnAction(event->{
			if(Target_ACC.getText().length()==0 || Amount.getText().length()==0) {
				errorlabel.setText("All Field are mandatory");
				return;
			}
			int target=Integer.parseInt(Target_ACC.getText());
			int amount_to=Integer.parseInt(Amount.getText());
			if (Balance<amount_to) {
				errorlabel.setText("Insufficient Balance");
				return;
			}
			try {
				query.Transfer(ACC_No, target, amount_to);
				Detail_account_page(Username,Balance-amount_to,ACC_No,ACC_Type);
			}catch(SQLException e) {
				errorlabel.setText("Error:-"+e.getMessage());
			}
			
		});
		Button Back=new Button("Back");
		
		Back.setOnAction(event->{
			Detail_account_page(Username,Balance,ACC_No,ACC_Type);
		});
		GridPane root=new GridPane();
		Label head=new Label("Transfer Money");
		head.setId("heading");
		errorlabel.setId("Error");
		root.addRow(0, head);
		root.addRow(1, acc_no,Target_ACC);
		root.addRow(2, amount,Amount);
		root.addRow(3, errorlabel);
		root.addRow(4, Back,Transfer);
		
		//Style
		root.getStylesheets().add("Style.css");
		root.setHgap(50);
		root.setVgap(10);
		root.setAlignment(Pos.CENTER);
		GridPane.setColumnSpan(head, 2);
		GridPane.setColumnSpan(errorlabel, 2);
		root.setBackground(new Background(new BackgroundFill(
				Color.AQUA,
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
		GridPane.setHalignment(head, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(head, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Back, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Back, javafx.geometry.VPos.CENTER);
		GridPane.setHalignment(Transfer, javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Transfer, javafx.geometry.VPos.CENTER);
		
		Scene scene=new Scene(root,600,300);
		primaryStage.setScene(scene);
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

}
