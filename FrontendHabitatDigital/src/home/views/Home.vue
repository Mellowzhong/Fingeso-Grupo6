<script setup>
import { ref, onMounted } from 'vue';
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
        <!-- Product Card -->
        <div class="grid grid-cols-4 gap-4">
            <PropertyCard v-for="property in properties" :key="property.id" :property @click="selectProperty(property)"/>
        </div>
    </main>
</template>