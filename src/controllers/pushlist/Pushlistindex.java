package controllers.pushlist;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Pushlist;
import models.Report;
import utils.DBUtil;


/**
 * Servlet implementation class Pushlistindex
 */
@WebServlet("/pushlist/index")
public class Pushlistindex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pushlistindex() {
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

        // 開くページ数を取得（1ページ目は必ず例外になる）
        int page ;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page=1;
        }

        // 最大件数と開始位置を指定して"getMyAllPushlist"の代入条件を1つ設定して日報情報を取得
        List<Pushlist> pushlist = em.createNamedQuery("getMyAllPushlist", Pushlist.class)
                                  .setParameter("report", r)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        //"getMyPushlistCount"の代入条件を1つ設定して全件数を取得
        long Pushlist_count = (long)em.createNamedQuery("getMyPushlistCount", Long.class)
                                     .setParameter("report", r)
                                     .getSingleResult();

        em.close();

        //各取得データをリクエストスコープにセットして/pushlist/index.jspを呼び出す
        request.setAttribute("pushlist", pushlist);
        request.setAttribute("pushlist_count", Pushlist_count);
        request.setAttribute("page", page);
        request.setAttribute("report", r);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/pushlist/index.jsp");
        rd.forward(request, response);
    }

}