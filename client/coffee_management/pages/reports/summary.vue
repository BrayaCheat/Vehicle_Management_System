<template>
  <section id="summary-report" class="bg-gray-50 dark:bg-gray-800">
    <UDashboardNavbar
      class="capitalize bg-white dark:bg-gray-900 fixed top-0 w-full z-50"
      title="Summary"
      badge="Report"
    />
    <div class="grid grid-cols-3 gap-4 p-4 mt-16">

      <ULandingCard>
        <h1>Total Vehicle</h1>
        <p>{{ Vehicle.length }}</p>
      </ULandingCard>

      <UDashboardCard
        data-aos="zoom-in"
        title="Vehicles's Based On Their Price"
        description="Price Distribution of Vehicles."
        icon="i-material-symbols:attach-money"
        class="col-span-3"
      >
        <ULandingCard>
          <LineChart :chartData="TotalVehicle"/>
        </ULandingCard>
      </UDashboardCard>

      <UDashboardCard
        data-aos="zoom-in"
        v-if="Vehicle"
        title="Recents Sales"
        :description="`Customers who have recently purchased our products.`"
        icon="i-svg-spinners:clock"
        class="col-span-2"
      >
        <UTable :rows="newestSales.slice(0,8)" :columns="columns"/>
      </UDashboardCard>

      <UDashboardCard
        data-aos="zoom-in"
        v-if="Vehicle"
        title="Frequent Customers"
        :description="`Customers who regularly purchase our products.`"
        icon="i-line-md:gauge-loop"
      >
        <UTable :rows="CustomerSales.slice(0,8)" :columns="columnsRecentSales"/>
      </UDashboardCard>

      
    </div>
  </section>
</template>

<script setup>
definePageMeta({
  middleware: "auth",
});
import axios from "axios";
import {
  DoughnutChart,
  BarChart,
  LineChart,
  RadarChart,
  PieChart,
  ScatterChart,
} from "vue-chart-3";
import { Chart } from "chart.js";
import {
  CategoryScale,
  LinearScale,
  ArcElement,
  BarElement,
  PointElement,
  LineElement,
  RadialLinearScale,
  Tooltip,
  Title,
  DoughnutController,
  BarController,
  LineController,
  RadarController,
  PieController,
  ScatterController,
  Legend,
} from "chart.js";
// Register the required components from Chart.js
Chart.register(
  CategoryScale,
  LinearScale,
  ArcElement,
  BarElement,
  PointElement,
  LineElement,
  RadialLinearScale,
  Tooltip,
  Title,
  DoughnutController,
  BarController,
  LineController,
  RadarController,
  PieController,
  ScatterController,
  Legend
);

const Reports = ref([]);
const Customer = ref([]);
const Vehicle = ref([]);
const Sales = ref([]);
const CustomerSales = ref([]);
let revenue = ref(0);
let expense = ref(0);
const newestSales = ref([]);

const columns = [
  {
    key: 'customerName',
    label: 'Customer',
  },
  {
    key: 'customerPhone',
    label: 'Phone',
  },
  {
    key: 'carName',
    label: 'Model',
  },
  {
    key: 'carPrice',
    label: 'Price',
  }
]

const columnsRecentSales = [
  {
    key: 'customerName',
    label: 'Customer',
  },
  {
    key: 'salesCount',
    label: 'Times',
  },
]

const getNewestSales = async () => {
  try {
    await axios
      .get(`http://127.0.0.1:8082/api/v1/report/sortByNewest`)
      .then((res) => {
        newestSales.value = res.data.payload;
      });
  } catch (error) {
    console.log(error);
  }
};

const getReports = async () => {
  try {
    await axios.get(`http://127.0.0.1:8082/api/v1/report`).then((res) => {
      Reports.value = res.data.payload;
      revenue.value = Reports.value.reduce((sum, car) => sum + car.carPrice, 0);
    });
  } catch (error) {
    console.log(error);
  }
};

const getAllVehicle = async () => {
  try {
    await axios.get(`/api/vehicle/getVehicle`).then((res) => {
      Vehicle.value = res.data.payload;
      expense.value = Vehicle.value.reduce((sum, car) => sum + car.price, 0);
    });
  } catch (error) {
    console.log(error);
  }
};

const getAllCustomer = async () => {
  try {
    await axios
      .get(`/api/customer/getCustomer`)
      .then((res) => (Customer.value = res.data.payload));
  } catch (error) {
    console.log(error);
  }
};

const getRecentSales = async () => {
  try {
    await axios
      .get(`http://127.0.0.1:8082/api/v1/report/RecentSales`)
      .then((res) => {
        Sales.value = res.data.payload;
      });
  } catch (error) {
    console.log(error);
  }
};

