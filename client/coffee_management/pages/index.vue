<template>
  <section id="summary-report" class="bg-gray-50 dark:bg-gray-800 h-screen">
    <UDashboardNavbar
      class="capitalize bg-white dark:bg-gray-900"
      title="Dashboard"
    >
      <template #right>
        <UButton
          icon="i-line-md:arrows-horizontal"
          label="Make an order"
          trailing
          color="gray"
          to="/POS"
        />
      </template>
    </UDashboardNavbar>

    <div class="grid grid-cols-2 gap-4 p-4">
      <div class="grid grid-cols-2 gap-4">
        <ULandingCard data-aos="zoom-in" class="shadow-sm rounded-md gap-3">
          <div class="flex flex-col gap-3">
            <h1>Total Expense</h1>
            <UDivider />
            <div class="flex flex-col gap-3 mt-2">
              <span
                id="number"
                class="text-2xl flex items-center"
                :class="expense > revenue ? 'text-violet-500' : 'text-red-600'"
                >${{ new Intl.NumberFormat().format(expense) }}
                <Icon
                  :name="
                    expense > revenue
                      ? 'i-heroicons-arrow-trending-up'
                      : 'i-heroicons-arrow-trending-down'
                  "
                />
              </span>
            </div>
          </div>
          <!-- <LineChart :chartData="TotalExpense" class="h-[200px]" /> -->
        </ULandingCard>

        <ULandingCard data-aos="zoom-in" class="shadow-sm rounded-md gap-3">
          <div class="flex flex-col gap-3">
            <h1>Total Revenue</h1>
            <UDivider />
            <div class="flex flex-col gap-3 mt-2">
              <span
                id="number"
                class="text-2xl flex items-center"
                :class="revenue > expense ? 'text-emerald-600' : 'text-red-600'"
                >${{ new Intl.NumberFormat().format(revenue) }}
                <Icon
                  :name="
                    revenue > expense
                      ? 'i-heroicons-arrow-trending-up'
                      : 'i-heroicons-arrow-trending-down'
                  "
                />
              </span>
            </div>
          </div>
          <!-- <LineChart :chartData="TotalExpense" class="h-[200px]" /> -->
        </ULandingCard>

        <ULandingCard
          data-aos="zoom-in"
          class="shadow-sm rounded-md gap-3 col-span-2"
        >
          <div class="flex flex-col gap-3">
            <h1>Total Profit</h1>
            <UDivider />
            <div class="flex flex-col gap-3 mt-2">
              <span
                id="number"
                class="text-2xl flex items-center"
                :class="profit > 0 ? 'text-emerald-600' : 'text-red-600'"
                >${{ new Intl.NumberFormat().format(profit) }}
                <Icon
                  :name="
                    profit > 0
                      ? 'i-heroicons-arrow-trending-up'
                      : 'i-heroicons-arrow-trending-down'
                  "
                />
              </span>
            </div>
          </div>
          <!-- <LineChart :chartData="TotalExpense" class="h-[200px]" /> -->
        </ULandingCard>
        <UDashboardCard
          data-aos="zoom-in"
          title="Overall"
          description="Here are some of the trends seen from your transactions"
          icon="i-line-md:gauge-loop"
          class="col-span-2"
        >
          <ULandingCard>
            <LineChart :chartData="ExpenseTracker" class="h-[330px]"/>
          </ULandingCard>
        </UDashboardCard>
      </div>

      <UDashboardCard
        data-aos="zoom-in"
        title="Top Sales"
        :description="`You have made ${Reports.length} sales for this month.`"
        icon="i-line-md:speedometer-loop"
      >
        <ULandingCard>
          <BarChart :chartData="RecentSales" class="h-[670px]"/>
        </ULandingCard>
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
} from "vue-chart-3";
import { Chart, Scale } from "chart.js";
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
  Legend
);

const Reports = ref([]);
const Customer = ref([]);
const Vehicle = ref([]);
const Sales = ref([]);
const CustomerSales = ref([]);
let revenue = ref(0);
let expense = ref(0);
const user = useSupabaseUser();
const toast = useToast();
const newestSales = ref([]);

const totalMoney = computed(() => revenue.value + expense.value);
const profit = computed(() => revenue.value - expense.value);

