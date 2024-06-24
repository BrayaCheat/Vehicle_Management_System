<template>
  <form
    @submit.prevent="handleCheckOut"
    id="checkout-page"
    class="h-[900px] border dark:border-gray-800 shadow container mx-auto grid grid-cols-2 my-10"
    v-if="vehicle"
    data-aos="zoom-in"
  >
    <div class="flex flex-col py-10 px-20 gap-10 bg-gray-50 dark:bg-gray-800">
      <UButton
        @click="useRouter().go(-1)"
        label="Back To Cart"
        icon="i-heroicons-chevron-left"
        color="gray"
        variant="link"
        class="w-fit"
      />
      <div>
        <h1 class="text-xl">Product Information & Review</h1>
        <p class="text-gray-400">
          By placing your order, you agree to Storelo in's privacy and policy
        </p>
      </div>

      <div
        class="card flex items-start gap-10 p-3 bg-white dark:bg-gray-900 rounded shadow-sm border dark:border-gray-800"
      >
        <img
          :src="getImage(vehicle.image)"
          class="w-[200px] object-contain h-full"
        />
        <div class="flex flex-col gap-3">
          <h1 class="text-xl">{{ vehicle.model }}</h1>
          <ul class="ms-5 list-disc text-gray-500 text-[14px]">
            <li>{{ vehicle.year }}</li>
            <li>{{ vehicle.color }}</li>
            <li>{{ vehicle.carCondition }}</li>
            <li>{{ vehicle.type }}</li>
            <li class="text-primary">
              ${{ new Intl.NumberFormat().format(vehicle.price) }}
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div class="flex flex-col py-10 px-20 gap-10 dark:bg-gray-900">
      <div>
        <h1 class="text-xl">Payment Details</h1>
        <p class="text-gray-400">
          Complete your purchase by providing your payment details
        </p>
      </div>
      <div class="flex flex-col gap-3">
        <h1>Choose a customer</h1>
        <USelect
          placeholder="None"
          :options="customerName"
          v-model="isSelectCustomer"
          required
          icon="i-heroicons-user"
        />
      </div>
      <div class="flex flex-col gap-3">
        <h1>Select Payment Method</h1>

        <div class="flex items-center gap-10">
          <ULandingCard
            icon="i-heroicons-credit-card"
            title="Credit Card"
            @click="isSwitchPayment = true"
            class="cursor-pointer"
          />
          <ULandingCard
            icon="i-material-symbols:attach-money"
            title="Cash"
            @click="isSwitchPayment = false"
            class="cursor-pointer"
          />
        </div>
      </div>

      <div
        class="flex flex-col gap-3"
        v-if="isSwitchPayment"
        data-aos="zoom-in"
      >
        <h1>Cardholder Name</h1>
        <UInput
          placeholder="Full name on card"
          icon="i-heroicons-user"
          required
        />
        <div class="flex flex-col gap-3">
          <UInput
            placeholder="1234 1234 1234 1234"
            icon="i-heroicons-credit-card"
            required
            max="16"
          />
          <div class="grid grid-cols-2 gap-3">
            <UInput
              icon="i-heroicons-calendar"
              placeholder="MM / YY"
              required
              max="4"
            />
            <UInput
              icon="i-heroicons-lock-closed"
              placeholder="CVC"
              required
              max="4"
            />
          </div>
        </div>
        <p class="text-gray-400">Payment are secure and encrypted</p>
      </div>

      <div class="flex flex-col">
        <div class="flex items-center justify-between">
          <h1>Sub Total</h1>
          <span class="text-violet-600"
            >${{ new Intl.NumberFormat().format(vehicle.price) }}</span
          >
        </div>
        <div class="flex items-center justify-between">
          <h1>Discount</h1>
          <span class="text-violet-600">$0</span>
        </div>
        <div class="flex items-center justify-between">
          <h1>Total</h1>
          <span class="text-violet-600"
            >${{ new Intl.NumberFormat().format(vehicle.price) }}</span
          >
        </div>
      </div>

      <UButton
        :loading="checkCard"
        type="submit"
        :label="
          checkCard
            ? `Processing`
            : `Pay now $${new Intl.NumberFormat().format(vehicle.price)}`
        "
        color="black"
        varaint="solid"
        block
        trailing
        size="xl"
        class="font-bold"
      />

      <UModal v-model="IsPayModal">
        <ULandingCard>
          <div class="flex flex-col gap-4 p-4">
            <div class="flex items-start gap-3">
              <UIcon
                name="i-material-symbols:check-circle-outline"
                size="36"
                class="text-green-600"
              />
              <div class="flex flex-col gap-2">
                <h1 class="text-2xl">Payment success</h1>
                <p class="text-sm text-gray-400">
                  Thank you for purchasing our products. Good Luck.
                </p>
              </div>
            </div>
          </div>
        </ULandingCard>
      </UModal>
    </div>
  </form>
</template>

<script setup>
definePageMeta({
  layout: "receipt",
  middleware: "auth",
});

import axios from "axios";

const IsPayModal = ref(false);
const checkCard = ref(false);
const id = useRoute().params.id;
const vehicle = ref();
const customers = ref([]);
const user = useSupabaseUser();
const imageURL = "http://127.0.0.1:8082/";
const customerName = ref();
const isSelectCustomer = ref();
const customerID = ref();
const customerPhone = ref();
const toast = useToast();
const isSwitchPayment = ref(true);

const getVehicleById = async () => {
  try {
    await axios.get(`/api/vehicle/${id}`).then((res) => {
      (vehicle.value = res.data.payload), console.table(res.data.payload);
    });
  } catch (error) {
    console.log(error);
  }
};

const getCustomer = async () => {
  try {
    await axios.get(`/api/customer/getCustomer`).then((res) => {
      customers.value = res.data.payload;
      customerName.value = customers.value.map((key) => key.name);
    });
  } catch (error) {
    console.log(error);
  }
};

const getImage = (name) => {
  return imageURL + name;
};

const getCustomerByName = async () => {
  try {
    await axios
      .post(`/api/customer/search`, {
        query: isSelectCustomer.value,
      })
      .then((res) => {
        customerID.value = res.data.payload[0].name;
        customerPhone.value = res.data.payload[0].phone;
      });
  } catch (error) {
    console.log(error);
  }
};

const handleCheckOut = async () => {
  try {
    await getCustomerByName();
    checkCard.value = true;
    setTimeout(async () => {
      checkCard.value = false;
      await axios
        .post(
          `http://127.0.0.1:8082/api/v1/order?customerName=
        ${customerID.value}&customerPhone=
        ${customerPhone.value}&carName=
        ${vehicle.value.model}&carPrice=
        ${vehicle.value.price}`
        )
        .then((res) => {
          IsPayModal.value = true;
          toast.add({
            title: `${res.data.payload}`,
            icon: "i-heroicons-check-circle",
          });
          setTimeout(() => {
            return navigateTo('/')
          }, 2000);
        });
    }, 3000);
  } catch (error) {
    console.log(error);
  }
};

const goBack = () => {
  useRouter().go(-1);
};

onMounted(() => {
  getVehicleById();
  getCustomer();
});
</script>
