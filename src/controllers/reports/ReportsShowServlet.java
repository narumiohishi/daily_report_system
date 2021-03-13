package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // 該当のIDの日報を1件のみをデータベースから取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        // セッションスコープに保存された従業員（ログインユーザ）情報を取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        // "getMyPushlist_pushCount"の代入条件を2つ設定して指定の日報に対してのいいねの数を取得
        long Pushlist_pushCount = (long)em.createNamedQuery("getMyPushlist_pushCount", Long.class)
                .setParameter("employee", login_employee)
                .setParameter("report", r)
                .getSingleResult();

        // "getMyFollowlist_list"の代入条件を2つ設定してログインユーザの指定の日報作成者にフォローの数を取得
        long Followlist_list = (long)em.createNamedQuery("getMyFollowlist_list", Long.class)
                .setParameter("employee", login_employee)
                .setParameter("follow_employee", r.getEmployee())
                .getSingleResult();

        em.close();

        //各取得データをリクエストスコープにセットして/reports/show.jspを呼び出す
        request.setAttribute("report", r);
        request.setAttribute("pushlist_pushCount", Pushlist_pushCount);
        request.setAttribute("followlist_list", Followlist_list);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}