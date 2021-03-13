package controllers.reports;
import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Pushlist;
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

            // 該当のIDの日報を1件のみをデータベースから取得
            Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

            //いいね数が1ずつ増えるようにフィールドにデータを代入
            int count = r.getReports_push() + 1;
            r.setReports_push(count);

            //Pushlistのインスタンスを生成
            Pushlist p = new Pushlist();

            // 各フィールドにデータを代入
            p.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
            p.setReport(r);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            p.setCreated_at(currentTime);
            p.setUpdated_at(currentTime);

            // データベースに保存
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "いいねしました");

            // reports/indexのページにリダイレクト
            response.sendRedirect(request.getContextPath() + "/reports/index");
        }
    }

