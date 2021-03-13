package controllers.followlist;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class Followlistindex
 */
@WebServlet("/followlist/index")
public class FollowlistindexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowlistindexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // セッションスコープに保存された従業員（ログインユーザ）情報を取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        // 開くページ数を取得（1ページ目は必ず例外になる）
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        // 最大件数と開始位置を指定して"getMyFollowlist"の代入条件に1つ設定して日報情報を取得
        List<Report> followlist = em.createNamedQuery("getMyFollowlist", Report.class)
                .setParameter("employee",login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        //"getMyFollowlist_Count"の代入条件に1つ設定して全件数を取得
        long followlist_count = (long)em.createNamedQuery("getMyFollowlist_Count", Long.class)
                .setParameter("employee",login_employee)
                .getSingleResult();

        em.close();

        request.setAttribute("followlist", followlist);
        request.setAttribute("followlist_count", followlist_count);
        request.setAttribute("page", page);

        // フラッシュメッセージがセッションスコープにセットされていたら
        if(request.getSession().getAttribute("flush") != null) {
            // セッションスコープ内のフラッシュメッセージをリクエストスコープに保存し、セッションスコープからは削除する
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        //followlist/index.jspを呼び出す
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/followlist/index.jsp");
        rd.forward(request, response);
    }
}