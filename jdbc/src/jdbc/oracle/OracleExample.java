package jdbc.oracle;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class OracleExample {
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public static Connection getConn(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.56.101:1521:orcl", "ism", "tigerscott");
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	private void closeConn(ResultSet rs, PreparedStatement pstmt, Connection conn){
		if(rs!=null) try{rs.close();}catch(SQLException ex){}
		if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		if(conn!=null) try{conn.close();}catch(SQLException ex){}
	}
	
	public void insert(int id){
		try{
			int cnt = 0;
			conn = getConn();
			
			String sql = "INSERT INTO board (id,title,content) VALUES (?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, id);
			pstmt.setString(++cnt, "test");
			pstmt.setString(++cnt, "test");
			
			pstmt.executeUpdate();
			
			System.out.println("insert 완료!");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(null, pstmt, conn);
		}
	}
	
	public void select(){
		
		try{
			conn = getConn();
			
			String sql = "SELECT * FROM board";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			System.out.println("select 완료!");
			
			ResultSetMetaData meta = rs.getMetaData();
			
			int cols = meta.getColumnCount();
			
			System.out.print(meta.getColumnName(1));
			for(int i=2; i <= cols; i++){
				System.out.print("\t"+meta.getColumnName(i));
			}
			System.out.print("\n");
			
			while(rs.next()){
				System.out.print(rs.getInt(1));
				for(int i=2; i <= cols; i++){
					System.out.print("\t"+rs.getString(i));
				}
				System.out.print("\n");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(null, pstmt, conn);
		}
	}
	
	public void getMetaData(){
		try{
			conn = getConn();
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getColumns(null, null, "BOARD", "%");
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			
			System.out.println("[테이블 구조]");
			System.out.print(rsmd.getColumnName(1));
			for(int i=2;i<=cols;i++){
				System.out.print(" | " + rsmd.getColumnName(i));
			}
			System.out.print("\n");
			
			while(rs.next()){
				for(int i=1;i<=cols;i++){
					System.out.print(rs.getString(i));
					if(rs.getString(i) == null){
						for(int j=0;j<(rsmd.getColumnName(i).length()-1);j++){
							System.out.print(" ");
						}
					}else if(rsmd.getColumnName(i).length()+3-rs.getString(i).length()>0){
						for(int j=0;j<rsmd.getColumnName(i).length()+3-rs.getString(i).length();j++){
								System.out.print(" ");
						}
					}
				}
				System.out.print("\n");
			}
			
			rs = meta.getIndexInfo(null, null, "BOARD", true, true);
			rsmd = rs.getMetaData();
			cols = rsmd.getColumnCount();
			
			System.out.println("[index 정보]");
			System.out.print(rsmd.getColumnName(1));
			for(int i=2;i<=cols;i++){
				System.out.print(" | " + rsmd.getColumnName(i));
			}
			System.out.print("\n");
			
			while(rs.next()){
				for(int i=1;i<=cols;i++){
					System.out.print(rs.getString(i));
					if(rs.getString(i) == null){
						for(int j=0;j<(rsmd.getColumnName(i).length()-1);j++){
							System.out.print(" ");
						}
					}else if(rsmd.getColumnName(i).length()+3-rs.getString(i).length()>0){
						for(int j=0;j<rsmd.getColumnName(i).length()+3-rs.getString(i).length();j++){
								System.out.print(" ");
						}
					}
				}
				System.out.print("\n");
			}
			
			rs = meta.getPrimaryKeys(null, null, "BOARD");
			rsmd = rs.getMetaData();
			cols = rsmd.getColumnCount();
			
			System.out.println("[primary key]");
			System.out.print(rsmd.getColumnName(1));
			for(int i=2;i<=cols;i++){
				System.out.print(" | " + rsmd.getColumnName(i));
			}
			System.out.print("\n");
			
			while(rs.next()){
				for(int i=1;i<=rsmd.getColumnCount();i++){
					System.out.print(rs.getString(i));
					if(rs.getString(i) == null){
						for(int j=0;j<(rsmd.getColumnName(i).length()-1);j++){
							System.out.print(" ");
						}
					}else if(rsmd.getColumnName(i).length()+3-rs.getString(i).length()>0){
						for(int j=0;j<rsmd.getColumnName(i).length()+3-rs.getString(i).length();j++){
								System.out.print(" ");
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(rs, null, conn);
		}
	}
	
	/*public void getDB(){
		
		try{
			conn = getConn();
			
			String sql = "SELECT * FROM col WHERE tname='BOARD'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			ResultSetMetaData meta = rs.getMetaData();
			
			int cols = meta.getColumnCount();
			
			System.out.printf("%s",meta.getColumnName(1));
			for(int i=2; i <= cols; i++){
				System.out.printf("    %s",meta.getColumnName(i));
			}
			System.out.printf("\n");
			
			while(rs.next()){
				System.out.printf("%s",rs.getString(1));
				for(int i=2; i <= cols; i++){
					if(rs.getString(i-1) == null){
						for(int j=0;j<(meta.getColumnName(i-1).length());j++){
							System.out.print(" ");
						}
					}else if((meta.getColumnName(i-1).length()+4-rs.getString(i-1).length())>0){
						for(int j=0;j<(meta.getColumnName(i-1).length()+4-rs.getString(i-1).length());j++){
							System.out.print(" ");
						}
					}
					System.out.printf("%s",rs.getString(i));
					if(i%cols == 0) System.out.printf("\n");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(null, pstmt, conn);
		}
	}
	
	public void getIndex(){

		try{
			conn = getConn();
			
			String sql = "SELECT * FROM USER_INDEXES WHERE TABLE_NAME='BOARD'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			ResultSetMetaData meta = rs.getMetaData();
			
			int cols = meta.getColumnCount();
			
			System.out.printf("%s",meta.getColumnName(1));
			for(int i=2; i <= cols; i++){
				System.out.print("   ");
				System.out.printf("%s",meta.getColumnName(i));
			}
			System.out.printf("\n");
			
			while(rs.next()){
				System.out.printf("%s",rs.getString(1));
				for(int i=2; i <= cols; i++){
					if(rs.getString(i-1) == null){
						for(int j=0;j<(meta.getColumnName(i-1).length()-1);j++){
							System.out.print(" ");
						}
					}else if((meta.getColumnName(i-1).length()+3-rs.getString(i-1).length())>0){
						for(int j=0;j<(meta.getColumnName(i-1).length()+3-rs.getString(i-1).length());j++){
							System.out.print(" ");
						}
					}
					System.out.printf("%s",rs.getString(i));
					if(i%cols == 0) System.out.printf("\n");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(null, pstmt, conn);
		}
	}*/
	
	public static void main(String[] args) {
		OracleExample orclEx = new OracleExample();
		
		//orclEx.insert(11);
		orclEx.select();
		orclEx.getMetaData();
		
	}
}
