<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>いいねした人　一覧</h2>
        <h3></h3>
        <table id="pushlist_list">
            <tbody>
                <tr>
                    <th class="employee_id">氏名</th>
                    <th class="created_at">日付</th>

                </tr>
                <c:forEach var="pushlist" items="${pushlist}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="employee_id"><c:out value="${pushlist.employee.name}" /></td>
                        <td class="created_at"><fmt:formatDate value='${pushlist.created_at}' pattern='yyyy-MM-dd HH:mm:ss' /></td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${pushlist_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((pushlist_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>