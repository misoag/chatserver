/**
 * @Package com.baikan.dao
 * @Description: TODO
 * @author HuangShuzhen  
 * @date 2012-7-3 下午3:21:18
 * @version V1.0  
 */
package tv.baikan.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class BasicDao {

	protected Connection getConnection() {
		return Proxool.getConnection();
	}

	public <T> List<T> queryList(Class<T> type, String sql, Object... params) {
		Connection conn = getConnection();
		ResultSetHandler<List<T>> rsHandler = new BeanListHandler<T>(type);
		QueryRunner runner = new QueryRunner();
		List<T> list = null;
		try {
			list = runner.query(conn, sql, rsHandler, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return list;
	}

	public <T> T querySingal(Class<T> type, String sql, Object... params) {
		Connection conn = getConnection();
		try {
			return new QueryRunner().query(conn, sql, new BeanHandler<T>(type), params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public Long queryLong(String sql, Object... params) {
		Connection conn = getConnection();
		try {
			QueryRunner queryRunner = new QueryRunner();
			Object obj = queryRunner.query(conn, sql, new ScalarHandler(), params);
			return (Long) obj;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return 0l;
	}

	public long count(String sql, Object... params) {
		return queryLong(sql, params);
	}

	public int queryInt(String sql, Object... params) {
		Connection conn = getConnection();
		try {
			QueryRunner queryRunner = new QueryRunner();
			Object obj = queryRunner.query(conn, sql, new ScalarHandler(), params);
			return (Integer) obj;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return 0;
	}

	public String queryString(String sql, Object... params) {
		Connection conn = getConnection();
		try {
			QueryRunner queryRunner = new QueryRunner();
			Object obj = queryRunner.query(conn, sql, new ScalarHandler(), params);
			return (String) obj;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return "";
	}

	public void batch(String sql, Object[][] params) {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		try {
			qr.batch(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public void insert(String sql, Object... params) {
		Connection conn = getConnection();
		QueryRunner qr = new QueryRunner();
		try {
			qr.update(conn, sql, params);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public void update(String sql, Object... params) {
		insert(sql, params);
	}

	public void delete(String sql, Object... params) {
		update(sql, params);
	}

	protected void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
