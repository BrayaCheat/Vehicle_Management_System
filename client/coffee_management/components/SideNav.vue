<template>
  <UDashboardPanel
    :width="300"
    :resizable="{ min: 200, max: 400 }"
    class="h-screen flex-col justify-between md:flex hidden fixed"
  >
    <UDashboardNavbar>
      <template #left>
        <div class="flex items-center">
          <UIcon name="i-logos:drift" class="w-[200px] h-[30px]" />
        </div>
      </template>
    </UDashboardNavbar>

    <UDashboardSidebar>
      <UDashboardSearchButton
        label="Search..."
        @click="isSearchSetting = true"
      />
      <UDashboardSidebarLinks :links="links" />
    </UDashboardSidebar>

    <!-- modal-for-search -->
    <UModal v-model="isSearchSetting">
      <ULandingCard>
        <div class="flex items-center">
          <UInput
            placeholder="Search for settings..."
            v-model="SearchForSettings"
            icon="i-heroicons-magnifying-glass"
            color="primary"
            class="w-full"
          />
        </div>
        <UDivider />
        <div class="flex flex-col gap-3">
          <div v-for="item in handleSearch" :key="item">
            <UButton
              :to="`${item.to}`"
              :label="item.label"
              color="gray"
              variant="ghost"
              @click="isSearchSetting = false"
              :icon="item.icon"
              class="w-full"
            />
            <UDivider />
          </div>
          <div class="flex items-center px-1">
            <UTooltip text="Toggle DarkMode">
              <UColorModeButton />
            </UTooltip>
          </div>
        </div>
      </ULandingCard>
    </UModal>

    <div
      class="p-4 flex flex-col gap-1 border-t dark:border-gray-800 text-xs dark:text-gray-400"
    >
      <UButton
        label="Regarding The Project"
        icon="i-line-md:plus-circle"
        variant="ghost"
        color="gray"
      />
      <UButton
        label="Help & Support"
        icon="i-heroicons-question-mark-circle"
        variant="ghost"
        color="gray"
        @click="isOpen = !isOpen"
      />
    </div>

    <div v-if="user" class="flex flex-col p-4 border-t dark:border-gray-800">
      <UDropdown :items="items" :popper="{ placement: 'bottom-start' }">
        <UButton
          :label="user.email"
          variant="ghost"
          color="gray"
          class="w-full"
        >
          <template #leading>
            <UAvatar :src="user.user_metadata.avatar_url" size="sm" />
          </template>
        </UButton>

        <template #account="{ item }">
          <div class="text-left">
            <p>Signed in as</p>
            <p class="truncate font-medium text-gray-900 dark:text-white">
              {{ item.label }}
            </p>
          </div>
        </template>

        <template #item="{ item }">
          <span class="truncate">{{ item.label }}</span>

          <UIcon
            :name="item.icon"
            class="flex-shrink-0 h-4 w-4 text-gray-400 dark:text-gray-500 ms-auto"
          />
        </template>
      </UDropdown>
    </div>
  </UDashboardPanel>

  <USlideover v-model="isOpen">
    <UCard
      class="flex flex-col flex-1"
      :ui="{
        body: { base: 'flex-1' },
        ring: '',
        divide: 'divide-y divide-gray-100 dark:divide-gray-800',
      }"
    >
      <template #header>
        <div class="flex items-center justify-between">
          Help & Support
          <UButton icon="i-heroicons-x-mark" variant @click="isOpen = false" />
        </div>
      </template>

      <div class="grid gap-3">
        <UButton
          class="w-full"
          variant="solid"
          color="gray"
          to="https://nuxt.com/docs/getting-started/introduction"
          target="_blank"
        >
          <Icon name="i-heroicons-book-open" class="w-5 h-5" />
          Documentation
        </UButton>

        <UButton
          class="w-full"
          variant="solid"
          color="gray"
          to="https://github.com/BrayaCheat"
          target="_blank"
        >
          <Icon name="i-mdi:github" class="w-5 h-5" />
          GitHub Repository
        </UButton>

        <UButton
          class="w-full"
          variant="solid"
          color="gray"
          to="tel:0965984619"
          target="_blank"
        >
          <Icon
            name="i-material-symbols:headset-mic-outline-sharp"
            class="w-5 h-5"
          />
          Call Center & Support
        </UButton>

        <UButton
          class="w-full"
          variant="solid"
          color="gray"
          to="mailto:prayacheat@gmail.com"
          target="_blank"
        >
          <Icon name="i-material-symbols:bug-report-outline" class="w-5 h-5" />
          Report Bugs & Issue
        </UButton>
      </div>

      <template #footer>
        <Placeholder class="h-8" />
        <span
          >Developed By:
          <a href="https://www.facebook.com/" target="_blank"
            >Cheat ChanChaoBraya</a
          ></span
        >
      </template>
    </UCard>
  </USlideover>

  <UModal v-model="showProfile">
    <div v-if="user" class="p-6 grid gap-6">
      <UDivider label="User's Information" />
      <div class="flex items-start gap-6">
        <div class="flex flex-col gap-3 text-md text-gray-400">
          <span>Username: {{ user.user_metadata.full_name }}</span>
          <span>Email: {{ user.user_metadata.email }}</span>
          <span>Provider: {{ user.app_metadata.provider }}</span>
          <span
            >Last Sign In:
            {{ new Date(user.last_sign_in_at).toLocaleString() }}</span
          >
        </div>
        <img :src="user.user_metadata.avatar_url" class="w-[120px]" />
      </div>
    </div>
  </UModal>
