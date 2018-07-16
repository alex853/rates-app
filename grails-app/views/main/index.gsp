<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Daily Rates App</title>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>
</head>

<body>
<ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#data">Daily Rates</a></li>
    <li><a data-toggle="tab" href="#chart">Chart</a></li>
</ul>

<div class="tab-content">
    <div id="data" class="tab-pane active">
        <section class="row colset-2-its">
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
                        <td style="${it.usdRate == usdMinRate ? 'background-color: yellow;' : (it.usdRate == usdMaxRate ? 'background-color: pink;' : '')}">${it.usdRate}</td>
                        <td style="${it.eurRate == eurMinRate ? 'background-color: yellow;' : (it.eurRate == eurMaxRate ? 'background-color: pink;' : '')}">${it.eurRate}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </section>
    </div>

    <div id="chart" class="tab-pane">
        <section class="row colset-2-its">
            <canvas id="canvas"></canvas>
        </section>
    </div>
</div>

<script>
    var labels = [];
    var usd = [];
    var eur = [];

    <g:each in="${dailyRateSets}">
    labels.push('${it.date}');
    usd.push(${it.usdRate});
    eur.push(${it.eurRate});
    </g:each>

    var config = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'USD/RUB',
                backgroundColor: '#80DD40',
                borderColor: '#A0A0A0',
                data: usd,
                fill: false
            }, {
                label: 'EUR/RUB',
                fill: false,
                backgroundColor: '#4080DD',
                borderColor: '#A0A0A0',
                data: eur,
                fill: false
            }]
        },
        options: {
            responsive: true,
            title: {
                display: false,
                text: 'USD/RUB and EUR/RUB chart'
            },
            tooltips: {
                mode: 'index',
                intersect: false
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Date'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Rate'
                    }
                }]
            }
        }
    };

    window.onload = function () {
        var ctx = document.getElementById('canvas').getContext('2d');
        window.myLine = new Chart(ctx, config);
    };
</script>

</body>
</html>
