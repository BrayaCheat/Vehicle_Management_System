<template>
  <section
    id="list-vehicles"
    class="border-e border-b dark:border-gray-800 rounded-md"
  >
    <UDashboardNavbar
      class="capitalize"
      :title="$route.path.slice(1, 30)"
      :badge="vehicle.length"
    >
      <template #right>
        <UInput
          v-model="searchQuery"
          icon="i-heroicons-magnifying-glass"
          autocomplete="off"
          placeholder="Search for vehicles..."
          class="hidden lg:block"
          @keydown.esc="$event.target.blur()"
        >
          <template #trailing>
            <UKbd> / </UKbd>
          </template>
        </UInput>

        <UButton
          label="New vehicle"
          trailing-icon="i-line-md:plus"
          color="gray"
          @click="isOpenModal = !isOpenModal"
        />
      </template>
    </UDashboardNavbar>

    <div
      class="flex items-center gap-3 p-4 border-b dark:border-gray-800"
    >
      <div class="flex items-center gap-3">
        <USelect
          :options="options"
          v-model="sortValue"
          placeholder="Filter"
          icon="i-heroicons-bars-arrow-up"
          @change="handleSort"
        />
        <USelect
          :options="type"
          v-model="filterValue"
          placeholder="Type"
          icon="i-heroicons-adjustments-horizontal"
          @change="handleFilter"
        />
      </div>
      <UTooltip text="Reload List">
        <UButton
        variant="solid"
        color="gray"
        icon="i-heroicons-arrow-path"
        @click="
          reloadPage(),
            (sortValue = null),
            (filterValue = null),
            (searchQuery = '')
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
                    Image
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Model
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Year
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Color
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Condition
                  </th>

                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Type
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Price
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Import Date
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-end text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Operation
                  </th>
                </tr>
              </thead>
              <tbody
                v-if="vehicle.length > 0 || search.length > 0"
                class="divide-y divide-gray-200 dark:divide-gray-800"
              >
                <tr
                  class="dark:hover:bg-gray-800 hover:bg-gray-100 duration-300 cursor-pointer"
                  v-for="item in search"
                  :key="item.id"
                >
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-500 dark:text-gray-400"
                  >
                    {{ item.id }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    <img
                      class="w-[150px] h-[100px] object-contain"
                      :src="getImage(item.image)"
                      alt=""
                    />
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.model }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.year }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.color }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.carCondition }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.type }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    ${{ new Intl.NumberFormat().format(item.price) }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ new Date(item.createdAt).toLocaleDateString() }}
                    -
                    {{ new Date(item.createdAt).toLocaleTimeString() }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-end text-sm font-medium"
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

              <tbody v-else>
                <UButton
                  icon="i-heroicons-circle-stack-20-solid"
                  label="No items."
                  color="gray"
                  variant
                  size="xl"
                  block
                  class="text-2xl"
                />
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!-- modal -->
    <UModal v-model="isOpenModal">
      <ULandingCard>
        <form
          enctype="multipart/form-data"
          class="w-full flex flex-col"
          @submit.prevent="handleCreate"
        >
          <div class="flex flex-col mb-10">
            <div class="flex items-center justify-between">
              <h1 class="font-bold text-lg">New vehicle</h1>
              <UButton
                @click="isOpenModal = false"
                icon="i-heroicons-x-mark"
                variant
              />
            </div>
            <p class="text-sm">Add a new vehicle to your database</p>
          </div>

          <div class="w-full grid gap-6">
            <UFormGroup label="Model">
              <UInput
                placeholder="GTR"
                icon="i-heroicons-truck"
                v-model="vehicleModel.model"
                required
              />
            </UFormGroup>

            <div class="grid grid-cols-2 gap-3">
              <UFormGroup label="Year">
                <UInput
                  placeholder="2001"
                  icon="i-heroicons-calendar-days"
                  v-model="vehicleModel.year"
                  required
                />
              </UFormGroup>
              <UFormGroup label="Color">
                <USelect
                  v-model="vehicleModel.color"
                  :options="color"
                  placeholder="Select color"
                  icon="i-heroicons-swatch"
                  required
                />
              </UFormGroup>
            </div>

            <UFormGroup label="Price">
              <UInput
                placeholder="99,999"
                icon="i-heroicons-currency-dollar"
                v-model="vehicleModel.price"
                required
              />
            </UFormGroup>

            <div class="grid grid-cols-2 gap-3">
              <UFormGroup label="Condition">
                <USelect
                  v-model="vehicleModel.carCondition"
                  :options="condition"
                  placeholder="Select condition"
                  icon="i-heroicons-check-circle"
                  required
                />
              </UFormGroup>
              <UFormGroup label="Type">
                <USelect
                  v-model="vehicleModel.type"
                  :options="type"
                  placeholder="Select type"
                  icon="i-heroicons-tag"
                  required
                />
              </UFormGroup>
            </div>
            <UFormGroup class="grid">
              <UButton
                color="gray"
                label="Upload Image"
                icon="i-heroicons-photo"
                @click="uploadFile"
                block
                class="w-full"
              />
              <input
                accept="image/*"
                type="file"
                id="image"
                class="hidden"
                @change="handleFileChanged"
                required
              />
              <div class="w-full flex items-center justify-center">
                <img
                  v-if="displayImage"
                  :src="displayImage"
                  class="mt-10 object-cover"
                />
              </div>
            </UFormGroup>

            <div class="h-10">
              <span v-if="errorMsg" class="text-sm text-red-500">
                {{ errorMsg }}
              </span>
            </div>
          </div>

          <div class="flex items-center justify-end gap-3">
            <UButton label="Cancel" variant @click="isOpenModal = false" />
            <UButton label="Save" color="black" type="submit" />
          </div>
        </form>
      </ULandingCard>
    </UModal>

    <!-- deleteModal -->
    <UDashboardModal
      v-if="selectedItem"
      v-model="deleteModal"
      :title="`Delete ${selectedItem.model}`"
      :description="`Are you sure you want to delete ${selectedItem.model}?`"
      icon="i-material-symbols:delete-outline"
      :ui="{
        icon: { base: 'text-red-500 dark:text-red-400' },
        footer: { base: 'ml-16' },
      }"
    >
      <template #footer>
        <UButton
          color="red"
          label="Delete"
          @click="handleDelete(selectedItem.id)"
        />
        <UButton color="white" label="Cancel" @click="deleteModal = false" />
      </template>
    </UDashboardModal>
  </section>
