<template>
  <section id="update-vehicle" class="px-72 grid place-items-center">
    <ULandingCard class="w-[600px] my-3" data-aos="zoom-in">
      <form
        enctype="multipart/form-data"
        class="flex flex-col p-3"
        @submit.prevent="handleUpdate"
      >
        <div class="flex flex-col mb-10">
          <div class="flex items-center justify-between">
            <h1 class="font-bold text-lg">Update Vehicle's Information</h1>
          </div>
          <p class="text-sm">Modify a vehicle's information</p>
        </div>

        <div class="w-full grid gap-6">
          <UFormGroup label="Vehicle's model">
            <UInput
              placeholder="JohnDoe"
              icon="i-material-symbols:directions-car-outline"
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
              placeholder="99999"
              icon="i-material-symbols:attach-money-rounded"
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
                icon="i-material-symbols:filter-alt-outline"
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
              label="Upload Photo"
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
            />
            <div class="w-full flex items-center justify-center">
              <img
                v-if="displayImage"
                :src="getImage(displayImage)"
                class="mt-10 object-contain h-[300px]"
              />
              <img
                v-if="displayImage"
                :src="displayImage"
                class="mt-10 object-contain h-[300px]"
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
const vehicleModel = ref({
  model: null,
  year: null,
  color: null,
  carCondition: null,
  file: null,
  type: null,
  price: null,
});
const imgURL = "http://127.0.0.1:8082/";
const displayImage = ref(false);
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

const toast = useToast();

const getImage = (imgName) => {
  return imgURL + imgName;
};

const errorMsg = ref(false);

const handleFileChanged = (e) => {
  const file = e.target.files[0];
  vehicleModel.value.file = file;
  displayImage.value = URL.createObjectURL(file);
};

const uploadFile = () => {
  const file = document.getElementById("image");
  file.click();
};

const fetchVehicle = async () => {
  try {
    await axios.get(`/api/vehicle/${id}`).then((res) => {
      (vehicleModel.value.model = res.data.payload.model),
        (vehicleModel.value.year = res.data.payload.year),
        (vehicleModel.value.color = res.data.payload.color),
        (vehicleModel.value.carCondition = res.data.payload.carCondition),
        (vehicleModel.value.file = res.data.payload.image),
        (vehicleModel.value.type = res.data.payload.type),
        (vehicleModel.value.price = res.data.payload.price);
      displayImage.value = res.data.payload.image;
    });
  } catch (error) {
    console.log(error);
  }
};

const handleUpdate = async () => {
  try {
    const { model, year, color, carCondition, file, type, price } =
      vehicleModel.value;
    const formData = new FormData();
    formData.append("model", model);
    formData.append("year", year);
    formData.append("color", color);
    formData.append("carCondition", carCondition);
    formData.append("file", vehicleModel.value.file);
    formData.append("type", type);
    formData.append("price", price);
    await axios
      .put(`http://127.0.0.1:8082/api/v1/car/${id}`, formData)
      .then(() => {
        toast.add({
          title: `Update success: ${id}`,
          icon: "i-heroicons-check-circle",
        });
        navigateTo("/vehicles");
      });
  } catch (error) {
    console.log(error);
  }
};

onMounted(() => fetchVehicle());
</script>
