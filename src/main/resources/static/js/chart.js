document.addEventListener('DOMContentLoaded', async function() {
    try {
        const response = await fetch('/balanceHistory');
        if (!response.ok) {
            throw new Error('Failed to fetch balance history');
        }
        const balanceHistory = await response.json();
        console.log('Received balance history:', balanceHistory);
        updateBalanceChart(balanceHistory);
    } catch (error) {
        console.error('Error fetching balance history:', error);
    }
});

function updateBalanceChart(balanceHistory) {
    var labels = balanceHistory.map(entry => formatDate(entry.dateAndTime));
    var dataPoints = balanceHistory.map(entry => entry.balanceAmount);

    var data = {
        labels: labels,
        datasets: [{
            label: 'Balance',
            data: dataPoints,
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
        }]
    };

    var options = {
        scales: {
            y: {
                beginAtZero: true
            }
        },
        plugins: {
            zoom: {
                pan: {
                    enabled: true,
                    mode: 'x',
                    rangeMin: {
                        x: null
                    },
                    rangeMax: {
                        x: null
                    }
                },
                zoom: {
                    wheel: {
                        enabled: true
                    },
                    pinch: {
                        enabled: true
                    },
                    mode: 'x',
                    rangeMin: {
                        x: null
                    },
                    rangeMax: {
                        x: null
                    }
                }
            }
        }
    };

    var ctx = document.getElementById('balanceChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: data,
        options: options
    });

    // Add event listener for chart type toggle button
    document.getElementById('toggleChartType').addEventListener('click', function() {
        var chartType = myChart.config.type === 'line' ? 'bar' : 'line';
        myChart.config.type = chartType;

        // Update color and axis label based on chart type
        if (chartType === 'bar') {
            myChart.data.datasets[0].backgroundColor = 'rgb(75,191,191)';
            myChart.options.scales.y.title.text = 'Balance Amount (Bars)';
        } else {
            myChart.data.datasets[0].backgroundColor = '';
            myChart.options.scales.y.title.text = 'Balance Amount (Line)';
        }

        myChart.update();
    });

    // Функция за форматиране на датата
    function formatDate(dateString) {
        var date = new Date(dateString);
        return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
    }
}
