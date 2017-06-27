package com.springboot.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println(">>>>>>>>>>doGet()<<<<<<<<<<<");

		doPost(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {

		System.out.println(">>>>>>>>>>doPost()<<<<<<<<<<<");

		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = resp.getWriter();

		out.println("<html>");

		out.println("<head>");

		out.println("<title>HelloWorld</title>");

		out.println("</head>");

		out.println("<body>");

		out.println("<h1>���ǣ�MyServlet1</h1>");

		out.println("</body>");

		out.println("</html>");

	}
}