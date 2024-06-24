<template>
  <div class="flex items-end">
    <span class="text-xl">Redirecting</span>
    <UIcon name="i-svg-spinners:3-dots-fade" size="20"/>
  </div>
</template>

<script setup>
definePageMeta({
  layout: "auth",
});
onMounted(() => {
  const user = useSupabaseUser();
  if (user.value) {
    return navigateTo("/");
  }
});
</script>