const getCustomerSalesCount = async () => {
  try {
    await axios
      .get(`http://127.0.0.1:8082/api/v1/report/CustomerSalesCount`)
      .then((res) => {
        (CustomerSales.value = res.data.payload)
      });
  } catch (error) {
    console.log(error);
  }
};

onMounted(() => {
  getReports();
  getAllCustomer();
  getAllVehicle();
  getRecentSales();
  getCustomerSalesCount();
  getNewestSales();
});

const ExpenseTracker = computed(() => ({
  labels: ["Total Revenue", "Total Expense", "Total Profit"],
  datasets: [
    {
      label: "Financial Overview",
      data: [revenue.value, expense.value, revenue.value - expense.value],
      backgroundColor: [
        "rgba(253, 224, 71, 0.2)",
        "rgba(252, 165, 165, 0.2)",
        "rgba(134, 239, 172, 0.2)",
      ],
      borderColor: ["rgb(234, 179, 8)", "rgb(239, 68, 68)", "rgb(34, 197, 94)"],
      borderWidth: 1,
      barPercentage: 0.6,
      barThickness: 100,
    },
  ],
}));

const RecentSales = computed(() => ({
  labels: Sales.value.map((item) => item.carName),
  datasets: [
    {
      label: "Gross Revenue Overview",
      data: Sales.value.map((item) => item.salesCount),
      backgroundColor: [
        "rgba(134, 239, 172, 0.2)",
        "rgba(252, 211, 77, 0.2)",
        "rgba(94, 234, 212, 0.2)",
        "rgba(216, 180, 254, 0.2)",
        "rgba(252, 165, 165, 0.2)",
        "rgba(203, 213, 225, 0.2)",
        "rgb(190, 242, 100, 0.2)",
      ],
      borderColor: [
        "rgb(34 197 94)",
        "rgb(245 158 11)",
        "rgb(20 184 166)",
        "rgb(168 85 247)",
        "rgb(239 68 68)",
        "rgb(100 116 139)",
        "rgb(132 204 22)",
      ],
      borderWidth: 1,
      barThickness: 3,
      indexAxis: 'y',
    },
  ],
}));

const TotalCustomer = computed(() => ({
  labels: CustomerSales.value.map((item) => item.customerName),
  datasets: [
    {
      label: "Total Customers Overview",
      data: CustomerSales.value.map((item) => item.salesCount),
      backgroundColor: ["#4ade80"],
      backgroundColor: ["rgba(134, 239, 172, 0.2)"],
      borderColor: ["rgb(34, 197, 94)"],
      borderWidth: 1,
      barPercentage: 0.6,
      barThickness: 100,
    },
  ],
}));

const TotalVehicle = computed(() => ({
  labels: Vehicle.value.map((item) => item.model),
  datasets: [
    {
      label: "Price Overview",
      data: Vehicle.value.map((item) => item.price),
      backgroundColor: [
        "rgba(134, 239, 172, 0.2)",
        "rgba(252, 211, 77, 0.2)",
        "rgba(94, 234, 212, 0.2)",
        "rgba(216, 180, 254, 0.2)",
        "rgba(252, 165, 165, 0.2)",
        "rgba(203, 213, 225, 0.2)",
        "rgb(190, 242, 100, 0.2)",
      ],
      borderColor: [
        "rgb(34 197 94)",
        "rgb(245 158 11)",
        "rgb(20 184 166)",
        "rgb(168 85 247)",
        "rgb(239 68 68)",
        "rgb(100 116 139)",
        "rgb(132 204 22)",
      ],
      borderWidth: 1,
      barPercentage: 10,
      barThickness: 30,
      tension: 0.3,
      pointRadius: 3,
    },
  ],
}));

const CustomerCount = computed(() => ({
  labels: ["Total Customers"],
  datasets: [
    {
      data: [Customer.value.length],
      backgroundColor: ["rgba(134, 239, 172, 0.2)"],
      borderColor: ["rgb(34, 197, 94)"],
      borderWidth: 1,
      barPercentage: 0.6,
      barThickness: 100,
    },
  ],
}));

const VehicleCount = computed(() => ({
  labels: ["Total Vehicles"],
  datasets: [
    {
      data: [Vehicle.value.length],
      backgroundColor: ["rgba(134, 239, 172, 0.2)"],
      borderColor: ["rgb(34, 197, 94)"],
      borderWidth: 1,
      barPercentage: 0.6,
      barThickness: 100,
    },
  ],
}));

const TotalReports = computed(() => ({
  labels: ["Total Reports"],
  datasets: [
    {
      data: [Reports.value.length],
      backgroundColor: ["rgba(134, 239, 172, 0.2)"],
      borderColor: ["rgb(34, 197, 94)"],
      borderWidth: 1,
      barPercentage: 0.6,
      barThickness: 100,
    },
  ],
}));
</script>

<style></style>
