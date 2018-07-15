<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Daily Rates App</title>
</head>

<body>
<content tag="nav">
    <li>
        <a href="#">Daily Rates</a>
    </li>
    <li>
        <a href="#">Chart</a>
    </li>
</content>

<div id="content" role="main">
    <section class="row colset-2-its">
        <h1>Daily Rates</h1>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Date</th>
                <th scope="col">USD/RUB</th>
                <th scope="col">EUR/RUB</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${dailyRateSets}">
                <tr>
                    <th scope="row">${it.date}</th>
                    <td>${it.usdRate}</td>
                    <td>${it.eurRate}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </section>
</div>

</body>
</html>
