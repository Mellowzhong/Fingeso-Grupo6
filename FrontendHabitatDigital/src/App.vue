<script setup>
import Header from './common/components/Header.vue';
import PropertyInfo from './property/views/PropertyInfo.vue';
import { RouterView } from 'vue-router'
</script>

<template>
  <section class="h-screen flex flex-col">
    <Header />
    <RouterView />
    
  </section>
</template>