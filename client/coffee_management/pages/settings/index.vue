<template>
  <section id="setting-page">
    <UDashboardNavbar class="capitalize" title="Setting">
      <!-- <template #right>
        <div class="flex items-center gap-3">
          <UButton
            label="Save changed"
            color="black"
          />
        </div>
      </template> -->
    </UDashboardNavbar>
    <UHorizontalNavigation :links="links" />

    <div class="p-4" data-aos="zoom-in">
      <div
        class="flex items-center justify-between py-4 border-b dark:border-gray-800"
      >
        <div class="flex flex-col">
          <h1 class="text-md">Theme</h1>
          <p class="text-gray-400 text-sm">
            Customize the look and feel of your dashboard.
          </p>
        </div>
        <UColorModeSelect />
      </div>

      <form
        @submit.prevent="handleChangeWebsiteName"
        class="flex items-center justify-between py-4 border-b dark:border-gray-800"
      >
        <div class="flex flex-col">
          <h1 class="text-md">Change System's Name</h1>
          <p class="text-gray-400 text-sm">
            Customize the look and feel of your dashboard.
          </p>
        </div>
        <UInput v-model="newName" :placeholder="state.WebTitle" />
      </form>
    </div>
  </section>
</template>

<script setup>
import { useStateStore } from "~/store/state";

definePageMeta({
  middleware: "auth",
});

const toast = useToast()
const newName = ref();
const state = useStateStore();
const links = [
  {
    label: "General",
    to: "/settings",
    icon: "i-line-md:cog-loop",
  },
  {
    label: "Map",
    to: "/settings/Map",
    icon: "i-line-md:map-marker",
  },
];

const handleChangeWebsiteName = () => {
  state.changeName(newName.value)
  toast.add({
    title: `Updated!`,
    icon: 'i-material-symbols:bigtop-updates-rounded'
  })
};
</script>