</template>

<script setup>
import { useStateStore } from "~/store/state";
const { toggleDashboardSearch } = useUIState()
const state = useStateStore();
const SearchForSettings = ref("");
const isSearchSetting = ref(false);
const selected = ref(null);
const isOpen = ref(false);
const user = useSupabaseUser();
const supabase = useSupabaseClient();
const toast = useToast();
const showProfile = ref(false);

const items = [
  [
    {
      label: `${user.value.email}`,
      slot: "account",
      disabled: true,
    },
  ],
  [
    {
      label: "Profile",
      icon: "i-heroicons-user-circle",
      click: () => {
        showProfile.value = true;
      },
    },
    {
      label: "Settings",
      icon: "i-heroicons-cog-8-tooth",
      to: "/settings",
    },
  ],
  [
    {
      label: "Documentation",
      icon: "i-heroicons-book-open",
      to: "https://nuxt.com/docs/getting-started/installation",
    },
    {
      label: "Github Repository",
      icon: "i-mdi:github",
      to: "https://github.com/BrayaCheat",
    },
    {
      label: "Status",
      icon: "i-heroicons-signal",
      click: () => {
        toast.add({
          title: `${user.value.role}`,
          icon: "i-heroicons-finger-print",
        });
      },
    },
  ],
  [
    {
      label: "Sign out",
      icon: "i-heroicons-arrow-left-on-rectangle",
      click: async () => {
        try {
          const { error } = await supabase.auth.signOut();
          if (error) {
            console.log(error);
          }
          return navigateTo("/login");
        } catch (error) {
          console.log(error);
        }
      },
    },
  ],
];

const navigateLink = () => {
  return navigateTo(`/${selected.value}`);
};
const links = [
  {
    label: "Dashboard",
    icon: "i-material-symbols-light:dashboard-outline",
    to: "/",
  },
  {
    label: "Customers",
    icon: "i-heroicons-user-group",
    to: "/customers",
  },
  {
    label: "Vehicles",
    icon: "i-material-symbols:directions-car-outline",
    to: "/vehicles",
  },
  {
    label: "Report",
    icon: "i-heroicons-chart-bar-square",
    children: [
      {
        label: "List All Reports",
        to: "/reports/listReport",
      },
      {
        label: "Summary All Reports",
        to: "/reports/summary",
      },
    ],
  },
  {
    label: "Setting",
    icon: "i-line-md:cog-loop",
    children: [
      {
        label: "General",
        to: "/settings",
      },
      {
        label: "Location",
        to: "/settings/map",
      },
    ],
  },
];

const sideNavForSearch = [
  {
    label: "Dashboard",
    icon: "i-material-symbols-light:dashboard-outline",
    to: "/",
  },
  {
    label: "Customers",
    icon: "i-heroicons-user-group",
    to: "/customers",
  },
  {
    label: "Vehicles",
    icon: "i-material-symbols:directions-car-outline",
    to: "/vehicles",
  },
  {
    label: "List All Reports",
    icon: "i-heroicons-chart-bar-square",
    to: "/reports/listReport",
  },
  {
    label: "Summary All Reports",
    icon: "i-heroicons-chart-bar-square",
    to: "/reports/summary",
  },
  {
    label: "Setting",
    icon: "i-line-md:cog-loop",
    to: "/settings",
  },
];

const handleSearch = computed(() => {
  return sideNavForSearch.filter((item) =>
    item.label.toLowerCase().includes(SearchForSettings.value.toLowerCase())
  );
});
</script>
