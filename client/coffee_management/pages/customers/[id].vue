<template>
  <section id="update-customer" class="px-72 grid place-items-center">
    <ULandingCard class="w-[600px] my-3" data-aos="zoom-in">
      <form
        class="w-full flex flex-col p-3"
        @submit.prevent="handleUpdate"
      >
        <div class="flex flex-col mb-10">
          <div class="flex items-center justify-between">
            <h1 class="font-bold text-lg">Update Customer's Information</h1>
          </div>
          <p class="text-sm">Modify a customer's information</p>
        </div>

        <div class="w-full grid gap-6">
          <UFormGroup label="Customer's name">
            <UInput
              placeholder="JohnDoe"
              icon="i-heroicons-face-smile"
              v-model="customer.name"
              required
            />
          </UFormGroup>

          <UFormGroup label="Phone">
            <UInput
              placeholder="+855 12 123 123"
              icon="i-heroicons-phone"
              v-model="customer.phone"
              required
            />
          </UFormGroup>

          <UFormGroup label="Email">
            <UInput
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
          <UButton label="Cancel" variant @click="useRouter().go(-1)" />
          <UButton label="Save Changed" color="black" type="submit" />
        </div>
      </form>
    </ULandingCard>
  </section>
</template>

<script setup>
definePageMeta({
  layout: "receipt",
  middleware: 'auth'
});

import axios from "axios";

const id = useRoute().params.id;
const errorMsg = ref(false);
const customer = ref({
  name: null,
  email: null,
  phone: null,
});
const toast = useToast();

const handleUpdate = async () => {
  try {
    await axios
      .put(`/api/customer/update/${id}`, {
        name: customer.value.name,
        email: customer.value.email,
        phone: customer.value.phone,
      })
      .then(() => {
        toast.add({
          title: `Updating Customer`,
          icon: "i-heroicons-check-circle",
        });
        navigateTo("/customers");
      });
  } catch (error) {
    console.log(error);
  }
};

const fetchCusomter = async () => {
  try {
    await axios.get(`/api/customer/${id}`).then((res) => {
      customer.value.name = res.data.payload.name;
      customer.value.email = res.data.payload.email;
      customer.value.phone = res.data.payload.phone;
    });
  } catch (error) {
    console.log(error);
  }
};

onMounted(() => fetchCusomter());
</script>
