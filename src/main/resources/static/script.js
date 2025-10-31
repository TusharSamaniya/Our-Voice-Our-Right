const BASE_URL = "http://localhost:8080/api/performance";
let charts = {};
let allData = [];

document.addEventListener("DOMContentLoaded", () => {
  const districtSelect = document.getElementById("districtSelect");
  const yearSelect = document.getElementById("yearSelect");
  const filterBtn = document.getElementById("filterBtn");
  const resetBtn = document.getElementById("resetBtn");
  const tableBody = document.getElementById("tableBody");

  // Initialize charts
  initializeCharts();

  // Load dropdown options
  loadDistricts();
  loadYears();

  // Load all data initially
  loadAllData();

  // Event listeners
  filterBtn.addEventListener("click", () => {
    const district = districtSelect.value;
    const year = yearSelect.value;
    if (district && year) {
      fetchData(`${BASE_URL}/${district}/${year}`);
    } else if (district) {
      fetchData(`${BASE_URL}/${district}`);
    } else {
      loadAllData();
    }
  });

  resetBtn.addEventListener("click", () => {
    districtSelect.value = "";
    yearSelect.value = "";
    loadAllData();
  });

  function initializeCharts() {
    const chartConfigs = {
      expenditureChart: {
        type: 'bar',
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: { display: false }
          },
          scales: {
            y: { beginAtZero: true }
          }
        }
      },
      householdsChart: {
        type: 'line',
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: { display: false }
          },
          scales: {
            y: { beginAtZero: true }
          }
        }
      },
      wageChart: {
        type: 'line',
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: { display: false }
          },
          scales: {
            y: { beginAtZero: false }
          }
        }
      },
      paymentChart: {
        type: 'doughnut',
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: { position: 'bottom' }
          }
        }
      }
    };

    Object.keys(chartConfigs).forEach(chartId => {
      const ctx = document.getElementById(chartId).getContext('2d');
      charts[chartId] = new Chart(ctx, {
        type: chartConfigs[chartId].type,
        data: { labels: [], datasets: [] },
        options: chartConfigs[chartId].options
      });
    });
  }

  function loadAllData() {
    fetchData(`${BASE_URL}/all`);
  }

  async function fetchData(url) {
    tableBody.innerHTML = `<tr><td colspan="8" class="loading"><div class="spinner"></div>Loading...</td></tr>`;
    try {
      const response = await fetch(url);
      if (!response.ok) throw new Error("Network error");
      const data = await response.json();
      allData = data;
      renderTable(data);
      updateStatistics(data);
      updateCharts(data);
    } catch (err) {
      tableBody.innerHTML = `<tr><td colspan="8" class="loading error">Error loading data. Please check if the server is running.</td></tr>`;
      console.error(err);
    }
  }

  function renderTable(data) {
    if (!data || data.length === 0) {
      tableBody.innerHTML = `<tr><td colspan="8" class="loading">No records found</td></tr>`;
      return;
    }

    tableBody.innerHTML = "";
    data.forEach(item => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${item.districtName || "-"}</td>
        <td>${item.finYear || "-"}</td>
        <td>${item.month || "-"}</td>
        <td>₹${item.totalExp ? item.totalExp.toLocaleString() : "-"}</td>
        <td>₹${item.averageWageRatePerDayPerPerson ?? "-"}</td>
        <td>${item.totalHouseholdsWorked ? item.totalHouseholdsWorked.toLocaleString() : "-"}</td>
        <td>${item.percentOfCategoryBWorks ?? "-"}%</td>
        <td>${item.percentagePaymentsGenerated ?? "-"}%</td>
      `;
      tableBody.appendChild(row);
    });
  }

  function updateStatistics(data) {
    const totalRecords = data.length;
    const totalExp = data.reduce((sum, item) => sum + (item.totalExp || 0), 0);
    const avgWage = data.length > 0 
      ? data.reduce((sum, item) => sum + (item.averageWageRatePerDayPerPerson || 0), 0) / data.length 
      : 0;
    const totalHH = data.reduce((sum, item) => sum + (item.totalHouseholdsWorked || 0), 0);

    document.getElementById('totalRecords').textContent = totalRecords.toLocaleString();
    document.getElementById('totalExpenditure').textContent = `₹${(totalExp / 100000).toFixed(2)}`;
    document.getElementById('avgWage').textContent = `₹${avgWage.toFixed(2)}`;
    document.getElementById('totalHouseholds').textContent = totalHH.toLocaleString();
  }

  function updateCharts(data) {
    if (!data || data.length === 0) return;

    // Expenditure by District
    const districtExp = {};
    data.forEach(item => {
      if (item.districtName && item.totalExp) {
        districtExp[item.districtName] = (districtExp[item.districtName] || 0) + item.totalExp;
      }
    });
    
    const topDistricts = Object.entries(districtExp)
      .sort((a, b) => b[1] - a[1])
      .slice(0, 10);

    charts.expenditureChart.data = {
      labels: topDistricts.map(d => d[0]),
      datasets: [{
        label: 'Total Expenditure',
        data: topDistricts.map(d => d[1]),
        backgroundColor: 'rgba(102, 126, 234, 0.7)',
        borderColor: 'rgba(102, 126, 234, 1)',
        borderWidth: 2
      }]
    };
    charts.expenditureChart.update();

    // Households over time
    const timeData = data.slice().sort((a, b) => {
      if (a.finYear !== b.finYear) return a.finYear.localeCompare(b.finYear);
      return (a.month || "").localeCompare(b.month || "");
    }).slice(0, 20);

    charts.householdsChart.data = {
      labels: timeData.map(d => `${d.month || ''} ${d.finYear || ''}`),
      datasets: [{
        label: 'Households Worked',
        data: timeData.map(d => d.totalHouseholdsWorked || 0),
        borderColor: 'rgba(118, 75, 162, 1)',
        backgroundColor: 'rgba(118, 75, 162, 0.1)',
        tension: 0.4,
        fill: true
      }]
    };
    charts.householdsChart.update();

    // Wage trends
    charts.wageChart.data = {
      labels: timeData.map(d => `${d.month || ''} ${d.finYear || ''}`),
      datasets: [{
        label: 'Average Wage Rate',
        data: timeData.map(d => d.averageWageRatePerDayPerPerson || 0),
        borderColor: 'rgba(245, 87, 108, 1)',
        backgroundColor: 'rgba(245, 87, 108, 0.1)',
        tension: 0.4,
        fill: true
      }]
    };
    charts.wageChart.update();

    // Payment generation
    const avgPayment = data.reduce((sum, item) => sum + (item.percentagePaymentsGenerated || 0), 0) / data.length;
    charts.paymentChart.data = {
      labels: ['Payments Generated', 'Pending'],
      datasets: [{
        data: [avgPayment, 100 - avgPayment],
        backgroundColor: [
          'rgba(102, 126, 234, 0.8)',
          'rgba(118, 75, 162, 0.3)'
        ],
        borderWidth: 2
      }]
    };
    charts.paymentChart.update();
  }

  async function loadDistricts() {
    try {
      const res = await fetch(`${BASE_URL}/names`);
      const districts = await res.json();
      districts.forEach(d => {
        const option = document.createElement("option");
        option.value = d;
        option.textContent = d;
        districtSelect.appendChild(option);
      });
    } catch (err) {
      console.error("Error loading districts", err);
    }
  }

  async function loadYears() {
    try {
      const res = await fetch(`${BASE_URL}/years`);
      const years = await res.json();
      years.forEach(y => {
        const option = document.createElement("option");
        option.value = y;
        option.textContent = y;
        yearSelect.appendChild(option);
      });
    } catch (err) {
      console.error("Error loading years", err);
    }
  }
});