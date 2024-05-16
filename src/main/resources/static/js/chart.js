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
    var labels = balanceHistory.map(entry => entry.dateAndTime);
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
        }
    };

    var ctx = document.getElementById('balanceChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: data,
        options: options
    });
}