</template>

<script setup>
definePageMeta({
  middleware: "auth",
});

import axios from "axios";

const selectedItem = ref(null);
const deleteModal = ref(false);
const errorMsg = ref(false);
const sortValue = ref();
const filterValue = ref();
const toast = useToast();
const displayImage = ref(false);
const vehicle = ref([]);
const imgURL = "http://127.0.0.1:8082/";
const searchQuery = ref("");
const isOpenModal = ref(false);
const vehicleModel = ref({
  model: null,
  year: null,
  color: null,
  carCondition: null,
  file: null,
  type: null,
  price: null,
});
const color = [
  "Red",
  "Black",
  "Blue",
  "Pink",
  "White",
  "Silver",
  "Gold",
  "Green",
  "Gray",
  "Lime",
  "Purple",
  "Brown",
  "Yellow",
];
const condition = ["New", "Used", "Old"];
const type = [
  "SUV",
  "Coupe",
  "Sport Car",
  "Truck",
  "Luxury Car",
  "Off Road",
  "Supercar",
  "Van",
  "Electric",
];
const options = [
  "Price Low To High",
  "Price High To Low",
  "Year New To Old",
  "Year Old To New",
];

const getImage = (imgName) => {
  return imgURL + imgName;
};

const uploadFile = () => {
  const image = document.getElementById("image");
  image.click();
};

