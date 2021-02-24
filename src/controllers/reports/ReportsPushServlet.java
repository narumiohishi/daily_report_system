package controllers.reports;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;
/**
 * Servlet implementation class ReportsUpdateServlet
 */
@WebServlet("/reports/push")
public class ReportsPushServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsPushServlet() {
        super();
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

            int count = r.getReports_push() + 1;
            r.setReports_push(count);



                em.getTransaction().begin();

                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "いいねしました");

                response.sendRedirect(request.getContextPath() + "/reports/index");


        }
    }

