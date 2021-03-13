package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesDestroyServlet
 */
@WebServlet("/employees/destroy")
public class EmployeesDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        //CSRF対策のチェックを行う
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // セッションスコープから従業員のIDを取得して
            // 該当のIDの従業員情報を1件のみをデータベースから取得
            Employee e = em.find(Employee.class, (Integer)(request.getSession().getAttribute("employee_id")));

            //delete_flag が1になっている従業員情報は削除される
            e.setDelete_flag(1);
            e.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            // データベースを更新
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "削除が完了しました。");
            // employees/indexページへリダイレクト//
            response.sendRedirect(request.getContextPath() + "/employees/index");
        }
    }

}