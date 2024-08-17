<script setup>
import Header from './common/components/Header.vue';
import Home from './common/components/Home.vue';
import { RouterView } from 'vue-router'
</script>

<template>
  <section class="nav">
    <Header />
    <RouterView />
  </section>
</template>