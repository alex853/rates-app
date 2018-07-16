<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Daily Rates App</title>
</head>

<body>
<ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#data">Daily Rates</a></li>
    <li><a data-toggle="tab" href="#chart">Chart</a></li>
</ul>

<div class="tab-content">
    <div id="data" class="tab-pane active">
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

    <div id="chart" class="tab-pane">
        Chart is here
    </div>
</div>

</body>
</html>
