const BASE_URL = "http://localhost:8080/api/performance";

document.addEventListener("DOMContentLoaded", () => {
  const districtSelect = document.getElementById("districtSelect");
  const yearSelect = document.getElementById("yearSelect");
  const filterBtn = document.getElementById("filterBtn");
  const resetBtn = document.getElementById("resetBtn");
  const tableBody = document.getElementById("tableBody");

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

  // Fetch all records
  function loadAllData() {
    fetchData(`${BASE_URL}/all`);
  }

  // Fetch data and render table
  async function fetchData(url) {
    tableBody.innerHTML = `<tr><td colspan="8" class="loading">Loading...</td></tr>`;
    try {
      const response = await fetch(url);
      if (!response.ok) throw new Error("Network error");
      const data = await response.json();
      renderTable(data);
    } catch (err) {
      tableBody.innerHTML = `<tr><td colspan="8" class="loading error">Error loading data</td></tr>`;
      console.error(err);
    }
  }

  // Render table rows
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
        <td>${item.totalExp ?? "-"}</td>
        <td>${item.averageWageRatePerDayPerPerson ?? "-"}</td>
        <td>${item.totalHouseholdsWorked ?? "-"}</td>
        <td>${item.percentOfCategoryBWorks ?? "-"}</td>
        <td>${item.percentagePaymentsGenerated ?? "-"}</td>
      `;
      tableBody.appendChild(row);
    });
  }

  // Load district names
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

  // Load years
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
