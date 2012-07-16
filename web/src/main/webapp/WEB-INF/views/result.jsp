<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<div>
    <table summary="Hier finden Sie das Suchresultat">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Typ</th>
            <th scope="col">Erstelldatum</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doc" items="${documents}">
            <tr>
                <td>${doc} </td>
                <td>hjdsaf</td>
                <td>hjdsaf</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>