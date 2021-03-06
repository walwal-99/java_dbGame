import java.sql.*;
import DTO.ItemClass;

public class DBClass {

    //생성자
    //변수
    //메소드
    //디비 연결 메소스
    public Connection dbConn() {
        final String driver = "org.mariadb.jdbc.Driver";
        final String DB_IP = "localhost";
        final String DB_PORT = "3307";
        final String DB_USER = "root";
        final String DB_NAME = "mydb";
        final String DB_PASS = "1843";
        final String DB_URL =
                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;

        Connection conn = null;


        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            if (conn != null) {
                System.out.println("DB 접속 성공");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }
        return conn; //conn반환
    }

    //데이터 넣기 메소드
    public void insertItem(String name, String att, String dam, String hyo) {

        Connection conn = dbConn(); //conn 불러오기

        //쿼리문 준비
        String sql = "INSERT INTO `item` (`name`, `att`, `dam`, `hyo`) VALUES (? , ? , ? , ?)";

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, att);
            pstmt.setString(3, dam);
            pstmt.setString(4, hyo);

            int result = pstmt.executeUpdate();
            if (result == 1) {
                System.out.println("데이터 삽입 성공!");

            }

        } catch (Exception e) {
            System.out.println("데이터 삽입 실패!");
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace(); //오류출력 printStackTrace
            }
        }
    }

    public void insertItem(ItemClass item) {

        Connection conn = dbConn(); //conn 불러오기
        //쿼리문 준비
        String sql = "INSERT INTO `item` (`name`, `att`, `dam`, `hyo`) VALUES (? , ? , ? , ?)";

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getAtt());
            pstmt.setInt(3, item.getDam());
            pstmt.setString(4, item.getHyo());
            int result = pstmt.executeUpdate();
            if (result == 1) {
                System.out.println("데이터 삽입 성공!");

            }

        } catch (Exception e) {
            System.out.println("데이터 삽입 실패!");
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace(); //오류출력 printStackTrace
            }
        }
    }

    //데이터 보기 메소드
    public void selectItem() {

        Connection conn = dbConn();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select * from item";

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String att = rs.getString("att");
                String dam = rs.getString("dam");
                String hyo = rs.getString("hyo");
                System.out.println("이름: " + name + "  속성: " + att + "  데미지: " + dam + "  효과: " + hyo);
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
