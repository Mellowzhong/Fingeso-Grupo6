<script setup>
const props = defineProps({
    property: {
        type: Object,
        required: true,
    },
});
</script>

<template>
    <figure class="w-64 p-4 relative bg-white rounded-lg shadow-md overflow-hidden m-2">
        <img :src="property.photos[0]" :alt="property.type" class="w-52 h-52 object-scale-down mx-auto">
        <figcaption class="grid">
            <span class="text-lg font-bold mb-2">{{ property.type }}</span>
            <span class="text-lg font-bold mb-2"> {{ property.price }} </span>
            <span class="text-gray-600 font-bold mb-4">{{ property.direction }}</span>
        </figcaption>
    </figure>
</template>