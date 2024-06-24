<template>
  <section
    id="list-customers"
    class="border-e border-b dark:border-gray-800 rounded-md shadow-sm"
  >
    <UDashboardNavbar
      class="capitalize"
      title="Order Reports"
      :badge="reports.length"
    >
      <template #right>
        <UInput
          v-model="searchQuery"
          icon="i-heroicons-magnifying-glass"
          autocomplete="off"
          placeholder="Search for reports..."
          class="hidden lg:block"
          @keydown.esc="$event.target.blur()"
        >
          <template #trailing>
            <UKbd value="/" />
          </template>
        </UInput>
      </template>
    </UDashboardNavbar>

    <div
      class="flex gap-3 items-center p-4 border-b dark:border-gray-800"
    >
      <USelect
        :options="options"
        v-model="filterValue"
        placeholder="Filter"
        icon="i-heroicons-bars-arrow-up"
        @change="handleFilter"
      />
      <UTooltip text="Reload List">
        <UButton
        variant="solid"
        color="gray"
        icon="i-heroicons-arrow-path"
        @click="
          getAllReport(),
          reloadPage(),
            (filterValue = null),
            (searchQuery = ''),
            toast.add({
              title: 'Refresh',
              icon: 'i-heroicons-arrow-path',
            })
        "
      />
      </UTooltip>
      
    </div>
    <!-- table -->
    <div class="flex flex-col" data-aos="zoom-in">
      <div class="overflow-x-auto">
        <div class="p-1.5 min-w-full inline-block align-middle">
          <div class="overflow-hidden">
            <table
              class="min-w-full divide-y divide-gray-200 dark:divide-gray-800"
            >
              <thead>
                <tr>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    #
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Customer's name
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Customer's phone
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Vehicle's model
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Vehicle's price
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Status
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Checkout Date
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Operation
                  </th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-200 dark:divide-gray-800">
                <tr
                  class="dark:hover:bg-gray-800 hover:bg-gray-100 duration-300 cursor-pointer"
                  v-for="(item, index) in search"
                  :key="index"
                >
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-500 dark:text-gray-400"
                  >
                    {{ item.id }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.customerName }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.customerPhone }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.carName }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    ${{ new Intl.NumberFormat().format(item.carPrice) }}
                  </td>
                  <td
                    class="pe-6 ps-3 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    <UDashboardNavbar
                      badge="paid"
                      class="uppercase border-none"
                    />
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ new Date(item.createdAt).toDateString() }}
                    -
                    {{ new Date(item.createdAt).toLocaleTimeString() }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-start text-sm font-medium"
                  >
                    <UDropdown
                      :items="items(item)"
                      :popper="{ placement: 'bottom-start' }"
                    >
                      <UButton icon="i-heroicons-ellipsis-horizontal" variant />
                    </UDropdown>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <!-- deleteModal -->
    <UDashboardModal
      v-if="selectedItem"
      v-model="deleteModal"
      :title="`Delete Report ${selectedItem.id}`"
      :description="`Are you sure you want to delete ${selectedItem.id}?`"
      icon="i-heroicons-exclamation-circle"
      :ui="{
        icon: { base: 'text-red-500 dark:text-red-400' },
        footer: { base: 'ml-16' },
      }"
    >
      <template #footer>
        <UButton
          color="red"
          label="Delete"
          @click="handleDelete(selectedItem.id), getAllReport(), filterValue = null, searchQuery = ''"
        />
        <UButton color="white" label="Cancel" @click="deleteModal = false" />
      </template>
    </UDashboardModal>
  </section>
</template>

<script setup>
definePageMeta({
  middleware: 'auth'
})

import axios from "axios";

const deleteModal = ref(false);
const selectedItem = ref(null);
const errorMsg = ref(false);
const reports = ref([]);
const toast = useToast();
const searchQuery = ref("");
const options = [
  "Price Low To High",
  "Price High To Low",
  "Name Ascending",
  "Name Descending",
  "Newest Date",
  "Oldest Date",
];
const filterValue = ref();

const reloadPage = () => {
  window.location.reload()
}

const getAllReport = async () => {
  try {
    await axios.get(`http://127.0.0.1:8082/api/v1/report`).then((res) => {
      reports.value = res.data.payload;
    });
  } catch (error) {
    console.log(error);
  }
};

const items = (row) => [
  [
    {
      label: "Generate Receipt",
      icon: "i-line-md:clipboard-list",
      click: () => navigateTo(`/POS/print/${row.id}`),
    },
    {
      label: "Delete",
      icon: "i-line-md:buy-me-a-coffee",
      click: () => {
        OpenDeleteModal(row);
      },
    },
  ],
];

const search = computed(() => {
  return reports.value.filter((key) =>
    key.customerName.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const handleFilter = async () => {
  try {
    switch (filterValue.value) {
      case "Price Low To High":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/report/sortByCarPriceASC`)
          .then((res) => {
            reports.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Price High To Low":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/report/sortByCarPriceDESC`)
          .then((res) => {
            reports.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Name Ascending":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/report/sortByCustomerNameASC`)
          .then((res) => {
            reports.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Name Descending":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/report/sortByCustomerNameDESC`)
          .then((res) => {
            reports.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Newest Date":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/report/sortByNewest`)
          .then((res) => {
            reports.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Oldest Date":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/report/sortByOldest`)
          .then((res) => {
            reports.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      default:
        console.log("Unknown filter value");
    }
  } catch (error) {
    console.log(error);
  }
};

const handleDelete = async (id) => {
  try {
    await axios.delete(`http://127.0.0.1:8082/api/v1/report/${id}`).then(() => {
      toast.add({
        title: `Deleting Report`,
        icon: "i-heroicons-check-circle",
        color: "red",
      });
      deleteModal.value = false;
      getAllReport();
    });
  } catch (error) {
    console.log(error);
  }
};

const OpenDeleteModal = (item) => {
  deleteModal.value = true;
  selectedItem.value = item;
};

onMounted(() => {
  getAllReport();
});
</script>

<style></style>
