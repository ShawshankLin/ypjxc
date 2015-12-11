package com.jxc.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.jxc.model.CusInfo;
import com.jxc.model.DrugInfo;
import com.jxc.model.EmpInfo;
import com.jxc.model.GysInfo;
import com.jxc.model.KuCunInfo;
import com.jxc.model.RuKuDetailInfo;
import com.jxc.model.RuKuMainInfo;
import com.jxc.model.SellDetailInfo;
import com.jxc.model.SellMainInfo;
import com.jxc.model.UserInfo;
import com.jxc.tool.Item;

public class Dao {
	protected static String dbClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	protected static String dbUrl=null;
	protected static String dbUser=null;
	protected static String dbPwd =null;
	protected static String second = null;
	public static Connection conn = null;

	public void Dao() {

	}

	static {
		dbUrl = "jdbc:sqlserver://"+PropertiesUtils.getPropertyValue("HOST")+":1433;DatabaseName="+PropertiesUtils.getPropertyValue("URL")+";SelectMethod=Cursor";
		dbUser = PropertiesUtils.getPropertyValue("USER");
		dbPwd = PropertiesUtils.getPropertyValue("PWD");
		try {
			if (conn == null) {
				Class.forName(dbClassName).newInstance();
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public static ResultSet query(String queryStr) {
		ResultSet set = findForResultSet(queryStr);
		return set;
	}

	public static ResultSet findForResultSet(String sql) {
		if (conn == null)
			return null;
		long time = System.currentTimeMillis();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			second = ((System.currentTimeMillis() - time) / 1000d) + "";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static int insertGysInfo(GysInfo gysInfo) {
		GysInfo gys = gysInfo;
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("insert into tb_gysInfo values('"
					+ gys.getGid() + "','" + gys.getGysmc() + "','"
					+ gys.getLxr() + "','" + gys.getTel() + "','"
					+ gys.getFax() + "','" + gys.getEmail() + "','"
					+ gys.getYhzh() + "','" + gys.getKhyh() + "','"
					+ gys.getYhzh() + "','" + gys.getAddress() + "')");
			System.out.println(count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 删除供应商信息
	public static int delGysInfo(String gid) {
		// GysInfo gys = gysInfo;
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("delete from tb_gysInfo where gid='"
					+ gid + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

	// 更新供应商信息
	public static int modifyGysInfo(GysInfo gysInfo) {
		Statement stmt = null;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("update tb_gysInfo set gysmc='"
					+ gysInfo.getGysmc() + "',lxr='" + gysInfo.getLxr()
					+ "',tel='" + gysInfo.getTel() + "',fax='"
					+ gysInfo.getFax() + "',email='" + gysInfo.getEmail()
					+ "',yzbm='" + gysInfo.getYzbm() + "',khyh='"
					+ gysInfo.getKhyh() + "',yhzh='" + gysInfo.getYhzh()
					+ "',address='" + gysInfo.getAddress() + "' where gid='"
					+ gysInfo.getGid() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 获取供应商信息列表
	public static List getGysInfos() {
		List gysInfoList = findForList("select * from tb_gysInfo");
		return gysInfoList;

	}

	// 获取供应商信息
	public static GysInfo getGysInfo(Item item) {
		Item it = item;
		String where = null;
		if (it.getId() != null) {
			where = it.getId();
		}
		ResultSet rs = Dao.query("select * from tb_gysInfo where gid='"
				+ it.getId() + "'");
		GysInfo gysInfo = new GysInfo();
		try {
			if (rs != null && rs.next()) {
				gysInfo.setGid(rs.getString("gid").trim());
				gysInfo.setGysmc(rs.getString("gysmc").trim());
				gysInfo.setLxr(rs.getString("lxr").trim());
				gysInfo.setTel(rs.getString("tel").trim());
				gysInfo.setFax(rs.getString("fax").trim());
				gysInfo.setEmail(rs.getString("email").trim());
				gysInfo.setYzbm(rs.getString("yzbm").trim());
				gysInfo.setKhyh(rs.getString("khyh").trim());
				gysInfo.setYhzh(rs.getString("yhzh").trim());
				gysInfo.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gysInfo;

	}

	// 添加药品信息
	public static int insertDrugInfo(DrugInfo drugInfo) {
		DrugInfo drug = drugInfo;
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("insert into tb_drugInfo values('"
					+ drug.getDid() + "','" + drug.getYpmc() + "','"
					+ drug.getXsj() + "','" + drug.getJhj() + "','"
					+ drug.getSl() + "','" + drug.getYt() + "','"
					+ drug.getScrq() + "','" + drug.getBzq() + "','"
					+ drug.getSccj() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

	// 删除药品信息
	public static int delDrugInfo(String did) {
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("delete from tb_drugInfo where did='"
					+ did + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 修改药品信息
	public static int modifyDrugInfo(DrugInfo drugInfo) {
		Statement stmt = null;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("update tb_drugInfo set xsj='"
					+ Float.valueOf(drugInfo.getXsj()).toString().trim()
					+ "',jhj='"
					+ Float.valueOf(drugInfo.getJhj()).toString().trim()
					+ "',sl='"
					+ Integer.valueOf(drugInfo.getSl()).toString().trim()
					+ "',scrq='" + drugInfo.getScrq() + "',bzq='"
					+ drugInfo.getBzq() + "',sccj='" + drugInfo.getSccj()
					+ "' where did='" + drugInfo.getDid() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 获取药品信息
	public static List getDrugInfos() {
		List<List> list = Dao.findForList("select * from tb_drugInfo");
		return list;
	}

	// 获取单个药品信息
	public static DrugInfo getDrugInfo(Item item) {
		String where = null;
		if (item.getId() != null) {
			where = item.getId();
		}
		ResultSet rs = Dao.query("select * from tb_drugInfo where did='"
				+ where + "'");
		DrugInfo drugInfo = new DrugInfo();
		try {
			if (rs.next() && rs != null) {
				drugInfo.setDid(item.getId().trim());
				drugInfo.setYpmc(rs.getString("ypmc").trim());
				drugInfo.setXsj(Float.parseFloat(rs.getString("xsj").trim()));
				drugInfo.setJhj(Float.parseFloat(rs.getString("jhj").trim()));
				drugInfo.setSl(Integer.parseInt(rs.getString("sl").trim()));
				drugInfo.setYt(rs.getString("yt").trim());
				drugInfo.setScrq(rs.getString("scrq").trim());
				drugInfo.setBzq(rs.getString("bzq").trim());
				drugInfo.setSccj(rs.getString("sccj").trim());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return drugInfo;

	}

	// 插入员工信息
	public static int insertEmpInfo(EmpInfo empInfo) {
		EmpInfo emp = empInfo;
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("insert into tb_empInfo values('"
					+ emp.getEid() + "','" + emp.getEname() + "','"
					+ emp.getSex() + "','" + emp.getAge() + "','"
					+ emp.getTel() + "','" + emp.getXl() + "','" + emp.getZhi()
					+ "','" + emp.getAddress() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

	// 获取员工列表
	public static List getEmpInfos() {
		List<List> empInfoList = findForList("select * from tb_empInfo");
		return empInfoList;
	}

	// 获取员工信息
	public static EmpInfo getEmpInfo(Item item) {
		String where = null;

		if (item.getId() != null) {
			where = item.getId();
		}
		ResultSet rs = Dao.query("select * from tb_empInfo where eid='" + where
				+ "'");
		EmpInfo empInfo = new EmpInfo();
		try {
			if (rs.next() && rs != null) {
				empInfo.setEid(item.getId().trim());
				empInfo.setEname(rs.getString("ename").trim());
				empInfo.setSex(rs.getString("sex").trim());
				empInfo.setAge(Integer.parseInt(rs.getString("age").trim()));
				empInfo.setTel(rs.getString("tel").trim());
				empInfo.setXl(rs.getString("xl").trim());
				empInfo.setZhi(rs.getString("zhiwu").trim());
				empInfo.setAddress(rs.getString("address").trim());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empInfo;
	}

	// 删除员工信息
	public static int delEmpInfo(String eid) {
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("delete from tb_empInfo where eid='"
					+ eid + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 更新员工信息
	public static int modifyEmpInfo(EmpInfo empInfo) {
		Statement stmt = null;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("update tb_empInfo set ename='"
					+ empInfo.getEname() + "',sex='" + empInfo.getSex()
					+ "',age='" + empInfo.getAge() + "',tel='"
					+ empInfo.getTel() + "',xl='" + empInfo.getXl()
					+ "',zhiwu='" + empInfo.getZhi() + "',address='"
					+ empInfo.getAddress() + "' where eid='" + empInfo.getEid()
					+ "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 插入客户信息
	public static int insertEmpInfo(CusInfo cusInfo) {
		CusInfo cus = cusInfo;
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("insert into tb_cusInfo values('"
					+ cus.getCid() + "','" + cus.getCname() + "','"
					+ cus.getLxr() + "','" + cus.getSex() + "','"
					+ cus.getAge() + "','" + cus.getTel() + "','"
					+ cus.getEmail() + "','" + cus.getKhyh() + "','"
					+ cus.getYhzh() + "','" + cus.getAddress() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int modifyCusInfo(CusInfo cusInfo) {
		Statement stmt = null;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("update tb_cusInfo set cname='"
					+ cusInfo.getCname() + "',lxr='" + cusInfo.getLxr()
					+ "',sex='" + cusInfo.getSex() + "',age='"
					+ cusInfo.getAge() + "',tel='" + cusInfo.getTel()
					+ "',email='" + cusInfo.getEmail() + "',khyh='"
					+ cusInfo.getKhyh() + "',yhzh='" + cusInfo.getYhzh()
					+ "',address='" + cusInfo.getAddress() + "' where cid='"
					+ cusInfo.getCid() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 获取客户信息列表
	public static List getCusInfos() {
		List list = Dao.findForList("select * from tb_cusInfo");
		return list;
	}

	// 获取单个客户信息
	public static CusInfo getCusInfo(Item item) {
		String where = null;
		if (item.getId() != null) {
			where = item.getId();
		}
		ResultSet rs = Dao.query("select * from tb_cusInfo where cid='" + where
				+ "'");
		CusInfo cusInfo = new CusInfo();
		try {
			if (rs.next() && rs != null) {
				cusInfo.setCid(rs.getString("cid").trim());
				cusInfo.setCname(rs.getString("cname").trim());
				cusInfo.setLxr(rs.getString("lxr").trim());
				cusInfo.setSex(rs.getString("sex").trim());
				cusInfo.setAge(Integer.parseInt(rs.getString("age").trim()));
				cusInfo.setTel(rs.getString("tel").trim());
				cusInfo.setEmail(rs.getString("email").trim());
				cusInfo.setKhyh(rs.getString("khyh").trim());
				cusInfo.setYhzh(rs.getString("yhzh").trim());
				cusInfo.setAddress(rs.getString("address").trim());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cusInfo;
	}

	public static int delCusInfo(String cid) {
		Statement stmt = null;

		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("delete from tb_CusInfo where cid='"
					+ cid + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

	public static List findForList(String sql) {
		List<List> list = new ArrayList<List>();
		ResultSet rs = findForResultSet(sql);
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				List<String> row = new ArrayList<String>();
				for (int i = 1; i <= colCount; i++) {
					String str = rs.getString(i);
					if (str != null && !str.isEmpty())
						str = str.trim();
					row.add(str);
				}
				list.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取入库单的最大ID，即最大入库票号
	public static String getRuKuMainMaxId(Date date) {
		return getMainTypeTableMaxId(date, "tb_ruku_main", "RK", "rid");
	}

	// 获取入库单的最大ID，即最大入库票号
	public static String getSellMainMaxId(Date date) {
		return getMainTypeTableMaxId(date, "tb_sell_main", "XS", "sid");
	}

	// 获取更类主表最大ID
	private static String getMainTypeTableMaxId(Date date, String table,
			String idChar, String idName) {
		String dateStr = date.toString().replace("-", "");
		String id = idChar + dateStr;
		String sql = "select max(" + idName + ") from " + table + " where "
				+ idName + " like '" + id + "%'";
		ResultSet set = query(sql);
		String baseId = null;
		try {
			if (set.next())
				baseId = set.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		baseId = baseId == null ? "000" : baseId.substring(baseId.length() - 3);
		int idNum = Integer.parseInt(baseId) + 1;
		id += String.format("%03d", idNum);
		return id;
	}

	public static boolean insert(String sql) {
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int update(String sql) {
		int result = 0;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 获取库存商品信息
	public static KuCunInfo getKucun(Item item) {
		ResultSet rs = findForResultSet("select * from tb_kucun where did='"
				+ item.getId() + "'");
		KuCunInfo kucun = new KuCunInfo();
		try {
			if (rs.next()) {
				kucun.setDid(rs.getString("did"));
				kucun.setRks(Integer.parseInt(rs.getString("rks")));
				kucun.setXss(Integer.parseInt(rs.getString("xss")));
				kucun.setKcs(Integer.parseInt(rs.getString("kcs")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kucun;
	}

	// 在事务中添加入库信息
	public static boolean insertDrugRukuInfo(RuKuMainInfo rukuMain) {
		boolean flag = false;
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			System.out.println(rukuMain.getRkje());
			// 添加入库主表记录
			insert("insert into tb_ruku_main values('" + rukuMain.getRid()
					+ "','" + rukuMain.getPzs() + "','" + rukuMain.getRks()
					+ "','" + rukuMain.getRkje() + "','" + rukuMain.getRkrq()
					+ "','" + rukuMain.getJsr() + "','" + rukuMain.getGid()
					+ "')");
			Set<RuKuDetailInfo> rkDetails = rukuMain.getTabRukuDetails();
			for (Iterator<RuKuDetailInfo> iter = rkDetails.iterator(); iter
					.hasNext();) {
				RuKuDetailInfo details = iter.next();
				// 添加入库详细表记录
				insert("insert into tb_ruku_detaile values('"
						+ rukuMain.getRid() + "','" + details.getDid() + "','"
						+ details.getSl() + "','" + details.getJhj() + "','"
						+ details.getJe() + "')");

				// 添加或修改库存表记录
				Item item = new Item();
				item.setId(details.getDid());
				DrugInfo drugInfo = getDrugInfo(item);
				if (drugInfo.getDid() != null && !drugInfo.getDid().isEmpty()) {
					KuCunInfo kucun = getKucun(item);
					if (kucun.getDid() == null || kucun.getDid().isEmpty()) {
						insert("insert into tb_kucun values('"
								+ details.getDid() + "','" + details.getSl()
								+ "','" + 0 + "','" + details.getSl() + "')");
					} else {
						int rks = kucun.getRks() + details.getSl();
						int kcs = kucun.getKcs() + details.getSl();
						update("update tb_kucun set rks='" + rks + "',kcs='"
								+ kcs + "' where did='" + kucun.getDid() + "'");
					}
				}

			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
			flag = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
	}

	// 在事务中添加销售信息
	public static boolean insertDrugSellInfo(SellMainInfo sellMain) {
		boolean flag = false;
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			// 添加销售主表记录
			insert("insert into tb_sell_main values('" + sellMain.getSid()
					+ "','" + sellMain.getCid() + "','" + sellMain.getXssl()
					+ "','" + sellMain.getXsje() + "','" + sellMain.getXsrq()
					+ "','" + sellMain.getJsr() + "','" + sellMain.getPzs()
					+ "')");
			Set<SellDetailInfo> sellDetails = sellMain.getTabSellDetails();
			for (Iterator<SellDetailInfo> iter = sellDetails.iterator(); iter
					.hasNext();) {
				SellDetailInfo details = iter.next();
				// 添加入库详细表记录
				insert("insert into tb_sell_detaile values('"
						+ sellMain.getSid() + "','" + details.getDid() + "','"
						+ details.getSl() + "','" + details.getXsj() + "','"
						+ details.getJe() + "')");

				// 添加或修改库存表记录
				Item item = new Item();
				item.setId(details.getDid());
				DrugInfo drugInfo = getDrugInfo(item);
				if (drugInfo.getDid() != null && !drugInfo.getDid().isEmpty()) {
					KuCunInfo kucun = getKucun(item);
					if (kucun.getDid() != null && !kucun.getDid().isEmpty()) {
						int xss = kucun.getXss() + details.getSl();
						int kcs = kucun.getKcs() - details.getSl();
						update("update tb_kucun set xss='" + xss + "',kcs='"
								+ kcs + "' where did='" + kucun.getDid() + "'");
					}
				}

			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
			flag = true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
	}

	// 获取入库明细表
	public static List getRuKuDetailInfos() {
		List rukudetailes = findForList("select * from tb_ruku_detaile");
		return rukudetailes;

	}

	// 获取单个入库明细信息
	public static RuKuDetailInfo getRuKuDetailInfo(Item item) {
		String where = null;
		if (item.getId() != null) {
			where = item.getId();
		}
		ResultSet rs = Dao.query("select * from tb_ruku_detaile where rid='"
				+ where + "'");
		RuKuDetailInfo rkDetailInfo = new RuKuDetailInfo();
		try {
			if (rs.next() && rs != null) {
				rkDetailInfo.setRid(rs.getString("rid").trim());
				rkDetailInfo.setDid(rs.getString("did").trim());
				rkDetailInfo.setSl(Integer.parseInt(rs.getString("sl").trim()));
				rkDetailInfo.setJhj(Float
						.parseFloat(rs.getString("jhj").trim()));
				rkDetailInfo.setJe(Float.parseFloat(rs.getString("je").trim()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rkDetailInfo;
	}

	// 获取销售明细表
	public static List getSellDetailInfos() {
		List selldetailes = findForList("select * from tb_sell_detaile");
		return selldetailes;

	}

	// 获取单个销售记录
	public static SellDetailInfo getSellDetailInfo(Item item) {
		String where = null;
		if (item.getId() != null) {
			where = item.getId();
		}
		ResultSet rs = Dao.query("select * from tb_sell_detaile where sid='"
				+ where + "'");
		SellDetailInfo sellDetailInfo = new SellDetailInfo();
		try {
			if (rs.next() && rs != null) {
				sellDetailInfo.setSid(rs.getString("sid").trim());
				sellDetailInfo.setDid(rs.getString("did").trim());
				sellDetailInfo.setSl(Integer
						.parseInt(rs.getString("sl").trim()));
				sellDetailInfo.setXsj(Float.parseFloat(rs.getString("xsj")
						.trim()));
				sellDetailInfo.setJe(Float
						.parseFloat(rs.getString("je").trim()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sellDetailInfo;
	}

	// 获取库存表
	public static List getKuCunInfos() {
		List kucuns = findForList("select * from tb_kucun");
		return kucuns;

	}

	// 获取单个库存记录
	public static KuCunInfo getKuCunInfo(Item item) {
		String where = null;
		if (item.getId() != null) {
			where = item.getId();
		}
		ResultSet rs = Dao.query("select * from tb_kucun where did='" + where
				+ "'");
		KuCunInfo kcInfo = new KuCunInfo();
		try {
			if (rs.next() && rs != null) {
				kcInfo.setDid(rs.getString("did").trim());
				kcInfo.setRks(Integer.parseInt(rs.getString("rks")));
				kcInfo.setXss(Integer.parseInt(rs.getString("xss").trim()));
				kcInfo.setKcs(Integer.parseInt(rs.getString("kcs").trim()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kcInfo;
	}

	// 获取用户信息
	public static UserInfo getUserInfo(String uid) {
		ResultSet rs = query("select * from tb_user where uid='" + uid + "'");
		UserInfo userInfo = new UserInfo();
		try {
			if (rs.next()) {
				userInfo.setUid(rs.getString("uid").trim());
				userInfo.setUsername(rs.getString("username").trim());
				userInfo.setPassword(rs.getString("password").trim());
				return userInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 获取用户列表
	public static List getUserInfos() {
		List list = findForList("select * from tb_user");
		return list;
	}

	// 插入用户信息
	public static int insertUserInfo(UserInfo userInfo) {
		UserInfo user = userInfo;
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("insert into tb_user values('"
					+ user.getUid() + "','" + user.getUsername() + "','"
					+ user.getPassword() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 删除用户信息
	public static int delUserInfo(String uid) {
		int count = 0;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("delete from tb_user where uid='" + uid
					+ "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

	// 修改用户信息
	public static int modifyUserInfo(UserInfo userInfo) {
		Statement stmt = null;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate("update tb_user set username='"
					+ userInfo.getUsername() + "',password='"
					+ userInfo.getPassword() + "' where uid='"
					+ userInfo.getUid() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