const findPercentage = (op, value) => {
  let percentage = 0;
  switch (op) {
    case "expense":
      percentage = (value / totalMoney.value) * 100;
      break;
    case "revenue":
      percentage = (value / totalMoney.value) * 100;
      break;
    case "profit":
      percentage = (value / totalMoney.value) * 100;
      break;
  }
  return percentage.toFixed(1);
};

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

const greeting = () => {
  toast.add({
    title: `Welcome Mr. ${user.value.user_metadata.full_name}`,
    avatar: {
      src: `${user.value.user_metadata.picture}`,
    },
  });
};

const getImage = (name) => "http://127.0.0.1:8082/" + name;

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
    await axios.get(`/api/customer/getCustomer`).then((res) => {
      Customer.value = res.data.payload;
    });
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
        CustomerSales.value = res.data.payload;
      });
  } catch (error) {
    console.log(error);
  }
};

onMounted(() => {
  greeting();
  getReports();
  getAllCustomer();
  getAllVehicle();
  getRecentSales();
  getCustomerSalesCount();
  getNewestSales();
});

const ExpenseTracker = computed(() => ({
  labels: ["Total Expense", "Total Profit", "Total Revenue"],
  datasets: [
    {
      label: "Financial Overview",
      data: [expense.value, profit.value, revenue.value],
      backgroundColor: ["rgb(5 150 105)"],
      borderColor: ["rgb(5 150 105)"],
      fill: true,
      tension: 0.2,
      pointRadius: 3,
      borderWidth: 2,
      barPercentage: 0.1,
      barThickness: 20,
      // indexAxis: "y",
    },
  ],
}));

const RecentSales = computed(() => ({
  labels: Sales.value.map((item) => item.carName),
  datasets: [
    {
      label: 'Sales Count',
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
      borderWidth: 0.1,
      barPercentage: 0.9,
      barThickness: 9,
      indexAxis: "y",
    },
  ],
}));

const TotalCustomer = computed(() => ({
  labels: CustomerSales.value.map((item) => item.customerName),
  datasets: [
    {
      label: "Top Customer",
      data: CustomerSales.value.map((item) => item.salesCount),
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
      barPercentage: 0.6,
      barThickness: 100,
    },
  ],
}));

const TotalVehicle = computed(() => ({
  labels: ["Total Vehicle"],
  datasets: [
    {
      label: "Total Vehicles Overview",
      data: [Vehicle.value.length],
      backgroundColor: ["rgba(134, 239, 172, 0.2)"],
      borderColor: ["rgb(34, 197, 94)"],
      borderWidth: 1,
      barPercentage: 0.6,
      barThickness: 100,
    },
  ],
}));

const TotalExpense = computed(() => ({
  labels: ["Start", "", "End"],
  datasets: [
    {
      label: "Expense",
      data: [totalMoney.value, 0, expense.value],
      backgroundColor: ["rgb(220 38 38)"],
      borderColor: ["rgb(220 38 38)"],
      borderWidth: 1.5,
      barPercentage: 0.6,
      barThickness: 100,
      fill: true,
      tension: 0.2,
      pointRadius: 2,
    },
  ],
}));

const TotalRevenue = computed(() => ({
  labels: ["Start", "", "End"],
  datasets: [
    {
      label: "Revenue",
      data: [totalMoney.value, 0, revenue.value],
      backgroundColor: ["rgb(202 138 4)"],
      borderColor: ["rgb(202 138 4)"],
      borderWidth: 1.5,
      barPercentage: 0.6,
      barThickness: 100,
      fill: true,
      tension: 0.2,
      pointRadius: 2,
    },
  ],
}));

const TotalProfit = computed(() => ({
  labels: ["Start", "", "End"],
  datasets: [
    {
      label: "Profit",
      data: [totalMoney.value, 0, profit.value],
      backgroundColor: ["rgb(22 163 74)"],
      borderColor: ["rgb(22 163 74)"],
      borderWidth: 1.5,
      barPercentage: 0.6,
      barThickness: 100,
      fill: true,
      tension: 0.2,
      pointRadius: 2,
    },
  ],
}));
</script>

<style scoped>
  #number{
    font-family: 'Open Sans', sans-serif;
  }
</style>
