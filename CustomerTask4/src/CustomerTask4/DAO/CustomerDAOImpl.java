package CustomerTask4.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import CustomerTask4.Entities.Customer;

import CustomerTask4.Connection.ConnectionProvider;


public class CustomerDAOImpl implements CustomerDAO{
	public static Connection con;
	public static PreparedStatement statement;

	
	public static java.sql.Date convertUtiltoSql(java.util.Date uDate){
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}
	@Override
	public int insertCustomerDetails(Customer customer) {
		String sql = "INSERT INTO CUSTOMER VALUES(?,?,?,?,?,?,?)";
		try {
			con = ConnectionProvider.getConnection();
			statement = con.prepareStatement(sql);
			
			String firstName = customer.getFirstName();
			String lastName = customer.getLastName();
			String custid = firstName.substring(0,2).concat(lastName.substring(lastName.length() - 2)) + Math.round(Math.random()*1000);
			statement.setInt(1, 0);
			statement.setString(2, custid);
			statement.setString(3, firstName);
			statement.setString(4, lastName);
			statement.setDate(5, convertUtiltoSql(customer.getDateOfBirth()));
			statement.setString(6, customer.getGender());
			statement.setString(7, customer.getCustomerCreditCardType());
			int rowsInserted = statement.executeUpdate();
			
			return rowsInserted;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateCustomerDetails(Customer customer) {
		String sql = "UPDATE Customer SET firstname = ?, lastname=?, customercreditcard=? WHERE custId =?" ;
			
			try {
				con =ConnectionProvider.getConnection();
				statement = con.prepareStatement(sql);
				
				statement.setString(1, customer.getFirstName());
				statement.setString(2, customer.getLastName());
				statement.setString(3, customer.getCustomerCreditCardType());
				statement.setString(9, customer.getCustId());
				
				
				int rowsUpdated = statement.executeUpdate();
				return rowsUpdated;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
	}

	@Override
	public int deleteCustomerDetails(String custId) {
		String sql = "DELETE FROM CUSTOMER WHERE custId = ?";
				
			try {
					con = ConnectionProvider.getConnection();
					statement = con.prepareStatement(sql);
					
					statement.setString(1, custId);
					
					int rowsDeleted = statement.executeUpdate();
					return rowsDeleted;
			} catch (Exception e) {
					e.printStackTrace();
			}
		return 0;
	}

	@Override
	public Customer viewCustomerDetails(String custId) {
		String sql = "SELECT * FROM CUSTOMER WHERE custid = ?";
		Customer customer  = new Customer();
		
		try {
			con = ConnectionProvider.getConnection();
			statement = con.prepareStatement(sql);
			statement.setString(1,custId);
			ResultSet rs= statement.executeQuery();
			
			while(rs.next()) {
				customer.setCustId(rs.getString("custid"));
				customer.setFirstName(rs.getString("FIRSTNAME"));
				customer.setLastName(rs.getString("LASTNAME"));
				customer.setDateOfBirth(rs.getDate("dateofbirth"));
				customer.setGender(rs.getString("gender"));
				customer.setCustomerCreditCardType(rs.getString("customercreditcard"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public List<Customer> viewAllCustomerDetails() {
		List<Customer> list = new ArrayList<>();
		String sql = "SELECT * FROM CUSTOMER";
		Customer customer = new Customer();
		
		try {
			con = ConnectionProvider.getConnection();
			statement = con.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				customer.setCustId(rs.getString("custid"));
				customer.setFirstName(rs.getString("firstname"));
				customer.setLastName(rs.getString("lastname"));
				customer.setDateOfBirth(rs.getDate("dateofbirth"));
				customer.setGender(rs.getString("gender"));
				customer.setCustomerCreditCardType(rs.getString("customercreditcard"));
				list.add(customer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;

	}

}
