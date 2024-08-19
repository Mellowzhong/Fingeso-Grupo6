<script setup>
import { ref, onMounted, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { getProperties } from '../../property/services/PropertyServices';
import PropertyCard from '../components/PropertyCard.vue';

const store = useStore();

const router = useRouter();

const properties = ref([]);

const getAllProperties = async () => {
    const response = await getProperties();
    
    if (response.success) {
        properties.value = response.data;
    }
};

const isEmptyProperties = computed(() => properties.value.length === 0);

const selectProperty = (property) => {
    store.dispatch('selectProperty', property);
    console.log(property);
    router.push('/property');
};

onMounted(() => {
    getAllProperties();
});
</script>

<template>
    <main class="h-screen mx-32 my-4 p-4">
        <h2 v-if="isEmptyProperties" class="text-2xl">No existen inmuebles 😢</h2>
        <!-- Product Card -->
        <div v-else>
            <h2 class="text-2xl">Inmuebles</h2>
            <div class="grid grid-cols-4 gap-4">
            <PropertyCard v-for="property in properties" :key="property.id" :property @click="selectProperty(property)"/>
        </div>
        </div>
    </main>
</template>