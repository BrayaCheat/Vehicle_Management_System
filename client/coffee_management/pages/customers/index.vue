<template>
  <section id="list-customers" class="border-e border-b dark:border-gray-800">
    <UDashboardNavbar
      class="capitalize"
      :title="$route.path.slice(1, 30)"
      :badge="people.length"
    >
      <template #right>
        <UInput
          v-model="searchQuery"
          icon="i-heroicons-magnifying-glass"
          autocomplete="off"
          placeholder="Search for customers..."
          class="hidden lg:block"
          @keydown.esc="$event.target.blur()"
        >
          <template #trailing>
            <UKbd value="/" />
          </template>
        </UInput>

        <UButton
          label="New user"
          trailing-icon="i-line-md:plus"
          color="gray"
          @click="isOpenModal = !isOpenModal"
        />
      </template>
    </UDashboardNavbar>

    <div
      class="flex items-center gap-3 p-4 border-b dark:border-gray-800"
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
                    Profile
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Name
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Email
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Phone
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Enroll
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-start text-xs font-medium text-gray-500 dark:text-gray-200 uppercase"
                  >
                    Operation
                  </th>
                </tr>
              </thead>
              <tbody
                v-if="search"
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
                    class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-500 dark:text-gray-400"
                  >
                    <UAvatar src="/images/pf_skeleton.png" alt="" />
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.name }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.email }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ item.phone }}
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400"
                  >
                    {{ new Date(item.createdAt).toLocaleDateString() }}
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
              <h1 class="font-bold text-lg">New customer</h1>
              <UButton
                @click="isOpenModal = false"
                icon="i-heroicons-x-mark"
                variant
              />
            </div>
            <p class="text-sm">Add a new customer to your database</p>
          </div>

          <div class="w-full grid gap-6">
            <UFormGroup label="Name">
              <UInput
                type="text"
                placeholder="JohnDoe"
                icon="i-ic:outline-person"
                v-model="customer.name"
                required
              />
            </UFormGroup>

            <UFormGroup label="Phone">
              <UInput
                type="number"
                placeholder="+855 12 123 123"
                icon="i-material-symbols:smartphone-outline"
                v-model="customer.phone"
                required
              />
            </UFormGroup>

            <UFormGroup label="Email">
              <UInput
                type="email"
                placeholder="JohnDoe@example.com"
                icon="i-heroicons-envelope"
                v-model="customer.email"
                required
              />
            </UFormGroup>

            <div class="h-2">
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
      :title="`Delete ${selectedItem.name}`"
      :description="`Are you sure you want to delete ${selectedItem.name}?`"
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
const toast = useToast();
const isOpenModal = ref(false);
const errorMsg = ref(false);
const searchQuery = ref("");
const customer = ref({
  name: null,
  email: null,
  phone: null,
});
const options = ["Sort By Name (ASC)", "Sort By Name (DESC)"];
const filterValue = ref();

const people = ref([]);

const items = (row) => [
  [
    {
      label: `Send Message`,
      icon: "i-line-md:email",
      to: `mailto:${row.email}`,
    },
    {
      label: `Call to`,
      icon: "i-line-md:phone-outgoing",
      to: `tel:${row.phone}`,
    },
    {
      label: "Modify",
      icon: "i-line-md:edit",
      to: `/customers/${row.id}`,
    },

    {
      label: "Delete",
      icon: "i-line-md:buy-me-a-coffee",
      click: () => {
        openDeleteModal(row);
      },
    },
  ],
];

const handleFilter = async () => {
  try {
    if (filterValue.value === "Sort By Name (ASC)") {
      await axios
        .get(`http://127.0.0.1:8082/api/v1/customer/sortByName`)
        .then((res) => {
          people.value = res.data.payload;
          toast.add({
            title: `${res.data.message}`,
            icon: "i-heroicons-funnel",
          });
        });
    } else if (filterValue.value === "Sort By Name (DESC)") {
      getCustomers();
      toast.add({
        title: `Sort by name decending`,
        icon: "i-heroicons-funnel",
      });
    }
  } catch (error) {
    console.log(error);
  }
};

const getCustomers = async () => {
  try {
    await axios.get(`/api/customer/getCustomer`).then((res) => {
      people.value = res.data.payload;
      console.log(people.value);
    });
  } catch (error) {
    console.log(error);
  }
};

const search = computed(() => {
  return people.value.filter((p) =>
    p.name.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const handleCreate = async () => {
  try {
    await axios
      .post(`/api/customer/createCustomer`, {
        name: customer.value.name,
        email: customer.value.email,
        phone: customer.value.phone,
      })
      .then(() => {
        toast.add({
          title: "Adding new customer",
          icon: "i-heroicons-check-circle",
        });
        isOpenModal.value = false;
        customer.value.name = null;
        customer.value.phone = null;
        customer.value.email = null;
        getCustomers();
      });
  } catch (error) {
    console.log(error);
  }
};

const handleDelete = async (id) => {
  try {
    await axios.get(`/api/customer/delete/${id}`).then(() => {
      toast.add({
        title: `Deleting Customer`,
        icon: "i-heroicons-check-circle",
        color: "red",
      });
      deleteModal.value = false;
      getCustomers();
    });
  } catch (error) {
    console.log(error);
  }
};

const openDeleteModal = (item) => {
  selectedItem.value = item;
  deleteModal.value = true;
};

const reloadPage = () => {
  window.location.reload();
};

onMounted(() => {
  getCustomers();
});
</script>
