document.addEventListener('DOMContentLoaded', function() {
    // Дефинирайте данните за графиката
    var data = {
        labels: ["Date1", "Date2", "Date3"], // Маркирайте осите с дати или времеви метки
        datasets: [{
            label: 'Balance',
            data: [1000, 1200, 800], // Данни за баланса във времето
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
        }]
    };

    // Определете опциите на графиката
    var options = {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    };

    // Използвайте Chart.js за рисуване на графиката
    var ctx = document.getElementById('balanceChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: data,
        options: options
    });
});