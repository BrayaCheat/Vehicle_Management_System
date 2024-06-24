<template>
  <section id="map" class="border-b border-e dark:border-gray-800 rounded-md shadow-sm">
    <UDashboardNavbar class="capitalize" title="Setting"/>
    <UHorizontalNavigation :links="links"/>
    <div class="p-4 w-full flex flex-col" data-aos="zoom-in">
      <iframe
      src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3908.772962317091!2d104.88950057601151!3d11.568127044090096!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3109519fe4077d69%3A0x20138e822e434660!2sRoyal%20University%20of%20Phnom%20Penh!5e0!3m2!1sen!2sus!4v1716894563487!5m2!1sen!2sus"
      class="h-[870px] rounded-md"
      style="border: 0"
      allowfullscreen="true"
      loading="lazy"
      referrerpolicy="no-referrer-when-downgrade"
    ></iframe>
    </div>
    
  </section>
</template>

<script setup>
definePageMeta({
  middleware: 'auth'
})
const links = [
  {
    label: 'General',
    to: '/settings',
    icon: 'i-heroicons-cog-8-tooth'
  },
  {
    label: 'Map',
    to: '/settings/Map',
    icon: 'i-heroicons-map-pin'
  }
]
</script>

