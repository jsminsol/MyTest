package jdbc.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MysqlExample {
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public static Connection getConn(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/test", "ism", "tigerscott");
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
			
			for(int i=1; i<=cols; i++){
				System.out.print(meta.getTableName(i)+"\t");
				System.out.print(meta.getColumnName(i)+"\t");
				System.out.print(meta.getPrecision(i)+"\t");
				System.out.print(meta.getColumnTypeName(i)+"\n");
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
			rs = meta.getColumns(null, null, "board", "%");
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
			
			rs = meta.getIndexInfo(null, null, "board", true, true);
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
			
			rs = meta.getPrimaryKeys(null, null, "board");
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
			
			String sql = "DESC board";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			ResultSetMetaData meta = rs.getMetaData();
			
			int cols = meta.getColumnCount();
			
			System.out.print(meta.getColumnName(1));
			for(int i=2; i <= cols; i++){
				System.out.print("   "+meta.getColumnName(i));
			}
			System.out.print("\n");
			
			while(rs.next()){
				System.out.print(rs.getString(1));
				for(int i=2; i <= cols; i++){
					if(rs.getString(i-1) == null){
						for(int j=0;j<(meta.getColumnName(i-1).length()-1);j++){
							System.out.print(" ");
						}
					}else if(meta.getColumnName(i-1).length()+3-rs.getString(i-1).length()>0){
						for(int j=0;j<meta.getColumnName(i-1).length()+3-rs.getString(i-1).length();j++){
								System.out.print(" ");
						}
					}
					System.out.print(rs.getString(i));
					if(i%cols == 0) System.out.print("\n");
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
			
			String sql = "SHOW INDEX FROM board";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			ResultSetMetaData meta = rs.getMetaData();
			
			int cols = meta.getColumnCount();
			
			System.out.print(meta.getColumnName(1));
			for(int i=2; i <= cols; i++){
				System.out.print("   "+meta.getColumnName(i));
			}
			System.out.print("\n");
			
			while(rs.next()){
				System.out.print(rs.getString(1));
				for(int i=2; i <= cols; i++){
					if(rs.getString(i-1) == null){
						for(int j=0;j<(meta.getColumnName(i-1).length()-1);j++){
							System.out.print(" ");
						}
					}else if(meta.getColumnName(i-1).length()+3-rs.getString(i-1).length()>0){
						for(int j=0;j<meta.getColumnName(i-1).length()+3-rs.getString(i-1).length();j++){
								System.out.print(" ");
						}
					}
					System.out.print(rs.getString(i));
					if(i%cols == 0) System.out.print("\n");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(null, pstmt, conn);
		}
	}*/
	
	public static void main(String[] args) {
		MysqlExample mysqlEx = new MysqlExample();
		
		//mysqlEx.insert(14);
		mysqlEx.select();
		mysqlEx.getMetaData();
		
	}
}
