import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class database_query {
	private static Connection con;
	database_query(){
		try {
			con=DatabaseConnection.make_connection();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public boolean login_query(String username,String password)throws SQLException{
		String sql="select password from users where username=?";
		try {
			PreparedStatement prstmt=con.prepareStatement(sql);
			prstmt.setString(1, username);
			ResultSet result=prstmt.executeQuery();
			if (result.next()) {
				String pass=result.getString("password");
				if (pass.equals(password)) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}catch(SQLException e) {
			throw e;
		}
		
	}
	public  void adduser(String username,String password,String email,String phone_no) throws SQLException{
		String sql="Insert into users (username,password,email,phone_number) values(?,?,?,?)";
		try {
			PreparedStatement prstmt=con.prepareStatement(sql);
			prstmt.setString(1, username);
			prstmt.setString(2, password);
			prstmt.setString(3, email);
			prstmt.setString(4, phone_no);
			prstmt.executeUpdate();
		}catch(SQLException e) {
			throw e;
		}
	}
	
	public static void create_accounts(String username,String Account_type)throws SQLException{
		String sql1="Select user_id from users where username=?";
		String sql2="insert into accounts (user_id,account_type) values (?,?)";
		try {
			PreparedStatement prstmt1=con.prepareStatement(sql1);
			PreparedStatement prstmt2=con.prepareStatement(sql2);
			prstmt1.setString(1, username);
			ResultSet r=prstmt1.executeQuery();
			if(r.next()) {
				int user_id=r.getInt("user_id");
				prstmt2.setInt(1, user_id);
				prstmt2.setString(2, Account_type);
				prstmt2.executeUpdate();
			}
		}catch(SQLException e) {
			throw e;
		}
	}
	
	public static ResultSet get_account_list(String usern) throws SQLException{
		String sql1="Select user_id from users where username=?";
		String sql2="select * from accounts where user_id=?";
		try {
			PreparedStatement prstmt1=con.prepareStatement(sql1);
			PreparedStatement prstmt2=con.prepareStatement(sql2);
			prstmt1.setString(1, usern);
			ResultSet r=prstmt1.executeQuery();
			if (r.next()) {
				String user_id=r.getString("user_id");
				prstmt2.setString(1, user_id);
				ResultSet result=prstmt2.executeQuery();
				return result;
			}else {
				return null;
			}
		}catch(SQLException e) {
			throw e;
		}
		
	}
	
	
	
	public static void transaction(int acc_id,int balance,String tran_type,String description) throws SQLException {
		String sql="insert into transactions (account_id,amount,transaction_type,description) values (?,?,?,?)";
		try {
			PreparedStatement prstmt=con.prepareStatement(sql);
			prstmt.setInt(1, acc_id);
			prstmt.setInt(2, balance);
			prstmt.setString(3, tran_type);
			prstmt.setString(4, description);
			prstmt.executeUpdate();
		}catch(SQLException e) {
			throw e;
		}
	}
	
	public static void deposit(int acc_id,int balance) throws SQLException {
		String sql1="select balance from accounts where account_id=?";
		String sql="update accounts set balance=? where account_id=?";
		try {
			int initial_balance=0;
			PreparedStatement prstmt1=con.prepareStatement(sql1);
			prstmt1.setInt(1, acc_id);
			ResultSet r=prstmt1.executeQuery();
			if(r.next()) {
				initial_balance=r.getInt("balance");
			}
			PreparedStatement prstmt=con.prepareStatement(sql);
			prstmt.setInt(1, initial_balance+balance);
			prstmt.setInt(2, acc_id);
			prstmt.executeUpdate();
			
		}catch(SQLException e) {
			throw e;
		}
	}
	
	public static void Withdraw(int acc_id,int balance) throws SQLException {
		String sql1="select balance from accounts where account_id=?";
		String sql="update accounts set balance=? where account_id=?";
		try {
			int initial_balance=0;
			PreparedStatement prstmt1=con.prepareStatement(sql1);
			prstmt1.setInt(1, acc_id);
			ResultSet r=prstmt1.executeQuery();
			if(r.next()) {
				initial_balance=r.getInt("balance");
			}
			PreparedStatement prstmt=con.prepareStatement(sql);
			prstmt.setInt(1, initial_balance-balance);
			prstmt.setInt(2, acc_id);
			prstmt.executeUpdate();
			
		}catch(SQLException e) {
			throw e;
		}
	}
	
	public static ResultSet view_trans(int ACC_id) throws SQLException {
		String sql="select * from transactions where account_id=?";
		try {
			PreparedStatement prstmt=con.prepareStatement(sql);
			prstmt.setInt(1, ACC_id);
			ResultSet result=prstmt.executeQuery();
			return result;
			
		}catch(SQLException e) {
			throw e;
		}
	}
	
	public static ResultSet view_fund_transfer(int ACC_id) throws SQLException {
		String sql="select * from fund_transfers where from_account_id=? or to_account_id=?";
		try {
			PreparedStatement prstmt=con.prepareStatement(sql);
			prstmt.setInt(1, ACC_id);
			prstmt.setInt(2, ACC_id);
			ResultSet result=prstmt.executeQuery();
			return result;
			
		}catch(SQLException e) {
			throw e;
		}
	}
	
	public static void Transfer(int ACC_No,int Target_ACC,int Amount) throws SQLException {
		String sql1="Select * from accounts where account_id=?";
		String sql2="insert into fund_transfers (from_account_id,to_account_id,amount) values(?,?,?)";
		try {
			PreparedStatement prstmt1=con.prepareStatement(sql1);
			PreparedStatement prstmt2=con.prepareStatement(sql2);
			prstmt1.setInt(1, Target_ACC);
			ResultSet r=prstmt1.executeQuery();
			if (r.next()) {
				deposit(Target_ACC,Amount);
				Withdraw(ACC_No,Amount);
				prstmt2.setInt(1, ACC_No);
				prstmt2.setInt(2, Target_ACC);
				prstmt2.setInt(3, Amount);
				prstmt2.executeUpdate();
				
			}else {
				throw new SQLException("User not Found");
			}
		}catch(SQLException e) {
			throw e;
		}
	}
}
