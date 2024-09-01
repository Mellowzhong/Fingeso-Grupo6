<script setup>
const props = defineProps({
    property: {
        type: Object,
        required: true,
    },
});
</script>

<template>
    <figure class="w-64 p-4 relative rounded-lg shadow-md overflow-hidden m-2"
        :class="{ 'bg-white': property.disponibility, 'bg-gray-400': !property.disponibility }">
        <div v-if="property.photos.length === 0">
            <img src="../../Images/noImage.png" alt="Imagen de la propiedad" class="w-52 h-52 object-scale-down mx-auto">
        </div>
        <div v-else>
            <img :src="property.photos[0]" :alt="property.type" class="w-52 h-52 object-scale-down mx-auto">
        </div>
        <figcaption class="grid">
            <span class="text-lg font-bold mb-2">{{ property.type }}</span>
            <span class="text-lg font-bold mb-2"> {{ property.price }} UF </span>
            <span class="text-gray-600 font-bold mb-4">{{ property.direction }}</span>
        </figcaption>
    </figure>
</template>