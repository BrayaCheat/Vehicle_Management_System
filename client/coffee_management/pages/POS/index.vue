<template>
  <section id="pos-page" class="bg-gray-50 dark:bg-gray-800">
    <UDashboardNavbar
      class="capitalize fixed top-0 w-full z-40 bg-white dark:bg-gray-900"
      :title="$route.path.slice(1, 30)"
      badge="System"
    />

    
        <UInput
          v-model="searchQuery"
          icon="i-heroicons-magnifying-glass"
          autocomplete="off"
          placeholder="Search for vehicles..."
          class="hidden lg:block fixed z-50 right-4 top-4"
          @keydown.esc="$event.target.blur()"
        >
          <template #trailing>
            <UKbd value="/" />
          </template>
        </UInput>
      
    

    <div v-if="vehicles.length" class="grid grid-cols-5 gap-4 p-4 mt-16">
      <ULandingCard v-for="item in search" :key="item.id" data-aos="zoom-in" :to="`/POS/Order/${item.id}`">
        <div class="flex flex-col gap-3">
          <img
            class="w-[300px] h-[150px] object-contain col-span-3"
            :src="getImage(item.image)"
            alt=""
          />
          <div class="flex items-center justify-between">
            <span class="text-xl font-normal uppercase">{{ item.model }}</span>
            <!-- <UDashboardNavbar badge="Availavle" class="border-0"/> -->
          </div>
          <ul class="ms-5 list-disc text-gray-500 text-[14px]">
            <li>{{ item.year }}</li>
            <li>{{ item.color }}</li>
            <li>{{ item.carCondition }}</li>
            <li>{{ item.type }}</li>
            <li class="text-lg font-bold mt-2">${{ new Intl.NumberFormat().format(item.price) }}</li>
          </ul>
        </div>
      </ULandingCard>
    </div>
  </section>
</template>

<script setup>
definePageMeta({
  middleware: 'auth'
})

import axios from "axios";
const vehicles = ref([]);
const searchQuery = ref('')
const imgURL = "http://127.0.0.1:8082/";

const getImage = (name) => {
  return imgURL + name;
};

const getVehicle = async () => {
  try {
    await axios.get(`/api/vehicle/getVehicle`).then((res) => {
      vehicles.value = res.data.payload;
    });
  } catch (error) {
    console.log(error);
  }
};

const search = computed(() => {
  return vehicles.value.filter((item) => item.model.toLowerCase().includes(searchQuery.value.toLowerCase()))
})

onMounted(() => {
  getVehicle();
});
</script>
