<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.09.2024
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="styles/menu.css">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8" />
    <title>Код блочного сайта</title>
    <script type="text/javascript" src="js/forMenu.js"></script>
</head>
<body>
<div id="wrapper"> <!--Оболочка страницы-->
    <!--Шапка сайта-->
    <div id="header">
        <!--Заголовок сайта-->
        <h1>Автосервис</h1>
        <!--Описание (телефон)-->
        <p class="nomer"> +7 953 015 62 18</p>
        <!--Фоновая картинка в шапке сайта-->
        <img src="https://avatars.mds.yandex.net/get-altay/2433982/2a000001742b0ef9b257b2a6cec30fce101f/XXL">
    </div>
    <!--Сайдбар-->
    <div id="sidebar">
        <!--меню-->
        <h3>На нашем сайте</h3>
        <!--Картинки маркеров меню (галочки)-->
        <p><img class="marcer" src="https://tracers.ru/wp-content/uploads/2020/05/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/list">Автомобили</a></p>
        <p><img class="marcer" src="https://tracers.ru/wp-content/uploads/2020/05/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/add">Добавить авто</a></p>
        <p><img class="marcer" src="https://tracers.ru/wp-content/uploads/2020/05/icon_green_checkbox.png" width="10" height="10">Сотрудники</p>
        <!--Прямая синяя линия-->
        <hr width="50" color="#037FFC" size="5">
        <!--Общая информация в сайдбаре-->
        <c:if test="${sessionScope.get('username') == null}">
            <h3>Аутентификация</h3>
            <form method="post" action="${pageContext.servletContext.contextPath}/profile">
                <label for="name">Имя пользователя:
                    <input type="text" id="name" name="name" placeholder="Your name.." required>
                </label>
                <label for="password">Пароль:
                    <input type="password" id="password" name="password" placeholder="Your password.." required>
                </label>
                <input type="submit" value="Войти">
            </form>
            <button onclick='openForm()'>Регистрация</button>
            <div id='req'>
                <form role="form" action="${pageContext.servletContext.contextPath}/registr" autocomplete="off" method="POST">
                    <label>Имя пользователя:</label>
                    <input type="text" name="nameReq">
                    <label>Пароль:</label>
                    <input type="password" name="passwordReq">
                    <label>Номер телефона(Вводить без +7):</label>
                    <input type="text" name="phoneReq" pattern="[7-9]{1}[0-9]{9}">
                    <button type="submit" class="btn btn-success">Зарегистрироваться</button>
                </form>
                <button onclick='closeForm()'>Закрыть</button>
            </div>
        </c:if>
        <c:if test="${sessionScope.get('username') != null}">
            <h3>Здравствуйте! ${sessionScope.get('username')}</h3>
            <p><img class="marcer" src="https://tracers.ru/wp-content/uploads/2020/05/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/my_cars">Мои объявления</a></p>
            <c:if test="${sessionScope.get('status').equals('admin')}">
                <p><img class="marcer" src="https://tracers.ru/wp-content/uploads/2020/05/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/all_users">Список пользователей</a></p>
            </c:if>
            <form method="post" action="${pageContext.servletContext.contextPath}/exit">
                <button type = submit>Выход</button>
            </form>

        </c:if>

    </div>
    <!--Основной контент (статья)-->
    <div id="content">
        <!--Картинка слева-->
        <img class="left" src="https://i.pinimg.com/736x/3e/51/56/3e51563a896f1698da8e7dad14a4510d.jpg">
        <!--Заголовок статьи-->
        <h3>О нашей работе</h3>
        <!--Текст статьи-->
        <p>Какой-то рандомный текст</p>
        <p>Мне 55 лет и я рад приветствовать Вас на своём сайте.
            Этот сайт первый, который я разработал самостоятельно,
            а до этого умел только входить в интернет.
            Почему я решил его сделать?</p>
        <p>За те 3 месяца, пока
            разбирался в сайтостроении и создавал этот ресурс
            обнаружилось, что авторы руководств по созданию
            сайтов считают многие нюансы само собой разумеющимися
            и не обращают на них внимание.</p>
        <p>А мне, учитывая
            возраст и «опыт», было не просто понять как раз эти
            нюансы, они отнимали больше всего времени. И я решил
            написать свой материал, так что-бы другим было легче
            сориентироваться в потоке новой информации.</p>
        <!--Картинка справа-->
        <img class="right" src="https://avatars.mds.yandex.net/i?id=7f6cbd1f78687a51c619e5a8a769d2f6bea8dbd7-7760813-images-thumbs&n=13">

        <p>Здесь
            «разжеваны» все мелочи сопровождающие создание сайта,
            мимо которых обычно проскакивают другие авторы.
            Если вам что-то будет непонятно, спрашивайте, для
            меня нет «глупых» вопросов.
            Читайе и создавайте свой сайт самостоятельно, каким
            бы ни был Ваш возраст и стаж работы на компьютере.</p>
        <p>Уверен, у Вас получится еще лучше и уж точно, в
            несколько раз быстрее, чем у меня.</p>
    </div>
    <!--Запрет наплывания-->
    <div class="clear"></div>
    <!--Подвал-->
    <div id="footer">
        <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
        <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>

    </div>
</div>
</body>
</html>
