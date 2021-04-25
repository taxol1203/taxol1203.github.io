MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookDao bookDao = BookDaoImpl.getInstance();
    private UserDao userDao= UserDaoImpl.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("act");
		System.out.println("action = " + action);
		switch(action) {
			case "registForm":
				mvregist(request,response);
				break;
			case "regist":
				regist(request,response);
				break;
			case "list":
				list(request,response);
				break;
			case "login":
				login(request,response);
				break;
			case "logout":
				logout(request,response);
				break;
		}
	}
	private void mvregist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/regist.jsp").forward(request, response);
	}
	protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isbn=request.getParameter("isbn");
		String title=request.getParameter("title");
		String author=request.getParameter("author");
		int price=Integer.parseInt(request.getParameter("price"));
		String desc=request.getParameter("desc");
		
		Book book=new Book(isbn, title, author, price, desc);
		System.out.println(book);
		try {
			bookDao.insert(book);
			
			Cookie c=new Cookie("endbook", isbn);
			c.setMaxAge(30*60);
			response.addCookie(c);

			request.setAttribute("book", book);
			RequestDispatcher rd=request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		}catch(SQLIntegrityConstraintViolationException e) {
			request.setAttribute("msg", "도서 등록 실패: "+isbn+"은 이미 사용중입니다.");
			RequestDispatcher rd=request.getRequestDispatcher("/regist.jsp");
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchField=request.getParameter("searchField");
		String searchText =request.getParameter("searchText");
		if(searchField==null) searchField="";
		if(searchText ==null) searchText ="";
		
		try {
			List<Book> books=null;
			switch(searchField) {
				case "ISBN":
					books = new ArrayList<>();
					books.add(bookDao.select("%"+searchText+"%"));
					break;
				default:
					books= bookDao.select();
					searchText ="";
			}
			
			request.setAttribute("books", books);
			request.setAttribute("searchField", searchField);
			request.setAttribute("searchText",  searchText);
			RequestDispatcher rd=request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		
		try {
			User user= userDao.select(id,pass);
			if(user!=null && user.getPass().equals(pass)) {
				HttpSession session=request.getSession();
				session.setAttribute("loginUser", user);
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}else{
				request.setAttribute("msg", "로그인 실패");
				RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
			}		
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
	}
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session!=null) session.invalidate();
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
}


-----------// userDaoImpl
public class UserDaoImpl implements UserDao {
	private static UserDaoImpl instance=new UserDaoImpl();
	private UserDaoImpl() { }
	public static UserDaoImpl getInstance() {
		return instance;
	}	
	
	private DBUtil util=DBUtil.getUtil();
	
	@Override
	public User select(String id, String pass) throws SQLException {
		String sql="select * from user where id = ?";
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		User user=null;
		try{
			con=util.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs=ps.executeQuery();
			if(rs.next()) {
				user=new User(rs.getString("id"),rs.getString("pass"),rs.getString("name"));
			}
		}finally{
			util.close(rs,ps,con);
		}		
		return user;
	}
}


/// bookdaoImpl

public class BookDaoImpl implements BookDao {

	private static BookDao instance = new BookDaoImpl();
	private BookDaoImpl() {	}
	public static BookDao getInstance() {
		return instance;
	}
	
	private DBUtil util = DBUtil.getUtil();		// cc
	
	@Override
	public void insert(Book product) throws SQLException {
		String sql="insert into book values (?, ?, ?, ?, ?,'')";
		
		Connection con=null;
		PreparedStatement ps=null;
		
		try{
			con=util.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, product.getIsbn());
			ps.setString(2, product.getTitle());
			ps.setString(3, product.getAuthor());
			ps.setInt(4, product.getPrice());
			ps.setString(5, product.getDesc());
			System.out.println(ps);
			ps.executeUpdate();
			//result=ps.executeUpdate();
		}finally{
			util.close(ps,con);
		}
	}

	@Override
	public Book select(String pCode) throws SQLException {
		String sql="select * from book where isbn like ?";
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		Book book=null;
		try{
			con=util.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, pCode);
			
			rs=ps.executeQuery();
			if(rs.next()){
				book=new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("author"), rs.getInt("price"), rs.getString("describ"));
			}
		}finally{
			util.close(rs,ps,con);
		}
		return book;
	}

	@Override
	public List<Book> select() throws SQLException {
		String sql = "select * from book";
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		List<Book> books=new ArrayList<Book>();
		try{
			con=util.getConnection();
			ps=con.prepareStatement(sql);
			
			rs=ps.executeQuery();
			while(rs.next()){
				Book book=new Book(rs.getString("isbn"), rs.getString("title"), rs.getString("author"), rs.getInt("price"), rs.getString("describ"));
				books.add(book);
			}
		}finally{
			util.close(rs,ps,con);
		}
		return books;
	}

}
