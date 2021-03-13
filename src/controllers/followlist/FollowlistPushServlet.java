package controllers.followlist;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Followlist;
import models.Report;
import utils.DBUtil;
/**
 * Servlet implementation class FollowlistPush
 */
@WebServlet("/followlist/push")
public class FollowlistPushServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowlistPushServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // パラメータから取得した該当のIDの日報を1件のみをデータベースから取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        //Followlistのインスタンスを生成
        Followlist f = new Followlist();

        // 各フィールドにデータを代入
        f.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        f.setFollow_employee((r.getEmployee()));
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        // データベースに保存
        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "フォローしました");

        //followlist/indexのページにリダイレクト
        response.sendRedirect(request.getContextPath() + "/followlist/index");
    }
}

