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
    <link rel="icon" href="pages/ico.png" type="image/png">
    <meta charset="utf-8" />
    <title>VladCarService</title>
    <script type="text/javascript" src="js/forMenu.js"></script>
</head>
<body>
<div id="wrapper"> <!--Оболочка страницы-->
    <!--Шапка сайта-->
    <div id="header">
        <!--Заголовок сайта-->
        <h1>Автомаркет</h1>
        <!--Описание (телефон)-->
        <p class="nomer"> +7 953 015 62 18</p>
        <!--Фоновая картинка в шапке сайта-->
        <img class = "logo" src="icons/логотип-без-фона.png">
        <img class = "fone" src="pages/XXL.jpg">
    </div>
    <!--Сайдбар-->
    <div id="sidebar">
        <!--меню-->
        <h3>На нашем сайте</h3>
        <!--Картинки маркеров меню (галочки)-->
        <p><img class="marcer" src="pages/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/list">Автомобили</a></p>
        <p><img class="marcer" src="pages/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/all_users">Список пользователей</a></p>
        <p><img class="marcer" src="pages/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/list_of_emp">Сотрудники</a></p>
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
            <p><img class="marcer" src="pages/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/my_cars">Мои объявления</a></p>
            <p><img class="marcer" src="pages/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/my_likes">Избранные</a></p>
            <p><img class="marcer" src="pages/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/add">Добавить авто</a></p>
            <c:if test="${!sessionScope.get('status').equals('default')}">
                <p><img class="marcer" src="pages/icon_green_checkbox.png" width="10" height="10"><a href="${pageContext.servletContext.contextPath}/list_of_reports">Жалобы</a></p>
            </c:if>
            <form method="post" action="${pageContext.servletContext.contextPath}/exit">
                <button type = submit>Выход</button>
            </form>

        </c:if>

    </div>
    <!--Основной контент (статья)-->
    <div id="content">
        <!--Картинка слева-->
        <img class="left" src="pages/left_page.jpg">
        <!--Заголовок статьи-->
        <h3>О нашей работе</h3>
        <!--Текст статьи-->
        <p>
            Добро пожаловать на сайт автосервиса VCS - вашего надежного партнера в мире подержанных автомобилей!
        </p>
        <p>
            Наша команда профессионалов с многолетним опытом работы в автомобильной индустрии гордится тем, что предлагает вам широкий выбор качественных и проверенных б/у автомобилей. Мы тщательно отбираем каждый экземпляр, проводим всестороннюю диагностику и восстанавливаем их до безупречного состояния, чтобы вы могли быть уверены в безопасности и надежности своей покупки.
        </p>
        <p>
            В нашем автосервисе вы найдете автомобили на любой вкус и бюджет - от компактных городских хэтчбеков до просторных семейных седанов и внедорожников. Мы гордимся тем, что предлагаем не только отличные цены, но и высокий уровень обслуживания, включая профессиональную консультацию, тест-драйв и помощь в оформлении документов.
        </p>
        <p>
            Наша миссия - сделать процесс покупки подержанного автомобиля максимально комфортным и безопасным для наших клиентов. Мы верим, что каждый человек заслуживает возможность приобрести надежное и качественное транспортное средство по доступной цене. Именно поэтому мы тщательно отбираем и проверяем каждый автомобиль в нашем ассортименте, чтобы вы могли быть уверены в его исправности и долговечности.
        </p>

            <!--Картинка справа-->
        <img class="right" src="pages/right_page.jpg">

        <p>
            Приглашаем вас посетить наш автосервис и убедиться в нашем профессионализме и внимательном отношении к каждому клиенту. Мы гарантируем, что вы найдете здесь именно тот автомобиль, который идеально подойдет вам по всем параметрам. Добро пожаловать в VCS - ваш надежный партнер в мире подержанных автомобилей!
        </p>
    </div>
    <!--Запрет наплывания-->
    <div class="clear"></div>
    <!--Подвал-->
    <div id="footer">
        <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
        <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
        <img class = "logo_bottom" src="icons/логотип-без-фона.png">
    </div>
</div>
</body>
</html>
