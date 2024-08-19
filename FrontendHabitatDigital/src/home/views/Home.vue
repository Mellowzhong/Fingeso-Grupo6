<script setup>
import { ref, onMounted } from 'vue';
import { getProperties } from '../../property/services/PropertyServices';
import PropertyCard from '../components/PropertyCard.vue';

const properties = ref([]);

const getAllProperties = async () => {
    const response = await getProperties();
    
    if (response.success) {
        properties.value = response.data;
    }
};

onMounted(() => {
    getAllProperties();
});
</script>

<template>
    <main class="h-screen mx-32 my-4 p-4">
        <!-- Product Card -->
        <div class="grid grid-cols-4 gap-4">
            <PropertyCard v-for="property in properties" :key="property.id" :property />
        </div>
    </main>
</template>