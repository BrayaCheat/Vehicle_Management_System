<template>
  <ULandingCard
    data-aos="zoom-in"
    class="w-[500px] shadow-sm flex flex-col m-auto bg-white dark:bg-gray-800"
  >
    <h1 class="text-2xl text-center"><UIcon name="i-logos:drift" class="w-[200px] h-[60px]"/></h1>
    <div v-if="Reports" class="flex flex-col gap-6 mt-10 text-sm">
      <span class="flex items-center gap-1"
        >Hello
        <p class="font-bold">{{ Reports.customerName }}</p></span
      >
      <p>You have successfully paid for the order #{{ Reports.id }}</p>
      <p>Receipt from <UIcon name="i-logos:drift" class="w-[40px]"/></p>

      <span class="text-xl font-bold"
        >${{ new Intl.NumberFormat().format(Reports.carPrice) }}</span
      >
      <p>Paid {{ new Date(Reports.createdAt).toDateString() }}</p>

      <span class="flex items-center justify-between"
        >Receipt number
        <p>#{{ Reports.id }}</p></span
      >

      <span class="flex items-center justify-between"
        >Invoice number
        <p>#{{ randomToken }}</p></span
      >

      <UDivider />

      <span class="flex items-center justify-between font-bold"
        >{{ Reports.carName }}
        <p>${{ new Intl.NumberFormat().format(Reports.carPrice) }}</p></span
      >

      <UDivider />

      <span class="flex items-center justify-between"
        >Subtotal
        <p>${{ new Intl.NumberFormat().format(Reports.carPrice) }}</p></span
      >
      <span class="flex items-center justify-between"
        >VAT (0%)
        <p>$0</p></span
      >

      <UDivider />

      <span class="flex items-center justify-between"
        >Total amount
        <p>${{ new Intl.NumberFormat().format(Reports.carPrice) }}</p></span
      >
    </div>
  </ULandingCard>
</template>

<script setup>
import axios from "axios";

definePageMeta({
  layout: "receipt",
  middleware: "auth",
});

const totalPrice = ref();
const id = useRoute().params.id;
const user = useSupabaseUser();
const Reports = ref();

const getReport = async () => {
  try {
    await axios.get(`http://127.0.0.1:8082/api/v1/report/${id}`).then((res) => {
      console.log(res.data.payload),
        (Reports.value = res.data.payload),
        (totalPrice.value = res.data.carPrice);
    });
  } catch (error) {
    console.log(error);
  }
};

const generateRandomToken = (length = 16) => {
  const characters =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  let result = "";
  for (let i = 0; i < length; i++) {
    const randomIndex = Math.floor(Math.random() * characters.length);
    result += characters.charAt(randomIndex);
  }
  return result;
};

const randomToken = computed(() => generateRandomToken());

const getRandomInt = computed(() => {
  let min = Math.ceil(1);
  let max = Math.floor(10000);
  return Math.floor(Math.random() * (max - min) + min);
});

onMounted(() => {
  getReport();
});
</script>

<style scoped></style>
