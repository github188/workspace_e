
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlobTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException, IOException {

		create();
		// read();
	}

	static void read() throws SQLException, IOException {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			conn = (Connection) JdbcUtils.getConnection();

			String s = "select svgfile from svg_file_names where svgname=?";

			ps = (PreparedStatement) conn.prepareStatement(s);

			ps.setString(1, "anhui.svg");

			rs = ps.executeQuery();
			while (rs.next()) {

				InputStream in = rs.getBinaryStream("svgfile");
				File file = new File("anhuiex2.svg");
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(file));
				byte[] buff = new byte[1024];
				for (int i = 0; (i = in.read(buff)) > 0;) {
					out.write(buff, 0, i);
				}
				out.close();
				in.close();
			}
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	static void create() throws SQLException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = JdbcUtils.getConnection();
		
			String sql = "insert into svgfiles (svgname,svgfile) values (?,?) ";
			ps = conn.prepareStatement(sql);
			File file = new File("anhui.svg");
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			ps.setString(1, "anhui.svg");
			ps.setBinaryStream(2, in, (int) file.length());
			System.out.println(file.length());

			int i = ps.executeUpdate();

			in.close();

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