const handleFileChanged = (e) => {
  const image = document.getElementById("image");
  const image_path = image.value;
  //regexp && validation image
  const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
  if (!allowedExtensions.exec(image_path)) {
    errorMsg.value =
      "Invalid file type. Only .jpg, .jpeg, .png files are allowed.";
    image_path = null;
    displayImage.value = null;
    return false;
  } else {
    const file = e.target.files[0];
    vehicleModel.value.file = file;
    displayImage.value = URL.createObjectURL(file);
    errorMsg.value = null;
    console.log(vehicleModel.value.file);
    return true;
  }
};

const items = (row) => [
  [
    {
      label: "View Picture",
      icon: "i-line-md:watch",
      click: () => {
        window.location.href = `http://localhost:8082/${row.image}`;
      },
    },
    {
      label: "Modify",
      icon: "i-line-md:edit",
      click: () => {
        navigateTo(`/vehicles/${row.id}`);
      },
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

const handleDelete = async (id) => {
  try {
    await axios.get(`/api/vehicle/delete/${id}`).then(() => {
      toast.add({
        title: `Deleting Vehicle`,
        icon: "i-heroicons-check-circle",
        color: "red",
      });
      deleteModal.value = false;
      getVehicle();
    });
  } catch (error) {
    console.log(error);
  }
};

const getVehicle = async () => {
  try {
    await axios.get(`/api/vehicle/getVehicle`).then((res) => {
      vehicle.value = res.data.payload;
      console.log(res.data.payload);
    });
  } catch (error) {
    console.log(error);
  }
};

const search = computed(() => {
  return vehicle.value.filter((key) =>
    key.model.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const handleCreate = async () => {
  try {
    const { model, year, color, carCondition, file, type, price } =
      vehicleModel.value;
    const formData = new FormData();
    formData.append("model", model);
    formData.append("year", year);
    formData.append("color", color);
    formData.append("carCondition", carCondition);
    formData.append("file", file);
    formData.append("type", type);
    formData.append("price", price);

    await axios
      .post("http://127.0.0.1:8082/api/v1/car", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then(() => {
        toast.add({
          title: "Adding new vehicle",
          icon: "i-heroicons-check-circle",
        });
        isOpenModal.value = false;
        vehicleModel.value.model = null;
        vehicleModel.value.year = null;
        vehicleModel.value.color = null;
        vehicleModel.value.carCondition = null;
        vehicleModel.value.file = null;
        displayImage.value = null;
        vehicleModel.value.type = null;
        vehicleModel.value.price = null;
        getVehicle();
      });

    console.table("Results from fetching: ", res.data);
  } catch (error) {
    console.error(error);
  }
};

const handleSort = async () => {
  try {
    switch (sortValue.value) {
      case "Price Low To High":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/car/sortByPriceASC`)
          .then((res) => {
            vehicle.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Price High To Low":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/car/sortByPriceDESC`)
          .then((res) => {
            vehicle.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Year Old To New":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/car/sortByYearASC`)
          .then((res) => {
            vehicle.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      case "Year New To Old":
        await axios
          .get(`http://127.0.0.1:8082/api/v1/car/sortByYearDESC`)
          .then((res) => {
            vehicle.value = res.data.payload;
            toast.add({
              title: `${res.data.message}`,
              icon: "i-heroicons-funnel",
            });
          });
        break;
      default:
        console.log("Unknown sort value");
    }
  } catch (error) {
    console.log(error);
  }
};

const handleFilter = async () => {
  try {
    await axios
      .get(`http://127.0.0.1:8082/api/v1/car/filter?type=${filterValue.value}`)
      .then((res) => (vehicle.value = res.data.payload));
  } catch (error) {
    console.log(error);
  }
};

const OpenDeleteModal = (item) => {
  deleteModal.value = true;
  selectedItem.value = item;
};

const reloadPage = () => {
  window.location.reload();
};

onMounted(() => {
  getVehicle();
});
</script>
